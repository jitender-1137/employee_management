package com.universal.em.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeResDto {

    private Long id;

    private String name;

    private String employeeMail;

    private String contactNo;

    private String designation;

    private String companyEmpId;

    private String department;

    private String address;

    private String panNo;

    private String doj;
}
