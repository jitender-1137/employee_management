package com.universal.em.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeReqDto {

    private String id;

    @NotBlank
    private String name;

    private String employeeMail;

    private String contactNo;

    @NotBlank
    private String designation;

    @NotBlank
    private String companyEmpId;

    @NotBlank
    private String department;

    private String address;

    @NotBlank
    private String panNo;

    @NotBlank
    private String doj;

}
