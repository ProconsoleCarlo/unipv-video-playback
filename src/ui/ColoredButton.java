package ui;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.border.MatteBorder;

import settings.MediaPlayerSettings;

/**
 * Un bottone che usa due colori per sfumare sullo sfondo
 *
 */
public class ColoredButton extends JButton{

	private static final long serialVersionUID = 0;

		private Color colorMain =   MediaPlayerSettings.getSettings().getColors().colorButtonMain;
		private Color colorSecond = MediaPlayerSettings.getSettings().getColors().colorButtonSecond;
		private Color colorBorder = MediaPlayerSettings.getSettings().getColors().colorButtonBorder;
		
		/**
		 * Crea un bottone colorato usando le setting (i colori) di default del programma.
		 * @param text Il testo da visualizzare sul bottone
		 */
		public ColoredButton(String text){
			super(text);
			setAll();
        }
		
        @Override
        protected void paintComponent(Graphics g){
             Graphics2D g2 = (Graphics2D)g.create();
          	 g2.setPaint(new GradientPaint(new Point(0, 0), colorSecond,
            				new Point(getWidth(), getHeight()), colorMain));
          	if (getModel().isPressed()) {
				g2.setPaint(new GradientPaint(new Point(0, 0), colorSecond.darker(),
            				new Point(getWidth(), getHeight()), colorMain.darker()));
			}
          	 
          	g2.fillRect(0, 0, getWidth(), getHeight());
            g2.dispose();
            super.paintComponent(g);
        }
        
        private void setAll(){
        	 setContentAreaFilled(false);
        	 setBorder(new MatteBorder(1, 1, 1, 1, colorBorder));
             setFocusPainted(false);
        }
}