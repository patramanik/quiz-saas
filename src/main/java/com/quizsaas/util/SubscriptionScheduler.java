package com.quizsaas.util;

import com.quizsaas.entity.Organization;
import com.quizsaas.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class SubscriptionScheduler {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Scheduled(cron = "0 0 */6 * * *") // every 6 hours
    public void checkExpiredSubscriptions() {
        List<Organization> organizations = organizationRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        for (Organization org : organizations) {
            if ("ACTIVE".equals(org.getStatus()) && org.getSubscriptionEnd() != null
                    && org.getSubscriptionEnd().isBefore(now)) {
                org.setStatus("EXPIRED");
                organizationRepository.save(org);
                System.out.println("Organization " + org.getName() + " subscription expired.");
            }
        }
    }
}
