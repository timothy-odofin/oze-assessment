package oze.career.assessment.util;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

public class AppUtil {
    public static String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static String evaluate(String data) {
        return data == null ? "" : data.trim();
    }

    public static Integer getYearDifference(LocalDate start, LocalDate end) {

        return start.until(end).getYears();
    }

    public static Integer getAge(LocalDate birthday) {
        Period p = Period.between(birthday, LocalDate.now());
        return p.getYears();
    }

    public static String convertDate(LocalDateTime localDate) {
        return localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static ResponseEntity<Resource> getResourceBody(InputStreamResource file, String filename) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + getFileName(filename) + ".csv")
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }

    private static String getFileName(String originalName) {
        if (originalName == null || originalName.isBlank())
            return "patient";
        else if (originalName.contains(" "))
            return String.join("_", originalName.split(""));
        else
            return originalName;
    }

    public static Optional<Date> validateDate(String dateString) {
        try {
            return Optional.of(new SimpleDateFormat("yyyy-MM-dd").parse(dateString));
        } catch (ParseException e) {
            return Optional.empty();
        }

    }
}
