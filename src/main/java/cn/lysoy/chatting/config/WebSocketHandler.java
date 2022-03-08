package cn.lysoy.chatting.config;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("与客户端建立连接，通道开启！");
        //添加到channelGroup通道组
        ChannelHandlerPool.channelGroup.add(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("与客户端断开连接，通道关闭！");
        //从channelGroup通道组删除
        ChannelHandlerPool.channelGroup.remove(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
    //接收的消息
        log.info(String.format("收到客户端%s的数据：%s" ,ctx.channel().id(), msg.text()));

        // 单独发消息
        // sendMessage(ctx, msg.text());
        // 群发消息
        sendAllMessage(msg.text());
    }

    private void sendMessage(ChannelHandlerContext ctx, String message){
        ctx.writeAndFlush(new TextWebSocketFrame(message));
    }

    private void sendAllMessage(String message){
        ChannelHandlerPool.channelGroup.writeAndFlush(new TextWebSocketFrame(message));
    }

}