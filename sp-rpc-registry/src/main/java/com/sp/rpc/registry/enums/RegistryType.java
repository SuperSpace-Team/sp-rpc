package com.sp.rpc.registry.enums;

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

    public Integer getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Integer typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
