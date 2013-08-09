package BLATStuff;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class UniqBLATHits {

	public static void main(String[] args) {
		
		try {
			HashMap map = new HashMap();
			String fileName = args[0];
			FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream din = new DataInputStream(fstream); 			
			BufferedReader in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				String[] split = str.split("\t");
				if (!map.containsKey(split[9])) {
					map.put(split[9], split[9]);
					System.out.println(str);
				} else {
					
				}
				
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
