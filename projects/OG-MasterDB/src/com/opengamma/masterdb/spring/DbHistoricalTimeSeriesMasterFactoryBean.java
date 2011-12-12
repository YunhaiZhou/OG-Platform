/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.masterdb.spring;

import java.util.Map;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.core.change.JmsChangeManager;
import com.opengamma.masterdb.historicaltimeseries.DbHistoricalTimeSeriesMaster;

/**
 * Spring factory bean to create the database HTS master.
 */
@BeanDefinition
public class DbHistoricalTimeSeriesMasterFactoryBean extends AbstractDbMasterFactoryBean<DbHistoricalTimeSeriesMaster> {

  /**
   * Creates an instance.
   */
  public DbHistoricalTimeSeriesMasterFactoryBean() {
    super(DbHistoricalTimeSeriesMaster.class);
  }

  //-------------------------------------------------------------------------
  @Override
  public DbHistoricalTimeSeriesMaster createObject() {
    DbHistoricalTimeSeriesMaster master = new DbHistoricalTimeSeriesMaster(getDbConnector());
    if (getUniqueIdScheme() != null) {
      master.setUniqueIdScheme(getUniqueIdScheme());
    }
    if (getMaxRetries() != null) {
      master.setMaxRetries(getMaxRetries());
    }
    if (getJmsConnector() != null) {
      JmsChangeManager cm = new JmsChangeManager(getJmsConnector().ensureTopicName(getJmsChangeManagerTopic()));
      master.setChangeManager(cm);
      cm.start();
    }
    return master;
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code DbHistoricalTimeSeriesMasterFactoryBean}.
   * @return the meta-bean, not null
   */
  @SuppressWarnings("unchecked")
  public static DbHistoricalTimeSeriesMasterFactoryBean.Meta meta() {
    return DbHistoricalTimeSeriesMasterFactoryBean.Meta.INSTANCE;
  }
  static {
    JodaBeanUtils.registerMetaBean(DbHistoricalTimeSeriesMasterFactoryBean.Meta.INSTANCE);
  }

  @Override
  public DbHistoricalTimeSeriesMasterFactoryBean.Meta metaBean() {
    return DbHistoricalTimeSeriesMasterFactoryBean.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName, boolean quiet) {
    return super.propertyGet(propertyName, quiet);
  }

  @Override
  protected void propertySet(String propertyName, Object newValue, boolean quiet) {
    super.propertySet(propertyName, newValue, quiet);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      return super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    return hash ^ super.hashCode();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code DbHistoricalTimeSeriesMasterFactoryBean}.
   */
  public static class Meta extends AbstractDbMasterFactoryBean.Meta<DbHistoricalTimeSeriesMaster> {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<Object>> _map = new DirectMetaPropertyMap(
      this, (DirectMetaPropertyMap) super.metaPropertyMap());

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    public BeanBuilder<? extends DbHistoricalTimeSeriesMasterFactoryBean> builder() {
      return new DirectBeanBuilder<DbHistoricalTimeSeriesMasterFactoryBean>(new DbHistoricalTimeSeriesMasterFactoryBean());
    }

    @Override
    public Class<? extends DbHistoricalTimeSeriesMasterFactoryBean> beanType() {
      return DbHistoricalTimeSeriesMasterFactoryBean.class;
    }

    @Override
    public Map<String, MetaProperty<Object>> metaPropertyMap() {
      return _map;
    }

    //-----------------------------------------------------------------------
  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
