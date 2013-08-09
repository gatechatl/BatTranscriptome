package GO;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class CountAnnotatedGenes {
	public static void main(String[] args) {
		
		String fileName = "C:\\School Notes\\Bat\\BlastHits.txt";
		
		try {
			HashMap map = new HashMap();
			FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream din = new DataInputStream(fstream); 
			BufferedReader in = new BufferedReader(new InputStreamReader(din));
			in.readLine();
			while (in.ready()) {
				String str = in.readLine();
				map.put(str, str);
			}
			in.close();
			System.out.println(map.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
