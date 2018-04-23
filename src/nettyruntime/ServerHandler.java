/**
 * Copyright (C), 2018-2020, 998电商集团
 * FileName: ServerHandler
 * Author:   yushi
 * Date:     2018/4/23 9:22
 * Description: 服务器端ServerHandler
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package nettyruntime;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 〈一句话功能简述〉<br>
 * 〈服务器端ServerHandler〉
 *
 * @author yushi
 * @create 2018/4/23
 * @since 1.0.0
 */
public class ServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Request request = (Request) msg;
        System.out.println("Server : " + request.getId() + ", " + request.getName() + ", " + request.getRequestMessage());
        Response response = new Response();
        response.setId(request.getId());
        response.setName("response" + request.getId());
        response.setResponseMessage("响应内容" + request.getId());
        ctx.writeAndFlush(response);//.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}