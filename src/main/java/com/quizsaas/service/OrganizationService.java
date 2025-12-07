package com.quizsaas.service;

import com.quizsaas.dto.OrganizationDTO;
import com.quizsaas.entity.Organization;
import com.quizsaas.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    public Organization createOrganization(OrganizationDTO dto) {
        Organization org = new Organization();
        org.setName(dto.getName());
        org.setSubscriptionPlan(dto.getSubscriptionPlan());

        if ("FREE".equalsIgnoreCase(dto.getSubscriptionPlan())) {
            org.setStatus("ACTIVE");
        } else {
            org.setStatus("PENDING_PAYMENT");
        }

        return organizationRepository.save(org);
    }

    public Organization updateStatus(Long orgId, String status) {
        Organization org = organizationRepository.findById(orgId)
                .orElseThrow(() -> new RuntimeException("Organization not found"));
        org.setStatus(status);
        return organizationRepository.save(org);
    }
}
