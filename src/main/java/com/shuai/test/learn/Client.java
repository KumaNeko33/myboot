package com.shuai.test.learn;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String ECHO_SERVER_HOST = "127.0.0.1";
    private static final int ECHO_SERVER_PORT = 6069;

    public static void main(String[] args) {
        try (Socket client = new Socket(ECHO_SERVER_HOST, ECHO_SERVER_PORT);
             Writer writer = new OutputStreamWriter(client.getOutputStream());
             BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入内容：");
            String msg = scanner.nextLine();//获取nextline（）以回车作为结束标志的用户输入的字符串
            writer.write(msg);
            writer.write("eof\n");
            writer.flush();

            StringBuilder sb = new StringBuilder();
            String temp = null;
            int index;
            while ((temp = br.readLine()) != null) {
                if ((index = temp.indexOf("eof")) != -1) {
                    sb.append(temp.substring(0, index));
                }
                sb.append(temp);
            }
            System.out.println("from Server:" + sb);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}