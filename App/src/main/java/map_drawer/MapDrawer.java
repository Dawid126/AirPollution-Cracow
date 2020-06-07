package map_drawer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import processing.core.PApplet;
import processing.core.PFont;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.events.EventDispatcher;
import de.fhpotsdam.unfolding.events.MapEvent;
import de.fhpotsdam.unfolding.events.PanMapEvent;
import de.fhpotsdam.unfolding.events.ZoomMapEvent;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.interactions.MouseHandler;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.ui.BarScaleUI;

public class MapDrawer extends PApplet 
{
	private UnfoldingMap map;
	private ZoomSlider slider;
	private BarScaleUI barScale;
	
	private EventDispatcher eventDispatcher;
	float maxPanningDistance = 15;
	private Location cityLocation = new Location(50.034f, 19.94f);
	
	private ResultSet stations;
	private ArrayList<ArrayList<MeasurementContainer>> measurementsPerStation;
	
	private PFont myFont;

	public void setQueriesResults(ResultSet stations, ArrayList<ArrayList<MeasurementContainer>> measurementsPerStation)
	{
		this.stations = stations;
		this.measurementsPerStation = measurementsPerStation;
	}

	public void setup()
	{
		size(900, 700, OPENGL);
		
		
		map = new UnfoldingMap(this, new Microsoft.RoadProvider());
		map.zoomAndPanTo(11, cityLocation);
		map.setPanningRestriction(cityLocation, maxPanningDistance);
		map.setZoomRange(11, 14);
		
		eventDispatcher = new EventDispatcher();
		MouseHandler mouseHandler = new MouseHandler(this, map);
		eventDispatcher.addBroadcaster(mouseHandler);
		listen();

		slider = new ZoomSlider(this, map, 50, 30);
		
		barScale = new BarScaleUI(this, map, 820, 36);
		myFont = createFont("OpenSans", 16);
		barScale.setStyle(color(0, 250), 6, -2, myFont);
		
		try 
		{
			addMarkesToMap();
		} 
		catch (SQLException e) 
		{
			System.out.println("SQL exception" + e);
			System.exit(1);
		}
	}
	
	
	public void listen() 
	{
		eventDispatcher.register(map, PanMapEvent.TYPE_PAN, map.getId());
		eventDispatcher.register(map, ZoomMapEvent.TYPE_ZOOM, map.getId());
	}

	public void mute() 
	{
		eventDispatcher.unregister(map, PanMapEvent.TYPE_PAN, map.getId());
		eventDispatcher.unregister(map, ZoomMapEvent.TYPE_ZOOM, map.getId());
	}

	public void draw() {
		map.draw();

		slider.draw();
		barScale.draw();
	}

	public void mapChanged(MapEvent mapEvent) 
	{
		slider.setZoomLevel(map.getZoomLevel());
	}

	public void mousePressed() 
	{
		if (slider.contains(mouseX, mouseY)) 
		{
			slider.startDrag(mouseX, mouseY);
			mute(); 
		}
	}

	public void mouseDragged() 
	{
		if (slider.isDragging()) 
		{
			slider.drag(mouseX, mouseY);
		}
	}

	public void mouseReleased() 
	{
		if (slider.isDragging()) 
		{
			slider.endDrag();
			listen(); 
		}
	}
	
	public void mouseMoved() 
	{

		for (Marker marker : map.getMarkers()) 
		{
			marker.setSelected(false);
		}

		Marker marker = map.getFirstHitMarker(mouseX, mouseY);
		if (marker != null) 
		{
			marker.setSelected(true);
		}
	}

	private void addMarkesToMap() throws SQLException 
	{
		int counter = 0;
		while(stations.next())
		{
			Location stationLocation = new Location(stations.getFloat(3)
					, stations.getFloat(4));
			 
			ArrayList<MeasurementContainer> stationMeasurements = measurementsPerStation.get(counter);
			String stationName = stations.getString(2);
			
			LabeledMarker marker = new LabeledMarker(
					  stationLocation
					, stationName
					, stationMeasurements
					, myFont);
				
			map.addMarkers(marker);	
			counter++;
		}
	}
	
	public static void main(String[] args) 
	{
	    PApplet.main(new String[] { MapDrawer.class.getName() });
	}

}
