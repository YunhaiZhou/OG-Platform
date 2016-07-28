/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.sesame.component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

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

import com.opengamma.component.ComponentRepository;
import com.opengamma.component.factory.AbstractComponentFactory;
import com.opengamma.sesame.engine.DataViewRunnerResource;
import com.opengamma.sesame.engine.DefaultEngine;
import com.opengamma.sesame.engine.DefaultViewRunner;
import com.opengamma.sesame.engine.ViewFactory;
import com.opengamma.sesame.engine.ViewRunner;
import com.opengamma.sesame.marketdata.builders.MarketDataEnvironmentFactory;

/**
 * Component factory to build an instance of {@link ViewRunner} and expose it via REST.
 */
@BeanDefinition
public class ViewRunnerComponentFactory extends AbstractComponentFactory {

  /** The classifier under which the view runner will be published. */
  @PropertyDefinition(validate = "notEmpty")
  private String _classifier;

  /** Factory for creating views to perform calculations. */
  @PropertyDefinition(validate = "notNull")
  private ViewFactory _viewFactory;

  /** Builds market data that's required by the calculations but not supplied by the user. */
  @PropertyDefinition(validate = "notNull")
  private MarketDataEnvironmentFactory _marketDataEnvironmentFactory;

  /** Executor for running concurrent tasks. */
  @PropertyDefinition(validate = "notNull")
  private ExecutorService _executor;

  @Override
  public void init(ComponentRepository repo, LinkedHashMap<String, String> configuration) throws Exception {
    DefaultEngine engine = new DefaultEngine(_viewFactory, _marketDataEnvironmentFactory, _executor);
    DefaultViewRunner viewRunner = new DefaultViewRunner(engine);
    repo.registerComponent(ViewRunner.class, _classifier, viewRunner);
    DataViewRunnerResource viewRunnerResource = new DataViewRunnerResource(viewRunner);
    repo.getRestComponents().publishResource(viewRunnerResource);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code ViewRunnerComponentFactory}.
   * @return the meta-bean, not null
   */
  public static ViewRunnerComponentFactory.Meta meta() {
    return ViewRunnerComponentFactory.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(ViewRunnerComponentFactory.Meta.INSTANCE);
  }

  @Override
  public ViewRunnerComponentFactory.Meta metaBean() {
    return ViewRunnerComponentFactory.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the classifier under which the view runner will be published.
   * @return the value of the property, not empty
   */
  public String getClassifier() {
    return _classifier;
  }

  /**
   * Sets the classifier under which the view runner will be published.
   * @param classifier  the new value of the property, not empty
   */
  public void setClassifier(String classifier) {
    JodaBeanUtils.notEmpty(classifier, "classifier");
    this._classifier = classifier;
  }

  /**
   * Gets the the {@code classifier} property.
   * @return the property, not null
   */
  public final Property<String> classifier() {
    return metaBean().classifier().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets factory for creating views to perform calculations.
   * @return the value of the property, not null
   */
  public ViewFactory getViewFactory() {
    return _viewFactory;
  }

  /**
   * Sets factory for creating views to perform calculations.
   * @param viewFactory  the new value of the property, not null
   */
  public void setViewFactory(ViewFactory viewFactory) {
    JodaBeanUtils.notNull(viewFactory, "viewFactory");
    this._viewFactory = viewFactory;
  }

  /**
   * Gets the the {@code viewFactory} property.
   * @return the property, not null
   */
  public final Property<ViewFactory> viewFactory() {
    return metaBean().viewFactory().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets builds market data that's required by the calculations but not supplied by the user.
   * @return the value of the property, not null
   */
  public MarketDataEnvironmentFactory getMarketDataEnvironmentFactory() {
    return _marketDataEnvironmentFactory;
  }

  /**
   * Sets builds market data that's required by the calculations but not supplied by the user.
   * @param marketDataEnvironmentFactory  the new value of the property, not null
   */
  public void setMarketDataEnvironmentFactory(MarketDataEnvironmentFactory marketDataEnvironmentFactory) {
    JodaBeanUtils.notNull(marketDataEnvironmentFactory, "marketDataEnvironmentFactory");
    this._marketDataEnvironmentFactory = marketDataEnvironmentFactory;
  }

  /**
   * Gets the the {@code marketDataEnvironmentFactory} property.
   * @return the property, not null
   */
  public final Property<MarketDataEnvironmentFactory> marketDataEnvironmentFactory() {
    return metaBean().marketDataEnvironmentFactory().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets executor for running concurrent tasks.
   * @return the value of the property, not null
   */
  public ExecutorService getExecutor() {
    return _executor;
  }

  /**
   * Sets executor for running concurrent tasks.
   * @param executor  the new value of the property, not null
   */
  public void setExecutor(ExecutorService executor) {
    JodaBeanUtils.notNull(executor, "executor");
    this._executor = executor;
  }

  /**
   * Gets the the {@code executor} property.
   * @return the property, not null
   */
  public final Property<ExecutorService> executor() {
    return metaBean().executor().createProperty(this);
  }

  //-----------------------------------------------------------------------
  @Override
  public ViewRunnerComponentFactory clone() {
    return JodaBeanUtils.cloneAlways(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      ViewRunnerComponentFactory other = (ViewRunnerComponentFactory) obj;
      return JodaBeanUtils.equal(getClassifier(), other.getClassifier()) &&
          JodaBeanUtils.equal(getViewFactory(), other.getViewFactory()) &&
          JodaBeanUtils.equal(getMarketDataEnvironmentFactory(), other.getMarketDataEnvironmentFactory()) &&
          JodaBeanUtils.equal(getExecutor(), other.getExecutor()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = hash * 31 + JodaBeanUtils.hashCode(getClassifier());
    hash = hash * 31 + JodaBeanUtils.hashCode(getViewFactory());
    hash = hash * 31 + JodaBeanUtils.hashCode(getMarketDataEnvironmentFactory());
    hash = hash * 31 + JodaBeanUtils.hashCode(getExecutor());
    return hash ^ super.hashCode();
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(160);
    buf.append("ViewRunnerComponentFactory{");
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
    buf.append("classifier").append('=').append(JodaBeanUtils.toString(getClassifier())).append(',').append(' ');
    buf.append("viewFactory").append('=').append(JodaBeanUtils.toString(getViewFactory())).append(',').append(' ');
    buf.append("marketDataEnvironmentFactory").append('=').append(JodaBeanUtils.toString(getMarketDataEnvironmentFactory())).append(',').append(' ');
    buf.append("executor").append('=').append(JodaBeanUtils.toString(getExecutor())).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code ViewRunnerComponentFactory}.
   */
  public static class Meta extends AbstractComponentFactory.Meta {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code classifier} property.
     */
    private final MetaProperty<String> _classifier = DirectMetaProperty.ofReadWrite(
        this, "classifier", ViewRunnerComponentFactory.class, String.class);
    /**
     * The meta-property for the {@code viewFactory} property.
     */
    private final MetaProperty<ViewFactory> _viewFactory = DirectMetaProperty.ofReadWrite(
        this, "viewFactory", ViewRunnerComponentFactory.class, ViewFactory.class);
    /**
     * The meta-property for the {@code marketDataEnvironmentFactory} property.
     */
    private final MetaProperty<MarketDataEnvironmentFactory> _marketDataEnvironmentFactory = DirectMetaProperty.ofReadWrite(
        this, "marketDataEnvironmentFactory", ViewRunnerComponentFactory.class, MarketDataEnvironmentFactory.class);
    /**
     * The meta-property for the {@code executor} property.
     */
    private final MetaProperty<ExecutorService> _executor = DirectMetaProperty.ofReadWrite(
        this, "executor", ViewRunnerComponentFactory.class, ExecutorService.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "classifier",
        "viewFactory",
        "marketDataEnvironmentFactory",
        "executor");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -281470431:  // classifier
          return _classifier;
        case -1101448539:  // viewFactory
          return _viewFactory;
        case 964996125:  // marketDataEnvironmentFactory
          return _marketDataEnvironmentFactory;
        case 2043017427:  // executor
          return _executor;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends ViewRunnerComponentFactory> builder() {
      return new DirectBeanBuilder<ViewRunnerComponentFactory>(new ViewRunnerComponentFactory());
    }

    @Override
    public Class<? extends ViewRunnerComponentFactory> beanType() {
      return ViewRunnerComponentFactory.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code classifier} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> classifier() {
      return _classifier;
    }

    /**
     * The meta-property for the {@code viewFactory} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ViewFactory> viewFactory() {
      return _viewFactory;
    }

    /**
     * The meta-property for the {@code marketDataEnvironmentFactory} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<MarketDataEnvironmentFactory> marketDataEnvironmentFactory() {
      return _marketDataEnvironmentFactory;
    }

    /**
     * The meta-property for the {@code executor} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ExecutorService> executor() {
      return _executor;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -281470431:  // classifier
          return ((ViewRunnerComponentFactory) bean).getClassifier();
        case -1101448539:  // viewFactory
          return ((ViewRunnerComponentFactory) bean).getViewFactory();
        case 964996125:  // marketDataEnvironmentFactory
          return ((ViewRunnerComponentFactory) bean).getMarketDataEnvironmentFactory();
        case 2043017427:  // executor
          return ((ViewRunnerComponentFactory) bean).getExecutor();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -281470431:  // classifier
          ((ViewRunnerComponentFactory) bean).setClassifier((String) newValue);
          return;
        case -1101448539:  // viewFactory
          ((ViewRunnerComponentFactory) bean).setViewFactory((ViewFactory) newValue);
          return;
        case 964996125:  // marketDataEnvironmentFactory
          ((ViewRunnerComponentFactory) bean).setMarketDataEnvironmentFactory((MarketDataEnvironmentFactory) newValue);
          return;
        case 2043017427:  // executor
          ((ViewRunnerComponentFactory) bean).setExecutor((ExecutorService) newValue);
          return;
      }
      super.propertySet(bean, propertyName, newValue, quiet);
    }

    @Override
    protected void validate(Bean bean) {
      JodaBeanUtils.notEmpty(((ViewRunnerComponentFactory) bean)._classifier, "classifier");
      JodaBeanUtils.notNull(((ViewRunnerComponentFactory) bean)._viewFactory, "viewFactory");
      JodaBeanUtils.notNull(((ViewRunnerComponentFactory) bean)._marketDataEnvironmentFactory, "marketDataEnvironmentFactory");
      JodaBeanUtils.notNull(((ViewRunnerComponentFactory) bean)._executor, "executor");
      super.validate(bean);
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
