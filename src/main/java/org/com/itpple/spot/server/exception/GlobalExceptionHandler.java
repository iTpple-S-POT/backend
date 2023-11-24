package org.com.itpple.spot.server.exception;

import org.com.itpple.spot.server.exception.code.GlobalErrorCode;
import org.com.itpple.spot.server.exception.dto.ErrorResponse;
import org.com.itpple.spot.server.exception.dto.FieldError;
import org.com.itpple.spot.server.exception.dto.ValidationErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        return ResponseEntity
            .status(ex.getHttpStatus())
            .body(new ErrorResponse(ex.getCode(), ex.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
        WebRequest request
    ) {
        List<FieldError> errors = ex.getBindingResult().getFieldErrors().stream()
            .map(error -> new FieldError(
                error.getField(),
                error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                error.getDefaultMessage()
            )).toList();

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ValidationErrorResponse(
                GlobalErrorCode.METHOD_ARGUMENT_NOT_VALID.getCode(),
                GlobalErrorCode.METHOD_ARGUMENT_NOT_VALID.getMessage(),
                errors
            ));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationErrorResponse> handleConstraintViolationException(
        ConstraintViolationException ex) {
        List<FieldError> errors = ex.getConstraintViolations().stream()
            .map(violation -> new FieldError(
                getFieldName(violation),
                violation.getInvalidValue().toString(),
                violation.getMessage()
            )).toList();

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ValidationErrorResponse(
                GlobalErrorCode.CONSTRAINT_VIOLATION.getCode(),
                GlobalErrorCode.CONSTRAINT_VIOLATION.getMessage(),
                errors
            ));
    }

    protected ResponseEntity<Object> handleExceptionInternal(
        Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status,
        WebRequest request
    ) {
        GlobalErrorCode globalErrorCode = GlobalErrorCode.from(ex.getClass());
        return ResponseEntity
            .status(status)
            .body(new ErrorResponse(
                globalErrorCode.getCode(),
                globalErrorCode.getMessage()
            ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorResponse(
                GlobalErrorCode.UNHANDLED.getCode(),
                GlobalErrorCode.UNHANDLED.getMessage()
            ));
    }

    private static String getFieldName(ConstraintViolation<?> violation) {
        String propertyPath = violation.getPropertyPath().toString();
        int dotIdx = propertyPath.lastIndexOf(".");
        return propertyPath.substring(dotIdx + 1);
    }
}
