<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
	<title>菜单授权</title>
	<%@include file="../common/common.jsp"%>
</head>
<body style="padding-top: 5px">
	<!-- <div class="easyui-panel" data-options="fit:true,border:false">
	    <table id="allMenuBtn"></table>  
	</div> -->
	<div class="easyui-layout" data-options="fit:true,border:true">
        <div data-options="region:'west',split:true,title:'菜单'"  style="width:220px;">
            <ul id="allmenuTree"></ul> 
        </div>
        <div data-options="region:'center',title:'菜单按钮'">
        	<ul id="allMenuBtn"></ul> 
        </div>
    </div>
</body>

<script type="text/javascript">
var allmenuTree;
var allMenuBtn;
var buttons = [];// 按钮code
var select_menu_code = [];// 重新设置的菜单code 用于删除角色菜单按钮

$(function(){
	getAllMenuBtn();// 加载菜单按钮
	loadMenuBtn();// 初始化菜单按钮
});

// 加载全部菜单按钮
function getAllMenuBtn() {
	allmenuTree = $('#allmenuTree').tree({
        url : '${ct}/businessConsole/menu/getAllMenu.do',
	    checkbox : true,
        onLoadSuccess : function(node, data){
			getGrantMenus();// 获取已授权的菜单，并勾选
        },
        onBeforeSelect : function(node){
        	var checked = allMenuBtn.tree('getChecked');// 获取选中按钮
        	if(checked){
				var indeterminate = allMenuBtn.tree('getChecked', 'indeterminate');// 获取不确定的按钮
				checked = $.merge(checked, indeterminate);
				
				for(var i = 0; i < checked.length; i++) {
					var flag = false;
					for(var j = 0; j < buttons.length; j++) {
						if(buttons[j].id == checked[i].id) {
							flag = true;
						}
					}
					
					if(!flag) {
						buttons.push(checked[i]);
					}
				}
        	}
        },
        onSelect : function(node){
        	select_menu_code.push(node.id);
        	// 加载菜单按钮
        	loadMenuBtn(node);
		}
	});
}

// 获取已授权的菜单，并勾选
function getGrantMenus(){
	$.post('${ct}/businessConsole/role/getRoleAssginMenu.do', {roleId:'${param.roleId}'}, function(result){
        if(result.success){
            if(result.msg){
                for(var i =0; i < result.msg.length;i++){
                    var node = allmenuTree.tree('find',result.msg[i]);
                    if(node){
                        var isLeaf = allmenuTree.tree('isLeaf', node.target);
                        if(isLeaf){
                            allmenuTree.tree('check', node.target);
                        }
                    }
                }
            }
        }
	},'json');
}

// 加载菜单下所有按钮
function loadMenuBtn(menuNode) {
	var datas = {'data' : menuNode ? menuNode.id : ''};
	$.ajax({
         type : 'post',
         url : '${ct}/businessConsole/menuBtn/getMenuBtnList.do',
         data : datas,
         dataType : 'json',
         error : function(){
         	parent.$.messager.alert('错误', '系统异常！', 'error');
         },
         success : function(result) {
             allMenuBtn = $('#allMenuBtn').tree({
		        data : result,
			    checkbox : true,
		        onLoadSuccess : function(node, data){ 
		        	// 已设置过菜单按钮，选中原先设置的按钮
		        	var flag = true;
		        	for(var i = 0; i < buttons.length; i++) {
		        		var node = allMenuBtn.tree('find',buttons[i].id);
						if(node){
		                    var isLeaf = allMenuBtn.tree('isLeaf', node.target);
		                    if(isLeaf){
		                        allMenuBtn.tree('check', node.target);
		                        flag = false;
		                    }
		                }
		        	}
		        	
		        	// 如果不存在，则获取数据库授权的按钮
		        	if(flag){
						getGrantMenuBtn( menuNode ? menuNode.id : '');// 获取已授权的菜单按钮，并勾选
					}
		        },
		        onCheck: function(node, checked){
					if(!checked){
						for(var i = 0; i < buttons.length; i++){
							if(node.id == buttons[i].id){
								buttons.splice(i, 1);
								break;
							}
						}
					}
				},
		        onSelect : function(node){
		        	//console.info(node);
				}
			});
         }
	});
}

// 获取已授权的菜单按钮
function getGrantMenuBtn(menu_code){
	$.post('${ct}//businessConsole/menuBtn/getGrantMenuBtn.do', 
		{role_code:'${param.roleId}',menu_code:menu_code}, function(result){
        if(result){
			for(var i =0; i < result.length;i++){
			    var node = allMenuBtn.tree('find',result[i].button_code);
			    if(node){
			        var isLeaf = allMenuBtn.tree('isLeaf', node.target);
			        if(isLeaf){
			            allMenuBtn.tree('check', node.target);
			        }
			    }
			}
        }
	},'json');
}

// 保存
function submitMenuToRole($dialog,$pjq){
	var menus = [];// 菜单code
	var btns = [];// 按钮信息
	var menu_code = [];// 菜单code 用于删除角色菜单按钮
	
	menus = allmenuTree.tree('getChecked');// 获取选中菜单
	var unchecked_menus = allmenuTree.tree('getChecked', 'unchecked');	// 获取未选择节点
	var indeterminate_menus = allmenuTree.tree('getChecked', 'indeterminate');// 获取不确定的菜单
	// 合并选中菜单和不确定菜单
	menus = $.merge(menus, indeterminate_menus);
	
	if(menus.length == 0){
	    $pjq.messager.alert('提示', '请选择菜单！', 'info');
	    return;
	}
	
	var checked = allMenuBtn.tree('getChecked');// 获取选中菜单按钮
	if(checked){
		var indeterminate = allMenuBtn.tree('getChecked', 'indeterminate');// 获取不确定的菜单按钮
		
		checked = $.merge(checked, indeterminate);
				
		for(var i = 0; i < checked.length; i++) {
			var flag = false;
			for(var j = 0; j < buttons.length; j++) {
				if(buttons[j].id == checked[i].id) {
					flag = true;
				}
			}
			
			if(!flag) {
				buttons.push(checked[i]);
			}
		}
	}
	
	// 处理未选中菜单，用于删除 角色下这些菜单授权的按钮
	for(var i = 0; i < unchecked_menus.length; i++){
		menu_code.push(unchecked_menus[i].id);
	}
	// 处理菜单按钮
	for(var i = 0; i < buttons.length; i++){
		btns.push(buttons[i].attributes);
	}
	
	$.ajax({
         type : 'post',
         url : '${ct}/businessConsole/role/changeRoleMenu.do',
         data : {
				'roleId' : '${param.roleId}',
				'menus' : JSON.stringify(menus),
				'buttons' : JSON.stringify(btns),
				'menu_code' : menu_code.join(','),
				'select_menu_code' : select_menu_code.join(',')
			},
			dataType : 'json',
			error : function() {
				$pjq.messager.alert('错误', '系统异常！', 'error');
			},
			success : function(result) {
				if (result.success) {
					$pjq.messager.alert('提示', '授权成功！', 'info', function() {
						$dialog.dialog('destroy');
					});
				}
			}
		});
	}
</script>
</html>