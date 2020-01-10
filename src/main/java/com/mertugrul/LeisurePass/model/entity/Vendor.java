package com.mertugrul.LeisurePass.model.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity @Table(name = "vendor")
public class Vendor {

    @Id
    private Long vendorId;
    private String vendorName;
    private String address;
}
