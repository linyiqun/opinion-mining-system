package TextMining.chart;

import static TextMining.tool.ConstantUtils.CHART_LINE;
import static TextMining.tool.ConstantUtils.CHART_LINE_X_TITLE;
import static TextMining.tool.ConstantUtils.CHART_LINE_Y_TITLE;
import static TextMining.tool.ConstantUtils.EWORD_LEVEL_NEGATIVE;
import static TextMining.tool.ConstantUtils.EWORD_LEVEL_NEGATIVE_NAME;
import static TextMining.tool.ConstantUtils.EWORD_LEVEL_NEUTRAL;
import static TextMining.tool.ConstantUtils.EWORD_LEVEL_NEUTRAL_NAME;
import static TextMining.tool.ConstantUtils.EWORD_LEVEL_POSITIVE;
import static TextMining.tool.ConstantUtils.EWORD_LEVEL_POSITIVE_NAME;
import static TextMining.tool.ConstantUtils.MINING_SYSTEM_NAME;
import static TextMining.tool.ConstantUtils.initFrameLocation;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;

import TextMining.entity.TimeEWordLevel;
import TextMining.tool.ConstantUtils;

/**
 * 折线统计图
 * 
 * @author lyq
 * 
 */
public class LineChart extends BaseChart {
	//显示的时间维度单位
	public int timeDimension;
	// 词汇时间极性数据
	private Map<String, ArrayList<Double>> mapData;

	public LineChart(String chartName, int timeDimension, Map<String, ArrayList<Double>> mapData) {
		this.timeDimension = timeDimension;
		this.chartName = chartName;
		this.mapData = mapData;
	}

	@Override
	public void initChartStyle() {
		// TODO Auto-generated method stub
		CategoryPlot plot;
		CategoryAxis domainAxis;
		ValueAxis valueAxis;
		TextTitle textTitle;
		LegendTitle legend;

		textTitle = jChart.getTitle();
		textTitle.setFont(new Font("宋体", Font.BOLD, 20));
		legend = jChart.getLegend();
		if (legend != null) {
			legend.setItemFont(new Font("宋体", Font.BOLD, 20));
		}

		plot = (CategoryPlot) jChart.getPlot();
		// (柱状图的x轴)
		domainAxis = plot.getDomainAxis();
		// 设置x轴坐标上的字体
		domainAxis.setTickLabelFont(new Font("宋体", Font.BOLD, 20));
		// 设置x轴上的标题的字体
		domainAxis.setLabelFont(new Font("宋体", Font.BOLD, 20));
		// (柱状图的y轴)
		valueAxis = plot.getRangeAxis();
		// 设置y轴坐标上的字体
		valueAxis.setTickLabelFont(new Font("宋体", Font.BOLD, 20));
		// 设置y轴坐标上的标题的字体
		valueAxis.setLabelFont(new Font("宋体", Font.BOLD, 20));
	}

	@Override
	public void buildChart() {
		// TODO Auto-generated method stub
		double[] infoCount;
		String chartTitle;
		String time;
		ChartFrame frame;
		TimeEWordLevel tEwl;
		ArrayList<Double> levelList;
		// 时间极性列表
		ArrayList<TimeEWordLevel> timeLevels;
		//经过时间维度转换后的图数据
		Map<String, ArrayList<Double>> timeMapData;

		//将时间戳图数据按时间单位维度分隔
		timeMapData = timeTransForm(mapData);
		
		timeLevels = new ArrayList<>();
		for (Map.Entry<String, ArrayList<Double>> entry : timeMapData.entrySet()) {
			time = entry.getKey();
			levelList = entry.getValue();

			tEwl = new TimeEWordLevel(time, levelList);

			timeLevels.add(tEwl);
		}
		Collections.sort(timeLevels);

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		TimeEWordLevel t;
		timeLevels = selectTopData(timeLevels);
		
		//按照时间先后顺序倒着输出值
		for(int i=timeLevels.size()-1; i>=0; i--){
			t = timeLevels.get(i);
			
			time = t.getTime();
			infoCount = t.statLevels();

			dataset.addValue(infoCount[EWORD_LEVEL_POSITIVE],
					EWORD_LEVEL_POSITIVE_NAME, time);
			dataset.addValue(infoCount[EWORD_LEVEL_NEUTRAL],
					EWORD_LEVEL_NEUTRAL_NAME, time);
			dataset.addValue(infoCount[EWORD_LEVEL_NEGATIVE],
					EWORD_LEVEL_NEGATIVE_NAME, time);
		}

		chartTitle = this.chartName + CHART_LINE;
		// 创建图表对象
		jChart = ChartFactory.createLineChart(chartTitle, CHART_LINE_X_TITLE,
				CHART_LINE_Y_TITLE, dataset, PlotOrientation.VERTICAL, true,
				true, false);

		frame = new ChartFrame(MINING_SYSTEM_NAME, jChart, true);
		frame.pack();
		frame.setVisible(true);
		initFrameLocation(frame);
		
		super.buildChart();
	}
	
	/**
	 * 将图数据进行时间维度的汇总
	 * @param mapData
	 * @return
	 */
	private Map<String , ArrayList<Double>> timeTransForm(Map<String , ArrayList<Double>> mapData){
		Map<String , ArrayList<Double>> newMapData;
		long time;
		String newTime;
		ArrayList<Double> list;
		ArrayList<Double> levels;
		newMapData = new HashMap<String, ArrayList<Double>>();
		
		//根据选定的时间维度进行汇总
		for(Map.Entry<String, ArrayList<Double>> entry: mapData.entrySet()){
			time = Long.parseLong(entry.getKey());
			list = entry.getValue();
			
			newTime = ConstantUtils.timeTransFormat(time, timeDimension);
			
			if(newMapData.containsKey(newTime)){
				levels = newMapData.get(newTime);
			}else{
				levels = new ArrayList<>();
			}
			
			//进行数据的汇总
			levels.addAll(list);
			
			//重新存入新图中
			newMapData.put(newTime, levels);
		}
		
		return newMapData;
	}
	
	/**
	 * 截取数据
	 */
	private ArrayList<TimeEWordLevel> selectTopData(ArrayList<TimeEWordLevel> data){
		ArrayList<TimeEWordLevel> newData;
		//只截取5个时间段的时刻
		int limit = 0;
		
		newData = new ArrayList<>();
		for(TimeEWordLevel t: data){
			if(limit == ConstantUtils.CHART_LINE_SHOW_NUMS_LIMIT){
				break;
			}
			
			newData.add(t);
			
			limit++;
		}
		
		return newData;
	}
}