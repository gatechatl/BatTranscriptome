package DnDs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class Split2Thousand {

	public static void main(String[] args) {
		String fileName = args[0];
		
		try {
			
			int count = 0;
			String outputFile = args[1] + count;
			FileWriter fwriter = new FileWriter(outputFile);
			BufferedWriter out = new BufferedWriter(fwriter);
			
			FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream din = new DataInputStream(fstream); 
			BufferedReader in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				if (str.contains(">")) {
					
					if (count % 1000 == 0 && count != 0) {
						out.close();
						outputFile = args[1] + count;
						fwriter = new FileWriter(outputFile);
						out = new BufferedWriter(fwriter);
					}
					count++;
				}
				out.write(str + "\n");
			}
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
