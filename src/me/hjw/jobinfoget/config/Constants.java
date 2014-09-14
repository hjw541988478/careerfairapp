package me.hjw.jobinfoget.config;

public class Constants {

	public static final String BASE_URL = "http://scc.hnu.edu.cn/";
	// 校内招聘基础页面
	public static final String JOB_GET_BASE_URL = "http://scc.hnu.edu.cn/newsjob!getMore.action?p.currentPage=";
	// 校招标识
	public static final String XIAOZHAO_LABEL_URL = "&Lb=1";
	// 实习标识
	public static final String SHIXI_LABEL_URL = "&Lb=2";
	// 页面数
	public static int PAGE_NUM = 1;
	// 记录数
	public static int JOB_NUM = 0;

	public static final String COMPARE_FILE_PATH = "d:\\srnew.txt";
	public static final String LATEST_FILE_PATH = "d:\\save_job.txt";

	public static final String DB_URL = "jdbc:mysql://localhost:3306/test";
	public static final String DB_NAME = "root";
	public static final String DB_PSWD = "admin";

	public static final String TB_NAME = "tb_job";
	public static final String TB_KEY_ID = "_id";
	public static final String TB_KEY_COPNAME = "cop_name";
	public static final String TB_KEY_JOB_TITLE_DATE = "job_title_date";
	public static final String TB_KEY_JOB_TITLE_ADDR = "job_title_addr";
	public static final String TB_KEY_JOB_START_TIME = "job_start_time";
	public static final String TB_KEY_JOB_COMPLE_ADDR = "job_comple_addr";
	public static final String TB_KEY_JOB_LINK = "job_link";

}
