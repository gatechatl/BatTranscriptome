package KEGGMapping;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Calculate what genes are missing in each pathway
 * @author Tim Shaw
 */
public class ComprehensiveKEGGMapping {

	public static void main(String[] args) {
		
		HashMap getKEGGList = new HashMap();
		
		
		String fileName = "C:\\School Notes\\Transcriptome Projects\\Bat\\KEGG_Mapping\\Cumulative\\KEGG_List.txt";
		
		String ensemblPathway = "C:\\School Notes\\Transcriptome Projects\\Bat\\KEGG_Mapping\\Cumulative\\mouse_Pathway_KEDDID_ensembl.txt";
		
		String completeKEGGID = "C:\\School Notes\\Transcriptome Projects\\Bat\\KEGG_Mapping\\Cumulative\\Mouse_KEGGID.txt";
		try {
			FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream din = new DataInputStream(fstream); 
			BufferedReader in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				String[] split = str.split("\t");
				getKEGGList.put(split[0], split[1]);
			}
			in.close();
		
			Iterator itr = getKEGGList.keySet().iterator();
			while (itr.hasNext()) {
				HashMap map = new HashMap();
				String key = (String)itr.next();
				fstream = new FileInputStream(ensemblPathway);
				din = new DataInputStream(fstream); 
				in = new BufferedReader(new InputStreamReader(din));
				while (in.ready()) {
					String str = in.readLine();
					String[] split = str.split("\t");
					if (key.equals(split[0])) {
						map.put(split[2], split[2]);
					}									
				}
				in.close();
				HashMap mapuniq = new HashMap();
				fstream = new FileInputStream(completeKEGGID);
				din = new DataInputStream(fstream); 
				in = new BufferedReader(new InputStreamReader(din));
				while (in.ready()) {
					String str = in.readLine();
					if (map.containsKey(str)) {
						mapuniq.put(str, str);
					}
				}
				System.out.println(getKEGGList.get(key) + "\t" + mapuniq.size() + "\t" + map.size() + "\t" + new Integer(mapuniq.size()).doubleValue() / map.size());
			}
			
		} catch (Exception e) {
			
		}
	}
}
