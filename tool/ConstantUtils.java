package TextMining.tool;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;

/**
 * 基本变量类
 * 
 * @author lyq
 * 
 */
public class ConstantUtils {
	// 挖掘系统名称
	public static final String MINING_SYSTEM_NAME = "新闻观点评论挖掘系统";

	// 中文的句号结束字符
	public static final String CHINESE_PUNCTUATION_END = "。";
	// 中文的逗号字符
	public static final String CHINESE_PUNCTUATION_COMMA = "，";
	// 中文的感叹号字符
	public static final String CHINESE_PUNCTUATION_EXCLAMATORY = "！";
	// 中文的问号字符
	public static final String CHINESE_PUNCTUATION_QUESTION = "？";
	// 英语的句号结束字符
	public static final String ENGLISH_PUNCTUATION_END = ".";
	// 英文的逗号字符
	public static final String ENGLISH_PUNCTUATION_COMMA = ",";
	// 英文中的分隔符
	public static final String PUNCTUATION_LINK = "-";
	// 制表符
	public static final String PUNCTUATION_TAB = "\t";
	// 标号空格符
	public static final String PUNCTUATION_BLANK = " ";

	// 预处理中的部分过滤词，目前数量比较少
	public static final String[] FILTERED_WORDS = new String[] { "的", "-" };

	// 统计相关名称
	public static final String CHART_PIE = "网评观点饼状图";
	public static final String CHART_BAR = "网评观点柱形图";
	public static final String CHART_LINE = "网评观点折线图";
	public static final String CHART_BAR_X_TITLE = "观点分类";
	public static final String CHART_BAR_Y_TITLE = "频率";
	public static final String CHART_LINE_X_TITLE = "评论时间";
	public static final String CHART_LINE_Y_TITLE = "观点比例";

	// 折线图维度划分单位
	public static final int CHART_LINE_DIMENSION_DAY = 0;
	public static final int CHART_LINE_DIMENSION_HOUR = 1;
	public static final int CHART_LINE_DIMENSION_DAY_AND_HOUR = 2;
	public static final int CHART_LINE_SHOW_NUMS_LIMIT = 5;

	// 情绪词汇等级划分间隔
	public static final double EWORD_PER_LEVEL = 0.333;
	public static final String EWORD_NEUTRAL_LEVEL = "持中立态度";
	/**
	 * 消极情绪词汇的等级划分
	 */
	public static final String[] EWORD_NEGATIVE_LEVEL = new String[] { "有点讨厌",
			"不喜欢", "非常厌恶" };

	/**
	 * 积极情绪词汇的等级划分
	 */
	public static final String[] EWORD_POSITIVE_LEVEL = new String[] { "有点喜欢",
			"喜欢", "非常好的" };

	// 语义词汇观点倾向
	public static final int EWORD_LEVEL_POSITIVE = 0;
	public static final int EWORD_LEVEL_NEUTRAL = 1;
	public static final int EWORD_LEVEL_NEGATIVE = 2;

	public static final String EWORD_LEVEL_POSITIVE_NAME = "积极";
	public static final String EWORD_LEVEL_NEUTRAL_NAME = "中立";
	public static final String EWORD_LEVEL_NEGATIVE_NAME = "消极";

	// 消极的词性级别划分
	public static final String[] NEGATIVE_WORDS_LEVEL = new String[] { "NA",
			"NB", "NC", "ND", "NE", "NF", "NG", "NH", "NI", "NJ", "NK", "NL",
			"NN" };

	// 积极的词语级别划分
	public static final String[] POSITIVE_WORDS_LEVEL = new String[] { "PA",
			"PB", "PC", "PD", "PE", "PF", "PG", "PH", "PI", "PJ", "PK", "PL",
			"PN" };

	// 字典库路径，测试时使用
	public static String DICT_PATH = "C:\\Users\\lyq\\Desktop\\icon\\dict.txt";
	//中间评论数据默认输出位置
	public static String DEFAULT_OUTPUT_PATH = "C:\\Users\\lyq\\Desktop\\我的毕业设计\\newsComments2.txt";
	//评论数据输出名
	public static String DEFAULT_OUTPUT_FILE_NAME = "newsComments.txt";

	// 字典库每列属性含义
	public static int DICT_WORD_CONTENT = 0;
	public static int DICT_WORD_CLASS = 4;
	public static int DICT_WORD_STRONG = 5;

	// 存储观点极性的数组大默认10W个
	public static final int HASH_NUM = 100000;

	// 爬虫每次爬取评论的数目
	public static final int CRAWLER_PER_REQ_NUM = 50;
	// 爬虫爬取的总评论数
	public static final int CRAWLER_TOTAL_REQ_NUM = 100;

	// 界面显示的坐标位置
	public static final int FRAME_LEFT = 0;
	public static final int FRAME_TOP = 0;
	public static final int FRAME_WIDTH_SIZE = 500;
	public static final int FRAME_HEIGHT_SIZE = 400;
	
	public static final int FRAME_TOP_FREQUENT_WORDS_SIZE = 10;
	
	//界面主背景色
	public static final Color BACK_COLOR = new Color(41, 91, 126);
	//界面文字颜色
	public static final Color FONT_COLOR = Color.WHITE;

	// 界面显示文字及标识
	// 第一个界面用到的部分名称
	public static final String FRAME_START_MINING_BTN_TEXT = "开始观点挖掘";
	public static final String FRAME_START_MINING_BTN_NAME = "miningButton";
	public static final String FRAME_URL_LABEL_TEXT = "新闻链接";
	public static final String FRAME_EXIST_PATH_TEXT = "评论文件路径";
	public static final String FRAME_OUTPUT_PATH_TEXT = "评论内容输出路径";
	public static final String FRAME_BROWSER_BTN_TEXT = "浏览";
	public static final String FRAME_BROWSER_BTN_NAME = "awButton";
	public static final String FRAME_BROWSER_OUTPUT_BTN_TEXT = "浏览";
	public static final String FRAME_BROWSER_OUTPUT_BTN_NAME = "outputBwButton";

	// 第二个名称用到的部分名称
	public static final String FRAME_NEWS_TITLE_TEXT = "新闻标题";
	public static final String FRAME_QUICK_OPERATION_TEXT = "快捷操作";
	public static final String FRAME_BROWSER_COMMENT_TEXT = "查看评论";
	public static final String FRAME_BROWSER_COMMENT_NAME = "commentContentBtn";
	public static final String FRAME_HOT_WORDS_TEXT = "热门评论词";
	public static final String FRAME_HOT_WORDS_NAME = "freqWordsBtn";
	public static final String FRAME_STAT_RESULT_TEXT = "统计结果";
	public static final String FRAME_STAT_PIE_TEXT = "饼状图";
	public static final String FRAME_STAT_PIE_NAME = "pieBtn";
	public static final String FRAME_STAT_BAR_TEXT = "柱形图";
	public static final String FRAME_STAT_BAR_NAME = "barBtn";
	public static final String FRAME_STAT_LINE_TEXT = "折线图";
	public static final String FRAME_STAT_LINE_NAME = "lineBtn";
	public static final String FRAME_STAT_LINE_SPLIT_DIMENSION_TEXT = "折线图划分维度";
	public static final String FRAME_STAT_LINE_SPLIT_DAY_TEXT = "按天划分";
	public static final String FRAME_STAT_LINE_SPLIT_DAY_NAME = "dayCheck";
	public static final String FRAME_STAT_LINE_SPLIT_HOUR_TEXT = "按小时划分";
	public static final String FRAME_STAT_LINE_SPLIT_HOUR_NAME = "hourCheck";
	public static final String FRAME_BACK_BTN_TEXT = "上一步";
	public static final String FRAME_BACK_BTN_NAME = "backBtn";

	// 评论内容页标题
	public static final String FRAME_COMMENT_CONTENT_VIEW_NAME = "评论内容";
	
	//文件选择模式，选择到目录
	public static final int FILE_CHOSEN_DIR_MODE = 1;
	//选择到文件
	public static final int FILE_CHOSEN_FILE_MODE = 0;

	/**
	 * 判断字符str1是否包含字符str2
	 * 
	 * @param srt1
	 * @param str2
	 * @return
	 */
	public static boolean isContained(String str1, String str2) {
		boolean isContained;
		String temp;

		// 默认不包含
		isContained = false;

		// 如果字符str1的长度小于str2的长度，则直接返回
		if (str1.length() < str2.length()) {
			return isContained;
		}

		for (int i = 0; i <= str1.length() - str2.length(); i++) {
			// 在str1中截取与str2相应长度的子串，做匹配
			temp = str1.substring(i, i + str2.length());

			if (temp.equals(str2)) {
				isContained = true;
				break;
			}
		}

		return isContained;
	}

	/**
	 * 时间戳转化成天小时格式
	 * 
	 * @param timeStr
	 */
	public static String timeTransFormat(long time, int dimen) {
		String date;
		long t;
		SimpleDateFormat sdf;

		if (dimen == CHART_LINE_DIMENSION_DAY) {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		} else if (dimen == CHART_LINE_DIMENSION_HOUR) {
			sdf = new SimpleDateFormat("yyyy-MM-dd-HH时");
		} else {
			sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
		}

		date = sdf.format(new Date(time * 1000L));

		return date;
	}

	/**
	 * 初始化窗口位置，居中显示
	 */
	public static void initFrameLocation(JFrame frame) {
		int windowWidth = frame.getWidth(); // 获得窗口宽
		int windowHeight = frame.getHeight(); // 获得窗口高
		
		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		
		int screenWidth = screenSize.width; // 获取屏幕的宽
		int screenHeight = screenSize.height; // 获取屏幕的高
		
		frame.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2
				- windowHeight / 2);// 设置窗口居中显示
	}
}
