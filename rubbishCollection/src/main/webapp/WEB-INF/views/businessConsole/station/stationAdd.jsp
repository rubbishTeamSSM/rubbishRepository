<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <title>岗位新增</title> 
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge"></meta>
	<%@include file="/WEB-INF/views/businessConsole/common/common.jsp"%>   
  </head>
  
  
  <body>
    <div class="easyui-panel" data-options="fit:true,border:false">
		<form id="addForm" style="margin: 20px 20px 20px -30px;height:85%">
			<table
				style="text-align:right;border-collapse:separate; border-spacing:12px;margin:0 auto">
				<tr>					
					<td><font color='red' size='2' style='vertical-align: middle;'>*&nbsp;</font>岗位名称：</td>
					<td><input type="text" style="width: 175px; height:22px;line-height:22px;" id="POST_NAME"
						name="POST_NAME" class="easyui-validatebox"
						data-options="required:true,validType:['length[1,9]','unnormal']" />
					</td>
				</tr>
				<tr>
					<td>岗位名称简：</td>
					<td><input type="text" style="width: 175px; height:22px;" id="POST_NAME_J"
						name="POST_NAME_J" class="easyui-validatebox"
						data-options="validType:['length[1,9]','unnormal']" />
					</td>
				</tr>
				<tr>
					<td><font color='red' size='2' style='vertical-align: middle;'>*&nbsp;</font>品质部：</td>
					<td><select id="IS_QUALITY" name="IS_QUALITY" style="width:175px;height:22px;" class="easyui-combobox" data-options="editable:false,required:true,panelHeight:50">
										<option value="0">是</option>
										<option value="1">否</option>
						</select></td>
				</tr>		
				<tr>
					<td><font color='red' size='2' style='vertical-align: middle;'>*&nbsp;</font>400客服：</td>
					<td><select id="IS_400SERVICE" name="IS_400SERVICE" style="width:175px;height:22px;" class="easyui-combobox" data-options="editable:false,required:true,panelHeight:50">
										<option value="1">否</option>
										<option value="0">是</option>										
					</select></td>
				</tr>
				
				<tr>
					<td><font color='red' size='2' style='vertical-align: middle;'>*&nbsp;</font>固定时限(单位：分钟)：</td>
					<td><input type="text" style="width: 175px; height:22px;" id="LIMIT_TIME"
						name="LIMIT_TIME" class="easyui-validatebox"
						data-options="required:true,validType:['isNumber','strlen[1,9]']" />
					</td>
				</tr>
			</table>
		</form>
	</div>
  </body>
  
  <script type="text/javascript">
  	$.extend($.fn.validatebox.defaults.rules, {
    	isNumber: {    
	        validator:function(value,param){
	            var reg = /^\d+$/g;
	            return reg.test(value);
	        },
	        message:  '只能输入数字！'
    	}
	});

		function submitStation($stationDialog, $grid, $pjq) {
			var form = $("#addForm");
			var isValid = $(form).form('validate');
			if (isValid) {
				var data = ns.serializeObject(form);
				data.POST_CODE = 'null';
				if(!validStation(data.POST_NAME,data.POST_CODE)){
	                return;
	            }else if(0 == data.IS_400SERVICE){
	            	if(!validService(data.IS_400SERVICE,data.POST_CODE)){
	            		return;
	            	}	            	
	            }
			parent.$.messager.confirm('确认','确认保存吗？',function(r) {
				if (r) {
					$.ajax({
						type : 'post',
						url : url+'/businessConsole/station/insertStation.do',
						data : {
							'data' : JSON.stringify(data)
						},
						dataType : 'json',
						success : function(result) {
							if (result.success) {
								parent.$.messager.alert('提示','新增岗位成功','info');
									$grid.datagrid('reload');
									$stationDialog.dialog('destroy');			                    
							} else {
								parent.$.messager.alert('错误','新增岗位失败','error');
							}
						},error : function(){
			            	parent.$.messager.alert('错误','系统异常','error');
			            }
					});						
				}
			});
			}
		}
		
		function validStation(POST_NAME,POST_CODE){
	        var data = {};
	        data.POST_NAME = POST_NAME;
	        data.POST_CODE = POST_CODE;
	        var res;
	        $.ajax({
	            type : 'post',
	            url : '${ct}/businessConsole/station/validStation.do',
	            data :{'data' : JSON.stringify(data)},
	            async:false,
	            dataType : 'json',
	            success : function(result) {
	                res = result;
	            }
	        });	
	        if(res.success){
	            return true;
	        }else{
	            parent.$.messager.alert('提示',res.msg,'info');
	            return false;
	        }
		  }
		    
	    function validService(IS_400SERVICE,POST_CODE){
	        var data = {};
	        data.IS_400SERVICE = IS_400SERVICE;
	        data.POST_CODE = POST_CODE;
	        var res;
	        $.ajax({
	            type : 'post',
	            url : '${ct}/businessConsole/station/validService.do',
	            data :{'data' : JSON.stringify(data)},
	            async:false,
	            dataType : 'json',
	            success : function(result) {
	                res = result;
	            }
	        });	
	        if(res.success){
	            return true;
	        }else{
	            parent.$.messager.alert('提示',res.msg,'info');
	            return false;
	        }
	    }
  </script>
</html>
