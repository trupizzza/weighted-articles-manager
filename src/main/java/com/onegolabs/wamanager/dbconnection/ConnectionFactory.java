package com.onegolabs.wamanager.dbconnection;

import com.onegolabs.wamanager.Configuration;
import com.onegolabs.wamanager.exception.ConnectionCode;
import com.onegolabs.wamanager.exception.InitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionFactory.class);

	private static ConnectionFactory instance = new ConnectionFactory(new Configuration());

	private Configuration config;

	private ConnectionFactory(Configuration config) {
		this.config = config;
		try {
			Class.forName(config.getProperty("connection.driver"));
		} catch (ClassNotFoundException e) {
			throw new InitializationException(ConnectionCode.NO_DRIVER_FOUND)
					.set("url", config.getProperty("connection.driver"));
		}
	}

	public static Connection getConnection() {
		return instance.createConnection();
	}

	private Connection createConnection() {
		Connection connection;
		String url = null;
		String user = null;
		String password;
		try {
			url = config.getProperty("connection.url");
			user = config.getProperty("connection.user");
			password = config.getProperty("connection.password");
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			throw new InitializationException(ConnectionCode.NO_CONNECTION).set("url", url).set("user", user);
		}
		return connection;
	}
}