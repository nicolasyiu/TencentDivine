package com.blue.getdata.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.blue.getdata.bean.Constellation;
import com.blue.getdata.bean.MonthLuck;
import com.blue.getdata.bean.Times;
import com.blue.getdata.bean.TodayLuck;
import com.blue.getdata.bean.WeekLuck;
import com.blue.getdata.bean.YearLuck;

public class GetDateDetalUtils {

	public Object getDate(String strURL, int model) throws Exception {
		TodayLuck today = new TodayLuck();
		WeekLuck week = new WeekLuck();
		MonthLuck month = new MonthLuck();
		YearLuck year = new YearLuck();

		URL url = new URL(strURL);
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
					.compile("<p class=\"p1\">((.(?!p>))*)<br/><br/><br/></p>");
			Matcher m3 = p3.matcher(buf);
			while (m3.find()) {
				String weekMsg = new String(m3.group(1).getBytes("UTF-8"));
				String[] weekMsgs = weekMsg.split("<br/>");
				for (int i = 0; i < weekMsgs.length; i++) {
					switch (i) {
					case 0:
						String love = weekMsgs[i].substring(3, weekMsgs[i].length());
						week.setLoveMsg(love);
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
					String zonghe = m4.group(1).replaceAll("<br/>","");
					month.setZonghe(zonghe);
					break;
				case 2:
					String love = m4.group(1).replaceAll("<br/>","");
					month.setLove(love);
					break;
				case 3:
					String work = m4.group(1).replaceAll("<br/>","");
					month.setWork(work);
					break;
				case 4:
					String health = m4.group(1).replaceAll("<br/>","");
					month.setHealth(health);
					break;
				case 5:
					String caiyun = m4.group(1).replaceAll("<br/>","");
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
					.compile("<p class=\"p1\">((.(?!p>))*)<br/></p>");
			Matcher m5 = p5.matcher(buf);
			while (m5.find()) {
				String yearMsg = new String(m5.group(1).getBytes("UTF-8"));
				String[]msgs = yearMsg.split("<br/>");	
				ArrayList<String> list = new ArrayList<String>();
				for(int i = 0;i<msgs.length;i++){
					if(msgs[i].length()>0){
						list.add(msgs[i]);
					}
				}
				year.setZongHeMsg(list.get(1));
				year.setLoveMsg(list.get(4));
				year.setWorkMsg(list.get(6));
				year.setCaiyunMsg(list.get(8));
			}
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
		int b = (int)((count / (double) list.size()) * 100);
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
