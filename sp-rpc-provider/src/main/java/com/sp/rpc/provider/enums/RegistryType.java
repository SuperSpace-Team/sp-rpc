package com.sp.rpc.provider.enums;

/**
 * 服务注册类型枚举
 *
 * @author luchao Created in 6/22/22 1:51 AM
 */
public enum RegistryType {
    ZooKeeper(1, "ZooKeeper");

    RegistryType(Integer typeCode, String typeName) {
        this.typeCode = typeCode;
        this.typeName = typeName;
    }

    private Integer typeCode;

    private String typeName;
}
