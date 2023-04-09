package com.spring.studentproject.dto.invoice;

import com.spring.studentproject.constant.DiscountType;
import com.spring.studentproject.dto.student.StudentResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class InvoiceResponse {

    private Long invoiceId;

    private StudentResponse studentResponse;

    private DiscountType discountType;

    private Double amount;

    private Boolean isPaid;

    private Double discountAmount;

    private Double remainingAmount;

    private LocalDate dueDate;

    private Integer year;

    private Integer dueMonth;

    private Boolean critical;

    private Double rate;


}
