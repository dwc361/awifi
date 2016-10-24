package com.awifi.bigscreen;

/**
 * 常量类
 * 
 * @author zhangmm
 * 
 */
public class AwifiConstants {

	/**
	 * 接口返回的数据在Map里对应的key
	 */
	public static final String Interface_Return_Data = "chartData";
	
	/**
	 * Redis里面ZSet类型数据的score
	 */
	public static final String Redis_ZSet_Score = "score";
	
	/**
	 * Redis插入数据的时间戳的key
	 */
	public static final String Redis_Create_Time = "createTime";
	
	
	
	/**
	 * [用户、商户、PV、UV统计]图表对应Redis里面的Key
	 */
	public static final String Redis_Key_User_PV_UV = "User_PV_UV";
	
	/**
	 * [全省设备排名]图表对应Redis里面的Key
	 */
	public static final String Redis_Key_Funnel_SBPM_Chart = "Funnel_SBPM_Chart";
	
	/**
	 * [用户认证状态]图表对应Redis里面的Key
	 */
	public static final String Redis_Key_Line_YHRZ_Chart = "Line_YHRZ_Chart";

	/**
	 * [定制终端设备状态统计]图表对应Redis里面的Key
	 */
	public static final String Redis_Key_Mix_DZZD_Chart = "Mix_DZZD_Chart";
	
	/**
	 * [胖ap激活率统计]图表对应Redis里面的Key
	 */
	public static final String Redis_Key_Mix_JHL_Chart = "Mix_JHL_Chart";
	
	/**
	 * [NAS设备状态统计]图表对应Redis里面的Key
	 */
	public static final String Redis_Key_Mix_NAS_Chart = "Mix_NAS_Chart";
	
	/**
	 * [设备类型分布]图表对应Redis里面的Key
	 */
	public static final String Redis_Key_Pie_LXFB_Chart = "Pie_LXFB_Chart";
	
	/**
	 * [爱wifi热点类型分布]图表对应Redis里面的Key
	 */
	public static final String Redis_Key_Scatter_HotSpot_Chart = "Scatter_HotSpot_Chart";
	
	
	
	/**
	 * 设备类型-定制终端
	 */
	public static final String SBLX_DZZD ="31,32,33,34,35,36,37";
	
	/**
	 * 设备类型-NAS
	 */
	public static final String SBLX_NAS ="11,21,41,42,43";
}