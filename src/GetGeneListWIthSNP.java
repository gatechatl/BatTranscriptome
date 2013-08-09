import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;


public class GetGeneListWIthSNP {

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
				map.put(split[0], split[0]);				
			}
			in.close();
		    fstream = new FileInputStream(fileName2);
			din = new DataInputStream(fstream); 			
			in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				String[] split = str.split("\t");
				if (map.containsKey(split[0])) {
					System.out.println(str);
				}				
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
