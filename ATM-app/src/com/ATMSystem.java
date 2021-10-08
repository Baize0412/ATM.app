package com;

import javax.swing.*;
import java.net.PasswordAuthentication;
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
                    login(accounts, sc);
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

    /**
     * \
     * 用户登录功能
     *
     * @param accounts
     */
    private static void login(ArrayList<Account> accounts, Scanner sc) {
        //系统要存在账户才可以登录
        if (accounts.size() == 0) {
            //没有任何账户
            System.out.println("当前没有任何账户");
            return;
        }


        while (true) {
            //让用户键盘录入卡号
            System.out.println("请输入卡号：");
            String CardId = sc.next();
            //根据卡号查找账户对象
            Account acc = getAccountByCarId(CardId, accounts);
            //判断账户对象是否存在
            if (acc != null) {
                while (true) {
                    //让用户继续输入密码
                    System.out.println("请输入密码");
                    String password = sc.next();
                    //判断密码是否正确
                    if (acc.getPassWord().equals(password)) {
                        //密码正确，登录成功
                        //显示登录后界面
                        System.out.println("恭喜您" + acc.getUsername() + "先生/女士成功进入系统，您的卡号是" + acc.getCardID());
                        //展示操作界面
                        showUserCommand(sc, acc, accounts);
                        return;     //结束登录方法

                    } else {
                        System.out.println("您的密码有误，请确认");
                    }
                }
            } else {
                System.out.println("对不起，不存在该账户");
            }
        }


    }

    private static void showUserCommand(Scanner sc, Account acc, ArrayList<Account> accounts) {
        while (true) {
            System.out.println("=================用户操作界面=================");
            System.out.println("1.查询账户");
            System.out.println("2.存款");
            System.out.println("3.取款");
            System.out.println("4.转账");
            System.out.println("5.修改密码");
            System.out.println("6.退出");
            System.out.println("7.注销账户");
            System.out.println("请输入操作命令");
            int command = sc.nextInt();
            switch (command) {
                case 1:
                    //查询账户
                    showAccount(acc);
                    break;
                case 2:
                    //存款
                    depositMoney(acc, sc);
                    break;
                case 3:
                    //取款
                    drawMoney(acc, sc);
                    break;
                case 4:
                    //转账
                    transferMoney(acc, accounts, sc);
                    break;
                case 5:
                    //修改密码
                    updatePassWord(acc,sc);
                    return;
                case 6:
                    //退出
                    System.out.println("欢迎下次光临！");
                    return; //结束当前的方法
                case 7:
                    //注销账户
                    //从集合中抹掉当前账户对象
                    accounts.remove(acc);
                    System.out.println("销户成功");
                    return;
                default:
                    System.out.println("命令输入有误");
            }
        }
    }

    private static void updatePassWord(Account acc, Scanner sc) {
        System.out.println("===================修改密码===================");
        while (true) {
            System.out.println("请您输入正确密码");
            String PassWord = sc.next();
            //判断密码是否正确
            if (acc.getPassWord().equals(PassWord)){
                while (true) {
                    //可以输入新密码了
                    System.out.println("请输入新密码");
                    String newPassWord = sc.next();
                    System.out.println("请确认密码");
                    String okNewPassWord = sc.next();
                    if (newPassWord.equals(okNewPassWord)){
                        //修改账户密码
                        acc.setPassWord(newPassWord);
                        return;
                    }else {
                        System.out.println("您输入的两次密码不一致");
                    }
                }

            }else {
                System.out.println("当前输入的密码不正确");
            }
        }

    }

    /**
     * 转账功能
     * @param acc
     * @param accounts
     * @param sc
     */
    private static void transferMoney(Account acc, ArrayList<Account> accounts, Scanner sc) {
        //判断系统中是否有两个账户及以上
        if (accounts.size() < 2) {
            System.out.println("对不起，系统中无其他账户，您不可以转账");
            return;
        }
        //判断自己账户有没有余额
        if (acc.getMoney() == 0) {
            System.out.println("对不起，，您的余额不足无法转账");
        }

        while (true) {
            //开始转账逻辑
            System.out.println("请您输入对方账户的卡号");
            String cardId = sc.next();
            Account account = getAccountByCarId(cardId, accounts);
            //判断这个账户对象是否存在，存在说明对方卡号输入正确
            if (account != null) {
                //判断这个账户对象是不是当前账户对象
                if (account.getCardID().equals(acc.getCardID())) {
                    //正在给自己转账
                    System.out.println("您不能为自己转账！");
                } else {
                    //进入姓氏确认
                    String name = "*" + account.getUsername().substring(1);   //截取：0姓，1名
                    System.out.println("请您确认[" + name + "]的姓氏");
                    String preName = sc.next();
                    //判断
                    if (account.getUsername().startsWith(preName)) {
                        //可以开始转账了
                        System.out.println("请您输入转账的金额：");
                        double money = sc.nextDouble();
                        //判断这个金额是否超过了自己的金额
                        if (money > acc.getMoney()) {
                            System.out.println("对不起，您要转账的金额太多了，您最多可转账" + acc.getMoney() + "元");
                        } else {
                            //转账
                            acc.setMoney(acc.getMoney() - money);
                            account.setMoney(account.getMoney() + money);
                            System.out.println("恭喜你，转账成功，已经为"+ account.getUsername()+"转账"+money+"元");
                            showAccount(acc);
                            return;
                        }
                    } else {
                        System.out.println("对不起，您输入的认证信息有误！");
                    }
                }

            } else {
                System.out.println("对不起，您输入的转账卡号有误");
            }
        }
    }

    private static void drawMoney(Account acc, Scanner sc) {
        System.out.println("===================取款操作===================");
        //判断账户是否足够100元
        if (acc.getMoney() != 100) {
            //足够
            while (true) {
                System.out.println("请您输入取款金额");
                double money = sc.nextDouble();
                //判断金额有没有超过当次限额
                if (money > acc.getQuotaMoney()) {
                    System.out.println("您当次取款超过限额，每次最多可以取" + acc.getQuotaMoney() + "元");
                } else {
                    //判断当前余额是否足够取钱
                    if (acc.getMoney() >= money) {
                        //可以取钱
                        acc.setMoney(acc.getMoney() - money);
                        System.out.println("恭喜您取款成功！！，当前账户剩余" + acc.getMoney() + "元");
                        return;
                    } else {
                        System.out.println("余额不足");
                    }
                }
            }
        } else {
            System.out.println("您的账户余额不足100元，请存款");
        }
    }

    /**
     * 存款
     *
     * @param acc
     */

    private static void depositMoney(Account acc, Scanner sc) {
        System.out.println("===================存款操作===================");
        System.out.println("请输入存款金额：");
        double money = sc.nextDouble();

        //直接把金额修改到账户对象的money属性中去
        acc.setMoney(acc.getMoney() + money);
        System.out.println("存款完成!");
        showAccount(acc);
    }

    private static void showAccount(Account acc) {
        System.out.println("=================当前账户详情=================");
        System.out.println("卡号:" + acc.getCardID());
        System.out.println("姓名:" + acc.getUsername());
        System.out.println("余额:" + acc.getMoney());
        System.out.println("当次限额:" + acc.getQuotaMoney());


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
        Account account = new Account(cardId, name, password, quotaMoney);

        //把对象添加到集合中去
        accounts.add(account);  //.add插入数据

        System.out.println("恭喜您开户成功，您的卡号是" + account.getCardID() + "请您妥善保管");
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
