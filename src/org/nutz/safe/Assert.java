package org.nutz.safe;

import org.nutz.lang.Lang;


public class Assert {
	
	public static void mustNull(Object obj) {
		mustNull(obj, "Must null!");
	}
	
	public static void mustNull(Object obj, String msg) {
		if (obj == null)
			throw new RuntimeException(msg);
	}

	public static void notNull(Object obj) {
		notNull(obj, "Must not NULL!");
	}
	
	public static void notNull(Object obj, String msg) {
		if (obj == null)
			throw new RuntimeException(msg);
	}
	
	public static void len(Object obj, int min, int max) {
		len(obj, min, max, String.format("Must Between %d and %d", min, max));
	}
	
	public static void len(Object obj, int min, int max, String msg) {
		int len = Lang.length(obj);
		if (len < min || (max > 0 && len > max))
			throw new RuntimeException(msg);
	}

	public static void equal(String want, String face) {
		equal(want, face, String.format("Want %s but %s", want, face));
	}
	public static void equal(String want, String face, String msg) {
		if (want == null || face == null || !want.equals(face))
			throw new RuntimeException(msg);
	}
	
	public static void equal(Object want, Object face) {
		equal(want, face, String.format("Want %s but %s", want, face));
	}
	public static void equal(Object want, Object face, String msg) {
		if (want == null || face == null || !want.equals(face))
			throw new RuntimeException(msg);
	}
}
