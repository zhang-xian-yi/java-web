function btn_login_onclicked()
{
	//console.log("aaaaa")
	window.location.href = "./login.html"
	//console.log("xxxxxxx")
}

/*重新加载底部的时候完全显示页面*/
function reinitIframe()
{
	console.log("reinitIframe start")
	var iframe = document.getElementById("urlIframe");                
	try 
	{                          
	    var bHeight = iframe.contentWindow.document.body.scrollHeight;                
	    /*       
	    var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;              
	    var height = Math.max(bHeight, dHeight);                          
	    iframe.height = height;                
	    */                         
	    iframe.height = bHeight;                      
	} 
    catch (ex)
	{

	}       
	console.log("reinitIframe end")
}