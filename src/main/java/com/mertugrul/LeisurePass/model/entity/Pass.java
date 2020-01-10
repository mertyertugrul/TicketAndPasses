package com.mertugrul.LeisurePass.model.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;


@Data
@Entity @Table(name = "pass")
public class Pass {

    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    private String cityName;
    private Long customerId;
    private String customerName;
    private String customerAddress;
    private Long vendorId;
    private String vendorName;
    private String vendorAddress;
    private Instant activationDate;
    private Instant expireDate;
    private int passLength;

}
