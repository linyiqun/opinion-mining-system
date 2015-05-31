package TextMining.chart;

import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;

import static TextMining.tool.ConstantUtils.*;
/**
 * 饼图构造类
 * 
 * @author lyq
 * 
 */
public class PieChart extends BaseChart {
	public PieChart(String chartName, ArrayList<Double> totalEWordLevels) {
		this.chartName = chartName;
		this.totalEWordLevels = totalEWordLevels;
	}

	@Override
	public void buildChart() {
		// TODO Auto-generated method stub
		String titleName;
		String levelName;
		double count;
		ChartFrame frame;
		DefaultPieDataset dfp;
		HashMap<String, Double> mapData;

		titleName = chartName + CHART_PIE;
		dfp = new DefaultPieDataset();

		mapData = parseEWordLevels();
		for (Map.Entry<String, Double> entry : mapData.entrySet()) {
			levelName = entry.getKey();
			count = entry.getValue();

			dfp.setValue(levelName, count);
		}

		jChart = ChartFactory.createPieChart(titleName, dfp, true, true, true);

		frame = new ChartFrame(MINING_SYSTEM_NAME, jChart, true);
		frame.pack();
		frame.setVisible(true);
		initFrameLocation(frame);
		
		super.buildChart();
	}

	@Override
	public void initChartStyle() {
		// TODO Auto-generated method stub
		PiePlot pie;
		TextTitle textTitle;
		LegendTitle legend;

		textTitle = jChart.getTitle();
		textTitle.setFont(new Font("宋体", Font.BOLD, 20));
		legend = jChart.getLegend();
		if (legend != null) {
			legend.setItemFont(new Font("宋体", Font.BOLD, 20));
		}

		pie = (PiePlot) jChart.getPlot();
		pie.setLabelFont(new Font("宋体", Font.BOLD, 12));
		pie.setNoDataMessage("No data available");
		pie.setCircular(true);
		pie.setLabelGap(0.01D);// 间距
	}
}
