package com.universal.em.controller;

import com.universal.em.dto.request.SalarySlipReqDto;
import com.universal.em.dto.response.ResponseDto;
import com.universal.em.dto.response.SuccessResponseDto;
import com.universal.em.exception.ServiceException;
import com.universal.em.service.SalarySlipService;
import com.universal.em.utils.AesEncryption;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/v1/salarySlip")
@AllArgsConstructor
@Validated
@Slf4j
public class SalarySlipController {

    private final SalarySlipService salarySlipService;

    @PostMapping("/generate")
    public ResponseDto<?> generate(@Valid @RequestBody SalarySlipReqDto salarySlipReqDto, HttpServletRequest request) throws Exception {

        String encryptedPath = salarySlipService.generate(salarySlipReqDto, request);
        String downloadUrl = request.getScheme() + "://" + request.getServerName() + ":"
                + request.getServerPort() + "/api/v1/salarySlip/download?path=" + encryptedPath;

        log.info("Salary slip generate successfully");
        return new SuccessResponseDto<>(downloadUrl, "Salary slip generate successfully", HttpStatus.CREATED);
    }

    @GetMapping("/download")
//	@PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseDto<?> downloadLoggerFile(@RequestParam("path") String path, HttpServletResponse response,
                                             HttpServletRequest request) throws IOException {

        if (path == null || path.length() < 1) {
            log.info("Salary slip request path is null");
            throw new ServiceException("CS78");
        }
        try {
            path = AesEncryption.decrypt(path.replaceAll(" ", "+"));

        } catch (Exception e) {
            log.info("Error in Decrypt Download Logger file path :: " + e.getMessage());
            throw new ServiceException("CS79");
        }

        Path path1 = Paths.get(path);
        Path fileName = path1.getFileName();
        byte[] videoContent = null;
        try {
            videoContent = Files.readAllBytes(path1);
            response.setHeader("Cache-Control", "no-store");
            response.setDateHeader("Expires", 0);
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);

            String mimeType = URLConnection.guessContentTypeFromName(path);
            if (mimeType == null) {
                log.info("mimetype is not detectable, will take default");
                mimeType = "application/octet-stream";
            }
            response.setContentType(mimeType);
            ServletOutputStream responseOutputStream = response.getOutputStream();
            responseOutputStream.write(videoContent);
            responseOutputStream.flush();
            responseOutputStream.close();

            log.info("Salary slip download successfully");

        } catch (IOException e) {
            String errorMessage = "Sorry!! The file you are looking for does not exist or it may be system IO Problem.";
            log.info(errorMessage + " :: " + e.getMessage());
            OutputStream outputStream;
            try {
                outputStream = response.getOutputStream();
                outputStream.write(errorMessage.getBytes(StandardCharsets.UTF_8));
                outputStream.close();
            } catch (IOException e1) {
                log.info("Error in Downloading Salary slip :: " + e1.getMessage());
                throw new ServiceException("CS79");
            }
        }
        File file = null;
        file = new File(path);
        file.delete();
        return new SuccessResponseDto<>("Salary slip download successfully");
    }
}
