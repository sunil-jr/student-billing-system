package com.spring.studentproject.model;

import com.spring.studentproject.constant.DiscountType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "Student Invoice")
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice id")
    private Long invoiceId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student id")
    private StudentEntity studentEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount type")
    private DiscountType discountType;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "paidAmount")
    private Double paidAmount;

    @Column(name = "isPaid")
    private Boolean isPaid = false;

    @Column(name = "isCritical")
    private Boolean critical = false;

    @Column(name = "discount amount")
    private Double discountAmount;

    @Column(name = "remaining amount")
    private Double remainingAmount;

    @Column(name = "due date")
    private LocalDate dueDate;

    @Column(name = "year")
    private Integer year;

    @Column(name = "dueMonth")
    private Integer dueMonth;

    @Column(name = "rate")
    private Double rate;

}
