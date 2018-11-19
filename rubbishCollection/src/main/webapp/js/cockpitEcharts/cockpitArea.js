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
$(document).ready(function(){
	 var flag = 0;
	 getSummaryInfo();
	 initUserRoom(currentdate,flag);//增长用户和房间折线图
	 initAreaRoom(PAGE1);//初始化总房间和绑定房间柱状图
	 initUserActive(currentdate,flag);//活跃用户用户数  折线图
	 initProjectPv(PAGE2);//本日各项目使用PV
	 
	 initUserUv();//本日使用用户
	 initActiveUserProportion();//本日活跃用户
	 
	 getComplNoteInfo();//报修投诉发帖总计（闭合率完成率及时回复率）
	 initRepairCir();//报修圆圈图
     initComplaintCir();//投诉圆圈图
     initPostCir();//帖子圆圈图
     initTodayRepair(currentdate);
});
    
    //获取抬头信息
    function getSummaryInfo(){
    	var data = {};
    	data.SIGN = "1";
    	data.AREA_CODE = area_code;
    	$.ajax({
			url : url + '/cockpit/CockpitController/getSummaryInfo.do',
			data :{'data':JSON.stringify(data)},
			type : 'post',
			dataType : 'json',
			error : function(){},
			success : function(data){
				$("#OWNER_REGIS_TOTAL").text(data.OWNER_REGIS_TOTAL);
				$("#OWNER_REGIS_TODAY").text(data.OWNER_REGIS_TODAY);
				$("#BIND_ROOM_TOTAL").text(data.BIND_ROOM_TOTAL);
				$("#BIND_ROOM_TODAY").text(data.ADD_BIND_ROOM_TODAY);
				$("#DOWNLOAD_RATE").text(data.DOWNLOAD_RATE);
				$("#LINK_GROWTH_RATE").text(data.LINK_GROWTH_RATE);
			}
		});
    }
    
    var userRoomRel = {};
    //增长用户和房间折线图
    function initUserRoom(date,flag){
    	var data = {};
  	  	data.SIGN = "1";
       	data.END_DATE = date;
       	data.AREA_CODE = area_code;
       	if(0==flag){
       		passurl = url + '/cockpit/CockpitController/getRegisBindDayTotal.do';
       		data.NUMBER = "7";
       	}else if(1==flag){
       		passurl = url + '/cockpit/CockpitController/getRegisBindWeekTotal.do';
       		data.NUMBER = "49";
       	}else if(2==flag){
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
					userRoomRel.flag = flag;
					createUserRoomData(data);
				}
			}
		});
	}
	
	function createUserRoomData(data){
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
      	initUserRoomChart(chart);
	}
	
	function initUserRoomChart(chart) {
		//获取当前父元素的宽度
		var width = $(".first-con").find("#first-cockpit-detail-con").width();
		var myChart = echarts.init(document.getElementById("RoomBrokenLine"));
	    var option = {
    	    tooltip: {
    	        trigger: 'axis'
    	    },
    	    legend: {
    	    	bottom:"0",
    	        data:chart.legend
    	    },
    	    grid: {
    	    	top:15,
    	    	left: '5%',
		    	right: '10%',
    	        bottom: '10%',
    	        containLabel: true
    	    },
    	    xAxis: {
    	        type: 'category',
    	        boundaryGap: false,
    	        data: chart.xAxis
    	    },
    	    yAxis: {
    	        type: 'value'
    	    },
    	    series: [
    	        {
    	            name:'用户数',
    	            type:'line',
    	            stack: '总量',
    	            itemStyle: {
		                normal: {
		                    color: "#ffc900",
		                    lineStyle: {
		                        color: "#ffc900"
		                    }
		                }
		            },
    	            data:chart.dd_dataset
    	        },
    	        {
    	            name:'下载数',
    	            type:'line',
    	            stack: '总量',
    	            itemStyle: {
		                normal: {
		                    color: "#00b3ec",
		                    lineStyle: {
		                        color: "#00b3ec"
		                    }
		                }
		            },
    	            data:chart.ee_dataset
    	        }
    	    ]
    	};
	    myChart.setOption(option);
	}
	
	/* 日增 */
  	$("#userRoomDay").click(function(){
  		var dates = currentdate;
  		var flag = 0;
  		initUserRoom(dates,flag);
  	});
	
	/* 周增 */
  	$("#userRoomWeek").click(function(){
  		var dates = currentdate;
  		var flag = 1;
  		initUserRoom(dates,flag);
  	});
	
  	/* 月增 */
  	$("#userRoomMonth").click(function(){
  		var dates = currentdate;
  		var flag = 2;
  		initUserRoom(dates,flag);
  	});
  	
	/* 左分页 */
  	$("#userRoomLeft").click(function(){
  		if(0 == userRoomRel.flag){
  			var dates = addDate(userRoomRel[0].DATE,-1);
  		}else if(1 == userRoomRel.flag){
  			var dates = addDate(userRoomRel[0].DATE,-7);
  		}else if(2 == userRoomRel.flag){
  			var dates = addDate(userRoomRel[0].DATE,-30);
  		}
  		var flag = userRoomRel.flag;
  		initUserRoom(dates,flag);
  	});
  	
  	
  	/* 右分页 */
  	$("#userRoomRight").click(function(){
  		if(0 == userRoomRel.flag){
  			var dates = addDate(userRoomRel[6].DATE,7);
  		}else if(1 == userRoomRel.flag){
  			var dates = addDate(userRoomRel[6].DATE,49);
  		}else if(2 == userRoomRel.flag){
  			var dates = addDate(userRoomRel[6].DATE,210);
  		}
  		var flag = userRoomRel.flag;
  		initUserRoom(dates,flag);
  	});

	
  	
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
	//获取各小区所有房间和已绑定小区
    function initAreaRoom(PAGE1){		
    	var data = {};
    	data.PAGE = PAGE1;
    	data.ROWS= 4;
     	data.SIGN = "1";  
     	data.AREA_CODE = area_code;
		 $.ajax({
			url : url + '/cockpit/CockpitController/getAreaRoom.do',
			data :{'data':JSON.stringify(data)},
			type : 'post',
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
		initChart(chart);
	}
	
	
    function initChart(chartdata) {
     //获取当前父元素的宽度
		var width = $(".third-con").find(".cockpit-detail-con").width();
		var myChart = echarts.init(document.getElementById("chartContainer"));
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
		    	top:15,
		    	left: '0%',
		    	right: '2%',
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
	
	    
    var userActiveRel = {};
	//活跃用户增长情况
	function initUserActive(date,flag){
		 var data = {};
     	 data.SIGN = "1";
         data.END_DATE = date;
         data.AREA_CODE = area_code;
         
		if(0==flag){
			passurl = url + '/cockpit/CockpitController/getActiveUsersDayCounts.do';
			data.NUMBER = "7";
		}else if(1==flag){
			passurl = url + '/cockpit/CockpitController/getActiveUsersWeekCounts.do';
			data.NUMBER = "49";
		}else if(2==flag){
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
					userActiveRel.flag = flag;
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
    	//绘制echarts图表
      	initUserChart(chart);
	}
	
	function initUserChart(chart) {
		//获取当前父元素的宽度
		var width = $(".four-con").find(".cockpit-detail-con").width();
		var myChart = echarts.init(document.getElementById("UserBrokenLine"));
    	var option = {
			tooltip: {
    	        trigger: 'axis'
    	    },
		    legend: {
		    	bottom : '0',
		        data: chart.legend
		    },
		    xAxis: {
		        type: 'category',
		        name: '',
		        splitLine: {show: false},
		        axisLabel: {  
		        	interval:0,  
		        	rotate:40,
	        	    textStyle: {
                       fontSize:'10'
                    }
	        	},
		        data: chart.xAxis
		    },
		    dataZoom: [
		            {
		                start: 1,
		                end: 50,
		                type: 'slider',
		                show: true,
		                xAxisIndex: [0],
		                handleSize: 8,//滑动条的 左右2个滑动条的大小
		                height: 5,//组件高度
		                left: 20, //左边的距离
		                right: 8,//右边的距离
		                bottom: 65,//右边的距离
		                handleColor: '#ddd',//h滑动图标的颜色
		                handleStyle: {
		                    borderColor: "#cacaca",
		                    borderWidth: "1",
		                    shadowBlur: 2,
		                    background: "#ddd",
		                    shadowColor: "#ddd",
		                },
		                fillerColor: new echarts.graphic.LinearGradient(1, 0, 0, 0, [{
		                    //给颜色设置渐变色 前面4个参数，给第一个设置1，第四个设置0 ，就是水平渐变
		                    //给第一个设置0，第四个设置1，就是垂直渐变
		                    offset: 0,
		                    color: '#1eb5e5'
		                }, {
		                    offset: 1,
		                    color: '#5ccbb1'
		                }]),
		                backgroundColor: '#ddd',//两边未选中的滑动条区域的颜色
		                showDataShadow: false,//是否显示数据阴影 默认auto
		                showDetail: false,//即拖拽时候是否显示详细数值信息 默认true
		                handleIcon: 'M-292,322.2c-3.2,0-6.4-0.6-9.3-1.9c-2.9-1.2-5.4-2.9-7.6-5.1s-3.9-4.8-5.1-7.6c-1.3-3-1.9-6.1-1.9-9.3c0-3.2,0.6-6.4,1.9-9.3c1.2-2.9,2.9-5.4,5.1-7.6s4.8-3.9,7.6-5.1c3-1.3,6.1-1.9,9.3-1.9c3.2,0,6.4,0.6,9.3,1.9c2.9,1.2,5.4,2.9,7.6,5.1s3.9,4.8,5.1,7.6c1.3,3,1.9,6.1,1.9,9.3c0,3.2-0.6,6.4-1.9,9.3c-1.2,2.9-2.9,5.4-5.1,7.6s-4.8,3.9-7.6,5.1C-285.6,321.5-288.8,322.2-292,322.2z',
		                filterMode: 'filter',
		            },
		            //下面这个属性是里面拖到
		            {
		                type: 'inside',
		                show: true,
		                xAxisIndex: [0],
		                start: 1,
		                end: 50
		            }
		        ],
		    grid: {
		    	top:15,
		    	left: '0%',
		    	right: '2%',
		        bottom: '28%',
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
	
	/* 日增 */
  	$("#userActiveDay").click(function(){
  		var dates = currentdate;
  		var flag = 0;
  		initUserActive(dates,flag);
  	});
	
	/* 周增 */
  	$("#userActiveWeek").click(function(){
  		var dates = currentdate;
  		var flag = 1;
  		initUserActive(dates,flag);
  	});
	
  	/* 月增 */
  	$("#userActiveMonth").click(function(){
  		var dates = currentdate;
  		var flag = 2;
  		initUserActive(dates,flag);
  	});
  	
	/* 左分页 */
  	$("#userActiveLeft").click(function(){
  		if(0 == userActiveRel.flag){
  			var dates = addDate(userActiveRel[0].DATE,-1);
  		}else if(1 == userActiveRel.flag){
  			var dates = addDate(userActiveRel[0].DATE,-7);
  		}else if(2 == userActiveRel.flag){
  			var dates = addDate(userActiveRel[0].DATE,-30);
  		}
  		var flag = userActiveRel.flag;
  		initUserActive(dates,flag);
  	});
  	
  	
  	/* 右分页 */
  	$("#userActiveRight").click(function(){
  		if(0 == userActiveRel.flag){
  			var dates = addDate(userActiveRel[6].DATE,7);
  		}else if(1 == userActiveRel.flag){
  			var dates = addDate(userActiveRel[6].DATE,49);
  		}else if(2 == userActiveRel.flag){
  			var dates = addDate(userActiveRel[6].DATE,210);
  		}
  		var flag = userActiveRel.flag;
  		initUserActive(dates,flag);
  	});
	
  		
  	
    //本日各项目使用PV分页
  	/* 左分页 */
    $("#projectPvLeft").click(function(){
  		PAGE2 = PAGE2 - 1;
  		initProjectPv(PAGE2);
  		
  	});
  	
  	
  	/* 右分页 */
  	$("#projectPvRight").click(function(){
  		PAGE2 = PAGE2 + 1;
  		initProjectPv(PAGE2);
  	});
	//本日各项目使用占比
	function initProjectPv(PAGE2){  
		var data = {};
		
		data.AREA_CODE = area_code;
		data.PAGE = PAGE2;
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
		       		if(reCount <= PAGE2){
		       			$("#projectPvRight" ).css("display", "none"); 
		       		}else{
		       			$("#projectPvRight" ).css("display", "block"); 
		       		}
		       		if(1>=PAGE2){
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
		initProjectPvChart(chart);
	}

	    function initProjectPvChart(chartdata) {
	    	//获取当前父元素的宽度
			var width = $(".senevn-con").find(".cockpit-detail-con").width();
			var myChart = echarts.init(document.getElementById("ProjectPvHistogram"));
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
			    	top:15,
			    	left: '0%',
			    	right: '2%',
			        bottom: '30%',
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
	
    
	//本日使用用户
	function initUserUv(){
		var data = {};
		data.FLAG = "1";
		data.AREA_CODE = area_code;
		$.ajax({
			url : url + '/cockpit/CockpitController/getCpyOrAreaUserInfo.do',
			type : 'post',
			data :{'data':JSON.stringify(data)},
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
				if(null != data && "" != data){
				
					var datas = data.list;
					$("#PV_TOTAL_DAY").text(data.PV_TOTAL_TODAY);// 使用用户 PV_TOTAL_TODAY
					$("#UV_TOTAL_DAY").text(data.GROWTH_DIFFERENCE);//今日同比昨天增加 GROWTH_DIFFERENCE
					
					createUserUv(data);
				}
			}
		});
	}
	
	//创建饼图加载数据数组
	function createUserUv(data){
		var chart = {
	    	legend:['用户使用数','非用户使用数'],	
    		dataset:[{
    			name : "用户使用数",
    			value : data.PV_TOTAL_TODAY//日uv
               },
               {
            	name : "非用户使用数",
            	value : data.NO_USER_TOTAL//日uv
               }]
    	};
      	initCircularUserUvChart(chart);
	}
	
	//加载圆饼图
	function initCircularUserUvChart(data) {
		var myChart = echarts.init(document.getElementById("UserUvProportion"));
		var option = {
		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		    legend: {
		    	orient: 'vertical',
		        x: 'left',
		        data:data.legend
		    },
		    color:['#f521ad', '#ff9000'],
		    series: [
		        {
		            name:'',
		            type:'pie',
		            radius: ['50%', '70%'],
		            avoidLabelOverlap: false,
		            label: {
		            	normal: {
		                    show: false,
		                    position: 'center'
		                },
		                emphasis: {
		                    show: true,
		                    textStyle: {
		                        fontSize: '15'
		                    },
		                    formatter:'{b}\n{c}'
		                }
		            },
		            labelLine: {
		                normal: {
		                    show: false
		                }
		            },
		            data:data.dataset
		        }
		    ]
		};
		myChart.setOption(option);
	}
	
	//本日活跃用户
    function initActiveUserProportion(){
    	var data = {};
		data.FLAG = "1";
		data.AREA_CODE = area_code;
    	$.ajax({
			url : url + '/cockpit/CockpitController/getCpyOrAreaActiveUserInfo.do',
			type : 'post',
			data :{'data':JSON.stringify(data)},
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
				if(null != data && "" != data){
					
					var datas = data.list;
					$("#TODAY_ACTIVE_USERS").text(data.UV_TOTAL_TODAY);//活跃用户数
					$("#CHAIN_GROWTH").text(data.GROWTH_DIFFERENCE);//今日同比昨日增加数
					
					createActiveUserProportion(data);
				}
			}
		});
	}
	
	//创建饼图加载数据数组
	function createActiveUserProportion(data){
		var chart = {
	    	legend:['活跃用户数','非活跃用户数'],	
    		dataset:[{
    			name : "活跃用户数",
    			value : data.UV_TOTAL_TODAY//日uv
               },
               {
            	name : "非活跃用户数",
            	value : data.NO_ACTIVE_USER_TOTAL//日uv
               }]
    	};
      	initActiveUserProportionChart(chart);
	}
	
	//加载圆饼图
	function initActiveUserProportionChart(data) {
		var myChart = echarts.init(document.getElementById("ActiveUserProportion"));
		var option = {
		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		    legend: {
		    	orient: 'vertical',
		        x: 'left',
		        data:data.legend
		    },
		    color:['#009b34', '#00dcdc'],
		    series: [
		        {
		            name:'',
		            type:'pie',
		            radius: ['50%', '70%'],
		            avoidLabelOverlap: false,
		            label: {
		            	normal: {
		                    show: false,
		                    position: 'center'
		                },
		                emphasis: {
		                    show: true,
		                    textStyle: {
		                        fontSize: '15'
		                    },
		                    formatter:'{b}\n{c}'
		                }
		            },
		            labelLine: {
		                normal: {
		                    show: false
		                }
		            },
		            data:data.dataset
		        }
		    ]
		};
		myChart.setOption(option);
	}
	
	
	/* ================================================ */
	//投诉保修发帖的汇总信息
	function getComplNoteInfo(){
    	var data = {};
     	data.SIGN = "1";
     	data.AREA_CODE = area_code;
    	$.ajax({
			url : url + '/cockpit/CockpitController/getComplNoteInfo.do',
			data :{'data':JSON.stringify(data)},
			type : 'post',
			dataType : 'json',
			error : function(){},
			success : function(data){
				$("#CPT_COMPL_TOTAL").text(data.CPT_COMPL_TOTAL);//今日投诉数
				$("#COMPL_EVAL_RATE").text(data.COMPL_EVAL_RATE);//投诉闭合率
				$("#COMPLETED_RATE").text(data.COMPLETED_RATE);//投诉完成率
				$("#REP_COMPL_TOTAL").text(data.REP_COMPL_TOTAL);//今日报修数
				$("#REP_COMPL_EVAL_RATE").text(data.REP_COMPL_EVAL_RATE);//报修闭合率
				$("#REP_COMPL_COMPLETED_RATE").text(data.REP_COMPL_COMPLETED_RATE);//报修完成率
				$("#REPLY_NOTE_TOTAL").text(data.REP_COMPL_TOTAL);//今日物业相关发帖数
				$("#NOTE_TIMELY_REPLY_RATE").text(data.NOTE_TIMELY_REPLY_RATE);//帖子及时回复率
				
				
			}
		});
    }
	
	
	 //本日报修
    function initRepairCir(){
    	var data = {};
     	data.AREA_CODE = area_code;
		$.ajax({
			url : url + '/cockpit/CockpitController/getRepAnalysis.do',
			type : 'post',
			data :{'data':JSON.stringify(data)},
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
				if(null != data && "" != data){
					var datas = data.list;
					var html="";
					for(i=0;i<datas.length;i++){
						html += "<div class='alock'>"+datas[i].COMPL_TYPE_NAME;
						html += "<span id='"+ repair[i] +"' style='font-size:5px;'>"+datas[i].COMPL_TOTAL+"</span>&nbsp;条 ";
						html += "</div>";
						
					}
					$("#repair").append(html); 				
					createRepairCir(data);
				}
			}
		});
	}
	
	//创建饼图加载数据数组
	function createRepairCir(data){	    
		var list = data.list;
		var map = data.map;
		var chart = {
			legend:[],	
    		dataset:[]
    	};
    	$.each(list,function(i,v){
    		chart.legend.push(v.COMPL_TYPE_NAME);
    		chart.dataset.push({
    				name: v.COMPL_TYPE_NAME,
	               	value: v.COMPL_TOTAL
	        })
        });
      	initRepairCirChart(chart,map);
	}
	
	//加载圆饼图
	function initRepairCirChart(datas,map) {
	    //获取当前父元素的宽度
		var width = $(".nine-con").find(".cockpit-detail-con").width() * 0.65;
		//获取类别总数
		var total = map.TOTAL;
		var myChart = echarts.init(document.getElementById("repairCir"));
		var option = {
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		    	orient: 'vertical',
		        x: 'right',
		        data:datas.legend
		    },
		    series : [
		        {
		            name: '',
		            type: 'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            data:datas.dataset,
		            itemStyle: {
		                emphasis: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                }
		            }
		        }
		    ]
		};
		myChart.setOption(option);
	}
	
	 //本日投诉
    function initComplaintCir(){ 
    	var data = {};
     	data.AREA_CODE = area_code;
		$.ajax({
			url : url + '/cockpit/CockpitController/getCmptAnalysis.do',
			type : 'post',
			data :{'data':JSON.stringify(data)},
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
				if(null != data && "" != data){
					
					var datas = data.list;
					
					var html="";
					for(i=0;i<datas.length;i++){
						html += "<div class='alock'>"+datas[i].COMPL_TYPE_NAME;
						html += "<span id='"+ complaint[i] +"' style='font-size:5px;'>"+datas[i].COMPL_TOTAL+"</span>&nbsp;条 ";
						html += "</div>";
						
					}
					$("#complaint").append(html); 
					createComplaintCir(data);
				}
			}
		});
	}
	
	//创建饼图加载数据数组
	function createComplaintCir(data){
		var list = data.list;
		var map = data.map;
		var chart = {
			legend:[],	
    		dataset:[]
    	};
    	$.each(list,function(i,v){
    		chart.legend.push(v.COMPL_TYPE_NAME);
    		chart.dataset.push({
    				name: v.COMPL_TYPE_NAME,
	               	value: v.COMPL_TOTAL
	        })
        });
      	initComplaintCirChart(chart,map);
	}
	
	//加载圆饼图
	function initComplaintCirChart(datas,map) {
	    //获取当前父元素的宽度
		var width = $(".ten-con").find(".cockpit-detail-con").width() * 0.65;
		//获取类别总数
		var total = map.TOTAL;
		var myChart = echarts.init(document.getElementById("complaintCir"));
		var option = {
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		    	orient: 'vertical',
		        x: 'right',
		        data:datas.legend
		    },
		    series : [
		        {
		            name: '',
		            type: 'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            data:datas.dataset,
		            itemStyle: {
		                emphasis: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                }
		            }
		        }
		    ]
		};
		myChart.setOption(option);
	}
	
	 //本日发帖
    function initPostCir(){
          var data = {};
     	  data.SIGN = "1";
      	  data.AREA_CODE = area_code;
      
		$.ajax({
			url : url + '/cockpit/CockpitController/getNoteAnalysis.do',
			data :{'data':JSON.stringify(data)},
			type : 'post',
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
				if(null != data && "" != data){
					
					var datas = data.list;
					
					var html="";
					for(i=0;i<datas.length;i++){
						html += "<div class='alock'>"+datas[i].NOTE_CAT_NAME;
						if("1"==datas[i].IS_ESTATE_FLAG){
							html += "<font color='red' size='2' style='vertical-align: middle;'>*&nbsp;</font><span id='"+ post[i] +"' style='font-size:5px;'>"+datas[i].COMPL_TOTAL+"</span>&nbsp;条 ";
						}else{
							html += "<span id='"+ post[i] +"' style='font-size:5px;'>"+datas[i].NOTE_TOTAL+"</span>&nbsp;条 ";
						}
						html += "</div>";
						
					}
					
					$("#post").append(html); 
					createPostCir(data);
				}
			}
		});
	}
	
	//创建饼图加载数据数组
	function createPostCir(data){
	    
		var list = data.list;
		var map = data.map;
		var chart = {
			legend:[],	
    		dataset:[]
    	};
    	$.each(list,function(i,v){
    		chart.legend.push(v.NOTE_CAT_NAME);
    		chart.dataset.push({
    				name: v.NOTE_CAT_NAME,
	               	value: v.NOTE_TOTAL
	        })
        });
      	initPostCirChart(chart,map);
	}
	
	//加载圆饼图
	function initPostCirChart(datas,map) {
	    //获取当前父元素的宽度
		var width = $(".eleven-con").find(".cockpit-detail-con").width() * 0.65;
		//获取类别总数
		var total = map.TOTAL;
		var myChart = echarts.init(document.getElementById("postCir"));
		var option = {
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		    	orient: 'vertical',
		        x: 'right',
		        data:datas.legend
		    },
		    series : [
		        {
		            name: '',
		            type: 'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            data:datas.dataset,
		            itemStyle: {
		                emphasis: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                }
		            }
		        }
		    ]
		};
		myChart.setOption(option);
	}
	
	//报修 发现 投诉折线图
	function initTodayRepair(date){
        var data = {};
     	data.SIGN = "1";
        data.END_DATE = date;
        data.AREA_CODE = area_code;
        
        data.NUMBER = "7";
		$.ajax({
			url : url + '/cockpit/CockpitController/getCmptRepNoteInfo.do?',
			data :{'data':JSON.stringify(data)},
			type : 'post',
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
			    
				if(null != data && "" != data){
				 
				 createTodayRepair(data);
				}
			}
		});
	
	}
	
	
	function createTodayRepair(data){
		var chart = {
    		legend:['报修','投诉','发帖'],	
    		xAxis:[],
    		//报修数
    		dd_dataset:[],
    		//投诉数
    		ee_dataset:[],
    		//发帖数
    		ff_dataset:[]
    	};
    	$.each(data,function(i,v){
    	    //x轴数据封装
    		chart.xAxis.push(v.DATE);
    	    //报修数数据封装
    	    chart.dd_dataset.push(v.REP_TOTAL);
    	    //投诉数数据封装
    	    chart.ee_dataset.push(v.CMP_TOTAL);
    	    //发帖数数据封装
    	    chart.ff_dataset.push(v.NOTE_TOTAL);
        }); 

      	initTodayRepairChart(chart);
	}
	
	function initTodayRepairChart(chart){
		//获取当前父元素的宽度
		var width = $(".twelve-con").find(".cockpit-detail-con").width();
		var myChart = echarts.init(document.getElementById("repairComplaintPost"));
	    var option = {
    	    title: {
    	        text: '报修投诉发帖',
    	        x:'center'
    	    },
    	    tooltip: {
    	        trigger: 'axis'
    	    },
    	    legend: {
    	    	left : 'right',
    	        data:chart.legend
    	    },
    	    grid: {
    	        left: '3%',
    	        right: '4%',
    	        bottom: '3%',
    	        containLabel: true
    	    },
    	    xAxis: {
    	        type: 'category',
    	        boundaryGap: false,
    	        data: chart.xAxis
    	    },
    	    yAxis: {
    	        type: 'value'
    	    },
    	    series: [
    	        {
    	            name:'报修',
    	            type:'line',
    	            stack: '总量',
    	            itemStyle: {
		                normal: {
		                    color: "#00bcc6",
		                    lineStyle: {
		                        color: "#00bcc6"
		                    }
		                }
		            },
    	            data:chart.dd_dataset
    	        },
    	        {
    	            name:'投诉',
    	            type:'line',
    	            stack: '总量',
    	            itemStyle: {
		                normal: {
		                    color: "#fff01f",
		                    lineStyle: {
		                        color: "#fff01f"
		                    }
		                }
		            },
    	            data:chart.ee_dataset
    	        },
    	        {
    	            name:'发帖',
    	            type:'line',
    	            stack: '总量',
    	            itemStyle: {
		                normal: {
		                    color: "#fe3772",
		                    lineStyle: {
		                        color: "#fe3772"
		                    }
		                }
		            },
    	            data:chart.ff_dataset
    	        }
    	    ]
    	};
	    myChart.setOption(option);
	}
	
	//双击展示大图
	function showCockpit(flag,title){
		 
		var sendDialog = parent.ns.modalDialog({
			title : title,
			width :680,
			height : 500,
			resizable : true,
			url :url+'/cockpit/CockpitController/showCockpit.do?flag='+ flag +'&title='+encodeURI(encodeURI(title))+'&area_code='+area_code
		});
	}
	
	/* =======计算日期========= */
	function addDate(date,days){ 
	   var now = date.split('-');
	   now = new Date(Number(now['0']),(Number(now['1'])-1),Number(now['2']));  
	   now.setDate(now.getDate() + days);
       var m=now.getMonth()+1; 
       return now.getFullYear()+'-'+m+'-'+now.getDate(); 
       
    } 
