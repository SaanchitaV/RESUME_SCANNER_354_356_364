package pdf;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Font;

public class PDFgui {

	private JFrame frame;
	private JButton btnScan;
	private JTextField showResult;
	private JTextField pdfPath;
	private JTextField querySkill;
	private JTextField Email1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PDFgui window = new PDFgui();
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
	public PDFgui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 572, 398);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//This is the scan button
		btnScan = new JButton("scan & store");
		//Read PDF from file path and store into database
		btnScan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filePath;
				String[] result;
				ReadingText reader = new ReadingText();
				try {
					//Read PDF, output result of ReadingText reader
					filePath = pdfPath.getText();
					result = reader.read(filePath);
					//Show result in showResult window
					String scanShow = "";
					for (int i = 1; i< result.length; i++) {
						scanShow = result[i]+ ";  "+ scanShow;
					}
					showResult.setText(scanShow);
					//Store into skill database;
					PostgreSQLJDBC dblink = new PostgreSQLJDBC();
					for (int i = 1; i< result.length; i++) {
						dblink.insertSkill(result[0],result[i]); //execute storage through ProstgreSQLJDBC class
					}
					Email1.setText(result[0]);
					
					JOptionPane.showMessageDialog(null,"email and skills successully stored");
				}catch(Exception error) {
					JOptionPane.showMessageDialog(null,"PDF file not found");
				}
			}
		});
		btnScan.setBounds(419, 76, 107, 29);
		frame.getContentPane().add(btnScan);		
		showResult = new JTextField();
		showResult.setEditable(false);
		showResult.setBounds(172, 114, 239, 21);
		frame.getContentPane().add(showResult);
		showResult.setColumns(10);
		
		pdfPath = new JTextField();
		pdfPath.setBounds(172, 76, 239, 26);
		frame.getContentPane().add(pdfPath);
		pdfPath.setColumns(10);
		
		JLabel lblPdfPath = new JLabel("Input PDF path:");
		lblPdfPath.setBounds(56, 79, 97, 21);
		frame.getContentPane().add(lblPdfPath);
		
		JLabel lblSkillPresentedIn = new JLabel("Skill presented:");
		lblSkillPresentedIn.setBounds(56, 117, 107, 16);
		frame.getContentPane().add(lblSkillPresentedIn);
		
		JLabel lblSkillScanner = new JLabel("Query section");
		lblSkillScanner.setBounds(244, 213, 97, 16);
		frame.getContentPane().add(lblSkillScanner);
		
		JTextArea emailWindow = new JTextArea();
		emailWindow.setEditable(false);
		emailWindow.setBounds(172, 273, 239, 80);
		frame.getContentPane().add(emailWindow);
		
		//This is the search button
		JButton btnSearch = new JButton("search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//GetText from querySkill box, search database, return emailList
				String skillToSearch = querySkill.getText();
				PostgreSQLJDBC dblink = new PostgreSQLJDBC();
				//String[] emailList = dblink.skillQuery(skillToSearch);
				String[] emailList = dblink.multipleSkillQuery(skillToSearch);
				String emailShow = "";
				for (int i = 0; i < emailList.length; i++) {
					emailShow = emailList[i] + ";   " + emailShow;
				}
				emailWindow.setLineWrap(true);
				emailWindow.setWrapStyleWord(true);
				emailWindow.setText(emailShow);
			}
		});
		btnSearch.setBounds(419, 235, 107, 29);
		frame.getContentPane().add(btnSearch);
		
		JLabel lblQuerySkill = new JLabel("Query skills:");
		lblQuerySkill.setBounds(82, 240, 81, 16);
		frame.getContentPane().add(lblQuerySkill);
		
		querySkill = new JTextField();
		querySkill.setBounds(168, 235, 245, 26);
		frame.getContentPane().add(querySkill);
		querySkill.setColumns(10);
		
		JLabel lblEmailOfSkill = new JLabel("E-mails of skills owners:");
		lblEmailOfSkill.setBounds(6, 273, 163, 16);
		frame.getContentPane().add(lblEmailOfSkill);
		
		JLabel lblEamil = new JLabel("E-mail:");
		lblEamil.setBounds(106, 152, 47, 16);
		frame.getContentPane().add(lblEamil);
		
		Email1 = new JTextField();
		Email1.setEditable(false);
		Email1.setBounds(172, 147, 239, 26);
		frame.getContentPane().add(Email1);
		Email1.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("________________________________________________________________________________");
		lblNewLabel.setBounds(6, 180, 560, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblScanSection = new JLabel("Scan section");
		lblScanSection.setBounds(244, 48, 86, 16);
		frame.getContentPane().add(lblScanSection);
		
		JLabel lblSkillScanAnd = new JLabel("Skill Scan and Query System");
		lblSkillScanAnd.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblSkillScanAnd.setBounds(172, 16, 225, 16);
		frame.getContentPane().add(lblSkillScanAnd);
	}
}
