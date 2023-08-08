package com.universal.em.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.universal.em.dto.response.SalarySlipCalculationDto;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;

public class PaySlipTemplate1 {

    public static String generate(SalarySlipCalculationDto slipCalculationDto) throws Exception {
        String rootPath = Paths.get("").toAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator;
//        rootPath = "/Users/jitenderyadav/Documents/slip/";

        String bankName = slipCalculationDto.getBankName();
        String bankAccount = slipCalculationDto.getBankAccount();
        String basicPay = slipCalculationDto.getBasicPay();
        String pfPay = slipCalculationDto.getPfPay();
        String hraPay = slipCalculationDto.getHraPay();
        String tdsPay = slipCalculationDto.getTdsPay();
        String performancePay = slipCalculationDto.getPerformancePay();
        String medical = slipCalculationDto.getMedical();
        String convenyencePay = slipCalculationDto.getConvenyencePay();
        String leaveDeduction = slipCalculationDto.getLeaveDeduction();
        String statutoryPay = slipCalculationDto.getStatutoryPay();
        String toaltEarnings = slipCalculationDto.getToaltEarnings();
        String netDeduction = slipCalculationDto.getNetDeduction();
        String totalSalary = slipCalculationDto.getTotalSalary();
        String netPay = slipCalculationDto.getNetPay();
        String employeeId = slipCalculationDto.getEmployeeId();
        String doj = slipCalculationDto.getDoj();
        String employeeName = slipCalculationDto.getEmployeeName();
        String pan = slipCalculationDto.getPan();
        String designation = slipCalculationDto.getDesignation();
        String uanNo = slipCalculationDto.getUanNo();
        String department = slipCalculationDto.getDepartment();
        String monthInString = slipCalculationDto.getMonthInString();
        String logo = slipCalculationDto.getLogo();
        String companyName = slipCalculationDto.getCompanyName();
        String companyAddress = slipCalculationDto.getCompanyAddress();
        String companyMail = slipCalculationDto.getCompanyMail();
        String year = slipCalculationDto.getYear();
        String unpaidDays = slipCalculationDto.getUnpaidDays();
        String paidDays = slipCalculationDto.getPaidDays();
        String totalDays = slipCalculationDto.getTotalDays();
        String location = slipCalculationDto.getLocation();

        Rectangle rect = PageSize.LETTER_LANDSCAPE;
        Document document = new Document(rect);
        document.setMargins(-40, -40, 40, 40);

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
        Font boldFieldFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK);
        Font fieldFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
        Font boldFooterFont = new Font(Font.FontFamily.HELVETICA, 8f, Font.BOLD, BaseColor.BLACK);
        Font footerFont = new Font(Font.FontFamily.HELVETICA, 8f, Font.NORMAL, BaseColor.BLACK);

        // Header Image and Company Name Start
        if (logo == null)
            logo = "";
        String imageUrl = logo;
        PdfPTable tableHeder1 = null;
        PdfPCell cell1 = null;
        if (new File(imageUrl).exists()) {
            System.out.println("Image is present");
            Image image = Image.getInstance(imageUrl);
            image.scaleToFit(50, 50);
            image.setAlignment(Element.ALIGN_TOP);

            float[] pointColumnWidths1 = {50f, 250f};
            tableHeder1 = new PdfPTable(pointColumnWidths1);
            cell1 = new PdfPCell(image, true);
            cell1.setPaddingLeft(50f);
            cell1.setBorder(Rectangle.NO_BORDER);
        } else {
            System.out.println("Image is not present");
            float[] pointColumnWidths1 = {100f};
            tableHeder1 = new PdfPTable(pointColumnWidths1);
        }

        PdfPCell cell2 = new PdfPCell();
        Paragraph p = new Paragraph();
        p.add(new Phrase(companyName, headFont));
        p.add(new Phrase("\n" + companyAddress + "\nE-mail : " + companyMail, fieldFont));
        p.setAlignment(Element.ALIGN_CENTER);
        cell2.addElement(p);

        cell2.setHorizontalAlignment(1);
        cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell2.setPaddingRight(60f);
        cell2.setBorder(Rectangle.NO_BORDER);

        if (new File(imageUrl).exists()) {
            System.out.println("Image is present");
            tableHeder1.addCell(cell1);
            tableHeder1.addCell(cell2);
        } else {
            System.out.println("Image is not present");
            cell2.setPaddingRight(0f);
            tableHeder1.addCell(cell2);
        }
        // End

        // Pay Slip Month year
        float[] pointColumnWidths2 = {100f};
        PdfPTable tableHeder2 = new PdfPTable(pointColumnWidths2);
        tableHeder2.setSpacingBefore(30f);
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

        blueinsertHeadingCell(tableHeder3, ": " + totalDays, 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, "Bank Name ", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, ": " + bankName, 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, "Working Days", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, ": " + paidDays, 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, "Bank A/C No. ", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, ": " + bankAccount, 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, "Days Absent ", 3f, 33f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, ": " + unpaidDays, 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);


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


        float[] pointColumnWidths8 = {85f, 40f, 85f, 40f};
        PdfPTable tableHeder8 = new PdfPTable(pointColumnWidths8);
        tableHeder8.setSpacingAfter(20f);
        blueinsertHeadingCell(tableHeder8, "Total Earnings", 5f, 5f, Element.ALIGN_CENTER, 1, 0, boldFieldFont, 0f, 0f, 0.3f, 0f);
        blueinsertHeadingCell(tableHeder8, "Rs. " + toaltEarnings, 5f, 5f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0.3f, 0f);

        blueinsertHeadingCell(tableHeder8, "Total Deductions", 5f, 5f, Element.ALIGN_CENTER, 1, 0, boldFieldFont, 0f, 0f, 0.3f, 0f);
        blueinsertHeadingCell(tableHeder8, "Rs. " + netDeduction, 5f, 5f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0.3f, 0f);

        blueinsertHeadingCell(tableHeder8, "Gross Salary", 5f, 5f, Element.ALIGN_CENTER, 1, 0, fieldFont, 0f, 0f, 0f, 0f);
        blueinsertHeadingCell(tableHeder8, "Rs. " + totalSalary, 5f, 5f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

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
            document.add(tableHeder6);
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
}
