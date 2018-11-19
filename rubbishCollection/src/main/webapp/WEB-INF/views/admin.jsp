<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>后台</title>
	<%@include file="businessConsole/common/common.jsp"%>
	<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	%>
	<script type="text/javascript">
	var path = "${ct}/";
	</script>
    <style type="text/css">
       
    	.commontool{
    		width:100%;
    	    border-spacing:0px;
    	}
    	.commontool tr:hover{
    		background-color:#2983cf;
    		color:white;
    	}
    	.commontool tr td {
			cursor:pointer;
			}
    	#topArrow {  
    		width: 50px; 
    		text-align: center;
    		margin-top: 18px;
    	}
    	#topArrow img {
    		width: 14px;
    		height: 13px;
    	}
    	#arrow-down {   
    		position: absolute;
    		bottom: 0;
    		right: 50%;
		    border-left: 8px solid transparent;  
		    border-right: 8px solid transparent;  
		    border-top: 8px solid #fff; 
		    display: none;     
    	}
    	.userinfo-con{
    		width: 80px;
    		height:53px;
		    line-height: 53px;
		    color: #fff;
		    border-right: 1px solid #e8e8e8;
		    border-left: 1px solid #e8e8e8;
		    text-align: center;
		    cursor:pointer;
    	}
    
    </style>
</head>

<body class="easyui-layout" id="mainLayout">

	<!-- 顶部 -->
	<div class="topPanel" id="topPanel" data-options="region:'north'">
		<!-- logo -->
		<!-- <div class="topPanel_logo"></div>-->
		<div class="topPanel_logo_right"></div> 
		<!-- 头部主体 -->
		<div class="topPanel_main">
			<b id="arrow-down"></b>
			<div class="userInfo" id="userInfo">
				<%-- <div class="userInfo_img">
					<a href="javascript:void(0)" id="rolesMenu" class="easyui-menubutton" data-options="menu:'#roles'">
						<span id="user_name" class="userName">${LOGIN_USER.USER_NAME}</span>
					    <span id="userid" style="display:none;">${LOGIN_USER.USER_ID}</span> 
					</a>
				        
					<div id="roles" style="width:30px;">
						<table class="commontool">
							<tr style="height:20px">
								<td style="width:15px"></td>
								<td id="fixpassword">修改密码</td>
							</tr>
							<tr style="height:20px">
								<td style="width:15px"></td>
								<td id="loginout">退出</td>
							</tr>
						</table>		   
					</div> 
				</div> --%>
				<audio id="bgMusic"><source src="${ct}/images/tip.mp3" /></audio>
				<div class="userinfo-con" id="loginout" style="padding-top:19px;border-right:0;">
					<img src="${ct}/images/exit.png" alt="" title="退出" />
				</div>
				<div class="userinfo-con" id="fixpassword" style="padding-top:19px;border:0;">
					<img src="${ct}/images/modify.png" alt="" title="修改密码" />
					
				</div>
				<div class="userinfo-con" style="text-overflow: ellipsis;overflow: hidden;white-space: nowrap;" title="${LOGIN_USER.USER_NAME}">
					<span id="user_name" class="userName">${LOGIN_USER.USER_NAME}</span>
					<span id="userid" style="display:none;">${LOGIN_USER.USER_ID}</span> 
					
				</div>				
			</div>
		</div>
	</div>
	
 	<div data-options="region:'west'" style="width:220px;">
		<div id="rootMenu" ></div>    	
	</div>
	
	<div id="centerPanel" data-options="region:'center'" style="background:#FFFFFF;">
		<div id="mainTab" class="easyui-tabs"  data-options="'border':false,'fit':true">
			<div class="mainTab" title="首页" data-options="closable:false,iconCls:'icon-home'">
            	<!-- <iframe id="mainFrame" width="0" height="0" ></iframe> -->
				<div></div>
			</div>
		</div>
	</div>

	<div id="mm" class="easyui-menu" style="width:120px;">
		<table class="commontool">
			<tr style="height:20px">
				<td style="width:15px"></td>
				<td id="m-refresh">刷新</td>
			</tr>
			<tr style="height:20px">
				<td style="width:15px"></td>
				<td id="m-closeall">全部关闭</td>
			</tr>
			<tr style="height:20px">
				<td style="width:15px"></td>
				<td id="m-closeother">除此之外全部关闭</td>
			</tr>
			<tr style="height:20px">
				<td style="width:15px"></td>
				<td id="m-close">关闭</td>
			</tr>
		</table>
	</div>
	
	<div id="secondCatalog" class="hidden" data-options="region:'center'">
	</div>
        
<script>
	//console.info('${LOGIN_USER_DEFAULT_ROLE}');
	var userDefaultRole = '${LOGIN_USER_DEFAULT_ROLE.role_code}';
	var user_id='${LOGIN_USER.USER_ID}';//用户代码
	var flag = 0;//小红点标记
	$(document).ready(function(){
	    initTabBtn();// 初始化tab按钮
		clickAccordion();// 一级菜单点击事件
	    initRootMenu();// 初始化一级菜单
	    //getRedNotice(); //获取是否有新更新小红点
	});
	
	function getRedNotice(){
		$.ajax({
			url: "${ct}/platformMsg/platformMsg/getRedNotice.do",
			type : 'post', 
			error : function(){
				flag = 1;
			},
			success : function(result){
				flag = JSON.parse(result).READFLAG;
			}
		});
	}
	// 初始化tab按钮
	function initTabBtn() {
		//右击tab
		$('.tabs li').live('contextmenu',function(e){
			var subtitle = $(this).text();
			$('#tabs').tabs('select',subtitle);
			
			$('#mm').menu('show',{
				left:e.pageX,
				top:e.pageY
			});
			return false;
		});
		
		$("#m-refresh").click(function(){
	        refreshTab();
	    });
	    
	    //关闭所有
	    $("#m-closeall").click(function(){
	        $(".tabs li").each(function(i, n){
	            var title = $(n).text();
	            $('#mainTab').tabs('close',title);    
	        });
	    });
	    
	    //除当前之外关闭所有
	    $("#m-closeother").click(function(){
	        var currTab = $('#mainTab').tabs('getSelected');
	        currTitle = currTab.panel('options').title;    
	        
	        $(".tabs li").each(function(i, n){
	            var title = $(n).text();
	            
	            if(currTitle != title){
	                $('#mainTab').tabs('close',title);            
	            }
	        });
	    });
	    
	    //关闭当前
	    $("#m-close").click(function(){
	        closeCurrentTab();
	    });
	}
	
	// 初始化系统菜单-手风琴
	function initRootMenu() {
		var $rootMenu = $('#rootMenu');
		$.post('${ct}/businessConsole/menu/getUserMenu.do?roleId=' + userDefaultRole,function(data){
			 $.each(data,function(i,index){				  
				 $($rootMenu).accordion('add',{
					 title: index.text,
					 selected: false,
					 id:index.id,
					 iconCls:'icon-menu',
					 LEVEL:index.attributes.LEVEL,
                     content:'<div style="padding:10px"><ul id="'+index.id+'"></ul></div>'
				 });
			 });
		 },'json');
	}
	
	// 点击手风琴（一级菜单）触发事件
	function clickAccordion(){
		$('#rootMenu').accordion({
	    	fit:true,
	    	border:false,
	    	onSelect:function(title,index){
	    		var selectPanel = $(this).accordion('getSelected');
	    		if (selectPanel){
	    		    var options = selectPanel.panel('options');
	    		    $('#'+options.id).tree({
	    		    	url:'${ct}/businessConsole/menu/getUserMenu.do?roleId=' + userDefaultRole+'&menuPId='+options.id+'&LEVEL='+(parseInt(options.LEVEL)+1),
	    		    	onBeforeExpand:function(node){
							$(this).tree('options').url = '${ct}/businessConsole/menu/getUserMenu.do?roleId='+userDefaultRole+'&menuPId='+node.id+'&LEVEL='+(parseInt(node.attributes.LEVEL)+1);
	    		    	},
	    		    	onExpand:function(){
	    		    		//调接口判断是否加小红点
    		    			$.ajax({
								url: "${ct}/platformMsg/platformMsg/getRedNotice.do",
								type : 'post', 
								error : function(){
								},
								success : function(result){
									$(".redFlag").hide();
									flag = JSON.parse(result).READFLAG;
									if(flag!=0){
			    		    			$("[node-id='2017110215425273572']").append("<span style='color:red;font-weight:800;' class = 'redFlag'>·</span>");
			    		    		}
			    		    		flag = 0 ;
								}
							});  		    		
	    		    	},
	    		    	onClick:function(node){
	    		    		//$(".redFlag").hide();
	    		    		var url = node.attributes.url;
	    		    		if(url){
	    		    			if(url.substring(0,4) =='http'){
	    		    				
	    		    			}else{
	    		    				url = '${ct}/' + url;
	    		    			}
	    		    			var loginFlag=$("#loginFlag").val();
	    		    			if(loginFlag=='1'){
	    		    				$.messager.alert('提示信息', '账户已经有人登录', 'error', function () {
											location.href = "http://localhost:8080/light";
       								 });
	    		    			}else{
	    		    				//console.log(node);
	    		    				var arr = node.attributes.url.split('?');
	    		    				if(arr.length > 1){
	    		    					var flags = arr[1].split('=');
	    		    					if('openFlag' == flags[0]){
	    		    						window.open('${ct}/' + arr[0], node.name);
	    		    					}
	    		    				} else {
	    		    					openTab(node);
	    		    				}
	    		    				
	    		    			}									    		    			
	    		    		} else {
	    		    			// 点击菜单，展开或折叠节点
	    		    			$(this).tree(node.state === 'closed' ? 'expand' : 'collapse', node.target);  
	    		    		}
	    		    	}
	    		    });
	    		} 
	    	}
	    });
	}
	
	function openTab(node) {
		var title = node.text;// tab标题
		var url = '${ct}/' + node.attributes.url + "?menu_code=" + node.id;// 页面url
		
		if($('#mainTab').tabs('exists',title)){
			//$('#mainTab').tabs('select',title);
			 $('#mainTab').tabs('close',title);
		}
		$('#mainTab').tabs('add',{
	    	title:title,
	    	content:createTabContent(url),
	    	closable:true,
	    	icon:node.iconCls
	    });
	}

	function createTabContent(url){
		return '<iframe style="width:100%;height:99%;" scrolling="auto" frameborder="0" src="'+ url+'"></iframe>';
	}
	
	//新开tab
	function openNewTab(title,url,icon){
		if($('#mainTab').tabs('exists',title)){
			$('#mainTab').tabs('close',title);   
		}
		$('#mainTab').tabs('add',{
	    	title:title,
	    	content:createTabContent(url),
	    	closable:true,
	    	icon:icon
	    });
	}
	
	//关闭当前tab，打开并刷新父tab
	function closeTab(title){
		closeCurrentTab();//首先关闭当前标签
		$("#mainTab ul.tabs li").each(function(){
			var cur_title=$(this).find("span:eq(0)").text();
			if(title==cur_title){
				$(this).addClass("tabs-selected");
				refreshTab();//刷新父页面
			}
			else{
				$(this).removeClass("tabs-selected");
			}
		});
	}
	
	/* 修改密码 */
	/* function updatePwd(){
	  var dataDialog =  parent.ns.modalDialog({
           title:'修改密码',
           width:400,
           height:180,
           resizable:true,
           url:'${ct}/businessConsole/user/userModifyPwd.do?yhDm=${LOGIN_USER.USER_CODE}',
           buttons:[{
               text:'保存',
               iconCls:'icon-save',
               handler:function(){
                   dataDialog.find('iframe').get(0).contentWindow.submitUser(dataDialog,null,parent.$,'${LOGIN_USER.USER_CODE}');
               }
           }]

       });
	} */
	
	// 关闭当前tab
    function closeCurrentTab(){
   		var currTab = $('#mainTab').tabs('getSelected');
        var  currTitle = currTab.panel('options').title;
        var s = currTab.panel('options').closable;
       	if(s){    	        
        	$('#mainTab').tabs('close', currTitle);
       	}
    }	
    
    // 刷新选中tab
    function refreshTab(){
   		var currTab = $('#mainTab').tabs('getSelected');
    	var url = $(currTab.panel('options').content).attr('src');
        if(url){
	        /* 重新设置该标签 */
	        $('#mainTab').tabs('update',{
	            tab:currTab,
	            options:{
	            	content:createTabContent(url)
	            }
	        });
        }
    } 
    
    // 关闭指定tab
    function closeTabBy(title) {
    	var isExists = $('#mainTab').tabs('exists', title);
    	
    	if(isExists) {
    		$('#mainTab').tabs('close', title);
    	};
    }
    
	//用户的退出登录
	$("#loginout").click(function(){ 
		$.messager.confirm("提示","是否确认退出系统",function(r){
			if(r){
				$.ajax({
					url: "${ct}/businessConsole/login/getloginOut.do",
					type : 'post', 
					error : function(){
						window.location.href='${ct}';
					},
					success : function(){
						window.location.href='${ct}';
					}
				});
			}
		});   
	});
	
	//修改密码
	$("#fixpassword").click(function(){ 
		var bmGrid;
		var USER_CODE=$("#userid").html();
	   	var userDialog =  parent.ns.modalDialog({
          	title : '修改密码',
          	width : 450,
          	height : 300,
          	resizable : true,
          	url : url+'/businessConsole/user/showChangePassword.do?USER_CODE='+USER_CODE,
          	buttons : [{
              	text : '保存',
              	iconCls : 'icon-save',
             	handler : function(){
              	    userDialog.find('iframe').get(0).contentWindow.save(userDialog,bmGrid,parent.$,USER_CODE);
              	}
          	}]
      	});
	});  
	

</script>
</body>
</html>