/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.convention;

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

import com.opengamma.core.convention.ConventionType;
import com.opengamma.id.ExternalId;
import com.opengamma.id.ExternalIdBundle;
import com.opengamma.util.ArgumentChecker;
import com.opengamma.util.time.Tenor;

/**
 * Convention for an Ibor leg based on roll date adjuster. This convention should be used only in a IMMSwapConvention.
 */
@BeanDefinition
public class VanillaIborLegRollDateConvention extends FinancialConvention {

  /**
   * Type of the convention.
   */
  public static final ConventionType TYPE = ConventionType.of("VanillaIborLegRollDate");

  /** Serialization version. */
  private static final long serialVersionUID = 1L;

  /**
   * The ibor index.
   */
  @PropertyDefinition(validate = "notNull")
  private ExternalId _iborIndexConvention;
  /**
   * Whether the fixing in advance (true) or in arrears (false).
   */
  @PropertyDefinition
  private boolean _isAdvanceFixing;
  /**
   * The reset tenor of the underlying index
   */
  @PropertyDefinition(validate = "notNull")
  private Tenor _resetTenor;
  /**
   * The stub type.
   */
  @PropertyDefinition(validate = "notNull")
  private StubType _stubType;
  /**
   * Wheter the notional exchanged.
   */
  @PropertyDefinition
  private boolean _isExchangeNotional;
  /**
   * The payment lag in days.
   */
  @PropertyDefinition
  private int _paymentLag;

  /**
   * Creates an instance.
   */
  protected VanillaIborLegRollDateConvention() {
    super();
  }

  /**
   * Creates an instance.
   * 
   * @param name  the convention name, not null
   * @param externalIdBundle  the external identifiers for this convention, not null
   * @param iborIndexConvention  the underlying ibor index convention, not null
   * @param isAdvanceFixing  true if fixing is in advance
   * @param resetTenor  the reset tenor, not null TODO: Remove: in iborIndex
   * @param stubType  the stub type, not null
   * @param isExchangeNotional  true if notional is to be exchanged
   * @param paymentLag  the payment lag in days
   */
  public VanillaIborLegRollDateConvention(
      final String name, final ExternalIdBundle externalIdBundle, final ExternalId iborIndexConvention,
      final boolean isAdvanceFixing, final Tenor resetTenor, final StubType stubType,
      final boolean isExchangeNotional, final int paymentLag) {
    super(name, externalIdBundle);
    setIborIndexConvention(iborIndexConvention);
    setIsAdvanceFixing(isAdvanceFixing);
    setResetTenor(resetTenor);
    setStubType(stubType);
    setIsExchangeNotional(isExchangeNotional);
    setPaymentLag(paymentLag);
  }

  //-------------------------------------------------------------------------
  /**
   * Gets the type identifying this convention.
   * 
   * @return the {@link #TYPE} constant, not null
   */
  @Override
  public ConventionType getConventionType() {
    return TYPE;
  }

  /**
   * Accepts a visitor to manage traversal of the hierarchy.
   *
   * @param <T>  the result type of the visitor
   * @param visitor  the visitor, not null
   * @return the result
   */
  @Override
  public <T> T accept(final FinancialConventionVisitor<T> visitor) {
    ArgumentChecker.notNull(visitor, "visitor");
    return visitor.visitVanillaIborLegRollDateConvention(this);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code VanillaIborLegRollDateConvention}.
   * @return the meta-bean, not null
   */
  public static VanillaIborLegRollDateConvention.Meta meta() {
    return VanillaIborLegRollDateConvention.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(VanillaIborLegRollDateConvention.Meta.INSTANCE);
  }

  @Override
  public VanillaIborLegRollDateConvention.Meta metaBean() {
    return VanillaIborLegRollDateConvention.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the ibor index.
   * @return the value of the property, not null
   */
  public ExternalId getIborIndexConvention() {
    return _iborIndexConvention;
  }

  /**
   * Sets the ibor index.
   * @param iborIndexConvention  the new value of the property, not null
   */
  public void setIborIndexConvention(ExternalId iborIndexConvention) {
    JodaBeanUtils.notNull(iborIndexConvention, "iborIndexConvention");
    this._iborIndexConvention = iborIndexConvention;
  }

  /**
   * Gets the the {@code iborIndexConvention} property.
   * @return the property, not null
   */
  public final Property<ExternalId> iborIndexConvention() {
    return metaBean().iborIndexConvention().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets whether the fixing in advance (true) or in arrears (false).
   * @return the value of the property
   */
  public boolean isIsAdvanceFixing() {
    return _isAdvanceFixing;
  }

  /**
   * Sets whether the fixing in advance (true) or in arrears (false).
   * @param isAdvanceFixing  the new value of the property
   */
  public void setIsAdvanceFixing(boolean isAdvanceFixing) {
    this._isAdvanceFixing = isAdvanceFixing;
  }

  /**
   * Gets the the {@code isAdvanceFixing} property.
   * @return the property, not null
   */
  public final Property<Boolean> isAdvanceFixing() {
    return metaBean().isAdvanceFixing().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the reset tenor of the underlying index
   * @return the value of the property, not null
   */
  public Tenor getResetTenor() {
    return _resetTenor;
  }

  /**
   * Sets the reset tenor of the underlying index
   * @param resetTenor  the new value of the property, not null
   */
  public void setResetTenor(Tenor resetTenor) {
    JodaBeanUtils.notNull(resetTenor, "resetTenor");
    this._resetTenor = resetTenor;
  }

  /**
   * Gets the the {@code resetTenor} property.
   * @return the property, not null
   */
  public final Property<Tenor> resetTenor() {
    return metaBean().resetTenor().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the stub type.
   * @return the value of the property, not null
   */
  public StubType getStubType() {
    return _stubType;
  }

  /**
   * Sets the stub type.
   * @param stubType  the new value of the property, not null
   */
  public void setStubType(StubType stubType) {
    JodaBeanUtils.notNull(stubType, "stubType");
    this._stubType = stubType;
  }

  /**
   * Gets the the {@code stubType} property.
   * @return the property, not null
   */
  public final Property<StubType> stubType() {
    return metaBean().stubType().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets wheter the notional exchanged.
   * @return the value of the property
   */
  public boolean isIsExchangeNotional() {
    return _isExchangeNotional;
  }

  /**
   * Sets wheter the notional exchanged.
   * @param isExchangeNotional  the new value of the property
   */
  public void setIsExchangeNotional(boolean isExchangeNotional) {
    this._isExchangeNotional = isExchangeNotional;
  }

  /**
   * Gets the the {@code isExchangeNotional} property.
   * @return the property, not null
   */
  public final Property<Boolean> isExchangeNotional() {
    return metaBean().isExchangeNotional().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the payment lag in days.
   * @return the value of the property
   */
  public int getPaymentLag() {
    return _paymentLag;
  }

  /**
   * Sets the payment lag in days.
   * @param paymentLag  the new value of the property
   */
  public void setPaymentLag(int paymentLag) {
    this._paymentLag = paymentLag;
  }

  /**
   * Gets the the {@code paymentLag} property.
   * @return the property, not null
   */
  public final Property<Integer> paymentLag() {
    return metaBean().paymentLag().createProperty(this);
  }

  //-----------------------------------------------------------------------
  @Override
  public VanillaIborLegRollDateConvention clone() {
    return JodaBeanUtils.cloneAlways(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      VanillaIborLegRollDateConvention other = (VanillaIborLegRollDateConvention) obj;
      return JodaBeanUtils.equal(getIborIndexConvention(), other.getIborIndexConvention()) &&
          (isIsAdvanceFixing() == other.isIsAdvanceFixing()) &&
          JodaBeanUtils.equal(getResetTenor(), other.getResetTenor()) &&
          JodaBeanUtils.equal(getStubType(), other.getStubType()) &&
          (isIsExchangeNotional() == other.isIsExchangeNotional()) &&
          (getPaymentLag() == other.getPaymentLag()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash += hash * 31 + JodaBeanUtils.hashCode(getIborIndexConvention());
    hash += hash * 31 + JodaBeanUtils.hashCode(isIsAdvanceFixing());
    hash += hash * 31 + JodaBeanUtils.hashCode(getResetTenor());
    hash += hash * 31 + JodaBeanUtils.hashCode(getStubType());
    hash += hash * 31 + JodaBeanUtils.hashCode(isIsExchangeNotional());
    hash += hash * 31 + JodaBeanUtils.hashCode(getPaymentLag());
    return hash ^ super.hashCode();
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(224);
    buf.append("VanillaIborLegRollDateConvention{");
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
    buf.append("iborIndexConvention").append('=').append(JodaBeanUtils.toString(getIborIndexConvention())).append(',').append(' ');
    buf.append("isAdvanceFixing").append('=').append(JodaBeanUtils.toString(isIsAdvanceFixing())).append(',').append(' ');
    buf.append("resetTenor").append('=').append(JodaBeanUtils.toString(getResetTenor())).append(',').append(' ');
    buf.append("stubType").append('=').append(JodaBeanUtils.toString(getStubType())).append(',').append(' ');
    buf.append("isExchangeNotional").append('=').append(JodaBeanUtils.toString(isIsExchangeNotional())).append(',').append(' ');
    buf.append("paymentLag").append('=').append(JodaBeanUtils.toString(getPaymentLag())).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code VanillaIborLegRollDateConvention}.
   */
  public static class Meta extends FinancialConvention.Meta {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code iborIndexConvention} property.
     */
    private final MetaProperty<ExternalId> _iborIndexConvention = DirectMetaProperty.ofReadWrite(
        this, "iborIndexConvention", VanillaIborLegRollDateConvention.class, ExternalId.class);
    /**
     * The meta-property for the {@code isAdvanceFixing} property.
     */
    private final MetaProperty<Boolean> _isAdvanceFixing = DirectMetaProperty.ofReadWrite(
        this, "isAdvanceFixing", VanillaIborLegRollDateConvention.class, Boolean.TYPE);
    /**
     * The meta-property for the {@code resetTenor} property.
     */
    private final MetaProperty<Tenor> _resetTenor = DirectMetaProperty.ofReadWrite(
        this, "resetTenor", VanillaIborLegRollDateConvention.class, Tenor.class);
    /**
     * The meta-property for the {@code stubType} property.
     */
    private final MetaProperty<StubType> _stubType = DirectMetaProperty.ofReadWrite(
        this, "stubType", VanillaIborLegRollDateConvention.class, StubType.class);
    /**
     * The meta-property for the {@code isExchangeNotional} property.
     */
    private final MetaProperty<Boolean> _isExchangeNotional = DirectMetaProperty.ofReadWrite(
        this, "isExchangeNotional", VanillaIborLegRollDateConvention.class, Boolean.TYPE);
    /**
     * The meta-property for the {@code paymentLag} property.
     */
    private final MetaProperty<Integer> _paymentLag = DirectMetaProperty.ofReadWrite(
        this, "paymentLag", VanillaIborLegRollDateConvention.class, Integer.TYPE);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "iborIndexConvention",
        "isAdvanceFixing",
        "resetTenor",
        "stubType",
        "isExchangeNotional",
        "paymentLag");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -1542426233:  // iborIndexConvention
          return _iborIndexConvention;
        case 1363941829:  // isAdvanceFixing
          return _isAdvanceFixing;
        case -1687017807:  // resetTenor
          return _resetTenor;
        case 1873675528:  // stubType
          return _stubType;
        case 348962765:  // isExchangeNotional
          return _isExchangeNotional;
        case 1612870060:  // paymentLag
          return _paymentLag;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends VanillaIborLegRollDateConvention> builder() {
      return new DirectBeanBuilder<VanillaIborLegRollDateConvention>(new VanillaIborLegRollDateConvention());
    }

    @Override
    public Class<? extends VanillaIborLegRollDateConvention> beanType() {
      return VanillaIborLegRollDateConvention.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code iborIndexConvention} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ExternalId> iborIndexConvention() {
      return _iborIndexConvention;
    }

    /**
     * The meta-property for the {@code isAdvanceFixing} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Boolean> isAdvanceFixing() {
      return _isAdvanceFixing;
    }

    /**
     * The meta-property for the {@code resetTenor} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Tenor> resetTenor() {
      return _resetTenor;
    }

    /**
     * The meta-property for the {@code stubType} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<StubType> stubType() {
      return _stubType;
    }

    /**
     * The meta-property for the {@code isExchangeNotional} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Boolean> isExchangeNotional() {
      return _isExchangeNotional;
    }

    /**
     * The meta-property for the {@code paymentLag} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Integer> paymentLag() {
      return _paymentLag;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -1542426233:  // iborIndexConvention
          return ((VanillaIborLegRollDateConvention) bean).getIborIndexConvention();
        case 1363941829:  // isAdvanceFixing
          return ((VanillaIborLegRollDateConvention) bean).isIsAdvanceFixing();
        case -1687017807:  // resetTenor
          return ((VanillaIborLegRollDateConvention) bean).getResetTenor();
        case 1873675528:  // stubType
          return ((VanillaIborLegRollDateConvention) bean).getStubType();
        case 348962765:  // isExchangeNotional
          return ((VanillaIborLegRollDateConvention) bean).isIsExchangeNotional();
        case 1612870060:  // paymentLag
          return ((VanillaIborLegRollDateConvention) bean).getPaymentLag();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -1542426233:  // iborIndexConvention
          ((VanillaIborLegRollDateConvention) bean).setIborIndexConvention((ExternalId) newValue);
          return;
        case 1363941829:  // isAdvanceFixing
          ((VanillaIborLegRollDateConvention) bean).setIsAdvanceFixing((Boolean) newValue);
          return;
        case -1687017807:  // resetTenor
          ((VanillaIborLegRollDateConvention) bean).setResetTenor((Tenor) newValue);
          return;
        case 1873675528:  // stubType
          ((VanillaIborLegRollDateConvention) bean).setStubType((StubType) newValue);
          return;
        case 348962765:  // isExchangeNotional
          ((VanillaIborLegRollDateConvention) bean).setIsExchangeNotional((Boolean) newValue);
          return;
        case 1612870060:  // paymentLag
          ((VanillaIborLegRollDateConvention) bean).setPaymentLag((Integer) newValue);
          return;
      }
      super.propertySet(bean, propertyName, newValue, quiet);
    }

    @Override
    protected void validate(Bean bean) {
      JodaBeanUtils.notNull(((VanillaIborLegRollDateConvention) bean)._iborIndexConvention, "iborIndexConvention");
      JodaBeanUtils.notNull(((VanillaIborLegRollDateConvention) bean)._resetTenor, "resetTenor");
      JodaBeanUtils.notNull(((VanillaIborLegRollDateConvention) bean)._stubType, "stubType");
      super.validate(bean);
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
