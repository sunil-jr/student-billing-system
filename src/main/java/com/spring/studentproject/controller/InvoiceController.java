package com.spring.studentproject.controller;

import com.spring.studentproject.dto.invoice.InvoiceRequest;
import com.spring.studentproject.dto.invoice.InvoiceResponse;
import com.spring.studentproject.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/mis/invoice")
public class InvoiceController {
    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping(value = "/{studentId}")
    public InvoiceResponse addInvoice(@RequestBody @Valid InvoiceRequest invoiceRequest, @PathVariable Long studentId) {
        return invoiceService.addInvoice(invoiceRequest, studentId);
    }

    @PutMapping("/update/{id}")
    public InvoiceResponse updateInvoice(@PathVariable(value = "id") Long invoiceId, @RequestBody @Valid InvoiceRequest invoiceRequest) {
        return invoiceService.updateInvoice(invoiceId, invoiceRequest);
    }

    @GetMapping("/getAllDueInvoices")
    public List<InvoiceResponse> getAllDueInvoices() {
        return invoiceService.getAllDueInvoices();
    }

    @GetMapping("/getAllPaidInvoices")
    public List<InvoiceResponse> getAllPaidInvoices() {
        return invoiceService.getAllPaidInvoices();
    }

    @GetMapping("/getAllInvoices")
    public List<InvoiceResponse> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    @GetMapping("/filter")
    public List<InvoiceResponse> filterInvoice(@RequestParam(value="year") Integer year,
                                               @RequestParam(value = "month", required = false) Integer month,
                                               @RequestParam(value = "studentId", required = false) Long studentId) {
        return invoiceService.filterInvoice(year, month, studentId);
    }

    @GetMapping("/getInvoiceByStudent/{id}")
    public List<InvoiceResponse> getInvoiceByStudentId(@PathVariable(value = "id") Long studentId) {
        return invoiceService.getInvoiceByStudentId(studentId);
    }

}
