package com.universal.em.service;

import com.universal.em.dao.CompanyDao;
import com.universal.em.dao.EmployeeDao;
import com.universal.em.dao.EmployeeSalaryDao;
import com.universal.em.dto.request.SalarySlipReqDto;
import com.universal.em.dto.response.CompanyDetailResDto;
import com.universal.em.dto.response.EmployeeResDto;
import com.universal.em.dto.response.EmployeeSalaryResDto;
import com.universal.em.entitiy.CompanyDetail;
import com.universal.em.entitiy.Employee;
import com.universal.em.entitiy.EmployeeSalary;
import com.universal.em.exception.ServiceException;
import com.universal.em.utils.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class SalarySlipServiceImpl implements SalarySlipService {

    private final EmployeeDao employeeDao;
    private final EmployeeSalaryDao employeeSalaryDao;
    private final CompanyDao companyDao;

    @Override
    public String generate(SalarySlipReqDto salarySlipReqDto, HttpServletRequest httpServletRequest) {

        int month = Integer.parseInt(salarySlipReqDto.getMonth());
        int year = Integer.parseInt(salarySlipReqDto.getYear());
        String empId = getDecId(salarySlipReqDto.getEmpId());

//        Employee employee = employeeDao.getByCompanyEmpIdAndEnabledTrue(empId);
        Employee employee = (Employee) httpServletRequest.getSession().getAttribute("employee");
        if (employee == null) {
            log.info("Employee not found for id");
            throw new ServiceException("EM003");
        }
        EmployeeResDto employeeResDto = CommonUtils.convert(employee, EmployeeResDto.class);

//        EmployeeSalary employeeSalary = employeeSalaryDao.findByEmployeeIdAndEnabledTrue(empId);
        EmployeeSalary employeeSalary = (EmployeeSalary) httpServletRequest.getSession().getAttribute("salary");
        if (employeeSalary == null) {
            log.info("Employee Salary not found for id");
            throw new ServiceException("EM008");
        }
        EmployeeSalaryResDto employeeSalaryResDto = CommonUtils.convert(employeeSalary, EmployeeSalaryResDto.class);

        //        CompanyDetail companyDetail= companyDao.findByIdAndEnabledTrue(id);
        CompanyDetail companyDetail = (CompanyDetail) httpServletRequest.getSession().getAttribute("company");
        if (companyDetail == null) {
            log.info("Company Details not found");
            throw new ServiceException("EM014");
        }
        CompanyDetailResDto companyDetailResDto = CommonUtils.convert(companyDetail, CompanyDetailResDto.class);

        Double paidDays = Double.valueOf(salarySlipReqDto.getPaidDays());
//        Double unpaidDays = Double.valueOf(salarySlipReqDto.getUnpaidDays());

        String salarySlipPath = GenerateSlipUtils.calculation(month, year, paidDays, employeeSalaryResDto, employeeResDto, companyDetailResDto, salarySlipReqDto.getTemplate());

        return AesEncryption.encrypt(salarySlipPath);
    }

    public String getDecId(String id) {
        try {
            return AesEncryption.decrypt(id.replaceAll(" ", "+"));
        } catch (Exception e) {
            log.error("Error in get Decrypted Id ::: {}", e.getMessage(), e.getCause());
            throw new ServiceException("EM006");
        }
    }
}
