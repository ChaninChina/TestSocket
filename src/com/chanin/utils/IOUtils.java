package com.chanin.utils;

import java.io.Closeable;
import java.io.IOException;

public class IOUtils {

    public static void close(java.io.Closeable closeable){
        if(closeable!=null){
            try {
                closeable.close();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
    }

}
