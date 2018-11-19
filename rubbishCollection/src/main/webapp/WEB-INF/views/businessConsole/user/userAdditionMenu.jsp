<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@include file="/WEB-INF/views/businessConsole/common/common.jsp"%>
</head>
<body>
<div  class="easyui-panel" data-options="fit:true,border:false">

	<div style="margin-bottom: 20px;">
		<table>
			<tr>
				<td>有效时间 </td>
			</tr>
			<tr>
				<td ><font color='red' size='2' style='vertical-align: middle;'>*&nbsp;</font>起始时间：</td>
				<td><input id="startTime" name="startTime" type="text" class="easyui-datebox" required="required"></input></td>
			</tr>
			<tr>
				<td ><font color='red' size='2' style='vertical-align: middle;'>*&nbsp;</font>终止时间：</td>
				<td><input id="endTime" name="endTime" type="text" class="easyui-datebox" required="required"></input></td>
			</tr>
			
		</table>
	</div>
    <div style="overflow: scroll;height:300px;overflow-x: hidden;"><ul id="allmenuTree"></ul></div>

    <script>
        var allmenuTree;
        $(document).ready(function(){
            allmenuTree =$('#allmenuTree').tree({
                url:'${ct}/businessConsole/menu/getAllMenu.do',
                checkbox:true,
                onLoadSuccess:function(node,data){
                    $.post('${ct}/businessConsole/user/getAdditionMenu.do',{user_code:'${param.user_code}'},function(result){
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

            });

        });

        function submitUserAdditionMenu($dialog,$pjq){
        	
        	
        	//时间
        	
        	var startTime = $('#startTime').datebox('getValue');
        	var endTime = $('#endTime').datebox('getValue');
        	
        	if(startTime == '' || endTime == ''){
        		  $pjq.messager.alert('提示', '请输入时间！', 'info');
        		  return;
        	}
        	
        	if(startTime > endTime){
      		  $pjq.messager.alert('提示', '终止时间要大于起始时间', 'info');
      		  return;
      		}
        	
            var checkednodes = allmenuTree.tree('getChecked');
            var indeterminatenodes = allmenuTree.tree('getChecked', 'indeterminate');

            var ids = [];
            for (var i = 0; i < checkednodes.length; i++) {
                ids.push(checkednodes[i].id);
            }

            for (var i = 0; i < indeterminatenodes.length; i++) {
                ids.push(indeterminatenodes[i].id);
            }

            if(ids.length == 0){
                $pjq.messager.alert('提示', '请选择菜单！', 'info');
                return;
            }
           $.post('${ct}/businessConsole/user/changeUserAdditionMenu.do', {
        	    user_code:'${param.user_code}',
                ids : ids.join(','),
                startTime:startTime,
                endTime:endTime
            }, function(result) {
                if (result.success) {
                    $dialog.dialog('destroy');
                } else {
                    $pjq.messager.alert('提示', result.msg, 'error');
                }
                $pjq.messager.alert('提示', '授权成功！', 'info');
            }, 'json');
        }
	</script>
</div>
</body>
</html>