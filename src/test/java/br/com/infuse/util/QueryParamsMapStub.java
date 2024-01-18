package br.com.infuse.util;

import spark.QueryParamsMap;

import java.util.Map;

public class QueryParamsMapStub extends QueryParamsMap {

    protected QueryParamsMapStub(){
        super();
    }

    protected QueryParamsMapStub(final Map<String, String[]> params){
        super(params);
    }

    public static QueryParamsMapStub getInstance(){
        return new QueryParamsMapStub();
    }

    public static QueryParamsMapStub getInstance(final Map<String, String[]> params){
        return new QueryParamsMapStub(params);
    }

}
