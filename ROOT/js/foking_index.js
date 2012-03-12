	$(function(){
				
				$("#login").hover(function(){
					$("#login-nav").show(); 
				},function(){ 
					setTimeout(function(){ 
						if (!$("#login-nav").hasClass('rmb'))
							$("#login-nav").hide(); 
						},1000); 
					}); 
					
				$("#login-nav").hover(function(){
					$(this).addClass("rmb").show();
				},function(){
					$(this).removeClass("rmb").hide();
				});
				
				$(".expandlink1").live('click',function(){
					$(this).hide();
					$("#s"+this.id.slice(1)).hide();
					$("#d"+this.id.slice(1)).animate({
						opacity:'show'
					});
					$("#ea"+this.id.slice(1)).show();
				});
				$(".expandlink2").live('click',function(){
					$(this).hide();
					$("#d"+this.id.slice(2)).hide();
					$("#s"+this.id.slice(2)).animate({
						opacity:'show'
					});
					$("#e"+this.id.slice(2)).show();
				});
				
				$.getJSON("http://www.nutz.cn/me.jsonp?jsoncallback=?", function(result){
					if(typeof(result.nickName)!="undefined") {
						$("#login").hide();
						$("#nav-username").text(result.nickName);
						$("#logout").show();
						$("#li-user").show();
					}
				});
				
				$.getJSON("http://www.nutz.cn/tags.jsonp?jsoncallback=?", function(result){
					renderTags(result);
				});
				
				//初始化获取第1页数据
				$.getJSON("http://www.nutz.cn/question/query/list/1.jsonp?jsoncallback=?", function(result){
					renderList(result);
					pagination(result);
				});
				
				//分页
				function pagination(result){
					$("#Pagination").pagination(result.count, {
						items_per_page : result.pageSize,
						//...两侧显示数目
						num_edge_entries: 1,
						//连续分页主体部分显示的分页条目数
						num_display_entries: 5,
						//回调方法
						callback: pageselectCallback
					});
				}
				
				function pageselectCallback(page_id,jq){
						//清空原先页面的数据
						$(".excerpt").empty();
						//获取指定页面数据
						$.getJSON("http://www.nutz.cn/question/query/list/"+ (page_id+1) +".jsonp?jsoncallback=?", function(pageResult){
							//渲染页面
							renderList(pageResult);	
						});
				}
				
				function renderTags(result){
					var _temp = '';
					$.each(result,function(i,k){
						_temp += '<a title="' + k + '个话题" href="http://www.nutz.cn/tag/' + i.escapeHTML() + '">' + '&#12288;' + i.escapeHTML() + '(' + k +') </a>';						
					})
					$(".sub-tags").append(_temp);
				}
				
				function renderList(result){
					var _temp = '';
					$.each(result.data,function(i,k){
						_temp += '<li>'
							  + '<div class="excerpt-num"><img src="'+'http://gravatar.com/avatar/'+k.user.email+'.png?s=48&d=http://www.nutzam.com/wiki/img/logo.png"></img></div>'
							  +  '<h2 class="excerpt-tit"><a title="' + k.title + '" href="http://www.nutz.cn/question/' + k.id + '">' + k.title + '</a></h2>'
							  +   '<p class="excerpt-desc" id="s_' + k.id + '">' + k.content + '</p>'
							  +  '<p class="excerpt-desc" id="d_' +  k.id + '" style="display:none">' + k.content + '</p>'
							  +  '<div class="excerpt-tag-index"><a rel="tag" href=http://www.nutz.cn/tag/'+k.tags[0]+'>'+k.tags[0]+'</a></div>'
							  +  '<span class="excerpt-time">' + k.user.nickName + ' Question At ' + k.createdAt + '</span>'
							  +  '<div id="e_' + k.id +   '" class="expandlink1">全文↓</div>'
							  +  '<div id="ea_' + k.id +   '" class="expandlink2" style="display:none">收起全文↑</div>'
							  +  '</li>';
							  
					});

					$(".excerpt").append(_temp);
					
					if(!$.browser.msie)
						$(".excerpt-num").preloader();
				}
	});