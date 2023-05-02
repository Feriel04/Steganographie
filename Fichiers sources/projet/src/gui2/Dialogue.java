package gui2;

import javax.swing.JOptionPane;

public class Dialogue {
	
	private String textToHide = "";
	
	public Dialogue(){
	
	textToHide = JOptionPane.showInputDialog("Entrez le texte à cacher ");
	}
	
	public String getText() {
		return this.textToHide;
	}
	
	
}
