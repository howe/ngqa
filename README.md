### 广告位招租 ###
详情见[http://howe.im](http://howe.im)

PS:  
此项目用到了 nutz-web nutz-mongo nutz-web  
还将会用到 nutz-social ╮(╯▽╰)╭  
本项目主要 贡献者 @wendal (兽) @ywjno (温泉)  
本人只是个监工而已

### 编译说明 ###

* ####文件夹说明
  
	* src 源文件夹
	* conf 配置文件存放文件夹,需要加入build path作为源文件夹
	* sdk SDK实现,尚未完成
	* test 测试用例
	* build 以普通Java项目启动时所需要的jar(jetty及其jsp支持)
	* ROOT 网站根文件夹,创建JavaEE项目时,请指向这个文件夹,而非默认的WebContent文件夹
	
* ####建立工程
  
	* #####JavaWeb项目
		* 建立一个JavaEE工程,网站根目录指向ROOT,而非WebContent,并且不要自动生成web.xml
		* 发布到tomcat并启动之
		
	* #####普通Java项目
		* 建立工程,把conf文件夹也设置为源文件夹, classes文件输出路径是ROOT/WEB-INF/classes
		* 将build文件夹中的全部jar,加入Build Path
		* 将ROOT/WEB-INF/lib下面的全部jar,除log4j之外, 加入Build Path
		* 使用org.nutz.ngqa.ServerLauncher类作为main类来启动项目
	
  * ####依赖
  
		* mongodb 2.0以上,下载地址 http://www.mongodb.org/downloads 项目启动前,请确保mongod已经运行
		* 其他所有必须的jar,都已经存放在ROOT/WEB-INF/lib下,无需其他jar
