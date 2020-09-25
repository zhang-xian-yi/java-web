/**
 * 
 */
 function handleSuccData(data)
{
	//定义dom
	var dom = '';
	//清除以前的旧数据
	$("#equip_list").innerHTML = '';
	//alert('success');
	var jsondataStr = JSON.stringify(data);
	var MapData = $.parseJSON(jsondataStr);
	//console.log(MapData);
	//获取返回的json 对象
	var pageBean = MapData.data;
	//console.log("pageBean: "+pageBean);
	//这是一个数组 和 限制查询的数据数量有关
	var e_list = pageBean.data;
	//共有多少页
	var page_num  = pageBean.page_num;
	//一页有多少数据
	var page_size = pageBean.page_size;
	//数据库中 所有数据的数量 
	var data_count = pageBean.data_count;
	//当前页的页号 
	var page_curr = pageBean.page_curr;
	
	/*返回的记录 个数 可能没有页面大小那么多 所以循环条件 必须限制*/
	var size = e_list.length;
	
	//13 means return code select succ
	if(MapData.code == 13)
	{
		//循环加载 页面大小 条记录
		for (var index = 0; index < size; index++) 
		{
			//每一行数据 都是 一次循环 一共加载页面大小
			dom += '<tr onmouseover="this.style.backgroundcolor = "white" "onmouseout="this.style.backgroundcolor = "#f5fafe";">';
			// 加载 每一个字段信息
			dom += '<td id="equip_name" style="cursor: hand; height: 22px" align="center" width="12%">'+e_list[index].name+'</td>';
			dom += '<td style="cursor: hand; height: 22px" align="center" width="13%" class="createTime" >'+dateConvert(e_list[index].login_time.time)+'</td>';
			dom += '<td style="cursor: hand; height: 22px" align="center" width="13%" class="createTime" >'+dateConvert(e_list[index].logout_time.time)+'</td>';
			dom += '<td style="cursor: hand; height: 22px" align="center" width="12%">'+e_list[index].state+'</td>';
			dom += '<td style="cursor: hand; height: 22px" align="center"width="17%"><a href="/EquipManageSystem/equipControl/download?path='+e_list[index].file_path+'">下载文件</a></td>'
			//一行记录的 编辑 和删除按钮
			dom += '<td align="center" style="height: 22px" width="8%"> <a href="/EquipManageSystem/afterLogin/admin/editEquipment.html?name='+e_list[index].name+'"> <img src="/EquipManageSystem/afterLogin/admin/images/i_edit.gif"border="0" style="cursor: hand"></a></td>';
			dom += '<td align="center" style="height: 22px" width="8%"> <a href="javascript:ajax_delete_equipment(\''+e_list[index].name+'\')"> <img src="/EquipManageSystem/afterLogin/admin/images/i_del.gif"border="0" style="cursor: hand"></a></td>';
			
			//一行记录的  结束
			dom += '</tr>';
		}
	}
	else if(MapData.code == -13)
	{
		//查询失败
		dom +='<tr>暂无数据</tr>'
		//设置属性
		page_curr = 1;
		page_num = 1;		
	}
	
	//设置 页面
	var page_curr_html = document.getElementById("page_curr");
	page_curr_html.innerHTML = page_curr;
	var page_num_html = document.getElementById("page_num");
	page_num_html.innerHTML = page_num;
	
	//添加数据
	//console.log(dom); 	
	var equip_list = document.getElementById("equip_list");
	equip_list.innerHTML = dom;
	
}

function ajax_delete_equipment(delete_name)
{
	//获取请求路径
	var targetURL = "/EquipManageSystem/equipControl/deleteEquipment?name="+delete_name;
   	//FormData对象加入参数
	//console.log("delete name:"+delete_name);
	$.ajax({
		type:"DELETE",
		url:targetURL,
		success:function(data){ 
			console.log("delete succ");
 			AJAXsend_data_flush();
 		},
 		error:function(data){ 
 			console.log("error delete");
 		}
	});
	
	//后台接受json 数据格式的对象 似乎无法request。getP  得到 那么必须进行转换
}

function AJAXsend_data_flush()
{
	var data = {
		"page_curr":$("#page_curr").text(),
		"page_num":$("#page_num").text(),
		"page_size":$("#page_size option:selected").val()};
	var targetURL = "/EquipManageSystem/equipControl/getAllEquipments";
	$.ajax({
			type:"GET",
			url:targetURL,
			cache:false,
			dataType : "json",
			data:data,
			success:function(data){ 
	 			handleSuccData(data);
	 		},
	 		error:function(data){ 
	 			handleErrorData(data);
	 		}
		})
}


function handleErrorData(data)
{
	
} 

function handlePageErrorData(data)
{
	var jsondataStr = JSON.stringify(data);
	var MapData = $.parseJSON(jsondataStr);
	//console.log(MapData);
	//获取返回的json 对象
	console.log("data: "+MapData+"  msg: "+MapData.msg);
}

function handlePageSuccData(data)
{
	//返回的 pagebean  对象的解析函数
	handleSuccData(data);
}
 
 
 function AJAX_change_page(targetURL,data)
{
	$.ajax({
			type:"GET",
			url:targetURL,
			cache:false,
			dataType : "json",
			data:data,
			success:function(data){ 
	 			handlePageSuccData(data);
	 		},
	 		error:function(data){ 
	 			handlePageErrorData(data);
	 		}
		})
}

function check_page_info(page_curr,page_size,page_num)
{
	var reg_num = /^[1-9][0-9]*$/;
	//判断是不是数字
	//console.log("curr: "+page_curr+"num: "+page_num+ "  size: "+page_size);
	if((! reg_num.test(page_curr)) || (! reg_num.test(page_num)) || (!reg_num.test(page_size)) )
	{
		return false;
	}
	//总页数 == 1 没必要刷新
	//第一页 没有必要刷新
	if(page_curr == "1" && page_num == "1")
	{
		return false;
	}
	//当前页 》 总页数 数据异常
	if(page_curr > page_num)
	{
		return false;	
	}
	return true;
}
