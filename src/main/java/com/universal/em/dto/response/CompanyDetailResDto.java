package com.universal.em.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CompanyDetailResDto {

    private Long id;

    private String companyName;

    private String companyMail;

    private String companyAddress;

    private String companyLogo;

    private String contactNo;
}
