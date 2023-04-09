package com.spring.studentproject.service.impl;

import com.spring.studentproject.constant.DiscountType;
import com.spring.studentproject.dto.invoice.InvoiceRequest;
import com.spring.studentproject.dto.invoice.InvoiceResponse;
import com.spring.studentproject.model.InvoiceEntity;
import com.spring.studentproject.model.StudentEntity;
import com.spring.studentproject.repository.InvoiceRepository;
import com.spring.studentproject.repository.StudentRepository;
import com.spring.studentproject.service.InvoiceService;
import com.spring.studentproject.service.StudentService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private final StudentRepository studentRepository;
    private final StudentService studentService;
    private final InvoiceRepository invoiceRepository;
    private final EntityManager entityManager;
    private final UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter;
    private final StudentValidator studentValidator;

    @Autowired
    public InvoiceServiceImpl(StudentRepository studentRepository, StudentService studentService, InvoiceRepository invoiceRepository, EntityManager entityManager, UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter, StudentValidator studentValidator) {
        this.studentRepository = studentRepository;
        this.studentService = studentService;
        this.invoiceRepository = invoiceRepository;
        this.entityManager = entityManager;
        this.usernamePasswordAuthenticationFilter = usernamePasswordAuthenticationFilter;
        this.studentValidator = studentValidator;
    }

    @Override
    public InvoiceResponse addInvoice(InvoiceRequest invoiceRequest, Long studentId) {
        StudentEntity studentEntity = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student Id not valid!!"));
        InvoiceEntity save = invoiceRepository.save(prepareToInvoiceEntity(invoiceRequest, studentEntity));
        return prepareToInvoiceResponse(save);
    }

    @Override
    public InvoiceResponse updateInvoice(Long invoiceId, InvoiceRequest invoiceRequest) {
        InvoiceEntity invoiceEntity = invoiceRepository.findById(invoiceId).orElseThrow(() -> new RuntimeException("Id not valid"));
        invoiceEntity = invoiceRepository.save(prepareToUpdateInvoice(invoiceEntity, invoiceRequest));
        return prepareToInvoiceResponse(invoiceEntity);
    }

    @Override
    public List<InvoiceResponse> getAllDueInvoices() {
        return prepareToInvoiceResponse(invoiceRepository.findAllDueInvoices());
    }

    @Override
    public List<InvoiceResponse> getAllDueInvoicesForStudent() {
        StudentEntity studentEntity = studentValidator.validateByUserName(usernamePasswordAuthenticationFilter.getUsernameParameter());
        return prepareToInvoiceResponse(invoiceRepository.findAllDueInvoicesOfStudent(studentEntity));
    }

    @Override
    public List<InvoiceResponse> getAllPaidInvoices() {
        return prepareToInvoiceResponse(invoiceRepository.findAllPaidInvoices());
    }


    @Override
    public List<InvoiceResponse> getAllInvoices() {
        return prepareToInvoiceResponse(invoiceRepository.findAll());
    }


    @Override
    public List<InvoiceResponse> getInvoiceByStudentId(Long studentId) {
        List<InvoiceEntity> invoiceEntityList = invoiceRepository.findInvoiceEntityByStudentId(studentId);
        if (invoiceEntityList.isEmpty()) return null;
        return prepareToInvoiceResponse(invoiceEntityList);
    }

    @Override
    public List<InvoiceResponse> filterInvoice(Integer year, Integer month, Long studentId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<InvoiceEntity> cq = cb.createQuery(InvoiceEntity.class);
        Root<InvoiceEntity> invoice = cq.from(InvoiceEntity.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(invoice.get("year"), year));
        if (month != null) predicates.add(cb.equal(invoice.get("dueMonth"), month));
        if (studentId != null) predicates.add(cb.equal(invoice.join("studentEntity").get("id"), studentId));

        cq.where(predicates.toArray(new Predicate[0]));

        return prepareToInvoiceResponse(entityManager.createQuery(cq).getResultList());
    }

    private Double calculateDiscount(DiscountType discountType, Double rate, Double amount) {
        if (discountType == DiscountType.FLAT) return rate;
        else return amount * (rate / 100);
    }


    private Double payPartialBill(Double amount, Double paidAmount) {
        return amount - paidAmount;

    }

    public InvoiceResponse setCriticalDues(InvoiceEntity invoiceEntity) {
        LocalDate dueDate = invoiceEntity.getDueDate();
        if (Period.between(dueDate, LocalDate.now()).getMonths() >= 3) {
            invoiceEntity.setCritical(true);
            return prepareToInvoiceResponse(invoiceRepository.save(invoiceEntity));
        }
        return null;
    }

    public List<InvoiceResponse> getCriticalDues() {
        List<InvoiceResponse> invoiceResponseList = new ArrayList<>();
        List<InvoiceEntity> invoiceEntityList = invoiceRepository.findCriticalDues();
        for (InvoiceEntity invoiceEntity : invoiceEntityList) {
            invoiceEntityList.add(invoiceEntity);
        }
        return invoiceResponseList;
    }

    private InvoiceEntity prepareToInvoiceEntity(InvoiceRequest invoiceRequest, StudentEntity studentEntity) {
        InvoiceEntity invoiceEntity = new InvoiceEntity();
        invoiceEntity.setYear(invoiceRequest.getYear());
        invoiceEntity.setRate(invoiceRequest.getRate());
        invoiceEntity.setDueMonth(invoiceRequest.getDueMonth());
        invoiceEntity.setAmount(invoiceRequest.getAmount());
        invoiceEntity.setDiscountType(invoiceRequest.getDiscountType());
        invoiceEntity.setStudentEntity(studentEntity);
        invoiceEntity.setRemainingAmount(payPartialBill(invoiceRequest.getAmount(), invoiceRequest.getPaidAmount()));
        invoiceEntity.setDiscountAmount(calculateDiscount(invoiceRequest.getDiscountType(), invoiceRequest.getRate(), invoiceRequest.getAmount()));
        return invoiceEntity;
    }

    private InvoiceEntity prepareToUpdateInvoice(InvoiceEntity invoiceEntity, InvoiceRequest invoiceRequest) {
        if (invoiceRequest.getDueMonth() != null) {
            invoiceEntity.setDueMonth(invoiceRequest.getDueMonth());
        }
        if (invoiceRequest.getYear() != null) {
            invoiceEntity.setYear(invoiceRequest.getYear());
        }
        if (invoiceRequest.getStudentId() != null) {
            StudentEntity studentEntity = studentRepository.findById(invoiceRequest.getStudentId()).orElseThrow(() -> new RuntimeException("Id not valid"));
            invoiceEntity.setStudentEntity(studentEntity);
        }
        return invoiceEntity;
    }

    private List<InvoiceResponse> prepareToInvoiceResponse(List<InvoiceEntity> invoiceEntities) {
        return invoiceEntities.stream().map(this::prepareToInvoiceResponse).collect(Collectors.toList());
    }

    private InvoiceResponse prepareToInvoiceResponse(InvoiceEntity invoiceEntity) {
        if (invoiceEntity != null) {
            return InvoiceResponse.builder()
                    .invoiceId(invoiceEntity.getInvoiceId())
                    .amount(invoiceEntity.getAmount())
                    .dueDate(invoiceEntity.getDueDate())
                    .discountType(invoiceEntity.getDiscountType())
                    .isPaid(invoiceEntity.getIsPaid())
                    .dueMonth(invoiceEntity.getDueMonth())
                    .year(invoiceEntity.getYear())
                    .discountAmount(calculateDiscount(invoiceEntity.getDiscountType(), invoiceEntity.getRate(), invoiceEntity.getAmount()))
                    .studentResponse(studentService.prepareToStudentResponse(invoiceEntity.getStudentEntity()))
                    .critical(invoiceEntity.getCritical())
                    .remainingAmount(invoiceEntity.getRemainingAmount() - invoiceEntity.getDiscountAmount())
                    .build();

        }
        return InvoiceResponse.builder().build();
    }
}
