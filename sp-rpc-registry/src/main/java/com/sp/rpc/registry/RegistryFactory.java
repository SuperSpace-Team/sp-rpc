package com.sp.rpc.registry;

import com.sp.rpc.registry.enums.RegistryType;

/**
 * 注册中心工厂
 *
 * @author luchao Created in 6/25/22 1:22 PM
 */
public class RegistryFactory {

    public static RegistryService getInstance(String registryAddr, RegistryType type) {
        if (type.equals(RegistryType.ZooKeeper)) {
            try {
                return new ZooKeeperRegistryService(registryAddr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
