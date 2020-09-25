// 将dateTime 格式转换成String yyyy-MM-dd HH:mm:ss
function Todate(num) { 
	num = num + "";
	var date = "";
	var month = new Array();
	month["Jan"] = 1;
	month["Feb"] = 2;
	month["Mar"] = 3;
	month["Apr"] = 4;
	month["May"] = 5;
	month["Jun"] = 6;
	month["Jul"] = 7;
	month["Aug"] = 8;
	month["Sep"] = 9;
	month["Oct"] = 10;
	month["Nov"] = 11;
	month["Dec"] = 12;
	var week = new Array();
	week["Mon"] = "一";
	week["Tue"] = "二";
	week["Wed"] = "三";
	week["Thu"] = "四";
	week["Fri"] = "五";
	week["Sat"] = "六";
	week["Sun"] = "日";
	str = num.split(" ");
	date = str[5] + "-";
	date = date + month[str[1]] + "-" + str[2] + " " + str[3];
	return date;
}



function dateConvert(dateParms){

//console.log("data"+dateParms);
// 对传入的时间参数进行判断
if(dateParms instanceof Date){
	console.log("data: "+dateParms);
	var datetime=dateParms;
}
//判断是否为字符串
if((typeof dateParms=="string")&&dateParms.constructor==String){
	console.log("data: "+dateParms);
	//将字符串日期转换为日期格式
	var datetime= new Date(Date.parse(dateParms.replace(/-/g, "/")));
}
//console.log("data: "+dateParms);
var datetime= new Date(dateParms);
//获取年月日时分秒
var year = datetime.getFullYear();
var month = datetime.getMonth()+1;
var date = datetime.getDate();
var hour = datetime.getHours();
var minutes = datetime.getMinutes();
var second = datetime.getSeconds();

//月，日，时，分，秒 小于10时，补0
if(month<10){
month = "0" + month;
}
if(date<10){
date = "0" + date;
}
if(hour <10){
hour = "0" + hour;
}
if(minutes <10){
minutes = "0" + minutes;
}
if(second <10){
second = "0" + second ;
}

//拼接日期格式【例如：yyyymmdd】
//var time = year+month+date;

//或者：其他格式等
var time = year+"年"+month+"月"+date+"日"+hour+":"+minutes+":"+second;

//返回处理结果
return time;
}






/* 已经不采用  只为了参考*/
function setNoEmptyEleValue()
{
	if($("#equip_name").val() != '')
	{
		equip_name = $("#equip_name").val();
	}
		
	if($("#status option:selected").val() != '')
	{
		equip_curr_status = $("#status option:selected").val();
	}
		
	if($("#equip_login_time").val() != '')
	{
		equip_login_time = $("#login_time").val();
	}
	if($("#equip_logout_time").val() != '')
	{
		equip_logout_time = $("#logout_time").val();
	}
}