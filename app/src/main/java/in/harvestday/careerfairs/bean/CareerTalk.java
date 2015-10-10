package in.harvestday.careerfairs.bean;

/**
 * Created by hjw on 15/10/10.
 */
public class CareerTalk {
    public int id;
    public int uid;
    public String startDatetime;
    public String startDate;
    public String starTime;
    public String updateTime;

    public String copName;
    public String hostRoom;
    public String type;
    public String url;

    public CareerTalk(int id, int uid, String startDatetime, String startDate, String startTime, String updateTime, String copName, String hostRoom, String type, String url) {
        this.id = id;
        this.uid = uid;
        this.startDatetime = startDatetime;
        this.startDate = startDate;
        this.starTime = startTime;
        this.updateTime = updateTime;
        this.copName = copName;
        this.hostRoom = hostRoom;
        this.type = type;
        this.url = url;
    }

    @Override
    public String toString() {
        return "CareerTalk{" +
                "id=" + id +
                ", uid=" + uid +
                ", startDatetime='" + startDatetime + '\'' +
                ", startDate='" + startDate + '\'' +
                ", startTime='" + starTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", copName='" + copName + '\'' +
                ", hostRoom='" + hostRoom + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
