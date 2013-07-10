/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.maths.lowlevelapi.functions.checkers.catchInf;

import com.opengamma.maths.commonapi.exceptions.MathsExceptionEncounteredInf;
import com.opengamma.maths.highlevelapi.datatypes.OGMatrix;
import com.opengamma.maths.lowlevelapi.functions.checkers.Catchers;
import com.opengamma.maths.lowlevelapi.functions.iss.IsInf;

/**
 * Catches Inf in OGDoubleArray
 */
public final class CatchInfOGMatrix extends CatchInfAbstract<OGMatrix> {
  private static CatchInfOGMatrix s_instance = new CatchInfOGMatrix();

  public static CatchInfOGMatrix getInstance() {
    return s_instance;
  }

  private CatchInfOGMatrix() {
  }

  @Override
  public void catchinf(OGMatrix array1) {
    Catchers.catchNullFromArgList(array1, 1);
    boolean ret;
    final double[] data = array1.getData();
    ret = IsInf.any(data);
    //Inf found, deal with it...
    if (ret) {
      int badrow = 0, badcol = 0;
      final int rows = array1.getNumberOfRows();
      final int columns = array1.getNumberOfColumns();
      boolean[] infs = IsInf.getBooleans(data);
      int jmp = 0;
      for (int i = 0; i < columns; i++) {
        jmp = i * rows;
        for (int j = 0; j < rows; j++) {
          if (infs[jmp + j] == true) {
            badrow = j;
            badcol = i;
            break;
          }
        }
      }
      throw new MathsExceptionEncounteredInf(badrow, badcol);      
    }
  }
}
