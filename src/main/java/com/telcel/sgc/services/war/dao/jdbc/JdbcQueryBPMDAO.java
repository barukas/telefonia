/**
 * 
 */
package com.telcel.sgc.services.war.dao.jdbc;

import java.util.List;
import java.util.Map;

/**
 * JdbcQueryDAO
 *
 */
public interface JdbcQueryBPMDAO {
	
	List<Map<String,Object>> findList(String queryName, Map<String,Object> parms);

}
