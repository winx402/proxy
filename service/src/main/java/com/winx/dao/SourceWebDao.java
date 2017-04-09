package com.winx.dao;

import com.winx.crawler.bean.SourceWeb;
import com.winx.model.ProxyIp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wangwenxiang
 * @create 2017-03-24.
 */
@Repository
public interface SourceWebDao {
    List<SourceWeb> getAll();

    void updateLastTime(@Param("sourceWebs") List<SourceWeb> sourceWebs);

    void updateBanId(@Param("ids") List<Integer> ids);

    void updateNotBanId(@Param("ids") List<Integer> ids);
}
