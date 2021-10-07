package com;    //账户类

public class Account {
    private String cardID;  //卡号
    private String username;    //客户名称
    private String passWord;   //密码
    private double money;   //余额
    private double quotaMoney;  //单次取现额度

    public Account() {
    }

    public Account(String cardID, String username, String passWord, double quotaMoney) {
        this.cardID = cardID;
        this.username = username;
        this.passWord = passWord;
        this.money = money;
        this.quotaMoney = quotaMoney;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }


    public double getQuotaMoney() {
        return quotaMoney;
    }

    public void setQuotaMoney(double quotaMoney) {
        this.quotaMoney = quotaMoney;
    }
}
