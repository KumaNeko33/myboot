package com.shuai.test.tires_special_offer;

import com.zcckj.sso.service.AuthenticationService;
import net.showcoo.Page;
import net.showcoo.Pageable;
import net.showcoo.controller.admin.BaseController;
import net.showcoo.entity.*;
import net.showcoo.plugin.payment.umpay.api.util.StringUtil;
import net.showcoo.push.AndroidNotification;
import net.showcoo.push.PushClient;
import net.showcoo.push.android.AndroidCustomizedcast;
import net.showcoo.push.ios.IOSListcast;
import net.showcoo.service.*;
import net.showcoo.utils.DateUtils;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * 特价轮胎商品
 */
@Controller
@RequestMapping("/admin/specialOffer")
public class TiresSpecialOfferController extends BaseController {
    private static org.apache.commons.logging.Log logger = LogFactory.getLog(TiresSpecialOfferController.class);

    @Resource(name = "tiresServiceImpl")
    private TiresService tireService;

    @Resource(name = "brandServiceImpl")
    private BrandService brandService;

    @Resource(name = "distributorServiceImpl")
    private DistributorService distributorService;

    @Resource(name = "storeServiceImpl")
    private StoreService storeService;


    @Resource(name = "tiresSpecialOfferServiceImpl")
    private TiresSpecialOfferService tiresSpecialOfferService;

    @Resource(name = "TiresSpecialOfferBlackStoreServiceImpl")
    private TiresSpecialOfferBlackStoreService blackStoreService;

    @Resource(name = "authenticationService")
    private AuthenticationService authenticationService;

    @Value("${push.android.appKey}")
    private String androidAppKey;

    @Value("${push.android.appMasterSecret}")
    private String androidAppMasterSecret;

    @Value("${push.ios.appKey}")
    private String iosAppKey;

    @Value("${push.ios.appMasterSecret}")
    private String iosAppMasterSecret;

    @Value("${push.production.mode}")
    private String productionMode;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Date beginDate, Date endDate, String brandId, String status, Pageable pageable, ModelMap model) {
        Page<TiresSpecialOffer> tiresPage = tiresSpecialOfferService.findPage(beginDate, endDate, brandId, status, pageable);
        model.addAttribute("page", tiresPage);
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("beginDate", beginDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("brandId", brandId);
        model.addAttribute("status", status);

        return "/admin/tires_special_offer/list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(ModelMap model) {
        String defaultNum = DateUtils.format(new Date(), "yyyyMMdd");
        model.addAttribute("defaultNum", defaultNum);
        model.addAttribute("storeTypes", Store.StoreType.values());
        model.addAttribute("brands", brandService.findAll());

        return "/admin/tires_special_offer/add";
    }

    @RequestMapping("/save")
    public String save(TiresSpecialOffer tiresSpecialOffer, Long tiresId) {
        Tires tires = tireService.find(tiresId);

        Iterator it = tiresSpecialOffer.getTiresSpecialOfferPrices().iterator();
        while (it.hasNext()) {
            TiresSpecialOfferPrice tempobj = (TiresSpecialOfferPrice) it.next();
            if (null == tempobj.getIsEnabled()) {
                it.remove();
            }
            tempobj.setTiresSpecialOffer(tiresSpecialOffer);
        }
        tiresSpecialOffer.setTires(tires);

        //启用，修改上架日期
        if (tiresSpecialOffer.getIsEnabled().equals("1")) {
            tiresSpecialOffer.setGroundingDate(new Date());
        }

        //不限量
        if (null == tiresSpecialOffer.getIsLimitNumber()) {
            tiresSpecialOffer.setLimitNumber(null);
        }
        tiresSpecialOfferService.save(tiresSpecialOffer);
        return "redirect:list.cgi";
    }

    @RequestMapping("edit")
    public String edit(Long id, ModelMap model) {
        TiresSpecialOffer tiresSpecialOffer = tiresSpecialOfferService.find(id);
        List<Map> disList = new ArrayList<Map>();
        if (null != tiresSpecialOffer.getDistributorIds()) {
            for (String disId : tiresSpecialOffer.getDistributorIds().split(",")) {
                Distributor distributor = distributorService.find(Long.valueOf(disId));
                Map map = new HashMap();
                map.put("name", distributor.getName());
                map.put("id", distributor.getId());
                disList.add(map);
            }
        }
        Date nowDate = new Date();

        //结束日期是否在当前日期之后,在后面则没有到限时
        boolean isExceed = tiresSpecialOffer.getEndDate().after(nowDate);

        model.addAttribute("exceed", isExceed ? 1 : 0);
        model.addAttribute("distributors", disList);
        model.addAttribute("storeTypes", Store.StoreType.values());
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("specialOffer", tiresSpecialOffer);

        return "/admin/tires_special_offer/edit";
    }

    @RequestMapping("update")
    public String update(TiresSpecialOffer tiresSpecialOffer, Long tiresId) {
        Tires tires = tireService.find(tiresId);

        for (Iterator<TiresSpecialOfferPrice> iterator = tiresSpecialOffer.getTiresSpecialOfferPrices().iterator(); iterator.hasNext(); ) {
            TiresSpecialOfferPrice tiresSpecialOfferPrice = iterator.next();
            if (tiresSpecialOfferPrice == null || tiresSpecialOfferPrice.getIsEnabled() == null) {
                iterator.remove();
            } else {
                tiresSpecialOfferPrice.setTiresSpecialOffer(tiresSpecialOffer);
            }
        }

        tiresSpecialOffer.setTires(tires);

        //下架,才能修改
        if (tiresSpecialOffer.getIsEnabled().equals("0")) {
            tiresSpecialOffer.setGroundingDate(null);
        } else {
            tiresSpecialOffer.setGroundingDate(new Date());
        }

        //不限量
        if (null == tiresSpecialOffer.getIsLimitNumber()) {
            tiresSpecialOffer.setLimitNumber(null);
        }
        tiresSpecialOffer.setModifyDate(new Date());
        tiresSpecialOfferService.update(tiresSpecialOffer);

        return "redirect:list.cgi";
    }

    /**
     * 商品搜索
     *
     * @param keyword
     * @return
     */
    @ResponseBody
    @RequestMapping("tiresSearch")
    public Map<String, Object> tiresSearch(String keyword) {
        List<Map> returnList = new ArrayList<>();
        List<Tires> list = tireService.findList(keyword.trim(), true, 20);
        if (null != list && !list.isEmpty()) {
            for (Tires tires : list) {
                Map temp = new HashMap();
                temp.put("title", tires.getErpImportName());
                temp.put("id", tires.getId());
                temp.put("sn", tires.getSn());
                temp.put("price", tires.getPrice());
                returnList.add(temp);
            }
        } else {
            Map temp = new HashMap();
            temp.put("title", "未搜索到此商品");
            returnList.add(temp);
        }

        Map returnMap = new HashMap();
        returnMap.put("data", returnList);

        return returnMap;
    }

    @ResponseBody
    @RequestMapping(value = "/checkTiresBatchSn", method = RequestMethod.GET)
    public boolean checkTiresBatchSn(String batchSn, Long tiresId) {
        return tiresSpecialOfferService.checkTiresBatchSn(batchSn, tiresId);
    }

    /**
     * 检查指定的批次号，历史是否存在。
     *
     * @param batchSn
     * @return true 不存在
     */
    @ResponseBody
    @RequestMapping(value = "/checkBatchSn", method = RequestMethod.GET)
    public Boolean checkBatchSn(String batchSn) {
        String nowDate = DateUtils.format(new Date(), null);
        return tiresSpecialOfferService.checkHistoryBatchSn(batchSn, nowDate);
    }

    @ResponseBody
    @RequestMapping(value = "/push", method = RequestMethod.POST)
    public boolean push(String android, String ios) {
        PushClient pushClient = new PushClient();
        try {
            if (StringUtil.isNotEmpty(android)) {
                AndroidCustomizedcast customizedcast;
                customizedcast = new AndroidCustomizedcast(androidAppKey, androidAppMasterSecret);
                customizedcast.setAlias(android, "zcckj_store");
                customizedcast.setTicker("新的特价轮胎上架啦，手慢就没啦！");
                customizedcast.setTitle("中策车空间");
                customizedcast.setText("新的特价轮胎上架啦，手慢就没啦！");
                customizedcast.goActivityAfterOpen("com.zcckj.market.view.activity.SpecialPriceTireListActivity");
                customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
                if (productionMode.equals("false")) {
                    customizedcast.setTestMode();
                } else {
                    customizedcast.setProductionMode();
                }
                return pushClient.send(customizedcast);
            }

            if (StringUtil.isNotEmpty(ios)) {
                IOSListcast listcast;

                listcast = new IOSListcast(iosAppKey, iosAppMasterSecret);
                listcast.setAlert("新的特价轮胎上架啦，手慢就没啦！");
                listcast.setCustomizedField("link", "tj");
                if (productionMode.equals("false")) {
                    listcast.setDeviceToken("d12c03cbeaf980f2aab41b945790547bffce727d4f566d877036c8e6a921c831");
                    listcast.setTestMode();
                } else {
                    listcast.setDeviceToken(ios);
                    listcast.setProductionMode();
                }
                return pushClient.send(listcast);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return false;
    }
}
