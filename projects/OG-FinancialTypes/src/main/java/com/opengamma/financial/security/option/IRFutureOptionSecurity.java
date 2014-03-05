/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.security.option;

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

import com.opengamma.financial.security.FinancialSecurity;
import com.opengamma.financial.security.FinancialSecurityVisitor;
import com.opengamma.id.ExternalId;
import com.opengamma.util.money.Currency;
import com.opengamma.util.time.Expiry;

/**
 * A security for IR future options.
 */
@BeanDefinition
public class IRFutureOptionSecurity extends FinancialSecurity {

  /** Serialization version. */
  private static final long serialVersionUID = 1L;

  /**
   * The security type.
   */
  public static final String SECURITY_TYPE = "IRFUTURE_OPTION";

  /**
   * The exchange.
   */
  @PropertyDefinition(validate = "notNull")
  private String _exchange;
  /**
   * The expiry.
   */
  @PropertyDefinition(validate = "notNull")
  private Expiry _expiry;
  /**
   * The exercise type.
   */
  @PropertyDefinition(validate = "notNull")
  private ExerciseType _exerciseType;
  /**
   * The underlying identifier.
   */
  @PropertyDefinition(validate = "notNull")
  private ExternalId _underlyingId;
  /**
   * The point value.
   */
  @PropertyDefinition
  private double _pointValue;
  /**
   * The margined flag.
   */
  @PropertyDefinition
  private boolean _margined;
  /**
   * The currency.
   */
  @PropertyDefinition(validate = "notNull")
  private Currency _currency;
  /**
   * The strike.
   */
  @PropertyDefinition
  private double _strike;
  /**
   * The option type.
   */
  @PropertyDefinition(validate = "notNull")
  private OptionType _optionType;

  IRFutureOptionSecurity() { //For builder
    super(SECURITY_TYPE);
  }

  public IRFutureOptionSecurity(String exchange, Expiry expiry, ExerciseType exerciseType, ExternalId underlyingIdentifier,
      double pointValue, boolean margined, Currency currency, double strike, OptionType optionType) {
    super(SECURITY_TYPE);
    setExchange(exchange);
    setExpiry(expiry);
    setExerciseType(exerciseType);
    setUnderlyingId(underlyingIdentifier);
    setPointValue(pointValue);
    setMargined(margined);
    setCurrency(currency);
    setStrike(strike);
    setOptionType(optionType);
  }

  //-------------------------------------------------------------------------
  @Override
  public final <T> T accept(FinancialSecurityVisitor<T> visitor) {
    return visitor.visitIRFutureOptionSecurity(this);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code IRFutureOptionSecurity}.
   * @return the meta-bean, not null
   */
  public static IRFutureOptionSecurity.Meta meta() {
    return IRFutureOptionSecurity.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(IRFutureOptionSecurity.Meta.INSTANCE);
  }

  @Override
  public IRFutureOptionSecurity.Meta metaBean() {
    return IRFutureOptionSecurity.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the exchange.
   * @return the value of the property, not null
   */
  public String getExchange() {
    return _exchange;
  }

  /**
   * Sets the exchange.
   * @param exchange  the new value of the property, not null
   */
  public void setExchange(String exchange) {
    JodaBeanUtils.notNull(exchange, "exchange");
    this._exchange = exchange;
  }

  /**
   * Gets the the {@code exchange} property.
   * @return the property, not null
   */
  public final Property<String> exchange() {
    return metaBean().exchange().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the expiry.
   * @return the value of the property, not null
   */
  public Expiry getExpiry() {
    return _expiry;
  }

  /**
   * Sets the expiry.
   * @param expiry  the new value of the property, not null
   */
  public void setExpiry(Expiry expiry) {
    JodaBeanUtils.notNull(expiry, "expiry");
    this._expiry = expiry;
  }

  /**
   * Gets the the {@code expiry} property.
   * @return the property, not null
   */
  public final Property<Expiry> expiry() {
    return metaBean().expiry().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the exercise type.
   * @return the value of the property, not null
   */
  public ExerciseType getExerciseType() {
    return _exerciseType;
  }

  /**
   * Sets the exercise type.
   * @param exerciseType  the new value of the property, not null
   */
  public void setExerciseType(ExerciseType exerciseType) {
    JodaBeanUtils.notNull(exerciseType, "exerciseType");
    this._exerciseType = exerciseType;
  }

  /**
   * Gets the the {@code exerciseType} property.
   * @return the property, not null
   */
  public final Property<ExerciseType> exerciseType() {
    return metaBean().exerciseType().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the underlying identifier.
   * @return the value of the property, not null
   */
  public ExternalId getUnderlyingId() {
    return _underlyingId;
  }

  /**
   * Sets the underlying identifier.
   * @param underlyingId  the new value of the property, not null
   */
  public void setUnderlyingId(ExternalId underlyingId) {
    JodaBeanUtils.notNull(underlyingId, "underlyingId");
    this._underlyingId = underlyingId;
  }

  /**
   * Gets the the {@code underlyingId} property.
   * @return the property, not null
   */
  public final Property<ExternalId> underlyingId() {
    return metaBean().underlyingId().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the point value.
   * @return the value of the property
   */
  public double getPointValue() {
    return _pointValue;
  }

  /**
   * Sets the point value.
   * @param pointValue  the new value of the property
   */
  public void setPointValue(double pointValue) {
    this._pointValue = pointValue;
  }

  /**
   * Gets the the {@code pointValue} property.
   * @return the property, not null
   */
  public final Property<Double> pointValue() {
    return metaBean().pointValue().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the margined flag.
   * @return the value of the property
   */
  public boolean isMargined() {
    return _margined;
  }

  /**
   * Sets the margined flag.
   * @param margined  the new value of the property
   */
  public void setMargined(boolean margined) {
    this._margined = margined;
  }

  /**
   * Gets the the {@code margined} property.
   * @return the property, not null
   */
  public final Property<Boolean> margined() {
    return metaBean().margined().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the currency.
   * @return the value of the property, not null
   */
  public Currency getCurrency() {
    return _currency;
  }

  /**
   * Sets the currency.
   * @param currency  the new value of the property, not null
   */
  public void setCurrency(Currency currency) {
    JodaBeanUtils.notNull(currency, "currency");
    this._currency = currency;
  }

  /**
   * Gets the the {@code currency} property.
   * @return the property, not null
   */
  public final Property<Currency> currency() {
    return metaBean().currency().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the strike.
   * @return the value of the property
   */
  public double getStrike() {
    return _strike;
  }

  /**
   * Sets the strike.
   * @param strike  the new value of the property
   */
  public void setStrike(double strike) {
    this._strike = strike;
  }

  /**
   * Gets the the {@code strike} property.
   * @return the property, not null
   */
  public final Property<Double> strike() {
    return metaBean().strike().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the option type.
   * @return the value of the property, not null
   */
  public OptionType getOptionType() {
    return _optionType;
  }

  /**
   * Sets the option type.
   * @param optionType  the new value of the property, not null
   */
  public void setOptionType(OptionType optionType) {
    JodaBeanUtils.notNull(optionType, "optionType");
    this._optionType = optionType;
  }

  /**
   * Gets the the {@code optionType} property.
   * @return the property, not null
   */
  public final Property<OptionType> optionType() {
    return metaBean().optionType().createProperty(this);
  }

  //-----------------------------------------------------------------------
  @Override
  public IRFutureOptionSecurity clone() {
    return JodaBeanUtils.cloneAlways(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      IRFutureOptionSecurity other = (IRFutureOptionSecurity) obj;
      return JodaBeanUtils.equal(getExchange(), other.getExchange()) &&
          JodaBeanUtils.equal(getExpiry(), other.getExpiry()) &&
          JodaBeanUtils.equal(getExerciseType(), other.getExerciseType()) &&
          JodaBeanUtils.equal(getUnderlyingId(), other.getUnderlyingId()) &&
          JodaBeanUtils.equal(getPointValue(), other.getPointValue()) &&
          (isMargined() == other.isMargined()) &&
          JodaBeanUtils.equal(getCurrency(), other.getCurrency()) &&
          JodaBeanUtils.equal(getStrike(), other.getStrike()) &&
          JodaBeanUtils.equal(getOptionType(), other.getOptionType()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash += hash * 31 + JodaBeanUtils.hashCode(getExchange());
    hash += hash * 31 + JodaBeanUtils.hashCode(getExpiry());
    hash += hash * 31 + JodaBeanUtils.hashCode(getExerciseType());
    hash += hash * 31 + JodaBeanUtils.hashCode(getUnderlyingId());
    hash += hash * 31 + JodaBeanUtils.hashCode(getPointValue());
    hash += hash * 31 + JodaBeanUtils.hashCode(isMargined());
    hash += hash * 31 + JodaBeanUtils.hashCode(getCurrency());
    hash += hash * 31 + JodaBeanUtils.hashCode(getStrike());
    hash += hash * 31 + JodaBeanUtils.hashCode(getOptionType());
    return hash ^ super.hashCode();
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(320);
    buf.append("IRFutureOptionSecurity{");
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
    buf.append("exchange").append('=').append(JodaBeanUtils.toString(getExchange())).append(',').append(' ');
    buf.append("expiry").append('=').append(JodaBeanUtils.toString(getExpiry())).append(',').append(' ');
    buf.append("exerciseType").append('=').append(JodaBeanUtils.toString(getExerciseType())).append(',').append(' ');
    buf.append("underlyingId").append('=').append(JodaBeanUtils.toString(getUnderlyingId())).append(',').append(' ');
    buf.append("pointValue").append('=').append(JodaBeanUtils.toString(getPointValue())).append(',').append(' ');
    buf.append("margined").append('=').append(JodaBeanUtils.toString(isMargined())).append(',').append(' ');
    buf.append("currency").append('=').append(JodaBeanUtils.toString(getCurrency())).append(',').append(' ');
    buf.append("strike").append('=').append(JodaBeanUtils.toString(getStrike())).append(',').append(' ');
    buf.append("optionType").append('=').append(JodaBeanUtils.toString(getOptionType())).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code IRFutureOptionSecurity}.
   */
  public static class Meta extends FinancialSecurity.Meta {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code exchange} property.
     */
    private final MetaProperty<String> _exchange = DirectMetaProperty.ofReadWrite(
        this, "exchange", IRFutureOptionSecurity.class, String.class);
    /**
     * The meta-property for the {@code expiry} property.
     */
    private final MetaProperty<Expiry> _expiry = DirectMetaProperty.ofReadWrite(
        this, "expiry", IRFutureOptionSecurity.class, Expiry.class);
    /**
     * The meta-property for the {@code exerciseType} property.
     */
    private final MetaProperty<ExerciseType> _exerciseType = DirectMetaProperty.ofReadWrite(
        this, "exerciseType", IRFutureOptionSecurity.class, ExerciseType.class);
    /**
     * The meta-property for the {@code underlyingId} property.
     */
    private final MetaProperty<ExternalId> _underlyingId = DirectMetaProperty.ofReadWrite(
        this, "underlyingId", IRFutureOptionSecurity.class, ExternalId.class);
    /**
     * The meta-property for the {@code pointValue} property.
     */
    private final MetaProperty<Double> _pointValue = DirectMetaProperty.ofReadWrite(
        this, "pointValue", IRFutureOptionSecurity.class, Double.TYPE);
    /**
     * The meta-property for the {@code margined} property.
     */
    private final MetaProperty<Boolean> _margined = DirectMetaProperty.ofReadWrite(
        this, "margined", IRFutureOptionSecurity.class, Boolean.TYPE);
    /**
     * The meta-property for the {@code currency} property.
     */
    private final MetaProperty<Currency> _currency = DirectMetaProperty.ofReadWrite(
        this, "currency", IRFutureOptionSecurity.class, Currency.class);
    /**
     * The meta-property for the {@code strike} property.
     */
    private final MetaProperty<Double> _strike = DirectMetaProperty.ofReadWrite(
        this, "strike", IRFutureOptionSecurity.class, Double.TYPE);
    /**
     * The meta-property for the {@code optionType} property.
     */
    private final MetaProperty<OptionType> _optionType = DirectMetaProperty.ofReadWrite(
        this, "optionType", IRFutureOptionSecurity.class, OptionType.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "exchange",
        "expiry",
        "exerciseType",
        "underlyingId",
        "pointValue",
        "margined",
        "currency",
        "strike",
        "optionType");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 1989774883:  // exchange
          return _exchange;
        case -1289159373:  // expiry
          return _expiry;
        case -466331342:  // exerciseType
          return _exerciseType;
        case -771625640:  // underlyingId
          return _underlyingId;
        case 1257391553:  // pointValue
          return _pointValue;
        case 243392205:  // margined
          return _margined;
        case 575402001:  // currency
          return _currency;
        case -891985998:  // strike
          return _strike;
        case 1373587791:  // optionType
          return _optionType;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends IRFutureOptionSecurity> builder() {
      return new DirectBeanBuilder<IRFutureOptionSecurity>(new IRFutureOptionSecurity());
    }

    @Override
    public Class<? extends IRFutureOptionSecurity> beanType() {
      return IRFutureOptionSecurity.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code exchange} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> exchange() {
      return _exchange;
    }

    /**
     * The meta-property for the {@code expiry} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Expiry> expiry() {
      return _expiry;
    }

    /**
     * The meta-property for the {@code exerciseType} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ExerciseType> exerciseType() {
      return _exerciseType;
    }

    /**
     * The meta-property for the {@code underlyingId} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ExternalId> underlyingId() {
      return _underlyingId;
    }

    /**
     * The meta-property for the {@code pointValue} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Double> pointValue() {
      return _pointValue;
    }

    /**
     * The meta-property for the {@code margined} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Boolean> margined() {
      return _margined;
    }

    /**
     * The meta-property for the {@code currency} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Currency> currency() {
      return _currency;
    }

    /**
     * The meta-property for the {@code strike} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Double> strike() {
      return _strike;
    }

    /**
     * The meta-property for the {@code optionType} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<OptionType> optionType() {
      return _optionType;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 1989774883:  // exchange
          return ((IRFutureOptionSecurity) bean).getExchange();
        case -1289159373:  // expiry
          return ((IRFutureOptionSecurity) bean).getExpiry();
        case -466331342:  // exerciseType
          return ((IRFutureOptionSecurity) bean).getExerciseType();
        case -771625640:  // underlyingId
          return ((IRFutureOptionSecurity) bean).getUnderlyingId();
        case 1257391553:  // pointValue
          return ((IRFutureOptionSecurity) bean).getPointValue();
        case 243392205:  // margined
          return ((IRFutureOptionSecurity) bean).isMargined();
        case 575402001:  // currency
          return ((IRFutureOptionSecurity) bean).getCurrency();
        case -891985998:  // strike
          return ((IRFutureOptionSecurity) bean).getStrike();
        case 1373587791:  // optionType
          return ((IRFutureOptionSecurity) bean).getOptionType();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 1989774883:  // exchange
          ((IRFutureOptionSecurity) bean).setExchange((String) newValue);
          return;
        case -1289159373:  // expiry
          ((IRFutureOptionSecurity) bean).setExpiry((Expiry) newValue);
          return;
        case -466331342:  // exerciseType
          ((IRFutureOptionSecurity) bean).setExerciseType((ExerciseType) newValue);
          return;
        case -771625640:  // underlyingId
          ((IRFutureOptionSecurity) bean).setUnderlyingId((ExternalId) newValue);
          return;
        case 1257391553:  // pointValue
          ((IRFutureOptionSecurity) bean).setPointValue((Double) newValue);
          return;
        case 243392205:  // margined
          ((IRFutureOptionSecurity) bean).setMargined((Boolean) newValue);
          return;
        case 575402001:  // currency
          ((IRFutureOptionSecurity) bean).setCurrency((Currency) newValue);
          return;
        case -891985998:  // strike
          ((IRFutureOptionSecurity) bean).setStrike((Double) newValue);
          return;
        case 1373587791:  // optionType
          ((IRFutureOptionSecurity) bean).setOptionType((OptionType) newValue);
          return;
      }
      super.propertySet(bean, propertyName, newValue, quiet);
    }

    @Override
    protected void validate(Bean bean) {
      JodaBeanUtils.notNull(((IRFutureOptionSecurity) bean)._exchange, "exchange");
      JodaBeanUtils.notNull(((IRFutureOptionSecurity) bean)._expiry, "expiry");
      JodaBeanUtils.notNull(((IRFutureOptionSecurity) bean)._exerciseType, "exerciseType");
      JodaBeanUtils.notNull(((IRFutureOptionSecurity) bean)._underlyingId, "underlyingId");
      JodaBeanUtils.notNull(((IRFutureOptionSecurity) bean)._currency, "currency");
      JodaBeanUtils.notNull(((IRFutureOptionSecurity) bean)._optionType, "optionType");
      super.validate(bean);
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
