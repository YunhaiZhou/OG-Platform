/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.batch;

import java.util.Map;

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

import com.opengamma.id.UniqueId;
import com.opengamma.util.ArgumentChecker;
import com.opengamma.util.paging.PagingRequest;

/**
 * Request containing options for getting a single batch data set.
 * <p>
 * Each batch data set is potentially large, thus options are available
 * to filter the resulting document.
 * <p>
 * This class is mutable and not thread-safe.
 */
@BeanDefinition
public class BatchGetRequest extends DirectBean {

  /**
   * The unique identifier of the batch.
   * This must not be null for a valid search,
   */
  @PropertyDefinition
  private UniqueId _uniqueId;
  /**
   * The request for paging the main batch data.
   * By default, the entire data set will be returned.
   */
  @PropertyDefinition
  private PagingRequest _dataPagingRequest = PagingRequest.ALL;
  /**
   * The request for paging the batch errors.
   * By default, no error data will be returned, although the total count will be.
   */
  @PropertyDefinition
  private PagingRequest _errorPagingRequest = PagingRequest.NONE;

  /**
   * Creates an instance.
   */
  public BatchGetRequest() {
  }

  /**
   * Creates an instance specifying a unique identifier.
   * <p>
   * With no further customisation this will retrieve all the batch data and
   * the total error count.
   * 
   * @param uniqueId  the batch unique identifier, not null
   */
  public BatchGetRequest(UniqueId uniqueId) {
    ArgumentChecker.notNull(uniqueId, "uniqueId");
    setUniqueId(uniqueId);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code BatchGetRequest}.
   * @return the meta-bean, not null
   */
  public static BatchGetRequest.Meta meta() {
    return BatchGetRequest.Meta.INSTANCE;
  }
  static {
    JodaBeanUtils.registerMetaBean(BatchGetRequest.Meta.INSTANCE);
  }

  @Override
  public BatchGetRequest.Meta metaBean() {
    return BatchGetRequest.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName, boolean quiet) {
    switch (propertyName.hashCode()) {
      case -294460212:  // uniqueId
        return getUniqueId();
      case -100490791:  // dataPagingRequest
        return getDataPagingRequest();
      case 704383035:  // errorPagingRequest
        return getErrorPagingRequest();
    }
    return super.propertyGet(propertyName, quiet);
  }

  @Override
  protected void propertySet(String propertyName, Object newValue, boolean quiet) {
    switch (propertyName.hashCode()) {
      case -294460212:  // uniqueId
        setUniqueId((UniqueId) newValue);
        return;
      case -100490791:  // dataPagingRequest
        setDataPagingRequest((PagingRequest) newValue);
        return;
      case 704383035:  // errorPagingRequest
        setErrorPagingRequest((PagingRequest) newValue);
        return;
    }
    super.propertySet(propertyName, newValue, quiet);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      BatchGetRequest other = (BatchGetRequest) obj;
      return JodaBeanUtils.equal(getUniqueId(), other.getUniqueId()) &&
          JodaBeanUtils.equal(getDataPagingRequest(), other.getDataPagingRequest()) &&
          JodaBeanUtils.equal(getErrorPagingRequest(), other.getErrorPagingRequest());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getUniqueId());
    hash += hash * 31 + JodaBeanUtils.hashCode(getDataPagingRequest());
    hash += hash * 31 + JodaBeanUtils.hashCode(getErrorPagingRequest());
    return hash;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the unique identifier of the batch.
   * This must not be null for a valid search,
   * @return the value of the property
   */
  public UniqueId getUniqueId() {
    return _uniqueId;
  }

  /**
   * Sets the unique identifier of the batch.
   * This must not be null for a valid search,
   * @param uniqueId  the new value of the property
   */
  public void setUniqueId(UniqueId uniqueId) {
    this._uniqueId = uniqueId;
  }

  /**
   * Gets the the {@code uniqueId} property.
   * This must not be null for a valid search,
   * @return the property, not null
   */
  public final Property<UniqueId> uniqueId() {
    return metaBean().uniqueId().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the request for paging the main batch data.
   * By default, the entire data set will be returned.
   * @return the value of the property
   */
  public PagingRequest getDataPagingRequest() {
    return _dataPagingRequest;
  }

  /**
   * Sets the request for paging the main batch data.
   * By default, the entire data set will be returned.
   * @param dataPagingRequest  the new value of the property
   */
  public void setDataPagingRequest(PagingRequest dataPagingRequest) {
    this._dataPagingRequest = dataPagingRequest;
  }

  /**
   * Gets the the {@code dataPagingRequest} property.
   * By default, the entire data set will be returned.
   * @return the property, not null
   */
  public final Property<PagingRequest> dataPagingRequest() {
    return metaBean().dataPagingRequest().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the request for paging the batch errors.
   * By default, no error data will be returned, although the total count will be.
   * @return the value of the property
   */
  public PagingRequest getErrorPagingRequest() {
    return _errorPagingRequest;
  }

  /**
   * Sets the request for paging the batch errors.
   * By default, no error data will be returned, although the total count will be.
   * @param errorPagingRequest  the new value of the property
   */
  public void setErrorPagingRequest(PagingRequest errorPagingRequest) {
    this._errorPagingRequest = errorPagingRequest;
  }

  /**
   * Gets the the {@code errorPagingRequest} property.
   * By default, no error data will be returned, although the total count will be.
   * @return the property, not null
   */
  public final Property<PagingRequest> errorPagingRequest() {
    return metaBean().errorPagingRequest().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code BatchGetRequest}.
   */
  public static class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code uniqueId} property.
     */
    private final MetaProperty<UniqueId> _uniqueId = DirectMetaProperty.ofReadWrite(
        this, "uniqueId", BatchGetRequest.class, UniqueId.class);
    /**
     * The meta-property for the {@code dataPagingRequest} property.
     */
    private final MetaProperty<PagingRequest> _dataPagingRequest = DirectMetaProperty.ofReadWrite(
        this, "dataPagingRequest", BatchGetRequest.class, PagingRequest.class);
    /**
     * The meta-property for the {@code errorPagingRequest} property.
     */
    private final MetaProperty<PagingRequest> _errorPagingRequest = DirectMetaProperty.ofReadWrite(
        this, "errorPagingRequest", BatchGetRequest.class, PagingRequest.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<Object>> _map = new DirectMetaPropertyMap(
        this, null,
        "uniqueId",
        "dataPagingRequest",
        "errorPagingRequest");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -294460212:  // uniqueId
          return _uniqueId;
        case -100490791:  // dataPagingRequest
          return _dataPagingRequest;
        case 704383035:  // errorPagingRequest
          return _errorPagingRequest;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends BatchGetRequest> builder() {
      return new DirectBeanBuilder<BatchGetRequest>(new BatchGetRequest());
    }

    @Override
    public Class<? extends BatchGetRequest> beanType() {
      return BatchGetRequest.class;
    }

    @Override
    public Map<String, MetaProperty<Object>> metaPropertyMap() {
      return _map;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code uniqueId} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<UniqueId> uniqueId() {
      return _uniqueId;
    }

    /**
     * The meta-property for the {@code dataPagingRequest} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<PagingRequest> dataPagingRequest() {
      return _dataPagingRequest;
    }

    /**
     * The meta-property for the {@code errorPagingRequest} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<PagingRequest> errorPagingRequest() {
      return _errorPagingRequest;
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
