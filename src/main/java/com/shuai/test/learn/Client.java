package com.shuai.test.learn;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;

public class Client {
    private static final String ECHO_SERVER_HOST = "127.0.0.1";
    private static final int ECHO_SERVER_PORT = 6069;

    public static void main(String[] args) {
        try (Socket client = new Socket(ECHO_SERVER_HOST, ECHO_SERVER_PORT);//与服务端建立连接
             //获取输出流，向服务器端发送信息
             Writer writer = new OutputStreamWriter(client.getOutputStream());
             //获取读取流，接收服务端发送的信息，使用缓存区
             BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()))) {

//            进行手输入字符串信息传递：Scanner scanner = new Scanner(System.in);
//            System.out.println("请输入内容：");
//            String msg = scanner.nextLine();//获取nextline（）以回车作为结束标志的用户输入的字符串
//            writer.write(msg);
            //进行写死的字符串信息传递
            writer.write("你好，服务端！");
            writer.write("eof\n");//结束标记
            writer.flush();//将缓冲区的数据写入流

            //设置超时间为10秒（Socket为我们提供了一个setSoTimeout()方法来设置接收数据的超时时间，
            // 单位是毫秒。当设置的超时时间大于0，并且超过了这一时间Socket还没有接收到返回的数据的话，Socket就会抛出一个SocketTimeoutException。）
            client.setSoTimeout(10*1000);
            StringBuilder sb = new StringBuilder();
            String temp;
            int index;
            while ((temp = br.readLine()) != null) {
                if ((index = temp.indexOf("eof")) != -1) {//遇到eof时就结束接收
                    sb.append(temp.substring(0, index));
                    break;
                }
                sb.append(temp);
            }
            System.out.println("来自服务端:" + sb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}