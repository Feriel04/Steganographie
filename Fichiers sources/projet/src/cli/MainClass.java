package cli;

public class MainClass {

	public static void main(String[] args) {

		if (args.length == 0) {
			System.out.println("erreur syntaxique : Cli.jar [-options] \n \t\t  "
					+ "-> arguments manquants\nvoir   -h   ou   -help    pour voir toutes les options. ");
		} else if (args[0].equals("-h") || args[0].equals("-help")) {
			System.out.println(
					"\t-d [<Repertoire>]\n\t\tAnalyser et lister tous les fichiers image (jpeg et png) a partir du dossier specifie . \n"
							+ "\t-f [<Fichier>]\n\t\tAfficher les metadonnees du fichier image specifie. \n"
							+ "\t-f [<Fichier>] -s [<texteASCII a dissimuler>]\n\t\tStocker le texte indique l'image specifie a l'aide de la steganographie.\n"
							+ "\t-f [<Fichier>] -e\n\t\tExtraire un texte dissimuler dans le fichier specifie. \n");

		} else if (args[0].equals("-d")) {

			// pour parcourir tout lerepertoir et affichier les fichier png
			// et jpg
			if (args.length !=  2) 	System.err.println("Veuillez choisir un dossier");
			
			else {
				Reprtoire rp = new Reprtoire(args[1]);
				rp.displayRepertoryFiles();
			}
			
		} else if (args[0].equals("-f")) {

			if (args.length == 2) {
				{
					// pour afficher les metas data d'un seul fichier
					Metadata m = new Metadata(args[1]);

					System.out.println(m);

				}
			} else if (args[2].equals("-e")) {
				Steganography s = new Steganography();
				
				String str = args[1];
				
				String[] words = str.split("\\.");
				
				System.out.println(s.decode(".", words[0]));
				

			} else if (args[2].equals("-s")) {
				// encoder

				Steganography s = new Steganography();

				String str = args[1];
				String[] words = str.split("\\.");
				s.encode(".", words[0], words[1], "newImage", args[3]);

			}

		}
	

	}

}
