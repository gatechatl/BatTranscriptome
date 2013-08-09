import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;


/**
 * After AQUA we trimmed the alignment to produce a better alignment across the species
 * @author Tim Shaw
 *
 */
public class TrimAlignment {

	public static void main(String[] args) {
		String contigNameFile = args[0];
		try {
			HashMap contigName = getContigName(contigNameFile);
			for (int i = 0; i < 8000; i ++) {
				String num = "";
				if (new Integer(i).toString().length() == 1) {
					num = "000" + i;
				} else if (new Integer(i).toString().length() == 2) {
					num = "00" + i;
				} else if (new Integer(i).toString().length() == 3) {
					num = "0" + i;
				}
				String fileName = "Combined." + num + ".best";
				
				FileWriter fwriter = new FileWriter("temp.temp");
				BufferedWriter out = new BufferedWriter(fwriter);
			    FileInputStream fstream = new FileInputStream(fileName);
				DataInputStream din = new DataInputStream(fstream); 			
				BufferedReader in = new BufferedReader(new InputStreamReader(din));
				boolean first = true;
				while (in.ready()) {
					String str = in.readLine();
					if (str.contains(">")) {
						if (first) {
					        out.write(str + "\n");
						} else {
							out.write("\n" + str + "\n");
						}
					} else {
						out.write(str);
					}
					first = false;
				}
				in.close();
				out.close();
	
				String contig = "";
				int min = Integer.MIN_VALUE;
				int max = Integer.MAX_VALUE;
				
				fwriter = new FileWriter("temp.temp2");
				out = new BufferedWriter(fwriter);
				
				
			    fstream = new FileInputStream("temp.temp");
				din = new DataInputStream(fstream); 			
				in = new BufferedReader(new InputStreamReader(din));				
				while (in.ready()) {
					String str = in.readLine();
					if (!str.contains(">")) {
						boolean firstBracket = false;
						for (int j = 0; j < str.length(); j++) {
							if (str.substring(j, j + 1).equals("-")) {
								if (!firstBracket) {
									if (min < j) {
									    min = j;
									}
								}
								firstBracket = true;
							}
						}
						firstBracket = false;
						for (int j = str.length() - 1; j >= 0; j--) {
							if (str.substring(j, j + 1).equals("-")) {
								if (!firstBracket) {
									if (max > j) {
									    max = j;
									}
								}
								firstBracket = true;
							}
						}
					} else {
						if (str.contains("ENSMUST")) {
							contig = (String)contigName.get(str.replaceAll(">", ""));	
						}
					}
				}
				in.close();
				
				fwriter = new FileWriter("New_" + fileName);
				out = new BufferedWriter(fwriter);
				
			    fstream = new FileInputStream("temp.temp2");
				din = new DataInputStream(fstream);
				while (in.ready()) {
					String str = in.readLine();
					if (str.contains(">")) {
						if (str.contains("ENST")) {
							str = str + "_Human";
						} else if (str.contains("ENSMUST")) {
							str = str + "_Mouse";
						} else if (str.contains("ENSECAT")) {
						    str = str + "_Horse";
						} else if (str.contains("ENSCAFT")) {
							str = str + "_Dog";
						} else if (str.contains("ENSBTAT")) {
							str = str + "_Cow";
						} else if (str.contains("BAT")) {
							str = str + "_" + contig;
						}
						out.write(str + "\n");
					} else {
						out.write(str.substring(min, max) + "\n");
					}
				}
				in.close();
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static HashMap getContigName(String fileName) {
		HashMap map = new HashMap();
	    try {
		    FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream din = new DataInputStream(fstream); 			
			BufferedReader in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				String[] split = str.split("\t");
				map.put(split[0], split[1]);
				
			}
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return map;
	}
}
