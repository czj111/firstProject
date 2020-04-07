$(function(){
    $("#username").blur(funUserName);
    $("#password").blur(funPassword);
    $("#email").blur(funEmail);
    $("#name").blur(funName);
    $("#telephone").blur(funPhone);
    $("#birthday").blur(funDate);
    $("#checkCode").click(changeCheckCode);
    // 提交表单,异步请求
    $("#registerForm").submit(function(){
        if( funUserName() && funPassword() && funEmail()&& funName() && funPhone() && funDate() && funCheck())
        {
            $.post("user/register",$(this).serialize());
        }

        return false;
        })

});

/**
 *验证用户名
 * @returns {boolean}
 */
function funUserName() {
    var username=$("#username").val();
    if(username==null || username=="")
    {
        console.log("请输入用户名");
        return false;
    }
    else{
        var flag;
        $.ajax({
            type:"POST",
            async:false,
            url:"user/rgUserName",
            data:"username="+username,
            dataType:"json",
            success:function(data){
                if(data.error==null)
                {
                    flag= true;
                }
                else
                {
                    flag= false;
                }
            }
        });
        return flag;
    }
}

/**
 * 验证密码
 * @returns {*}
 */
function funPassword(){

    var password=$("#password").val();
    if(password==null || password=="")
    {
        console.log("请输入密码");
        return false;
    }
    else{
        if(password.length<8 || password.length>20)
        {
            console.log("密码由8-20位字符组成");
            return false;
        }
        else
        {
            var regx1=/^(?:\d+|[a-zA-Z]+|[!@#$%^&*]+)$/;
            var regx2=/^(?![a-zA-z]+$)(?!\d+$)(?![!@#$%^&*]+$)[a-zA-Z|\d!|@#$%^&*]+$/;
            // var regx3=/^(?![a-zA-z]+$)(?!\d+$)(?![!@#$%^&*]+$)(?![a-zA-z\d]+$)(?![a-zA-z!@#$%^&*]+$)(?![\d!@#$%^&*]+$)[a-zA-Z\d!@#$%^&*]+$/;
            if(regx1.test(password))
            {
                console.log("密码强度弱");
            }
            if(regx2.test(password))
            {
                console.log("密码强度强");
            }
            return true;
        }

    }
}

/**
 * 验证邮箱
 * @returns {boolean}
 */
function funEmail(){
    var email=$("#email").val();
    var reg=/^\w+@\w+\.\w+$/;
    var flag=reg.test(email);
    if(flag)
    {
        console.log("正确")
    }
    else
    {
        console.log("请输入正确的邮箱格式");
    }
    return flag;
}

/**
 * 验证姓名
 * @returns {boolean}
 */
function funName(){
    var name=$("#name").val();
    if(name==null || name=="")
    {
        console.log("请输入姓名");
        return false;
    }
    else
    {
        console.log("已有姓名");
        return true;
    }


}

/**
 * 验证电话号码
 * @returns {boolean}
 */
function funPhone(){
    var telephone=$("#telephone").val();
    if(telephone==null || telephone=="")
    {
        console.log("请输入电话号码");
        return false;
    }
    else
    {
        console.log("已有号码");
        return true;
    }

}

/**
 * 验证日期
 * @returns {boolean}
 */
function funDate(){
    var birthday=$("#birthday").val();
    if(birthday==null || birthday=="")
    {
        console.log("请输入生日");
        return false;
    }
    else
    {
        console.log("已有生日");
        return true;
    }
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
            if(data.flag)
            {
                console.log(data.errorMsg);
            }
            else
            {
                console.log(data.errorMsg);
            }
        }
    });
    if(!flag)
    {
        $("#check").val("");
        changeCheckCode();
    }
    return flag;
}

/**
 * 验证码图
 */
function changeCheckCode() {
    $("#checkCode").prop("src","checkCode?"+new Date().getTime());
}
