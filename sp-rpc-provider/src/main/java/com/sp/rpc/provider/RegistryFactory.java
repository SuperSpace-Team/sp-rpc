package com.sp.rpc.provider;

import com.sp.rpc.provider.config.RpcProperties;
import com.sp.rpc.provider.enums.RegistryType;

/**
 * [Add Description Here]
 *
 * @author luchao Created in 6/25/22 1:22 PM
 */
public class RegistryFactory {

    public static RegistryService getInstance(String registryAddr, RegistryType type){

        return new RegistryService();
    }
}
