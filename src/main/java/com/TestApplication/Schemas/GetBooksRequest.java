package com.TestApplication.Schemas;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class GetBooksRequest {

    private Integer page;
    private Integer size;

}
