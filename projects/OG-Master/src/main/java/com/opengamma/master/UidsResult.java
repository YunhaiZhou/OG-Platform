/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.master;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.beans.Bean;
import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBean;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.id.UniqueId;
import com.opengamma.util.PublicSPI;
import com.opengamma.util.paging.Paging;

/**
 * Result providing a list of Uids with paging.
 * 
 */
@PublicSPI
@BeanDefinition
public abstract class UidsResult extends DirectBean {

  /**
   * The paging information, not null if correctly created.
   */
  @PropertyDefinition
  private Paging _paging;
  /**
   * The documents, not null.
   */
  @PropertyDefinition
  private final List<UniqueId> _uids = new ArrayList<UniqueId>();

  /**
   * Creates an instance.
   */
  public UidsResult() {
  }

  /**
   * Creates an instance.
   * @param uids the list of Uids to add, not null
   */
  public UidsResult(List<UniqueId> uids) {
    _uids.addAll(uids);
    _paging = Paging.ofAll(uids);
  }

  //-------------------------------------------------------------------------
  /**
   * Gets the first Uid, or null if no Uids.
   * @return the first Uid, null if none
   */
  public UniqueId getFirst() {
    return getUids().size() > 0 ? getUids().get(0) : null;
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code UidsResult}.
   * @return the meta-bean, not null
   */
  public static UidsResult.Meta meta() {
    return UidsResult.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(UidsResult.Meta.INSTANCE);
  }

  @Override
  public UidsResult.Meta metaBean() {
    return UidsResult.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the paging information, not null if correctly created.
   * @return the value of the property
   */
  public Paging getPaging() {
    return _paging;
  }

  /**
   * Sets the paging information, not null if correctly created.
   * @param paging  the new value of the property
   */
  public void setPaging(Paging paging) {
    this._paging = paging;
  }

  /**
   * Gets the the {@code paging} property.
   * @return the property, not null
   */
  public final Property<Paging> paging() {
    return metaBean().paging().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the documents, not null.
   * @return the value of the property, not null
   */
  public List<UniqueId> getUids() {
    return _uids;
  }

  /**
   * Sets the documents, not null.
   * @param uids  the new value of the property, not null
   */
  public void setUids(List<UniqueId> uids) {
    JodaBeanUtils.notNull(uids, "uids");
    this._uids.clear();
    this._uids.addAll(uids);
  }

  /**
   * Gets the the {@code uids} property.
   * @return the property, not null
   */
  public final Property<List<UniqueId>> uids() {
    return metaBean().uids().createProperty(this);
  }

  //-----------------------------------------------------------------------
  @Override
  public UidsResult clone() {
    return JodaBeanUtils.cloneAlways(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      UidsResult other = (UidsResult) obj;
      return JodaBeanUtils.equal(getPaging(), other.getPaging()) &&
          JodaBeanUtils.equal(getUids(), other.getUids());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getPaging());
    hash += hash * 31 + JodaBeanUtils.hashCode(getUids());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(96);
    buf.append("UidsResult{");
    int len = buf.length();
    toString(buf);
    if (buf.length() > len) {
      buf.setLength(buf.length() - 2);
    }
    buf.append('}');
    return buf.toString();
  }

  protected void toString(StringBuilder buf) {
    buf.append("paging").append('=').append(JodaBeanUtils.toString(getPaging())).append(',').append(' ');
    buf.append("uids").append('=').append(JodaBeanUtils.toString(getUids())).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code UidsResult}.
   */
  public static class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code paging} property.
     */
    private final MetaProperty<Paging> _paging = DirectMetaProperty.ofReadWrite(
        this, "paging", UidsResult.class, Paging.class);
    /**
     * The meta-property for the {@code uids} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<List<UniqueId>> _uids = DirectMetaProperty.ofReadWrite(
        this, "uids", UidsResult.class, (Class) List.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "paging",
        "uids");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -995747956:  // paging
          return _paging;
        case 3589667:  // uids
          return _uids;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends UidsResult> builder() {
      throw new UnsupportedOperationException("UidsResult is an abstract class");
    }

    @Override
    public Class<? extends UidsResult> beanType() {
      return UidsResult.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code paging} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Paging> paging() {
      return _paging;
    }

    /**
     * The meta-property for the {@code uids} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<List<UniqueId>> uids() {
      return _uids;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -995747956:  // paging
          return ((UidsResult) bean).getPaging();
        case 3589667:  // uids
          return ((UidsResult) bean).getUids();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -995747956:  // paging
          ((UidsResult) bean).setPaging((Paging) newValue);
          return;
        case 3589667:  // uids
          ((UidsResult) bean).setUids((List<UniqueId>) newValue);
          return;
      }
      super.propertySet(bean, propertyName, newValue, quiet);
    }

    @Override
    protected void validate(Bean bean) {
      JodaBeanUtils.notNull(((UidsResult) bean)._uids, "uids");
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
