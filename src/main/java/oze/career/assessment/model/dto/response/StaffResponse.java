package oze.career.assessment.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaffResponse extends BaseDto{
    private String uuid;
    private String name;
    private String photoImage;
}
