package main;

import java.sql.*;
import java.util.ArrayList;

import gui.*;
import program_controller.ProgramController;

public class Main 
{

    public static void main(String[] args) throws SQLException
    {

    	ArrayList<Integer> months = new ArrayList<Integer>();
    	months.add(5);
    	months.add(6);
    	ProgramController controller = new ProgramController(months);
    	GUI gui = new GUI(controller);
    	gui.showGUI();	
      }
}