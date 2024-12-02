package com.example.mobileinventory.controller;

import com.example.mobileinventory.entity.MobilePhone;
import com.example.mobileinventory.service.MobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/phones")
public class MobileController {
    @Autowired
    private MobileService service;

    @GetMapping
    public List<MobilePhone> getAllPhones() {
        return service.getAllPhones();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MobilePhone> getPhoneById(@PathVariable Long id) {
        return service.getPhoneById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public MobilePhone addPhone(@Valid @RequestBody MobilePhone phone) {
        return service.addPhone(phone);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MobilePhone> updatePhone(@PathVariable Long id, @Valid @RequestBody MobilePhone phoneDetails) {
        return ResponseEntity.ok(service.updatePhone(id, phoneDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhone(@PathVariable Long id) {
        service.deletePhone(id);
        return ResponseEntity.noContent().build();
    }
}
