/*
 * Copyright (C) 2016 Yong Zhu.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */
package com.github.drinkjava2.jsqlbox;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbutils.ResultSetHandler;

import com.github.drinkjava2.jdbpro.DbPro;
import com.github.drinkjava2.jdbpro.DbRuntimeException;
import com.github.drinkjava2.jdialects.Dialect;
import com.github.drinkjava2.jdialects.model.TableModel;
import com.github.drinkjava2.jdialects.utils.StrUtils;
import com.github.drinkjava2.jtransactions.ConnectionManager;

/**
 * @author Yong Zhu
 * @since 1.0.0
 */
public class SqlBoxContext extends DbPro {
	public static final SqlBoxLogger LOGGER = SqlBoxLogger.getLog(SqlBoxContext.class);
	public static String sqlBoxClassSuffix = "SqlBox";// NOSONAR
	public static SqlBoxContext defaultContext = null;// NOSONAR
	private Dialect dialect;
	private TableModel[] metaTableModels;

	public SqlBoxContext() {
		super();
	}

	public SqlBoxContext(DataSource ds) {
		super(ds);
		this.dialect = Dialect.guessDialect(ds);
		refreshMetaData();
	}

	public SqlBoxContext(DataSource ds, ConnectionManager cm) {
		super(ds, cm);
		dialect = Dialect.guessDialect(ds);
		refreshMetaData();
	}

	public SqlBoxContext(DataSource ds, Dialect dialect) {
		super(ds);
		this.dialect = dialect;
		refreshMetaData();
	}

	public SqlBoxContext(DataSource ds, Dialect dialect, ConnectionManager cm) {
		super(ds, cm);
		this.dialect = dialect;
		refreshMetaData();
	}

	public void refreshMetaData() {
		metaTableModels = SqlBoxContextUtils.metaDataToModels(this, dialect);
	}

	public TableModel getMetaTableModel(String tableName) {
		if (StrUtils.isEmpty(tableName))
			return null;
		for (TableModel tableModel : metaTableModels)
			if (tableName.equalsIgnoreCase(tableModel.getTableName()))
				return tableModel;
		return null;
	}

	/**
	 * create a box for class
	 */
	public SqlBox box(Class<?> clazz) {
		return SqlBoxUtils.createSqlBox(this, clazz);
	}

	/**
	 * Equal to dialect's paginate method
	 */
	public String paginate(int pageNumber, int pageSize, String sql) {
		return dialect.paginate(pageNumber, pageSize, sql);
	}

	// =========== Query methods to support double star '**' =====

	@Override
	public <T> T nQuery(String sql, ResultSetHandler<T> rsh, Object... params) {
		return super.nQuery(SqlBoxContextUtils.explainDoubleStarSql(this, sql), rsh, params);
	}

	@Override
	public <T> T iQuery(ResultSetHandler<T> rsh, String... inlineSQL) {
		return super.iQuery(rsh, SqlBoxContextUtils.explainDoubleStarSql(this, inlineSQL));
	}

	@Override
	public <T> T tQuery(ResultSetHandler<T> rsh, String... templateSQL) {
		return super.tQuery(rsh, SqlBoxContextUtils.explainDoubleStarSql(this, templateSQL));
	}

	// =============CRUD methods=====
	public void insert(Object entity) {
		SqlBoxContextUtils.insert(entity, SqlBoxUtils.findAndBindSqlBox(this, entity));
	}

	public int update(Object entity) {
		return SqlBoxContextUtils.update(entity, SqlBoxUtils.findAndBindSqlBox(this, entity));
	}

	public void delete(Object entity) {
		SqlBoxContextUtils.delete(entity, SqlBoxUtils.findAndBindSqlBox(this, entity));
	}

	public <T> T load(Class<?> entityClass, Object pkey) {
		return SqlBoxContextUtils.load(this, entityClass, pkey);
	}

	public void unbind(Object entity) {
		SqlBoxUtils.unbind(entity);
	}
	// getter & setter =======

	public Dialect getDialect() {
		return dialect;
	}

	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}

	public static SqlBoxContext getDefaultContext() {
		return defaultContext;
	}

	public static void setDefaultContext(SqlBoxContext defaultContext) {
		SqlBoxContext.defaultContext = defaultContext;
	}

	public TableModel[] getMetaTableModels() {
		return metaTableModels;
	}

	public void setMetaTableModels(TableModel[] metaTableModels) {
		this.metaTableModels = metaTableModels;
	}

}