/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.sesame.function.scenarios.curvedata;

import java.util.Map;

import org.testng.annotations.Test;
import org.threeten.bp.LocalDate;

import com.opengamma.financial.analytics.curve.CurveSpecification;
import com.opengamma.id.ExternalIdBundle;
import com.opengamma.util.test.TestGroup;

@Test(groups = TestGroup.UNIT)
public class CurveDataParallelShiftTest {

  @Test
  public void shift() {
    CurveDataParallelShift shift = new CurveDataParallelShift(0.1, CurveSpecificationMatcher.named(CurveTestUtils.CURVE_NAME));
    Map<ExternalIdBundle, Double> shiftedValues = shift.apply(CurveTestUtils.CURVE_SPEC, CurveTestUtils.VALUE_MAP);
    CurveTestUtils.checkValues(shiftedValues, 0.2, 0.3, 0.6, 0.5);
  }

  @Test
  public void noMatch() {
    CurveSpecification curveSpec = new CurveSpecification(LocalDate.now(), "a different name", CurveTestUtils.NODES);
    CurveDataParallelShift shift = new CurveDataParallelShift(0.1, CurveSpecificationMatcher.named(CurveTestUtils.CURVE_NAME));
    Map<ExternalIdBundle, Double> shiftedValues = shift.apply(curveSpec, CurveTestUtils.VALUE_MAP);
    CurveTestUtils.checkValues(shiftedValues, 0.1, 0.2, 0.7, 0.4);
  }
}
