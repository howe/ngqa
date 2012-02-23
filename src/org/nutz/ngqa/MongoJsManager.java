package org.nutz.ngqa;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.Streams;
import org.nutz.resource.NutResource;
import org.nutz.resource.Scans;

public class MongoJsManager {

	public static Map<String, String> load(String path) {
		Map<String, String> map = new HashMap<String, String>();
		List<NutResource> list = Scans.me().scan(path, ".+\\.js");
		for (NutResource nutResource : list) {
			try {
				map.put(Files.getMajorName(nutResource.getName()), Streams.readAndClose(nutResource.getReader()));
			} catch (IOException e) {
				throw Lang.wrapThrow(e);
			}
		}
		return map;
	}
}
