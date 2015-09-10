package in.harvesday.careerfairs.parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import in.harvesday.careerfairs.bean.CareerFair;

public class SpiderEngine {

    public static void initConfigs() throws IOException {
        Document doc = Jsoup
                .connect(
                        Constants.JOB_GET_BASE_URL + "1"
                                + Constants.XIAOZHAO_LABEL_URL).timeout(3000)
                .get();
        String str = doc.select("div[class=page]").first()
                .getElementsByTag("span").first().text();
        int job_count_index = str.indexOf("条");
        int job_index_1 = str.indexOf("共");
        int job_index_2 = str.indexOf("页");
        Constants.PAGE_NUM = Integer.parseInt(str.substring(job_index_1 + 1,
                job_index_2));
        Constants.JOB_NUM = Integer.parseInt(str.substring(0, job_count_index));
    }

    /**
     * 获取第index页的招聘详情URL列表
     *
     * @param index
     * @return
     */
    public static List<String> getCarrerFairLinks(int index) {
        List<String> link_list = new LinkedList<>();
        try {
            Document doc = Jsoup
                    .connect(
                            Constants.JOB_GET_BASE_URL + index
                                    + Constants.XIAOZHAO_LABEL_URL)
                    .timeout(3000).get();
            Elements links = doc.getElementsByClass("r_list1").first()
                    .getElementsByTag("a");
            for (Element link : links) {
                link_list.add(link.absUrl("href"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return link_list.size() == 0 ? null : link_list;
    }

    /**
     * 获取所有超链接详情页的企业详情页，返回招聘信息详情
     *
     * @param links
     * @return
     */
    public static List<CareerFair> getCareerFairListDetails(List<String> links) {
        List<CareerFair> job_List = new LinkedList<>();
        CareerFair jobInfo = null;
        try {
            if (links != null) {
                for (String job_link : links) {
                    Document doc = Jsoup.connect(job_link).timeout(3000).get();
                    jobInfo = new CareerFair();
                    jobInfo.careerFairUrl = job_link;
                    jobInfo.careerFairCopName = ParseUtil.formatCopName(doc
                            .getElementsByClass("artititle").first().text());
                    Elements infos = doc.getElementsByClass("tr_height25");
                    jobInfo.careerFairAddr = ParseUtil.formatAddr(infos.first()
                            .text());
                    jobInfo.careerFairCompAddr = infos.first().text().split("：")[1];
                    jobInfo.careerFairStartTime = infos.get(1).text().split("：")[1]
                            + ":00";
                    jobInfo.careerFairTime = ParseUtil.formatTime(infos.get(1)
                            .text().split("：")[1]);
                    // Elements elements = doc.select("div[style]");
                    job_List.add(jobInfo);
                }
            } else {
                System.out.println("未获取到链接");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return job_List.size() == 0 ? null : job_List;
    }

    /**
     * 查找更新的招聘信息内容
     *
     * @param olds
     * @param news
     * @return
     */
//    public static List<JobInfo> compareJobInfo(List<JobInfo> news) {
//        JobInfo temp = null;
//        JobDb db = new JobDb();
//        List<JobInfo> rnew = new ArrayList<JobInfo>();
//        int j;
//        for (j = 0; j < news.size(); j++) {
//            temp = news.get(j);
//            int count = db.getJobInfos(temp).size();
//            if (count == 0) {
//                rnew.add(temp);
//            }
//        }
//        db.closeDb();
//        return rnew.size() == 0 ? null : rnew;
//    }

    /**
     * 将招聘信息信息写入文件
     *
     * @param infos
     */
//    public static void WriteToDisk(List<JobInfo> infos) {
//        JobDb db = new JobDb();
//        boolean f = db.insertJob(infos);
//        if (f) {
//            System.out.println("数据插入成功！");
//            try {
//                BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
//                        Constants.LATEST_FILE_PATH)));
//                int index = 1;
//                for (JobInfo job : infos) {
//                    bw.write("第" + (index++) + "条");
//                    bw.newLine();
//                    bw.write(job.getJob_date() + " " + job.getJob_addr() + " "
//                            + job.getCop_name());
//                    bw.newLine();
//                    bw.write("[table=98%][tr][td=15%][size=2]招聘单位:[/size][/td][td=85%][size=2]"
//                            + job.getCop_name()
//                            + "[/size][/td][/tr][tr][td=15%][size=2]招聘会时间:[/size][/td][td=85%][size=2]"
//                            + job.getJob_start_time()
//                            + "[/size][/td][/tr][tr][td=15%][size=2]招聘会地点:[/size][/td][td=85%][size=2]"
//                            + job.getJob_comp_addr()
//                            + "[/size][/td][/tr][/table][s:71]任何意见和建议请联系 [img=25,20]http://www.63jiuye.com/forum.php?mod=attachment&aid=NzkyMXw2NzUwZDRiYXwxMzYxNzk0ODM2fDk2MDV8OTEwMDI%3D&noupdate=yes[/img] [url=http://e.weibo.com/63jiuye]@麓山就业网[/url]  [url=http://weibo.com/63wuhan]@麓山武汉就业网[/url]  或  [img=24,24]http://www.63jiuye.com/forum.php?mod=attachment&aid=ODM3NHw4MmEyZDVmMHwxMzY4MTY0NzQ1fDk2MDV8OTA5MDE%3D&noupdate=yes[/img] [color=#000000][size=2]添加  [/size][/color][color=#ff0000][size=2]jiuye63[/size][/color][color=#000000][size=2] 为好友[/size][/color][color=#000000][size=2][/size][/color]");
//                    bw.newLine();
//                    bw.write(job.getJob_link());
//                    bw.newLine();
//                    bw.newLine();
//                }
//                bw.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            System.out
//                    .println("更新的招聘信息写入文件成功，路径：" + Constants.LATEST_FILE_PATH);
//        } else {
//            System.out.println("插入失败！");
//        }
//    }

    /**
     * 预写入文件
     *
     * @param infos
     */
//    public static void PreWriteToDisk(List<JobInfo> infos) {
//        try {
//            // 获取新增信息
//            List<JobInfo> rnews = compareJobInfo(infos);
//            if (rnews == null) {
//                System.out.println("与上次一致");
//            } else {
//                // 将原始信息写入原文件
//                WriteToDisk(rnews);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public static void main(String[] args) throws Exception {
//        List<JobInfo> oldInfos = new ArrayList<>();
//        BufferedReader br = new BufferedReader(new InputStreamReader(
//                new FileInputStream(new File("d:\\save_job.txt"))));
//        String line = "";
//        JobInfo oldJob = null;
//        int index = 1;
//        while ((line = br.readLine()) != null) {
//            if (index == 2) {
//                oldJob = new JobInfo();
//                String[] infos = line.split(" ");
//                oldJob.setJob_date(infos[0]);
//                oldJob.setJob_addr(infos[1]);
//                oldJob.setCop_name(infos[2]);
//            } else if (index == 3) {
//
//                int time_index_1 = line.indexOf("间:");
//                int addr_index_1 = line.indexOf("点:");
//                int addr_index_2 = line
//                        .indexOf("[/size][/td][/tr][/table][s:71]");
//                String time = line.substring(time_index_1 + 30,
//                        time_index_1 + 49);
//                String addr = line.substring(addr_index_1 + 30, addr_index_2);
//                oldJob.setJob_comp_addr(addr);
//                oldJob.setJob_start_time(time);
//            } else if (index == 4) {
//                oldJob.setJob_link(line);
//                oldInfos.add(oldJob);
//            } else if (index == 5) {
//            } else if (index == 6) {
//                index = 0;
//            }
//            index++;
//        }
//        br.close();
//        JobDb db = new JobDb();
//        db.createDb();
//        db.insertJob(oldInfos);
//        oldInfos = db.getJobInfos(null);
//        for (JobInfo info : oldInfos) {
//            System.out.println(info.toString());
//        }
//        db.closeDb();
//    }
}
