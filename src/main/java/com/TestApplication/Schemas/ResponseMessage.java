package com.TestApplication.Schemas;


import lombok.*;

@Data
@Builder
@ToString
public class ResponseMessage {
    private int statusCode;
    private String message;
}
