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
import java.util.ArrayList;
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
	private String[] scenarioNames = {"Shop Robbery", "s2", "Risky scenario"};
	private HashMap<String, String[]> scenarioObjects = new HashMap<String, String[]>();
	private HashMap<String, String[]> scenarioGods = new HashMap<String, String[]>();
	private JLabel object1Label, object2Label, object3Label, object4Label;
	
	private JTextArea storyArea;
	
	private JComboBox scenarioCombo;
	
	private ArrayList<JLabel> godLabels;
	private ArrayList<ButtonGroup> godGroups;
	
	private JPanel radioButtonPanel;
	
	private int currentSelectedIndex;
	
	public static void main(String[] args) {
		new MainForm();
	}
	
	public MainForm() {
		radioButtonPanel = new JPanel();
		
		String[] scenario1Objects = {"Robbers","Alcohol","Clerk","Trolley"};
		String[] scenario1Gods = {"Debauchery","Butter","Fire","Motion"};
		addScenario(scenarioNames[0], scenario1Objects, scenario1Gods);
		String[] scenario2Objects = {"Bed","Piano","Chicken","Window"};
		String[] scenario2Gods = {"Cats","Ice","Confusion","Euphoria"};
		addScenario(scenarioNames[1], scenario2Objects, scenario2Gods);
		String[] scenario3Objects = {"Bacon","Pliers","Lamppost","Giraffe"};
		String[] scenario3Gods = {"Tits","Wine"};
		addScenario(scenarioNames[2], scenario3Objects, scenario3Gods);
		
		final JFrame mainFrame = new JFrame();
		mainFrame.setSize(300, 300);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setTitle("Divine Intervention");
		
		mainFrame.setLayout(new BorderLayout());
		
		scenarioCombo = new JComboBox(scenarioNames);
		scenarioCombo.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	if(scenarioCombo.getSelectedIndex() != currentSelectedIndex){
			        updateLabels(scenarioCombo.getSelectedIndex());
			        createRadioButtons();
			        populateRadioButtonPanel();
			        mainFrame.repaint();
			        currentSelectedIndex = scenarioCombo.getSelectedIndex();
		    	}
		    }
		});
		currentSelectedIndex = scenarioCombo.getSelectedIndex();
		
		godGroups = new ArrayList<ButtonGroup>();
		godLabels = new ArrayList<JLabel>();
		
		int godCount = scenarioGods.get(scenarioNames[scenarioCombo.getSelectedIndex()]).length;
		createRadioButtons();
		
		object1Label = new JLabel(scenarioObjects.get(scenarioNames[0])[0]);
		object2Label = new JLabel(scenarioObjects.get(scenarioNames[0])[1]);
		object3Label = new JLabel(scenarioObjects.get(scenarioNames[0])[2]);
		object4Label = new JLabel(scenarioObjects.get(scenarioNames[0])[3]);
		
		for(int i=0; i<godCount; i++) {
			godLabels.add(new JLabel(scenarioGods.get(scenarioNames[scenarioCombo.getSelectedIndex()])[i]));
		}
		
		JLabel storyLabel = new JLabel("Scenario Solution:");
		storyArea = new JTextArea();
		storyArea.setLineWrap(true);
		JButton addButton = new JButton("Add to file");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String pairs = "";
				
				TreeSet<Integer> intTree = new TreeSet<Integer>();
				
				int godCount = scenarioGods.get(scenarioNames[scenarioCombo.getSelectedIndex()]).length;
				for(int i=0; i<godCount; i++) {
					int groupSelection = 1;
					for (Enumeration<AbstractButton> buttons = godGroups.get(i).getElements(); buttons.hasMoreElements();) {
						AbstractButton button = buttons.nextElement();
			            if (button.isSelected()) {
			                break;
			            }
			            groupSelection++;
					}
					pairs += groupSelection + "," + i + ",";
					intTree.add(new Integer(groupSelection));
				}
				System.out.println(pairs);
				
				if(intTree.size() != godCount) {
					JOptionPane.showMessageDialog(mainFrame, "Please select " + godCount + " unique god/object combinations.","Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if(pairs.split(",").length != (godCount * 2)) {
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
		
		populateRadioButtonPanel();
		
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
	
	public void createRadioButtons() {
		godGroups = new ArrayList<ButtonGroup>();
		int godCount = scenarioGods.get(scenarioNames[scenarioCombo.getSelectedIndex()]).length;
		for(int i=0; i<godCount; i++) {
			ButtonGroup group = new ButtonGroup();
			group.add(new JRadioButton());
			group.add(new JRadioButton());
			group.add(new JRadioButton());
			group.add(new JRadioButton());
			godGroups.add(group);
		}
	}
	
	private void populateRadioButtonPanel() {
		int godCount = scenarioGods.get(scenarioNames[scenarioCombo.getSelectedIndex()]).length;
		radioButtonPanel.removeAll();
		radioButtonPanel.setLayout(new GridLayout(godCount + 1, 5));
		radioButtonPanel.add(new JLabel());
		radioButtonPanel.add(object1Label);
		radioButtonPanel.add(object2Label);
		radioButtonPanel.add(object3Label);
		radioButtonPanel.add(object4Label);
		
		for(int i=0; i<godCount; i++) {
			radioButtonPanel.add(godLabels.get(i));
			for (Enumeration<AbstractButton> buttons = godGroups.get(i).getElements(); buttons.hasMoreElements();) {
				AbstractButton button = buttons.nextElement();
				radioButtonPanel.add(button);
			}
		}
	}
	
	private void updateLabels(int index) {
		object1Label.setText(scenarioObjects.get(scenarioNames[index])[0]);
		object2Label.setText(scenarioObjects.get(scenarioNames[index])[1]);
		object3Label.setText(scenarioObjects.get(scenarioNames[index])[2]);
		object4Label.setText(scenarioObjects.get(scenarioNames[index])[3]);
		
		int godCount = scenarioGods.get(scenarioNames[scenarioCombo.getSelectedIndex()]).length;
		for(int i=0; i< godCount; i++) {
			godLabels.get(i).setText(scenarioGods.get(scenarioNames[index])[i]);
		}
	}
	
	private void clear() {
		storyArea.setText("");
		for(ButtonGroup bg : godGroups) {
			bg.clearSelection();
		}
	}
}
