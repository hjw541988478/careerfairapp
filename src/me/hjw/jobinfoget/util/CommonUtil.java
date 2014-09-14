package me.hjw.jobinfoget.util;

public class CommonUtil {
	// 获取公司名
	public static String formatCopName(String cop_name) {
		return cop_name.substring(0, cop_name.length() - 5);
	}

	// 获取标题格式时间
	public static String formatTime(String cop_time) {
		StringBuffer buffer = new StringBuffer();
		String[] times = cop_time.split("-");
		buffer.append(times[0]).append(".").append(times[1]).append(".")
				.append(times[2].substring(0, 2));
		return buffer.toString();
	}

	// 获取标题格式地点
	public static String formatAddr(String cop_addr) {
		cop_addr = cop_addr.split("：")[1];
		if (cop_addr.indexOf("东风") != -1) {
			return "东风厅";
		} else if (cop_addr.indexOf("前进") != -1) {
			return "前进厅";
		} else if (cop_addr.indexOf("东风") != -1) {
			return "东风厅";
		} else if (cop_addr.indexOf("化工") != -1) {
			return "化工厅";
		} else if (cop_addr.indexOf("复临舍201") != -1) {
			return "复临舍201";
		} else if (cop_addr.indexOf("复临舍101") != -1) {
			return "复临舍101";
		} else if (cop_addr.indexOf("逸夫楼") != -1) {
			return "逸夫楼";
		} else {
			return cop_addr;
		}
	}
}
