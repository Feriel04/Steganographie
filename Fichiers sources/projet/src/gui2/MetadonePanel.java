package gui2;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import cli.Extension;
import cli.FileWithoutExtension;
import cli.Metadata;
import cli.Steganography;

//import gui2.MainFrame.MetadataAction;

import javax.swing.JFileChooser;
import java.io.*;

public class MetadonePanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static File selectedFile;
	JLabel selectedFileLabel = new JLabel("Aucun fichier sélectionné");
    
	JTextArea metadataLabel = new JTextArea("Les métadonnées de l'image sont affiché ici");
	JPanel panel312 = new JPanel();
	
	public MetadonePanel() {
		
	    super();	

		//this.setLayout(new FlowLayout());
		
	    this.setLayout(new BorderLayout());

	    //****************JFilechooser
	    
	    
	    JPanel panel1 = new JPanel();
	    JPanel panel2 = new JPanel();
	    JPanel panel3 = new JPanel();
	    
	    panel1.setLayout(new FlowLayout());
	    JButton chooseButton = new JButton("Sélectionner une image");
	    //JLabel selectedFileLabel = new JLabel("Aucun fichier sélectionné");
	    panel1.add(chooseButton);
	    	JPanel panel11 = new JPanel();
	    	panel11.setLayout(new BorderLayout());
	    	JLabel label =new JLabel("Fichier courant");
	    	label.setFont(new Font("Verdana", Font.PLAIN, 18));
	    	panel11.add(label, BorderLayout.NORTH);
	    	panel11.add(selectedFileLabel, BorderLayout.CENTER);

	    panel1.add(panel11);
	    this.add(panel1, BorderLayout.NORTH);
	    
	    
	    panel2.setLayout(new BorderLayout());
	    	JPanel panel22 = new JPanel();
	    	panel22.setLayout(new FlowLayout());
	    	JButton metadataButton = new JButton(" AfficherLes métadonnnées");
	    	panel22.add(metadataButton);
	    	panel2.add(panel22,BorderLayout.NORTH);
	    	panel2.add(metadataLabel, BorderLayout.CENTER);
	    	
	    this.add(panel2, BorderLayout.WEST);
	    
	    
	    panel3.setLayout(new BorderLayout());
	    	JPanel panel31 = new JPanel();
	   
	    	panel31.setLayout(new BorderLayout());
	    	
	    		JPanel panel311 = new JPanel();
	    		//JPanel panel312 = new JPanel();
	    	
	    		panel31.setLayout(new FlowLayout());
	    		JButton afficherImageButton = new JButton("Afficher l'image");
	    		panel31.add(afficherImageButton);
	    		
	    		panel31.add(panel311, BorderLayout.NORTH);
	    		//panel312 pour afficher l'image
	    		panel31.add(panel312, BorderLayout.CENTER);
	    		
	    		
	    	JPanel panel32 = new JPanel();
	    	panel32.setLayout(new FlowLayout());
		    JButton encoderButton = new JButton("Encoder");
			JButton decoderButton = new JButton("Decoder");
			panel32.add(encoderButton);
			panel32.add(decoderButton);
			
			panel3.add(panel31,BorderLayout.NORTH);
			panel3.add(panel32,BorderLayout.CENTER);

	    	
	    this.add(panel3, BorderLayout.EAST);
	    
	    
	    
	    //ajout des action listner
		chooseButton.addActionListener(new SelectAction(this));
		metadataButton.addActionListener(new MetadataAction());
	    afficherImageButton.addActionListener(new afficherImageAction());
	    
	    encoderButton.addActionListener(new EncoderAction());
	    decoderButton.addActionListener(new DecoderAction());
		this.setVisible(true);
		
		
	}
	
	public void writeData(String text) {
		metadataLabel.setText(text);
		metadataLabel.setEditable(false);
	}
	private class EncoderAction implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
			String fileName = MetadonePanel.selectedFile.getAbsolutePath();
			
			String imageToEncode = MetadonePanel.selectedFile.getName();
			
			
			
				String txtToHide ="aucun message";
				
				Dialogue dg = new Dialogue();
				txtToHide = dg.getText();
				
				Steganography s = new Steganography();
				
				String[] words = imageToEncode.split("\\.");
				
				s.encode(MetadonePanel.selectedFile.getParent(), words[0], words[1], "newImage",txtToHide);
				
			
		}
	}
	
	private class DecoderAction implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			File file = MetadonePanel.selectedFile;

			String imageTodecode = MetadonePanel.selectedFile.getName();
			

			Steganography s = new Steganography();


			//String[] words = imageTodecode.split("\\.");
			String result = s.decode(file.getParent(), imageTodecode);
			
			//String message_Caché = JOptionPane.showInputDialog(result);
			
		    JOptionPane.showMessageDialog(null, result,"Le message caché.",JOptionPane.PLAIN_MESSAGE); 

		}
	}

	private class SelectAction implements ActionListener {
		Container cmpt;
		public  SelectAction(Container cmpt) {
			super();
			this.cmpt = cmpt;
		}
		public void actionPerformed(ActionEvent e) {
			
			 
		    JFileChooser chooser = new JFileChooser();
		    
		    chooser.setCurrentDirectory(new File(System.getProperty("user.home")) );
		    
		    int result = chooser.showOpenDialog(cmpt);
		    
		   if(result == JFileChooser.APPROVE_OPTION) {
			   MetadonePanel.selectedFile = chooser.getSelectedFile();
			   selectedFileLabel.setText(MetadonePanel.selectedFile.getAbsolutePath());
			   
		   } 
		}
	}
	
	private class MetadataAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			String chemin = MetadonePanel.selectedFile.getAbsolutePath();
			Metadata m = new Metadata (chemin);
			
			writeData(m.toString());
		
		}
	}
	
	private class afficherImageAction implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
	        File file = new File(MetadonePanel.selectedFile.getAbsolutePath());
	       
	        
	     
	        BufferedImage bufferedImage;
			try {
				bufferedImage = ImageIO.read(file);

		        ImageIcon imageIcon = new ImageIcon(bufferedImage);
		        JLabel jLabel = new JLabel();

		        jLabel.setIcon(imageIcon);
		        
		        PhotoFrame phFrame = new PhotoFrame(jLabel);
		        
		        

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
	
		}
	

	}
}
	
