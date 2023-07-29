package com.universal.em.service;

import com.universal.em.dto.request.CompanyDetailReqDto;
import com.universal.em.dto.response.CompanyDetailResDto;
import com.universal.em.dto.response.EmployeeResDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

public interface CompanyService {
    CompanyDetailResDto saveCompanyDetails(CompanyDetailReqDto companyDetailReqDto, HttpServletRequest httpServletRequest);

    String uploadFile(MultipartFile attachment, HttpServletRequest request);
}
