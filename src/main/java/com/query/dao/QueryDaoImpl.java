package com.query.dao;

import com.query.entity.JoinInfo;
import com.query.entity.Query;
import com.query.exception.WorkflowException;

import java.util.List;

public class QueryDaoImpl extends AbstractDao implements IQueryDao {

    @Override
    public String createQuery(Query query) throws WorkflowException {
        QueryBuilder builder = new QueryBuilder.QueryFormatter()
                .field(getField(query.getColumnFields(), query.getOperation(), query.getTable()))
                .join(getJoinInfo(query.getJoin()))
                .condition(getCondition(query.getFilters()))
                .build();
        return builder.toString();
    }

    @Override
    public String getJoinInfo(List<JoinInfo> joins) {
        StringBuffer info = new StringBuffer("");
        for (JoinInfo j : joins) {
            info.append(" " + j.getJoinTable() + " " + j.getJoinType() + " ");
            info.append(j.getJoinWith() + " ");
            info.append(" ON " + j.getTableColumn() + "." + j.getJoinColumn());
            info.append(" = " + j.getJoinWith() + "." + j.getJoinColumn());
        }
        return info.toString();
    }
}
