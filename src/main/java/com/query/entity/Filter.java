package com.query.entity;

import lombok.Data;

import java.util.List;

@Data
public class Filter {

    private String operator;
    private String columnField;
    private String columnValues;
    private String filterOperation;
    private Query subquery;
}
