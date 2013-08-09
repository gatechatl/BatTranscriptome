package GO;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class FilterGeneList {

	
	public static void main(String[] args) {
		
		String fileName = "C:\\School Notes\\Bat\\Manuscript\\PLOS Manuscript\\Immune Related Transcripts\\GeneList.txt";
		
		try {
			HashMap map = new HashMap();
			FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream din = new DataInputStream(fstream); 
			BufferedReader in = new BufferedReader(new InputStreamReader(din));
			in.readLine();
			while (in.ready()) {
				String str = in.readLine();
				String[] split = str.split("\t");
				for (int i = 0; i < split.length; i++) {
					map.put(split[i], split[i]);
				}
			}
			in.close();
			System.out.println(map.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
