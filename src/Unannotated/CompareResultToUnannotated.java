package Unannotated;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class CompareResultToUnannotated {

	public static void main(String[] args) {
		
		try {
			String fileName = args[0];
			String fileName2 = args[1];
			int index = new Integer(args[2]);
			HashMap map = new HashMap();
			
					
			FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream din = new DataInputStream(fstream); 
			BufferedReader in = new BufferedReader(new InputStreamReader(din));						
			while(in.ready()) { 
				String str = in.readLine().trim();
				map.put(str, str);
			}
			in.close();
			HashMap hit = new HashMap();
			fstream = new FileInputStream(fileName);
			din = new DataInputStream(fstream); 
			in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				String[] split = str.split("\t");
				if (map.containsKey(split[index])) {
					hit.put(split[index], split[index]);
				}
			}
			in.close();
			System.out.println("Total Unannotated: " + map.size());
			System.out.println("Still Unannotated: " + (map.size() - hit.size()));
			System.out.println("Newly Found: " + hit.size());
		} catch (Exception e) {
			
		}
	}
}
