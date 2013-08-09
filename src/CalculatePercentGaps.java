import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;


public class CalculatePercentGaps {
	public static void main(String[] args) {
		
		try {
			
			for (int i = 0; i < 8000; i ++) {
				String num = "";
				if (new Integer(i).toString().length() == 1) {
					num = "000" + i;
				} else if (new Integer(i).toString().length() == 2) {
					num = "00" + i;
				} else if (new Integer(i).toString().length() == 3) {
					num = "0" + i;
				} else if (new Integer(i).toString().length() == 4) {
					num = "" + i;
				}
				String fileName = "New_Combined." + num + ".best";
				
				boolean write = false;
			    FileInputStream fstream = new FileInputStream(fileName);
				DataInputStream din = new DataInputStream(fstream); 			
				BufferedReader in = new BufferedReader(new InputStreamReader(din));		
				while (in.ready()) {
					String str = in.readLine();
					if (str.contains(">Bat")) {
						String sequence = in.readLine();
						double gapCount = 0;
						for (int j = 0; j < sequence.length(); j++) {
							if (sequence.substring(j, j + 1).equals("-")) {
								gapCount++;
							}
						}
						if (gapCount / sequence.length() > 0.85) {
							write = true;
						}
					}
				}
				in.close();
				FileWriter fwriter = new FileWriter("NoGap_" + fileName);
				BufferedWriter out = new BufferedWriter(fwriter);
				
			    fstream = new FileInputStream(fileName);
				din = new DataInputStream(fstream); 			
				in = new BufferedReader(new InputStreamReader(din));		
				while (in.ready()) {
					String str = in.readLine();
					out.write(str + "\n");
				}
				in.close();
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
