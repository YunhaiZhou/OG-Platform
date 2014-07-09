/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.util.fudgemsg;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.google.common.collect.ImmutableMap;

/**
 * A test Joda-Bean.
 */
@BeanDefinition
public class JodaTestBean implements ImmutableBean {

  @PropertyDefinition
  private final ImmutableMap<String, Object> map;
  @PropertyDefinition
  private final Object object;

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code JodaTestBean}.
   * @return the meta-bean, not null
   */
  public static JodaTestBean.Meta meta() {
    return JodaTestBean.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(JodaTestBean.Meta.INSTANCE);
  }

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static JodaTestBean.Builder builder() {
    return new JodaTestBean.Builder();
  }

  /**
   * Restricted constructor.
   * @param builder  the builder to copy from, not null
   */
  protected JodaTestBean(JodaTestBean.Builder builder) {
    this.map = (builder.map != null ? ImmutableMap.copyOf(builder.map) : null);
    this.object = builder.object;
  }

  @Override
  public JodaTestBean.Meta metaBean() {
    return JodaTestBean.Meta.INSTANCE;
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
   * Gets the map.
   * @return the value of the property
   */
  public ImmutableMap<String, Object> getMap() {
    return map;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the object.
   * @return the value of the property
   */
  public Object getObject() {
    return object;
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
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      JodaTestBean other = (JodaTestBean) obj;
      return JodaBeanUtils.equal(getMap(), other.getMap()) &&
          JodaBeanUtils.equal(getObject(), other.getObject());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getMap());
    hash += hash * 31 + JodaBeanUtils.hashCode(getObject());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(96);
    buf.append("JodaTestBean{");
    int len = buf.length();
    toString(buf);
    if (buf.length() > len) {
      buf.setLength(buf.length() - 2);
    }
    buf.append('}');
    return buf.toString();
  }

  protected void toString(StringBuilder buf) {
    buf.append("map").append('=').append(JodaBeanUtils.toString(getMap())).append(',').append(' ');
    buf.append("object").append('=').append(JodaBeanUtils.toString(getObject())).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code JodaTestBean}.
   */
  public static class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code map} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<ImmutableMap<String, Object>> _map = DirectMetaProperty.ofImmutable(
        this, "map", JodaTestBean.class, (Class) ImmutableMap.class);
    /**
     * The meta-property for the {@code object} property.
     */
    private final MetaProperty<Object> _object = DirectMetaProperty.ofImmutable(
        this, "object", JodaTestBean.class, Object.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "map",
        "object");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 107868:  // map
          return _map;
        case -1023368385:  // object
          return _object;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public JodaTestBean.Builder builder() {
      return new JodaTestBean.Builder();
    }

    @Override
    public Class<? extends JodaTestBean> beanType() {
      return JodaTestBean.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code map} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ImmutableMap<String, Object>> map() {
      return _map;
    }

    /**
     * The meta-property for the {@code object} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Object> object() {
      return _object;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 107868:  // map
          return ((JodaTestBean) bean).getMap();
        case -1023368385:  // object
          return ((JodaTestBean) bean).getObject();
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
   * The bean-builder for {@code JodaTestBean}.
   */
  public static class Builder extends DirectFieldsBeanBuilder<JodaTestBean> {

    private Map<String, Object> map;
    private Object object;

    /**
     * Restricted constructor.
     */
    protected Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    protected Builder(JodaTestBean beanToCopy) {
      this.map = (beanToCopy.getMap() != null ? new HashMap<String, Object>(beanToCopy.getMap()) : null);
      this.object = beanToCopy.getObject();
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 107868:  // map
          return map;
        case -1023368385:  // object
          return object;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 107868:  // map
          this.map = (Map<String, Object>) newValue;
          break;
        case -1023368385:  // object
          this.object = (Object) newValue;
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
    public JodaTestBean build() {
      return new JodaTestBean(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the {@code map} property in the builder.
     * @param map  the new value
     * @return this, for chaining, not null
     */
    public Builder map(Map<String, Object> map) {
      this.map = map;
      return this;
    }

    /**
     * Sets the {@code object} property in the builder.
     * @param object  the new value
     * @return this, for chaining, not null
     */
    public Builder object(Object object) {
      this.object = object;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(96);
      buf.append("JodaTestBean.Builder{");
      int len = buf.length();
      toString(buf);
      if (buf.length() > len) {
        buf.setLength(buf.length() - 2);
      }
      buf.append('}');
      return buf.toString();
    }

    protected void toString(StringBuilder buf) {
      buf.append("map").append('=').append(JodaBeanUtils.toString(map)).append(',').append(' ');
      buf.append("object").append('=').append(JodaBeanUtils.toString(object)).append(',').append(' ');
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}