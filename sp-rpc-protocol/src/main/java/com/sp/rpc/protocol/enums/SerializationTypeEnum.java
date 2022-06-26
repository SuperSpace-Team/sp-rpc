package com.sp.rpc.protocol.enums;

/**
 * 序列化算法类型枚举
 *
 * @author luchao Created in 6/26/22 4:58 PM
 */
public enum SerializationTypeEnum {
    HESSIAN(Byte.parseByte("1")),
    JSON(Byte.parseByte("2"));

    SerializationTypeEnum(byte serializationType){
        this.serializationType = serializationType;
    }

    private byte serializationType;

    public byte getSerializationType() {
        return serializationType;
    }

    public void setSerializationType(byte serializationType) {
        this.serializationType = serializationType;
    }

    public static SerializationTypeEnum findByType(byte serializationType){
        for(SerializationTypeEnum serializationTypeEnum : SerializationTypeEnum.values()){
            if(serializationTypeEnum.getSerializationType() == serializationType){
                return serializationTypeEnum;
            }
        }

        return null;
    }
}
