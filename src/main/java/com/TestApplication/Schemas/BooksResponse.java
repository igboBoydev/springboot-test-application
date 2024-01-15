package com.TestApplication.Schemas;


import lombok.*;

@Data
@Builder
@ToString
public class BooksResponse {
    private String status;
    private String name;
    private String author_name;
    private String uuid;
}
