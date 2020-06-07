package main;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.File;
import java.net.URL;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
//import org.json.simple.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import gui.*;
import program_controller.ProgramController;

public class Main 
{

    public static void main(String[] args) throws SQLException
    {

    	ArrayList<Integer> avaiableMonths = loadParameters();
    	ProgramController controller = new ProgramController(avaiableMonths);	
    }
    
    public static ArrayList<Integer> loadParameters()
    {
    	ArrayList<Integer> avaiableMonths = null;
    	
        Class Main = Main.class;
        JSONParser jsonParser = new JSONParser();
        String parametersPath = new File("").getAbsolutePath();
        parametersPath = parametersPath.concat("\\src\\main\\java\\main\\parameters.json");

        try 
        {
            FileReader fileReader = new FileReader(parametersPath);
            JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);
                  
            JSONArray avaiableMonthsJSONArray = (JSONArray) jsonObject.get("avaiableMonths");
            if(avaiableMonthsJSONArray == null)
            {
            	System.out.println("Loading array error");
            	System.exit(1);
            }
                        
            Iterator<Integer> iterator = avaiableMonthsJSONArray.iterator();
            
            avaiableMonths = new ArrayList<>();
            while (iterator.hasNext()) 
            {
            	int month = ((Number) iterator.next()).intValue();
            	avaiableMonths.add(month);
            }
            
        } 
        catch (FileNotFoundException e) 
        {
            System.out.println("File not found");
            System.exit(-1);
        } 
        catch (IOException e) 
        {
            System.out.println("Error when loading parameters.");
            System.exit(-1);
        } 
        catch (ParseException e) 
        {
            System.out.println("Parsing error.");
            System.exit(-1);
        } 

        
        return avaiableMonths;
    }
}