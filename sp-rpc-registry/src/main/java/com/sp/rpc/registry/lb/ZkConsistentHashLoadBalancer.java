package com.sp.rpc.registry.lb;

import com.sp.rpc.registry.model.ServiceMeta;
import org.apache.curator.x.discovery.ServiceInstance;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * ZK一致性哈希负载均衡器实现
 * 一致性哈希算法还可以参考使用MurmurHash实现
 *
 * @author luchao Created in 6/29/22 12:22 AM
 */
public class ZkConsistentHashLoadBalancer implements ServiceLoadBalancer<ServiceInstance<ServiceMeta>> {
    private final static int VIRTUAL_NODE_SIZE = 10;
    private final static String VIRTUAL_NODE_SPLIT = "#";

    @Override
    public ServiceInstance<ServiceMeta> select(List<ServiceInstance<ServiceMeta>> servers, int hashCode) {
        //构造哈希环,根据hash code分配节点
        TreeMap<Integer, ServiceInstance<ServiceMeta>> hashRing = makeConsistentHashRing(servers);
        return allocateNode(hashRing, hashCode);
    }

    private ServiceInstance<ServiceMeta> allocateNode(TreeMap<Integer, ServiceInstance<ServiceMeta>> hashRing, int hashCode) {
        //TreeMap会对hashCode从小到大排序,顺时针找到第一个节点
        Map.Entry<Integer, ServiceInstance<ServiceMeta>> entry = hashRing.ceilingEntry(hashCode);

        if (entry == null) {
            //若未找到大于hashCode的节点,则取第一个
            entry = hashRing.firstEntry();
        }

        return entry.getValue();
    }

    private TreeMap<Integer, ServiceInstance<ServiceMeta>> makeConsistentHashRing(List<ServiceInstance<ServiceMeta>> servers) {
        TreeMap<Integer, ServiceInstance<ServiceMeta>> hashRing = new TreeMap<>();
        for (ServiceInstance<ServiceMeta> instance : servers) {
            for (int i = 0; i < VIRTUAL_NODE_SIZE; i++) {
                hashRing.put((buildServiceInstanceKey(instance) + VIRTUAL_NODE_SPLIT + i).hashCode(), instance);
            }
        }
        return hashRing;
    }

    private String buildServiceInstanceKey(ServiceInstance<ServiceMeta> instance) {
        ServiceMeta payLoad = instance.getPayload();
        return String.join(":", payLoad.getServerAddr(), String.valueOf(payLoad.getServerPort()));

    }
}
