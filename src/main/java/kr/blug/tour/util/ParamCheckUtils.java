package kr.blug.tour.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ParamCheckUtils {
	
	public static boolean isValidDate(String dateStr) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		
		try {
			LocalDate parsedDate = LocalDate.parse(dateStr, formatter);
			return true;
		} catch (DateTimeParseException e) {
			System.out.println("********************** date parse error"+ dateStr + " " + e.toString());
			
			return false;
			
		}
	}

}
