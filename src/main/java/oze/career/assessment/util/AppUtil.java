package oze.career.assessment.util;

import java.time.LocalDate;

public class AppUtil {
    public static String evaluate(String data){
        return data==null?"":data.trim();
    }
    public static Integer getYearDifference(LocalDate start, LocalDate end){
        return start.until(end).getYears();
    }
}
