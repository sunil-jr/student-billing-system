package com.spring.studentproject.dto.invoice;

import com.spring.studentproject.constant.DiscountType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceRequest {

    private Long studentId;

    @NotNull
    private Integer year;

    @NotNull
    private Integer dueMonth;

    @NotNull
    private DiscountType discountType;

    @NotNull
    private Double rate;

    @NotNull
    private Double amount;

    @NotNull
    private Double paidAmount;

}
