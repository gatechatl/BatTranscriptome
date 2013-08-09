import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;


public class GrabMouseGeneName {

	
	public static void main(String[] args) {
		try {
			HashMap map = new HashMap();
			String ensembl_file = "C:\\School Notes\\Bat\\Manuscript\\Mus_musculus.GRCm38.70.gtf";
		    FileInputStream fstream = new FileInputStream(ensembl_file);
			DataInputStream din = new DataInputStream(fstream); 
			BufferedReader in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				String[] split = str.split("\t");
				String meta = split[8];
				String transcriptid = grabMeta(meta, "transcript_id");
				String geneName = grabMeta(meta, "gene_name");
				String ensemblid = grabMeta(meta, "gene_id");
				map.put(transcriptid, geneName);
			}
			in.close();
			
			ensembl_file = "C:\\School Notes\\Bat\\Manuscript\\mouse.txt";
		    fstream = new FileInputStream(ensembl_file);
			din = new DataInputStream(fstream); 
			in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				if (map.containsKey(str)) {
					String geneName = (String)map.get(str);
					System.out.println(geneName.toUpperCase());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	public static String grabMeta(String text, String id) {
		String returnval = "";
		if (text.contains(id)) {
			String val = text.split(id)[1].split(";")[0].trim();
			val = val.replaceAll("\"", "");
			val.trim();
			returnval = val;				
		}
		return returnval;
	}
}
