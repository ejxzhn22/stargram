package com.sujin.stargram.handler;

import com.sujin.stargram.dto.CMRespDto;
import com.sujin.stargram.handler.ex.CustomApiException;
import com.sujin.stargram.handler.ex.CustomException;
import com.sujin.stargram.handler.ex.CustomValidationApiException;
import com.sujin.stargram.handler.ex.CustomValidationException;
import com.sujin.stargram.util.Script;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@ControllerAdvice // 모든 예외를 낚아챈다
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomValidationException.class)
    public String validationException(CustomValidationException e) {

        // CMRespDto , Script 비교
        // 1. 클라이언트에게 응답할때는 Script 좋음
        // 2. Ajax - CMRespDto  => 개발자가 응답받을 때

        if(e.getErrorMap() == null){
            return Script.back(e.getMessage());
        }else{
            return Script.back(e.getErrorMap().toString());
        }
    }

    @ExceptionHandler(CustomValidationApiException.class)
    public ResponseEntity<?> validationApiException(CustomValidationApiException e) {

        return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), e.getErrorMap()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> customApiException(CustomApiException e) {

        return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), null),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public String CustomException(CustomException e) {
        return Script.back(e.getMessage());
    }
}
