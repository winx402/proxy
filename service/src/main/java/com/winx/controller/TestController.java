package com.winx.controller;

import com.winx.model.Test;
import com.winx.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author wangwenxiang
 * @create 2017-03-23.
 */
@Controller
@RequestMapping("test")
public class TestController {

    @Resource
    private TestService testService;

    @RequestMapping("test")
    @ResponseBody
    public String test(){
        Test test = new Test();
        test.setName("wang");
        testService.insert(test);
        return "test";
    }
}
