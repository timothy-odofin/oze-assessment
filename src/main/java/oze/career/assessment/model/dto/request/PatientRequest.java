package oze.career.assessment.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static oze.career.assessment.util.MessageUtil.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientRequest {
    @NotBlank(message = INVALID_FIRSTNAME)
    private String firstName;
    @NotBlank(message = INVALID_LASTNAME)
    private String lastName;
    private String middleName;
    @NotNull(message=INVALIDATE_DOB)
    private LocalDate dob;
    @NotNull(message=INVALIDATE_LAST_VISIT)
    private LocalDateTime last_visit_date;
}
