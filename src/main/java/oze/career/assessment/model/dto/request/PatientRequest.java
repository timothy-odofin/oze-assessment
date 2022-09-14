package oze.career.assessment.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientRequest {
    private String firstName;
    private String lastName;
    private String middleName;
    private LocalDate dob;
    private LocalDateTime last_visit_date;
}
