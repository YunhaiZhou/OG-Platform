/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.maths.lowlevelapi.slatec.fnlib;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.opengamma.maths.highlevelapi.datatypes.OGComplexMatrix;
import com.opengamma.maths.lowlevelapi.exposedapi.SLATEC;
import com.opengamma.maths.lowlevelapi.functions.D1mach;

/**
 * Tests complex abs()
 */
@Test
public class ZABSTest {
  private static double[][] realP = { {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 } };

  private static double[][] imagP = {
      {-10.00, -10.00, -10.00, -10.00, -10.00, -10.00, -10.00, -10.00, -10.00, -10.00, -10.00, -10.00, -10.00, -10.00, -10.00, -10.00, -10.00, -10.00, -10.00, -10.00, -10.00 },
      {-9.00, -9.00, -9.00, -9.00, -9.00, -9.00, -9.00, -9.00, -9.00, -9.00, -9.00, -9.00, -9.00, -9.00, -9.00, -9.00, -9.00, -9.00, -9.00, -9.00, -9.00 },
      {-8.00, -8.00, -8.00, -8.00, -8.00, -8.00, -8.00, -8.00, -8.00, -8.00, -8.00, -8.00, -8.00, -8.00, -8.00, -8.00, -8.00, -8.00, -8.00, -8.00, -8.00 },
      {-7.00, -7.00, -7.00, -7.00, -7.00, -7.00, -7.00, -7.00, -7.00, -7.00, -7.00, -7.00, -7.00, -7.00, -7.00, -7.00, -7.00, -7.00, -7.00, -7.00, -7.00 },
      {-6.00, -6.00, -6.00, -6.00, -6.00, -6.00, -6.00, -6.00, -6.00, -6.00, -6.00, -6.00, -6.00, -6.00, -6.00, -6.00, -6.00, -6.00, -6.00, -6.00, -6.00 },
      {-5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00 },
      {-4.00, -4.00, -4.00, -4.00, -4.00, -4.00, -4.00, -4.00, -4.00, -4.00, -4.00, -4.00, -4.00, -4.00, -4.00, -4.00, -4.00, -4.00, -4.00, -4.00, -4.00 },
      {-3.00, -3.00, -3.00, -3.00, -3.00, -3.00, -3.00, -3.00, -3.00, -3.00, -3.00, -3.00, -3.00, -3.00, -3.00, -3.00, -3.00, -3.00, -3.00, -3.00, -3.00 },
      {-2.00, -2.00, -2.00, -2.00, -2.00, -2.00, -2.00, -2.00, -2.00, -2.00, -2.00, -2.00, -2.00, -2.00, -2.00, -2.00, -2.00, -2.00, -2.00, -2.00, -2.00 },
      {-1.00, -1.00, -1.00, -1.00, -1.00, -1.00, -1.00, -1.00, -1.00, -1.00, -1.00, -1.00, -1.00, -1.00, -1.00, -1.00, -1.00, -1.00, -1.00, -1.00, -1.00 },
      {0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00 },
      {1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00 },
      {2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00 },
      {3.00, 3.00, 3.00, 3.00, 3.00, 3.00, 3.00, 3.00, 3.00, 3.00, 3.00, 3.00, 3.00, 3.00, 3.00, 3.00, 3.00, 3.00, 3.00, 3.00, 3.00 },
      {4.00, 4.00, 4.00, 4.00, 4.00, 4.00, 4.00, 4.00, 4.00, 4.00, 4.00, 4.00, 4.00, 4.00, 4.00, 4.00, 4.00, 4.00, 4.00, 4.00, 4.00 },
      {5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00 },
      {6.00, 6.00, 6.00, 6.00, 6.00, 6.00, 6.00, 6.00, 6.00, 6.00, 6.00, 6.00, 6.00, 6.00, 6.00, 6.00, 6.00, 6.00, 6.00, 6.00, 6.00 },
      {7.00, 7.00, 7.00, 7.00, 7.00, 7.00, 7.00, 7.00, 7.00, 7.00, 7.00, 7.00, 7.00, 7.00, 7.00, 7.00, 7.00, 7.00, 7.00, 7.00, 7.00 },
      {8.00, 8.00, 8.00, 8.00, 8.00, 8.00, 8.00, 8.00, 8.00, 8.00, 8.00, 8.00, 8.00, 8.00, 8.00, 8.00, 8.00, 8.00, 8.00, 8.00, 8.00 },
      {9.00, 9.00, 9.00, 9.00, 9.00, 9.00, 9.00, 9.00, 9.00, 9.00, 9.00, 9.00, 9.00, 9.00, 9.00, 9.00, 9.00, 9.00, 9.00, 9.00, 9.00 },
      {10.00, 10.00, 10.00, 10.00, 10.00, 10.00, 10.00, 10.00, 10.00, 10.00, 10.00, 10.00, 10.00, 10.00, 10.00, 10.00, 10.00, 10.00, 10.00, 10.00, 10.00 } };

  private static double[][] answerReal = {
      {14.1421356237309510, 13.4536240470737098, 12.8062484748656971, 12.2065556157337021, 11.6619037896906015, 11.1803398874989490, 10.7703296142690075, 10.4403065089105507, 10.1980390271855690,
          10.0498756211208899, 10.0000000000000000, 10.0498756211208899, 10.1980390271855690, 10.4403065089105507, 10.7703296142690075, 11.1803398874989490, 11.6619037896906015, 12.2065556157337021,
          12.8062484748656971, 13.4536240470737098, 14.1421356237309510 },
      {13.4536240470737098, 12.7279220613578552, 12.0415945787922958, 11.4017542509913792, 10.8166538263919687, 10.2956301409870008, 9.8488578017961039, 9.4868329805051381, 9.2195444572928871,
          9.0553851381374173, 9.0000000000000000, 9.0553851381374173, 9.2195444572928871, 9.4868329805051381, 9.8488578017961039, 10.2956301409870008, 10.8166538263919687, 11.4017542509913792,
          12.0415945787922958, 12.7279220613578552, 13.4536240470737098 },
      {12.8062484748656971, 12.0415945787922958, 11.3137084989847612, 10.6301458127346500, 10.0000000000000000, 9.4339811320566032, 8.9442719099991592, 8.5440037453175304, 8.2462112512353212,
          8.0622577482985491, 8.0000000000000000, 8.0622577482985491, 8.2462112512353212, 8.5440037453175304, 8.9442719099991592, 9.4339811320566032, 10.0000000000000000, 10.6301458127346500,
          11.3137084989847612, 12.0415945787922958, 12.8062484748656971 },
      {12.2065556157337021, 11.4017542509913792, 10.6301458127346500, 9.8994949366116654, 9.2195444572928871, 8.6023252670426267, 8.0622577482985491, 7.6157731058639087, 7.2801098892805181,
          7.0710678118654755, 7.0000000000000000, 7.0710678118654755, 7.2801098892805181, 7.6157731058639087, 8.0622577482985491, 8.6023252670426267, 9.2195444572928871, 9.8994949366116654,
          10.6301458127346500, 11.4017542509913792, 12.2065556157337021 },
      {11.6619037896906015, 10.8166538263919687, 10.0000000000000000, 9.2195444572928871, 8.4852813742385695, 7.8102496759066540, 7.2111025509279782, 6.7082039324993694, 6.3245553203367590,
          6.0827625302982193, 6.0000000000000000, 6.0827625302982193, 6.3245553203367590, 6.7082039324993694, 7.2111025509279782, 7.8102496759066540, 8.4852813742385695, 9.2195444572928871,
          10.0000000000000000, 10.8166538263919687, 11.6619037896906015 },
      {11.1803398874989490, 10.2956301409870008, 9.4339811320566032, 8.6023252670426267, 7.8102496759066540, 7.0710678118654755, 6.4031242374328485, 5.8309518948453007, 5.3851648071345037,
          5.0990195135927845, 5.0000000000000000, 5.0990195135927845, 5.3851648071345037, 5.8309518948453007, 6.4031242374328485, 7.0710678118654755, 7.8102496759066540, 8.6023252670426267,
          9.4339811320566032, 10.2956301409870008, 11.1803398874989490 },
      {10.7703296142690075, 9.8488578017961039, 8.9442719099991592, 8.0622577482985491, 7.2111025509279782, 6.4031242374328485, 5.6568542494923806, 5.0000000000000000, 4.4721359549995796,
          4.1231056256176606, 4.0000000000000000, 4.1231056256176606, 4.4721359549995796, 5.0000000000000000, 5.6568542494923806, 6.4031242374328485, 7.2111025509279782, 8.0622577482985491,
          8.9442719099991592, 9.8488578017961039, 10.7703296142690075 },
      {10.4403065089105507, 9.4868329805051381, 8.5440037453175304, 7.6157731058639087, 6.7082039324993694, 5.8309518948453007, 5.0000000000000000, 4.2426406871192848, 3.6055512754639891,
          3.1622776601683795, 3.0000000000000000, 3.1622776601683795, 3.6055512754639891, 4.2426406871192848, 5.0000000000000000, 5.8309518948453007, 6.7082039324993694, 7.6157731058639087,
          8.5440037453175304, 9.4868329805051381, 10.4403065089105507 },
      {10.1980390271855690, 9.2195444572928871, 8.2462112512353212, 7.2801098892805181, 6.3245553203367590, 5.3851648071345037, 4.4721359549995796, 3.6055512754639891, 2.8284271247461903,
          2.2360679774997898, 2.0000000000000000, 2.2360679774997898, 2.8284271247461903, 3.6055512754639891, 4.4721359549995796, 5.3851648071345037, 6.3245553203367590, 7.2801098892805181,
          8.2462112512353212, 9.2195444572928871, 10.1980390271855690 },
      {10.0498756211208899, 9.0553851381374173, 8.0622577482985491, 7.0710678118654755, 6.0827625302982193, 5.0990195135927845, 4.1231056256176606, 3.1622776601683795, 2.2360679774997898,
          1.4142135623730951, 1.0000000000000000, 1.4142135623730951, 2.2360679774997898, 3.1622776601683795, 4.1231056256176606, 5.0990195135927845, 6.0827625302982193, 7.0710678118654755,
          8.0622577482985491, 9.0553851381374173, 10.0498756211208899 },
      {10.0000000000000000, 9.0000000000000000, 8.0000000000000000, 7.0000000000000000, 6.0000000000000000, 5.0000000000000000, 4.0000000000000000, 3.0000000000000000, 2.0000000000000000,
          1.0000000000000000, 0.0000000000000000, 1.0000000000000000, 2.0000000000000000, 3.0000000000000000, 4.0000000000000000, 5.0000000000000000, 6.0000000000000000, 7.0000000000000000,
          8.0000000000000000, 9.0000000000000000, 10.0000000000000000 },
      {10.0498756211208899, 9.0553851381374173, 8.0622577482985491, 7.0710678118654755, 6.0827625302982193, 5.0990195135927845, 4.1231056256176606, 3.1622776601683795, 2.2360679774997898,
          1.4142135623730951, 1.0000000000000000, 1.4142135623730951, 2.2360679774997898, 3.1622776601683795, 4.1231056256176606, 5.0990195135927845, 6.0827625302982193, 7.0710678118654755,
          8.0622577482985491, 9.0553851381374173, 10.0498756211208899 },
      {10.1980390271855690, 9.2195444572928871, 8.2462112512353212, 7.2801098892805181, 6.3245553203367590, 5.3851648071345037, 4.4721359549995796, 3.6055512754639891, 2.8284271247461903,
          2.2360679774997898, 2.0000000000000000, 2.2360679774997898, 2.8284271247461903, 3.6055512754639891, 4.4721359549995796, 5.3851648071345037, 6.3245553203367590, 7.2801098892805181,
          8.2462112512353212, 9.2195444572928871, 10.1980390271855690 },
      {10.4403065089105507, 9.4868329805051381, 8.5440037453175304, 7.6157731058639087, 6.7082039324993694, 5.8309518948453007, 5.0000000000000000, 4.2426406871192848, 3.6055512754639891,
          3.1622776601683795, 3.0000000000000000, 3.1622776601683795, 3.6055512754639891, 4.2426406871192848, 5.0000000000000000, 5.8309518948453007, 6.7082039324993694, 7.6157731058639087,
          8.5440037453175304, 9.4868329805051381, 10.4403065089105507 },
      {10.7703296142690075, 9.8488578017961039, 8.9442719099991592, 8.0622577482985491, 7.2111025509279782, 6.4031242374328485, 5.6568542494923806, 5.0000000000000000, 4.4721359549995796,
          4.1231056256176606, 4.0000000000000000, 4.1231056256176606, 4.4721359549995796, 5.0000000000000000, 5.6568542494923806, 6.4031242374328485, 7.2111025509279782, 8.0622577482985491,
          8.9442719099991592, 9.8488578017961039, 10.7703296142690075 },
      {11.1803398874989490, 10.2956301409870008, 9.4339811320566032, 8.6023252670426267, 7.8102496759066540, 7.0710678118654755, 6.4031242374328485, 5.8309518948453007, 5.3851648071345037,
          5.0990195135927845, 5.0000000000000000, 5.0990195135927845, 5.3851648071345037, 5.8309518948453007, 6.4031242374328485, 7.0710678118654755, 7.8102496759066540, 8.6023252670426267,
          9.4339811320566032, 10.2956301409870008, 11.1803398874989490 },
      {11.6619037896906015, 10.8166538263919687, 10.0000000000000000, 9.2195444572928871, 8.4852813742385695, 7.8102496759066540, 7.2111025509279782, 6.7082039324993694, 6.3245553203367590,
          6.0827625302982193, 6.0000000000000000, 6.0827625302982193, 6.3245553203367590, 6.7082039324993694, 7.2111025509279782, 7.8102496759066540, 8.4852813742385695, 9.2195444572928871,
          10.0000000000000000, 10.8166538263919687, 11.6619037896906015 },
      {12.2065556157337021, 11.4017542509913792, 10.6301458127346500, 9.8994949366116654, 9.2195444572928871, 8.6023252670426267, 8.0622577482985491, 7.6157731058639087, 7.2801098892805181,
          7.0710678118654755, 7.0000000000000000, 7.0710678118654755, 7.2801098892805181, 7.6157731058639087, 8.0622577482985491, 8.6023252670426267, 9.2195444572928871, 9.8994949366116654,
          10.6301458127346500, 11.4017542509913792, 12.2065556157337021 },
      {12.8062484748656971, 12.0415945787922958, 11.3137084989847612, 10.6301458127346500, 10.0000000000000000, 9.4339811320566032, 8.9442719099991592, 8.5440037453175304, 8.2462112512353212,
          8.0622577482985491, 8.0000000000000000, 8.0622577482985491, 8.2462112512353212, 8.5440037453175304, 8.9442719099991592, 9.4339811320566032, 10.0000000000000000, 10.6301458127346500,
          11.3137084989847612, 12.0415945787922958, 12.8062484748656971 },
      {13.4536240470737098, 12.7279220613578552, 12.0415945787922958, 11.4017542509913792, 10.8166538263919687, 10.2956301409870008, 9.8488578017961039, 9.4868329805051381, 9.2195444572928871,
          9.0553851381374173, 9.0000000000000000, 9.0553851381374173, 9.2195444572928871, 9.4868329805051381, 9.8488578017961039, 10.2956301409870008, 10.8166538263919687, 11.4017542509913792,
          12.0415945787922958, 12.7279220613578552, 13.4536240470737098 },
      {14.1421356237309510, 13.4536240470737098, 12.8062484748656971, 12.2065556157337021, 11.6619037896906015, 11.1803398874989490, 10.7703296142690075, 10.4403065089105507, 10.1980390271855690,
          10.0498756211208899, 10.0000000000000000, 10.0498756211208899, 10.1980390271855690, 10.4403065089105507, 10.7703296142690075, 11.1803398874989490, 11.6619037896906015, 12.2065556157337021,
          12.8062484748656971, 13.4536240470737098, 14.1421356237309510 } };

  @Test
  public void correctnessTest() {
    OGComplexMatrix c = new OGComplexMatrix(realP, imagP);
    double ans, tabans;
    final int rows = c.getNumberOfRows();
    final int cols = c.getNumberOfColumns();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        ans = (SLATEC.getInstance().zabs(c.getEntry(i, j).getReal(), c.getEntry(i, j).getImag()));
        tabans = answerReal[i][j];
        assertTrue(Math.abs(ans - tabans) < 10 * D1mach.four());
      }
    }
  }
}
