/**
 * DBConnection
 * @author
 */
package com.cgtsi.util;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.IOException;

import com.cgtsi.application.LogClass;
import com.cgtsi.common.Log;


/**
  * Creates the initial connection pool and gets connection from the pool.
  */

public class DBConnection
{
private static final int MIN_CONNECTIONS=1;
private static final int MAX_CONNECTIONS=2;
private static final double CONNECTION_REFRESH_DAYS=1;

private DBConnection()
{
}
	private static DbConnectionBroker dbBroker = null;
  /**
	*Creates the initial connection pool. This method is invoked in the init method of the calling jsp or servlet.<BR>
	*driver		:	The Database driver. Obtained from property file.<BR>
	*conUrl		:	The Connection String. Obtained from property file.<BR>
	*login		:	Database username.Obtained from property file.<BR>
	*password   :	Database password.Obtained from property file.<BR>
	*minConn	:	Minimum number of connections allowed.Obtained from property file.<BR>
	*maxConn 	:	Maximum number of connections allowed.Obtained from property file.<BR>
	*dbLogFile	:	path of the file where which is used to log the connection details as connections are being created or destroyed.<BR>
	*maxTimeOut :	The maximum time allowed for a connection to exist.Obtained from property file.<BR>
	*<B>Procedures Called</B><BR>
	*DbConnectionBroker	: Creates the initial connection pool.<BR>
	*/

	private static DbConnectionBroker createPool() throws Exception
	{
		int minConn = 0;
		int maxConn = 0;
		double maxTimeOut =0;
		try
		{
			String driver=PropertyLoader.getValue("databasedriver");
			//System.out.println("driver"+driver);
			String conUrl = PropertyLoader.getValue("connectionurl");
   //   System.out.println("conUrl:"+conUrl);
			String login = PropertyLoader.getValue("dblogin");
    //  System.out.println("login:"+login);
			String password = PropertyLoader.getValue("dbpassword");
    //  System.out.println("password:"+password);

			//System.out.println(driver+","+conUrl+", "+login+","+password);

			if(driver==null || driver.trim().equals("")||
			 conUrl==null || conUrl.trim().equals("") ||
			 login==null || login.trim().equals("") ||
			 password==null || password.trim().equals(""))
			{
				throw new Exception("Enter Values for " +
					"DatabaseDriver/ConnectionURL/DBLogin/DBPassword");
			}
			try
			{
				minConn = Integer.parseInt(PropertyLoader.getValue("minconnections"));
			}
			catch(NumberFormatException invalidNumber)
			{
				minConn=MIN_CONNECTIONS;
			}
			try
			{
				maxConn = Integer.parseInt(PropertyLoader.getValue("maxconnections"));
			}
			catch(NumberFormatException invalidNumber)
			{
				maxConn =MAX_CONNECTIONS;
			}

			String dbLogFile = PropertyLoader.getValue("contextpath")+PropertyLoader.CONFIG_DIRECTORY+"/dbPool.log";
			try
			{
				maxTimeOut = Double.parseDouble(PropertyLoader.getValue("maxconnectiontime"));
			}
			catch(NumberFormatException invalidNumber)
			{
				maxTimeOut = CONNECTION_REFRESH_DAYS;
			}

			dbBroker = new DbConnectionBroker(driver.trim(), conUrl.trim(),
								 login.trim(), password.trim(), minConn, maxConn,
								 	dbLogFile.trim(), maxTimeOut);
		}
		catch(IOException e)
		{
			throw new Exception (e.getMessage());
		}
		return dbBroker;
	}
    public static Connection getConnection()
    {
		return getConnection(true);
    }

	public static Connection getConnection(boolean autoCommit)
	{
		if(dbBroker==null)
		{
			throw new IllegalStateException("Connection pool is not" +
				"started. Please call startConnectionPool() befoer " +
				"calling this method.");
		}
		Connection connection=dbBroker.getConnection();
		if(connection!=null)
		{
			try {
				connection.setAutoCommit(autoCommit);
			} catch (SQLException e) {
				  Log.log(Log.ERROR,"DBConnection","getConnection="+autoCommit,e.getMessage());

                  Log.logException(e);
				connection=null;
			}
		}
		return connection;

	}
    public static void startConnectionPool() throws Exception
    {
		dbBroker=createPool();
    }
    public static void freeConnection(Connection connection)
    {
		if(dbBroker!=null)
		{
			if(connection!=null)
			{
				try
				{
					connection.commit();
					//connection.close();
				}
				catch(SQLException exception)
				{
					exception.printStackTrace();
				}
			}

			dbBroker.freeConnection(connection);
		}
    }
    public static void destroy()
    {
		if(dbBroker!=null)
		{
			dbBroker.destroy();
			dbBroker=null;
		}
    }
    public static Connection getNewConnection(boolean autoCommit)
    {
		Connection connection=dbBroker.getNewConnection(autoCommit);

		return connection;
    }

}

