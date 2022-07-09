package com.sp.rpc.protocol.codec;

import com.sp.rpc.protocol.constant.ProtocolConstant;
import com.sp.rpc.protocol.definition.MsgHeader;
import com.sp.rpc.protocol.definition.SpRpcProtocol;
import com.sp.rpc.protocol.definition.SpRpcRequest;
import com.sp.rpc.protocol.definition.SpRpcResponse;
import com.sp.rpc.protocol.enums.MsgTypeEnum;
import com.sp.rpc.protocol.serialization.RpcSerialization;
import com.sp.rpc.protocol.serialization.SerializationFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 自定义RPC解码器
 *
 * @author luchao Created in 6/26/22 5:07 PM
 */
public class SpRpcDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < ProtocolConstant.HEADER_TOTAL_LEN) {
            return;
        }

        in.markReaderIndex();

        short magic = in.readShort();
        if (magic != ProtocolConstant.MAGIC) {
            throw new IllegalArgumentException("Magic number is illegal:" + magic);
        }

        byte version = in.readByte();
        byte serializeType = in.readByte();
        byte msgType = in.readByte();
        byte status = in.readByte();
        long requestId = in.readLong();

        int dataLength = in.readInt();
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }

        byte[] data = new byte[dataLength];
        in.readBytes(data);

        MsgTypeEnum msgTypeEnum = MsgTypeEnum.findByType(msgType);
        if (msgTypeEnum == null) {
            return;
        }

        MsgHeader header = new MsgHeader();
        header.setMagic(magic);
        header.setVersion(version);
        header.setSerialization(serializeType);
        header.setStatus(status);
        header.setRequestId(requestId);
        header.setMsgType(msgType);
        header.setMsgLen(dataLength);

        //根据消息类型解码出SpRpcProtocol和响应结果,传递给RpcResponseHandler
        RpcSerialization rpcSerialization = SerializationFactory.getRpcSerialization(serializeType);
        switch (msgTypeEnum) {
            case REQUEST:
                SpRpcRequest request = rpcSerialization.deserialize(data, SpRpcRequest.class);
                if (request != null) {
                    SpRpcProtocol<SpRpcRequest> protocol = new SpRpcProtocol<>();
                    protocol.setMsgHeader(header);
                    protocol.setBody(request);
                    out.add(protocol);
                }
            case RESPONSE:
                SpRpcResponse response = rpcSerialization.deserialize(data, SpRpcResponse.class);
                if (response == null) {
                    SpRpcProtocol<SpRpcResponse> protocol = new SpRpcProtocol<>();
                    protocol.setMsgHeader(header);
                    protocol.setBody(response);
                    out.add(protocol);
                }
            case HEARTBEAT:
                break;
        }
    }
}
