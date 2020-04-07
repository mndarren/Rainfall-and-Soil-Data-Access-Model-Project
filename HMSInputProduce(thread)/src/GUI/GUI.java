package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import precipitationFrequencyGet.*;
import soilInfoGet.SoilInfoGet;

public class GUI {
	private JFrame frame;
	private JTextField rainfallOutputField;
	private JTextField soilOutputField;
	private JTextField rainfallInputField;
	private JTextField soilInputField;
	private static PFGet pfGet;
	private static SoilInfoGet soilInfoGet;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					soilInfoGet = SoilInfoGet.getInstance();
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 650, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		pfGet = new PFGet();
		
		JButton getDataButton = new JButton("Get Rain&Soil Data");
		getDataButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String rainInput, soilInput, rainOutput, soilOutput;
				try {
					rainInput = rainfallInputField.getText();
					soilInput = soilInputField.getText();
					pfGet.setFilePath(rainInput);
					pfGet.processInput();
					rainOutput = pfGet.getResult();
					rainfallOutputField.setText(rainOutput);
					
					soilInfoGet.setFilePath(soilInput);
			        soilOutput = soilInfoGet.getFinalSoilInfo().toString();
			        soilOutputField.setText(soilOutput);
				} catch (Exception e) {
					e.printStackTrace();
					String message = "Input file is wrong!";
			        JOptionPane.showMessageDialog(null, message);
				}
			}
		});
		getDataButton.setForeground(new Color(0, 0, 204));
		getDataButton.setBackground(new Color(255, 255, 204));
		getDataButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		getDataButton.setBounds(216, 238, 195, 36);
		frame.getContentPane().add(getDataButton);
		
		JLabel lblNewLabel = new JLabel("Rainfall Data");
		lblNewLabel.setBounds(97, 284, 93, 25);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblSoilData = new JLabel("Soil Data");
		lblSoilData.setBounds(418, 284, 85, 25);
		frame.getContentPane().add(lblSoilData);
		
		rainfallOutputField = new JTextField();
		rainfallOutputField.setBounds(81, 383, 152, 36);
		frame.getContentPane().add(rainfallOutputField);
		rainfallOutputField.setColumns(10);
		
		soilOutputField = new JTextField();
		soilOutputField.setBounds(378, 383, 162, 36);
		frame.getContentPane().add(soilOutputField);
		soilOutputField.setColumns(10);
		
		JLabel lblhighVeryhighDepth = new JLabel("(high, veryhigh, depth)");
		lblhighVeryhighDepth.setBounds(388, 359, 152, 25);
		frame.getContentPane().add(lblhighVeryhighDepth);
		
		rainfallInputField = new JTextField();
		rainfallInputField.setBounds(26, 109, 241, 39);
		frame.getContentPane().add(rainfallInputField);
		rainfallInputField.setColumns(10);
		
		JLabel lblInputFileFor = new JLabel("Input file address for Rainfall:");
		lblInputFileFor.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblInputFileFor.setBounds(26, 86, 195, 25);
		frame.getContentPane().add(lblInputFileFor);
		
		JLabel lblInputFileAddress = new JLabel("Input file address for Soil Data:");
		lblInputFileAddress.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblInputFileAddress.setBounds(354, 86, 207, 25);
		frame.getContentPane().add(lblInputFileAddress);
		
		soilInputField = new JTextField();
		soilInputField.setBounds(354, 112, 241, 36);
		frame.getContentPane().add(soilInputField);
		soilInputField.setColumns(10);
		
		rainfallInputField.setText(Utility.defaultRainFilePath);
		soilInputField.setText(Utility.defaultSoilFilePath);
	}
}
