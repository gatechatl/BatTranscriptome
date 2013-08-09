package BatSequenceScreening;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

public class ScreenAssemblyFiles {

	public static void main(String[] args) {
		
		String inputFile = args[0];
		String outputFile = args[1];
		try {
			String sequence = "";
			String title = "";
			
			HashMap map = new HashMap();
			
			FileWriter fwriter = new FileWriter(outputFile);
			BufferedWriter out = new BufferedWriter(fwriter);
			
            FileInputStream fstream = new FileInputStream(inputFile);
            DataInputStream din = new DataInputStream(fstream);
            BufferedReader in = new BufferedReader(new InputStreamReader(din));
            
            while (in.ready()) {
                    String str = in.readLine();
                    if (str.contains(">")) {
                    	title = str;
                    	sequence = "";
                    } else {
                    	sequence += str;
                    	map.put(title,  sequence);
                    }
            }
            map.put(title,  sequence);
            in.close();
            
            Iterator itr = map.keySet().iterator();
            while (itr.hasNext()) {            	
            	title = (String)itr.next();
            	sequence = (String) map.get(title);
            	if (longer200(sequence) && goodNs(sequence)) {
            		out.write(title + "\n" + sequence + "\n");
            	}
            }
            out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static boolean longer200(String sequence) {
		if (sequence.length() >= 200) {
			return true;
		}
		return false;		
	}
	public static boolean goodNs(String sequence) {
		double Ns = 0;
		double consecutive = 0;
		
		for (int i = 0; i < sequence.length(); i++) {
			if (sequence.toUpperCase().substring(i, i + 1).equals("N")) {
				Ns++;
				consecutive++;
			} else {
				consecutive = 0;
			}
			if (Ns / sequence.length() > 0.1) {
				return false;
			}
			if (consecutive > 14) {
				return false;
			}
		}
		return true;
	}
}
