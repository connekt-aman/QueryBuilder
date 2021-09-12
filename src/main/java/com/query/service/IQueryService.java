package com.query.service;

import com.query.exception.WorkflowException;
import org.json.simple.JSONObject;

import java.util.List;

public interface IQueryService {
    List<String> createQuery(JSONObject queryObject) throws WorkflowException;
}
