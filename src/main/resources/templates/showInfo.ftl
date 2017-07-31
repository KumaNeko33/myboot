<!DOCTYPE html>
<html xmlns:th=http://www.thymeleaf.org></html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="HandheldFriendly" content="true">
    <title>${store.storeName}</title>
<#include "include/common.ftl">
    <link type="text/css" href="${staticPath}/static/css/showInfo.css" rel="stylesheet"/>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=6yAoynmTPNlTBa8z1X4LfwGE"></script>
</head>
<body>
<div class="top">
    <#--swiper-container定义Swiper的容器，里面包括滑动块（slides）的封套（wrapper)、分页器(pagination)、前进按钮等-->
    <div class="swiper-container promo-store-container" style="background-color: #000000;height:100%;"
         <#--layer.closeAll(); //疯狂模式，关闭所有层-->
         onclick="layer.closeAll()">
        <#--swiper-wrapper定义触控的对象，可触摸区域，移动的块的集合，过渡时会随slide切换产生位移-->
        <div class="swiper-wrapper">
        <#if store.storePhotos??&&store.storePhotos?size gt 0>
            <#list store.storePhotos as storePhoto>
                <#if storePhoto_index gte 6>
                    <#break>
                </#if>
            <#--swiper-slide定义切换的滑块，可以包含文字、图片、html元素或另外一个Swiper-->
                <div class="swiper-slide">
                    <img src="${storePhoto}">
                </div>
            </#list>
        <#else>
            <div class="swiper-slide">
                <img src="${staticPath}/static/img/md_0.png">
            </div>
        </#if>
        </div>
            <#---->
        <div class="swiper-pagination promo-store-pagination"></div>
    </div>
</div>
<div class="box">
    <div class="contact">
        <p>
            <span><img src="${staticPath}/static/img/m1.png"></span>
            <b><#if store.storeName??>${store.storeName}</#if></b>
        </p>
        <p>
            <#--门店地址，在showInfo.js中绑定了点击事件click-->
            <span><img src="${staticPath}/static/img/m2.png"></span>
            <b id="address" lng="<#if store.lng??>${store.lng}</#if>"
               lat="<#if store.lat??>${store.lat}</#if>"><#if store.storeAddress??>${store.storeAddress}</#if></b>
        </p>
        <p>
            <span><img src="${staticPath}/static/img/m3.png"></span>
            <b><a href="tel:<#if store.mobilePhone??>${store.mobilePhone}</#if>"><#if store.mobilePhone??>${store.mobilePhone}</#if></a></b>
        </p>
        <#--<div class="middlePosition">
            <a href="tel:<#if store.mobilePhone??>${store.mobilePhone}</#if>"><img
                    src="${staticPath}/static/img/tel.png"></a>
        </div>-->
    </div>
    <#--卖家秀开始展示-->
    <div class="comment">
        <ul class="commentList">
            <li>
                <p class="commentListP">
                    <label>${show.content}</label>
                    </br>
                    #<#if show.tyreSpecName??>
                ${show.tyreSpecName}
                </#if>
                <#if show.carBrandName??>
                ${show.carBrandName}
                </#if>
                <#if show.carSeriesName??>
                ${show.carSeriesName}
                </#if>#
                </p>
                <div class="commentListDiv">
                <#list show.photos as photo>
                    <#--${photo}_640x640展示每张照片的一部分-->
                    <a href="javascript:;"><img src="${photo}_640x640"
                                                <#--卖家秀点击照片，触发全局变量 ShowInfoObj的viewImg（使用layer弹窗查看图片）的方法，并把当前照片对象和索引传进来-->
                                                onclick="ShowInfoObj.viewImg(this,${photo_index})"></a>
                </#list>
                </div>
            </li>
            <div class="commentDiv">
                <span><#if show.modifyTm??>${show.modifyTm}</#if></span>
            <#--<label><#if show.dzNum??>${show.dzNum}</#if></label>-->

            <#if hasUserPlused?? && hasUserPlused>
                <h4 class="djsBoxDivRightColor"><b
                        class="djsBoxDivRightB"></b>&nbsp;<em><#if show.dzNum<10000>${show.dzNum}<#else>
                    10000+</#if></em></h4>
            <#else>
                <h4><b></b>&nbsp;<em><#if show.dzNum<10000>${show.dzNum}<#else>10000+</#if></em></h4>
            </#if>
            </div>
        </ul>
        <div class="ewm"><img src="${staticPath}/static/img/ewm.png">
            <p>关注中策车空间，赢取福利</p></div>
    </div>
</div>
<script id="swiperTemplate" type="text/x-jquery-tmpl">
    <#--卖家秀的照片滑动功能容器div  photo-container-->
    <div class="swiper-container photo-container" style="background-color: #000000;height:100%;" onclick="layer.closeAll()">
        <div class="swiper-wrapper">
        {{each photos}}
            <div class="swiper-slide"><img src="{{= $value}}" onload="ShowInfoObj.loadView(this)"></div>
        {{/each}}
        </div>
        <div class="swiper-pagination photo-pagination"></div>
    </div>











</script>
<script type="text/javascript">
    <#--点击点赞，点赞数加1-->
    var showId = '';
    <#if show?? && show.id??>
    showId = '${show.id?c}';
    </#if>
    (function (doc, win) {
        var docEl = doc.documentElement,
                resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
                recalc = function () {
                    var clientWidth = docEl.clientWidth;
                    if (!clientWidth) return;
                    docEl.style.fontSize = 100 * (clientWidth / 750) + 'px';
                };

        if (!doc.addEventListener) return;
        win.addEventListener(resizeEvt, recalc, false);
        doc.addEventListener('DOMContentLoaded', recalc, false);
    })(document, window);
</script>
<#--初始化页面时加载showInfo.js,这个js里只有一个$(function(){})函数，在页面加载完后执行-->
<script src="${staticPath}/static/js/showInfo.js"></script>
<div id="map" style="width: 100%;height: 100%;display: none">
</div>
</body>
</html>