package com.example.learnnetty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * <p>
 * ReplayingDecoder 比 ByteToMessageDecoder更加灵活，牺牲了速度。引入过多复杂性时使用ReplayingDecoder
 * </p>
 *
 * @author gaox
 * @since 2020/4/2
 */
public class MyReplayingDecoder extends ReplayingDecoder<MyReplayingDecoder.State> {

    public enum State{
        LENGTH,//读取长度状态
        CONTENT//读取内容状态
    }

    public MyReplayingDecoder(){
        super(State.LENGTH);
    }
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        //根据当前的状态
        switch (state()){
            case LENGTH:
                int length =byteBuf.readInt();
                if(length>0){
                    checkpoint(State.CONTENT);
                }else{
//                    list.add(message);
                }
                break;
            case CONTENT:
//                byte[] bytes = new byte[];
                break;
        }

    }
}
