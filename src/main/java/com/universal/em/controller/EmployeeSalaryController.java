package com.universal.em.controller;

import com.universal.em.dto.request.EmployeeSalaryReqDto;
import com.universal.em.dto.response.EmployeeSalaryResDto;
import com.universal.em.dto.response.ResponseDto;
import com.universal.em.dto.response.SuccessResponseDto;
import com.universal.em.exception.ServiceException;
import com.universal.em.service.EmployeeSalaryService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/v1/employeeSalary")
@AllArgsConstructor
@Validated
@Slf4j
public class EmployeeSalaryController {

    protected final EmployeeSalaryService employeeSalaryService;

    @PostMapping("/save")
    public ResponseDto<?> saveEmployee(@Valid @RequestBody EmployeeSalaryReqDto employeeSalaryReqDto, HttpServletRequest httpServletRequest) {

        EmployeeSalaryResDto employeeSalaryResDto = employeeSalaryService.save(employeeSalaryReqDto, httpServletRequest);
        log.info("Employee Salary save successfully");
        return new SuccessResponseDto<>(employeeSalaryResDto, "Employee Salary save successfully", HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseDto<?> update(@Valid @RequestBody EmployeeSalaryReqDto employeeSalaryReqDto,
                                 @NotNull HttpServletRequest httpServletRequest) {

        EmployeeSalaryResDto employeeSalaryResDto = employeeSalaryService.update(employeeSalaryReqDto, httpServletRequest);
        log.info("Employee Salary update Successfully");
        return new SuccessResponseDto<>(employeeSalaryResDto, "Employee Salary update Successfully", HttpStatus.OK);
    }

    @GetMapping("/fetch")
    public ResponseDto<?> fetch(@RequestParam("empId") String empId, @RequestParam("id") String id, @NotNull HttpServletRequest httpServletRequest) {

        EmployeeSalaryResDto employeeSalaryResDto = employeeSalaryService.fetch(empId, id, httpServletRequest);
        log.info("Employee Salary fetch Successfully");
        return new SuccessResponseDto<>(employeeSalaryResDto, "Employee Salary fetch Successfully",
                HttpStatus.OK);
    }

    @DeleteMapping("{empId}/delete/{id}")
    public ResponseDto<?> deleteMail(@PathVariable("id") String id, @PathVariable("empId") String empId, @NotNull HttpServletRequest httpServletRequest) {

        employeeSalaryService.delete(id, empId, httpServletRequest);
        log.info("Employee Salary delete successfully");
        return new SuccessResponseDto<>("Employee Salary delete successfully", Long.valueOf(id));
    }

}
