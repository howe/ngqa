var ioc = {
		dao : {
                type : "org.nutz.dao.impl.NutDao",
                args : [{refer:"dataSource"}]
        },
        dataSource : {
                type : "org.apache.tomcat.jdbc.pool.DataSource",
                events : {
                	depose : "close"
                },
                fields : {
                		driverClassName : {java : '$conf.get("db_driver")'},
                        url : {java : '$conf.get("db_url")'},
                        username : {java : '$conf.get("db_user")'},
                        password : {java : '$conf.get("db_passwd")'}
                }
        },
		lazyDao : {
                type : "org.nutz.dao.impl.ext.LazyNutDao",
                args : [{refer:"dataSource"}]
        }
};