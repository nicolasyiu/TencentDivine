package com.blue.getdata.bean;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetDateDetalUtils {

    public Object getDate(String strURL, int model) throws Exception {
        TodayLuck today = new TodayLuck();
        WeekLuck week = new WeekLuck();
        MonthLuck month = new MonthLuck();
        YearLuck year = new YearLuck();

        URL url = new URL(strURL);
        System.out.println("\n\n\n" + url);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        InputStreamReader input = new InputStreamReader(
                httpConn.getInputStream(), "GBK");
        BufferedReader bufReader = new BufferedReader(input);
        String line = "";
        StringBuilder contentBuf = new StringBuilder();
        while ((line = bufReader.readLine()) != null) {
            contentBuf.append(line);
        }
        String buf = contentBuf.toString();


        switch (model) {
            case 1:// 今日
                Pattern p = Pattern
                        .compile("<p class=\"p1\" id=\"maintext\">((.(?!p>))*)</p>");
                Matcher m = p.matcher(buf);
                while (m.find()) {
                    String msg = new String(m.group(1).getBytes("UTF-8"));
                    // today.setOverviews("今日概述：" + msg);
                    today.setOverviews(msg);
                }
                Pattern p1 = Pattern
                        .compile("<div class=\"timu\">((.(?!div>))*)</div>");
                Matcher m1 = p1.matcher(buf);
                int num = 0;
                while (m1.find()) {
                    getImageUrl(m1.group(1), num, today);
                    num++;
                }

                Pattern p2 = Pattern
                        .compile("<span class=\"span2\">((.(?!span>))*)</span>");
                Matcher m2 = p2.matcher(buf);
                int n = 0;
                while (m2.find()) {
                    switch (n) {
                        case 0:
                            String[] guirens = m2.group(1).split("：");
                            today.setGuiren(guirens[1]);
                            break;
                        case 1:
                            String[] colors = m2.group(1).split("：");
                            today.setColor(colors[1]);
                            break;
                        case 2:
                            String[] numbers = m2.group(1).split("：");
                            today.setNumber(numbers[1]);
                            break;
                        case 3:
                            String[] healths = m2.group(1).split("：");
                            today.setHealth(healths[1]);
                            break;
                        default:
                            break;
                    }
                    n++;
                }
                return today;
            // break;
            case 2:// 明日
                Pattern p6 = Pattern
                        .compile("\"TomorrowFID\":\"(.*)\",\"WeekFID\"");
                Matcher m6 = p6.matcher(buf);
                int tomorrowFID = 0;
                while (m6.find()) {
                    tomorrowFID = Integer.parseInt(m6.group(1));
                }
                int urlPage = (int) Math.floor(tomorrowFID / 1000);
                String tomorrowUrl = "http://data.astro.qq.com/dayastro/" + urlPage
                        + "/" + tomorrowFID + "/index.shtml";
                TodayLuck t = (TodayLuck) getDate(tomorrowUrl, 1);
                String tomorrowmsg = t.getOverviews();
                tomorrowmsg = tomorrowmsg.replace("今日概述", "明日概述");
                t.setOverviews(tomorrowmsg);
                return t;
            // break;
            case 3:// 本周
                Pattern p3 = Pattern
                        .compile("<p class=\"p1\">((.(?!p>))*)</p>");
                Matcher m3 = p3.matcher(buf);

                while (m3.find()) {
                    String weekMsg = new String(m3.group(1).getBytes("UTF-8"));

                    String[] weekMsgs = weekMsg.split("<br/>");
                    for (int i = 0; i < weekMsgs.length; i++) {
                        switch (i) {
                            case 0:
                                String love = weekMsgs[i].substring(3, weekMsgs[i].length());
                                love = love.split("href")[0].replace("\n", "").trim();
                                if (love != null && !love.equals("")) {
                                    week.setLoveMsg(love);
                                    System.out.println("week:" + week);
                                }
                                break;
                            case 1:
                                String work = weekMsgs[i].substring(3, weekMsgs[i].length());
                                week.setWorkMsg(work);
                                break;
                            case 2:
                                String apply = weekMsgs[i].substring(3, weekMsgs[i].length());
                                week.setApplyMsg(apply);
                                break;
                            case 3:
                                String caiyun = weekMsgs[i].substring(3, weekMsgs[i].length());
                                week.setCaiYunMsg(caiyun);
                                break;
                            case 4:
                                String health = weekMsgs[i].substring(3, weekMsgs[i].length());
                                week.setHealthMsg(health);
                                break;

                            default:
                                break;
                        }
                    }
                    break;
                }
                return week;
            // break;
            case 4:// 本月
                int num4 = 0;
                Pattern p4 = Pattern.compile(" <p>((.(?!p>))*)</p>");
                Matcher m4 = p4.matcher(buf);
                while (m4.find()) {
                    switch (num4) {
                        case 1:
                            String zonghe = m4.group(1).replaceAll("<br/>", "");
                            month.setZonghe(zonghe);
                            break;
                        case 2:
                            String love = m4.group(1).replaceAll("<br/>", "");
                            month.setLove(love);
                            break;
                        case 3:
                            String work = m4.group(1).replaceAll("<br/>", "");
                            month.setWork(work);
                            break;
                        case 4:
                            String health = m4.group(1).replaceAll("<br/>", "");
                            month.setHealth(health);
                            break;
                        case 5:
                            String caiyun = m4.group(1).replaceAll("<br/>", "");
                            month.setCaiyun(caiyun);
                            break;

                        default:
                            break;

                    }
                    num4++;
                }
                return month;

            // break;
            case 5:// 本年
                Pattern p5 = Pattern
                        .compile("<p class=\"p1\">((.(?!p>))*)</p>");
                Matcher m5 = p5.matcher(buf);
                while (m5.find()) {
                    String yearMsg = new String(m5.group(1).getBytes("UTF-8"));
                    String[] msgs = yearMsg.split("<br/>");
                    if (msgs.length > 2 && msgs[2].equals("事业运：")) {
                        if (msgs.length > 1)
                            year.setZongHeMsg(msgs[1]);
                        if (msgs.length > 5)
                            year.setLoveMsg(msgs[5]);
                        if (msgs.length > 3)
                            year.setWorkMsg(msgs[3]);
                        if (msgs.length > 7)
                            year.setCaiyunMsg(msgs[7]);
                    } else {
                        if (msgs.length > 0)
                            year.setZongHeMsg(msgs[0]);
                        if (msgs.length > 4)
                            year.setLoveMsg(msgs[4]);
                        if (msgs.length > 2)
                            year.setWorkMsg(msgs[2]);
                        if (msgs.length > 6)
                            year.setCaiyunMsg(msgs[6]);
                    }
                    break;
                }
                System.out.println(year);
                return year;
            // break;

            default:
                break;
        }
        return null;
    }

    // 获取今日运势的运势信息(主要获取有图片的)
    public void getImageUrl(String str, int num, TodayLuck today) {
        Pattern p1 = Pattern.compile("<img src=\"((.(?! />))*)\" />");
        Matcher m1 = p1.matcher(str);
        ArrayList<String> imgUrl = new ArrayList<String>();
        while (m1.find()) {
            imgUrl.add(m1.group(1));
        }
        String msg = yunshi(imgUrl);
        switch (num) {
            case 0:
                today.setTitleZonghe(msg);
                break;
            case 1:
                today.setTitleLove(msg);
                break;
            case 2:
                today.setTitleWoke(msg);
                break;
            case 3:
                today.setTitleCaiyun(msg);
                break;
            default:
                break;
        }
    }

    // 今日运势将五角星换成%
    public static String yunshi(ArrayList<String> list) {
        String imgUrl = "http://mat1.gtimg.com/astro/2014zlk/jrys/xing1.jpg";
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(imgUrl)) {
                count += 1;
            }
        }
        int b = (int) ((count / (double) list.size()) * 100);
        return b + "%";
    }

    public static void main(String[] args) {
        GetDateDetalUtils utils = new GetDateDetalUtils();
        // 今日运势
        // try {
        // TodayLuck t = (TodayLuck) getDate(
        // "http://data.astro.qq.com/dayastro/77/77782/index.shtml", 1);
        // System.out.println(t);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // 本周
        // try {
        // WeekLuck w = (WeekLuck) getDate(
        // "http://data.astro.qq.com/weekastro/12/12268/index.shtml",3);
        // System.out.println(w);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // 本月
//		 try {
//		 MonthLuck month = (MonthLuck) utils.getDate(
//		 "http://data.astro.qq.com/monthastro/0/929/index.shtml", 4);
//		 System.out.println(month);
//		 } catch (Exception e) {
//		 e.printStackTrace();
//		 }
        // 本年

//		 try {
//		 YearLuck year = (YearLuck)
//				 utils.getDate("http://data.astro.qq.com/yearastro/0/98/index.shtml", 5);
//		 //System.out.println(year);
//		 } catch (Exception e) {
//		 e.printStackTrace();
//		 }
        // 明日
        // try {
        // TodayLuck t =
        // (TodayLuck)getDate("http://data.astro.qq.com/infopic/1/todayAstro.js",
        // 2);
        // System.out.println(t);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }

    }

}
