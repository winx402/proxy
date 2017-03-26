package com.winx.exception;

import com.winx.enums.ExceptionEnum;

/**
 * @author wangwenxiang
 * @create 2017-03-26.
 */
public class ProcessException extends Exception {

    public ProcessException(String msg){
        super(msg);
    }

    public ProcessException(String msg, Exception e){
        super(msg, e);
    }

    public ProcessException(ExceptionEnum exceptionEnum){
        super(exceptionEnum.name());
    }
}
