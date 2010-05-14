/**
 * Copyright (C) 2009 - 2010 by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.engine.view.client;

import java.util.Set;

import com.opengamma.engine.position.Portfolio;
import com.opengamma.engine.view.ComputationResultListener;
import com.opengamma.engine.view.DeltaComputationResultListener;
import com.opengamma.engine.view.ViewComputationResultModel;

/**
 * 
 * 
 * NOTE: The result listeners must be maintained as a set. It should be possible to add the same listener multiple times but
 * only have it receive one notification. After repeated calls to add, a single call to remove the listener should stop any
 * further notifications.
 *
 * @author kirk
 */
public interface ViewClient {
  
  String getName();
  
  boolean isLiveComputationRunning();
  
  Portfolio getPortfolio();
  
  Set<String> getAllSecurityTypes();
  
  Set<String> getAllValueNames();
  
  Set<String> getRequirementNames(String securityType);
  
  boolean isResultAvailable();
  
  void performComputation();
  
  ViewComputationResultModel getMostRecentResult();
  
  void addComputationResultListener(ComputationResultListener listener);
  
  void removeComputationResultListener(ComputationResultListener listener);
  
  void addDeltaResultListener(DeltaComputationResultListener listener);

  void removeDeltaResultListener(DeltaComputationResultListener listener);
}
