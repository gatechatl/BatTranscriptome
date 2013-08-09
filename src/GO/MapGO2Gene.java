package GO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;

public class MapGO2Gene {

	public static void main(String[] args) {
		
		try {
			HashMap map = new HashMap();
			String inputFile = "C:\\School Notes\\Bat\\Manuscript\\Bat Paper Draft Ver 3 Data Figure and Manuscript\\Figures\\GOslim\\Scaffold_GO_Mapping.txt";
			FileInputStream fstream = new FileInputStream(inputFile);
			DataInputStream din = new DataInputStream(fstream); 
			BufferedReader in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				String[] split = str.split("\t");
				split[0] = split[0].split(" ")[0];
				if (map.containsKey(split[1])) {
					
					String result = (String)map.get(split[1]);
					result += "\t" + split[2] + "(" + split[0] + ")";
				} else {
					map.put(split[1], split[2] + "(" + split[0] + ")");
				}
			} 
			in.close();
			String outputFile = "C:\\School Notes\\Bat\\Manuscript\\Bat Paper Draft Ver 3 Data Figure and Manuscript\\Figures\\GOslim\\Category Mapping.txt";
			FileWriter fwriter = new FileWriter(outputFile);
			BufferedWriter out = new BufferedWriter(fwriter);
			
			inputFile = "C:\\School Notes\\Bat\\Manuscript\\Bat Paper Draft Ver 3 Data Figure and Manuscript\\Figures\\GOslim\\GO_Term_GOSlim.txt";
			fstream = new FileInputStream(inputFile);
			din = new DataInputStream(fstream); 
			in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				String[] split = str.split("\t");
				out.write(split[0]);
				for (int i = 1; i < split.length; i++) {
					String content = (String)map.get(split[i]);
					out.write("\t" + content);
				}
				out.write("\n");
			} 
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
