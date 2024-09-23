package dev.felix2000jp.microservicetemplatespring.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex) {
        var problemDetails = ex.getBody();

        logger.warn(ex.getMessage(), ex);
        return ResponseEntity.of(problemDetails).build();
    }

    @ExceptionHandler(Throwable.class)
    ResponseEntity<Object> handleThrowable(Throwable ex) {
        var problemDetails = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetails.setTitle("Internal Server Error");
        problemDetails.setDetail("An error occurred while processing the request");

        logger.error(ex.getMessage(), ex);
        return ResponseEntity.of(problemDetails).build();
    }

}
