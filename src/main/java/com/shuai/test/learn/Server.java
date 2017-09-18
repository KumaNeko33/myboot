package com.shuai.test.learn;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int ECHO_SERVER_PORT = 6069;
    public static void main(String[] args) {
        //定义一个ServerSocket监听在端口 6069 上
        try (ServerSocket serverSocket = new ServerSocket(ECHO_SERVER_PORT)) {
            while (true) {
                //server尝试接收其他Socket的连接请求，server的accept方法是阻塞式的
                Socket socket = serverSocket.accept();
                //每接收到一个Socket就建立一个新的线程来处理它
                new Thread(new ClientHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用来处理Socket请求的,跟客户端Socket进行通信
     */
    private static class ClientHandler implements Runnable {
        private Socket socket;
        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter pw = new PrintWriter(socket.getOutputStream())) {
                //服务端读取
                StringBuilder sb = new StringBuilder();
                String temp;
                int index;
                while ((temp = br.readLine()) != null) {
//                    System.out.println(temp);
                    if ((index = temp.indexOf("eof")) != -1) {//遇到eof时就结束接收
                        sb.append(temp.substring(0, index));
                        break;
                    }
                    sb.append(temp);
                }
                System.out.println("来自客服端:" + sb);
                //读完后写一句,传递给客户端
                pw.write("你好，客户端!");
                pw.write("eof\n");//结束标记
                pw.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*结束标记的作用：
    因为从输入流中读取数据是一个阻塞式操作，在上述的while循环中当读到数据的时候就会执行循环体，否则就会阻塞，这样后面的写操作就永远都执行不了了。
    除非客户端对应的Socket关闭了阻塞才会停止，while循环也会跳出。针对这种可能永远无法执行下去的情况的解决方法是while循环需要在里面有条件的跳出来，
    纵观上述代码，在不断变化的也只有取到的长度len和读到的数据了，len已经是不能用的了，唯一能用的就是读到的数据了。针对这种情况，通常我们都会约定一个结束标记，
    当客户端发送过来的数据包含某个结束标记时就说明当前的数据已经发送完毕了，这个时候我们就可以进行循环的跳出了。

BufferedReader的readLine方法是一次读一行的，这个方法是阻塞的，直到它读到了一行数据为止程序才会继续往下执行，那么readLine什么时候才会读到一行呢？
直到程序遇到了换行符或者是对应流的结束符readLine方法才会认为读到了一行，才会结束其阻塞，让程序继续往下执行。
所以我们在使用BufferedReader的readLine读取数据的时候一定要记得在对应的输出流里面一定要写入换行符（流结束之后会自动标记为结束，readLine可以识别）
，写入换行符之后一定记得如果输出流不是马上关闭的情况下记得flush一下，这样数据才会真正的从缓冲区里面写入
     */
}
