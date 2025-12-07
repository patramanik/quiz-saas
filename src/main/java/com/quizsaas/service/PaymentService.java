package com.quizsaas.service;

import com.quizsaas.dto.PaymentRequestDTO;
import com.quizsaas.entity.Organization;
import com.quizsaas.entity.PaymentTransaction;
import com.quizsaas.repository.OrganizationRepository;
import com.quizsaas.repository.PaymentTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Autowired
    private PaymentTransactionRepository paymentTransactionRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    public String initializePayment(PaymentRequestDTO request) {
        // Integrate with Stripe/Razorpay here
        // For now, return a dummy order ID
        return "order_" + System.currentTimeMillis();
    }

    public void verifyPayment(String orderId, String paymentId, String signature, Long organizationId) {
        // Verify signature with Payment Gateway

        // If success:
        Organization org = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        PaymentTransaction transaction = new PaymentTransaction();
        transaction.setOrganization(org);
        transaction.setTransactionId(paymentId);
        transaction.setAmount(new BigDecimal("99.99")); // Example amount
        transaction.setCurrency("USD");
        transaction.setStatus("SUCCESS");
        transaction.setGateway("STRIPE"); // or RAZORPAY
        paymentTransactionRepository.save(transaction);

        org.setStatus("ACTIVE");
        org.setSubscriptionStart(LocalDateTime.now());
        org.setSubscriptionEnd(LocalDateTime.now().plusMonths(1)); // Example duration
        organizationRepository.save(org);
    }
}
