package com.shuai.test.utils;

import java.io.*;

public final class MyUtil {
    private MyUtil(){
        throw new AssertionError();
    }

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
