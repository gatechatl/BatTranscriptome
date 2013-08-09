import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;


public class GetTopKEGGEnsembl {
	
	public static void main(String[] args) {
		try {
			
			String fileName = args[0];
			String outputFile = args[1];

		    FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream din = new DataInputStream(fstream); 			
			BufferedReader in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				FileWriter fwriter = new FileWriter("temp.R");
				BufferedWriter out = new BufferedWriter(fwriter);
				out.write(create_kegg(str.trim(), outputFile));
				out.close();
	
				fwriter = new FileWriter("temp.sh");
				out = new BufferedWriter(fwriter);
				out.write("R --vanilla < temp.R");
				out.close();
		    	int exitValue = -1;
		    	try {
			    	Process process = Runtime.getRuntime().exec("temp.sh");
		                while (true) {	                	
		                    try {
		                        exitValue = process.exitValue();
		                        
		                        break;
		                    } catch (IllegalThreadStateException e) {	                        
		                        try {
		                            Thread.sleep(10);
		                        } catch (InterruptedException e1) {
		                            
		                        }
		                        
		                    }
		                }
		    	} catch (IOException ex) {
		    		ex.printStackTrace();
		    	}
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	public static String create_kegg(String xml, String outputFile) {
		String str = "";
		str += "library(KEGGgraph);\n";
		str += "library(KEGG.db);";
		str += "library(Rgraphviz);";
		str += "library(biomaRt);";
		str += "library(org.Mm.eg.db);";
		str += "library(RBGL);";
		str += "makeAttr <- function(graph, default, valNodeList) {";
		str += "tmp <- nodes(graph)";
		str += "x <- rep(default, length(tmp)); names(x) <- tmp";
		str += "if(!missing(valNodeList)) {";
		str += "stopifnot(is.list(valNodeList))";
		str += "allnodes <- unlist(valNodeList)";
		str += "stopifnot(all(allnodes %in% tmp))";
		str += "for(i in seq(valNodeList)) {";
		str += "x[valNodeList[[i]]] <- names(valNodeList)[i]";
		str += "}";
		str += "}";
		str += "return(x)";
		str += "}";
		str += "mapfile<- system.file(\"extdata/" + xml + "\", package=\"KEGGgraph\");";
		str += "maptest <- parseKGML(mapfile);";
		str += "mapkG <- KEGGpathway2Graph(maptest, expandGenes=TRUE);";
		str += "mapkNodes <- nodes(mapkG);";
		str += "library(RBGL);";
		str += "bcc <- brandes.betweenness.centrality(mapkG)";
		str += "rbccs <- bcc$relative.betweenness.centrality.vertices[1L,]";
		str += "toprbccs <- sort(rbccs,decreasing=TRUE)[1:4]";
		str += "toprbccName <- names(toprbccs)";
		str += "toprSub <- subGraph(toprbccName, mapkG)";
		str += "tnodes <- nodes(toprSub)";
		str += "tgeneids <- translateKEGGID2GeneID(tnodes)";
		str += "tgenesymbols <- sapply(mget(tgeneids, org.Mm.egENSEMBLTRANS, ifnotfound=NA), \"[[\",1)";
		str += "keyName <- as.list(KEGGPATHID2NAME)";
		String xmlID = xml.replaceAll(".xml", "").trim();
		str += "keyName[\"" + xmlID + "\"]";
		str += "text = paste(i, \"\t\", mappedkeys(value), \"\t\", value)";
		str += "for (i in tgenesymbols) {write(text, file=\"" + outputFile + "\",append=TRUE)}";
		return str;
	}
}
