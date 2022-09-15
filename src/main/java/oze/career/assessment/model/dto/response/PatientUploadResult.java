package oze.career.assessment.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientUploadResult {
    private String firstName;
    private String lastName;
    private String middleName;
    private String lastVisitDate;
    private Integer age;
    private List<String> errors;
}
