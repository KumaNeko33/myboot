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


日期插件WdatePicker的使用：
（1）下载WdatePicker.js（包括lang和skin文件夹）。
（2）在html页面中导入WdatePicker.js。
（3）在输入框input元素上加入class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'beginDate\')}'})"代码。
开始日期-maxDate:'#F{$dp.$D(\'endDate\')||\'new Date()\'}'
结束日期-minDate:'#F{$dp.$D(\'startDate\')}',maxDate:new Date()
这里的maxDate和minDate后的是标准写法，不能把endDate换成其他的，如createDate
（4）打开页面查看效果。
推荐格式：id="beginDate"和id="endDate"日期插件才能定位到当前标签正下方显示
<input type="text" id="beginDate" name="createTimeBegin" value="${createTimeBegin}" class="text Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', maxDate: '#F{$dp.$D(\'endDate\')}'});" />

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
layer弹出层的content无法接收之前javascript的代码（title可以），在content中写入html标签后，里面标签调用javascript方法，可以通过隐藏域来存储值，然后在调用的javascript方法通过$("#id")选择器来获取
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