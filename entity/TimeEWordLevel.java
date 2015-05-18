package TextMining.entity;

import java.util.ArrayList;

import static TextMining.tool.ConstantUtils.*;

/**
 * 词汇时间极性类
 * @author lyq
 *
 */
public class TimeEWordLevel implements Comparable<TimeEWordLevel>{
	//词汇的统计时间
	String time;
	//属于该段时间内的极性列表
	ArrayList<Double> levels;
	
	/**
	 * 获取时间段值
	 * @return
	 */
	public String getTime(){
		return time;
	}
	
	public TimeEWordLevel(String time, ArrayList<Double> levels) {
		this.time = time;
		this.levels = levels;
	}
	
	/**
	 * 统计极性列表中的极性倾向，分积极，中立和消极
	 * @return
	 */
	public double[] statLevels(){
		double[] array;
		
		array = new double[3];
		for(double value: levels){
			if(value > 0){
				array[EWORD_LEVEL_NEGATIVE]++;
			}else if(value == 0){
				array[EWORD_LEVEL_NEUTRAL]++;
			}else{
				array[EWORD_LEVEL_POSITIVE]++;
			}
		}
		
		//对统计频率做归一化处理
		for(int i=0; i<array.length; i++){
			array[i] /= levels.size();
		}
		
		return array;
	}

	@Override
	public int compareTo(TimeEWordLevel o) {
		// TODO Auto-generated method stub
		return o.time.compareTo(this.time);
	}
}
