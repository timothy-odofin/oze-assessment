package oze.career.assessment.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

import static oze.career.assessment.util.MessageUtil.INVALID_FIRSTNAME;
import static oze.career.assessment.util.MessageUtil.INVALID_LASTNAME;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaffRequest {
    @NotBlank(message = INVALID_FIRSTNAME)
    private String firstName;
    @NotBlank(message = INVALID_LASTNAME)
    private String lastName;
    private String middleName;
}
