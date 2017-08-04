<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@page import="java.util.UUID"%>
<%@page import="java.security.interfaces.RSAPublicKey"%>
<%@page import="org.apache.commons.lang.ArrayUtils"%>
<%@page import="org.apache.commons.codec.binary.Base64"%>
<%@page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="com.shuai.test.entity.Setting"%>
<%@page import="com.shuai.test.utils.SettingUtils"%>
<%@page import="com.shuai.test.utils.SpringUtils"%>
<%@page import="com.shuai.test.entity.Setting.CaptchaType"%>
<%@page import="com.shuai.test.entity.Setting.AccountLockType"%>
<%@page import="com.shuai.test.service.RSAService"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
String base = request.getContextPath();
String captchaId = UUID.randomUUID().toString();
Setting setting = SettingUtils.get();
String staticPath = setting.getStaticPath();
String siteUrl = setting.getSiteUrl();

ApplicationContext applicationContext = SpringUtils.getApplicationContext();
if (applicationContext != null) {
%>
<shiro:authenticated>
<%
	response.sendRedirect(base + "/admin/common/main.cgi");
%>
</shiro:authenticated>
<%
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<%
if (applicationContext != null) {
	RSAService rsaService = SpringUtils.getBean("rsaServiceImpl", RSAService.class);
	RSAPublicKey publicKey = rsaService.generateKey(request);
	String modulus = Base64.encodeBase64String(publicKey.getModulus().toByteArray());
	String exponent = Base64.encodeBase64String(publicKey.getPublicExponent().toByteArray());
	
	String message = null;
	String loginFailure = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
	if (loginFailure != null) {
		if (loginFailure.equals("org.apache.shiro.authc.pam.UnsupportedTokenException")) {
			message = "admin.captcha.invalid";
		} else if (loginFailure.equals("org.apache.shiro.authc.UnknownAccountException")) {
			message = "admin.login.unknownAccount";
		} else if (loginFailure.equals("org.apache.shiro.authc.DisabledAccountException")) {
			message = "admin.login.disabledAccount";
		} else if (loginFailure.equals("org.apache.shiro.authc.LockedAccountException")) {
			message = "admin.login.lockedAccount";
		} else if (loginFailure.equals("org.apache.shiro.authc.IncorrectCredentialsException")) {
			if (ArrayUtils.contains(setting.getAccountLockTypes(), AccountLockType.admin)) {
				message = "admin.login.accountLockCount";
			} else {
				message = "admin.login.incorrectCredentials";
			}
		} else if (loginFailure.equals("org.apache.shiro.authc.AuthenticationException")) {
			message = "admin.login.authentication";
		}
	}
%>
<title><%=SpringUtils.getMessage("admin.login.title")%> - Powered By SHOP</title>
<meta http-equiv="expires" content="0" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta name="author" content="SHOP Team" />
<meta name="copyright" content="SHOP" />
<meta name="renderer" content="webkit"> 
<link href="<%=staticPath%>/tiger/admin/css/common.css" rel="stylesheet" type="text/css" />
<link href="<%=staticPath%>/tiger/admin/css/login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=staticPath%>/tiger/admin/js/jquery.js"></script>
<script type="text/javascript" src="<%=staticPath%>/tiger/admin/js/jsbn.js"></script>
<script type="text/javascript" src="<%=staticPath%>/tiger/admin/js/prng4.js"></script>
<script type="text/javascript" src="<%=staticPath%>/tiger/admin/js/rng.js"></script>
<script type="text/javascript" src="<%=staticPath%>/tiger/admin/js/rsa.js"></script>
<script type="text/javascript" src="<%=staticPath%>/tiger/admin/js/base64.js"></script>
<script type="text/javascript" src="<%=staticPath%>/tiger/admin/js/common.js"></script>
<script type="text/javascript">
	$().ready( function() {
		
		var $loginForm = $("#loginForm");
		var $enPassword = $("#enPassword");
		var $username = $("#username");
		var $password = $("#password");
		var $captcha = $("#captcha");
		var $captchaImage = $("#captchaImage");
		var $isRememberUsername = $("#isRememberUsername");
		
		// 记住用户名
		if(getCookie("adminUsername") != null) {
			$isRememberUsername.prop("checked", true);
			$username.val(getCookie("adminUsername"));
			$password.focus();
		} else {
			$isRememberUsername.prop("checked", false);
			$username.focus();
		}
		
		// 更换验证码
		$captchaImage.click( function() {
			$captchaImage.attr("src", "<%=base%>/admin/common/captcha.cgi?captchaId=<%=captchaId%>&timestamp=" + (new Date()).valueOf());
		});
		
		// 表单验证、记住用户名
		$loginForm.submit( function() {
			if ($username.val() == "") {
				$.message("warn", "<%=SpringUtils.getMessage("admin.login.usernameRequired")%>");
				return false;
			}
			if ($password.val() == "") {
				$.message("warn", "<%=SpringUtils.getMessage("admin.login.passwordRequired")%>");
				return false;
			}
			if ($captcha.val() == "") {
				$.message("warn", "<%=SpringUtils.getMessage("admin.login.captchaRequired")%>");
				return false;
			}
			
			if ($isRememberUsername.prop("checked")) {
				addCookie("adminUsername", $username.val(), {expires: 7 * 24 * 60 * 60});
			} else {
				removeCookie("adminUsername");
			}
			
			var rsaKey = new RSAKey();
			rsaKey.setPublic(b64tohex("<%=modulus%>"), b64tohex("<%=exponent%>"));
			var enPassword = hex2b64(rsaKey.encrypt($password.val()));
			$enPassword.val(enPassword);
		});
		
		<%if (message != null) {%>
			$.message("error", "<%=SpringUtils.getMessage(message, setting.getAccountLockCount())%>");
		<%}%>
	});
</script>
<%} else {%>
<title>提示信息 - Powered By SHOP</title>
<meta http-equiv="expires" content="0" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta name="author" content="SHOP Team" />
<meta name="copyright" content="SHOP" />
<link href="<%=staticPath%>/tiger/admin/css/common.css" rel="stylesheet" type="text/css" />
<link href="<%=staticPath%>/tiger/admin/css/login.css" rel="stylesheet" type="text/css" />
<%}%>
</head>
<body>
<div class="home">
	<div class="logo">
		<a href="<%=siteUrl %>"><img src="<%=staticPath%>/tiger/admin/images/logo.png" /></a>
		<span>账号登录</span>
	</div>
</div>
<div class="banner">
	<%if (applicationContext != null) {%>
		<div class="login">
		<div class="login-tab">
			<div class="tab">
				<form id="loginForm" action="login.jsp" method="post">
					<input type="hidden" id="enPassword" name="enPassword" />
					<%if (ArrayUtils.contains(setting.getCaptchaTypes(), CaptchaType.adminLogin)) {%>
						<input type="hidden" name="captchaId" value="<%=captchaId%>" />
					<%}%>
					<div class="tab-one">
						<span><%=SpringUtils.getMessage("admin.login.username")%>:<input type="text" name="username" id="username" maxlength="20"/></span>
					</div>
					<div class="tab-one">
						<span><%=SpringUtils.getMessage("admin.login.password")%>:<input type="password" id="password" maxlength="20" /></span>
					</div>
					<%if (ArrayUtils.contains(setting.getCaptchaTypes(), CaptchaType.adminLogin)) {%>
					<div class="tab-two">
						<span><%=SpringUtils.getMessage("admin.captcha.name")%>:</span>
					</div>
					<div class="tab-three">
						<span><input type="input" id="captcha" name="captcha" class="text1" maxlength="4" autocomplete="off"/></span>
					</div>
					<div class="tab-img">
						<img id="captchaImage" class="code_img" src="<%=base%>/admin/common/captcha.cgi?captchaId=<%=captchaId%>" title="<%=SpringUtils.getMessage("admin.captcha.imageTitle")%>" />
					</div>
					<%}%>
					<div class="tab-four">
					<label>
						<input type="checkbox" id="isRememberUsername" value="true" />
						<%=SpringUtils.getMessage("admin.login.rememberUsername")%>
					</label>
					</div>
					<div class="tab-button">
						<input type="submit" name="login_btn" id="login_btn" value="<%=SpringUtils.getMessage("admin.login.login")%>" class="loginbtnfocus btn" />
					</div>
				</form>
			</div>	
		</div>
	</div>
	<div class="text_bg"><img src="<%=staticPath%>/tiger/admin/images/text_bg.png" /></div>
	<div class="img_bg"><img src="<%=staticPath%>/tiger/admin/images/login_background.jpg" /></div>
</div>
<div class="last">
	<p>  中策橡胶 车空间 Copyright&copy;2008-2015 All Right Reserved.</p>
</div>
	<%} else {%>
		<fieldset>
			<legend>系统出现异常</legend>
			<p>请检查ShowCoo程序是否已正确安装 [<a href="<%=base%>/install/">点击此处进行安装</a>]</p>
			<p>
				<strong>提示: ShowCoo安装完成后必须重新启动WEB服务器</strong>
			</p>
		</fieldset>
	<%}%>
</body>
</html>