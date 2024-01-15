package com.TestApplication.Schemas;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class CreateBookRequest {
    private String status;
    private String name;
    private String author_name;
}
