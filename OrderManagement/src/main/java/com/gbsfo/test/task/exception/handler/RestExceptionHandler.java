package com.gbsfo.test.task.exception.handler;

import com.gbsfo.test.task.exception.EntityUpdatingException;
import com.gbsfo.test.task.exception.IncorrectOrderStatus;
import com.gbsfo.test.task.exception.JwtTokenException;
import com.gbsfo.test.task.exception.ObjectAccessDeniedException;
import com.gbsfo.test.task.exception.PaymentException;
import com.gbsfo.test.task.exception.QueryNotFoundException;
import com.gbsfo.test.task.exception.StatusCannotBeUpdatedException;
import com.gbsfo.test.task.exception.UserNotFoundException;
import com.gbsfo.test.task.payload.response.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String ERROR_DISPLAY_PATTERN = "%s %s";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errors = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(e -> errors.add(String.format(ERROR_DISPLAY_PATTERN, e.getField(), e.getDefaultMessage())));
        ex.getBindingResult().getGlobalErrors().forEach(e -> errors.add(String.format(ERROR_DISPLAY_PATTERN, e.getObjectName(), e.getDefaultMessage())));

        ApiResponse apiResponse = new ApiResponse(HttpStatus.BAD_REQUEST, errors.toString());
        return handleExceptionInternal(
                ex, apiResponse, headers, apiResponse.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return buildResponseEntity(new ApiResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage()));
    }

    @ExceptionHandler({QueryNotFoundException.class, UserNotFoundException.class, UsernameNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(RuntimeException ex) {
        return buildResponseEntity(new ApiResponse(HttpStatus.NOT_FOUND, ex.getLocalizedMessage()));
    }

    @ExceptionHandler({IncorrectOrderStatus.class, JwtTokenException.class, ExpiredJwtException.class})
    public ResponseEntity<Object> handleBadRequest(RuntimeException ex) {
        return buildResponseEntity(new ApiResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage()));
    }

    @ExceptionHandler(ObjectAccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDenied(ObjectAccessDeniedException ex) {
        return buildResponseEntity(new ApiResponse(HttpStatus.FORBIDDEN, ex.getLocalizedMessage()));
    }

    @ExceptionHandler({StatusCannotBeUpdatedException.class, PaymentException.class, EntityUpdatingException.class})
    public ResponseEntity<Object> handleUnprocessableEntity(RuntimeException ex) {
        return buildResponseEntity(new ApiResponse(HttpStatus.UNPROCESSABLE_ENTITY, ex.getLocalizedMessage()));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiResponse apiResponse) {
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }
}
