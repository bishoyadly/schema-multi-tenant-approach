package com.example.demo.repositories;

import com.example.demo.entities.Tenant;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface TenantRepository extends PagingAndSortingRepository<Tenant, String> {

    public Optional<Tenant> findBySchemaName(String schemaName);

}
