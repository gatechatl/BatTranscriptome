package Unannotated;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

public class GetLessThan300bp {

	public static void main(String[] args) {
		
		try {
			HashMap protein = new HashMap();
			HashMap map = new HashMap();
			
			HashMap unannotate = new HashMap();

			
			String fileName = "C:\\School Notes\\Bat\\Bat ORF\\OrfPredictor.cds";
			String fileName2 = "C:\\School Notes\\Bat\\Bat ORF\\OrfPredictor.pep";
			String outFile = "C:\\School Notes\\Bat\\Bat ORF\\FinalProteinBatORF.fasta";
			String unannotateFile = "C:\\School Notes\\Bat\\Bat ORF\\BatUnannotated.txt";

			FileInputStream fstream = new FileInputStream(unannotateFile);
			DataInputStream din = new DataInputStream(fstream); 
			BufferedReader in = new BufferedReader(new InputStreamReader(din));			
			String name = "";
			String sequence = "";
			while(in.ready()) { 
				String str = in.readLine();
				unannotate.put(str, str);
			}
			in.close();
			
			FileWriter fwriter = new FileWriter(outFile); 	    	    
		    BufferedWriter out = new BufferedWriter(fwriter);
		    
			fstream = new FileInputStream(fileName);
			din = new DataInputStream(fstream); 
			in = new BufferedReader(new InputStreamReader(din));			
			name = "";
			sequence = "";
			while(in.ready()) { 
				String str = in.readLine();
				if (str.contains(">")) {
					String[] split = str.split("\t");
					
					name = "";
					sequence = "";
					
					name = split[0];
					if (unannotate.containsKey(name.replaceAll(">", ""))) {
						map.put(name, "");
					}
				} else {
					if (unannotate.containsKey(name.replaceAll(">", ""))) {
						sequence += str.trim();											
						map.put(name, sequence);
					}
				}
			}			
			in.close();			
			
			System.out.println(map.size());
			
			fstream = new FileInputStream(fileName2);
			din = new DataInputStream(fstream); 
			in = new BufferedReader(new InputStreamReader(din));			
			name = "";
			sequence = "";
			while(in.ready()) { 
				String str = in.readLine();
				if (str.contains(">")) {
					String[] split = str.split("\t");
					
					name = "";
					sequence = "";
					
					name = split[0];
					protein.put(name, "");
				} else {
					sequence += str.trim();
										
					protein.put(name, sequence);
				}
			}
			in.close();
						
			Iterator itr = map.keySet().iterator();
			HashMap finalList = new HashMap();
			
			while (itr.hasNext()) {
				String key = (String)itr.next();
				sequence = (String)map.get(key);
				if (sequence.length() >= 300) {					
					
					out.write(key + "\n" + (String)protein.get(key) + "\n");
					
				}
			}
			
			out.close();
						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
