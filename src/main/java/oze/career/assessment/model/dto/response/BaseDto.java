package oze.career.assessment.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BaseDto {
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime dateCreated;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime lastModified;

}
