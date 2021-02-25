package com.anji.src.fpe;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.anji.src.fpe.model.AnnotationsReflection;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static com.anji.src.fpe.config.AlgorithmMethon.algorithmMethon;

/**
 * Created by Pank on 2020/05/28
 */
public class TestFpe {


    public static void main(String[] args) throws UnsupportedEncodingException, IllegalAccessException {
   /*     AlgorithmMethon algorithmMethon = new AlgorithmMethon();*/

        long currentTimeMillis = System.currentTimeMillis();
        JSONObject jsonObject = new JSONObject();
        for (int i = 10; i <10000 ; i++) {
          //  String json ="{\"blankCards\":\""+i+"\",\"Voiceprint\":\"asdasdqweqw213123123\",\"WebBrowsingRecord\":\"上海市杨浦区江浦公园1101号\",\"WebBrowsingInfo\":\"1101php\",\"WebBrowsingInfo\":\"1102php\"}";
            String json = "{\"WebBrowsingInfo\":\"1102php\",\"WebBrowsingRecord\":\"胤瘵寂檄韦腔漠咒独沽1101鲶\",\"Voiceprint\":\"OY9uqTwKJP0pGzWFQm7O\",\"blankCards\":\"9999\"}";
            AnnotationsReflection annotationsReflection = JSON.parseObject(json, AnnotationsReflection.class);
           /* jsonObject = algorithmMethon(json, "Encrypt");*/
            jsonObject= algorithmMethon(json,"Decrypt");
        }
        System.out.println("解密数据:"+jsonObject+",总耗时："+(System.currentTimeMillis()-currentTimeMillis)+"毫秒");



    }
}
