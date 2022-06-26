package com.sp.rpc.protocol.serialization;

import com.caucho.hessian.io.HessianSerializerOutput;
import com.sun.xml.internal.ws.encoding.soap.SerializationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Hessian序列化自定义协议实现类
 *
 * @author luchao Created in 6/26/22 11:58 AM
 */
@Component
@Slf4j
public class HessianSerialization implements RpcSerialization{
    public <T> byte[] serialize(T obj) throws IOException {
        if(obj == null){
            throw new NullPointerException();
        }

        byte[] results;
        HessianSerializerOutput hessianOutput;
        try(ByteArrayOutputStream os = new ByteArrayOutputStream()){
            hessianOutput = new HessianSerializerOutput(os);
            hessianOutput.writeObject(obj);
            hessianOutput.flush();
            results = os.toByteArray();
        }catch (Exception e){
            throw new SerializationException(e);
        }

        return results;
    }

    public <T> T deserialize(byte[] data, Class<T> clz) throws IOException {
        return null;
    }
}
