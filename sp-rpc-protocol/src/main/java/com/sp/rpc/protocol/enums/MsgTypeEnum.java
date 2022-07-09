package com.sp.rpc.protocol.enums;

/**
 * 消息类型枚举
 *
 * @author luchao Created in 6/26/22 5:15 PM
 */
public enum MsgTypeEnum {
    REQUEST(Byte.parseByte("T")),
    RESPONSE(Byte.parseByte("P")),
    HEARTBEAT(Byte.parseByte("H"));

    private byte msgType;

    MsgTypeEnum(byte msgType) {
        this.msgType = msgType;
    }

    public byte getMsgType() {
        return msgType;
    }

    public void setMsgType(byte msgType) {
        this.msgType = msgType;
    }

    public static MsgTypeEnum findByType(byte msgType) {
        for (MsgTypeEnum msgTypeEnum : MsgTypeEnum.values()) {
            if (msgTypeEnum.getMsgType() == msgType) {
                return msgTypeEnum;
            }
        }

        return null;
    }
}
