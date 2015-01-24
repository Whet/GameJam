package game.parser.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.TreeSet;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class MainForm {
	private String[] scenarioNames = {"Shop Robbery", "s2"};
	private HashMap<String, String[]> scenarioObjects = new HashMap<String, String[]>();
	private HashMap<String, String[]> scenarioGods = new HashMap<String, String[]>();
	private JLabel object1Label, object2Label, object3Label, object4Label, god1Label, god2Label, god3Label, god4Label;
	private JTextArea storyArea;
	private JRadioButton radio11, radio21, radio31, radio41, radio12, radio22, radio32, radio42, radio13, radio23, radio33, radio43, radio14, radio24, radio34, radio44;
	private ButtonGroup godGroup1, godGroup2, godGroup3, godGroup4;
	
	public static void main(String[] args) {
		new MainForm();
	}
	
	public MainForm() {
		String[] scenario1Objects = {"Robbers","Alcohol","Clerk","Trolley"};
		String[] scenario1Gods = {"Debauchery","Butter","Fire","Motion"};
		addScenario(scenarioNames[0], scenario1Objects, scenario1Gods);
		String[] scenario2Objects = {"Bed","Piano","Chicken","Window"};
		String[] scenario2Gods = {"Cats","Ice","Confusion","Euphoria"};
		addScenario(scenarioNames[1], scenario2Objects, scenario2Gods);
		
		final JFrame mainFrame = new JFrame();
		mainFrame.setSize(300, 300);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setTitle("Divine Intervention");
		
		mainFrame.setLayout(new BorderLayout());
		
		radio11 = new JRadioButton();
		radio21 = new JRadioButton();
		radio31 = new JRadioButton();
		radio41 = new JRadioButton();
		godGroup1 = new ButtonGroup();
		godGroup1.add(radio11);
		godGroup1.add(radio21);
		godGroup1.add(radio31);
		godGroup1.add(radio41);
		radio12 = new JRadioButton();
		radio22 = new JRadioButton();
		radio32 = new JRadioButton();
		radio42 = new JRadioButton();
		godGroup2 = new ButtonGroup();
		godGroup2.add(radio12);
		godGroup2.add(radio22);
		godGroup2.add(radio32);
		godGroup2.add(radio42);
		radio13 = new JRadioButton();
		radio23 = new JRadioButton();
		radio33 = new JRadioButton();
		radio43 = new JRadioButton();
		godGroup3 = new ButtonGroup();
		godGroup3.add(radio13);
		godGroup3.add(radio23);
		godGroup3.add(radio33);
		godGroup3.add(radio43);
		radio14 = new JRadioButton();
		radio24 = new JRadioButton();
		radio34 = new JRadioButton();
		radio44 = new JRadioButton();
		godGroup4 = new ButtonGroup();
		godGroup4.add(radio14);
		godGroup4.add(radio24);
		godGroup4.add(radio34);
		godGroup4.add(radio44);
		object1Label = new JLabel(scenarioObjects.get(scenarioNames[0])[0]);
		object2Label = new JLabel(scenarioObjects.get(scenarioNames[0])[1]);
		object3Label = new JLabel(scenarioObjects.get(scenarioNames[0])[2]);
		object4Label = new JLabel(scenarioObjects.get(scenarioNames[0])[3]);
		god1Label = new JLabel(scenarioGods.get(scenarioNames[0])[0]);
		god2Label = new JLabel(scenarioGods.get(scenarioNames[0])[1]);
		god3Label = new JLabel(scenarioGods.get(scenarioNames[0])[2]);
		god4Label = new JLabel(scenarioGods.get(scenarioNames[0])[3]);
		
		final JComboBox scenarioCombo = new JComboBox(scenarioNames);
		scenarioCombo.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        updateLabels(scenarioCombo.getSelectedIndex());
		        mainFrame.repaint();
		    }
		});
		
		JLabel storyLabel = new JLabel("Scenario Solution:");
		storyArea = new JTextArea();
//		storyArea.getDocument().putProperty("filterNewlines", Boolean.TRUE);
		JButton addButton = new JButton("Add to file");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String pairs = "";
				if(radio11.isSelected()) pairs += "1,1,";
				if(radio21.isSelected()) pairs += "2,1,";
				if(radio31.isSelected()) pairs += "3,1,";
				if(radio41.isSelected()) pairs += "4,1,";
				if(radio12.isSelected()) pairs += "1,2,";
				if(radio22.isSelected()) pairs += "2,2,";
				if(radio32.isSelected()) pairs += "3,2,";
				if(radio42.isSelected()) pairs += "4,2,";
				if(radio13.isSelected()) pairs += "1,3,";
				if(radio23.isSelected()) pairs += "2,3,";
				if(radio33.isSelected()) pairs += "3,3,";
				if(radio43.isSelected()) pairs += "4,3,";
				if(radio14.isSelected()) pairs += "1,4,";
				if(radio24.isSelected()) pairs += "2,4,";
				if(radio34.isSelected()) pairs += "3,4,";
				if(radio44.isSelected()) pairs += "4,4,";
				System.out.println(pairs);
				
				int group1Selection = 0;
				for (Enumeration<AbstractButton> buttons = godGroup1.getElements(); buttons.hasMoreElements();) {
					AbstractButton button = buttons.nextElement();
		            if (button.isSelected()) {
		                break;
		            }
		            group1Selection++;
				}
				int group2Selection = 0;
				for (Enumeration<AbstractButton> buttons = godGroup2.getElements(); buttons.hasMoreElements();) {
					AbstractButton button = buttons.nextElement();
		            if (button.isSelected()) {
		                break;
		            }
		            group2Selection++;
				}
				int group3Selection = 0;
				for (Enumeration<AbstractButton> buttons = godGroup3.getElements(); buttons.hasMoreElements();) {
					AbstractButton button = buttons.nextElement();
		            if (button.isSelected()) {
		                break;
		            }
		            group3Selection++;
				}
				int group4Selection = 0;
				for (Enumeration<AbstractButton> buttons = godGroup4.getElements(); buttons.hasMoreElements();) {
					AbstractButton button = buttons.nextElement();
		            if (button.isSelected()) {
		                break;
		            }
		            group4Selection++;
				}
				
				TreeSet<Integer> intTree = new TreeSet<Integer>();
				intTree.add(new Integer(group1Selection));
				intTree.add(new Integer(group2Selection));
				intTree.add(new Integer(group3Selection));
				intTree.add(new Integer(group4Selection));
				if(intTree.size() != 4) {
					JOptionPane.showMessageDialog(mainFrame, "Please select 4 unique god/object combinations.","Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if(pairs.split(",").length != 8) {
					JOptionPane.showMessageDialog(mainFrame, "Please select 4 god/object combinations.","Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				pairs = pairs.substring(0, pairs.length()-1);
				
				String story = storyArea.getText().replaceAll("\n", "@c");
				
				PrintWriter writer;
				try {
					File file = new File("solutions.txt");
					if(!file.exists()) {
						file.createNewFile();
					} else {
						System.out.println("This file exists");
					}
					writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
					writer.println(scenarioNames[scenarioCombo.getSelectedIndex()] + ";" + pairs + ";" + story);
					writer.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				clear();
			}
		});
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clear();
			}
		});
		
		JPanel radioButtonPanel = new JPanel();
		radioButtonPanel.setLayout(new GridLayout(5, 5));
		radioButtonPanel.add(new JLabel());
		radioButtonPanel.add(object1Label);
		radioButtonPanel.add(object2Label);
		radioButtonPanel.add(object3Label);
		radioButtonPanel.add(object4Label);
		radioButtonPanel.add(god1Label);
		radioButtonPanel.add(radio11);
		radioButtonPanel.add(radio21);
		radioButtonPanel.add(radio31);
		radioButtonPanel.add(radio41);
		radioButtonPanel.add(god2Label);
		radioButtonPanel.add(radio12);
		radioButtonPanel.add(radio22);
		radioButtonPanel.add(radio32);
		radioButtonPanel.add(radio42);
		radioButtonPanel.add(god3Label);
		radioButtonPanel.add(radio13);
		radioButtonPanel.add(radio23);
		radioButtonPanel.add(radio33);
		radioButtonPanel.add(radio43);
		radioButtonPanel.add(god4Label);
		radioButtonPanel.add(radio14);
		radioButtonPanel.add(radio24);
		radioButtonPanel.add(radio34);
		radioButtonPanel.add(radio44);
		
		mainFrame.add(scenarioCombo,BorderLayout.NORTH);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(clearButton);
		buttonPanel.add(addButton);
		mainFrame.add(buttonPanel, BorderLayout.SOUTH);
		
		JPanel centrePanel = new JPanel();
		centrePanel.setLayout(new BoxLayout(centrePanel, BoxLayout.Y_AXIS));
		centrePanel.add(radioButtonPanel);
		JPanel storyPanel = new JPanel();
		storyPanel.setLayout(new BorderLayout());
		storyPanel.add(storyLabel, BorderLayout.NORTH);
		storyPanel.add(storyArea, BorderLayout.CENTER);
		centrePanel.add(storyPanel);
		mainFrame.add(centrePanel, BorderLayout.CENTER);
		
		mainFrame.setVisible(true);
	}
	
	public void addScenario(String scenarioName, String[] objects, String[] gods) {
		scenarioObjects.put(scenarioName, objects);
		scenarioGods.put(scenarioName, gods);
	}
	
	private void updateLabels(int index) {
		object1Label.setText(scenarioObjects.get(scenarioNames[index])[0]);
		object2Label.setText(scenarioObjects.get(scenarioNames[index])[1]);
		object3Label.setText(scenarioObjects.get(scenarioNames[index])[2]);
		object4Label.setText(scenarioObjects.get(scenarioNames[index])[3]);
		god1Label.setText(scenarioGods.get(scenarioNames[index])[0]);
		god2Label.setText(scenarioGods.get(scenarioNames[index])[1]);
		god3Label.setText(scenarioGods.get(scenarioNames[index])[2]);
		god4Label.setText(scenarioGods.get(scenarioNames[index])[3]);
	}
	
	private void clear() {
		storyArea.setText("");
		godGroup1.clearSelection();
		godGroup2.clearSelection();
		godGroup3.clearSelection();
		godGroup4.clearSelection();
	}
}
