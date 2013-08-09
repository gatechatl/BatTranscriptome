package DnDs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class UseBLASTGetDirection {

	public static void main(String[] args) {
		
		try {
			
			String inputFile = args[0];
			String outputFile = "test.fa";
			int count = 0;
			String scaffoldTitle = "";
			String ensemblTitle = "";
			FileInputStream fstream = new FileInputStream(inputFile);
			DataInputStream din = new DataInputStream(fstream); 
			BufferedReader in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				if (count % 4 == 1) {
					FileWriter fwriter = new FileWriter("scaffold.fa");
					BufferedWriter out = new BufferedWriter(fwriter);
					out.write(">query\n");
					out.write(str + "\n");
					out.close();
					
				}
				
				if (count % 4 == 3) {
					
					FileWriter fwriter = new FileWriter("data.fa");
					BufferedWriter out = new BufferedWriter(fwriter);
					out.write(">Data\n");
					out.write(str + "\n");
					out.close();
					
					executeCommand("formatdb -p F -i data.fa");
					executeCommand("blastall -p blastn -i scaffold.fa -d data.fa -o output.txt -m8");
					System.out.println(scaffoldTitle + "\t" + ensemblTitle + "\t" + isForward("output.txt"));
					
				}
				if (count % 4 == 0) {
					scaffoldTitle = str.replaceAll(">", "");
				}
				if (count % 4 == 2) {
					ensemblTitle = str.replaceAll(">", "");
				}
				count++;
			}
			in.close();
			
		} catch (Exception e) {
			
		}
	}
	
	public static boolean isForward(String inputFile) {
		boolean isForward = false;
		try {
			FileInputStream fstream = new FileInputStream(inputFile);
			DataInputStream din = new DataInputStream(fstream); 
			BufferedReader in = new BufferedReader(new InputStreamReader(din));
			int count = 0;
			while (in.ready()) {
				String str = in.readLine();
				if (count == 0) {
					in.close();
					String[] split = str.split("\t");
					int qstart = new Integer(split[6]);
					int qend = new Integer(split[7]);
					int sstart = new Integer(split[8]);
					int send = new Integer(split[9]);
					if (qstart > qend) {
						System.out.println("Something Wrong?");
					}
					if (sstart > send) {
						return false;
					} else {
						return true;
					}
					
					
					
				}
				count++;
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isForward;
	}
	public static void executeCommand(String executeThis) {
		try {
			writeFile("tempexecuteCommand.sh", executeThis);
	        String[] command = {"sh", "tempexecuteCommand.sh"};
	        Process p1 = Runtime.getRuntime().exec(command);		        
            BufferedReader inputn = new BufferedReader(new InputStreamReader(p1.getInputStream()));            
            String line=null;
            while((line=inputn.readLine()) != null) {}                        
            inputn.close();
             
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void writeFile(String fileName, String command) {
		try {
		    FileWriter fwriter2 = new FileWriter(fileName);
		    BufferedWriter out2 = new BufferedWriter(fwriter2);
		    out2.write(command + "\n");		    		
		    out2.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
