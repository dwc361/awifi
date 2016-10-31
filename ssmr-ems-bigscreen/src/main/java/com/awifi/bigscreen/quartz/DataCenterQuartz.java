package com.awifi.bigscreen.quartz;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.awifi.bigscreen.AwifiConstants;
import com.awifi.bigscreen.redisCache.api.IDataAcquisition;
import com.awifi.bigscreen.redisCache.api.IRedisCache;

/**
 * 业务相关的作业调度
 * 字段               允许值                           允许的特殊字符
 * 秒			0-59	 			, - * /
 * 分	 		0-59	 			, - * /
 * 小时	 		0-23	 			, - * /
 * 日期	 		1-31	 			, - * ? / L W C
 * 月份	 		1-12 或者 JAN-DEC	 	, - * /
 * 星期	 		1-7 或者 SUN-SAT	 	, - * ? / L C #
 * 年（可选）	留空, 1970-2099	 	, - * /
 * 
 * *  字符代表所有可能的值
 * /  字符用来指定数值的增量
 * L  字符仅被用于天（月）和天（星期）两个子表达式，表示一个月的最后一天或者一个星期的最后一天
 * 6L 可以表示倒数第６天
 * @author zhangmm
 *
 */
@Component
public class DataCenterQuartz {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private IRedisCache redisHashCache;
	@Resource
	private IRedisCache redisZSetCache;
	
	@Resource
	private IDataAcquisition scatter_HotSpot_Chart_DataAcquisition;
	/**
	 * [爱wifi热点类型分布]数据拉取
	 * 每天23:00拉取一次
	 */
	@Scheduled(cron = "0 0 23 * * ? ")
	public void pull_Scatter_HotSpot_Chart_data() {
		log.info("@Scheduled--------pull_Scatter_HotSpot_Chart_data()");
		redisHashCache.createOrUpdateCache(AwifiConstants.Redis_Key_Scatter_HotSpot_Chart, scatter_HotSpot_Chart_DataAcquisition, "{'key':'value'}");
	}
	
	@Resource
	private IDataAcquisition mix_JHL_Chart_DataAcquisition;
	/**
	 * [胖ap激活率统计]数据拉取
	 * 本月第一天0:30:00拉取一次
	 */
	@Scheduled(cron = "0 30 0 1 * ? ")
	public void pull_Mix_JHL_Chart_data() {
		log.info("@Scheduled-------pull_Mix_JHL_Chart_data()");
		redisZSetCache.createOrUpdateCache(AwifiConstants.Redis_Key_Mix_JHL_Chart, mix_JHL_Chart_DataAcquisition, "{'key':'value'}");
	}
	
	@Resource
	private IDataAcquisition mix_DZZD_Chart_DataAcquisition;
	/**
	 * [定制终端设备状态统计]数据拉取
	 * 本月第一天1:30:00拉取一次
	 */
	@Scheduled(cron = "0 30 1 1 * ? ")
	public void pull_Mix_DZZD_Chart_data() {
		log.info("@Scheduled-------pull_Mix_DZZD_Chart_data()");
		redisZSetCache.createOrUpdateCache(AwifiConstants.Redis_Key_Mix_DZZD_Chart, mix_DZZD_Chart_DataAcquisition, "{'key':'value'}");
	}
	
	@Resource
	private IDataAcquisition mix_NAS_Chart_DataAcquisition;
	/**
	 * [NAS设备状态统计]数据拉取
	 * 本月第一天2:30:00拉取一次
	 */
	@Scheduled(cron = "0 30 2 1 * ? ")
	public void pull_Mix_NAS_Chart_data() {
		log.info("@Scheduled-------pull_Mix_NAS_Chart_data()");
		redisZSetCache.createOrUpdateCache(AwifiConstants.Redis_Key_Mix_NAS_Chart, mix_NAS_Chart_DataAcquisition, "{'key':'value'}");
	}
	
	@Resource
	private IDataAcquisition funnel_SBPM_Chart_DataAcquisition;
	/**
	 * [全省设备排名]数据拉取
	 * 每天0:00:00拉取一次
	 */
	@Scheduled(cron = "0 0 0 * * ? ")
	public void pull_Funnel_SBPM_Chart_data() {
		log.info("@Scheduled-------pull_Funnel_SBPM_Chart_data()");
		redisHashCache.createOrUpdateCache(AwifiConstants.Redis_Key_Funnel_SBPM_Chart, funnel_SBPM_Chart_DataAcquisition, "{'key':'value'}");
	}
	
	@Resource
	private IDataAcquisition line_YHRZ_Chart_DataAcquisition;
	/**
	 * [用户认证状态]数据拉取
	 * 每天1:00:00拉取一次
	 */
	@Scheduled(cron = "0 0 1 * * ? ")
	public void pull_Line_YHRZ_Chart_data() {
		log.info("@Scheduled-------pull_Line_YHRZ_Chart_data()");
		redisZSetCache.createOrUpdateCache(AwifiConstants.Redis_Key_Line_YHRZ_Chart, line_YHRZ_Chart_DataAcquisition, "{'key':'value'}");
	}
	
	@Resource
	private IDataAcquisition pie_LXFB_Chart_DataAcquisition;
	/**
	 * [设备类型分布]数据拉取
	 * 每天2:00:00拉取一次
	 */
	@Scheduled(cron = "0 0 2 * * ? ")
	public void pull_Pie_LXFB_Chart_data() {
		log.info("@Scheduled-------pull_Pie_LXFB_Chart_data()");
		redisHashCache.createOrUpdateCache(AwifiConstants.Redis_Key_Pie_LXFB_Chart, pie_LXFB_Chart_DataAcquisition, "{'key':'value'}");
	}
	
	@Resource
	private IDataAcquisition user_PV_UV_DataAcquisition;
	/**
	 * [用户、商户、PV、UV统计]数据拉取
	 * 每天3:00:00拉取一次
	 */
	@Scheduled(cron = "0 0 3 * * ? ")
	public void pull_User_PV_UV_data() {
		log.info("@Scheduled-------pull_User_PV_UV_data()");
		redisZSetCache.createOrUpdateCache(AwifiConstants.Redis_Key_User_PV_UV, user_PV_UV_DataAcquisition, "{'key':'value'}");
	}
	
	@Resource
	private IDataAcquisition user_Click_DataAcquisition;
	/**
	 * [平台用户点击量]数据拉取
	 * 每分钟拉取一次
	 */
	@Scheduled(cron = "0 * * * * ? ")
	public void pull_User_Click_data() {
		log.info("@Scheduled-------pull_User_PV_UV_data()");
		redisZSetCache.createOrUpdateCache(AwifiConstants.Redis_Key_User_Click, user_Click_DataAcquisition, "{'key':'value'}");
	}
}