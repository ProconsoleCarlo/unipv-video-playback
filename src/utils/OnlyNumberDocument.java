package utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
/**
 * Un plain document che controlla se gli inserimenti in un area di testo sono interi
 * e si occupa di effettuarne gli inserimenti tramite il metodo insertString.
 * 
 */
public class OnlyNumberDocument extends PlainDocument{

	private static final long serialVersionUID = 1L;
	@Override
	public void insertString(int arg0, String arg1, AttributeSet arg2) throws BadLocationException {
		try{
			if(Integer.parseInt(arg1) >= 0)
				super.insertString(arg0, arg1, arg2);
			}
			catch(NumberFormatException exc){
			return;
			}
	}
}
