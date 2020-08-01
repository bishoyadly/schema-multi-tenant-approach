package com.example.demo.controllers;

import com.example.demo.entities.Tenant;
import com.example.demo.services.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/tenants")
public class TenantController {

    private TenantService tenantService;

    @Autowired
    TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping("/{tenantId}")
    public ResponseEntity<Tenant> getTenant(@PathVariable(value = "tenantId") String tenantId) {
        return ResponseEntity.ok(this.tenantService.getTenant(tenantId));
    }

    @PostMapping
    public void createTenant(@RequestBody Tenant tenant) throws Exception {
        this.tenantService.createTenant(tenant);
        ResponseEntity.status(HttpStatus.OK).body("Created");
    }


}
