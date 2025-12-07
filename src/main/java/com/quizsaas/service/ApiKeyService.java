package com.quizsaas.service;

import com.quizsaas.entity.ApiKey;
import com.quizsaas.entity.Organization;
import com.quizsaas.repository.ApiKeyRepository;
import com.quizsaas.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApiKeyService {

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    public ApiKey generateApiKey(Long organizationId) {
        Organization org = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        ApiKey apiKey = new ApiKey();
        apiKey.setKey(UUID.randomUUID().toString());
        apiKey.setStatus("ACTIVE");
        apiKey.setOrganization(org);

        return apiKeyRepository.save(apiKey);
    }
}
