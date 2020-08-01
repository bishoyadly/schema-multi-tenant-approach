package com.example.demo.services;

import com.example.demo.config.TenantContext;
import com.example.demo.entities.Tenant;
import com.example.demo.repositories.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenantService {

    private TenantRepository tenantRepository;

    @Autowired
    TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public Tenant getTenant(String tenantId) {
        return tenantRepository.findById(tenantId).get();
    }

    public void createTenant(Tenant tenant) {
        tenantRepository.save(tenant);
    }
}
