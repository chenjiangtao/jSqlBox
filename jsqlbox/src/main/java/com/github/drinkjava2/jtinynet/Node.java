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
package com.github.drinkjava2.jtinynet;

import java.util.List;

/**
 * Node is a POJO represents a Node in entity net, Node allow have many parents,
 * but Node do not allow have child node, so "Many to one" is the only
 * relationship allowed in EntityNet system, exact like Relational Database, so
 * it's easy to translate a Relational Database into an EntityNet.
 * 
 * @author Yong Zhu (Yong9981@gmail.com)
 * @since 1.0.0
 */
public class Node {
	/**
	 * a unique String ID generated by computer, usually format is columnValue for
	 * singly key, or col1Value_(Seperator)_col2Value_(Seperator)_col3Value... for
	 * compound foreign key columns
	 */
	String id;

	/** The entity instance */
	Object entity;

	Object entityClass;

	/**
	 * Parents of node is a 3 dimensions array: <br/>
	 * parents[0]: node's fkeyColumnName <br/>
	 * parents[1]: node's fkeyColumnValue <br/>
	 * parents[0]: parent class <br/>
	 * for example: <br/>
	 * teacher_firstName_lastname, sam_zhu , Teacher.class <br/>
	 * teacher_firstName_lastname, sam_zhu , SuperTeacher.class <br/>
	 * teacher_firstName_lastname, tom_li ,  SuperTeacher.class <br/>
	 * 
	 * parents will be filled when Node be created
	 */
	private List<Object[]> parents;

	public Node(String id, Object entity) {
		this.id = id;
		this.entity = entity;
		if (entity != null)
			this.entityClass = entity.getClass();
	}

	public Node(String id, Object entity, List<Object[]> parents) {
		this(id, entity);
		this.parents = parents;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}

	public Object getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Object entityClass) {
		this.entityClass = entityClass;
	}

	public List<Object[]> getParents() {
		return parents;
	}

	public void setParents(List<Object[]> parents) {
		this.parents = parents;
	}

 
}
