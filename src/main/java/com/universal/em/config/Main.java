package com.universal.em.config;

import com.itextpdf.text.Image;
import com.universal.em.utils.AesEncryption;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Base64;

public class Main {
    private static final DecimalFormat decforTwo = new DecimalFormat("#.00");

    public static void main(String[] args) throws IOException {
//        		String str = "this is";
//		int flag = 0;
//		char[] chars = str.toCharArray();
//		for (int i = 0; i < chars.length; i++) {
//			flag = 0;
//			for (int j = 0; j < chars.length; j++) {
//				char a = chars[i];
//				char b = chars[j];
//				if (a == ' ') {
//					flag = 1;
//					break;
//				}
//				if (a == b && i != j) {
//					System.out.println(a + "  " + b);
//					flag = 1;
//					break;
//				}
//			}
//			if (flag == 0)
//				chars[i] = '$';
//		}
//		System.out.println(String.valueOf(chars));
//
//
//		Double num =3d;
//
//		String s = String.format("%.2f", num);
//
//		System.out.printf(s);
//
//Double tds = Double.valueOf("1");
//		System.out.println(	Double.parseDouble(decforTwo.format(tds)));
//
//		double doubleTdsPay = tds == null || tds.equals("") ? Double.parseDouble("0"):Double.parseDouble(decforTwo.format(11));

        try {
            File file = new File("/Users/jitenderyadav/Documents/slip/geteapy.png");
            file = new File("/Users/jitenderyadav/IdeaProjects/employee_management/src/main/resources/static/image/company_logo/getepay.png");
            System.out.println(file.getPath());
            System.out.println(file.getAbsolutePath());
            Image image = Image.getInstance(file.getPath());
            if (file.exists()) {
                System.out.println("is present");
            } else {
                System.out.println("not");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("next");

//        String img = "kbekwkLrBWkKwI5Y4GtWlCUtjTovMTGwACKzZHeNBQ85HfwLIsGHhLUkJ_aA4H+gXwdI0AD+QJuCi2cKMEbSGg==";
//        img = AesEncryption.decrypt(img.replaceAll(" ","+"));
//        System.out.println(img);
//        byte[] fileContent = FileUtils.readFileToByteArray(new File(img));
//        System.out.println(Base64.getEncoder().encodeToString(fileContent));

    }

}
