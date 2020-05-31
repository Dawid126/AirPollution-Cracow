package gui;

import java.awt.event.*;
import java.awt.*;

import java.io.File;

import java.sql.SQLException;

import java.util.List;
 
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import program_controller.*;

public class GUI{
	
	private JFrame frame;
	private JList<Integer> days;
	private JList<Integer> months;
	
	private ProgramController programController;
	
	DefaultListModel<Integer> daysListModel;
    DefaultListModel<Integer> monthsListModel;
    
	JButton showMapButton;
	
	
	private boolean isDateAvaiable(int month)
	{
		return programController.getAvaiableMonths().contains(month);
	}
	
	private boolean checkDateCorectness(int month, int day)
	{
		if(isDateAvaiable(month))
		{
			if(month == 2)
			{
				return day < 29;
			}
			else if(month % 2 == 0)
			{
				return day < 31;
			}
			else return true;
		}
		else return false;
	}
		
	public GUI(ProgramController programController) 
	{	
		this.programController = programController;
		
		DefaultListModel<Integer> daysListModel = new DefaultListModel<>();
	    DefaultListModel<Integer> monthsListModel = new DefaultListModel<>();
	    for(int i=1; i<=31; i++)
	    {
	    	daysListModel.addElement(i);
	    	if(i<=12)
	    	{
	    		monthsListModel.addElement(i);
	    	}
	    }
	    
	    days = new JList<>(daysListModel);
	    months = new JList<>(monthsListModel);
	}
	
	public void showGUI() throws SQLException
	{
		this.frame = new JFrame();
		this.showMapButton = new JButton("Poka¿ mapê");
		String imagePath = new File("").getAbsolutePath();
        imagePath = imagePath.concat("\\src\\main\\resources\\factory-chimneys-smoke.jpg");
        Image img = Toolkit.getDefaultToolkit().getImage(imagePath);
	    
	    days.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    months.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    
	    days.addListSelectionListener(new ListSelectionListener() 
	    {
            @Override
            public void valueChanged(ListSelectionEvent e) 
            {
                if (!e.getValueIsAdjusting()) 
                {
                    List<Integer> selectedDayList = days.getSelectedValuesList();
                    List<Integer> selectedMonthList = months.getSelectedValuesList();
                    if(!selectedMonthList.isEmpty() && checkDateCorectness(selectedMonthList.get(0), selectedDayList.get(0)))
                    {
                    	showMapButton.setEnabled(true);
                    }
                    else
                    {
                    	showMapButton.setEnabled(false);
                    }
                }  
            }
        });
	    
	    months.addListSelectionListener(new ListSelectionListener() 
	    {
            @Override
            public void valueChanged(ListSelectionEvent e) 
            {
                if (!e.getValueIsAdjusting()) 
                {
                	List<Integer> selectedDayList = days.getSelectedValuesList();
                    List<Integer> selectedMonthList = months.getSelectedValuesList();
                    if(!selectedDayList.isEmpty() && checkDateCorectness(selectedMonthList.get(0), selectedDayList.get(0)))
                    {
                    	showMapButton.setEnabled(true);
                    }
                    else
                    {
                    	showMapButton.setEnabled(false);
                    }
                }   
            }
        });
	    
	    JScrollPane daysScrollPane = new JScrollPane(days);
	    JScrollPane monthsScrollPane = new JScrollPane(months);
	    
	    JLabel label1 = new JLabel("Wybierz dzieñ i miesi¹c z listy, aby wyœwietliæ mapê", SwingConstants.CENTER);
	    JLabel label2 = new JLabel("z informacj¹ o jakoœci powietrza w danym dniu.", SwingConstants.CENTER);
	    JLabel label3 = new JLabel("Dostêpne dni: od 01-05 do 30-06-2020", SwingConstants.CENTER);
	    JLabel label4 = new JLabel("Miesi¹c", SwingConstants.CENTER);
	    JLabel label5 = new JLabel("Dzieñ", SwingConstants.CENTER);
	    label1.setOpaque(true);
	    label2.setOpaque(true);
	    label3.setOpaque(true);
	    label4.setOpaque(true);
	    label5.setOpaque(true);
	    label1.setBackground(Color.lightGray);
	    label2.setBackground(Color.lightGray);
	    label3.setBackground(Color.lightGray);
	    label4.setBackground(Color.lightGray);
	    label5.setBackground(Color.lightGray);
        label1.setPreferredSize(new Dimension(400, 30));
        label2.setPreferredSize(new Dimension(400, 30));
        label3.setPreferredSize(new Dimension(350, 30));
        label4.setPreferredSize(new Dimension(60, 19));
        label5.setPreferredSize(new Dimension(60, 19));
        label1.setFont(new Font("Verdana", Font.PLAIN, 15));
        label2.setFont(new Font("Verdana", Font.PLAIN, 15));
        label3.setFont(new Font("Verdana", Font.PLAIN, 15));
        label4.setFont(new Font("Verdana", Font.PLAIN, 15));
        label5.setFont(new Font("Verdana", Font.PLAIN, 15));
        Dimension size = label1.getPreferredSize();
        label1.setBounds(12, 35, size.width, size.height);
        label2.setBounds(12, 35 + size.height, size.width, size.height);
        label3.setBounds(37, 50 + 2 * size.height, size.width - 50, size.height);
        
        
        daysScrollPane.setPreferredSize(new Dimension(60, 80));
	    monthsScrollPane.setPreferredSize(new Dimension(60, 80));
	    Dimension scrollPaneSize = daysScrollPane.getPreferredSize();
	    monthsScrollPane.setBounds(142, 65 + 3 * size.height, scrollPaneSize.width, scrollPaneSize.height);
	    daysScrollPane.setBounds(222, 65 + 3 * size.height, scrollPaneSize.width, scrollPaneSize.height);
	    
	    label4.setBounds(142, 150 + 3 * size.height, scrollPaneSize.width, 19);
        label5.setBounds(222, 150 + 3 * size.height, scrollPaneSize.width, 19);
        
        showMapButton.setPreferredSize(new Dimension(110, 24));
        Dimension buttonSize = showMapButton.getPreferredSize();
		showMapButton.setBounds(157, 175 + 3 * size.height, buttonSize.width, buttonSize.height);
		showMapButton.setEnabled(false);
		
		showMapButton.addActionListener(new ActionListener() 
		{		
			@Override
			public void actionPerformed(ActionEvent event) 
			{
				programController.setMeasurementDay(days.getSelectedValuesList().get(0));
				programController.setMeasurementMonth(months.getSelectedValuesList().get(0));
				try 
				{	
					programController.createMap();
				} 
				catch (SQLException e) 
				{
					System.out.println("SQL exception" + e);
					System.exit(1);
				}		
			}	
		});

	    
	    frame.setContentPane(new JPanel() 
		{
		     @Override
		     public void paintComponent(Graphics g) 
		     {
		        super.paintComponent(g);
		        g.drawImage(img, 0, 0, this);
		        this.setLayout(null);
		        this.setSize(440, 500);
		        this.add(label1);
		        this.add(label2);
		        this.add(label3);
		        this.add(label4);
		        this.add(label5);
		        this.add(daysScrollPane);
		        this.add(monthsScrollPane);
		        this.add(showMapButton);
		     }
		});
	    
  		frame.pack();
  		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setTitle("Kraków - jakoœæ powietrza");
	    frame.setResizable(false);
	    
	    Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
  		frame.setLocation((int) (screenDimension.getWidth() / 2 - 220), 
  				(int) (screenDimension.getHeight() /2 - 250));
	    frame.setVisible(true);
  		frame.setSize(440, 500);
	}
}
