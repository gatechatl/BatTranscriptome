package DnDs;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class FilterResult {

	public static void main(String[] args) {
		String fileName = args[0];
		try {
			FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream din = new DataInputStream(fstream); 
			BufferedReader in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				String[] split = str.split("\t");
				String[] name = split[0].split("-");
				double dNdS = new Double(split[1]);
				double dN = new Double(split[2]);
				double dS = new Double(split[3]);
				double countMed = 0;
				double countLow = 0;
				double countPos = 0;
				if (dNdS >= 0.7 && dNdS <= 1.0) {
					countMed++;
					System.out.println(name[0] + "\t" + name[1] + "\t" + dNdS);
				} else if (dNdS > 1.0) {
					countPos++;
					System.out.println(name[0] + "\t" + name[1] + "\t" + dNdS);
				} else if (dNdS != -1) {
					countLow++;
				}

			}
			//System.out.println(countLow + "\t" + countMed + "\t" + countPos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
