package com.sp.rpc.protocol.codec;

import com.sp.rpc.protocol.definition.MsgHeader;
import com.sp.rpc.protocol.definition.SpRpcProtocol;
import com.sp.rpc.protocol.serialization.RpcSerialization;
import com.sp.rpc.protocol.serialization.SerializationFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 自定义RPC编码器
 *
 * @author luchao Created in 6/26/22 5:01 PM
 */
public class SpRpcEncoder extends MessageToByteEncoder<SpRpcProtocol<Object>> {
    @Override
    protected void encode(ChannelHandlerContext ctx, SpRpcProtocol<Object> msg, ByteBuf out) throws Exception {
        MsgHeader msgHeader = new MsgHeader();
        out.writeShort(msgHeader.getMagic());
        out.writeByte(msgHeader.getVersion());
        out.writeByte(msgHeader.getSerialization());
        out.writeByte(msgHeader.getMsgType());
        out.writeByte(msgHeader.getStatus());
        out.writeLong(msgHeader.getRequestId());

        RpcSerialization rpcSerialization = SerializationFactory.getRpcSerialization(msgHeader.getSerialization());
        byte[] data = rpcSerialization.serialize(msg.getBody());
        out.writeInt(data.length);
        out.writeBytes(data);
    }
}
