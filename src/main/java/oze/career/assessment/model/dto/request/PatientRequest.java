package oze.career.assessment.model.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "Object for collection single patient information")
public class PatientRequest {
    @NotBlank(message = INVALID_FIRSTNAME)
    @ApiModelProperty(allowEmptyValue = false, value = "The firstname of the patient", dataType = "String", required = true)
    private String firstName;
    @NotBlank(message = INVALID_LASTNAME)
    @ApiModelProperty(allowEmptyValue = false, value = "The lastname of the patient", dataType = "String", required = true)
    private String lastName;
    @ApiModelProperty(allowEmptyValue = true, value = "The middle name of the patient, if any", dataType = "String", required = false)
    private String middleName;
    @NotNull(message= INVALID_AGE_VALUE_IS_REQUIRED)
    @Min(value=1, message = INVALID_AGE_VALUE_IS_REQUIRED)
    @ApiModelProperty(allowEmptyValue = false, value = "The age of the patient", dataType = "Integer", required = true)
    private Integer age;
    @NotNull(message=INVALIDATE_LAST_VISIT)
    @ApiModelProperty(allowEmptyValue = false, value = "The last visitation date of the patient", dataType = "Date", required = true)
    private LocalDate lastVisitDate;
    @NotBlank(message = INVALID_FIRSTNAME)
    @ApiModelProperty(allowEmptyValue = false, value = "The unique identification number of the staff creating the patient", dataType = "String", required = true)
    private UUID staffId;
}
