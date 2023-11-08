package com.sky.service;

import com.sky.vo.TurnoverReportVO;

import java.time.LocalDate;

/**
 * @Auther: szw
 * @Date: 2023/11/3
 * @Description: com.sky.service
 * @version: 1.0
 */
public interface ReportService {
    TurnoverReportVO getTurnover(LocalDate begin, LocalDate end);
}
