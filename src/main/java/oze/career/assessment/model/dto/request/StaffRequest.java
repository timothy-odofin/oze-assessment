package oze.career.assessment.model.dto.request;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(allowEmptyValue = false, value = "The firstname of the staff", dataType = "String", required = true)
    private String firstName;
    @NotBlank(message = INVALID_LASTNAME)
    @ApiModelProperty(allowEmptyValue = false, value = "The lastname of the staff", dataType = "String", required = true)
    private String lastName;
    @ApiModelProperty(allowEmptyValue = true, value = "The middle name of the staff, if any", dataType = "String", required = false)
    private String middleName;
}
