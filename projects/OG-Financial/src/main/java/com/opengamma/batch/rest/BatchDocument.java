/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.batch.rest;

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
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;

import com.opengamma.batch.domain.BatchError;
import com.opengamma.engine.ComputationTargetSpecification;
import com.opengamma.engine.view.ViewResultEntry;
import com.opengamma.id.ObjectId;
import com.opengamma.util.ArgumentChecker;
import com.opengamma.util.paging.Paging;

/**
 * A document used to pass into and out of the batch master.
 * <p>
 * This class is mutable and not thread-safe.
 */
@BeanDefinition
public class BatchDocument extends DirectBean {

  /**
   * The batch unique identifier.
   * This field is managed by the master but must be set for updates.
   */
  @PropertyDefinition
  private ObjectId _batchId;
  /**
   * The batch date, not null.
   */
  @PropertyDefinition
  private LocalDate _observationDate;
  /**
   * The batch time, such as LDN_CLOSE, not null.
   */
  @PropertyDefinition
  private String _observationTime;
  /**
   * The status of the batch, determining if it is running, not null.
   */
  @PropertyDefinition
  private boolean _complete;
  /**
   * The master process host, not null.
   */
  @PropertyDefinition
  private String _masterProcessHost;
  /**
   * The instant that the batch run was first created, not null.
   */
  @PropertyDefinition
  private Instant _creationInstant;
  /**
   * The instant that the batch run started, not null.
   */
  @PropertyDefinition
  private Instant _startInstant;
  /**
   * The instant that the batch run ended, not null.
   */
  @PropertyDefinition
  private Instant _endInstant;
  /**
   * The number of restarts.
   */
  @PropertyDefinition
  private int _numRestarts;
  /**
   * The paging information for the main batch data, not null if correctly created.
   */
  @PropertyDefinition
  private Paging _dataPaging;
  /**
   * The paged list of main batch data, may be empty, not null.
   */
  @PropertyDefinition
  private final List<ViewResultEntry> _data = new ArrayList<ViewResultEntry>();
  /**
   * The paging information for the errors, not null if correctly created.
   */
  @PropertyDefinition
  private Paging _errorsPaging;
  /**
   * The paged list of errors, may be empty, not null.
   */
  @PropertyDefinition
  private final List<BatchError> _errors = new ArrayList<BatchError>();

  /**
   * Creates an instance.
   */
  public BatchDocument() {
  }

  /**
   * Creates an instance specifying a unique identifier.
   * 
   * @param batchId  the batch unique identifier, not null
   */
  public BatchDocument(ObjectId batchId) {
    ArgumentChecker.notNull(batchId, "batchId");
    setBatchId(batchId);
  }

  //-------------------------------------------------------------------------
  /**
   * Gets the unique entry by {@code ComputationTargetSpecification}.
   * Mainly useful in tests because in general uniqueness cannot be assumed.
   * 
   * @param spec  the computation target, not null
   * @return only result in batch master for this computation target, null if not found
   * @throws IllegalArgumentException if there is more than 1 entry in batch master for the
   *  given computation target
   */
  public ViewResultEntry getData(ComputationTargetSpecification spec) {
    ViewResultEntry result = null;
    for (ViewResultEntry item : _data) {
      ComputationTargetSpecification match = item.getComputedValue().getSpecification().getTargetSpecification();
      if (spec.equals(match)) {
        if (result != null) {
          throw new IllegalArgumentException("More than 1 entry in batch DB for " + spec);
        }
        result = item;
      }
    }
    return result;
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code BatchDocument}.
   * @return the meta-bean, not null
   */
  public static BatchDocument.Meta meta() {
    return BatchDocument.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(BatchDocument.Meta.INSTANCE);
  }

  @Override
  public BatchDocument.Meta metaBean() {
    return BatchDocument.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the batch unique identifier.
   * This field is managed by the master but must be set for updates.
   * @return the value of the property
   */
  public ObjectId getBatchId() {
    return _batchId;
  }

  /**
   * Sets the batch unique identifier.
   * This field is managed by the master but must be set for updates.
   * @param batchId  the new value of the property
   */
  public void setBatchId(ObjectId batchId) {
    this._batchId = batchId;
  }

  /**
   * Gets the the {@code batchId} property.
   * This field is managed by the master but must be set for updates.
   * @return the property, not null
   */
  public final Property<ObjectId> batchId() {
    return metaBean().batchId().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the batch date, not null.
   * @return the value of the property
   */
  public LocalDate getObservationDate() {
    return _observationDate;
  }

  /**
   * Sets the batch date, not null.
   * @param observationDate  the new value of the property
   */
  public void setObservationDate(LocalDate observationDate) {
    this._observationDate = observationDate;
  }

  /**
   * Gets the the {@code observationDate} property.
   * @return the property, not null
   */
  public final Property<LocalDate> observationDate() {
    return metaBean().observationDate().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the batch time, such as LDN_CLOSE, not null.
   * @return the value of the property
   */
  public String getObservationTime() {
    return _observationTime;
  }

  /**
   * Sets the batch time, such as LDN_CLOSE, not null.
   * @param observationTime  the new value of the property
   */
  public void setObservationTime(String observationTime) {
    this._observationTime = observationTime;
  }

  /**
   * Gets the the {@code observationTime} property.
   * @return the property, not null
   */
  public final Property<String> observationTime() {
    return metaBean().observationTime().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the status of the batch, determining if it is running, not null.
   * @return the value of the property
   */
  public boolean isComplete() {
    return _complete;
  }

  /**
   * Sets the status of the batch, determining if it is running, not null.
   * @param complete  the new value of the property
   */
  public void setComplete(boolean complete) {
    this._complete = complete;
  }

  /**
   * Gets the the {@code complete} property.
   * @return the property, not null
   */
  public final Property<Boolean> complete() {
    return metaBean().complete().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the master process host, not null.
   * @return the value of the property
   */
  public String getMasterProcessHost() {
    return _masterProcessHost;
  }

  /**
   * Sets the master process host, not null.
   * @param masterProcessHost  the new value of the property
   */
  public void setMasterProcessHost(String masterProcessHost) {
    this._masterProcessHost = masterProcessHost;
  }

  /**
   * Gets the the {@code masterProcessHost} property.
   * @return the property, not null
   */
  public final Property<String> masterProcessHost() {
    return metaBean().masterProcessHost().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the instant that the batch run was first created, not null.
   * @return the value of the property
   */
  public Instant getCreationInstant() {
    return _creationInstant;
  }

  /**
   * Sets the instant that the batch run was first created, not null.
   * @param creationInstant  the new value of the property
   */
  public void setCreationInstant(Instant creationInstant) {
    this._creationInstant = creationInstant;
  }

  /**
   * Gets the the {@code creationInstant} property.
   * @return the property, not null
   */
  public final Property<Instant> creationInstant() {
    return metaBean().creationInstant().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the instant that the batch run started, not null.
   * @return the value of the property
   */
  public Instant getStartInstant() {
    return _startInstant;
  }

  /**
   * Sets the instant that the batch run started, not null.
   * @param startInstant  the new value of the property
   */
  public void setStartInstant(Instant startInstant) {
    this._startInstant = startInstant;
  }

  /**
   * Gets the the {@code startInstant} property.
   * @return the property, not null
   */
  public final Property<Instant> startInstant() {
    return metaBean().startInstant().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the instant that the batch run ended, not null.
   * @return the value of the property
   */
  public Instant getEndInstant() {
    return _endInstant;
  }

  /**
   * Sets the instant that the batch run ended, not null.
   * @param endInstant  the new value of the property
   */
  public void setEndInstant(Instant endInstant) {
    this._endInstant = endInstant;
  }

  /**
   * Gets the the {@code endInstant} property.
   * @return the property, not null
   */
  public final Property<Instant> endInstant() {
    return metaBean().endInstant().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the number of restarts.
   * @return the value of the property
   */
  public int getNumRestarts() {
    return _numRestarts;
  }

  /**
   * Sets the number of restarts.
   * @param numRestarts  the new value of the property
   */
  public void setNumRestarts(int numRestarts) {
    this._numRestarts = numRestarts;
  }

  /**
   * Gets the the {@code numRestarts} property.
   * @return the property, not null
   */
  public final Property<Integer> numRestarts() {
    return metaBean().numRestarts().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the paging information for the main batch data, not null if correctly created.
   * @return the value of the property
   */
  public Paging getDataPaging() {
    return _dataPaging;
  }

  /**
   * Sets the paging information for the main batch data, not null if correctly created.
   * @param dataPaging  the new value of the property
   */
  public void setDataPaging(Paging dataPaging) {
    this._dataPaging = dataPaging;
  }

  /**
   * Gets the the {@code dataPaging} property.
   * @return the property, not null
   */
  public final Property<Paging> dataPaging() {
    return metaBean().dataPaging().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the paged list of main batch data, may be empty, not null.
   * @return the value of the property, not null
   */
  public List<ViewResultEntry> getData() {
    return _data;
  }

  /**
   * Sets the paged list of main batch data, may be empty, not null.
   * @param data  the new value of the property, not null
   */
  public void setData(List<ViewResultEntry> data) {
    JodaBeanUtils.notNull(data, "data");
    this._data.clear();
    this._data.addAll(data);
  }

  /**
   * Gets the the {@code data} property.
   * @return the property, not null
   */
  public final Property<List<ViewResultEntry>> data() {
    return metaBean().data().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the paging information for the errors, not null if correctly created.
   * @return the value of the property
   */
  public Paging getErrorsPaging() {
    return _errorsPaging;
  }

  /**
   * Sets the paging information for the errors, not null if correctly created.
   * @param errorsPaging  the new value of the property
   */
  public void setErrorsPaging(Paging errorsPaging) {
    this._errorsPaging = errorsPaging;
  }

  /**
   * Gets the the {@code errorsPaging} property.
   * @return the property, not null
   */
  public final Property<Paging> errorsPaging() {
    return metaBean().errorsPaging().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the paged list of errors, may be empty, not null.
   * @return the value of the property, not null
   */
  public List<BatchError> getErrors() {
    return _errors;
  }

  /**
   * Sets the paged list of errors, may be empty, not null.
   * @param errors  the new value of the property, not null
   */
  public void setErrors(List<BatchError> errors) {
    JodaBeanUtils.notNull(errors, "errors");
    this._errors.clear();
    this._errors.addAll(errors);
  }

  /**
   * Gets the the {@code errors} property.
   * @return the property, not null
   */
  public final Property<List<BatchError>> errors() {
    return metaBean().errors().createProperty(this);
  }

  //-----------------------------------------------------------------------
  @Override
  public BatchDocument clone() {
    return JodaBeanUtils.cloneAlways(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      BatchDocument other = (BatchDocument) obj;
      return JodaBeanUtils.equal(getBatchId(), other.getBatchId()) &&
          JodaBeanUtils.equal(getObservationDate(), other.getObservationDate()) &&
          JodaBeanUtils.equal(getObservationTime(), other.getObservationTime()) &&
          (isComplete() == other.isComplete()) &&
          JodaBeanUtils.equal(getMasterProcessHost(), other.getMasterProcessHost()) &&
          JodaBeanUtils.equal(getCreationInstant(), other.getCreationInstant()) &&
          JodaBeanUtils.equal(getStartInstant(), other.getStartInstant()) &&
          JodaBeanUtils.equal(getEndInstant(), other.getEndInstant()) &&
          (getNumRestarts() == other.getNumRestarts()) &&
          JodaBeanUtils.equal(getDataPaging(), other.getDataPaging()) &&
          JodaBeanUtils.equal(getData(), other.getData()) &&
          JodaBeanUtils.equal(getErrorsPaging(), other.getErrorsPaging()) &&
          JodaBeanUtils.equal(getErrors(), other.getErrors());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getBatchId());
    hash += hash * 31 + JodaBeanUtils.hashCode(getObservationDate());
    hash += hash * 31 + JodaBeanUtils.hashCode(getObservationTime());
    hash += hash * 31 + JodaBeanUtils.hashCode(isComplete());
    hash += hash * 31 + JodaBeanUtils.hashCode(getMasterProcessHost());
    hash += hash * 31 + JodaBeanUtils.hashCode(getCreationInstant());
    hash += hash * 31 + JodaBeanUtils.hashCode(getStartInstant());
    hash += hash * 31 + JodaBeanUtils.hashCode(getEndInstant());
    hash += hash * 31 + JodaBeanUtils.hashCode(getNumRestarts());
    hash += hash * 31 + JodaBeanUtils.hashCode(getDataPaging());
    hash += hash * 31 + JodaBeanUtils.hashCode(getData());
    hash += hash * 31 + JodaBeanUtils.hashCode(getErrorsPaging());
    hash += hash * 31 + JodaBeanUtils.hashCode(getErrors());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(448);
    buf.append("BatchDocument{");
    int len = buf.length();
    toString(buf);
    if (buf.length() > len) {
      buf.setLength(buf.length() - 2);
    }
    buf.append('}');
    return buf.toString();
  }

  protected void toString(StringBuilder buf) {
    buf.append("batchId").append('=').append(JodaBeanUtils.toString(getBatchId())).append(',').append(' ');
    buf.append("observationDate").append('=').append(JodaBeanUtils.toString(getObservationDate())).append(',').append(' ');
    buf.append("observationTime").append('=').append(JodaBeanUtils.toString(getObservationTime())).append(',').append(' ');
    buf.append("complete").append('=').append(JodaBeanUtils.toString(isComplete())).append(',').append(' ');
    buf.append("masterProcessHost").append('=').append(JodaBeanUtils.toString(getMasterProcessHost())).append(',').append(' ');
    buf.append("creationInstant").append('=').append(JodaBeanUtils.toString(getCreationInstant())).append(',').append(' ');
    buf.append("startInstant").append('=').append(JodaBeanUtils.toString(getStartInstant())).append(',').append(' ');
    buf.append("endInstant").append('=').append(JodaBeanUtils.toString(getEndInstant())).append(',').append(' ');
    buf.append("numRestarts").append('=').append(JodaBeanUtils.toString(getNumRestarts())).append(',').append(' ');
    buf.append("dataPaging").append('=').append(JodaBeanUtils.toString(getDataPaging())).append(',').append(' ');
    buf.append("data").append('=').append(JodaBeanUtils.toString(getData())).append(',').append(' ');
    buf.append("errorsPaging").append('=').append(JodaBeanUtils.toString(getErrorsPaging())).append(',').append(' ');
    buf.append("errors").append('=').append(JodaBeanUtils.toString(getErrors())).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code BatchDocument}.
   */
  public static class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code batchId} property.
     */
    private final MetaProperty<ObjectId> _batchId = DirectMetaProperty.ofReadWrite(
        this, "batchId", BatchDocument.class, ObjectId.class);
    /**
     * The meta-property for the {@code observationDate} property.
     */
    private final MetaProperty<LocalDate> _observationDate = DirectMetaProperty.ofReadWrite(
        this, "observationDate", BatchDocument.class, LocalDate.class);
    /**
     * The meta-property for the {@code observationTime} property.
     */
    private final MetaProperty<String> _observationTime = DirectMetaProperty.ofReadWrite(
        this, "observationTime", BatchDocument.class, String.class);
    /**
     * The meta-property for the {@code complete} property.
     */
    private final MetaProperty<Boolean> _complete = DirectMetaProperty.ofReadWrite(
        this, "complete", BatchDocument.class, Boolean.TYPE);
    /**
     * The meta-property for the {@code masterProcessHost} property.
     */
    private final MetaProperty<String> _masterProcessHost = DirectMetaProperty.ofReadWrite(
        this, "masterProcessHost", BatchDocument.class, String.class);
    /**
     * The meta-property for the {@code creationInstant} property.
     */
    private final MetaProperty<Instant> _creationInstant = DirectMetaProperty.ofReadWrite(
        this, "creationInstant", BatchDocument.class, Instant.class);
    /**
     * The meta-property for the {@code startInstant} property.
     */
    private final MetaProperty<Instant> _startInstant = DirectMetaProperty.ofReadWrite(
        this, "startInstant", BatchDocument.class, Instant.class);
    /**
     * The meta-property for the {@code endInstant} property.
     */
    private final MetaProperty<Instant> _endInstant = DirectMetaProperty.ofReadWrite(
        this, "endInstant", BatchDocument.class, Instant.class);
    /**
     * The meta-property for the {@code numRestarts} property.
     */
    private final MetaProperty<Integer> _numRestarts = DirectMetaProperty.ofReadWrite(
        this, "numRestarts", BatchDocument.class, Integer.TYPE);
    /**
     * The meta-property for the {@code dataPaging} property.
     */
    private final MetaProperty<Paging> _dataPaging = DirectMetaProperty.ofReadWrite(
        this, "dataPaging", BatchDocument.class, Paging.class);
    /**
     * The meta-property for the {@code data} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<List<ViewResultEntry>> _data = DirectMetaProperty.ofReadWrite(
        this, "data", BatchDocument.class, (Class) List.class);
    /**
     * The meta-property for the {@code errorsPaging} property.
     */
    private final MetaProperty<Paging> _errorsPaging = DirectMetaProperty.ofReadWrite(
        this, "errorsPaging", BatchDocument.class, Paging.class);
    /**
     * The meta-property for the {@code errors} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<List<BatchError>> _errors = DirectMetaProperty.ofReadWrite(
        this, "errors", BatchDocument.class, (Class) List.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "batchId",
        "observationDate",
        "observationTime",
        "complete",
        "masterProcessHost",
        "creationInstant",
        "startInstant",
        "endInstant",
        "numRestarts",
        "dataPaging",
        "data",
        "errorsPaging",
        "errors");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -331744779:  // batchId
          return _batchId;
        case 950748666:  // observationDate
          return _observationDate;
        case 951232793:  // observationTime
          return _observationTime;
        case -599445191:  // complete
          return _complete;
        case 2095858933:  // masterProcessHost
          return _masterProcessHost;
        case -961305086:  // creationInstant
          return _creationInstant;
        case 1823123231:  // startInstant
          return _startInstant;
        case -2109892474:  // endInstant
          return _endInstant;
        case -1329836566:  // numRestarts
          return _numRestarts;
        case 1173228502:  // dataPaging
          return _dataPaging;
        case 3076010:  // data
          return _data;
        case -49547561:  // errorsPaging
          return _errorsPaging;
        case -1294635157:  // errors
          return _errors;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends BatchDocument> builder() {
      return new DirectBeanBuilder<BatchDocument>(new BatchDocument());
    }

    @Override
    public Class<? extends BatchDocument> beanType() {
      return BatchDocument.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code batchId} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ObjectId> batchId() {
      return _batchId;
    }

    /**
     * The meta-property for the {@code observationDate} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<LocalDate> observationDate() {
      return _observationDate;
    }

    /**
     * The meta-property for the {@code observationTime} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> observationTime() {
      return _observationTime;
    }

    /**
     * The meta-property for the {@code complete} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Boolean> complete() {
      return _complete;
    }

    /**
     * The meta-property for the {@code masterProcessHost} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> masterProcessHost() {
      return _masterProcessHost;
    }

    /**
     * The meta-property for the {@code creationInstant} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Instant> creationInstant() {
      return _creationInstant;
    }

    /**
     * The meta-property for the {@code startInstant} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Instant> startInstant() {
      return _startInstant;
    }

    /**
     * The meta-property for the {@code endInstant} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Instant> endInstant() {
      return _endInstant;
    }

    /**
     * The meta-property for the {@code numRestarts} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Integer> numRestarts() {
      return _numRestarts;
    }

    /**
     * The meta-property for the {@code dataPaging} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Paging> dataPaging() {
      return _dataPaging;
    }

    /**
     * The meta-property for the {@code data} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<List<ViewResultEntry>> data() {
      return _data;
    }

    /**
     * The meta-property for the {@code errorsPaging} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Paging> errorsPaging() {
      return _errorsPaging;
    }

    /**
     * The meta-property for the {@code errors} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<List<BatchError>> errors() {
      return _errors;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -331744779:  // batchId
          return ((BatchDocument) bean).getBatchId();
        case 950748666:  // observationDate
          return ((BatchDocument) bean).getObservationDate();
        case 951232793:  // observationTime
          return ((BatchDocument) bean).getObservationTime();
        case -599445191:  // complete
          return ((BatchDocument) bean).isComplete();
        case 2095858933:  // masterProcessHost
          return ((BatchDocument) bean).getMasterProcessHost();
        case -961305086:  // creationInstant
          return ((BatchDocument) bean).getCreationInstant();
        case 1823123231:  // startInstant
          return ((BatchDocument) bean).getStartInstant();
        case -2109892474:  // endInstant
          return ((BatchDocument) bean).getEndInstant();
        case -1329836566:  // numRestarts
          return ((BatchDocument) bean).getNumRestarts();
        case 1173228502:  // dataPaging
          return ((BatchDocument) bean).getDataPaging();
        case 3076010:  // data
          return ((BatchDocument) bean).getData();
        case -49547561:  // errorsPaging
          return ((BatchDocument) bean).getErrorsPaging();
        case -1294635157:  // errors
          return ((BatchDocument) bean).getErrors();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -331744779:  // batchId
          ((BatchDocument) bean).setBatchId((ObjectId) newValue);
          return;
        case 950748666:  // observationDate
          ((BatchDocument) bean).setObservationDate((LocalDate) newValue);
          return;
        case 951232793:  // observationTime
          ((BatchDocument) bean).setObservationTime((String) newValue);
          return;
        case -599445191:  // complete
          ((BatchDocument) bean).setComplete((Boolean) newValue);
          return;
        case 2095858933:  // masterProcessHost
          ((BatchDocument) bean).setMasterProcessHost((String) newValue);
          return;
        case -961305086:  // creationInstant
          ((BatchDocument) bean).setCreationInstant((Instant) newValue);
          return;
        case 1823123231:  // startInstant
          ((BatchDocument) bean).setStartInstant((Instant) newValue);
          return;
        case -2109892474:  // endInstant
          ((BatchDocument) bean).setEndInstant((Instant) newValue);
          return;
        case -1329836566:  // numRestarts
          ((BatchDocument) bean).setNumRestarts((Integer) newValue);
          return;
        case 1173228502:  // dataPaging
          ((BatchDocument) bean).setDataPaging((Paging) newValue);
          return;
        case 3076010:  // data
          ((BatchDocument) bean).setData((List<ViewResultEntry>) newValue);
          return;
        case -49547561:  // errorsPaging
          ((BatchDocument) bean).setErrorsPaging((Paging) newValue);
          return;
        case -1294635157:  // errors
          ((BatchDocument) bean).setErrors((List<BatchError>) newValue);
          return;
      }
      super.propertySet(bean, propertyName, newValue, quiet);
    }

    @Override
    protected void validate(Bean bean) {
      JodaBeanUtils.notNull(((BatchDocument) bean)._data, "data");
      JodaBeanUtils.notNull(((BatchDocument) bean)._errors, "errors");
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
