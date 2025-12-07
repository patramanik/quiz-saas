package com.quizsaas.controller;

import com.quizsaas.repository.OrganizationRepository;
import com.quizsaas.repository.PaymentTransactionRepository;
import com.quizsaas.service.OrganizationService;
import com.quizsaas.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/super-admin")
public class SuperAdminController {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private PaymentTransactionRepository paymentTransactionRepository;

    @GetMapping("/organizations")
    public ResponseEntity<Object> getAllOrganizations() {
        return ResponseBuilder.success("Organizations fetched successfully", organizationRepository.findAll());
    }

    @PutMapping("/organizations/{id}/block")
    public ResponseEntity<Object> blockOrganization(@PathVariable Long id) {
        return ResponseBuilder.success("Organization blocked", organizationService.updateStatus(id, "BLOCKED"));
    }

    @PutMapping("/organizations/{id}/unblock")
    public ResponseEntity<Object> unblockOrganization(@PathVariable Long id) {
        return ResponseBuilder.success("Organization unblocked", organizationService.updateStatus(id, "ACTIVE"));
    }

    @GetMapping("/payments")
    public ResponseEntity<Object> getAllPayments() {
        return ResponseBuilder.success("Payments fetched successfully", paymentTransactionRepository.findAll());
    }
}
