package in.harvesday.careerfairs.bean;

import java.io.Serializable;

public class CareerFair implements Serializable {

    // 标题招聘时间
    public String careerFairTime;
    // 标题简写地点
    public String careerFairAddr;
    // 招聘公司名
    public String careerFairCopName;
    // 招聘开始时间
    public String careerFairStartTime;
    // 招聘完整地点
    public String careerFairCompAddr;
    // 源链接
    public String careerFairUrl;


    @Override
    public boolean equals(Object obj) {
        CareerFair old = (CareerFair) obj;
        return old.careerFairUrl.equals(this.careerFairUrl);
    }

    @Override
    public String toString() {
        return careerFairAddr + "," + careerFairCopName + "," + careerFairStartTime;
    }
}
