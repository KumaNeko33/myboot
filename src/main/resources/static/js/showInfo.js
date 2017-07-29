$(function () {
    var URL = {
        dz : staticPath + "/webDzOpt"
    };
    var mapLoad = true;
    var showInfo = {
        adjustImgSize : function(img, boxWidth, boxHeight) {
            img = $(img);
            // var imgWidth=img.width();
            // var imgHeight=img.height();
            // 上面这种取得img的宽度和高度的做法有点儿bug
            // src如果由两个大小不一样的图片相互替换时，上面的写法就有问题了，换过之后的图片的宽度和高度始终取得的还是换之前图片的值。
            // 解决方法：创建一个新的图片类，并将其src属性设上。
            var tempImg = new Image();
            tempImg.src = img.attr('src');
            var imgWidth=tempImg.width;
            var imgHeight=tempImg.height;

            //比较imgBox的长宽比与img的长宽比大小
            if((boxWidth/boxHeight)>=(imgWidth/imgHeight)){
                //重新设置img的width和height
                img.width(Math.floor((boxHeight*imgWidth)/imgHeight));
                img.height(boxHeight);
                //让图片居中显示
                var margin=Math.floor((boxWidth-img.width())/2);
                img.css("margin-left",margin);
                img.css("margin-right",margin);
            }else{
                //重新设置img的width和height
                img.width(boxWidth);
                img.height(Math.floor((boxWidth*imgHeight)/imgWidth));
                //让图片居中显示
                var margin=Math.floor((boxHeight-img.height())/2);
                img.css("margin-top",margin);
                var tempHeight = img.height()+margin*2
                if(tempHeight!=boxHeight){
                    margin += boxHeight-tempHeight;
                }
                img.css("margin-bottom",margin);
            }
        },
        loadView: function (img) {
            this.adjustImgSize(img,window.innerWidth,window.innerHeight)
        },
        //展示卖家秀viewImg
        viewImg : function(img,slide){
            img = $(img);   //获取当前图片对象，slide是照片的在list遍历时索引
            var imgList = [];
            var i = 0;
            img.parent().parent().find("img").each(function () {//当前img父元素a的父元素是<div class="commentListDiv">图片展示框的div
                //.find("img")获取所有img标签对象，形成一个对象集合，然后.each()进行遍历
                imgList[i] = $(this).attr("src");//获取当前img标签对象的src值，赋给imgList
                i++;
            });

            $(".photo-container").remove();//移除.photo-container的div卖家秀照片展示容器，如果存在的话
            //.tmpl()是jQuery的模板功能
            $("#swiperTemplate").tmpl({'photos':imgList},{//重新给 swiperTemplate模板 设置.photo-container的div卖家秀照片展示容器,照片资源是遍历imgList
                getMarginTop: function (img) {
                    var imgheight = $(img).find("img").height;
                    return (867-imgheight)/2;
                }
            }).appendTo($("body"));

            var showSwiper2 = new Swiper('.photo-container', {  //定义卖家秀照片展示容器的 Swiper滑动展示化
                pagination : '.photo-pagination',
                paginationType : 'bullets',
                autoHeight: true, //高度随内容变化
                width : window.innerWidth,
                initialSlide: slide,   //设定初始化时slide的索引。即 展示滑动化展示 为当前点击的照片
            });
            for(i=0; i<imgList.length; i++){
                showSwiper2.bullets[i].style.border='1px solid #fff'; //获取Swiper的分页器的小点bullets对象。通过类似mySwiper.bullets[1]获取其中某个。
                // 并给获取的指示点加一像素白边。
            }
            var width = window.innerWidth;
            var height = window.innerHeight;
            layer.open({    //将该卖家秀照片展示容器的 Swiper滑动展示化 内容弹窗展示，高宽为当前 窗体文档的高宽
             type: 1,   //弹窗类型：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）。
             shade: true,//遮罩即弹层外区域，默认是0.3透明度的黑色背景（'#000'），如果你想定义别的颜色，可以shade: [0.8, '#393D49']；如果你不想显示遮罩，可以shade: 0
             shadeClose: true,//是否点击遮罩关闭弹窗，如果你的shade是存在的，那么你可以设定shadeClose来控制点击弹层外区域关闭
             closeBtn: 0,//不显示关闭按钮,layer提供了两种风格的关闭按钮，可通过配置1和2来展示，如果不显示，则closeBtn: 0
             title: false, //不显示标题
             content: $('.photo-container'), //弹窗主要内容，$('.photo-container')即卖家秀照片展示容器的 Swiper滑动展示化 内容，
                // 注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
             area:[width+'px', height+'px'],// 宽高,在默认状态下，layer是宽高都自适应的，但当你只想定义宽度时，你可以area: '500px'，高度仍然是自适应的。当你宽高都要定义时，你可以area: ['500px', '300px']
             cancel: function(){    //右上角关闭按钮触发的回调,
                 // 该回调携带两个参数，分别为：当前层索引参数（index）、当前层的DOM对象（layero），默认会自动触发关闭。如果不想关闭，return false即可
             }
             });
        },
        map : function(obj){
            var title = $(obj).html();//获取当前元素，即地址的值（内容）

            var width = window.innerWidth;
            var height = window.innerHeight;
            //弹出即全屏
            var index = layer.open({
                type: 1,
                title: [title, 'font-size:13px;'],
                content: $('#map'),
                area:[width+'px', height+'px'],
                maxmin: false
            });

            if(mapLoad){
                // 百度地图API功能
                var map = new BMap.Map("map");
                var point = new BMap.Point($(obj).attr('lng'), $(obj).attr('lat'));
                map.centerAndZoom(point, 15);
                map.enableScrollWheelZoom(true);//开启鼠标滚轮缩放

                // 编写自定义函数,创建标注
                function addMarker(point){
                    var marker = new BMap.Marker(point);
                    var label = new BMap.Label(title,{offset:new BMap.Size(20,-10)});
                    marker.setLabel(label);
                    map.addOverlay(marker);
                }
                addMarker(point);

                mapLoad = false;
            }
        },
        dzOpt : function(id){
            var data;
            $.ajax({
                url: URL.dz+"/"+id,
                type: "POST",
                dataType: "json",
                async: false,
                contentType: "application/json",
                success: function (msg) {
                    if(msg.success){
                        data = msg.data;
                    }else{
                        alert(msg.message);
                    }
                }
            });

            return data;
        },
        bindEvent : function () {//function()执行时由showInfo自动触发
            var _self = this;//局部变量_self绑定this（最近的元素，即showInfo,因为bindEvent只是方法)
            var showSwiper = new Swiper('.promo-store-container', {//初始化Swiper容器,'.promo-store-container'是类选择器，选择展示门店的图片的div元素作为容器
                pagination : '.promo-store-pagination',//分页器，指示slide的数量和当前活动的slide
                paginationType : 'bullets', //分页器样式类型，可选‘bullets’ 圆点（默认）,‘fraction’分式 ,‘progress’ 进度条,‘custom’ 自定义
                autoHeight: true, //自动高度。设置为true时，wrapper和container会随着当前slide的高度而发生变化。
                width : window.innerWidth,//强制Swiper的宽度，当你的Swiper在隐藏状态下初始化时才用得上。这个参数会使自适应失效。
                //innerheight	返回窗口的文档显示区的高度。
                // innerwidth	返回窗口的文档显示区的宽度。
            });
            $("#address").click(function(){ //绑定地址点击触发 百度地图API功能
                var lng = $(this).attr('lng');
                var lat = $(this).attr('lat');
                if(lng.length>0&&lat.length>0){
                    _self.map(this);//根据经纬度触发 百度地图API功能
                }
            });
            //卖家秀点赞事件
            $('.commentDiv').unbind("click").click(function(){
                if(showId.length==0){
                    return false;
                }
                layer.load(2, {
                    shade: [0.1,'#555'],
                    time:100000
                });
                var data = _self.dzOpt(showId);
                if(data != null && typeof(data) != 'undefined'){
                    if(data.hasUserPlused){
                        $(this).find('h4').addClass('djsBoxDivRightColor');
                        $(this).find('b').addClass('djsBoxDivRightB');
                    }else{
                        $(this).find('h4').removeClass('djsBoxDivRightColor');
                        $(this).find('b').removeClass('djsBoxDivRightB');
                    }
                    if(data.dzNum >= 0 && data.dzNum < 10000){
                        $(this).find("em").html(data.dzNum);
                    }else{
                        $(this).find("em").html('10000+');
                    }
                }
                layer.closeAll('loading');
            });
        },
        init : function(){
            var _self = this;//设置showInfo元素中的局部变量_self为当前showInfo元素,以便于对自己自行一系列操作
            _self.bindEvent();//执行showInfo元素的bindEvent()方法
        }
    };
    window.ShowInfoObj = showInfo;  //window. 设置页面全局变量 ShowInfoObj
    showInfo.init();   //执行function()方法里 局部变量showInfo 的 init()方法
});
