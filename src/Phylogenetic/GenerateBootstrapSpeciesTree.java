package Phylogenetic;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Random;


public class GenerateBootstrapSpeciesTree {

	public static void main(String[] args) {
		
		try {
			
			String path = args[1];						
			File folder = new File(path);
			File[] listOfFiles = folder.listFiles();								
			String concatenateUs = "cat";
			LinkedList list = new LinkedList();
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].getName().contains("alignment.phylip_phyml_boot_trees.txt")) {									
					list.add(listOfFiles[i].getName());	
				}
			}
			for (int j = 0; j < 500; j++) {
				
			    FileWriter fwriter2 = new FileWriter("NJst.input.tre");
			    BufferedWriter out2 = new BufferedWriter(fwriter2);
			      		
			    
				for (int i = 0; i < listOfFiles.length; i++) {
					Random rand = new Random();
					int index = rand.nextInt(list.size());
					
					String fileName = (String)list.get(index);
					String tree = grabTree(fileName, j);
					out2.write(tree + "\n");
				}
				out2.close();
				runNJst("NJst.input.tre", "NJst." + j + ".output.tre");
			}												
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String grabTree(String fileName, int iteration) {
		String tree = "";
		try {
			FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream din = new DataInputStream(fstream); 			
			BufferedReader in = new BufferedReader(new InputStreamReader(din));
			int count = 0;
			while (in.ready()) {
				String str = in.readLine();
				if (count == iteration) {
					tree = str;
					in.close();
					return tree;
				}
				count++;
			}
			in.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tree;
	}
	public static boolean checkFileExists(String fileName) {
		File f = new File(fileName);
		if (f.exists()) {
			return true;
		}
		return false;
	}
	public static void runNJst(String inTreeFile, String outTreeFile) {
		String njst = "java GenerateNJstScript " + inTreeFile + " " + outTreeFile + "> tempScript.r";
		executeCommand(njst);
		
		String runRScript = "R --vanilla < tempScript.r";
		executeCommand(runRScript);
		String rmPhylip = "rm tempScript.r";
		executeCommand(rmPhylip);
	}
	public static void runMrAIC(String fileName, String outFile) {
		String rmPhylip = "rm *phylip*";
		executeCommand(rmPhylip);
		
		String convertPhylip = "perl fasta2relaxedPhylip.pl " + fileName;
		String runMrAIC = "mraic " + fileName + ".phylip";
		
		executeCommand(convertPhylip);
		executeCommand(runMrAIC);
		
		String rmAICc = "mv *AIC-* " + outFile;
		executeCommand(rmAICc);
				
		executeCommand(rmPhylip);
		
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
	
	public static void shuffleFile(String fileName, String outputFile) {
		
		String shuffleScript = "perl shuffle-aln.pl --mode complete " + fileName + " > shuffletemp.fa";
		executeCommand(shuffleScript);
		try {
			LinkedList title = new LinkedList();
			FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream din = new DataInputStream(fstream); 			
			BufferedReader in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				if (str.contains(">")) {
				    title.add(str);	
				}
			}
			in.close();
			FileWriter fwriter = new FileWriter(outputFile);
			BufferedWriter out = new BufferedWriter(fwriter);
			
			fstream = new FileInputStream("shuffletemp.fa");
			din = new DataInputStream(fstream); 			
			in = new BufferedReader(new InputStreamReader(din));
			int count = 0;
			while (in.ready()) {
				String str = in.readLine();
				if (str.contains(">")) {
					if (count == 0) {
						out.write((String)title.get(count) + "\n");
					} else {
						out.write("\n" + (String)title.get(count) + "\n");
					}
					count++;
				} else {
					out.write(str);
				}
			}
			in.close();
			out.close();
			File f = new File("shuffletemp.fa");
			f.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
