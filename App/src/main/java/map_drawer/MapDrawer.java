package map_drawer;

import java.sql.ResultSet;
import java.sql.SQLException;
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
	UnfoldingMap map;
	ZoomSlider slider;
	BarScaleUI barScale;
	
	EventDispatcher eventDispatcher;
	float maxPanningDistance = 15;
	Location cityLocation = new Location(50.034f, 19.94f);
	
	ResultSet stationsAndMeasurementsResult;
	ResultSet normsResult;
	
	PFont myFont;

	public void setQueriesResults(ResultSet stationsAndMeasurementsResult, ResultSet normsResult)
	{
		this.stationsAndMeasurementsResult = stationsAndMeasurementsResult;
		this.normsResult = normsResult;
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
			addMarkesToMap(stationsAndMeasurementsResult, normsResult);
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

	private void addMarkesToMap(ResultSet stationsAndMeasurementsResults, ResultSet normsResult) throws SQLException 
	{
		Hashtable<String, Integer> norms = new Hashtable<String, Integer>();
		while(normsResult.next())
		{
			norms.put(normsResult.getString(2), normsResult.getInt(1));
		}
		
		while(stationsAndMeasurementsResults.next())
		{
			Location stationLocation = new Location(stationsAndMeasurementsResults.getFloat(2)
					, stationsAndMeasurementsResults.getFloat(3));
			
			LabeledMarker marker = new LabeledMarker(stationLocation
					, stationsAndMeasurementsResults.getString(1)
					, stationsAndMeasurementsResults.getInt(4)
					, stationsAndMeasurementsResults.getInt(5)
					, (int) norms.get("PM10")
					, (int) norms.get("PM2,5")
					, myFont);
			
			map.addMarkers(marker);
		}
	}
	
	public static void main(String[] args) 
	{
	    PApplet.main(new String[] { MapDrawer.class.getName() });
	}

}
