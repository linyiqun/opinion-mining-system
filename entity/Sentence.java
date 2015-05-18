package TextMining.entity;

import java.util.ArrayList;

/**
 * 观点句子
 * 
 * @author lyq
 * 
 */
public class Sentence {
	// 观点句子中的每个子句
	public ArrayList<String> childSentences;
	// 观点句子的发表时间
	public long time;
	
	public Sentence(ArrayList<String> childSentences, long time) {
		this.childSentences = childSentences;
		this.time = time;
	}
}
