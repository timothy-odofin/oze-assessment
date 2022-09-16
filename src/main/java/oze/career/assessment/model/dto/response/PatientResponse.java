package oze.career.assessment.model.dto.response;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponse extends BaseDto{
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate lastVisitDate;
    private String patientCode;
    private String name;
    private Integer age;
    private StaffHelper createdBy;
}
