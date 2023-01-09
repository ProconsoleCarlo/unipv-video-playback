package utils;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class LimConstraints extends GridBagConstraints {

	private static final long serialVersionUID = 0;

	/**
	 * Dato il nome di un component, 
	 * permette di posizionarlo nella casella (x,y) all'interno del GridBagLayout.
	 * @param name nome componente
	 * @param x lim.gridx
	 * @param y lim.gridy
	 * @param width lim.gridwidth
	 * @param height lim.gridheight
	 * @param layout
	 */
	public void setLimAndConstraints(Component name, int x, int y, int width,
			int height, GridBagLayout layout) {
		this.gridx = x;
		this.gridy = y;
		this.gridwidth = width;
		this.gridheight = height;
		layout.setConstraints(name, this);

	}
}
