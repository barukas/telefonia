package com.telcel.sgc.services.war.dao.jdbc.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.text.CaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.telcel.sgc.services.war.dao.jdbc.AbstractDBGenericBPMDAO;
import com.telcel.sgc.services.war.dao.jdbc.JdbcQueryBPMDAO;

@Repository("genericQueryBPMDAO")
public class GenericQueryBPMDao extends AbstractDBGenericBPMDAO implements JdbcQueryBPMDAO{
	
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
	
	@Autowired
	GenericQueryDAO genericQueryDAO;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.telcel.sgc.services.war.dao.jdbc.JdbcQueryDAO#findList(java.lang.String,
	 * java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> findList(String queryName, Map<String, Object> parms) {
		String query = genericQueryDAO.getQueryByName(queryName);
		return this.processCamelCase(
				this.getNamedParmJdbcTmplt().queryForList(
						query,
						this.processCapitalizeUnderscore(parms)
				)
			);
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
