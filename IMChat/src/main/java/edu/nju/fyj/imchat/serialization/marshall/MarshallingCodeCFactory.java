package edu.nju.fyj.imchat.serialization.marshall;

/**
 * Created by fangyj on 2017/9/24.
 */

import io.netty.handler.codec.marshalling.DefaultMarshallerProvider;
import io.netty.handler.codec.marshalling.DefaultUnmarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;

import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

public class MarshallingCodeCFactory {
    public static NettyMarshallingDecoder buildMarshallingDecoder() {
        /*
         * 通过 Marshalling 工具类的 getProvidedMarshallerFactory
         * 静态方法获取MarshallerFactory 实例, , 参数 serial 表示创建的是 Java 序列化工厂对象.它是由
         * jboss-marshalling-serial 包提供
         */
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        /*
         * 创建
         */
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);

        UnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory, configuration);
        /*
         * provider : 提供商 maxSize : 单个对象最大尺寸
         */
        int maxSize = 1024 << 2;
        NettyMarshallingDecoder decoder = new NettyMarshallingDecoder(provider, maxSize);
        return decoder;
    }

    public static NettyMarshallingEncoder buildMarshallingEncoder() {
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);

        MarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory, configuration);
        NettyMarshallingEncoder decoder = new NettyMarshallingEncoder(provider);
        return decoder;
    }

}
