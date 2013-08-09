package DnDs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

public class ExtractSequences {

	public static void main(String[] args) {
		
		try {
			
			String fileName1 = args[0];
			String fileName2 = args[1];
			String fileName3 = args[2];
			String outputFile = args[3];
			
			
			FileWriter fwriter = new FileWriter(outputFile);
			BufferedWriter out = new BufferedWriter(fwriter);
		
			HashMap map2 = getHashMap(fileName2);
			HashMap map3 = getHashMap(fileName3);
			int count = 0;
			FileInputStream fstream = new FileInputStream(fileName1);
			DataInputStream din = new DataInputStream(fstream); 
			BufferedReader in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				String[] split = str.split("\t");
				String seq1 = grabSequence(split[0], map2);
				String seq2 = grabSequence(split[1], map3);
				if (seq1.equals("")) {
					System.out.println("Problem file 1!");
					
				}
				if (seq2.equals("")) {
					System.out.println("Problem file 2!");
				}
				out.write(">" + split[0] + "\n" + seq1 + "\n");
				out.write(">" + split[1] + "\n" + seq2 + "\n");
				System.out.println(count);
				count++;
			}
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static HashMap getHashMap(String fileName) {
		HashMap map = new HashMap();
		try {
			
			FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream din = new DataInputStream(fstream); 
			BufferedReader in = new BufferedReader(new InputStreamReader(din));
			String title = "";
			
			while (in.ready()) {	
				String str = in.readLine();
				if (str.contains(">")) {
					title = str;
				} else {
					if (map.containsKey(title)) {
						String seq = (String)map.get(title);
						seq += str;
						map.put(title, seq);
					} else {
						map.put(title, str);
					}
				}
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return map;
	}
	public static String grabSequence(String tag, HashMap map) {
		
		String result = "";
		try {
			
			Iterator itr = map.keySet().iterator();
			while (itr.hasNext()) {
				String key = (String)itr.next();
				if (key.contains(tag)) {
					result = (String)map.get(key);
					return result;
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
