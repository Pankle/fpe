package com.anji.src.fpe.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.anji.src.fpe.FormatPreservingEncryption;
import com.anji.src.fpe.builder.FormatPreservingEncryptionBuilder;
import com.anji.src.fpe.model.AlgorithmModel;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by Pank on 2020/06/01
 */
public class AlgorithmMethon {
    public static CharactersToAsc charactersToAsc = new CharactersToAsc();
    private static Map chineseIndex= new HashMap();
    private static ArrayList chinese = new ArrayList();
    private static  final String encode ="GB2312";
    private static  byte[] anyKey;
    private static byte[] aTweak;
    private static   String algorithmType;
    private static FormatPreservingEncryption formatPreservingEncryption;
    public static AlgorithmModel algorithmModel = new AlgorithmModel();
    static {
        //128-bit 秘钥
        anyKey = new byte[]{(byte) 0x2C, (byte) 0x7E, (byte) 0x15, (byte) 0x16, (byte) 0x28, (byte) 0xAE, (byte) 0xD2,
                (byte) 0xA6, (byte) 0xAB, (byte) 0xF7, (byte) 0x15, (byte) 0x88, (byte) 0x09, (byte) 0xCF, (byte) 0x4F,
                (byte) 0x3C};
        aTweak = new byte[]{(byte) 0x2C, (byte) 0x7D, (byte) 0x15, (byte) 0x16, (byte) 0x27, (byte) 0xAE, (byte) 0xD2,
                (byte) 0xA6, (byte) 0xAB, (byte) 0xF7, (byte) 0x25, (byte) 0x89, (byte) 0x09, (byte) 0xCF, (byte) 0x4F,
                (byte) 0x3F};
        algorithmType = "MAC/ECB/CTR/GMAC/GCM";
        // with default values
        formatPreservingEncryption= FormatPreservingEncryptionBuilder
                .ff1Implementation()
                .withDefaultDomain()
                .withDefaultPseudoRandomFunction(anyKey)
                .withDefaultLengthRange()
                .build();
    }

    //数据出入库时加密解密方法调用每次数据同一个字段只允许对应一个值进行加密解密
    public static JSONObject algorithmMethon(String json,String algorithmType )  {
        JSONObject jsonObject =  JSON.parseObject(json);
        AlgorithmModel algorithmModel = JSON.parseObject(json, AlgorithmModel.class);
        try {
        if (algorithmType.equals("Encrypt")){
            Class<? extends AlgorithmModel> aClass = algorithmModel.getClass();
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field field:declaredFields){
                field.setAccessible(true);
                    if (field.get(algorithmModel) !=null && !field.get(algorithmModel).equals("")){
                        String encrypt = Encrypt(field.get(algorithmModel).toString());
                        jsonObject.put(field.getName(),encrypt);
                    }
                }
        }else if (algorithmType.equals("Decrypt")){
            Class<? extends AlgorithmModel> aClass = algorithmModel.getClass();
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field field:declaredFields){
                field.setAccessible(true);
                    if (field.get(algorithmModel) !=null && !field.get(algorithmModel).equals("")){
                        String decrypt = Decrypt(field.get(algorithmModel).toString());
                        jsonObject.put(field.getName(),decrypt);
                    }
                 }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    //进行查询时可以对一个字段的多个值进行加密 例如card-id in(11121,2222231,44444) 传入sql
    public static void algorithmMethon(JSONObject json)  {
        Set<String> strings = json.keySet();
        Iterator<String> iterator = strings.iterator();
        while (iterator.hasNext()){
            ArrayList<String> list = new ArrayList<String>();
            String next = iterator.next();
            Boolean existField = isExistField(next, algorithmModel);
            if (existField.equals(true) ){
                Object o = json.get(next);
                ArrayList<String> arrayList = new ArrayList();
                if (o instanceof  ArrayList){
                    arrayList = (ArrayList) o;
                    for (String text:arrayList){
                        String encrypt = Encrypt(text);
                        list.add(encrypt);
                        json.put(next,list);
                    }
                }else {
                    String encrypt = Encrypt(o.toString());
                    json.put(next,encrypt);
                }
            }
        }
       /* System.out.println("加密结果："+json);*/
    }

    public static Boolean isExistField(String field, Object obj) {
        if (obj == null || StringUtils.isEmpty(field)) {
            return null;
        }
        Object o = JSON.toJSON(obj);
        JSONObject jsonObj = new JSONObject();
        if (o instanceof JSONObject) {
            jsonObj = (JSONObject) o;
        }
        return jsonObj.containsKey(field);
    }

   /* public static String lowerFirst(String oldStr){
        char[]chars = oldStr.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }*/

    //中文字符解析
    private static char[] Analysize(String aText) {
        char[] chars = aText.toCharArray();
        for (int i=0;i<chars.length;i++){
            if (charactersToAsc.isChinese(chars[i])){
                if (charactersToAsc.isSimplifiedChinese(chars[i],   encode )){
                    chineseIndex.put(i,chars[i]);
                    chinese.add(String.valueOf(chars[i]));
                }
            }
        }
        return chars;
    }


    //数据加密
    public static String Encrypt(String field){

        if (charactersToAsc.PatternNumber(field)==true){
            String encrypt = formatPreservingEncryption.encryptLong(field, aTweak);
            return encrypt;
        } else if (charactersToAsc.isChinese(field)==true){
            char[] analysize = Analysize(field);
            String encrypt = formatPreservingEncryption.encryptUnicode(chinese, aTweak);
            char[] chars1 = encrypt.toCharArray();
            int j = 0;
            for (int i = 0; i < analysize.length; i++) {
                if (chineseIndex.get(i) != null) {
                    analysize[i] = chars1[j];
                    j++;
                }
            }
         chinese.clear();
            return String.valueOf(analysize);
        }else{
            String encrypt = formatPreservingEncryption.encrypt(field, aTweak);
            return encrypt;
        }
    }

    //数据解密
    public static  String Decrypt(String field){
        if (charactersToAsc.PatternNumber(field)==true){
            String decrypt = formatPreservingEncryption.decryptLong(field, aTweak);
            return decrypt;
        } else if (charactersToAsc.isChinese(field)==true) {
            char[] analysize = Analysize(field);
            String decrypt = formatPreservingEncryption.decrypts(chinese, aTweak);
            char[] chars1 = decrypt.toCharArray();
            int j = 0;
            for (int i = 0; i < analysize.length; i++) {
                if (chineseIndex.get(i) != null) {
                    analysize[i] = chars1[j];
                    j++;
                }
            }
            chinese.clear();
            return String.valueOf(analysize);
        }else {
            String decrypt = formatPreservingEncryption.decrypt(field, aTweak);
            return decrypt;
        }
    }

    //ocr计算 图片敏感数据

    //高级api Sql转换器(全文匹配需要进行加密的数据进行转换)
    public static  String  SqlTransform(String sql){

        return "";
    }

}
