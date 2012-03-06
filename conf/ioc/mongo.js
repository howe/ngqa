var ioc = {
	/*
	 * 数据库连接器
	 */
	connector : {
		type : 'org.nutz.mongo.MongoConnector',
		events : { depose : 'close' },
		args : [ "127.0.0.1", 27017 ]
	// ~ End bean
	},
	/*
	 * 抽象的服务
	 */
	mongoService : { // 仅仅提供构造函数
	args : [ { refer : 'connector' }, "ngqa" ]
	// ~ End bean
	},
	/*
	 * 分布式MongoSession
	 */
	sessionManager : {
		type : "org.nutz.mongo.session.MongoSessionManager",
		args : [ { refer : 'connector' }, "ngqa" ]
	}
	
};