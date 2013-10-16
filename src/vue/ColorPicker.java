package vue;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modele.CoupsManager;


public class ColorPicker extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private String[] couleurs = {"carreaux", "coeur", "pic", "trefle"};
	private JLabel[] images = new JLabel[4];
	private CoupsManager cm;

	public ColorPicker(){
		super("Choisir la couleur");
		cm = CoupsManager.getInstance();
		this.setSize(new Dimension(230,85));
		this.setResizable(false);
		panel = new JPanel();
		this.setContentPane(panel);
		panel.setBackground(new Color(0,80,0));
		panel.setLayout(new FlowLayout());
		this.setUndecorated(true);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		for(int i=0;i<couleurs.length;i++){
			images[i] = new JLabel();
			images[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
			images[i].setName(couleurs[i]);
			images[i].setIcon(RessourcesManager.getImage(couleurs[i]));
		}
		for(JLabel img : images){
			img.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					cm.getCurrentCoup().getCurrentJoueur().choixCouleur(((JLabel)e.getSource()).getName());
					fermer();
				}
				@Override
				public void mouseReleased(MouseEvent arg0) {
				}
				
				@Override
				public void mousePressed(MouseEvent arg0) {
				}
				
				@Override
				public void mouseExited(MouseEvent arg0) {
				}
				
				@Override
				public void mouseEntered(MouseEvent arg0) {
				}
			});
			panel.add(img);
		}
		
		this.setLocationRelativeTo(this.getParent());
		this.setVisible(true);
	}
	
	private void fermer(){
		this.setVisible(false);
	}
}
