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
    <script type="text/javascript" src="${staticPath}/static/js/iscroll.js"></script>
</head>
<body>
<div class="top">
    <div class="swiper-container promo-store-container" style="background-color: #000000;height:100%;"
         onclick="layer.closeAll()">
        <div class="swiper-wrapper">
        <#if store.storePhotos??&&store.storePhotos?size gt 0>
            <#list store.storePhotos as storePhoto>
                <#if storePhoto_index gte 6>
                    <#break>
                </#if>
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
    <div class="comment">
        <ul class="commentList">
        <#--<#list shows as show>-->
        <#--<li>-->
        <#--<p class="commentListP">-->
        <#--<label>${show.content}</label>-->
        <#--</br>-->
        <#--#<#if show.tyreSpecName??>-->
        <#--${show.tyreSpecName}-->
        <#--</#if>-->
        <#--<#if show.carBrandName??>-->
        <#--${show.carBrandName}-->
        <#--</#if>-->
        <#--<#if show.carSeriesName??>-->
        <#--${show.carSeriesName}-->
        <#--</#if>#-->
        <#--</p>-->
        <#--<div class="commentListDiv">-->
        <#--<#list show.photos as photo>-->
        <#--<a href="javascript:;"><img src="${photo}_640x640"-->
        <#--onclick="ShowInfoObj.viewImg(this,${photo_index})"></a>-->
        <#--</#list>-->
        <#--</div>-->
        <#--</li>-->
        <#--<div class="commentDiv">-->
        <#--<span><#if show.modifyTm??>${show.modifyTm}</#if></span>-->
        <#--&lt;#&ndash;<label><#if show.dzNum??>${show.dzNum}</#if></label>&ndash;&gt;-->

        <#--<#if show.hasUserPlused?? && show.hasUserPlused>-->
        <#--<h4 class="djsBoxDivRightColor"><b-->
        <#--class="djsBoxDivRightB"></b>&nbsp;<em><#if show.dzNum<10000>${show.dzNum}<#else>-->
        <#--10000+</#if></em></h4>-->
        <#--<#else>-->
        <#--<h4><b></b>&nbsp;<em><#if show.dzNum<10000>${show.dzNum}<#else>10000+</#if></em></h4>-->
        <#--</#if>-->
        <#--</div>-->
        <#--</#list>-->
            <li>
                <p class="commentListP">
                    <label>${shows[0].content}</label>
                    </br>
                    #<#if shows[0].tyreSpecName??>
                ${shows[0].tyreSpecName}
                </#if>
                <#if shows[0].carBrandName??>
                ${shows[0].carBrandName}
                </#if>
                <#if shows[0].carSeriesName??>
                ${shows[0].carSeriesName}
                </#if>#
                </p>
                <div class="commentListDiv">
                <#list shows[0].photos as photo>
                    <a href="javascript:;"><img src="${photo}_640x640"
                                                onclick="ShowInfoObj.viewImg(this,${photo_index})"></a>
                </#list>
                </div>
                <div class="commentDiv">
                    <span><#if shows[0].modifyTm??>${shows[0].modifyTm}</#if></span>
                <#--<label><#if shows[0].dzNum??>${shows[0].dzNum}</#if></label>-->

                <#if shows[0].hasUserPlused?? && shows[0].hasUserPlused>
                    <h4 class="djsBoxDivRightColor"><b
                            class="djsBoxDivRightB"></b>&nbsp;<em><#if shows[0].dzNum<10000>${shows[0].dzNum}<#else>
                        10000+</#if></em></h4>
                <#else>
                    <h4><b></b>&nbsp;<em><#if shows[0].dzNum<10000>${shows[0].dzNum}<#else>10000+</#if></em></h4>
                </#if>
                </div>
            </li>
        </ul>
          <div id="pullUp">
           <span class="pullUpIcon"></span><span class="pullUpLabel" >上拉加载更多...</span>
          </div>
        <div class="ewm"><img src="${staticPath}/static/img/ewm.png">
            <p>关注中策车空间，赢取福利</p></div>
    </div>
</div>
<script id="swiperTemplate" type="text/x-jquery-tmpl">
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
    <#list shows as show>
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
    </#list>
</script>
<script type="text/javascript">
    var   myScroll,
            pullDownEl, pullDownOffset,
            pullUpEl, pullUpOffset,
            generatedCount = 0;

    /**
     * 下拉刷新 （自定义实现此方法）
     * myScroll.refresh(); 数据加载完成后，调用界面更新方法
     */
    function pullDownAction () {
        setTimeout(function () {
            var el, li, i;
            el = document.getElementById('commentList');

            for (i=0; i<3; i++) {
                li = document.createElement('li');
                li.innerText = 'Generated row ' + (++generatedCount);
                el.insertBefore(li, el.childNodes[0]);
            }

            myScroll.refresh();   //数据加载完成后，调用界面更新方法
        }, 1000);
    }

    /**
     * 滚动翻页 （自定义实现此方法）
     * myScroll.refresh();   // 数据加载完成后，调用界面更新方法
     */
    function pullUpAction () {
        setTimeout(function () {  // <-- Simulate network congestion, remove setTimeout from production!
            var el, li, i;
            el = document.getElementById('commentList');

            for (i=0; i<10; i++) {  //创建十行
                li = document.createElement('li');
                li.innerText = 'Generated row ' + (++generatedCount);
                el.appendChild(li, el.childNodes[0]);
            }

            myScroll.refresh();   //数据加载完成后，调用界面更新方法
        }, 1000);
    }

    /**
     * 初始化iScroll控件
     */
    function loaded() {
        pullDownEl = document.getElementById('pullDown');
        pullDownOffset = pullDownEl.offsetHeight;
        pullUpEl = document.getElementById('pullUp');
        pullUpOffset = pullUpEl.offsetHeight;

        myScroll = new iScroll('wrapper', {
            scrollbarClass: 'myScrollbar',
            useTransition: false,
            topOffset: pullDownOffset,
            onRefresh: function () {
                if (pullDownEl.className.match('loading')) {
                    pullDownEl.className = '';
                    pullDownEl.querySelector('.pullDownLabel').innerHTML = '下拉刷新...';
                } else if (pullUpEl.className.match('loading')) {
                    pullUpEl.className = '';
                    pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载更多...';
                }
            },
            onScrollMove: function () {
                if (this.y > 5 && !pullDownEl.className.match('flip')) {
                    pullDownEl.className = 'flip';
                    pullDownEl.querySelector('.pullDownLabel').innerHTML = '松手开始更新...';
                    this.minScrollY = 0;
                } else if (this.y < 5 && pullDownEl.className.match('flip')) {
                    pullDownEl.className = '';
                    pullDownEl.querySelector('.pullDownLabel').innerHTML = '下拉刷新...';
                    this.minScrollY = -pullDownOffset;
                } else if (this.y < (this.maxScrollY - 5) && !pullUpEl.className.match('flip')) {
                    pullUpEl.className = 'flip';
                    pullUpEl.querySelector('.pullUpLabel').innerHTML = '松手开始更新...';
                    this.maxScrollY = this.maxScrollY;
                } else if (this.y > (this.maxScrollY + 5) && pullUpEl.className.match('flip')) {
                    pullUpEl.className = '';
                    pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载更多...';
                    this.maxScrollY = pullUpOffset;
                }
            },
            onScrollEnd: function () {
                if (pullDownEl.className.match('flip')) {
                    pullDownEl.className = 'loading';
                    pullDownEl.querySelector('.pullDownLabel').innerHTML = '加载中...';
                    pullDownAction();  // ajax call
                } else if (pullUpEl.className.match('flip')) {
                    pullUpEl.className = 'loading';
                    pullUpEl.querySelector('.pullUpLabel').innerHTML = '加载中...';
                    pullUpAction(); // ajax call
                }
            }
        });

        setTimeout(function () { document.getElementById('wrapper').style.left = '0'; }, 800);
    }

    //初始化绑定iScroll控件
    document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
    document.addEventListener('DOMContentLoaded', loaded, false);
</script>
<script src="${staticPath}/static/js/showInfo.js"></script>
<div id="map" style="width: 100%;height: 100%;display: none">
</div>
</body>
</html>