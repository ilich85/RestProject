package com.ilich.handler;

import com.ilich.exception.*;
import com.ilich.model.FullAdvertInfo;
import com.ilich.model.ResultInfo;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

import static com.ilich.util.PropertyStrings.INCORRECT_AUTH_DATA;
import static com.ilich.util.PropertyStrings.INCORRECT_DATA;
import static org.springframework.http.HttpStatus.valueOf;


@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ResultInfo> handler(EmptyResultDataAccessException ex) {
        ResultInfo response = new ResultInfo(ex.getMessage());
        return new ResponseEntity<>(response, valueOf(500));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ResultInfo> handler(DataAccessException ex) {
        ResultInfo response = new ResultInfo(ex.getMessage());
        return new ResponseEntity<>(response, valueOf(500));
    }

    @ExceptionHandler(AdvertsNotFoundException.class)
    public List<FullAdvertInfo> handler(AdvertsNotFoundException ex) {
        return new ArrayList<>();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResultInfo> handler(AccessDeniedException ex) {
        ResultInfo response = new ResultInfo(ex.getMessage());
        return new ResponseEntity<>(response, valueOf(500));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResultInfo> handle() {
        ResultInfo response = new ResultInfo(INCORRECT_DATA);
        return new ResponseEntity<>(response, valueOf(400));
    }

    @ExceptionHandler(UserExistsException.class)
    public ResultInfo handler(UserExistsException ex) {
        return new ResultInfo(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResultInfo handler() {
        return new ResultInfo(INCORRECT_AUTH_DATA);
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResultInfo handler(WrongPasswordException ex) {
        return new ResultInfo(ex.getMessage());
    }
}
