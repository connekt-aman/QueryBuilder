package com.query.dao;

public class QueryBuilder {

    private String field;
    private String condition;
    private String join;

    public QueryBuilder(QueryFormatter formatter) {
        this.field = formatter.field;
        this.join = formatter.join;
        this.condition = formatter.condition;
    }

    @Override
    public String toString() {
        return " " + this.field + this.join + " " + this.condition + ";";
    }

    public static class QueryFormatter {

        private String field;
        private String condition;
        private String join;

        public QueryFormatter field(String field) {
            this.field = field;
            return this;
        }

        public QueryFormatter condition(String condition) {
            this.condition = condition;
            return this;
        }

        public QueryFormatter join(String join) {
            this.join = join;
            return this;
        }


        public QueryBuilder build() {
            QueryBuilder query = new QueryBuilder(this);
            return query;
        }
    }
}
