package com.shuai.test.controller;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: MiaoHongShuai
 * @Description:
 * @Date: Created on 2017/8/7
 * @Modified By:
 */
@Controller
@RequestMapping("/admin/store_activity")
public class StoreActivityController extends BaseController {

    @InitBinder//解决vo封装页面表单数据时，日期格式转换及是否可为空 的问题
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//这里的allowEmpty一般设为true，即允许前端页面传过来的日期为空或空串
    }

//    @RequestMapping(value = "/list", method = RequestMethod.GET)
//    public String getStoreActivity(StoreActivitySearchDto searchDto, Pageable pageable, Model model) {
//        Integer totalPages = 4;
//        Integer totalRecord = 80;
//        model.addAttribute("searchDto", searchDto);
//        model.addAttribute("storeActivityList", null);
//        model.addAttribute("page", pageable);
//        model.addAttribute("totalPages", totalPages);
//        model.addAttribute("totalRecord", totalRecord);
//        return "/admin/store_activity/list";
//    }

    @RequestMapping(value = "/showStatus", method = RequestMethod.POST)
    public String updateShowStatus(Long storeActivityId, Boolean storeShowStatus) {
        //调用API修改门店活动状态

        return "redirect:list.cgi";
    }
}
