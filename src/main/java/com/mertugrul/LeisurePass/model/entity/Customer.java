package com.mertugrul.LeisurePass.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity @Table(name = "customer")
public class Customer {

    @Id
    private Long customerId;
    private String fullName;
    private String address;

}
