package com.github.existedname.healthcalculatorv2.model;

public class UserBodyInfo {
    /*      1. 私有非静态成员变量        */
    private double weightKg; // 体重 kg
    private double heightCm; // 身高 cm
    private double waistCircumCm; // 腰围 cm
    private double armCircumCm; // 臂围 cm
    private double neckCircumCm; // 颈围 cm
    private double hipCircumCm; // 臀围 cm
    private double BMI; // body mass index 身体质量指数( 体重指数 )
    private double BMR; // Basal Metabolic Rate 基础代谢率( 通俗说的 BMR 是以 day 为单位时间,即 1 天的基础代谢 )
    private double activityCoeff; // 活动系数
    private double TDEE; // total daily energy expedite 每日总消耗热量
    private double BFR; // body fat rate 体脂率
    private double BSA; // body surface area 体表面积
    private double BRI; // body roundness index 身体圆度指数
    private double WHR; // Waist-Hip Ratio 腰臀比



    /*      2. 构造器        */
    /*  2.1 无参数构造器  */
    public UserBodyInfo(){ }


    /*  2.2 有参数构造器  */
    // 2.2.1 只保留基础的体重、身高
    public UserBodyInfo(double weightKg, double heightCm ){
        this.weightKg = weightKg;
        this.heightCm = heightCm;
    }



    /*      3. setter        */
    public void setWeightKg( double weightKg ) { this.weightKg = weightKg; }
    public void setHeightCm( double heightCm ) { this.heightCm = heightCm; }
    public void setWaistCircumCm(double waistCircumCm) {
        this.waistCircumCm = waistCircumCm;
    }
    public void setArmCircumCm(double armCircumCm) {
        this.armCircumCm = armCircumCm;
    }
    public void setNeckCircumCm(double neckCircumCm) {
        this.neckCircumCm = neckCircumCm;
    }
    public void setHipCircumCm(double hipCircumCm) {
        this.hipCircumCm = hipCircumCm;
    }
    public void setBMI(double BMI) {
        this.BMI = BMI;
    }
    public void setBMR(double BMR) {
        this.BMR = BMR;
    }
    public void setActivityCoeff(double activityCoeff) {
        this.activityCoeff = activityCoeff;
    }
    public void setTDEE(double TDEE) {
        this.TDEE = TDEE;
    }
    public void setBFR(double BFR) {
        this.BFR = BFR;
    }
    public void setBSA(double BSA) {
        this.BSA = BSA;
    }
    public void setBRI(double BRI) {
        this.BRI = BRI;
    }
    public void setWHR(double WHR) {
        this.WHR = WHR;
    }



    /*      4. getter        */
    public double getWeightKg() { return this.weightKg; }
    public double getHeightCm() { return heightCm; }
    public double getWaistCircumCm() {
        return waistCircumCm;
    }
    public double getArmCircumCm() {
        return armCircumCm;
    }
    public double getNeckCircumCm() {
        return neckCircumCm;
    }
    public double getHipCircumCm() {
        return hipCircumCm;
    }
    public double getBMI() {
        return BMI;
    }
    public double getBMR() {
        return BMR;
    }
    public double getActivityCoeff() {
        return activityCoeff;
    }
    public double getTDEE() {
        return TDEE;
    }
    public double getBFR() {
        return BFR;
    }
    public double getBSA() {
        return BSA;
    }
    public double getBRI() {
        return BRI;
    }
    public double getWHR() {
        return WHR;
    }
}
