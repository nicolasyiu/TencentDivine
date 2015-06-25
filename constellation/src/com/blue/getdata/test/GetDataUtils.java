package com.blue.getdata.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

public class GetDataUtils {

	// 获取不同时间下得星座和访问路径 今日 本周 本月 本年
	public static ArrayList<Times> captureHtml(String strURL) throws Exception {
		ArrayList<Times> list = new ArrayList<Times>();
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
		Pattern p = Pattern.compile("<ul class=\"xz\">((.(?!ul>))*)</ul>");
		Matcher m = p.matcher(buf);
		int num = 0;
		while (m.find()) {
			Times time = new Times();
			switch (num) {
			case 0:
				time.setName("今日");
				time.setModel(1);
				break;
			case 1:
				time.setName("本周");
				time.setModel(3);
				break;
			case 2:
				time.setName("本月");
				time.setModel(4);
				break;
			case 3:
				time.setName("本年");
				time.setModel(5);
				break;
			default:
				break;
			}
			time.setList(parseStr(m.group(1)));
			num++;
			list.add(time);
		}

		return list;
	}

	// 获取十二星座名称和访问路径
	public static ArrayList<Constellation> parseStr(String str) {
		ArrayList<Constellation> list = new ArrayList<Constellation>();
		String[] result = str.split("<li>");
		for (int i = 1; i < result.length; i++) {
			Constellation con = new Constellation();
			Pattern p = Pattern.compile("href=\"(.*)\">");
			Pattern p1 = Pattern.compile(">((.(?!a>))*)</a>");
			Matcher m = p.matcher(result[i]);
			Matcher m1 = p1.matcher(result[i]);
			while (m.find()) {
				con.setUrl(m.group(1));
			}
			while (m1.find()) {
				try {
					String name = new String(m1.group(1).getBytes("UTF-8"));
					con.setName(name);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

			}
			list.add(con);
		}
		return list;
	}

	public Map<String, List> getAllData(String url) {
		GetDateDetalUtils utils = new GetDateDetalUtils();
		Map<String, List> map = new HashMap<String, List>();
		try {
			ArrayList<Times> lists = captureHtml(url);
			for (Times times : lists) {
				// System.out.println("时间：" + times.getName() + " model:"+
				// times.getModel());
				ArrayList<Constellation> cons = times.getList();
				if (times.getName().equals("今日")) {
					List<TodayLuck> todayList = new ArrayList<TodayLuck>();
					List<TodayLuck> tomorrowList = new ArrayList<TodayLuck>();
					for (Constellation constellation : cons) {
						TodayLuck today = (TodayLuck) utils.getDate(
								constellation.getUrl(), times.getModel());
						// System.out.println(today);
						todayList.add(today);
						TodayLuck tomorrow = (TodayLuck) utils.getDate(
								"http://data.astro.qq.com/infopic/"
										+ constellation.getAstros().get(
												constellation.getName())
										+ "/todayAstro.js", 2);
						tomorrowList.add(tomorrow);
					}
					map.put("明日", tomorrowList);
					map.put("今日", todayList);
				} else if (times.getName().equals("本周")) {
					List<WeekLuck> weekList = new ArrayList<WeekLuck>();
					for (Constellation constellation : cons) {
						WeekLuck week = (WeekLuck) utils.getDate(
								constellation.getUrl(), times.getModel());
						weekList.add(week);
					}
					map.put("本周", weekList);
				} else if (times.getName().equals("本月")) {
					List<MonthLuck> monthList = new ArrayList<MonthLuck>();
					for (Constellation constellation : cons) {
						MonthLuck month = (MonthLuck) utils.getDate(
								constellation.getUrl(), times.getModel());
						monthList.add(month);
					}
					map.put("本月", monthList);
				} else if (times.getName().equals("本年")) {
					List<YearLuck> yearList = new ArrayList<YearLuck>();
					for (Constellation constellation : cons) {
						YearLuck year = (YearLuck) utils.getDate(
								constellation.getUrl(), times.getModel());
						yearList.add(year);
					}
					map.put("本年", yearList);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public static String http(String url, Map<String, String> params) {
		URL u = null;
		HttpURLConnection con = null;
		// 构建请求参数
		StringBuffer sb = new StringBuffer();
		String sbUrl = null;
		if (params != null) {
			for (Entry<String, String> e : params.entrySet()) {
				System.out.println("key:" + e.getKey() + "  value:"
						+ e.getValue());
				sb.append(e.getKey());
				sb.append("=");
				try {
					sb.append(URLEncoder.encode(e.getValue(), "utf-8"));
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				sb.append("&");
			}
			sbUrl = sb.substring(0, sb.length() - 1);
		}
		System.out.println("send_url:" + url);
		//System.out.println("send_data:" + sbUrl);
		// 尝试发送请求
		try {
			u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			con.setRequestProperty("safe-passwd",
					"67d46ec7d84ba284982e634970c5b7df");
			OutputStreamWriter osw = new OutputStreamWriter(
					con.getOutputStream(), "UTF-8");
			osw.write(sbUrl);
			osw.flush();
			osw.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
		// 读取返回内容
		StringBuffer buffer = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "UTF-8"));
			String temp;
			while ((temp = br.readLine()) != null) {
				buffer.append(temp);
				buffer.append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	public static void main(String[] args) {
		GetDataUtils util = new GetDataUtils();
		String url = "http://divine.sinaapp.com/enter.php/Api/Horoscope/updateHoroscope";
		Map<String, List> map = util
				.getAllData("http://astro.fashion.qq.com/index.shtml");
		System.out
				.println("-----------------------今日-------------------------");
		ArrayList<TodayLuck> todays = (ArrayList<TodayLuck>) map.get("今日");
		for (int i = 0; i < todays.size(); i++) {
			String msg = util.http(url,
					util.getTodayMap(getName(i), getDay(), todays.get(i)));
			System.out.println("今日返回结果：：" + msg);
		}

		System.out
				.println("-----------------------明日-------------------------");
		ArrayList<TodayLuck> tomorrows = (ArrayList<TodayLuck>) map.get("明日");
		for (int i = 0; i < tomorrows.size(); i++) {
			String msg = util.http(
					url,
					util.getTodayMap(getName(i), getTomorrow(),
							tomorrows.get(i)));
			System.out.println("明日返回结果：：" + msg);
		}
		System.out
				.println("-----------------------本周-------------------------");
		ArrayList<WeekLuck> weeks = (ArrayList<WeekLuck>) map.get("本周");
		for (int i = 0; i < weeks.size(); i++) {
			String msg = util.http(url,
					util.getWeekMap(getName(i), weeks.get(i)));
			System.out.println("本周返回结果：：" + msg);
		}
		System.out
				.println("-----------------------本月-------------------------");
		ArrayList<MonthLuck> months = (ArrayList<MonthLuck>) map.get("本月");
		for (int i = 0; i < months.size(); i++) {
			String msg = util.http(url,
					util.getMonthMap(getName(i), months.get(i)));
			System.out.println("本月返回结果：：" + msg);
		}
		System.out
				.println("-----------------------本年-------------------------");
		ArrayList<YearLuck> years = (ArrayList<YearLuck>) map.get("本年");
		for (int i = 0; i < years.size(); i++) {
			String msg = util.http(url,
					util.getYearMap(getName(i), years.get(i)));
			System.out.println("本年返回结果：：" + msg);
		}
	}

	public static String getName(int i) {
		String name = null;
		switch (i) {
		case 0:
			name = "白羊座";
			break;
		case 1:
			name = "金牛座";
			break;
		case 2:
			name = "双子座";
			break;
		case 3:
			name = "巨蟹座";
			break;
		case 4:
			name = "狮子座";
			break;
		case 5:
			name = "处女座";
			break;
		case 6:
			name = "天秤座";
			break;
		case 7:
			name = "天蝎座";
			break;
		case 8:
			name = "射手座";
			break;
		case 9:
			name = "摩羯座";
			break;
		case 10:
			name = "水瓶座";
			break;
		case 11:
			name = "双鱼座";
			break;
		default:
			break;
		}
		System.out.println(name);
		return name;
	}

	// 今日和明日
	public Map<String, String> getTodayMap(String name, String time,
			TodayLuck today) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("star_name", name);// 星座名称
		map.put("update_time", time);// 时间
		map.put("overview_index", today.getTitleZonghe());// 综合指数
		map.put("overview_desc", "");// 综合运势
		map.put("love_index", today.getTitleLove());// 爱情指数
		map.put("love_desc", "");// 爱情运势
		map.put("health_index", today.getHealth());// 健康指数
		map.put("health_desc", "");// 健康运势
		map.put("work_index", today.getTitleWoke());// 工作指数
		map.put("work_desc", "");// 工作运势
		map.put("wealth_index", today.getTitleCaiyun());// 财富指数
		map.put("wealth_desc", "");// 财富运势
		map.put("lucky_color", today.getColor());// 幸运色
		map.put("lucky_number", today.getNumber());// 幸运数字
		map.put("fast_match", today.getGuiren());// 星座速配
		map.put("description", today.getOverviews());// 综合描述
		return map;
	}

	// 本周
	public Map<String, String> getWeekMap(String name, WeekLuck week) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("star_name", name);// 星座名称
		map.put("update_time", getWeek());// 时间
		map.put("overview_index", "");// 综合指数
		map.put("overview_desc", "");// 综合运势
		map.put("love_index", "");// 爱情指数
		map.put("love_desc", week.getLoveMsg());// 爱情运势
		map.put("health_index", "");// 健康指数
		map.put("health_desc", week.getHealthMsg());// 健康运势
		map.put("work_index", "");// 工作指数
		map.put("work_desc", week.getWorkMsg());// 工作运势
		map.put("wealth_index", "");// 财富指数
		map.put("wealth_desc", week.getCaiYunMsg());// 财富运势
		map.put("lucky_color", "");// 幸运色
		map.put("lucky_number", "");// 幸运数字
		map.put("fast_match", "");// 星座速配
		map.put("description", "");// 综合描述
		return map;
	}

	// 本月
	public Map<String, String> getMonthMap(String name, MonthLuck month) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("star_name", name);// 星座名称
		map.put("update_time", getMonth());// 时间
		map.put("overview_index", "");// 综合指数
		map.put("overview_desc", month.getZonghe());// 综合运势
		map.put("love_index", "");// 爱情指数
		map.put("love_desc", month.getLove());// 爱情运势
		map.put("health_index", "");// 健康指数
		map.put("health_desc", month.getHealth());// 健康运势
		map.put("work_index", "");// 工作指数
		map.put("work_desc", month.getWork());// 工作运势
		map.put("wealth_index", "");// 财富指数
		map.put("wealth_desc", month.getCaiyun());// 财富运势
		map.put("lucky_color", "");// 幸运色
		map.put("lucky_number", "");// 幸运数字
		map.put("fast_match", "");// 星座速配
		map.put("description", "");// 综合描述

		return map;
	}

	// 本年
	public Map<String, String> getYearMap(String name, YearLuck year) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("star_name", name);// 星座名称
		map.put("update_time", getYear());// 时间
		map.put("overview_index", "");// 指数
		map.put("overview_desc", year.getZongHeMsg());// 综合运势
		map.put("love_index", "");// 爱情指数
		map.put("love_desc", year.getLoveMsg());// 爱情运势
		map.put("health_index", "");// 健康指数
		map.put("health_desc", "");// 健康运势
		map.put("work_index", "");// 工作指数
		map.put("work_desc", year.getWorkMsg());// 工作运势
		map.put("wealth_index", "");// 财富指数
		map.put("wealth_desc", year.getCaiyunMsg());// 财富运势
		map.put("lucky_color", "");// 幸运色
		map.put("lucky_number", "");// 幸运数字
		map.put("fast_match", "");// 星座速配
		map.put("description", "");// 综合描述

		return map;
	}

	// 获得当前日期
	public static String getDay() {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy年MM月dd日");
		String dateStr = sd.format(new Date());
		System.out.println(dateStr);
		return dateStr;
	}

	public static String getTomorrow() {
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
		String nowDate = sf.format(date);
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sf.parse(nowDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.add(Calendar.DAY_OF_YEAR, +1);
		String nextDate_1 = sf.format(cal.getTime());
		// 通过秒获取下一天日期
		long time = (date.getTime() / 1000) + 60 * 60 * 24;// 秒
		date.setTime(time * 1000);// 毫秒
		String nextDate_2 = sf.format(date).toString();
		System.out.println(nextDate_2);
		return nextDate_2;
	}

	// 获得本周起始日期
	public static String getWeek() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		Calendar c = Calendar.getInstance();
		int weekday = c.get(7) - 1;
		c.add(5, -weekday);
		String start = df.format(c.getTime());
		System.out.println("本周开始时间：" + start);
		c.add(5, 6);
		String end = df.format(c.getTime());
		System.out.println("本周开始结束：" + end);
		return start + "-" + end;
	}

	// 获得本月日期
	public static String getMonth() {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy年MM月");
		String dateStr = sd.format(new Date());
		System.out.println(dateStr);
		return dateStr;
	}

	// 获得本年
	public static String getYear() {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy年");
		String dateStr = sd.format(new Date());
		System.out.println(dateStr);
		return dateStr;
	}
}
