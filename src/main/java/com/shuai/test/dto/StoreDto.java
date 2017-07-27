package com.shuai.test.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: MiaoHongShuai
 * @Description:
 * @Date: Created on 2017/7/27
 * @Modified By:
 */
@Data
@Builder
public class StoreDto implements Serializable{
    private Long id;
    private Long storeId;
    private String phone;
    private String password;
    private Boolean isEnabled;
    private String ip;
    private String salt;
}
