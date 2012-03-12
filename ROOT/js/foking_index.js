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
				
				$.getJSON("http://www.nutz.cn/question/query/list.jsonp?jsoncallback=?", function(result){
					renderList(result);
				});
				
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
					
					$(".excerpt-num").preloader();
				}
	});