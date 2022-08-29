package com.telcel.sgc.services.war.dao.jdbc;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

/**
 * JdbcQueryDAO
 */
public interface JdbcQueryDAO {
	/**
	 * 
	 * @param queryName
	 * @param parms
	 * @return
	 */
	Map<String,Object> findUnique(String queryName, Map<String,Object> parms);
	
	/**
	 * 
	 * @param queryName
	 * @param parms
	 * @return
	 */
	List<Map<String,Object>> findList(String queryName, Map<String,Object> parms);
	
	/**
	 * 
	 * @param query
	 * @param parms
	 * @return
	 */
	List<JSONObject> findListQuery(String query, Map<String,Object> parms);

	/**
	 * 
	 * @param queryName
	 * @param parms
	 * @return
	 */
	Map<String,Object> count(String queryName, Map<String,Object> parms);
	
	/**
	 * 
	 * @param queryName
	 * @return
	 */
	String getQueryString(String queryName);
}
