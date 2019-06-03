//
//Name: Galaviz, Saul
//
//Description:
//An application working with menus and dialogs
//

import java.awt.*;
import javax.swing.*;

public class JFontChooser 
{
	public static Font showDialog(JFrame frame, Font font) 
	{
		String[] fonts = {"Consolas", "Courier New", "DejaVu Sans Mono", "Fixedsys", "Monospace"};
		String[] styles = {"Plain", "Bold", "Italic"};
		String[] sizes = {"8", "12", "16", "20", "24", "28", "32", "36"};
		
		JComboBox fontz = new JComboBox(fonts);
		fontz.setSelectedItem(font.getName());
		
		JComboBox stylez = new JComboBox(styles);
		if(font.getStyle() == 0)
			stylez.setSelectedItem("Plain");
		else if(font.getStyle() == 1)
			stylez.setSelectedItem("Bold");
		else
			stylez.setSelectedItem("Italic");
		
		JComboBox sizez = new JComboBox(sizes);
		sizez.setSelectedItem(Integer.toString(font.getSize()));
		
		JPanel panel = new JPanel();
		
		panel.add(fontz);
		panel.add(stylez);
		panel.add(sizez);
		
		int input = JOptionPane.showConfirmDialog(null, panel, "Font Chooser", JOptionPane.OK_CANCEL_OPTION);
		if (input == JOptionPane.OK_OPTION) 
		{
			Font newFont = font;
			switch(stylez.getSelectedItem().toString())
			{
				case "Plain":
					newFont = new Font(fontz.getSelectedItem().toString(), 0, Integer.parseInt(sizez.getSelectedItem().toString()));
					break;
				case "Bold":
					newFont = new Font(fontz.getSelectedItem().toString(), 1, Integer.parseInt(sizez.getSelectedItem().toString()));
					break;
				case "Italic":
					newFont = new Font(fontz.getSelectedItem().toString(), 2, Integer.parseInt(sizez.getSelectedItem().toString()));
				
			}
		    return newFont;
		}
		return null;
	}
}
