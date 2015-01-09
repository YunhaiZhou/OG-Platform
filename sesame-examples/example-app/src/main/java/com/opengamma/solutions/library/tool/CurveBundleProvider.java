/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.solutions.library.tool;

import java.util.List;
import java.util.Set;

import org.threeten.bp.Instant;
import org.threeten.bp.ZonedDateTime;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import com.opengamma.core.link.ConfigLink;
import com.opengamma.core.marketdatasnapshot.MarketDataSnapshotSource;
import com.opengamma.core.marketdatasnapshot.impl.ManageableMarketDataSnapshot;
import com.opengamma.engine.marketdata.spec.MarketDataSpecification;
import com.opengamma.engine.marketdata.spec.UserMarketDataSpecification;
import com.opengamma.financial.currency.CurrencyMatrix;
import com.opengamma.id.VersionCorrection;
import com.opengamma.integration.regression.DatabaseRestore;
import com.opengamma.master.region.RegionMaster;
import com.opengamma.master.region.impl.RegionFileReader;
import com.opengamma.service.ServiceContext;
import com.opengamma.service.ThreadLocalServiceContext;
import com.opengamma.service.VersionCorrectionProvider;
import com.opengamma.sesame.MulticurveBundle;
import com.opengamma.sesame.engine.ComponentMap;
import com.opengamma.sesame.engine.FixedInstantVersionCorrectionProvider;
import com.opengamma.sesame.marketdata.MarketDataEnvironment;
import com.opengamma.sesame.marketdata.MarketDataEnvironmentBuilder;
import com.opengamma.sesame.marketdata.MarketDataRequirement;
import com.opengamma.sesame.marketdata.MulticurveId;
import com.opengamma.sesame.marketdata.SingleValueRequirement;
import com.opengamma.sesame.marketdata.SnapshotMarketDataFactory;
import com.opengamma.sesame.marketdata.builders.MarketDataBuilder;
import com.opengamma.sesame.marketdata.builders.MarketDataBuilders;
import com.opengamma.sesame.marketdata.builders.MarketDataEnvironmentFactory;
import com.opengamma.util.ArgumentChecker;

/**
 * Sample Curve Bundle Provider
 */
public class CurveBundleProvider {

  private final DatabaseRestore _databaseRestore;
  private final RegionMaster _regionMaster;
  private final ComponentMap _componentMap;
  private final MarketDataSnapshotSource _snapshotSource;

  /**
   * Create an instance of the Curve Bundle Provider
   * @param databaseRestore utility to populate data into the in memory masters
   * @param regionMaster regions master
   * @param snapshotSource market data snapshot source
   * @param componentMap component map to build the MarketDataEnvironment
   *
   */
  @Inject
  public CurveBundleProvider(DatabaseRestore databaseRestore,
                             RegionMaster regionMaster,
                             MarketDataSnapshotSource snapshotSource,
                             ComponentMap componentMap) {
    _databaseRestore =  ArgumentChecker.notNull(databaseRestore, "databaseRestore");
    _regionMaster = ArgumentChecker.notNull(regionMaster, "regionMaster");
    _componentMap = ArgumentChecker.notNull(componentMap, "componentMap");
    _snapshotSource = ArgumentChecker.notNull(snapshotSource, "snapshotSource");
  }


  public MulticurveBundle buildMulticurve(String bundleName,
                                          String snapshotName,
                                          String currencyMatrixName,
                                          ZonedDateTime valuationTime) {
    _databaseRestore.restoreDatabase();
    RegionFileReader.createPopulated(_regionMaster);

    // This is needed to ensure that the version correction provided is after the population of the masters
    ServiceContext serviceContext =
        ThreadLocalServiceContext.getInstance().with(VersionCorrectionProvider.class,
                                                     new FixedInstantVersionCorrectionProvider(Instant.now()));
    ThreadLocalServiceContext.init(serviceContext);

    // Need to create another MarketDataEnvironmentFactory here, rather than using the one provided by Guice.
    // This is because resolving the currency matrix link can only happen after the population of masters
    ConfigLink<CurrencyMatrix> currencyMatrixLink  = ConfigLink.resolvable(currencyMatrixName, CurrencyMatrix.class);
    List<MarketDataBuilder> builders = ImmutableList.of(
        MarketDataBuilders.raw(_componentMap, "DEFAULT"),
        MarketDataBuilders.multicurve(_componentMap, currencyMatrixLink),
        MarketDataBuilders.fxMatrix());


    SnapshotMarketDataFactory snapshotMarketDataFactory = new SnapshotMarketDataFactory(_snapshotSource);
    MarketDataEnvironmentFactory environmentFactory = new MarketDataEnvironmentFactory(snapshotMarketDataFactory, builders);

    ManageableMarketDataSnapshot snapshot = _snapshotSource.getSingle(ManageableMarketDataSnapshot.class,
                                                                                snapshotName,
                                                                                VersionCorrection.LATEST);
    MarketDataSpecification marketDataSpec = UserMarketDataSpecification.of(snapshot.getUniqueId());

    MarketDataEnvironment suppliedData = MarketDataEnvironmentBuilder.empty();
    MulticurveId multicurveId = MulticurveId.of(bundleName);
    SingleValueRequirement requirement = SingleValueRequirement.of(multicurveId);
    Set<MarketDataRequirement> requirements = ImmutableSet.<MarketDataRequirement>of(requirement);
    MarketDataEnvironment marketData = environmentFactory.build(suppliedData, requirements, marketDataSpec, valuationTime);
    return (MulticurveBundle) marketData.getData().get(requirement);
  }

}
