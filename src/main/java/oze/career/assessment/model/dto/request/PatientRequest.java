package oze.career.assessment.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

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
    @NotNull(message= INVALID_AGE_VALUE_IS_REQUIRED)
    @Min(value=1, message = INVALID_AGE_VALUE_IS_REQUIRED)
    private Integer age;
    @NotNull(message=INVALIDATE_LAST_VISIT)
    private LocalDate lastVisitDate;
    @NotBlank(message = INVALID_FIRSTNAME)
    private UUID staffId;
}
