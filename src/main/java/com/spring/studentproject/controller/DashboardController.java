package com.spring.studentproject.controller;

import com.spring.studentproject.dto.dashboard.DashboardResponse;
import com.spring.studentproject.service.DashboardService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/v1/mis/dashboard")

public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public DashboardResponse getDashboardData(@RequestParam(value="isPaid", required = true) Boolean isPaid,
                                              @RequestParam(value="year")LocalDate year){
        return dashboardService.getDashboardData(isPaid, year);
    }
}
