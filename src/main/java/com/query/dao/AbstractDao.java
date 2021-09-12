package com.query.dao;

import com.query.entity.Filter;
import com.query.entity.JoinInfo;
import com.query.entity.Operator;
import com.query.entity.Query;
import com.query.exception.WorkflowException;

import java.util.List;

public abstract class AbstractDao {

    protected String getCondition(List<Filter> filters) throws WorkflowException {
        StringBuffer cond = new StringBuffer("");
        if (filters.size() > 0) {
            cond.append("where ");
            boolean flag = false;
            for (Filter filter : filters) {
                if (flag)
                    cond.append(" " + filter.getFilterOperation() + " ");
                flag = true;
                cond.append(filter.getColumnField() + " ");
                String value = filter.getColumnValues();
                if (filter.getColumnValues() == null) {
                    Query subquery = filter.getSubquery();
                    value = getField(subquery.getColumnFields(), subquery.getOperation(), subquery.getTable());
                    value += getJoinInfo(subquery.getJoin()) + " " + getCondition(subquery.getFilters());
                }
                cond.append(getOperatorAndValue(filter.getOperator().trim(), value));
            }
        }
        return cond.toString();
    }

    protected String getOperatorAndValue(String operator, String values) throws WorkflowException {
        Operator op = Operator.valueOf(operator.replace(" ","_").toUpperCase());
        String data = "";
        switch (op) {

            case IN:
            case NOT_IN:
                data = op.getAction() + " (" + values + ")";
                break;
            case LIKE:
            case GREATER_THAN:
            case EQUAL:
            case NOT_EQUAL:
            case LESS_THAN:
            case LESS_THAN_EQUAL:
            case GREATER_THAN_EQUAL:
                data = op.getAction() + " " + values;
                break;
            default:
                throw new WorkflowException("Invalid operation provided: " + op);
        }
        return data;
    }

    protected String getField(String columnFields, String operation, String table) throws WorkflowException {
        String field = "";
        switch (operation.toLowerCase()) {
            case "select":
                if (columnFields == null || columnFields.isEmpty())
                    columnFields = "* ";
                return operation + " " + columnFields + " from " + (table != null ? table : "");
            case "insert":
                if (columnFields == null || columnFields.isEmpty())
                    throw new WorkflowException("Error, Provide columns to insert.");
                return operation + " into (" + columnFields + ") values (" + ")";
        }
        throw new WorkflowException("Error, Invalid query operation provided!");
    }

    public abstract String getJoinInfo(List<JoinInfo> joins);
}
