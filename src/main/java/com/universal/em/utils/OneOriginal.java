package com.universal.em.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.util.Calendar;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.universal.em.dto.response.EmployeeResDto;
import com.universal.em.dto.response.EmployeeSalaryResDto;

public class OneOriginal {

    private static final DecimalFormat decfor = new DecimalFormat("#");
    private static final DecimalFormat df = new DecimalFormat("#.000");
    private static final DecimalFormat decforTwo = new DecimalFormat("#.00");

    public static void main(String[] args) {

        try {
            int month = 6;
            int year = 2023;
            Double PA = 27.5; // Persence attendence
            Double TA = 30.0; // Total attendence

            EmployeeSalaryResDto employeeSalaryResDto = new EmployeeSalaryResDto();
            employeeSalaryResDto.setEmpId("FC0049");
            employeeSalaryResDto.setSalary("30000");
            employeeSalaryResDto.setBasicSalary("10000");
            employeeSalaryResDto.setId(1L);
            employeeSalaryResDto.setHra("7060");
            employeeSalaryResDto.setMedical("240");
            employeeSalaryResDto.setAccountNo("9687999967");
            employeeSalaryResDto.setBankName("icici");
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


            String salarySlipPath = generate(month, year, PA, TA - PA, employeeSalaryResDto, employeeResDto);
            System.out.println(AesEncryption.encrypt(salarySlipPath));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("catch");
        }
    }

    public static String generate(int month, int year, Double paidDays, Double unpaidDays, EmployeeSalaryResDto employeeSalaryResDto, EmployeeResDto employeeResDto) throws Exception {

        String rootPath = Paths.get("").toAbsolutePath().toString() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator;

        Double basic = Double.valueOf(employeeSalaryResDto.getBasicSalary());
        Double hra = Double.valueOf(employeeSalaryResDto.getHra());
        Double performanceBased = Double.valueOf(employeeSalaryResDto.getPerformanceBased());
        Double convenyenceAllowance = Double.valueOf(employeeSalaryResDto.getConveyanceAllowance());
        Double statutoryAllowance = Double.valueOf(employeeSalaryResDto.getStatutoryAllowance());
        Double doublMedical = Double.valueOf(employeeSalaryResDto.getMedical());

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

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);

        String monthInString = new DateFormatSymbols().getMonths()[month - 1];

        Double monthDays = Math.floor((Double.valueOf(calendar.getActualMaximum(Calendar.DAY_OF_MONTH))));

        unpaidDays = monthDays - paidDays;

        // Salary Calculation START
        Double doubleTotalSalary = Double.valueOf(decfor.format(basic + hra + performanceBased + convenyenceAllowance + statutoryAllowance));
        String totalSalary = String.format("%,.2f", doubleTotalSalary); // Total Earnings

        Double persence = Double.valueOf(df.format(paidDays * 100 / monthDays));

        Double totalPF = basic * 24 / 100;
        Double doublePfPay = Double.parseDouble(decfor.format(totalPF * persence / 100));
        String pfPay = String.format("%,.2f", doublePfPay);
        double doubleDasicPay = Double.parseDouble(decforTwo.format(basic * persence / 100));
        String basicPay = String.format("%,.2f", doubleDasicPay);
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

        Double payWithoutPF = doubleTotalSalary * persence / 100;
        Double doubleLeaveDeduction = Double.valueOf(decfor.format(doubleTotalSalary * (unpaidDays * 100 / monthDays) / 100));
        String leaveDeduction = String.format("%,.2f", doubleLeaveDeduction);
        Double doubleNetPay = Double.valueOf(decfor.format(payWithoutPF - doublePfPay - doublMedical));
        String netPay = String.format("%,.2f", doubleNetPay);
        Double doubleNetDeduction = Double.valueOf(decfor.format(doublePfPay + doublMedical + doubleLeaveDeduction));
        String netDeduction = String.format("%,.2f", doubleNetDeduction);

        // Salary Calculation END
        Rectangle rect = PageSize.A4;
        Document document = new Document(rect);
        document.setMargins(-30, -30, 40, 40);

        String url = rootPath + "salarySlip" + File.separator + employeeId + File.separator;
        File file = new File(url);
        if (!file.exists()) file.mkdirs();

        url = url + monthInString + "_" + year + ".pdf";

        try {
            PdfWriter.getInstance(document, new FileOutputStream(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
        document.open();

        Font headFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLACK);
        Font subHeadFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
        Font boldFieldFont = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.BLACK);
        Font fieldFont = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL, BaseColor.BLACK);
        Font boldFooterFont = new Font(Font.FontFamily.HELVETICA, 7.5f, Font.BOLD, BaseColor.BLACK);
        Font footerFont = new Font(Font.FontFamily.HELVETICA, 7.5f, Font.NORMAL, BaseColor.BLACK);

        // Header Image and Month Name
        String imageUrl = rootPath + "static" + File.separator + "image" + File.separator + "getepay.png";
        Image image = Image.getInstance(imageUrl);
        image.scaleToFit(50, 100);
        image.setAlignment(1);

        float[] pointColumnWidths1 = {50f, 250f};
        PdfPTable tableHeder1 = new PdfPTable(pointColumnWidths1);
        PdfPCell cell1 = new PdfPCell(image, false);
        cell1.setPaddingLeft(30f);
        cell1.setBorder(0);

        PdfPCell cell2 = new PdfPCell(new Phrase("Futuretek Commerce Private Limited", headFont));
        cell2.setHorizontalAlignment(1);
        cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell2.setPaddingTop(-15f);
        cell2.setPaddingLeft(-50f);
        cell2.setBorder(0);

        PdfPCell cell3 = new PdfPCell(new Phrase("", headFont));
        cell3.setHorizontalAlignment(1);
        cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell3.setBorder(0);

        PdfPCell cell4 = new PdfPCell(new Phrase("Plot No. 60, Vishwamitra Nagar, Murlipura, Jaipur - 302039 \nE-mail : info@getepay.in", fieldFont));
        cell4.setHorizontalAlignment(1);
        cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell4.setPaddingTop(-22f);
        cell4.setPaddingLeft(-50f);
        cell4.setBorder(0);

        tableHeder1.addCell(cell1);
        tableHeder1.addCell(cell2);
        tableHeder1.addCell(cell3);
        tableHeder1.addCell(cell4);

//		New PDF start
        float[] pointColumnWidths2 = {100f};
        PdfPTable tableHeder2 = new PdfPTable(pointColumnWidths2);
        tableHeder2.setSpacingBefore(20f);
        tableHeder2.setSpacingAfter(15f);

        try {
            blueinsertHeadingCell(tableHeder2, "Payslip for the month of " + monthInString + " " + year, 5f, 5f, Element.ALIGN_CENTER, 1, 0, subHeadFont, 0f, 0f, 0f, 0f);
        } catch (Exception e) {
            e.printStackTrace();
        }

        float[] pointColumnWidths3 = {50f, 65f, 50f, 65f};
        PdfPTable tableHeder3 = new PdfPTable(pointColumnWidths3);

        blueinsertHeadingCell(tableHeder3, "Employee ID", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, ": " + employeeId, 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, "Date Of Join  ", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, ": " + doj, 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, "Employee Name  ", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, ": " + employeeName, 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, "PAN No. ", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, ": " + pan, 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, "Designation ", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, ": " + designation, 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, "UAN No. ", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, ": " + uanNo, 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, "Department ", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, ": " + department, 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, "Total Days", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, ": " + monthDays, 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, "Bank Name ", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, ": " + bankName, 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, "Working Days", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, ": " + paidDays, 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, "Bank A/C No. ", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, ": " + bankAccount, 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, "Days Absent ", 3f, 33f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, ": " + unpaidDays, 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        float[] pointColumnWidths4 = {33f, 40f, 33f};
        PdfPTable tableHeder4 = new PdfPTable(pointColumnWidths4);
        blueinsertHeadingCell(tableHeder4, "Total Days : " + monthDays, 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0.3f, 0f, 0.3f, 0f);
        blueinsertHeadingCell(tableHeder4, "Paid Days : " + paidDays, 3f, 3f, Element.ALIGN_CENTER, 1, 0, fieldFont, 0.3f, 0f, 0.3f, 0f);
        blueinsertHeadingCell(tableHeder4, "Absents : " + unpaidDays, 3f, 3f, Element.ALIGN_RIGHT, 1, 0, fieldFont, 0.3f, 0f, 0.3f, 0f);

        float[] pointColumnWidths5 = {100f};
        PdfPTable tableHeder5 = new PdfPTable(pointColumnWidths5);
//        tableHeder5.setSpacingBefore(15f);

        try {
            blueinsertHeadingCell(tableHeder5, "Salary Details", 20f, 5f, Element.ALIGN_CENTER, 1, 0, subHeadFont, 0f, 0.3f, 0f, 0.3f);
        } catch (Exception e) {
            e.printStackTrace();
        }

        float[] pointColumnWidths6 = {85f, 40f, 85f, 40f};
        PdfPTable tableHeder6 = new PdfPTable(pointColumnWidths6);

        blueinsertHeadingCell(tableHeder6, "Earnings", 5f, 5f, Element.ALIGN_LEFT, 1, 0, boldFieldFont, 0f, 0f, 1f, 0f);
        blueinsertHeadingCell(tableHeder6, "Amount", 5f, 5f, Element.ALIGN_LEFT, 1, 0, boldFieldFont, 0f, 0f, 1f, 0f);

        blueinsertHeadingCell(tableHeder6, "Deductions", 5f, 5f, Element.ALIGN_LEFT, 1, 0, boldFieldFont, 0f, 0f, 1f, 0f);
        blueinsertHeadingCell(tableHeder6, "Amount", 5f, 5f, Element.ALIGN_LEFT, 1, 0, boldFieldFont, 0f, 0f, 1f, 0f);

        blueinsertHeadingCell(tableHeder6, "Basic Salary", 5f, 5f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0.3f, 0f);

        blueinsertHeadingCell(tableHeder6, "Rs. " + basicPay, 5f, 5f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0.3f, 0f);

        blueinsertHeadingCell(tableHeder6, "Provident Fund (PF)", 5f, 5f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0.3f, 0f);

        blueinsertHeadingCell(tableHeder6, "Rs. " + pfPay, 5f, 5f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0.3f, 0f);

        blueinsertHeadingCell(tableHeder6, "House Rent Allowances", 5f, 5f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0.3f, 0f);

        blueinsertHeadingCell(tableHeder6, "Rs. " + hraPay, 5f, 5f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0.3f, 0f);

        blueinsertHeadingCell(tableHeder6, "Tax Deduction (TDs)", 5f, 5f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0.3f, 0f);

        blueinsertHeadingCell(tableHeder6, "Rs. " + tdsPay, 5f, 5f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0.3f, 0f);

        blueinsertHeadingCell(tableHeder6, "Performance Allowances", 5f, 5f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0.3f, 0f);

        blueinsertHeadingCell(tableHeder6, "Rs. " + performancePay, 5f, 5f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0.3f, 0f);

        blueinsertHeadingCell(tableHeder6, "ESI/Health Insurance", 5f, 5f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0.3f, 0f);

        blueinsertHeadingCell(tableHeder6, "Rs. " + medical, 5f, 5f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0.3f, 0f);

        blueinsertHeadingCell(tableHeder6, "Convenyence Allowances", 5f, 5f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0.3f, 0f);

        blueinsertHeadingCell(tableHeder6, "Rs. " + convenyencePay, 5f, 5f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0.3f, 0f);

        blueinsertHeadingCell(tableHeder6, "Loss of Pay (LOP)", 5f, 5f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0.3f, 0f);

        blueinsertHeadingCell(tableHeder6, "Rs. " + leaveDeduction, 5f, 5f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0.3f, 0f);

        blueinsertHeadingCell(tableHeder6, "Statutory Allowances ", 5f, 5f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0.3f, 0f);

        blueinsertHeadingCell(tableHeder6, "Rs. " + statutoryPay, 5f, 5f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0.3f, 0f);

        blueinsertHeadingCell(tableHeder6, " ", 5f, 5f, Element.ALIGN_LEFT, 2, 0, fieldFont, 0f, 0f, 0.3f, 0f);

        blueinsertHeadingCell(tableHeder6, " ", 5f, 5f, Element.ALIGN_LEFT, 4, 0, fieldFont, 0f, 0f, 0.3f, 0f);

        float[] pointColumnWidths7 = {100f};
        PdfPTable tableHeder7 = new PdfPTable(pointColumnWidths7);
//        tableHeder7.setSpacingBefore(15f);
        try {
            blueinsertHeadingCell(tableHeder7, "Payment", 20f, 5f, Element.ALIGN_CENTER, 1, 0, subHeadFont, 0f, 0.3f, 0f, 0.3f);
        } catch (Exception e) {
            e.printStackTrace();
        }

        float[] pointColumnWidths8 = {85f, 40f, 85f, 40f};
        PdfPTable tableHeder8 = new PdfPTable(pointColumnWidths8);
        tableHeder8.setSpacingAfter(20f);
        blueinsertHeadingCell(tableHeder8, "Total Earnings", 5f, 5f, Element.ALIGN_CENTER, 1, 0, boldFieldFont, 0f, 0f, 0.3f, 0f);
        blueinsertHeadingCell(tableHeder8, "Rs. " + totalSalary, 5f, 5f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0.3f, 0f);

        blueinsertHeadingCell(tableHeder8, "Total Deductions", 5f, 5f, Element.ALIGN_CENTER, 1, 0, boldFieldFont, 0f, 0f, 0.3f, 0f);
        blueinsertHeadingCell(tableHeder8, "Rs. " + netDeduction, 5f, 5f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0.3f, 0f);

        blueinsertHeadingCell(tableHeder8, "", 5f, 5f, Element.ALIGN_LEFT, 2, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder8, "                               Net Pay", 5f, 5f, Element.ALIGN_LEFT, 1, 0, boldFieldFont, 0f, 0f, 0f, 0f);
        blueinsertHeadingCell(tableHeder8, "Rs. " + netPay, 5f, 5f, Element.ALIGN_LEFT, 1, 0, boldFieldFont, 0f, 0f, 0f, 0f);


        float[] pointColumnWidths9 = {20f, 50f};
        PdfPTable tableHeder9 = new PdfPTable(pointColumnWidths9);
        tableHeder9.setSpacingBefore(25f);
        try {
            blueinsertHeadingCell(tableHeder9, "Note :-", 0f, 5f, Element.ALIGN_RIGHT, 1, 0, boldFooterFont, 0f, 0f, 0f, 0f);

            blueinsertHeadingCell(tableHeder9, "This is a computer generated Payslip and does not require signature.", 0f, 5f, Element.ALIGN_LEFT, 1, 0, footerFont, 0f, 0f, 0f, 0f);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            document.add(tableHeder1);
            document.add(tableHeder2);
            document.add(tableHeder3);
//            document.add(tableHeder4);
//            document.add(tableHeder5);
            document.add(tableHeder6);
//            document.add(tableHeder7);
            document.add(tableHeder8);
            document.add(tableHeder9);

            document.close();
            return url;

        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return "";
    }


    private static void blueinsertHeadingCell(PdfPTable table1, String text, float paddingTop, float paddingBottom, int align, int colspan, int rowspan, Font font, float top, float left, float bottom, float right) {
        if (text == null) {
            text = "";
        }
        PdfPCell cell = new PdfPCell();
        try {
            cell = new PdfPCell(new Phrase(text, font));
        } catch (Exception e) {
            e.printStackTrace();
        }
        cell.setBackgroundColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(align);
        if (rowspan >= 2) {
            cell.setRowspan(rowspan);
        }
        cell.setColspan(colspan);
        if (text.trim().equalsIgnoreCase("")) {
            cell.setMinimumHeight(6f);
        }
        cell.setPaddingRight(5f);
        cell.setPaddingBottom(paddingBottom);
        cell.setPaddingTop(paddingTop);
        cell.setPaddingLeft(5f);
        cell.setBorder(Rectangle.BOX);
        cell.setBorderWidthTop(top);
        cell.setBorderWidthBottom(bottom);
        cell.setBorderWidthLeft(left);
        cell.setBorderWidthRight(right);
        table1.addCell(cell);
    }

    private static void blueinsertHeadingCell(PdfPTable table1, String text, int align, int colspan, int rowspan, Font font) {
        if (text == null) {
            text = "";
        }
        PdfPCell cell = new PdfPCell();
        try {
            cell = new PdfPCell(new Phrase(text.trim(), font));
        } catch (Exception e) {
            e.printStackTrace();
        }
        cell.setBackgroundColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(align);
        if (rowspan >= 2) {
            cell.setRowspan(rowspan);
        }
        cell.setColspan(colspan);
        if (text.trim().equalsIgnoreCase("")) {
            cell.setMinimumHeight(6f);
        }
        cell.setPaddingRight(5f);
        cell.setPaddingBottom(5f);
        cell.setPaddingLeft(5f);
        cell.setBorder(Rectangle.BOX);
        table1.addCell(cell);
    }

    private static void blueinsertWithoutBorder(PdfPTable table1, String text, int align, int colspan, int rowspan, Font font) {
        if (text.equalsIgnoreCase("") || text == null) {
            text = "";
        }
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
        cell.setBackgroundColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(align);
        if (rowspan >= 2) {
            cell.setRowspan(rowspan);
        }
        cell.setColspan(colspan);
        if (text.trim().equalsIgnoreCase("")) {
            cell.setMinimumHeight(6f);
        }
        cell.setPaddingRight(5f);
        cell.setBorder(Rectangle.NO_BORDER);
        table1.addCell(cell);
    }

}
