package com.onegolabs.wamanager.dbconnection;

import com.onegolabs.wamanager.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author dmzhg
 */
public class ConnectionManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionManager.class);

	public Connection connect(Configuration config) throws Exception {
		LOGGER.info("pls connect");
		Class clazz = Class.forName(config.getProperty("connection.driver").trim());
		String address = config.getProperty("connection.url").trim();
		String userName = config.getProperty("connection.user").trim();
		String password = config.getProperty("connection.password").trim();

		Connection connection = DriverManager.getConnection(address, userName, password);
		Statement query = connection.createStatement();
		ResultSet queryResult = query.executeQuery(" SELECT Name, description, gid FROM SHOPTREE WHERE GID = 0");
//		if (!queryResult.next()) {
//			throw new InitializationException(ErrorCode.CONNECTION);
//		}
		return connection;
	}

//	public Connection getConnection() throws Exception {
//		return connect();
//	}
}
