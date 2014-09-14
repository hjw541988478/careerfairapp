package me.hjw.jobinfoget;

import java.util.ArrayList;
import java.util.List;

import me.hjw.jobinfoget.config.Constants;
import me.hjw.jobinfoget.model.JobInfo;
import me.hjw.jobinfoget.util.JobGetUtil;

public class MainClass {
	public static void main(String[] args) throws Exception {
		System.out.println("-----开始执行-------");
		long start = System.currentTimeMillis();
		JobGetUtil.getJobConfigs();
		List<String> links = new ArrayList<String>();
		for (int i = 1; i <= Constants.PAGE_NUM; i++) {
			List<String> adds = JobGetUtil.getJobLinks(i);
			links.addAll(adds);
		}
		List<JobInfo> infos = JobGetUtil.getJobDetails(links);
		System.out.println("共" + Constants.PAGE_NUM + "页,共" + Constants.JOB_NUM
				+ "条");
		JobGetUtil.PreWriteToDisk(infos);
		long end = System.currentTimeMillis();
		System.out.println("耗时：" + (end - start) / 1000 + "s");
		System.out.println("-----完成执行-------");
	}
}
