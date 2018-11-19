<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="ct" value="${pageContext.request.contextPath}"></c:set>

<script src="${ct}/plugin/jquery-easyui-1.3.2/jquery-1.8.0.min.js"></script>
<script src="${ct}/plugin/jquery-easyui-1.3.2/jquery.easyui.min.js"></script>
<script src="${ct}/plugin/jquery-easyui-1.3.2/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" href="${ct}/plugin/jquery-easyui-1.3.2/themes/${empty cookie.easyuiTheme ? 'pure-blue' : cookie.easyuiTheme.value}/easyui.css" id="swicth-style">
<link rel="stylesheet" href="${ct}/plugin/jquery-easyui-1.3.2/themes/${empty cookie.easyuiTheme ? 'pure-blue' : cookie.easyuiTheme.value}/easyui-rep.css" id="swicth-style-rep">
<link rel="stylesheet" type="text/css" href="${ct}/css/table.css">

<%-- <link rel="stylesheet" href="${ct}/businessConsole/javascript/jquery-easyui-1.3.2/themes/cupertino/easyui.css" id="swicth-style"> --%>
<link rel="stylesheet" href="${ct}/plugin/jquery-easyui-1.3.2/themes/icon.css">
<script src="${ct}/plugin/extEasyui.js"></script>
<script src="${ct}/plugin/extJquery.js"></script>
<script src="${ct}/plugin/jquery.cookie.js"></script>
<script src="${ct}/plugin/json2.js"></script>
<script src="${ct}/plugin/jsUtil.js"></script> 
<script src="${ct}/plugin/ajaxfileupload.js"></script>

<script type="text/javascript">
//全局的ajax访问，处理ajax清求时sesion超时 
$.ajaxSetup({
    contentType : "application/x-www-form-urlencoded;charset=utf-8",
    complete : function(XMLHttpRequest, textStatus) {
        var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus"); // 通过XMLHttpRequest取得响应头，sessionstatus，
        if (sessionstatus == "sessionOut") {
            // 如果超时就处理 ，指定要跳转的页面
            //window.location.replace("login.html");
			var top, eWindow = window;
            try {
                while(eWindow.location.host == eWindow.parent.location.host && eWindow.location.href != eWindow.parent.location.href){
                    eWindow = eWindow.parent;
                }
                top = eWindow;
            } catch(e) {
                top = eWindow;
            }
            top.location.href = "" == "${pageContext.request.contextPath}" ? "/" : "${pageContext.request.contextPath}";
        }
    }
});

var url='${ct}';

//fileId上传空间id,filePath上传路径
function uploadFiles(fileId,filePath,type){
    /* parent.$.messager.progress({
	    title : '',
	    text : '上传中。。。。'
    }); */
    var fileName = $("#"+fileId).val();
    if(null == fileName || "" == fileName){	
    	parent.$.messager.progress('close');
    	parent.$.messager.alert("提示", '请选择要上传的文件', "info");
		return;
    }
    if(type){
       //图片以外的格式 先不做限制
       
    }else{
       //默认type没有，默认就是图片格式，支持jpg,png,bmp
       	var fileNameindex = fileName.lastIndexOf(".");
		if (fileNameindex < 0) {
			parent.$.messager.progress('close');
			parent.$.messager.alert("提示", '请上传jpg、png、bmp格式的文件', "info");
			return;
		} else {
			var ext = fileName.substring(fileNameindex + 1, fileName.length);
			ext = ext.toUpperCase();
			if (ext != "JPG" && ext != "PNG" && ext != "BMP") {
				parent.$.messager.progress('close');
				parent.$.messager.alert("提示", '请上传jpg、png、bmp格式的文件', "info");
				return;
			}
		}
    }
	$.ajaxFileUpload({
			url : url+'/upload.tool?wjjDm='+filePath,
			secureuri : false,
			fileElementId : fileId,
			dataType : 'json', //返回值类型 一般设置为json
			success : function(result) {
			    parent.$.messager.progress('close');
			    result = eval('(' + result + ')');
				uploadCallBack(result,fileId);
			},
			error : function(result, status, e) {
				parent.$.messager.progress('close');
				parent.$.messager.alert("提示", '系统异常', "error");	
			}
	});
}
 
//表单重置
function resetForm(id)
{
   $('#'+id).form('clear');
 // $("#searchForm")[0].reset();
}

//设月底跟月头时间  
function time(number){
	var date = new Date();  
	var y = date.getFullYear();  
    var m = date.getMonth();  
    var d = date.getDate();
    var strDate = '';  
    
    if(0 == number){ //0 为月底  
    	var nextMonthFirstDay = new Date(y , m + 1, 1);
    	var oneDay = 1000 * 60 * 60 * 24;
    	var lastDay = new Date(nextMonthFirstDay - oneDay);
		y = lastDay.getFullYear();  
	    m = lastDay.getMonth() + 1;  
	    d = lastDay.getDate();
    	strDate = y + '-' + (m < 10 ? ('0' + m) : m) + '-' + ( d < 10 ? ('0' + d) : d);
    } else if(1 == number) {// 1 为月初
    	m = m + 1;
    	strDate = y + '-' + (m < 10 ? ('0' + m) : m) + '-' + number;
    } else { // 当天
    	m = m + 1;
    	strDate = y + '-' + (m < 10 ? ('0' + m) : m) + '-' + ( d < 10 ? ('0' + d) : d);
    } 
     
    return strDate;  
}

// tab切换
function changeTab(){
	$(".panel-switch li").bind("click",function(){
		$(".panel-switch li").removeClass("select");
		$(this).addClass("select");
		
		var num=$(this).attr("flag");
		$(".panel-result .panel-r").addClass("hidden");
		$(".panel-result .panel-r").addClass("visibility");
		$(".panel-result .panel-r:eq("+parseInt(num)+")").removeClass("hidden");
		$(".panel-result .panel-r:eq("+parseInt(num)+")").removeClass("visibility");
	});
}

//切换竖着的tab
function changeVtab(){
	$(".bills-tab li").bind("click",function(){
		var num=$(this).attr("flag");
		$(this).addClass("select").siblings().removeClass("select");
		$(".bills-result .bills-r:eq("+parseInt(num)+")").removeClass("visibility").siblings().addClass("visibility");
	});
}

/*
 * where_key 修改的时候需要清除的那个字段,
 */
function initCombogrid(gjData,inpId,width,height,where_key)
{
	 if(!gjData.where_value1 || ''== gjData.where_value1 )
     {
     	gjData.where_key1 = '';
     }
	$('#'+inpId).combogrid({
	    panelWidth:width,
	    panelHeight:height,
	    fitColumns:true,
	    pagination:true,
	 /*    rowStyler: function(index,row){
				return 'height: 1.2em';
			}, */
    	pageSize:10,
    	pageList:[10,20,30,40,50],
	    queryParams: {'data' : JSON.stringify(gjData)},
	    idField:'CODE',
	    textField:'NAME',
	    url : url+'/comboboxMgr/getCombogridList.do',  
	    columns:[[
	        {field:'CODE',title:'编码',width:100},
	        {field:'NAME',title:'名称',width:100}
	    ]], 
	    onChange :function(newV,oldV){
	      var grids = $('#'+inpId).combogrid("grid").datagrid("getSelected");
	      if(grids && grids.CODE != newV)
	      {
	      	$('#'+inpId).combogrid("clear");
	      }
	    },
	     keyHandler: {  
                 query: function(q) {  
                     //动态搜索  
                     if(where_key){
                       gjData.where_key1 = '';
                     }
                    
                     gjData.where_sql="("+gjData.name+" LIKE '%"+q+"%' OR "+gjData.code+" LIKE '%"+q+"%')";
                     $('#'+inpId).combogrid("grid").datagrid("reload", {'data' : JSON.stringify(gjData)});  
                     $('#'+inpId).combogrid("setValue", q);  
                 }  
             } 
	});
}

//初始化下拉框datas：查询参数；  inpId：下拉框id；  flag：同步异步标记 1为同步，非1为异步；
//initValue：是否默认选中第一个选项；redisFlag：是否从缓存查询 true：从缓存查  false：从表查
//addOptFlag 默认不传 如果有值代表在下拉选项最前面添加一行'全部'选项
function initCombo(datas,inpId,flag,initValue,redisFlag,addOptFlag){
    var asy = true;
    if('1' == flag){
       asy = false;
    }
   	$.ajax({
         	type : 'post',
         	url : url + '/comboboxMgr/getDictionaryList.do',
         	dataType : 'json',
         	async :asy,
         	data:{'data':JSON.stringify(datas)},
         	success : function(data) {
         		//如果true在第一行添加一列
         		if(addOptFlag){
         			data.splice(0,0,{CODE:'',NAME:'全部'});
         		}
   			$("#"+inpId).combobox({
				data:data,
				valueField:'CODE',
				textField:'NAME',
				//editable:false,
				panelHeight:'100',
				validType : addOptFlag ? '' : 'comboboxCheck['+JSON.stringify(datas)+']'
  			});
  			if(!initValue){
  				$("#"+inpId).combobox({value : data[0].CODE});
  			}
      		},
         	error: function(){
         		$.messager.alert('错误', '系统异常！', 'error');
         	}
    	});
}

//多选 加全部选项
//初始化下拉框datas：查询参数；  inpId：下拉框id；  flag：同步异步标记 1为同步，非1为异步；
//initValue：是否默认选中第一个选项；redisFlag：是否从缓存查询 true：从缓存查  false：从表查
//addOptFlag 默认不传 如果有值代表在下拉选项最前面添加一行'全部'选项
function initComboMultiple(datas,inpId,flag,initValue,redisFlag,addOptFlag){
    var asy = true;
    if('1' == flag){
       asy = false;
    }
    var selectarray=[];  
   	$.ajax({
         	type : 'post',
         	url : url + '/comboboxMgr/getDictionaryList.do',
         	dataType : 'json',
         	async :asy,
         	data:{'data':JSON.stringify(datas)},
         	success : function(data) {
         		//如果true在第一行添加一列
         		//将全部数据데code存到数组
         		for(var i=0;i<data.length;i++){
				selectarray.push(data[i].CODE);
			}
         		if(addOptFlag){
         			data.splice(0,0,{CODE:'',NAME:'全部'});
         		}
         		
   			$("#"+inpId).combobox({
				data:data,
				valueField:'CODE',
				textField:'NAME',
				//editable:false,
				panelHeight:'100',
				validType : addOptFlag ? '' : 'comboboxCheck['+JSON.stringify(datas)+']',
				onSelect : function(record){
					
					if("" == record.CODE){
						$("#"+inpId).combobox("setValues",selectarray);
					}
				}
  			});
  			if(!initValue){
  				$("#"+inpId).combobox({value : data[0].CODE});
  			}
      		},
         	error: function(){
         		$.messager.alert('错误', '系统异常！', 'error');
         	}
    	});
}

//加载操作菜单
function initAction(menu_code, button_code){
	var data = {'menu_code':menu_code, 'button_code' : button_code};
	$.ajax({
		type : 'post',
       	url : url + '/businessConsole/role/getRoleMenuButton.do',
       	data : data,
       	dataType : 'json',
       	async : false,
       	success : function(result) {
       		 var listhtml = "";
        	 for(var i = 0; i < result.length; i++){
        	 	if(result[i].remark && result[i].remark == 'disableBtn'){
        	 		listhtml += '<a id="disableBtn" href="javascript:void(0)" class="easyui-linkbutton" '
	        	 			+ 'button_code="' + result[i].button_code
	        	 			+ '" data-options="iconCls:\'' 
	        	 			+ result[i].button_style
	        	 			+ '\'" onclick="'
	        	 			+ result[i].button_url + '(this)">'
	        	 			+ result[i].button_name + '</a>';
        	 	}else{
		        	listhtml += '<a href="javascript:void(0)" class="easyui-linkbutton" '
		        	 			+ 'button_code="' + result[i].button_code
		        	 			+ '" data-options="iconCls:\'' 
		        	 			+ result[i].button_style
		        	 			+ '\'" onclick="'
		        	 			+ result[i].button_url + '(this)">'
		        	 			+ result[i].button_name + '</a>'; 
        	 	}
        	 }
	         $("#buttonContent").html(listhtml);
	         $.parser.parse($("#buttonContent"));
	         //loadData();
		}
	});
}	
// 高级查询 参数：flag:up收起，down展开，newHeight：设置的高度
function showAdvSearch(flag, newHeight){
	var c = $('#cc');
	var np = c.layout('panel','north');//获取north面板
	var cp = c.layout('panel','center');//获取center面板
	var oldNpHeight = cp.panel('panel').outerHeight();//north原内容高度

	if('down' == flag){
		$("#searchForm .adv-search-up").removeClass("hidden");
		$("#searchForm .adv-search-down").addClass("hidden");
		np.panel('resize', {height:newHeight});//north重新设置高度
		
		$("#searchForm .tr-search").removeClass("hidden");
	} else {
		$("#searchForm .adv-search-down").removeClass("hidden");
		$("#searchForm .adv-search-up").addClass("hidden");
		np.panel('resize', {height:newHeight});//north重新设置位原始高度
		$("#searchForm .tr-search").addClass("hidden");
	}
	
	var newNpHeight = np.panel('panel').outerHeight();//north新内容高度
   
	cp.panel('resize', {height:(cp.height() - newNpHeight + oldNpHeight)});//center重新设置高度
   
	//layout重新设置高度
    c.layout('resize',{
         height: (c.height() + newNpHeight - oldNpHeight)
     });
     
    $("#searchForm").css("height", np.height()- 35);
}

// 格式化数值 -- 参数：value当前值，precision精度（小数点后面的位数）
function formatNumbers(val,precision){
 
	if(!val || '' == val){
		return fmoney(0, precision);
	} else if(val != ''){
		return fmoney(val, precision);
	} else {
		return '';
	}
}
// 格式化金额
function fmoney(s, n)   
{   
  /*  n = n > 0 && n <= 20 ? n : 2;   
   s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";   
   var l = s.split(".")[0].split("").reverse(),   
   r = s.split(".")[1];   
   t = "";   
   for(i = 0; i < l.length; i ++ )   
   {   
      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");   
   }    */
   return (parseFloat(s).toFixed(n) + '').replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,');
}

// 校验开始日期和结束日期
function validDate(startDate, endDate){
	if(!startDate || !endDate){
		return;
	} else{
		if(startDate < endDate){
			return true;
		} else {
			return false;
		}
	}
}
// 验证是否包含非法字符
$.extend($.fn.validatebox.defaults.rules, {
    unnormal: {
             validator: function (value) {  
                 return !/[`~!@#$%^&*\+=<>?:"{}|\/;'\\[\]~！@#￥%……&*（）——\+={}|《》？：“”【】、；‘’。、]/im.test(value);  
             },  
             message: '输入值不能包含其他非法字符'  
         }
});
		
//移除前后空格	
String.prototype.Trim = function(){
	return this.replace(/(^\s*)|(\s*$)/g, "");
};
	
// 查重
$.extend($.fn.validatebox.defaults.rules, {
    duplicateCheck: {
		validator: function(value){
		var data = {};
		data.TABLE_NAME = TABLE_NAME;
		data.COLUMNS_NAME = COLUMNS_NAME;
		data.VALUE = value;
		data.MODI_VALUE = MODI_VALUE;
			var checkR = $.ajax({
					async : false,
					cache : false,
					type : 'post',
					url : url+'/masterData/countryCodeMgr/duplicateCheck.do',
					data : {'data' : JSON.stringify(data)},
					dataType : 'json'
				}).responseText;
					return checkR =="0";
		},
		message: '已存在'
    }
});

// 传参查重
$.extend($.fn.validatebox.defaults.rules, {
    repeatValCheck: {
		validator: function(value,param){
		param[0].VALUE = value.Trim();
		var checkR = $.ajax({
				async : false,
				cache : false,
				type : 'post',
				url : url+'/masterData/countryCodeMgr/duplicateCheck.do',
				data : {'data' : JSON.stringify(param[0])},
				dataType : 'json'
			}).responseText;
			return checkR =="0";
		},
		message: '已存在'
    }
});

//验证下拉框的数据
$.extend($.fn.validatebox.defaults.rules, {
    comboboxCheck: {
		validator: function(value,param){
		param[0].ck_value = value;
		var checkR = $.ajax({
				async : false,
				cache : false,
				type : 'post',
				url : url+'/comboboxMgr/comboboxCheck.do',
				data : {'data' : JSON.stringify(param[0])},
				dataType : 'json'
			}).responseText;
			return checkR != "0";
		},
		message: '不存在'
    }
});

//字段title
function fieldFormatter(val,row){
	if(val){
      		return "<span title='" + val + "'>" + val + "</span>";
	}else{
		return val;
	}
}
		
/**
 * 
 * 获取当前时间
 */
function p(s) {
    return s < 10 ? '0' + s: s;
}

//获取当前日期
function getNow(){
	var myDate = new Date();
	//获取当前年
	var year= myDate.getFullYear();
	//获取当前月
	var month = myDate.getMonth()+1;
	//获取当前日
	var date = myDate.getDate(); 
	return year + '-' + p(month) + "-" + p(date);
}

//获取当前日期
function getNow(){
	var myDate = new Date();
	//获取当前年
	var year= myDate.getFullYear();
	//获取当前月
	var month = myDate.getMonth()+1;
	//获取当前日
	var date = myDate.getDate(); 
	return year + '-' + p(month) + "-" + p(date);
}

//获取当前时间 年月日时分秒
function getNowTime(){
	var myDate = new Date();
	//获取当前年
	var year= myDate.getFullYear();
	//获取当前月
	var month = myDate.getMonth()+1;
	//获取当前日
	var date = myDate.getDate();
	//当前时
	var hours = myDate.getHours();
	//当前分
	var minutes = myDate.getMinutes();
	//当前秒
	var seconds = myDate.getSeconds(); 
	return year + '-' + p(month) + '-' + p(date) + ' ' + p(hours) + ':' + p(minutes) + ':' + p(seconds);
}

//数组去重
function unique(array){ 
	var n = []; //一个新的临时数组 
	//遍历当前数组 
	for(var i = 0; i < array.length; i++){ 
		//如果当前数组的第i已经保存进了临时数组，那么跳过， 
		//否则把当前项push到临时数组里面 
		if (n.indexOf(array[i]) == -1){
			n.push(array[i]);
		} 
	} 
	return n; 
}

function showDelete(obj){
   obj.find(".m-del").show();
}
	
function hideDelete(obj){
   obj.find(".m-del").hide();
}
</script>
<style>
.checkps{background-color: #00b1bd;width: 84px;overflow: hidden;height: 26px;cursor: pointer;text-align: center;line-height: 26px;color: white;border-radius:2px;}
.fileUploader{font-size:200px;border: 1px solid #ddd;width: 80px;height: 26px;background: #fff;position: relative;top: -26px;left: 0px;opacity: 0;}
/* iframe table 修正字体变大 */
table{
	font-size: 12px;
}
*{
	font-family:微软雅黑
}
/* .panel-body {
	overflow: hidden;
}
.combo-panel{
	overflow-y:scroll; 
}
.tabs-panels .panel-body {
	overflow-y:auto; 
} */
/*  当宽度在1200与960之间时使用次效果  chh 处理小屏展示不全的为题 */
/* @media ( max-width: 1200px ) and (min-width: 960px) {
  .panel-body {
		overflow: auto;
	} 
 } */
.panel-noscroll .easyui-layout { min-width:auto;}
input[type=checkbox],input[type=raido]{ border:none;}
</style>

