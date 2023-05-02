package cli;
		import java.io.File;
		import java.io.IOException;
		import java.nio.file.Files;
		import java.nio.file.Path;
		import java.nio.file.Paths;
		import java.nio.file.attribute.BasicFileAttributeView;
		import java.nio.file.attribute.BasicFileAttributes;
		import java.nio.file.attribute.FileTime;
		import java.text.SimpleDateFormat;
import java.util.IllegalFormatWidthException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import javax.swing.text.html.HTMLDocument.Iterator;

import java.awt.image.BufferedImage; 
		
		public class Metadata{
			
		private File file;
		
		private Extension extension;
		
		public MimeType typemime;
		
		public Metadata (String FileName){

			file = new File(FileName);
			
			extension = new Extension(file);
			
			typemime = new MimeType(file.toPath());
		}
		
		
		
		public  String getCreationDetails()
		    {
		       try{
		        Path p = Paths.get(file.getAbsolutePath());
		        
		        BasicFileAttributes view
		           = Files.getFileAttributeView(p, BasicFileAttributeView.class)
		                  .readAttributes();
		        
		        FileTime fileTime=view.creationTime();
		        
		        return (""+new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format((fileTime.toMillis())));
		       
		       }catch(IOException e){ 
		        e.printStackTrace(); 
		       }

		       return ""; 
		   }

		public String getSize() 
		{
		
		String size = null;
		
		  BufferedImage image;
		try {
			image = ImageIO.read(file);
			
			 int width  = image.getWidth();
			 int height  = image.getHeight();
			 size = "Largeur:" + String.valueOf(width) + "   Hauteur:" + String.valueOf(height);
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  return size;
		}  
		  
		
		  
		
		
		public String toString() {
		String result;
		
		String ext = null;
		
		try {
			ext = extension.getExtension();
		} catch (FileWithoutExtension e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			result = "[ Les metadonnes de l'image : \n" + "La date de creation :" + this.getCreationDetails()   + " \nExtension :"+ ext 
					 
			
					+ this.getSize() + "\n type mime:" + typemime.getMimeType()   +  "\n]" ;
		
		
			return result;	
		}
		
		
		

		
		}
		
		
		
