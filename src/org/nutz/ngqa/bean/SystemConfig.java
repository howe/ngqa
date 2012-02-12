package org.nutz.ngqa.bean;

import org.nutz.mongo.annotation.Co;
import org.nutz.mongo.annotation.CoId;
import org.nutz.mongo.annotation.CoIdType;

import lombok.Data;

@Data
@Co
public class SystemConfig {

	@CoId(CoIdType.AUTO_INC)
	private int id;
}
