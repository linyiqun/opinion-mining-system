package TextMining.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import TextMining.chart.BarChart;
import TextMining.chart.FrequentEWordsGraph;
import TextMining.chart.LineChart;
import TextMining.chart.PieChart;
import TextMining.entity.EWord;
import TextMining.tool.ConstantUtils;
import TextMining.tool.MiningTool;
import static TextMining.tool.ConstantUtils.*;

public class Entrance implements ActionListener {
	// 第一个界面
	JFrame firstFram;
	JPanel firstPanel;
	JTextField urlText;
	JTextField existCommentPathText;
	JTextField outputPathText;
	JLabel label;
	JButton browserButton;
	JButton outputBrowserButton;
	JButton miningButton;
	String urlStr;
	String existPathStr;
	String outputPathStr;

	// 第二个界面
	JFrame secondFrame;
	JPanel secondPanel;
	JLabel newsTitleLabel;
	JButton commentContentBtn;
	JButton freqWordsBtn;
	JButton pieBtn;
	JButton barBtn;
	JButton lineBtn;
	JButton backBtn;
	JCheckBox dayCheckBox;
	JCheckBox hourCheckBox;

	// 热门评论词界面
	JFrame hotWordsFrame;
	JPanel hotWordsPanel;

	// 评论内容页面
	CommentContentView ccView;
	JFrame commentContentFrame;
	JScrollPane jsp;

	// 挖掘类相关
	MiningTool mTool;

	// 文件选择器
	private JFileChooser awPathCh = new JFileChooser();

	private void firstFrame() {
		firstFram = new JFrame(MINING_SYSTEM_NAME);
		firstFram.setSize(FRAME_WIDTH_SIZE, FRAME_HEIGHT_SIZE);
		firstFram.setDefaultCloseOperation(3);
		firstFram.setResizable(false);

		firstPanel = new JPanel();
		firstPanel.setBackground(BACK_COLOR);
		firstPanel.setLayout(null);

		miningButton = new JButton(FRAME_START_MINING_BTN_TEXT);
		miningButton.setName(FRAME_START_MINING_BTN_NAME);
		miningButton.setBounds(350, 320, 130, 26);
		miningButton.addActionListener(this);
		firstPanel.add(miningButton);

		label = new JLabel(FRAME_URL_LABEL_TEXT);
		label.setBounds(20, 20, 100, 50);
		label.setForeground(Color.WHITE);
		firstPanel.add(label);

		label = new JLabel(FRAME_EXIST_PATH_TEXT);
		label.setBounds(20, 80, 100, 50);
		label.setForeground(FONT_COLOR);
		firstPanel.add(label);

		label = new JLabel(FRAME_OUTPUT_PATH_TEXT);
		label.setBounds(20, 140, 100, 50);
		label.setForeground(FONT_COLOR);
		firstPanel.add(label);

		urlText = new JTextField();
		urlText.setBounds(120, 20, 250, 50);
		firstPanel.add(urlText);

		existCommentPathText = new JTextField();
		existCommentPathText.setBounds(120, 80, 250, 50);
		firstPanel.add(existCommentPathText);

		outputPathText = new JTextField();
		outputPathText.setBounds(120, 140, 250, 50);
		firstPanel.add(outputPathText);

		browserButton = new JButton(FRAME_BROWSER_BTN_TEXT);
		browserButton.setName(FRAME_BROWSER_BTN_NAME);
		browserButton.setBounds(375, 100, 60, 25);
		browserButton.addActionListener(this);
		
		outputBrowserButton = new JButton(FRAME_BROWSER_OUTPUT_BTN_TEXT);
		outputBrowserButton.setName(FRAME_BROWSER_OUTPUT_BTN_NAME);
		outputBrowserButton.setBounds(375, 160, 60, 25);
		outputBrowserButton.addActionListener(this);

		firstPanel.add(browserButton);
		firstPanel.add(outputBrowserButton);
		firstFram.add(firstPanel);
		firstFram.setVisible(true);
		initFrameLocation(firstFram);
	}

	private void secondFrame() {
		JLabel label;

		secondFrame = new JFrame(MINING_SYSTEM_NAME);
		secondFrame.setSize(FRAME_WIDTH_SIZE, FRAME_HEIGHT_SIZE);
		secondFrame.setDefaultCloseOperation(3);
		secondFrame.setResizable(false);

		secondPanel = new JPanel();
		secondPanel.setBackground(BACK_COLOR);
		secondPanel.setLayout(null);

		label = new JLabel(FRAME_NEWS_TITLE_TEXT);
		label.setBounds(20, 20, 100, 50);
		label.setForeground(FONT_COLOR);
		secondPanel.add(label);

		newsTitleLabel = new JLabel("新闻标题");
		newsTitleLabel.setBounds(80, 20, 400, 50);
		newsTitleLabel.setForeground(FONT_COLOR);
		secondPanel.add(newsTitleLabel);

		label = new JLabel(FRAME_QUICK_OPERATION_TEXT);
		label.setBounds(20, 70, 100, 50);
		label.setForeground(FONT_COLOR);
		secondPanel.add(label);

		commentContentBtn = new JButton(FRAME_BROWSER_COMMENT_TEXT);
		commentContentBtn.setName(FRAME_BROWSER_COMMENT_NAME);
		commentContentBtn.setBounds(80, 80, 120, 25);
		commentContentBtn.addActionListener(this);
		secondPanel.add(commentContentBtn);

		freqWordsBtn = new JButton(FRAME_HOT_WORDS_TEXT);
		freqWordsBtn.setName(FRAME_HOT_WORDS_NAME);
		freqWordsBtn.setBounds(220, 80, 120, 25);
		freqWordsBtn.addActionListener(this);
		secondPanel.add(freqWordsBtn);

		label = new JLabel(FRAME_STAT_RESULT_TEXT);
		label.setBounds(20, 130, 100, 50);
		label.setForeground(FONT_COLOR);
		secondPanel.add(label);

		pieBtn = new JButton(FRAME_STAT_PIE_TEXT);
		pieBtn.setName(FRAME_STAT_PIE_NAME);
		pieBtn.setBounds(80, 140, 120, 25);
		pieBtn.addActionListener(this);
		secondPanel.add(pieBtn);

		barBtn = new JButton(FRAME_STAT_BAR_TEXT);
		barBtn.setName(FRAME_STAT_BAR_NAME);
		barBtn.setBounds(220, 140, 120, 25);
		barBtn.addActionListener(this);
		secondPanel.add(barBtn);

		lineBtn = new JButton(FRAME_STAT_LINE_TEXT);
		lineBtn.setName(FRAME_STAT_LINE_NAME);
		lineBtn.setBounds(360, 140, 120, 25);
		lineBtn.addActionListener(this);
		secondPanel.add(lineBtn);

		label = new JLabel(FRAME_STAT_LINE_SPLIT_DIMENSION_TEXT);
		label.setBounds(20, 190, 100, 50);
		label.setForeground(FONT_COLOR);
		secondPanel.add(label);

		dayCheckBox = new JCheckBox(FRAME_STAT_LINE_SPLIT_DAY_TEXT);
		dayCheckBox.setName(FRAME_STAT_LINE_SPLIT_DAY_NAME);
		dayCheckBox.setBounds(120, 200, 100, 25);
		secondPanel.add(dayCheckBox);

		hourCheckBox = new JCheckBox(FRAME_STAT_LINE_SPLIT_HOUR_TEXT);
		hourCheckBox.setName(FRAME_STAT_LINE_SPLIT_HOUR_NAME);
		hourCheckBox.setBounds(250, 200, 100, 25);
		secondPanel.add(hourCheckBox);

		backBtn = new JButton(FRAME_BACK_BTN_TEXT);
		backBtn.setName(FRAME_BACK_BTN_NAME);
		backBtn.setBounds(350, 320, 130, 26);
		backBtn.addActionListener(this);
		secondPanel.add(backBtn);

		secondFrame.add(secondPanel);
		secondFrame.setVisible(true);
		initFrameLocation(secondFrame);
	}

	/**
	 * 热门评论词页面
	 */
	private void hotWordsFrame() {
		int x;
		int y;
		JLabel label;
		Random random;
		EWord ew;
		ArrayList<EWord> hotWordsList;
		FrequentEWordsGraph feg;
		Color[] colors = new Color[]{
				Color.RED, Color.WHITE, Color.GREEN, Color.YELLOW
		};

		// 随机数生成器用来产生随机位置
		random = new Random();
		feg = new FrequentEWordsGraph(10, mTool.geteWordCount());
		hotWordsList = feg.eWordsSort();

		hotWordsFrame = new JFrame(MINING_SYSTEM_NAME);
		hotWordsFrame.setSize(FRAME_WIDTH_SIZE + 100, FRAME_HEIGHT_SIZE + 100);
		hotWordsFrame.setDefaultCloseOperation(2);
		hotWordsFrame.setResizable(false);

		hotWordsPanel = new JPanel();
		hotWordsPanel.setBackground(BACK_COLOR);
		hotWordsPanel.setLayout(null);

		// 取出前若干个个热门观点词做展示
		for (int i = 0; i < FRAME_TOP_FREQUENT_WORDS_SIZE; i++) {
			ew = hotWordsList.get(i);

			x = random.nextInt(45);
			y = random.nextInt(30);

			label = new JLabel(ew.getName());
			label.setFont(new Font("宋体", Font.BOLD, 35));
			label.setBounds(x*10, y*10, 200, 100);
			label.setForeground(colors[random.nextInt(colors.length)]);
			hotWordsPanel.add(label);
		}

		hotWordsFrame.add(hotWordsPanel);
		hotWordsFrame.setVisible(true);
		initFrameLocation(hotWordsFrame);
	}
	
	public static void main(String[] args) {
		Entrance entrance = new Entrance();
		entrance.firstFrame();
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String name;
		JButton bt = (JButton) e.getSource();

		name = bt.getName();
		if (name == FRAME_BROWSER_BTN_NAME) {
			//设置为文件选择模式
			awPathCh.setFileSelectionMode(FILE_CHOSEN_FILE_MODE);
			
			int showOpenDialog = awPathCh.showOpenDialog(firstPanel);
			File selectedFile = awPathCh.getSelectedFile();
			if (null != selectedFile
					&& showOpenDialog == JFileChooser.APPROVE_OPTION) {
				existCommentPathText.setText(selectedFile.getPath());
			}
		}else if(name == FRAME_BROWSER_OUTPUT_BTN_NAME){
			//设置为目录选择模式
			awPathCh.setFileSelectionMode(FILE_CHOSEN_DIR_MODE);
			
			int showOpenDialog = awPathCh.showOpenDialog(firstPanel);
			File selectedFile = awPathCh.getSelectedFile();
			if (null != selectedFile
					&& showOpenDialog == JFileChooser.APPROVE_OPTION) {
				outputPathText.setText(selectedFile.getPath());
			}
		}else if (name == FRAME_START_MINING_BTN_NAME) {
			firstFram.removeAll();
			firstFram.dispose();
			firstFram.setVisible(false);

			// 将输入的值赋值
			urlStr = urlText.getText();
			existPathStr = existCommentPathText.getText();
			outputPathStr = outputPathText.getText();

			// urlStr = "http://news.qq.com/a/20150508/004453.htm";
			if(outputPathStr == null || outputPathStr.equals("")){
				outputPathStr = DEFAULT_OUTPUT_PATH + "\\" + DEFAULT_OUTPUT_FILE_NAME;
			}else{
				outputPathStr = outputPathStr + "\\" +DEFAULT_OUTPUT_FILE_NAME;
			}

			secondFrame();

			mTool = new MiningTool(null, existPathStr, outputPathStr,
					urlStr);
			mTool.opinionMining();

			newsTitleLabel.setText(mTool.getNewsTitle());
		} else if (name == FRAME_BACK_BTN_NAME) {
			secondFrame.removeAll();
			secondFrame.dispose();
			secondFrame.setVisible(false);

			firstFrame();
		} else if (name == FRAME_STAT_PIE_NAME) {
			PieChart pc = new PieChart(mTool.getNewsTitle(),
					mTool.getTotalLevels());
			pc.buildChart();
		} else if (name == FRAME_STAT_BAR_NAME) {
			BarChart bc = new BarChart(mTool.getNewsTitle(),
					mTool.getTotalLevels());
			bc.buildChart();
		} else if (name == FRAME_STAT_LINE_NAME) {
			LineChart lc;
			// 判断选择的哪个时间维度的
			if (hourCheckBox.isSelected()) {
				lc = new LineChart(mTool.getNewsTitle(),
						ConstantUtils.CHART_LINE_DIMENSION_HOUR,
						mTool.geteWordTimeLevel());
			} else {
				lc = new LineChart(mTool.getNewsTitle(),
						ConstantUtils.CHART_LINE_DIMENSION_DAY,
						mTool.geteWordTimeLevel());
			}

			lc.buildChart();
		} else if (name == FRAME_HOT_WORDS_NAME) {
			hotWordsFrame();
		}else if(name == FRAME_BROWSER_COMMENT_NAME){
			ccView = new CommentContentView(mTool.getNewsTitle(), mTool.getCrawler().getCommentLists());
			ccView.showCommentContent();
		}
	}
}
