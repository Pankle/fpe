package com.anji.src.fpe.config;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class WriterFile {
    private static File writefile;
    private static String path=System.getProperty("user.dir")+"/test.log";
   static {
       try {
       // 通过这个对象来判断是否向文本文件中追加内容
       // boolean addStr = append;
       writefile = new File(path);
       // 如果文本文件不存在则创建它
       if (!writefile.exists()) {
               writefile.createNewFile();
           }
           writefile = new File(path); // 重新实例化
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

    public static void writeLog(String content){

        try {
            FileOutputStream fw = new FileOutputStream(writefile,true);
            Writer out = new OutputStreamWriter(fw, "utf-8");
            out.write(content);
            String newline = System.getProperty("line.separator");
            //写入换行
            out.write(newline);
            out.close();
            fw.flush();
            fw.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    // 获取当前时间
    public static String getCurrentYYYYMMDDHHMMSS() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Date currTime = new Date();
        String thisTime = new String(formatter.format(currTime));
        return thisTime;
    }

    public static void main(String[] args) {


        String content = getCurrentYYYYMMDDHHMMSS() + " 日志内容" ;
        writeLog(content);


    }
}
