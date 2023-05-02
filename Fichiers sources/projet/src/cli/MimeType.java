package cli;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Classe qui represente le type Mime d'un objet (le type de son contenu)<br/>
 * Lien avec MyFile : relation d'aggregation<br/>
 * Type mime gerer par cette classe : audio/mpeg, audio/MPA, audio/mpa-robust <br/>
 * La partie la plus importante est la méthode hasMp3TypeMime <br/>
 */
public class MimeType {
	
	/**
	 * Le type mime de fichier
	 */
	String mimeType;
	
	
	/**
	 * Chemin de fichier sous la forme d'un objet de la classe(Interface) Path
	 */
	Path path; 

	/**
	 * Constucteur initialise le path 
	 * Utilise setMimeType pour initialiser le MimeType
	 * @param path : chemin de fichier sous la forme d'un objet de la classe java.nio.file.Path 
	 */
	public MimeType(Path path) {
		 this.path = path;
		 try {
			setMimeType();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * initialise la valeur de mimeType il utilise la méthode Files.probeContent-Type(path)
	 * @throws IOException en cas d'erreur d'E/S
	 * @throws SecurityException : si l'accès au fichier n'est pas autorisé 
	 */
	public void setMimeType() throws IOException,SecurityException {
		this.mimeType = Files.probeContentType(path);
	}
	
	/**
	 * @return le type mime de fichier
	 */
	public String getMimeType(){
		return this.mimeType;
	}
	
	/**
	 * @return : le path de fichier (objet de type Path)
	 */
	public Path getPath() {
		return path;
	}
	
	/**
	 *@return Le path et le type mime ,transforme l'objet en une chaine de caractère
	 */
	public String toString() {
		return "[ Chemin de fichier: " + path.toString() + ", Type MIME de fichier: " + mimeType + " ]";
	}
	
	
	/**
	 * @return true si le type mime est égal à audio/ mpeg, retourne false sinon 
	 * on ne traite pas le cas de fichier sans type mime
	 */
	public boolean hasMp3TypeMime() {
		if(mimeType != null) {
			if(mimeType.equals("audio/mpeg") || mimeType.equals("audio/MPA")
					|| mimeType.equals("audio/mpa-robust")){
				return true;
			}
			else return false;
		}
		else {
			return false;
		}
	}
}