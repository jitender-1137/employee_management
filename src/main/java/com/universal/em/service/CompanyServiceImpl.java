package com.universal.em.service;

import com.universal.em.dao.CompanyDao;
import com.universal.em.dto.request.CompanyDetailReqDto;
import com.universal.em.dto.response.CompanyDetailResDto;
import com.universal.em.dto.response.EmployeeResDto;
import com.universal.em.entitiy.CompanyDetail;
import com.universal.em.entitiy.Employee;
import com.universal.em.exception.ServiceException;
import com.universal.em.utils.AesEncryption;
import com.universal.em.utils.CommonUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@AllArgsConstructor
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    protected final CompanyDao companyDao;

    @Override
    public CompanyDetailResDto saveCompanyDetails(CompanyDetailReqDto companyDetailReqDto, HttpServletRequest httpServletRequest) {

        CompanyDetail companyDetail = CommonUtils.convert(companyDetailReqDto, CompanyDetail.class);

        companyDetail.setId(1L);
        httpServletRequest.getSession().setAttribute("company", companyDetail);
        if (companyDetail.getId() == null || companyDetail.getId() <= 0) {
            log.error("Failed to save Company Details");
            throw new ServiceException("EM001");
        }
        CompanyDetailResDto companyDetailResDto = CommonUtils.convert(companyDetail, CompanyDetailResDto.class);
//        companyDetailResDto.setCompanyEmpId(AesEncryption.encrypt(String.valueOf(employee.getCompanyEmpId())));
        return companyDetailResDto;
    }

    @Override
    public String uploadFile(MultipartFile attachment, HttpServletRequest request) {
        File file = null;
        String finalPath = null;
        if (!attachment.isEmpty()) {
            if (attachment.getSize() / 1024 > 1024) {
                log.info("File size can not be more then 1MB");
                throw new ServiceException("CS62");
            }
            Path currentRelativePath = Paths.get("");
            String rootPath = currentRelativePath.toAbsolutePath().toString();
            String path = rootPath + File.separator + "src" + File.separator + "main" + File.separator + "resources"
                    + File.separator + "static" + File.separator + "image" + File.separator + "company_logo" + File.separator;
            file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            try {
                finalPath = path + attachment.getOriginalFilename();
                if (!new File(finalPath).exists()) {
                    new File(finalPath).createNewFile();
                }
                byte[] bytes = attachment.getBytes();
                Path newPath = Paths.get(finalPath);
                Path path2 = Files.write(newPath, bytes);
                return AesEncryption.encrypt(path2.toString());
            } catch (Exception e) {
                e.printStackTrace();
                log.info("Failed to save Attchment file ==>> " + attachment.getOriginalFilename());
                file = null;
                path = null;
                return "Failed to upload file";
            }
        }
        return finalPath;
    }
}
