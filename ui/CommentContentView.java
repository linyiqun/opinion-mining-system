package TextMining.ui;

import static TextMining.tool.ConstantUtils.initFrameLocation;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import TextMining.crawler.entity.Comment;
import TextMining.tool.ConstantUtils;

public class CommentContentView {
	//新闻详情页标题
	private String newsTitle;
	//评论数据列表
	private ArrayList<Comment> commentList;
	
	public CommentContentView(String newsTitle, ArrayList<Comment> commentList) {
		this.newsTitle = newsTitle;
		this.commentList = commentList;
	}

	/**
	 * 显示评论内容数据
	 */
	public void showCommentContent() {
		StringBuilder strBuilder;
		String time;
		
		JFrame f = new JFrame(ConstantUtils.FRAME_COMMENT_CONTENT_VIEW_NAME);
		Container contentPane = f.getContentPane();
		contentPane.setLayout(new BorderLayout());

		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(1, 1));
		p1.setBorder(BorderFactory
				.createTitledBorder(newsTitle));

		JTextArea t1 = new JTextArea(25, 50);
		t1.setTabSize(10);
		t1.setFont(new Font("标楷体", Font.BOLD, 16));
		t1.setLineWrap(true);// 激活自动换行功能
		t1.setWrapStyleWord(true);// 激活断行不断字功能
		
		//设置不可编辑
		t1.setEditable(false);
		
		strBuilder = new StringBuilder();
		//首先显示标题行
		strBuilder.append(newsTitle);
		strBuilder.append("\n");
		
		//显示每行数据格式为时间：评论内容
		for(Comment c: commentList){
			//做时间转换
			time = ConstantUtils.timeTransFormat(c.getTime(), ConstantUtils.CHART_LINE_DIMENSION_DAY_AND_HOUR);
			
			strBuilder.append(time);
			strBuilder.append("：");
			strBuilder.append(c.getContent());
			//以换行符作为分隔符
			strBuilder.append("\n");
		}
		//将数据展现在文本中
		t1.setText(strBuilder.toString());
		t1.setBackground(ConstantUtils.BACK_COLOR);
		t1.setForeground(ConstantUtils.FONT_COLOR);
		// 将JTextArea放入JScrollPane中，这样就能利用滚动的效果看到输入超过JTextArea高度的
		p1.add(new JScrollPane(t1));
		// 文字.
		contentPane.add(p1);
		f.pack();
		f.show();
		f.setDefaultCloseOperation(2);
		initFrameLocation(f);
	}
}