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