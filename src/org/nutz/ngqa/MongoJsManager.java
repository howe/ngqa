package org.nutz.ngqa;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bson.types.Code;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.Streams;
import org.nutz.lang.Strings;
import org.nutz.resource.NutResource;
import org.nutz.resource.Scans;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;

public class MongoJsManager {

	public static Map<String, String> load(DB db, String path) {
		Map<String, String> map = new HashMap<String, String>();
		List<NutResource> list = Scans.me().scan(path, ".+\\.js");
		for (NutResource nutResource : list) {
			try {
				map.put(Files.getMajorName(nutResource.getName()), Streams.readAndClose(nutResource.getReader()));
			} catch (IOException e) {
				throw Lang.wrapThrow(e);
			}
		}
		Map<String, String> jses = map;
		if (!jses.isEmpty()) {
			for (final Entry<String, String> entry : jses.entrySet()) {
				if (Strings.isBlank(entry.getValue()))
					continue;
				db.getCollection("system.js").update(new BasicDBObject("_id", entry.getKey()), 
									new BasicDBObject("$set", new BasicDBObject("value", new Code(entry.getValue()))),
									true, false);
			}
		}
		return map;
	}
}
