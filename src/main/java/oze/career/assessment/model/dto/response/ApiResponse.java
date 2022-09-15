package oze.career.assessment.model.dto.response;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 *
 * @author JIDEX
 * @param <T>
 */
@Data
@Builder
@NoArgsConstructor
public class ApiResponse<T> {
    private String message;
    private HttpStatus code;
    private T data;

    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public ApiResponse(String message, HttpStatus code, T data) {
        this.message = message;
        this.data = data;
        this.code= code;
    }
}
