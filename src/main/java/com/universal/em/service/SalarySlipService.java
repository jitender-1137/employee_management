package com.universal.em.service;

import com.universal.em.dto.request.SalarySlipReqDto;
import jakarta.servlet.http.HttpServletRequest;

public interface SalarySlipService {
    String generate(SalarySlipReqDto salarySlipReqDto, HttpServletRequest request) throws Exception;
}
