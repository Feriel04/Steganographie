package cli;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

/*
*import list
*/
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/*
*Class Steganography
*/
public class Steganography
{
	
	/*
	 *Steganography Constructeur vide
	 */
	public Steganography()
	{
	}
	
	/*
	 *encoder une image avec du texte, le fichier de sortie sera de type .png
	 *@param path		 Le chemin (dossier) contenant l'image à modifier
	 *@param original	Le nom de l'image à modifier
	 *@param ext1		  Le type d'extension de l'image à modifier (jpg, png)
	 *@param stegan	  Le nom de sortie du fichier
	 *@param message  Le texte à cacher dans l'image
	 *@param type	  un entier représentant l'encodage de base ou avancé
	 */
	public boolean encode(String path, String original, String ext1, String stegan, String message)
	{
		String			file_name 	= image_path(path,original,ext1);
		BufferedImage 	image_orig	= getImage(file_name);
		
		//l'espace utilisateur n'est pas nécessaire pour le cryptage
		BufferedImage image = user_space(image_orig);
		image = add_text(image,message);
		
		return(setImage(image,new File(image_path(path,stegan,"png")),"png"));
	}
	
	/*
	 *decoder suppose que l'image utilisée est de type .png, extrait le texte caché d'une image
	 *@param path   Le chemin (dossier) contenant l'image à partir de laquelle extraire le message
	 *@param name Le nom de l'image à partir de laquelle extraire le message
	 *@param type un entier représentant l'encodage de base ou avancé
	 */
	public String decode(String path, String name)
	{
		byte[] decode;
		try
		{
			//l'espace utilisateur est nécessaire pour déchiffrer
			//****************************************************************************************
			//BufferedImage image  = user_space(getImage(image_path(path,name,"png")));
			BufferedImage image  = user_space(getImage(path +"/" +name));
			decode = decode_text(get_byte_data(image));
			return(new String(decode));
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, 
				"There is no hidden message in this image!","Error",
				JOptionPane.ERROR_MESSAGE);
			return "";
		}
	}
	
	/*
	 *Renvoie le chemin complet d'un fichier, sous la forme: path\name.ext
	 *@param path   Le chemin (dossier) du fichier
	 *@param name Le nom du fichier
	 *@param ext	 L'extension du fichier
	 *@return A Chaîne représentant le chemin complet d'un fichier
	 */
	private String image_path(String path, String name, String ext)
	{
		return path + "/" + name + "." + ext;
	}
	
	/*
	 *Get méthode pour retourner un fichier image
	 *@param f Le chemin d'accès complet de l'image.
	 *@return Une BufferedImage du chemin d'accès au fichier fourni
	 *@see	Steganography.image_path
	 */
	private BufferedImage getImage(String f)
	{
		BufferedImage 	image	= null;
		File 		file 	= new File(f);
		
		try
		{
			image = ImageIO.read(file);
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, 
				"Image could not be read!","Error",JOptionPane.ERROR_MESSAGE);
		}
		return image;
	}
	
	/*
	 *Définir la méthode pour enregistrer un fichier image
	 *@param image Le fichier image à enregistrer
	 *@param file	  Fichier dans lequel enregistrer l'image
	 *@param ext	  L'extension et donc le format du fichier à enregistrer
	 *@return Renvoie vrai si la sauvegarde est réussie
	 */
	private boolean setImage(BufferedImage image, File file, String ext)
	{
		try
		{
			file.delete(); //supprimer les ressources utilisées par le fichier
			ImageIO.write(image,ext,file);
			return true;
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, 
				"File could not be saved!","Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	/*
	 *Gère l'ajout de texte dans une image
	 *@param image L'image à laquelle ajouter du texte masqué
	 *@param text	 il texte à cacher dans l'image
	 *@return  l'image avec le texte incorporé dedans
	 */
	private BufferedImage add_text(BufferedImage image, String text)
	{
		//convertir tous les éléments en tableaux d'octets: image, message, message length
		byte img[]  = get_byte_data(image);
		byte msg[] = text.getBytes();
		byte len[]   = bit_conversion(msg.length);
		try
		{
			encode_text(img, len,  0); //0 première position
			encode_text(img, msg, 32); //4 octets d'espace pour la longueur: 4bytes*8bit = 32 bits
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, 
"Target File cannot hold message!", "Error",JOptionPane.ERROR_MESSAGE);
		}
		return image;
	}
	
	/*
	 *Crée une version d'espace utilisateur d'une image tampon, pour l'édition et l'enregistrement d'octets
	 *@param image L'image à mettre dans l'espace utilisateur, supprime les interférences de compression
	 *@return La version espace utilisateur de l'image fournie
	 */
	private BufferedImage user_space(BufferedImage image)
	{
		//créer une nouvelle image avec les attributs de l'image
		BufferedImage new_img  = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D	graphics = new_img.createGraphics();
		graphics.drawRenderedImage(image, null);
		graphics.dispose(); //libérer toute la mémoire allouée pour cette image
		return new_img;
	}
	
	/*
	 *Obtient le tableau d'octets d'une image
	 *@param L'image à partir de laquelle obtenir les données d'octet
	 *@return Renvoie le tableau d'octets de l'image fournie
	 *@see Raster
	 *@see WritableRaster
	 *@see DataBufferByte
	 */
	private byte[] get_byte_data(BufferedImage image)
	{
		WritableRaster raster   = image.getRaster();
		DataBufferByte buffer = (DataBufferByte)raster.getDataBuffer();
		return buffer.getData();
	}
	
	/*
	 *Génère le format d'octet approprié d'un entier
	 *@param i L'entier à convertir
	 *@returnRenvoie un tableau d'octets[4] convertissant l'entier fourni en octets
	 */
	private byte[] bit_conversion(int i)
	{
		//originally integers (ints) cast into bytes
		//byte byte7 = (byte)((i & 0xFF00000000000000L) >>> 56);
		//byte byte6 = (byte)((i & 0x00FF000000000000L) >>> 48);
		//byte byte5 = (byte)((i & 0x0000FF0000000000L) >>> 40);
		//byte byte4 = (byte)((i & 0x000000FF00000000L) >>> 32);
		
		//only using 4 bytes
		byte byte3 = (byte)((i & 0xFF000000) >>> 24); //0
		byte byte2 = (byte)((i & 0x00FF0000) >>> 16); //0
		byte byte1 = (byte)((i & 0x0000FF00) >>> 8 ); //0
		byte byte0 = (byte)((i & 0x000000FF)	   );
		//{0,0,0,byte0} est equivalent,  puisque tous les déplacements >=8 will be 0
		return(new byte[]{byte3,byte2,byte1,byte0});
	}
	
	/*
	 *Encoder un tableau d'octets dans un autre tableau d'octets à un décalage fourni
	 *@param image	 Tableau de données représentant une image
	 *@param addition Tableau de données à ajouter au tableau de données d'image fourni
	 *@param offset	  Le décalage dans le tableau d'images pour ajouter les données supplémentaires
	 *@return Renvoie des données Tableau d'image de fusion et de données supplémentaires
	 */
	private byte[] encode_text(byte[] image, byte[] addition, int offset)
	{
		//vérifier que les données + offset s'adapteront à l'image
		if(addition.length + offset > image.length)
		{
			throw new IllegalArgumentException("File not long enough!");
		}
		//boucle sur chaque octet d'addition
		for(int i=0; i<addition.length; ++i)
		{
			//boucle sur les 8 bits de chaque octet
			int add = addition[i];
			for(int bit=7; bit>=0; --bit, ++offset) //s'assurer que la nouvelle valeur de décalage continue à travers les deux boucles
			{
				//affecter un entier à b, décalé par des espaces de bits ET 1
				//un seul bit de l'octet courant
				int b = (add >>> bit) & 1;
				//affecter le bit en prenant : [(valeur d'octet précédente) ET 0xfe] OU bit à ajouter
				//change le dernier bit de l'octet dans l'image pour être le bit d'addition
				image[offset] = (byte)((image[offset] & 0xFE) | b );
			}
		}
		return image;
	}
	
	/*
	 *Retrieves hidden text from an image 
	 *@param image Array of data, représenter une image
	 *@return  le tableau de données qui contient le texte caché
	 */
	private byte[] decode_text(byte[] image)
	{
		int length = 0;
		int offset  = 32;
		//boucle sur 32 octets de données pour déterminer la longueur du texte
		for(int i=0; i<32; ++i) //i=24 fonctionnera également, car seul le 4ème octet contient des données réelles
		{
			length = (length << 1) | (image[i] & 1);
		}
		
		byte[] result = new byte[length];
		
		//parcourir chaque octet de texte
		for(int b=0; b<result.length; ++b )
		{
			//boucle sur chaque bit dans un octet de texte
			for(int i=0; i<8; ++i, ++offset)
			{
				//attribuer bit: [(new byte value) << 1] OR [(text byte) AND 1]
				result[b] = (byte)((result[b] << 1) | (image[offset] & 1));
			}
		}
		return result;
	}
}
