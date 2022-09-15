package oze.career.assessment.model.dto.request;

import lombok.Data;

@Data
public class PatientFetchRequest {
  private  Integer minAge;
    private  Integer page;
    private  Integer size;
    private String staffId;
}
