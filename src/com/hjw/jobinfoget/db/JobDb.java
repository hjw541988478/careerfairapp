package com.hjw.jobinfoget.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import me.hjw.jobinfoget.config.Constants;
import me.hjw.jobinfoget.model.JobInfo;

public class JobDb {

	private Connection conn;
	private ResultSet rs;
	private PreparedStatement pst;

	public JobDb() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(Constants.DB_URL,
					Constants.DB_NAME, Constants.DB_PSWD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeDb() {
		try {
			if (rs != null)
				rs.close();
			if (pst != null)
				pst.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 创建表
	public boolean createDb() {
		String sql = " CREATE TABLE " + Constants.TB_NAME + "("
				+ Constants.TB_KEY_ID + " INTEGER AUTO_INCREMENT PRIMARY KEY,"
				+ Constants.TB_KEY_COPNAME + " VARCHAR(50),"
				+ Constants.TB_KEY_JOB_TITLE_DATE + " VARCHAR(30),"
				+ Constants.TB_KEY_JOB_TITLE_ADDR + " VARCHAR(10),"
				+ Constants.TB_KEY_JOB_START_TIME + " VARCHAR(20),"
				+ Constants.TB_KEY_JOB_COMPLE_ADDR + " VARCHAR(50),"
				+ Constants.TB_KEY_JOB_LINK + " VARCHAR(150) "
				+ ") ENGINE = InnoDB DEFAULT CHARSET = utf8";
		String drop_sql = "DROP TABLE IF EXISTS " + Constants.TB_NAME;
		try {
			pst = conn.prepareStatement(drop_sql);
			pst.execute();
			pst = conn.prepareStatement(sql);
			System.out.println(sql);
			return pst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void execlSql(String sql) {
		try {
			pst = conn.prepareStatement(sql);
			pst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 批量插入
	public boolean insertJob(List<JobInfo> jobs) {
		boolean flag = false;
		String sql = "insert into " + Constants.TB_NAME + "("
				+ Constants.TB_KEY_COPNAME + ","
				+ Constants.TB_KEY_JOB_TITLE_DATE + ","
				+ Constants.TB_KEY_JOB_TITLE_ADDR + ","
				+ Constants.TB_KEY_JOB_START_TIME + ","
				+ Constants.TB_KEY_JOB_COMPLE_ADDR + ","
				+ Constants.TB_KEY_JOB_LINK + ") values ";
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			for (int i = 0; i < jobs.size(); i++) {
				JobInfo t_job = jobs.get(i);
				String temp_sql = sql + "('" + t_job.getCop_name() + "','"
						+ t_job.getJob_date() + "','" + t_job.getJob_addr()
						+ "','" + t_job.getJob_start_time() + "','"
						+ t_job.getJob_comp_addr() + "','"
						+ t_job.getJob_link() + "')";
				stmt.addBatch(temp_sql);
			}
			int[] count = stmt.executeBatch();
			if (count != null) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;

	}

	public List<JobInfo> getJobInfos(JobInfo info) {

		List<JobInfo> list = new ArrayList<JobInfo>();
		String sql = null;
		if (info == null) {
			sql = "select *from " + Constants.TB_NAME;
		} else {
			sql = "select *from " + Constants.TB_NAME + " where "
					+ Constants.TB_KEY_JOB_LINK + " = '" + info.getJob_link()
					+ "'";
		}
		try {
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			JobInfo t = null;
			while (rs.next()) {
				t = new JobInfo();
				t.setCop_name(rs.getString(Constants.TB_KEY_COPNAME));
				t.setJob_addr(rs.getString(Constants.TB_KEY_JOB_TITLE_ADDR));
				t.setJob_comp_addr(rs
						.getString(Constants.TB_KEY_JOB_COMPLE_ADDR));
				t.setJob_date(rs.getString(Constants.TB_KEY_JOB_TITLE_DATE));
				t.setJob_link(rs.getString(Constants.TB_KEY_JOB_LINK));
				t.setJob_start_time(Constants.TB_KEY_JOB_START_TIME);
				list.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void main(String[] args) {
		JobDb db = new JobDb();
		db.createDb();
		// // List<JobInfo> list = new ArrayList<JobInfo>();
		// JobInfo info = new JobInfo();
		// info.setCop_name("网易（杭州）网络有限公司");
		// info.setJob_addr("复临舍201");
		// info.setJob_comp_addr("复临舍201");
		// info.setJob_date("2014.09.17");
		// info.setJob_link("http://scc.hnu.edu.cn:80/articledetail?t.PostId=634");
		// info.setJob_start_time("2014.09.17 19:00:00");
		// // list.add(info);
		// // db.insertJob(list);
		// System.out.println(db.getJobInfos(info));
		db.closeDb();
	}

}
