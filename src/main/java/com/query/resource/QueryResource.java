package com.query.resource;

import com.query.exception.WorkflowException;
import com.query.service.IQueryService;
import com.query.service.QueryService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.management.Query;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QueryResource {

    public IQueryService queryService;

    public QueryResource() {
        this.queryService = new QueryService();
    }

    public List<String> createQuery(String path) {
        List<String> response = new ArrayList<>();
        try {
            JSONObject query = readJsonFile(path);
            response = queryService.createQuery(query);
        } catch (WorkflowException e) {
            response.add("Exception: " + e.getMessage());
        }
        return response;
    }

    private JSONObject readJsonFile(String path) throws WorkflowException {
        try {
            JSONParser parser = new JSONParser();
            JSONObject parseData = (JSONObject) parser.parse(new FileReader(path));
            return parseData;
        } catch (IOException e) {
            System.out.println("ERROR! -> File not found at path: " + path);
            throw new WorkflowException("Error! " + e.getMessage());
        } catch (ParseException e) {
            System.out.println("ERROR! -> JSON Parsing exception: " + path);
            throw new WorkflowException("Error! " + e.getMessage());
        }
    }
}
