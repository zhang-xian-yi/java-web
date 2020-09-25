$(function () {
    var surl = location.href;
    var surl2 = $(".place a:eq(1)").attr("href");
    $("#nav .zh1 li a").each(function () {
        if ($(this).attr("href") == surl || $(this).attr("href") == surl2) $(this).parent().addClass("on")
    });
});

$(document).ready(function () {
    var tags_a = $("#divTags a");
    tags_a.each(function () {
        var x = 9;
        var y = 0;
        var rand = parseInt(Math.random() * (x - y + 1) + y);
        $(this).addClass("tags" + rand);
    });
})


$("#nav>ul>li").hover(function() {
	if($(this).find("li").length > 0){
		$(this).children("ul").stop(true, true).fadeIn(400);
		$(this).addClass("hover");
	}
},function() {
	$(this).children("ul").stop(true, true).fadeOut();
	$(this).removeClass("hover");
});


$(".nav-on").click(function () {
    $("#nav>ul").fadeIn();
});
$(".nav-off").click(function () {
    $("#nav>ul").fadeOut();
});



$(function () {
    var nav = $("#nav"); //得到导航对象
    var win = $(window); //得到窗口对象
    var sc = $(document); //得到document文档对象。
    win.scroll(function () {
        if (sc.scrollTop() >= 100) {
            nav.addClass("fixednav");
            $(".navTmp").fadeIn();
        } else {
            nav.removeClass("fixednav");
            $(".navTmp").fadeOut();
        }
    })
})




$(document).ready(function() { 
    var tags_a = $("#divhottag a"); 
    tags_a.each(function(){ 
        var x = 6; 
        var y = 0; 
        var rand = parseInt(Math.random() * (x - y + 1) + y); 
        $(this).addClass("tags"+rand); 
    }); 
})  

$(document).ready(function() { 
    var tags_a = $("#divrandtag a"); 
    tags_a.each(function(){ 
        var x = 6; 
        var y = 0; 
        var rand = parseInt(Math.random() * (x - y + 1) + y); 
        $(this).addClass("tags"+rand); 
    }); 
})  

$(function(){
    $('.tx-tab-hd li').click(function(){
        $(this).addClass('tx-on').siblings().removeClass('tx-on');
        $('.tx-tab-bd ul').hide().eq($(this).index()).show();
    })
})

//返回顶部
document.writeln('<div id="kfbacktop" class="kfbacktop"><a id="backTop" href="javascript:;" style="display: block;" target="_self"><i></i></a></div>');
function backfuc(){jQuery(this).scrollTop() > 100 ? jQuery("#kfbacktop").css("display","block"):jQuery("#kfbacktop").css("display","none");}
jQuery(function(){jQuery("#backTop").click(function(){jQuery("html,body").animate({scrollTop:0},500)});backfuc();jQuery(window).bind("scroll", backfuc).bind('resize', backfuc);})	


window._bd_share_config = {
			common: {
				"bdSnsKey": {
					"tqq": _deel.appkey.tqq || null,
					"tsina": _deel.appkey.tsina || null,
				},
				"bdText": "『" + $("title").text() + "』" + $(".article-content p:lt(2)").text(),
				"bdMini": "2",
				"bdMiniList": false,
				"bdPic": $(".article-content img:first") ? $(".article-content img:first").attr("src") : "",
				"bdStyle": "0",
				"bdSize": "24"
			},
			share: [{
				bdCustomStyle: _deel.url + '/css/share.css'
			}],
		};
		with(document) 0[(getElementsByTagName("head")[0] || body).appendChild(createElement("script")).src = "http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion=" + ~ ( - new Date() / 36e5)];
		
		
		
		
	