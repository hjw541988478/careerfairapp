package in.harvestday.careerfairs.service;

import com.squareup.okhttp.Request;

import java.util.List;

import in.harvestday.careerfairs.bean.CareerTalk;
import in.harvestday.careerfairs.http.OkHttpClientManager;

/**
 * Created by Administrator on 2015/9/11.
 */
public class CareerTalkService {

    private static CareerTalkService instance;
    public static String URL = "http://107.182.176.50/api/v1?page=%s&uid=%s&pageSize=%s";

    private CareerTalkService() {
    }

    public static CareerTalkService getInstance() {
        if (instance == null) {
            synchronized (CareerTalkService.class) {
                instance = new CareerTalkService();
            }
        }
        return instance;
    }

    public interface CareerFairRequestListener {
        void onSuccess(List<CareerTalk> talks);

        void onFailure(String errMsg);
    }

    public void fetchList(int page, int uid, int pageSize, final CareerFairRequestListener listener) {

        OkHttpClientManager.getAsyn(String.format(URL, page, uid, pageSize),
                new OkHttpClientManager.ResultCallback<List<CareerTalk>>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        e.printStackTrace();
                        if (listener != null)
                            listener.onFailure(e.getMessage());
                    }

                    @Override
                    public void onResponse(List<CareerTalk> us) {
                        if (listener != null)
                            listener.onSuccess(us);
                    }
                });
    }

}
