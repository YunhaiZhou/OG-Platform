/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.sesame;

import org.threeten.bp.LocalDate;
import org.threeten.bp.ZonedDateTime;

import com.opengamma.util.ArgumentChecker;

/**
 * Function implementation that provides the injected valuation time.
 */
public class DefaultValuationTimeFn implements ValuationTimeFn {

  /**
   * The valuation time.
   */
  private ZonedDateTime _valuationTime;

  public DefaultValuationTimeFn() {
  }

  public DefaultValuationTimeFn(ZonedDateTime valuationTime) {
    _valuationTime = ArgumentChecker.notNull(valuationTime, "valuationTime");
  }

  public void setValuationTime(ZonedDateTime valuationTime) {
    _valuationTime = valuationTime;
  }

  //-------------------------------------------------------------------------
  @Override
  public LocalDate getDate() {
    return _valuationTime.toLocalDate();
  }

  @Override
  public ZonedDateTime getTime() {
    return _valuationTime;
  }

}
