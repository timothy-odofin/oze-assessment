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
public class PatientResponseData {
    private Long totalItems;
    private List<PatientResponse> patients;
    private Integer totalPages;
    private Integer currentPage;
}
