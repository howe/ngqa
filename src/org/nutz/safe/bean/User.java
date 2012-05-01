package org.nutz.safe.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.EL;
import org.nutz.dao.entity.annotation.Index;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.TableIndexes;
import org.nutz.json.JsonField;

@Table("safe_user")
@TableIndexes({@Index(fields="name", name="username", unique=true)})
public class User extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7714758834158819986L;

	@Name
	@Prev(els={@EL("$me.uuid()")})
	private String id;

	@Column
	private String name;

	@JsonField(ignore=true)
	@Column
	private String passwd;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	
}
