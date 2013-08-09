import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;


public class ExamineTreeTopologyDistance {

	public static void main(String[] args) {
		
		File folder = new File(".");
		File[] listOfFiles = folder.listFiles();			
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				if (listOfFiles[i].getName().contains("tre")) {
					if (checkTopology(args[0], listOfFiles[i].getName())) {
						System.out.println(listOfFiles[i].getName().split("_")[0]);
					}
				}
				
			}
		}
		
	}
	
	public static boolean checkTopology(String file1, String file2) {
		String rscript = "";
		rscript += "library(ape);\n";
		rscript += "tree1 <- read.tree(\"" + file1 + "\")\n";
		rscript += "tree2 <- read.tree(\"" + file2 + "\")\n";
		rscript += "distance = dist.topo(tree1, tree2, method = \"PH85\")\n";
		rscript += "write(distance, file=\"output.txt\");\n";
		
		writeFile("topology.R", rscript);
		String runRscript = "R --vanilla < topology.R";
		executeCommand(runRscript);
		String output = "";
		try {
		    FileInputStream fstream = new FileInputStream("output.txt");
			DataInputStream din = new DataInputStream(fstream); 
			BufferedReader in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				output = str;
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (output.equals("0")) {
			return true;
		} else {
			return false;
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
}
