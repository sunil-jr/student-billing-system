package com.spring.studentproject.service;

import com.spring.studentproject.dto.invoice.InvoiceRequest;
import com.spring.studentproject.dto.invoice.InvoiceResponse;

import java.util.List;

public interface InvoiceService {

    InvoiceResponse addInvoice(InvoiceRequest invoiceRequest, Long studentId);

    InvoiceResponse updateInvoice(Long invoiceId, InvoiceRequest invoiceRequest);

    List<InvoiceResponse> getAllDueInvoices();

    List<InvoiceResponse> getAllDueInvoicesForStudent();

    List<InvoiceResponse> getAllPaidInvoices();

    List<InvoiceResponse> getAllInvoices();

    List<InvoiceResponse> getInvoiceByStudentId(Long studentId);

    List<InvoiceResponse> filterInvoice(Integer year, Integer month, Long studentId);
}
