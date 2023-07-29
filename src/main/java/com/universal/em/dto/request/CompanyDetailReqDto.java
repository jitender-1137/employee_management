package com.universal.em.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CompanyDetailReqDto {

    private Long id;

    @NotBlank
    private String companyName;

    @NotBlank
    private String companyMail;

    @NotBlank
    private String companyAddress;

    private String companyLogo;

    private String contactNo;
}
