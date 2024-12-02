package com.example.mobileinventory.scheduler;

import com.example.mobileinventory.entity.MobilePhone;
import com.example.mobileinventory.repository.MobilePhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Component
public class ScheduledTasks {

    @Autowired
    private MobilePhoneRepository repository;

    private final RestTemplate restTemplate = new RestTemplate();

    // Task 1: Send stock summary
    @Scheduled(fixedRate = 5000) // Run every 5 seconds
    public void sendStockSummary() {
        List<MobilePhone> phones = repository.findAll();
        System.out.println("Stock Summary (Every 5 Seconds):");
        for (MobilePhone phone : phones) {
            System.out.println("ID: " + phone.getId() + ", Model: " + phone.getModel() + ", Stock: " + phone.getStock());
        }
    }

    // Task 2: Post promotions to an external service
    @Scheduled(fixedRate = 5000) // Run every 5 seconds
    public void postPromotion() {
        List<MobilePhone> phones = repository.findAll();
        if (!phones.isEmpty()) {
            MobilePhone phone = phones.get(0); // Promote the first phone for example
            String promotionMessage = "Promotion: Check out " + phone.getBrand() + " " + phone.getModel() + " at $" + phone.getPrice();

            // Simulated external API endpoint (replace with a real endpoint if available)
            String externalApiUrl = "https://jsonplaceholder.typicode.com/posts";

            // Prepare the request payload
            Map<String, String> requestPayload = new HashMap<>();
            requestPayload.put("title", "New Promotion");
            requestPayload.put("body", promotionMessage);

            // Make the POST request
            restTemplate.postForObject(externalApiUrl, requestPayload, String.class);

            System.out.println("Posted promotion: " + promotionMessage);
        }
    }

    // Task 3: Archive out-of-stock items
    @Scheduled(fixedRate = 5000) // Run every 5 seconds
    public void archiveOutOfStockItems() {
        List<MobilePhone> outOfStockPhones = repository.findAll().stream()
                .filter(phone -> phone.getStock() == 0)
                .toList();
        if (!outOfStockPhones.isEmpty()) {
            System.out.println("Archiving Out-of-Stock Items (Every 5 Seconds):");
            for (MobilePhone phone : outOfStockPhones) {
                System.out.println("Archiving: " + phone.getBrand() + " " + phone.getModel());
                // Implement archiving logic (e.g., move to another table or mark as archived)
            }
        }
    }

    // Task 4: Check outdated models
    @Scheduled(fixedRate = 5000) // Run every 5 seconds
    public void checkOutdatedModels() {
        List<MobilePhone> outdatedPhones = repository.findAll().stream()
                .filter(phone -> phone.getLastUpdated().isBefore(LocalDateTime.now().minusMonths(3)))
                .toList();
        if (!outdatedPhones.isEmpty()) {
            System.out.println("Outdated Models (Every 5 Seconds):");
            for (MobilePhone phone : outdatedPhones) {
                System.out.println("Outdated Model: " + phone.getBrand() + " " + phone.getModel() + ", Last Updated: " + phone.getLastUpdated());
            }
        }
    }
}
