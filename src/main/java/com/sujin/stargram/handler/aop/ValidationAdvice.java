package com.sujin.stargram.handler.aop;

import com.sujin.stargram.handler.ex.CustomValidationApiException;
import com.sujin.stargram.handler.ex.CustomValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class ValidationAdvice {

    @Around("execution(* com.sujin.stargram.controller.api.*Controller.*(..))")
    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        System.out.println("api 컨트롤러=========================");
        Object[] args = proceedingJoinPoint.getArgs();

        for(Object arg: args){
            if(arg instanceof BindingResult){ // 파라미터에 binding result가 잇으면 얘가 실행.
                BindingResult bindingResult = (BindingResult) arg;

                if(bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();

                    for(FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }
                    throw new CustomValidationApiException("유효성 검사 실패함", errorMap);
                }
            }
        }
        //proceedingJoinPoint => profile 함수의 모든 곳에 접근할 수 있는 변수
        //profile 함수보다 먼저 실행

        return proceedingJoinPoint.proceed(); // profile 함수가 실행됨.
    }

    @Around("execution(* com.sujin.stargram.controller.*Controller.*(..))")
    public  Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        System.out.println("컨트롤러===============================");
        Object[] args = proceedingJoinPoint.getArgs();

        for(Object arg: args){
            if(arg instanceof BindingResult){
                BindingResult bindingResult = (BindingResult) arg;
                if (bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();

                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }

                    throw new CustomValidationException("유효성 검사 실패함",errorMap);
                }
            }
        }

        return proceedingJoinPoint.proceed();
    }
}
