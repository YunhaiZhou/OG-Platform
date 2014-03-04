/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.sesame;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.ImmutableConstructor;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;
import org.threeten.bp.LocalDate;
import org.threeten.bp.ZonedDateTime;

import com.opengamma.sesame.marketdata.MarketDataSource;
import com.opengamma.util.ArgumentChecker;

/**
 * Simple immutable {@link Environment} implementation.
 */
@BeanDefinition
public final class SimpleEnvironment implements Environment, ImmutableBean {

  /** The valuation time. */
  @PropertyDefinition(validate = "notNull", get = "manual")
  private final ZonedDateTime _valuationTime;

  /** The function that provides market data. */
  @PropertyDefinition(validate = "notNull", get = "private", set = "private")
  private final MarketDataSource _marketDataSource;

  @Override
  public LocalDate getValuationDate() {
    return _valuationTime.toLocalDate();
  }

  @Override
  public ZonedDateTime getValuationTime() {
    return _valuationTime;
  }

  @Override
  public MarketDataSource getMarketDataSource() {
    return _marketDataSource.get(idBundle, fieldName);
  }

  @Override
  public Environment withValuationTime(ZonedDateTime valuationTime) {
    return new SimpleEnvironment(ArgumentChecker.notNull(valuationTime, "valuationTime"), _marketDataSource);
  }

  @Override
  public Environment withMarketData(MarketDataSource marketData) {
    return new SimpleEnvironment(_valuationTime, ArgumentChecker.notNull(marketData, "marketData"));
  }

  @Override
  public Environment with(ZonedDateTime valuationTime, MarketDataSource marketData) {
    return new SimpleEnvironment(valuationTime, marketData);
  }

  @ImmutableConstructor
  public SimpleEnvironment(ZonedDateTime valuationTime, MarketDataSource marketDataSource) {
    _valuationTime = ArgumentChecker.notNull(valuationTime, "valuationTime");
    _marketDataSource = ArgumentChecker.notNull(marketDataSource, "marketDataSource");
  }
  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code SimpleEnvironment}.
   * @return the meta-bean, not null
   */
  public static SimpleEnvironment.Meta meta() {
    return SimpleEnvironment.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(SimpleEnvironment.Meta.INSTANCE);
  }

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static SimpleEnvironment.Builder builder() {
    return new SimpleEnvironment.Builder();
  }

  @Override
  public SimpleEnvironment.Meta metaBean() {
    return SimpleEnvironment.Meta.INSTANCE;
  }

  @Override
  public <R> Property<R> property(String propertyName) {
    return metaBean().<R>metaProperty(propertyName).createProperty(this);
  }

  @Override
  public Set<String> propertyNames() {
    return metaBean().metaPropertyMap().keySet();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the function that provides market data.
   * @return the value of the property, not null
   */
  private MarketDataSource getMarketDataSource() {
    return _marketDataSource;
  }

  //-----------------------------------------------------------------------
  /**
   * Returns a builder that allows this bean to be mutated.
   * @return the mutable builder, not null
   */
  public Builder toBuilder() {
    return new Builder(this);
  }

  @Override
  public SimpleEnvironment clone() {
    return this;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      SimpleEnvironment other = (SimpleEnvironment) obj;
      return JodaBeanUtils.equal(getValuationTime(), other.getValuationTime()) &&
          JodaBeanUtils.equal(getMarketDataSource(), other.getMarketDataSource());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getValuationTime());
    hash += hash * 31 + JodaBeanUtils.hashCode(getMarketDataSource());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(96);
    buf.append("SimpleEnvironment{");
    buf.append("valuationTime").append('=').append(getValuationTime()).append(',').append(' ');
    buf.append("marketDataSource").append('=').append(JodaBeanUtils.toString(getMarketDataSource()));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code SimpleEnvironment}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code valuationTime} property.
     */
    private final MetaProperty<ZonedDateTime> _valuationTime = DirectMetaProperty.ofImmutable(
        this, "valuationTime", SimpleEnvironment.class, ZonedDateTime.class);
    /**
     * The meta-property for the {@code marketDataSource} property.
     */
    private final MetaProperty<MarketDataSource> _marketDataSource = DirectMetaProperty.ofImmutable(
        this, "marketDataSource", SimpleEnvironment.class, MarketDataSource.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "valuationTime",
        "marketDataSource");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 113591406:  // valuationTime
          return _valuationTime;
        case -1608011327:  // marketDataSource
          return _marketDataSource;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public SimpleEnvironment.Builder builder() {
      return new SimpleEnvironment.Builder();
    }

    @Override
    public Class<? extends SimpleEnvironment> beanType() {
      return SimpleEnvironment.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code valuationTime} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ZonedDateTime> valuationTime() {
      return _valuationTime;
    }

    /**
     * The meta-property for the {@code marketDataSource} property.
     * @return the meta-property, not null
     */
    public MetaProperty<MarketDataSource> marketDataSource() {
      return _marketDataSource;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 113591406:  // valuationTime
          return ((SimpleEnvironment) bean).getValuationTime();
        case -1608011327:  // marketDataSource
          return ((SimpleEnvironment) bean).getMarketDataSource();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      metaProperty(propertyName);
      if (quiet) {
        return;
      }
      throw new UnsupportedOperationException("Property cannot be written: " + propertyName);
    }

  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code SimpleEnvironment}.
   */
  public static final class Builder extends DirectFieldsBeanBuilder<SimpleEnvironment> {

    private ZonedDateTime _valuationTime;
    private MarketDataSource _marketDataSource;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(SimpleEnvironment beanToCopy) {
      this._valuationTime = beanToCopy.getValuationTime();
      this._marketDataSource = beanToCopy.getMarketDataSource();
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 113591406:  // valuationTime
          return _valuationTime;
        case -1608011327:  // marketDataSource
          return _marketDataSource;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 113591406:  // valuationTime
          this._valuationTime = (ZonedDateTime) newValue;
          break;
        case -1608011327:  // marketDataSource
          this._marketDataSource = (MarketDataSource) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public Builder set(MetaProperty<?> property, Object value) {
      super.set(property, value);
      return this;
    }

    @Override
    public Builder setString(String propertyName, String value) {
      setString(meta().metaProperty(propertyName), value);
      return this;
    }

    @Override
    public Builder setString(MetaProperty<?> property, String value) {
      super.set(property, value);
      return this;
    }

    @Override
    public Builder setAll(Map<String, ? extends Object> propertyValueMap) {
      super.setAll(propertyValueMap);
      return this;
    }

    @Override
    public SimpleEnvironment build() {
      return new SimpleEnvironment(
          _valuationTime,
          _marketDataSource);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the {@code valuationTime} property in the builder.
     * @param valuationTime  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder valuationTime(ZonedDateTime valuationTime) {
      JodaBeanUtils.notNull(valuationTime, "valuationTime");
      this._valuationTime = valuationTime;
      return this;
    }

    /**
     * Sets the {@code marketDataSource} property in the builder.
     * @param marketDataSource  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder marketDataSource(MarketDataSource marketDataSource) {
      JodaBeanUtils.notNull(marketDataSource, "marketDataSource");
      this._marketDataSource = marketDataSource;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(96);
      buf.append("SimpleEnvironment.Builder{");
      buf.append("valuationTime").append('=').append(JodaBeanUtils.toString(_valuationTime)).append(',').append(' ');
      buf.append("marketDataSource").append('=').append(JodaBeanUtils.toString(_marketDataSource));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
