package com.spring.studentproject.dto.dashboard;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardResponse {
    private final Long totalStudent;
    private final Long invoiceCleared;
    private final Long invoiceDue;
    private final Long criticalInvoiceOverdue;
    private final Double totalAmountCollection;
    private final Double totalDueCollection;
}
