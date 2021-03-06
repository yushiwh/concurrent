package aio; /**
 * Copyright (C), 2018-2020, 998电商集团
 * FileName: aio.ServerCompletionHandler
 * Author:   yushi
 * Date:     2018/4/9 15:15
 * Description: 服务器端Handler
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * 〈一句话功能简述〉<br>
 * 〈服务器端Handler〉
 *
 * @author yushi
 * @create 2018/4/9
 * @since 1.0.0
 */
public class ServerCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, Server> {
    @Override
    public void completed(AsynchronousSocketChannel channel, Server attachment) {
        //当有下一个客户端接入的时候，直接调用Server的accept方法，这样反复执行下去，保证多个客户端都可以阻塞
        attachment.channel.accept(attachment, this);
        read(channel);
    }

    private void read(AsynchronousSocketChannel channel) {
        //读取数据
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        channel.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer resultSize, ByteBuffer attachment) {
                attachment.flip();
                System.out.println("aio.Server->" + "收到客户端发送的数据长度为：" + resultSize);
                String data = new String(buffer.array()).trim();
                System.out.println("aio.Server->" + "收到客户端发送的数据为：" + data);
                String response = "服务器端响应了客户端。。。。。。";
                write(channel, response);
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                exc.printStackTrace();
            }
        });
    }

    private void write(AsynchronousSocketChannel channel, String response) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(response.getBytes());
            buffer.flip();
            channel.write(buffer).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void failed(Throwable exc, Server attachment) {
        exc.printStackTrace();
    }
}