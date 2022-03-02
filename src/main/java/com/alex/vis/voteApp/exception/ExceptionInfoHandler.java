package com.alex.vis.voteApp.exception;

import com.alex.vis.voteApp.validation.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionInfoHandler {
    private static final Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorInfo> handleError(HttpServletRequest req, NotFoundException e) {
        return logAndGetErrorInfo(req, e, false, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IllegalRequestDataException.class, DataIntegrityViolationException.class, MethodArgumentNotValidException.class})
    @ResponseBody
    public ResponseEntity<ErrorInfo> conflict(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, true,  HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorInfo> handleError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, true, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorInfo> logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logStackTrace, HttpStatus status) {
        Throwable rootCause = ValidationUtil.logAndGetRootCause(log, req, e, logStackTrace, status);
        return ResponseEntity.status(status)
                .body(new ErrorInfo(req.getRequestURL(), rootCause)
                );
    }
}
