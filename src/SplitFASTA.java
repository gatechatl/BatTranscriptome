import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;


public class SplitFASTA {

	
	public static void main(String[] args) {
		
		
		try {
			
			String fileName = args[0];
			FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream din = new DataInputStream(fstream); 			
			BufferedReader in = new BufferedReader(new InputStreamReader(din));
			
			
			FileWriter fwriter1 = new FileWriter(fileName + ".1");
			BufferedWriter out1 = new BufferedWriter(fwriter1);

			FileWriter fwriter2 = new FileWriter(fileName + ".2");
			BufferedWriter out2 = new BufferedWriter(fwriter2);
		
			FileWriter fwriter3 = new FileWriter(fileName + ".3");
			BufferedWriter out3 = new BufferedWriter(fwriter3);
			
			FileWriter fwriter4 = new FileWriter(fileName + ".4");
			BufferedWriter out4 = new BufferedWriter(fwriter4);
			
			FileWriter fwriter5 = new FileWriter(fileName + ".5");
			BufferedWriter out5 = new BufferedWriter(fwriter5);
			
			int count = 0;
			while (in.ready()) {
				String str = in.readLine();
				if (str.contains(">")) {
					count++;					
				}
				if (count % 5 == 0) {
					out1.write(str + "\n");
				}
				if (count % 5 == 1) {
					out2.write(str + "\n");
				}
				if (count % 5 == 2) {
					out3.write(str + "\n");
				}
				if (count % 5 == 3) {
					out4.write(str + "\n");
				}
				if (count % 5 == 4) {
					out5.write(str + "\n");
				}
				
				
			}
			in.close();
			out1.close();
			out2.close();
			out3.close();
			out4.close();
			out5.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
