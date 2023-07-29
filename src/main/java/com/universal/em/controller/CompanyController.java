package com.universal.em.controller;

import com.universal.em.dto.request.CompanyDetailReqDto;
import com.universal.em.dto.response.CompanyDetailResDto;
import com.universal.em.dto.response.ResponseDto;
import com.universal.em.dto.response.SuccessResponseDto;
import com.universal.em.service.CompanyService;
import com.universal.em.utils.AesEncryption;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/company")
@AllArgsConstructor
@Validated
@Slf4j
public class CompanyController {

    protected final CompanyService companyService;

    @PostMapping("/save")
    public ResponseDto<?> saveEmployee(@Valid @RequestBody CompanyDetailReqDto companyDetailReqDto, HttpServletRequest httpServletRequest) {

        CompanyDetailResDto companyDetailResDto = companyService.saveCompanyDetails(companyDetailReqDto, httpServletRequest);
        log.info("Company Details save successfully");
        return new SuccessResponseDto<>(companyDetailResDto, "Company Details save successfully", HttpStatus.CREATED);
    }

    @PostMapping("/uploadLogo")
    public ResponseDto<?> uploadFile(@Valid @RequestParam("file") MultipartFile attachment,
                                     @NotNull HttpServletRequest request) {
        String path = companyService.uploadFile(attachment, request);

        log.info("File uploaded Successfully");
        return new SuccessResponseDto<>(path, "File Uploaded Successfully", HttpStatus.OK);
    }

    @PostMapping("/imageToBase64")
    public ResponseDto<?> imageToBase64(@Valid @RequestParam("path") String path,
                                        @NotNull HttpServletRequest request) throws IOException {
        Map<String, String> data = new HashMap<>();

        path = AesEncryption.decrypt(path.replaceAll(" ","+"));
        byte[] fileContent = FileUtils.readFileToByteArray(new File(path));
        data.put("image", Base64.getEncoder().encodeToString(fileContent));

        log.info("Image to Base64 Successfully");
        return new SuccessResponseDto<>(data, "Image to Base64 Successfully", HttpStatus.OK);
    }

}
