/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.security.index;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

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

import com.opengamma.id.ExternalId;
import com.opengamma.master.security.ManageableSecurity;
import com.opengamma.util.time.Tenor;

/**
 * Meta-data linking tenors to indices.
 */
@BeanDefinition
public class IndexFamily extends ManageableSecurity {
  private static final long serialVersionUID = 1L;
  /**
   * the type of object in the security master
   */
  public static final String METADATA_TYPE = "INDEX_FAMILY";
  @PropertyDefinition
  private final SortedMap<Tenor, ExternalId> _members = new TreeMap<>();
  
  /**
   * For the builder.
   */
  public IndexFamily() {
    super(METADATA_TYPE);
  }

  /**
   * Creates an index family with a name set
   * @param name The index name, not null
   */
  public IndexFamily(final String name) {
    setName(name);
  }
  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code IndexFamily}.
   * @return the meta-bean, not null
   */
  public static IndexFamily.Meta meta() {
    return IndexFamily.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(IndexFamily.Meta.INSTANCE);
  }

  @Override
  public IndexFamily.Meta metaBean() {
    return IndexFamily.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the members.
   * @return the value of the property, not null
   */
  public SortedMap<Tenor, ExternalId> getMembers() {
    return _members;
  }

  /**
   * Sets the members.
   * @param members  the new value of the property, not null
   */
  public void setMembers(SortedMap<Tenor, ExternalId> members) {
    JodaBeanUtils.notNull(members, "members");
    this._members.clear();
    this._members.putAll(members);
  }

  /**
   * Gets the the {@code members} property.
   * @return the property, not null
   */
  public final Property<SortedMap<Tenor, ExternalId>> members() {
    return metaBean().members().createProperty(this);
  }

  //-----------------------------------------------------------------------
  @Override
  public IndexFamily clone() {
    return JodaBeanUtils.cloneAlways(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      IndexFamily other = (IndexFamily) obj;
      return JodaBeanUtils.equal(getMembers(), other.getMembers()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash += hash * 31 + JodaBeanUtils.hashCode(getMembers());
    return hash ^ super.hashCode();
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(64);
    buf.append("IndexFamily{");
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
    buf.append("members").append('=').append(JodaBeanUtils.toString(getMembers())).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code IndexFamily}.
   */
  public static class Meta extends ManageableSecurity.Meta {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code members} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<SortedMap<Tenor, ExternalId>> _members = DirectMetaProperty.ofReadWrite(
        this, "members", IndexFamily.class, (Class) SortedMap.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "members");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 948881689:  // members
          return _members;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends IndexFamily> builder() {
      return new DirectBeanBuilder<IndexFamily>(new IndexFamily());
    }

    @Override
    public Class<? extends IndexFamily> beanType() {
      return IndexFamily.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code members} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<SortedMap<Tenor, ExternalId>> members() {
      return _members;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 948881689:  // members
          return ((IndexFamily) bean).getMembers();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 948881689:  // members
          ((IndexFamily) bean).setMembers((SortedMap<Tenor, ExternalId>) newValue);
          return;
      }
      super.propertySet(bean, propertyName, newValue, quiet);
    }

    @Override
    protected void validate(Bean bean) {
      JodaBeanUtils.notNull(((IndexFamily) bean)._members, "members");
      super.validate(bean);
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
