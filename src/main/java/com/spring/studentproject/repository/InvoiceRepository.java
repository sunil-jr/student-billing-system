package com.spring.studentproject.repository;

import com.spring.studentproject.model.InvoiceEntity;
import com.spring.studentproject.model.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {

    @Query("SELECT invoice FROM InvoiceEntity invoice WHERE invoice.isPaid=false")
    List<InvoiceEntity> findAllDueInvoices();

    @Query("SELECT invoice FROM InvoiceEntity invoice WHERE invoice.isPaid=true")
    List<InvoiceEntity> findAllPaidInvoices();

    @Query("SELECT invoice FROM InvoiceEntity invoice WHERE invoice.studentEntity.id=?1")
    List<InvoiceEntity> findInvoiceEntityByStudentId(Long studentId);

    @Query("SELECT invoice FROM InvoiceEntity invoice WHERE invoice.critical=true")
    List<InvoiceEntity> findCriticalDues();

    @Query("SELECT COUNT (invoice) FROM InvoiceEntity invoice WHERE invoice.isPaid=true and invoice.year=?1")
    Long totalPaidInvoice(Integer year);

    @Query("SELECT COUNT (invoice) FROM InvoiceEntity invoice WHERE invoice.isPaid=false and invoice.year=?1")
    Long totalDueInvoice(int year);

    @Query("SELECT COUNT (invoice) FROM InvoiceEntity invoice WHERE invoice.critical=true and invoice.year=?1")
    Long totalCriticalInvoice(int year);

    @Query("SELECT SUM (invoice.amount) FROM InvoiceEntity invoice WHERE invoice.isPaid=?1 and invoice.year=?2")
    Double totalAmountCollection(Boolean isPaid, Integer year);

    @Query("SELECT invoice FROM InvoiceEntity invoice WHERE invoice.isPaid=false AND invoice.studentEntity=:studentEntity")
    List<InvoiceEntity> findAllDueInvoicesOfStudent(StudentEntity studentEntity);

}