package com.query.exception;

import lombok.Data;

@Data
public class WorkflowException extends Exception{

    private String message;
    public WorkflowException(String msg){
        this.message = msg;
    }
}
