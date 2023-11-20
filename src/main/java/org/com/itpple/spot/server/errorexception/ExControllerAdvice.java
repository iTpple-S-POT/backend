package org.com.itpple.spot.server.errorexception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResultMessage exHandle(Exception e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResultMessage("EX", "내부 오류");
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResultMessage illegalExHandle(IllegalArgumentException e) {
        log.error("[exceptionHandle] bad", e);
        return new ErrorResultMessage("BAD", e.getMessage());
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResultMessage> userExHandle(UserException e) {
        log.error("[exceptionHandle] user-ex", e);
        ErrorResultMessage errorResult = new ErrorResultMessage("USER-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }


}
