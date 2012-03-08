package org.nutz.ngqa;

public class NgqaRuntimeException extends RuntimeException {

	public NgqaRuntimeException() {
		super();
	}

	public NgqaRuntimeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public NgqaRuntimeException(String arg0) {
		super(arg0);
	}

	public NgqaRuntimeException(Throwable arg0) {
		super(arg0);
	}

	public static class JustFailViewException extends RuntimeException {
		private Object data;
		public JustFailViewException(Object data) {
			this.data = data;
		}
		public Object getData() {
			return data;
		}
	}
}
