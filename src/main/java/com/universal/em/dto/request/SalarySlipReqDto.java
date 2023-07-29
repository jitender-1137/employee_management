package com.universal.em.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SalarySlipReqDto {

    @NotBlank
    private String empId;

    @NotBlank
    private String month;

    @NotBlank
    private String year;

    private String paidDays;

    private String unpaidDays;

    @NotBlank
    private String template;

}
