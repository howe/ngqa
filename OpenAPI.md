基于OpenAPI思想的服务器端API
===========================

登录/登出API
---------

	* /user/login/_openid   普通用户登录
		* 路径参数_openid  -- 所支持的OpenID提供商简称
		* 将跳转到OpenID提供商的登录页面
		
	* /user/login/root      Root用户登录
		* 参数key(必选) -- 一个在应用初始启动是随机生成的字符串
		
	* /user/login/anonymous 匿名登录
		* 无参数
		
	* /user/login/app       应用程序登录
		* 参数appId(必选) -- 应用程序的识别号
		
	* /user/logout          登出,销毁当前会话
	
Question管理核心API
------------------

	* /ask					创建新Question
		* 参数title(必选)  -- Question的主标题,长度必须是5~100个字符
		* 参数content      -- Question的详情,长度不能大于2000个字符
		* 参数format       -- content的格式,默认值是txt
		
	* /question/_id			Question详情
		* 路径参数_id -- Question的Id
		
	* /question/query        通用查询入口
		* 只支持Json格式的参数输入,支持复杂的Mongo查询条件及分页参数
		
	* /question/_id/answer/add  为特定问题添加一个答案
		* 路径参数_id  -- Question的Id
		* 参数content -- 答案的内容,长度不能大于2000个字符
		* 参数format -- content的格式
		
	* /question/_id/tag/add/_tag 为特定问题添加一个Tag
		* 路径参数_id  -- Question的Id
		* 路径参数_tag  -- Tag的名字
		
	* /question/_id/tag/remove/_tag 为特定问题移除一个Tag
		* 路径参数_id  -- Question的Id
		* 路径参数_tag  -- Tag的名字
		
	* /question/_id/watch          关注一个问题
		* 路径参数_id  -- Question的Id
		
	* /question/_id/unwatch         不再关注一个问题
		* 路径参数_id  -- Question的Id
		
	* /question/_id/update   更新一个问题
		* 路径参数_id  -- Question的Id
		* 参数title    -- Question的title
		* 参数content  -- Qestion的content
		* 参数format   -- content的format
	
	* /tags 列出所有的Tag
		* 无参数
		
	* /question/search      全文搜索
		* 参数key(必选) -- Question所包含的字符

用户信息API
----------
	
	* /me                  获取当前会话的用户信息
		* 无参数
		
	* /me/update           更新用户信息
		* 参数nickName      更新昵称
		* 参数email			更新Email地址
		
辅助查询API
----------
	
	* /question/query/list/_page
		* 路径参数_page,可选,默认为1
		
	