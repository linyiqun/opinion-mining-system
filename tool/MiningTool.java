package TextMining.tool;

import static TextMining.tool.ConstantUtils.CHINESE_PUNCTUATION_COMMA;
import static TextMining.tool.ConstantUtils.CHINESE_PUNCTUATION_END;
import static TextMining.tool.ConstantUtils.CHINESE_PUNCTUATION_EXCLAMATORY;
import static TextMining.tool.ConstantUtils.CHINESE_PUNCTUATION_QUESTION;
import static TextMining.tool.ConstantUtils.CRAWLER_PER_REQ_NUM;
import static TextMining.tool.ConstantUtils.CRAWLER_TOTAL_REQ_NUM;
import static TextMining.tool.ConstantUtils.DICT_WORD_CLASS;
import static TextMining.tool.ConstantUtils.DICT_WORD_CONTENT;
import static TextMining.tool.ConstantUtils.DICT_WORD_STRONG;
import static TextMining.tool.ConstantUtils.HASH_NUM;
import static TextMining.tool.ConstantUtils.NEGATIVE_WORDS_LEVEL;
import static TextMining.tool.ConstantUtils.POSITIVE_WORDS_LEVEL;
import static TextMining.tool.ConstantUtils.isContained;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import TextMining.chart.BarChart;
import TextMining.chart.LineChart;
import TextMining.crawler.QQNewsCrawler;
import TextMining.crawler.entity.Comment;
import TextMining.entity.Sentence;
import TextMining.res.Resource;

/**
 * 文本挖掘算法工具类
 * 
 * @author lyq
 * 
 */
public class MiningTool {
	// 情绪词库文件路径
	private String ewFilePath;
	// 已有评论数据路径
	private String existDataPath;
	// 每次随机采样的文本数
	private int sampleNum;
	// 随机数产生器
	private Random random;
	// 评论的输出路径
	private String outputPath;
	// 腾讯新闻页url链接
	private String newsUrl;
	// 爬取到的新闻标题
	private String newsTitle;
	// 腾讯新闻爬虫工具
	private QQNewsCrawler crawler;
	// 预处理类
	private PreTreatTool preTreatTool;
	// 总的文本数据
	private ArrayList<Sentence> mainWords;
	// 情绪词库行数据
	private ArrayList<String> emotionLines;
	// 情绪词库数据
	private ArrayList<String> emotionWords;
	// 总的抽样采取的评论的极性
	private ArrayList<Double> totalLevels;
	// 情绪词词频统计图
	private Map<String, Integer> eWordCount;
	// 时间观点极性图
	private Map<String, ArrayList<Double>> eWordTimeLevel;
	// 观点情绪词语，用哈希算法保存情绪词语的观点极性
	private double[] eword2Level;

	public MiningTool(String ewFilePath, String existDataPath,
			String outputPath, String newsUrl) {
		this.ewFilePath = ewFilePath;
		this.existDataPath = existDataPath;
		this.outputPath = outputPath;
		this.newsUrl = newsUrl;

		this.random = new Random();
		this.crawler = new QQNewsCrawler(newsUrl, CRAWLER_TOTAL_REQ_NUM,
				CRAWLER_PER_REQ_NUM, outputPath);

		initEWord2Level();
		initCommemtDatas();
	}

	/**
	 * 获取爬虫类
	 * 
	 * @return
	 */
	public QQNewsCrawler getCrawler() {
		return crawler;
	}

	/**
	 * 获取观点词频统计图
	 * 
	 * @return
	 */
	public Map<String, Integer> geteWordCount() {
		return eWordCount;
	}

	/**
	 * 获取新闻标题
	 * 
	 * @return
	 */
	public String getNewsTitle() {
		return newsTitle;
	}

	/**
	 * 获取总的观点极性
	 * 
	 * @return
	 */
	public ArrayList<Double> getTotalLevels() {
		return totalLevels;
	}

	/**
	 * 获取时间观点极性图数据
	 * 
	 * @return
	 */
	public Map<String, ArrayList<Double>> geteWordTimeLevel() {
		return eWordTimeLevel;
	}

	/**
	 * 从文件中读取数据
	 * 
	 * @param filePath
	 *            单个文件
	 */
	private ArrayList<String> readDataFile(String filePath) {
		File file = new File(filePath);
		ArrayList<String> sentences = new ArrayList<>();

		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String str;
			while ((str = in.readLine()) != null) {
				sentences.add(str);
			}
			in.close();
		} catch (IOException e) {
			e.getStackTrace();
		}

		return sentences;
	}

	/**
	 * 初始化情绪词极性哈希数组
	 */
	private void initEWord2Level() {
		double level = 0;
		long pos = 0;
		String[] array;

		emotionWords = new ArrayList<>();
		eword2Level = new double[ConstantUtils.HASH_NUM];

		//判断是否有系统外的字典库存在
		if(ewFilePath == null || ewFilePath.equals("")){
			//没有的话，加载系统自带的字典库
			emotionLines = new Resource().getResource();
		}else{
			//有则到文件中加载
			emotionLines = readDataFile(ewFilePath);
		}
		emotionLines.remove(0);

		for (String str : emotionLines) {
			array = str.split(ConstantUtils.PUNCTUATION_TAB);

			pos = HashTool.BKDRHash(array[DICT_WORD_CONTENT]);
			pos %= ConstantUtils.HASH_NUM;

			level = calWordLevel(array[DICT_WORD_CLASS],
					array[DICT_WORD_STRONG]);

			// 存入哈希数组中
			eword2Level[(int) pos] = level;
			// 将首个情绪词加入列表中
			emotionWords.add(array[0]);
		}
	}

	// 读入评论数据做小数据集的测试
	private void initCommemtDatas() {
		String reg;
		String[] array;
		Sentence sentence;
		long time;
		String s;

		// 评论列表数据
		ArrayList<Comment> commentList;
		ArrayList<String> commentLines;
		ArrayList<String> words;

		this.mainWords = new ArrayList<>();
		this.preTreatTool = new PreTreatTool();

		// 组装句子拆分正则匹配字符
		reg = CHINESE_PUNCTUATION_END + "|" + CHINESE_PUNCTUATION_COMMA + "|"
				+ CHINESE_PUNCTUATION_EXCLAMATORY + "|"
				+ CHINESE_PUNCTUATION_QUESTION;

		// 如果已有评论数据未空，则说明是爬取数据的方式
		if (existDataPath == null || existDataPath.equals("")) {
			// 新闻评论数据爬取
			crawler.crawlNewsComments();
			commentList = crawler.getCommentLists();
			newsTitle = crawler.getNewsTitle();
			System.out.println("成功抓取到评论数据");
			System.out.println("开始进行观点数据分析");

			for (Comment c : commentList) {
				time = c.getTime();
				s = c.getContent();

				// 进行预处理操作
				s = preTreatTool.preTreatWords(s);
				array = s.split(reg);

				words = new ArrayList<>();
				for (String w : array) {
					words.add(w);
				}

				sentence = new Sentence(words, time);
				mainWords.add(sentence);
			}
		} else {
			// 从文件中读取评论的方式
			commentLines = readDataFile(existDataPath);

			for (String c : commentLines) {
				s = c;

				// 进行预处理操作
				s = preTreatTool.preTreatWords(s);
				array = s.split(reg);

				words = new ArrayList<>();
				for (String w : array) {
					words.add(w);
				}

				sentence = new Sentence(words, 0);
				mainWords.add(sentence);
			}
		}
	}

	/**
	 * 根据情绪词的分类和强度，计算词语的极性
	 * 
	 * @param classType
	 * @param strong
	 * @return
	 */
	private double calWordLevel(String classType, String strong) {
		int index;
		int strongLevel;
		double resultLevel;
		boolean isPositive;

		index = -1;
		isPositive = false;
		strongLevel = Integer.parseInt(strong);

		for (int i = 0; i < POSITIVE_WORDS_LEVEL.length; i++) {
			if (POSITIVE_WORDS_LEVEL[i].equals(classType)) {
				index = i;
				isPositive = true;
				break;
			}
		}

		if (!isPositive) {
			for (int i = 0; i < NEGATIVE_WORDS_LEVEL.length; i++) {
				if (NEGATIVE_WORDS_LEVEL[i].equals(classType)) {
					index = i;
					isPositive = false;
					break;
				}
			}
		}

		resultLevel = index * 0.1 + strongLevel * 1.0 / 100;
		// 做归一化的处理
		resultLevel /= (NEGATIVE_WORDS_LEVEL.length * 0.1);

		// 根据词性性质，添加符号
		if (!isPositive) {
			resultLevel *= -1;
		}

		return resultLevel;
	}

	/**
	 * 根据给定词获取词的极性
	 * 
	 * @param word
	 */
	private double getWordLevel(String word) {
		boolean isContained;
		double level;
		long pos;

		// 默认词的极性是中性为0
		level = 0;

		// 语义字典词遍历扫描
		for (String ew : emotionWords) {
			isContained = isContained(word, ew);

			if (isContained) {
				countEWord(ew);

				// 如果匹配上了，把这个词对应的极性从哈希数组中取出
				pos = HashTool.BKDRHash(ew);
				pos %= HASH_NUM;

				level += eword2Level[(int) pos];
			}
		}

		return level;
	}

	/**
	 * 统计情绪词的计数量
	 * 
	 * @param ew
	 *            当前匹配到的语义词
	 */
	private void countEWord(String ew) {
		int count;

		if (eWordCount.containsKey(ew)) {
			count = eWordCount.get(ew);
		} else {
			count = 0;
		}

		// 进行计数加1并重新存入图中
		count++;
		eWordCount.put(ew, count);
	}

	/**
	 * 进行观点挖掘的过程
	 */
	public void opinionMining() {
		this.eWordCount = new HashMap<String, Integer>();
		this.eWordTimeLevel = new HashMap<>();

		// 通过迭代的方式获取部分观点的极性
		totalLevels = getAllEWordLevels();
	}

	/**
	 * 获取样本中所有的观点极性词语
	 * 
	 * @return
	 */
	private ArrayList<Double> getAllEWordLevels() {
		ArrayList<Double> totalLevels;
		ArrayList<String> childSentences;
		double sentenceLevel;
		String time;

		totalLevels = new ArrayList<>();
		for (Sentence sentence : mainWords) {
			sentenceLevel = 0;
			time = sentence.time + "";
			childSentences = sentence.childSentences;

			// 对于每段评论，再做分词的词性识别
			for (String childWord : childSentences) {
				sentenceLevel += getWordLevel(childWord);
			}

			// 对超出范围的极性数据做修正
			if (sentenceLevel > 0.99) {
				sentenceLevel = 0.99;
			} else if (sentenceLevel < -0.99) {
				sentenceLevel = -0.99;
			}

			insertTimeLevelToMap(time, sentenceLevel);
			totalLevels.add(sentenceLevel);
		}

		return totalLevels;
	}

	/**
	 * 插入词语时间极性到图中
	 * 
	 * @param time
	 * @param level
	 */
	private void insertTimeLevelToMap(String time, double level) {
		ArrayList<Double> levelList;

		if (eWordTimeLevel.containsKey(time)) {
			levelList = eWordTimeLevel.get(time);
		} else {
			levelList = new ArrayList<>();
		}

		// 加入当前的level列表中
		levelList.add(level);
		eWordTimeLevel.put(time, levelList);
	}

	/**
	 * 从文本有效词中随机获取有效的观点极性
	 * 
	 * @return
	 */
	/*
	 * private ArrayList<Double> getRandomEWordLevels() { boolean keepRunning;
	 * int temp; // 观点极性 double level = 0; // 上次采取的随机值 double avgLevel; double
	 * sumLevel = 0; double tempLevelSum; double tempAVgLevel; long pos; //
	 * 每段评论包含的词干关键词 ArrayList<String> tempWords; ArrayList<Integer>
	 * sampleRandomNum; // 总的抽样采取的评论的极性 ArrayList<Double> totalLevels;
	 * 
	 * keepRunning = true; avgLevel = 0; sumLevel = 0; totalLevels = new
	 * ArrayList<>(); while (keepRunning) { tempLevelSum = 0; sampleRandomNum =
	 * new ArrayList<>(); for (int i = 0; i < sampleNum;) { temp =
	 * random.nextInt(mainWords.size());
	 * 
	 * if (!sampleRandomNum.contains(temp)) { sampleRandomNum.add(temp); i++; }
	 * }
	 * 
	 * for (int i : sampleRandomNum) { tempWords = mainWords.get(i); level = 0;
	 * // 用于对每段评论中的情形词的计数 temp = 0;
	 * 
	 * // 取出拆分其中的每个词语 for (String w : tempWords) { // 从哈希表中取对应值 pos =
	 * HashTool.BKDRHash(w); pos %= ConstantUtils.HASH_NUM;
	 * 
	 * if (eword2Level[(int) pos] != 0) { level += eword2Level[(int) pos];
	 * temp++; } }
	 * 
	 * if (level != 0) { level /= temp; totalLevels.add(level); tempLevelSum +=
	 * level; } }
	 * 
	 * sumLevel += tempLevelSum; tempAVgLevel = tempLevelSum /
	 * sampleRandomNum.size(); // 最后取一个极性平均值，作为新闻最终的 avgLevel = sumLevel /
	 * totalLevels.size();
	 * 
	 * // 刚刚开始的第一次情况因为没有参考值，直接跳过 if (sumLevel == tempLevelSum) { continue; }
	 * 
	 * // 与目前的所有采集的做平均树对比，小于一定的阈值就算终止条件 if (Math.abs(avgLevel - tempAVgLevel) <
	 * 0.1) { // 如果所求极性的平均值已经小于所给阈值，则可以跳出 keepRunning = false; } }
	 * 
	 * return totalLevels; }
	 */
}
