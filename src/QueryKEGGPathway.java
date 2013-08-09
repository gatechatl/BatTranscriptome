import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;


public class QueryKEGGPathway {

	public static void main(String[] args) {
		
		try {
			String geneListFile = args[0];
			/*String fileName = args[1];*/
		    FileInputStream fstream = new FileInputStream(geneListFile);
			DataInputStream din = new DataInputStream(fstream); 			
			BufferedReader in = new BufferedReader(new InputStreamReader(din));
			
			String geneList = "";
			boolean first = true;
			while (in.ready()) {
				String str = in.readLine();
				if (first) {
				    geneList += "\"" + str + "\"";
				} else {
					geneList += ",\"" + str + "\"";
				}
				first = false;
			}
			
			System.out.println(createKeggScript("mmu04060", geneList, ""));
			/*
			HashMap map = new HashMap();
		    fstream = new FileInputStream(fileName);
			din = new DataInputStream(fstream); 			
			in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				
				
				
			}
			in.close();*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    public static String createKeggScript(String kegg_id, String refseq, String microRNAList) {
        String str = "library(KEGGgraph);\n";
        str += "library(KEGG.db);\n";
        str += "library(Rgraphviz);\n";
        str += "library(biomaRt);\n";
        str += "library(org.Mm.eg.db);\n";
        str += "library(RBGL);\n";
        str += "makeAttr <- function(graph, default, valNodeList) {\n";
        str += "tmp <- nodes(graph)\n";
        str += "x <- rep(default, length(tmp)); names(x) <- tmp\n";

        str += "if(!missing(valNodeList)) {\n";
        str += "stopifnot(is.list(valNodeList))\n";
        str += "allnodes <- unlist(valNodeList)\n";
        str += "stopifnot(all(allnodes %in% tmp))\n";
        str += "for(i in seq(valNodeList)) {\n";
        str += "x[valNodeList[[i]]] <- names(valNodeList)[i]\n";
        str += "}\n";

        str += "}\n";
        str += "return(x)\n";
        str += "}\n";

        str += "mapfile<- system.file(\"extdata/" + kegg_id + ".xml\", package=\"KEGGgraph\");\n";
        str += "maptest <- parseKGML(mapfile);\n";

        str += "mapkG <- KEGGpathway2Graph(maptest, expandGenes=TRUE);\n";
        str += "mapkNodes <- nodes(mapkG);\n";

        str += "library(RBGL);\n";
        str += "bcc <- brandes.betweenness.centrality(mapkG)\n";
        str += "rbccs <- bcc$relative.betweenness.centrality.vertices[1L,]\n";
        str += "toprbccs <- sort(rbccs,decreasing=TRUE)[1:6]\n";
        str += "toprbccs\n";

        str += "toprbccName <- names(toprbccs)\n";
        str += "toprin <- sapply(toprbccName, function(x) inEdges(mapkG)[x])\n";
        str += "toprout <- sapply(toprbccName, function(x) edges(mapkG)[x])\n";
        str += "toprSubnodes <- unique(unname(c(unlist(toprin), unlist(toprout), toprbccName)))\n";
        str += "toprSub <- subGraph(toprSubnodes, mapkG)\n";
        str += "tnodes <- nodes(toprSub)\n";

        str += "all_nodes <- nodes(mapkG)\n";
        str += "all_tgeneids <- translateKEGGID2GeneID(all_nodes)\n";
        str += "all_tgenesymbols <- sapply(mget(all_tgeneids, org.Mm.egSYMBOL, ifnotfound=NA), \"[[\",1)\n";
        str += "all_Symbol <- mapkG;\n";
        str += "nodes(all_Symbol) <- all_tgenesymbols;\n";

        str += "tgeneids <- translateKEGGID2GeneID(tnodes)\n";
        str += "tgenesymbols <- sapply(mget(tgeneids, org.Mm.egSYMBOL, ifnotfound=NA), \"[[\",1)\n";

        str += "top_tgeneids <- translateKEGGID2GeneID(toprbccName)\n";
        str += "top_tgenesymbols <- sapply(mget(top_tgeneids, org.Mm.egSYMBOL, ifnotfound=NA), \"[[\",1)\n";
        str += "toprSubSymbol <- toprSub\n";
        str += "nodes(toprSubSymbol) <- tgenesymbols\n";


        str += "id = c(" + refseq + ");\n";
        str += "query = sapply(mget(id, org.Mm.egSYMBOL, ifnotfound=NA), \"[[\",1)\n";


        str += "query_top = top_tgenesymbols[intersect(names(top_tgenesymbols), names(query))];\n";
        str += "microRNA_id = c(" + microRNAList + ");\n";
        /*str += "microRNA = sapply(mget(microRNA_id, org.Mm.egSYMBOL, ifnotfound=NA), \"[[\",1);\n";
        str += "microRNA_top = top_tgenesymbols[intersect(names(top_tgenesymbols), names(microRNA))];\n";
        str += "top_con_microRNA = tgenesymbols[intersect(names(tgenesymbols), names(microRNA))];\n";
        str += "query_microRNA = query[intersect(names(query), names(microRNA))];\n";


        str += "query_top_microRNA = query[intersect(names(query), names(top_tgenesymbols))];\n";*/
        str += "orig_nAttrs <- list()\n";
        str += "orig_nAttrs$fillcolor <- makeAttr(mapkG, \"lightblue\", list(orange=toprbccName))\n";
        str += "orig_nAttrs$width <- makeAttr(mapkG,\"\",list(\"0.8\"=toprbccName))\n";
        str += "orig_nAttrs$fontsize <- makeAttr(toprSubSymbol,\"5\",list(\"5\"=top_tgenesymbols))\n";

        str += "all_nAttrs <- list()\n";
        /*str += "newlist = list(yellow=tgenesymbols, orange=top_tgenesymbols, lightgreen=microRNA, purple=microRNA_top, red=query, brown=query_top, pink=query_microRNA);\n";
        str += "topnewlist = list(yellow=tgenesymbols, orange=top_tgenesymbols, lightgreen=top_con_microRNA, purple=microRNA_top, brown=query_top, pink=query_top_microRNA);\n";*/

        str += "newlist = list(yellow=tgenesymbols, orange=top_tgenesymbols, red=query, brown=query_top);\n";
        str += "topnewlist = list(yellow=tgenesymbols, orange=top_tgenesymbols, brown=query_top);\n";
        
        str += "if (length(tgenesymbols) == 0) {newlist[\"yellow\"]=NULL;};\n";
        str += "if (length(top_tgenesymbols) == 0) {newlist[\"orange\"]=NULL;};\n";
        /*str += "if (length(microRNA) == 0) {newlist[\"lightgreen\"]=NULL;};\n";
        str += "if (length(microRNA_top) == 0) {newlist[\"purple\"]=NULL;};\n";*/
        str += "if (length(query) == 0) {newlist[\"red\"]=NULL;};\n";
        str += "if (length(query_top) == 0) {newlist[\"brown\"]=NULL;};\n";
        /*str += "if (length(query_microRNA) == 0 ) {newlist[\"pink\"]=NULL;};\n";*/

        str += "if (length(tgenesymbols) == 0) {topnewlist[\"yellow\"]=NULL;};\n";
        str += "if (length(top_tgenesymbols) == 0) {topnewlist[\"orange\"]=NULL;};\n";
        /*str += "if (length(top_con_microRNA) == 0) {newlist[\"lightgreen\"]=NULL;};\n";
        str += "if (length(microRNA_top) == 0) {topnewlist[\"purple\"]=NULL;};\n";*/
        str += "if (length(query) == 0) {topnewlist[\"red\"]=NULL;};\n";
        str += "if (length(query_top) == 0) {topnewlist[\"brown\"]=NULL;};\n";
        /*str += "if (length(query_top_microRNA) == 0 ) {topnewlist[\"pink\"]=NULL;};\n";*/
        str += "nAttrs <- list()\n";
        str += "nAttrs$fillcolor <- makeAttr(toprSubSymbol, \"lightblue\", topnewlist)\n";
        str += "nAttrs$width <- makeAttr(toprSubSymbol,\"\",list(\"0.8\"=top_tgenesymbols))\n";
        str += "nAttrs$fontsize <- makeAttr(toprSubSymbol,\"5\",list(\"5\"=top_tgenesymbols))\n";

        str += "all_nAttrs$fillcolor <- makeAttr(all_Symbol, \"lightblue\", newlist)\n";
        str += "all_nAttrs$width <- makeAttr(all_Symbol,\"\",list(\"0.8\"=top_tgenesymbols))\n";
        str += "all_nAttrs$fontsize <- makeAttr(toprSubSymbol,\"5\",list(\"5\"=top_tgenesymbols))\n";


        str += "png(\"full.png\", width = 1200, height = 1200);\n";
        str += "plot(all_Symbol, \"neato\",nodeAttrs=all_nAttrs,attrs=list(node=list(fixedsize=FALSE, fillcolor=\"lightblue\")))\n";
        str += "legend(\"topleft\", c(\"Regular Node\", \"Regular Node neighboring Top Node\", \"Query\", \"Query that is Top Node\", \"Top Node\", \"MicroRNA Regulated\", \"MicroRNA Regulated Top Node\", \"Query that is microRNA Regulated\"), lwd = 3, col=c(\"lightblue\" ,\"yellow\",\"red\", \"brown\", \"orange\", \"lightgreen\", \"purple\", \"pink\"));\n";
        str += "dev.off();\n";


        /*str += "png(\"PathwayCenter-1.png\", width = 1000, height = 1000);\n";
        str += "plot(toprSubSymbol, \"neato\", nodeAttrs=nAttrs,attrs=list(node=list(fixedsize=FALSE, fillcolor=\"lightblue\")))\n";
        str += "legend(\"topleft\", c(\"Regular Node\", \"Regular Node neighboring Top Node\", \"Query\", \"Query that is Top Node\", \"Top Node\", \"MicroRNA Regulated\", \"MicroRNA Regulated Top Node\", \"Query that is microRNA Regulated\"), lwd = 3, col=c(\"lightblue\" ,\"yellow\",\"red\", \"brown\", \"orange\", \"lightgreen\", \"purple\", \"pink\"));\n";
        str += "dev.off();\n";

        str += "png(\"PathwayCenter-2.png\", width = 1000, height = 1000);\n";
        str += "plot(toprSubSymbol, \"twopi\",nodeAttrs=nAttrs,attrs=list(node=list(fixedsize=FALSE, fillcolor=\"lightblue\")))\n";
        str += "legend(\"topleft\", c(\"Regular Node\", \"Regular Node neighboring Top Node\", \"Query\", \"Query that is Top Node\", \"Top Node\", \"MicroRNA Regulated\", \"MicroRNA Regulated Top Node\", \"Query that is microRNA Regulated\"), lwd = 3, col=c(\"lightblue\" ,\"yellow\",\"red\", \"brown\", \"orange\", \"lightgreen\", \"purple\", \"pink\"));\n";
        str += "dev.off();\n";*/

        return str;
	}

}
