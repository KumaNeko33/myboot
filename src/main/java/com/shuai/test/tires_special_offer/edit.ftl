<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>编辑特价轮胎 - Powered By SHOWCOO</title>
    <meta name="author" content="SHOWCOO Team"/>[#include "/admin/include/config.ftl" /]
    <link href="${staticPath}/tiger/admin/css/common.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="${staticPath}/tiger/admin/css/jquery.bigautocomplete.css" type="text/css"/>
    <script type="text/javascript" src="${staticPath}/tiger/admin/js/jquery.js"></script>
    <script type="text/javascript" src="${staticPath}/tiger/admin/js/jquery.tools.js"></script>
    <script type="text/javascript" src="${staticPath}/tiger/admin/js/jquery.validate.js"></script>
    <script type="text/javascript" src="${staticPath}/tiger/admin/js/jquery.lSelect.js"></script>
    <script type="text/javascript" src="${staticPath}/tiger/admin/js/common.js"></script>
    <script type="text/javascript" src="${staticPath}/tiger/admin/js/input.js"></script>
    <script type="text/javascript" src="${staticPath}/tiger/admin/datepicker/JJG.date.js"></script>

    <style type="text/css">
        .mdselect {
            padding-left: 5px;
        }
        .mdcon {
            margin-top: 13px;
        }
        .mdcell {
            border: 1px solid #dcdcdc;
            float: left;
            width: 368px;
        }
        .newscon .mdcell {
            width: 340px;
        }
        .newscon .mdcon {
            margin-top: 0;
        }
        .mdtitle {
            background-color: #f0f0f0;
            color: #999;
            font-size: 12px;
            height: 40px;
            line-height: 40px;
            padding-left: 20px;
            position: relative;
        }
        .mdtitle span {
            color: #666;
            font-weight: bold;
            padding-left: 90px;
        }
        .mdtitle input {
            margin-right: 5px;
            vertical-align: middle;
        }
        .mdright {
            border-left: medium none;
        }
        .scrollmd {
            height: 410px;
            overflow-y: scroll;
            width: 100%;
        }
        .scrollmd ul li input {
            display: inline-block;
            float: left;
            margin-right: 5px;
            margin-top: 14px;
            vertical-align: middle;
        }
        .scrollmd ul li p {
            float: left;
        }
        .newscon .scrollmd ul li p {
            width: 295px;
        }
        .mdcell ul li {
            border-top: 1px solid #dcdcdc;
            line-height: 40px;
            padding-left: 20px;
        }
        .mdright .scrollmd ul li p {
            float: inherit;
            width: 287px;
        }
        .mdright .scrollmd ul li {
            box-sizing: border-box;
            padding-left: 10px;
            position: relative;
        }
        .mdright .mdtitle a {
            position: absolute;
            right: 11px;
            top: 0;
        }
        .storeType {
            width: auto;
            padding: 10px;
            border: 1px solid #cccccc;
            float: left;
            margin-right: 18px;
        }

        .storeType input[type="text"] {
            width: 65px;
        }

    </style>
    <script type="text/javascript">
        $().ready(function () {
            var $inputForm = $("#inputForm");
            var isEnabled = '${specialOffer.isEnabled}';

            //加载地区
            $("#areaId").lSelect({
                url: "${base}/admin/common/area.cgi"
            });

            //上架状态，禁止编辑
            if (isEnabled === '1') {
                //禁用全部文本框
                $("input:text").attr("readonly", true).addClass("disabled");
                //地区下拉框
                $("select[name=areaId_select]").attr("disabled", true);
                //禁用添加经销商
                $("#distributorAdd").attr("disabled", true);

                $("#batchSn").removeClass("disabled").removeAttr("readonly");
            } else {
                disabledInput();
            }

            //表单验证
            $inputForm.validate({
                rules: {
                    tiresId: {required: true},
                    batchSn: {
                        required: true,
                        pattern:/^\d{8}$/,
                        remote: {
                            url: "checkBatchSn.cgi",
                            cache: false
                        }
                    },
                    price: {
                        required: true,
                        pattern: /^(?!(0[0-9]{0,}$))[0-9]{1,}[.]{0,}[0-9]{0,}$/,
                        decimal: {
                            integer: 12,
                            fraction: ${setting.priceScale}
                        }
                    },
                    distributorIds: {required: true},
                    beginDate: {
                        required: true
                    },
                    endDate: {
                        required: true
                    },
                    limitNumber: {
                        digits: true,
                        min: 1
                    }
                }, messages: {
                    price: {
                        pattern: "${message("admin.validate.positive")}"
                    },
                    batchSn:{
                        pattern: "必须填写8个长度的数字",
                        remote: "已经存在历史批次号"
                    },
                    limitNumber: {
                        digits: "${message("admin.validate.positive")}"
                    },
                    distributorIds: {
                        required: "必须至少有一个参与经销商"
                    }
                }
            });

            //地区变更事件
            $("select[name=areaId_select]").on("change", function () {
                lastAreaSelect($(this).val());
            });

            //加载经销商
            $("#distributorBtn").click(function () {
                var areaObj = $("select[name=areaId_select]:last");
                while (areaObj.val() == "" && areaObj.prev("[name=areaId_select]").length > 0) {
                    areaObj = areaObj.prev("[name=areaId_select]");
                }
                var areaVal = "0";
                if(areaObj) {
                 areaVal = areaObj.val();
                }
                lastAreaSelect(areaVal);
            });

            //下架才能操作
            if(isEnabled=='0') {
                //全选复选框
                $("input.select_all").click(function () {
                    var leftChkboxLi = $("div.left_panel li");
                    leftChkboxLi.remove();
                    leftChkboxLi.appendTo("div.right_panel ul").find("input:checkbox").attr("checked", true);
                    $(this).attr("checked", false);
                    containDistributor();
                });

                //全部取消
                $("a.cancel_all").click(function () {
                    var rightChkboxLi = $("div.right_panel li");
                    rightChkboxLi.remove();
                    rightChkboxLi.appendTo("div.left_panel ul").find("input:checkbox").attr("checked", false);
                    containDistributor();
                });

                //左边经销商列表单个复选框
                $("div.left_panel input:checkbox").live("click", function () {
                    var leftChkboxLi = $(this).closest("li");
                    leftChkboxLi.remove();
                    leftChkboxLi.appendTo("div.right_panel ul");
                    containDistributor();
                });

                //右边经销商列表单个复选框
                $("div.right_panel input:checkbox").live("click", function () {
                    var rightChkboxLi = $(this).closest("li");
                    rightChkboxLi.remove();
                    rightChkboxLi.appendTo("div.left_panel ul");
                    containDistributor();
                });
            }

            //复选框事件
            $(":checkbox").on("click", function () {
                if (isEnabled === '1') {
                    return false;
                }
                var check = $(this).attr('checked');
                var inputs = $(this).siblings("input:text");

                if (inputs.length == 0) {
                    inputs = $(this).parent().parent().find("input:text");
                }

                if (check) {
                    $.each(inputs, function (i, n) {
                        $(this).removeAttr("disabled");
                        if ($(this).siblings("input").length == 0) {
                            //特价文本框
                            if (i == 0) {
                                $(n).rules("add", {
                                    required: true,
                                    pattern: /^(?!(0[0-9]{0,}$))[0-9]{1,}[.]{0,}[0-9]{0,}$/,
                                    decimal: {
                                        integer: 12,
                                        fraction: ${setting.priceScale}
                                    }
                                });
                                //限购数量
                            } else if (i == 1) {
                                $(n).rules("add", {
                                    digits: true,
                                    min: 1,
                                    messages: {digits: "只允许输入正整数"}
                                });
                            }
                        } else {
                            $(n).rules("add", {required: true});
                        }
                    });
                } else {
                    $.each(inputs, function (i, n) {
                        $(n).rules("remove");
                        $(this).removeClass("fieldError");
                        $(this).siblings("label").remove();

                        //禁用文本框，清空原来值
                        $(this).attr("disabled", true);
                        $(this).val("");
                        $(this).parent().parent().find("input:hidden").remove();
                    });
                }
            });

            checkInputValid();
        });

        //禁用复选框下面的文本框
        function disabledInput() {
            var allCheckbox = $(":checkbox");

            $.each(allCheckbox, function (i, n) {
                var check = $(this).attr('checked');
                if (!check) {
                    $(n).siblings("input:text").attr("disabled", true);
                    $(n).parent().parent().find("input:text").attr("disabled", true);
                }
            });
        }

        //打开页时，添加原数据校验
        function checkInputValid() {

            //门店类型
            var storeCheckboxs = $(".storeType").find(":checkbox");

            $.each(storeCheckboxs, function (i, n) {
                var check = $(n).attr('checked');
                if (check) {
                    var inputs = $(n).parent().parent().find("input:text");

                    $.each(inputs, function (i, n) {
                        if ($(n).siblings("input").length == 0) {
                            //特价文本框
                            if (i == 0) {
                                $(n).rules("add", {
                                    required: true,
                                    pattern: /^(?!(0[0-9]{0,}$))[0-9]{1,}[.]{0,}[0-9]{0,}$/,
                                    decimal: {
                                        integer: 12,
                                        fraction: ${setting.priceScale}
                                    }
                                });
                                //限购数量
                            } else if (i == 1) {
                                $(n).rules("add", {
                                    digits: true,
                                    min: 1,
                                    messages: {digits: "只允许输入正整数"}
                                });
                            }
                        }
                    });
                }
            });

            //限时，限量
            var timeNumberCheck = $(".limit").find(":checkbox");
            $.each(timeNumberCheck, function (i, n) {
                var check = $(n).attr('checked');
                if (check) {
                    var inputs = $(n).siblings("input");
                    $.each(inputs, function (i, n) {
                        $(n).rules("add", {required: true});
                    });
                }
            });
        }


        //最后选择的地区，加载经销商
        function lastAreaSelect(areaVal) {
            $("div.left_panel ul").empty();

            $.ajax({
                url: "${base}/admin/distributor/findList.cgi",
                data: {areaId: areaVal},
                type: "GET",
                async: false,
                timeout: 5000,
                success: function (data) {
                    if (data.code == 0) {
                        var list = data.list, liHtml = "";
                        for (var i = 0; i < list.length; i++) {
                            if ($("div.right_panel input[value=" + list[i].id + "]:checkbox").length == 0) {
                                liHtml += "<li class='clearfix'><input type='checkbox' value='" + list[i].id + "' name='distributorId'/><p class='w_cut'>" + list[i].name + "</p>";
                            }
                        }
                        $("div.left_panel ul").append(liHtml);
                    }
                },
            });
        }

        //参与经销商
        function containDistributor() {
            var checkDis = $('input[name="distributorId"]:checked');
            var disIds = new Array();
            $.each(checkDis, function(i, n){
                disIds.push($(n).val());
            });
            $("#distributorIds").val(disIds.join(","));
            if(disIds.length>0){
                $("label[for='distributorIds']").remove();
            }
        }
    </script>
</head>
<body>
<div class="path">
    <a href="${base}/admin/common/index.cgi">${message("admin.path.index")}</a> &raquo; 编辑特价轮胎
</div>
<form id="inputForm" action="update.cgi" method="post" enctype="multipart/form-data"
      onkeydown="if(event.keyCode==13){return false;}">
    <table class="input">
        <tr>
            <th>
                <span class="requiredField">*</span>商品ID:
                <input type="hidden" name="tiresId" id="tiresId" value="${specialOffer.tires.id}"/>
                <input type="hidden" name="sn" id="tiresSN" value="${specialOffer.sn}"/>
                <input type="hidden" name="id" value="${specialOffer.id}"/>
            </th>
            <td>
                <label id="lblTiresId">${specialOffer.tires.id}</label>
            </td>
        </tr>
        <tr>
            <th>
                <span class="requiredField">*</span>批次号:
            </th>
            <td>
                <input type="text" class="text" id="batchSn" name="batchSn" value="${specialOffer.batchSn}" maxlength="30"/>
            </td>
        </tr>
        <tr>
            <th>
                <span class="requiredField">*</span>商品名称:
            </th>
            <td>
            ${specialOffer.tires.erpImportName}
            </td>
        </tr>
        <tr>
            <th>
                <span class="requiredField">*</span>建议零售价:
            </th>
            <td>
                <input type="text" id="price" name="price" class="text" maxlength="16" value="${specialOffer.price}"/>
            </td>
        </tr>
        <tr>
            <th>
                参与经销商:
            </th>
            <td>
                <div class="mdselect">
                    <p>
                    <span class="fieldSet">
                        <input id="areaId" name="areaId" type="hidden">
                        <select name="areaId_select" style="margin-right: 4px;">
                        </select>
                    </span>
                        <input name="distrbutorBtn" id="distributorBtn" value="加载经销商" type="button">
                        <input type="hidden" class="text" name="distributorIds" id="distributorIds" value="${specialOffer.distributorIds}"/>
                    </p>

                    <div class="mdcon clearfix">
                        <div class="mdcell mdleft">
                            <div class="mdtitle"><label>
                                <input class="select_all" type="checkbox"/>全选</label><span>当前经销商</span></div>
                            <div class="scrollmd left_panel">
                                <ul></ul>
                            </div>
                        </div>
                        <div class="mdcell mdright">
                            <div class="mdtitle"><span>已选经销商</span>
                                <a href="javascript:void(0);" class="cancel_all">全部取消</a></div>
                            <div class="scrollmd right_panel">
                                <ul>
                                [#if distributors??]
                                    [#list distributors as distr]
                                    <li class='clearfix'><input type='checkbox' value='${distr.id}' name='distributorId' checked='true'/>
                                        <p class='w_cut'>${distr.name}</p>
                                    [/#list]
                                [/#if]
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </td>
        </tr>
        <tr>
            <th>

                <div>
                    <dl>门店类型:</dl>
                    <dl>特价:</dl>
                    <dl>限购数量:</dl>
                </div>
            </th>
            <td>
            [#list storeTypes as storeType]
                <div class="storeType">
                    [#assign id='' /]
                    [#assign type=-1 /]
                    [#assign price=''/]
                    [#assign limitNumber=''/]
                    [#list specialOffer.tiresSpecialOfferPrices as specialOfferPrices]
                        [#if specialOfferPrices.storeType==storeType]
                            [#assign id=specialOfferPrices.id /]
                            [#assign type=storeType /]
                            [#assign price=specialOfferPrices.price /]
                            [#assign limitNumber=specialOfferPrices.limitNumber/]
                            [#break]
                        [/#if]
                    [/#list]
                    <dl>
                        <input type="checkbox" name="tiresSpecialOfferPrices[${storeType_index}].isEnabled" value="1"
                               [#if type==storeType]checked="true"[/#if]  style="transform: scale(1.3,1.3);"
                               [#if specialOffer.isEnabled=='1']onclick="return false"[/#if]>
                    ${message("Store.StoreType." + storeType)}
                    </dl>
                    <dl>
                        <input type="text" class="text" name="tiresSpecialOfferPrices[${storeType_index}].price" value="${price}"/>
                    </dl>
                    <dl>
                        <input type="text" class="text" name="tiresSpecialOfferPrices[${storeType_index}].limitNumber"
                               value="${limitNumber}"/>
                    </dl>
                    <input type="hidden" name="tiresSpecialOfferPrices[${storeType_index}].storeType" value="${storeType}"/>
                    <input type="hidden" name="tiresSpecialOfferPrices[${storeType_index}].id" value="${id}"/>
                </div>
            [/#list]
            </td>
        </tr>
        <tr>
            <th>
                门店经营品牌(可见):
            </th>
            <td>
            [#list brands as brand]
                [#assign temp='']
                [#if specialOffer.storeBrandIds??]
                    [#list specialOffer.storeBrandIds?split(",") as brandId]
                        [#if brandId==brand.id]
                            [#assign temp=brandId]
                            [#break ]
                        [/#if]
                    [/#list]
                [/#if]
                <label>
                    <input type="checkbox" name="storeBrandIds" value="${brand.id}"
                           [#if temp!='']checked="true"[/#if]/>${brand.name}
                </label>
            [/#list]
            </td>
        </tr>
        <tr>
            <th>
                限时:
            </th>
            <td class="limit">
               [#-- <input type="checkbox" id="isTire" name="isLimitTime" value="1"
                       [#if specialOffer.isLimitTime=='1']checked="true"[/#if]/>--]

                <input type="text" id="beginDate" name="beginDate" class="text Wdate" autocomplete="off"
                       value="[#if specialOffer.beginDate]${specialOffer.beginDate?string("yyyy-MM-dd HH:mm:ss")}[/#if]"
                [#if specialOffer.isEnabled=='0']
                       onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', maxDate: '#F{$dp.$D(\'endDate\')}'});"[/#if]/>
                &nbsp;&nbsp;-&nbsp;&nbsp;
                <input type="text" id="endDate" name="endDate" class="text Wdate" autocomplete="off"
                       value="[#if specialOffer.endDate]${specialOffer.endDate?string("yyyy-MM-dd HH:mm:ss")}[/#if]"
                [#if specialOffer.isEnabled=='0']
                       onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', minDate: '#F{$dp.$D(\'beginDate\')}'});" [/#if]/>

            </td>
        </tr>
        <tr>
            <th>
                限量:
            </th>
            <td class="limit">
                <input type="checkbox" name="isLimitNumber" value="1"
                       [#if specialOffer.isLimitNumber=='1']checked="true"[/#if]/>
                <input type="text" class="text" name="limitNumber" value="${specialOffer.limitNumber}"/>
            </td>
        </tr>
        <tr>
            <th>
                上架状态:
            </th>
            <td>
                <label>
                    <input type="radio" name="isEnabled" value="1"
                           [#if specialOffer.isEnabled=='1']checked="true"[/#if]/>上架
                </label>
                <label>
                    <input type="radio" name="isEnabled" value="0"
                           [#if specialOffer.isEnabled=='0']checked="true"[/#if]/>下架
                </label>
            </td>
        </tr>
    </table>
    <table class="input">
        <tr>
            <th>
                &nbsp;
            </th>
            <td>
                [#if exceed=='1']
                    <input type="submit" class="button" value="${message("admin.common.submit")}"/>
                [#else]
                    <input type="button" class="button" value="已结束" disabled="true"/>
                [/#if]
                <input type="button" class="button" value="${message("admin.common.back")}"
                       onclick="location.href='list.cgi'"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>