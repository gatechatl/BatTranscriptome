import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;

import java.util.Iterator;


public class ConvertEnsembl2KEGGID {

	public static void main(String[] args) {
		try {
			
			String fileName = args[0];
			String fileName2 = args[1];
			
			HashMap map = new HashMap();
		    FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream din = new DataInputStream(fstream); 			
			BufferedReader in = new BufferedReader(new InputStreamReader(din));		
			
			while (in.ready()) {
				String str = in.readLine();
				String[] split = str.split("\t");
				if (split.length == 2) {
					String key = split[0];
					String value = split[1];
					LinkedList list = parse_list(value);
					Iterator itr = list.iterator();
					while (itr.hasNext()) {
						String ensembl = (String)itr.next();
						map.put(ensembl, key);
					}
				}
			}			
			in.close();
			
		    fstream = new FileInputStream(fileName2);
			din = new DataInputStream(fstream); 			
			in = new BufferedReader(new InputStreamReader(din));		
			
			while (in.ready()) {
				String str = in.readLine();
				System.out.println((String)map.get(str));
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static LinkedList parse_list(String str) {
		str = str.replaceAll("c\\(", "");
		str = str.replaceAll(" ", "");
		str = str.replaceAll("\\)", "");
		str = str.replaceAll("\"\"", "\"");
		str = str.replaceAll("\"", ",");
		String[] split = str.split(",");
		
		LinkedList list = new LinkedList();
		for (int i = 0; i < split.length; i++) {
			if (!split[i].equals("")) {
				if (!split[i].equals("NA")) {
				    list.add(split[i]);
				}
			}
		}
		return list;
	}
}
