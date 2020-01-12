package com.mertugrul.LeisurePass.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;


@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private int passLength;
    private Boolean isActive;

}
