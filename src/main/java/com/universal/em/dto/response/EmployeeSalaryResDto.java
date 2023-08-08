package com.universal.em.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeSalaryResDto {

    private Long id;

    private String empId;

    private String salary;

    private String basicSalary;

    private String hra;

    private String conveyanceAllowance;

    private String performanceBased;

    private String statutoryAllowance;

    private String accountNo;

    private String uanNo;

    private String bankName;

    private String medical;

    private String txnDate;

    private String tds;

    private String pfPercent;

    private String location;

}
