package com.universal.em.service;

import com.universal.em.dao.EmployeeDao;
import com.universal.em.dao.EmployeeSalaryDao;
import com.universal.em.dto.request.EmployeeSalaryReqDto;
import com.universal.em.dto.response.EmployeeSalaryResDto;
import com.universal.em.entitiy.EmployeeSalary;
import com.universal.em.exception.ServiceException;
import com.universal.em.utils.AesEncryption;
import com.universal.em.utils.CommonUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeSalaryServiceImpl implements EmployeeSalaryService {

    protected final EmployeeDao employeeDao;

    protected final EmployeeSalaryDao employeeSalaryDao;
    @Override
    public EmployeeSalaryResDto save(EmployeeSalaryReqDto employeeSalaryReqDto, HttpServletRequest httpServletRequest) {
        String id = getDecId(employeeSalaryReqDto.getEmpId());

//        if (employeeSalaryDao.existsByEmployeeIdAndEnabledTrue(id)) {
//            log.info("Employee Salary already exists for the employee");
//            throw new ServiceException("EM013");
//        }
//        if (!employeeDao.existsByCompanyEmpIdAndEnabledTrue(id)) {
//            log.info("Employee not found for id");
//            throw new ServiceException("EM005");
//        }

        EmployeeSalary employeeSalary = CommonUtils.convert(employeeSalaryReqDto, EmployeeSalary.class);
        employeeSalary.setEmployeeId(id);

//        employeeSalary = employeeSalaryDao.save(employeeSalary);
        employeeSalary.setId(1L);
        httpServletRequest.getSession().setAttribute("salary", employeeSalary);
        if (employeeSalary.getId() == null || employeeSalary.getId() <= 0) {
            log.error("Failed to save Employee Salary");
            throw new ServiceException("EM011");
        }
        EmployeeSalaryResDto employeeSalaryResDto = CommonUtils.convert(employeeSalary, EmployeeSalaryResDto.class);
        employeeSalaryResDto.setEmpId(AesEncryption.encrypt(String.valueOf(employeeSalary.getEmployeeId())));
        return employeeSalaryResDto;
    }

    @Override
    public EmployeeSalaryResDto update(EmployeeSalaryReqDto employeeSalaryReqDto, HttpServletRequest httpServletRequest) {
        if (employeeSalaryReqDto.getId() == null || employeeSalaryReqDto.getId() <= 0) {
            log.info("Employee Salary id is null for update employee");
            throw new ServiceException("EM007");
        }
        if (employeeSalaryReqDto.getEmpId() == null || employeeSalaryReqDto.getEmpId().trim().length() <= 0) {
            log.info("Employee id is null for update employee");
            throw new ServiceException("EM002");
        }
        if (!employeeSalaryDao.existsByIdAndEmployeeIdAndEnabledTrue(Long.valueOf(employeeSalaryReqDto.getId()), getDecId(employeeSalaryReqDto.getEmpId()))) {
            log.info("Employee Salary not found for id");
            throw new ServiceException("EM008");
        }

        EmployeeSalary employeeSalary = CommonUtils.convert(employeeSalaryReqDto, EmployeeSalary.class);

        employeeSalary = employeeSalaryDao.save(employeeSalary);
        if (employeeSalary.getId() == null || employeeSalary.getId() <= 0) {
            log.error("Failed to update Employee Salary");
            throw new ServiceException("EM009");
        }
        EmployeeSalaryResDto employeeSalaryResDto = CommonUtils.convert(employeeSalary, EmployeeSalaryResDto.class);
        employeeSalaryResDto.setEmpId(AesEncryption.encrypt(String.valueOf(employeeSalary.getEmployeeId())));

        return employeeSalaryResDto;
    }

    @Override
    public EmployeeSalaryResDto fetch(String empId, String id, HttpServletRequest httpServletRequest) {
        if (id == null || id.trim().length() <= 0) {
            log.info("Employee Salary id is null");
            throw new ServiceException("EM010");
        }

        if (empId == null || empId.trim().length() <= 0) {
            log.info("Employee id is null");
            throw new ServiceException("EM004");
        }

        EmployeeSalary employeeSalary = employeeSalaryDao.getByIdAndEmployeeIdAndEnabledTrue(Long.valueOf(id), getDecId(empId));
        if (employeeSalary == null) {
            log.info("Employee Salary not found");
            throw new ServiceException("EM008");
        }

        EmployeeSalaryResDto employeeSalaryResDto = CommonUtils.convert(employeeSalary, EmployeeSalaryResDto.class);
        employeeSalaryResDto.setEmpId(AesEncryption.encrypt(String.valueOf(employeeSalary.getEmployeeId())));

        return employeeSalaryResDto;
    }

    @Override
    public void delete(String id, String empId, HttpServletRequest httpServletRequest) {
        EmployeeSalary employeeSalary = employeeSalaryDao.getByIdAndEmployeeIdAndEnabledTrue(Long.valueOf(id), getDecId(empId));
        if (employeeSalary == null) {
            log.info("Employee Salary not found");
            throw new ServiceException("EM008");
        }
        employeeSalary.setEnabled(false);
        employeeSalaryDao.save(employeeSalary);
    }

    public String getDecId(String id){
        try{
            return AesEncryption.decrypt(id.replaceAll(" ", "+"));
        }catch (Exception e){
            log.error("Error in get Decrypted Id ::: {}", e.getMessage(), e.getCause());
            throw new ServiceException("EM006");
        }
    }
}
