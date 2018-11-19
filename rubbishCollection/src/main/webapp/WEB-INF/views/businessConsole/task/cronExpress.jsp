<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp"%>
 <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>

<link rel="stylesheet" type="text/css" href="${ct }/css/style.css" />
<script type="text/javascript" src="${ct }/plugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript">
var path = "${ct}/";

function displays(nowdisplay)
	{

		var cronThis = document.getElementById("nowdisplay").value;
				if(nowdisplay!=cronThis)
		{
			document.getElementById(cronThis).style.display = "none";
			//将老Tab置灰
			//document.getElementById(cronThis+"Tab").className = document.getElementById(nowdisplay+"Tab").className.substring(0,document.getElementById(nowdisplay+"Tab").className.length-10);
			//将新Tab亮起
			//document.getElementById(nowdisplay+"Tab").className = 	document.getElementById(nowdisplay+"Tab").className +" selected ";
			
			document.getElementById(nowdisplay).style.display = "block";
			document.getElementById("nowdisplay").value = nowdisplay;
		}			
	}
	
	function getExp($sjDialog,$pjq)
	{
		var mycron = "-1";
		var nextTime = "";
		var nowdisplay = document.getElementById("nowdisplay").value;
		var second = getSecond();
		var minute = getMinute();
		var hour = getHour();
		var day = getDay();
		var month = getMonth();
		var year = getYear();
		
		if (nowdisplay == "Hourly") {			
			var val = $('#hours').timespinner('getValue');
			if(val == ''){
				parent.$.messager.alert('错误','请选择时间!','error');
				return;
			}
			var array=val.split(":");
			var hourPart = "*";
			var minutePart = Number(array[1]);
			var secondPart = Number(array[2]);
			mycron = secondPart + " " + minutePart + " " + hourPart + "/1 * * ?";	
			if(Number(minutePart) < 10){
				minutePart = "0" + minutePart;
			}
			if(Number(secondPart) < 10){
				secondPart = "0" + secondPart;
			}
			if(Number(hour) + 1 >= 10){
				nextTime = year + "-" + month + "-" + day + " " 
					+ (Number(hour) + 1) + ":" + minutePart + ":" + secondPart;
			}else if(Number(hour) + 1 == 24){
				nextTime = year + "-" + month + "-" + Number(day)+1 + " 00:"
					+ minutePart + ":" + secondPart;
			}else{
				nextTime = year + "-" + month + "-" + day + " 0" + (Number(hour)+1) 
					+ ":" + minutePart + ":" + secondPart;
			}			
		}
		else if(nowdisplay == "Daily")
		{
			
			var val = $('#dailys').val();
			if(val==''){
				parent.$.messager.alert('错误','请选择时间!','error');
				return;
			}
			
			var array = val.split(":");
			var hourPart = Number(array[0]);
			var minutePart = Number(array[1]);
			var secondPart = Number(array[2]);
			
			mycron = secondPart + " " + minutePart + " " + hourPart + " 1/1 * ?";
			
			if(Number(day)+1 >= 10 && Number(day)+1 < dayOfMonth(year, month)){
				nextTime = year + "-" + month + "-" + (Number(day)+1) + " " 
					+ hourPart + ":" + minutePart + ":" + secondPart;
			}else if(Number(day)+1 > dayOfMonth(year, month)){
				nextTime = year + "-" + (Number(month)+1) + "-01" + hourPart + ":"
					+ minutePart + ":" + secondPart;
			}else{
				nextTime = year + "-" + month + "-0" + (Number(day)+1) + " "
					+ hourPart + ":" + minutePart + ":" + secondPart;
			}
			
		}
		else if(nowdisplay == "Weekly")
		{
			var week = "";
			var huang = document.all['txtWeekly'];
			var mydate = new myDate();
			
			for(i = 0; i < huang.length; i++){
				if(huang[i].checked == true)
				{
				   week = week+huang[i].value + ",";
				}
  		  	}
			
			if(week.length==0)
			{
				parent.$.messager.alert('错误','请选择时间!','error');
				return;
			}
			week = week.substring(0,week.length-1);
			
			var weekArray=[];
			weekArray[0]='MON';
			weekArray[1]='TUE';
			weekArray[2]='WED';
			weekArray[3]='THU';
			weekArray[4]='FRI';
			weekArray[5]='SAT';
			weekArray[6]='MON';
			weekArray[7]='SUN';
			
			var val = $('#weeks').val();
			var array = val.split(":");
			
			var hourPart = Number(array[0]);
			var minutePart = Number(array[1]);
			var secondPart = Number(array[2]);	
			
			var weekval = week.split(",");
			for(var i=0; i<weekval.length; i++){
				if(i == 0){
					mycron = secondPart + " " + minutePart + " " + hourPart 
						+ " ? * " + weekArray[Number(weekval[i])];
				}else{
					mycron = mycron + ',' + weekArray[Number(weekval[i])];
				}
				
			}
			var weekTime = [];
			var dyweek = [];
			var xyweek = [];
			

			for ( var i = 0; i < weekval.length; i++) {
				if (Number(weekval[i]) < Number(dayOfWeek())) {
					xyweek.push(weekval[i]);
				} else {
					dyweek.push(weekval[i]);
				}
			}

			if (dyweek.length != 0) {
				weekTime = mydate.currWeek();
				nextTime = weekTime[Number(dyweek[0])] + " " + val;
			} else {
				weekTime = mydate.nextWeek();
				nextTime = weekTime[Number(xyweek[0])] + " " + val;
			}
			
/*			if(Number(week)<Number(dayOfWeek())){
				weekTime=mydate.nextWeek();
			}else{
				weekTime=mydate.currWeek();
			}
			nextTime=weekTime[Number(week)]+" "+val;*/
		} else if (nowdisplay == "Monthly") {
			var val = $('#months').val();
			if (val == '') {
				parent.$.messager.alert('错误', '请选择时间!', 'error');
				return;
			}
			var array = val.split(" ");
			var date = array[0];
			var time = array[1];
	
			var ChoiceMonth = Number(date.split("-")[1]);
			var ChoiceDay = Number(date.split("-")[2]);
			var hourPart = Number(time.split(":")[0]);
			var minutePart = Number(time.split(":")[1]);
			var secondPart = Number(time.split(":")[2]);
			
		//	mycron = secondPart+" "+minutePart+" "+hourPart+" "+ChoiceDay+" 1/"+ChoiceMonth+" ? * ";
			mycron = secondPart + " " + minutePart + " " + hourPart + " "
				+ ChoiceDay + " * ?";
			if (Number(month) + 1 > 12) {
				nextTime = Number(year) + 1 + "-01-" + ChoiceDay + " " + hourPart
						+ ":" + minutePart + ":" + secondPart;
			} else if (Number(month) + 1 >= 10 && Number(month) + 1 <= 12) {
				nextTime = year + "-" + ChoiceMonth + "-" + ChoiceDay + " "
						+ hourPart + ":" + minutePart + ":" + secondPart;
			} else {
				nextTime = year + "-0" + ChoiceMonth + "-" + ChoiceDay + " "
						+ hourPart + ":" + minutePart + ":" + secondPart;
			}
		} else if (nowdisplay == "Yearly") {
			var val = $('#years').val();
			if (val == '') {
				parent.$.messager.alert('错误', '请选择时间!', 'error');
				return;
			}
			var array = val.split(" ");
			var date = array[0];
			var time = array[1];
			
			var choiceYear = Number(date.split("-")[0]);
			var ChoiceMonth = Number(date.split("-")[1]);
			var ChoiceDay = Number(date.split("-")[2]);
			var hourPart = Number(time.split(":")[0]);
			var minutePart = Number(time.split(":")[1]);
			var secondPart = Number(time.split(":")[2]);
			
			mycron = secondPart + " " + minutePart + " " + hourPart + " "
					+ ChoiceDay + " " + ChoiceMonth + " ?";
			nextTime = Number(year) + 1 + "-0" + ChoiceMonth + "-" + ChoiceDay
					+ " " + hourPart + ":" + minutePart + ":" + secondPart;
				

		} else if (nowdisplay == "Simple") {
			var val = $('#simples').val();
			if (val == '') {
				parent.$.messager.alert('错误', '请选择时间!', 'error');
				return;
			}
			var array = val.split(" ");
			var date = array[0];
			var time = array[1];
			var choiceYear = Number(date.split("-")[0]);
			var ChoiceMonth = Number(date.split("-")[1]);
			var ChoiceDay = Number(date.split("-")[2]);
			var hourPart = Number(time.split(":")[0]);
			var minutePart = Number(time.split(":")[1]);
			var secondPart = Number(time.split(":")[2]);
			mycron = secondPart + " " + minutePart + " " + hourPart + " * * ?";
			var crontime = choiceYear + ChoiceMonth + ChoiceDay + hourPart
					+ minutePart + secondPart;
			var nowtime = year + month + day + hour + minute + second;
			nextTime = Number(year) + 1 + "-0" + ChoiceMonth + "-" + ChoiceDay
					+ " " + hourPart + ":" + minutePart + ":" + secondPart;
		} else if (nowdisplay == "Minsly") {
			var val = $('#mins').val();
			if (val == '') {
				parent.$.messager.alert('错误', '请填写分钟!', 'error');
				return;
			}
			if (val == '0') {
				parent.$.messager.alert('错误', '请填写大于的整数!', 'error');
				return;
			}
			mycron = "0 0/" + val + " * * * ?";
			nextTime = year + "-" + month + "-" + day + " " + hour + ":"
					+ (Number(minute) + Number(val)) + ":" + second;
	
		}
		if (mycron != "-1") {
			document.getElementById("mycron").value = mycron;
			// window.parent.document.getElementById('CRON_EXPRESS').value=mycron;
			$sjDialog.dialog('close');
			return mycron + "---" + nowdisplay + "---" + nextTime;
		}
	
	}
	

	function toShowNow() {
		var s = "";
		var d = new Date();
		var vYear = d.getFullYear();
		var vMon = d.getMonth() + 1;
		var vDay = d.getDate();
		
		var h = d.getHours();
		var m = d.getMinutes();
		var se = d.getSeconds();
		
		s = vYear + "-" + (vMon < 10 ? "0" + vMon : vMon) + "-"
				+ (vDay < 10 ? "0" + vDay : vDay) + " " + (h < 10 ? "0" + h : h)
				+ ":" + (m < 10 ? "0" + m : m) + ":" + (se < 10 ? "0" + se : se);
	}	

	function getYear() {
		var d = new Date();
		var vYear = d.getFullYear();
		return vYear;
	}
	function getMonth() {
		var d = new Date();
		var vMon = d.getMonth() + 1;
		return vMon;
	}
	function getDay() {
		var d = new Date();
		var vDay = d.getDate();
		return vDay;
	}
	function getHour() {
		var d = new Date();
		var h = d.getHours();
		return h;
	}
	function getMinute() {
		var d = new Date();
		var m = d.getMinutes();
		return m;
	}
	function getSecond() {
		var d = new Date();
		var se = d.getSeconds();
		return se;
	}
	function dayOfWeek() {
		var now = new Date();
		var nowDayOfWeek = now.getDay();
		return nowDayOfWeek;
	}
	function dayOfMonth(year, month) {
		var day = new Date(year, month, 0);
		var daycount = day.getDate();
		return Number(daycount);
	}
	function myDate() {
		function formatDate(d) {
			var _todayDate = d.getDate();
			var _year = d.getYear();
			!document.addEventListener || (_year += 1900);
			var _month = d.getMonth() + 1;
			return _year + "-" + _month + "-" + _todayDate;
		}
		var now = new Date(), dd = new Date();
		var n = now.getDay();
		function check(date, m) {
			for ( var i = 0; i < 7; i++) {
				var datevalue = now.getDate() - n + m + i + 1;
				var month = now.getMonth() + 1;
				if (month == 1 || month == 3 || month == 5 || month == 7
						|| month == 8 || month == 10 || month == 12) {
					if (datevalue > 31) {
						if (m < 0 && month - 1 != 2) {
							dd.setDate(31 - (30 - datevalue));
							dd.setMonth(month)
						} else {
							dd.setDate(datevalue - 31);
							dd.setMonth(month);
						}
					} else {
						dd.setDate(datevalue);
					}
				} else if (month == 4 || month == 6 || month == 9 || month == 11) {
					if (datevalue > 30) {
						if (m < 0 && month - 1 != 2) {
							dd.setDate(31 - (30 - datevalue));
							dd.setMonth(month)
						} else {
							dd.setDate(datevalue - 30);
							dd.setMonth(month);
						}
					} else {
						dd.setDate(datevalue);
					}
				}// liehuo.net Lie huo . net
				else if (month == 2) {
					if (datevalue > 28) {
						dd.setDate(datevalue - 28);
						m >= 0 ? dd.setMonth(month + 1) : dd.setMonth(month - 1);
					} else {
						dd.setDate(datevalue);
					}
				}
				date.push(formatDate(dd));
			}
		}
		return {
			currWeek : function() {
				var date = [];
				check(date, 0);
				return date;
			},
			nextWeek : function() {
				var date = [];
				check(date, 7);
				return date;
			},
			nextextWeek : function() {
				var date = [];
				check(date, 14);
				return date;
			},
			prevWeek : function() {
				var date = [];
				check(date, -7);
				return date;
			}
		}
	}
	
	
	
</script>

</head>
<body style="overflow-x:hidden;overflow-y:hidden">
<table width="100%">
	<tr>
		<td>
		<input type="hidden" id="nowdisplay" value="Simple">
		<table cellpadding="0" cellspacing="0">

			<tr>
				<td height="125" valign="top">

				<div class="tabpanel4" style="width: 700px" id="id3">
				<div class="tab-row">
				<ul>
					<!-- <li id ="SimpleTab" class="tab0">
					<a href="#" onClick="displays('Simple')" id="id16">
						<span>简单</span></a>
					</li> -->
				    <li id ="MinsTab" class="tab2">
					<a href="#" onClick="displays('Minsly')" id="id20">
						<span>每分钟</span></a>
					</li>
					<li id ="HourlyTab" class="tab3">
					<a href="#" onClick="displays('Hourly')" id="id17">
						<span>每小时</span></a>
					</li>
					<li id ="DailyTab" class="tab4">
					<a href="#" onClick="displays('Daily')" id="id18"><span>每天</span></a>
					</li>
					<li id ="WeeklyTab" class="tab5">
					<a href="#" onClick="displays('Weekly')" id="id19"><span>每周</span></a>
					</li>
					<li id ="MonthlyTab" class="tab6">
					<a href="#" onClick="displays('Monthly')" id="id1a"><span>每月</span></a>
					</li>
					<li id ="MonthlyTab" class="tab7">
					<a href="#" onClick="displays('Yearly')" id="id1a"><span>每年</span></a>
					</li>
				</ul>
				</div>
				</div>

				<div class="tab-panel" id="Daily" style="display:none">
				<table >
					<tr >
						<td valign="middle" style="padding-left:10px;">执行时间</td>
						<td valign="middle" style=""><input type="text" id="dailys" readonly onfocus="WdatePicker({skin:'whyGreen',dateFmt:'HH:mm:ss'})" class="Wdate" style="height: 25px;"/>
					</tr>
				</table>
				</div>
				<div class="tab-panel"  id="Yearly" style="display:none">
				<table>
					<tr>
						<td valign="middle" style="padding-left:10px;">执行时间</td>
						<td style=""><input type="text" id="years" readonly onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" style="height: 25px;"/>
					</tr>
				</table>
				</div>
				<div class="tab-panel"  id="Monthly" style="display:none">
				<table>
					<tr>
						<td valign="middle" style="padding-left:10px;">执行时间</td>
						<td style=""><input type="text" id="months" readonly onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" style="height: 25px;"/>
					</tr>
				</table>
				</div>
				<div class="tab-panel"  id="Simple" >
				<table>
					<tr>
						<td valign="middle" style="padding-left:10px;">执行时间</td>
						<td style=""><input type="text" id="simples" readonly onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" style="height: 25px;"/>
					</tr>
				</table>
				</div>
				
					<div class="tab-panel"  id="Minsly" style="display:none">
				<table >
					<tr >		
						<td  style="padding-left:10px;">执行时间</td>				
						<td  style=""><input Id="mins" name="mins" class="easyui-numberbox" type="text" data-options="showSeconds: true"></input>					
						<br/><a style="color: red;">每隔几分钟执行一次，即输入几</a></td>								
					</tr>				
				</table>
				</div>
				
				<div class="tab-panel"  id="Hourly" style="display:none">
				<table >
					<tr >		
						<td  style="padding-left:10px;">执行时间</td>				
						<td  style=""><input Id="hours" name="hours" class="easyui-timespinner" type="text" data-options="showSeconds: true,editable:false"></input>					
						<br/><a style="color: red;">该执行时间分钟和秒数设置有效，小时设置无效</a></td>								
					</tr>				
				</table>
				</div>
				<div class="tab-panel"  id="Weekly" style="display:none">
				<table>
					<tr>
						<td >
						<table>
							<tr>
								<td><input type="checkbox" id="id42"
									name="txtWeekly" value="0"
									class="wicket-id41"> </input></td>
								<td>周一</td>
								<td><input type="checkbox" id="id43"
									name="txtWeekly" value="1"
									class="wicket-id41" /></td>
								<td>周二</td>
								<td><input type="checkbox" id="id44"
									name="txtWeekly" value="2"
									class="wicket-id41" /></td>
								<td>周三</td>
								<td><input type="checkbox" id="id45"
									name="txtWeekly" value="3"
									class="wicket-id41" /></td>
								<td>周四</td>
								<td><input type="checkbox" id="id46"
									name="txtWeekly" value="4"
									class="wicket-id41" /></td>
								<td>周五</td>
								<td><input type="checkbox" id="id47"
									name="txtWeekly" value="5"
									class="wicket-id41" /></td>

								<td>周六</td>
								<td><input type="checkbox" id="id48"
									name="txtWeekly" value="6"
									class="wicket-id41" /></td>
								<td>周日</td>
							</tr>
						</table>
						</td>
					</tr>
								
					<tr>
						<td colspan="1"style="padding-left:10px;" >执行时间<input type="text" id="weeks" readonly onfocus="WdatePicker({skin:'whyGreen',dateFmt:'HH:mm:ss'})" class="Wdate" style="height: 25px;"/></td>
					</tr>			
				</table>
				</div>
			<tr style="display: none;">
				<td height="40">
				
				 CRON表达式：
				 <input type="text" style="width: 200px" name="mycron" id="mycron" />
				</td>
				
			</tr>
			<tr style="display: none;" >
				<td height="40" style="padding-left: 100px">
				<button class="button" name="btnGenerate" id="idf" onClick="getExp()">
				 生成</button>&nbsp;&nbsp;
				 <button class="button" name="btnGenerate" id="idf" onClick="ret()">
				 确认返回</button>
				 </td>
				 </tr>


						

		</table>


		</td>

	</tr>

	<tr>

		<td></td>
	</tr>








</table>





</div>


</body>
<script type="text/javascript">
	
	
</script>
<html>
  