package com.universal.em.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.util.Calendar;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.universal.em.dto.response.EmployeeResDto;
import com.universal.em.dto.response.EmployeeSalaryResDto;

public class NewOriginal {

	private static final DecimalFormat decfor = new DecimalFormat("#");

	private static final DecimalFormat df = new DecimalFormat("#.000");
	private static final DecimalFormat decforTwo = new DecimalFormat("#.00");

	public static void main(String[] args) {

		try {
			int month = 6;
			int year = 2023;
			Double PA = 27.5; // Persence attendence
			Double TA = 30.0; // Total attendence
//			PA = 29.0; // Persence attendence
//			TA = 31.0; // Total attendence
			Double basic = 10000.0;
			Double hra = 7060.0;
			Double performanceBased = 7200.0;
			Double convenyenceAllowance = 3100.0;
			Double statutoryAllowance = 2640.0;
			Double medical = 240.0;


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


			String salarySlipPath = generate(month, year, PA, TA-PA, employeeSalaryResDto, employeeResDto);
			System.out.println(AesEncryption.encrypt(salarySlipPath));
			Double totalSalary = basic + hra + performanceBased + convenyenceAllowance + statutoryAllowance;
			System.out.println("Total Salary ==>> " + totalSalary);

			Double persence = PA * 100 / TA;
			System.out.println("Total Persent in % = " + persence);

			Double totalPF = basic * 24 / 100;
			System.out.println("Total PF ==>> " + totalPF);
			Double paidPF = totalPF * persence / 100;

			Double paidBasic = Double.valueOf(decforTwo.format(basic * persence / 100));
			System.out.println("Paid basic =>> " + paidBasic);
			Double paidHra = Double.valueOf(decforTwo.format(hra * persence / 100));
			System.out.println("Paid HRA =>> " + paidHra);
			Double paidPerformance = Double.valueOf(decforTwo.format(performanceBased * persence / 100));
			System.out.println("Paid Performance =>> " + paidPerformance);
			Double paidConvenyence = Double.valueOf(decforTwo.format(convenyenceAllowance * persence / 100));
			System.out.println("Paid Convenyence =>> " + paidConvenyence);
			Double paidStatutory = Double.valueOf(decforTwo.format(statutoryAllowance * persence / 100));
			System.out.println("Paid Statutory =>> " + paidStatutory);

			Double payWithoutPF = totalSalary * persence / 100;
			Double netPaid = payWithoutPF - paidPF - medical;
			System.out.println("Paid PF =>> " + paidPF);
			System.out.println("Salary credit in Acc ==>> " + Double.valueOf(decfor.format(netPaid)));

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("catch");
		}
	}

	public static String generate(int month, int year, Double paidDays, Double unpaidDays, EmployeeSalaryResDto employeeSalaryResDto, EmployeeResDto employeeResDto)
			throws Exception {

		Double basic = Double.valueOf(employeeSalaryResDto.getBasicSalary());
		Double hra = Double.valueOf(employeeSalaryResDto.getHra());
		Double performanceBased = Double.valueOf(employeeSalaryResDto.getPerformanceBased());
		Double convenyenceAllowance = Double.valueOf(employeeSalaryResDto.getConveyanceAllowance());
		Double statutoryAllowance = Double.valueOf(employeeSalaryResDto.getStatutoryAllowance());
		Double medical = Double.valueOf(employeeSalaryResDto.getMedical());

		String employeeId = employeeResDto.getCompanyEmpId();
		String doj = employeeResDto.getDoj();
		String employeeName = employeeResDto.getName();
		String pan = employeeResDto.getPanNo();
		String designation = employeeResDto.getDesignation();
		String uanNo = employeeSalaryResDto.getUanNo();
		String department = employeeResDto.getDepartment();
		String location = null;
		String bankAccount = employeeSalaryResDto.getAccountNo();
		String bankName = employeeSalaryResDto.getBankName();

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);

		String monthInString = new DateFormatSymbols().getMonths()[month - 1];

		Double monthDays = Math.floor((Double.valueOf(calendar.getActualMaximum(Calendar.DAY_OF_MONTH))));

		unpaidDays = monthDays - paidDays;

		// Salary Calculation START
		Double totalSalary = Double
				.valueOf(decfor.format(basic + hra + performanceBased + convenyenceAllowance + statutoryAllowance));

		Double persence = Double.valueOf( df.format(paidDays * 100 / monthDays));

		Double totalPF = basic * 24 / 100;
		Double pfPay = Double.parseDouble(decfor.format(totalPF * persence / 100));

		double basicPay = Double.parseDouble(decforTwo.format(basic * persence / 100));
		double hraPay = Double.parseDouble(decforTwo.format(hra * persence / 100));
		double performancePay = Double.parseDouble(decforTwo.format(performanceBased * persence / 100));
		double convenyencePay = Double.parseDouble(decforTwo.format(convenyenceAllowance * persence / 100));
		double statutoryPay = Double.parseDouble(decforTwo.format(statutoryAllowance * persence / 100));
		double tdsPay = Double.parseDouble(decforTwo.format(0));

		Double payWithoutPF = totalSalary * persence / 100;
		Double leaveDeduction = Double.valueOf(decfor.format(totalSalary*(unpaidDays * 100 / monthDays)/100));
		Double netPay = Double.valueOf(decfor.format(payWithoutPF - pfPay - medical));
		Double netDeduction = Double.valueOf(decfor.format(pfPay + medical+leaveDeduction));

		// Salary Calculation END

		Document document = new Document();
		String url = "/Users/jitenderyadav/Documents/Slip/" + monthInString + year + File.separator;
		File file = new File(url);
		if (!file.exists())
			file.mkdirs();

		url = url + monthInString + year + ".pdf";

		try {
			PdfWriter.getInstance(document, new FileOutputStream(url));
		} catch (Exception e) {
			e.printStackTrace();
		}

		document.open();

		Font urlFont1 = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
		Font urlFont2 = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
		Font fontHead = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
		Font urlFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
		Font urlFont3 = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK);
		Font urlFont4 = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);

		// Header Image and Month Name
		String url2 = Paths.get("").toAbsolutePath().toString() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator;
		String finalUrl = url2 + "/static/image/getepay.png";
		Image img = Image.getInstance(finalUrl);
		img.scaleToFit(50, 100);
		img.setAlignment(1);

		float[] pointColumnWidths = { 50f, 250f };
		PdfPTable tableHeder1 = new PdfPTable(pointColumnWidths);
		PdfPCell cell1 = new PdfPCell(img, false);
		cell1.setBorder(0);

		PdfPCell cell2 = new PdfPCell(new Phrase("Futuretek Commerce Private Limited ", fontHead));
		cell2.setHorizontalAlignment(1);
		cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell2.setPaddingTop(-15f);
		cell2.setBorder(0);

		PdfPCell cell3 = new PdfPCell(new Phrase("", fontHead));
		cell3.setHorizontalAlignment(1);
		cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell3.setBorder(0);

		PdfPCell cell4 = new PdfPCell(new Phrase(
				"Plot No. 60, Vishwamitra Nagar, Murlipura, Jaipur - 302039 \nE-mail : info@getepay.in", urlFont2));
		cell4.setHorizontalAlignment(1);
		cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell4.setPaddingTop(-30f);
		cell4.setBorder(0);

		tableHeder1.addCell(cell1);
		tableHeder1.addCell(cell2);
		tableHeder1.addCell(cell3);
		tableHeder1.addCell(cell4);

//		New PDF start
		float[] pointColumnWidths31 = { 100f };
		PdfPTable tableHeder31 = new PdfPTable(pointColumnWidths31);
		tableHeder31.setSpacingBefore(30f);

		try {
			blueinsertHeadingCell(tableHeder31, "Salaryslip for the month of " + monthInString + "-" + year,
					Element.ALIGN_CENTER, 1, 0, urlFont3);
		} catch (Exception e) {
			e.printStackTrace();
		}

		float[] pointColumnWidths32 = { 45f, 65f, 40f, 75f };
		PdfPTable tableHeder32 = new PdfPTable(pointColumnWidths32);

		blueinsertHeadingCellRightButtom(tableHeder32, "Employee ID :", Element.ALIGN_LEFT, 1, 0, urlFont2, 0);

		blueinsertHeadingCellLeftBottom(tableHeder32, employeeId, Element.ALIGN_LEFT, 1, 0, urlFont1, 0);

		blueinsertHeadingCellBottom(tableHeder32, "Date Of Joining :  ", Element.ALIGN_LEFT, 1, 0, urlFont2, 0, 0);

		blueinsertHeadingCellLeftBottom(tableHeder32, doj, Element.ALIGN_LEFT, 1, 0, urlFont1, 0);

		blueinsertHeadingCellRightButtom(tableHeder32, "Employee Name :  ", Element.ALIGN_LEFT, 1, 0, urlFont2, 0);

		blueinsertHeadingCellLeftBottom(tableHeder32, employeeName, Element.ALIGN_LEFT, 1, 0, urlFont1, 0);

		blueinsertHeadingCellBottom(tableHeder32, "PAN No. : ", Element.ALIGN_LEFT, 1, 0, urlFont2, 0, 0);

		blueinsertHeadingCellLeftBottom(tableHeder32, pan, Element.ALIGN_LEFT, 1, 0, urlFont1, 0);

		blueinsertHeadingCellRightButtom(tableHeder32, "Designation : ", Element.ALIGN_LEFT, 1, 0, urlFont2, 0);

		blueinsertHeadingCellLeftBottom(tableHeder32, designation, Element.ALIGN_LEFT, 1, 0, urlFont1, 0);

		blueinsertHeadingCellBottom(tableHeder32, "UAN No. : ", Element.ALIGN_LEFT, 1, 0, urlFont2, 0, 0);

		blueinsertHeadingCellLeftBottom(tableHeder32, uanNo, Element.ALIGN_LEFT, 1, 0, urlFont1, 0);

		blueinsertHeadingCellRightButtom(tableHeder32, "Department : ", Element.ALIGN_LEFT, 1, 0, urlFont2, 0);

		blueinsertHeadingCellLeftBottom(tableHeder32, department, Element.ALIGN_LEFT, 1, 0, urlFont1, 0);

		blueinsertHeadingCellBottom(tableHeder32, "Location : ", Element.ALIGN_LEFT, 1, 0, urlFont2, 0, 0);

		blueinsertHeadingCellLeftBottom(tableHeder32, location, Element.ALIGN_LEFT, 1, 0, urlFont1, 0);

		blueinsertHeadingCellRight(tableHeder32, "Bank Name : ", Element.ALIGN_LEFT, 1, 0, urlFont2, 0);

		blueinsertHeadingCellLeft(tableHeder32, bankName, Element.ALIGN_LEFT, 1, 0, urlFont1, 0);

		blueinsertHeadingCell(tableHeder32, "Account No. : ", Element.ALIGN_LEFT, 1, 0, urlFont2, 0,0);

		blueinsertHeadingCellLeft(tableHeder32, bankAccount, Element.ALIGN_LEFT, 1, 0, urlFont1, 0);

		float[] pointColumnWidths40 = { 33f, 40f, 33f };
		PdfPTable tableHeder40 = new PdfPTable(pointColumnWidths40);
		blueinsertHeadingCellRight(tableHeder40, "Total Days : " + monthDays, Element.ALIGN_LEFT, 1, 0, urlFont2, 0);
		blueinsertHeadingCell(tableHeder40, "Paid Days : "+ paidDays, Element.ALIGN_CENTER, 1, 0, urlFont2, 0, 0);
		blueinsertHeadingCellLeft(tableHeder40, "Absent : " +unpaidDays, Element.ALIGN_RIGHT, 1, 0, urlFont2, 0);

		float[] pointColumnWidths33 = { 100f };
		PdfPTable tableHeder33 = new PdfPTable(pointColumnWidths33);
		tableHeder33.setSpacingBefore(15f);

		try {
			blueinsertHeadingCell(tableHeder33, "Salary Details", Element.ALIGN_CENTER, 1, 0, urlFont3);
		} catch (Exception e) {
			e.printStackTrace();
		}

		float[] pointColumnWidths34 = { 65f, 25f, 65f, 25f };
		PdfPTable tableHeder34 = new PdfPTable(pointColumnWidths34);

		blueinsertHeadingCellRight(tableHeder34, "Earnings", Element.ALIGN_LEFT, 1, 0, urlFont4, 0);
		blueinsertHeadingCellLeft(tableHeder34, "Amount", Element.ALIGN_CENTER, 1, 0, urlFont4, 0);

		blueinsertHeadingCellRightLeft(tableHeder34, "Deductions", Element.ALIGN_LEFT, 1, 0, urlFont4, 0);
		blueinsertHeadingCellLeft(tableHeder34, "Amount", Element.ALIGN_CENTER, 1, 0, urlFont4, 0);

		blueinsertHeadingCellRightButtom(tableHeder34, "Basic Salary", Element.ALIGN_LEFT, 1, 0, urlFont2, 0);

		blueinsertHeadingCellLeftBottom(tableHeder34, "Rs. " + basicPay, Element.ALIGN_LEFT, 1, 0, urlFont1, 0);

		blueinsertHeadingCellBottom(tableHeder34, "Provident Fund (PF)", Element.ALIGN_LEFT, 1, 0, urlFont2, 0, 0);

		blueinsertHeadingCellLeftBottom(tableHeder34, "Rs. " + pfPay, Element.ALIGN_LEFT, 1, 0, urlFont1, 0);

		blueinsertHeadingCellRightButtom(tableHeder34, "House Rent Allowances", Element.ALIGN_LEFT, 1, 0, urlFont2, 0);

		blueinsertHeadingCellLeftBottom(tableHeder34, "Rs. " + hraPay, Element.ALIGN_LEFT, 1, 0, urlFont1, 0);

		blueinsertHeadingCellBottom(tableHeder34, "Tax Deduction (TDs)", Element.ALIGN_LEFT, 1, 0, urlFont2, 0, 0);

		blueinsertHeadingCellLeftBottom(tableHeder34, "Rs. " + tdsPay, Element.ALIGN_LEFT, 1, 0, urlFont1, 0);

		blueinsertHeadingCellRightButtom(tableHeder34, "Performance Allowances", Element.ALIGN_LEFT, 1, 0, urlFont2, 0);

		blueinsertHeadingCellLeftBottom(tableHeder34, "Rs. " + performancePay, Element.ALIGN_LEFT, 1, 0, urlFont1, 0);

		blueinsertHeadingCellBottom(tableHeder34, "ESI/Health Insurance", Element.ALIGN_LEFT, 1, 0, urlFont2, 0, 0);

		blueinsertHeadingCellLeftBottom(tableHeder34, "Rs. " + medical, Element.ALIGN_LEFT, 1, 0, urlFont1, 0);

		blueinsertHeadingCellRightButtom(tableHeder34, "Convenyence Allowances", Element.ALIGN_LEFT, 1, 0, urlFont2, 0);

		blueinsertHeadingCellLeftBottom(tableHeder34, "Rs. " + convenyencePay, Element.ALIGN_LEFT, 1, 0, urlFont1, 0);

		blueinsertHeadingCellBottom(tableHeder34, "Leave", Element.ALIGN_LEFT, 1, 0, urlFont2, 0, 0);

		blueinsertHeadingCellLeftBottom(tableHeder34, "Rs. "+leaveDeduction, Element.ALIGN_LEFT, 1, 0, urlFont1, 0);

		blueinsertHeadingCellRightButtom(tableHeder34, "Statutory Allowances ", Element.ALIGN_LEFT, 1, 0, urlFont2, 0);

		blueinsertHeadingCellLeftBottom(tableHeder34, "Rs. " + statutoryPay, Element.ALIGN_LEFT, 1, 0, urlFont1, 0);

		blueinsertHeadingCellBottom(tableHeder34, "", Element.ALIGN_LEFT, 1, 0, urlFont2, 0, 0);

		blueinsertHeadingCellLeftBottom(tableHeder34, "", Element.ALIGN_LEFT, 1, 0, urlFont1, 0);

		blueinsertHeadingCellEmptyTB(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);
		blueinsertHeadingCellEmptyLeft(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);
		blueinsertHeadingCellEmptyTB(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);
		blueinsertHeadingCellEmptyLeft(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);
		blueinsertHeadingCellEmptyTB(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);
		blueinsertHeadingCellEmptyLeft(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);
		blueinsertHeadingCellEmptyTB(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);
		blueinsertHeadingCellEmptyLeft(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);
		blueinsertHeadingCellEmptyTB(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);
		blueinsertHeadingCellEmptyLeft(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);
		blueinsertHeadingCellEmptyTB(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);
		blueinsertHeadingCellEmptyLeft(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);
		blueinsertHeadingCellEmptyTB(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);
		blueinsertHeadingCellEmptyLeft(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);
		blueinsertHeadingCellEmptyTB(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);
		blueinsertHeadingCellEmptyLeft(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);
		blueinsertHeadingCellEmptyTB(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);
		blueinsertHeadingCellEmptyLeft(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);
		blueinsertHeadingCellEmptyTB(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);
		blueinsertHeadingCellEmptyLeft(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);
		blueinsertHeadingCellEmptyTB(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);
		blueinsertHeadingCellEmptyLeft(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);
		blueinsertHeadingCellEmptyTB(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);
		blueinsertHeadingCellEmptyLeft(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);
		blueinsertHeadingCellEmptyTB(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);
		blueinsertHeadingCellEmptyLeft(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);
		blueinsertHeadingCellEmptyTB(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);
		blueinsertHeadingCellEmptyLeft(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);
		blueinsertHeadingCellEmptyTB(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);
		blueinsertHeadingCellEmptyLeft(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);
		blueinsertHeadingCellEmptyTB(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);
		blueinsertHeadingCellEmptyLeft(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);
		blueinsertHeadingCellEmptyTB(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);
		blueinsertHeadingCellEmptyLeft(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);
		blueinsertHeadingCellEmptyTB(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);
		blueinsertHeadingCellEmptyLeft(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);

		blueinsertHeadingCellTop(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4);
		blueinsertHeadingCellLeft(tableHeder34, " ", Element.ALIGN_LEFT, 2, 0, urlFont4, 0);

		float[] pointColumnWidths36 = { 100f };
		PdfPTable tableHeder36 = new PdfPTable(pointColumnWidths36);
		tableHeder36.setSpacingBefore(15f);
		try {
			blueinsertHeadingCell(tableHeder36, "Payment", Element.ALIGN_CENTER, 1, 0, urlFont3);
		} catch (Exception e) {
			e.printStackTrace();
		}

		float[] pointColumnWidths35 = { 50f, 65f, 50f, 65f };
		PdfPTable tableHeder35 = new PdfPTable(pointColumnWidths35);
		tableHeder35.setSpacingAfter(20f);
		blueinsertHeadingCellRight(tableHeder35, "Total Earnings : ", Element.ALIGN_CENTER, 1, 0, urlFont4, 0);
		blueinsertHeadingCell(tableHeder35, "Rs. " + totalSalary, Element.ALIGN_RIGHT, 1, 0, urlFont4, 0, 0);

		blueinsertHeadingCellRight(tableHeder35, "Total Deduction : ", Element.ALIGN_LEFT, 1, 0, urlFont4, 0);
		blueinsertHeadingCellLeft(tableHeder35, "Rs. " + netDeduction, Element.ALIGN_RIGHT, 1, 0, urlFont4, 0);

		blueinsertHeadingCellRight(tableHeder35, "", Element.ALIGN_LEFT, 2, 0, urlFont1, 0);

		blueinsertHeadingCell(tableHeder35, "Net Payable : ", Element.ALIGN_LEFT, 1, 0, urlFont4, 0, 0);
		blueinsertHeadingCellLeft(tableHeder35, "Rs. " + netPay, Element.ALIGN_RIGHT, 1, 0, urlFont4, 0);

		blueinsertWithoutBorder(tableHeder35, "This is a computer generated Payslip and does not require signature",
				Element.ALIGN_CENTER, 4, 0, urlFont1);

		try {
			document.add(tableHeder1);
			document.add(tableHeder31);
			document.add(tableHeder32);
			document.add(tableHeder40);
			document.add(tableHeder33);
			document.add(tableHeder34);
			document.add(tableHeder36);
			document.add(tableHeder35);

			document.close();
			return url;

		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return "";
	}

	private static void blueinsertHeadingCell(PdfPTable table1, String text, int align, int colspan, int rowspan,
			Font font) {
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

	private static void blueinsertHeadingCellEmptyTB(PdfPTable table1, String text, int align, int colspan, int rowspan,
			Font font) {
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
		cell.setBorderWidthTop(0);
		cell.setBorderWidthBottom(0);
//		cell.setBorderWidthLeft(0);
		table1.addCell(cell);
	}

	private static void blueinsertHeadingCellEmptyLeft(PdfPTable table1, String text, int align, int colspan,
			int rowspan, Font font) {
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
		cell.setBorderWidthTop(0);
		cell.setBorderWidthBottom(0);
		cell.setBorderWidthLeft(0);
		table1.addCell(cell);
	}

	private static void blueinsertHeadingCell(PdfPTable table1, String text, int align, int colspan, int rowspan,
			Font font, int leftBorder, int rightBoarder) {
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
		cell.setBorderWidthLeft(leftBorder);
		cell.setBorderWidthRight(rightBoarder);
		cell.setBorderWidthTop(0);
		table1.addCell(cell);
	}

	private static void blueinsertHeadingCellTop(PdfPTable table1, String text, int align, int colspan, int rowspan,
			Font font) {
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
		cell.setBorderWidthTop(0);
		table1.addCell(cell);
	}

	private static void blueinsertHeadingCellBottom(PdfPTable table1, String text, int align, int colspan, int rowspan,
			Font font, int leftBorder, int rightBoarder) {
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
		cell.setBorderWidthLeft(leftBorder);
		cell.setBorderWidthRight(rightBoarder);
		cell.setBorderWidthTop(0);
		cell.setBorderWidthBottom(0);
		table1.addCell(cell);
	}

	private static void blueinsertHeadingCellRight(PdfPTable table1, String text, int align, int colspan, int rowspan,
			Font font, int rightBoarder) {
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
//		cell.setBorder(Rectangle.BOX);
		cell.setBorderWidthTop(0);
		cell.setBorderWidthRight(rightBoarder);
		table1.addCell(cell);
	}

	private static void blueinsertHeadingCellRightButtom(PdfPTable table1, String text, int align, int colspan,
			int rowspan, Font font, int rightBoarder) {
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
//		cell.setBorder(Rectangle.BOX);
		cell.setBorderWidthTop(0);
		cell.setBorderWidthBottom(0);
		cell.setBorderWidthRight(rightBoarder);
		table1.addCell(cell);
	}

	private static void blueinsertHeadingCellLeft(PdfPTable table1, String text, int align, int colspan, int rowspan,
			Font font, int leftBorder) {
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
//		cell.setBorder(Rectangle.BOX);
		cell.setBorderWidthTop(0);
		cell.setBorderWidthLeft(leftBorder);
		table1.addCell(cell);
	}

	private static void blueinsertHeadingCellLeftBottom(PdfPTable table1, String text, int align, int colspan,
			int rowspan, Font font, int leftBorder) {
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
		cell.setBorderWidthTop(0);
		cell.setBorderWidthBottom(0);
		cell.setBorderWidthLeft(leftBorder);
		table1.addCell(cell);
	}

	private static void blueinsertWithoutBorder(PdfPTable table1, String text, int align, int colspan, int rowspan,
			Font font) {
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

	private static void blueinsertHeadingCellRightLeft(PdfPTable table1, String text, int align, int colspan, int rowspan,
												   Font font, int rightBoarder) {
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
//		cell.setBorder(Rectangle.BOX);
		cell.setBorderWidthTop(0);
		cell.setBorderWidthLeft(0);
		cell.setBorderWidthRight(rightBoarder);
		table1.addCell(cell);
	}

}
