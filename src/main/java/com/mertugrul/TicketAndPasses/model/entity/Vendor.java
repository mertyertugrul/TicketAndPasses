package com.mertugrul.TicketAndPasses.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity @Table(name = "vendor")
public class Vendor {

    @Id
    private Long vendorId;
    private String vendorName;
    private String address;
}
