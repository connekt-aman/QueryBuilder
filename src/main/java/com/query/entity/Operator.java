package com.query.entity;

public enum Operator {

    IN("in"),
    NOT_IN("not in"),
    LIKE("like"),
    EQUAL("="),
    NOT_EQUAL("<>"),
    LESS_THAN("<"),
    LESS_THAN_EQUAL("<="),
    GREATER_THAN(">"),
    GREATER_THAN_EQUAL(">="),
    ;

    private String action;

    public String getAction()
    {
        return this.action;
    }
    Operator(String action) {
        this.action = action;
    }
}
