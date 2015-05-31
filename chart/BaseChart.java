package TextMining.chart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jfree.chart.JFreeChart;

import static TextMining.tool.ConstantUtils.*;

/**
 * 图构造基类
 * @author lyq
 *
 */
public abstract class BaseChart {
	//统计图的名称
	protected String chartName;
	//统计完成的情绪词的极性数据
	protected ArrayList<Double> totalEWordLevels;
	//图类，用来设置图的样式
	protected JFreeChart jChart;
	
	/**
	 * 解析统计图极性数据，得到统计键值对
	 * @return
	 */
	public HashMap<String, Double> parseEWordLevels(){
		String levelName;
		int pos;
		HashMap<String, Double> map;
		
		map = new HashMap<>();
		for(Double level: totalEWordLevels){
			//如果词性为中立，则直接存入
			if(level == 0){
				levelName = EWORD_NEUTRAL_LEVEL;
			}else if(level > 0){
				pos = (int) (level/EWORD_PER_LEVEL);
				levelName = EWORD_POSITIVE_LEVEL[pos];
			}else{
				pos = (int) (-1.0 * level/EWORD_PER_LEVEL);
				levelName = EWORD_NEGATIVE_LEVEL[pos];
			}
			
			insertDataToMap(levelName, map);
		}
		
		return map;
	}
	
	/**
	 * 插入数据到图中
	 * @param levelName
	 * @param map
	 */
	private void insertDataToMap(String levelName, HashMap<String, Double> map){
		double count;
		
		if(map.containsKey(levelName)){
			count = map.get(levelName);
		}else{
			count = 0;
		}
		
		//进行计数加1并重新存入图中
		count++;
		map.put(levelName, count);
	}
	
	/**
	 * 初始化图的样式
	 * @return
	 */
	public abstract void initChartStyle();
	
	/**
	 * 创建图的方法
	 */
	public void buildChart(){
		initChartStyle();
	}
}
