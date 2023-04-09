package com.spring.studentproject.service.impl;

import com.spring.studentproject.dto.dashboard.DashboardResponse;
import com.spring.studentproject.repository.InvoiceRepository;
import com.spring.studentproject.repository.StudentRepository;
import com.spring.studentproject.service.DashboardService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Service
public class DashboardServiceImpl implements DashboardService {
    private final StudentRepository studentRepository;
    private final InvoiceRepository invoiceRepository;

    public DashboardServiceImpl(StudentRepository studentRepository, InvoiceRepository invoiceRepository) {
        this.studentRepository = studentRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public DashboardResponse getDashboardData(Boolean isPaid, LocalDate year) {
        LocalDate today = LocalDate.now();
        Long totalStudent = studentRepository.count();
        Long invoiceCleared = invoiceRepository.totalPaidInvoice(today.getYear());
        Long invoiceDue = invoiceRepository.totalDueInvoice(today.getYear());
        Long criticalInvoiceOverdue = invoiceRepository.totalCriticalInvoice(today.getYear());
        Double totalAmountCollection = invoiceRepository.totalAmountCollection(isPaid,today.getYear());

        return DashboardResponse.builder()
                .totalStudent(totalStudent)
                .invoiceCleared(invoiceCleared)
                .invoiceDue(invoiceDue)
                .criticalInvoiceOverdue(criticalInvoiceOverdue)
                .totalAmountCollection(totalAmountCollection)
                .build();
    }
}
