//
//Name: Galaviz, Saul
//
//Description:
//A simulated note pad using Java Swing.
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultEditorKit;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.*;

public class JNotepad implements ActionListener 
{
	JFrame frame;
	JFileChooser fileChooser;
	ImageIcon defaultImage;
	JTextArea textArea;
	Font font, fontSel;
	String fileHeading;
	int x, y;
	JLabel label, txtArea;
	JMenuItem statusItem;
	Color color;
	JTextField findField;
	int counter;
	JButton findNext;
	JLabel status;
	
	@SuppressWarnings("static-access")
	public JNotepad()
	{
		frame = new JFrame();
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
	    y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
	    frame.setLocation(x, y);
	    
	    defaultImage = new ImageIcon();
	    
	    try {
			defaultImage = new ImageIcon(ImageIO.read(getClass().getResource("JNotepad.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	    frame.setIconImage(defaultImage.getImage());
	    
	    font = new Font("Courier Normal", Font.PLAIN, 12);
	    frame.setFont(font);
	    fontSel = font;
	    
	    textArea = new JTextArea();
	    textArea.setLineWrap(true);
	    textArea.setWrapStyleWord(true);
	    textArea.setFont(new Font("Courier Normal", Font.PLAIN, 12));
	    
	    JScrollPane scrollPane = new JScrollPane(textArea);
	    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    
	    frame.add(scrollPane);
	    
	    JMenuBar jmb = new JMenuBar();
	    
	    JMenu fileMenu = new JMenu("File");
	    fileMenu.setMnemonic('F');
	    
	    JMenuItem newItem = new JMenuItem("New", 'N');
	    newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
	    
	    JMenuItem openItem = new JMenuItem("Open...", 'O');
	    openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
	    
	    JMenuItem saveItem = new JMenuItem("Save", 'S');
	    saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
	    
	    JMenuItem saveAsItem = new JMenuItem("Save As...", 'A');
	    saveAsItem.setDisplayedMnemonicIndex(5);
	    
	    JMenuItem pageSetItem = new JMenuItem("Page Setup...", 'u');
	    
	    JMenuItem printItem = new JMenuItem("Print...", 'P');
	    printItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
	    
	    JMenuItem exitItem = new JMenuItem("Exit", 'x');
	    
	    fileMenu.add(newItem);
	    fileMenu.add(openItem);
	    fileMenu.add(saveItem);
	    fileMenu.add(saveAsItem);
	    fileMenu.addSeparator();
	    fileMenu.add(pageSetItem);
	    fileMenu.add(printItem);
	    fileMenu.addSeparator();
	    fileMenu.add(exitItem);
	    
	    JMenu editMenu = new JMenu("Edit");
	    editMenu.setMnemonic('E');
	    
	    JMenuItem undoItem = new JMenuItem("Undo", 'U');
	    
	    JPopupMenu pop = new JPopupMenu();
	    
	    Action cutItem = new DefaultEditorKit.CutAction();
        cutItem.putValue(Action.NAME, "Cut");
        cutItem.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
        cutItem.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        
        Action copyItem = new DefaultEditorKit.CopyAction();
        copyItem.putValue(Action.NAME, "Copy");
        copyItem.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
        copyItem.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        
        Action pasteItem = new DefaultEditorKit.PasteAction();
        pasteItem.putValue(Action.NAME, "Paste");
        pasteItem.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
        pasteItem.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        
        pop.add(cutItem);
        pop.add(copyItem);
        pop.add(pasteItem);
        
        textArea.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    pop.show(me.getComponent(), me.getX(), me.getY());
                }
            }
        });
        
        JMenuItem deleteItem = new JMenuItem("Delete", 'l');
	    deleteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
	    
	    JMenuItem findItem = new JMenuItem("Find...", 'F');
	    findItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
	    
	    JMenuItem findNextItem = new JMenuItem("Find Next", 'N');
	    findNextItem.setDisplayedMnemonicIndex(5);
	    
	    JMenuItem replaceItem = new JMenuItem("Replace...", 'R');
	    replaceItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
	    
	    JMenuItem goToItem = new JMenuItem("Go To...", 'G');
	    goToItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
	    
	    JMenuItem selectAllItem = new JMenuItem("Select All", 'A');
	    selectAllItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
	    
	    JMenuItem timeDateItem = new JMenuItem("Time/Date", 'D');
	    timeDateItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
	    
	    editMenu.add(undoItem);
	    editMenu.addSeparator();
	    editMenu.add(cutItem);
	    editMenu.add(copyItem);
	    editMenu.add(pasteItem);
	    editMenu.add(deleteItem);
	    editMenu.addSeparator();
	    editMenu.add(findItem);
	    editMenu.add(findNextItem);
	    editMenu.add(replaceItem);
	    editMenu.add(goToItem);
	    editMenu.addSeparator();
	    editMenu.add(selectAllItem);
	    editMenu.add(timeDateItem);
	    
	    JMenu formatMenu = new JMenu("Format");
	    formatMenu.setMnemonic('o');
	    
	    JMenuItem wordWrapItem = new JMenuItem("Word Wrap", 'W');
	    
	    JMenuItem fontItem = new JMenuItem("Font...", 'F');
	    
	    JMenuItem foregroundItem = new JMenuItem("Set Foreground...", 'C');
	    
	    JMenuItem backgroundItem = new JMenuItem("Set Background...", 'B');
	    
	    formatMenu.add(wordWrapItem);
	    formatMenu.add(fontItem);
	    formatMenu.add(foregroundItem);
	    formatMenu.add(backgroundItem);
	    
	    JMenu viewMenu = new JMenu("View");
	    viewMenu.setMnemonic('V');
	    
	    statusItem = new JMenuItem("Status Bar", 'S');
	    statusItem.setEnabled(false);
	    
	    viewMenu.add(statusItem);
	    
	    JMenu helpMenu = new JMenu("Help");
	    helpMenu.setMnemonic('H');
	    
	    JMenuItem viewHelpItem = new JMenuItem("View Help", 'H');
	    
	    JMenuItem aboutItem = new JMenuItem("About JNotepad", 'A');
	    
	    helpMenu.add(viewHelpItem);
	    helpMenu.addSeparator();
	    helpMenu.add(aboutItem);
	    
	    jmb.add(fileMenu);
	    jmb.add(editMenu);
	    jmb.add(formatMenu);
	    jmb.add(viewMenu);
	    jmb.add(helpMenu);
	    
	    newItem.addActionListener(this);
	    openItem.addActionListener(this);
	    saveItem.addActionListener(this);
	    saveAsItem.addActionListener(this);
	    pageSetItem.addActionListener(this);
	    printItem.addActionListener(this);
	    exitItem.addActionListener(this);
	    undoItem.addActionListener(this);
	    deleteItem.addActionListener(this);
	    findItem.addActionListener(this);
	    findNextItem.addActionListener(this);
	    replaceItem.addActionListener(this);
	    goToItem.addActionListener(this);
	    selectAllItem.addActionListener(this);
	    timeDateItem.addActionListener(this);
	    wordWrapItem.addActionListener(this);
	    fontItem.addActionListener(this);
	    foregroundItem.addActionListener(this);
	    backgroundItem.addActionListener(this);
	    statusItem.addActionListener(this);
	    viewHelpItem.addActionListener(this);
	    aboutItem.addActionListener(this);
	    
	    frame.setJMenuBar(jmb);
	    
	    fileHeading = "Untitled";
	    frame.setTitle(fileHeading + " - Notepad");
	    findField = new JTextField("");
	    counter = 0;
        
	    frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		switch(ae.getActionCommand())
		{
			case "New":
				if(!textArea.getText().equals(""))
				{
					JDialog newArea = new JDialog(frame, "Notepad", true);
					newArea.setSize(400, 125);
					newArea.setLocation(x, y);
					
					newArea.setLayout(new BorderLayout());
					JLabel label = new JLabel("Do you want to save changes to " + fileHeading + "?");
					JButton yesSaveBtn = new JButton("Save");
					JButton noSaveBtn = new JButton("Don't Save");
					JButton cancelBtn = new JButton("Cancel");
					newArea.add(label, BorderLayout.NORTH);
					
					JPanel newPanel = new JPanel();
					newPanel.setLayout(new GridLayout(0, 3));
					newPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
					
					newPanel.add(yesSaveBtn);
					newPanel.add(noSaveBtn);
					newPanel.add(cancelBtn);
					
					newArea.add(newPanel, BorderLayout.SOUTH);
					
					yesSaveBtn.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							fileChooser = new JFileChooser();
							fileChooser.setDialogTitle("Save As");  
							
							int savesResult = fileChooser.showSaveDialog(frame);
							
							if (savesResult == JFileChooser.APPROVE_OPTION) 
							{
								File fileToSave = fileChooser.getSelectedFile();
								fileHeading = fileToSave.getName();
								frame.setTitle(fileHeading + " - Notepad");
								
								try
								{
									FileWriter fileWriter = new FileWriter(fileToSave);
									
									String text = textArea.getText();
									fileWriter.write(text);
									fileWriter.close();
								}catch (IOException e) 
				                {
				                	JOptionPane.showConfirmDialog(frame, "Catastrophic failure", "Error Message", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				                }
								newArea.setVisible(false);
							}
						}
					});
					
					noSaveBtn.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							newArea.setVisible(false);
						}
					});
					
					cancelBtn.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae)
						{
							newArea.setVisible(false);
						}
					});
					
					newArea.setVisible(true);
				}
				
				textArea.setText("");
				fileHeading = "Untitled";
				frame.setTitle(fileHeading + " - Notepad"); 
				break;
			case "Open...":
				JDialog openArea = new JDialog(frame, "Notepad", true);
				openArea.setSize(400, 125);
				openArea.setLocation(x, y);
				
				openArea.setLayout(new BorderLayout());
				JLabel labell = new JLabel("Do you want to save changes to " + fileHeading + "?");
				JButton yesSaveeBtn = new JButton("Save");
				JButton noSaveeBtn = new JButton("Don't Save");
				JButton cancellBtn = new JButton("Cancel");
				openArea.add(labell, BorderLayout.NORTH);
				
				JPanel newPanel = new JPanel();
				newPanel.setLayout(new GridLayout(0, 3));
				newPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
				
				newPanel.add(yesSaveeBtn);
				newPanel.add(noSaveeBtn);
				newPanel.add(cancellBtn);
				
				openArea.add(newPanel, BorderLayout.SOUTH);
				
				yesSaveeBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						fileChooser = new JFileChooser();
						fileChooser.setDialogTitle("Save As");  
						
						int savesResult = fileChooser.showSaveDialog(frame);
						
						if (savesResult == JFileChooser.APPROVE_OPTION) 
						{
							File fileToSave = fileChooser.getSelectedFile();
							fileHeading = fileToSave.getName();
							frame.setTitle(fileHeading + " - Notepad");
							
							try
							{
								FileWriter fileWriter = new FileWriter(fileToSave);
								
								String text = textArea.getText();
								fileWriter.write(text);
								fileWriter.close();
							}catch (IOException e) 
			                {
			                	JOptionPane.showConfirmDialog(frame, "Catastrophic failure", "Error Message", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			                }
							openArea.setVisible(false);
						}
					}
				});
				
				noSaveeBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						textArea.setText("");
						fileChooser = new JFileChooser();
						fileChooser.setDialogTitle("Open"); 
						
						fileChooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt"));
						fileChooser.setFileFilter(new FileNameExtensionFilter("Java source files", "java"));
						int openResult = fileChooser.showOpenDialog(frame);
						
						if(openResult == JFileChooser.APPROVE_OPTION)
						{
							File selectedFile = fileChooser.getSelectedFile();
							fileHeading = selectedFile.getName();
							frame.setTitle(fileHeading + " - Notepad"); 
							
							String fileName = "";
			                File file = selectedFile;
			                fileName = file.getAbsolutePath();

			                try 
			                {
			                    FileReader fileReader = new FileReader(fileName);
			                    
			                    @SuppressWarnings("resource")
								BufferedReader bufferedReader = new BufferedReader(fileReader);

			                    String fileText = bufferedReader.readLine();

			                    while (fileText != null) 
			                    {
			                    	if(textArea.getText().equals(""))
			                    		textArea.setText(fileText);
			                    	else
			                    		textArea.setText(textArea.getText() + "\n" + fileText);
			                        fileText = bufferedReader.readLine();
			                    }

			                } 
			                catch (FileNotFoundException e) 
			                {
			                	JOptionPane.showConfirmDialog(frame, "The File You've Selected Doesn't Exist.", "Error Message", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			                } 
			                catch (IOException e) 
			                {
			                	JOptionPane.showConfirmDialog(frame, "Catastrophic failure", "Error Message", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			                }
						openArea.setVisible(false);
					}
					}});
				
				cancellBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae)
					{
						openArea.setVisible(false);
					}
				});
				
				openArea.setVisible(true);
				break;
				
			case "Save":
				if(fileHeading.equals("Untitled"))
				{
					fileChooser = new JFileChooser();
					fileChooser.setDialogTitle("Save As");  
					
					int saveResult = fileChooser.showSaveDialog(frame);
					
					if (saveResult == JFileChooser.APPROVE_OPTION) 
					{
						File fileToSave = fileChooser.getSelectedFile();
						fileHeading = fileToSave.getName();
						frame.setTitle(fileHeading + " - Notepad");
						
						try
						{
							FileWriter fileWriter = new FileWriter(fileToSave);
							
							String text = textArea.getText();
							fileWriter.write(text);
							fileWriter.close();
						}catch (IOException e) 
		                {
		                	JOptionPane.showConfirmDialog(frame, "Catastrophic failure", "Error Message", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
		                }
					}
				}
				
				else
				{
					File fileToSave = new File(fileHeading);
					
					try
					{
						FileWriter fileWriter = new FileWriter(fileToSave);
						
						String text = textArea.getText();
						fileWriter.write(text);
						fileWriter.close();
					}catch (IOException e) 
	                {
	                	JOptionPane.showConfirmDialog(frame, "Catastrophic failure", "Error Message", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
	                }
				}
				break;
			case "Save As...":
				fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Save As");  
				
				int saveResult = fileChooser.showSaveDialog(frame);
				
				if (saveResult == JFileChooser.APPROVE_OPTION) 
				{
					File fileToSave = fileChooser.getSelectedFile();
					fileHeading = fileToSave.getName();
					frame.setTitle(fileHeading + " - Notepad");
					
					try
					{
						FileWriter fileWriter = new FileWriter(fileToSave);
						
						String text = textArea.getText();
						fileWriter.write(text);
						fileWriter.close();
					}catch (IOException e) 
	                {
	                	JOptionPane.showConfirmDialog(frame, "Catastrophic failure", "Error Message", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
	                }
				}
				break;
			case "Page Setup...":
				break;
			case "Print...":
				break;
			case "Exit":
				JDialog exit = new JDialog(frame, "Notepad", true);
				exit.setSize(400, 125);
				exit.setLocation(x, y);
				
				exit.setLayout(new BorderLayout());
				JLabel label = new JLabel("Do you want to save changes to " + fileHeading + "?");
				JButton yesSaveBtn = new JButton("Save");
				JButton noSaveBtn = new JButton("Don't Save");
				JButton cancelBtn = new JButton("Cancel");
				exit.add(label, BorderLayout.NORTH);
				
				JPanel exitPanel = new JPanel();
				exitPanel.setLayout(new GridLayout(0, 3));
				exitPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
				
				exitPanel.add(yesSaveBtn);
				exitPanel.add(noSaveBtn);
				exitPanel.add(cancelBtn);
				
				exit.add(exitPanel, BorderLayout.SOUTH);
				
				yesSaveBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						fileChooser = new JFileChooser();
						fileChooser.setDialogTitle("Save As");  
						
						int savesResult = fileChooser.showSaveDialog(frame);
						
						if (savesResult == JFileChooser.APPROVE_OPTION) 
						{
							File fileToSave = fileChooser.getSelectedFile();
							fileHeading = fileToSave.getName();
							frame.setTitle(fileHeading + " - Notepad");
							
							try
							{
								FileWriter fileWriter = new FileWriter(fileToSave);
								
								String text = textArea.getText();
								fileWriter.write(text);
								fileWriter.close();
							}catch (IOException e) 
			                {
			                	JOptionPane.showConfirmDialog(frame, "Catastrophic failure", "Error Message", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			                }
							exit.setVisible(false);
						}
					}
				});
				
				noSaveBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						System.exit(0);
					}
				});
				
				cancelBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae)
					{
						exit.setVisible(false);
					}
				});
				
				exit.setVisible(true);
				break;
			case "Undo":
				break;
			case "Delete":
				textArea.replaceSelection("");
				break;
			case "Find...":
				JDialog findDialog = new JDialog(frame , "Find" , false);
		        findDialog.setLocation(x, y);
		        findDialog.setSize(400, 300);
		        findDialog.setLayout(null);

		        findField = new JTextField(); 
		        JLabel findLabel = new JLabel("Find What:");
		        findLabel.setBounds(20, 20, 100, 20);
		        findField.setBounds(findLabel.getX()+100, findLabel.getY(), 150, findLabel.getHeight());
		        findDialog.add(findLabel);
		        findDialog.add(findField);

		        JTextField replacefield = new JTextField(); 
		        JLabel replacelabel = new JLabel("Replace With:");
		        replacelabel.setBounds(10, 60, 100, 20);
		        replacefield.setBounds(replacelabel.getX()+100, replacelabel.getY(), 150, replacelabel.getHeight());
		        findDialog.add(replacelabel);
		        findDialog.add(replacefield);

		        JLabel status = new JLabel();
		        status.setBounds(10, 130, 200, 20);
		        findDialog.add(status);

		        JButton findnext = new JButton("Find Next");
		        findnext .setBounds(10,100,120,20);
		        findnext.addActionListener(new ActionListener(){
		            int counter = 0;
		            public void actionPerformed(ActionEvent e) 
		            {
		                String text = textArea.getText();
		                Pattern pat = Pattern.compile(findField.getText());
		                Matcher matcher=pat.matcher(text);
		                
		                if(matcher.find(counter))
		                {
		                    textArea.setSelectionStart(matcher.start());
		                    textArea.setSelectionEnd(counter = matcher.end());
		                }
		                else
		                {
		                    status.setText("No more words found");
		                    status.repaint();
		                }
		            }
		        });
		        findDialog.add(findnext);
		        findDialog.setVisible(true);
				break;
			case "Find Next":
				String text = textArea.getText();
                Pattern pat = Pattern.compile(findField.getText());
                Matcher matcher=pat.matcher(text);
                
                if(matcher.find(counter))
                {
                    textArea.setSelectionStart(matcher.start());
                    textArea.setSelectionEnd(counter = matcher.end());
                }
                else
                {
                    JOptionPane.showConfirmDialog(frame, null, "No more words found", JOptionPane.OK_OPTION);
                }
                counter++;
				break;
			case "Replace...":
				JDialog findDialogs = new JDialog(frame , "Find" , false);
		        findDialogs.setLocation(x, y);
		        findDialogs.setSize(400, 300);
		        findDialogs.setLayout(null);

		        findField = new JTextField(); 
		        findLabel = new JLabel("Find What:");
		        findLabel.setBounds(20, 20, 100, 20);
		        findField.setBounds(findLabel.getX()+100, findLabel.getY(), 150, findLabel.getHeight());
		        findDialogs.add(findLabel);
		        findDialogs.add(findField);

		        JTextField replacefields = new JTextField(); 
		        JLabel replacelabels = new JLabel("Replace With:");
		        replacelabels.setBounds(10, 60, 100, 20);
		        replacefields.setBounds(replacelabels.getX()+100, replacelabels.getY(), 150, replacelabels.getHeight());
		        findDialogs.add(replacelabels);
		        findDialogs.add(replacefields);
		        
		        status = new JLabel();
		        status.setBounds(10, 130, 200, 20);
		        findDialogs.add(status);
		        
		        findNext = new JButton("Find Next");
		        findNext .setBounds(10,100,120,20);
		        findNext.addActionListener(new ActionListener(){
		            int counter = 0;
		            public void actionPerformed(ActionEvent e) 
		            {
		                String text = textArea.getText();
		                Pattern pat = Pattern.compile(findField.getText());
		                Matcher matcher=pat.matcher(text);
		                
		                if(matcher.find(counter))
		                {
		                    textArea.setSelectionStart(matcher.start());
		                    textArea.setSelectionEnd(counter = matcher.end());
		                }
		                else
		                {
		                    status.setText("No more words found");
		                    status.repaint();
		                }
		            }
		        });
		        findDialogs.add(findNext);
		        
		        JButton replace = new JButton("Replace");
		        replace.setBounds(findNext.getX()+findNext.getWidth()+10,findNext.getY(),findNext.getWidth(),findNext.getHeight());
		        replace.addActionListener(new ActionListener(){
		            public void actionPerformed(ActionEvent e) {
		                String text = textArea.getText();
		                Pattern pattern = Pattern.compile(findField.getText());

		                Matcher match = pattern.matcher(text);
		                if(match.find())
		                {
		                    textArea.setText(match.replaceFirst(replacefields.getText()));
		                }
		                else
		                {
		                    status.setText("No more words found");
		                    status.repaint();
		                }

		            }
		        });
		        findDialogs.add(replace);
		        findDialogs.setVisible(true);
				break;
			case "Go To...":
				 String input = JOptionPane.showInputDialog(frame, "Enter Line Number", "Go To Line", JOptionPane.PLAIN_MESSAGE);
	             if(input != null)
	             {
	            	 try 
	            	 { 
	            		 textArea.setCaretPosition(textArea.getLineStartOffset(Integer.valueOf(input)-1));
	            		 } 
	            	 catch (Exception e1) {}
	             }
				break;
			case "Select All":
				textArea.selectAll();
				break;
			case "Time/Date":
				DateFormat timeFormat = new SimpleDateFormat("hh:mm a");
				DateFormat dateFormat = new SimpleDateFormat(" MM/dd/yyyy");
				
				Date date = new Date();
	            String time = timeFormat.format(date) + dateFormat.format(date);
	            
	            textArea.append(time);
				break;
			case "Word Wrap":
				if(textArea.getLineWrap())
				{
					textArea.setLineWrap(false);
				    textArea.setWrapStyleWord(false);
				    statusItem.setEnabled(true);
				}
				
				else
				{
					textArea.setLineWrap(true);
				    textArea.setWrapStyleWord(true);
				    statusItem.setEnabled(false);
				}
				break;
			case "Font...":
				fontSel = JFontChooser.showDialog(frame, font);
				font = fontSel;
				textArea.setFont(font);
				break;
			case "Set Foreground...":
				color = JColorChooser.showDialog(frame, "Foreground Color Chooser", color);
				if (color != null)
					textArea.setForeground(color);
				break;
			case "Set Background...":
				color = JColorChooser.showDialog(frame, "Background Color Chooser", color);
				if (color != null)
					textArea.setBackground(color);
				break;
			case "Status Bar":
				break;
			case "View Help":
				JDialog help = new JDialog(frame, "About", false);
				help.setSize(1500, 100);
				help.setLocation(x, y);
				
				help.setLayout(new BorderLayout());
				txtArea = new JLabel("Under File, you can:"
						+ " Save, Open, and Override files"
						+ " - Under Edit, you can:"
						+ " Cut, Copy, and Paste as well as find text in the notepad"
						+ " - Under Format, you can:"
						+ " Change the font and toggle word wrap"
						+ " - Under Help, you can:"
						+ " Learn about the developer (me!)");
				JButton okrButton = new JButton("OK");
				help.add(txtArea, BorderLayout.NORTH);
				help.add(okrButton, BorderLayout.EAST);
				okrButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						help.setVisible(false);
					}
				});
				help.setVisible(true);
				break;
			case "About JNotepad":
				JDialog about = new JDialog(frame, "About", true);
				about.setSize(300, 100);
				about.setLocation(x, y);
				
				about.setLayout(new BorderLayout());
				label = new JLabel("(c) Saul Galaviz. All Rights Reserved.");
				JButton okButton = new JButton("OK");
				about.add(label, BorderLayout.NORTH);
				about.add(okButton, BorderLayout.EAST);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						about.setVisible(false);
					}
				});
				about.setVisible(true);
				break;
		}
	}
	
	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(() -> new JNotepad());
	}
}