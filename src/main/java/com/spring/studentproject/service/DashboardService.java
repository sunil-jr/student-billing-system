package com.spring.studentproject.service;

import com.spring.studentproject.dto.dashboard.DashboardResponse;

import java.time.LocalDate;

public interface DashboardService {

    DashboardResponse getDashboardData(Boolean isPaid, LocalDate year);
}
