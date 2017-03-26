package com.winx.dao;

import com.winx.model.Test;
import org.springframework.stereotype.Repository;

/**
 * @author wangwenxiang
 * @create 2017-03-24.
 */
@Repository
public interface TestDao {
    void insert(Test test);
}
