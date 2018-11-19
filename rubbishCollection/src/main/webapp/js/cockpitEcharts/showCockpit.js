//获取当前日期
Date.prototype.Format = function (fmt) { //author: meizz 
		var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "h+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
    	};
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
};
var currentdate = new Date().Format("yyyy-MM-dd");
   /*var mydate = new Date();
   var reg = new RegExp("/","g");
   var currentdate = mydate.toLocaleDateString().replace(reg,"-");*/
	var PAGE1=1;//页数
	var PAGE2=1;//页数
	var PAGE3=1;//页数
	var PAGE4=1;//页数
	var PAGE5=1;//页数
	var PAGE6=1;//页数
	var PAGE7=1;//页数
$(document).ready(function(){
   var groupFlag = 0;
   $(".cockpit-word").html(title);
   var groupFlag = 0;
   
   if("0" == flag){
	   $("#part-one").show();
	   initUserRoom(currentdate,groupFlag);//增长用户和房间折线图
   }
   if("1" == flag){
	   $("#part-two").show();
	   initUserRegist();//今日各项目增加情况圆形图
   }
   if("2" == flag){
	   $("#part-three").show();
	   initAreaRoom(PAGE1);//各项目下载情况
   }
   if("3" == flag){
	   $("#part-four").show();
	   initProjectActiveUser(PAGE2);//本日各项目活跃用户数
   }
   if("4" == flag){
	   $("#part-five").show();
	   initUserActive(currentdate,groupFlag);//活跃用户数增长情况
   }
   if("5" == flag){
	   $("#part-six").show();
	   initPvUv(currentdate);//本周活跃用户趋势
   }
   if("6" == flag){
	   $("#part-seven").show();
	   initProjectPv(PAGE3);//本日各项目使用PV
   }
   if("7" == flag){
	   $("#part-eight").show();
	   initUserRoomDept(currentdate,groupFlag);//子公司下增长用户和房间折线图
   }
   if("8" == flag){
	   $("#part-nine").show();
	   initUserRegistDept();//子公司下今日各项目增加情况圆形图
   }
   if("9" == flag){
	   $("#part-ten").show();
	   initAreaRoomDept(PAGE4);//子公司下总房间和绑定房间柱状图
   }
   if("10" == flag){
	   $("#part-eleven").show();
	   initUserActiveDept(currentdate,groupFlag);//子公司下活跃用户用户数  折线图
   }
   if("11" == flag){
	   $("#part-twelve").show();
	   initProjectPvDept(PAGE5);//本日各项目使用占比
   }
   if("12" == flag){
	   $("#part-thirteen").show();
	   initUserRoomArea(currentdate,groupFlag);//小区下增长用户和房间折线图
   }
   if("13" == flag){
	   $("#part-fourteen").show();
	   initAreaRoomArea(PAGE6);//小区下总房间和绑定房间柱状图
   }
   if("14" == flag){
	   $("#part-fifteen").show();
	   initUserActiveArea(currentdate,groupFlag);//小区下活跃用户用户数  折线图
   }
   if("15" == flag){
	   $("#part-sixteen").show();
	   initProjectPvArea(PAGE7);//小区下本日各项目使用PV
   }
});
 var userRoomRel = {};
 //增长用户和房间折线图
 function initUserRoom(date,groupFlag){
 		var data = {};
	  	data.SIGN = "0";
    	data.END_DATE = date;
    	
    	if(0==groupFlag){
    		passurl = url + '/cockpit/CockpitController/getRegisBindDayTotal.do';
    		data.NUMBER = "7";
    	}else if(1==groupFlag){
    		passurl = url + '/cockpit/CockpitController/getRegisBindWeekTotal.do';
    		data.NUMBER = "49";
    	}else if(2==groupFlag){
    		passurl = url + '/cockpit/CockpitController/getRegisBindMonthTotal.do';
    		data.NUMBER = "210";
    	}
    	
		$.ajax({
			url : passurl,
			data :{'data':JSON.stringify(data)},
			type : 'post',
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
				if(null != data && "" != data){
					if(currentdate == data[6].DATE){
						$("#userRoomRight").hide();
					}else{
						$("#userRoomRight").show();
					}
					userRoomRel = data;
					userRoomRel.groupFlag = groupFlag;
					createUserRoomData(data);
				}
			}
		});
	}
	
//echarts
 function createUserRoomData(data){
 	var chart = {
 		legend:['用户数', '下载数'],	
 		xAxis:[],
 		//用户数
 		dd_dataset:[],
 		//下载数
 		ee_dataset:[]
 	};
 	$.each(data,function(i,v){
 	   //x轴数据封装
 		chart.xAxis.push(v.DATE);
 	   //用户数数据封装
 	    chart.dd_dataset.push(v.OWNER_ADD_TOTAL);
 	    //下载数数据封装
 	    chart.ee_dataset.push(v.BIND_ROOM_TOTAL);
     }) ; 
 	//绘制echarts图表
 	initUserRoomEcharts(chart);
 }
 
 //echarts
 function initUserRoomEcharts(data){
	var height = $(".easyui-panel").height() - 85;
	$("#RoomBrokenLine").height(height);
 	var myChart = echarts.init(document.getElementById("RoomBrokenLine"));
 	var option = {
	    tooltip: {
	        trigger: 'item',
	        formatter: '{a} <br/>{b} : {c}'
	    },
	    tooltip: {
	        trigger: 'axis'
	    },
	    legend: {
	    	bottom:"0",
	        data: data.legend     
	    },
	    xAxis: {
	        type: 'category',
	        name: '',
	        splitLine: {show: false},	        
	        data: data.xAxis,
	        axisLabel: {  
	        	interval:0,  
        	    textStyle: {
                   fontSize:'10'
                }
        	},
	    },
	    grid: {
	    	top:10,
	        left: '5%',
	        right: '5%',
	        bottom: '10%',
	        containLabel: true
	    },
	    yAxis: {
	        type: 'value',
	        name: '',
	        min:0
	    },
	    series: [
	        {
	            name: '用户数',
	            type: 'line',
	            itemStyle: {
	                normal: {
	                    color: "#ffc900",
	                    lineStyle: {
	                        color: "#ffc900"
	                    }
	                }
	            },
	            data: data.dd_dataset
	        },
	        {
	            name: '下载数',
	            type: 'line',
	            itemStyle: {
	                normal: {
	                    color: "#00b3ec",
	                    lineStyle: {
	                        color: "#00b3ec"
	                    }
	                }
	            },
	            data: data.ee_dataset
	        }
	    ]
	};
 	myChart.setOption(option);
 }
	
	/* 日增 */
  	$("#userRoomDay").click(function(){
  		var dates = currentdate;
  		var groupFlag = 0;
  		initUserRoom(dates,groupFlag);
  	});
	
	/* 周增 */
  	$("#userRoomWeek").click(function(){
  		var dates = currentdate;
  		var groupFlag = 1;
  		initUserRoom(dates,groupFlag);
  	});
	
  	/* 月增 */
  	$("#userRoomMonth").click(function(){
  		var dates = currentdate;
  		var groupFlag = 2;
  		initUserRoom(dates,groupFlag);
  	});
  	
	/* 左分页 */
  	$("#userRoomLeft").click(function(){
  		if(0 == userRoomRel.groupFlag){
  			var dates = addDate(userRoomRel[0].DATE,-1);
  		}else if(1 == userRoomRel.groupFlag){
  			var dates = addDate(userRoomRel[0].DATE,-7);
  		}else if(2 == userRoomRel.groupFlag){
  			var dates = addDate(userRoomRel[0].DATE,-30);
  		}
  		var groupFlag = userRoomRel.groupFlag;
  		initUserRoom(dates,groupFlag);
  	});
  	
  	/* 右分页 */
  	$("#userRoomRight").click(function(){
  		if(0 == userRoomRel.groupFlag){
  			var dates = addDate(userRoomRel[6].DATE,7);
  		}else if(1 == userRoomRel.groupFlag){
  			var dates = addDate(userRoomRel[6].DATE,49);
  		}else if(2 == userRoomRel.groupFlag){
  			var dates = addDate(userRoomRel[6].DATE,210);
  		}
  		var groupFlag = userRoomRel.groupFlag;
  		initUserRoom(dates,groupFlag);
  	});
	
	 //今日各项目增加情况
    function initUserRegist(){
    	var data = {};
    	data.SIGN = "0";  
    	var BEGINDATE = $('#DATE_START').datebox('getValue');
  		var ENDDATE = $('#DATE_END').datebox('getValue');
  		
  		if(""!=BEGINDATE && "" != ENDDATE){
  			if(BEGINDATE>ENDDATE){
  	  			parent.$.messager.alert('错误', '结束时间不可小于开始时间', 'error');
  	  			return;
  	  		}
  			data.FLAG = "1";
  			data.BEGINDATE = BEGINDATE;
  	  		data.ENDDATE = ENDDATE;
  		}else if(""!=BEGINDATE && "" == ENDDATE){
  			data.FLAG = "2";
  			data.BEGINDATE = BEGINDATE;
  		}else if(""==BEGINDATE && "" != ENDDATE){
  			data.FLAG = "3";
  	  		data.ENDDATE = ENDDATE;
  		}
  		
		$.ajax({
			url : url + '/cockpit/CockpitController/getAreaRoomBindTotal.do',
			type : 'post',
			data :{'data':JSON.stringify(data)},
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
				if(null != data && "" != data){
					
					var datas = data.list;
					var map = data.map;
					createCircularData(datas,map);
				}
			}
		});
	}
	
	//创建饼图加载数据数组
	function createCircularData(data,map){
		var chart = {
    		legend:[],	
    		dataset:[]
    	};
    	$.each(data,function(i,v){
    		chart.legend.push(v.AREA_NAME);
    		chart.dataset.push({
    				name: v.AREA_NAME,
	               	value: v.ROOM_BIND_AREA
	        })
        });
      	initCircularChart(chart,map);
	}
	
	//加载圆饼图
	function initCircularChart(datas,map) {
		//获取当前父元素的宽度
		var width = $("#part-two").width() - 10;
		var height = $(".easyui-panel").height() - 85;
		//获取绑定总数
		var allbind = map.ROOM_BIND_TOTAL;
		var bind = "共绑定"+allbind+"户";
		$("#circularChart").height(height);
		var myChart = echarts.init(document.getElementById("circularChart"));
		var option = {
		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		    legend: {
		        orient: 'vertical',
		        x: 'right',
		        data:datas.legend
		    },
		    series: [
		        {
		            name:'',
		            type:'pie',
		            radius: ['50%', '70%'],
		            avoidLabelOverlap: false,
		            label: {
		            	normal: {
		                    show: true,
		                    position: 'center',
		                    formatter: function(){
		                    	return bind;
		                    },
		        			textStyle:{
		        				fontSize:'30',
		        				color:'green'
		        			}
		                },
		                emphasis: {
		                    show: true,
		                    textStyle: {
		                        fontSize: '30'
		                    }
		                }
		            },
		            labelLine: {
		                normal: {
		                    show: false
		                }
		            },
		            data:datas.dataset
		        }
		    ]
		};
		myChart.setOption(option);
	
	}
	
	
	/* 左分页 */
  	$("#areaRoomLeft").click(function(){
  		PAGE1 = PAGE1 - 1;
  		initAreaRoom(PAGE1);
  		
  	});
  	
  	
  	/* 右分页 */
  	$("#areaRoomRight").click(function(){
  		PAGE1 = PAGE1 + 1;
  		initAreaRoom(PAGE1);
  	});
  	
	//各项目下载情况
    function initAreaRoom(PAGE1){			
		var data = {}; 
		data.PAGE = PAGE1;
    	data.ROWS= 4;
		data.SIGN = "0"; 
    	$.ajax({
			url : url + '/cockpit/CockpitController/getAreaRoom.do',
			type : 'post',
			data :{'data':JSON.stringify(data)},
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
				
				if(null != data && "" != data){
				//加载到最后一页，去除加载更多按钮   
	       		var counts = data.total;
	       		reCount = Math.ceil(counts / 4);
	       		if(reCount <= PAGE1){
	       			$("#areaRoomRight" ).css("display", "none"); 
	       		}else{
	       			$("#areaRoomRight" ).css("display", "block"); 
	       		}
	       		if(1>=PAGE1){
	       			$("#areaRoomLeft" ).css("display", "none"); 
	       		}else{
	       			$("#areaRoomLeft" ).css("display", "block"); 
	       		}
				 createChartData(data.rows);
				}
			}
		}); 
	}
	
	function createChartData(data) {
		var chart = {
    		legend:['总用户数', '下载用户数'],	
    		xAxis:[],
    		//总用户数
    		dd_dataset:[],
    		//下载用户数
    		ee_dataset:[]
    	};
    	$.each(data,function(i,v){
    	   //x轴数据封装
    		chart.xAxis.push(v.AREA_NAME);
    	   //总用户数数据封装
    	    chart.dd_dataset.push(v.ALLROOM);
    	    //下载用户数数据封装
    	    chart.ee_dataset.push(v.BINDROOM);
        }); 
    	//绘制echarts图表
    	initChart(chart);
	}
	
	
    function initChart(chart) {
    	var height = $(".easyui-panel").height() - 30;
    	$("#chartContainer").height(height);
     	var myChart = echarts.init(document.getElementById("chartContainer"));
     	var option = {
     			color: ['#9BDFFB','#EE5C42'],
    		    legend: {
    		    	bottom : '0',
    		        data:chart.legend
    		    },
    		    tooltip : {
    		        trigger: 'axis',
    		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
    		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
    		        }
    		    },
    		    grid: {
    		    	top:10,
    		    	left: '5%',
    		        right: '5%',
    		        bottom: '10%',
    		        containLabel: true
    		    },
    		    xAxis : [
    		        {
    		            type : 'category',
    		            data : chart.xAxis,
    		            axisTick: {
    		                alignWithLabel: true
    		            }
    		        }
    		    ],
    		    yAxis : [
    		        {
    		            type : 'value'
    		        }
    		    ],
    		    series : [
    		        {
    		            name:'总用户数',
    		            type:'bar',
    		            barWidth : 30,//柱图宽度
    		            itemStyle : { 
    		            	normal: {
    		            		label : {
    		            			show: true, 
    		            			position: 'top'
    		            			}
    		            	}
    		            },
    		            data:chart.dd_dataset
    		            
    		        },
    		        {
    		            name:'下载用户数',
    		            type:'bar',
    		            barWidth : 30,//柱图宽度
    		            itemStyle : { 
    		            	normal: {
    		            		label : {
    		            			show: true, 
    		            			position: 'top'
    		            			}
    		            	}
    		            },
    		            data:chart.ee_dataset
    		        }
    		    ]
    		};
    	myChart.setOption(option);
	}
    
    //本日活跃用户分页
    /* 左分页 */
    $("#projectActiveLeft").click(function(){
  		PAGE2 = PAGE2 - 1;
  		initProjectActiveUser(PAGE2);
  		
  	});
  	
  	
  	/* 右分页 */
  	$("#projectActiveRight").click(function(){
  		PAGE2 = PAGE2 + 1;
  		initProjectActiveUser(PAGE2);
  	});
    
    //本日活跃用户数
	function initProjectActiveUser(PAGE2){
		var data = {};
		data.PAGE = PAGE2;
    	data.ROWS= 4;
		$.ajax({
			url : url + '/cockpit/CockpitController/getItemDayActiveUsers.do',
			type : 'post',
			dataType : 'json',
			data :{'data':JSON.stringify(data)},
			async : false,
			error : function(){},
			success : function(data){
				if(null != data && "" != data){
				//加载到最后一页，去除加载更多按钮   
	       		var counts = data.total;
	       		reCount = Math.ceil(counts / 4);
	       		if(reCount <= PAGE2){
	       			$("#projectActiveRight" ).css("display", "none"); 
	       		}else{
	       			$("#projectActiveRight" ).css("display", "block"); 
	       		}
	       		if(1>=PAGE2){
	       			$("#projectActiveLeft" ).css("display", "none"); 
	       		}else{
	       			$("#projectActiveLeft" ).css("display", "block"); 
	       		}
				 createProjectActiveUser(data.rows);
				}
			}
		});
	}
	
	function createProjectActiveUser(data) {
		var chart = {
    		legend:['用户数', '活跃用户数'],	
    		xAxis:[],
    		//总用户数
    		dd_dataset:[],
    		//下载用户数
    		ee_dataset:[]
    	};
    	$.each(data,function(i,v){
    	   //x轴数据封装
    		chart.xAxis.push(v.AREA_NAME);
    	   //总用户数数据封装
    	    chart.dd_dataset.push(v.EACH_ITEM_USERS);
    	    //下载用户数数据封装
    	    chart.ee_dataset.push(v.EACH_ITEM_ACTIVE_USERS);
        }); 
    	//绘制echarts图表
		initProjectActiveUserChart(chart);
	}

    function initProjectActiveUserChart(chart) {
    	var height = $(".easyui-panel").height() - 85;
    	$("#ProjectActiveUser").height(height);
     	var myChart = echarts.init(document.getElementById("ProjectActiveUser"));
     	var option = {
 			color: ['#9BDFFB','#EE5C42'],
		    legend: {
		    	bottom : '0',
		        data:chart.legend
		    },
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        }
		    },
		    grid: {
		    	top:10,
		    	left: '5%',
		        right: '5%',
		        bottom: '10%',
		        containLabel: true
		    },
		    xAxis : [
		        {
		            type : 'category',
		            data : chart.xAxis,
		            axisTick: {
		                alignWithLabel: true
		            }
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series : [
		        {
		            name:'用户数',
		            type:'bar',
		            barWidth : 30,//柱图宽度
		            itemStyle : { 
		            	normal: {
		            		label : {
		            			show: true, 
		            			position: 'top'
		            			}
		            	}
		            },
		            data:chart.dd_dataset
		            
		        },
		        {
		            name:'活跃用户数',
		            type:'bar',
		            barWidth : 30,//柱图宽度
		            itemStyle : { 
		            	normal: {
		            		label : {
		            			show: true, 
		            			position: 'top'
		            			}
		            	}
		            },
		            data:chart.ee_dataset
		        }
		    ]
		};
    	myChart.setOption(option);
	}
    
    
    var userActiveRel = {};
	//活跃用户增长情况
	function initUserActive(date,groupFlag){
		 var data = {};
     	 data.SIGN = "0";
         data.END_DATE = date;
         
		if(0==groupFlag){
			passurl = url + '/cockpit/CockpitController/getActiveUsersDayCounts.do';
			data.NUMBER = "7";
		}else if(1==groupFlag){
			passurl = url + '/cockpit/CockpitController/getActiveUsersWeekCounts.do';
			data.NUMBER = "49";
		}else if(2==groupFlag){
			passurl = url + '/cockpit/CockpitController/getActiveUsersMonthCounts.do';
			data.NUMBER = "210";
		}
         
		 $.ajax({
			url : passurl,
			data :{'data':JSON.stringify(data)},
			type : 'post',
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
				if(null != data && "" != data){
					if(currentdate == data[6].DATE){
						$("#userActiveRight").hide();
					}else{
						$("#userActiveRight").show();
					}
					userActiveRel = data;
					userActiveRel.groupFlag = groupFlag;
					createUserData(data);
				}
			}
		});
	}
	
	function createUserData(data){
		var chart = {
    		legend:['活跃用户量'],	
    		xAxis:[],
    		//用户数
    		dd_dataset:[],
    	};
    	$.each(data,function(i,v){
    	   //x轴数据封装
    		chart.xAxis.push(v.DATE);
    	   //活跃用户量数据封装
    	    chart.dd_dataset.push(v.TODAY_ACTIVE_USERS);
        }); 
      	initUserChart(chart);
	}
	
	function initUserChart(chart) {
		//获取当前父元素的宽度
		var width = $("#part-five").width() - 15;
		var height = $(".easyui-panel").height() - 85;
		$("#UserBrokenLine").height(height);
	 	var myChart = echarts.init(document.getElementById("UserBrokenLine"));
	 	var option = {
 			tooltip: {
    	        trigger: 'axis'
    	    },
    	    legend: {
    	    	bottom:"0",
    	        data: chart.legend     
    	    },
		    xAxis: {
		        type: 'category',
		        name: '',
		        splitLine: {show: false},
		        axisLabel: {  
		        	interval:0,  
	        	    textStyle: {
                       fontSize:'10'
                    }
	        	},
		        data: chart.xAxis
		    },
		    grid: {
		    	top:10,
		        left: '5%',
		        right: '5%',
		        bottom: '10%',
		        containLabel: true
		    },
		    yAxis: {
		        type: 'value',
		        name: '',
		        min:0
		    },
		    series: [
		        {
	            name: '活跃用户量',
	            type: 'line',
	            itemStyle: {
	                normal: {
	                    color: "#7dc432",
	                    lineStyle: {
	                        color: "#7dc432"
	                    }
	                }
	            },
	            data: chart.dd_dataset
		        }
		    ]
		};
	 	myChart.setOption(option);
	}
	
	 //日增 
  	$("#userActiveDay").click(function(){
  		var dates = currentdate;
  		var groupFlag = 0;
  		initUserActive(dates,groupFlag);
  	});
	
	 //周增 
  	$("#userActiveWeek").click(function(){
  		var dates = currentdate;
  		var groupFlag = 1;
  		initUserActive(dates,groupFlag);
  	});
	
  	 //月增 
  	$("#userActiveMonth").click(function(){
  		var dates = currentdate;
  		var groupFlag = 2;
  		initUserActive(dates,groupFlag);
  	});
  	
	 //左分页 
  	$("#userActiveLeft").click(function(){
  		if(0 == userActiveRel.groupFlag){
  			var dates = addDate(userActiveRel[0].DATE,-1);
  		}else if(1 == userActiveRel.groupFlag){
  			var dates = addDate(userActiveRel[0].DATE,-7);
  		}else if(2 == userActiveRel.groupFlag){
  			var dates = addDate(userActiveRel[0].DATE,-30);
  		}
  		var groupFlag = userActiveRel.groupFlag;
  		initUserActive(dates,groupFlag);
  	});
  	
  	
  	 //右分页 
  	$("#userActiveRight").click(function(){
  		if(0 == userActiveRel.groupFlag){
  			var dates = addDate(userActiveRel[6].DATE,7);
  		}else if(1 == userActiveRel.groupFlag){
  			var dates = addDate(userActiveRel[6].DATE,49);
  		}else if(2 == userActiveRel.groupFlag){
  			var dates = addDate(userActiveRel[6].DATE,210);
  		}
  		var groupFlag = userActiveRel.groupFlag;
  		initUserActive(dates,groupFlag);
  	});
	
	
	
	var pvUvRel = {};
	//pv,uv增长情况
	function initPvUv(date){
	    var data = {};
        data.END_DATE = date;
        data.NUMBER = "7";
		$.ajax({
			url : url + '/cockpit/CockpitController/getPvUvDailyGrowth.do?',
			data :{'data':JSON.stringify(data)},
			type : 'post',
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
			    
				if(null != data && "" != data){
					if(currentdate == data[6].DATE){
						$("#pvUvRight").hide();
					}else{
						$("#pvUvRight").show();
					}
					pvUvRel = data;
					createPvUvData(data);
				}
			}
		});
	}
	
	function createPvUvData(data){
		var chart = {
    		legend:['PV', 'UV'],	
    		xAxis:[],
    		//PV
    		dd_dataset:[],
    		//UV
    		ee_dataset:[]
    	};
    	$.each(data,function(i,v){
    	   //x轴数据封装
    		chart.xAxis.push(v.DATE);
    	   //PV数据封装
    	    chart.dd_dataset.push(v.PV_TOTAL);
    	    //UV数数据封装
    	    chart.ee_dataset.push(v.UV_TOTAL);
        }); 
    	//绘制echarts图表
		initPvUvChart(chart);	
	}
	
	function initPvUvChart(chart){
		//获取当前父元素的宽度
		var width = $("#part-six").width() - 10;
		var height = $(".easyui-panel").height() - 80;
		$("#PvUvBrokenLine").height(height);
	 	var myChart = echarts.init(document.getElementById("PvUvBrokenLine"));
	 	var option = {
	 			tooltip: {
	    	        trigger: 'axis'
	    	    },
			    legend: {
			    	bottom:"0",
	    	        data: chart.legend		        
			    },
			    xAxis: {
			        type: 'category',
			        name: '',
			        splitLine: {show: false},
			        axisLabel: {  
			        	interval:0,  
		        	    textStyle: {
	                       fontSize:'10'
	                    }
		        	},
			        data: chart.xAxis
			    },
			    grid: {
			    	top:10,
			    	left: '5%',
			        right: '5%',
			        bottom: '10%',
			        containLabel: true
			    },
			    yAxis: {
			        type: 'value',
			        name: '',
			        min:0
			    },
			    series: [
			        {
			            name: 'PV',
			            type: 'line',
			            itemStyle: {
			                normal: {
			                    color: "#fe006e",
			                    lineStyle: {
			                        color: "#fe006e"
			                    }
			                }
			            },
			            data: chart.dd_dataset
			        },
			        {
			            name: 'UV',
			            type: 'line',
			            itemStyle: {
			                normal: {
			                    color: "#ffd200",
			                    lineStyle: {
			                        color: "#ffd200"
			                    }
			                }
			            },
			            data: chart.ee_dataset
			        }
			    ]
			};
	 	myChart.setOption(option);
	}
	
	 //pv、uv左分页 
  	$("#pvUvLeft").click(function(){
  		var dates = addDate(pvUvRel[0].DATE,-1);
  		initPvUv(dates);
  	});
  	
  	 //pv、uv右分页 
  	$("#pvUvRight").click(function(){
  		var dates = addDate(pvUvRel[6].DATE,7);
  		initPvUv(dates);
  	});
	
  	
  //本日各项目使用PV分页
  	/* 左分页 */
    $("#projectPvLeft").click(function(){
  		PAGE3 = PAGE3 - 1;
  		initProjectPv(PAGE3);
  		
  	});
  	
  	
  	/* 右分页 */
  	$("#projectPvRight").click(function(){
  		PAGE3 = PAGE3 + 1;
  		initProjectPv(PAGE3);
  	});
	//本日各项目使用PV
	function initProjectPv(PAGE3){  
		var data = {};
		var BEGINDATE = $('#PV_DATE_START').datebox('getValue');
  		var ENDDATE = $('#PV_DATE_END').datebox('getValue');
  		data.PAGE = PAGE3;
    	data.ROWS= 4;
  		if(""!=BEGINDATE && "" != ENDDATE){
  			if(BEGINDATE>ENDDATE){
  	  			parent.$.messager.alert('错误', '结束时间不可小于开始时间', 'error');
  	  			return;
  	  		}
  			data.FLAG = "1";
  			data.BEGINDATE = BEGINDATE;
  	  		data.ENDDATE = ENDDATE;
  		}else if(""!=BEGINDATE || "" != ENDDATE){
  			data.FLAG = "1";
  			data.BEGINDATE = BEGINDATE;
  	  		data.ENDDATE = ENDDATE;
  		}
		
  		
  		
		$.ajax({
			url : url + '/cockpit/CockpitController/getEachItemDayPv.do',
			type : 'post',
			data :{'data':JSON.stringify(data)},
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
			
				if(null != data && "" != data){
					//加载到最后一页，去除加载更多按钮   
		       		var counts = data.total;
		       		reCount = Math.ceil(counts / 4);
		       		if(reCount <= PAGE3){
		       			$("#projectPvRight" ).css("display", "none"); 
		       		}else{
		       			$("#projectPvRight" ).css("display", "block"); 
		       		}
		       		if(1>=PAGE3){
		       			$("#projectPvLeft" ).css("display", "none"); 
		       		}else{
		       			$("#projectPvLeft" ).css("display", "block"); 
		       		}
				 	createProjectPv(data.rows);
				}
			}
		});
	}
	
	function createProjectPv(data) {
		var chart = {
    		legend:['PV'],	
    		xAxis:[],
    		//总用户数
    		dd_dataset:[],
    		//下载用户数
    		ee_dataset:[]
    	};
    	$.each(data,function(i,v){
    	   //x轴数据封装
    		chart.xAxis.push(v.AREA_NAME);
    	   //PV数数据封装
    	    chart.dd_dataset.push(v.DAY_PV_TOTAL);
        }); 
    	//绘制echarts图表		
		initProjectPvChart(chart);
	}

    function initProjectPvChart(chartdata) {
    	//获取当前父元素的宽度
		var width = $("#part-seven").width() - 15;
		var height = $(".easyui-panel").height() - 85;
		$("#ProjectPvHistogram").height(height);
     	var myChart = echarts.init(document.getElementById("ProjectPvHistogram"));
     	var option = {
    		    color: ['#1E90FF'],
    		    legend: {
    		    	bottom : '0',
    		        data:chartdata.legend
    		    },
    		    tooltip : {
    		        trigger: 'axis',
    		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
    		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
    		        }
    		    },
    		    grid: {
    		    	top:10,
    		    	left: '5%',
			        right: '5%',
			        bottom: '10%',
			        containLabel: true
    		    },
    		    xAxis : [
    		        {
    		            type : 'category',
    		            data : chartdata.xAxis,
    		            axisTick: {
    		                alignWithLabel: true
    		            }
    		        }
    		    ],
    		    yAxis : [
    		        {
    		            type : 'value'
    		        }
    		    ],
    		    series : [
    		        {
    		            name:'PV',
    		            type:'bar',
    		            barWidth : 30,//柱图宽度
    		            itemStyle : { 
    		            	normal: {
    		            		label : {
    		            			show: true, 
    		            			position: 'top'
    		            			}
    		            	}
    		            },
    		            data:chartdata.dd_dataset
    		        }
    		    ]
    		};
    	myChart.setOption(option);
	}

    /* ================ 子公司下 ================ */
    
    var userRoomRelDept = {};
    //增长用户和房间折线图
    function initUserRoomDept(date,groupFlag){
    	var data = {};
  	  	data.SIGN = "1";
       	data.END_DATE = date;
       	data.DEPT_CODE = dept_code;
       	
       	if(0==groupFlag){
       		passurl = url + '/cockpit/CockpitController/getRegisBindDayTotal.do';
       		data.NUMBER = "7";
       	}else if(1==groupFlag){
       		passurl = url + '/cockpit/CockpitController/getRegisBindWeekTotal.do';
       		data.NUMBER = "49";
       	}else if(2==groupFlag){
       		passurl = url + '/cockpit/CockpitController/getRegisBindMonthTotal.do';
       		data.NUMBER = "210";
       	}
       	
		$.ajax({
			url : passurl,
			data :{'data':JSON.stringify(data)},
			type : 'post',
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
				if(null != data && "" != data){
					if(currentdate == data[6].DATE){
						$("#userRoomRightDept").hide();
					}else{
						$("#userRoomRightDept").show();
					}
					userRoomRelDept = data;
					userRoomRelDept.groupFlag = groupFlag;
					createUserRoomDataDept(data);
				}
			}
		});
	}
	
	function createUserRoomDataDept(data){
		var chart = {
    		legend:['用户数', '下载数'],	
    		xAxis:[],
    		//用户数
    		dd_dataset:[],
    		//下载数
    		ee_dataset:[]
    	};
    	$.each(data,function(i,v){
    	   //x轴数据封装
    		chart.xAxis.push(v.DATE);
    	   //用户数数据封装
    	    chart.dd_dataset.push(v.OWNER_ADD_TOTAL);
    	    //下载数数据封装
    	    chart.ee_dataset.push(v.BIND_ROOM_TOTAL);
        }); 
    	//绘制echarts图表
      	initUserRoomChartDept(chart);
	}
	
	function initUserRoomChartDept(chart) {
		//获取当前父元素的宽度
		var width = $("#part-eight").width() - 10;
   		var height = $(".easyui-panel").height() - 100;
   		$("#RoomBrokenLineDept").height(height);
   	 	var myChart = echarts.init(document.getElementById("RoomBrokenLineDept"));
   	 	var option = {
   		    tooltip: {
   		        trigger: 'item',
   		        formatter: '{a} <br/>{b} : {c}'
   		    },
   		    tooltip: {
   		        trigger: 'axis'
   		    },
   		    legend: {
   		    	bottom:"0",
   		        data: chart.legend     
   		    },
   		    xAxis: {
		        type: 'category',
		        name: '',
		        splitLine: {show: false},	        
		        data: chart.xAxis,
		        axisLabel: {  
		        	interval:0,  
	        	    textStyle: {
	                   fontSize:'10'
	                }
	        	},
		    },
		    grid: {
		    	top:5,
		        left: '5%',
		        right: '5%',
		        bottom: '10%',
		        containLabel: true
		    },
		    yAxis: {
		        type: 'value',
		        name: '',
		        min:0
		    },
   		    series: [
   		        {
   		            name: '用户数',
   		            type: 'line',
   		            itemStyle: {
   		                normal: {
   		                    color: "#ffc900",
   		                    lineStyle: {
   		                        color: "#ffc900"
   		                    }
   		                }
   		            },
   		            data: chart.dd_dataset
   		        },
   		        {
   		            name: '下载数',
   		            type: 'line',
   		            itemStyle: {
   		                normal: {
   		                    color: "#00b3ec",
   		                    lineStyle: {
   		                        color: "#00b3ec"
   		                    }
   		                }
   		            },
   		            data: chart.ee_dataset
   		        }
   		    ]
   		};
   	 	myChart.setOption(option);
	}
  	
	/* 左分页 */
  	$("#userRoomLeftDept").click(function(){
  		if(0 == userRoomRelDept.groupFlag){
  			var dates = addDate(userRoomRelDept[0].DATE,-1);
  		}else if(1 == userRoomRelDept.groupFlag){
  			var dates = addDate(userRoomRelDept[0].DATE,-7);
  		}else if(2 == userRoomRelDept.groupFlag){
  			var dates = addDate(userRoomRelDept[0].DATE,-30);
  		}
  		var groupFlag = userRoomRelDept.groupFlag;
  		initUserRoomDept(dates,groupFlag);
  	});
  	
  	/* 右分页 */
  	$("#userRoomRightDept").click(function(){
  		if(0 == userRoomRelDept.groupFlag){
  			var dates = addDate(userRoomRelDept[6].DATE,7);
  		}else if(1 == userRoomRelDept.groupFlag){
  			var dates = addDate(userRoomRelDept[6].DATE,49);
  		}else if(2 == userRoomRelDept.groupFlag){
  			var dates = addDate(userRoomRelDept[6].DATE,210);
  		}
  		var groupFlag = userRoomRelDept.groupFlag;
  		initUserRoomDept(dates,groupFlag);
  	});
    
  	//子公司下今日各项目增加情况圆形图
  	function initUserRegistDept(){
    	var data = {};
    	data.SIGN = "1";  
    	data.DEPT_CODE = dept_code;
  		
		$.ajax({
			url : url + '/cockpit/CockpitController/getAreaRoomBindTotal.do',
			type : 'post',
			data :{'data':JSON.stringify(data)},
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
				if(null != data && "" != data){
					
					var datas = data.list;
					var map = data.map;
					createCircularDataDept(datas,map);
				}
			}
		});
	}
	
	//创建饼图加载数据数组
	function createCircularDataDept(data,map){
     	var chart = {
    		legend:[],	
    		dataset:[]
    	};
    	$.each(data,function(i,v){
    		chart.legend.push(v.AREA_NAME);
    		chart.dataset.push({
    				name: v.AREA_NAME,
	               	value: v.ROOM_BIND_AREA
	        })
        });
      	initCircularChartDept(chart,map);
	}
	
	//加载圆饼图
	function initCircularChartDept(datas,map) {
		//获取当前父元素的宽度
		var width = $("#part-nine").width() - 10;
		var height = $(".easyui-panel").height() - 85;
		//获取绑定总数
		var allbind = map.ROOM_BIND_TOTAL;
		var bind = "今日共绑定"+allbind+"户";
		$("#circularChartDept").height(height);
		var myChart = echarts.init(document.getElementById("circularChartDept"));
		var option = {
		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		    legend: {
		        orient: 'vertical',
		        x: 'right',
		        data:datas.legend
		    },
		    series: [
		        {
		            name:'',
		            type:'pie',
		            radius: ['50%', '70%'],
		            avoidLabelOverlap: false,
		            label: {
		            	normal: {
		                    show: true,
		                    position: 'center',
		                    formatter: function(){
		                    	return bind;
		                    },
		        			textStyle:{
		        				fontSize:'30',
		        				color:'green'
		        			}
		                },
		                emphasis: {
		                    show: true,
		                    textStyle: {
		                        fontSize: '30'
		                    }
		                }
		            },
		            labelLine: {
		                normal: {
		                    show: false
		                }
		            },
		            data:datas.dataset
		        }
		    ]
		};
		myChart.setOption(option);
	}
	
	//子公司下各小区所有房间和已绑定小区  分页
	/* 左分页 */
  	$("#areaRoomDeptLeft").click(function(){
  		PAGE4 = PAGE4 - 1;
  		initAreaRoomDept(PAGE4);
  		
  	});
  	
  	
  	/* 右分页 */
  	$("#areaRoomDeptRight").click(function(){
  		PAGE4 = PAGE4 + 1;
  		initAreaRoomDept(PAGE4);
  	});
	//子公司下各小区所有房间和已绑定小区
    function initAreaRoomDept(PAGE4){			
    	var data = {};
    	data.SIGN = "1";
    	data.DEPT_CODE = dept_code;
    	data.PAGE = PAGE4;
    	data.ROWS= 4;
    	$.ajax({
			url : url + '/cockpit/CockpitController/getAreaRoom.do',
			type : 'post',
			data :{'data':JSON.stringify(data)}, 
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
				if(null != data && "" != data){
				//加载到最后一页，去除加载更多按钮   
	       		var counts = data.total;
	       		reCount = Math.ceil(counts / 4);
	       		if(reCount <= PAGE4){
	       			$("#areaRoomDeptRight" ).css("display", "none"); 
	       		}else{
	       			$("#areaRoomDeptRight" ).css("display", "block"); 
	       		}
	       		if(1>=PAGE4){
	       			$("#areaRoomDeptLeft" ).css("display", "none"); 
	       		}else{
	       			$("#areaRoomDeptLeft" ).css("display", "block"); 
	       		}
				 createChartDataDept(data.rows);
				}
			}
		}); 
	}
	
	function createChartDataDept(data) {
		var chart = {
    		legend:['总用户数', '下载用户数'],	
    		xAxis:[],
    		//总用户数
    		dd_dataset:[],
    		//下载用户数
    		ee_dataset:[]
    	};
    	$.each(data,function(i,v){
    	   //x轴数据封装
    		chart.xAxis.push(v.AREA_NAME);
    	   //总用户数数据封装
    	    chart.dd_dataset.push(v.ALLROOM);
    	    //下载用户数数据封装
    	    chart.ee_dataset.push(v.BINDROOM);
        }); 
    	//绘制echarts图表
		initChartDept(chart);
	}
	
    function initChartDept(chartdata) {
     //获取当前父元素的宽度
    	var width = $("#part-ten").width() - 10;
		var height = $(".easyui-panel").height() - 85;
		$("#chartContainerDept").height(height);
     	var myChart = echarts.init(document.getElementById("chartContainerDept"));
     	var option = {
 			color: ['#9BDFFB','#EE5C42'],
		    legend: {
		    	bottom : '0',
		        data:chartdata.legend
		    },
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        }
		    },
		    grid: {
		    	top:5,
		    	left: '0%',
		    	right: '2%',
		        bottom: '12%',
		        containLabel: true
		    },
		    xAxis : [
		        {
		            type : 'category',
		            data : chartdata.xAxis,
		            axisTick: {
		                alignWithLabel: true
		            },
		            axisLabel: {  
			        	interval:0,  
		        	    textStyle: {
	                       fontSize:'10'
	                    }
		        	}
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series : [
		        {
		            name:'总用户数',
		            type:'bar',
		            barWidth : 30,//柱图宽度
		            itemStyle : { 
		            	normal: {
		            		label : {
		            			show: true, 
		            			position: 'top'
		            			}
		            	}
		            },
		            data:chartdata.dd_dataset
		            
		        },
		        {
		            name:'下载用户数',
		            type:'bar',
		            barWidth : 30,//柱图宽度
		            itemStyle : { 
		            	normal: {
		            		label : {
		            			show: true, 
		            			position: 'top'
		            			}
		            	}
		            },
		            data:chartdata.ee_dataset
		        }
		    ]
		};
    	myChart.setOption(option);
	}
	
    var userActiveRelDept = {};
	//活跃用户增长情况
	function initUserActiveDept(date,groupFlag){
		 var data = {};
     	 data.SIGN = "1";
         data.END_DATE = date;
         data.DEPT_CODE = dept_code;
         
		if(0==groupFlag){
			passurl = url + '/cockpit/CockpitController/getActiveUsersDayCounts.do';
			data.NUMBER = "7";
		}else if(1==groupFlag){
			passurl = url + '/cockpit/CockpitController/getActiveUsersWeekCounts.do';
			data.NUMBER = "49";
		}else if(2==groupFlag){
			passurl = url + '/cockpit/CockpitController/getActiveUsersMonthCounts.do';
			data.NUMBER = "210";
		}
         
		 $.ajax({
			url : passurl,
			data :{'data':JSON.stringify(data)},
			type : 'post',
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
				if(null != data && "" != data){
					if(currentdate == data[6].DATE){
						$("#userActiveRightDept").hide();
					}else{
						$("#userActiveRightDept").show();
					}
					userActiveRelDept = data;
					userActiveRelDept.groupFlag = groupFlag;
					createUserDataDept(data);
				}
			}
		});
	}
	
	function createUserDataDept(data){  
		var chart = {
    		legend:['活跃用户量'],	
    		xAxis:[],
    		//用户数
    		dd_dataset:[],
    	};
    	$.each(data,function(i,v){
    	   //x轴数据封装
    		chart.xAxis.push(v.DATE);
    	   //活跃用户量数据封装
    	    chart.dd_dataset.push(v.TODAY_ACTIVE_USERS);
        }); 
      	initUserChartDept(chart);
	}
	
	function initUserChartDept(chart) {
		//获取当前父元素的宽度
		var width = $("#part-eleven").width() - 10;
		var height = $(".easyui-panel").height() - 85;
		$("#UserBrokenLineDept").height(height);
	 	var myChart = echarts.init(document.getElementById("UserBrokenLineDept"));
	 	var option = {
 			tooltip: {
    	        trigger: 'axis'
    	    },
    	    legend: {
		    	bottom : '0',
		        data:chart.legend
		    },
		    xAxis: {
		        type: 'category',
		        name: '',
		        splitLine: {show: false},
	            axisLabel: {  
		        	interval:0,  
	        	    textStyle: {
                       fontSize:'10'
                    }
	        	},
		        data: chart.xAxis
		    },
		    grid: {
		    	top:5,
		    	left: '0%',
		    	right: '2%',
		        bottom: '12%',
		        containLabel: true
		    },
		    yAxis: {
		        type: 'value',
		        name: '',
		        min:0
		    },
		    series: [
		        {
	            name: '活跃用户量',
	            type: 'line',
	            itemStyle: {
	                normal: {
	                    color: "#7dc432",
	                    lineStyle: {
	                        color: "#7dc432"
	                    }
	                }
	            },
	            data: chart.dd_dataset
		        }
		    ]
		};
	 	myChart.setOption(option);
	}
	
	 //左分页 
  	$("#userActiveLeftDept").click(function(){
  		if(0 == userActiveRelDept.groupFlag){
  			var dates = addDate(userActiveRelDept[0].DATE,-1);
  		}else if(1 == userActiveRelDept.groupFlag){
  			var dates = addDate(userActiveRelDept[0].DATE,-7);
  		}else if(2 == userActiveRelDept.groupFlag){
  			var dates = addDate(userActiveRelDept[0].DATE,-30);
  		}
  		var groupFlag = userActiveRelDept.groupFlag;
  		initUserActiveDept(dates,groupFlag);
  	});
  	
  	
  	 //右分页 
  	$("#userActiveRightDept").click(function(){
  		if(0 == userActiveRelDept.groupFlag){
  			var dates = addDate(userActiveRelDept[6].DATE,7);
  		}else if(1 == userActiveRelDept.groupFlag){
  			var dates = addDate(userActiveRelDept[6].DATE,49);
  		}else if(2 == userActiveRelDept.groupFlag){
  			var dates = addDate(userActiveRelDept[6].DATE,210);
  		}
  		var groupFlag = userActiveRelDept.groupFlag;
  		initUserActiveDept(dates,groupFlag);
  	});
  			
  	
    //本日各项目使用PV分页
  	/* 左分页 */
    $("#projectPvDeptLeft").click(function(){
  		PAGE5 = PAGE5 - 1;
  		initProjectPvDept(PAGE5);
  		
  	});
  	
  	
  	/* 右分页 */
  	$("#projectPvDeptRight").click(function(){
  		PAGE5 = PAGE5 + 1;
  		initProjectPvDept(PAGE5);
  	});
	//子公司下本日各项目使用PV
	function initProjectPvDept(PAGE5){  
		var data = {};
		data.DEPT_CODE = dept_code;
		data.PAGE = PAGE5;
    	data.ROWS= 4;
		$.ajax({
			url : url + '/cockpit/CockpitController/getItemDayUseRatio.do',
			type : 'post',
			data :{'data':JSON.stringify(data)},
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
			
				if(null != data && "" != data){
					//加载到最后一页，去除加载更多按钮   
		       		var counts = data.total;
		       		reCount = Math.ceil(counts / 4);
		       		if(reCount <= PAGE5){
		       			$("#projectPvDeptRight" ).css("display", "none"); 
		       		}else{
		       			$("#projectPvDeptRight" ).css("display", "block"); 
		       		}
		       		if(1>=PAGE5){
		       			$("#projectPvDeptLeft" ).css("display", "none"); 
		       		}else{
		       			$("#projectPvDeptLeft" ).css("display", "block"); 
		       		}
				 	createProjectPvDept(data.rows);
				}
			}
		});
	}
	
	function createProjectPvDept(data) {
		var chart = {
    		legend:['总用户数', '本日用户数'],	
    		xAxis:[],
    		//总用户数
    		dd_dataset:[],
    		//本日用户数
    		ee_dataset:[]
    	};
    	$.each(data,function(i,v){
    	   //x轴数据封装
    		chart.xAxis.push(v.AREA_NAME);
    	   //总用户数数据封装
    	    chart.dd_dataset.push(v.EACH_ITEM_USERS);
    	    //下载用户数数据封装
    	    chart.ee_dataset.push(v.EACH_ITEM_USE_USERS);
        }); 
		initProjectPvChartDept(chart);
	}

	    function initProjectPvChartDept(chartdata) {
	    	//获取当前父元素的宽度
			var width = $("#part-twelve").width() - 10;
			var height = $(".easyui-panel").height() - 85;
			$("#ProjectPvHistogramDept").height(height);
	     	var myChart = echarts.init(document.getElementById("ProjectPvHistogramDept"));
	     	var option = {
     			color: ['#9BDFFB','#EE5C42'],
    		    legend: {
    		    	bottom : '0',
    		        data:chartdata.legend
    		    },
    		    tooltip : {
    		        trigger: 'axis',
    		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
    		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
    		        }
    		    },
    		    grid: {
    		    	top:5,
    		    	left: '0%',
    		    	right: '2%',
    		        bottom: '12%',
    		        containLabel: true
    		    },
    		    xAxis : [
    		        {
    		            type : 'category',
    		            data : chartdata.xAxis,
    		            axisTick: {
    		                alignWithLabel: true
    		            }
    		        }
    		    ],
    		    yAxis : [
    		        {
    		            type : 'value'
    		        }
    		    ],
    		    series : [
    		        {
    		            name:'总用户数',
    		            type:'bar',
    		            barWidth : 30,//柱图宽度
    		            itemStyle : { 
    		            	normal: {
    		            		label : {
    		            			show: true, 
    		            			position: 'top'
    		            			}
    		            	}
    		            },
    		            data:chartdata.dd_dataset
    		            
    		        },
    		        {
    		            name:'本日用户数',
    		            type:'bar',
    		            barWidth : 30,//柱图宽度
    		            itemStyle : { 
    		            	normal: {
    		            		label : {
    		            			show: true, 
    		            			position: 'top'
    		            			}
    		            	}
    		            },
    		            data:chartdata.ee_dataset
    		        }
    		    ]
    		};
	    	myChart.setOption(option);
		}	
    
    
/* ================ 小区下 ================ */
    
    var userRoomRelArea = {};
    //增长用户和房间折线图
    function initUserRoomArea(date,groupFlag){
    	var data = {};
  	  	data.SIGN = "1";
       	data.END_DATE = date;
       	data.AREA_CODE = area_code;
       	if(0==groupFlag){
       		passurl = url + '/cockpit/CockpitController/getRegisBindDayTotal.do';
       		data.NUMBER = "7";
       	}else if(1==groupFlag){
       		passurl = url + '/cockpit/CockpitController/getRegisBindWeekTotal.do';
       		data.NUMBER = "49";
       	}else if(2==groupFlag){
       		passurl = url + '/cockpit/CockpitController/getRegisBindMonthTotal.do';
       		data.NUMBER = "210";
       	}
       	
		$.ajax({
			url : passurl,
			data :{'data':JSON.stringify(data)},
			type : 'post',
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
				if(null != data && "" != data){
					if(currentdate == data[6].DATE){
						$("#userRoomRightArea").hide();
					}else{
						$("#userRoomRightArea").show();
					}
					userRoomRelArea = data;
					userRoomRelArea.groupFlag = groupFlag;
					createUserRoomDataArea(data);
				}
			}
		});
	}
	
	function createUserRoomDataArea(data){
		var chart = {
    		legend:['用户数','下载数'],	
    		xAxis:[],
    		//用户数
    		dd_dataset:[],
    		//下载数
    		ee_dataset:[]
    	};
    	$.each(data,function(i,v){
    	    //x轴数据封装
    		chart.xAxis.push(v.DATE);
    	    //用户数数据封装
    	    chart.dd_dataset.push(v.OWNER_ADD_TOTAL);
    	    //下载数数据封装
    	    chart.ee_dataset.push(v.BIND_ROOM_TOTAL);
        }); 
      	initUserRoomChartArea(chart);
	}
	
	function initUserRoomChartArea(chart) {
		//获取当前父元素的宽度
		var width = $("#part-thirteen").width() - 10;
   		var height = $(".easyui-panel").height() - 85;
   		$("#RoomBrokenLineArea").height(height);
   		var myChart = echarts.init(document.getElementById("RoomBrokenLineArea"));
   		var option = {
   			tooltip: {
		        trigger: 'item',
		        formatter: '{a} <br/>{b} : {c}'
		    },
		    tooltip: {
		        trigger: 'axis'
		    },
		    legend: {
		    	bottom:"0",
		        data: chart.legend     
		    },
		    xAxis: {
   		        type: 'category',
   		        name: '',
   		        splitLine: {show: false},	        
   		        data: chart.xAxis,
   		        axisLabel: {  
   		        	interval:0,  
   	        	    textStyle: {
   	                   fontSize:'10'
   	                }
   	        	},
   		    },
   		    grid: {
   		    	top:5,
   		        left: '5%',
   		        right: '5%',
   		        bottom: '10%',
   		        containLabel: true
   		    },
   		    yAxis: {
   		        type: 'value',
   		        name: '',
   		        min:0
   		    },
		    series: [
		        {
		            name: '用户数',
		            type: 'line',
		            itemStyle: {
		                normal: {
		                    color: "#ffc900",
		                    lineStyle: {
		                        color: "#ffc900"
		                    }
		                }
		            },
		            data: chart.dd_dataset
		        },
		        {
		            name: '下载数',
		            type: 'line',
		            itemStyle: {
		                normal: {
		                    color: "#00b3ec",
		                    lineStyle: {
		                        color: "#00b3ec"
		                    }
		                }
		            },
		            data: chart.ee_dataset
		        }
		    ]
		};
	    myChart.setOption(option);
	}
  	
	/* 左分页 */
  	$("#userRoomLeftArea").click(function(){
  		if(0 == userRoomRelArea.groupFlag){
  			var dates = addDate(userRoomRelArea[0].DATE,-1);
  		}else if(1 == userRoomRelArea.groupFlag){
  			var dates = addDate(userRoomRelArea[0].DATE,-7);
  		}else if(2 == userRoomRelArea.groupFlag){
  			var dates = addDate(userRoomRelArea[0].DATE,-30);
  		}
  		var groupFlag = userRoomRelArea.groupFlag;
  		initUserRoomArea(dates,groupFlag);
  	});
  	
  	/* 右分页 */
  	$("#userRoomRightArea").click(function(){
  		if(0 == userRoomRelArea.groupFlag){
  			var dates = addDate(userRoomRelArea[6].DATE,7);
  		}else if(1 == userRoomRelArea.groupFlag){
  			var dates = addDate(userRoomRelArea[6].DATE,49);
  		}else if(2 == userRoomRelArea.groupFlag){
  			var dates = addDate(userRoomRelArea[6].DATE,210);
  		}
  		var groupFlag = userRoomRelArea.groupFlag;
  		initUserRoomArea(dates,groupFlag);
  	});
    
  	
  	
  //小区下各小区所有房间和已绑定小区 分页
  	/* 左分页 */
  	$("#areaRoomAreaLeft").click(function(){
  		PAGE6 = PAGE6 - 1;
  		initAreaRoomArea(PAGE6);
  		
  	});
  	
  	
  	/* 右分页 */
  	$("#areaRoomAreaRight").click(function(){
  		PAGE6 = PAGE6 + 1;
  		initAreaRoomArea(PAGE6);
  	});
  	
	//小区下各小区所有房间和已绑定小区
    function initAreaRoomArea(PAGE6){			
    	var data = {};
  	  	data.SIGN = "1";
       	data.AREA_CODE = area_code;
       	data.PAGE = PAGE6;
    	data.ROWS= 4;
    	$.ajax({
			url : url + '/cockpit/CockpitController/getAreaRoom.do',
			type : 'post',
			data :{'data':JSON.stringify(data)},
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
				if(null != data && "" != data){
				//加载到最后一页，去除加载更多按钮   
	       		var counts = data.total;
	       		reCount = Math.ceil(counts / 4);
	       		if(reCount <= PAGE6){
	       			$("#areaRoomAreaRight" ).css("display", "none"); 
	       		}else{
	       			$("#areaRoomAreaRight" ).css("display", "block"); 
	       		}
	       		if(1>=PAGE6){
	       			$("#areaRoomAreaLeft" ).css("display", "none"); 
	       		}else{
	       			$("#areaRoomAreaLeft" ).css("display", "block"); 
	       		}
				 createChartDataArea(data.rows);
				}
			}
		}); 
	}
	
	function createChartDataArea(data) {
		var chart = {
    		legend:['总用户数', '下载用户数'],	
    		xAxis:[],
    		//总用户数
    		dd_dataset:[],
    		//下载用户数
    		ee_dataset:[]
    	};
    	$.each(data,function(i,v){
    	   //x轴数据封装
    		chart.xAxis.push(v.AREA_NAME);
    	   //总用户数数据封装
    	    chart.dd_dataset.push(v.ALLROOM);
    	    //下载用户数数据封装
    	    chart.ee_dataset.push(v.BINDROOM);
        }); 
		
		initChartArea(chart);
	}
	
    function initChartArea(chartdata) {
     //获取当前父元素的宽度
    	var width = $("#part-fourteen").width() - 10;
		var height = $(".easyui-panel").height() - 85;
		$("#chartContainerArea").height(height);
     	var myChart = echarts.init(document.getElementById("chartContainerArea"));
     	var option = {
 			color: ['#9BDFFB','#EE5C42'],
		    legend: {
		    	bottom : '0',
		        data:chartdata.legend
		    },
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        }
		    },
		    grid: {
		    	top:5,
		    	left: '0%',
		    	right: '2%',
		        bottom: '12%',
		        containLabel: true
		    },
		    xAxis : [
		        {
		            type : 'category',
		            data : chartdata.xAxis,
		            axisTick: {
		                alignWithLabel: true
		            },
		            axisLabel: {  
			        	interval:0,  
		        	    textStyle: {
	                       fontSize:'10'
	                    }
		        	}
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series : [
		        {
		            name:'总用户数',
		            type:'bar',
		            barWidth : 30,//柱图宽度
		            itemStyle : { 
		            	normal: {
		            		label : {
		            			show: true, 
		            			position: 'top'
		            			}
		            	}
		            },
		            data:chartdata.dd_dataset
		            
		        },
		        {
		            name:'下载用户数',
		            type:'bar',
		            barWidth : 30,//柱图宽度
		            itemStyle : { 
		            	normal: {
		            		label : {
		            			show: true, 
		            			position: 'top'
		            			}
		            	}
		            },
		            data:chartdata.ee_dataset
		        }
		    ]
		};
    	myChart.setOption(option);
	}
	
    var userActiveRelArea = {};
	//活跃用户增长情况
	function initUserActiveArea(date,groupFlag){
		 var data = {};
     	 data.SIGN = "1";
         data.END_DATE = date;
         data.AREA_CODE = area_code;
         
		if(0==groupFlag){
			passurl = url + '/cockpit/CockpitController/getActiveUsersDayCounts.do';
			data.NUMBER = "7";
		}else if(1==groupFlag){
			passurl = url + '/cockpit/CockpitController/getActiveUsersWeekCounts.do';
			data.NUMBER = "49";
		}else if(2==groupFlag){
			passurl = url + '/cockpit/CockpitController/getActiveUsersMonthCounts.do';
			data.NUMBER = "210";
		}
         
		 $.ajax({
			url : passurl,
			data :{'data':JSON.stringify(data)},
			type : 'post',
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
				if(null != data && "" != data){
					if(currentdate == data[6].DATE){
						$("#userActiveRightArea").hide();
					}else{
						$("#userActiveRightArea").show();
					}
					userActiveRelArea = data;
					userActiveRelArea.groupFlag = groupFlag;
					createUserDataArea(data);
				}
			}
		});
	}
	
	function createUserDataArea(data){    
		var chart = {
    		legend:['活跃用户量'],	
    		xAxis:[],
    		//用户数
    		dd_dataset:[],
    	};
    	$.each(data,function(i,v){
    	   //x轴数据封装
    		chart.xAxis.push(v.DATE);
    	   //活跃用户量数据封装
    	    chart.dd_dataset.push(v.TODAY_ACTIVE_USERS);
        }); 
      	initUserChartArea(chart);
	}
	
	function initUserChartArea(chart) {
		//获取当前父元素的宽度
		var width = $("#part-fifteen").width() - 10;
		var height = $(".easyui-panel").height() - 80;
		$("#UserBrokenLineArea").height(height);
	 	var myChart = echarts.init(document.getElementById("UserBrokenLineArea"));
	 	var option = {
 			tooltip: {
    	        trigger: 'axis'
    	    },
    	    legend: {
    	    	bottom:"0",
    	        data: chart.legend     
    	    },
		    xAxis: {
		        type: 'category',
		        name: '',
		        splitLine: {show: false},
		        axisLabel: {  
		        	interval:0,  
	        	    textStyle: {
                       fontSize:'10'
                    }
	        	},
		        data: chart.xAxis
		    },
		    grid: {
		    	top:5,
		        left: '5%',
		        right: '5%',
		        bottom: '10%',
		        containLabel: true
		    },
		    yAxis: {
		        type: 'value',
		        name: '',
		        min:0
		    },
		    series: [
		        {
	            name: '活跃用户量',
	            type: 'line',
	            itemStyle: {
	                normal: {
	                    color: "#7dc432",
	                    lineStyle: {
	                        color: "#7dc432"
	                    }
	                }
	            },
	            data: chart.dd_dataset
		        }
		    ]
		};
	 	myChart.setOption(option);
	}
		
	/* 左分页 */
  	$("#userActiveLeftArea").click(function(){
  		if(0 == userActiveRelArea.groupFlag){
  			var dates = addDate(userActiveRelArea[0].DATE,-1);
  		}else if(1 == userActiveRelArea.groupFlag){
  			var dates = addDate(userActiveRelArea[0].DATE,-7);
  		}else if(2 == userActiveRelArea.groupFlag){
  			var dates = addDate(userActiveRelArea[0].DATE,-30);
  		}
  		var groupFlag = userActiveRelArea.groupFlag;
  		initUserActiveArea(dates,groupFlag);
  	});
  	
  	
  	/* 右分页 */
  	$("#userActiveRightArea").click(function(){
  		if(0 == userActiveRelArea.groupFlag){
  			var dates = addDate(userActiveRelArea[6].DATE,7);
  		}else if(1 == userActiveRelArea.groupFlag){
  			var dates = addDate(userActiveRelArea[6].DATE,49);
  		}else if(2 == userActiveRelArea.groupFlag){
  			var dates = addDate(userActiveRelArea[6].DATE,210);
  		}
  		var groupFlag = userActiveRelArea.groupFlag;
  		initUserActiveArea(dates,groupFlag);
  	});
	
  	
  	/* 左分页 */
    $("#projectPvAreaLeft").click(function(){
  		PAGE7 = PAGE7 - 1;
  		initProjectPvArea(PAGE7);
  		
  	});
  	
  	
  	/* 右分页 */
  	$("#projectPvAreaRight").click(function(){
  		PAGE7 = PAGE7 + 1;
  		initProjectPvArea(PAGE7);
  	});
  	
	//子公司下本日各项目使用占比
	function initProjectPvArea(PAGE7){  
		var data = {};
		data.AREA_CODE = area_code;
		data.PAGE = PAGE7;
    	data.ROWS= 4;
		$.ajax({
			url : url + '/cockpit/CockpitController/getItemDayUseRatio.do',
			type : 'post',
			data :{'data':JSON.stringify(data)},
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
				
				if(null != data && "" != data){
					//加载到最后一页，去除加载更多按钮   
		       		var counts = data.total;
		       		reCount = Math.ceil(counts / 4);
		       		if(reCount <= PAGE7){
		       			$("#projectPvAreaRight" ).css("display", "none"); 
		       		}else{
		       			$("#projectPvAreaRight" ).css("display", "block"); 
		       		}
		       		if(1>=PAGE7){
		       			$("#projectPvAreaLeft" ).css("display", "none"); 
		       		}else{
		       			$("#projectPvAreaLeft" ).css("display", "block"); 
		       		}
				 	createProjectPvArea(data.rows);
				}
			}
		});
	}

    function createProjectPvArea(data) {
		var chart = {
    		legend:['总用户数', '本日用户数'],	
    		xAxis:[],
    		//总用户数
    		dd_dataset:[],
    		//本日用户数
    		ee_dataset:[]
    	};
    	$.each(data,function(i,v){
    	   //x轴数据封装
    		chart.xAxis.push(v.AREA_NAME);
    	   //总用户数数据封装
    	    chart.dd_dataset.push(v.EACH_ITEM_USERS);
    	    //下载用户数数据封装
    	    chart.ee_dataset.push(v.EACH_ITEM_USE_USERS);
        }); 
		initProjectPvChartArea(chart);
	}

	    function initProjectPvChartArea(chartdata) {
	    	//获取当前父元素的宽度
			var width = $("#part-sixteen").width() - 10;
			var height = $(".easyui-panel").height() - 85;
			$("#ProjectPvHistogramArea").height(height);
	     	var myChart = echarts.init(document.getElementById("ProjectPvHistogramArea"));
	     	var option = {
     			color: ['#9BDFFB','#EE5C42'],
    		    legend: {
    		    	bottom : '0',
    		        data:chartdata.legend
    		    },
    		    tooltip : {
    		        trigger: 'axis',
    		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
    		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
    		        }
    		    },
    		    grid: {
    		    	top:5,
    		    	left: '0%',
    		    	right: '2%',
    		        bottom: '12%',
    		        containLabel: true
    		    },
    		    xAxis : [
    		        {
    		            type : 'category',
    		            data : chartdata.xAxis,
    		            axisTick: {
    		                alignWithLabel: true
    		            }
    		        }
    		    ],
    		    yAxis : [
    		        {
    		            type : 'value'
    		        }
    		    ],
    		    series : [
    		        {
    		            name:'总用户数',
    		            type:'bar',
    		            barWidth : 30,//柱图宽度
    		            itemStyle : { 
    		            	normal: {
    		            		label : {
    		            			show: true, 
    		            			position: 'top'
    		            			}
    		            	}
    		            },
    		            data:chartdata.dd_dataset
    		            
    		        },
    		        {
    		            name:'本日用户数',
    		            type:'bar',
    		            barWidth : 30,//柱图宽度
    		            itemStyle : { 
    		            	normal: {
    		            		label : {
    		            			show: true, 
    		            			position: 'top'
    		            			}
    		            	}
    		            },
    		            data:chartdata.ee_dataset
    		        }
    		    ]
    		};
	    	myChart.setOption(option);
		}
    
    
    /* =======计算日期========= */
	function addDate(date,days){ 
	   var now = date.split('-');
	   now = new Date(Number(now['0']),(Number(now['1'])-1),Number(now['2']));  
	   now.setDate(now.getDate() + days);
       var m=now.getMonth()+1; 
       return now.getFullYear()+'-'+m+'-'+now.getDate(); 
       
    } 