package com.universal.em.utils;

import com.universal.em.dto.response.CompanyDetailResDto;
import com.universal.em.dto.response.EmployeeResDto;
import com.universal.em.dto.response.EmployeeSalaryResDto;
import com.universal.em.dto.response.SalarySlipCalculationDto;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.util.Calendar;

public class GenerateSlipUtils {

    private static final DecimalFormat decfor = new DecimalFormat("#");
    private static final DecimalFormat df = new DecimalFormat("#.000");
    private static final DecimalFormat decforTwo = new DecimalFormat("#.00");

    public static String template(SalarySlipCalculationDto slipCalculationDto, String template) {
        String salarySlipPath = "";
        try {
            if (template.contains("template_1"))
                salarySlipPath = PaySlipTemplate1.generate(slipCalculationDto);
            else if (template.contains("template_2"))
                salarySlipPath = PaySlipTemplate2.generate(slipCalculationDto);
            else if (template.contains("template_3"))
                salarySlipPath = PaySlipTemplate3.generate(slipCalculationDto);
            else
                salarySlipPath = PaySlipTemplate1.generate(slipCalculationDto);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return salarySlipPath;
    }

    public static String calculation(int month, int year, Double paidDays, EmployeeSalaryResDto employeeSalaryResDto, EmployeeResDto employeeResDto, CompanyDetailResDto companyDetailResDto, String template) {

        SalarySlipCalculationDto slipCalculationDto = new SalarySlipCalculationDto();

        Double basic = Double.valueOf(employeeSalaryResDto.getBasicSalary());
        Double hra = Double.valueOf(employeeSalaryResDto.getHra());
        Double performanceBased = Double.valueOf(employeeSalaryResDto.getPerformanceBased());
        Double convenyenceAllowance = Double.valueOf(employeeSalaryResDto.getConveyanceAllowance());
        Double statutoryAllowance = Double.valueOf(employeeSalaryResDto.getStatutoryAllowance());
        Double doublMedical = Double.valueOf(employeeSalaryResDto.getMedical());
        Double pfPercent = Double.valueOf(employeeSalaryResDto.getPfPercent());
        String logo = "";
        try {
            logo = AesEncryption.decrypt(companyDetailResDto.getCompanyLogo());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String companyName = companyDetailResDto.getCompanyName();
        String companyMail = companyDetailResDto.getCompanyMail();
        String companyAddress = companyDetailResDto.getCompanyAddress();

        String tdsString = employeeSalaryResDto.getTds();
        Double tds = Double.valueOf(tdsString == null || tdsString.trim().equals("") ? "0" : tdsString);

        String medical = String.format("%,.2f", doublMedical);

        String employeeId = employeeResDto.getCompanyEmpId();
        String doj = employeeResDto.getDoj();
        String employeeName = employeeResDto.getName();
        String pan = employeeResDto.getPanNo();
        String designation = employeeResDto.getDesignation();
        String uanNo = employeeSalaryResDto.getUanNo();
        String department = employeeResDto.getDepartment();
        String bankAccount = employeeSalaryResDto.getAccountNo();
        String bankName = employeeSalaryResDto.getBankName();
        String location = employeeSalaryResDto.getLocation();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);

        String monthInString = new DateFormatSymbols().getMonths()[month - 1];

        Double monthDays = Math.floor((Double.valueOf(calendar.getActualMaximum(Calendar.DAY_OF_MONTH))));

        Double unpaidDays = monthDays - paidDays;

        // Salary Calculation START
        Double doubleTotalSalary = Double.valueOf(decfor.format(basic + hra + performanceBased + convenyenceAllowance + statutoryAllowance));
        String totalSalary = String.format("%,.2f", doubleTotalSalary); // Total Earnings

        Double persence = Double.valueOf(df.format(paidDays * 100 / monthDays));

        Double totalPF = basic * pfPercent / 100;
        Double doublePfPay = Double.parseDouble(decfor.format(totalPF * persence / 100));
        String pfPay = String.format("%,.2f", doublePfPay);
        double doubleBasicPay = Double.parseDouble(decforTwo.format(basic * persence / 100));
        String basicPay = String.format("%,.2f", doubleBasicPay);
        double doubleHraPay = Double.parseDouble(decforTwo.format(hra * persence / 100));
        String hraPay = String.format("%,.2f", doubleHraPay);
        double doublePerformancePay = Double.parseDouble(decforTwo.format(performanceBased * persence / 100));
        String performancePay = String.format("%,.2f", doublePerformancePay);
        double doubleConvenyencePay = Double.parseDouble(decforTwo.format(convenyenceAllowance * persence / 100));
        String convenyencePay = String.format("%,.2f", doubleConvenyencePay);
        double doubleStatutoryPay = Double.parseDouble(decforTwo.format(statutoryAllowance * persence / 100));
        String statutoryPay = String.format("%,.2f", doubleStatutoryPay);
        double doubleTdsPay = Double.parseDouble(decforTwo.format(tds));
        String tdsPay = String.format("%,.2f", doubleTdsPay);

        Double doubleToaltEarnings = doubleBasicPay + doubleConvenyencePay + doubleHraPay + doublePerformancePay + doubleStatutoryPay;
        String toaltEarnings = String.format("%,.2f", doubleToaltEarnings);

        Double payWithoutPF = doubleTotalSalary * persence / 100;
        Double doubleLeaveDeduction = Double.valueOf(decfor.format(doubleTotalSalary * (unpaidDays * 100 / monthDays) / 100));
        String leaveDeduction = String.format("%,.2f", doubleLeaveDeduction);
        Double doubleNetPay = Double.valueOf(decfor.format(payWithoutPF - doublePfPay - doublMedical));
        String netPay = String.format("%,.2f", doubleNetPay);
        Double doubleNetDeduction = Double.valueOf(decfor.format(doublePfPay + doublMedical + doubleLeaveDeduction + doubleTdsPay));
        String netDeduction = String.format("%,.2f", doubleNetDeduction);

        // Salary Calculation END
        slipCalculationDto.setBankAccount(bankAccount);
        slipCalculationDto.setBasicPay(basicPay);
        slipCalculationDto.setBankName(bankName);
        slipCalculationDto.setDoj(doj);
        slipCalculationDto.setCompanyName(companyName);
        slipCalculationDto.setCompanyMail(companyMail);
        slipCalculationDto.setCompanyAddress(companyAddress);
        slipCalculationDto.setDepartment(department);
        slipCalculationDto.setDesignation(designation);
        slipCalculationDto.setUanNo(uanNo);
        slipCalculationDto.setTotalSalary(totalSalary);
        slipCalculationDto.setToaltEarnings(toaltEarnings);
        slipCalculationDto.setTdsPay(tdsPay);
        slipCalculationDto.setStatutoryPay(statutoryPay);
        slipCalculationDto.setPfPay(pfPay);
        slipCalculationDto.setPerformancePay(performancePay);
        slipCalculationDto.setPan(pan);
        slipCalculationDto.setNetPay(netPay);
        slipCalculationDto.setNetDeduction(netDeduction);
        slipCalculationDto.setMonthInString(monthInString);
        slipCalculationDto.setMedical(medical);
        slipCalculationDto.setLogo(logo);
        slipCalculationDto.setLeaveDeduction(leaveDeduction);
        slipCalculationDto.setHraPay(hraPay);
        slipCalculationDto.setEmployeeName(employeeName);
        slipCalculationDto.setEmployeeId(employeeId);
        slipCalculationDto.setConvenyencePay(convenyencePay);
        slipCalculationDto.setYear(String.valueOf(year));
        slipCalculationDto.setTotalDays(String.valueOf(monthDays));
        slipCalculationDto.setPaidDays(String.valueOf(paidDays));
        slipCalculationDto.setUnpaidDays(String.valueOf(unpaidDays));
        slipCalculationDto.setLocation(location);

        String salarySlipPath = "";
        try {
            salarySlipPath = template(slipCalculationDto, template);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salarySlipPath;
    }
}
