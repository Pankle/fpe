package com.anji.src.fpe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor                 //无参构造
/**
 * Created by Pank on 2020/06/01
 */
public class AlgorithmModel {
    byte [] key;
    byte [] tweak;
    String algorithmType;

    /*个人财产信息*/
    //银行账号
    String BlankCard;
    //鉴别信息口令
    String AuthenticationInformationPassword;
    //存款信息-资金数量
    String HowMuchMoney;
    //支付收款记录
    String PaymentCollectionRecord;
    //房产信息
    String RealEstateInformation;
    //信贷记录
    String CreditRecord;
    //征信信息
    String CreditInformation;
    //交易和消费记录
    String TransactionsConsumptionRecords;
    //流水记录
    String BillingRecords;
    //虚拟货币
    String VirtualCurrency;
    //虚拟交易
    String VirtualTrading;
    //游戏类兑换码
    String GameExchangeCode;

    /*个人健康生理信息*/
    //个人因生病医治等产生的相关记录
    String PersonalIllnessRecords;
    //病症
    String Symptoms;
    //住院志
    String HospitalVolunteers;
    //医嘱单
    String  DoctorAdviceSingle;
    //检验报告
    String InspectionReport;
    //手术及麻醉记录
    String SurgicalRecords;
    //护理记录
    String NursingRecord;
    //用药记录
    String MedicalRecords;
    //药物食物过敏信息
    String DrugFoodAllergyInformation;
    //生育信息
    String BirthInformation;
    //以往病史
    String PastMedicalHistory;
    //诊治情况
    String DiagnosisTreatmentSituation;
    //家族病史
    String FamilyHistory;
    //现病史
    String hpi;
    //传染病史
    String HistoryInfection;

    /*个人生物识别信息*/
    //个人基因
    String IndividualGenes;
    //指纹
    String Fingerprint;
    //声纹
    String Voiceprint;
    //掌纹
    String Palmprint;
    //耳廓
    String Pinna;
    //虹膜
    String Iris;
    //面部识别特征
    String FacialRecognitionFeatures;

    /*个人身份信息*/
    //身份证
    String ID_Card;
    //军官证
    String MilitaryCard;
    //护照
    String Passport;
    //驾驶证
    String DriverLicense;
    //工作证
    String EmployeeCard;
    //社保卡
    String SocialSecurityCard;
    //居住证
    String ResidencePermit;

    /*其他信息*/
    //性取向
    String SexualOrientation;
    //婚史
    String HistoryMarriage;
    //宗教信仰
    String ReligiousBeliefs;
    //未公开的违法犯罪记录
    String UnpublishedCriminalRecord;
    //通信记录和内容
    String CommunicationRecords;
    //通讯录
    String AddressBook;
    //好友列表
    String FriendsList;
    //群组列表
    String GroupList;
    //行踪轨迹
    String WhereaboutsTrajectory;
    //网页浏览记录
    String WebBrowsingRecord;
    //住宿信息
    String LodgingInformation;
    //精准定位信息
    String LocationInformation;


}
