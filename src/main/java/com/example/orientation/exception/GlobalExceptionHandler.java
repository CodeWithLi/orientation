package com.example.orientation.exception;

import com.example.orientation.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends BaseException {

    //实现全局异常处理
    @ExceptionHandler
    public Result<String> exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public  Result<String>  handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        String errorMessage = "参数验证失败: " + Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
        return Result.error(errorMessage);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public  Result<String>  httpMessageNotReadableExceptionExceptions(HttpMessageNotReadableException ex) {
        String errorMessage = "参数验证失败: " + ex.getMessage();
        return Result.error(errorMessage);
    }
}
