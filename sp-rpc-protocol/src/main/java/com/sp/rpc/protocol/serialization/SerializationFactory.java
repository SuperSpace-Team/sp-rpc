package com.sp.rpc.protocol.serialization;

import com.sp.rpc.protocol.enums.SerializationTypeEnum;

/**
 * 序列化类型工厂
 *
 * @author luchao Created in 6/26/22 4:56 PM
 */
public class SerializationFactory {
    public static RpcSerialization getRpcSerialization(byte serializationType) {
        SerializationTypeEnum typeEnum = SerializationTypeEnum.findByType(serializationType);

        switch (typeEnum) {
            case HESSIAN:
                return new HessianSerialization();
            case JSON:
                return new JsonSerialization();
            default:
                throw new IllegalArgumentException("serialization type is illegal," + serializationType);
        }
    }
}
