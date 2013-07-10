/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.maths.highlevelapi.functions.DOGMAFunctions.DOGMAArithmetic.rdivide;

import com.opengamma.maths.dogma.engine.DOGMAMethodHook;
import com.opengamma.maths.dogma.engine.methodhookinstances.infix.Rdivide;
import com.opengamma.maths.highlevelapi.datatypes.OGArray;
import com.opengamma.maths.highlevelapi.datatypes.OGMatrix;
import com.opengamma.maths.highlevelapi.datatypes.OGSparseMatrix;
import com.opengamma.maths.lowlevelapi.exposedapi.EasyIZY;
import com.opengamma.maths.lowlevelapi.functions.checkers.Catchers;

/**
 * Does elementwise OGSparse / OGDouble
 */
@DOGMAMethodHook(provides = Rdivide.class)
public final class RdivideOGSparseMatrixOGMatrix implements Rdivide<OGArray<? extends Number>, OGSparseMatrix, OGMatrix> {
  @Override
  public OGArray<? extends Number> eval(OGSparseMatrix array1, OGMatrix array2) {
    Catchers.catchNullFromArgList(array1, 1);
    Catchers.catchNullFromArgList(array2, 2);
    // if either is a single number then we just div by that
    // else ew div.
    int rowsArray1 = array1.getNumberOfRows();
    int columnsArray1 = array1.getNumberOfColumns();
    int rowsArray2 = array2.getNumberOfRows();
    int columnsArray2 = array2.getNumberOfColumns();
    int retRows = 0, retCols = 0;
    final int[] colPtr = array1.getColumnPtr();
    final int[] rowIdx = array1.getRowIndex();
    final double[] denseData = array2.getData();
    final double[] sparseData = array1.getData();
    int ptr = 0;
    double[] tmp = null;
    int n;
    int denseOffset = 0;
    OGArray<? extends Number> ret = null;

    if (rowsArray1 == 1 && columnsArray1 == 1) { // Single valued SparseMatrix rdivide dense
      n = rowsArray2 * columnsArray2;
      tmp = new double[n];
      EasyIZY.vd_xdiv(array1.getData()[0], denseData, tmp);
      retRows = rowsArray2;
      retCols = columnsArray2;
      ret = new OGMatrix(tmp, retRows, retCols);
    } else if (rowsArray2 == 1 && columnsArray2 == 1) { // Sparse matrix rdivide single dense
      n = sparseData.length;
      tmp = new double[n];
      System.arraycopy(sparseData, 0, tmp, 0, n);
      final double deref = denseData[0];
      for (int i = 0; i < n; i++) {
        tmp[i] /= deref;
      }
      retRows = rowsArray1;
      retCols = columnsArray1;
      ret = new OGSparseMatrix(colPtr, rowIdx, tmp, rowsArray1, columnsArray1);
    } else { // Sparse/Dense, ignore division in sparse 0 spaces 
      Catchers.catchBadCommute(rowsArray1, "Rows in arg 1", rowsArray2, "Rows in arg 2");
      Catchers.catchBadCommute(columnsArray1, "Columns in arg 1", columnsArray2, "Columns in arg 2");
      retRows = rowsArray1;
      retCols = columnsArray1;
      n = sparseData.length;
      tmp = new double[n];
      System.arraycopy(sparseData, 0, tmp, 0, n);
      for (int ir = 0; ir < retCols; ir++) {
        denseOffset = ir * retRows;
        for (int i = colPtr[ir]; i < colPtr[ir + 1]; i++) {
          tmp[ptr] = sparseData[ptr] / denseData[rowIdx[i] + denseOffset];
          ptr++;
        }
      }
      ret = new OGSparseMatrix(colPtr, rowIdx, tmp, rowsArray1, columnsArray1);
    }
    return ret;
  }

}
