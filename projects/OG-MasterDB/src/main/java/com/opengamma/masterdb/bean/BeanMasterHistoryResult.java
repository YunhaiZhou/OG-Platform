/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.masterdb.bean;

import java.util.Map;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.master.AbstractDocument;
import com.opengamma.master.AbstractHistoryResult;

/**
 * Provides access to the results of querying history in a {@code DbBeanMaster}.
 * 
 * @param <D>  the type of the document
 */
@BeanDefinition
public class BeanMasterHistoryResult<D extends AbstractDocument> extends AbstractHistoryResult<D> {

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code BeanMasterHistoryResult}.
   * @return the meta-bean, not null
   */
  @SuppressWarnings("rawtypes")
  public static BeanMasterHistoryResult.Meta meta() {
    return BeanMasterHistoryResult.Meta.INSTANCE;
  }

  /**
   * The meta-bean for {@code BeanMasterHistoryResult}.
   * @param <R>  the bean's generic type
   * @param cls  the bean's generic type
   * @return the meta-bean, not null
   */
  @SuppressWarnings("unchecked")
  public static <R extends AbstractDocument> BeanMasterHistoryResult.Meta<R> metaBeanMasterHistoryResult(Class<R> cls) {
    return BeanMasterHistoryResult.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(BeanMasterHistoryResult.Meta.INSTANCE);
  }

  @SuppressWarnings("unchecked")
  @Override
  public BeanMasterHistoryResult.Meta<D> metaBean() {
    return BeanMasterHistoryResult.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  @Override
  public BeanMasterHistoryResult<D> clone() {
    return JodaBeanUtils.cloneAlways(this);
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

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(32);
    buf.append("BeanMasterHistoryResult{");
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
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code BeanMasterHistoryResult}.
   */
  public static class Meta<D extends AbstractDocument> extends AbstractHistoryResult.Meta<D> {
    /**
     * The singleton instance of the meta-bean.
     */
    @SuppressWarnings("rawtypes")
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, (DirectMetaPropertyMap) super.metaPropertyMap());

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    public BeanBuilder<? extends BeanMasterHistoryResult<D>> builder() {
      return new DirectBeanBuilder<BeanMasterHistoryResult<D>>(new BeanMasterHistoryResult<D>());
    }

    @SuppressWarnings({"unchecked", "rawtypes" })
    @Override
    public Class<? extends BeanMasterHistoryResult<D>> beanType() {
      return (Class) BeanMasterHistoryResult.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
