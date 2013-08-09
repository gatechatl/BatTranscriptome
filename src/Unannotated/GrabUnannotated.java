package Unannotated;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class GrabUnannotated {

	public static void main(String[] args) {
		
		
		try {
			String fileName = args[0];
			String fileName2 = args[1];
			HashMap map = new HashMap();
			
					
			FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream din = new DataInputStream(fstream); 
			BufferedReader in = new BufferedReader(new InputStreamReader(din));						
			while(in.ready()) { 
				String str = in.readLine().trim();
				map.put(str, str);
			}
			in.close();
			
			fstream = new FileInputStream(fileName2);
			din = new DataInputStream(fstream); 
			in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine().trim();
				if (!map.containsKey(str)) {
					System.out.println(str);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
