package test;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.event.*;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.Principal;
import java.security.acl.Group;



@SuppressWarnings("serial")
public class econGUI extends JFrame implements ListSelectionListener {
	
	ReadFile rf = null;
	static econGUI ex = null;
	static JTextArea console = new JTextArea(10,50);
	GridLayout grid = new GridLayout(3,3);
	
    public econGUI() {
        
        setTitle("Simple example");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);      
    	initUI();
     }
    
    private void initUI() {

    	JFrame frame = new JFrame("Frame");    
    	JPanel master = new JPanel(new GridLayout(3,1));
    	JPanel shiz = new JPanel();
    	shiz.setLayout(grid);
    	JPanel varPanel = new JPanel();
    	JPanel classPanel = new JPanel();
    	JPanel statPanel = new JPanel();       
        
        JPanel conPanel = new JPanel();
        JPanel buttonPanel = new JPanel(new GridLayout(1,2));
    	//GroupLayout gl = new GroupLayout(panel);
        
       
    	
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
        //panel.setLayout(gl);
        
        frame.setMinimumSize(new Dimension(100,100));      
        
        

        JButton quitButton = new JButton("Quit");
        JButton doBankrupcyButton = new JButton("DO BK");
        
        JLabel varLabel = new JLabel("Select Variables: ");        
        JLabel classLabel = new JLabel("Select Classes: ");        
        JLabel statLabel = new JLabel("Select Statistics: ");
        
        final JList varList;
        DefaultListModel  dL = new DefaultListModel();
        dL.addElement("CUSIP");
        dL.addElement("Date");
        dL.addElement("Quarter");
        dL.addElement("SIC");
        dL.addElement("PPEGT");
        varList  = new JList(dL);
        varList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        varList.setSelectedIndex(0);
        varList.addListSelectionListener(this);

        final JList classList;
        DefaultListModel dL2 = new DefaultListModel();
        dL2.addElement("Before");
        dL2.addElement("During");
        dL2.addElement("After");
        dL2.addElement("Going Concern");
        classList = new JList(dL2);
        classList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        classList.setSelectedIndex(0);
        classList.addListSelectionListener(this);
        
        final JList statList;
        DefaultListModel dL3 = new DefaultListModel();
        dL3.addElement("Average");
        dL3.addElement("Sic diff");
        statList = new JList(dL3);
        statList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        statList.setSelectedIndex(0);
        statList.addListSelectionListener(this);
        
        final JLabel varSelected = new JLabel("{ }");
        final JLabel classSelected = new JLabel("{ }");
        final JLabel statSelected = new JLabel("{ }");
        
        JScrollPane sp = new JScrollPane(console); 
        
        varList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting()) {
                  varSelected.setText("{ " + varList.getSelectedValue().toString() + " }" );
                }
            }
        });
        
        classList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting()) {
                  classSelected.setText("{ " + classList.getSelectedValue().toString() + " }" );
                }
            }
        });
        
        statList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting()) {
                  statSelected.setText("{ " + statList.getSelectedValue().toString() + " }" );
                }
            }
        });
        
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        
        doBankrupcyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
               // rf = new ReadFile();
                while(rf.getText() == ""){}
                ex.initUI();
                console.setText(rf.getText());
                // send all text from fields to handler class
                ex.setVisible(true);
               
            }
        });
        
       // console.setSize(300, 300);        
       // console.resize(500,500);
        
        frame.add(master);
        master.add(shiz);
        master.add(buttonPanel);
        master.add(conPanel);
        
       // shiz.add(varPanel);

     /*   
        shiz.add(classPanel);
        shiz.add(statPanel);
        shiz.add(buttonPanel);
        shiz.add(conPanel);
  */      
        shiz.add(varLabel);
        shiz.add(varList);
        shiz.add(varSelected);
        
        shiz.add(classLabel);
        shiz.add(classList);
        shiz.add(classSelected);
        
        shiz.add(statLabel);
        shiz.add(statList);
        shiz.add(statSelected);
        
        
        conPanel.add(sp);
        
        buttonPanel.add(doBankrupcyButton);
        buttonPanel.add(quitButton);
        
        
        //frame.setBounds(0, 0, 100, 50);
        frame.pack();
        frame.setVisible(true);
          
    }

     

     public static void main(String[] args) {
         
         EventQueue.invokeLater(new Runnable() {
             @Override
             public void run() {
            	 //console.setLineWrap( true );
            	 //console.setWrapStyleWord( true );
            	 //console.setSize(400, 400);
            	// console.setText("Output");
            	 ex = new econGUI();
            	 //ex.initUI();
                // ex.setVisible(true);
             }
         });
     }

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
