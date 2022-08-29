package com.telcel.sgc.services.war.dao.jdbc.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.text.CaseUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.telcel.sgc.services.war.dao.jdbc.AbstractDBGenericDAO;
import com.telcel.sgc.services.war.dao.jdbc.JdbcQueryDAO;
import com.telcel.sgc.services.war.service.implementation.TableroRowMapper;

/**
 * Clase de ejecucion de consultas genericas.
 */
@Repository("genericQueryDAO")
public class GenericQueryDAO extends AbstractDBGenericDAO implements JdbcQueryDAO {
	/**
	 * DELIMITER.
	 */
	private static final char[] DELIMITER = new char[] { '_' };
	/**
	 * "([a-z])([A-Z]+)"
	 */
	private static final String CAMEL_2_UPPERCASE_REGEXP = "([a-z])([A-Z]+)";
	/**
	 * "$1_$2".
	 */
	private static final String CAMEL_2_UPPERCASE_REPLACE = "$1_$2";
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.telcel.sgc.services.war.dao.jdbc.JdbcQueryDAO#findUnique(java.lang.
	 * String, java.util.Map)
	 */
	@Override
	public Map<String, Object> findUnique(String queryName, Map<String, Object> parms) {
		return this.processCamelCase(this.getNamedParmJdbcTmplt().queryForMap(this.getQueryByName(queryName),
				this.processCapitalizeUnderscore(parms)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.telcel.sgc.services.war.dao.jdbc.JdbcQueryDAO#findList(java.lang.String,
	 * java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> findList(String queryName, Map<String, Object> parms) {
		return this.processCamelCase(this.getNamedParmJdbcTmplt().queryForList(this.getQueryByName(queryName),
				this.processCapitalizeUnderscore(parms)));
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.telcel.sgc.services.war.dao.jdbc.JdbcQueryDAO#findListQuery(java.lang.String,
	 * java.util.Map)
	 */
	@Override
	public List<JSONObject> findListQuery(String query, Map<String, Object> parms) {
		return this.getNamedParmJdbcTmplt().query(query,
				this.processCapitalizeUnderscore(parms),
				new TableroRowMapper());
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.telcel.sgc.services.war.dao.jdbc.JdbcQueryDAO#count(java.lang.String,
	 * java.util.Map)
	 */
	@Override
	public Map<String, Object> count(String query, Map<String, Object> parms) {
		return this.getNamedParmJdbcTmplt().queryForMap(query,
				this.processCapitalizeUnderscore(parms));
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.telcel.sgc.services.war.dao.jdbc.JdbcQueryDAO#getQueryByName(java.lang.String)
	 */
	@Override
	public String getQueryString(String queryName) {
		return this.getQueryByName(queryName);
	}

	/**
	 * @param list
	 * @return
	 */
	private List<Map<String, Object>> processCamelCase(List<Map<String, Object>> list) {
		for (int i = 0; i < list.size(); i++) {
			list.set(i, this.processCamelCase(list.get(i)));
		}
		return list;
	}

	/**
	 * @param map
	 * @return
	 */
	private Map<String, Object> processCamelCase(Map<String, Object> map) {
		final Map<String, Object> toReturn = new HashMap<>();
		final Set<Entry<String, Object>> entries = map.entrySet();
		for (Entry<String, Object> entry : entries) {
			toReturn.put(CaseUtils.toCamelCase(entry.getKey(), false, DELIMITER), entry.getValue());
		}
		return toReturn;
	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	private Map<String, Object> processCapitalizeUnderscore(Map<String, Object> map) {
		final Map<String, Object> toReturn = new HashMap<>();
		final Set<Entry<String, Object>> entries = map.entrySet();
		for (Entry<String, Object> entry : entries) {
			toReturn.put(entry.getKey().replaceAll(CAMEL_2_UPPERCASE_REGEXP, CAMEL_2_UPPERCASE_REPLACE).toUpperCase(),
					entry.getValue());
		}
		return toReturn;
	}
}
