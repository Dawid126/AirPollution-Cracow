package database_provider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseProvider {
	
	private Connection connection;
	
	public DatabaseProvider() throws SQLException
	{
		String connectionUrl = "jdbc:sqlserver://dbmanage.lab.ki.agh.edu.pl:1433;authentication=NotSpecified;authenticationScheme=nativeAuthentication;xopenStates=false;sendTimeAsDatetime=true;trustServerCertificate=false;TransparentNetworkIPResolution=true;serverNameAsACE=false;sendStringParametersAsUnicode=true;selectMethod=direct;responseBuffering=adaptive;packetSize=8000;multiSubnetFailover=false;loginTimeout=30;lockTimeout=-1;lastUpdateCount=true;encrypt=false;disableStatementPooling=true;columnEncryptionSetting=Disabled;applicationName=Database Navigator - Main;applicationIntent=readwrite;databaseName=u_tekielak;user=u_tekielak;password=PKxvRNkmYtqW";

        connection = DriverManager.getConnection(connectionUrl);
	}
	
	public ResultSet execute(String query) throws SQLException
	{
		Statement statement = connection.createStatement();
        return statement.executeQuery(query);
	}
}