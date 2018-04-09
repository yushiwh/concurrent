/**
 * Copyright (C), 2018-2020, 998电商集团
 * FileName: Server
 * Author:   yushi
 * Date:     2018/4/9 15:13
 * Description: Aio的服务器端
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 〈一句话功能简述〉<br>
 * 〈Aio的服务器端〉
 *
 * @author yushi
 * @create 2018/4/9
 * @since 1.0.0
 */
public class Server {
    //线程池
    private ExecutorService executorService;
    //线程组
    private AsynchronousChannelGroup channelGroup;
    //服务器通道
    public AsynchronousServerSocketChannel channel;

    public Server(int port) {
        try {
            //创建线程池
            executorService = Executors.newCachedThreadPool();
            //创建线程组
            channelGroup = AsynchronousChannelGroup.withCachedThreadPool(executorService, 1);
            //创建服务器通道
            channel = AsynchronousServerSocketChannel.open(channelGroup);
            //绑定地址
            channel.bind(new InetSocketAddress(port));
            System.out.println("server start, port：" + port);
            channel.accept(this, new ServerCompletionHandler());
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server(8379);
    }


}