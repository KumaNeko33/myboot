[#if showInfo.content??]
<span title="${showInfo.content?html}">${abbreviate(showInfo.content?html, 40, "...")}</span>
    [#--内容大于40字符的才加查看全文的按钮--]
    [#if showInfo.content?html?length gt 40]
    <a  href="javascript:;" style="font-family : 微软雅黑,宋体;color: #00F;" onclick="tips(this)" value="${showInfo.content?html}">[查看全文]</a>
    [/#if]
[/#if]

<script type="text/javascript">
    function tips(a) {
        var that = a;
        var content = $(a).attr("value").toString();//获取a标签的value属性值  attr(key,value)设置属性key的值为value
        layer.tips(content, that,{
            shade: [0.001,'#FFFFFF'], //遮罩透明度越低越小，且配置遮罩背景色为白色，达到遮罩基本透明效果，这样配合点击遮罩关闭tips，达到手动关闭tips的功能
            shadeClose: true,   //遮罩开启
            tips: [3, '#C43839'],//tips层的私有参数。支持上右下左四个方向，通过1-4进行方向设定。如tips: 3则表示在元素的下面出现。有时你还可能会定义一些颜色，可以设定tips: [1, '#c00']
            time:20000,//定义20s后自动关闭tips，单位毫秒，1秒=1000毫秒
        });
    }; //在元素的事件回调体中，follow直接赋予this即可
</script>
<script>
    var $labelsFilterOption = $("#labelsFilterOption a");
    // 筛选选项
    $labelsFilterOption.click(function() {
        var $this = $(this);//当前的a标签
        var $dest = $("#" + $this.attr("name"));
        if ($this.hasClass("checked")) {
            $dest.val("");
        } else {
            $dest.val($this.attr("val"));
        }
        $listForm.submit();
        return false;
    });
</script>
<style type="text/css">
    /*让class="list"的table表格内的所有th标签内的span标签中的文字，在th标签中居中显示*/
    table.list span{
        display:block;//解锁边界，与父标签融合
        text-align: center;
    }
</style>
<body>
<table id="listTable" class="list" align="center">
    <tr>
        <th width="5%">
            <span>${message("SellerShow.createTime")}</span>
        </th>
        <th width="8%">
            <span>${message("SellerShow.imageUrls")}</span>
        </th>
        <th width="9%">
            <span>${message("SellerShow.content")}</span>
        </th>
        <th width="5%">
            <span>${message("SellerShow.storeName")}</span>
        </th>
        <th width="5%">
            <span>${message("SellerShow.supplierName")}</span>
        </th>
        <th width="8%">
            <span>${message("SellerShow.tireSpec")}</span>
        </th>
        <th width="5%">
            <span>${message("SellerShow.tireModels")}</span>
        </th>
        <th width="3%">
            <span>是否推荐</span>
        </th>
        <th width="5%">
            <span>推荐时间</span>
        </th>
        <th width="9%">
            <span>推荐标题</span>
        </th>
        <th width="3%">
            <span>${message("SellerShow.totalPraiseCount")}</span>
        </th>
        <th width="3%">
            <span>${message("SellerShow.totalShareCount")}</span>
        </th>
        <th width="3%">
            <span>${message("SellerShow.consumersPraiseCount")}</span>
        </th>
        <th width="3%">
            <span>${message("SellerShow.status")}</span>
        </th>
        <th width="3%">
            <span>${message("SellerShow.labels")}</span>
        </th>
        <th width="9%">
            <span>${message("admin.common.handle")}</span>
        </th>
    </tr>
[#--隐藏表单传递给后台--]
    [#if showInfo.tjStatus == 'TJ']
        <a href="javascript:;" class="changeTj" data-id="${showInfo.id}" data-tjStatus="${showInfo.tjStatus}">[设为推荐]</a>
    [#else]
        <a href="javascript:;" class="changeTj" data-id="${showInfo.id}" data-tjStatus="${showInfo.tjStatus}">[取消推荐]</a>
    [/#if]
    <form id="tjStatusForm" action="updateTjStatus.cgi" method="post">
        [#--后台controller根据name属性来对应注值如 Long id,String tjStatus--]
        <input type="hidden" name="id" id="tjId"/>
        <input type="hidden" name="tjStatus" id="tjStatusInp"/>
    </form>
</body>
<script type="text/javascript">
    $btnTjStatus.click(function () {
        var $this = $(this);
        var $id = $this.attr("data-id")
        var $tjStatus = $this.attr("data-tjStatus");
        var $message = "";
        if($tjStatus == "TJ"){
            $tjStatus = "FTJ";
            $message = "确认取消推荐该卖家秀？";
        }else{
            $tjStatus = "TJ";
            $message = ""
        }

        $.dialog({//jQuery的弹窗，
            type:"warn",//样式
            content:$message,//弹窗内容
            onOk: function () {//点确认后的 函数
                $("#tjId").val($id);
                $("#tjStatusInp").val($tjStatus);
                $("#tjStatusForm").submit();
            }
        });
    });
</script>

[#--图片的缩小展示${photoUrl}_32x32--]
<a href="${photoUrl}" target="_blank"><img src="${photoUrl}_32x32" width="40" height="40" /></a>

[#--通过properties，将controller传过来的如枚举类的字符串英文值 转换成 对应意义的中文,多种可能值--]
[#--
admin.sellerShow.tjStatus.TJ=\u63a8\u8350  推荐，对应枚举值TJ
admin.sellerShow.tjStatus.FTJ=\u975e\u63a8\u8350 非推荐，对应枚举值FTJ
--]
<td>${message("admin.sellerShow.tjStatus." + showInfo.tjStatus)}</td>

[#--问题：后台传来的时间Date格式在页面显示不了--]
[#--freemarker.template.TemplateModelException: Can't convert the date to string, because it is not known which parts of the date variable are in use. Use ?date, ?time or ?datetime built-in, or ?string.<format> or ?string(format) built-in with this date.--]
[#--freemarker.core.InvalidReferenceException: Expression showInfo.tjTm is undefined on line 445, column 27 in admin/seller_show/list.ftl.--]
[#--解决：将Date格式转成String类型的日期即可显示--]
<td>
    [#if showInfo.tjTm??]
        ${showInfo.tjTm?string("yyyy-MM-dd hh:mm:ss")}
    [/#if]
</td>


[#--需求，弹窗输入层--]
[#--解决：使用layer.prompt(options, yes) - 输入层--]
[#--prompt的参数也是向前补齐的。options不仅可支持传入基础参数，还可以传入prompt专用的属性。当然，也可以不传。yes携带value 表单值index 索引elem 表单元素--]
[#--使用prompt需要引入<script type="text/javascript" src="${base}/resource/editor/plugins/layer/extend/layer.ext.js"></script>--]
<script>
    //prompt层新定制的成员如下
    {
        formType: 1, //输入框类型，支持0（文本）默认1（密码）2（多行文本）
        value: '', //初始时的值，默认空字符
        maxlength: 140, //可输入文本的最大长度，默认500
    }
    应用：
layer.prompt({
        title:"请填写推荐标题",  //title: [message, 'font-size:15px;padding:0 0 0 15px'],可以设置title的css样式
        closeBtn: 0,
        area:['500px','400px'],
        //type: 2,//0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
        formType:2,//输入框类型，支持0（文本）默认1（密码）2（多行文本）
        value:'最多20字',
        maxlength:20,
    },function(value, index, elem){
        $("#tjTitle").val(value);//得到value
        $("#tjStatusForm").submit();
        layer.close(index);//关闭弹窗);
});//在写页面方法的时候一定要注意 括号的正常结束，少括号的话没有功能显示，前端调试才能看到错误很蛋疼
</script>
//添加新的js引用，热部署不会起作用，需要重启应用

[#--com.alibaba.dubbo.remoting.RemotingException: com.alibaba.dubbo.rpc.RpcException: Failed to invoke remote proxy method setTjStatus to registry://171.188.0.161:2181/com.alibaba.dubbo.registry.RegistryService?application=store-show-provider-mhs&dubbo=2.5.3&export=dubbo%3A%2F%2F10.1.221.111%3A20880%2Fcom.zcckj.storeshow.service.StoreShowService%3Fanyhost%3Dtrue%26application%3Dstore-show-provider-mhs%26dubbo%3D2.5.3%26group%3D_mhs%26interface%3Dcom.zcckj.storeshow.service.StoreShowService%26methods%3DgetShowInfo%2CdzOpt%2Csave%2CsetTjStatus%2Cupdate%2CstatisticStoreAndSpec%2CgetUnReadLogsCnt%2CgetDzLogs%2CgetDetailPage%2Cdelete%2CsetStatus%2CsetJxStatus%2CfxOpt%2CsetLabel%2CgetSimplePage%26owner%3Dstore-show%26pid%3D18296%26revision%3D1.0.0%26side%3Dprovider%26timeout%3D6000%26timestamp%3D1501728823434%26version%3D1.0.0&group=dubbo&owner=store-show&pid=18296&registry=zookeeper&timestamp=1501728823432, cause: Not found method "setTjStatus" in class com.zcckj.storeshow.service.StoreShowService.
com.alibaba.dubbo.rpc.RpcException: Failed to invoke remote proxy method setTjStatus to registry://171.188.0.161:2181/com.alibaba.dubbo.registry.RegistryService?application=store-show-provider-mhs&dubbo=2.5.3&export=dubbo%3A%2F%2F10.1.221.111%3A20880%2Fcom.zcckj.storeshow.service.StoreShowService%3Fanyhost%3Dtrue%26application%3Dstore-show-provider-mhs%26dubbo%3D2.5.3%26group%3D_mhs%26interface%3Dcom.zcckj.storeshow.service.StoreShowService%26methods%3DgetShowInfo%2CdzOpt%2Csave%2CsetTjStatus%2Cupdate%2CstatisticStoreAndSpec%2CgetUnReadLogsCnt%2CgetDzLogs%2CgetDetailPage%2Cdelete%2CsetStatus%2CsetJxStatus%2CfxOpt%2CsetLabel%2CgetSimplePage%26owner%3Dstore-show%26pid%3D18296%26revision%3D1.0.0%26side%3Dprovider%26timeout%3D6000%26timestamp%3D1501728823434%26version%3D1.0.0&group=dubbo&owner=store-show&pid=18296&registry=zookeeper&timestamp=1501728823432, cause: Not found method "setTjStatus" in class com.zcckj.storeshow.service.StoreShowService.
	at com.alibaba.dubbo.rpc.proxy.AbstractProxyInvoker.invoke(AbstractProxyInvoker.java:76)
	at com.alibaba.dubbo.rpc.protocol.InvokerWrapper.invoke(InvokerWrapper.java:53)
	at com.alibaba.dubbo.rpc.filter.ExceptionFilter.invoke(ExceptionFilter.java:64)
	at com.alibaba.dubbo.rpc.protocol.ProtocolFilterWrapper$1.invoke(ProtocolFilterWrapper.java:91)
	at com.alibaba.dubbo.monitor.support.MonitorFilter.invoke(MonitorFilter.java:75)
	at com.alibaba.dubbo.rpc.protocol.ProtocolFilterWrapper$1.invoke(ProtocolFilterWrapper.java:91)
	at com.alibaba.dubbo.rpc.filter.TimeoutFilter.invoke(TimeoutFilter.java:42)
	at com.alibaba.dubbo.rpc.protocol.ProtocolFilterWrapper$1.invoke(ProtocolFilterWrapper.java:91)
	at com.alibaba.dubbo.rpc.protocol.dubbo.filter.TraceFilter.invoke(TraceFilter.java:78)
	at com.alibaba.dubbo.rpc.protocol.ProtocolFilterWrapper$1.invoke(ProtocolFilterWrapper.java:91)
	at com.alibaba.dubbo.rpc.filter.ContextFilter.invoke(ContextFilter.java:60)
	at com.alibaba.dubbo.rpc.protocol.ProtocolFilterWrapper$1.invoke(ProtocolFilterWrapper.java:91)
	at com.alibaba.dubbo.rpc.filter.GenericFilter.invoke(GenericFilter.java:112)
	at com.alibaba.dubbo.rpc.protocol.ProtocolFilterWrapper$1.invoke(ProtocolFilterWrapper.java:91)
	at com.alibaba.dubbo.rpc.filter.ClassLoaderFilter.invoke(ClassLoaderFilter.java:38)
	at com.alibaba.dubbo.rpc.protocol.ProtocolFilterWrapper$1.invoke(ProtocolFilterWrapper.java:91)
	at com.alibaba.dubbo.rpc.filter.EchoFilter.invoke(EchoFilter.java:38)
	at com.alibaba.dubbo.rpc.protocol.ProtocolFilterWrapper$1.invoke(ProtocolFilterWrapper.java:91)
	at com.alibaba.dubbo.rpc.protocol.dubbo.DubboProtocol$1.reply(DubboProtocol.java:108)
	at com.alibaba.dubbo.remoting.exchange.support.header.HeaderExchangeHandler.handleRequest(HeaderExchangeHandler.java:84)
	at com.alibaba.dubbo.remoting.exchange.support.header.HeaderExchangeHandler.received(HeaderExchangeHandler.java:170)
	at com.alibaba.dubbo.remoting.transport.DecodeHandler.received(DecodeHandler.java:52)
	at com.alibaba.dubbo.remoting.transport.dispatcher.ChannelEventRunnable.run(ChannelEventRunnable.java:82)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
	at java.lang.Thread.run(Thread.java:748)
Caused by: com.alibaba.dubbo.common.bytecode.NoSuchMethodException: Not found method "setTjStatus" in class com.zcckj.storeshow.service.StoreShowService.
	at com.alibaba.dubbo.common.bytecode.Wrapper1.invokeMethod(Wrapper1.java)
	at com.alibaba.dubbo.rpc.proxy.javassist.JavassistProxyFactory$1.doInvoke(JavassistProxyFactory.java:46)
	at com.alibaba.dubbo.rpc.proxy.AbstractProxyInvoker.invoke(AbstractProxyInvoker.java:72)
	... 25 more
--]
//一般这种错都是因为修改了service服务后，确没有重新启动服务所致
//注：泛型中的类改变后，也需要重启应用才能生效，热部署没用

spring中的freemarker配置：
<bean id="freeMarkerConfigurer" class="org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer">
    <property name="templateLoaderPaths" value="${template.loader_path}" />
    <property name="freemarkerSettings">
        <props>
            <prop key="defaultEncoding">${template.encoding}</prop>
            <prop key="url_escaping_charset">${url_escaping_charset}</prop>
            <prop key="locale">${locale}</prop>
            <prop key="template_update_delay">${template.update_delay}</prop>
            <prop key="tag_syntax">auto_detect</prop>
            <prop key="whitespace_stripping">true</prop>
            <prop key="classic_compatible">true</prop>
            <prop key="number_format">${template.number_format}</prop>
            <prop key="boolean_format">${template.boolean_format}</prop>
            <prop key="datetime_format">${template.datetime_format}</prop>
            <prop key="date_format">${template.date_format}</prop>
            <prop key="time_format">${template.time_format}</prop>
            <prop key="object_wrapper">freemarker.ext.beans.BeansWrapper</prop>
            <prop key="template_update_delay">0</prop>
        </props>
    </property>
    <property name="freemarkerVariables">
        <map>
            <entry key="systemName" value="${system.name}" />
            <entry key="systemVersion" value="${system.version}" />
            <entry key="systemDescription" value="${system.description}" />
            <entry key="systemShowPowered" value="${system.show_powered}" />
            <entry key="base" value="#{servletContext.contextPath}" />
            <entry key="locale" value="${locale}" />
            <entry key="version" value="${static.version}" />
            <entry key="message" value-ref="messageMethod" />   //freeMarker引用自定义java函数
            <entry key="abbreviate" value-ref="abbreviateMethod" />
            <entry key="execute_time" value-ref="executeTimeDirective" />
            <entry key="currency" value-ref="currencyMethod" />
            <entry key="flash_message" value-ref="flashMessageDirective" />
            <entry key="pagination" value-ref="paginationDirective" />
            <entry key="current_member" value-ref="currentMemberDirective" />
            <entry key="ad_position" value-ref="adPositionDirective" />
            <entry key="distributor_list" value-ref="distributorListDirective" />
            <entry key="article_list" value-ref="articleListDirective" />
            <entry key="article_category_root_list" value-ref="articleCategoryRootListDirective" />
            <entry key="article_category_parent_list" value-ref="articleCategoryParentListDirective" />
            <entry key="article_category_children_list" value-ref="articleCategoryChildrenListDirective" />

        </map>
    </property>
</bean>
页面中：
${message("")}是freeMarker引用自定义java函数

在国际化消息配置文件message_zh_CN.properties中可使用占位符 {0},这样页面中可传值进来、
admin.page.total=\u5171<span id="pageTotal">{0}</span>\u6761\u8bb0\u5f55
页面中：
<span>(${message("admin.page.total", totalRecord)})</span> //totalRecord是后台传来的值，可以传入国际化消息配置文件message_zh_CN.properties中的占位符

&raquo;


**日期插件WdatePicker的使用：
（1）下载WdatePicker.js（包括lang和skin文件夹）。
（2）在html页面中导入WdatePicker.js。
（3）在输入框input元素上加入class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'beginDate\')}'})"代码。
开始日期-maxDate:'#F{$dp.$D(\'endDate\')||\'new Date()\'}'，里面的endDate对应结束日期的id
结束日期-minDate:'#F{$dp.$D(\'startDate\')}',maxDate:new Date()，里面的startDate对应开始日期的id
（4）打开页面查看效果。
推荐格式：开始日期的id和结束日期的minDate里的名称相同才能起到作用，同理，结束日期的id和开始日期的maxDate里的名称相同才能起到作用。
这样日期插件才能定位到当前标签正下方显示。如下：
<td>
    <input type="text" id="createTmBegin" name="createTmBegin" value="${createTimeBegin}"
           class="text Wdate"
           onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', maxDate: '#F{$dp.$D(\'createTmEnd\')}'});"/>
</td>
<td>&nbsp;&#150;</td>
<td>
    <input type="text" id="createTmEnd" name="createTmEnd" value="${createTimeEnd}" class="text Wdate"
           onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', minDate: '#F{$dp.$D(\'createTmBegin\')}'});"/>
</td>


表table内的元素CSS样式：
.myTable th{
width: 75px;
height:20px;
padding: 0px 0px 0px 15px;
text-align: right;
}
.myTable td {
line-height: 25px;
padding: 5px;
color: #666666;
}
//设置表单内td标签内的span标签的文字内容居中
.list span{
display: block;
text-align: center;
}

//热部署失效情况：类中新添方法需要重启应用
//隐藏域的表单form不得包含在 主表单form中 ，这样隐藏域的表单form.submit()会出错
//request可用来获取shiro安全框架等登录验证成功后存入session的用户信息，如：
Principal userPrincipal = request.getUserPrincipal();
String userName = userPrincipal.getName();获取登录用户名

//若有多个标签的id相同  通过id选择器进行赋值时只对第一个id=指定名称的标签 进行赋值，这时要么换成.class类选择器，要么改变不同标签的id

//    当数据不足一页的大小如pageSize=20时，不会进行分页，即看不到分页组件
[#--[@pagination pageNumber = page.pageNumber totalPages = totalPages]--]
    [#--[#include "*/admin/include/pagination.ftl"]--]
[#--[/@pagination]--]
//当一个javascript中的一个方法出现问题，会导致其他js方法一起失效


用compress directive或者transform来处理输出。
<#compress>...</#compress>：消除空白行。
<@compress single_line=true>...</@compress>将输出压缩为一行。都需要包裹所需文档
//freemarker可用"["代替"<".在模板的文件开头加上[#ftl].

使用layer的css样式及弹窗引入：
./layui/css/layui.css
./layui/layui.js
/extend/layer.ext.js  //用于prompt弹窗

//freemarker中前端获取 后端传过来的 list集合的单个值
    ${showContentList[0]}
//普通页面应该也行得通
showContentList[0]
//js中通过freemarker的方式获取后端传过来的 值：两边加引号即可，如：
    var a = '${showContentList[0]}';
    [@flash_message /]

**freemarker中判断字符串数组是否含有 某个字符串：
[#if role.authorities?seq_contains("admin:storeActivityModel")] checked="checked"[/#if]

/*页面的标签的 宽高是auto 是因为这些标签不是块元素，是inline的，需要设成display:block才能进行设置 宽高
display:inline
属性的元素默认宽高属性是auto，设置了浏览器也会变成auto，所以想要设置元素的宽高，需要把元素变成块元素，即

display:block
等
默认宽高是浏览器自动计算的inline元素内文本占据的行高等的高度，所以可以设置背景色

inline元素的高度与font-size相关，但不是font-size决定，
涉及到匿名框，行框，行内框，内容区等

font-size决定匿名框，匿名框构成内容区，内容区加行间距得到行内框高度，行内框的最高，最低点的最小框构成行框；

行间距是line-height与font-size之差，上下各1/2分布内容区上下

默认情况下，line-height:nomarl，转换成数字比1大，字体不同，行高也会不同

所以，你的字体是16px，但元素高度大于16
*/

layer弹出的页面层：在content中写入html标签后，和jQuery的$.dialog()方法类似：
layer弹出的页面层，是当前页面的一部分，即插入到当前页面 的body中
所以可以直接通过onclick()调用当前页面的javascript方法
layer弹出层也可以使用freemarker，然后javascript的加载先于freemarker语法
layer弹出层的content可以接收之前javascript的代码（title也可以），在content中写入html标签后，加个 ' + 前面用var声明的元素 + '  如：
var a = 'aaaa';
layer.open({
    content:'<div>'+a+'<\/div>',
});
    layer弹出层的content里面的html标签可以调用javascript方法，可以通过隐藏域来存储值，然后在调用的javascript方法通过$("#id")选择器来获取
原理：layer弹出层的html标签，虽然无法和当前页面的其他标签进行交互，但是仍然可以调用当前页面的js方法，所以可以通过js方法来实现 layer弹出层的html标签和当前页面的标签的传值互动,如下：
<script type="text/javascript">
    $(function () {
        $(".status").click(function () {
            var id = $(this).attr("data-id");
            $("#changeFlagModelId").val(id);
            var status = $(this).attr("data-status");
//                var content = $(this).attr("data-content");
            var countModel = $("#countModel").val();
            var message = "确定下架该卖家秀内容模板？";
            if(status) {
                if(countModel == 4) {
                    message = "请最少保留4个内容模板！";
                    layer.msg(message,{icon:2});
                    return false;
                }
            }
            if(!status) {
                if(countModel == 4) {
                    message = "最多上架4个，需选择下面某个模版下架才可上架此模版";
                    layer.open({
                        title: [message, 'font-size:15px;padding:0 0 0 15px'],
                        [@compress single_line = true]
                        content:'<table>
                            [#--<tr>--]
                                [#--<td class=\"myButton\"><button data-id=\"${showContentList[0].id}\" onclick="changeFlag(this)">${abbreviate(showContentList[0].content?html, 14, "...")}<\/button>--]
                                [#--<\/td>--]
                                [#--<td class=\"myButton\"><button data-id=\"${showContentList[1].id}\" onclick="changeFlag(this)">${abbreviate(showContentList[1].content?html, 14, "...")}<\/button>--]
                                [#--<\/td>--]
                            [#--<\/tr>--]
                            [#--<tr>--]
                                [#--<td class=\"myButton\"><button data-id=\"${showContentList[2].id}\" onclick=\"changeFlag(this)\">${abbreviate(showContentList[2].content?html, 14, "...")}<\/button>--]
                                [#--<\/td>--]
                                [#--<td class=\"myButton\"><button data-id=\"${showContentList[3].id}\" onclick="changeFlag(this)">${abbreviate(showContentList[3].content?html, 14, "...")}<\/button>--]
                                [#--<\/td>--]
                            [#--<\/tr>--]
                        [#--<\/table>',--]
                        [#--上面是当showContentList为空时无法通过页面编译的版本，于是改成下面这种可以骗过编译器，不使用明文的索引调用list，使用showContent_index来调用--]
                            [#if showContentList??]
                                [#list showContentList as showContent]
                                    [#if showContent_index = 0]
                                    <tr>
                                    <td class=\"myButton\"><button data-id=\"${showContentList[showContent_index].id}\" onclick="changeFlag(this)">${abbreviate(showContentList[showContent_index].content?html, 14, "...")}<\/button>
//                                    ${abbreviate(showContentList[showContent_index].content?html, 14, "...")}大于14字符的内容用...表示
                                    <\/td>
                                    [/#if]
                                    [#if showContent_index = 1]
                                    <td class=\"myButton\"><button data-id=\"${showContentList[showContent_index].id}\" onclick="changeFlag(this)">${abbreviate(showContentList[showContent_index].content?html, 14, "...")}<\/button>
                                    <\/td>
                                    <\/tr>
                                    [/#if]
                                    [#if showContent_index = 2]
                                    <tr>
                                    <td class=\"myButton\"><button data-id=\"${showContentList[showContent_index].id}\" onclick="changeFlag(this)">${abbreviate(showContentList[showContent_index].content?html, 14, "...")}<\/button>
                                    <\/td>
                                    [/#if]
                                    [#if showContent_index = 3]
                                    <td class=\"myButton\"><button data-id=\"${showContentList[showContent_index].id}\" onclick="changeFlag(this)">${abbreviate(showContentList[showContent_index].content?html, 14, "...")}<\/button>
                                    <\/td>
                                    <\/tr>
                                    [/#if]
                                [/#list]
                            [/#if]
                        <\/table>',
                        [/@compress]
                        area: ['400px','225px'],
                        closeBtn: 0,
                    })
                }
                return false;
            }
            layer.confirm(message, {icon: 3, title:'提示'},function (index) {
                $("#flag").val(!status);
                $("#id").val(id);
                $("#updateShowModelFlag").submit();
                layer.close(index);
            })
        });
        $("#addButtion").click(function () {
            layer.prompt({
                title: "设置模板内容（最多15字）",
                formType: 2,
                area:['350px','225px'],
                closeBtn: 0,
                maxlength: 15,
            },function (value, index, elem) {
                $("#storeModelContent").val(value);
                layer.confirm('确认保存？',{icon: 3, title:'提示'},function (index) {
                    $("#saveShowModel").submit();
                    layer.close(index);
                });
                layer.close(index);
            });
        });
        $(".editModel").click(function () {
            var id = $(this).attr("data-id");
            var content = $(this).attr("data-content");
            layer.prompt({
                title: "设置模板内容（最多15字）",
                formType: 2,
                area:['350px','225px'],
                closeBtn: 0,
                maxlength: 15,
                value: content,
            },function (value, index, elem) {
                $("#storeModelId").val(id);
                $("#updateModelContent").val(value);
                $("#updateShowModel").submit();
                layer.close(index);
            })
        });
</script>
<body>
<input type="hidden" id="changeFlagModelId" name="changeFlagModelId"/>
</body>
<script type="text/javascript">
    function changeFlag(e) {
        var id = $(e).attr("data-id");
        var preId = $("#changeFlagModelId").val();
        var content = $(e).attr("data-content");
        layer.confirm('确定下架该模板？',{icon:3,title:'提示'},function (index) {
            $("#id").val(id);
            $("#storeModelPreId").val(preId);
            $("#flag").val(false);
            $("#updateShowModelFlag").submit();
            layer.close(index);
        })
    }
</script>


使用input type="submit"来提交表单form，后端用VO对象来封装表单数据，这时要注意的是表单中没有 name属性在VO对象中没有对应字段的 多余的标签，如隐藏域的标签不应该放在该form中
字符串类型的非空和非null判断应该用StringUtils.isNotEmpty(str)

后台传来的json


mybatis中使用mapper3如何进行分页关联查询:
mapper3无法实现，需要自己手动分页关联查询：
使用注解@Select和@Results
如：
@Mapper
public interface ShowDzLogMapper extends BaseMapper<ShowDzLog> {
    @Select({"<script>",
    "SELECT A.ID,B.ID DZ_LOG_ID,B.CREATE_BY STORE_ID,B.CREATE_BY_NAME STORE_NAME,B.STORE_ICON_URL,C.PHOTO_URL,B.CREATE_TM "+
    "FROM STORE_SHOW A,SHOW_DZ_LOG B,SHOW_PHOTO C "+
    "WHERE B.SHOW_ID=A.ID AND C.ID=(SELECT MIN(ID) FROM SHOW_PHOTO P WHERE P.SHOW_ID=A.ID) " +
    "AND B.DZ_CHANNEL='APP' AND A.STORE_ID = #{storeId} AND B.CREATE_BY != #{storeId} " +
    "<if test=' lastId &gt; 0'>"+
    "AND B.ID &lt; #{lastId} "+
    "</if>"+
    "ORDER BY B.ID DESC "+
    "LIMIT 0,#{pageSize}" ,
            "</script>"})
    @Results({
    @Result(property = "dzLogId",  column = "DZ_LOG_ID"),
    @Result(property = "storeId",  column = "STORE_ID"),
    @Result(property = "storeName", column = "STORE_NAME"),
    @Result(property = "storeIconUrl", column = "STORE_ICON_URL"),
    @Result(property = "photoUrl", column = "PHOTO_URL"),
    @Result(property = "createTm", column = "CREATE_TM")
    })
    public List<ShowDzLogDto> getReadedLogs(@Param("storeId") Long storeId, @Param("lastId") Long lastId, @Param("pageSize") Integer pageSize);

    //关联查询的对象也是一个表，也有一个对应mapper时
    @Select("SELECT * FROM inputParam WHERE inputParamId = #{id}")
    @Results({
    //查询关联对象
    @Result(property = "api",
    column = "apiId",
    one = @One(select = "com.tuya.mapper.ApiMapper.selectById"))
    })
    InputParam selectById(@Param("id") int id);
}


但是我的项目中用的是Criteria的拼接查询，@Select()内的语句很难写，于是只能通过Mapper3的分页查询后，再对结果进行关联赋值了：
@Override
public ResponseDto<PageDto<TyreBrandPatternSettingListDto>> getPage(TyreBrandPatternSettingSearchDto searchDto) {
    Example e=new Example(TyreBrandPatternSetting.class);
    Example.Criteria c = e.createCriteria();
    //排序
    if(StringUtils.isEmpty(searchDto.getSort())){
    e.setOrderByClause("id");
    }else{
    e.setOrderByClause(searchDto.getSort());
    }
    //根据品牌查询
    if(null != searchDto.getTyreBrandId()){
    c.andEqualTo("tyreBrandId",searchDto.getTyreBrandId());
    }
    //根据花纹查询
    if(StringUtils.isNotEmpty(searchDto.getTyrePattern())){
    c.andLike("tyrePatternName",searchDto.getTyrePattern());
    }
    PageRowBounds bounds = new PageRowBounds(searchDto.getOffset(), searchDto.getPageSize());
    List<TyreBrandPatternSetting> list = mapper.selectByExampleAndRowBounds(e,bounds);
        return ResponseDtoFactory.toSuccess("",new PageDto<>(searchDto,toDtos(list),bounds.getTotal().intValue()));//里面的toDtos(list)调用下面的方法
}

private List<TyreBrandPatternSettingListDto> toDtos(List<TyreBrandPatternSetting> entitys){//将查询结果list转成数据传输用的listDto，于是我在这步进行关联赋值
    List<TyreBrandPatternSettingListDto> dtos = new ArrayList<>();
    if(null != entitys && entitys.size() > 0)
    for(TyreBrandPatternSetting entity : entitys){
        if(entity != null){
            //TyreBrandPatternSetting类和对应数据表中只含有 轮胎类型（在数据库也有对应的表） 的id
            String tyreType = tyreTypeMapper.selectByPrimaryKey(entity.getTyreType()).getName();//在这里给Mapper3的分页查询后的结果进行关联赋值，设置轮胎类型的名称
            TyreBrandPatternSettingListDto dto = new TyreBrandPatternSettingListDto(entity.getId(), entity.getTyreBrandName(), entity.getTyrePatternName(),tyreType, entity.getTyreInsFlag(), entity.getPhotoUrl());
            dtos.add(dto);
        }
    }
    return dtos;
}

//实体类listDto
@Data
public class TyreBrandPatternSettingListDto extends BaseDto {

    /** 轮胎品牌名称 */
    private String tyreBrandName;
    /** 轮胎花纹名称 */
    private String tyrePatternName;
    /** 轮胎类型 */
    private String tyreType;
    /** 是否参加轮胎保： 1:是，0:否 */
    private Boolean tyreInsFlag;
    /** 图片URL */
    private String photoUrl;

    public TyreBrandPatternSettingListDto() {
    }

    public TyreBrandPatternSettingListDto(Long id, String tyreBrandName, String tyrePatternName,String tyreType, Boolean tyreInsFlag, String photoUrl) {
    super(id);
    this.tyreBrandName = tyreBrandName;
    this.tyrePatternName = tyrePatternName;
    this.tyreType = tyreType;
    this.tyreInsFlag = tyreInsFlag;
    this.photoUrl = photoUrl;
    }
}

//TyreBrandPatternSetting实体类对象，对应数据库表TYRE_BRAND_PATTERN_SETTING
@Data
@Table(name = "TYRE_BRAND_PATTERN_SETTING")
public class TyreBrandPatternSetting extends BaseEntity {
    /** 轮胎品牌ID */
    private Integer tyreBrandId;
    /** 轮胎品牌名称 */
    private String tyreBrandName;
    /** 轮胎花纹ID */
    private Integer tyrePatternId;
    /** 轮胎花纹名称 */
    private String tyrePatternName;
    /** 是否参加轮胎保： 1:是，0:否 */
    private Boolean tyreInsFlag;
    /** 图片URL */
    private String photoUrl;
    /** 创建人 */
    private String createBy;
    /** 创建时间 */
    private Timestamp createTm;
    /** 修改人 */
    private String modifyBy;
    /** 修改时间 */
    private Timestamp modifyTm;
    /** 轮胎类型 */
    private Integer tyreType;
}


调用API接口的方法，API接口的方法中传递数据的对象DTO需要实现序列化implement java.io.Serializable
                        操作数据库的DO也要实现系列化

                        优化思想：
                <select id="status" name="availableFlag" class="selected">
                    <option value="">${message("admin.common.choose")}</option>
                    [#if showModelSearchDto.availableFlag?? && showModelSearchDto.availableFlag]
                        <option value="true" selected="selected">${message("admin.sellerShowModel.status.true")}</option>
                        <option value="false">${message("admin.sellerShowModel.status.false")}</option>
                    [#elseif ]
                        <option value="true">${message("admin.sellerShowModel.status.true")}</option>
                        <option value="false" selected="selected">${message("admin.sellerShowModel.status.false")}</option>
                    [#else]
                        <option value="true">${message("admin.sellerShowModel.status.true")}</option>
                        <option value="false">${message("admin.sellerShowModel.status.false")}</option>
                    [/#if]
                </select>
                        优化后：
                <select id="status" name="availableFlag" class="selected">
                    <option value="">${message("admin.common.choose")}</option>
                    <option value="true" [#if showModelSearchDto.availableFlag?? && showModelSearchDto.availableFlag]selected="selected"[/#if]>${message("admin.sellerShowModel.status.true")}</option>
                    <option value="false" [#if showModelSearchDto.availableFlag?? && !showModelSearchDto.availableFlag]selected="selected"[/#if]>${message("admin.sellerShowModel.status.false")}</option>
                </select>


在用对象的属性进行真假条件判断时，需要保证这个对象的属性不为空，不然会报空指针异常NullpointException
    如：
        if (null != modelSearchDto.getAvailableFlag() && modelSearchDto.getAvailableFlag()) {
        //如果在除第一页的其他页进行上架的搜索，默认从第一页开始
            searchDto.setCurrentPage(1);
        }



易错：freemarker中的？length gt 20是根据问号之前的数据类型来判断是 字母数 还是 文字数。一个西文字符一个字节，一个中文字符两个字节，如：
[#if showInfo.content?html?length gt 20]//如果content内容为文字，则这里判断的是 文字个数大于20则条件成立；如果content内容是字母，则这里判断的是 字节个数大于20则条件成立
    <a  href="javascript:;" style="font-family : 微软雅黑,宋体;color: #00F;" onclick="tips(this)" value="${showInfo.content?html}">[${message("admin.sellerShow.viewAll")}]</a>
[/#if]


//实现从新窗口打开点击一张图片，target="_blank"
<a href="${storeActivity.photoUrl}" target="_blank">
    <img src="${storeActivity.photoUrl}_32x32" width="40" height="40">
</a>
js中



@Service(version="1.0.0",group = "spring.dubbo.group",timeout = 6000)
该注解配置了dubbo的服务

**两步实现springmvc中string转化成Timestamp类型：
                            1.创建CustomerTimestampEditor类：
                            package net.showcoo;

                            import org.springframework.util.StringUtils;

                            import java.beans.PropertyEditorSupport;
                            import java.sql.Timestamp;
                            import java.text.ParseException;
                            import java.text.SimpleDateFormat;

                            /**
                            * @Author: MiaoHongShuai
                            * @Description: 字符串转为Timestamp
                            * @Date: Created on 2017/8/21
                            * @Modified By:
                            */
                            public class CustomTimestampEditor extends PropertyEditorSupport{
                                    private final SimpleDateFormat dateFormat;
                                    private final boolean allowEmpty;
                                    private final int exactDateLength;

                                public CustomTimestampEditor(SimpleDateFormat dateFormat, boolean allowEmpty) {
                                    this.dateFormat = dateFormat;
                                    this.allowEmpty = allowEmpty;
                                    this.exactDateLength = -1;
                                }

                                public CustomTimestampEditor(SimpleDateFormat dateFormat,
                                boolean allowEmpty, int exactDateLength) {
                                    this.dateFormat = dateFormat;
                                    this.allowEmpty = allowEmpty;
                                    this.exactDateLength = exactDateLength;
                                }

                                public void setAsText(String text) throws IllegalArgumentException {
                                    if ((this.allowEmpty) && (!(StringUtils.hasText(text)))) {
                                    setValue(null);
                                    } else {
                                    if ((text != null) && (this.exactDateLength >= 0)
                                        && (text.length() != this.exactDateLength)) {
                                        throw new IllegalArgumentException(
                                            "Could not parse date: it is not exactly"
                                            + this.exactDateLength + "characters long");
                                    }
                                    try {
                                        setValue(new Timestamp(this.dateFormat.parse(text).getTime()));
                                        } catch (ParseException ex) {
                                            throw new IllegalArgumentException("Could not parse date: "
                                        + ex.getMessage(), ex);
                                        }
                                    }
                                }

                                public String getAsText() {
                                    Timestamp value = (Timestamp) getValue();
                                    return ((value != null) ? this.dateFormat.format(value) : "");
                                }
                            }

                            2.controller中添加注解代码：
                            @InitBinder
                            public void initBinder(WebDataBinder binder) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            binder.registerCustomEditor(Timestamp.class, new CustomTimestampEditor(dateFormat, true));
                            }



**页面使用ajaxFileUpload实现点击图片弹出 文件上传框，确认后图片上传后显示在页面原来的地方覆盖原图片，且不显示input标签的“未选择任何文件”；
        如果showActivity.photoUrl为空，则说明是新增门店活动，于是切换成添加活动海报的按钮和显示框：
                            1.body中的代码：
                            <tr>
                                <th>活动海报:</th>
                                <td class="move">
                                    <div>
                                    [#if showActivity.photoUrl??]
                                        <img id="source" src="${showActivity.photoUrl}" width="300px" height="150px" onclick="$('#multipartFile').click();"/>
                                    [#else]
                                        <a href="#" id="addPhoto" class="iconButton" onclick="$('#multipartFile').click();" style="z-index: 10">
                                            <span class="addIcon">&nbsp;</span>请添加活动海报
                                        </a>
                                        <img id="source" src="" width="300px" height="150px" onclick="$('#multipartFile').click();" style="display:none"/>
                                    [/#if]
                                        <input type="file" id="multipartFile" name="multipartFile" value="${showActivity.photoUrl}" title=""/>
                                        <input type="hidden" id="photoUrl" name="photoUrl" value="${showActivity.photoUrl}" />
                                    </div>
                                </td>
                            </tr>
                            2.css样式：使原本的file上传input按钮的透明化，position: relative;表示父标签位置相对，position: absolute;表示子标签位置相对于父标签是绝对的
                            ，这里的top和left指子标签相对父标签内容右上角的相对距离
                            <style type="text/css">
                                .move{
                                    position: relative;
                                }
                                .move input{
                                    opacity:0;
                                    filter:alpha(opacity=0);
                                    height: 150px;
                                    width: 300px;
                                    position: absolute;
                                    top: 5px;
                                    left: 5px;
                                    z-index: 9;
                                }
                            </style>
                            3.js中的方法：给文件上传input按钮绑定改变触发事件
                            <script type="text/javascript">
                                $(document).ready(function() {
                                    $('#multipartFile').change(function(){
                                        var file = $("#multipartFile").val();
                                        if( !file.match( /.jpg|.gif|.png|.bmp/i ) ){//正则检验文件的后缀名是否为图片格式
                                            $.dialog({
                                                type: 'warn',
                                                content: '图片格式错误！',
                                            });
                                            return false;
                                        }
                                        $.ajaxFileUpload({
                                            url: '${base}/admin/store_activity_model/uploadOnePhoto.cgi?inputId=multipartFile',
                                            secureuri : false,
                                            fileElementId: 'multipartFile',
                                            dataType: 'json',
                                            async: false,
                                            success: function (data) {
                                                if(data.result === 'success') {
                                                    $("#source").attr("src", data.url);
                                                    $("#photoUrl").val(data.url);
                                                }else {
                                                    $.dialog({
                                                        type: 'warn',
                                                        content: '出现异常：'+data.msg+'',
                                                    });
                                                }
                                            }
                                        });
                                    });
                                });
                            </script>


**日期插件Wdate的扩展应用：实现年月日和时分的分开输入，并且设置默认的时分值，最后输入的年月日和时分合并为一个时间传给后端
                            1.body中的代码：
                            <tr>
                                <th>
                                    活动开始时间:
                                </th>
                                <td id="begin">
                                    <input type="text" id="beginDate" value="${beginDay}"
                                           class="text Wdate"
                                           onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', maxDate: '#F{$dp.$D(\'endDate\')}'});"/>
                                    <input type="text" id="beginTime" value="${beginTime}"  //原来这里的id不必为beginDate，和结束时间的minDate里的名称对应即可
                                           class="text Wdate"
                                           onfocus="WdatePicker({dateFmt: 'HH:mm'});"/>  //注意：这里开始时分不设置 对应结束时分的上限，因为两者互相可大可小，时间的先后顺序交给 年月日 来控制
                                </td>
                            </tr>
                            <tr>
                                <th>
                                    活动结束时间:
                                </th>
                                <td id="end">
                                    <input type="text" id="endDate" value="${endDay}" class="text Wdate"
                                           onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', minDate: '#F{$dp.$D(\'beginDate\')}'});"/>
                                    <input type="text" id="endTime" value="${endTime}" class="text Wdate" //原来这里的id不必为endDate，和结束时间的maxDate里的名称对应即可
                                           onfocus="WdatePicker({dateFmt: 'HH:mm'});"/> //注意：这里结束时分不设置 对应开始时分的下限，因为两者互相可大可小，时间的先后顺序交给 年月日 来控制
                                </td>
                            </tr>
                            2.用freemarker处理后端传来的时间数据，转化成年月日和时分显示,没有的话设置默认值，WDate日期框默认值可为字符串：
                            [#assign beginDay = ""]
                            [#assign endDay = ""]
                            [#assign beginTime = ""]
                            [#assign endTime = ""]
                            [#if showActivity??]
                                [#if showActivity.beginTm??]
                                    [#assign beginDay = showActivity.beginTm?string("yyyy-MM-dd")]
                                    [#assign beginTime = showActivity.beginTm?string("HH:mm")]
                                [#else]
                                    [#assign beginDay = "请选择"]
                                    [#assign beginTime = "00:00"]
                                [/#if]
                                [#if showActivity.endTm??]
                                    [#assign endDay = showActivity.endTm?string("yyyy-MM-dd")]
                                    [#assign endTime = showActivity.endTm?string("HH:mm")]
                                [#else]
                                    [#assign endDay = "请选择"]
                                    [#assign endTime = "24:00"]
                                [/#if]
                            [/#if]
                            3.js中的方法进行年月日和时分输入值合并成 一个时间（这个值赋给隐藏标签），并进行表单校验，然后提交表单传给后端：
                            <form id="inputForm" action="update.cgi" method="post" enctype="multipart/form-data">
                                <input type="hidden" name="id" value="${showActivity.id}" />
                                <input type="hidden" id="beginTm" name="beginTm" />
                                <input type="hidden" id="endTm" name="endTm" />
                                ...
                            </form>
                            js:
                            <script type="text/javascript">
                                $(document).ready(function() {
                                    $("#submitBtn").click(function () {
                                        var $beginDay = $("#begin").children(":eq(0)").val();
                                        var $beginTime = $("#begin").children(":eq(1)").val();
                                        var $endDay = $("#end").children(":eq(0)").val();
                                        var $endTime = $("#end").children(":eq(1)").val();
                                        var beginTm = $beginDay + " " + $beginTime + ":00";
                                        var endTm = $endDay + " " + $endTime + ":00";
                                        if($beginDay == "请选择") {
                                            $.dialog({
                                                type: 'warn',
                                                content: '活动开始时间不能为空！'
                                            })
                                            return false;
                                        }
                                        if($endDay == "请选择") {
                                            $.dialog({
                                                type: 'warn',
                                                content: '活动结束时间不能为空!'
                                            })
                                            return false;
                                        }
                                        var title = $("#title").val();
                                        if(title == null || title == "" || title == undefined) {
                                            $.dialog({
                                                type: 'warn',
                                                content: '活动名称不能为空!'
                                            })
                                            return false;
                                        }
                                        var photoUrl = $("#photoUrl").val();
                                        if(photoUrl == null || photoUrl == "" || photoUrl == undefined) {
                                            $.dialog({
                                                type: 'warn',
                                                content: '活动海报不能为空!'
                                            })
                                            return false;
                                        }
                                        if($beginDay == $endDay && $beginTime > $endTime) { //时间的值可互相比较，如2017-8-15==2017-8-15和23:00>11:00，这时满足提交，提示报错。
                                            layer.msg("活动开始时间不能晚于活动结束时间！",{icon:2,offset:'t'});
                                            return false;
                                        }
                                        layer.confirm('确定保存“'+title+'”？',{icon:3,title:['提示','background-color:#CD4344;color:#FFFFFF']},function () {
                                            $("#beginTm").val(beginTm);
                                            $("#endTm").val(endTm);
                                            $("#inputForm").submit();
                                        })
                                    });
                                });
                            </script>



**控制标签的层次显示：让一个div层浮在最上层的方法：z-index:auto;越大代表越置前，如可定义为： z-index:9999。若定义为-1，代表为最底层。
**如何在freemarker中遍历list时进行计数，然后条件成立时跳出list循环：如下：
定义循环外部变量,然后在循环内部累加,最后做判断，用'<#break>'可以跳出<#list></#list>循环
                            <#if (articleList)??>
                                <#assign x=0 />
                                <#list articleList?sort_by(["wa_postdate"])?reverse  as item>
                                    <#if item.wa_recommend=='1' && item.wa_status=="1">
                                        <#assign x=x+1 />
                                        <li style="list-style-type:circle;color:#000000; margin-left:20px;">
                                             <span style="display:block;height:24px;float:right;color:blue;font-size: 12px;margin-right:7px;"><@dateMonthOut item.wa_postdate/>
                                             </span>
                                            <span class="news_title">
                                                <a href="${path}/web/article_newsViewA.do?wa_id=${(item.wa_id)!}"
                                                   target="_blank">${ellipsis(item.wa_title,24)}</a>
                                                <#if getBetweenDays(formatDate(item.wa_postdate),getNow("yyyy-MM-dd"))<=3>
                                                    <img src="${path}/website/images/newnail.png"/>
                                                </#if>
                                            </span>
                                        </li>
                                    </#if>
                                    <#if x == 4> <#break> </#if>
                                </#list>
                            </#if>
                                我的项目的实际运用：只展示后端返回的List集合中最多三个storeActivity.availableFlag=true的活动模板
                            $("#viewAppModelButton").click(function () {
                                layer.open({
                                title: ['APP模板页面','font-size:15px;color:#ffffff;text-align:center;margin:auto;display:block;padding:0 20px;background-color:#CD4344'],
                                [@compress single_line = true]//freemarker的compress标签,将content内容压缩成一行
                                content: '[#if storeActivityModelList??]
                                [#assign x = 0]
                                <div>
                                [#list storeActivityModelList as storeActivity]
                                    [#if storeActivity.availableFlag?? && storeActivity.availableFlag]
                                        [#assign x = x + 1]
                                    <div><strong>${storeActivity.title}<\/strong><\/div style=\"height:10%\" class=\"flex-item\">
                                    <div style=\"height:10%\" class=\"flex-item\"><span style=\"font-size:13px\">${message("admin.storeActivity.startAndEndDate")}<\/span> :
                                    <span style=\"font-size:13px\" >${storeActivity.beginTm} — ${storeActivity.endTm}<\/span><\/div>
                                    <div style=\"height:45%\" class=\"flex-item\"><img src=\"${storeActivity.photoUrl}\" width=\"100%\" height=\"160px\"><\/div>
                                    <div style=\"height:35%\" class=\"flex-item\"><p>${storeActivity.content}<\/p><\/div>
                                        <br\/>
                                    [/#if]
                                    [#if x == 3][#break][/#if]
                                [/#list]
                                <\/div id=\"flex-container\">
                                [/#if]',
                                [/@compress]
                                    area: ['375px','460px'],
                                    closeBtn: 0,
                                })
                            });
                                因为是竖向显示的弹窗，这里还采用了flex布局，使用方法：对父标签使用：
                                #flex-container{
                                    display: -webkit-flex;
                                    display: flex;
                                    -webkit-animation-direction: column;
                                    flex-direction: column;
                                }
                                对父标签内的子标签使用：
                                #flex-container .flex-iten{
                                    -webkit-flex: auto;
                                    flex: auto;//当存在剩余空间，则子标签将等分剩余空间（如果有的话）；当空间不足时，子标签都将等比例缩小。
                                }
                                这样，子标签将整齐的排成一列，且不会互相重叠，而且可根据子标签内的子标签内容高度调整子标签的高度，
                                但是宽度都是固定的


**使用ajaxFileUpload.js来实现异步上传文件：
                                1.body中的代码：
                                <th>活动海报:</th>
                                <td class="move">
                                    <div>
                                        [#if showActivity.photoUrl??]
                                            <img id="source" src="${showActivity.photoUrl}" width="300px" height="150px" onclick="$('#multipartFile').click();"/>
                                        [#else]
                                            <a href="#" id="addPhoto" class="iconButton" onclick="$('#multipartFile').click();" style="z-index: 10">
                                                <span class="addIcon">&nbsp;</span>请添加活动海报
                                            </a>
                                            <img id="source" src="" width="300px" height="150px" onclick="$('#multipartFile').click();" style="display:none"/>
                                        [/#if]
                                            <input type="file" id="multipartFile" name="multipartFile" value="${showActivity.photoUrl}" title=""/>
                                        <input type="hidden" id="photoUrl" name="photoUrl" value="${showActivity.photoUrl}" />
                                    </div>
                                </td>
                                2.css中的样式：
                                .move{
                                    position: relative;
                                }
                                .move input{
                                    opacity:0;
                                    filter:alpha(opacity=0);
                                    height: 150px;
                                    width: 300px;
                                    position: absolute;
                                    top: 5px;
                                    left: 5px;
                                    z-index: 9;
                                }
                                .move div{
                                    height: 150px;
                                    width: 300px;
                                }
                                3.js中的方法：
                                $('#multipartFile').change(function(){
                                    var file = $("#multipartFile").val();
                                    if( !file.match( /.jpg|.gif|.png|.bmp/i ) ){
                                        layer.msg("图片格式错误！",{icon:2,offset:'t'});
                                        return false;
                                    }
                                    $.ajaxFileUpload({
                                        url: '${base}/admin/store_activity_model/uploadOnePhoto.cgi?inputId=multipartFile',
                                        secureuri : false,
                                        fileElementId: 'multipartFile',
                                        dataType: 'json',
                                        async: false,
                                        success: function (data) {
                                            if(data.result === 'success') {
                                                $("#source").attr("src", data.url);
                                                $("#photoUrl").val(data.url);
                                                $("#addPhoto").css("display", "none");
                                                $("#source").css("display", "");
                                            }else {
                                                layer.msg("出现异常：" + data.msg, {icon:2,offset:'t'});
                                            }
                                        }
                                    });
                                });



**对于table的tr、th、td布局，可使用属性colspan和align来破坏表格的上下对齐

**原子性：原子是世界上的最小单位，具有不可分割性。比如 a=0；（a非long和double类型） 这个操作是不可分割的，那么我们说这个操作时原子操作。
    再比如：a++； 这个操作实际是a = a + 1；是可分割的，所以他不是一个原子操作。非原子操作都会存在线程安全问题，
        需要我们使用同步技术（sychronized）来让它变成一个原子操作。一个操作是原子操作，那么我们称它具有原子性。
            Java的concurrent包下提供了一些原子类，我们可以通过阅读API来了解这些原子类的用法。比如：AtomicInteger、AtomicLong、AtomicReference等。
**可见性，是指线程之间的可见性，一个线程修改的状态对另一个线程是可见的。也就是一个线程修改的结果。另一个线程马上就能看到。
    比如：用volatile修饰的变量，就会具有可见性。volatile修饰的变量不允许线程内部缓存和重排序，即直接修改内存。所以对其他线程是可见的。
        但是这里需要注意一个问题，volatile只能让被他修饰内容具有可见性，但不能保证它具有原子性。比如 volatile int a = 0；之后有一个操作 a++；
            这个变量a具有可见性，但是a++ 依然是一个非原子操作，也就这这个操作同样存在线程安全问题。
**volatile 会拒绝编译器对其修饰的变量进行优化。也就不会存在重排序的问题。volatile只会影响可见性，不会影响原子性。
例子：正确使用volatile的模式：#状态标志：
                                package com.chu.test.thread;
                                /**
                                * 可见性分析
                                * @author Administrator
                                *
                                *volatile 会拒绝编译器对其修饰的变量进行优化。也就不会存在重排序的问题。volatile只会影响可见性，不会影响原子性。
                                *下面程序如果不加
                                */
                                public class Test {

                                    volatile int a = 1;
                                    volatile boolean ready;

                                    public class PrintA extends Thread{
                                    @Override
                                        public void run() {
                                            while(!ready){//循环直到main线程中将ready修改为true
                                                Thread.yield();//使当前线程休眠，只能让同优先级的线程有执行的机会。且无法指定休眠时间，不释放对象锁，也就是说如果有synchronized同步块，其他线程仍然不能访问共享数据。注意该方法要捕捉异常。
                                            }
                                            System.out.println(a);
                                        }
                                    }
                                    public static void main(String[] args) throws InterruptedException {
                                        Test t = new Test();
                                        t.new PrintA().start();
                                        //下面两行如果不加volatile的话，执行的先后顺序是不可预测的。并且下面两行都是原子操作，但是这两行作为一个整体的话就不是一个原子操作。
                                        t.a = 48; //这是一个原子操作，但是其结果不一定具有可见性，（即main线程对a的修改对于线程PrintA不一定可见，PrintA看到的共享变量可能仍是修改前的值1）。
                                        //加上volatile后就具备了可见性。
                                        t.ready = true;//同理,
                                    }
                                }
                                上面程序如果变量a不用volatile修饰那么输出结果很可能就是1.
                                模式 #1：状态标志

                                也许实现 volatile 变量的规范使用仅仅是使用一个布尔状态标志，用于指示发生了一个重要的一次性事件，例如完成初始化或请求停机。

                                很多应用程序包含了一种控制结构，形式为 “在还没有准备好停止程序时再执行一些工作”，如清单 2 所示：

                                清单 2. 将 volatile 变量作为状态标志使用

                                volatile boolean shutdownRequested;

                                ...

                                public void shutdown() { shutdownRequested = true; }

                                public void doWork() {
                                    while (!shutdownRequested) {
                                    // do stuff
                                    }
                                }
                                很可能会从循环外部调用 shutdown() 方法 —— 即在另一个线程中 —— 因此，需要执行某种同步来确保正确实现 shutdownRequested 变量的可见性。
                                （可能会从 JMX 侦听程序、GUI 事件线程中的操作侦听程序、通过 RMI 、通过一个 Web 服务等调用）。
                                然而，使用 synchronized 块编写循环要比使用清单 2 所示的 volatile 状态标志编写麻烦很多。由于 volatile 简化了编码，并且状态标志并不依赖于程序内任何其他状态，因此此处非常适合使用 volatile。

                                这种类型的状态标记的一个公共特性是：通常只有一种状态转换；shutdownRequested 标志从 false 转换为 true，然后程序停止。
                                这种模式可以扩展到来回转换的状态标志，但是只有在转换周期不被察觉的情况下才能扩展（从 false 到 true，再转换到 false）。此外，还需要某些原子状态转换机制，例如原子变量。
**结束语:
    与锁相比，Volatile 变量是一种非常简单但同时又非常脆弱的同步机制，它在某些情况下将提供优于锁的性能和伸缩性。如果严格遵循 volatile 的使用条件 —— 即变量真正独立于其他变量和自己以前的值 —— 在某些情况下可以使用 volatile 代替 synchronized 来简化代码。
        然而，使用 volatile 的代码往往比使用锁的代码更加容易出错。本文介绍的模式涵盖了可以使用 volatile 代替 synchronized 的最常见的一些用例。遵循这些模式（注意使用时不要超过各自的限制）可以帮助您安全地实现大多数用例，使用 volatile 变量获得更佳性能。

**前端页面中可以传入Boolean值进行判断，但无法使用data-status保存下false，可以转成字符串保存：如data-status="${showInfo.status?string}" 这样false就是"false"字符串了
        运用实例：
                                1.body中的标签按钮：
                                <td>
                                    <span>
                                        <a href="#" class="editModel" data-id="${showModel.id}" data-content="${showModel.content}">[${message("admin.sellerShowModel.edit")}]</a>
                                    [#if showModel.availableFlag]
                                        <a href="#" class="status" data-id="${showModel.id}" data-status="${showModel.availableFlag?string}">[${message("admin.sellerShowModel.status.false")}]</a>
                                    [#else]
                                        <a href="#" class="status" data-id="${showModel.id}" data-status="${showModel.availableFlag?string}">[${message("admin.sellerShowModel.status.true")}]</a>
                                    [/#if]
                                    </span>
                                </td>
                                2.js方法：
                                $(".status").click(function () {
                                    var id = $(this).attr("data-id");
                                    var status = $(this).attr("data-status");
                                    var afterStatus = false;
                                    $("#changeFlagModelId").val(id);
                                    //                var content = $(this).attr("data-content");
                                    var countModel = $("#countModel").val();
                                    var message = "确定下架该卖家秀内容模板？";
                                    if(status == "true") {
                                        if(countModel == 4) {
                                        message = "请最少保留4个内容模板！";
                                        layer.msg(message,{icon:2});
                                        return false;
                                        }
                                    }
                                    if(status == "false") {
                                        var list = $("#showContentList");
                                        var afterStatus = true;
                                        message = "确定上架该卖家秀内容模板？";
                                        //                    alert(list);
                                        if(countModel == 4 && list !== null && list !== undefined) {
                                            message = "最多上架4个，需选择下面某个模版下架才可上架此模版";
                                            layer.open({
                                                title: [message, 'font-size:15px;padding:0 0 0 15px'],
                                                [@compress single_line = true]
                                                content:'<table>
                                                [#if showContentList??]
                                                    [#list showContentList as showContent]
                                                        [#if showContent_index = 0]
                                                        <tr>
                                                        <td class=\"myButton\"><button data-id=\"${showContentList[showContent_index].id}\" onclick="changeFlag(this)">${abbreviate(showContentList[showContent_index].content?html, 14, "...")}<\/button>
                                                            <\/td>
                                                        [/#if]
                                                        [#if showContent_index = 1]
                                                        <td class=\"myButton\"><button data-id=\"${showContentList[showContent_index].id}\" onclick="changeFlag(this)">${abbreviate(showContentList[showContent_index].content?html, 14, "...")}<\/button>
                                                            <\/td>
                                                            <\/tr>
                                                        [/#if]
                                                        [#if showContent_index = 2]
                                                        <tr>
                                                        <td class=\"myButton\"><button data-id=\"${showContentList[showContent_index].id}\" onclick="changeFlag(this)">${abbreviate(showContentList[showContent_index].content?html, 14, "...")}<\/button>
                                                            <\/td>
                                                        [/#if]
                                                        [#if showContent_index = 3]
                                                        <td class=\"myButton\"><button data-id=\"${showContentList[showContent_index].id}\" onclick="changeFlag(this)">${abbreviate(showContentList[showContent_index].content?html, 14, "...")}<\/button>
                                                            <\/td>
                                                            <\/tr>
                                                        [/#if]
                                                    [/#list]
                                                [/#if]
                                                <\/table>',
                                                [/@compress]
                                                area: ['400px','225px'],
                                                closeBtn: 0,
                                            });
                                            return false;
                                        }
                                    }
                                    layer.confirm(message, {icon: 3, title:'提示'},function (index) {
                                        $("#flag").val(afterStatus);
                                        $("#id").val(id);
                                        //                    $("#updateModelContent").val(content);
                                        $("#updateShowModelFlag").submit();
                                        layer.close(index);
                                    })
                                });


**js方法中的return false;是跳出整个方法，相当于java后端的return
**layer弹窗中，如果弹窗区域设置了宽度和高度即area:['300px','400px'],则因为设置高度而在火狐浏览器里会出现按钮布局出错的问题，
                                可以不设置高度，只设置宽度，如 area:['300px']
**js中的标签状态改变调用触发的方法$("#id").change()容易出问题， 用在该标签上加onchange="changePhoto()"方法调用和js中添加function changePhoto(){}来替换

**如果有人更新了项目所依赖的 dao项目的方法，则需要clean install -U更新本项目的依赖库。
**还有有时页面会缓存了 jQuery的validate.js检验参数的结果，需要刷新页面

${tyreBrand.tyreInsFlag?string("是", "否")}转换boolean为对应字符串

**索引的存储：
    b-tree结构指的是数据的逻辑存储方式。物理存储显然不会是指定顺序（B-Tree）的存储，不然试想如果要update或者insert那需要多大的代价才能维护物理顺序啊。

    index（无论clustered或是non clustered）每一个leaf（叶节点）都有指向它的前项和后项，这样就维护了index的逻辑顺序。b-tree指的就是这个leaf链表形式的基础上，再建立它的上层（中间层和root，用于为它的leaf提供索引），好处是每一次lookup的消耗都是相同的（等于这个tree的depth）。

    index存了什么，如果是clustered index它存了这个表里所有的数据和key（用于lookup），如果是non clustered index它存了这个表的clustered index key（如果表没有clustered index，那就是RID，RID包括了fileid，pageid，slotid？，可以用于直接定位到需要的数据），如果这个nonclustered index有included column，这个数据也会被储存在index里。

    如果要进一步研究物理结构，那就更复杂了，推荐去读一下inside sql server - storage engine。看完以后（当然还要配合msdn的一些文章），lz就全清楚啦。

    内存（你指的是buffer pool吧）的作用无非是把物理磁盘的数据读进内存，避免反复I/O读写。你所谓的b树结构是逻辑结构，不是物理上的。
    举个例子，比如index的page结构大致是这样的，
    File/Page ID: xxx
    IAMF/PID: xxx
    Index ID: xxx
    Index Level： 0（代表是leaf）
    PrevFile/Page ID： xxx
    NextFile/Page ID： xxx
    ..还有其他一些信息保存它里面每一个index row
    有了index level，它的前后file和page ID组成了树结构。index无论在磁盘还是内存里都是以这一个一个的页保存的。

            内存里面存储的和索引在磁盘上存储过程结构相同，只是存储到内存里避免频繁磁盘IO节省时间。
            索引在磁盘上存储时也是按页存储的，如果单纯从物理结构上来说就是连续的存储页而已，而他们的逻辑结构是通过指针实现的b树，
            并不是在磁盘上或者内存上就是b树的结构了。b树只是逻辑上的，不是物理上的。
            其实mayuanf已经说得很明白了，只是索引空间与表空间的数据物理存储顺序有点不一样，还是有区别的。LZ不要太纠结了。

                                更新非常频繁的字段不适合创建索引
                                1、表的主键、外键必须有索引；
                                2、数据量超过300的表应该有索引；
                                3、经常与其他表进行连接的表，在连接字段上应该建立索引，如join中on后的连接字段；
                                4、经常出现在Where子句中的字段，特别是大表的字段，应该建立索引；
                                5、索引应该建在选择性高的字段上；
                                6、索引应该建在小字段上，对于大的文本字段甚至超长字段（text和Blog)，不要建索引；
                                7、复合索引的建立需要进行仔细分析；尽量考虑用单字段索引代替：
                                A、正确选择复合索引中的主列字段，一般是选择性较好的字段；
                                B、复合索引的几个字段是否经常同时以AND方式出现在Where子句中？单字段查询是否极少甚至没有？如果是，则可以建立复合索引；否则考虑单字段索引；
                                C、如果复合索引中包含的字段经常单独出现在Where子句中，则分解为多个单字段索引；
                                D、如果复合索引所包含的字段超过3个，那么仔细考虑其必要性，考虑减少复合的字段；
                                E、如果既有单字段索引，又有这几个字段上的复合索引，一般可以删除复合索引；
                                8、频繁进行数据操作(insert和update)的表，不要建立太多的索引；
                                9、删除无用的索引，避免对执行计划造成负面影响；
                                10、更新非常频繁的字段不适合创建索引
                                11、较频繁的作为查询条件的字段应该创建索引
                                12、唯一性太差的字段不适合单独创建索引，即使频繁作为查询条件：
                                唯一性太差的字段：如状态字段，类型字段等。这些字段即使创建了单独的索引，MySQL Query Optimizer大多数也不会选择使用，如果什么时候选择了这种索引，可能会带来极大的性能问题。
                                由于索引字段中每个值都含有大量的记录，那么存储引擎在根据索引访问数据的时候会带来大量的随机IO，甚至有时候可能还好出现大量的重复IO

            以上是一些普遍的建立索引时的判断依据。一言以蔽之，索引的建立必须慎重，对每个索引的必要性都应该经过仔细分析，要有建立的依据。
        因为太多的索引与不充分、不正确的索引对性能都毫无益处：在表上建立的每个索引都会增加存储开销，索引对于插入、删除、更新操作也会增加处理上的开销。
    另外，过多的复合索引，在有单字段索引的情况下，一般都是没有存在价值的；相反，还会降低数据增加删除时的性能，特别是对频繁更新的表来说，负面影响更大。


                不管数据表有无索引，首先在SGA的数据缓冲区中查找所需要的数据，如果数据缓冲区中没有需要的数据时，server服务器进程才去读磁盘。
                读磁盘时：
                    1、无索引，直接去读表数据存放的磁盘块，读到数据缓冲区中再查找需要的数据。
                    2、有索引，先读入索引表（即将索引文件加载到内存，索引文件在存储器上分为两个区：索引区和数据区。索引区存放索引表，数据区存放主文件），
                                通过索引表直接找到所需数据的物理地址（先找到对应的叶子节点，所有的叶子节点包括关键字信息以及指向这些关键字的指针，而且叶子节点是根据关键字大小、顺序链接的）
                                ，并把数据读入数据缓冲区中。


                                EXPLAIN SELECT * FROM `t_store` where phone = '13805746240';  --type=const说明使用了唯一索引或者主键，返回记录一定是1行记录的等值where条件时;Extra=NULL
                                EXPLAIN SELECT phone FROM `t_store` where phone = '13805746240';  --此时phone已设置为唯一索引，type=const说明使用了唯一索引或者主键，返回记录一定是1行记录的等值where条件时;Extra=Using index
                                说明 查询的字段在查询条件的字段中，且查询字段是 单键索引/或者多列索引的非最左字段，则执行计划是Extra=Using index;

                                --phone改为联合索引中的一员后如下:
                                EXPLAIN SELECT `PASSWORD` FROM t_store WHERE salt = '13968140594'; --执行计划是Extra=Using where;Using INDEX
                                因为密码数据在所加载的索引文件（所使用的索引是idx_password_salt_phone）中无法直接获得,还需要根据索引中的地址指针找到磁盘中的密码数据
                                EXPLAIN SELECT `PASSWORD` FROM t_store WHERE PASSWORD = '6a66880ed845c57c903def3e6a30a681'; --执行计划是Extra=Using index;type=ref（使用了普通索引的等值条件）,key=idx_password_salt_phone,rows=1
                                因为查询的字段在查询条件的字段中，且查询字段是 单键索引/或者多列索引的最左字段，所以密码数据在所加载的索引文件（所使用的索引是idx_password_salt_phone）中可以直接获得,
                                不需要再根据索引中的地址指针找到磁盘中的密码数据。
                                EXPLAIN SELECT `phone` FROM t_store WHERE phone = '13805746240'; --执行计划是Extra=Using where; Using index;（跟下面salt原因一样）
                                因为查询的字段在查询条件的字段中，且查询字段不是 单键索引/或者多列索引的最左字段，所以密码数据在所加载的索引文件（所使用的索引是idx_password_salt_phone）中不可以直接获得,
                                还需要再根据索引中的地址指针找到磁盘中的密码数据。
                                EXPLAIN SELECT `salt` FROM t_store WHERE salt = '13968140594'; --执行计划是Extra=Using where;Using index,type=index（索引表全表扫描），rows=31977,key=idx_password_salt_phone
                                因为查询的字段salt和查询条件的字段salt相同，但查询字段salt不是 单键索引/或者多列索引的非最左字段,
                                salt数据在所加载的索引文件（所使用的索引是idx_password_salt_phone）中无法直接获得,还需要根据索引中的地址指针找到磁盘中的salt数据。
                                EXPLAIN SELECT `salt` FROM t_store WHERE PASSWORD = '6a66880ed845c57c903def3e6a30a681' and salt = '13968140594'; --执行计划是Extra=Using index;（因为满足最左匹配），即：
                                因为查询的字段在查询条件的字段中，且查询字段的排序与多列索引的字段顺序相同,最左匹配
                                salt数据在所加载的索引文件（所使用的索引是idx_password_salt_phone）中可以直接获得,不需要根据索引中的地址指针找到磁盘中的salt数据。
                                EXPLAIN SELECT * FROM t_store WHERE PASSWORD = '6a66880ed845c57c903def3e6a30a681' and salt = '13968140594'; --执行计划是Extra=NULL,因为查询字段是*
                                EXPLAIN SELECT `salt` FROM t_store WHERE is_enabled = 1 and PASSWORD = '6a66880ed845c57c903def3e6a30a681' and salt = '13968140594' ; --执行计划是Extra=Using where
                                因为查询的字段在查询条件的字段中，且查询字段中含有非索引字段，所以执行计划为Using where,但是任然使用了组合索引


                                EXPLAIN SELECT * FROM t_store where user_name = '13806517253';  --type=const说明使用了唯一索引或者主键，返回记录一定是1行记录的等值where条件时

                                EXPLAIN select id from t_store where id = 194;

                                EXPLAIN SELECT * FROM t_store where user_name like '%138%';  --使用的是全表扫描，type=ALL，ref=NULL rows=全表行数 且没有使用索引key=NULL
                                ***使用全局索引(注意：全文索引的字段内容不能用逗号分隔,不可以有汉字)后，建立fuk_username， 字段user_name,索引类型FULL TEXT,索引方法null后：
                                **EXPLAIN SELECT * FROM t_store where MATCH (`user_name`) AGAINST ('138');    --type=fulltext ref=const rows=1 Extra=Using where;Ft_hints:sorted

                                EXPLAIN SELECT * FROM t_store where sourceid is null;  --当索引为sourceid的单键索引时使用索引Using index condition
                                EXPLAIN SELECT * FROM t_store where sourceid is null;  --当索引为sourceid的和虚拟列创建多列索引时未使用索引未使用索引
                                EXPLAIN SELECT * FROM t_store where sourceid is null UNION SELECT * FROM t_store where id BETWEEN 300 AND 400; --
                                --UNION 操作符用于合并两个或多个 SELECT 语句的结果集。
                                请注意，UNION 内部的 SELECT 语句必须拥有相同数量的列。列也必须拥有相似的数据类型。同时，每条 SELECT 语句中的列的顺序必须相同。

                                ALTER TABLE t_store ADD KEY idx_sourceid_virtua (sourceid,0);
                                ALTER TABLE t_store ADD COLUMN virtua TINYINT AS virtual, ADD KEY idx_sourceid_virtua (sourceid,virtual);

                                ALTER TABLE t_store ADD COLUMN log_date  DATE AS (DATE(locked_date)) stored, ADD KEY idx_log_date (log_date);
                                ALTER TABLE t_store drop COLUMN log_date, drop KEY idx_log_date;  --删除虚拟列和虚拟列索引

                                ALTER TABLE t_store ADD COLUMN log_date  DATE AS (DATE(locked_date)) virtual, ADD KEY idx_log_date (log_date);--注：创建virtual比stored快了3、4倍

                                高效： select * FROM EMP where DEPTNO >=4

                                　　低效： select * FROM EMP where DEPTNO >3

                                -- 从4开始查当然要比从3开始查要快，因为大于等于3小于4的数据不需要进行对比了。如果是DEPTNO是没有索引的，并且DEPTNO都是整数，则没有区别。
                                -- 如果是DEPTNO是有索引的，并且DEPTNO都是整数，则>=4是从索引4开始查找，而>3是从索引3开始查找，如果数据量的分布情况是如下的情况，则查询的效果很明显

                                低效： select * FROM DEPT where SAL * 12 > 25000;

                                　　高效： select * FROM DEPT where SAL > 25000/12;

                                -- 如果SAL是索引列，那么第一句就明显低效了，因为SAL*12 > 25000是不走索引的，如果对索引列进行了计算（包括函数），然包含该索引列的判断部分就不能再走索引了。

                                低效：select * FROM EMP E where SAL > 50000 AND JOB = ‘MANAGER’ AND 25 < (select count(*) FROM EMP where MGR=E.EMPNO);

                                　　高效：select * FROM EMP E where 25 < (select count(*) FROM EMP where MGR=E.EMPNO) AND SAL > 50000 AND JOB = ‘MANAGER’;

                                -- 这两句SQL实在是看不出来有什么区别，现在的ORACLE很智能了，这两对SQL对于ORACLE来说执行计划应当是没有区别的吧？

                                　低效： select JOB , AVG(SAL) FROM EMP GROUP BY JOB HAVING JOB = ‘PRESIDENT’ OR JOB = ‘MANAGER’

                                　　高效： select JOB , AVG(SAL) FROM EMP where JOB = ‘PRESIDENT’ OR JOB = ‘MANAGER’ GROUP BY JOB

                                -- 先过滤后分组明显要比先分组后过滤要快，因为少了很多的的分组计算和处理的记录。


***jQuery表单验证validate.js使用记录：实现多个属性的合并重复校验，如：
                1.在有直径参数的情况下，品牌和花纹可以有重复，但是三个参数不能同时重复；
                2.在没有选择直径参数的情况下，品牌和花纹不能同时重复（其实也相当于三个参数同时重复的情况）。

                    1.html的代码：
<body>
    <div class="path">
        <a href="${base}/admin/common/index.cgi">${message("admin.path.index")}</a> &raquo; ${message("admin.tyreBrandPatternSetting.edit")}
    </div>
    <form id="inputForm" action="update.cgi" method="post" enctype="multipart/form-data">
    <input type="hidden" id="id" name="id" value="${tyreBrandPatternInfo.id}" />
    <table class="input tabContent">
         <tr>
			<th>
				<span class="requiredField">*</span>${message("TyreBrandPatternSetting.tyreBrandName")}:
			</th>
			<td>
				<label class="select">
					<select id="tyreBrandId" name="tyreBrandId" class="remove" onchange="removeError()">
                        //class="remove"用于方便一起清除三个参数的错误样式。
                        //onchange方法指三个参数下拉框的一个被点击时清除三个参数的错误样式和错误提示，错误提示由于设置插入了span标签中
						<option value="">${message("admin.common.choose")}</option>
                    [#list brands as brand]
                        <option value="${brand.id}"[#if tyreBrandPatternInfo.tyreBrandId == brand.id] selected="selected"[/#if]>${brand.name}</option>
                    [/#list]
					</select>
                    <span class="selected"></span> //span用于接收三个参数同时重复（包括空值重复）时的错误提示，class="selected"方便于一起清除三个参数的错误提示
				</label>
			</td>
		 </tr>
         <tr>
			<th>
				<span class="requiredField">*</span>${message("TyreBrandPatternSetting.tyrePatternName")}:
			</th>
			<td>
				<label class="select">
					<select id="tyrePatternName" name="tyrePatternName" class="remove" onchange="removeError()">
						<option value="">${message("admin.common.choose")}</option>
                    [#list dictionarys as dictionary]
                        <option value="${dictionary.dataValue}"[#if tyreBrandPatternInfo.tyrePatternName == dictionary.dataValue] selected="selected"[/#if]>${dictionary.dataValue}</option>
                    [/#list]
					</select>
                    <span class="selected"></span>
				</label>
			</td>
		 </tr>
         <tr>
			<th>
				<span class="requiredField">*</span>${message("TyreBrandPatternSetting.tyreRim")}:
			</th>
			<td>
				<label class="select">
                    <select id="tyreRim" name="tyreRim" class="remove" onchange="removeError()">
                        <option value="">${message("admin.common.choose")}</option>
                    [#list tyreRimList as tyreRim]
                        <option value="${tyreRim.value}" [#if tyreRim.value == tyreBrandPatternInfo.tyreRim]selected="selected"[/#if]>${tyreRim.name}</option>
                    [/#list]
                    </select>
                    <span class="selected"></span>
				</label>
			</td>
		 </tr>
        <tr>
            <th>
                <span class="requiredField">*</span>${message("TyreBrandPatternSetting.tyreType")}:
            </th>
            <td>
                <label class="select">
                    <select id="tyreType" name="tyreType">
                        <option value="">${message("admin.common.choose")}</option>
                    [#list tyreTypes as tyreType]
                        <option value="${tyreType.id}" [#if tyreBrandPatternInfo.tyreType == tyreType.id] selected="selected"[/#if]>${tyreType.name}</option>
                    [/#list]
                    </select>
					<span></span>
                </label>
            </td>
        </tr>
         <tr>
			<th>
				<span class="requiredField">*</span>${message("TyreBrandPatternSetting.tyreInsFlag")}:
			</th>
			<td>
				<label>
					<input type="checkbox" name="tyreInsFlag" value="true" [#if tyreBrandPatternInfo.tyreInsFlag]checked="checked"[/#if] />
					<input type="hidden" name="_tyreInsFlag" value="false" />
				</label>
			</td>
		 </tr>
         <tr>
            <th>
                <span class="requiredField">*</span>${message("TyreBrandPatternSetting.photoUrl")}:
            </th>
            <td>
                <input type="file" name="photoFile" />
				<span></span>
            </td>
         </tr>
    [#if tyreBrandPatternInfo.photoUrl??]
        <tr>
            <th></th>
            <td>
                <a href="${tyreBrandPatternInfo.photoUrl}" target="_blank"><img src="${tyreBrandPatternInfo.photoUrl}" width="120" height="120" /></a>
            </td>
         </tr>
    [/#if]
    </table>
    <table class="input">
        <tr>
            <th>
                &nbsp;
            </th>
            <td>
                <input type="button" id="submitBtn" class="button" value="${message("admin.common.submit")}" />//改成普通按钮提交表单，增加提交前的操作
                <input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.cgi'" />
            </td>
        </tr>
    </table>
    </form>
</body>

                2.js中的代码：
<script type="text/javascript">
    $().ready(function () {

        var $inputForm = $('#inputForm');
        var $submitBtn = $('#submitBtn');

    [@flash_message /]

        $submitBtn.click(function () {//点击提交表单按钮，进行表单验证
            var flag = $inputForm.valid();
            if(!flag){
                //没有通过验证
                return;
            }
            $inputForm.submit();//验证通过，提交表单到后端
        });

        $inputForm.validate({//具体验证规则和内容
            rules: {
                tyreBrandId: {
                    required: true,  //这个说明该参数不能为空，即必须输入值
                    remote: { //通过异步进行参数校验
                        data: {  //传给后端url接口的数据，json格式
                            id: function () {  //由于是编辑，需要将当前记录的id传入后端，便于定位，且编辑时三个参数可以和自己一样，即需要排除掉自己的重复判断
                                return $('#id').val();
                            },
                            tyreBrandId: function () {
                                return $('#tyreBrandId').val();
                            },
                            tyrePatternName: function () {
                                return $('#tyrePatternName').val();
                            },
                            tyreRim: function () {
                                return $('#tyreRim').val();
                            }
                        },
                        url: "checkBrandPattern.cgi",  //验证参数的后端url接口
                    }
                },
                tyrePatternName: {
                    required: true,
                    remote: {
                        data: {
                            id: function () {
                                return $('#id').val();
                            },
                            tyreBrandId: function () {
                                return $('#tyreBrandId').val();
                            },
                            tyrePatternName: function () {
                                return $('#tyrePatternName').val();
                            },
                            tyreRim: function () {
                                return $('#tyreRim').val();
                            }
                        },
                        url: "checkBrandPattern.cgi",
                    }
                },
                tyreRim: {
                    remote: {
                        data: {
                            id: function () {
                                return $('#id').val();
                            },
                            tyreBrandId: function () {
                                return $('#tyreBrandId').val();
                            },
                            tyrePatternName: function () {
                                return $('#tyrePatternName').val();
                            },
                            tyreRim: function () {
                                return $('#tyreRim').val();
                            }
                        },
                        url: "checkBrandPattern.cgi",
                    },
                },
                tyreType: {
                    required: true
                },
                photoFile: {
//                    required: true,
                    extension: "${setting.uploadImageExtension}"
                }
            },
            messages: {  //检验失败后的提示信息（错误提示）
                tyreBrandId: {
                    required: "请选择品牌", //当参数未填写时显示的提示信息
                    remote: "品牌花纹已存在"  //当异步参数校验失败返回false时，显示的错误提示信息
                },
                tyrePatternName: {
                    required: "请选择花纹",
                    remote: "品牌花纹已存在"
                },
                tyreRim: {
                    remote: "品牌花纹直径均已存在"
                },
                tyreType: {
                    required: "请选择轮胎类型"
                },
//                photoFile: {
//                    required: "请上传图片"
//                }
            },
            errorPlacement:function(error,element){  //修改检验参数后，提示信息出现的位置
                error.appendTo(element.siblings("span")); //这里设置为，提示信息出现在 需校验的标签element 的 身为它兄弟标签siblings的span标签内
            },
//            success: function() { //所有参数检验成功后回调的函数功能
//
//            }
        });
    });
    function removeError() { //绑定onchange改变事件
        $(".selected label").text(""); //清除span标签内的错误提示
        $(".remove").removeClass("fieldError"); //清除被校验标签的 错误样式
        $(".remove").removeData("previousValue"); //清除被校验标签的 错误内容存储，不清除的话，会导致同一需校验标签处前后选同一个参数值，不会引发校验
    }
</script>

                                .span_vertical{
                                position: relative;
                                top: 2px;
                                padding: 8px 8px;
                                border-radius: 5px;
                                cursor: pointer;
                                /*margin-left: 20px;*/
                                }
                                .submit_1{
                                background:#4F8BFD;
                                font-family: "Microsoft YaHei UI";
                                color: #FFFFFF;
                                padding: 10px 20px;
                                margin-right: 20px;
                                }
                                按钮属性class="span_vertical submit_1"



**       //标签REGEXP
        if(CollectionUtils.isNotEmpty(searchDto.getLabel())){
            c.andCondition("label REGEXP replace('"+ LabelEnum.getLabelStrs(searchDto.getLabel())+"',',','|') ");
        } else if (null != searchDto.getLabelEnum()) {
            c.andCondition("label REGEXP ", searchDto.getLabelEnum()); //andCondition为自定义条件，而REGEXP是Mysql里的正则匹配， 指数据库字符串label匹配 后面的字符串的 所有记录
            //如pi|apa表示匹配pi或匹配apa，如:
                                mysql> select "pi" REGEXP "pi|apa"; -> 1（表示匹配）
                                mysql> select "axe" REGEXP "pi|apa"; -> 0（表示不匹配）
                                mysql> select "apa" REGEXP "pi|apa"; -> 1（表示匹配）
        }

****问题：传输数据用的DTO, 原本DTO的List属性（存的Enum枚举数据，即List<LabelEnum>）是有值的，调用RPC接口后，在RPC接口实现类中，传过来的DTO对象的List数据丢失，变成NULL
    试了好久，才发现真正原因是DTO的getter和setter方法有问题：删除原本的getter和setter方法，用alt+insert重新创建。理由不明
        结果：修改DTO的getter和setter方法后，List数据不再丢失，但是切换分之后，该方法又失效，于是采用方法二：
        方法二：修改DTO，增加String label字段来传递打标的搜索条件（因为是单个标签搜索）


****解决emoji表情的数据库存取问题：先设置数据库对应字段（列）的字符集为utf8mb4，然后service层，在进行数据库插入和更新操作前设置sql语句“SET NAMES 'utf8mb4'”
      更新时：
            storeShowMapper.setUTF8MB4();
            storeShowMapper.updateByPrimaryKey(show);
      插入时：
            storeShowMapper.setUTF8MB4();
            storeShowMapper.insertSelective(show);

setUTF8MB4()方法：
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
    //TODO
    //FIXME 特别注意，该接口不能被扫描到，否则会出错

    @Select("SET NAMES 'utf8mb4'")
    public void setUTF8MB4();
}

--查看字符集的字节大小
 show character set;
--设置mysql的特定字符集
SET character_set_server=utf8mb4;
--查看db（数据库）的字符集，
show variables like '%char%';

****dialog弹窗的非空验证，验证不通过时不关闭弹窗，且是对单选框的非空验证：
         1.body层：
    <body>
        ...
         <td>
            <input type="button" class="button" value="${message("admin.common.label")}" onclick="javascript:;" id="labelButton"/> //触发dialog弹窗的按钮
        </td>
        ...
            <!-- 隐藏表单 -->
        <form id="labelForm" action="updateLabel.cgi" method="post">//要进行表单验证的表单
            <input type="hidden" name="id" value="${showInfo.id}" />
            <input type="hidden" name="labels" id="labelInput" />//要进行表单验证的表单中name=labels的input标签
            <input type="hidden" name="ids" value="${ids}"/>
            <input type="hidden" name="index" value="${index}"/>
        </form>
    </body>
         2.js层：
        var $labelForm = $('#labelForm');  //要进行表单验证的表单
        var $labelInput = $('#labelInput'); //要进行表单验证的表单中name=labels的input标签
        var $labelButton = $('#labelButton'); //触发dialog弹窗的按钮

        $labelButton.click(function() {
        $.dialog({  //dialog弹窗：内容是  单选框，
            title: "打标",
            type : 'iframe',
            [@compress single_line = true]
                content: '
            <table id="moreTable" class="moreTable">
            <tr>
                [#list labelEnums as labelEnum]
                <td>&nbsp;&nbsp;&nbsp;&nbsp;<input name="label" type="radio" value="${labelEnum}" [#if labelList?seq_contains(labelEnum)] checked="checked"[/#if] />${labelEnum.getName()}<\/td>
                [/#list]
                <\/tr>
                <\/table>',
            [/@compress]
            width: 600,
            modal: true,
            ok: "${message("admin.dialog.ok")}",
            cancel: "${message("admin.dialog.cancel")}",
            onOk: function() {
                var chk_value =[];
                $('input[name="label"]:checked').each(function(){ //获取遍历name="label"的所有单选框，如果被选中了checked，则将值存入 数组
                    chk_value.push($(this).val());
                });

                $labelInput.val(chk_value); //将数组的值存入 隐藏表单的name=labels的input标签中
                var flag = $labelForm.valid(); //主动进行表单的验证
                if(flag) { //flag = true表示表单验证通过
                    $labelForm.submit(); //提交表单的隐藏域
                    return; //返回
                }
                return false;//重要：***返回false，这样表单验证不通过不会关闭弹窗
            }
        });
    });

    $("#labelForm").validate({ //表单验证内容
        errorPlacement:function(error,element){
            error.appendTo(".dialogTitle"); //错误提示信息展示的位置， .appendTo(selector)是jQuery方法，在被选元素的结尾（仍然在元素内部）插入指定内容。可以是任意选择器
                                        //这里的.dialogTitle是dialog弹窗的标题，于是我把提示信息插到 标题后面，显而易见。
        },
        rules: {
            labels: {
                required: true,//labels是表单的 input标签的名称，这个标签的内容将在用户在dialog弹窗中 进行单选框的选择后点击确定后的onOk()方法里对该标签进行赋值，所以该标签没值时说明用户未进行单选框选择。
                                        //validate一般都是对标签的名称进行操作。
                                        //required:true表示这个input标签的内容不能为空。为空提示 错误提示信息
            },
        },
        messages: {
            labels: {
                required: "请选择一个标签",//为空的错误提示信息：说明用户未进行dialog弹窗的单选框选择
            },
        },
    });


**对日期进行，split切割成字符串数组，然后对日期字符串转成数值进行加减操作：
                方法：切割日期：split(separator);
                      字符串转成数值：parserInt(string, 10);这里的10是10进制的意思或者在字符串前加 "+"即可
                      判断是否是非数字值：isNaN(object); object是必需的，object为要检测的值，如果object不为数字，则isNaN(object)为true

                                具体代码如下：
                                1.body中：
                                <tr>
                                    <th>
                                        ${message("admin.storeActivityModel.activityStartDate")}:
                                    </th>
                                    <td id="begin">
                                        <input type="text" id="beginDate" value="${beginDay}"
                                               class="text Wdate"
                                               onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', maxDate: '#F{$dp.$D(\'endDate\')}'});"/>
                                    [#--<input type="text" id="beginTime" value="${beginTime}"--]
                                    [#--class="text Wdate"--]
                                    [#--onfocus="WdatePicker({dateFmt: 'HH:mm'});"/>--]
                                    </td>
                                 </tr>
                                                        <tr>
                                    <th>
                                        ${message("admin.storeActivityModel.activityEndDate")}:
                                    </th>
                                    <td id="end">
                                        <input type="text" id="endDate" value="${endDay}" class="text Wdate"
                                               onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', minDate: '#F{$dp.$D(\'beginDate\')}'});"/>
                                    [#--<input type="text" id="endTime" value="${endTime}" class="text Wdate"--]
                                    [#--onfocus="WdatePicker({dateFmt: 'HH:mm'});"/>--]
                                    </td>
                                 </tr>
                                2.js中：
                                $("#viewBtn").click(function () {
                                    var $beginDay = $("#begin").children(":eq(0)").val();//获取id="begin"标签的第一个 孩子的 值
                                    var $endDay = $("#end").children(":eq(0)").val();

                                    var beginTm = $beginDay + " 00:00";
                                    var endTm = $endDay + " 00:00";
                                    var arr = $endDay.split("-");
                                    var nextDay = +arr[2] + 1;
                                    if(!isNaN(nextDay)) {
                                        endTm = arr[0] + "-" + arr[1] + "-" + nextDay + " 00:00";
                                    }
                                    var $title = $("#title").val();
                                    var $content = $("#content").val();
                                    var $photoUrl = $("#photoUrl").val();

                                    layer.open({
                                        title: ['门店活动案例','font-size:15px;color:#ffffff;text-align:center;margin:auto;display:block;padding:0 20px;background-color:#CD4344'],
                                        area: ['385px','460px'],
                                        closeBtn: 0,
                                        [@compress single_line = true]
                                            content: '
                                        <div><strong>'+$title+'<\/strong><\/div style=\"height:10%\">
                                        <div style=\"height:45%\"><img src=\"'+ $photoUrl +'\" width=\"100%\" height=\"100%\"><\/div>
                                        <div><span style=\"font-size:13px\">${message("admin.storeActivity.startAndEndDate")}<\/span> :
                                        <span style=\"font-size:13px\">' +beginTm+ ' — '+endTm+'<\/span><\/div style=\"height:10%\">
                                        <div style=\"height:35%\"><p>'+$content+'<\/p><\/div>',
                                        [/@compress]
                                    })
                                });


**使用list和set之间的转换达到集合元素去重：我的测试见 domain 文件夹下的ListSetConvert.java
Set与List之间转化：
    List list = new ArrayList(set);
    Set set = new HashSet(list);
    //但是有一点,转换当中可能要丢失数据,尤其是从list转换到set的时候,因为set不能有重复数据 //还有转换到set之后,他们原先在list上的顺序就没了,
public class Csdn {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        Collections.addAll(list, "zhao","long","ri","zhao");//填充
        Set<String> set=new HashSet<String>();
        set.addAll(list);//给set填充，这时重复的数据"zhao"将只剩一个
        list.clear();//清空list，不然下次把set元素加入此list的时候是在原来的基础上追加元素的
        list.addAll(set);//把set的内容填充给list
    }
 }

**<a href="#" class="viewActivityModel" onclick="viewActivityModel('${storeActivity.title?html}','${storeActivity.beginTm?string("yyyy-MM-dd")}','${storeActivity.endTm?string("yyyy-MM-dd")}','${storeActivity.photoUrl}','${storeActivity.content?html?replace("\r\n","<br>")?replace("\n","<br>")}')">
        调用js方法传递参数时：参数如果含有回车符如"\r\n"或"\n"等，将无法正常调用，页面出错，这时需要将参数里的"\r\n"和"\n"替换replace成"<br>"换行标签即可。
                freemarker的话使用?replace("\r\n","<br>")?replace("\n","<br>");

/*
$.getJSON( )的使用方法简介
JSON（JavaScript Object Notation）即JavaScript对象表示法，是一种轻量级的数据交换格式。它非常便于编程人员对数据的处理，也便于机器对数据的解析和生成，应用非常广泛。
json文件可以保存为“test.json”这样的文件，json数据的一般格式如下（“{ }”中的为对象，“[ ]”中的为数组）。

jQuery中的$.getJSON( )方法函数主要用来从服务器加载json编码的数据，它使用的是GET HTTP请求。使用方法如下：

$.getJSON( url,  [data], [success(data, textStatus, jqXHR)] )

url是必选参数，表示json数据的地址；
data是可选参数，用于请求数据时发送数据参数；
success是可参数，这是一个回调函数，用于处理请求到的数据。

获取json数据举例：

$.getJSON('test.json', function(data){
   for (var i = 0; i < data.rows.length; i++) {
      $('#test').append('<p>' + data.rows[i].realName + '</p>');
    }
});
*/


***validate.js验证中验证成功后或是再次验证时移除错误信息：
                                $("label[for='tiresId']").remove();
                                $("label[for='price']").remove(); //移除指定的label标签：使用label[for='label附着的标签id'].remove()
                                $("#price").removeClass("fieldError");

**select2的功能分析：
                                $tyreSpecId.html('<option value="'+tyreSpecId+'">'+tyreSpecName+'</option>');//初始化时添加默认值，//新版，直接给select添加option$("#id").append(new Option("Jquery", 10001, false, true));//或者$("#id").append("<option value='10001'>Jquery</option>");
                                $tyreSpecId.select2({
                                    placeholder: '请选择轮胎规格',
                                [#--allowClear:true 允许清除选择的下拉框，但是无法清除初始化时设置的默认值--]
                                [#--multiple: true 启用多选--]
                                    ajax: {
                                        url: "findTires.cgi",
                                        dataType: 'json',
                                        delay: 250,
                                        data: function (params) {
                                            return {
                                                keyword: params.term,
                                                pageNumber: params.page,
                                            };
                                        },
                                        processResults: function (data, params) { //点击select下拉框后的 加载数据过程
                                            params.page = params.page || 1;
                                            return {
                                                results: data.items,
                                                pagination: {
                                                    more: params.page < data.totalPage
                                                }
                                            };
                                        },
                                        cache: true
                                    },
                                    escapeMarkup: function(markup){ //点击select下拉框后加载数据时 显示的内容，如可以返回“加载中”
                                        return markup;
                                    },
                                    templateResult: function(item){ //点击select下拉框后 加载数据后的动作，如将数据展示到下拉列表
                                        if(item.loading){
                                            return item.text;
                                        }
                                        return '<p>'+item['text']+'</p>';
                                    },
                                    templateSelection: function(repo){ //选择下拉框内容后 回填的值
                                        return repo.text;
                                    }
                                });
                                获取或设置值：
                                    五.获取选中项
                                    var res=$("#c01-select").select2("data")[0] ; //单选
                                    var reslist=$("#c01-select").select2("data");    //多选
                                    if(res==undefined)
                                    {
                                        alert("你没有选中任何项");
                                    }
                                    if(reslist.length)
                                    {
                                        alert("你选中任何项");
                                    }
                                停用或启用：$("select").enable(false);（老版）；$("select").prop("disabled", true);（新版）