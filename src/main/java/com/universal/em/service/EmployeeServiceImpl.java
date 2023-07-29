package com.universal.em.service;

import com.universal.em.dao.EmployeeDao;
import com.universal.em.dto.request.EmployeeReqDto;
import com.universal.em.dto.response.EmployeeResDto;
import com.universal.em.entitiy.Employee;
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
public class EmployeeServiceImpl implements EmployeeService {

    protected final EmployeeDao employeeDao;

    @Override
    public EmployeeResDto saveEmployee(EmployeeReqDto employeeReqDto, HttpServletRequest httpServletRequest) {

//        if(employeeDao.existsByCompanyEmpIdAndEnabledTrue(employeeReqDto.getCompanyEmpId())){
//            log.info("Employee already exsits");
//            throw new ServiceException("EM012");
//        }

        Employee employee = CommonUtils.convert(employeeReqDto, Employee.class);
        if(employee.getPanNo() != null)
            employee.setPanNo(employee.getPanNo().toUpperCase());

//        employee = employeeDao.save(employee);
        employee.setId(1L);
        httpServletRequest.getSession().setAttribute("employee", employee);
        if (employee.getId() == null || employee.getId() <= 0) {
            log.error("Failed to save Employee");
            throw new ServiceException("EM001");
        }
        EmployeeResDto employeeResDto = CommonUtils.convert(employee, EmployeeResDto.class);
        employeeResDto.setCompanyEmpId(AesEncryption.encrypt(String.valueOf(employee.getCompanyEmpId())));
        return employeeResDto;
    }

    @Override
    public EmployeeResDto update(EmployeeReqDto employeeReqDto, HttpServletRequest httpServletRequest) {
        if (employeeReqDto.getId() == null || employeeReqDto.getId().trim().length()<=0) {
            log.info("Employee id is null for update employee");
            throw new ServiceException("EM002");
        }
        if (employeeReqDto.getCompanyEmpId() == null || employeeReqDto.getCompanyEmpId().trim().length()<=0) {
            log.info("Company Employee id is null for update employee");
            throw new ServiceException("EM002");
        }

        Employee employee = employeeDao.findByIdAndCompanyEmpIdAndEnabledTrue(Long.valueOf(employeeReqDto.getId()), getDecId(employeeReqDto.getCompanyEmpId()));
        if (employee == null) {
            log.info("Employee not found for id");
            throw new ServiceException("EM003");
        }

        employee = CommonUtils.convert(employeeReqDto, Employee.class);
        if(employee.getPanNo() != null)
            employee.setPanNo(employee.getPanNo().toUpperCase());

        employee = employeeDao.save(employee);
        if (employee.getId() == null || employee.getId() <= 0) {
            log.error("Failed to update Employee");
            throw new ServiceException("EM001");
        }
        EmployeeResDto employeeResDto = CommonUtils.convert(employee, EmployeeResDto.class);
        employeeResDto.setCompanyEmpId(AesEncryption.encrypt(String.valueOf(employee.getId())));

        return employeeResDto;
    }

    @Override
    public EmployeeResDto fetch(String empId, HttpServletRequest httpServletRequest) {

        if (empId == null || empId.trim().length() <= 0) {
            log.info("Employee id is null");
            throw new ServiceException("EM004");
        }
        Employee employee = employeeDao.getByCompanyEmpIdAndEnabledTrue(getDecId(empId));
        if (employee == null) {
            log.info("Employee not found");
            throw new ServiceException("EM005");
        }

        EmployeeResDto employeeResDto = CommonUtils.convert(employee, EmployeeResDto.class);
        employeeResDto.setCompanyEmpId(AesEncryption.encrypt(String.valueOf(employee.getId())));

        return employeeResDto;
    }

    @Override
    public void delete(String id, HttpServletRequest httpServletRequest) {
        Employee employee = employeeDao.getByIdAndEnabledTrue(Long.valueOf(id));
        if (employee == null) {
            log.info("Employee not found");
            throw new ServiceException("EM005");
        }
        employee.setEnabled(false);
        employeeDao.save(employee);
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
