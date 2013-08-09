
public class ConcatenateFinalTree {

	public static void main(String[] args) {
		String concatenate = "cat";
		for (int i = 1; i <= 10; i++) {
			for (int j = 1; j <= 50; j++) {
			    concatenate += " Bootstrap" + i + "/finalTre" + j + ".NJst";
			}
		}
		concatenate += "> BootstrapTree.tre";
		System.out.println(concatenate);
	}
}
