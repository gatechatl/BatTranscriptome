package Phylogenetic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class GeneratePhyMLScript {

	public static void main(String[] args) {
		

		try {
			for (int i = 0; i < 22; i++) {
				String outputFile1 = "part" + i + ".sh";
	
				FileWriter fwriter1 = new FileWriter(outputFile1);
				BufferedWriter out1 = new BufferedWriter(fwriter1);
	
			    File dir = new File(".");
			    int count = 0;
			    for (File child : dir.listFiles()) {			  		      
			      if (child.getName().contains("Mouse")) {
			    	  String[] split = child.getName().split(".AIC-");
				      String type = split[1].replaceAll(".tre", "");
				      String fileName = split[0];
				      if (count % 22 == i) {
				    	  /*out1.write("mv " + fileName + " part" + i + "\n");*/
				    	  out1.write("phyml -d nt -i " + fileName + " -b 500 -m " + type + " -f e -t e -v e -a e\n");
				      } 
	
				      count++;
			      }
			    }
			    out1.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
