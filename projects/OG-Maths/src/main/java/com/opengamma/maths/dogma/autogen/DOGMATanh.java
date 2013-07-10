//CSOFF
// Autogenerated, do not edit!
/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.maths.dogma.autogen;
import com.opengamma.maths.commonapi.numbers.ComplexType;
import com.opengamma.maths.dogma.engine.language.InfixOperator;
import com.opengamma.maths.dogma.engine.language.UnaryFunction;
import com.opengamma.maths.dogma.engine.language.VoidUnaryFunction;
import com.opengamma.maths.dogma.engine.language.Function;
import com.opengamma.maths.dogma.engine.operationstack.InfixOpChain;
import com.opengamma.maths.dogma.engine.operationstack.MethodScraperForInfixOperators;
import com.opengamma.maths.dogma.engine.operationstack.MethodScraperForUnaryFunctions;
import com.opengamma.maths.dogma.engine.operationstack.MethodScraperForVoidUnaryFunctions;
import com.opengamma.maths.dogma.engine.operationstack.OperatorDictionaryPopulator;
import com.opengamma.maths.dogma.engine.operationstack.OperatorDictionaryPopulatorLibrary;
import com.opengamma.maths.dogma.engine.operationstack.RunInfixOpChain;
import com.opengamma.maths.dogma.engine.operationstack.RunUnaryFunctionChain;
import com.opengamma.maths.dogma.engine.operationstack.RunVoidUnaryFunctionChain;
import com.opengamma.maths.dogma.engine.operationstack.VoidUnaryFunctionChain;
import com.opengamma.maths.dogma.engine.operationstack.UnaryFunctionChain;
import com.opengamma.maths.lowlevelapi.functions.checkers.Catchers;
import com.opengamma.maths.dogma.engine.matrixinfo.ConversionCostAdjacencyMatrixStore;
import com.opengamma.maths.dogma.engine.matrixinfo.MatrixTypeToIndexMap;
import com.opengamma.maths.highlevelapi.datatypes.OGArray;
import com.opengamma.maths.highlevelapi.datatypes.OGComplexScalar;
import com.opengamma.maths.highlevelapi.datatypes.OGComplexDiagonalMatrix;
import com.opengamma.maths.highlevelapi.datatypes.OGComplexSparseMatrix;
import com.opengamma.maths.highlevelapi.datatypes.OGComplexMatrix;
import com.opengamma.maths.highlevelapi.datatypes.OGRealScalar;
import com.opengamma.maths.highlevelapi.datatypes.OGDiagonalMatrix;
import com.opengamma.maths.highlevelapi.datatypes.OGSparseMatrix;
import com.opengamma.maths.highlevelapi.datatypes.OGMatrix;
import com.opengamma.maths.highlevelapi.datatypes.OGIndexMatrix;
import com.opengamma.maths.highlevelapi.datatypes.OGPermutationMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.opengamma.maths.dogma.engine.methodhookinstances.unary.Tanh;
/**
 * Suppression against unused code, typically imports, this is due to autogeneration and it being easier to include all at little extra cost.
 */
@SuppressWarnings({"unused"})
/**
 * Provides the DOGMA Language
 */
public class DOGMATanh {
  private static DOGMATanh s_instance;
  DOGMATanh() {
  }
  public static DOGMATanh getInstance() {
    return s_instance;
  }
  private static Logger s_log = LoggerFactory.getLogger(Tanh.class);
  // switch for chatty start up
  private static boolean s_verbose;
  public DOGMATanh(boolean verbose) {
    s_verbose = verbose;
  };
  private static RunInfixOpChain s_infixOpChainRunner = new RunInfixOpChain();
  private static RunUnaryFunctionChain s_unaryFunctionChainRunner = new RunUnaryFunctionChain();
  private static RunVoidUnaryFunctionChain s_voidUnaryFunctionChainRunner = new RunVoidUnaryFunctionChain();
  private static UnaryFunctionChain[] s_tanhInstructions;
static {
final double[][] DefaultInfixFunctionEvalCosts = new double[][] {
{1.00, 1.00, 1.00, 1.00, 0.00, 1.00, 1.00, 1.00, 1.00, 1.00 },//
{1.00, 1.00, 0.00, 1.00, 0.00, 0.00, 0.00, 1.00, 0.00, 1.00 },//
{1.00, 0.00, 1.00, 1.00, 0.00, 0.00, 1.00, 1.00, 1.00, 1.00 },//
{1.00, 1.00, 1.00, 1.00, 0.00, 0.00, 0.00, 1.00, 0.00, 1.00 },//
{0.00, 0.00, 0.00, 0.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00 },//
{1.00, 0.00, 0.00, 0.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00 },//
{1.00, 0.00, 1.00, 0.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00 },//
{1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 0.00, 1.00 },//
{1.00, 0.00, 1.00, 0.00, 1.00, 1.00, 1.00, 0.00, 1.00, 1.00 },//
{1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00 } };
OGMatrix defaultInfixFunctionEvalCostsMatrix = new OGMatrix(DefaultInfixFunctionEvalCosts);
final double[][] DefaultUnaryFunctionEvalCosts = new double[][] {//
{1 },//
{1 },//
{2 },//
{3 },//
{3 },//
{5 },//
{5 },//
{5 },//
{10 },//
{20 } };
OGMatrix defaultUnaryFunctionEvalCostsMatrix = new OGMatrix(DefaultUnaryFunctionEvalCosts);
// Build instructions sets
 OperatorDictionaryPopulator<InfixOperator<OGArray<? extends Number>, OGArray<? extends Number>, OGArray<? extends Number>>> operatorDictInfix = OperatorDictionaryPopulatorLibrary.getInfixOperatorDictionary();
OperatorDictionaryPopulator<UnaryFunction<OGArray<? extends Number>, OGArray<? extends Number>>> operatorDictUnary = OperatorDictionaryPopulatorLibrary.getUnaryOperatorDictionary();
OperatorDictionaryPopulator<VoidUnaryFunction<OGArray<? extends Number>>> operatorDictVoidUnary = OperatorDictionaryPopulatorLibrary.getVoidUnaryOperatorDictionary();
UnaryFunction<OGArray<? extends Number>, OGArray<? extends Number>>[] TanhFunctionTable = MethodScraperForUnaryFunctions.availableMethodsForUnaryFunctions(operatorDictUnary.getOperationsMap(),Tanh.class);
s_tanhInstructions = MethodScraperForUnaryFunctions.computeFunctions(ConversionCostAdjacencyMatrixStore.getWeightedAdjacencyMatrix(),TanhFunctionTable, defaultUnaryFunctionEvalCostsMatrix);


}

public static OGArray<? extends Number> tanh(OGArray<? extends Number> arg0) {
Catchers.catchNullFromArgList(arg0, 1);
int type1 = MatrixTypeToIndexMap.getIndexFromClass(arg0.getClass());
OGArray<? extends Number> tmp = s_unaryFunctionChainRunner.dispatch(s_tanhInstructions[type1], arg0);
return tmp;
}

public static Number tanh(Number arg0) {Catchers.catchNullFromArgList(arg0, 1);
OGArray<? extends Number> arg0rewrite;
if (arg0.getClass() == ComplexType.class) {
arg0rewrite = new OGComplexScalar(arg0);
} else {
arg0rewrite = new OGRealScalar(arg0.doubleValue());
}
int type1 = MatrixTypeToIndexMap.getIndexFromClass(arg0rewrite.getClass());
OGArray<? extends Number> tmp = s_unaryFunctionChainRunner.dispatch(s_tanhInstructions[type1], arg0rewrite);
return tmp.getEntry(0, 0);
}


}
