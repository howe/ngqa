package org.nutz.ngqa.bean;

import lombok.Data;

import org.nutz.mongo.annotation.Co;
import org.nutz.mongo.annotation.CoId;
import org.nutz.mongo.annotation.CoIdType;

@Data
@Co
public class SystemConfig {

	@CoId(CoIdType.AUTO_INC)
	private int id;
}
