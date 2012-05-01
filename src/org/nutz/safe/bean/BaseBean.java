package org.nutz.safe.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.EL;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.Readonly;

public abstract class BaseBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5184112478135565698L;
	@Column
	@Prev(els={@EL("$me.now()")})
	@Readonly
	private Timestamp createTime;
	
	public String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	public Timestamp now() {
		return new Timestamp(System.currentTimeMillis());
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
}
