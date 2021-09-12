package com.query.entity;

import lombok.Data;

import java.util.List;

@Data
public class Query {

    private String table;
    private String operation;
    private String columnFields;
    private String columnValues;
    private List<JoinInfo> join;
    private List<Filter> filters;
}
