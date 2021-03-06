/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.sesame.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.annotation.Nullable;

import org.joda.beans.Bean;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.ImmutableConstructor;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.opengamma.sesame.function.scenarios.ScenarioDefinition;
import com.opengamma.sesame.function.scenarios.ScenarioFunction;
import com.opengamma.util.ArgumentChecker;

/**
 * Configuration object that defines the columns and outputs in a view.
 * <p>
 * A view is defined in terms of {@link ViewColumn} objects, each of
 * which has a {@link ViewOutput} for each target input type.
 * Stand-alone outputs, not associated with input targets, can also be specified.
 */
@BeanDefinition
public final class ViewConfig implements ImmutableBean {

  /** The view name. */
  @PropertyDefinition(validate = "notNull")
  private final String _name;
  
  /** The default configuration for the entire view. */
  @PropertyDefinition(validate = "notNull")
  private final FunctionModelConfig _defaultConfig;

  /** The columns in the view. */
  @PropertyDefinition(validate = "notNull")
  private final ImmutableList<ViewColumn> _columns;

  /** The list of outputs that stand-alone and are not connected to a column. */
  @PropertyDefinition(validate = "notNull")
  private final ImmutableList<NonPortfolioOutput> _nonPortfolioOutputs;

  /** Definition of the scenario that will be applied to the calculations when the view is executed. */
  @PropertyDefinition(validate = "notNull")
  private final ScenarioDefinition _scenarioDefinition;

  /**
   * Creates an instance.
   * 
   * @param name  the view name, not null
   * @param defaultConfig  the default configuration, not null
   * @param columns  the list of columns, not null
   */
  public ViewConfig(String name, FunctionModelConfig defaultConfig, List<ViewColumn> columns) {
    this(name, defaultConfig, columns, ImmutableList.<NonPortfolioOutput>of(), ScenarioDefinition.EMPTY);
  }

  /**
   * Creates an instance.
   *
   * @param name  the view name
   * @param defaultConfig  the default configuration
   * @param columns  the list of columns
   * @param nonPortfolioOutputs  the list of stand-alone outputs
   * @param scenarioDefinition  definition of the scenario that will be run when the view is executed
   */
  @ImmutableConstructor
  public ViewConfig(String name,
                    FunctionModelConfig defaultConfig,
                    List<ViewColumn> columns,
                    List<NonPortfolioOutput> nonPortfolioOutputs,
                    // TODO I don't like this but there are old versions serialized in regression tests without it
                    // need to investigate Joda beans migrations to see if they offer a cleaner solution
                    @Nullable ScenarioDefinition scenarioDefinition) {
    _scenarioDefinition = scenarioDefinition != null ?
        scenarioDefinition : ScenarioDefinition.EMPTY;
    _defaultConfig = ArgumentChecker.notNull(defaultConfig, "defaultConfig");
    _name = ArgumentChecker.notEmpty(name, "name");
    _columns = ImmutableList.copyOf(ArgumentChecker.notNull(columns, "columns"));
    _nonPortfolioOutputs = ImmutableList.copyOf(ArgumentChecker.notNull(nonPortfolioOutputs, "nonPortfolioOutputs"));

    Set<String> nonPortfolioOutputNames = Sets.newHashSetWithExpectedSize(nonPortfolioOutputs.size());
    for (NonPortfolioOutput output : nonPortfolioOutputs) {
      if (!nonPortfolioOutputNames.add(output.getName())) {
        throw new IllegalArgumentException("Non-portfolio output names must be unique, '" +
                                               output.getName() + "' is repeated");
      }
    }
  }

  /**
   * Creates a new view configuration derived from this one that will apply a scenario when performing calculations.
   * <p>
   * If this view configuration already contains a scenario then it will be merged with the scenario definition
   * parameter to create the scenario for the resulting view.
   *
   * @param scenarioDefinition  definition of the scenario
   * @return  a new view configuration derived from this one that contains the scenario definition and
   *   scenario functions required to execute the scenario
   */
  public ViewConfig withScenario(ScenarioDefinition scenarioDefinition) {
    ArgumentChecker.notNull(scenarioDefinition, "scenarioDefinition");

    ScenarioDefinition mergedDefinition = _scenarioDefinition.mergedWith(scenarioDefinition);
    FunctionModelConfig decoratedConfig = _defaultConfig;

    for (Class<? extends ScenarioFunction<?, ?>> decoratorType : mergedDefinition.getFunctionTypes()) {
      decoratedConfig = decoratedConfig.decoratedWith(decoratorType);
    }
    return new ViewConfig(_name, decoratedConfig, _columns, _nonPortfolioOutputs, mergedDefinition);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code ViewConfig}.
   * @return the meta-bean, not null
   */
  public static ViewConfig.Meta meta() {
    return ViewConfig.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(ViewConfig.Meta.INSTANCE);
  }

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static ViewConfig.Builder builder() {
    return new ViewConfig.Builder();
  }

  @Override
  public ViewConfig.Meta metaBean() {
    return ViewConfig.Meta.INSTANCE;
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
   * Gets the view name.
   * @return the value of the property, not null
   */
  public String getName() {
    return _name;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the default configuration for the entire view.
   * @return the value of the property, not null
   */
  public FunctionModelConfig getDefaultConfig() {
    return _defaultConfig;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the columns in the view.
   * @return the value of the property, not null
   */
  public ImmutableList<ViewColumn> getColumns() {
    return _columns;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the list of outputs that stand-alone and are not connected to a column.
   * @return the value of the property, not null
   */
  public ImmutableList<NonPortfolioOutput> getNonPortfolioOutputs() {
    return _nonPortfolioOutputs;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets definition of the scenario that will be applied to the calculations when the view is executed.
   * @return the value of the property, not null
   */
  public ScenarioDefinition getScenarioDefinition() {
    return _scenarioDefinition;
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
      ViewConfig other = (ViewConfig) obj;
      return JodaBeanUtils.equal(getName(), other.getName()) &&
          JodaBeanUtils.equal(getDefaultConfig(), other.getDefaultConfig()) &&
          JodaBeanUtils.equal(getColumns(), other.getColumns()) &&
          JodaBeanUtils.equal(getNonPortfolioOutputs(), other.getNonPortfolioOutputs()) &&
          JodaBeanUtils.equal(getScenarioDefinition(), other.getScenarioDefinition());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(getName());
    hash = hash * 31 + JodaBeanUtils.hashCode(getDefaultConfig());
    hash = hash * 31 + JodaBeanUtils.hashCode(getColumns());
    hash = hash * 31 + JodaBeanUtils.hashCode(getNonPortfolioOutputs());
    hash = hash * 31 + JodaBeanUtils.hashCode(getScenarioDefinition());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(192);
    buf.append("ViewConfig{");
    buf.append("name").append('=').append(getName()).append(',').append(' ');
    buf.append("defaultConfig").append('=').append(getDefaultConfig()).append(',').append(' ');
    buf.append("columns").append('=').append(getColumns()).append(',').append(' ');
    buf.append("nonPortfolioOutputs").append('=').append(getNonPortfolioOutputs()).append(',').append(' ');
    buf.append("scenarioDefinition").append('=').append(JodaBeanUtils.toString(getScenarioDefinition()));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code ViewConfig}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code name} property.
     */
    private final MetaProperty<String> _name = DirectMetaProperty.ofImmutable(
        this, "name", ViewConfig.class, String.class);
    /**
     * The meta-property for the {@code defaultConfig} property.
     */
    private final MetaProperty<FunctionModelConfig> _defaultConfig = DirectMetaProperty.ofImmutable(
        this, "defaultConfig", ViewConfig.class, FunctionModelConfig.class);
    /**
     * The meta-property for the {@code columns} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<ImmutableList<ViewColumn>> _columns = DirectMetaProperty.ofImmutable(
        this, "columns", ViewConfig.class, (Class) ImmutableList.class);
    /**
     * The meta-property for the {@code nonPortfolioOutputs} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<ImmutableList<NonPortfolioOutput>> _nonPortfolioOutputs = DirectMetaProperty.ofImmutable(
        this, "nonPortfolioOutputs", ViewConfig.class, (Class) ImmutableList.class);
    /**
     * The meta-property for the {@code scenarioDefinition} property.
     */
    private final MetaProperty<ScenarioDefinition> _scenarioDefinition = DirectMetaProperty.ofImmutable(
        this, "scenarioDefinition", ViewConfig.class, ScenarioDefinition.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "name",
        "defaultConfig",
        "columns",
        "nonPortfolioOutputs",
        "scenarioDefinition");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 3373707:  // name
          return _name;
        case 510972131:  // defaultConfig
          return _defaultConfig;
        case 949721053:  // columns
          return _columns;
        case 171658775:  // nonPortfolioOutputs
          return _nonPortfolioOutputs;
        case -690925309:  // scenarioDefinition
          return _scenarioDefinition;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public ViewConfig.Builder builder() {
      return new ViewConfig.Builder();
    }

    @Override
    public Class<? extends ViewConfig> beanType() {
      return ViewConfig.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code name} property.
     * @return the meta-property, not null
     */
    public MetaProperty<String> name() {
      return _name;
    }

    /**
     * The meta-property for the {@code defaultConfig} property.
     * @return the meta-property, not null
     */
    public MetaProperty<FunctionModelConfig> defaultConfig() {
      return _defaultConfig;
    }

    /**
     * The meta-property for the {@code columns} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ImmutableList<ViewColumn>> columns() {
      return _columns;
    }

    /**
     * The meta-property for the {@code nonPortfolioOutputs} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ImmutableList<NonPortfolioOutput>> nonPortfolioOutputs() {
      return _nonPortfolioOutputs;
    }

    /**
     * The meta-property for the {@code scenarioDefinition} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ScenarioDefinition> scenarioDefinition() {
      return _scenarioDefinition;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 3373707:  // name
          return ((ViewConfig) bean).getName();
        case 510972131:  // defaultConfig
          return ((ViewConfig) bean).getDefaultConfig();
        case 949721053:  // columns
          return ((ViewConfig) bean).getColumns();
        case 171658775:  // nonPortfolioOutputs
          return ((ViewConfig) bean).getNonPortfolioOutputs();
        case -690925309:  // scenarioDefinition
          return ((ViewConfig) bean).getScenarioDefinition();
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
   * The bean-builder for {@code ViewConfig}.
   */
  public static final class Builder extends DirectFieldsBeanBuilder<ViewConfig> {

    private String _name;
    private FunctionModelConfig _defaultConfig;
    private List<ViewColumn> _columns = new ArrayList<ViewColumn>();
    private List<NonPortfolioOutput> _nonPortfolioOutputs = new ArrayList<NonPortfolioOutput>();
    private ScenarioDefinition _scenarioDefinition;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(ViewConfig beanToCopy) {
      this._name = beanToCopy.getName();
      this._defaultConfig = beanToCopy.getDefaultConfig();
      this._columns = new ArrayList<ViewColumn>(beanToCopy.getColumns());
      this._nonPortfolioOutputs = new ArrayList<NonPortfolioOutput>(beanToCopy.getNonPortfolioOutputs());
      this._scenarioDefinition = beanToCopy.getScenarioDefinition();
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 3373707:  // name
          return _name;
        case 510972131:  // defaultConfig
          return _defaultConfig;
        case 949721053:  // columns
          return _columns;
        case 171658775:  // nonPortfolioOutputs
          return _nonPortfolioOutputs;
        case -690925309:  // scenarioDefinition
          return _scenarioDefinition;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 3373707:  // name
          this._name = (String) newValue;
          break;
        case 510972131:  // defaultConfig
          this._defaultConfig = (FunctionModelConfig) newValue;
          break;
        case 949721053:  // columns
          this._columns = (List<ViewColumn>) newValue;
          break;
        case 171658775:  // nonPortfolioOutputs
          this._nonPortfolioOutputs = (List<NonPortfolioOutput>) newValue;
          break;
        case -690925309:  // scenarioDefinition
          this._scenarioDefinition = (ScenarioDefinition) newValue;
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
      super.setString(property, value);
      return this;
    }

    @Override
    public Builder setAll(Map<String, ? extends Object> propertyValueMap) {
      super.setAll(propertyValueMap);
      return this;
    }

    @Override
    public ViewConfig build() {
      return new ViewConfig(
          _name,
          _defaultConfig,
          _columns,
          _nonPortfolioOutputs,
          _scenarioDefinition);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the {@code name} property in the builder.
     * @param name  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder name(String name) {
      JodaBeanUtils.notNull(name, "name");
      this._name = name;
      return this;
    }

    /**
     * Sets the {@code defaultConfig} property in the builder.
     * @param defaultConfig  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder defaultConfig(FunctionModelConfig defaultConfig) {
      JodaBeanUtils.notNull(defaultConfig, "defaultConfig");
      this._defaultConfig = defaultConfig;
      return this;
    }

    /**
     * Sets the {@code columns} property in the builder.
     * @param columns  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder columns(List<ViewColumn> columns) {
      JodaBeanUtils.notNull(columns, "columns");
      this._columns = columns;
      return this;
    }

    /**
     * Sets the {@code columns} property in the builder
     * from an array of objects.
     * @param columns  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder columns(ViewColumn... columns) {
      return columns(Arrays.asList(columns));
    }

    /**
     * Sets the {@code nonPortfolioOutputs} property in the builder.
     * @param nonPortfolioOutputs  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder nonPortfolioOutputs(List<NonPortfolioOutput> nonPortfolioOutputs) {
      JodaBeanUtils.notNull(nonPortfolioOutputs, "nonPortfolioOutputs");
      this._nonPortfolioOutputs = nonPortfolioOutputs;
      return this;
    }

    /**
     * Sets the {@code nonPortfolioOutputs} property in the builder
     * from an array of objects.
     * @param nonPortfolioOutputs  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder nonPortfolioOutputs(NonPortfolioOutput... nonPortfolioOutputs) {
      return nonPortfolioOutputs(Arrays.asList(nonPortfolioOutputs));
    }

    /**
     * Sets the {@code scenarioDefinition} property in the builder.
     * @param scenarioDefinition  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder scenarioDefinition(ScenarioDefinition scenarioDefinition) {
      JodaBeanUtils.notNull(scenarioDefinition, "scenarioDefinition");
      this._scenarioDefinition = scenarioDefinition;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(192);
      buf.append("ViewConfig.Builder{");
      buf.append("name").append('=').append(JodaBeanUtils.toString(_name)).append(',').append(' ');
      buf.append("defaultConfig").append('=').append(JodaBeanUtils.toString(_defaultConfig)).append(',').append(' ');
      buf.append("columns").append('=').append(JodaBeanUtils.toString(_columns)).append(',').append(' ');
      buf.append("nonPortfolioOutputs").append('=').append(JodaBeanUtils.toString(_nonPortfolioOutputs)).append(',').append(' ');
      buf.append("scenarioDefinition").append('=').append(JodaBeanUtils.toString(_scenarioDefinition));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
