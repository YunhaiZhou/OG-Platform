/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.analytics.curve;

import java.io.Serializable;
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

import com.google.common.collect.ImmutableMap;
import com.opengamma.core.link.ConfigLink;
import com.opengamma.util.ArgumentChecker;

/**
 * Configuration defining a group on curves, where a group is a set of curves
 * that are to be calculated at the same time.
 */
@BeanDefinition
public class CurveGroupConfiguration extends DirectBean implements Serializable {

  /** Serialization version */
  private static final long serialVersionUID = 1L;

  /**
   * The order.
   */
  @PropertyDefinition
  private int _order;

  /**
   * The types for each curve.
   */
  @PropertyDefinition(validate = "notNull", set = "manual")
  private Map<String, List<? extends CurveTypeConfiguration>> _typesForCurves;

  /**
   * The types for each curve, where each curve is resolved.
   * In the future we may want to expose the links directly.
   */
  private Map<ConfigLink<AbstractCurveDefinition>, List<? extends CurveTypeConfiguration>> _typesForCurvesLinks;

  /**
   * For the builder.
   */
  /* package */ CurveGroupConfiguration() {
  }

  /**
   * @param order The order of this configuration, not negative
   * @param curveTypes The curve types for a name, not null
   */
  public CurveGroupConfiguration(final int order, final Map<String, List<? extends CurveTypeConfiguration>> curveTypes) {
    ArgumentChecker.notNegative(order, "order");
    setOrder(order);
    setTypesForCurves(curveTypes);
  }

  /**
   * Sets the types for each curve.
   * @param typesForCurves  the new value of the property, not null
   */
  public void setTypesForCurves(Map<String, List<? extends CurveTypeConfiguration>> typesForCurves) {
    _typesForCurves = ArgumentChecker.notNull(typesForCurves, "typesForCurves");

    ImmutableMap.Builder<ConfigLink<AbstractCurveDefinition>, List<? extends CurveTypeConfiguration>> builder = ImmutableMap.builder();
    for (Map.Entry<String, List<? extends CurveTypeConfiguration>> entry : _typesForCurves.entrySet()) {
      builder.put(ConfigLink.resolvable(entry.getKey(), AbstractCurveDefinition.class), entry.getValue());
    }
    _typesForCurvesLinks = builder.build();
  }

  /**
   * Resolves the types for each curve.
   * @return map of CurveDefinition -> List<CurveTypeConfiguration>, not null
   */
  public Map<AbstractCurveDefinition, List<? extends CurveTypeConfiguration>> resolveTypesForCurves() {

    ImmutableMap.Builder<AbstractCurveDefinition, List<? extends CurveTypeConfiguration>> builder = ImmutableMap.builder();
    for (Map.Entry<ConfigLink<AbstractCurveDefinition>, List<? extends CurveTypeConfiguration>> entry : _typesForCurvesLinks.entrySet()) {
      builder.put(entry.getKey().resolve(), entry.getValue());
    }
    return builder.build();
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code CurveGroupConfiguration}.
   * @return the meta-bean, not null
   */
  public static CurveGroupConfiguration.Meta meta() {
    return CurveGroupConfiguration.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(CurveGroupConfiguration.Meta.INSTANCE);
  }

  @Override
  public CurveGroupConfiguration.Meta metaBean() {
    return CurveGroupConfiguration.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the order.
   * @return the value of the property
   */
  public int getOrder() {
    return _order;
  }

  /**
   * Sets the order.
   * @param order  the new value of the property
   */
  public void setOrder(int order) {
    this._order = order;
  }

  /**
   * Gets the the {@code order} property.
   * @return the property, not null
   */
  public final Property<Integer> order() {
    return metaBean().order().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the types for each curve.
   * @return the value of the property, not null
   */
  public Map<String, List<? extends CurveTypeConfiguration>> getTypesForCurves() {
    return _typesForCurves;
  }

  /**
   * Gets the the {@code typesForCurves} property.
   * @return the property, not null
   */
  public final Property<Map<String, List<? extends CurveTypeConfiguration>>> typesForCurves() {
    return metaBean().typesForCurves().createProperty(this);
  }

  //-----------------------------------------------------------------------
  @Override
  public CurveGroupConfiguration clone() {
    return JodaBeanUtils.cloneAlways(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      CurveGroupConfiguration other = (CurveGroupConfiguration) obj;
      return (getOrder() == other.getOrder()) &&
          JodaBeanUtils.equal(getTypesForCurves(), other.getTypesForCurves());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(getOrder());
    hash = hash * 31 + JodaBeanUtils.hashCode(getTypesForCurves());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(96);
    buf.append("CurveGroupConfiguration{");
    int len = buf.length();
    toString(buf);
    if (buf.length() > len) {
      buf.setLength(buf.length() - 2);
    }
    buf.append('}');
    return buf.toString();
  }

  protected void toString(StringBuilder buf) {
    buf.append("order").append('=').append(JodaBeanUtils.toString(getOrder())).append(',').append(' ');
    buf.append("typesForCurves").append('=').append(JodaBeanUtils.toString(getTypesForCurves())).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code CurveGroupConfiguration}.
   */
  public static class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code order} property.
     */
    private final MetaProperty<Integer> _order = DirectMetaProperty.ofReadWrite(
        this, "order", CurveGroupConfiguration.class, Integer.TYPE);
    /**
     * The meta-property for the {@code typesForCurves} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<Map<String, List<? extends CurveTypeConfiguration>>> _typesForCurves = DirectMetaProperty.ofReadWrite(
        this, "typesForCurves", CurveGroupConfiguration.class, (Class) Map.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "order",
        "typesForCurves");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 106006350:  // order
          return _order;
        case -1976018156:  // typesForCurves
          return _typesForCurves;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends CurveGroupConfiguration> builder() {
      return new DirectBeanBuilder<CurveGroupConfiguration>(new CurveGroupConfiguration());
    }

    @Override
    public Class<? extends CurveGroupConfiguration> beanType() {
      return CurveGroupConfiguration.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code order} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Integer> order() {
      return _order;
    }

    /**
     * The meta-property for the {@code typesForCurves} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Map<String, List<? extends CurveTypeConfiguration>>> typesForCurves() {
      return _typesForCurves;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 106006350:  // order
          return ((CurveGroupConfiguration) bean).getOrder();
        case -1976018156:  // typesForCurves
          return ((CurveGroupConfiguration) bean).getTypesForCurves();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 106006350:  // order
          ((CurveGroupConfiguration) bean).setOrder((Integer) newValue);
          return;
        case -1976018156:  // typesForCurves
          ((CurveGroupConfiguration) bean).setTypesForCurves((Map<String, List<? extends CurveTypeConfiguration>>) newValue);
          return;
      }
      super.propertySet(bean, propertyName, newValue, quiet);
    }

    @Override
    protected void validate(Bean bean) {
      JodaBeanUtils.notNull(((CurveGroupConfiguration) bean)._typesForCurves, "typesForCurves");
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
