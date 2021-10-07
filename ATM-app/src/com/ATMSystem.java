package com;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ATMSystem {
    public static void main(String[] args) {
        //准备系统需要的容器对象，用于存储账户对象
        ArrayList<Account> accounts = new ArrayList<>();    //ArrayLIst<E> 变量名

        showMain(accounts);     //首页
    }

    public static void showMain(ArrayList<Account> accounts) {
        System.out.println("=================欢迎进入首页=================");
        //准备系统首页（登陆，开户）
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("请您输入想做的操作");
            System.out.println("1.登录");
            System.out.println("2.开户");
            System.out.print("您可以输命令了：");

            int command = sc.nextInt();
            switch (command) {
                case 1:
                    //登录
                    logun(accounts,sc);
                    break;
                case 2:
                    //开户
                    register(accounts, sc);
                    break;
                default:
                    System.out.println("您当前输入的操作不被支持");
            }
        }
    }

    /**\
     * 用户登录功能
     * @param accounts
     */
    private static void logun(ArrayList<Account> accounts,Scanner sc) {
        //让用户键盘录入卡号
        System.out.println("请输入卡号：");
        String CardId = sc.next();
        Account account = new Account();
    }

    /**
     * 用户开户功能
     *
     * @param accounts 账户的集合对象
     */
    private static void register(ArrayList<Account> accounts, Scanner sc) {
        /*
        1.定义方法完成开户
        2.键盘录入姓名，密码，确认密码（验证两次输入的密码一致）if..else
        3.生成账户卡号,卡号由系统随机生成八位数字 for
        4.创建Account账户用于封装账户信息（姓名，密码，卡号）
        5.把Account账户类对象存入集合account中去
         */
        System.out.println("=================用户开户功能=================");
        //键盘录入 姓名 密码 确认密码
        System.out.println("请您输入开户名：");
        String name = sc.next();    //开户名

        String password = "";   //密码
        while (true) {

            System.out.println("请您输入开户密码：");
            password = sc.next();

            System.out.println("请您确认密码：");
            String okPassword = sc.next();

            //判断两次输入的密码是否一致
            if (okPassword.equals(password)) {   //变量名.equals(变量名）  判断两个String变量是否相等
                break;
            } else {
                System.out.println("两次密码必须一致！");
            }
        }
        System.out.println("请您输入当次限额：");
        double quotaMoney = sc.nextDouble();
        //生成一个随机的账户卡号，并且不能与其他账户卡号重复
        String cardId = createCardID(accounts);

        //创建一个账户对象封装账户信息
        //public Account(String cardID, String username, int passWord, double money, double quotaMoney)
        Account account = new Account(cardId, name, password,quotaMoney );

        //把对象添加到集合中去
        accounts.add(account);  //.add插入数据

        System.out.println("恭喜您开户成功，您的卡号是"+account.getCardID()+"请您妥善保管");
    }

    private static String createCardID(ArrayList<Account> accounts) {
        while (true) {
            //生成八位随机的数字代表卡号
            String cardId = "";
            Random r = new Random();
            for (int i = 0; i < 8; i++) {
                cardId += r.nextInt(10);    //每生成一个随机数加在变量：cardId上（卡号）
            }

            //判断卡号是否重复了
            Account acc = getAccountByCarId(cardId, accounts);
            if (acc == null) {
                //说明当前卡号没有重复
                return cardId;
            } else {
                //说明卡号重复了
            }
        }

    }

    public static Account getAccountByCarId(String carId, ArrayList<Account> accounts) {
        //根据卡号查找账户对象
        for (int i = 0; i < accounts.size(); i++) {         //遍历集合中的对象
            Account acc = accounts.get(i);
            if (acc.getCardID().equals(carId)) {
                return acc;
            }

        }
        return null;    //查无此户，说明卡号没有重复了
    }

}
