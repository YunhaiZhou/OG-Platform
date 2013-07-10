/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.maths.highlevelapi.functions.DOGMAFunctions.DOGMASparseUtilities.sparse;

import com.opengamma.maths.dogma.engine.DOGMAMethodHook;
import com.opengamma.maths.dogma.engine.methodhookinstances.unary.Sparse;
import com.opengamma.maths.highlevelapi.datatypes.OGSparseMatrix;

/**
 * Sparse's a sparse array
 */
@DOGMAMethodHook(provides = Sparse.class)
public final class SparseOGSparseMatrix implements Sparse<OGSparseMatrix, OGSparseMatrix> {

  @Override
  public OGSparseMatrix eval(OGSparseMatrix array1) {
    final int rows = array1.getNumberOfRows();
    final int cols = array1.getNumberOfColumns();
    final double[] data = array1.getData();
    final int[] colPtr = array1.getColumnPtr();
    final int[] rowIdx = array1.getRowIndex();

    final double[] tmpData = new double[data.length];
    System.arraycopy(data, 0, tmpData, 0, data.length);

    final int[] tmpColPtr = new int[colPtr.length];
    System.arraycopy(colPtr, 0, tmpColPtr, 0, colPtr.length);

    final int[] tmpRowIdx = new int[rowIdx.length];
    System.arraycopy(rowIdx, 0, tmpRowIdx, 0, rowIdx.length);

    return new OGSparseMatrix(tmpColPtr, tmpRowIdx, tmpData, rows, cols);
  }

}
