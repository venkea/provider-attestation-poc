package com.hcsc.provider.attestation.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DroolsUtils {
	public static boolean ValidateDate(Date date) {
        if (date != null) {
            SimpleDateFormat dfrmt = new SimpleDateFormat("MM/dd/yyyy");
            dfrmt.setLenient(false);
            try {
            	System.out.println("DATE STRING ----- " + date.toString());
				dfrmt.parse(date.toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
        }
        return true;
    }
     public static boolean ValidateDigits(String strId) {
         String regex = "[0-9]+";
         Pattern p = Pattern.compile(regex);
         Matcher m = p.matcher(strId);
         return m.matches();
     }

}
