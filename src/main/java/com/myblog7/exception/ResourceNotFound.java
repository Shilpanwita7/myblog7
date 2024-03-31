package com.myblog7.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//this object will created when post is not found or record is not found
@ResponseStatus(HttpStatus.NOT_FOUND)//404
public class ResourceNotFound extends RuntimeException {

//    Supply the msg to it's constructor, this super keyword here will automatically display the msg in postman response
   public ResourceNotFound(String msg){
       super(msg);
   }
}
