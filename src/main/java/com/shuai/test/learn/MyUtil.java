package com.shuai.test.learn;

import java.io.*;

public final class MyUtil {
    private MyUtil(){
        throw new AssertionError();
    }
    //使用try-resource自动释放资源，操作文件使用File开头，操作对象使用Object开头，使用缓冲区使用Buffer开头并与前面的流建立连接
    public static void fileCopy(String source, String target)throws IOException {
        try (InputStream in = new FileInputStream(source)) {
            try (OutputStream out = new FileOutputStream(target)) {
                byte[] buffer = new byte[4096];
                int byteToRead;
                while ((byteToRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, byteToRead);
                }
            }
        }
    }
//    public static void
}
