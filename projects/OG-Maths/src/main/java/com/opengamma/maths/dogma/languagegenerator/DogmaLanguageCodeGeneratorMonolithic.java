/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.maths.dogma.languagegenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opengamma.maths.dogma.engine.language.ArbitraryFunction;
import com.opengamma.maths.dogma.engine.language.InfixOperator;
import com.opengamma.maths.dogma.engine.language.UnaryFunction;
import com.opengamma.maths.dogma.engine.language.VoidUnaryFunction;
import com.opengamma.maths.dogma.languagegenerator.generators.ArbitraryFunctionGenerator;
import com.opengamma.maths.dogma.languagegenerator.generators.InfixOperatorGenerator;
import com.opengamma.maths.dogma.languagegenerator.generators.UnaryFunctionGenerator;
import com.opengamma.maths.dogma.languagegenerator.generators.VoidUnaryFunctionGenerator;

/**
 * Generates the monolithic class representation of the DOGMA language code  
 */
public class DogmaLanguageCodeGeneratorMonolithic {
  private static Logger s_log = LoggerFactory.getLogger(DogmaLanguageCodeGeneratorMonolithic.class);

  private static DogmaLanguageMethodParser s_dogmaLanguageMethodParser = DogmaLanguageMethodParser.getInstance();

  private static Map<Class<?>, DogmaLangTokenToCodeGenerator> s_generationMap = new HashMap<Class<?>, DogmaLangTokenToCodeGenerator>();
  private static String s_indent = "  ";
  static {
    s_generationMap.put(UnaryFunction.class, UnaryFunctionGenerator.getInstance());
    s_generationMap.put(VoidUnaryFunction.class, VoidUnaryFunctionGenerator.getInstance());
    s_generationMap.put(InfixOperator.class, InfixOperatorGenerator.getInstance());
    s_generationMap.put(ArbitraryFunction.class, ArbitraryFunctionGenerator.getInstance());
  }

  public String generateCode() {
    List<FullToken> fullTokens = s_dogmaLanguageMethodParser.getTokens();
    DogmaLangTokenToCodeGenerator g;
    StringBuffer sbprocedural = new StringBuffer();
    StringBuffer sbtablevars = new StringBuffer();
    StringBuffer sbjumptables = new StringBuffer();
    StringBuffer sbmethods = new StringBuffer();
    StringBuffer sbimports = new StringBuffer();

    // generate code from tokens
    for (FullToken tok : fullTokens) {
      g = s_generationMap.get(tok.getInterfaceClassType());
      //      System.out.println("name = " + tok.getSimpleName() + ". class id =" + tok.getInterfaceClass().getSimpleName());
      s_log.info("Creating code for:" + tok.getSimpleName());
      if (g == null) {
        s_log.warn("class " + tok.getInterfaceClassType() + " has no code generator class available");
      } else {
        sbtablevars.append(g.generateTableCodeVariables(tok));
        sbjumptables.append(g.generateTableCode(tok));
        sbmethods.append(g.generateMethodCode(tok));
      }
      sbimports.append("import " + tok.getCanonicalName() + ";\n");
    }

    // procedural code gathering

    // add in header
    sbprocedural.append(header());

    // imports
    sbprocedural.append(imports());
    sbprocedural.append(sbimports);

    // add in classname
    sbprocedural.append(classname());

    // add singleton
    sbprocedural.append(singleton());

    // add logger
    sbprocedural.append(logger());

    // add code for verbose start up
    sbprocedural.append(verboseHandler());

    // add dispatch
    sbprocedural.append(chainRunners());

    // add variables that make up the jump tables
    sbprocedural.append(sbtablevars);

    // start static block
    sbprocedural.append(beginStaticBlock());

    // add start up note
    sbprocedural.append(dogmastart());

    // add evaluation default matrix cost
    sbprocedural.append(evalCostMatrix());

    // add operation dictionary
    sbprocedural.append(operationDict());

    // add jump table code
    sbprocedural.append(sbjumptables);

    // add end note
    sbprocedural.append(dogmafinished());

    // close static block
    sbprocedural.append(closeBrace());

    // add method code
    sbprocedural.append(sbmethods);

    // close the class
    sbprocedural.append(closeBrace());

    return sbprocedural.toString();
  }

  private static String header() {
    StringBuffer tmp = new StringBuffer();
    tmp.append("//CSOFF\n");    
    tmp.append("// Autogenerated, do not edit!\n");
    tmp.append("/**\n");
    tmp.append(" * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies\n");
    tmp.append(" *\n");
    tmp.append(" * Please see distribution for license.\n");
    tmp.append(" */\n");
    tmp.append("package com.opengamma.maths.dogma;\n");
    return tmp.toString();
  }

  private static String classname() {
    StringBuffer tmp = new StringBuffer();
    tmp.append("/**\n");
    tmp.append(" * Suppression against unused code, typically imports, this is due to autogeneration and it being easier to include all at little extra cost.\n");
    tmp.append(" */\n");
    tmp.append("@SuppressWarnings(\"unused\")\n");
    tmp.append("/**\n");
    tmp.append(" * Provides the DOGMA Language\n");
    tmp.append(" */\n");
    tmp.append("public class DogmaLanguage {\n");
    return tmp.toString();
  }

  private static String verboseHandler() {
    StringBuffer tmp = new StringBuffer();
    tmp.append("// switch for chatty start up\n");
    tmp.append(s_indent + "private static boolean s_verbose;\n");
    tmp.append(s_indent + "public DogmaLanguage(boolean verbose) {\n");
    tmp.append(s_indent + s_indent + "s_verbose = verbose;\n");
    tmp.append(s_indent + "};\n");
    return tmp.toString();
  }

  private static String singleton() {
    StringBuffer tmp = new StringBuffer();
    tmp.append(s_indent + "private static DogmaLanguage s_instance;\n");
    tmp.append(s_indent + "DogmaLanguage() {\n");
    tmp.append(s_indent + "}\n");
    tmp.append(s_indent + "public static DogmaLanguage getInstance() {\n");
    tmp.append(s_indent + s_indent + "return s_instance;\n");
    tmp.append(s_indent + "}\n");
    return tmp.toString();
  }

  public static String imports() {
    StringBuffer tmp = new StringBuffer();
    tmp.append("import com.opengamma.maths.commonapi.numbers.ComplexType;\n");
    tmp.append("import com.opengamma.maths.highlevelapi.datatypes.OGArray;\n");
    tmp.append("import com.opengamma.maths.highlevelapi.datatypes.OGComplexScalar;\n");
    tmp.append("import com.opengamma.maths.highlevelapi.datatypes.OGComplexDiagonalMatrix;\n");
    tmp.append("import com.opengamma.maths.highlevelapi.datatypes.OGComplexSparseMatrix;\n");
    tmp.append("import com.opengamma.maths.highlevelapi.datatypes.OGComplexMatrix;\n");
    tmp.append("import com.opengamma.maths.highlevelapi.datatypes.OGRealScalar;\n");
    tmp.append("import com.opengamma.maths.highlevelapi.datatypes.OGDiagonalMatrix;\n");
    tmp.append("import com.opengamma.maths.highlevelapi.datatypes.OGSparseMatrix;\n");
    tmp.append("import com.opengamma.maths.highlevelapi.datatypes.OGMatrix;\n");
    tmp.append("import com.opengamma.maths.highlevelapi.datatypes.OGIndexMatrix;\n");
    tmp.append("import com.opengamma.maths.highlevelapi.datatypes.OGPermutationMatrix;\n");
    tmp.append("import com.opengamma.maths.dogma.engine.language.InfixOperator;\n");
    tmp.append("import com.opengamma.maths.dogma.engine.language.UnaryFunction;\n");
    tmp.append("import com.opengamma.maths.dogma.engine.language.VoidUnaryFunction;\n");
    tmp.append("import com.opengamma.maths.dogma.engine.language.Function;\n");
    tmp.append("import com.opengamma.maths.dogma.engine.operationstack.InfixOpChain;\n");
    tmp.append("import com.opengamma.maths.dogma.engine.operationstack.MethodScraperForInfixOperators;\n");
    tmp.append("import com.opengamma.maths.dogma.engine.operationstack.MethodScraperForUnaryFunctions;\n");
    tmp.append("import com.opengamma.maths.dogma.engine.operationstack.MethodScraperForVoidUnaryFunctions;\n");
    tmp.append("import com.opengamma.maths.dogma.engine.operationstack.OperatorDictionaryPopulator;\n");
    tmp.append("import com.opengamma.maths.dogma.engine.operationstack.OperatorDictionaryPopulatorLibrary;\n");
    tmp.append("import com.opengamma.maths.dogma.engine.operationstack.RunInfixOpChain;\n");
    tmp.append("import com.opengamma.maths.dogma.engine.operationstack.RunUnaryFunctionChain;\n");
    tmp.append("import com.opengamma.maths.dogma.engine.operationstack.RunVoidUnaryFunctionChain;\n");
    tmp.append("import com.opengamma.maths.dogma.engine.operationstack.UnaryFunctionChain;\n");
    tmp.append("import com.opengamma.maths.dogma.engine.operationstack.VoidUnaryFunctionChain;\n");
    tmp.append("import com.opengamma.maths.lowlevelapi.functions.checkers.Catchers;\n");
    tmp.append("import com.opengamma.maths.dogma.engine.matrixinfo.ConversionCostAdjacencyMatrixStore;\n");
    tmp.append("import com.opengamma.maths.dogma.engine.matrixinfo.MatrixTypeToIndexMap;\n");
    tmp.append("import org.slf4j.Logger;\n");
    tmp.append("import org.slf4j.LoggerFactory;\n");
    return tmp.toString();
  }

  private static String chainRunners() {
    StringBuffer tmp = new StringBuffer();
    tmp.append(s_indent + "private static RunInfixOpChain s_infixOpChainRunner = new RunInfixOpChain();\n");
    tmp.append(s_indent + "private static RunUnaryFunctionChain s_unaryFunctionChainRunner = new RunUnaryFunctionChain();\n");
    tmp.append(s_indent + "private static RunVoidUnaryFunctionChain s_voidUnaryFunctionChainRunner = new RunVoidUnaryFunctionChain();\n");
    return tmp.toString();
  }

  private static String operationDict() {
    StringBuffer tmp = new StringBuffer();
    tmp.append(s_indent + "// Build instructions sets\n ");
    //    tmp.append(s_indent +
    //        "OperatorDictionaryPopulator<InfixOperator<OGArray<? extends Number>, OGArray<? extends Number>, OGArray<? extends Number>>> operatorDictInfix = new OperatorDictionaryPopulator");
    //    tmp.append(s_indent + "<InfixOperator<OGArray<? extends Number>, OGArray<? extends Number>, OGArray<? extends Number>>>();\n");
    //    tmp.append(s_indent + "OperatorDictionaryPopulator<UnaryFunction<OGArray<? extends Number>, OGArray<? extends Number>>> operatorDictUnary");
    //    tmp.append(" = new OperatorDictionaryPopulator<UnaryFunction<OGArray<? extends Number>, OGArray<? extends Number>>>();\n");
    //    tmp.append("OperatorDictionaryPopulator<VoidUnaryFunction<OGArray<? extends Number>>> operatorDictVoidUnary");
    //    tmp.append(" = new OperatorDictionaryPopulator<VoidUnaryFunction<OGArray<? extends Number>>>();\n");

    tmp.append(s_indent +
        "OperatorDictionaryPopulator<InfixOperator<OGArray<? extends Number>, OGArray<? extends Number>, OGArray<? extends Number>>> operatorDictInfix = OperatorDictionaryPopulatorLibrary.getInfixOperatorDictionary();\n"); //CSIGNORE
    tmp.append(s_indent +
        "OperatorDictionaryPopulator<UnaryFunction<OGArray<? extends Number>, OGArray<? extends Number>>> operatorDictUnary = OperatorDictionaryPopulatorLibrary.getUnaryOperatorDictionary();\n");
    tmp.append(s_indent + "" +
        "OperatorDictionaryPopulator<VoidUnaryFunction<OGArray<? extends Number>>> operatorDictVoidUnary = OperatorDictionaryPopulatorLibrary.getVoidUnaryOperatorDictionary();\n");

    return tmp.toString();
  }

  private static String logger() {
    return s_indent + "private static Logger s_log = LoggerFactory.getLogger(DogmaLanguage.class);\n";
  }

  private static String dogmastart() {
    StringBuffer tmp = new StringBuffer();
    tmp.append(s_indent + "if(s_verbose){\n");
    tmp.append(s_indent + s_indent + "s_log.info(\"Welcome to DOGMA\");\n");
    tmp.append(s_indent + s_indent + "s_log.info(\"Building instructions...\");\n");
    tmp.append(s_indent + "}\n");
    return tmp.toString();
  }

  private static String dogmafinished() {
    StringBuffer tmp = new StringBuffer();
    tmp.append(s_indent + "if(s_verbose){\n");
    tmp.append(s_indent + s_indent + "s_log.info(\"DOGMA built.\");\n");
    tmp.append(s_indent + "}\n");
    return tmp.toString();
  }

  private static String beginStaticBlock() {
    return s_indent + "static {\n";
  }

  private static String closeBrace() {
    return "\n}\n";
  }

  private static String evalCostMatrix() {
    StringBuffer tmp = new StringBuffer();
    //    tmp.append("final double[][] DefaultInfixFunctionEvalCosts = new double[][] {//\n");
    //    tmp.append("{1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },//\n");
    //    tmp.append("{0, 1, 0, 0, 0, 0, 0, 0, 0, 0 },//\n");
    //    tmp.append("{0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },//\n");
    //    tmp.append("{0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },//\n");
    //    tmp.append("{0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },//\n");
    //    tmp.append("{0, 0, 0, 0, 0, 1, 0, 0, 0, 0 },//\n");
    //    tmp.append("{0, 0, 0, 0, 0, 0, 25, 0, 50, 0 },//\n");
    //    tmp.append("{0, 0, 0, 0, 0, 0, 0, 1, 0, 0 },//\n");
    //    tmp.append("{0, 0, 0, 0, 0, 0, 50, 0, 100, 200 },//\n");
    //    tmp.append("{0, 0, 0, 0, 0, 0, 0, 0, 0, 200 } };\n");

    tmp.append(s_indent + "final double[][] DefaultInfixFunctionEvalCosts = new double[][] {\n");
    tmp.append("{1.00, 1.00, 1.00, 1.00, 0.00, 1.00, 1.00, 1.00, 1.00, 1.00 },//\n");
    tmp.append("{1.00, 1.00, 0.00, 1.00, 0.00, 0.00, 0.00, 1.00, 0.00, 1.00 },//\n");
    tmp.append("{1.00, 0.00, 1.00, 1.00, 0.00, 0.00, 1.00, 1.00, 1.00, 1.00 },//\n");
    tmp.append("{1.00, 1.00, 1.00, 1.00, 0.00, 0.00, 0.00, 1.00, 0.00, 1.00 },//\n");
    tmp.append("{0.00, 0.00, 0.00, 0.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00 },//\n");
    tmp.append("{1.00, 0.00, 0.00, 0.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00 },//\n");
    tmp.append("{1.00, 0.00, 1.00, 0.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00 },//\n");
    tmp.append("{1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 0.00, 1.00 },//\n");
    tmp.append("{1.00, 0.00, 1.00, 0.00, 1.00, 1.00, 1.00, 0.00, 1.00, 1.00 },//\n");
    tmp.append("{1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00 } };\n");

    tmp.append("OGMatrix defaultInfixFunctionEvalCostsMatrix = new OGMatrix(DefaultInfixFunctionEvalCosts);\n");

    tmp.append("final double[][] DefaultUnaryFunctionEvalCosts = new double[][] {//\n");
    tmp.append("{1 },//\n");
    tmp.append("{1 },//\n");
    tmp.append("{2 },//\n");
    tmp.append("{3 },//\n");
    tmp.append("{3 },//\n");
    tmp.append("{5 },//\n");
    tmp.append("{5 },//\n");
    tmp.append("{5 },//\n");
    tmp.append("{10 },//\n");
    tmp.append("{20 } };\n");
    tmp.append("OGMatrix defaultUnaryFunctionEvalCostsMatrix = new OGMatrix(DefaultUnaryFunctionEvalCosts);\n");
    return tmp.toString();
  }
}
