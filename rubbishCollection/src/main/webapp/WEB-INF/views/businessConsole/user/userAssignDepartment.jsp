<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@include file="../common/common.jsp"%>
</head>
<body>
<div  class="easyui-panel" data-options="fit:true,border:false">

	<div style="margin-bottom: 20px;">
	</div>
    <ul id="allDepartmentTree"></ul>

    <script>
        var allDepartmentTree;
        $(document).ready(function(){
            allDepartmentTree =$('#allDepartmentTree').tree({
                url:'${ct}/businessConsole/department/getDepartmentTree.do',
                checkbox:true,
                cascadeCheck:true,
                onLoadSuccess:function(node,data){
                   $.post('${ct}/businessConsole/department/getUserDepartment.do',{user_code:'${param.user_code}'},function(result){
                    	if(result && result.length > 0){
	                        for(var i = 0;i < result.length;i++){
	                            var node = allDepartmentTree.tree('find',result[i].DEPT_CODE);
	                            if(node && result[i].SELECT_FLAG && node.attributes.type == 'AREA'){
	                            	 allDepartmentTree.tree('check', node.target);
	                            }
	                        }
                        }
                    },'json');
                }
            });
        });
		
		//保存
        function submitUserDepartment($dialog,$pjq){
            var checkedNodes = allDepartmentTree.tree('getChecked');
        	var unCheckedNodes = allDepartmentTree.tree('getChecked', 'indeterminate');
            var deptArrs = [];
            var areaCodes = [];
            if(checkedNodes && checkedNodes.length > 0){
	            for (var i = 0; i < checkedNodes.length; i++) {
	            	var data = {};
	            	if(checkedNodes[i].attributes.type == 'AREA'){
		                data.AREA_FLAG = 1;
		                areaCodes.push("'" + checkedNodes[i].id + "'");
	            	}else if(checkedNodes[i].attributes.type == 'DEPT'){
	            		data.AREA_FLAG = 0;
	            	}
	                data.SELECT_FLAG = 1;
	                data.DEPT_CODE = checkedNodes[i].id;
	                deptArrs.push(data);
	            }
	            
	            if(unCheckedNodes && unCheckedNodes.length > 0){
	            	for (var i = 0; i < unCheckedNodes.length; i++) {
	            		var data = {};
	            		data.AREA_FLAG = 0;
	            		data.SELECT_FLAG = 0;
	                	data.DEPT_CODE = unCheckedNodes[i].id;
	                	deptArrs.push(data);
		            }
	            }
            }else{
                $pjq.messager.alert('提示', '请选择小区！', 'info');
                return;
            }
            parent.$.messager.confirm('确认', '是否确认保存？',function(r){  
	    	    if (r){  
					var data = {};
					data.USER_CODE = '${param.user_code}';
					data.deptArrs = deptArrs;
					data.areaCodes = areaCodes.join(",");
					$.ajax({
		                type : 'post',
		                url : '${ct}/businessConsole/user/changeUserDepartment.do',
		                data :{'data':JSON.stringify(data)},
		                dataType : 'json',
		                beforeSend : function(){
				       		parent.$.messager.progress({
				       			title : '部门设置',
				       			text : '保存中。。。。'
				       		});
				       	},
		                success : function(result) {
		                	//关闭进度条
							parent.$.messager.progress('close');
		                    if(result.success){
		                    	$dialog.dialog('destroy');
			                	$pjq.messager.alert('提示', '保存成功！', 'info');
		                    }else{
		                        $pjq.messager.alert('提示', '保存失败', 'error');
		                    }
		                },error : function(){
		                	//关闭进度条
							parent.$.messager.progress('close');
		             		parent.$.messager.alert('错误','系统异常','error');
		             	}
		            });
            	}  
	    	}); 
        }
	</script>
</div>
</body>
</html>
