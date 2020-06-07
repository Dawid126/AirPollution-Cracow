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

public class ProgramController 
{
	
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
		
		this.gui = new GUI(this);
    	gui.showGUI();
	}
	
	public void createMap() throws SQLException
	{
		EventQueue.invokeLater(new Runnable()
		{
			@Override
            public void run()
            {
				ResultSet stationsResult;
				ResultSet stationMeasurementsResult;
				ArrayList<MeasurementContainer> stationMeasurements;
				ArrayList<ArrayList<MeasurementContainer>> measurementsPerStation = new ArrayList<>();
			    
			    String stationsQuery = "SELECT station_id, name, location_x, location_y FROM STATIONS";
				
				try 
				{
			        stationsResult = databaseProvider.execute(stationsQuery);
			        		        
			        while(stationsResult.next())
			        {
			        	stationMeasurements = new ArrayList<>();
			        	
			        	String stationMeasurementsQuery = "SELECT M.value, M.type, N.value "
					        			+ "FROM measurements M "
					        			+ "INNER JOIN norms N on N.type = M.type "
					        			+ "WHERE MONTH(M.time) = " + measurementMonth + " "
					        			+ "AND DAY(M.time) = " + measurementDay + " "
					        			+ "AND station_id = " + stationsResult.getInt(1);
			        	
			        	
			        	
			        	stationMeasurementsResult = databaseProvider.execute(stationMeasurementsQuery);
			        	
			        	while(stationMeasurementsResult.next())
			        	{
			        		MeasurementContainer measurement = new MeasurementContainer(
			        				stationMeasurementsResult.getInt(1),
			        				stationMeasurementsResult.getInt(3),
			        				stationMeasurementsResult.getString(2));
			        		stationMeasurements.add(measurement);
			        	}
			        	
			        	measurementsPerStation.add(stationMeasurements);
			        		        	
			        }
			        
			        stationsResult = databaseProvider.execute(stationsQuery);
			        
			        MapDrawer map = new MapDrawer();
					map.setQueriesResults(stationsResult, measurementsPerStation);
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
