package com.query.entity;

import lombok.Data;

@Data
public class JoinInfo {
    private String joinTable;
    private String tableColumn;
    private String joinWith;
    private String joinType;
    private String joinColumn;
}
