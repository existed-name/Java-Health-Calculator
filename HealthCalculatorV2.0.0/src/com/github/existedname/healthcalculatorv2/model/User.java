package com.github.existedname.healthcalculatorv2.model;

public class User extends UserBodyInfo {
    /*      1. 成员变量        */
    private String name;
    private String gender;
    private int age;
    private int id;



    /*      2. 构造器,先父类后子类      */
    // 2.1 无参数构造器
    public User(){
        super();
    }

    // 2.2 有参数构造器
        //  全参 + 基本身体指标( 体重,身高 )
    public User( String name, String gender, int age, int id, double weightKg, double heightCm ){
        super( weightKg, heightCm );
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.id = id;
    }
        //  省略 name, id
    public User( String gender, int age, double weightKg, double heightCm ){
        // 子类构造器首行只能调用 this() 或 super()
        this( "", gender, age, 0, weightKg, heightCm );
    }



    /*      3. setter      */
    public void setName( String name ) { this.name = name; }
    public void setGender( String gender ) { this.gender = gender; }
    public void setAge( int age ) { this.age = age; }
    public void setId( int id ) { this.id = id; }



    /*      4. getter       */
    public String getName() {
        return name;
    }
    public String getGender() {
        return gender;
    }
    public int getAge() {
        return age;
    }
    public int getId() {
        return id;
    }



    /*      5. 公有方法      */
    @Override
    public String toString(){
        return "user { " +
                "\n\tname = " + this.name +
                "\n\tgender = " + this.gender +
                "\n\tid = " + this.id +
                "\n\tweightKg = " + this.getWeightKg() +
                "\n\theightCm = " + this.getHeightCm() +
                "\n\twaistCircumCm = " + this.getWaistCircumCm() +
                "\n\tarmCircumCm = " + this.getArmCircumCm() +
                "\n\tneckCircumCm = " + this.getNeckCircumCm() +
                "\n\thipCircumCm = " + this.getHeightCm() +
                "\n\tBMI = " + this.getBMI() +
                "\n\tBMR = " + this.getBMR() +
                "\n\tBFR = " + this.getBFR() +
                "\n\tBSA = " + this.getBSA() +
                "\n\tBRI = " + this.getBRI() +
                "\n\tWHR = " + this.getWHR() +
                "}\n";
    }
}
