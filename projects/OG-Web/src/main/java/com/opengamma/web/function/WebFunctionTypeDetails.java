/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.web.function;

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

/**
 * Information for displaying in the green screens
 * re functions.
 */
@BeanDefinition
public class WebFunctionTypeDetails extends DirectBean {
 
  @PropertyDefinition
  private String _simpleName;
  
  @PropertyDefinition
  private String _fullyQualifiedName;
  
  @PropertyDefinition
  private boolean _parameterized;
  
  @PropertyDefinition
  private List<List<String>> _parameters;
  
  
  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code WebFunctionTypeDetails}.
   * @return the meta-bean, not null
   */
  public static WebFunctionTypeDetails.Meta meta() {
    return WebFunctionTypeDetails.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(WebFunctionTypeDetails.Meta.INSTANCE);
  }

  @Override
  public WebFunctionTypeDetails.Meta metaBean() {
    return WebFunctionTypeDetails.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the simpleName.
   * @return the value of the property
   */
  public String getSimpleName() {
    return _simpleName;
  }

  /**
   * Sets the simpleName.
   * @param simpleName  the new value of the property
   */
  public void setSimpleName(String simpleName) {
    this._simpleName = simpleName;
  }

  /**
   * Gets the the {@code simpleName} property.
   * @return the property, not null
   */
  public final Property<String> simpleName() {
    return metaBean().simpleName().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the fullyQualifiedName.
   * @return the value of the property
   */
  public String getFullyQualifiedName() {
    return _fullyQualifiedName;
  }

  /**
   * Sets the fullyQualifiedName.
   * @param fullyQualifiedName  the new value of the property
   */
  public void setFullyQualifiedName(String fullyQualifiedName) {
    this._fullyQualifiedName = fullyQualifiedName;
  }

  /**
   * Gets the the {@code fullyQualifiedName} property.
   * @return the property, not null
   */
  public final Property<String> fullyQualifiedName() {
    return metaBean().fullyQualifiedName().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the parameterized.
   * @return the value of the property
   */
  public boolean isParameterized() {
    return _parameterized;
  }

  /**
   * Sets the parameterized.
   * @param parameterized  the new value of the property
   */
  public void setParameterized(boolean parameterized) {
    this._parameterized = parameterized;
  }

  /**
   * Gets the the {@code parameterized} property.
   * @return the property, not null
   */
  public final Property<Boolean> parameterized() {
    return metaBean().parameterized().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the parameters.
   * @return the value of the property
   */
  public List<List<String>> getParameters() {
    return _parameters;
  }

  /**
   * Sets the parameters.
   * @param parameters  the new value of the property
   */
  public void setParameters(List<List<String>> parameters) {
    this._parameters = parameters;
  }

  /**
   * Gets the the {@code parameters} property.
   * @return the property, not null
   */
  public final Property<List<List<String>>> parameters() {
    return metaBean().parameters().createProperty(this);
  }

  //-----------------------------------------------------------------------
  @Override
  public WebFunctionTypeDetails clone() {
    return JodaBeanUtils.cloneAlways(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      WebFunctionTypeDetails other = (WebFunctionTypeDetails) obj;
      return JodaBeanUtils.equal(getSimpleName(), other.getSimpleName()) &&
          JodaBeanUtils.equal(getFullyQualifiedName(), other.getFullyQualifiedName()) &&
          (isParameterized() == other.isParameterized()) &&
          JodaBeanUtils.equal(getParameters(), other.getParameters());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getSimpleName());
    hash += hash * 31 + JodaBeanUtils.hashCode(getFullyQualifiedName());
    hash += hash * 31 + JodaBeanUtils.hashCode(isParameterized());
    hash += hash * 31 + JodaBeanUtils.hashCode(getParameters());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(160);
    buf.append("WebFunctionTypeDetails{");
    int len = buf.length();
    toString(buf);
    if (buf.length() > len) {
      buf.setLength(buf.length() - 2);
    }
    buf.append('}');
    return buf.toString();
  }

  protected void toString(StringBuilder buf) {
    buf.append("simpleName").append('=').append(JodaBeanUtils.toString(getSimpleName())).append(',').append(' ');
    buf.append("fullyQualifiedName").append('=').append(JodaBeanUtils.toString(getFullyQualifiedName())).append(',').append(' ');
    buf.append("parameterized").append('=').append(JodaBeanUtils.toString(isParameterized())).append(',').append(' ');
    buf.append("parameters").append('=').append(JodaBeanUtils.toString(getParameters())).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code WebFunctionTypeDetails}.
   */
  public static class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code simpleName} property.
     */
    private final MetaProperty<String> _simpleName = DirectMetaProperty.ofReadWrite(
        this, "simpleName", WebFunctionTypeDetails.class, String.class);
    /**
     * The meta-property for the {@code fullyQualifiedName} property.
     */
    private final MetaProperty<String> _fullyQualifiedName = DirectMetaProperty.ofReadWrite(
        this, "fullyQualifiedName", WebFunctionTypeDetails.class, String.class);
    /**
     * The meta-property for the {@code parameterized} property.
     */
    private final MetaProperty<Boolean> _parameterized = DirectMetaProperty.ofReadWrite(
        this, "parameterized", WebFunctionTypeDetails.class, Boolean.TYPE);
    /**
     * The meta-property for the {@code parameters} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<List<List<String>>> _parameters = DirectMetaProperty.ofReadWrite(
        this, "parameters", WebFunctionTypeDetails.class, (Class) List.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "simpleName",
        "fullyQualifiedName",
        "parameterized",
        "parameters");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -1431767203:  // simpleName
          return _simpleName;
        case 288467357:  // fullyQualifiedName
          return _fullyQualifiedName;
        case -378779463:  // parameterized
          return _parameterized;
        case 458736106:  // parameters
          return _parameters;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends WebFunctionTypeDetails> builder() {
      return new DirectBeanBuilder<WebFunctionTypeDetails>(new WebFunctionTypeDetails());
    }

    @Override
    public Class<? extends WebFunctionTypeDetails> beanType() {
      return WebFunctionTypeDetails.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code simpleName} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> simpleName() {
      return _simpleName;
    }

    /**
     * The meta-property for the {@code fullyQualifiedName} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> fullyQualifiedName() {
      return _fullyQualifiedName;
    }

    /**
     * The meta-property for the {@code parameterized} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Boolean> parameterized() {
      return _parameterized;
    }

    /**
     * The meta-property for the {@code parameters} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<List<List<String>>> parameters() {
      return _parameters;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -1431767203:  // simpleName
          return ((WebFunctionTypeDetails) bean).getSimpleName();
        case 288467357:  // fullyQualifiedName
          return ((WebFunctionTypeDetails) bean).getFullyQualifiedName();
        case -378779463:  // parameterized
          return ((WebFunctionTypeDetails) bean).isParameterized();
        case 458736106:  // parameters
          return ((WebFunctionTypeDetails) bean).getParameters();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -1431767203:  // simpleName
          ((WebFunctionTypeDetails) bean).setSimpleName((String) newValue);
          return;
        case 288467357:  // fullyQualifiedName
          ((WebFunctionTypeDetails) bean).setFullyQualifiedName((String) newValue);
          return;
        case -378779463:  // parameterized
          ((WebFunctionTypeDetails) bean).setParameterized((Boolean) newValue);
          return;
        case 458736106:  // parameters
          ((WebFunctionTypeDetails) bean).setParameters((List<List<String>>) newValue);
          return;
      }
      super.propertySet(bean, propertyName, newValue, quiet);
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
