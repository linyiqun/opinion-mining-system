package TextMining.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static TextMining.tool.ConstantUtils.*;

/**
 * 文档预处理工具类
 * 
 * @author lyq
 * 
 */
public class PreTreatTool {
	// 输出的有效词集合
	private ArrayList<ArrayList<String>> effectWords;

	/**
	 * 获取文档有效词文件路径
	 * 
	 * @return
	 */
	public ArrayList<ArrayList<String>> getEFWPaths() {
		return this.effectWords;
	}

	/**
	 * 对文本进行无效词的过滤和词干信息的提取
	 * 
	 * @param sentence
	 */
	public String preTreatWords(String sentence) {
		// 数字匹配
		Pattern numberPattern;
		Matcher numberMatcher;
		String[] strArray;
		String resultSentence;

		numberPattern = Pattern.compile("[0-9]+(.[0-9]+)?");

		strArray = new String[sentence.length()];
		for (int i = 0; i < sentence.length(); i++) {
			strArray[i] = sentence.charAt(i) + "";

			numberMatcher = numberPattern.matcher(strArray[i]);
			// 如果字符是数字进行过滤
			if (numberMatcher.matches()) {
				strArray[i] = "";
			}

			// 进行过滤词的过滤
			for (String filerWord : FILTERED_WORDS) {
				if (strArray[i].equals(filerWord)) {
					strArray[i] = "";
					break;
				}
			}
		}

		resultSentence = "";
		// 将过滤后的词重新组装
		for (String s : strArray) {
			resultSentence += s;
		}

		return resultSentence;
	}

}
