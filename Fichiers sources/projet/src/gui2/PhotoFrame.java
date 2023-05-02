package gui2;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PhotoFrame extends JFrame {
	
	private JLabel label;
	
	public PhotoFrame(JLabel label) {
		super();
		this.label = label;
	
	 Container contentPane = getContentPane();
	 contentPane.setLayout(new FlowLayout());
	 contentPane.add(label);
	 this.setSize(500,250); 
	 this.setLocationRelativeTo(null);;
	 setVisible(true);
}
}
