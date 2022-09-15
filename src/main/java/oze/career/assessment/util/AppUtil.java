package oze.career.assessment.util;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.Period;

public class AppUtil {
    public static String TYPE = "text/csv";
    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static String evaluate(String data){
        return data==null?"":data.trim();
    }
    public static Integer getYearDifference(LocalDate start, LocalDate end){

        return start.until(end).getYears();
    }
    public static Integer getAge(LocalDate birthday){
        Period p = Period.between(birthday, LocalDate.now());
        return p.getYears();
    }
}
