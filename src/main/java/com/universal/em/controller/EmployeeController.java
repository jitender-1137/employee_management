package com.universal.em.controller;

import com.universal.em.dto.request.EmployeeReqDto;
import com.universal.em.dto.response.EmployeeResDto;
import com.universal.em.dto.response.ResponseDto;
import com.universal.em.dto.response.SuccessResponseDto;
import com.universal.em.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee")
@AllArgsConstructor
@Validated
@Slf4j
public class EmployeeController {

    protected final EmployeeService employeeService;

    @PostMapping("/save")
    public ResponseDto<?> saveEmployee(@Valid @RequestBody EmployeeReqDto employeeReqDto, HttpServletRequest httpServletRequest) {
        log.info("");
        EmployeeResDto employeeResDto = employeeService.saveEmployee(employeeReqDto, httpServletRequest);
        return new SuccessResponseDto<>(employeeResDto, "Employee save successfully", HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseDto<?> update(@Valid @RequestBody EmployeeReqDto employeeReqDto,
                                 @NotNull HttpServletRequest httpServletRequest) {

        EmployeeResDto employeeResDto = employeeService.update(employeeReqDto, httpServletRequest);

        log.info("Employee update Successfully");
        return new SuccessResponseDto<>(employeeResDto, "Employee update Successfully", HttpStatus.OK);
    }

    @GetMapping("/fetch")
    public ResponseDto<?> fetch(@RequestParam("empId") String empId, @NotNull HttpServletRequest httpServletRequest) {

        EmployeeResDto employeeResDto = employeeService.fetch(empId, httpServletRequest);

        log.info("Employee fetch Successfully");
        return new SuccessResponseDto<>(employeeResDto, "Employee fetch Successfully",
                HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto<?> deleteMail(@PathVariable("id") String id, @NotNull HttpServletRequest httpServletRequest) {

        employeeService.delete(id, httpServletRequest);

        log.info("Employee delete successfully");
        return new SuccessResponseDto<>("Employee delete successfully");
    }

}
