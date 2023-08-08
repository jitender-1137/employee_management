package com.universal.em.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeSalaryReqDto {

    private Long id;

    @NotBlank
    private String empId;

    private String salary;

    @NotBlank
    private String basicSalary;

    private String hra;

    private String conveyanceAllowance;

    private String performanceBased;

    private String statutoryAllowance;

    @NotBlank
    private String accountNo;

    private String uanNo;

    @NotBlank
    private String bankName;

    private String medical;

    private String txnDate;

    private String ctc;

    private String tds;

    private String pfPercent;

    @NotBlank
    private String location;
}
