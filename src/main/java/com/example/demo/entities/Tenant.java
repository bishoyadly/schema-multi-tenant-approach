package com.example.demo.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tenants")
@Getter
@Setter
public class Tenant {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid;

    //    @Column(name = "schema_name")
    private String schemaName;

    //    @Column(name = "tenant_name")
    private String tenantName;

    //    @Column(name = "created_at")
    private Date createdAt;

    //    @Column(name = "updated_at")
    private Date updatedAt;
}
