package view.gui.swing;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

public class IntegerInputVerifier extends InputVerifier {

	@Override
	public boolean verify(JComponent input) {
		String text = ((JTextField) input).getText();
		
		try {
			Integer value = new Integer(text);
		} catch (NumberFormatException e) {
			return false;
		}
		
		return true;
	}

}
