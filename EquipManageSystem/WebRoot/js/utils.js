function showErrorText(MapData,choodeElement)
{
	if(MapData != null)
	{
		var temp = "<font color='red'>"+MapData+"</font>";
		var errorText = document.getElementById(choodeElement);
		errorText.innerHTML = temp;
	}
	else
	{
		var errorText = document.getElementById(choodeElement);
		errorText.innerHTML = "     ";
	}
}



function send_ajax(formName,handleSucc,handleError)
{
	//获取请求路径
	var targetUrl = $("#"+formName).attr("action");
	console.log("targetUrl:  "+targetUrl);
	//数据序列化 
	var data = $("#"+formName).serialize();
	console.log("data: "+data);
	$.ajax({
		type:"POST",
		url:targetUrl,
		cache:false,
		async : false,
		dataType : "json",
		data:data,
		success:function(data){ 
 			handleSucc(data);
 		},
 		error:function(data){ 
 			handleError(data);
 		}
	});
}

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
