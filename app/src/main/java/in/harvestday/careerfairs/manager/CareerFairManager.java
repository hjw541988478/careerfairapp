package in.harvestday.careerfairs.manager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import in.harvestday.careerfairs.bean.CareerFair;
import in.harvestday.careerfairs.bean.CareerPageInfo;
import in.harvestday.careerfairs.bean.College;
import in.harvestday.careerfairs.net.ThreadPoolUtils;
import in.harvestday.careerfairs.parse.Const;
import in.harvestday.careerfairs.parse.ParseUtil;

/**
 * Created by Administrator on 2015/9/11.
 */
public class CareerFairManager {

    private static CareerFairManager instance;

    private CareerFairManager() {
    }

    public static CareerFairManager getInstance() {
        if (instance == null) {
            synchronized (CareerFairManager.class) {
                instance = new CareerFairManager();
            }
        }
        return instance;
    }

    public interface CareerFairRequestListener {
        void onSuccess(Object fairs);

        void onFailure(String errMsg);
    }


    public void fetchPageCareerList(int page, College college, final CareerFairRequestListener listener, Const.CareerFairType type) {
        switch (college) {
            case HunanUniversity:
                fetchHunanUniversityCareerList(page, new CareerFairRequestListener() {
                    @Override
                    public void onSuccess(Object fairs) {
                        List<String> urls = (List) fairs;
                        fetchDetailCareerFairs(urls, listener);
                    }

                    @Override
                    public void onFailure(String errMsg) {
                        listener.onFailure(errMsg);
                    }
                }, type);

                break;
            case CentralSouthUniversity:
                break;
            case HunanUniversityOfTechnology:
                break;
        }
    }

    private int getCareerFariLabel(Const.CareerFairType type) {
        if (type == Const.CareerFairType.CAMPUS_LABEL)
            return 1;
        else if (type == Const.CareerFairType.INTERN_LABEL)
            return 2;
        else
            return 1;
    }

    private void fetchHunanUniversityCareerList(final int page, final CareerFairRequestListener listener, final Const.CareerFairType type) {
        final List careerSimpleFairs = new LinkedList<>();
        if (page <= 0)
            listener.onFailure("页码不能小于1");
        else {
            ThreadPoolUtils.execute(new Runnable() {
                @Override
                public void run() {
                    final String url = String.format(Const.HU_CAREERFAIRS_BASE_URL, page, getCareerFariLabel(type));
                    try {
                        Document doc = Jsoup
                                .connect(url)
                                .timeout(3000).get();
                        Elements links = doc.getElementsByClass("r_list1").first()
                                .getElementsByTag("a");
                        for (Element link : links) {
                            careerSimpleFairs.add(link.absUrl("href"));
                        }
                        listener.onSuccess(careerSimpleFairs);
                    } catch (IOException e) {
                        e.printStackTrace();
                        listener.onFailure(e.getMessage());
                    }
                }
            });
        }
    }

    /**
     * 获取所有超链接详情页的企业详情页，返回招聘信息详情
     *
     * @param links
     * @return
     */
    public void fetchDetailCareerFairs(final List<String> links, final CareerFairRequestListener listener) {
        final List<CareerFair> job_List = new LinkedList<>();
        ThreadPoolUtils.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (links != null) {
                        for (String job_link : links) {
                            Document doc = Jsoup.connect(job_link).timeout(3000).get();
                            CareerFair jobInfo = new CareerFair();
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
                            job_List.add(jobInfo);
                        }
                        listener.onSuccess(job_List);
                    } else {
                        listener.onFailure("未获取到有效的URL");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    listener.onFailure(e.getMessage());
                }
            }
        });
    }


    public void fetchBasicInfo(College college, final CareerFairRequestListener listener) {
        final CareerPageInfo info = new CareerPageInfo();
        final String requestUrl;
        switch (college) {
            case HunanUniversity:
                requestUrl = String.format(Const.HU_CAREERFAIRS_BASE_URL, 1, getCareerFariLabel(Const.CareerFairType.CAMPUS_LABEL));
                ThreadPoolUtils.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Document doc = Jsoup
                                    .connect(
                                            requestUrl).timeout(3000)
                                    .get();
                            String str = doc.select("div[class=page]").first()
                                    .getElementsByTag("span").first().text();
                            int job_count_index = str.indexOf("条");
                            int job_index_1 = str.indexOf("共");
                            int job_index_2 = str.indexOf("页");
                            info.pageNum = Integer.valueOf(str.substring(job_index_1 + 1,
                                    job_index_2));
                            info.totalNum = Integer.parseInt(str.substring(0, job_count_index));
                            listener.onSuccess(info);
                        } catch (Exception e) {
                            e.printStackTrace();
                            listener.onFailure(e.getMessage());
                        }
                    }
                });
                break;
            case CentralSouthUniversity:
                break;
            case HunanUniversityOfTechnology:
                break;
            default:
                break;
        }
    }

}
