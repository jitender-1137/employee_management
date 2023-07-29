package com.universal.em.service;

import com.universal.em.dto.request.EmployeeReqDto;
import com.universal.em.dto.response.EmployeeResDto;
import jakarta.servlet.http.HttpServletRequest;

public interface EmployeeService {
    EmployeeResDto saveEmployee(EmployeeReqDto employeeReqDto, HttpServletRequest httpServletRequest);

    EmployeeResDto update(EmployeeReqDto employeeReqDto, HttpServletRequest httpServletRequest);

    EmployeeResDto fetch(String empId, HttpServletRequest httpServletRequest);

    void delete(String id, HttpServletRequest httpServletRequest);
}
