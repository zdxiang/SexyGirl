package cn.zdxiang.sexygirl.base;

/**
 * @author jm
 * @date 16-12-6.下午3:03
 */

public class BaseResponse<T> {

    private String validateMessagesShowId;

    private boolean status;

    private int httpstatus;

    private T data;

}

