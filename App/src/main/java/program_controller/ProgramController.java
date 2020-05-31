package program_controller;

import java.awt.EventQueue;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import database_provider.*;
import gui.*;
import map_drawer.*;

public class ProgramController {
	
	private int measurementDay;
	private int measurementMonth;
	
	private GUI gui;
	private ArrayList<Integer> avaiableMonths;
	
	private DatabaseProvider databaseProvider;
	
	
	public ProgramController(ArrayList<Integer> avaiableMonths) throws SQLException
	{
		this.avaiableMonths = new ArrayList<Integer>();
		this.avaiableMonths.addAll(avaiableMonths);
		
		try
		{
			this.databaseProvider = new DatabaseProvider();	
		}
		catch (SQLException e)
		{
			System.out.println("SQL exception" + e);
			System.exit(1);
		}
	}
	
	public void createMap() throws SQLException
	{
		EventQueue.invokeLater(new Runnable()
		{
			@Override
            public void run()
            {
				ResultSet stationsAndMeasurementsResult;
			    ResultSet normsResult;
			    
				String stationsAndMeasurementsQuery = "SELECT name, location_x, location_y,"
		        		+ "(SELECT value from measurements M WHERE M.station_id = S.station_id"
		        		+ " AND M.type = 'PM10' AND MONTH(M.time) = " + measurementMonth
		        		+ " AND DAY(M.time) = " + measurementDay + ") as PM10,"
		        		+ ""
		        		+ " (SELECT value from measurements M WHERE M.station_id = S.station_id"
		        		+ " AND M.type = 'PM2,5' AND MONTH(M.time) = " + measurementMonth
		        		+ " AND DAY(M.time) = " + measurementDay + ") as PM2_5"
		        		+ " FROM stations S";
				
				String normsQuery = "SELECT value, type from norms";
				
				try 
				{
			        stationsAndMeasurementsResult = databaseProvider.execute(stationsAndMeasurementsQuery);
			        normsResult = databaseProvider.execute(normsQuery);
			        MapDrawer map = new MapDrawer();
					map.setQueriesResults(stationsAndMeasurementsResult, normsResult);
					map.init();	
					
					JFrame frame = new JFrame("Kraków - mapa stacji pomiarowych");
					JPanel panel = new JPanel();
				    panel.add(map);
				    
				    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				    frame.add(panel);
				    frame.setSize(900, 700);
				    frame.setResizable(false);
				    frame.setLocationRelativeTo(null);
				    frame.setVisible(true);
			        	
				}
				catch (SQLException e)
				{
					System.out.println("SQL exception" + e);
					System.exit(1);
				}
            }
		});	
	}
	
	public ArrayList<Integer> getAvaiableMonths()
	{
		return avaiableMonths;
	}
	
	public void setMeasurementDay(int day)
	{
		this.measurementDay = day;
	}
	
	public void setMeasurementMonth(int month)
	{
		this.measurementMonth = month;
	}
}
