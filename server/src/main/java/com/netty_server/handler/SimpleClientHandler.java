package com.whx.netty_server.handler;

import javax.swing.text.MaskFormatter;

import com.alibaba.fastjson.JSONObject;
import com.whx.netty_server.client.DefaultFuture;
import com.whx.netty_server.client.Response;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.util.AttributeKey;

public class SimpleClientHandler extends ChannelInboundHandlerAdapter {


		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			// TODO Auto-generated method stub
			if("ping".equals(msg.toString()))
			{
				ctx.channel().writeAndFlush("ping\r\n");
				return;
			}
//			ctx.channel().attr(AttributeKey.valueOf("sssss")).set(msg);
			Response response=JSONObject.parseObject(msg.toString(),Response.class);
			DefaultFuture .receive(response);
		}
}
	


