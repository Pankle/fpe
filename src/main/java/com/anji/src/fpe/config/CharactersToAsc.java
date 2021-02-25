package com.anji.src.fpe.config;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Pank on 2020/06/01
 */
public class CharactersToAsc {
    private Boolean flag =false;
    public String fun1(ArrayList<String> asc){
        StringBuilder sb = new StringBuilder(asc.size());
        for (String sca:asc) {
            sb.append((char)Integer.parseInt(sca));
        }
        return sb.toString();
    }


    public ArrayList<String> fun2(String text) {// 字符串转换为ASCII码
        ArrayList<String> arrayList = new ArrayList<String>();
        char[] chars = text.toCharArray(); // 把字符中转换为字符数组
        for (int i = 0; i < chars.length; i++) {// 输出结果
            System.out.println(" " + chars[i] + " " + (int) chars[i]);
            arrayList.add( String.valueOf((int)chars[i]));
        }
        return arrayList;
    }
    public boolean PatternNumber(String data){
        Pattern compile = Pattern.compile("[0-9]{1,}");
        Matcher matcher = compile.matcher((CharSequence) data);
        flag=matcher.matches();
        return flag;
    }
    public boolean isChinese(String data){
        if (data ==null){
            return flag;
        }else {
            for (char c:data.toCharArray()){
                if (isChinese(c)){
                   flag=true;
                }
            }
            return flag;
        }
    }

    // 判断一个字符是否是中文
    public  boolean isChinese(char c) {
        return c >= 0x4E00 &&  c <= 0x9FA5;// 根据字节码判断
    }
    // 判断一个字符是否是简体中文
    public  boolean isSimplifiedChinese(char c,String encode) {
        try {
            String valueOf = String.valueOf(c);
            if (valueOf.equals(new String(valueOf.getBytes(encode), encode))) {
                flag=true;
                return flag;
            }else {
                return flag;
            }
        } catch (Exception e) {
        }
       return flag;
    }
}
