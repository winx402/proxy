package com.winx.dao;

import com.winx.model.ProxyIp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wangwenxiang
 * @create 2017-03-24.
 */
@Repository
public interface ProxyIpDao {
    void insert(@Param("proxys") List<ProxyIp> proxys);
}
