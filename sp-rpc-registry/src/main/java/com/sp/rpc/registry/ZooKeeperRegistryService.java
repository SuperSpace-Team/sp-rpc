package com.sp.rpc.registry;

import com.sp.rpc.core.utils.RpcServiceHelper;
import com.sp.rpc.registry.lb.ZkConsistentHashLoadBalancer;
import com.sp.rpc.registry.model.ServiceMeta;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * ZooKeeper注册中心实现
 *
 * @author luchao Created in 6/28/22 11:49 PM
 */
public class ZooKeeperRegistryService implements RegistryService {
    public static final int BASE_SLEEP_TIME_MS = 1000;
    public static final int MAX_RETRIES = 3;

    /**
     * ZK节点目录名称,存放注册的服务及其各实例名
     */
    public static final String ZK_BASE_PATH = "/sp_rpc";

    private final ServiceDiscovery<ServiceMeta> serviceDiscovery;

    public ZooKeeperRegistryService(String registryAddr) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient(registryAddr,
                new ExponentialBackoffRetry(BASE_SLEEP_TIME_MS, MAX_RETRIES));
        client.start();

        JsonInstanceSerializer<ServiceMeta> serializer = new JsonInstanceSerializer<>(ServiceMeta.class);
        this.serviceDiscovery = ServiceDiscoveryBuilder.builder(ServiceMeta.class).client(client)
                .serializer(serializer).basePath(ZK_BASE_PATH).build();
        this.serviceDiscovery.start();
    }

    @Override
    public void register(ServiceMeta serviceMeta) throws Exception {
        ServiceInstance<ServiceMeta> serviceInstance =
                ServiceInstance.<ServiceMeta>builder()
                        .name(RpcServiceHelper.buildServiceKey(serviceMeta.getServerName(), serviceMeta.getServerVersion()))
                        .address(serviceMeta.getServerAddr())
                        .port(serviceMeta.getServerPort())
                        .payload(serviceMeta)
                        .build();
        serviceDiscovery.registerService(serviceInstance);
    }

    @Override
    public void unRegister(ServiceMeta serviceMeta) throws Exception {

    }

    @Override
    public ServiceMeta discovery(String serviceName, int invokerHashCode) throws Exception {
        Collection<ServiceInstance<ServiceMeta>> serviceInstances = serviceDiscovery.queryForInstances(serviceName);

        //通过ZK一致性Hash算法找到服务节点
        ServiceInstance<ServiceMeta> instance = new ZkConsistentHashLoadBalancer().select(
                (List<ServiceInstance<ServiceMeta>>) serviceInstances, invokerHashCode);

        if (instance != null) {
            return instance.getPayload();
        }
        return null;
    }

    @Override
    public void destroy() throws IOException {
        serviceDiscovery.close();
    }
}
