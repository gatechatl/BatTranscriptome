package DnDs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;

public class ExtractDnDsResult {

	public static void main(String[] args) {
		try {
			String directionFile = args[0];		
			String inputFile = args[1];
			String outputFile = args[2];
			
			FileWriter fwriter = new FileWriter(outputFile);
			BufferedWriter out = new BufferedWriter(fwriter);
			
			HashMap map = new HashMap();
			HashMap filter = new HashMap();
			FileInputStream fstream = new FileInputStream(directionFile);
			DataInputStream din = new DataInputStream(fstream); 
			BufferedReader in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				String[] split = str.split("\t");
				String key = split[1] + "-" + split[0];
				map.put(key, split[2]);
			}
			
			in.close();
			int count = 0;
			fstream = new FileInputStream(inputFile);
			din = new DataInputStream(fstream); 
			in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				String[] split = str.split("\t");
				if (count % 2 == 0) {
					/* forward */
					if (map.get(split[0]).equals("true")) {
						out.write(str + "\n");
					}
				} else {
					/* reverse */
					if (map.get(split[0]).equals("false")) {
						out.write(str + "\n");
					}
				}
				count++;
			}
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
