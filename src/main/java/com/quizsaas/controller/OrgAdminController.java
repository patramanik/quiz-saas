package com.quizsaas.controller;

import com.quizsaas.config.TenantResolver;
import com.quizsaas.dto.OrganizationDTO;
import com.quizsaas.dto.PaymentRequestDTO;
import com.quizsaas.service.ApiKeyService;
import com.quizsaas.service.OrganizationService;
import com.quizsaas.service.PaymentService;
import com.quizsaas.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/org-admin")
public class OrgAdminController {

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ApiKeyService apiKeyService;

    @Autowired
    private TenantResolver tenantResolver;

    @PostMapping("/create-org")
    public ResponseEntity<Object> createOrganization(@RequestBody OrganizationDTO dto) {
        return ResponseBuilder.success("Organization created", organizationService.createOrganization(dto));
    }

    @PostMapping("/payment/initialize")
    public ResponseEntity<Object> initializePayment(@RequestBody PaymentRequestDTO request) {
        return ResponseBuilder.success("Payment initialized",
                Map.of("orderId", paymentService.initializePayment(request)));
    }

    @PostMapping("/payment/verify")
    public ResponseEntity<Object> verifyPayment(@RequestBody Map<String, String> payload) {
        paymentService.verifyPayment(
                payload.get("orderId"),
                payload.get("paymentId"),
                payload.get("signature"),
                Long.parseLong(payload.get("organizationId")));
        return ResponseBuilder.success("Payment verified and subscription activated", null);
    }

    @PostMapping("/api-keys/generate")
    public ResponseEntity<Object> generateApiKey() {
        Long orgId = tenantResolver.getCurrentOrganizationId();
        return ResponseBuilder.success("API Key generated", apiKeyService.generateApiKey(orgId));
    }
}
