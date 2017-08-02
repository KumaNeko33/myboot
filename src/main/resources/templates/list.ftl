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
        $.dialog({
            type:"warn",
            content:$message,
            onOk: function () {
                $("#tjId").val($id);
                $("#tjStatusInp").val($tjStatus);
                $("#tjStatusForm").submit();
            }
        });
    });
</script>
