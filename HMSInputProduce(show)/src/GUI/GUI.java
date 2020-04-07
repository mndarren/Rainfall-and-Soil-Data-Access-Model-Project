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
		frame.setBounds(200, 200, 1250, 1000);
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
		getDataButton.setFont(new Font("Tahoma", Font.BOLD, 22));
		getDataButton.setBounds(443, 296, 337, 67);
		frame.getContentPane().add(getDataButton);
		
		JLabel lblNewLabel = new JLabel("Rainfall Data");
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 27));
		lblNewLabel.setBounds(169, 396, 260, 46);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblSoilData = new JLabel("Soil Data");
		lblSoilData.setFont(new Font("Trebuchet MS", Font.PLAIN, 27));
		lblSoilData.setBounds(765, 386, 190, 67);
		frame.getContentPane().add(lblSoilData);
		
		rainfallOutputField = new JTextField();
		rainfallOutputField.setFont(new Font("Trebuchet MS", Font.PLAIN, 27));
		rainfallOutputField.setBounds(142, 542, 435, 67);
		frame.getContentPane().add(rainfallOutputField);
		rainfallOutputField.setColumns(10);
		
		soilOutputField = new JTextField();
		soilOutputField.setFont(new Font("Trebuchet MS", Font.PLAIN, 27));
		soilOutputField.setBounds(742, 544, 435, 63);
		frame.getContentPane().add(soilOutputField);
		soilOutputField.setColumns(10);
		
		JLabel lblhighVeryhighDepth = new JLabel("(high, veryhigh, depth)");
		lblhighVeryhighDepth.setFont(new Font("Trebuchet MS", Font.PLAIN, 27));
		lblhighVeryhighDepth.setBounds(755, 458, 401, 67);
		frame.getContentPane().add(lblhighVeryhighDepth);
		
		rainfallInputField = new JTextField();
		rainfallInputField.setFont(new Font("Tahoma", Font.PLAIN, 28));
		rainfallInputField.setBounds(144, 133, 407, 59);
		frame.getContentPane().add(rainfallInputField);
		rainfallInputField.setColumns(10);
		
		JLabel lblInputFileFor = new JLabel("Input file address for Rainfall:");
		lblInputFileFor.setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
		lblInputFileFor.setBounds(144, 86, 395, 36);
		frame.getContentPane().add(lblInputFileFor);
		
		JLabel lblInputFileAddress = new JLabel("Input file address for Soil Data:");
		lblInputFileAddress.setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
		lblInputFileAddress.setBounds(804, 86, 354, 36);
		frame.getContentPane().add(lblInputFileAddress);
		
		soilInputField = new JTextField();
		soilInputField.setFont(new Font("Tahoma", Font.PLAIN, 28));
		soilInputField.setBounds(799, 133, 359, 59);
		frame.getContentPane().add(soilInputField);
		soilInputField.setColumns(10);
		
		rainfallInputField.setText(Utility.defaultRainFilePath);
		soilInputField.setText(Utility.defaultSoilFilePath);
	}
}
