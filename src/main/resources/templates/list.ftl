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
                                    })
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