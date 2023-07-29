package com.universal.em.service;

import com.universal.em.dto.request.EmployeeSalaryReqDto;
import com.universal.em.dto.response.EmployeeSalaryResDto;
import jakarta.servlet.http.HttpServletRequest;

public interface EmployeeSalaryService {
    EmployeeSalaryResDto save(EmployeeSalaryReqDto employeeSalaryReqDto, HttpServletRequest httpServletRequest);

    EmployeeSalaryResDto update(EmployeeSalaryReqDto employeeSalaryReqDto, HttpServletRequest httpServletRequest);

    EmployeeSalaryResDto fetch(String empId, String id, HttpServletRequest httpServletRequest);

    void delete(String id, String empId, HttpServletRequest httpServletRequest);
}
