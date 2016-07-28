/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.util.fudgemsg;

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
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.google.common.collect.Iterables;

/**
 * A wrapper for a list that ensures transfer by Fudge.
 * <p>
 * Fudge does not handle transfer of lists on their own very well,
 * but does handle them when wrapped in this class.
 * 
 * @param <T> the list type
 */
@BeanDefinition
public class FudgeListWrapper<T> extends DirectBean {

  /**
   * The list.
   */
  @PropertyDefinition(validate = "notNull")
  private List<T> _list = new ArrayList<T>();

  /**
   * Creates an instance.
   * 
   * @param <T> the list type
   * @param list  the list, not null
   * @return the list, not null
   */
  public static <T> FudgeListWrapper<T> of(Iterable<T> list) {
    return new FudgeListWrapper<T>(list);
  }

  //-------------------------------------------------------------------------
  /**
   * Creates an instance.
   */
  private FudgeListWrapper() {
  }

  /**
   * Creates an instance.
   * 
   * @param list  the list, not null
   */
  public FudgeListWrapper(Iterable<T> list) {
    Iterables.addAll(getList(), list);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code FudgeListWrapper}.
   * @return the meta-bean, not null
   */
  @SuppressWarnings("rawtypes")
  public static FudgeListWrapper.Meta meta() {
    return FudgeListWrapper.Meta.INSTANCE;
  }

  /**
   * The meta-bean for {@code FudgeListWrapper}.
   * @param <R>  the bean's generic type
   * @param cls  the bean's generic type
   * @return the meta-bean, not null
   */
  @SuppressWarnings("unchecked")
  public static <R> FudgeListWrapper.Meta<R> metaFudgeListWrapper(Class<R> cls) {
    return FudgeListWrapper.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(FudgeListWrapper.Meta.INSTANCE);
  }

  @SuppressWarnings("unchecked")
  @Override
  public FudgeListWrapper.Meta<T> metaBean() {
    return FudgeListWrapper.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the list.
   * @return the value of the property, not null
   */
  public List<T> getList() {
    return _list;
  }

  /**
   * Sets the list.
   * @param list  the new value of the property, not null
   */
  public void setList(List<T> list) {
    JodaBeanUtils.notNull(list, "list");
    this._list = list;
  }

  /**
   * Gets the the {@code list} property.
   * @return the property, not null
   */
  public final Property<List<T>> list() {
    return metaBean().list().createProperty(this);
  }

  //-----------------------------------------------------------------------
  @Override
  public FudgeListWrapper<T> clone() {
    return JodaBeanUtils.cloneAlways(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      FudgeListWrapper<?> other = (FudgeListWrapper<?>) obj;
      return JodaBeanUtils.equal(getList(), other.getList());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(getList());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(64);
    buf.append("FudgeListWrapper{");
    int len = buf.length();
    toString(buf);
    if (buf.length() > len) {
      buf.setLength(buf.length() - 2);
    }
    buf.append('}');
    return buf.toString();
  }

  protected void toString(StringBuilder buf) {
    buf.append("list").append('=').append(JodaBeanUtils.toString(getList())).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code FudgeListWrapper}.
   * @param <T>  the type
   */
  public static class Meta<T> extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    @SuppressWarnings("rawtypes")
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code list} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<List<T>> _list = DirectMetaProperty.ofReadWrite(
        this, "list", FudgeListWrapper.class, (Class) List.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "list");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 3322014:  // list
          return _list;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends FudgeListWrapper<T>> builder() {
      return new DirectBeanBuilder<FudgeListWrapper<T>>(new FudgeListWrapper<T>());
    }

    @SuppressWarnings({"unchecked", "rawtypes" })
    @Override
    public Class<? extends FudgeListWrapper<T>> beanType() {
      return (Class) FudgeListWrapper.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code list} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<List<T>> list() {
      return _list;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 3322014:  // list
          return ((FudgeListWrapper<?>) bean).getList();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 3322014:  // list
          ((FudgeListWrapper<T>) bean).setList((List<T>) newValue);
          return;
      }
      super.propertySet(bean, propertyName, newValue, quiet);
    }

    @Override
    protected void validate(Bean bean) {
      JodaBeanUtils.notNull(((FudgeListWrapper<?>) bean)._list, "list");
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
