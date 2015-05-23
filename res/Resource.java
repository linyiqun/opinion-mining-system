package TextMining.res;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Resource {
	public ArrayList<String> getResource() {
		String s;
		InputStream is;
		BufferedReader br;
		ArrayList<String> lines;

		s = "";
		lines = new ArrayList<>();
		// 读取此类的包下的资源文件
		is = this.getClass().getResourceAsStream("dict.txt");

		br = new BufferedReader(new InputStreamReader(is));

		try {
			// 逐行读入
			while ((s = br.readLine()) != null) {
				lines.add(s);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lines;
	}
}
