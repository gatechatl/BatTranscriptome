package Phylogenetic;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.LinkedList;


public class CreateSpeciesTree {

	public static void main(String[] args) {
		
		try {
			int bootstrap = new Integer(args[0]);			
			String path = args[1];
			
			if (bootstrap == 0) {
				File folder = new File(path);
				File[] listOfFiles = folder.listFiles();								
				String concatenateUs = "cat";
				for (int i = 0; i < listOfFiles.length; i++) {
					if (listOfFiles[i].isFile()) {
						if (listOfFiles[i].getName().contains("best")) {
							runMrAIC(listOfFiles[i].getName(), listOfFiles[i].getName() + ".tre");
							
							/* test NJst */
							runNJst(listOfFiles[i].getName() + ".tre", listOfFiles[i].getName() + ".NJst.tre");
							
							if (checkFileExists(listOfFiles[i].getName() + ".NJst.tre")) {
								concatenateUs += " " + listOfFiles[i].getName() + ".tre";
							}						
						}
					}
				}
				concatenateUs += " > finalTre.tre";
				executeCommand(concatenateUs);
				runNJst("finalTre.tre", "finalTre.NJst");
			} else if (bootstrap > 0) {
				File folder = new File(path);
				File[] listOfFiles = folder.listFiles();
				for (int j = 1; j <= bootstrap; j++) {								
					String concatenateUs = "cat";
					for (int i = 0; i < listOfFiles.length; i++) {
						if (listOfFiles[i].isFile()) {
							if (listOfFiles[i].getName().contains("best")) {
								shuffleFile(listOfFiles[i].getName(), listOfFiles[i].getName() + ".shuffle");
								runMrAIC(listOfFiles[i].getName() + ".shuffle", listOfFiles[i].getName() + ".shuffle.tre");
								
								/* test NJst */
								runNJst(listOfFiles[i].getName() + ".shuffle.tre", listOfFiles[i].getName() + ".shuffle.NJst.tre");
								
								if (checkFileExists(listOfFiles[i].getName() + ".shuffle.NJst.tre")) {
									concatenateUs += " " + listOfFiles[i].getName() + "shuffle.tre";
								}						
							}
						}
					}
					concatenateUs += " > finalTre" + j + ".tre";
					executeCommand(concatenateUs);
					runNJst("finalTre" + j + ".tre", "finalTre" + j + ".NJst");
					
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
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
