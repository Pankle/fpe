# 在Java中保存格式的加密实现

保持格式加密(FPE)是为不一定是二进制的数据而设计的。特别是，给定任何有限的符号集(如十进制数字)，FPE方法将转换格式化为符号序列的数据，这样加密的数据形式具有与原始数据相同的格式(包括长度)。因此，一个fpe加密的SSN将是一个由9位十进制数字组成的序列。
实现NIST批准的格式保存加密(FPE)在Java。


## 安装

您可以从中央Maven存储库中提取它:

```xml
<dependency>
  <groupId>com.anji.src</groupId>
  <artifactId>format-preserving-encryption</artifactId>
  <version>1.0.0</version>
</dependency>
```

## 特性

* 开箱即用的算法和一个简单的API
* 自定义域(可以使用任何字符子集)
* 自定义伪随机函数 (加密算法)

## 示例使用
        String json ="{\"blankCards\":\""+i+"\",\"Voiceprints\":\"asdasdqweqw213123123\",\"WebBrowsingRecord\":\"上海市杨浦区江浦公园1101号\",\"WebBrowsingInfo\":\"1101php\",\"WebBrowsingInfo\":\"1102php\"}";
        JSONObject jsonObject =  JSON.parseObject(json);
            try {
            AnnotationsReflection annotationsReflection = JSON.parseObject(json, AnnotationsReflection.class);
            Class<? extends AnnotationsReflection> aClass = annotationsReflection.getClass();
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field field:declaredFields){
                field.setAccessible(true);
                if (field.get(annotationsReflection) !=null && !field.get(annotationsReflection).equals("")){
                    String encrypt = Encrypt(field.get(annotationsReflection).toString());
                    jsonObject.put(field.getName(),encrypt);
                }
            }
              System.out.println(jsonObject);
        } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
### 输入数据

在创建保存格式的加密对象时，输入数据应满足以下要求:
- radix ∈ [ 2 .. 2<sup>16</sup> ]
- radix<sup>minlen</sup >= 100
- 2 <= minlen < maxlen <= 2^32
- key is an AES Key, must be 16, 24 or 32 bytes length


### 使用
加密
 String encrypt = Encrypt(field.get(annotationsReflection).toString());
解密
 String decrypt = Decrypt(field.get(algorithmModel).toString());
一个字段多内容加密(用于sql查询)
algorithmMethon(jsonObject);


### 问题处理
1、加密算法整合 (目前可选择但是没有整合全部)
2、sql转换(低级api:传入指定值、高级api:传入sql正则匹配) 目前低级api开发完成
3、异常处理(数据如果加密成功,原路返回。加密失败抛出异常,加密超过三次就保存原始数据)√
4、数据库查询结果批量解密 √
5、数据加密错误情况(加密内容为空,加密长度与原文长度不统一，原始数据的长度小于2) 这些日志数据落地√
6、中文加密只包含简体中文√
7、数字加密只包含阿拉伯数字√


### 法定需要脱敏的数据
依据《GB/T 35273-2020 信息安全技术 个人信息安全规范》标准中对个人敏感信息判定参考如下：
个人敏感信息是指一旦泄露、非法提供或滥用可能危害人身和财产安全，极易导致个人名誉、身心健康受到损害或歧视性待遇等的个人信息。通常情况下，14岁以下（含）
儿童的个人信息和涉及自然人隐私的信息属于个人敏感信息。

个人财产信息：银行账户、鉴别信息口令 、存款信息（包括资金数量、支付收款记录等）、房产信息、信贷记录、征信信息、交易和消费记录、流水记录等，以及虚拟货币、虚拟交易、
游戏类兑换码等虚拟财产信息
个人健康生理信息：个人因生病医治等产生的相关记录，如病症、住院志、医嘱单、检验报告、手术及麻醉记录、护理记录、用药记录、药物食物过敏信息、生育信息、以往病史、
诊治情况、家族病史、现病史、传染病史等
个人生物识别信息：个人基因、指纹、声纹、掌纹、耳廓、虹膜、面部识别特征等
个人身份信息：身份证、军官证、护照、驾驶证、工作证、社保卡、居住证等
其他信息：性取向、婚史、宗教信仰、未公开的违法犯罪记录、通信记录和内容、通讯录、好友列表、群组列表、行踪轨迹、网页浏览记录、住宿信息、精准定位信息等
