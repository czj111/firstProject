$(function(){
    $("#btnLogin").click(startLogin);
    $("#close").click(closeLogin);
    $("#checkRefresh").click(changeCheckCode);
    $("#loginForm").submit(login);
})

/**
 * 打开登录功能
 */
function startLogin() {
    $("#ucLoginShadow").css("display","block");
    $("#ucLogin").css("display","block");
}
/**
 * 关闭登录功能
 */
function closeLogin() {
    $("#ucLoginShadow").css("display","none");
    $("#ucLogin").css("display","none");
}
/**
 * 验证码图
 */
function changeCheckCode() {
    $("#checkCode").prop("src","checkCode?"+new Date().getTime());
}

/**
 * 验证验证码
 */
function funCheck() {
    var check=$("#check").val();
    var flag;
    $.ajax({
        type:"POST",
        async:false,
        url:"user/check",
        data:"check="+check,
        dataType:"json",
        success:function(data){
            flag=data.flag;
            if(!data.flag)
            {
                $("#check").val("");
                changeCheckCode();
                $("#errorMsg").text(data.errorMsg);
            }
        }
    });
    return flag;
}


/**
 * 登录
 */
function login(){
    console.log("error");
    var username=$("#username").val();
    var password=$("#password").val();
    if(username!="" && password!="")
    {
        if(funCheck()) {
            var flag;
            $.ajax({
                type:"POST",
                async:false,
                url:"managerLoginServlet",
                data:$(this).serialize(),
                success:function(data){
                    flag=data.flag;
                    if(!data.flag)
                    {
                        $("#check").val("");
                        changeCheckCode();
                        $("#errorMsg").text(data.errorMsg);
                    }
                }
            });
            if(flag)
            {
                location.href="manager.html";
            }
        }
    }
    else
    {
        $("#errorMsg").text("请输入管理员名或密码");
    }
    return false;
}