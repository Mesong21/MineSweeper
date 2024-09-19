package cn.edu.hust.FileUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class RWCache {
    public static char readFromCache(String cacheName) throws IOException {     //从指定cache中读取
        StringBuilder sb = new StringBuilder("src/Cache/");
        sb.append(cacheName);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(sb.toString()));
        String content = bufferedReader.readLine();
        char mark = content.toCharArray()[0];
        return mark;
    }
    public static void writeIntoCache(char num,String cacheName) throws IOException {       //向指定cache中写入
        StringBuilder sb = new StringBuilder("src/Cache/");
        sb.append(cacheName);
        FileWriter fileWriter = new FileWriter(sb.toString());
        fileWriter.write(num);
        fileWriter.flush();
        fileWriter.close();
    }

    public static void writeIntoCache(String num,String cacheName) throws IOException {       //向指定cache中写入
        StringBuilder sb = new StringBuilder("src/Cache/");
        sb.append(cacheName);
        FileWriter fileWriter = new FileWriter(sb.toString());
        fileWriter.write(num);
        fileWriter.flush();
        fileWriter.close();
    }
}
