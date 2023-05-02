package gui2;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;


public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private JLabel parcoursLabel = new JLabel("Pour Parcourir un dossier cliquer ici ");
	private JButton Parcours = new JButton(" Parcourir un répertoire ");
	

	
	

	
	public MainFrame(String title) {
		super(title);

		
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
        
		
		
		
		//contentPane.add(Parcours);
        
        //  contentPane.add(comp)
        
        /*
        contentPane.add(metadataLabel);
		contentPane.add(ImageMetadata);
		this.ImageMetadata.addActionListener(new MetadataAction());
		*/
		//this.setSize(800,500);
		
		JPanel metadonePanel = new MetadonePanel();
		this.add(metadonePanel,BorderLayout.CENTER);
		
		
		  this.setSize(800,500);
			
		  
		this.setLocationRelativeTo(null);;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	//	pack();
		setVisible(true);
	}
	
	
	
	
	
}
