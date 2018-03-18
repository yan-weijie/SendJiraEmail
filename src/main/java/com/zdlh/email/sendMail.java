package com.zdlh.email;

import org.testng.annotations.Test;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Created by Administrator on 2018/1/12.
 */
public class sendMail {
    private final static String host = ""; //smtp的服务器
    private final static String formName = "";//你的邮箱
    private final static String password = ""; //授权码
    private final static String replayAddress = ""; //你的邮箱

    private MailHtmlModle mailHtmlModle = new MailHtmlModle();


    /**
     * 设置发送邮件为HTML格式
     * @param info
     * @throws Exception
     */
    public static void sendHtmlMail(MailInfo info) throws Exception {
        info.setHost(host);
        info.setFormName(formName);
        info.setFormPassword(password);   //网易邮箱的授权码~不一定是密码
        info.setReplayAddress(replayAddress);

        Message message = getMessage(info);

        Multipart mainPart = new MimeMultipart();

        BodyPart html = new MimeBodyPart();

        html.setContent(info.getContent(), "text/html; charset=utf-8");

        mainPart.addBodyPart(html);

        message.setContent(mainPart);

        Transport.send(message);


    }

    @Test
    public void send () {
        String mail = "xxx@163.com";//收件人
        String title = "中大联合项目测试报告-" + nowDate();
        String content = mailHtmlModle.htmlMoble();
        //System.out.println(content);

        MailInfo info= new MailInfo();
        info.setToAddress(mail);
        info.setSubject(title);
        info.setContent(content);

        try {
            //MailSendUtil.sendTextMail(info);
            sendHtmlMail(info);
        } catch (Exception e) {
            System.out.print("'"+title+"'的邮件发送失败！");
            e.printStackTrace();
        }
    }

    /**
     * 发送带HTML格式的邮件
     * @param info
     * @return
     * @throws Exception
     */
    public static Message getMessage(MailInfo info) throws Exception{
        final Properties p = System.getProperties();
        p.setProperty("mail.smtp.host", info.getHost());
        p.setProperty("mail.smtp.auth", "true");
        p.setProperty("mail.smtp.user", info.getFormName());
        p.setProperty("mail.smtp.pass", info.getFormPassword());

        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session session = Session.getInstance(p, new Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(p.getProperty("mail.smtp.user"),p.getProperty("mail.smtp.pass"));
            }
        });

        //session.setDebug(true);

        Message message = new MimeMessage(session);
        //消息发送主题
        message.setSubject(info.getSubject());
        //接收消息的人
        message.setReplyTo(InternetAddress.parse(info.getReplayAddress()));
        //消息发送者
        message.setFrom(new InternetAddress(p.getProperty("mail.smtp.user"), "中大测试组"));

        if (info.getToAddress() != null && info.getToAddress().trim().length() > 0) {
            String[] arr = info.getToAddress().split(",");
            int receiverCount = arr.length;
            if (receiverCount > 0) {
                InternetAddress[] toAddress = new InternetAddress[receiverCount];
                for (int i = 0; i < receiverCount; i++) {
                    toAddress[i] = new InternetAddress(arr[i]);
//                            System.out.println(toAddress[i]);
                }
                message.setRecipients(Message.RecipientType.TO, toAddress);
            }
        }

        //创建邮箱接受者地址，并设置到邮件消息中
        //message.setRecipient(Message.RecipientType.TO, new InternetAddress(info.getToAddress()));
        //设置消息发送时间
        message.setSentDate(new Date());

        return message;
    }

    /**
     * 获取当前系统时间
     * @return
     */
    public String nowDate() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        return dateNowStr;
    }
}
