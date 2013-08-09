package Unannotated;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class VennDiagramData {

	public static void main(String[] args) {
		
		try {
			HashMap unannotated = new HashMap();
			HashMap all = new HashMap();
			String fileName = args[0]; 
			FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream din = new DataInputStream(fstream); 			
			BufferedReader in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				unannotated.put(str, str);
			}
			in.close();
			
			fileName = args[4];
			fstream = new FileInputStream(fileName);
			din = new DataInputStream(fstream); 			
			in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				if (unannotated.containsKey(str)) {
					unannotated.remove(str);
				}
			}
			
			HashMap myotis = new HashMap();
			fileName = args[1];
			fstream = new FileInputStream(fileName);
			din = new DataInputStream(fstream); 			
			in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				if (unannotated.containsKey(str)) {
					myotis.put(str, str);
					all.put(str, str);
				}
			}
			in.close();
			
			HashMap myotis_pteropus = new HashMap();
			HashMap pteropus = new HashMap();
			fileName = args[2];
			fstream = new FileInputStream(fileName);
			din = new DataInputStream(fstream); 			
			in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				if (unannotated.containsKey(str)) {
					pteropus.put(str, str);
					all.put(str, str);
					if (pteropus.containsKey(str) && myotis.containsKey(str)) {
						myotis_pteropus.put(str, str);
					}
				}
			}
			in.close();
			
			HashMap mergeAll = new HashMap();
			HashMap transcriptome_vs_myotis = new HashMap();
			HashMap transcriptome_vs_pteropus = new HashMap();
			HashMap transcriptome = new HashMap();
			fileName = args[3];
			fstream = new FileInputStream(fileName);
			din = new DataInputStream(fstream); 			
			in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				if (unannotated.containsKey(str)) {
					all.put(str, str);
					transcriptome.put(str, str);
					if (transcriptome.containsKey(str) && pteropus.containsKey(str)) {
						transcriptome_vs_pteropus.put(str, str);
					}
					if (transcriptome.containsKey(str) && myotis.containsKey(str)) {
						transcriptome_vs_myotis.put(str,  str);
					}
					if (transcriptome.containsKey(str) && myotis.containsKey(str) && pteropus.containsKey(str)) {
						mergeAll.put(str,  str);
					}
				}
			}
			in.close();
							
			System.out.println("Myotis: " + myotis.size());
			System.out.println("Pteropus: " + pteropus.size());
			System.out.println("transcriptome: " + transcriptome.size());
			System.out.println("Myotis vs Pteropus: " + myotis_pteropus.size());
			System.out.println("Pteropus vs Transcriptome: " + transcriptome_vs_pteropus.size());
			System.out.println("Myotis vs Transcriptome: " + transcriptome_vs_myotis.size());
			System.out.println("Merge All: " + mergeAll.size());
			System.out.println("All: " + all.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
