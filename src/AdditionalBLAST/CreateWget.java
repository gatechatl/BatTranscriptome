package AdditionalBLAST;

public class CreateWget {


	public static void main(String[] args) {
		
		for (int i = 1; i <= 56; i++) {
			String text = "";
			 

			if (i >= 100) {
			    text = i + "";
			}
			if (i < 100) {
				text = "0" + i;
			}
			if (i < 10) {
				text = "00" + i;
			}
			System.out.println("wget ftp://ftp.ncbi.nih.gov/pub/TraceDB/myotis_lucifugus/fasta.myotis_lucifugus." + text + ".gz");
		}
	}
}
