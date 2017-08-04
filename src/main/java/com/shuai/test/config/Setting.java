package com.shuai.test.config;

import net.showcoo.enums.TimeTypeEnum;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Title: Setting.java
 * 
 * @Package net.showcoo.entity
 * 
 * @Description: TODO(系统设置)
 * 
 * @author ~(≧▽≦)/~ duerlatter@vip.qq.com
 * 
 * @date 2015-06-09 下午16:11:09
 * 
 * @version V1.0
 * 
 * @Signature 怒发冲冠，凭阑处、潇潇雨歇。抬望眼、仰天长啸，壮怀激烈。三十功名尘与土，八千里路云和月。莫等闲，白了少年头，空悲切。
 *            靖康耻，犹未雪；臣子恨，何时灭。驾长车，踏破贺兰山缺。壮志饥餐胡虏肉，笑谈渴饮匈奴血。待从头、收拾旧山河，朝天阙。
 */
public class Setting implements Serializable {

    private static final long serialVersionUID = -4200960899970787888L;

    /**
     * 静态化存放位置
     */
    public enum StaticPosition {

        /** 本地 */
        local,

        /** FTP */
        net
    }

    /**
     * 小数位精确方式
     */
    public enum RoundType {

        /** 四舍五入 */
        roundHalfUp,

        /** 向上取整 */
        roundUp,

        /** 向下取整 */
        roundDown
    }

    /**
     * 验证码类型
     */
    public enum CaptchaType {

        /** 会员登录 */
        memberLogin,

        /** 经销商登录 */
        distributorLogin,

        /** 门店登录 */
        storeLogin,

        /** 会员注册 */
        memberRegister,

        /** 后台登录 */
        adminLogin,

        /** 地推人员登录 */
        collectorLogin,

        /** 经销商找回密码 */
        distributorFindPassword,

        /** 经销商重置密码 */
        distributorResetPassword,

        /** 门店找回密码 */
        storeFindPassword,

        /** 门店重置密码 */
        storeResetPassword,

        /** 找回密码 */
        findPassword,

        /** 重置密码 */
        resetPassword,

        /** 车小哥登录 **/
        chexiaogeLogin,

        /** 车小哥找回密码 */
        chexiaogeFindPassword,

        /** 车小哥获取密码 */
        chexiaogeGetPassword,

        /** 车小哥注册 **/
        chexiaogeRegister,

        /** 车小哥重置密码 **/
        chexiaogeResetPassword,

        /** 其它 */
        other
    }

    /**
     * 账号锁定类型
     */
    public enum AccountLockType {

        /** 会员 */
        member,

        /** 管理员 */
        admin,

        /** 经销商 */
        distributor,

        /** 店铺 */
        store,

        /** 地推人员 */
        collector,

        /** 车小哥 **/
        chexiaoge
    }

    /**
     * 评论权限
     */
    public enum ReviewAuthority {

        /** 任何访问者 */
        anyone,

        /** 注册会员 */
        member

    }

    /**
     * 库存分配时间点
     */
    public enum StockAllocationTime {

        /** 下订单 */
        order,

        /** 订单支付 */
        payment,

        /** 订单发货 */
        ship
    }

    /** 缓存名称 */
    public static final String CACHE_NAME = "setting_tiger_" + Setting.class.getName();

    /** 缓存Key */
    public static final Integer CACHE_KEY = 0;

    /** 分隔符 */
    private static final String SEPARATOR = ",";

    /** 网站名称 */
    private String siteName;

    /** 网站网址 */
    private String siteUrl;

    /** 微信网址 */
    private String weChatSiteUrl;

    /** 微信AppId */
    private String weChatAppId;

    /** 微信AppSecret */
    private String weChatAppSecret;

    /** 静态资源地址 */
    private String staticPath;

    /** 热门搜索 */
    private String hotSearch;

    /** app热门搜索 */
    private String appHotSearch;

    /** 联系地址 */
    private String address;

    /** 联系电话 */
    private String phone;

    /** 邮政编码 */
    private String zipCode;

    /** E-mail */
    private String email;

    /** 备案编号 */
    private String certtext;

    /** 是否网站开启 */
    private Boolean isSiteEnabled;

    /** 网站关闭消息 */
    private String siteCloseMessage;

    /** 默认商品图片(大) */
    private String defaultLargeProductImage;

    /** 默认商品图片(小) */
    private String defaultMediumProductImage;

    /** 默认缩略图 */
    private String defaultThumbnailProductImage;

    /** 价格精确位数 */
    private Integer priceScale;

    /** 价格精确方式 */
    private RoundType priceRoundType;

    /** 是否开放注册 */
    private Boolean isRegisterEnabled;

    /** 密码最小长度 */
    private Integer passwordMinLength;

    /** 密码最大长度 */
    private Integer passwordMaxLength;

    /** 注册协议 */
    private String registerAgreement;

    /** 是否允许E-mail登录 */
    private Boolean isEmailLogin;

    /** 验证码类型 */
    private CaptchaType[] captchaTypes;

    /** 账号锁定类型 */
    private AccountLockType[] accountLockTypes;

    /** 连续登录失败最大次数 */
    private Integer accountLockCount;

    /** 自动解锁时间 */
    private Integer accountLockTime;

    /** 手机安全密匙长度 */
    private Integer mobileSafeKeyLength;

    /** 手机安全密匙有效时间 */
    private Integer mobileSafeKeyExpiryTime;

    /** 安全密匙有效时间 */
    private Integer safeKeyExpiryTime;

    /** 上传文件最大限制 */
    private Integer uploadMaxSize;

    /** 允许上传图片扩展名 */
    private String uploadImageExtension;

    /** 允许上传Flash扩展名 */
    private String uploadFlashExtension;

    /** 允许上传媒体扩展名 */
    private String uploadMediaExtension;

    /** 允许上传文件扩展名 */
    private String uploadFileExtension;

    /** 图片上传路径 */
    private String imageUploadPath;

    /** Flash上传路径 */
    private String flashUploadPath;

    /** 媒体上传路径 */
    private String mediaUploadPath;

    /** 文件上传路径 */
    private String fileUploadPath;

    /** 货币符号 */
    private String currencySign;

    /** 货币单位 */
    private String currencyUnit;

    /** 库存警告数 */
    private Integer stockAlertCount;

    /** 库存分配时间点 */
    private StockAllocationTime stockAllocationTime;

    /** 是否开启开发模式 */
    private Boolean isDevelopmentEnabled;

    /** 静态化存放位置 */
    private StaticPosition staticPosition;

    /** 静态化存放路径前缀 */
    private String staticPathPrefix;

    /** Cookie路径 */
    private String cookiePath;

    /** Cookie作用域 */
    private String cookieDomain;

    /** 经销商提现手续费比率 **/
    private BigDecimal distributorRate;

    /** 门店提现手续费比率 **/
    private BigDecimal storeRate;

    /** 默认积分换算比例 */
    private Double defaultPointScale;

    /** 默认市场价换算比例 */
    private Double defaultMarketPriceScale;

    /** 是否前台显示市场价 */
    private Boolean isShowMarketPrice;

    /** 是否开启发票功能 */
    private Boolean isInvoiceEnabled;

    /** 是否开启含税价 */
    private Boolean isTaxPriceEnabled;

    /** 税率 */
    private Double taxRate;

    /** 积分失效时间. 单位:月 */
    private Integer pointExpire;

    /** 进货积分上限 */
    private Long stockInPointMax;

    /** 进货返利上限 */
    private Long stockInRebateMax;

    /** 销售积分上限 */
    private Long stockOutPointMax;

    /** 销售返利上限 */
    private Long stockOutRebateMax;

    /** 门店库存初始化有效期 单位：小时 */
    private Long stockInitDays;

    /** 经销商库存初始化有效期 单位：小时 */
    private Integer distributoryInitStockHours;

    /** 经销商新建退货单来源数据有效期 单位：天 */
    private Integer distributorReturnsDays;

    /** 安卓自动升级版本号 */
    private String apkversion;

    /** 积分返利结算时间 */
    private TimeTypeEnum pointRebateTimeType;

    /**
     * 获取网站名称
     * 
     * @return 网站名称
     */
    @NotEmpty
    @Length(max = 200)
    public String getSiteName() {
        return siteName;
    }

    /**
     * 设置网站名称
     * 
     * @param siteName
     *            网站名称
     */
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    /**
     * 获取网站网址
     * 
     * @return 网站网址
     */
    @NotEmpty
    @Length(max = 200)
    public String getSiteUrl() {
        return siteUrl;
    }

    /**
     * 设置网站网址
     * 
     * @param siteUrl
     *            网站网址
     */
    public void setSiteUrl(String siteUrl) {
        this.siteUrl = StringUtils.removeEnd(siteUrl, "/");
    }

    /**
     * 获取微信网址
     * 
     * @return
     */
    @NotEmpty
    @Length(max = 200)
    public String getWeChatSiteUrl() {
        return weChatSiteUrl;
    }

    /**
     * 设置微信网址
     * 
     * @param weChatSiteUrl
     */
    public void setWeChatSiteUrl(String weChatSiteUrl) {
        this.weChatSiteUrl = StringUtils.removeEnd(weChatSiteUrl, "/");
    }

    /**
     * 获取微信appid
     * 
     * @return
     */
    @NotEmpty
    @Length(max = 200)
    public String getWeChatAppId() {
        return weChatAppId;
    }

    /**
     * 设置微信appid
     * 
     * @param weChatAppId
     */
    public void setWeChatAppId(String weChatAppId) {
        this.weChatAppId = weChatAppId;
    }

    /**
     * 获取微信AppSecret
     * 
     * @return
     */
    @NotEmpty
    @Length(max = 200)
    public String getWeChatAppSecret() {
        return weChatAppSecret;
    }

    /**
     * 设置微信AppSecret
     * 
     * @param weChatAppSecret
     */
    public void setWeChatAppSecret(String weChatAppSecret) {
        this.weChatAppSecret = weChatAppSecret;
    }

    /**
     * 获取静态资源网址
     * 
     * @return 静态资源网址
     */
    @NotEmpty
    @Length(max = 200)
    public String getStaticPath() {
        return staticPath;
    }

    /**
     * 设置静态资源网址
     * 
     * @param staticPath
     *            静态资源网址
     */
    public void setStaticPath(String staticPath) {
        this.staticPath = staticPath;
    }

    /**
     * 获取热门搜索
     * 
     * @return 热门搜索
     */
    @Length(max = 200)
    public String getHotSearch() {
        return hotSearch;
    }

    /**
     * 设置热门搜索
     * 
     * @param hotSearch
     *            热门搜索
     */
    public void setHotSearch(String hotSearch) {
        if (hotSearch != null) {
            hotSearch = hotSearch.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "");
        }
        this.hotSearch = hotSearch;
    }

    /**
     * 获取app热门搜索
     * 
     * @return app热门搜索
     */
    public String getAppHotSearch() {
        return appHotSearch;
    }

    /**
     * 设置app热门搜索
     * 
     * @param appHotSearch
     */
    public void setAppHotSearch(String appHotSearch) {
        this.appHotSearch = appHotSearch;
    }

    /**
     * 获取联系地址
     * 
     * @return 联系地址
     */
    @Length(max = 200)
    public String getAddress() {
        return address;
    }

    /**
     * 设置联系地址
     * 
     * @param address
     *            联系地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取联系电话
     * 
     * @return 联系电话
     */
    @Length(max = 200)
    public String getPhone() {
        return phone;
    }

    /**
     * 设置联系电话
     * 
     * @param phone
     *            联系电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取邮政编码
     * 
     * @return 邮政编码
     */
    @Length(max = 200)
    public String getZipCode() {
        return zipCode;
    }

    /**
     * 设置邮政编码
     * 
     * @param zipCode
     *            邮政编码
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * 获取E-mail
     * 
     * @return E-mail
     */
    @Email
    @Length(max = 200)
    public String getEmail() {
        return email;
    }

    /**
     * 设置E-mail
     * 
     * @param email
     *            E-mail
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取备案编号
     * 
     * @return 备案编号
     */
    @Length(max = 200)
    public String getCerttext() {
        return certtext;
    }

    /**
     * 设置备案编号
     * 
     * @param certtext
     *            备案编号
     */
    public void setCerttext(String certtext) {
        this.certtext = certtext;
    }

    /**
     * 获取是否网站开启
     * 
     * @return 是否网站开启
     */
    @NotNull
    public Boolean getIsSiteEnabled() {
        return isSiteEnabled;
    }

    /**
     * 设置是否网站开启
     * 
     * @param isSiteEnabled
     *            是否网站开启
     */
    public void setIsSiteEnabled(Boolean isSiteEnabled) {
        this.isSiteEnabled = isSiteEnabled;
    }

    /**
     * 获取网站关闭消息
     * 
     * @return 网站关闭消息
     */
    @NotEmpty
    public String getSiteCloseMessage() {
        return siteCloseMessage;
    }

    /**
     * 设置网站关闭消息
     * 
     * @param siteCloseMessage
     *            网站关闭消息
     */
    public void setSiteCloseMessage(String siteCloseMessage) {
        this.siteCloseMessage = siteCloseMessage;
    }

    /**
     * 获取默认商品图片(大)
     * 
     * @return 默认商品图片(大)
     */
    @NotEmpty
    @Length(max = 200)
    public String getDefaultLargeProductImage() {
        return defaultLargeProductImage;
    }

    /**
     * 设置默认商品图片(大)
     * 
     * @param defaultLargeProductImage
     *            默认商品图片(大)
     */
    public void setDefaultLargeProductImage(String defaultLargeProductImage) {
        this.defaultLargeProductImage = defaultLargeProductImage;
    }

    /**
     * 获取默认商品图片(小)
     * 
     * @return 默认商品图片(小)
     */
    @NotEmpty
    @Length(max = 200)
    public String getDefaultMediumProductImage() {
        return defaultMediumProductImage;
    }

    /**
     * 设置默认商品图片(小)
     * 
     * @param defaultMediumProductImage
     *            默认商品图片(小)
     */
    public void setDefaultMediumProductImage(String defaultMediumProductImage) {
        this.defaultMediumProductImage = defaultMediumProductImage;
    }

    /**
     * 获取默认缩略图
     * 
     * @return 默认缩略图
     */
    @NotEmpty
    @Length(max = 200)
    public String getDefaultThumbnailProductImage() {
        return defaultThumbnailProductImage;
    }

    /**
     * 设置默认缩略图
     * 
     * @param defaultThumbnailProductImage
     *            默认缩略图
     */
    public void setDefaultThumbnailProductImage(String defaultThumbnailProductImage) {
        this.defaultThumbnailProductImage = defaultThumbnailProductImage;
    }

    /**
     * 获取价格精确位数
     * 
     * @return 价格精确位数
     */
    @NotNull
    @Min(0)
    @Max(3)
    public Integer getPriceScale() {
        return priceScale;
    }

    /**
     * 设置价格精确位数
     * 
     * @param priceScale
     *            价格精确位数
     */
    public void setPriceScale(Integer priceScale) {
        this.priceScale = priceScale;
    }

    /**
     * 获取价格精确方式
     * 
     * @return 价格精确方式
     */
    @NotNull
    public RoundType getPriceRoundType() {
        return priceRoundType;
    }

    /**
     * 设置价格精确方式
     * 
     * @param priceRoundType
     *            价格精确方式
     */
    public void setPriceRoundType(RoundType priceRoundType) {
        this.priceRoundType = priceRoundType;
    }

    /**
     * 获取是否开放注册
     * 
     * @return 是否开放注册
     */
    @NotNull
    public Boolean getIsRegisterEnabled() {
        return isRegisterEnabled;
    }

    /**
     * 设置是否开放注册
     * 
     * @param isRegisterEnabled
     *            是否开放注册
     */
    public void setIsRegisterEnabled(Boolean isRegisterEnabled) {
        this.isRegisterEnabled = isRegisterEnabled;
    }

    /**
     * 获取密码最小长度
     * 
     * @return 密码最小长度
     */
    @NotNull
    @Min(1)
    @Max(117)
    public Integer getPasswordMinLength() {
        return passwordMinLength;
    }

    /**
     * 设置密码最小长度
     * 
     * @param passwordMinLength
     *            密码最小长度
     */
    public void setPasswordMinLength(Integer passwordMinLength) {
        this.passwordMinLength = passwordMinLength;
    }

    /**
     * 获取密码最大长度
     * 
     * @return 密码最大长度
     */
    @NotNull
    @Min(1)
    @Max(117)
    public Integer getPasswordMaxLength() {
        return passwordMaxLength;
    }

    /**
     * 设置密码最大长度
     * 
     * @param passwordMaxLength
     *            密码最大长度
     */
    public void setPasswordMaxLength(Integer passwordMaxLength) {
        this.passwordMaxLength = passwordMaxLength;
    }

    /**
     * 获取注册协议
     * 
     * @return 注册协议
     */
    @NotEmpty
    public String getRegisterAgreement() {
        return registerAgreement;
    }

    /**
     * 设置注册协议
     * 
     * @param registerAgreement
     *            注册协议
     */
    public void setRegisterAgreement(String registerAgreement) {
        this.registerAgreement = registerAgreement;
    }

    /**
     * 获取是否允许E-mail登录
     * 
     * @return 是否允许E-mail登录
     */
    @NotNull
    public Boolean getIsEmailLogin() {
        return isEmailLogin;
    }

    /**
     * 设置是否允许E-mail登录
     * 
     * @param isEmailLogin
     *            是否允许E-mail登录
     */
    public void setIsEmailLogin(Boolean isEmailLogin) {
        this.isEmailLogin = isEmailLogin;
    }

    /**
     * 获取验证码类型
     * 
     * @return 验证码类型
     */
    public CaptchaType[] getCaptchaTypes() {
        return captchaTypes;
    }

    /**
     * 设置验证码类型
     * 
     * @param captchaTypes
     *            验证码类型
     */
    public void setCaptchaTypes(CaptchaType[] captchaTypes) {
        this.captchaTypes = captchaTypes;
    }

    /**
     * 获取账号锁定类型
     * 
     * @return 账号锁定类型
     */
    public AccountLockType[] getAccountLockTypes() {
        return accountLockTypes;
    }

    /**
     * 设置账号锁定类型
     * 
     * @param accountLockTypes
     *            账号锁定类型
     */
    public void setAccountLockTypes(AccountLockType[] accountLockTypes) {
        this.accountLockTypes = accountLockTypes;
    }

    /**
     * 获取连续登录失败最大次数
     * 
     * @return 连续登录失败最大次数
     */
    @NotNull
    @Min(1)
    public Integer getAccountLockCount() {
        return accountLockCount;
    }

    /**
     * 设置连续登录失败最大次数
     * 
     * @param accountLockCount
     *            连续登录失败最大次数
     */
    public void setAccountLockCount(Integer accountLockCount) {
        this.accountLockCount = accountLockCount;
    }

    /**
     * 获取自动解锁时间
     * 
     * @return 自动解锁时间
     */
    @NotNull
    @Min(0)
    public Integer getAccountLockTime() {
        return accountLockTime;
    }

    /**
     * 设置自动解锁时间
     * 
     * @param accountLockTime
     *            自动解锁时间
     */
    public void setAccountLockTime(Integer accountLockTime) {
        this.accountLockTime = accountLockTime;
    }

    /**
     * 获取手机秘钥长度 最小长度为4
     * 
     * @return
     */
    @NotNull
    @Min(4)
    public Integer getMobileSafeKeyLength() {
        return mobileSafeKeyLength;
    }

    /**
     * 设置手机秘钥长度
     * 
     * @param mobileSafeKeyLength
     */
    public void setMobileSafeKeyLength(Integer mobileSafeKeyLength) {
        this.mobileSafeKeyLength = mobileSafeKeyLength;
    }

    /**
     * 获取手机安全密匙有效时间
     * 
     * @return 手机安全密匙有效时间
     */
    @NotNull
    @Min(0)
    public Integer getMobileSafeKeyExpiryTime() {
        return mobileSafeKeyExpiryTime;
    }

    /**
     * 设置手机安全密匙有效时间
     * 
     * @param safeKeyExpiryTime
     *            手机安全密匙有效时间
     */
    public void setMobileSafeKeyExpiryTime(Integer mobileSafeKeyExpiryTime) {
        this.mobileSafeKeyExpiryTime = mobileSafeKeyExpiryTime;
    }

    /**
     * 获取安全密匙有效时间
     * 
     * @return 安全密匙有效时间
     */
    @NotNull
    @Min(0)
    public Integer getSafeKeyExpiryTime() {
        return safeKeyExpiryTime;
    }

    /**
     * 设置安全密匙有效时间
     * 
     * @param safeKeyExpiryTime
     *            安全密匙有效时间
     */
    public void setSafeKeyExpiryTime(Integer safeKeyExpiryTime) {
        this.safeKeyExpiryTime = safeKeyExpiryTime;
    }

    /**
     * 获取上传文件最大限制
     * 
     * @return 上传文件最大限制
     */
    @NotNull
    @Min(0)
    public Integer getUploadMaxSize() {
        return uploadMaxSize;
    }

    /**
     * 设置上传文件最大限制
     * 
     * @param uploadMaxSize
     *            上传文件最大限制
     */
    public void setUploadMaxSize(Integer uploadMaxSize) {
        this.uploadMaxSize = uploadMaxSize;
    }

    /**
     * 获取允许上传图片扩展名
     * 
     * @return 允许上传图片扩展名
     */
    @Length(max = 200)
    public String getUploadImageExtension() {
        return uploadImageExtension;
    }

    /**
     * 设置允许上传图片扩展名
     * 
     * @param uploadImageExtension
     *            允许上传图片扩展名
     */
    public void setUploadImageExtension(String uploadImageExtension) {
        if (uploadImageExtension != null) {
            uploadImageExtension = uploadImageExtension.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "").toLowerCase();
        }
        this.uploadImageExtension = uploadImageExtension;
    }

    /**
     * 获取允许上传Flash扩展名
     * 
     * @return 允许上传Flash扩展名
     */
    @Length(max = 200)
    public String getUploadFlashExtension() {
        return uploadFlashExtension;
    }

    /**
     * 设置允许上传Flash扩展名
     * 
     * @param uploadFlashExtension
     *            允许上传Flash扩展名
     */
    public void setUploadFlashExtension(String uploadFlashExtension) {
        if (uploadFlashExtension != null) {
            uploadFlashExtension = uploadFlashExtension.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "").toLowerCase();
        }
        this.uploadFlashExtension = uploadFlashExtension;
    }

    /**
     * 获取允许上传媒体扩展名
     * 
     * @return 允许上传媒体扩展名
     */
    @Length(max = 200)
    public String getUploadMediaExtension() {
        return uploadMediaExtension;
    }

    /**
     * 设置允许上传媒体扩展名
     * 
     * @param uploadMediaExtension
     *            允许上传媒体扩展名
     */
    public void setUploadMediaExtension(String uploadMediaExtension) {
        if (uploadMediaExtension != null) {
            uploadMediaExtension = uploadMediaExtension.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "").toLowerCase();
        }
        this.uploadMediaExtension = uploadMediaExtension;
    }

    /**
     * 获取允许上传文件扩展名
     * 
     * @return 允许上传文件扩展名
     */
    @Length(max = 200)
    public String getUploadFileExtension() {
        return uploadFileExtension;
    }

    /**
     * 设置允许上传文件扩展名
     * 
     * @param uploadFileExtension
     *            允许上传文件扩展名
     */
    public void setUploadFileExtension(String uploadFileExtension) {
        if (uploadFileExtension != null) {
            uploadFileExtension = uploadFileExtension.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "").toLowerCase();
        }
        this.uploadFileExtension = uploadFileExtension;
    }

    /**
     * 获取图片上传路径
     * 
     * @return 图片上传路径
     */
    @NotEmpty
    @Length(max = 200)
    public String getImageUploadPath() {
        return imageUploadPath;
    }

    /**
     * 设置图片上传路径
     * 
     * @param imageUploadPath
     *            图片上传路径
     */
    public void setImageUploadPath(String imageUploadPath) {
        if (imageUploadPath != null) {
            if (!imageUploadPath.startsWith("/")) {
                imageUploadPath = "/" + imageUploadPath;
            }
            if (!imageUploadPath.endsWith("/")) {
                imageUploadPath += "/";
            }
        }
        this.imageUploadPath = imageUploadPath;
    }

    /**
     * 获取Flash上传路径
     * 
     * @return Flash上传路径
     */
    @NotEmpty
    @Length(max = 200)
    public String getFlashUploadPath() {
        return flashUploadPath;
    }

    /**
     * 设置Flash上传路径
     * 
     * @param flashUploadPath
     *            Flash上传路径
     */
    public void setFlashUploadPath(String flashUploadPath) {
        if (flashUploadPath != null) {
            if (!flashUploadPath.startsWith("/")) {
                flashUploadPath = "/" + flashUploadPath;
            }
            if (!flashUploadPath.endsWith("/")) {
                flashUploadPath += "/";
            }
        }
        this.flashUploadPath = flashUploadPath;
    }

    /**
     * 获取媒体上传路径
     * 
     * @return 媒体上传路径
     */
    @NotEmpty
    @Length(max = 200)
    public String getMediaUploadPath() {
        return mediaUploadPath;
    }

    /**
     * 设置媒体上传路径
     * 
     * @param mediaUploadPath
     *            媒体上传路径
     */
    public void setMediaUploadPath(String mediaUploadPath) {
        if (mediaUploadPath != null) {
            if (!mediaUploadPath.startsWith("/")) {
                mediaUploadPath = "/" + mediaUploadPath;
            }
            if (!mediaUploadPath.endsWith("/")) {
                mediaUploadPath += "/";
            }
        }
        this.mediaUploadPath = mediaUploadPath;
    }

    /**
     * 获取文件上传路径
     * 
     * @return 文件上传路径
     */
    @NotEmpty
    @Length(max = 200)
    public String getFileUploadPath() {
        return fileUploadPath;
    }

    /**
     * 设置文件上传路径
     * 
     * @param fileUploadPath
     *            文件上传路径
     */
    public void setFileUploadPath(String fileUploadPath) {
        if (fileUploadPath != null) {
            if (!fileUploadPath.startsWith("/")) {
                fileUploadPath = "/" + fileUploadPath;
            }
            if (!fileUploadPath.endsWith("/")) {
                fileUploadPath += "/";
            }
        }
        this.fileUploadPath = fileUploadPath;
    }

    /**
     * 获取货币符号
     * 
     * @return 货币符号
     */
    @NotEmpty
    @Length(max = 200)
    public String getCurrencySign() {
        return currencySign;
    }

    /**
     * 设置货币符号
     * 
     * @param currencySign
     *            货币符号
     */
    public void setCurrencySign(String currencySign) {
        this.currencySign = currencySign;
    }

    /**
     * 获取货币单位
     * 
     * @return 货币单位
     */
    @NotEmpty
    @Length(max = 200)
    public String getCurrencyUnit() {
        return currencyUnit;
    }

    /**
     * 设置货币单位
     * 
     * @param currencyUnit
     *            货币单位
     */
    public void setCurrencyUnit(String currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    /**
     * 获取库存警告数
     * 
     * @return 库存警告数
     */
    @NotNull
    @Min(0)
    public Integer getStockAlertCount() {
        return stockAlertCount;
    }

    /**
     * 设置库存警告数
     * 
     * @param stockAlertCount
     *            库存警告数
     */
    public void setStockAlertCount(Integer stockAlertCount) {
        this.stockAlertCount = stockAlertCount;
    }

    /**
     * 获取库存分配时间点
     * 
     * @return 库存分配时间点
     */
    @NotNull
    public StockAllocationTime getStockAllocationTime() {
        return stockAllocationTime;
    }

    /**
     * 设置库存分配时间点
     * 
     * @param stockAllocationTime
     *            库存分配时间点
     */
    public void setStockAllocationTime(StockAllocationTime stockAllocationTime) {
        this.stockAllocationTime = stockAllocationTime;
    }

    /**
     * 获取是否开启开发模式
     * 
     * @return 是否开启开发模式
     */
    @NotNull
    public Boolean getIsDevelopmentEnabled() {
        return isDevelopmentEnabled;
    }

    /**
     * 设置是否开启开发模式
     * 
     * @param isDevelopmentEnabled
     *            是否开启开发模式
     */
    public void setIsDevelopmentEnabled(Boolean isDevelopmentEnabled) {
        this.isDevelopmentEnabled = isDevelopmentEnabled;
    }

    /**
     * 获取静态化存放位置
     * 
     * @return
     */
    @NotNull
    public StaticPosition getStaticPosition() {
        return staticPosition;
    }

    /**
     * 设置静态化存放位置
     * 
     * @param staticPosition
     */
    public void setStaticPosition(StaticPosition staticPosition) {
        this.staticPosition = staticPosition;
    }

    /**
     * 获取静态化路径前缀
     * 
     * @return
     */
    @NotNull
    public String getStaticPathPrefix() {
        if (StringUtils.isNotEmpty(staticPathPrefix) && staticPathPrefix.endsWith("/"))
            staticPathPrefix = staticPathPrefix.substring(0, staticPathPrefix.length() - 1);
        return staticPathPrefix;
    }

    /**
     * 设置静态化路径前缀
     * 
     * @param staticPathPrefix
     */
    public void setStaticPathPrefix(String staticPathPrefix) {
        this.staticPathPrefix = staticPathPrefix;
    }

    /**
     * 获取Cookie路径
     * 
     * @return Cookie路径
     */
    @NotEmpty
    @Length(max = 200)
    public String getCookiePath() {
        return cookiePath;
    }

    /**
     * 设置Cookie路径
     * 
     * @param cookiePath
     *            Cookie路径
     */
    public void setCookiePath(String cookiePath) {
        if (cookiePath != null && !cookiePath.endsWith("/")) {
            cookiePath += "/";
        }
        this.cookiePath = cookiePath;
    }

    /**
     * 获取Cookie作用域
     * 
     * @return Cookie作用域
     */
    @Length(max = 200)
    public String getCookieDomain() {
        return cookieDomain;
    }

    /**
     * 设置Cookie作用域
     * 
     * @param cookieDomain
     *            Cookie作用域
     */
    public void setCookieDomain(String cookieDomain) {
        this.cookieDomain = cookieDomain;
    }

    /**
     * 获得经销商提现手续费比率
     * 
     * @return
     */
    @Min(0)
    @Digits(integer = 12, fraction = 3)
    @Column(nullable = false, precision = 21, scale = 6)
    public BigDecimal getDistributorRate() {
        return distributorRate;
    }

    /**
     * 设置经销商提现手续费比率
     * 
     * @param distributorRate
     */
    public void setDistributorRate(BigDecimal distributorRate) {
        this.distributorRate = distributorRate;
    }

    /**
     * 获得门店提现手续费比率
     * 
     * @return
     */
    @Min(0)
    @Digits(integer = 12, fraction = 3)
    @Column(nullable = false, precision = 21, scale = 6)
    public BigDecimal getStoreRate() {
        return storeRate;
    }

    /**
     * 设置门店提现手续费比率
     * 
     * @param storeRate
     */
    public void setStoreRate(BigDecimal storeRate) {
        this.storeRate = storeRate;
    }

    /**
     * 获取热门搜索关键词
     * 
     * @return 热门搜索关键词
     */
    public String[] getHotSearches() {
        return StringUtils.split(hotSearch, SEPARATOR);
    }

    /**
     * 获取允许上传图片扩展名
     * 
     * @return 允许上传图片扩展名
     */
    public String[] getUploadImageExtensions() {
        return StringUtils.split(uploadImageExtension, SEPARATOR);
    }

    /**
     * 获取允许上传Flash扩展名
     * 
     * @return 允许上传Flash扩展名
     */
    public String[] getUploadFlashExtensions() {
        return StringUtils.split(uploadFlashExtension, SEPARATOR);
    }

    /**
     * 获取允许上传媒体扩展名
     * 
     * @return 允许上传媒体扩展名
     */
    public String[] getUploadMediaExtensions() {
        return StringUtils.split(uploadMediaExtension, SEPARATOR);
    }

    /**
     * 获取允许上传文件扩展名
     * 
     * @return 允许上传文件扩展名
     */
    public String[] getUploadFileExtensions() {
        return StringUtils.split(uploadFileExtension, SEPARATOR);
    }

    /**
     * 设置精度
     * 
     * @param amount
     *            数值
     * @return 数值
     */
    public BigDecimal setScale(BigDecimal amount) {
        if (amount == null) {
            return null;
        }
        int roundingMode;
        if (getPriceRoundType() == RoundType.roundUp) {
            roundingMode = BigDecimal.ROUND_UP;
        } else if (getPriceRoundType() == RoundType.roundDown) {
            roundingMode = BigDecimal.ROUND_DOWN;
        } else {
            roundingMode = BigDecimal.ROUND_HALF_UP;
        }
        return amount.setScale(getPriceScale(), roundingMode);
    }

    /**
     * 获取默认积分换算比例
     * 
     * @return 默认积分换算比例
     */
    @NotNull
    @Min(0)
    @Digits(integer = 3, fraction = 3)
    public Double getDefaultPointScale() {
        return defaultPointScale;
    }

    /**
     * 设置默认积分换算比例
     * 
     * @param defaultPointScale
     *            默认积分换算比例
     */
    public void setDefaultPointScale(Double defaultPointScale) {
        this.defaultPointScale = defaultPointScale;
    }

    /**
     * 获取默认市场价换算比例
     * 
     * @return 默认市场价换算比例
     */
    @NotNull
    @Min(0)
    @Digits(integer = 3, fraction = 3)
    public Double getDefaultMarketPriceScale() {
        return defaultMarketPriceScale;
    }

    /**
     * 设置默认市场价换算比例
     * 
     * @param defaultMarketPriceScale
     *            默认市场价换算比例
     */
    public void setDefaultMarketPriceScale(Double defaultMarketPriceScale) {
        this.defaultMarketPriceScale = defaultMarketPriceScale;
    }

    /**
     * 获取是否前台显示市场价
     * 
     * @return 是否前台显示市场价
     */
    @NotNull
    public Boolean getIsShowMarketPrice() {
        return isShowMarketPrice;
    }

    /**
     * 设置是否前台显示市场价
     * 
     * @param isShowMarketPrice
     *            是否前台显示市场价
     */
    public void setIsShowMarketPrice(Boolean isShowMarketPrice) {
        this.isShowMarketPrice = isShowMarketPrice;
    }

    /**
     * 获取是否开启发票功能
     * 
     * @return 是否开启发票功能
     */
    @NotNull
    public Boolean getIsInvoiceEnabled() {
        return isInvoiceEnabled;
    }

    /**
     * 设置是否开启发票功能
     * 
     * @param isInvoiceEnabled
     *            是否开启发票功能
     */
    public void setIsInvoiceEnabled(Boolean isInvoiceEnabled) {
        this.isInvoiceEnabled = isInvoiceEnabled;
    }

    /**
     * 获取是否开启含税价
     * 
     * @return 是否开启含税价
     */
    @NotNull
    public Boolean getIsTaxPriceEnabled() {
        return isTaxPriceEnabled;
    }

    /**
     * 设置是否开启含税价
     * 
     * @param isTaxPriceEnabled
     *            是否开启含税价
     */
    public void setIsTaxPriceEnabled(Boolean isTaxPriceEnabled) {
        this.isTaxPriceEnabled = isTaxPriceEnabled;
    }

    /**
     * 获取税率
     * 
     * @return 税率
     */
    @Min(0)
    @Digits(integer = 3, fraction = 3)
    public Double getTaxRate() {
        return taxRate;
    }

    /**
     * 设置税率
     * 
     * @param taxRate
     *            税率
     */
    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    /**
     * 获取积分失效时间. 单位:月
     * 
     * @return 积分失效时间. 单位:月
     */
    public Integer getPointExpire() {
        return pointExpire;
    }

    /**
     * 设置积分失效时间. 单位:月
     * 
     * @param pointExpire
     *            积分失效时间. 单位:月
     */
    public void setPointExpire(Integer pointExpire) {
        this.pointExpire = pointExpire;
    }

    /**
     * 获取进货积分上限
     * 
     * @return 进货积分上限
     */
    public Long getStockInPointMax() {
        return stockInPointMax;
    }

    /**
     * 设置进货积分上限
     * 
     * @param stockInPointMax
     */
    public void setStockInPointMax(Long stockInPointMax) {
        this.stockInPointMax = stockInPointMax;
    }

    /**
     * 获取进货返利上限
     * 
     * @return 进货返利上限
     */
    public Long getStockInRebateMax() {
        return stockInRebateMax;
    }

    /**
     * 设置进货返利上限
     * 
     * @param stockInRebateMax
     */
    public void setStockInRebateMax(Long stockInRebateMax) {
        this.stockInRebateMax = stockInRebateMax;
    }

    /**
     * 获取销售积分上限
     * 
     * @return 销售积分上限
     */
    public Long getStockOutPointMax() {
        return stockOutPointMax;
    }

    /**
     * 设置销售积分上限
     * 
     * @param stockOutPointMax
     */
    public void setStockOutPointMax(Long stockOutPointMax) {
        this.stockOutPointMax = stockOutPointMax;
    }

    /**
     * 获取销售返利上限
     * 
     * @return 销售返利上限
     */
    public Long getStockOutRebateMax() {
        return stockOutRebateMax;
    }

    /**
     * 设置销售返利上限
     * 
     * @param stockOutRebateMax
     */
    public void setStockOutRebateMax(Long stockOutRebateMax) {
        this.stockOutRebateMax = stockOutRebateMax;
    }

    /**
     * 获取门店初始化库存有效期 单位：小时
     * 
     * @return 门店初始化库存有效期
     */
    public Long getStockInitDays() {
        return stockInitDays;
    }

    /**
     * 设置门店初始化库存有效期 单位：小时
     * 
     * @param stock_init_days
     *            门店初始化库存有效期
     */
    public void setStockInitDays(Long stockInitDays) {
        this.stockInitDays = stockInitDays;
    }

    /**
     * 获取经销商初始化库存有效期
     * 
     * @return 经销商初始化库存有效期
     */
    public Integer getDistributoryInitStockHours() {
        return distributoryInitStockHours;
    }

    /**
     * 设置经销商初始化库存有效期
     * 
     * @param distributoryInitStockHours
     *            经销商初始化库存有效期
     */
    public void setDistributoryInitStockHours(Integer distributoryInitStockHours) {
        this.distributoryInitStockHours = distributoryInitStockHours;
    }

    /**
     * 获取经销商新建退货单来源数据有效期
     * 
     * @return 经销商新建退货单来源数据有效期
     */
    public Integer getDistributorReturnsDays() {
        return distributorReturnsDays;
    }

    /**
     * 设置经销商新建退货单来源数据有效期
     * 
     * @param distributorReturnsDays
     *            经销商新建退货单来源数据有效期
     */
    public void setDistributorReturnsDays(Integer distributorReturnsDays) {
        this.distributorReturnsDays = distributorReturnsDays;
    }

    /**
     * 获取安卓自动升级版本号
     * 
     * @return 安卓版本号
     */
    public String getApkversion() {
        return apkversion;
    }

    /**
     * 设置安卓自动升级版本号
     * 
     * @param apkversion
     */
    public void setApkversion(String apkversion) {
        this.apkversion = apkversion;
    }

    /**
     * 获取积分返利结算时间
     * 
     * @return pointRebateTimeType
     */
    public TimeTypeEnum getPointRebateTimeType() {
        if (null == pointRebateTimeType) {
            return TimeTypeEnum.quarter;
        }
        return pointRebateTimeType;
    }

    /**
     * 设置积分返利结算时间
     * 
     * @param pointRebateTimeType
     */
    public void setPointRebateTimeType(TimeTypeEnum pointRebateTimeType) {
        this.pointRebateTimeType = pointRebateTimeType;
    }

}
