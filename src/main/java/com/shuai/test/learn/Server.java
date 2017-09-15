package com.shuai.test.learn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int ECHO_SERVER_PORT = 6069;
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(ECHO_SERVER_PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ClientHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
                String temp = null;
                int index;
                while ((temp = br.readLine()) != null) {
                    System.out.println(temp);
                    if ((index = temp.indexOf("eof")) != -1) {
                        sb.append(temp.substring(0, index));
                    }
                    sb.append(temp);
                }
                System.out.println("fro client:" + sb);
                //传递给客户端
                pw.write("Hello ClientTest!");
                pw.write("eof\n");
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
}
