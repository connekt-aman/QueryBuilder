package com.query.service;

import com.query.dao.IQueryDao;
import com.query.dao.QueryDaoImpl;
import com.query.entity.Filter;
import com.query.entity.JoinInfo;
import com.query.entity.Query;
import com.query.exception.WorkflowException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QueryService implements IQueryService {

    private final IQueryDao iQueryDao;

    public QueryService() {
        iQueryDao = new QueryDaoImpl();
    }

    @Override
    public List<String> createQuery(JSONObject queryObject) {
        JSONArray array = (JSONArray) queryObject.get("query");
        List<String> sql = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            Query query = transform((JSONObject) array.get(i));
            try {
                validateQuery(query);
                sql.add(iQueryDao.createQuery(query));
            }catch (WorkflowException e){
                sql.add(e.getMessage());
            }

        }
        return sql;
    }

    private void validateQuery(Query query) throws WorkflowException {
        if(query.getOperation() == null || query.getOperation().isEmpty())
            throw new WorkflowException("Error, Provide operation to be perform.");
        if(query.getJoin().size() == 0 && (query.getTable() == null || query.getTable().isEmpty()))
            throw new WorkflowException("Error, Provide table name for the query.");

    }

    private Query transform(JSONObject obj) {
        Query query = new Query();
        query.setColumnFields((String) obj.get("columnFields"));
        query.setFilters(getFilters((JSONArray) obj.get("filters")));
        query.setOperation((String) obj.get("operation"));
        query.setTable((String) obj.get("table"));
        query.setJoin(getJoins((JSONArray) obj.get("join")));
        return query;
    }

    private List<JoinInfo> getJoins(JSONArray joinObject) {
        List<JoinInfo> joinInfoList = new ArrayList<>();
        if (joinObject != null) {
            for (int i = 0; i < joinObject.size(); i++) {
                JSONObject obj = (JSONObject) joinObject.get(i);
                JoinInfo joinInfo = new JoinInfo();
                joinInfo.setJoinColumn(obj.get("tableColumn").toString());
                joinInfo.setJoinTable(obj.get("joinTable").toString());
                joinInfo.setJoinType(obj.get("joinType").toString());
                joinInfo.setJoinWith(obj.get("joinWith").toString());
                joinInfo.setTableColumn(obj.get("joinColumn").toString());
                joinInfoList.add(joinInfo);
            }
        }
        return joinInfoList;
    }

    private List<Filter> getFilters(JSONArray filters) {
        List<Filter> filterList = new ArrayList<>();
        if (filters != null) {
            for (int i = 0; i < filters.size(); i++) {
                Filter filter = new Filter();
                JSONObject obj = (JSONObject) filters.get(i);
                filter.setColumnField((String) obj.get("columnField"));
                filter.setColumnValues((String) obj.get("columnValues"));
                filter.setOperator((String) obj.get("operator"));
                filter.setFilterOperation((String) obj.get("filterOperation"));
                if(filter.getColumnValues() == null)
                    filter.setSubquery( transform(obj));
                filterList.add(filter);
            }
        }
        return filterList;
    }
}
