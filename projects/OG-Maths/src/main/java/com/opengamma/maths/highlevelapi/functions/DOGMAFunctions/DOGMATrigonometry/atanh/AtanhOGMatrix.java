/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.maths.highlevelapi.functions.DOGMAFunctions.DOGMATrigonometry.atanh;

import com.opengamma.maths.dogma.engine.DOGMAMethodHook;
import com.opengamma.maths.dogma.engine.methodhookinstances.unary.Atanh;
import com.opengamma.maths.highlevelapi.datatypes.OGArray;
import com.opengamma.maths.highlevelapi.datatypes.OGMatrix;
import com.opengamma.maths.lowlevelapi.exposedapi.EasyIZY;
import com.opengamma.maths.lowlevelapi.functions.memory.DenseMemoryManipulation;
import com.opengamma.maths.lowlevelapi.functions.memory.OGTypesMalloc;

/**
 * Atanh() of an OGMatrix
 */
@DOGMAMethodHook(provides = Atanh.class)
public class AtanhOGMatrix implements Atanh<OGArray<? extends Number>, OGMatrix> {

  @Override
  public OGArray<? extends Number> eval(OGMatrix array1) {
    double[] data = array1.getData();
    int n = data.length;
    double[] tmp;
    // check bounds
    boolean complex = false;
    for (int i = 0; i < n; i++) {
      if (Math.abs(data[i]) > 1) {
        complex = true;
        break;
      }
    }
    OGArray<? extends Number> retarr;
    if (complex) {
      tmp = DenseMemoryManipulation.convertSinglePointerToZeroInterleavedSinglePointer(data);
      EasyIZY.vz_atanh(tmp, tmp);
      retarr = OGTypesMalloc.OGComplexMatrixBasedOnStructureOf(array1, tmp);
    } else {
      tmp = new double[n];
      EasyIZY.vd_atanh(data, tmp);
      retarr = OGTypesMalloc.OGMatrixBasedOnStructureOf(array1, tmp);
    }
    return retarr;
  }
}
