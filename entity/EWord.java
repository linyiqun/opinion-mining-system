package TextMining.entity;

/**
 * 情绪词汇类
 * @author lyq
 *
 */
public class EWord implements Comparable<EWord>{
	//情绪词语名称
	private String name;
	//清晰词的词频
	private Integer count;

	public EWord(String name, int count){
		this.name = name;
		this.count = count;
	}
	
	/**
	 * 返回词语名称
	 * @return
	 */
	public String getName(){
		return name;
	}
	
	@Override
	public int compareTo(EWord o) {
		// TODO Auto-generated method stub
		return o.count.compareTo(this.count);
	}

}
