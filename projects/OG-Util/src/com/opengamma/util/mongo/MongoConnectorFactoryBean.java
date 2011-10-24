/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.util.mongo;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.opengamma.util.ArgumentChecker;
import com.opengamma.util.SingletonFactoryBean;

/**
 * Factory bean to provide Mongo database connectors.
 * <p>
 * This class provides a simple-to-setup and simple-to-use way to access Mongo databases.
 * The main benefit is simpler configuration, especially if that configuration is in XML.
 */
public class MongoConnectorFactoryBean extends SingletonFactoryBean<MongoConnector> {

  /**
   * The configuration name.
   */
  private String _name;
  /**
   * The Mongo instance.
   */
  private Mongo _mongo;
  /**
   * The database.
   */
  private DB _database;
  /**
   * The host name.
   */
  private String _host = "localhost";
  /**
   * The host port.
   */
  private int _port = 27017;
  /**
   * The database name.
   */
  private String _databaseName = "OpenGamma";
  /**
   * The collection suffix.
   */
  private String _collectionSuffix = "";

  /**
   * Creates an instance.
   */
  public MongoConnectorFactoryBean() {
  }

  /**
   * Creates an instance based on an existing connector.
   * <p>
   * This copies the name, mongo and databse.
   * 
   * @param base  the base connector to copy, not null
   */
  public MongoConnectorFactoryBean(MongoConnector base) {
    setName(base.getName());
    setMongo(base.getMongo());
    setDB(base.getDB());
  }

  //-------------------------------------------------------------------------
  public String getName() {
    return _name;
  }

  public void setName(String name) {
    _name = name;
  }

  public Mongo getMongo() {
    return _mongo;
  }

  public void setMongo(Mongo mongo) {
    _mongo = mongo;
  }

  public DB getDB() {
    return _database;
  }

  public void setDB(DB database) {
    _database = database;
  }

  public String getHost() {
    return _host;
  }

  public void setHost(String host) {
    _host = host;
  }

  public int getPort() {
    return _port;
  }

  public void setPort(int port) {
    _port = port;
  }

  public String getDatabaseName() {
    return _databaseName;
  }

  public void setDatabaseName(String databaseName) {
    _databaseName = databaseName;
  }

  public String getCollectionSuffix() {
    return _collectionSuffix;
  }

  public void setCollectionSuffix(String collectionSuffix) {
    _collectionSuffix = collectionSuffix;
  }

  //-------------------------------------------------------------------------
  @Override
  public MongoConnector createObject() {
    ArgumentChecker.notNull(getName(), "name");
    final Mongo mongo = createMongo();
    final DB db = createDatabase(mongo);
    return new MongoConnector(getName(), mongo, db, getCollectionSuffix());
  }

  /**
   * Creates the Mongo instance, using the host and port.
   * 
   * @return the Mongo instance, not null
   */
  protected Mongo createMongo() {
    final Mongo mongo = getMongo();  // store in variable to protect against change by subclass
    if (mongo != null) {
      return mongo;
    }
    ArgumentChecker.notNull(getHost(), "host");
    try {
      return new Mongo(getHost(), getPort());
    } catch (UnknownHostException ex) {
      throw new MongoException(ex.getMessage(), ex);
    }
  }

  /**
   * Creates the database.
   * 
   * @param mongo  the Mongo instance, not null
   * @return the database, may be null
   */
  protected DB createDatabase(Mongo mongo) {
    final DB db = getDB();
    if (db != null) {
      return db;
    }
    final String databaseName = getDatabaseName();  // store in variable to protect against change by subclass
    ArgumentChecker.notNull(databaseName, "databaseName");
    return mongo.getDB(databaseName);
  }

  //-------------------------------------------------------------------------
  @Override
  public String toString() {
    return getClass().getSimpleName();
  }

}
