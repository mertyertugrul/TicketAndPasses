package com.mertugrul.LeisurePass.model.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Data
@Entity @Table(name = "customer")
public class Customer {

    @Id
    private Long customerId;
    private String fullName;
    private String address;

}
