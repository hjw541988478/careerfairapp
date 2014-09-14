package me.hjw.jobinfoget.model;

import java.io.Serializable;

/**
 * 招聘信息实体
 * 
 * @author hjw
 * 
 */
public class JobInfo implements Serializable {

	// 标题招聘时间
	private String job_date;
	// 标题简写地点
	private String job_addr;
	// 招聘公司名
	private String cop_name;
	// 招聘开始时间
	private String job_start_time;
	// 招聘完整地点
	private String job_comp_addr;
	// 源链接
	private String job_link;

	public String getJob_link() {
		return job_link;
	}

	public void setJob_link(String job_link) {
		this.job_link = job_link;
	}

	public String getJob_date() {
		return job_date;
	}

	public void setJob_date(String job_date) {
		this.job_date = job_date;
	}

	public String getJob_addr() {
		return job_addr;
	}

	public void setJob_addr(String job_addr) {
		this.job_addr = job_addr;
	}

	public String getCop_name() {
		return cop_name;
	}

	public void setCop_name(String cop_name) {
		this.cop_name = cop_name;
	}

	public String getJob_start_time() {
		return job_start_time;
	}

	public void setJob_start_time(String job_start_time) {
		this.job_start_time = job_start_time;
	}

	public String getJob_comp_addr() {
		return job_comp_addr;
	}

	public void setJob_comp_addr(String job_comp_addr) {
		this.job_comp_addr = job_comp_addr;
	}

	@Override
	public boolean equals(Object obj) {
		JobInfo old = (JobInfo) obj;
		return old.getJob_link().equals(this.getJob_link());
	}

	@Override
	public String toString() {
		return "JobInfo [job_date=" + job_date + ", job_addr=" + job_addr
				+ ", cop_name=" + cop_name + ", job_link=" + job_link + "]";
	}

}
