package com.sp.rpc.protocol.enums;

/**
 * 消息状态枚举
 *
 * @author luchao Created in 6/26/22 6:06 PM
 */
public enum MsgStatusEnum {
    SUCCESS(200),
    FAILED(500),
    EXCEPTION(999);

    MsgStatusEnum(int code) {
        this.code = code;
    }

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
