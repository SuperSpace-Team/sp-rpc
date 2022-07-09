package com.sp.rpc.core.utils;

/**
 * [Add Description Here]
 *
 * @author luchao Created in 6/23/22 12:35 AM
 */
public class RpcServiceHelper {

    public static String buildServiceKey(String serviceName, String serviceVersion) {
        return String.join("#", serviceName, serviceVersion);
    }
}
