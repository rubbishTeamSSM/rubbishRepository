<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<style>
.validatebox-text{
	width: 195px!important;
}
body{ font-size:14px;}
input{ vertical-align:middle; margin:0; padding:0}
.file-box{ position:relative;width:340px}
.txt{left:5px; height:30px; border:2px solid #cdcdcd; font-weight:bold; width:230px;font-size:18px;}
.btn {
	display: inline-block;
	padding: 4px 12px;
	margin-bottom: 0;
	font-size: 14px;
	font-weight:bold;
	line-height: 1.42857143;
	text-align: center;
	white-space: nowrap;
	vertical-align: middle;
	cursor: pointer;
	background-color: #CCCCCC;
	border: 1px solid transparent;
	border-radius: 4px;
	width: 94px;
}
.btn1 {
    left:240px;
	display: inline-block;
	padding: 4px 12px;
	margin-bottom: 0;
	font-size: 14px;
	font-weight:bold;
	line-height: 1.42857143;
	text-align: center;
	white-space: nowrap;
	vertical-align: middle;
	cursor: pointer;
	background-color:#B0E2FF;
	border: 1px solid transparent;
	border-radius: 4px;
	width: 94px;
}
.daorut{
    background-color:#32CD32;
    font-size:14px;
    font-family: sans-serif;
    color: #FFFFFF;
    font-weight:bold;

}
.file{ left:238px; position:absolute;right:80px; height:30px; filter:alpha(opacity:0);opacity: 0;width:94px }

</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" ></meta>
<%@include file="/WEB-INF/views/businessConsole/common/common.jsp"%>
</head>

<body>
  	<p class="daorut">导入文件：(支持2003、2007版excel)</p>
	<div class="easyui-panel" data-options="fit:true,border:false">
  		<form action="" method="post" enctype="multipart/form-data">
 			<input type='text' name='textfield' id='textfield' class='txt' readonly />  
 			<input type='button' class='btn' value='选择文件' />
		    <input type="file" name="allFile" class="file" id="fileField" onchange="document.getElementById('textfield').value=this.value" />
			<input type="button" id="ctlBtn" class="btn1" onclick="importRoom()" value="导入">
  		</form>
	</div>
	
	<script>
	//导入
	function importRoom() {
		var fileName = $("#fileField").val();
		if (null != fileName && "" != fileName) {
			var fileNameindex = fileName.lastIndexOf(".");
			if (fileNameindex < 0) {
				parent.$.messager.alert("提示", '导入文件格式不正确', "info");
				$("#fileField").val("");
				$("#textfield").val("");
				return;
			} else {
				var ext = fileName.substring(fileNameindex + 1, fileName.length);
				if (ext != "xls" && ext != "xlsx") {
					parent.$.messager.alert("提示", '导入文件格式不正确', "info");
					$("#fileField").val("");
					$("#textfield").val("");
					return;
				}
			}
			
			parent.$.messager.progress({
    			title : '导入进度',
    			text : '数据导入中,请勿关闭窗口........'
    		});
			$.ajaxFileUpload({
				url : url+'/roomInfoMgr/excelImportRoomInfo.do',
				secureuri : false,
				fileElementId : 'fileField',
				dataType : 'json', //返回值类型 一般设置为json
				success : function(result) {
				    parent.$.messager.progress('close');					
					result = eval('(' + result + ')');
					if(0==result.returnCode){
						//关闭进度条
						var msg= result.returnMsg.split('|');
						parent.$.messager.alert("操作提示", msg[0],"info",function(){
							var filepath=msg[1];
							var pageName='商品导入异常数据.zip';
							$("#fileField").val("");
							$("#textfield").val("");
                        	if(''!=filepath){
                        		parent.window.location.href = url+'/roomInfoMgr/downloadExcel.do?filepath='+filepath+'&pageName='+pageName;
                        	}					
    					}); 
					  
						
					}else{
						//关闭进度条
						parent.$.messager.alert("操作提示", result.returnMsg,"info",function(){
							$("#fileField").val("");
							$("#textfield").val("");
    					}); 
					}
				},
				error : function(result, status, e) {
					//关闭进度条
					parent.$.messager.progress('close');
					parent.$.messager.alert("操作提示","系统异常,检查导入excel是否为模板文件","info",function(){
						$("#fileField").val("");
						$("#textfield").val("");
							
    				}); 
				}
			});
		} else {
			parent.$.messager.alert("操作提示", '请选择要导入的文件', "info");
			return;
		}

	}
	
	</script> 
</body>
</html>