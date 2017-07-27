(function($){
    //备份jquery的ajax方法
    var _ajax=$.ajax;

    //重写jquery的ajax方法
    $.ajax=function(opt){
        //备份opt中error和success方法
        var fn = {
            error:function(XMLHttpRequest, textStatus, errorThrown){},
            success:function(data, textStatus){}
        };
        if(opt.error){
            fn.error=opt.error;
        }
        if(opt.success){
            fn.success=opt.success;
        }

        //扩展增强处理
        var loadinglayer ;
        var _opt = $.extend(opt,{
            error:function(XMLHttpRequest, textStatus, errorThrown){
                //错误方法增强处理
                console.log(textStatus);
                console.log(opt);
                console.log(errorThrown);
                window.location.href = "/"+XMLHttpRequest.status;
                layer.close(loadinglayer);
                fn.error(XMLHttpRequest, textStatus, errorThrown);
            },
            beforeSend:function (XMLHttpRequest, textStatus, errorThrown) {
            },
            success:function(data, textStatus){
                //成功回调方法增强处理
                fn.success(data, textStatus);
            }

        });
        _ajax(_opt);
    };
})(jQuery);
(function ($) {
    $.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]); return null;
    }
})(jQuery);

$(function () {
    $("#logout").click(function () {
        var logOut = layer.confirm("确定退出系统？",
            {icon: 3, title: "提示", fixed: true, move: false, offset: "10%"}
            , function () {
                window.location.href = "/logout";
            }, function () {
                layer.close(logOut);
            });
    });
    $(".rightTopLi").click(function(){
        var href = $(this).data("href");
        if(href == "#"){
            alert("功能即将开放~");
        }else {
            window.location.href = href;
        }
    });
    $("i.show-pwd").click(function () {
        var $input = $(this).toggleClass("fa-eye").toggleClass("fa-eye-slash").parent().find("input");
        if($input.attr("type").toLowerCase()=="text"){
            $input.attr("type","password");
        }else {
            $input.attr("type","text");
        }
    });
    $("#input-logo").click(function () {
        window.location.href = "/";
    });
    $(".logo-right-img").click(function () {
        window.open("http://www.hzzcckj.com/","_blank")
        // window.location.href = "http://www.hzzcckj.com/";
    });
    $("#back").click(function () {
        window.location.href = "/re";
    });

});
function showWarning(msg) {
    layer.msg(msg,{offset:"22%",anim:6});
}
function showTips(msg,$appender) {
    layer.tips(msg, $appender,{tips:[1,"#fb6187"]});
}
function initKaptcha(handler) {
    var product = isMobileDevice? "popup":"float";
//            var product = "float";
    $.ajax({
        url: "/captcha?t=" + (new Date()).getTime(), // 加随机数防止缓存
        type: "get",
        dataType: "json",
        success: function (data) {
            initGeetest({
                gt: data.gt,
                challenge: data.challenge,
                new_captcha: data.new_captcha,
                offline: !data.success,
                product: product,
                width: "100%"
            }, handler);
        }
    });
}
function getCode(codeType,$phone,$btn,captchaObj) {
    var phone = $phone.val();
    if (!isMobile(phone)) {
        showWarning("手机号不合法");
        $phone.focus();
        return false;
    }
    var result = captchaObj.getValidate();
    if(!result){
        showWarning("请先通过人机验证");
        return false;
    }else {
        $.ajax({
            url: "/user/code",
            type: "post",
            dataType: 'json',
            data: {
                phone: phone,
                codeType: codeType,
                geetest_challenge: result.geetest_challenge,
                geetest_validate: result.geetest_validate,
                geetest_seccode: result.geetest_seccode
            },
            success: function (data) {
                if (data.success) {
                    layer.msg('验证码获取成功', {offset: "7%"});
//                            $btn.attr("disabled",true);
                    countTime($btn);
                    return true;
                } else {
                    showWarning(data.message);
                    captchaObj.reset();
                    return false;
                }
            }
        });
    }
}
function isMobile(phoneNum) {
    return /^1[3|4|5|7|8][0-9]{9}$/.test(phoneNum);
}
function validatePwd(pwd) {
    var reg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/;
    return reg.test(pwd);
}
var countdown = 60;
function countTime($btn) {
    if (countdown === 0) {
        $btn.removeAttr("disabled");
        $btn.text("获取");

//                $btn.toggleClass("btn-success","btn-default");
        countdown = 60;
        return;
    } else {
        $btn.attr("disabled", true);
        $btn.text("重新发送(" + countdown + ")");
        countdown--;
    }
    setTimeout(function () {
        countTime($btn);
    }, 1000)
}