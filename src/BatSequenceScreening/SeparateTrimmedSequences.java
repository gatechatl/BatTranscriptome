package BatSequenceScreening;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class SeparateTrimmedSequences {

	public static void main(String[] args) {
		String inputFile = args[0];
		String outputFile = args[1];
		try {
			FileWriter fwriter = new FileWriter(outputFile);
			BufferedWriter out = new BufferedWriter(fwriter);
			
	        FileInputStream fstream = new FileInputStream(inputFile);
	        DataInputStream din = new DataInputStream(fstream);
	        BufferedReader in = new BufferedReader(new InputStreamReader(din));
	        String sequence = "";
	        String title = "";
	        while (in.ready()) {
                String str = in.readLine();
                if (str.contains(">")) {
                	title = str;
                } else {
                	sequence = str;
                	while (sequence.contains("NNNNNNNNNNNNNN")) {
                		sequence = sequence.replaceAll("NNNNNNNNNNNNNNN", "NNNNNNNNNNNNNN");
                	}
                	String[] split = sequence.split("NNNNNNNNNNNNNN");
                	for (int i = 0; i < split.length; i++) {
                		if (split[i].length() > 200) {
                			out.write(title + "_" + i + "\n" + split[i] + "\n");
                		}
                	}
                }
	        }
	        in.close();
	        out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
