package utils;

import javax.swing.JComponent;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

/**
 * Un ancestorListener per gestire correttamente il ridimensionamento dei componenti interni ad un gridBagLayout
 */

public class DimensionFreezer implements AncestorListener {

	private final JComponent[] components;
	/**
	 * @param comps I componenti da gestire
	 */
	public DimensionFreezer(JComponent... comps) {
		components = comps;
	}
	
	@Override
	public void ancestorAdded(AncestorEvent arg0) {
		for(JComponent c : components) {
			c.setPreferredSize(c.getSize());
			c.setMinimumSize(c.getSize());
		}
	}
	
	@Override
	public void ancestorMoved(AncestorEvent arg0) {
	}
	@Override
	public void ancestorRemoved(AncestorEvent arg0) {
	}
}
