package GO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;



public class GOSLIMFilter {

	public static void main(String[] args) {
		
		
		String inputFile = "C:\\School Notes\\Bat\\Manuscript\\Bat Paper Draft Ver 3 Data Figure and Manuscript\\Figures\\GOslim\\GoSLIM_SingleOccurrence.txt";
		String outputFile = "C:\\School Notes\\Bat\\Manuscript\\Bat Paper Draft Ver 3 Data Figure and Manuscript\\Figures\\GOslim\\Filtered_GoSLIM_SingleOccurrence.txt";
		
		String[] list = {"IMMUNOLOGY","LYMPHOCYTE ACTIVATION", "CYTOKINE PRODUCTION", "T CELL ACTIVATION", "LYMPHOCYTE DIFFERENTIATION", "REGULATION OF LYMPHOCYTE ACTIVATION",
				"RESPONSE TO BIOTIC STIMULUS", "CYTOKINE METABOLISM", "CYTOKINE BIOSYNTHESIS", "B CELL ACTIVATION", "REGULATION OF LYMPHOCYTE DIFFERENTIATION", "LYMPHOCYTE PROLIFERATION", "LYMPHOCYTE ACTIVATION", "B CELL MEDIATED IMMUNITY", "HUMORAL DEFENSE MECHANISM", "JAK-STAT CASCADE", "REGULATION OF LYMPHOCYTE PROLIFERATION", "PHAGOCYTOSIS", 
				"HUMORAL IMMUNE RESPONSE", "CYTOKINE SECRETION", "REGULATION OF JAK-STAT CASCADE", "I-KAPPAB KINASE", "JNK CASCADE", "COMPLEMENT ACTIVATION", "T-HELPER 2 TYPE IMMUNE RESPONSE", "T-HELPER 1 TYPE IMMUNE RESPONSE", "ENTRY OF VIRUS INTO HOST CELL", "REGULATION OF I-KAPPAB KINASE",
				"REGULATION OF JNK CASCADE", "MHC PROTEIN BINDING", "DEFENSE RESPONSE TO BACTERIA", "MONOCYTE DIFFERENTIATION", "REGULATION OF MONOCYTE DIFFERENTIATION", "MHC PROTEIN COMPLEX", "CYTOKINE ACTIVITY", "DEFENSE RESPONSE TO FUNGI",
				"COMPLEMENT ACTIVATION", "MONOCYTE ACTIVATION", "MHC CLASS II PROTEIN BINDING", "MHC CLASS I PROTEIN BINDING", "COMPLEMENT ACTIVATION", "CYTOKINESIS", "VIRAL", "CYTOKINE"};
				
				
		
		try {
			HashMap map = new HashMap();
			for (int i = 0; i < list.length; i++) {
				map.put(list[0], 0);
			}
			FileWriter fwriter = new FileWriter(outputFile);
			BufferedWriter out = new BufferedWriter(fwriter);
			
			FileInputStream fstream = new FileInputStream(inputFile);
			DataInputStream din = new DataInputStream(fstream); 
			BufferedReader in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				String[] split = str.split("\t");
				
				for (int i = 0; i < list.length; i++) {
					if (split[1].toUpperCase().contains(list[i])) {
						System.out.println(str);
						break;
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
