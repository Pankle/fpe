package com.anji.src.fpe;

import com.anji.src.fpe.algorithm.Cipher;
import com.anji.src.fpe.builder.validate.BuildValidator;
import com.anji.src.fpe.functions.prf.PseudoRandomFunction;
import com.anji.src.fpe.config.*;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FormatPreservingEncryption {

    private final Cipher cipher;
    private ExpireMap<String, Integer> expireMap = new ExpireMap<String, Integer>(1000*60*60);
    private final Domain selectedDomain;
    private final PseudoRandomFunction selectedPRF;
    private final LengthRange lengthRange;
    public  CharactersToAsc charactersToAsc = new CharactersToAsc();
    public FormatPreservingEncryption(Cipher cipher, Domain selectedDomain, PseudoRandomFunction selectedPRF, LengthRange lengthRange) {
        new BuildValidator(selectedDomain.alphabet().radix(), lengthRange.min(), lengthRange.max()).validate();
        this.cipher = cipher;
        this.selectedDomain = selectedDomain;
        this.selectedPRF = selectedPRF;
        this.lengthRange = lengthRange;
    }

    //字符串加密
    public String encrypt(String plainText, byte[] tweak) {
        String transform = encryptImpl(plainText, tweak);
        String checkEncryptDate = checkEncryptDate(transform, plainText);
        if (StringUtils.isEmpty(checkEncryptDate)){
            return transform;
        }else if (checkEncryptDate.equals("3")){
            return plainText;
        }else {
           return encrypt(plainText, tweak);
        }
    }
//tweak为秘钥
    public  String encryptImpl(String plainText, byte[] tweak){
        check(plainText, tweak);
        //获取加密内容每个字符串在字典表中的下标位置
        int[] numeralPlainText = selectedDomain.transform(plainText);
        //通过秘钥将下标混淆形成新的下标值
        int[] numeralCipher = cipher.encrypt(numeralPlainText, selectedDomain.alphabet().radix(), tweak, selectedPRF);
        //将混淆的下标重新匹配字典表形成新的字符串
        String transform = selectedDomain.transform(numeralCipher);
        return transform;
    }




    //int 数组加密
/*
    public String encrypt(int[] plainText, byte[] tweak) {
        check(plainText, tweak);
        int[] numeralCipher = cipher.encrypt(plainText, selectedDomain.alphabet().radix(), tweak, selectedPRF);
        return selectedDomain.transform(numeralCipher);
    }
*/
    //Long类型加密
    public String encryptLong(String plainText, byte[] tweak) {
        String longtransform = encryptLongImpl(plainText, tweak);
        String checkEncryptDate = checkEncryptDate(longtransform, plainText);
        if (StringUtils.isEmpty(checkEncryptDate)){
            return longtransform;
        }else if (checkEncryptDate.equals("3")){
            return plainText;
        }else{
            return encryptLong(plainText,tweak);
        }
    }

    public  String encryptLongImpl(String plainText, byte[] tweak){
        check(plainText, tweak);
        int[] numeralPlainText = selectedDomain.transformLong(plainText);
        int[] numeralCipher = cipher.encrypt(numeralPlainText, selectedDomain.alphabet().numberradix(), tweak, selectedPRF);
        String longtransform = selectedDomain.longTransform(numeralCipher);
        return longtransform;
    }


    //中文加密
    public String encrypt(ArrayList<String> plainText, byte[] tweak) {
        Map data = encryptImpl(plainText, tweak);
        String checkEncryptDate = checkEncryptDate(String.valueOf(data.get("transform")), String.valueOf(data.get("sb")));
            if (StringUtils.isEmpty(checkEncryptDate)){
                 return String.valueOf(data.get("transform"));
             }else if (checkEncryptDate.equals("3")){
                return data.values().toString();
             }else{
                return encrypt(plainText,tweak);
             }
    }

    //中文加密1.1
    public String encryptUnicode(ArrayList<String> plainText, byte[] tweak) {
        Map data = encryptUnicodeImpl(plainText, tweak);
        String checkEncryptDate = checkEncryptDate(String.valueOf(data.get("transform")), String.valueOf(data.get("sb")));
        if (StringUtils.isEmpty(checkEncryptDate)){
            return String.valueOf(data.get("transform"));
        }else if (checkEncryptDate.equals("3")){
            return data.values().toString();
        }else {
            return encrypt(plainText,tweak);
        }
    }
    public Map encryptUnicodeImpl(ArrayList<String> plainText, byte[] tweak){
        Map data = new HashMap();
        check(plainText, tweak);
        StringBuilder sb = new StringBuilder();
        for (String s : plainText) {
            if (s != null && !"".equals(s)) {
                //中文转unicode
            /*    String s1 = cnToUnicode(s);*/
                sb.append(s);
            }
        }
        int[] numeralPlainText = selectedDomain.transformAsc(sb.toString());
        int[] numeralCipher = cipher.encrypt(numeralPlainText, selectedDomain.alphabet().chinatadix(), tweak, selectedPRF);
        String transform = selectedDomain.intTransform(numeralCipher);
        data.put("transform",transform);
        data.put("sb",sb);
        return  data;
    }
    //tewak为秘钥
    public Map encryptImpl(ArrayList<String> plainText, byte[] tweak){
        Map data = new HashMap();
        check(plainText, tweak);
        StringBuilder sb = new StringBuilder();
        for (String s : plainText) {
            if (s != null && !"".equals(s)) {
                sb.append(s);
            }
        }
        int[] numeralPlainText = selectedDomain.transformAsc(sb.toString());
        int[] numeralCipher = cipher.encrypt(numeralPlainText, selectedDomain.alphabet().chinatadix(), tweak, selectedPRF);
        String transform = selectedDomain.intTransform(numeralCipher);
        data.put("transform",transform);
        data.put("sb",sb);
        return  data;
    }




    //字符串解密
    public String decrypt(String cipherText, byte[] tweak) {
        check(cipherText, tweak);
        int[] numeralCipherText = selectedDomain.transform(cipherText);
        int[] numeralPlainText = cipher.decrypt(numeralCipherText, selectedDomain.alphabet().radix(), tweak, selectedPRF);
        return selectedDomain.transform(numeralPlainText);
    }
    //int类型解密
    public String decrypt(Integer cipherText,byte[] tweak){
        check(cipherText, tweak);
        int[] numeralCipherText = selectedDomain.transform(cipherText);
        int[] numeralPlainText = cipher.decrypt(numeralCipherText, selectedDomain.alphabet().numberradix(), tweak, selectedPRF);
        return selectedDomain.intTransform(numeralPlainText);
    }

    //Long类型解密
    public String decryptLong(String cipherText,byte[] tweak){
        check(cipherText, tweak);
        int[] numeralCipherText = selectedDomain.transformLong(cipherText);
        int[] numeralPlainText = cipher.decrypt(numeralCipherText, selectedDomain.alphabet().numberradix(), tweak, selectedPRF);
        return selectedDomain.longTransform(numeralPlainText);
    }

    //中文解密
    public String decrypts(ArrayList<String> cipherText, byte[] tweak) {
        check(cipherText, tweak);
        StringBuilder sb = new StringBuilder();
        for (String s : cipherText) {
            if (s != null && !"".equals(s)) {
                sb.append(s);
            }
        }
        int[] numeralCipherText = selectedDomain.transformAsc(sb.toString());
        int[] numeralPlainText = cipher.decrypt(numeralCipherText, selectedDomain.alphabet().chinatadix(), tweak, selectedPRF);
        return selectedDomain.intTransform(numeralPlainText);
    }


    //核对数据加密情况
    private String checkEncryptDate(String text, String Original) {
        if (StringUtils.isEmpty(text)) {
            WriterFile.writeLog(WriterFile.getCurrentYYYYMMDDHHMMSS()+
                    FormatPreservingEncryptionErrorMessage.NULL_ENCRYPT_OUTPUT.toString());
            checkError(text,expireMap);
            return String.valueOf(expireMap.get(text));
        }
        if (text.length() != Original.length()){
            WriterFile.writeLog( WriterFile.getCurrentYYYYMMDDHHMMSS()+
                    String.format(String.valueOf(FormatPreservingEncryptionErrorMessage.
                            INVALID_SIZE_ENCRYPT),text.length(),Original.length(),
                            text,Original));
            checkError(text,expireMap);
            return String.valueOf(expireMap.get(text));
        }
        return null;
    }

    //错误次数计算
    private void checkError(String text, ExpireMap<String, Integer> expireMap) {
        if (StringUtils.isEmpty(expireMap.get(text))){
            expireMap.put(text,1);
        }else {
            Integer count = expireMap.get(text);
            expireMap.put(text,count++);
        }
    }


    //核对数据解密情况
    private void checkDecryptDate(String text, int lenth) {
        if (text == null || text.equals(""))
            throw  new IllegalArgumentException(FormatPreservingEncryptionErrorMessage.NULL_ENCRYPT_OUTPUT.toString());
        if (text.length() != lenth)
            throw  new IllegalArgumentException(String.format(String.valueOf(FormatPreservingEncryptionErrorMessage.INVALID_SIZE_ENCRYPT),text.length(),lenth));
    }

    private void check(String text, byte[] tweak) {
        if (text == null || tweak == null)
            throw  new IllegalArgumentException(FormatPreservingEncryptionErrorMessage.NULL_INPUT.toString());
        if (text.length() < lengthRange.min() || text.length() > lengthRange.max())
            throw  new IllegalArgumentException(FormatPreservingEncryptionErrorMessage.INVALID_SIZE + lengthRange.toString());
        if (tweak.length > lengthRange.max())
            throw  new IllegalArgumentException(FormatPreservingEncryptionErrorMessage.TWEAK_INVALID_SIZE.toString() + lengthRange.max());
    }
    private void check(int[] text, byte[] tweak) {
        if (text == null || tweak == null)
            throw  new IllegalArgumentException(FormatPreservingEncryptionErrorMessage.NULL_INPUT.toString());
        if (text.length < lengthRange.min() || text.length > lengthRange.max())
            throw  new IllegalArgumentException(FormatPreservingEncryptionErrorMessage.INVALID_SIZE + lengthRange.toString());
        if (tweak.length > lengthRange.max())
            throw  new IllegalArgumentException(FormatPreservingEncryptionErrorMessage.TWEAK_INVALID_SIZE.toString() + lengthRange.max());
    }

    private void check(Integer text, byte[] tweak) {
        if (text == null || tweak == null)
            throw  new IllegalArgumentException(FormatPreservingEncryptionErrorMessage.NULL_INPUT.toString());
        if (text.toString().length() < lengthRange.min() || text.toString().length() > lengthRange.max())
            throw  new IllegalArgumentException(FormatPreservingEncryptionErrorMessage.INVALID_SIZE + lengthRange.toString());
        if (tweak.length > lengthRange.max())
            throw  new IllegalArgumentException(FormatPreservingEncryptionErrorMessage.TWEAK_INVALID_SIZE.toString() + lengthRange.max());
    }


    private void check(Map text, byte[] tweak) {
        if (text == null || tweak == null)
            throw  new IllegalArgumentException(FormatPreservingEncryptionErrorMessage.NULL_INPUT.toString());
        if (text.toString().length() < lengthRange.min() || text.toString().length() > lengthRange.max())
            throw  new IllegalArgumentException(FormatPreservingEncryptionErrorMessage.INVALID_SIZE + lengthRange.toString());
        if (tweak.length > lengthRange.max())
            throw  new IllegalArgumentException(FormatPreservingEncryptionErrorMessage.TWEAK_INVALID_SIZE.toString() + lengthRange.max());
    }

    private void check(Long text, byte[] tweak) {
        if (text == null || tweak == null)
            throw  new IllegalArgumentException(FormatPreservingEncryptionErrorMessage.NULL_INPUT.toString());
        if (text.toString().length() < lengthRange.min() || text.toString().length() > lengthRange.max())
            throw  new IllegalArgumentException(FormatPreservingEncryptionErrorMessage.INVALID_SIZE + lengthRange.toString());
        if (tweak.length > lengthRange.max())
            throw  new IllegalArgumentException(FormatPreservingEncryptionErrorMessage.TWEAK_INVALID_SIZE.toString() + lengthRange.max());
    }
    private void check(ArrayList<String> text, byte[] tweak) {
        if (text == null || tweak == null)
            throw  new IllegalArgumentException(FormatPreservingEncryptionErrorMessage.NULL_INPUT.toString());

        if (text.size() < lengthRange.min() || text.size() > lengthRange.max())
            throw  new IllegalArgumentException(FormatPreservingEncryptionErrorMessage.INVALID_SIZE + lengthRange.toString());

        if (tweak.length > lengthRange.max())
            throw  new IllegalArgumentException(FormatPreservingEncryptionErrorMessage.TWEAK_INVALID_SIZE.toString() + lengthRange.max());
    }


    //Unicode转中文方法
    private static String unicodeToCn(String unicode) {
        /** 以 \ u 分割，因为java注释也能识别unicode，因此中间加了一个空格*/
        String[] strs = unicode.split("\\\\u");
        String returnStr = "";
        // 由于unicode字符串以 \ u 开头，因此分割出的第一个字符是""。
        for (int i = 1; i < strs.length; i++) {
            returnStr += (char) Integer.valueOf(strs[i], 16).intValue();
        }
        return returnStr;
    }

    //中文转Unicode
    private static String cnToUnicode(String cn) {
        char[] chars = cn.toCharArray();
        String returnStr = "";
        for (int i = 0; i < chars.length; i++) {
            returnStr += "\\u" + Integer.toString(chars[i], 16);
        }
        return returnStr;
    }
}
