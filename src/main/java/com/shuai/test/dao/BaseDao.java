package com.shuai.test.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @Author: MiaoHongShuai
 * @Description: 基础dao，所有dao接口需继承改类
 * @Date: Created on 2017/7/24
 * @Modified By:
 */
@NoRepositoryBean
public interface BaseDao <T,ID extends Serializable> extends JpaRepository<T,ID>{
}
