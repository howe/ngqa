package org.nutz.ngqa.bean;

import java.util.Date;

public interface Freshable {
	
	public Object getId();

	public void setUpdatedAt(Date date);
	
	public Date getUpdatedAt();
}
