<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@include file="/WEB-INF/views/businessConsole/common/common.jsp"%>
</head>
<body>
			 <div  class="easyui-panel" data-options="fit:true,border:false" style="margin-top:22px">
		    				<form id="newUserForm" style="margin: 10px 30px">
								<table class="table-ch-add" style="margin: 5px 10px">
									<tr>
										<td><label class="label-ch-w100"><font color='red' size='2' style='vertical-align: middle;'>*&nbsp;</font>用户账号：</label><input id="USER_ACCNT"  type="text" style="width:175px;"  name="USER_ACCNT" class="easyui-validatebox" data-options="required:true,validType:['length[1,40]']" onblur="upperCase()"/></td>
										<td><label class="label-ch-w100"><font color='red' size='2' style='vertical-align: middle;'>*&nbsp;</font>用户名称：</label><input type="text" name="USER_NAME" style="width: 175px;" class="easyui-validatebox" data-options="required:true,validType:['length[1,40]']"/></td>
										<%--<td rowspan="4">头像预览<div id="imgDiv" style="border: #DDDDDD 1px solid ; height: 130px;width: 100px;margin-right:10px;display: flex;justify-content: center;align-items: center; "></div></td>
									--%>
									</tr>
									
									<tr>
									    <td><label class="label-ch-w100">用户昵称：</label><input  style="width: 175px;" type="text" name="USER_NICK_NAME" class="easyui-validatebox"  data-options="required:false,validType:['length[1,40]']"/></td>
										<td><label class="label-ch-w100">用户密码：</label><input  type="password" id="USER_PWD" name="USER_PWD" style="width: 175px;" class="easyui-validatebox" data-options="validType:['passwd']"/></td>										
									</tr>
									<tr>
										<td><label class="label-ch-w100">手机号码：</label><input type="text" id="PHONE" name="PHONE" class="easyui-validatebox" style="width: 175px;" data-options="validType:['mobile']"/></td>
										<td><label class="label-ch-w100">性别：</label>
								        <input class="easyui-combobox" name="SEX" id="SEX" type="text" style="height: 25px;width: 175px;"/>
						                </td>
									</tr>
								<!-- 	<tr>
										<td><label class="label-ch-w100">固定电话：</label><input type="text" name="TEL" class="easyui-validatebox" style="width: 175px;" data-options="validType:['telephone']"/></td>
										<td><label class="label-ch-w100">管理员：</label>
										<select name="ADMIN_FLAG" class="easyui-combobox" data-options="width:155,panelHeight:'auto',editable:false">
											<option value="0">否</option>
											<option value="1">是</option>
									   </select>
									</tr> -->
									<tr>
										<td><label class="label-ch-w100">固定电话：</label><input type="text" name="TEL" class="easyui-validatebox" style="width: 175px;" data-options="validType:['telephone']"/></td>
										<td><label class="label-ch-w100"><font color='red' size='2' style='vertical-align: middle;'>*&nbsp;</font>管理员：</label>
										<input class="easyui-combobox" name="ADMIN_FLAG" id="ADMIN_FLAG" type="text" style="height: 25px;width: 175px;"/>
									</tr>
									<tr>
										
										<td><label class="label-ch-w100">身份证号：</label><input style="width: 175px;" type="text" name="ID_CARD" class="easyui-validatebox" data-options="validType:['idCardNo']"/></td>
										<td><label class="label-ch-w100">电子邮件：</label><input   type="text" name="EMAIL" style="width: 175px;" class="easyui-validatebox" data-options="validType:['length[1,40]','email']"/></td>
									</tr>
									<tr>								
										<td><label class="label-ch-w100">IP地址：</label><input type="text" name="IP" class="easyui-validatebox" style="width: 175px;" data-options="validType:['ipNo']"/></td>
										<%--<td>
									       <div class="upfile" style="margin-left: -45px;">
											    <label class="label-ch-w140">上传头像：</label>
												<!-- <a id="upButton" onclick="getFile()" style="float: left;" class="easyui-linkbutton">点击上传</a>
												<input type="file" style="width: 260px !important;display:none" name="allFile" id="fileField" onchange="uploadFiles('fileField','httpCs','')"> -->
												<div class="checkps">点击上传
						                             <input type="file" class="fileUploader" name="allFile" id="fileField" onchange="uploadFiles('fileField','wyApp','')">
				                                </div>
										   </div>
	          						 	</td>
									--%>
									</tr>
									<tr>
										<td><label class="label-ch-w100">是否内部人员：</label>
											<select name="SPECIAL_FLAG" class="easyui-combobox" style="height: 25px;width:175px;" data-options="panelHeight:'auto',editable:false">
												<option value="1">否</option>
												<option value="0">是</option>
										    </select>
										</td>
										<td></td>
									</tr>
									<!-- <tr>
									   <td>
									    <label class="label-ch-w100">主属部门：</label>
					                       <select id="DEPT_CODE" name="DEPT_CODE"  style="width: 50px;"></select>
					                   </td>
					                </tr> -->
									
									<tr>
										<td colspan="4"><label class="label-ch-w100">备注：</label><textarea name="REMARK" id="REMARK" class="easyui-validatebox text-w360" data-options="required:false,validType:['length[0,1024]']" style="width:738px;height:100px;"/></textarea></td>
									</tr>
								</table>
							</form>
		</div>
	<script>
	$(document).ready(function(){
		initStatCode();//初始化工单状态combobox
		initSexCode();//初始化工单状态combobox
	});
	//初始化工单状态combobox
	function initStatCode(){
		var data = [{'ID':'0','TEXT':'否'},{'ID':'1','TEXT':'是'}];
		$('#ADMIN_FLAG').combobox({
	        data:data,
	        value : data[0].ID,
	        valueField:'ID',
	        textField:'TEXT',
	        editable:false,
	        panelHeight:60
	    });
	}
	//初始化工单状态combobox
	function initSexCode(){
		var data = [{'ID':'0','TEXT':'女'},{'ID':'1','TEXT':'男'}];
		$('#SEX').combobox({
	        data:data,
	        value : data[0].ID,
	        valueField:'ID',
	        textField:'TEXT',
	        editable:false,
	        panelHeight:60
	    });
	}
	
	var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];    // 加权因子   
	var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];            // 身份证验证位值.10代表X   
	function IdCardValidate(idCard) { 
	    idCard = trim(idCard.replace(/ /g, ""));               //去掉字符串头尾空格                     
	    if (idCard.length == 15) {   
	        return isValidityBrithBy15IdCard(idCard);       //进行15位身份证的验证    
	    } else if (idCard.length == 18) {   
	        var a_idCard = idCard.split("");                // 得到身份证数组   
	        if(isValidityBrithBy18IdCard(idCard)&&isTrueValidateCodeBy18IdCard(a_idCard)){   //进行18位身份证的基本验证和第18位的验证
	            return true;   
	        }else {   
	            return false;   
	        }   
	    } else {   
	        return false;   
	    }   
	}   
	/**  
	 * 判断身份证号码为18位时最后的验证位是否正确  
	 * @param a_idCard 身份证号码数组  
	 * @return  
	 */  
	function isTrueValidateCodeBy18IdCard(a_idCard) {   
	    var sum = 0;                             // 声明加权求和变量   
	    if (a_idCard[17].toLowerCase() == 'x') {   
	        a_idCard[17] = 10;                    // 将最后位为x的验证码替换为10方便后续操作   
	    }   
	    for ( var i = 0; i < 17; i++) {   
	        sum += Wi[i] * a_idCard[i];            // 加权求和   
	    }   
	    valCodePosition = sum % 11;                // 得到验证码所位置   
	    if (a_idCard[17] == ValideCode[valCodePosition]) {   
	        return true;   
	    } else {   
	        return false;   
	    }   
	}   
	/**  
	  * 验证18位数身份证号码中的生日是否是有效生日  
	  * @param idCard 18位书身份证字符串  
	  * @return  
	  */  
	function isValidityBrithBy18IdCard(idCard18){   
	    var year =  idCard18.substring(6,10);   
	    var month = idCard18.substring(10,12);   
	    var day = idCard18.substring(12,14);   
	    var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
	    // 这里用getFullYear()获取年份，避免千年虫问题   
	    if(temp_date.getFullYear()!=parseFloat(year)   
	          ||temp_date.getMonth()!=parseFloat(month)-1   
	          ||temp_date.getDate()!=parseFloat(day)){   
	            return false;   
	    }else{   
	        return true;   
	    }   
	}   
	  /**  
	   * 验证15位数身份证号码中的生日是否是有效生日  
	   * @param idCard15 15位书身份证字符串  
	   * @return  
	   */  
	  function isValidityBrithBy15IdCard(idCard15){   
	      var year =  idCard15.substring(6,8);   
	      var month = idCard15.substring(8,10);   
	      var day = idCard15.substring(10,12);   
	      var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
	      // 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法   
	      if(temp_date.getYear()!=parseFloat(year)   
	              ||temp_date.getMonth()!=parseFloat(month)-1   
	              ||temp_date.getDate()!=parseFloat(day)){   
	                return false;   
	        }else{   
	            return true;   
	        }   
	  }   
	//去掉字符串头尾空格   
	function trim(str) {   
	    return str.replace(/(^\s*)|(\s*$)/g, "");   
	}
	
	$.extend($.fn.validatebox.defaults.rules, {
	    mobile:{
	        validator:function(value){
	        	var reg = /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
	        	return value.length == 11 && reg.test(value);
	        },
	        message:"请正确填写的手机号码."
	   },telephone:{
		    validator:function(value){
			        //电话号码格式010-12345678或者0510-86997732
			        var reg = /^0\d{2,3}-\d{7,8}(-\d{1,6})?$/;
			        return reg.test(value);
		        },
		        message:"请正确填写的电话号码."
		},idCardNo:{
		    validator:function(value){
		        	return IdCardValidate(value);
		        },
		        message:"请正确输入的身份证号码."
		},
		passwd:{
		    validator:function(value){
		          //6到20位数字字母组合
		             var reg = /^[A-Za-z0-9]{6,20}$/;
		        	return reg.test(value);
		        },
		        message:"请输入正确的密码格式：6到20位数字字母组合."
		},
		ipNo:{
		    validator:function(value){
		          //6到20位数字字母组合
		             var reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
                     return reg.test(value);
		        },
		        message:"请输入正确的ip"
		}
	    
	});
	
	
	function upperCase()
		{
		var y=document.getElementById("USER_ACCNT").value;
		var x=document.getElementById("PHONE").value;
		if(""==x||null==x)
		{
		  document.getElementById("PHONE").value=y;
		}
		}
	
	
/* 	$(document).ready(function(){
	     alert(11111);
		 $('#DEPT_CODE').combotree({  
		    url: '${ct}/businessConsole/department/getDepartmentTree.do',
		    width:176,
		    multiple:true,  
		    checkbox:true,
		    required:false
		});
		
		$('#ROLE_CODE').combobox({  
		    url: '${ct}/businessConsole/role/sysAllRole.do',
		    valueField:'ROLE_CODE',
		    textField:'ROLE_NAME',
		    width:176,
		    required:true,
		    editable:false
		});  
	}); 
	
	*/
	 //上传附件
	var filetext = '';
	var upLoadUrl = '';
	var imgIndex = 0;
	function uploadCallBack(result)
	{
		filetext=result[0].WJDM;
		upLoadUrl=result[0].URL;
		var html = '<img alt="" src="'+upLoadUrl+'" id="image" style=" height: 100%;width: 100%;">';
		$("#imgDiv").html(html);
	    $('#fileField').val("");
	}
	
	function getFile(){
	    $('#fileField').click();
    }
    
    /* function download(obj){
         var wjlj = obj.attr("src");
         console.info(wjlj);
         window.location.href=url+'/download.tool?wjlj='+wjlj;
    } */

	// 保存 
	function submitUser($userDialog,$userGrid,$pjq){
        var form= $("#newUserForm");
        var isValid = $(form).form('validate');
        if(isValid){
        	var data = ns.serializeObject(form);
        	data.PIC_URL=upLoadUrl;
        	data.FILETEXT = filetext;
        	if(!validUser(data.USER_ACCNT, $userDialog, $userGrid,$pjq)){
        		return;
        	}
            $.ajax({
                type : 'post',
                url : '${ct}/businessConsole/user/addNewUser.do',
                data : {'data':JSON.stringify(data)},
                dataType : 'json',
                beforeSend : function(){           
	             $pjq.messager.progress({
	   			   title : '进度',
	   			   text : '数据新增中,请勿关闭窗口........'
	    			}); 
	    	     },
                error : function() {
                    $pjq.messager.progress('close');
					$pjq.messager.alert('错误','系统异常！','error');
                },
                success : function(result) {
                    if(result.success){
                        $pjq.messager.progress('close');   
                        $pjq.messager.alert('成功','创建用户成功','info');
                        $userDialog.dialog('destroy');
                        $userGrid.datagrid('reload');
                    }else{
                        $pjq.messager.progress('close');
                        $pjq.messager.alert('错误','创建用户失败','error');
                    }
                }
            });
        }
	}
	
	// 校验用户
	var USER_CODE='';
	function validUser(USER_ACCNT, $userDialog, $userGrid,$pjq){
		
		var data = {};
		data.USER_ACCNT = USER_ACCNT;
		var res;
		$.ajax({
            type : 'post',
            url : '${ct}/businessConsole/user/validUser.do',
            data :data,
            async : false,
            dataType : 'json',
            success : function(result) {
            	res = result;
            	USER_CODE=result.USER_CODE;
            }
        });
		
		if('0' == res.result_code){// 如果用户账号不存在，则正常保存
			return true;
		} else if('1' == res.result_code){// 如果用户账号存在，且未作废，提示用户账号已存在
			parent.$.messager.alert('错误', res.result_msg, 'error');
			return false;
		} else {// 如果用户账号已存在，且已作废，提示用户账号已存在，是否覆盖；如果确认覆盖，则改用户恢复启用状态，并更新新填入的数据
			parent.$.messager.confirm('确认', res.result_msg, function(r){
				if (r){
				    // 退出操作;
				    updateUserInfo($userDialog, $userGrid,$pjq);
				    
				}
			});
			return false;
		}
	}
	
	// 更新用户信息，并启用改用户
	function updateUserInfo($userDialog, $userGrid,$pjq){
		var form= $("#newUserForm");
       	var data = ns.serializeObject(form);
       	data.DEL_FLAG = '0';
       	data.USER_CODE=USER_CODE;
		$.ajax({
			type : 'post',
			url : '${ct}/businessConsole/user/updateAccountManage.do',
			data : {'data':JSON.stringify(data)},
			dataType : 'json',
			error : function() {
				$pjq.messager.alert('错误','系统异常！','error');
			},
			success : function(result) {
				if(result.success){
				    $pjq.messager.alert('成功','创建用户成功','info');
					$userDialog.dialog('destroy');
					$userGrid.datagrid('reload');
				}else{
					parent.$.messager.alert('错误','覆盖失败！','error');
				}
			}
		});
	}
	
	</script>

</body>
</html>