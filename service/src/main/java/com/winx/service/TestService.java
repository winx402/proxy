package com.winx.service;

import com.winx.dao.TestDao;
import com.winx.model.Test;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wangwenxiang
 * @create 2017-03-24.
 */
@Service
public class TestService {

    @Resource
    private TestDao testDao;

    public void insert(Test test){
        testDao.insert(test);
    }
}
