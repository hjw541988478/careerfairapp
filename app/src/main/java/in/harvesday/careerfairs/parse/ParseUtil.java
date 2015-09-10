package in.harvesday.careerfairs.parse;

public class ParseUtil {
    // 获取公司名
    public static String formatCopName(String cop_name) {
        return cop_name.substring(0, cop_name.length() - 5);
    }

    // 获取标题格式时间
    public static String formatTime(String cop_time) {
        StringBuilder buffer = new StringBuilder();
        String[] times = cop_time.split("-");
        buffer.append(times[0]).append(".").append(times[1]).append(".")
                .append(times[2].substring(0, 2));
        return buffer.toString();
    }

    // 获取标题格式地点
    public static String formatAddr(String cop_addr) {
        cop_addr = cop_addr.split("：")[1];
        if (cop_addr.contains("东风")) {
            return "东风厅";
        } else if (cop_addr.contains("前进")) {
            return "前进厅";
        } else if (cop_addr.contains("东风")) {
            return "东风厅";
        } else if (cop_addr.contains("化工")) {
            return "化工厅";
        } else if (cop_addr.contains("复临舍201")) {
            return "复临舍201";
        } else if (cop_addr.contains("复临舍101")) {
            return "复临舍101";
        } else if (cop_addr.contains("逸夫楼")) {
            return "逸夫楼";
        } else {
            return cop_addr;
        }
    }
}
