package com.anji.src.fpe.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnotationsReflection {
    /*个人财产信息*/
    //银行账号
    @JSONField(
            name = "blankCards"
    )
    private String blankCard;
    //鉴别信息口令
    @JSONField(
            name = "authenticationInformationPassword"
    )
    private String authenticationInformationPassword;
    //存款信息-资金数量
    @JSONField(
            name = "howMuchMoney"
    )
    private String howMuchMoney;
    //支付收款记录
    @JSONField(
            name = "paymentCollectionRecord"
    )
    private String paymentCollectionRecord;
    //房产信息
    @JSONField(
            name = "realEstateInformation"
    )
    private String realEstateInformation;
    //信贷记录
    @JSONField(
            name = "creditRecord"
    )
    private String creditRecord;
    //征信信息
    @JSONField(
            name = "creditInformation"
    )
    private String creditInformation;
    //交易和消费记录
    @JSONField(
            name = "transactionsConsumptionRecords"
    )
    private String transactionsConsumptionRecords;
    //流水记录
    @JSONField(
            name = "billingRecords"
    )
    private String billingRecords;
    //虚拟货币
    @JSONField(
            name = "birtualCurrency"
    )
    private String birtualCurrency;
    //虚拟交易
    @JSONField(
            name = "virtualTrading"
    )
    private String virtualTrading;
    //游戏类兑换码
    @JSONField(
            name = "gameExchangeCode"
    )
    private String gameExchangeCode;

    /*个人健康生理信息*/
    //个人因生病医治等产生的相关记录
    @JSONField(
            name = "personalIllnessRecords"
    )
    private String personalIllnessRecords;
    //病症
    @JSONField(
            name = "symptoms"
    )
    private String symptoms;
    //住院志
    @JSONField(
            name = "hospitalVolunteers"
    )
    private String hospitalVolunteers;
    //医嘱单
    @JSONField(
            name = "doctorAdviceSingle"
    )
    private String  doctorAdviceSingle;
    //检验报告
    @JSONField(
            name = "inspectionReport"
    )
    private String inspectionReport;
    //手术及麻醉记录
    @JSONField(
            name = "surgicalRecords"
    )
    private String surgicalRecords;
    //护理记录
    @JSONField(
            name = "nursingRecord"
    )
    private String nursingRecord;
    //用药记录
    @JSONField(
            name = "medicalRecords"
    )
    private String medicalRecords;
    //药物食物过敏信息
    @JSONField(
            name = "drugFoodAllergyInformation"
    )
    private String drugFoodAllergyInformation;
    //生育信息
    @JSONField(
            name = "birthInformation"
    )
    private String birthInformation;
    //以往病史
    @JSONField(
            name = "pastMedicalHistory"
    )
    private String pastMedicalHistory;
    //诊治情况
    @JSONField(
            name = "diagnosisTreatmentSituation"
    )
    private String diagnosisTreatmentSituation;
    //家族病史
    @JSONField(
            name = "familyHistory"
    )
    private String familyHistory;
    //现病史
    @JSONField(
            name = "hpi"
    )
    private String hpi;
    //传染病史
    @JSONField(
            name = "historyInfection"
    )
    private String historyInfection;

    /*个人生物识别信息*/
    //个人基因
    @JSONField(
            name = "individualGenes"
    )
    private String individualGenes;
    //指纹
    @JSONField(
            name = "fingerprint"
    )
    private String fingerprint;
    //声纹
    @JSONField(
            name = "voiceprints"
    )
    private String voiceprint;
    //掌纹
    @JSONField(
            name = "palmprint"
    )
    String palmprint;
    //耳廓
    @JSONField(
            name = "pinna"
    )
    String pinna;
    //虹膜
    @JSONField(
            name = "iris"
    )
    String iris;
    //面部识别特征
    @JSONField(
            name = "facialRecognitionFeatures"
    )
    String facialRecognitionFeatures;

    /*个人身份信息*/
    //身份证
    @JSONField(
            name = "iDCard"
    )
    String iDCard;
    //军官证
    @JSONField(
            name = "militaryCard"
    )
    String militaryCard;
    //护照
    @JSONField(
            name = "passPort"
    )
    String passPort;
    //驾驶证
    @JSONField(
            name = "driverLicense"
    )
    String driverLicense;
    //工作证
    @JSONField(
            name = "employeeCard"
    )
    String employeeCard;
    //社保卡
    @JSONField(
            name = "socialSecurityCard"
    )
    String socialSecurityCard;
    //居住证
    @JSONField(
            name = "residencePermit"
    )
    String residencePermit;

    /*其他信息*/
    //性取向
    @JSONField(
            name = "sexualOrientation"
    )
    String sexualOrientation;
    //婚史
    @JSONField(
            name = "historyMarriage"
    )
    String historyMarriage;
    //宗教信仰
    @JSONField(
            name = "religiousBeliefs"
    )
    String religiousBeliefs;
    //未公开的违法犯罪记录
    @JSONField(
            name = "unpublishedCriminalRecord"
    )
    String unpublishedCriminalRecord;
    //通信记录和内容
    @JSONField(
            name = "communicationRecords"
    )
    String communicationRecords;
    //通讯录
    @JSONField(
            name = "addressBook"
    )
    String addressBook;
    //好友列表
    @JSONField(
            name = "friendsList"
    )
    String friendsList;
    //群组列表
    @JSONField(
            name = "groupList"
    )
    String groupList;
    //行踪轨迹
    @JSONField(
            name = "whereaboutsTrajectory"
    )
    String whereaboutsTrajectory;
    //网页浏览记录
    @JSONField(
            name = "webBrowsingRecord"
    )
    String webBrowsingRecord;
    //住宿信息
    @JSONField(
            name = "lodgingInformation"
    )
    String lodgingInformation;
    //精准定位信息
    @JSONField(
            name = "locationInformation"
    )
    String locationInformation;
}
