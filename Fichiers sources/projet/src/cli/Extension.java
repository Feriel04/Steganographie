package cli;

import java.io.File;

/**
 * Classe qui represente l'extension d'un fichier,
 */
public class Extension {
	
	/**
	 * l'extension de fichier 
	 */
	String extension;
	
	
	/**
	 * le nom de fichier pour lequelle on cherche l'extension
	 */
	String fileName;
	
	
	/**
	 * initialise la valeur de fileName � fileName et utilise le setExtension pour initialiser la valeur de l'extension
		 */
	public Extension (File file) {
		// TODO Auto-generated constructor stub
		this.fileName = file.getName();
		this.setExtension();
		
	}
	
	/**
	 * 	Initialise la valeur de l'attribut extension � la valeur de l'extension de fichier.
	 *	si le fichier n'a pas d'extension il l'initialise � null
	 */
	public void setExtension() {  
		int index = fileName.lastIndexOf('.');// Renvoie l'index dans cette chane de la dernire occurrence de .

		if(index > 0 && (index < fileName.length() - 1)) {
			extension = fileName.substring(index + 1);
		}
		else extension = null;
	}
	
	/**
	 * @return retourne l'extension si elle est differente de null sinon lance l'exception FileWithoutException 
	 * @throws FileWithoutExtension 
	 * On peut supprimer l'exception et crEer une mEthode qui nous informe que le fichier 
	 * n'a pas d'extension pour eviter que l'extension bloque le parcourt d'un dossier 
	 */
	public String getExtension() throws FileWithoutExtension{ 
		if (extension != null) {
			return this.extension;
		}
		else throw new FileWithoutExtension();
	}
	
	/**
	 * @return retourne true si le fichier a une extension mp3, retourne false sinon
	 */
	public boolean hasMpegExtension() {
		if (extension != null) return extension.equals("png");
		else return false;
	}
	
	public boolean hasJpegExtension() {
		if (extension != null) return extension.equals("jpeg");
		else return false;
	}

}


