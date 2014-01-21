/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.bbg.component;

import java.util.Map;

import org.joda.beans.Bean;
import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.livedata.entitlement.LiveDataEntitlementChecker;
import com.opengamma.livedata.entitlement.PermissiveLiveDataEntitlementChecker;
import com.opengamma.livedata.entitlement.UserEntitlementChecker;
import com.opengamma.livedata.resolver.DistributionSpecificationResolver;
import com.opengamma.security.user.HibernateUserManager;
import com.opengamma.security.user.UserManager;
import com.opengamma.util.db.DbConnector;

/**
 * Component factory to create a Bloomberg server.
 */
@BeanDefinition
public class BloombergLiveDataServerComponentFactory extends AbstractBloombergLiveDataServerComponentFactory {

  /**
   * The database connector for user entitlement.
   */
  @PropertyDefinition(validate = "notNull")
  private DbConnector _dbConnector;

  //-------------------------------------------------------------------------
  @Override
  protected LiveDataEntitlementChecker initEntitlementChecker(DistributionSpecificationResolver distSpecResolver) {
    return new PermissiveLiveDataEntitlementChecker();
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code BloombergLiveDataServerComponentFactory}.
   * @return the meta-bean, not null
   */
  public static BloombergLiveDataServerComponentFactory.Meta meta() {
    return BloombergLiveDataServerComponentFactory.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(BloombergLiveDataServerComponentFactory.Meta.INSTANCE);
  }

  @Override
  public BloombergLiveDataServerComponentFactory.Meta metaBean() {
    return BloombergLiveDataServerComponentFactory.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the database connector for user entitlement.
   * @return the value of the property, not null
   */
  public DbConnector getDbConnector() {
    return _dbConnector;
  }

  /**
   * Sets the database connector for user entitlement.
   * @param dbConnector  the new value of the property, not null
   */
  public void setDbConnector(DbConnector dbConnector) {
    JodaBeanUtils.notNull(dbConnector, "dbConnector");
    this._dbConnector = dbConnector;
  }

  /**
   * Gets the the {@code dbConnector} property.
   * @return the property, not null
   */
  public final Property<DbConnector> dbConnector() {
    return metaBean().dbConnector().createProperty(this);
  }

  //-----------------------------------------------------------------------
  @Override
  public BloombergLiveDataServerComponentFactory clone() {
    return (BloombergLiveDataServerComponentFactory) super.clone();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      BloombergLiveDataServerComponentFactory other = (BloombergLiveDataServerComponentFactory) obj;
      return JodaBeanUtils.equal(getDbConnector(), other.getDbConnector()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash += hash * 31 + JodaBeanUtils.hashCode(getDbConnector());
    return hash ^ super.hashCode();
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(64);
    buf.append("BloombergLiveDataServerComponentFactory{");
    int len = buf.length();
    toString(buf);
    if (buf.length() > len) {
      buf.setLength(buf.length() - 2);
    }
    buf.append('}');
    return buf.toString();
  }

  @Override
  protected void toString(StringBuilder buf) {
    super.toString(buf);
    buf.append("dbConnector").append('=').append(JodaBeanUtils.toString(getDbConnector())).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code BloombergLiveDataServerComponentFactory}.
   */
  public static class Meta extends AbstractBloombergLiveDataServerComponentFactory.Meta {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code dbConnector} property.
     */
    private final MetaProperty<DbConnector> _dbConnector = DirectMetaProperty.ofReadWrite(
        this, "dbConnector", BloombergLiveDataServerComponentFactory.class, DbConnector.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "dbConnector");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 39794031:  // dbConnector
          return _dbConnector;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends BloombergLiveDataServerComponentFactory> builder() {
      return new DirectBeanBuilder<BloombergLiveDataServerComponentFactory>(new BloombergLiveDataServerComponentFactory());
    }

    @Override
    public Class<? extends BloombergLiveDataServerComponentFactory> beanType() {
      return BloombergLiveDataServerComponentFactory.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code dbConnector} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<DbConnector> dbConnector() {
      return _dbConnector;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 39794031:  // dbConnector
          return ((BloombergLiveDataServerComponentFactory) bean).getDbConnector();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 39794031:  // dbConnector
          ((BloombergLiveDataServerComponentFactory) bean).setDbConnector((DbConnector) newValue);
          return;
      }
      super.propertySet(bean, propertyName, newValue, quiet);
    }

    @Override
    protected void validate(Bean bean) {
      JodaBeanUtils.notNull(((BloombergLiveDataServerComponentFactory) bean)._dbConnector, "dbConnector");
      super.validate(bean);
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
