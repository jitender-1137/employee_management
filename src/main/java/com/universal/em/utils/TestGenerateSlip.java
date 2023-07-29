package com.universal.em.utils;

import com.universal.em.dto.response.CompanyDetailResDto;
import com.universal.em.dto.response.EmployeeResDto;
import com.universal.em.dto.response.EmployeeSalaryResDto;

import java.io.File;
import java.nio.file.Paths;

public class TestGenerateSlip {

    public static void main(String[] args) {

        try {
            int month = 6;
            int year = 2023;
            Double PA = 27.5; // Persence attendence
            Double TA = 30.0; // Total attendencet
            String template = "template_2";

            EmployeeSalaryResDto employeeSalaryResDto = new EmployeeSalaryResDto();
            employeeSalaryResDto.setEmpId("FC0049");
            employeeSalaryResDto.setSalary("30000");
            employeeSalaryResDto.setBasicSalary("10000");
            employeeSalaryResDto.setId(1L);
            employeeSalaryResDto.setHra("7060");
            employeeSalaryResDto.setMedical("240");
            employeeSalaryResDto.setAccountNo("9687999967");
            employeeSalaryResDto.setBankName("Icici");
            employeeSalaryResDto.setConveyanceAllowance("3100");
            employeeSalaryResDto.setPerformanceBased("7200");
            employeeSalaryResDto.setStatutoryAllowance("2640");
            employeeSalaryResDto.setUanNo("57483938209480239");
            employeeSalaryResDto.setTxnDate("21-05-2023");

            EmployeeResDto employeeResDto = new EmployeeResDto();
            employeeResDto.setCompanyEmpId("1");
            employeeResDto.setEmployeeMail("jitender@mygetepay.com");
            employeeResDto.setAddress("Jaipur");
            employeeResDto.setName("Jitender Yadav");
            employeeResDto.setDepartment("Technical");
            employeeResDto.setDesignation("Java Developer");
            employeeResDto.setPanNo("BMGPJ6848A");
            employeeResDto.setContactNo("8708809806");
            employeeResDto.setCompanyEmpId("FC0049");
            employeeResDto.setDoj("20-06-2022");

            String rootPath = Paths.get("").toAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator;
            String imageUrl = rootPath + "static" + File.separator + "image" + File.separator + "company_logo" + File.separator + "getepay.png";
            imageUrl = AesEncryption.encrypt(imageUrl);
            CompanyDetailResDto companyDetailResDto = new CompanyDetailResDto();
            companyDetailResDto.setCompanyAddress("Plot No. 60, Vishwamitra Nagar, Murlipura, Jaipur - 302039");
            companyDetailResDto.setCompanyName("Futuretek Commerce Private Limited");
            companyDetailResDto.setCompanyMail("info@getepay.in");
            companyDetailResDto.setCompanyLogo(imageUrl);

            String salarySlipPath = GenerateSlipUtils.calculation(month, year, PA, employeeSalaryResDto, employeeResDto, companyDetailResDto, template);
            System.out.println(AesEncryption.encrypt(salarySlipPath));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
