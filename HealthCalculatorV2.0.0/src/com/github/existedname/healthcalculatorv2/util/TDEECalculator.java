package com.github.existedname.healthcalculatorv2.util;

public class TDEECalculator {
    /*      1. 私有静态成员变量        */
    // 1.1 活动系数对照表,由用户对比表格输入活动系数,计算 TDEE
    private static String[] activityCoeffComparisonTable = null;



    /*      2. 私有化构造器      */
    private TDEECalculator(){
        System.out.println( "不用创建对象,直接使用即可!" );
    }



    /*      3. 公开静态方法      */
    // 3.1 打印活动系数对照表( 根据 activityCoeffComparisonTable 数组 )
    public static void printCoeffTable(){
        if ( activityCoeffComparisonTable == null ) initializeCoeffTable();
        for ( String row : activityCoeffComparisonTable ){
            System.out.println( row );
        }
    }

    // 3.2 计算 TDEE = BMR * 活动系数
    public static double getTDEE( double bmr, double activityCoeff ){
        return bmr * activityCoeff;
    }

    // 3.3 main 方法用于测试( 方便对照修改表格格式 )
    public static void main( String[] args ){
        printCoeffTable();
    }



    /*      4. 私有静态方法      */
    /*  4.1 初始化数组   */
    // 4.1.1 初始化活动系数对照表( activityCoeffComparisonTable )
    private static void initializeCoeffTable(){
        activityCoeffComparisonTable = new String[] {
                "|活动系数|活动强度级别|    适用人群特征        |                日常活动举例                       |                运动训练举例                  |",
                "| 1.2  |  久坐不动  |几乎无日常活动,居家办公为主| 长时间阅读、看电视、伏案工作;日均步数 ＜ 3000 步       |                 无规律运动                  |",
                "| 1.375|  轻度活动  |每周 1-3 次低强度运动    |散步(30分钟/天)、轻松骑行、家务清洁;日均步数3000-6000步 |瑜伽、太极、低强度健身操( ≤ 2 次/周)             |",
                "| 1.55 |  中度活动  |每周 3-5 次中强度训练    |快走(5km/h)、慢跑、游泳(非竞技);日均步数6000-10000步  |健身房器械训练(45分钟/次)、羽毛球、篮球(3-5次/周)  |",
                "| 1.725|  高度活动  |每日高强度训练或体力劳动  |建筑工人、快递员、舞蹈演员;日均步数 ＞ 10000 步         |长跑(10km/次)、重量训练(1小时/天)、HIIT(≥5次/周) |",
                "| 1.9  |  超高强度  |职业运动员/重体力劳动者   |铁人三项训练、矿山作业、竞技体育集训;日均能耗 ＞ 3000 大卡| 马拉松备赛(每日 20km+ )、职业力量举( 2 小时/天)  |",
        };
    }
}