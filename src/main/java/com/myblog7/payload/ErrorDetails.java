package com.myblog7.payload;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor//constructor based injection or field contructor
public class ErrorDetails {
    private Date timestamp;
    private  String message;
    private String details;
}
