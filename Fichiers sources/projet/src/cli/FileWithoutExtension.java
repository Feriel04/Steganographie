package cli;

	/**
	 * exception thrown when when use a path of an existing file but whitout extension in instance "/home/bouzidia/myFile"<br/>
	 * exception déclenché quand un fichier n'a pas d'extension
	 * */
	public class FileWithoutExtension extends Exception {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		

		/**
		 * Constructeur : create an instance of this class
		 */
		public FileWithoutExtension() {
			super("1.Le fichier n'a pas d'extension !\n 2.Ou le dossier que vous voulez parcourir contient un fichier qui n'a pas d'extension");
			// TODO Auto-generated constructor stub
		}
		
	}

