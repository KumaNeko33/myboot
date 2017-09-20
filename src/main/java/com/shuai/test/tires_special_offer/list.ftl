<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.tires.list")} - Powered By Zhongce</title>
<meta name="author" content="SHOWCOO Team" />
<meta name="copyright" content="SHOWCOO" />[#include "/admin/include/config.ftl" /]
<link href="${staticPath}/tiger/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath}/tiger/admin/js/jquery.js"></script>
<script type="text/javascript" src="${staticPath}/tiger/admin/js/common.js"></script>
<script type="text/javascript" src="${staticPath}/tiger/admin/js/list.js"></script>
<script type="text/javascript" src="${staticPath}/tiger/admin/datepicker/JJG.date.js"></script>
<style type="text/css">
.promotion {
	color: #cccccc;
}
</style>
<script type="text/javascript">
$().ready(function() {
	var $listForm = $("#listForm");
	var $moreButton = $("#moreButton");

/*  var $deleteButton = $("#deleteButton");
	var $filterSelect = $("#filterSelect");
	var $filterOption = $("#filterOption a");
	*/
	[@flash_message /]
	
	// 更多选项
	$moreButton.click(function() {
		$.dialog({
			title: "${message("admin.tires.moreOption")}",
			[@compress single_line = true]
				content:
				'<table id="moreTable" class="moreTable">'+
                	'<tr>' +
                		'<th>${message("admin.main.startEndTime")}:</th>' +
                		'<td>' +
                			'<input type="text" id="_beginDate" name="beginDate" value="[#if beginDate??]${beginDate?string("yyyy-MM-dd HH:mm:ss")}[/#if]" class="text Wdate" onfocus="WdatePicker({dateFmt: \'yyyy-MM-dd HH:mm:ss\',maxDate: \'#F{$dp.$D(\\\'_endDate\\\')}\'});" />-' +
                			'<input type="text" id="_endDate" name="endDate" value="[#if endDate??]${endDate?string("yyyy-MM-dd HH:mm:ss")}[/#if]" class="text Wdate" onfocus="WdatePicker({dateFmt: \'yyyy-MM-dd HH:mm:ss\',minDate: \'#F{$dp.$D(\\\'_beginDate\\\')}\'});" />' +
                		'</td>' +
                	'</tr>' +
					'<tr>'+
						'<th>${message("Tires.brand")}:</th>'+
						'<td>'+
							'<label class="select"><select name="brandId">'+
								'<option value="">${message("admin.common.choose")}</option>'+
								[#list brands as brand]
									'<option value="${brand.id}"[#if brand.id == brandId] selected="selected"[/#if]>'+
										'${brand.name}'+
									'</option>'+
								[/#list]
							'</select>'+
						'</td>'+
					'</tr>'+
                	'<tr>'+
               		 	'<th>状态:</th>'+
                		'<td>'+
                		'<label class="select"><select name="status">'+
                			'<option value="">${message("admin.common.choose")}</option>'+
                			'<option value="1" [#if status==1]selected="selected"[/#if]>上架</option>'+
                    		'<option value="0" [#if status==0]selected="selected"[/#if]>下架</option>'+
						'</select>'+
               	 		'</td>'+
                	'</tr>'+
				'</table>',
			[/@compress]
			width: 530,
			modal: true,
			ok: "${message("admin.dialog.ok")}",
			cancel: "${message("admin.dialog.cancel")}",
			onOk: function() {
				$("#moreTable :input").each(function() {
					var $this = $(this);
					$("#" + $this.attr("name")).val($this.val());
				});
				$listForm.submit();
			}
		});
	});

	$("#btnPush").click(function() {
        $.dialog({
            title: "发送消息",
			content:'<form id="cancelForm" action="push.cgi" method="post">'+
            '<table id="moreTable" class="moreTable">'+
            '<tr>'+
            '<th>SSO手机号:</th>'+
            '<td>'+
            '<input class="text" type="text" name="android" id="txtAndroid">'+
            '</td>'+
            '</tr>'+
            '<tr>'+
            '<th>IOSToken:</th>'+
            '<td>'+
            '<input class="text" type="text" name="ios" id="txtIOS">'+
            '</td>'+
            '</tr>'+
            '</table>'+
			'</form>',
            width: 530,
            modal: true,
            onOk: function() {
                $.post("push.cgi", { android: $("#txtAndroid").val(), ios:$("#txtIOS").val() }, function(data){
                    if(data){
                        alert("发送成功");
					}else{
                        alert("发送失败");
					}
                });
                return false;
            }
		});
	});
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.cgi">${message("admin.path.index")}</a> &raquo; ${message("admin.tires.list")} <span>(${message("admin.page.total", page.total)})</span>
	</div>
	<form id="listForm" action="list.cgi" method="get">
		<input type="hidden" id="brandId" name="brandId" value="${brandId}" />
        <input type="hidden" id="beginDate" name="beginDate" value="[#if beginDate??]${beginDate?string("yyyy-MM-dd HH:mm:ss")}[/#if]"/>
        <input type="hidden" id="endDate" name="endDate" value="[#if endDate??]${endDate?string("yyyy-MM-dd HH:mm:ss")}[/#if]"/>
        <input type="hidden" id="status" name="status" value="${status}" />
		<div class="bar">
			<div class="buttonWrap">
                <a href="add.cgi" class="iconButton">
                    <span class="addIcon">&nbsp;</span>${message("admin.common.add")}
                </a>
                <a href="javascript:;" id="moreButton" class="button">${message("admin.tires.moreOption")}</a>
				<a href="javascript:;" id="refreshButton" class="iconButton">
					<span class="refreshIcon">&nbsp;</span>${message("admin.common.refresh")}
				</a>
				<a href="javascript:;" class="button"  id="btnPush">发个消息</a>
			</div>
			<div class="menuWrap">
				<div class="search">
					<span id="searchPropertySelect" class="arrow">&nbsp;</span>
					<input type="text" id="searchValue" name="searchValue" value="${page.searchValue}" maxlength="200" />
					<button type="submit">&nbsp;</button>
				</div>
				<div class="popupMenu">
					<ul id="searchPropertyOption">
						[#--<li>
							<a href="javascript:;"[#if page.searchProperty == "tires"] class="current"[/#if] val="tires">${message("Tires.name")}</a>
						</li>--]
						<li>
							<a href="javascript:;"[#if page.searchProperty == "sn"] class="current"[/#if] val="sn">${message("Tires.sn")}</a>
						</li>
						<li>
							<a href="javascript:;"[#if page.searchProperty == "batchSn"] class="current"[/#if] val="batchSn">批次号</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<table id="listTable" class="list">
			<tr>
				<th>
					<span>${message("Tires.sn")}</span>
				</th>
                <th>
                    <span>批次号</span>
                </th>
				<th>
					<span>${message("Tires.name")}</span>
				</th>
				<th>
					<span>${message("admin.main.MarketableDate")}</span>
				</th>
                <th>
                    <span>开始时间</span>
                </th>
                <th>
                    <span>结束时间</span>
                </th>
				<th>
					<span>${message("admin.main.MarketableStatus")}</span>
				</th>
                <th>
                    <span>剩余总量</span>
                </th>
				<th>
					<span>${message("admin.common.handle")}</span>
				</th>
			</tr>
			[#list page.content as specialOffer]
				<tr>
					<td>
						${specialOffer.sn}
					</td>
					<td>
						<span>${specialOffer.batchSn}</span>
					</td>
					<td>
						<span title="${specialOffer.tires.erpImportName}">
							${abbreviate(specialOffer.tires.erpImportName, 50, "...")}
						</span>
					</td>
                    <td>
                        [#if specialOffer.groundingDate??]
                            <span>${specialOffer.groundingDate?string("yyyy-MM-dd HH:mm:ss")}</span>
                        [/#if]
                    </td>
                    <td>
						[#if specialOffer.beginDate??]
                        <span>${specialOffer.beginDate?string('yyyy-MM-dd HH:mm:ss')}</span>
						[/#if]
                    </td>
                    <td>
						[#if specialOffer.endDate??]
                        <span>${specialOffer.endDate?string('yyyy-MM-dd HH:mm:ss')}</span>
						[/#if]
                    </td>
				    <td>
						[#if specialOffer.isEnabled=='1']
                            <span style="color:red">上架</span>
                        [#else]
                            <span >下架</span>
                        [/#if]
					</td>
                    <td>
						[#if specialOffer.limitNumber??]
							[#assign surplus=specialOffer.limitNumber-specialOffer.buyNumber]
                            <span>[#if surplus<0]0[#else]${surplus}[/#if]</span>
						[#else]
						不限量
						[/#if]
                    </td>
					<td>
						<a href="edit.cgi?id=${specialOffer.id}">[${message("admin.common.edit")}]</a>
					</td>
				</tr>
			[/#list]
		[#if page.total == 0 ]
            <tr>
                <td colspan="8" align="center">
                    <span><b>没有找到任何信息</b></span>
                </td>
            </tr>
		[/#if]
		</table>
		[#--分页--]
		[@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
			[#include "/admin/include/pagination.ftl"]
		[/@pagination]
	</form>
</body>
</html>