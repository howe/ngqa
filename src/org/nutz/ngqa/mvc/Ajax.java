package org.nutz.ngqa.mvc;

public abstract class Ajax {

	public static AjaxReturn ok() {
		AjaxReturn re = new AjaxReturn();
		re.ok = true;
		return re;
	}

	public static AjaxReturn fail() {
		AjaxReturn re = new AjaxReturn();
		re.ok = false;
		return re;
	}

	public static AjaxReturn expired() {
		AjaxReturn re = new AjaxReturn();
		re.ok = false;
		re.msg = "ajax.expired";
		return re;
	}

}
