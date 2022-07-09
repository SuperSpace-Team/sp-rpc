# 网络通信模块

包含 RPC 协议的编解码器、序列化和反序列化工具等。

## 通信过程

### 请求链路

(客户端-Outbound)SpRpcEncoder --> Channel --> (服务端-Inbound)SpRpcDecoder --> RpcRequestHandler

### 响应链路

(客户端-Inbound)RpcResponseHandler <-- SpRpcDecoder <-- Channel <-- (服务端)SpRpcEncoder

## 自定义协议结构

|魔数(2 byte) | 协议版本化(1 byte) | 序列化算法(1 byte) | 报文类型(1 byte) | 状态(1 byte) | 消息ID(8 byte) | 数据长度(4 byte) | 数据内容(长度不定)

## 序列化算法说明

常用的有:JSON/Kryo/Hessian/ProtoBuf