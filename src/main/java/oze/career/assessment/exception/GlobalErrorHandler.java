package oze.career.assessment.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import oze.career.assessment.model.dto.response.ApiResponse;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static oze.career.assessment.util.AppCode.*;
import static oze.career.assessment.util.MessageUtil.*;
import static org.springframework.http.HttpStatus.*;
import static oze.career.assessment.util.MessageUtil.SERVER_ERROR;


@ControllerAdvice
@Slf4j
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiResponse> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.ok(new ApiResponse<>(FAILED,EXPECTATION_FAILED.value(),FILE_TOO_LARGE));
    }
    @Override
    protected ResponseEntity handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        return  ResponseEntity.ok(new ApiResponse<>(FAILED, BAD_REQUEST.value(), String.join(",",errors)));
    }

    @ExceptionHandler(UnknownHostException.class)
    public ResponseEntity handleUnknownHostException(UnknownHostException exception, WebRequest webRequest) {
        String requestUrl = webRequest.getContextPath();
        log.warn("Unknown host for {} access through endpoint {}", exception.getMessage(),requestUrl);
        return ResponseEntity.ok(new ApiResponse<>(FAILED, NOT_FOUND.value(), SERVER_ERROR));
    }


    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity handleRecordNotFoundExceptions(RecordNotFoundException exception, WebRequest webRequest) {
        String requestUrl = webRequest.getContextPath();
        log.warn("Record not found for {} access through endpoint {} ", exception.getMessage(),requestUrl);
        return ResponseEntity.ok(new ApiResponse<>(FAILED, NOT_FOUND.value(), exception.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity handleBadRequestExceptions(BadRequestException exception, WebRequest webRequest) {
        String requestUrl = webRequest.getContextPath();
        log.warn("Bad request exception {} access through endpoint {}", exception.getMessage(),requestUrl);
        return ResponseEntity.ok(new ApiResponse<>(FAILED, BAD_REQUEST.value(), exception.getMessage()));

    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity illegalException(RecordNotFoundException exception, WebRequest webRequest) {
        String requestUrl = webRequest.getContextPath();
        log.warn("Record not found for {} access through {}", exception.getMessage(),requestUrl);
        return ResponseEntity.ok(new ApiResponse<>(FAILED, NOT_FOUND.value(), exception.getMessage()));

    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        String error =
                ex.getName() + " should be of type " + ex.getRequiredType().getName();

        return  ResponseEntity.ok(new ApiResponse<>(FAILED, BAD_REQUEST.value(), error)
        );
    }
    @ExceptionHandler(value = ReadingCsvException.class)
    public ResponseEntity handleReadingCsvException(ReadingCsvException exception) {
        exception.printStackTrace();
        log.warn("An error occur  {}", exception.fillInStackTrace().getMessage());
        return ResponseEntity.ok(new ApiResponse<>(FAILED, INTERNAL_SERVER_ERROR.value(), exception.getMessage()));
    }

    @ExceptionHandler(value = IOException.class)
    public ResponseEntity handleIOException(IOException exception) {
        exception.printStackTrace();
        log.warn("An error occur  {}", exception.fillInStackTrace().getMessage());
        return ResponseEntity.ok(new ApiResponse<>(FAILED, INTERNAL_SERVER_ERROR.value(), exception.getMessage()));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity handlerGlobalError(Exception exception) {
        exception.printStackTrace();
        log.warn("An error occur  {}", exception.fillInStackTrace().getMessage());
        return ResponseEntity.ok(new ApiResponse<>(FAILED, INTERNAL_SERVER_ERROR.value(), SERVER_ERROR));
    }


}
