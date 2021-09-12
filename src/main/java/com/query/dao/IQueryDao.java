package com.query.dao;

import com.query.entity.Query;
import com.query.exception.WorkflowException;

public interface IQueryDao {

    String createQuery(Query query) throws WorkflowException;

}
