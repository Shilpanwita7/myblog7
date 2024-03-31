package com.myblog7.exception;


import com.myblog7.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

//extends ResponseEntityExceptionHandler to make it custom exception
@ControllerAdvice //telling springboot if any exception occurs in the project take that exception and give it to this class
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler a ResourceNotFound.class pass kra hyeche RNF akta specific exception class tai omn type ar exception bade
//    baki sb exception k @ExceptionHandler global exception a niye jabe.

//    exception occur holei age ai class a ashbe bcz @ControllerAdvice then exception ta jdi RNF hoy tbe seti RNF class jabe
// bolei akhane (ResourceNotFound exception) as a parameter pass kra hyeche, whenever we handle an exception we have use
//    WebRequest bcz it has lot of information exception msg, url etc

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFound(ResourceNotFound exception, WebRequest webRequest){
        ErrorDetails errorDetails= new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }


//    if any generic exception occurs all will come to this method
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFound(Exception exception, WebRequest webRequest){
        ErrorDetails errorDetails= new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
}
