$(function(){
    $("#checkRefresh").click(changeCheckCode);
    $("#loginForm").submit(login);
    $("#close").click(closeLogin);
    $("#btnLogin").click(startLogin);
    $("#btnRegist").click(regist);
});

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
    // if(!flag)
    // {
    //     $("#check").val("");
    //     changeCheckCode();
    // }
    return flag;
}

/**
 * 登录
 */
function login(){
    var username=$("#username").val();
    var password=$("#password").val();
    if(username!="" && password!="")
    {
        if(funCheck()) {
            var flag;
            $.ajax({
                type:"POST",
                async:false,
                url:"user/login",
                dataType:"json",
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
                location.href="homePage.html";
            }
        }
    }
    else
    {
        $("#errorMsg").text("请输入用户名或密码");
    }
    return false;
}

/**
 * 验证码图
 */
function changeCheckCode() {
    $("#checkCode").prop("src","checkCode?"+new Date().getTime());
}

/**
 * 关闭登录功能
 */
function closeLogin() {
    $("#ucLoginShadow").css("display","none");
    $("#ucLogin").css("display","none");
}

/**
 * 打开登录功能
 */
function startLogin() {
    $("#ucLoginShadow").css("display","block");
    $("#ucLogin").css("display","block");
}

/**
 * 点击注册，进入注册页面
 */
function regist(){
    window.location="register.html";
}