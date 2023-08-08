package com.universal.em.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.universal.em.dto.response.SalarySlipCalculationDto;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;

public class PaySlipTemplate3 {

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


        Rectangle rect = PageSize.LETTER_LANDSCAPE;
        Document document = new Document(rect);
        document.setMargins(-40, -40, 25, 25);

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
        BaseColor color = WebColors.getRGBColor("rgb(21,27,141,255)"); // Color name to RGB

        Font headFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLACK);
        Font subHeadFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, color);
        Font boldFieldFont = new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, color);
        Font fieldFont = new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL, color);
        Font boldFooterFont = new Font(Font.FontFamily.HELVETICA, 7f, Font.BOLD, BaseColor.BLACK);
        Font footerFont = new Font(Font.FontFamily.HELVETICA, 6f, Font.NORMAL, BaseColor.BLACK);

        // Header Image and Company Name Start
        PdfPTable tableHeder1 = null;

        System.out.println("Image is not present");
        float[] pointColumnWidths1 = {100f};
        tableHeder1 = new PdfPTable(pointColumnWidths1);

        blueinsertHeadingCell(tableHeder1, companyName, 5f, 5f, Element.ALIGN_CENTER, 1, 0, subHeadFont, 0.5f, 0.5f, 0.5f, 0.5f);
        // End

        // Pay Slip Month year
        float[] pointColumnWidths2 = {100f};
        PdfPTable tableHeder2 = new PdfPTable(pointColumnWidths2);

        try {
            blueinsertHeadingCell(tableHeder2, "Payslip for the month of " + monthInString + " " + year, 5f, 5f, Element.ALIGN_CENTER, 1, 0, subHeadFont, 0f, 0.5f, 0.5f, 0.5f);
        } catch (Exception e) {
            e.printStackTrace();
        }

        float[] pointColumnWidths3 = {45f, 85f, 45f, 85f, 45f,45f};
        PdfPTable tableHeder3 = new PdfPTable(pointColumnWidths3);

        blueinsertHeadingCell(tableHeder3, "Emp ID", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0.5f, 0f, 0.5f);

        blueinsertHeadingCell(tableHeder3,  employeeId, 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, "DOJ", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0.5f, 0f, 0.5f);

        blueinsertHeadingCell(tableHeder3,  doj, 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, "Working Days", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0.5f, 0f, 0.5f);

        blueinsertHeadingCell(tableHeder3, ""+paidDays , 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0.5f);



        blueinsertHeadingCell(tableHeder3, "Emp Name", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0.5f, 0f, 0.5f);

        blueinsertHeadingCell(tableHeder3,  employeeName, 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, "PAN", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0.5f, 0f, 0.5f);

        blueinsertHeadingCell(tableHeder3,  pan, 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, "LOP", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0.5f, 0f, 0.5f);

        blueinsertHeadingCell(tableHeder3,  ""+unpaidDays, 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0.5f);




        blueinsertHeadingCell(tableHeder3, "Designation", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0.5f, 0f, 0.5f);

        blueinsertHeadingCell(tableHeder3,  designation, 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, "UAN NO.", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0.5f, 0f, 0.5f);

        blueinsertHeadingCell(tableHeder3,  uanNo, 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, " ", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0.5f, 0f, 0.5f);

        blueinsertHeadingCell(tableHeder3, " " , 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0.5f);




        blueinsertHeadingCell(tableHeder3, "Department", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0.5f, 0f, 0.5f);

        blueinsertHeadingCell(tableHeder3,  department, 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, "PF NO.", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0.5f, 0f, 0.5f);

        blueinsertHeadingCell(tableHeder3, " ", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, " ", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0.5f, 0f, 0.5f);

        blueinsertHeadingCell(tableHeder3, " ", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0.5f);




        blueinsertHeadingCell(tableHeder3, "Bank Account", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0.5f, 0f, 0.5f);

        blueinsertHeadingCell(tableHeder3,  bankAccount, 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, "ESI NO.", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0.5f, 0f, 0.5f);

        blueinsertHeadingCell(tableHeder3, " ", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder3, " ", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0.5f, 0f, 0.5f);

        blueinsertHeadingCell(tableHeder3, " ", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0.5f);





        blueinsertHeadingCell(tableHeder3, "Mode of Payment", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0.5f, 0.5f, 0.5f);

        blueinsertHeadingCell(tableHeder3, "Bank transfer", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0.5f, 0f);

        blueinsertHeadingCell(tableHeder3, "TRANS DATE", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0.5f, 0.5f, 0.5f);

        blueinsertHeadingCell(tableHeder3, " ", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0.5f, 0f);

        blueinsertHeadingCell(tableHeder3, "  ", 3f, 3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0.5f, 0.5f, 0.5f);

        blueinsertHeadingCell(tableHeder3, " ", 3f, 15f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0.5f, 0.5f);




        float[] pointColumnWidths6 = {65f, 50f, 65f, 50f};
        PdfPTable tableHeder6 = new PdfPTable(pointColumnWidths6);
        tableHeder6.setSpacingBefore(2f);

        blueinsertHeadingCell(tableHeder6, "Earnings", 3f, 3f, Element.ALIGN_LEFT, 1, 0, boldFieldFont, 0.5f, 0.5f, 0.5f, 0.5f);
        blueinsertHeadingCell(tableHeder6, " ", 3f, 3f, Element.ALIGN_LEFT, 1, 0, boldFieldFont, 0.5f, 0f, 0.5f, 0f);

        blueinsertHeadingCell(tableHeder6, "Deductions", 3f, 3f, Element.ALIGN_LEFT, 1, 0, boldFieldFont, 0.5f, 0.5f, 0.5f, 0.5f);
        blueinsertHeadingCell(tableHeder6, " ", 3f,3f, Element.ALIGN_LEFT, 1, 0, boldFieldFont, 0.5f, 0f, 0.5f, 0.5f);



        blueinsertHeadingCell(tableHeder6, "Basic Salary", 3f,3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0.5f, 0f, 0.5f);

        blueinsertHeadingCell(tableHeder6, "Rs. " + basicPay, 3f,3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder6, "Provident Fund (PF)", 3f,3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0.5f, 0f, 0.5f);

        blueinsertHeadingCell(tableHeder6, "Rs. " + pfPay, 3f,3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0.5f);



        blueinsertHeadingCell(tableHeder6, "House Rent Allowances", 3f,3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0.5f, 0f, 0.5f);

        blueinsertHeadingCell(tableHeder6, "Rs. " + hraPay, 3f,3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder6, "Tax Deduction (TDs)", 3f,3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0.5f, 0f, 0.5f);

        blueinsertHeadingCell(tableHeder6, "Rs. " + tdsPay, 3f,3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0.5f);



        blueinsertHeadingCell(tableHeder6, "Performance Allowances", 3f,3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0.5f, 0f, 0.5f);

        blueinsertHeadingCell(tableHeder6, "Rs. " + performancePay, 3f,3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder6, "ESI/Health Insurance", 3f,3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0.5f, 0f, 0.5f);

        blueinsertHeadingCell(tableHeder6, "Rs. " + medical, 3f,3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0.5f);



        blueinsertHeadingCell(tableHeder6, "Convenyence Allowances", 3f,3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0.5f, 0f, 0.5f);

        blueinsertHeadingCell(tableHeder6, "Rs. " + convenyencePay, 3f,3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder6, "Loss of Pay (LOP)", 3f,3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0.5f, 0f, 0.5f);

        blueinsertHeadingCell(tableHeder6, "Rs. " + leaveDeduction, 3f,3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0.5f);



        blueinsertHeadingCell(tableHeder6, "Statutory Allowances ", 3f,3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0.5f, 0.5f, 0.5f);

        blueinsertHeadingCell(tableHeder6, "Rs. " + statutoryPay, 3f,3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0.5f, 0f);

        blueinsertHeadingCell(tableHeder6, " ", 3f,3f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0.5f, 0.5f, 0.5f);
        blueinsertHeadingCell(tableHeder6, " ", 5f, 100f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0.5f, 0.5f);


        float[] pointColumnWidths8 = {65f, 50f, 65f, 50f};
        PdfPTable tableHeder8 = new PdfPTable(pointColumnWidths8);
        tableHeder8.setSpacingAfter(7f);
        blueinsertHeadingCell(tableHeder8, "Total", 5f, 5f, Element.ALIGN_LEFT, 1, 0, boldFieldFont, 0f, 0.5f, 0f, 0.5f);
        blueinsertHeadingCell(tableHeder8, "Rs. " + toaltEarnings, 5f, 5f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

        blueinsertHeadingCell(tableHeder8, "Total", 5f, 5f, Element.ALIGN_LEFT, 1, 0, boldFieldFont, 0f, 0.5f, 0f, 0.5f);
        blueinsertHeadingCell(tableHeder8, "Rs. " + netDeduction, 5f, 5f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0.5f);



        blueinsertHeadingCell(tableHeder8, "Gross Salary", 5f, 5f, Element.ALIGN_LEFT, 1, 0, boldFieldFont, 0.5f, 0.5f, 0.5f, 0.5f);
        blueinsertHeadingCell(tableHeder8, "Rs. " + totalSalary, 5f, 5f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0.5f, 0f, 0.5f, 0f);

        blueinsertHeadingCell(tableHeder8, "Bank Transafer", 5f, 5f, Element.ALIGN_LEFT, 1, 0, boldFieldFont, 0.5f, 0.5f, 0.5f, 0.5f);
        blueinsertHeadingCell(tableHeder8, "Rs. " + netPay, 5f, 5f, Element.ALIGN_LEFT, 1, 0, boldFieldFont, 0.5f, 0f, 0.5f, 0.5f);


        float[] pointColumnWidths9 = {30f, 50f};
        PdfPTable tableHeder9 = new PdfPTable(pointColumnWidths9);
//        tableHeder9.setSpacingBefore(10f);
        try {
            blueinsertHeadingCell(tableHeder9, "Note :-", 0f, 5f, Element.ALIGN_RIGHT, 1, 0, boldFieldFont, 0f, 0f, 0f, 0f);

            blueinsertHeadingCell(tableHeder9, "This is a computer generated Payslip and does not require signature.", 0f, 5f, Element.ALIGN_LEFT, 1, 0, fieldFont, 0f, 0f, 0f, 0f);

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
