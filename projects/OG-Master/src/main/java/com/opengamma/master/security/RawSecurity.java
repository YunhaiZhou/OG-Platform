/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.master.security;

import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
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

import com.opengamma.id.ExternalIdBundle;
import com.opengamma.id.UniqueId;
import com.opengamma.util.PublicSPI;

/**
 * A raw security that simply holds an array of bytes.
 * <p>
 * This can be used to integrate a security defined using another system.
 * The byte array might be a serialized object or a Fudge message.
 */
@PublicSPI
@BeanDefinition
public class RawSecurity extends ManageableSecurity {

  /** Serialization version. */
  private static final long serialVersionUID = 1L;

  /** Standard name for an unsupported security type; not all RawSecurities need to be of this type */
  public static final String UNSUPPORTED_SECURITY_TYPE = "UNSUPPORTED";

  /**
   * The raw data of the security, simply expressed as a byte array.
   * The array is not cloned on get or set, so treat with care.
   */
  @PropertyDefinition(validate = "notNull")
  private byte[] _rawData = ArrayUtils.EMPTY_BYTE_ARRAY;

  /**
   * Creates an empty instance.
   * <p>
   * The security details should be set before use.
   */
  public RawSecurity() {
    super(UNSUPPORTED_SECURITY_TYPE);
  }

  /**
   * Creates an instance with a security type.
   * 
   * @param securityType  the security type, not null
   */
  public RawSecurity(String securityType) {
    super(securityType);
  }

  /**
   * Creates an instance with a security type.
   * 
   * @param securityType  the security type, not null
   * @param rawData  the raw data, assigned, not null
   */
  public RawSecurity(String securityType, byte[] rawData) {
    super(securityType);
    setRawData(rawData);
  }

  /**
   * Creates a fully populated instance.
   * 
   * @param uniqueId  the security unique identifier, may be null
   * @param name  the display name, not null
   * @param securityType  the security type, not null
   * @param bundle  the security external identifier bundle, not null
   * @param rawData  the raw data, assigned, not null
   */
  public RawSecurity(UniqueId uniqueId, String name, String securityType, ExternalIdBundle bundle, byte[] rawData) {
    super(uniqueId, name, securityType, bundle);
    setRawData(rawData);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code RawSecurity}.
   * @return the meta-bean, not null
   */
  public static RawSecurity.Meta meta() {
    return RawSecurity.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(RawSecurity.Meta.INSTANCE);
  }

  @Override
  public RawSecurity.Meta metaBean() {
    return RawSecurity.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the raw data of the security, simply expressed as a byte array.
   * The array is not cloned on get or set, so treat with care.
   * @return the value of the property, not null
   */
  public byte[] getRawData() {
    return _rawData;
  }

  /**
   * Sets the raw data of the security, simply expressed as a byte array.
   * The array is not cloned on get or set, so treat with care.
   * @param rawData  the new value of the property, not null
   */
  public void setRawData(byte[] rawData) {
    JodaBeanUtils.notNull(rawData, "rawData");
    this._rawData = rawData;
  }

  /**
   * Gets the the {@code rawData} property.
   * The array is not cloned on get or set, so treat with care.
   * @return the property, not null
   */
  public final Property<byte[]> rawData() {
    return metaBean().rawData().createProperty(this);
  }

  //-----------------------------------------------------------------------
  @Override
  public RawSecurity clone() {
    return JodaBeanUtils.cloneAlways(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      RawSecurity other = (RawSecurity) obj;
      return JodaBeanUtils.equal(getRawData(), other.getRawData()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash += hash * 31 + JodaBeanUtils.hashCode(getRawData());
    return hash ^ super.hashCode();
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(64);
    buf.append("RawSecurity{");
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
    buf.append("rawData").append('=').append(JodaBeanUtils.toString(getRawData())).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code RawSecurity}.
   */
  public static class Meta extends ManageableSecurity.Meta {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code rawData} property.
     */
    private final MetaProperty<byte[]> _rawData = DirectMetaProperty.ofReadWrite(
        this, "rawData", RawSecurity.class, byte[].class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "rawData");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 985253874:  // rawData
          return _rawData;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends RawSecurity> builder() {
      return new DirectBeanBuilder<RawSecurity>(new RawSecurity());
    }

    @Override
    public Class<? extends RawSecurity> beanType() {
      return RawSecurity.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code rawData} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<byte[]> rawData() {
      return _rawData;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 985253874:  // rawData
          return ((RawSecurity) bean).getRawData();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 985253874:  // rawData
          ((RawSecurity) bean).setRawData((byte[]) newValue);
          return;
      }
      super.propertySet(bean, propertyName, newValue, quiet);
    }

    @Override
    protected void validate(Bean bean) {
      JodaBeanUtils.notNull(((RawSecurity) bean)._rawData, "rawData");
      super.validate(bean);
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
