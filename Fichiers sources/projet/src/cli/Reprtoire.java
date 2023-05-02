package cli;

import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Reprtoire {
	
	private String chemin;
	private File [] files;
	private ArrayList<File> allFile;

	
	
	public Reprtoire(String chemin) {
	//	super();

		this.chemin = chemin;
		this.allFile = new ArrayList<File>();
		
	}
	
	
	public void parcours(File dir)
	{
		File [] files = dir.listFiles();
		for (File file : files) {
			if(file.isDirectory()) {
				parcours(file);
			}
			else {
				if(file.getName().endsWith(".jpeg") || file.getName().endsWith(".png")) {
					System.out.println(file.getName());
					allFile.add(file);				}
			}
		}
	}

	public String getChemin() {
		return this.chemin;
	}
	
	/*
	 * Cette méthode affiche les fichiers(images) contenu dans le repértoire
	 * */
	public void displayRepertory() {
		
		File file = new File(this.getChemin());
		
		this.parcours(file);
		
		int index = 1;
		for(File currentFile : this.allFile) {
			
			Metadata m = new Metadata (currentFile.getAbsolutePath());
			System.out.println("Fichier numéro  "+ String.valueOf(index) + ":");
			System.out.println(m + "\n");	
			index++;
		} 
		
	}
	
	public void displayRepertoryFiles() {
		
		File file = new File(this.getChemin());
		
		this.parcours(file);
		
		int index = 1;
		for(File currentFile : this.allFile) {
			 
			System.out.println(currentFile.getName() + "\n");	
			
		} 
		
	}

}
	
			  
