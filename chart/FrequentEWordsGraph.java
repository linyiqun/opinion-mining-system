package TextMining.chart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import TextMining.entity.EWord;

/**
 * 最频繁出现的情形词语图
 * @author lyq
 *
 */
public class FrequentEWordsGraph {
	//需要输出前多少的词语
	private int k;
	//统计出的所有的观点情绪词
	private Map<String, Integer> eWordsCount;
	
	public FrequentEWordsGraph(int k, Map<String, Integer> eWordsCount){
		this.k = k;
		this.eWordsCount = eWordsCount;
	}
	
	/**
	 * 显示次数出现最多的观点词
	 */
	public void showTopKEWords(){
		ArrayList<EWord> wordList;
		
		wordList = eWordsSort();
		try {
			new ShowEWords(wordList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据词频，进行降序排序
	 */
	public ArrayList<EWord> eWordsSort(){
		String wordName;
		int count;
		EWord ew;
		ArrayList<EWord> wordList;
		
		wordList = new ArrayList<>();
		for(Map.Entry<String, Integer> entry: eWordsCount.entrySet()){
			wordName = entry.getKey();
			count = entry.getValue();
			
			ew = new EWord(wordName, count);
			wordList.add(ew);
		}
		
		//进行降序排序
		Collections.sort(wordList);
		
		return wordList;
	}
	
	class ShowEWords extends JFrame
	{
	JTextArea ta = null; 
	JScrollPane jsp = null;
	EWord ew;
	
	ShowEWords(ArrayList<EWord> wordList) throws Exception {
	this.setVisible(true);
	this.setSize(300,300);
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	ta = new JTextArea();
	jsp = new JScrollPane(ta);
	ta.setText(null);
	
	//筛选出前k个观点词
	for(int i=0; i<k; i++){
		ew = wordList.get(i);
		
		ta.append(ew.getName());
		ta.append("\n");
	}
	
	add(jsp);
	validate();
	}
	}
}
