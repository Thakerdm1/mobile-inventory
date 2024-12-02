package com.example.mobileinventory.service;

import com.example.mobileinventory.entity.MobilePhone;
import com.example.mobileinventory.repository.MobilePhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@Service
public class MobileService {
    @Autowired
    private MobilePhoneRepository repository;

    public List<MobilePhone> getAllPhones() {
        return repository.findAll();
    }

    public Optional<MobilePhone> getPhoneById(Long id) {
        return repository.findById(id);
    }

    public MobilePhone addPhone(MobilePhone phone) {
        phone.setLastUpdated(LocalDateTime.now());
        return repository.save(phone);
    }

    public MobilePhone updatePhone(Long id, MobilePhone phoneDetails) {
        return repository.findById(id).map(phone -> {
            phone.setBrand(phoneDetails.getBrand());
            phone.setModel(phoneDetails.getModel());
            phone.setPrice(phoneDetails.getPrice());
            phone.setStock(phoneDetails.getStock());
            phone.setLastUpdated(LocalDateTime.now());
            return repository.save(phone);
        }).orElseThrow(() -> new RuntimeException("Phone not found"));
    }

    public void deletePhone(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Phone with ID " + id + " not found");
        }
        repository.deleteById(id);
    }
}
