/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.web.spring;

import static com.opengamma.analytics.math.interpolation.Interpolator1DFactory.DOUBLE_QUADRATIC;
import static com.opengamma.analytics.math.interpolation.Interpolator1DFactory.FLAT_EXTRAPOLATOR;
import static com.opengamma.analytics.math.interpolation.Interpolator1DFactory.LINEAR;
import static com.opengamma.analytics.math.interpolation.Interpolator1DFactory.LINEAR_EXTRAPOLATOR;
import static com.opengamma.master.historicaltimeseries.impl.HistoricalTimeSeriesRatingFieldNames.DEFAULT_CONFIG_NAME;

import java.io.OutputStreamWriter;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.fudgemsg.FudgeContext;
import org.fudgemsg.FudgeMsg;
import org.fudgemsg.FudgeMsgFormatter;
import org.fudgemsg.wire.FudgeMsgWriter;
import org.fudgemsg.wire.xml.FudgeXMLSettings;
import org.fudgemsg.wire.xml.FudgeXMLStreamWriter;

import com.opengamma.analytics.financial.equity.future.pricing.EquityFuturePricerFactory;
import com.opengamma.analytics.financial.model.volatility.smile.fitting.interpolation.WeightingFunctionFactory;
import com.opengamma.analytics.financial.model.volatility.smile.function.VolatilityFunctionFactory;
import com.opengamma.analytics.financial.schedule.ScheduleCalculatorFactory;
import com.opengamma.analytics.financial.schedule.TimeSeriesSamplingFunctionFactory;
import com.opengamma.analytics.financial.timeseries.returns.TimeSeriesReturnCalculatorFactory;
import com.opengamma.analytics.math.interpolation.Interpolator1DFactory;
import com.opengamma.analytics.math.statistics.descriptive.StatisticsCalculatorFactory;
import com.opengamma.core.value.MarketDataRequirementNames;
import com.opengamma.engine.function.FunctionDefinition;
import com.opengamma.engine.function.config.FunctionConfiguration;
import com.opengamma.engine.function.config.ParameterizedFunctionConfiguration;
import com.opengamma.engine.function.config.RepositoryConfiguration;
import com.opengamma.engine.function.config.RepositoryConfigurationSource;
import com.opengamma.engine.function.config.SimpleRepositoryConfigurationSource;
import com.opengamma.engine.function.config.StaticFunctionConfiguration;
import com.opengamma.engine.value.ValueRequirementNames;
import com.opengamma.financial.aggregation.BottomPositionValues;
import com.opengamma.financial.aggregation.SortedPositionValues;
import com.opengamma.financial.aggregation.TopPositionValues;
import com.opengamma.financial.analytics.AttributesFunction;
import com.opengamma.financial.analytics.CurrencyPairsDefaults;
import com.opengamma.financial.analytics.CurrencyPairsFunction;
import com.opengamma.financial.analytics.DV01Function;
import com.opengamma.financial.analytics.FilteringSummingFunction;
import com.opengamma.financial.analytics.LastHistoricalValueFunction;
import com.opengamma.financial.analytics.MissingInputsFunction;
import com.opengamma.financial.analytics.NotionalFunction;
import com.opengamma.financial.analytics.PositionScalingFunction;
import com.opengamma.financial.analytics.PositionTradeScalingFunction;
import com.opengamma.financial.analytics.SummingFunction;
import com.opengamma.financial.analytics.UnitPositionScalingFunction;
import com.opengamma.financial.analytics.UnitPositionTradeScalingFunction;
import com.opengamma.financial.analytics.cashflow.FixedPayCashFlowFunction;
import com.opengamma.financial.analytics.cashflow.FixedReceiveCashFlowFunction;
import com.opengamma.financial.analytics.cashflow.FloatingPayCashFlowFunction;
import com.opengamma.financial.analytics.cashflow.FloatingReceiveCashFlowFunction;
import com.opengamma.financial.analytics.cashflow.NettedFixedCashFlowFunction;
import com.opengamma.financial.analytics.ircurve.DefaultYieldCurveMarketDataShiftFunction;
import com.opengamma.financial.analytics.ircurve.DefaultYieldCurveShiftFunction;
import com.opengamma.financial.analytics.ircurve.YieldCurveMarketDataShiftFunction;
import com.opengamma.financial.analytics.ircurve.YieldCurveShiftFunction;
import com.opengamma.financial.analytics.model.bond.BondCleanPriceFromCurvesFunction;
import com.opengamma.financial.analytics.model.bond.BondCleanPriceFromYieldFunction;
import com.opengamma.financial.analytics.model.bond.BondCouponPaymentDiaryFunction;
import com.opengamma.financial.analytics.model.bond.BondDefaultCurveNamesFunction;
import com.opengamma.financial.analytics.model.bond.BondDirtyPriceFromCurvesFunction;
import com.opengamma.financial.analytics.model.bond.BondDirtyPriceFromYieldFunction;
import com.opengamma.financial.analytics.model.bond.BondMacaulayDurationFromCurvesFunction;
import com.opengamma.financial.analytics.model.bond.BondMacaulayDurationFromYieldFunction;
import com.opengamma.financial.analytics.model.bond.BondMarketCleanPriceFunction;
import com.opengamma.financial.analytics.model.bond.BondMarketDirtyPriceFunction;
import com.opengamma.financial.analytics.model.bond.BondMarketYieldFunction;
import com.opengamma.financial.analytics.model.bond.BondModifiedDurationFromCurvesFunction;
import com.opengamma.financial.analytics.model.bond.BondModifiedDurationFromYieldFunction;
import com.opengamma.financial.analytics.model.bond.BondTenorFunction;
import com.opengamma.financial.analytics.model.bond.BondYieldFromCurvesFunction;
import com.opengamma.financial.analytics.model.bond.BondZSpreadFromCurveCleanPriceFunction;
import com.opengamma.financial.analytics.model.bond.BondZSpreadFromMarketCleanPriceFunction;
import com.opengamma.financial.analytics.model.bond.BondZSpreadPresentValueSensitivityFromCurveCleanPriceFunction;
import com.opengamma.financial.analytics.model.bond.BondZSpreadPresentValueSensitivityFromMarketCleanPriceFunction;
import com.opengamma.financial.analytics.model.bond.NelsonSiegelSvenssonBondCurveFunction;
import com.opengamma.financial.analytics.model.bondfutureoption.BondFutureOptionBlackDeltaFunction;
import com.opengamma.financial.analytics.model.bondfutureoption.BondFutureOptionBlackFromFuturePresentValueFunction;
import com.opengamma.financial.analytics.model.bondfutureoption.BondFutureOptionBlackGammaFunction;
import com.opengamma.financial.analytics.model.bondfutureoption.BondFutureOptionBlackPV01Function;
import com.opengamma.financial.analytics.model.bondfutureoption.BondFutureOptionBlackPresentValueFunction;
import com.opengamma.financial.analytics.model.bondfutureoption.BondFutureOptionBlackVegaFunction;
import com.opengamma.financial.analytics.model.bondfutureoption.BondFutureOptionBlackYCNSFunction;
import com.opengamma.financial.analytics.model.cds.ISDAApproxCDSPriceFlatSpreadFunction;
import com.opengamma.financial.analytics.model.cds.ISDAApproxCDSPriceHazardCurveFunction;
import com.opengamma.financial.analytics.model.cds.ISDAApproxDiscountCurveFunction;
import com.opengamma.financial.analytics.model.cds.ISDAApproxFlatSpreadFunction;
import com.opengamma.financial.analytics.model.credit.ISDALegacyCDSHazardCurveDefaults;
import com.opengamma.financial.analytics.model.credit.ISDALegacyCDSHazardCurveFunction;
import com.opengamma.financial.analytics.model.credit.ISDALegacyVanillaCDSCleanPriceFunction;
import com.opengamma.financial.analytics.model.credit.ISDALegacyVanillaCDSDefaults;
import com.opengamma.financial.analytics.model.credit.ISDALegacyVanillaCDSDirtyPriceFunction;
import com.opengamma.financial.analytics.model.credit.ISDAYieldCurveDefaults;
import com.opengamma.financial.analytics.model.credit.ISDAYieldCurveFunction;
import com.opengamma.financial.analytics.model.curve.forward.ForwardCurveValuePropertyNames;
import com.opengamma.financial.analytics.model.equity.AffineDividendFunction;
import com.opengamma.financial.analytics.model.equity.EquityForwardCurveDefaults;
import com.opengamma.financial.analytics.model.equity.EquityForwardCurveFunction;
import com.opengamma.financial.analytics.model.equity.SecurityMarketPriceFunction;
import com.opengamma.financial.analytics.model.equity.futures.EquityFuturesDefaultPropertiesFunction;
import com.opengamma.financial.analytics.model.equity.futures.EquityFuturesFunction;
import com.opengamma.financial.analytics.model.equity.futures.EquityFuturesYieldCurveNodeSensitivityFunction;
import com.opengamma.financial.analytics.model.equity.indexoption.EquityIndexOptionDefaultPropertiesFunction;
import com.opengamma.financial.analytics.model.equity.indexoption.EquityIndexOptionForwardValueFunction;
import com.opengamma.financial.analytics.model.equity.indexoption.EquityIndexOptionFundingCurveSensitivitiesFunction;
import com.opengamma.financial.analytics.model.equity.indexoption.EquityIndexOptionImpliedVolFunction;
import com.opengamma.financial.analytics.model.equity.indexoption.EquityIndexOptionPresentValueFunction;
import com.opengamma.financial.analytics.model.equity.indexoption.EquityIndexOptionRhoFunction;
import com.opengamma.financial.analytics.model.equity.indexoption.EquityIndexOptionSpotDeltaFunction;
import com.opengamma.financial.analytics.model.equity.indexoption.EquityIndexOptionSpotGammaFunction;
import com.opengamma.financial.analytics.model.equity.indexoption.EquityIndexOptionSpotIndexFunction;
import com.opengamma.financial.analytics.model.equity.indexoption.EquityIndexOptionSpotVannaFunction;
import com.opengamma.financial.analytics.model.equity.indexoption.EquityIndexOptionVegaFunction;
import com.opengamma.financial.analytics.model.equity.indexoption.EquityIndexOptionVegaMatrixFunction;
import com.opengamma.financial.analytics.model.equity.indexoption.EquityIndexOptionVommaFunction;
import com.opengamma.financial.analytics.model.equity.indexoption.EquityIndexVanillaBarrierOptionDefaultPropertiesFunction;
import com.opengamma.financial.analytics.model.equity.indexoption.EquityIndexVanillaBarrierOptionForwardValueFunction;
import com.opengamma.financial.analytics.model.equity.indexoption.EquityIndexVanillaBarrierOptionFundingCurveSensitivitiesFunction;
import com.opengamma.financial.analytics.model.equity.indexoption.EquityIndexVanillaBarrierOptionPresentValueFunction;
import com.opengamma.financial.analytics.model.equity.indexoption.EquityIndexVanillaBarrierOptionRhoFunction;
import com.opengamma.financial.analytics.model.equity.indexoption.EquityIndexVanillaBarrierOptionSpotDeltaFunction;
import com.opengamma.financial.analytics.model.equity.indexoption.EquityIndexVanillaBarrierOptionSpotGammaFunction;
import com.opengamma.financial.analytics.model.equity.indexoption.EquityIndexVanillaBarrierOptionSpotIndexFunction;
import com.opengamma.financial.analytics.model.equity.indexoption.EquityIndexVanillaBarrierOptionSpotVannaFunction;
import com.opengamma.financial.analytics.model.equity.indexoption.EquityIndexVanillaBarrierOptionVegaFunction;
import com.opengamma.financial.analytics.model.equity.indexoption.EquityIndexVanillaBarrierOptionVegaMatrixFunction;
import com.opengamma.financial.analytics.model.equity.indexoption.EquityIndexVanillaBarrierOptionVommaFunction;
import com.opengamma.financial.analytics.model.equity.portfoliotheory.CAPMBetaDefaultPropertiesPortfolioNodeFunction;
import com.opengamma.financial.analytics.model.equity.portfoliotheory.CAPMBetaDefaultPropertiesPositionFunction;
import com.opengamma.financial.analytics.model.equity.portfoliotheory.CAPMBetaModelPortfolioNodeFunction;
import com.opengamma.financial.analytics.model.equity.portfoliotheory.CAPMBetaModelPositionFunction;
import com.opengamma.financial.analytics.model.equity.portfoliotheory.CAPMFromRegressionDefaultPropertiesPortfolioNodeFunction;
import com.opengamma.financial.analytics.model.equity.portfoliotheory.CAPMFromRegressionDefaultPropertiesPositionFunction;
import com.opengamma.financial.analytics.model.equity.portfoliotheory.CAPMFromRegressionModelPortfolioNodeFunction;
import com.opengamma.financial.analytics.model.equity.portfoliotheory.CAPMFromRegressionModelPositionFunction;
import com.opengamma.financial.analytics.model.equity.portfoliotheory.JensenAlphaDefaultPropertiesPortfolioNodeFunction;
import com.opengamma.financial.analytics.model.equity.portfoliotheory.JensenAlphaDefaultPropertiesPositionFunction;
import com.opengamma.financial.analytics.model.equity.portfoliotheory.JensenAlphaPortfolioNodeFunction;
import com.opengamma.financial.analytics.model.equity.portfoliotheory.JensenAlphaPositionFunction;
import com.opengamma.financial.analytics.model.equity.portfoliotheory.SharpeRatioDefaultPropertiesPortfolioNodeFunction;
import com.opengamma.financial.analytics.model.equity.portfoliotheory.SharpeRatioDefaultPropertiesPositionFunction;
import com.opengamma.financial.analytics.model.equity.portfoliotheory.SharpeRatioPortfolioNodeFunction;
import com.opengamma.financial.analytics.model.equity.portfoliotheory.SharpeRatioPositionFunction;
import com.opengamma.financial.analytics.model.equity.portfoliotheory.StandardEquityModelFunction;
import com.opengamma.financial.analytics.model.equity.portfoliotheory.TotalRiskAlphaDefaultPropertiesPortfolioNodeFunction;
import com.opengamma.financial.analytics.model.equity.portfoliotheory.TotalRiskAlphaDefaultPropertiesPositionFunction;
import com.opengamma.financial.analytics.model.equity.portfoliotheory.TotalRiskAlphaPortfolioNodeFunction;
import com.opengamma.financial.analytics.model.equity.portfoliotheory.TotalRiskAlphaPositionFunction;
import com.opengamma.financial.analytics.model.equity.portfoliotheory.TreynorRatioDefaultPropertiesPortfolioNodeFunction;
import com.opengamma.financial.analytics.model.equity.portfoliotheory.TreynorRatioDefaultPropertiesPositionFunction;
import com.opengamma.financial.analytics.model.equity.portfoliotheory.TreynorRatioPortfolioNodeFunction;
import com.opengamma.financial.analytics.model.equity.portfoliotheory.TreynorRatioPositionFunction;
import com.opengamma.financial.analytics.model.equity.varianceswap.EquityForwardCalculationDefaults;
import com.opengamma.financial.analytics.model.equity.varianceswap.EquityForwardFromSpotAndYieldCurveFunction;
import com.opengamma.financial.analytics.model.equity.varianceswap.EquityVarianceSwapDefaults;
import com.opengamma.financial.analytics.model.equity.varianceswap.EquityVarianceSwapStaticReplicationPresentValueFunction;
import com.opengamma.financial.analytics.model.equity.varianceswap.EquityVarianceSwapStaticReplicationVegaFunction;
import com.opengamma.financial.analytics.model.equity.varianceswap.EquityVarianceSwapStaticReplicationYCNSFunction;
import com.opengamma.financial.analytics.model.fixedincome.InterestRateInstrumentDefaultPropertiesFunction;
import com.opengamma.financial.analytics.model.fixedincome.InterestRateInstrumentPV01Function;
import com.opengamma.financial.analytics.model.fixedincome.InterestRateInstrumentParRateCurveSensitivityFunction;
import com.opengamma.financial.analytics.model.fixedincome.InterestRateInstrumentParRateFunction;
import com.opengamma.financial.analytics.model.fixedincome.InterestRateInstrumentParRateParallelCurveSensitivityFunction;
import com.opengamma.financial.analytics.model.fixedincome.InterestRateInstrumentPresentValueFunction;
import com.opengamma.financial.analytics.model.fixedincome.InterestRateInstrumentYieldCurveNodeSensitivitiesFunction;
import com.opengamma.financial.analytics.model.forex.BloombergFXSpotRateMarketDataFunction;
import com.opengamma.financial.analytics.model.forex.defaultproperties.FXForwardDefaults;
import com.opengamma.financial.analytics.model.forex.defaultproperties.FXOptionBlackCurveDefaults;
import com.opengamma.financial.analytics.model.forex.defaultproperties.FXOptionBlackSurfaceDefaults;
import com.opengamma.financial.analytics.model.forex.forward.FXForwardCurrencyExposureFunction;
import com.opengamma.financial.analytics.model.forex.forward.FXForwardFXPresentValueFunction;
import com.opengamma.financial.analytics.model.forex.forward.FXForwardPV01Function;
import com.opengamma.financial.analytics.model.forex.forward.FXForwardPresentValueCurveSensitivityFunction;
import com.opengamma.financial.analytics.model.forex.forward.FXForwardYCNSFunction;
import com.opengamma.financial.analytics.model.forex.option.BloombergFXOptionSpotRateFunction;
import com.opengamma.financial.analytics.model.forex.option.BloombergFXSpotRatePercentageChangeFunction;
import com.opengamma.financial.analytics.model.forex.option.black.FXOneLookBarrierOptionBlackCurveSensitivityFunction;
import com.opengamma.financial.analytics.model.forex.option.black.FXOneLookBarrierOptionBlackDefaultPropertiesFunction;
import com.opengamma.financial.analytics.model.forex.option.black.FXOneLookBarrierOptionBlackGammaFunction;
import com.opengamma.financial.analytics.model.forex.option.black.FXOneLookBarrierOptionBlackPresentValueFunction;
import com.opengamma.financial.analytics.model.forex.option.black.FXOneLookBarrierOptionBlackVannaFunction;
import com.opengamma.financial.analytics.model.forex.option.black.FXOneLookBarrierOptionBlackVegaFunction;
import com.opengamma.financial.analytics.model.forex.option.black.FXOneLookBarrierOptionBlackVommaFunction;
import com.opengamma.financial.analytics.model.forex.option.black.FXOptionBlackCurrencyExposureFunction;
import com.opengamma.financial.analytics.model.forex.option.black.FXOptionBlackFXPresentValueFunction;
import com.opengamma.financial.analytics.model.forex.option.black.FXOptionBlackGammaFunction;
import com.opengamma.financial.analytics.model.forex.option.black.FXOptionBlackGammaSpotFunction;
import com.opengamma.financial.analytics.model.forex.option.black.FXOptionBlackImpliedVolatilityFunction;
import com.opengamma.financial.analytics.model.forex.option.black.FXOptionBlackPV01Function;
import com.opengamma.financial.analytics.model.forex.option.black.FXOptionBlackPresentValueCurveSensitivityFunction;
import com.opengamma.financial.analytics.model.forex.option.black.FXOptionBlackPresentValueFunction;
import com.opengamma.financial.analytics.model.forex.option.black.FXOptionBlackTermStructureCurrencyExposureFunction;
import com.opengamma.financial.analytics.model.forex.option.black.FXOptionBlackThetaFunction;
import com.opengamma.financial.analytics.model.forex.option.black.FXOptionBlackVannaFunction;
import com.opengamma.financial.analytics.model.forex.option.black.FXOptionBlackVegaFunction;
import com.opengamma.financial.analytics.model.forex.option.black.FXOptionBlackVegaMatrixFunction;
import com.opengamma.financial.analytics.model.forex.option.black.FXOptionBlackVegaQuoteMatrixFunction;
import com.opengamma.financial.analytics.model.forex.option.black.FXOptionBlackVommaFunction;
import com.opengamma.financial.analytics.model.forex.option.black.FXOptionBlackYCNSFunction;
import com.opengamma.financial.analytics.model.forex.option.localvol.FXOptionLocalVolatilityForwardPDEDualDeltaFunction;
import com.opengamma.financial.analytics.model.forex.option.localvol.FXOptionLocalVolatilityForwardPDEDualGammaFunction;
import com.opengamma.financial.analytics.model.forex.option.localvol.FXOptionLocalVolatilityForwardPDEForwardDeltaFunction;
import com.opengamma.financial.analytics.model.forex.option.localvol.FXOptionLocalVolatilityForwardPDEForwardGammaFunction;
import com.opengamma.financial.analytics.model.forex.option.localvol.FXOptionLocalVolatilityForwardPDEForwardVannaFunction;
import com.opengamma.financial.analytics.model.forex.option.localvol.FXOptionLocalVolatilityForwardPDEForwardVegaFunction;
import com.opengamma.financial.analytics.model.forex.option.localvol.FXOptionLocalVolatilityForwardPDEForwardVommaFunction;
import com.opengamma.financial.analytics.model.forex.option.localvol.FXOptionLocalVolatilityForwardPDEGridDualDeltaFunction;
import com.opengamma.financial.analytics.model.forex.option.localvol.FXOptionLocalVolatilityForwardPDEGridDualGammaFunction;
import com.opengamma.financial.analytics.model.forex.option.localvol.FXOptionLocalVolatilityForwardPDEGridForwardDeltaFunction;
import com.opengamma.financial.analytics.model.forex.option.localvol.FXOptionLocalVolatilityForwardPDEGridForwardGammaFunction;
import com.opengamma.financial.analytics.model.forex.option.localvol.FXOptionLocalVolatilityForwardPDEGridForwardVannaFunction;
import com.opengamma.financial.analytics.model.forex.option.localvol.FXOptionLocalVolatilityForwardPDEGridForwardVegaFunction;
import com.opengamma.financial.analytics.model.forex.option.localvol.FXOptionLocalVolatilityForwardPDEGridForwardVommaFunction;
import com.opengamma.financial.analytics.model.forex.option.localvol.FXOptionLocalVolatilityForwardPDEGridImpliedVolatilityFunction;
import com.opengamma.financial.analytics.model.forex.option.localvol.FXOptionLocalVolatilityForwardPDEGridPipsPresentValueFunction;
import com.opengamma.financial.analytics.model.forex.option.localvol.FXOptionLocalVolatilityForwardPDEImpliedVolatilityFunction;
import com.opengamma.financial.analytics.model.forex.option.localvol.FXOptionLocalVolatilityForwardPDEPipsPresentValueFunction;
import com.opengamma.financial.analytics.model.forex.option.vannavolga.FXOptionVannaVolgaPresentValueFunction;
import com.opengamma.financial.analytics.model.future.BondFutureGrossBasisFromCurvesFunction;
import com.opengamma.financial.analytics.model.future.BondFutureNetBasisFromCurvesFunction;
import com.opengamma.financial.analytics.model.future.InterestRateFutureDefaults;
import com.opengamma.financial.analytics.model.future.InterestRateFuturePV01Function;
import com.opengamma.financial.analytics.model.future.InterestRateFuturePresentValueFunction;
import com.opengamma.financial.analytics.model.future.InterestRateFutureYieldCurveNodeSensitivitiesFunction;
import com.opengamma.financial.analytics.model.futureoption.FutureOptionBlackDefaultPropertiesFunction;
import com.opengamma.financial.analytics.model.futureoption.FutureOptionBlackPresentValueFunction;
import com.opengamma.financial.analytics.model.horizon.FXOptionBlackConstantSpreadThetaFunction;
import com.opengamma.financial.analytics.model.horizon.FXOptionBlackForwardSlideThetaFunction;
import com.opengamma.financial.analytics.model.horizon.FXOptionBlackVolatilitySurfaceConstantSpreadThetaFunction;
import com.opengamma.financial.analytics.model.horizon.FXOptionBlackVolatilitySurfaceForwardSlideThetaFunction;
import com.opengamma.financial.analytics.model.horizon.FXOptionBlackYieldCurvesConstantSpreadThetaFunction;
import com.opengamma.financial.analytics.model.horizon.FXOptionBlackYieldCurvesForwardSlideThetaFunction;
import com.opengamma.financial.analytics.model.horizon.InterestRateFutureOptionConstantSpreadThetaFunction;
import com.opengamma.financial.analytics.model.horizon.SwaptionBlackThetaDefaults;
import com.opengamma.financial.analytics.model.horizon.SwaptionConstantSpreadThetaFunction;
import com.opengamma.financial.analytics.model.irfutureoption.InterestRateFutureOptionBlackDefaults;
import com.opengamma.financial.analytics.model.irfutureoption.InterestRateFutureOptionBlackGammaFunction;
import com.opengamma.financial.analytics.model.irfutureoption.InterestRateFutureOptionBlackImpliedVolatilityFunction;
import com.opengamma.financial.analytics.model.irfutureoption.InterestRateFutureOptionBlackPV01Function;
import com.opengamma.financial.analytics.model.irfutureoption.InterestRateFutureOptionBlackPresentValueFunction;
import com.opengamma.financial.analytics.model.irfutureoption.InterestRateFutureOptionBlackPriceFunction;
import com.opengamma.financial.analytics.model.irfutureoption.InterestRateFutureOptionBlackVolatilitySensitivityFunction;
import com.opengamma.financial.analytics.model.irfutureoption.InterestRateFutureOptionBlackYieldCurveNodeSensitivitiesFunction;
import com.opengamma.financial.analytics.model.irfutureoption.InterestRateFutureOptionDefaultValuesFunctionDeprecated;
import com.opengamma.financial.analytics.model.irfutureoption.InterestRateFutureOptionMarketUnderlyingPriceFunction;
import com.opengamma.financial.analytics.model.irfutureoption.InterestRateFutureOptionSABRPresentValueFunction;
import com.opengamma.financial.analytics.model.irfutureoption.InterestRateFutureOptionSABRSensitivitiesFunction;
import com.opengamma.financial.analytics.model.irfutureoption.InterestRateFutureOptionSABRVegaFunction;
import com.opengamma.financial.analytics.model.irfutureoption.InterestRateFutureOptionSABRYieldCurveNodeSensitivitiesFunction;
import com.opengamma.financial.analytics.model.irfutureoption.MarginPriceFunction;
import com.opengamma.financial.analytics.model.option.AnalyticOptionDefaultCurveFunction;
import com.opengamma.financial.analytics.model.option.BlackScholesMertonModelFunction;
import com.opengamma.financial.analytics.model.option.BlackScholesModelCostOfCarryFunction;
import com.opengamma.financial.analytics.model.pnl.EquityPnLDefaultPropertiesFunction;
import com.opengamma.financial.analytics.model.pnl.EquityPnLFunction;
import com.opengamma.financial.analytics.model.pnl.ExternallyProvidedSensitivityPnLDefaultPropertiesFunction;
import com.opengamma.financial.analytics.model.pnl.ExternallyProvidedSensitivityPnLFunction;
import com.opengamma.financial.analytics.model.pnl.FXForwardCurrencyExposurePnLCurrencyConversionFunction;
import com.opengamma.financial.analytics.model.pnl.FXForwardYCNSPnLCurrencyConversionFunction;
import com.opengamma.financial.analytics.model.pnl.PortfolioExchangeTradedDailyPnLFunction;
import com.opengamma.financial.analytics.model.pnl.PortfolioExchangeTradedPnLFunction;
import com.opengamma.financial.analytics.model.pnl.PositionExchangeTradedDailyPnLFunction;
import com.opengamma.financial.analytics.model.pnl.PositionExchangeTradedPnLFunction;
import com.opengamma.financial.analytics.model.pnl.SecurityPriceSeriesDefaultPropertiesFunction;
import com.opengamma.financial.analytics.model.pnl.SecurityPriceSeriesFunction;
import com.opengamma.financial.analytics.model.pnl.SimpleFXFuturePnLDefaultPropertiesFunction;
import com.opengamma.financial.analytics.model.pnl.SimpleFXFuturePnLFunction;
import com.opengamma.financial.analytics.model.pnl.SimpleFuturePnLDefaultPropertiesFunction;
import com.opengamma.financial.analytics.model.pnl.SimpleFuturePnLFunction;
import com.opengamma.financial.analytics.model.pnl.TradeExchangeTradedDailyPnLFunction;
import com.opengamma.financial.analytics.model.pnl.TradeExchangeTradedPnLFunction;
import com.opengamma.financial.analytics.model.pnl.ValueGreekSensitivityPnLDefaultPropertiesFunction;
import com.opengamma.financial.analytics.model.pnl.ValueGreekSensitivityPnLFunction;
import com.opengamma.financial.analytics.model.riskfactor.option.OptionGreekToValueGreekConverterFunction;
import com.opengamma.financial.analytics.model.sabrcube.SABRCMSSpreadNoExtrapolationPVCurveSensitivityFunction;
import com.opengamma.financial.analytics.model.sabrcube.SABRCMSSpreadNoExtrapolationPVSABRSensitivityFunction;
import com.opengamma.financial.analytics.model.sabrcube.SABRCMSSpreadNoExtrapolationPresentValueFunction;
import com.opengamma.financial.analytics.model.sabrcube.SABRCMSSpreadNoExtrapolationVegaFunction;
import com.opengamma.financial.analytics.model.sabrcube.SABRCMSSpreadNoExtrapolationYCNSFunction;
import com.opengamma.financial.analytics.model.sabrcube.SABRCMSSpreadRightExtrapolationPVCurveSensitivityFunction;
import com.opengamma.financial.analytics.model.sabrcube.SABRCMSSpreadRightExtrapolationPVSABRNodeSensitivityFunction;
import com.opengamma.financial.analytics.model.sabrcube.SABRCMSSpreadRightExtrapolationPVSABRSensitivityFunction;
import com.opengamma.financial.analytics.model.sabrcube.SABRCMSSpreadRightExtrapolationPresentValueFunction;
import com.opengamma.financial.analytics.model.sabrcube.SABRCMSSpreadRightExtrapolationVegaFunction;
import com.opengamma.financial.analytics.model.sabrcube.SABRCMSSpreadRightExtrapolationYCNSFunction;
import com.opengamma.financial.analytics.model.sabrcube.SABRNoExtrapolationPVCurveSensitivityFunction;
import com.opengamma.financial.analytics.model.sabrcube.SABRNoExtrapolationPVSABRSensitivityFunction;
import com.opengamma.financial.analytics.model.sabrcube.SABRNoExtrapolationPresentValueFunction;
import com.opengamma.financial.analytics.model.sabrcube.SABRNoExtrapolationVegaFunction;
import com.opengamma.financial.analytics.model.sabrcube.SABRNoExtrapolationYCNSFunction;
import com.opengamma.financial.analytics.model.sabrcube.SABRRightExtrapolationPVCurveSensitivityFunction;
import com.opengamma.financial.analytics.model.sabrcube.SABRRightExtrapolationPVSABRNodeSensitivityFunction;
import com.opengamma.financial.analytics.model.sabrcube.SABRRightExtrapolationPVSABRSensitivityFunction;
import com.opengamma.financial.analytics.model.sabrcube.SABRRightExtrapolationPresentValueFunction;
import com.opengamma.financial.analytics.model.sabrcube.SABRRightExtrapolationVegaFunction;
import com.opengamma.financial.analytics.model.sabrcube.SABRRightExtrapolationYCNSFunction;
import com.opengamma.financial.analytics.model.sabrcube.defaultproperties.SABRNoExtrapolationDefaults;
import com.opengamma.financial.analytics.model.sabrcube.defaultproperties.SABRNoExtrapolationVegaDefaults;
import com.opengamma.financial.analytics.model.sabrcube.defaultproperties.SABRRightExtrapolationDefaults;
import com.opengamma.financial.analytics.model.sabrcube.defaultproperties.SABRRightExtrapolationVegaDefaults;
import com.opengamma.financial.analytics.model.sensitivities.ExternallyProvidedSecurityMarkFunction;
import com.opengamma.financial.analytics.model.sensitivities.ExternallyProvidedSensitivitiesCreditFactorsFunction;
import com.opengamma.financial.analytics.model.sensitivities.ExternallyProvidedSensitivitiesDefaultPropertiesFunction;
import com.opengamma.financial.analytics.model.sensitivities.ExternallyProvidedSensitivitiesNonYieldCurveFunction;
import com.opengamma.financial.analytics.model.sensitivities.ExternallyProvidedSensitivitiesYieldCurveCS01Function;
import com.opengamma.financial.analytics.model.sensitivities.ExternallyProvidedSensitivitiesYieldCurveNodeSensitivitiesFunction;
import com.opengamma.financial.analytics.model.sensitivities.ExternallyProvidedSensitivitiesYieldCurvePV01Function;
import com.opengamma.financial.analytics.model.simpleinstrument.SimpleFuturePV01Function;
import com.opengamma.financial.analytics.model.simpleinstrument.SimpleFuturePresentValueFunction;
import com.opengamma.financial.analytics.model.simpleinstrument.SimpleFuturePriceDeltaFunction;
import com.opengamma.financial.analytics.model.simpleinstrument.SimpleFutureRhoFunction;
import com.opengamma.financial.analytics.model.swaption.black.SwaptionBlackDefaultPropertiesFunction;
import com.opengamma.financial.analytics.model.swaption.black.SwaptionBlackImpliedVolatilityFunction;
import com.opengamma.financial.analytics.model.swaption.black.SwaptionBlackPV01Function;
import com.opengamma.financial.analytics.model.swaption.black.SwaptionBlackPresentValueFunction;
import com.opengamma.financial.analytics.model.swaption.black.SwaptionBlackVolatilitySensitivityFunction;
import com.opengamma.financial.analytics.model.swaption.black.SwaptionBlackYieldCurveNodeSensitivitiesFunction;
import com.opengamma.financial.analytics.model.var.NormalPortfolioHistoricalVaRDefaultPropertiesFunction;
import com.opengamma.financial.analytics.model.var.NormalPortfolioHistoricalVaRFunction;
import com.opengamma.financial.analytics.model.var.NormalPositionHistoricalVaRDefaultPropertiesFunction;
import com.opengamma.financial.analytics.model.var.NormalPositionHistoricalVaRFunction;
import com.opengamma.financial.analytics.model.volatility.VolatilityDataFittingDefaults;
import com.opengamma.financial.analytics.model.volatility.cube.SABRNonLinearLeastSquaresSwaptionCubeFittingDefaults;
import com.opengamma.financial.analytics.model.volatility.cube.SABRNonLinearLeastSquaresSwaptionCubeFittingFunction;
import com.opengamma.financial.analytics.model.volatility.local.EquityDupireLocalVolatilitySurfaceFunction;
import com.opengamma.financial.analytics.model.volatility.local.ForexDupireLocalVolatilitySurfaceFunction;
import com.opengamma.financial.analytics.model.volatility.local.defaultproperties.BackwardPDEDefaults;
import com.opengamma.financial.analytics.model.volatility.local.defaultproperties.ForwardPDEDefaults;
import com.opengamma.financial.analytics.model.volatility.local.defaultproperties.LocalVolatilitySurfaceDefaults;
import com.opengamma.financial.analytics.model.volatility.surface.BlackScholesMertonImpliedVolatilitySurfaceFunction;
import com.opengamma.financial.analytics.model.volatility.surface.SABRNonLinearLeastSquaresIRFutureOptionSurfaceFittingFunction;
import com.opengamma.financial.analytics.model.volatility.surface.SABRNonLinearLeastSquaresIRFutureSurfaceDefaultValuesFunction;
import com.opengamma.financial.analytics.model.volatility.surface.black.BlackVolatilitySurfaceMixedLogNormalInterpolatorFunction;
import com.opengamma.financial.analytics.model.volatility.surface.black.BlackVolatilitySurfacePropertyNamesAndValues;
import com.opengamma.financial.analytics.model.volatility.surface.black.BlackVolatilitySurfaceSABRInterpolatorFunction;
import com.opengamma.financial.analytics.model.volatility.surface.black.BlackVolatilitySurfaceSplineInterpolatorFunction;
import com.opengamma.financial.analytics.model.volatility.surface.black.EquityBlackVolatilitySurfaceFunction;
import com.opengamma.financial.analytics.model.volatility.surface.black.ForexBlackVolatilitySurfaceFunction;
import com.opengamma.financial.analytics.model.volatility.surface.black.defaultproperties.BlackVolatilitySurfaceMixedLogNormalDefaults;
import com.opengamma.financial.analytics.model.volatility.surface.black.defaultproperties.BlackVolatilitySurfaceSABRDefaults;
import com.opengamma.financial.analytics.model.volatility.surface.black.defaultproperties.BlackVolatilitySurfaceSplineDefaults;
import com.opengamma.financial.analytics.model.volatility.surface.black.defaultproperties.EquityBlackVolatilitySurfaceDefaults;
import com.opengamma.financial.analytics.model.volatility.surface.black.defaultproperties.FXBlackVolatilitySurfaceDefaults;
import com.opengamma.financial.analytics.model.volatility.surface.black.pure.PureBlackVolatilitySurfaceDividendCorrectionFunction;
import com.opengamma.financial.analytics.model.volatility.surface.black.pure.PureBlackVolatilitySurfaceNoDividendCorrectionFunction;
import com.opengamma.financial.analytics.timeseries.DefaultHistoricalTimeSeriesShiftFunction;
import com.opengamma.financial.analytics.timeseries.FXVolatilitySurfaceHistoricalTimeSeriesFunction;
import com.opengamma.financial.analytics.timeseries.HistoricalTimeSeriesFunction;
import com.opengamma.financial.analytics.timeseries.HistoricalTimeSeriesLatestSecurityValueFunction;
import com.opengamma.financial.analytics.timeseries.HistoricalTimeSeriesLatestValueFunction;
import com.opengamma.financial.analytics.timeseries.HistoricalTimeSeriesSecurityFunction;
import com.opengamma.financial.analytics.timeseries.YieldCurveHistoricalTimeSeriesFunction;
import com.opengamma.financial.analytics.timeseries.YieldCurveInstrumentConversionHistoricalTimeSeriesFunction;
import com.opengamma.financial.analytics.timeseries.YieldCurveInstrumentConversionHistoricalTimeSeriesFunctionDeprecated;
import com.opengamma.financial.analytics.timeseries.YieldCurveInstrumentConversionHistoricalTimeSeriesShiftFunctionDeprecated;
import com.opengamma.financial.analytics.volatility.surface.DefaultVolatilitySurfaceShiftFunction;
import com.opengamma.financial.analytics.volatility.surface.VolatilitySurfaceShiftFunction;
import com.opengamma.financial.currency.BondFutureOptionBlackPnLSeriesCurrencyConversionFunction;
import com.opengamma.financial.currency.CurrencyMatrixConfigPopulator;
import com.opengamma.financial.currency.CurrencyMatrixSourcingFunction;
import com.opengamma.financial.currency.CurrencyPairs;
import com.opengamma.financial.currency.FXOptionBlackPnLSeriesCurrencyConversionFunction;
import com.opengamma.financial.currency.FixedIncomeInstrumentPnLSeriesCurrencyConversionFunction;
import com.opengamma.financial.currency.FixedIncomeInstrumentPnLSeriesCurrencyConversionFunctionDeprecated;
import com.opengamma.financial.currency.PortfolioNodeCurrencyConversionFunction;
import com.opengamma.financial.currency.PortfolioNodeDefaultCurrencyFunction;
import com.opengamma.financial.currency.PositionCurrencyConversionFunction;
import com.opengamma.financial.currency.PositionDefaultCurrencyFunction;
import com.opengamma.financial.currency.SecurityCurrencyConversionFunction;
import com.opengamma.financial.currency.SecurityDefaultCurrencyFunction;
import com.opengamma.financial.currency.YCNSPnLSeriesCurrencyConversionFunction;
import com.opengamma.financial.property.AggregationDefaultPropertyFunction;
import com.opengamma.financial.property.DefaultPropertyFunction.PriorityClass;
import com.opengamma.financial.property.PortfolioNodeCalcConfigDefaultPropertyFunction;
import com.opengamma.financial.property.PositionCalcConfigDefaultPropertyFunction;
import com.opengamma.financial.property.PositionDefaultPropertyFunction;
import com.opengamma.financial.property.PrimitiveCalcConfigDefaultPropertyFunction;
import com.opengamma.financial.property.SecurityCalcConfigDefaultPropertyFunction;
import com.opengamma.financial.property.TradeCalcConfigDefaultPropertyFunction;
import com.opengamma.financial.property.TradeDefaultPropertyFunction;
import com.opengamma.financial.value.ForwardPricePositionRenamingFunction;
import com.opengamma.financial.value.ForwardPriceSecurityRenamingFunction;
import com.opengamma.financial.value.ForwardPriceTradeRenamingFunction;
import com.opengamma.financial.value.PositionValueFunction;
import com.opengamma.financial.value.SecurityValueFunction;
import com.opengamma.util.SingletonFactoryBean;
import com.opengamma.util.fudgemsg.OpenGammaFudgeContext;

/**
 * Constructs a standard function repository.
 * <p>
 * This should be replaced by something that loads the functions from the configuration database
 */
public class DemoStandardFunctionConfiguration extends SingletonFactoryBean<RepositoryConfigurationSource> {

  private static final boolean OUTPUT_REPO_CONFIGURATION = false;

  public static <F extends FunctionDefinition> FunctionConfiguration functionConfiguration(final Class<F> clazz, final String... args) {
    if (Modifier.isAbstract(clazz.getModifiers())) {
      throw new IllegalStateException("Attempting to register an abstract class - " + clazz);
    }
    if (args.length == 0) {
      return new StaticFunctionConfiguration(clazz.getName());
    }
    return new ParameterizedFunctionConfiguration(clazz.getName(), Arrays.asList(args));
  }

  protected static void addValueFunctions(final List<FunctionConfiguration> functionConfigs) {
    addSummingFunction(functionConfigs, ValueRequirementNames.VALUE);
    functionConfigs.add(functionConfiguration(PositionValueFunction.class));
    functionConfigs.add(functionConfiguration(SecurityValueFunction.class));
    functionConfigs.add(functionConfiguration(ForwardPricePositionRenamingFunction.class));
    functionConfigs.add(functionConfiguration(ForwardPriceSecurityRenamingFunction.class));
    functionConfigs.add(functionConfiguration(ForwardPriceTradeRenamingFunction.class));
  }

  public static void addScalingFunction(final List<FunctionConfiguration> functionConfigs, final String requirementName) {
    functionConfigs.add(functionConfiguration(PositionScalingFunction.class, requirementName));
    functionConfigs.add(functionConfiguration(PositionTradeScalingFunction.class, requirementName));
  }

  public static void addUnitScalingFunction(final List<FunctionConfiguration> functionConfigs, final String requirementName) {
    functionConfigs.add(functionConfiguration(UnitPositionScalingFunction.class, requirementName));
    functionConfigs.add(functionConfiguration(UnitPositionTradeScalingFunction.class, requirementName));
  }

  /**
   * Adds a summing function for the value.
   *
   * @param functionConfigs the configuration block to add the definition to
   * @param requirementName the requirement to sum at portfolio node levels
   */
  public static void addSummingFunction(final List<FunctionConfiguration> functionConfigs, final String requirementName) {
    functionConfigs.add(functionConfiguration(FilteringSummingFunction.class, requirementName));
    functionConfigs.add(functionConfiguration(SummingFunction.class, requirementName));
    functionConfigs.add(functionConfiguration(AggregationDefaultPropertyFunction.class, requirementName, MissingInputsFunction.AGGREGATION_STYLE_FULL,
        FilteringSummingFunction.AGGREGATION_STYLE_FILTERED));
  }

  protected static void addValueGreekAndSummingFunction(final List<FunctionConfiguration> functionConfigs, final String requirementName) {
    functionConfigs.add(functionConfiguration(OptionGreekToValueGreekConverterFunction.class, requirementName));
    addSummingFunction(functionConfigs, requirementName);
  }

  protected static void addCurrencyConversionFunctions(final List<FunctionConfiguration> functionConfigs, final String requirementName) {
    functionConfigs.add(functionConfiguration(PortfolioNodeCurrencyConversionFunction.class, requirementName));
    functionConfigs.add(functionConfiguration(PositionCurrencyConversionFunction.class, requirementName));
    functionConfigs.add(functionConfiguration(SecurityCurrencyConversionFunction.class, requirementName));
    functionConfigs.add(functionConfiguration(PortfolioNodeDefaultCurrencyFunction.Permissive.class, requirementName));
    functionConfigs.add(functionConfiguration(PositionDefaultCurrencyFunction.Permissive.class, requirementName));
    functionConfigs.add(functionConfiguration(SecurityDefaultCurrencyFunction.Permissive.class, requirementName));
  }

  protected static void addCurrencyConversionFunctions(final List<FunctionConfiguration> functionConfigs) {
    addCurrencyConversionFunctions(functionConfigs, ValueRequirementNames.FAIR_VALUE);
    addCurrencyConversionFunctions(functionConfigs, ValueRequirementNames.PV01);
    addCurrencyConversionFunctions(functionConfigs, ValueRequirementNames.DV01);
    addCurrencyConversionFunctions(functionConfigs, ValueRequirementNames.PRESENT_VALUE);
    addCurrencyConversionFunctions(functionConfigs, ValueRequirementNames.DAILY_PNL);
    //TODO PRESENT_VALUE_CURVE_SENSITIVITY
    addCurrencyConversionFunctions(functionConfigs, ValueRequirementNames.VALUE_DELTA);
    addCurrencyConversionFunctions(functionConfigs, ValueRequirementNames.VALUE_GAMMA);
    addCurrencyConversionFunctions(functionConfigs, ValueRequirementNames.VALUE_VEGA);
    addCurrencyConversionFunctions(functionConfigs, ValueRequirementNames.VALUE_THETA);
    addCurrencyConversionFunctions(functionConfigs, ValueRequirementNames.VALUE_SPEED);
    addCurrencyConversionFunctions(functionConfigs, ValueRequirementNames.VALUE_VOMMA);
    addCurrencyConversionFunctions(functionConfigs, ValueRequirementNames.VALUE_VANNA);
    addCurrencyConversionFunctions(functionConfigs, ValueRequirementNames.VALUE_RHO);
    addCurrencyConversionFunctions(functionConfigs, ValueRequirementNames.VALUE_PHI);

    functionConfigs.add(functionConfiguration(SecurityCurrencyConversionFunction.class, ValueRequirementNames.YIELD_CURVE_NODE_SENSITIVITIES));
    functionConfigs.add(functionConfiguration(PortfolioNodeDefaultCurrencyFunction.Permissive.class, ValueRequirementNames.YIELD_CURVE_NODE_SENSITIVITIES));
    functionConfigs.add(functionConfiguration(CurrencyMatrixSourcingFunction.class, CurrencyMatrixConfigPopulator.BLOOMBERG_LIVE_DATA));
    functionConfigs.add(functionConfiguration(CurrencyMatrixSourcingFunction.class, CurrencyMatrixConfigPopulator.SYNTHETIC_LIVE_DATA));
    functionConfigs.add(functionConfiguration(FixedIncomeInstrumentPnLSeriesCurrencyConversionFunction.class, CurrencyMatrixConfigPopulator.BLOOMBERG_LIVE_DATA));
    functionConfigs.add(functionConfiguration(FixedIncomeInstrumentPnLSeriesCurrencyConversionFunction.class, CurrencyMatrixConfigPopulator.SYNTHETIC_LIVE_DATA));
    functionConfigs.add(functionConfiguration(YCNSPnLSeriesCurrencyConversionFunction.class, CurrencyMatrixConfigPopulator.BLOOMBERG_LIVE_DATA));
    functionConfigs.add(functionConfiguration(YCNSPnLSeriesCurrencyConversionFunction.class, CurrencyMatrixConfigPopulator.SYNTHETIC_LIVE_DATA));
    functionConfigs.add(functionConfiguration(FXForwardCurrencyExposurePnLCurrencyConversionFunction.class, CurrencyMatrixConfigPopulator.BLOOMBERG_LIVE_DATA));
    functionConfigs.add(functionConfiguration(FXForwardCurrencyExposurePnLCurrencyConversionFunction.class, CurrencyMatrixConfigPopulator.SYNTHETIC_LIVE_DATA));
    functionConfigs.add(functionConfiguration(FXForwardYCNSPnLCurrencyConversionFunction.class, CurrencyMatrixConfigPopulator.BLOOMBERG_LIVE_DATA));
    functionConfigs.add(functionConfiguration(FXForwardYCNSPnLCurrencyConversionFunction.class, CurrencyMatrixConfigPopulator.SYNTHETIC_LIVE_DATA));
    functionConfigs.add(functionConfiguration(FXOptionBlackPnLSeriesCurrencyConversionFunction.class, CurrencyMatrixConfigPopulator.BLOOMBERG_LIVE_DATA));
    functionConfigs.add(functionConfiguration(FXOptionBlackPnLSeriesCurrencyConversionFunction.class, CurrencyMatrixConfigPopulator.SYNTHETIC_LIVE_DATA));
    functionConfigs.add(functionConfiguration(BondFutureOptionBlackPnLSeriesCurrencyConversionFunction.class, CurrencyMatrixConfigPopulator.BLOOMBERG_LIVE_DATA));
    functionConfigs.add(functionConfiguration(BondFutureOptionBlackPnLSeriesCurrencyConversionFunction.class, CurrencyMatrixConfigPopulator.SYNTHETIC_LIVE_DATA));
    functionConfigs
        .add(functionConfiguration(FixedIncomeInstrumentPnLSeriesCurrencyConversionFunctionDeprecated.class, CurrencyMatrixConfigPopulator.BLOOMBERG_LIVE_DATA));
    functionConfigs
        .add(functionConfiguration(FixedIncomeInstrumentPnLSeriesCurrencyConversionFunctionDeprecated.class, CurrencyMatrixConfigPopulator.SYNTHETIC_LIVE_DATA));
  }

  protected static void addLateAggregationFunctions(final List<FunctionConfiguration> functionConfigs) {
    functionConfigs.add(functionConfiguration(BottomPositionValues.class));
    functionConfigs.add(functionConfiguration(SortedPositionValues.class));
    functionConfigs.add(functionConfiguration(TopPositionValues.class));
  }

  protected static void addDataShiftingFunctions(final List<FunctionConfiguration> functionConfigs) {
    functionConfigs.add(functionConfiguration(VolatilitySurfaceShiftFunction.class));
    functionConfigs.add(functionConfiguration(DefaultVolatilitySurfaceShiftFunction.class));
    functionConfigs.add(functionConfiguration(YieldCurveShiftFunction.class));
    functionConfigs.add(functionConfiguration(DefaultYieldCurveShiftFunction.class));
    functionConfigs.add(functionConfiguration(YieldCurveMarketDataShiftFunction.class));
    functionConfigs.add(functionConfiguration(DefaultYieldCurveMarketDataShiftFunction.class));
  }

  protected static void addDefaultPropertyFunctions(final List<FunctionConfiguration> functionConfigs) {
    functionConfigs.add(functionConfiguration(PortfolioNodeCalcConfigDefaultPropertyFunction.Generic.class));
    functionConfigs.add(functionConfiguration(PortfolioNodeCalcConfigDefaultPropertyFunction.Specific.class));
    functionConfigs.add(functionConfiguration(PositionCalcConfigDefaultPropertyFunction.Generic.class));
    functionConfigs.add(functionConfiguration(PositionCalcConfigDefaultPropertyFunction.Specific.class));
    functionConfigs.add(functionConfiguration(PrimitiveCalcConfigDefaultPropertyFunction.Generic.class));
    functionConfigs.add(functionConfiguration(PrimitiveCalcConfigDefaultPropertyFunction.Specific.class));
    functionConfigs.add(functionConfiguration(SecurityCalcConfigDefaultPropertyFunction.Generic.class));
    functionConfigs.add(functionConfiguration(SecurityCalcConfigDefaultPropertyFunction.Specific.class));
    functionConfigs.add(functionConfiguration(TradeCalcConfigDefaultPropertyFunction.Generic.class));
    functionConfigs.add(functionConfiguration(TradeCalcConfigDefaultPropertyFunction.Specific.class));
    functionConfigs.add(functionConfiguration(PositionDefaultPropertyFunction.class));
    functionConfigs.add(functionConfiguration(TradeDefaultPropertyFunction.class));
  }

  protected static void addHistoricalDataFunctions(final List<FunctionConfiguration> functionConfigs, final String requirementName) {
    addUnitScalingFunction(functionConfigs, requirementName);
    functionConfigs.add(functionConfiguration(LastHistoricalValueFunction.class, requirementName));
  }

  protected static void addHistoricalDataFunctions(final List<FunctionConfiguration> functionConfigs) {
    addHistoricalDataFunctions(functionConfigs, ValueRequirementNames.DAILY_VOLUME);
    addHistoricalDataFunctions(functionConfigs, ValueRequirementNames.DAILY_MARKET_CAP);
    addHistoricalDataFunctions(functionConfigs, ValueRequirementNames.DAILY_APPLIED_BETA);
    addHistoricalDataFunctions(functionConfigs, ValueRequirementNames.DAILY_PRICE);
    functionConfigs.add(functionConfiguration(HistoricalTimeSeriesFunction.class));
    functionConfigs.add(functionConfiguration(HistoricalTimeSeriesSecurityFunction.class));
    functionConfigs.add(functionConfiguration(HistoricalTimeSeriesLatestValueFunction.class));
    functionConfigs.add(functionConfiguration(HistoricalTimeSeriesLatestSecurityValueFunction.class));
    functionConfigs.add(functionConfiguration(YieldCurveHistoricalTimeSeriesFunction.class));
    functionConfigs.add(functionConfiguration(FXVolatilitySurfaceHistoricalTimeSeriesFunction.class));
    functionConfigs.add(functionConfiguration(YieldCurveInstrumentConversionHistoricalTimeSeriesFunction.class));
    functionConfigs.add(functionConfiguration(YieldCurveInstrumentConversionHistoricalTimeSeriesFunctionDeprecated.class));
    functionConfigs.add(functionConfiguration(YieldCurveInstrumentConversionHistoricalTimeSeriesShiftFunctionDeprecated.class));
    functionConfigs.add(functionConfiguration(DefaultHistoricalTimeSeriesShiftFunction.class));
    functionConfigs.add(functionConfiguration(CurrencyPairsFunction.class));
    functionConfigs.add(functionConfiguration(CurrencyPairsDefaults.class, CurrencyPairs.DEFAULT_CURRENCY_PAIRS));
  }

  public static RepositoryConfiguration constructRepositoryConfiguration() {
    final List<FunctionConfiguration> functionConfigs = new ArrayList<FunctionConfiguration>();

    addValueFunctions(functionConfigs);

    functionConfigs.add(functionConfiguration(SecurityMarketPriceFunction.class));
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.SECURITY_IMPLIED_VOLATILITY);

    // options
    functionConfigs.add(functionConfiguration(BlackScholesMertonModelFunction.class));
    functionConfigs.add(functionConfiguration(BlackScholesMertonImpliedVolatilitySurfaceFunction.class));
    functionConfigs.add(functionConfiguration(BlackScholesModelCostOfCarryFunction.class));

    // equity and portfolio
    functionConfigs.add(functionConfiguration(PositionExchangeTradedPnLFunction.class));
    functionConfigs.add(functionConfiguration(PortfolioExchangeTradedPnLFunction.class));
    functionConfigs.add(functionConfiguration(PortfolioExchangeTradedDailyPnLFunction.Impl.class));
    functionConfigs.add(functionConfiguration(AggregationDefaultPropertyFunction.class, ValueRequirementNames.DAILY_PNL, MissingInputsFunction.AGGREGATION_STYLE_FULL));

    addPnLCalculators(functionConfigs);
    addVaRCalculators(functionConfigs);
    addPortfolioAnalysisCalculators(functionConfigs);
    addFixedIncomeInstrumentCalculators(functionConfigs);

    functionConfigs.add(functionConfiguration(StandardEquityModelFunction.class));
    addBondCalculators(functionConfigs);
    addBondFutureCalculators(functionConfigs);
    addSABRCalculators(functionConfigs);
    addForexOptionCalculators(functionConfigs);
    addFXBarrierOptionCalculators(functionConfigs);
    addForexForwardCalculators(functionConfigs);
    addInterestRateFutureCalculators(functionConfigs);
    addSimpleFutureCalculators(functionConfigs);
    addInterestRateFutureOptionCalculators(functionConfigs);
    addBondFutureOptionCalculators(functionConfigs);
    addCommodityFutureOptionCalculators(functionConfigs);
    addEquityDerivativesCalculators(functionConfigs);
    addBlackCalculators(functionConfigs);
    addLocalVolatilityCalculators(functionConfigs);
    addLocalVolatilityGridFunctions(functionConfigs);
    addEquityVolatilitySurfaceCalculators(functionConfigs);
    addCDSCalculators(functionConfigs);
    
    addBlackVolatilitySurface(functionConfigs);
    addFXBlackVolatilitySurface(functionConfigs);
    addEquityBlackVolatilitySurface(functionConfigs);
    addLocalVolatilitySurface(functionConfigs);
    
    addExternallyProvidedSensitivitiesFunctions(functionConfigs);
    addCashFlowFunctions(functionConfigs);
    addScalingFunction(functionConfigs, ValueRequirementNames.FAIR_VALUE);

    addUnitScalingFunction(functionConfigs, ValueRequirementNames.SPOT_RATE_FOR_SECURITY);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.SPOT_FX_PERCENTAGE_CHANGE);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.DELTA);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.DELTA_BLEED);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.STRIKE_DELTA);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.DRIFTLESS_THETA);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.GAMMA);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.GAMMA_P);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.STRIKE_GAMMA);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.GAMMA_BLEED);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.GAMMA_P_BLEED);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.VEGA);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.VEGA_P);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.VARIANCE_VEGA);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.VEGA_BLEED);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.THETA);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.RHO);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.CARRY_RHO);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.ZETA);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.ZETA_BLEED);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.DZETA_DVOL);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.ELASTICITY);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.PHI);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.ZOMMA);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.ZOMMA_P);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.ULTIMA);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.VARIANCE_ULTIMA);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.SPEED);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.SPEED_P);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.VANNA);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.VARIANCE_VANNA);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.DVANNA_DVOL);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.VOMMA);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.VOMMA_P);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.VARIANCE_VOMMA);

    addUnitScalingFunction(functionConfigs, ValueRequirementNames.FORWARD_DELTA);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.FORWARD_GAMMA);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.DUAL_DELTA);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.DUAL_GAMMA);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.FORWARD_VEGA);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.FORWARD_VANNA);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.FORWARD_VOMMA);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.IMPLIED_VOLATILITY);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.GRID_FORWARD_DELTA);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.GRID_FORWARD_GAMMA);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.GRID_DUAL_DELTA);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.GRID_DUAL_GAMMA);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.GRID_FORWARD_VEGA);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.GRID_FORWARD_VANNA);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.GRID_FORWARD_VOMMA);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.GRID_IMPLIED_VOLATILITY);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.GRID_PRESENT_VALUE);

    addUnitScalingFunction(functionConfigs, ValueRequirementNames.BOND_TENOR);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.MARKET_DIRTY_PRICE);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.MARKET_CLEAN_PRICE);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.MARKET_YTM);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.CLEAN_PRICE);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.DIRTY_PRICE);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.YTM);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.MODIFIED_DURATION);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.Z_SPREAD);
    addScalingFunction(functionConfigs, ValueRequirementNames.PRESENT_VALUE_Z_SPREAD_SENSITIVITY);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.CONVEXITY);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.MACAULAY_DURATION);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.SECURITY_IMPLIED_VOLATILITY);

    addUnitScalingFunction(functionConfigs, ValueRequirementNames.GROSS_BASIS);
    addSummingFunction(functionConfigs, ValueRequirementNames.GROSS_BASIS);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.NET_BASIS);
    addSummingFunction(functionConfigs, ValueRequirementNames.NET_BASIS);

    addScalingFunction(functionConfigs, ValueRequirementNames.PV01);
    addScalingFunction(functionConfigs, ValueRequirementNames.PRESENT_VALUE);
    addScalingFunction(functionConfigs, ValueRequirementNames.PRESENT_VALUE_CURVE_SENSITIVITY);
    addScalingFunction(functionConfigs, ValueRequirementNames.PRESENT_VALUE_SABR_ALPHA_SENSITIVITY);
    addScalingFunction(functionConfigs, ValueRequirementNames.PRESENT_VALUE_SABR_NU_SENSITIVITY);
    addScalingFunction(functionConfigs, ValueRequirementNames.PRESENT_VALUE_SABR_RHO_SENSITIVITY);
    addScalingFunction(functionConfigs, ValueRequirementNames.PRESENT_VALUE_SABR_ALPHA_NODE_SENSITIVITY);
    addScalingFunction(functionConfigs, ValueRequirementNames.PRESENT_VALUE_SABR_RHO_NODE_SENSITIVITY);
    addScalingFunction(functionConfigs, ValueRequirementNames.PRESENT_VALUE_SABR_NU_NODE_SENSITIVITY);
    addScalingFunction(functionConfigs, ValueRequirementNames.YIELD_CURVE_NODE_SENSITIVITIES);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.PAR_RATE);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.PAR_RATE_PARALLEL_CURVE_SHIFT);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.PAR_RATE_CURVE_SENSITIVITY);

    addSummingFunction(functionConfigs, ValueRequirementNames.FAIR_VALUE);
    addSummingFunction(functionConfigs, ValueRequirementNames.PRESENT_VALUE);
    addSummingFunction(functionConfigs, ValueRequirementNames.PV01);
    addSummingFunction(functionConfigs, ValueRequirementNames.DV01);
    addSummingFunction(functionConfigs, ValueRequirementNames.CS01);
    addSummingFunction(functionConfigs, ValueRequirementNames.FX_CURRENCY_EXPOSURE);
    addSummingFunction(functionConfigs, ValueRequirementNames.FX_PRESENT_VALUE);
    addSummingFunction(functionConfigs, ValueRequirementNames.VEGA_MATRIX);
    addSummingFunction(functionConfigs, ValueRequirementNames.VEGA_QUOTE_MATRIX);
    addSummingFunction(functionConfigs, ValueRequirementNames.VEGA_QUOTE_CUBE);
    addSummingFunction(functionConfigs, ValueRequirementNames.PRESENT_VALUE_CURVE_SENSITIVITY);
    addScalingFunction(functionConfigs, ValueRequirementNames.PRESENT_VALUE_SABR_ALPHA_SENSITIVITY);
    addScalingFunction(functionConfigs, ValueRequirementNames.PRESENT_VALUE_SABR_NU_SENSITIVITY);
    addScalingFunction(functionConfigs, ValueRequirementNames.PRESENT_VALUE_SABR_RHO_SENSITIVITY);
    addSummingFunction(functionConfigs, ValueRequirementNames.PRESENT_VALUE_SABR_ALPHA_NODE_SENSITIVITY);
    addSummingFunction(functionConfigs, ValueRequirementNames.PRESENT_VALUE_SABR_RHO_NODE_SENSITIVITY);
    addSummingFunction(functionConfigs, ValueRequirementNames.PRESENT_VALUE_SABR_NU_NODE_SENSITIVITY);
    addSummingFunction(functionConfigs, ValueRequirementNames.PRICE_SERIES);
    addSummingFunction(functionConfigs, ValueRequirementNames.PNL_SERIES);
    addSummingFunction(functionConfigs, ValueRequirementNames.YIELD_CURVE_NODE_SENSITIVITIES);
    addSummingFunction(functionConfigs, ValueRequirementNames.EXTERNAL_SENSITIVITIES);
    addSummingFunction(functionConfigs, ValueRequirementNames.CREDIT_SENSITIVITIES);
    addSummingFunction(functionConfigs, ValueRequirementNames.WEIGHT);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.HISTORICAL_TIME_SERIES);
    addSummingFunction(functionConfigs, ValueRequirementNames.HISTORICAL_TIME_SERIES);

    addSummingFunction(functionConfigs, ValueRequirementNames.PRESENT_VALUE_Z_SPREAD_SENSITIVITY);
    addSummingFunction(functionConfigs, ValueRequirementNames.BOND_COUPON_PAYMENT_TIMES);
    addScalingFunction(functionConfigs, ValueRequirementNames.BOND_COUPON_PAYMENT_TIMES);

    addScalingFunction(functionConfigs, ValueRequirementNames.FX_PRESENT_VALUE);
    addScalingFunction(functionConfigs, ValueRequirementNames.FX_CURRENCY_EXPOSURE);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.FX_CURVE_SENSITIVITIES);

    addScalingFunction(functionConfigs, ValueRequirementNames.VEGA_MATRIX);
    addScalingFunction(functionConfigs, ValueRequirementNames.VEGA_QUOTE_MATRIX);
    addScalingFunction(functionConfigs, ValueRequirementNames.VEGA_QUOTE_CUBE);
    addScalingFunction(functionConfigs, ValueRequirementNames.VALUE_VEGA);
    addSummingFunction(functionConfigs, ValueRequirementNames.VALUE_VEGA);
    addScalingFunction(functionConfigs, ValueRequirementNames.VALUE_THETA);
    addSummingFunction(functionConfigs, ValueRequirementNames.VALUE_THETA);
    addScalingFunction(functionConfigs, ValueRequirementNames.VALUE_GAMMA);
    addSummingFunction(functionConfigs, ValueRequirementNames.VALUE_GAMMA);

    addScalingFunction(functionConfigs, ValueRequirementNames.VALUE_DELTA);
    addScalingFunction(functionConfigs, ValueRequirementNames.VALUE_RHO);
    addSummingFunction(functionConfigs, ValueRequirementNames.VALUE_RHO);
    addScalingFunction(functionConfigs, ValueRequirementNames.VALUE_PHI);
    addSummingFunction(functionConfigs, ValueRequirementNames.VALUE_PHI);

    addScalingFunction(functionConfigs, ValueRequirementNames.VALUE_VOMMA);
    addSummingFunction(functionConfigs, ValueRequirementNames.VALUE_VOMMA);
    addScalingFunction(functionConfigs, ValueRequirementNames.VALUE_VANNA);
    addSummingFunction(functionConfigs, ValueRequirementNames.VALUE_VANNA);

    addUnitScalingFunction(functionConfigs, ValueRequirementNames.SPOT);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.FORWARD);
    addValueGreekAndSummingFunction(functionConfigs, ValueRequirementNames.VALUE_DELTA);
    addValueGreekAndSummingFunction(functionConfigs, ValueRequirementNames.VALUE_VEGA);
    addValueGreekAndSummingFunction(functionConfigs, ValueRequirementNames.VALUE_GAMMA);
    addValueGreekAndSummingFunction(functionConfigs, ValueRequirementNames.VALUE_THETA);
    addValueGreekAndSummingFunction(functionConfigs, ValueRequirementNames.VALUE_SPEED);

    addCurrencyConversionFunctions(functionConfigs);
    addLateAggregationFunctions(functionConfigs);
    addDataShiftingFunctions(functionConfigs);
    addDefaultPropertyFunctions(functionConfigs);
    addHistoricalDataFunctions(functionConfigs);
    addPassthroughFunctions(functionConfigs);
    
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.NOTIONAL);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.ATTRIBUTES);
    functionConfigs.add(functionConfiguration(NotionalFunction.class));
    functionConfigs.add(functionConfiguration(AttributesFunction.class));

    functionConfigs.add(functionConfiguration(AnalyticOptionDefaultCurveFunction.class, "FUNDING"));
    functionConfigs.add(functionConfiguration(AnalyticOptionDefaultCurveFunction.class, "SECONDARY"));

    functionConfigs.add(functionConfiguration(ISDAApproxCDSPriceFlatSpreadFunction.class));
    functionConfigs.add(functionConfiguration(ISDAApproxCDSPriceHazardCurveFunction.class));
    functionConfigs.add(functionConfiguration(ISDAApproxFlatSpreadFunction.class));
    functionConfigs.add(functionConfiguration(ISDAApproxDiscountCurveFunction.class));


    final RepositoryConfiguration repoConfig = new RepositoryConfiguration(functionConfigs);

    if (OUTPUT_REPO_CONFIGURATION) {
      final FudgeMsg msg = OpenGammaFudgeContext.getInstance().toFudgeMsg(repoConfig).getMessage();
      FudgeMsgFormatter.outputToSystemOut(msg);
      try {
        final FudgeXMLSettings xmlSettings = new FudgeXMLSettings();
        xmlSettings.setEnvelopeElementName(null);
        final FudgeMsgWriter msgWriter = new FudgeMsgWriter(new FudgeXMLStreamWriter(FudgeContext.GLOBAL_DEFAULT, new OutputStreamWriter(System.out), xmlSettings));
        msgWriter.setDefaultMessageProcessingDirectives(0);
        msgWriter.setDefaultMessageVersion(0);
        msgWriter.setDefaultTaxonomyId(0);
        msgWriter.writeMessage(msg);
        msgWriter.flush();
      } catch (final Exception e) {
        // Just swallow it.
      }
    }
    return repoConfig;
  }

  private static void addPnLCalculators(final List<FunctionConfiguration> functionConfigs) {
    final String defaultReturnCalculatorName = TimeSeriesReturnCalculatorFactory.SIMPLE_NET_LENIENT;
    final String defaultSamplingPeriodName = "P2Y";
    final String defaultScheduleName = ScheduleCalculatorFactory.DAILY;
    final String defaultSamplingCalculatorName = TimeSeriesSamplingFunctionFactory.PREVIOUS_AND_FIRST_VALUE_PADDING;
    functionConfigs.add(functionConfiguration(TradeExchangeTradedPnLFunction.class, DEFAULT_CONFIG_NAME, "PX_LAST", "COST_OF_CARRY"));
    functionConfigs.add(functionConfiguration(TradeExchangeTradedDailyPnLFunction.class, DEFAULT_CONFIG_NAME, "PX_LAST", "COST_OF_CARRY"));
    functionConfigs.add(functionConfiguration(PositionExchangeTradedDailyPnLFunction.class, DEFAULT_CONFIG_NAME, "PX_LAST", "COST_OF_CARRY"));
    functionConfigs.add(functionConfiguration(SecurityPriceSeriesFunction.class, DEFAULT_CONFIG_NAME, MarketDataRequirementNames.MARKET_VALUE));
    functionConfigs.add(functionConfiguration(SecurityPriceSeriesDefaultPropertiesFunction.class, defaultSamplingPeriodName, defaultScheduleName,
        defaultSamplingCalculatorName));
    functionConfigs.add(functionConfiguration(EquityPnLFunction.class));
    functionConfigs.add(functionConfiguration(EquityPnLDefaultPropertiesFunction.class, defaultSamplingPeriodName, defaultScheduleName, defaultSamplingCalculatorName,
        defaultReturnCalculatorName));
    functionConfigs.add(functionConfiguration(SimpleFuturePnLFunction.class, DEFAULT_CONFIG_NAME));
    functionConfigs.add(functionConfiguration(SimpleFuturePnLDefaultPropertiesFunction.class, "FUNDING", defaultSamplingPeriodName, defaultScheduleName,
        defaultSamplingCalculatorName));
    functionConfigs.add(functionConfiguration(SimpleFXFuturePnLFunction.class, DEFAULT_CONFIG_NAME));
    functionConfigs.add(functionConfiguration(SimpleFXFuturePnLDefaultPropertiesFunction.class, "FUNDING", "FUNDING", defaultSamplingPeriodName, defaultScheduleName,
        defaultSamplingCalculatorName));
    functionConfigs.add(functionConfiguration(ValueGreekSensitivityPnLFunction.class, DEFAULT_CONFIG_NAME));
    functionConfigs.add(functionConfiguration(ValueGreekSensitivityPnLDefaultPropertiesFunction.class, defaultSamplingPeriodName, defaultScheduleName,
        defaultSamplingCalculatorName, defaultReturnCalculatorName));
  }

  private static void addVaRCalculators(final List<FunctionConfiguration> functionConfigs) {
    final String defaultSamplingPeriodName = "P2Y";
    final String defaultScheduleName = ScheduleCalculatorFactory.DAILY;
    final String defaultSamplingCalculatorName = TimeSeriesSamplingFunctionFactory.PREVIOUS_AND_FIRST_VALUE_PADDING;
    final String defaultMeanCalculatorName = StatisticsCalculatorFactory.MEAN;
    final String defaultStdDevCalculatorName = StatisticsCalculatorFactory.SAMPLE_STANDARD_DEVIATION;
    final String defaultConfidenceLevelName = "0.99";
    final String defaultHorizonName = "1";

    //   functionConfigs.add(functionConfiguration(OptionPositionParametricVaRFunction.class, DEFAULT_CONFIG_NAME));
    //functionConfigs.add(functionConfiguration(OptionPortfolioParametricVaRFunction.class, DEFAULT_CONFIG_NAME, startDate, defaultReturnCalculatorName,
    //  defaultScheduleName, defaultSamplingCalculatorName, "0.99", "1", ValueRequirementNames.VALUE_DELTA));
    //functionConfigs.add(functionConfiguration(PositionValueGreekSensitivityPnLFunction.class, DEFAULT_CONFIG_NAME, startDate, defaultReturnCalculatorName,
    //  defaultScheduleName, defaultSamplingCalculatorName, ValueRequirementNames.VALUE_DELTA));
    functionConfigs.add(functionConfiguration(NormalPositionHistoricalVaRFunction.class));
    functionConfigs.add(functionConfiguration(NormalPortfolioHistoricalVaRFunction.class));
    functionConfigs.add(functionConfiguration(NormalPositionHistoricalVaRDefaultPropertiesFunction.class, defaultSamplingPeriodName, defaultScheduleName,
        defaultSamplingCalculatorName, defaultMeanCalculatorName, defaultStdDevCalculatorName, defaultConfidenceLevelName, defaultHorizonName,
        PriorityClass.NORMAL.name()));
    functionConfigs.add(functionConfiguration(NormalPortfolioHistoricalVaRDefaultPropertiesFunction.class, defaultSamplingPeriodName, defaultScheduleName,
        defaultSamplingCalculatorName, defaultMeanCalculatorName, defaultStdDevCalculatorName, defaultConfidenceLevelName, defaultHorizonName,
        PriorityClass.NORMAL.name()));
  }

  private static void addEquityDerivativesCalculators(final List<FunctionConfiguration> functionConfigs) {
    functionConfigs.add(new ParameterizedFunctionConfiguration(EquityFuturesFunction.class.getName(), Arrays.asList(ValueRequirementNames.PRESENT_VALUE,
        EquityFuturePricerFactory.MARK_TO_MARKET)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(EquityFuturesFunction.class.getName(), Arrays.asList(ValueRequirementNames.PRESENT_VALUE,
        EquityFuturePricerFactory.DIVIDEND_YIELD)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(EquityFuturesFunction.class.getName(), Arrays.asList(ValueRequirementNames.PV01,
        EquityFuturePricerFactory.MARK_TO_MARKET)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(EquityFuturesFunction.class.getName(), Arrays.asList(ValueRequirementNames.PV01,
        EquityFuturePricerFactory.DIVIDEND_YIELD)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(EquityFuturesFunction.class.getName(), Arrays.asList(ValueRequirementNames.VALUE_RHO,
        EquityFuturePricerFactory.MARK_TO_MARKET)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(EquityFuturesFunction.class.getName(), Arrays.asList(ValueRequirementNames.VALUE_RHO,
        EquityFuturePricerFactory.DIVIDEND_YIELD)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(EquityFuturesFunction.class.getName(), Arrays.asList(ValueRequirementNames.VALUE_DELTA,
        EquityFuturePricerFactory.MARK_TO_MARKET)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(EquityFuturesFunction.class.getName(), Arrays.asList(ValueRequirementNames.VALUE_DELTA,
        EquityFuturePricerFactory.DIVIDEND_YIELD)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(EquityFuturesFunction.class.getName(), Arrays.asList(ValueRequirementNames.SPOT,
        EquityFuturePricerFactory.MARK_TO_MARKET)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(EquityFuturesFunction.class.getName(), Arrays.asList(ValueRequirementNames.SPOT,
        EquityFuturePricerFactory.DIVIDEND_YIELD)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(EquityFuturesFunction.class.getName(), Arrays.asList(ValueRequirementNames.FORWARD,
        EquityFuturePricerFactory.MARK_TO_MARKET)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(EquityFuturesFunction.class.getName(), Arrays.asList(ValueRequirementNames.FORWARD,
        EquityFuturePricerFactory.DIVIDEND_YIELD)));
    functionConfigs.add(functionConfiguration(EquityFuturesYieldCurveNodeSensitivityFunction.class, EquityFuturePricerFactory.MARK_TO_MARKET));
    functionConfigs.add(functionConfiguration(EquityFuturesYieldCurveNodeSensitivityFunction.class, EquityFuturePricerFactory.DIVIDEND_YIELD));
    functionConfigs.add(functionConfiguration(EquityFuturesDefaultPropertiesFunction.class, PriorityClass.NORMAL.name(), EquityFuturePricerFactory.MARK_TO_MARKET,
        "USD", "DefaultTwoCurveUSDConfig", "Discounting",
        "EUR", "DefaultTwoCurveEURConfig", "Discounting",
        "CAD", "DefaultTwoCurveCADConfig", "Discounting",
        "AUD", "DefaultTwoCurveAUDConfig", "Discounting",
        "CHF", "DefaultTwoCurveCHFConfig", "Discounting",
        "MXN", "DefaultCashCurveMXNConfig", "Cash",
        "JPY", "DefaultTwoCurveJPYConfig", "Discounting",
        "GBP", "DefaultTwoCurveGBPConfig", "Discounting",
        "NZD", "DefaultTwoCurveNZDConfig", "Discounting",
        "HUF", "DefaultCashCurveHUFConfig", "Cash",
        "KRW", "DefaultCashCurveKRWConfig", "Cash",
        "BRL", "DefaultCashCurveBRLConfig", "Cash",
        "HKD", "DefaultCashCurveHKDConfig", "Cash"));
    functionConfigs.add(functionConfiguration(EquityForwardFromSpotAndYieldCurveFunction.class));
    functionConfigs.add(functionConfiguration(EquityForwardCalculationDefaults.class, PriorityClass.ABOVE_NORMAL.name(),
        "DJX Index", "Discounting", "DefaultTwoCurveUSDConfig"));
    functionConfigs.add(functionConfiguration(EquityVarianceSwapStaticReplicationPresentValueFunction.class));
    functionConfigs.add(functionConfiguration(EquityVarianceSwapStaticReplicationYCNSFunction.class));
    functionConfigs.add(functionConfiguration(EquityVarianceSwapStaticReplicationVegaFunction.class));
    functionConfigs.add(functionConfiguration(EquityVarianceSwapDefaults.class, PriorityClass.ABOVE_NORMAL.name(),
        "DJX Index", "Discounting", "DefaultTwoCurveUSDConfig", "BBG"));
    functionConfigs.add(functionConfiguration(EquityForwardCurveFunction.class));
    functionConfigs.add(functionConfiguration(EquityForwardCurveDefaults.class, PriorityClass.ABOVE_NORMAL.name(),
        "SPX Index", "Discounting", "DefaultTwoCurveUSDConfig", "USD",
        "DJX Index", "Discounting", "DefaultTwoCurveUSDConfig", "USD",
        "NKY Index", "Discounting", "DefaultTwoCurveJPYConfig", "JPY"));

    functionConfigs.add(functionConfiguration(EquityIndexOptionPresentValueFunction.class));
    functionConfigs.add(functionConfiguration(EquityIndexOptionImpliedVolFunction.class));
    functionConfigs.add(functionConfiguration(EquityIndexOptionForwardValueFunction.class));
    functionConfigs.add(functionConfiguration(EquityIndexOptionSpotIndexFunction.class));
    functionConfigs.add(functionConfiguration(EquityIndexOptionSpotDeltaFunction.class));
    functionConfigs.add(functionConfiguration(EquityIndexOptionFundingCurveSensitivitiesFunction.class));
    functionConfigs.add(functionConfiguration(EquityIndexOptionVegaMatrixFunction.class));
    functionConfigs.add(functionConfiguration(EquityIndexOptionVegaFunction.class));
    functionConfigs.add(functionConfiguration(EquityIndexOptionSpotGammaFunction.class));
    functionConfigs.add(functionConfiguration(EquityIndexOptionSpotVannaFunction.class));
    functionConfigs.add(functionConfiguration(EquityIndexOptionVommaFunction.class));
    functionConfigs.add(functionConfiguration(EquityIndexOptionRhoFunction.class));
    functionConfigs.add(functionConfiguration(EquityIndexOptionDefaultPropertiesFunction.class, PriorityClass.NORMAL.name(), "BBG", "Spline",
        "USD", "DefaultTwoCurveUSDConfig", "Discounting",
        "EUR", "DefaultTwoCurveEURConfig", "Discounting",
        "CAD", "DefaultTwoCurveCADConfig", "Discounting",
        "AUD", "DefaultTwoCurveAUDConfig", "Discounting",
        "CHF", "DefaultTwoCurveCHFConfig", "Discounting",
        "MXN", "DefaultCashCurveMXNConfig", "Cash",
        "JPY", "DefaultTwoCurveJPYConfig", "Discounting",
        "GBP", "DefaultTwoCurveGBPConfig", "Discounting",
        "NZD", "DefaultTwoCurveNZDConfig", "Discounting",
        "HUF", "DefaultCashCurveHUFConfig", "Cash",
        "KRW", "DefaultCashCurveKRWConfig", "Cash",
        "BRL", "DefaultCashCurveBRLConfig", "Cash",
        "HKD", "DefaultCashCurveHKDConfig", "Cash"));
    functionConfigs.add(functionConfiguration(EquityIndexVanillaBarrierOptionPresentValueFunction.class));
    functionConfigs.add(functionConfiguration(EquityIndexVanillaBarrierOptionSpotIndexFunction.class));
    functionConfigs.add(functionConfiguration(EquityIndexVanillaBarrierOptionForwardValueFunction.class));
    functionConfigs.add(functionConfiguration(EquityIndexVanillaBarrierOptionRhoFunction.class));
    functionConfigs.add(functionConfiguration(EquityIndexVanillaBarrierOptionSpotDeltaFunction.class));
    functionConfigs.add(functionConfiguration(EquityIndexVanillaBarrierOptionSpotGammaFunction.class));
    functionConfigs.add(functionConfiguration(EquityIndexVanillaBarrierOptionSpotVannaFunction.class));
    functionConfigs.add(functionConfiguration(EquityIndexVanillaBarrierOptionVegaFunction.class));
    functionConfigs.add(functionConfiguration(EquityIndexVanillaBarrierOptionVegaMatrixFunction.class));
    functionConfigs.add(functionConfiguration(EquityIndexVanillaBarrierOptionVommaFunction.class));
    functionConfigs.add(functionConfiguration(EquityIndexVanillaBarrierOptionFundingCurveSensitivitiesFunction.class));
    functionConfigs.add(functionConfiguration(EquityIndexVanillaBarrierOptionDefaultPropertiesFunction.class, "0.0", "0.001"));
  }

  private static void addBondFutureCalculators(final List<FunctionConfiguration> functionConfigs) {
    functionConfigs.add(functionConfiguration(BondFutureGrossBasisFromCurvesFunction.class));
    functionConfigs.add(functionConfiguration(BondFutureNetBasisFromCurvesFunction.class));
  }

  private static void addBondCalculators(final List<FunctionConfiguration> functionConfigs) {
    functionConfigs.add(functionConfiguration(BondCouponPaymentDiaryFunction.class));
    functionConfigs.add(functionConfiguration(BondTenorFunction.class));
    functionConfigs.add(functionConfiguration(BondMarketCleanPriceFunction.class));
    functionConfigs.add(functionConfiguration(BondMarketDirtyPriceFunction.class));
    functionConfigs.add(functionConfiguration(BondMarketYieldFunction.class));
    functionConfigs.add(functionConfiguration(BondYieldFromCurvesFunction.class));
    functionConfigs.add(functionConfiguration(BondCleanPriceFromCurvesFunction.class));
    functionConfigs.add(functionConfiguration(BondDirtyPriceFromCurvesFunction.class));
    functionConfigs.add(functionConfiguration(BondMacaulayDurationFromCurvesFunction.class));
    functionConfigs.add(functionConfiguration(BondModifiedDurationFromCurvesFunction.class));
    functionConfigs.add(functionConfiguration(BondCleanPriceFromYieldFunction.class));
    functionConfigs.add(functionConfiguration(BondDirtyPriceFromYieldFunction.class));
    functionConfigs.add(functionConfiguration(BondMacaulayDurationFromYieldFunction.class));
    functionConfigs.add(functionConfiguration(BondModifiedDurationFromYieldFunction.class));
    functionConfigs.add(functionConfiguration(BondZSpreadFromCurveCleanPriceFunction.class));
    functionConfigs.add(functionConfiguration(BondZSpreadFromMarketCleanPriceFunction.class));
    functionConfigs.add(functionConfiguration(BondZSpreadPresentValueSensitivityFromCurveCleanPriceFunction.class));
    functionConfigs.add(functionConfiguration(BondZSpreadPresentValueSensitivityFromMarketCleanPriceFunction.class));
    functionConfigs.add(functionConfiguration(NelsonSiegelSvenssonBondCurveFunction.class));
    functionConfigs.add(functionConfiguration(BondDefaultCurveNamesFunction.class, PriorityClass.ABOVE_NORMAL.name(), "USD", "Discounting", "DefaultTwoCurveUSDConfig",
        "Discounting", "DefaultTwoCurveUSDConfig", "EUR", "Discounting", "DefaultTwoCurveEURConfig", "Discounting", "DefaultTwoCurveEURConfig", "GBP", "Discounting",
        "DefaultTwoCurveGBPConfig", "Discounting", "DefaultTwoCurveGBPConfig"));
  }

  private static void addForexOptionCalculators(final List<FunctionConfiguration> functionConfigs) {
    functionConfigs.add(functionConfiguration(BloombergFXSpotRateMarketDataFunction.class));
    functionConfigs.add(functionConfiguration(BloombergFXSpotRatePercentageChangeFunction.class));
    functionConfigs.add(functionConfiguration(BloombergFXOptionSpotRateFunction.class));
    functionConfigs.add(functionConfiguration(FXOptionBlackPresentValueFunction.class));
    functionConfigs.add(functionConfiguration(FXOptionBlackFXPresentValueFunction.class));
    functionConfigs.add(functionConfiguration(FXOptionBlackCurrencyExposureFunction.class));
    functionConfigs.add(functionConfiguration(FXOptionBlackVegaFunction.class));
    functionConfigs.add(functionConfiguration(FXOptionBlackVegaMatrixFunction.class));
    functionConfigs.add(functionConfiguration(FXOptionBlackVegaQuoteMatrixFunction.class));
    functionConfigs.add(functionConfiguration(FXOptionBlackGammaFunction.class));
    functionConfigs.add(functionConfiguration(FXOptionBlackGammaSpotFunction.class));
    functionConfigs.add(functionConfiguration(FXOptionBlackThetaFunction.class));
    functionConfigs.add(functionConfiguration(FXOptionBlackVannaFunction.class));
    functionConfigs.add(functionConfiguration(FXOptionBlackVommaFunction.class));
    functionConfigs.add(functionConfiguration(FXOptionBlackPresentValueCurveSensitivityFunction.class));
    functionConfigs.add(functionConfiguration(FXOptionBlackPV01Function.class));
    functionConfigs.add(functionConfiguration(FXOptionBlackImpliedVolatilityFunction.class));
    functionConfigs.add(functionConfiguration(FXOptionBlackTermStructureCurrencyExposureFunction.class));
    functionConfigs.add(functionConfiguration(FXOptionBlackConstantSpreadThetaFunction.class));
    functionConfigs.add(functionConfiguration(FXOptionBlackVolatilitySurfaceConstantSpreadThetaFunction.class));
    functionConfigs.add(functionConfiguration(FXOptionBlackYieldCurvesConstantSpreadThetaFunction.class));
    functionConfigs.add(functionConfiguration(FXOptionBlackForwardSlideThetaFunction.class));
    functionConfigs.add(functionConfiguration(FXOptionBlackVolatilitySurfaceForwardSlideThetaFunction.class));
    functionConfigs.add(functionConfiguration(FXOptionBlackYieldCurvesForwardSlideThetaFunction.class));
    functionConfigs.add(functionConfiguration(FXOptionBlackYCNSFunction.class));
    functionConfigs.add(functionConfiguration(FXOptionVannaVolgaPresentValueFunction.class));
    functionConfigs.add(functionConfiguration(FXOptionBlackCurveDefaults.class, PriorityClass.NORMAL.name(),
        "USD", "DefaultTwoCurveUSDConfig", "Discounting",
        "EUR", "DefaultTwoCurveEURConfig", "Discounting",
        "CAD", "DefaultTwoCurveCADConfig", "Discounting",
        "AUD", "DefaultTwoCurveAUDConfig", "Discounting",
        "CHF", "DefaultTwoCurveCHFConfig", "Discounting",
        "MXN", "DefaultCashCurveMXNConfig", "Cash",
        "JPY", "DefaultTwoCurveJPYConfig", "Discounting",
        "GBP", "DefaultTwoCurveGBPConfig", "Discounting",
        "NZD", "DefaultTwoCurveNZDConfig", "Discounting",
        "HUF", "DefaultCashCurveHUFConfig", "Cash",
        "KRW", "DefaultCashCurveKRWConfig", "Cash",
        "BRL", "DefaultCashCurveBRLConfig", "Cash",
        "HKD", "DefaultCashCurveHKDConfig", "Cash"));
    functionConfigs.add(functionConfiguration(FXOptionBlackSurfaceDefaults.class, PriorityClass.NORMAL.name(), DOUBLE_QUADRATIC, LINEAR_EXTRAPOLATOR,
        LINEAR_EXTRAPOLATOR,
        "USD", "EUR", "TULLETT",
        "USD", "CAD", "TULLETT",
        "USD", "AUD", "TULLETT",
        "USD", "CHF", "TULLETT",
        "USD", "MXN", "TULLETT",
        "USD", "JPY", "TULLETT",
        "USD", "GBP", "TULLETT",
        "USD", "NZD", "TULLETT",
        "USD", "HUF", "TULLETT",
        "USD", "KRW", "TULLETT",
        "USD", "BRL", "TULLETT",
        "EUR", "CHF", "TULLETT",
        "USD", "HKD", "TULLETT",
        "EUR", "JPY", "TULLETT"));
  }

  private static void addFXBarrierOptionCalculators(final List<FunctionConfiguration> functionConfigs) {
    functionConfigs.add(functionConfiguration(FXOneLookBarrierOptionBlackPresentValueFunction.class));
    functionConfigs.add(functionConfiguration(FXOneLookBarrierOptionBlackCurveSensitivityFunction.class));
    functionConfigs.add(functionConfiguration(FXOneLookBarrierOptionBlackGammaFunction.class));
    functionConfigs.add(functionConfiguration(FXOneLookBarrierOptionBlackVannaFunction.class));
    functionConfigs.add(functionConfiguration(FXOneLookBarrierOptionBlackVegaFunction.class));
    functionConfigs.add(functionConfiguration(FXOneLookBarrierOptionBlackVommaFunction.class));
    final String overhedge = "0.0";
    final String relativeStrikeSmoothing = "0.001";
    functionConfigs.add(functionConfiguration(FXOneLookBarrierOptionBlackDefaultPropertiesFunction.class, overhedge, relativeStrikeSmoothing));
  }

  private static void addForexForwardCalculators(final List<FunctionConfiguration> functionConfigs) {
    functionConfigs.add(functionConfiguration(FXForwardPV01Function.class));
    functionConfigs.add(functionConfiguration(FXForwardFXPresentValueFunction.class));
    functionConfigs.add(functionConfiguration(FXForwardCurrencyExposureFunction.class));
    functionConfigs.add(functionConfiguration(FXForwardYCNSFunction.class));
    functionConfigs.add(functionConfiguration(FXForwardPresentValueCurveSensitivityFunction.class));
    functionConfigs.add(functionConfiguration(FXForwardDefaults.class, PriorityClass.NORMAL.name(),
        "USD", "DefaultTwoCurveUSDConfig", "Discounting",
        "EUR", "DefaultTwoCurveEURConfig", "Discounting",
        "CHF", "DefaultTwoCurveCHFConfig", "Discounting",
        "RUB", "DefaultCashCurveRUBConfig", "Cash",
        "CAD", "DefaultTwoCurveCADConfig", "Discounting"));
  }

  private static void addSimpleFutureCalculators(final List<FunctionConfiguration> functionConfigs) {
    functionConfigs.add(functionConfiguration(SimpleFuturePresentValueFunction.class));
    functionConfigs.add(functionConfiguration(SimpleFuturePriceDeltaFunction.class));
    functionConfigs.add(functionConfiguration(SimpleFuturePV01Function.class));
    functionConfigs.add(functionConfiguration(SimpleFutureRhoFunction.class));
  }

  private static void addInterestRateFutureCalculators(final List<FunctionConfiguration> functionConfigs) {
    functionConfigs.add(functionConfiguration(InterestRateFuturePresentValueFunction.class));
    functionConfigs.add(functionConfiguration(InterestRateFuturePV01Function.class));
    functionConfigs.add(functionConfiguration(InterestRateFutureYieldCurveNodeSensitivitiesFunction.class));
    functionConfigs.add(functionConfiguration(InterestRateFutureDefaults.class, PriorityClass.NORMAL.name(),
        "USD", "DefaultTwoCurveUSDConfig",
        "EUR", "DefaultTwoCurveEURConfig",
        "CHF", "DefaultTwoCurveCHFConfig",
        "RUB", "DefaultCashCurveRUBConfig"));
  }


  private static void addInterestRateFutureOptionCalculators(final List<FunctionConfiguration> functionConfigs) {
    functionConfigs.add(functionConfiguration(InterestRateFutureOptionSABRPresentValueFunction.class));
    functionConfigs.add(functionConfiguration(InterestRateFutureOptionSABRSensitivitiesFunction.class, ValueRequirementNames.PRESENT_VALUE_SABR_ALPHA_SENSITIVITY));
    functionConfigs.add(functionConfiguration(InterestRateFutureOptionSABRSensitivitiesFunction.class, ValueRequirementNames.PRESENT_VALUE_SABR_NU_SENSITIVITY));
    functionConfigs.add(functionConfiguration(InterestRateFutureOptionSABRSensitivitiesFunction.class, ValueRequirementNames.PRESENT_VALUE_SABR_RHO_SENSITIVITY));
    functionConfigs.add(functionConfiguration(InterestRateFutureOptionSABRVegaFunction.class));
    functionConfigs.add(functionConfiguration(InterestRateFutureOptionSABRYieldCurveNodeSensitivitiesFunction.class));
    functionConfigs.add(functionConfiguration(InterestRateFutureOptionDefaultValuesFunctionDeprecated.class, "FUTURES", "FUNDING", "DEFAULT", "PresentValue", "USD",
        "EUR"));
    functionConfigs.add(functionConfiguration(SABRNonLinearLeastSquaresIRFutureOptionSurfaceFittingFunction.class));
    functionConfigs.add(functionConfiguration(SABRNonLinearLeastSquaresIRFutureSurfaceDefaultValuesFunction.class, "DEFAULT"));
    functionConfigs.add(functionConfiguration(InterestRateFutureOptionMarketUnderlyingPriceFunction.class));
    //functionConfigs.add(functionConfiguration(HestonFourierIRFutureSurfaceFittingFunction.class, "USD", "DEFAULT"));
    //functionConfigs.add(functionConfiguration(InterestRateFutureOptionHestonPresentValueFunction.class, "FORWARD_3M", "FUNDING", "DEFAULT"));
  }

  private static void addBondFutureOptionCalculators(final List<FunctionConfiguration> functionConfigs) {
    functionConfigs.add(functionConfiguration(BondFutureOptionBlackPresentValueFunction.class));
    functionConfigs.add(functionConfiguration(BondFutureOptionBlackDeltaFunction.class));
    functionConfigs.add(functionConfiguration(BondFutureOptionBlackGammaFunction.class));
    functionConfigs.add(functionConfiguration(BondFutureOptionBlackPV01Function.class));
    functionConfigs.add(functionConfiguration(BondFutureOptionBlackYCNSFunction.class));
    functionConfigs.add(functionConfiguration(BondFutureOptionBlackVegaFunction.class));
    functionConfigs.add(functionConfiguration(BondFutureOptionBlackFromFuturePresentValueFunction.class));
  }

  private static void addCommodityFutureOptionCalculators(final List<FunctionConfiguration> functionConfigs) {
    functionConfigs.add(functionConfiguration(FutureOptionBlackPresentValueFunction.class));
    functionConfigs.add(functionConfiguration(FutureOptionBlackDefaultPropertiesFunction.class, PriorityClass.NORMAL.name(), "BBG", "Spline",
        "USD", "DefaultTwoCurveUSDConfig", "Discounting",
        "EUR", "DefaultTwoCurveEURConfig", "Discounting",
        "JPY", "DefaultTwoCurveJPYConfig", "Discounting",
        "GBP", "DefaultTwoCurveGBPConfig", "Discounting"));
  }

  private static void addLocalVolatilityCalculators(final List<FunctionConfiguration> functionConfigs) {
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEPipsPresentValueFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.SPLINE)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEPipsPresentValueFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.SABR)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEPipsPresentValueFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.MIXED_LOG_NORMAL)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEDualDeltaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.SPLINE)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEDualDeltaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.SABR)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEDualDeltaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.MIXED_LOG_NORMAL)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEDualGammaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.SPLINE)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEDualGammaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.SABR)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEDualGammaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.MIXED_LOG_NORMAL)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEForwardDeltaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.SPLINE)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEForwardDeltaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.SABR)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEForwardDeltaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.MIXED_LOG_NORMAL)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEForwardGammaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.SPLINE)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEForwardGammaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.SABR)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEForwardGammaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.MIXED_LOG_NORMAL)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEForwardVegaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.SPLINE)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEForwardVegaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.SABR)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEForwardVegaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.MIXED_LOG_NORMAL)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEForwardVannaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.SPLINE)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEForwardVannaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.SABR)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEForwardVannaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.MIXED_LOG_NORMAL)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEForwardVommaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.SPLINE)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEForwardVommaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.SABR)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEForwardVommaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.MIXED_LOG_NORMAL)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEImpliedVolatilityFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.SPLINE)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEImpliedVolatilityFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.SABR)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEImpliedVolatilityFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.MIXED_LOG_NORMAL)));
    
    functionConfigs.add(functionConfiguration(ForwardPDEDefaults.class, 
        "0.5", "100", "100", "5.0", "0.05", "1.5", "1.0", Interpolator1DFactory.DOUBLE_QUADRATIC, "Discounting"));
    functionConfigs.add(functionConfiguration(BackwardPDEDefaults.class,
        "0.5", "100", "100", "5.0", "0.05", "3.5", Interpolator1DFactory.DOUBLE_QUADRATIC, "Discounting"));    
  }

  private static void addLocalVolatilityGridFunctions(final List<FunctionConfiguration> functionConfigs) {
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEGridDualDeltaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.SPLINE)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEGridDualDeltaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.SABR)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEGridDualDeltaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.MIXED_LOG_NORMAL)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEGridDualGammaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.SPLINE)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEGridDualGammaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.SABR)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEGridDualGammaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.MIXED_LOG_NORMAL)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEGridForwardDeltaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.SPLINE)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEGridForwardDeltaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.SABR)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEGridForwardDeltaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.MIXED_LOG_NORMAL)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEGridForwardGammaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.SPLINE)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEGridForwardGammaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.SABR)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEGridForwardGammaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.MIXED_LOG_NORMAL)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEGridForwardVegaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.SPLINE)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEGridForwardVegaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.SABR)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEGridForwardVegaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.MIXED_LOG_NORMAL)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEGridForwardVannaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.SPLINE)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEGridForwardVannaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.SABR)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEGridForwardVannaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.MIXED_LOG_NORMAL)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEGridForwardVommaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.SPLINE)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEGridForwardVommaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.SABR)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEGridForwardVommaFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.MIXED_LOG_NORMAL)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEGridImpliedVolatilityFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.SPLINE)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEGridImpliedVolatilityFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.SABR)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEGridImpliedVolatilityFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.MIXED_LOG_NORMAL)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEGridPipsPresentValueFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.SPLINE)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEGridPipsPresentValueFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.SABR)));
    functionConfigs.add(new ParameterizedFunctionConfiguration(FXOptionLocalVolatilityForwardPDEGridPipsPresentValueFunction.class.getName(), Arrays
        .asList(BlackVolatilitySurfacePropertyNamesAndValues.MIXED_LOG_NORMAL)));
  }

  //TODO move next few methods into the surface configuration class
  private static void addBlackVolatilitySurface(final List<FunctionConfiguration> functionConfigs) {
    final List<String> commonProperties = Arrays.asList(
      BlackVolatilitySurfacePropertyNamesAndValues.LOG_TIME,
      BlackVolatilitySurfacePropertyNamesAndValues.LOG_Y,
      BlackVolatilitySurfacePropertyNamesAndValues.INTEGRATED_VARIANCE,
      Interpolator1DFactory.DOUBLE_QUADRATIC,
      Interpolator1DFactory.LINEAR_EXTRAPOLATOR,
      Interpolator1DFactory.LINEAR_EXTRAPOLATOR);
    final List<String> sabrProperties = new ArrayList<String>(commonProperties);
    sabrProperties.add(VolatilityFunctionFactory.HAGAN);
    sabrProperties.add(WeightingFunctionFactory.SINE_WEIGHTING_FUNCTION_NAME);
    sabrProperties.add("false");
    sabrProperties.add("0.5");
    final List<String> mixedLogNormalProperties = new ArrayList<String>(commonProperties);
    mixedLogNormalProperties.add(WeightingFunctionFactory.SINE_WEIGHTING_FUNCTION_NAME);
    final List<String> splineProperties = new ArrayList<String>(commonProperties);
    splineProperties.add(Interpolator1DFactory.DOUBLE_QUADRATIC);
    splineProperties.add(Interpolator1DFactory.LINEAR_EXTRAPOLATOR);
    splineProperties.add(Interpolator1DFactory.LINEAR_EXTRAPOLATOR);
    // Spline default is: Quiet - if ShiftedLogNormalTailExtrapolationFitter fails on boundary, try on next strike in interior of domain
    splineProperties.add(BlackVolatilitySurfacePropertyNamesAndValues.QUIET_SPLINE_EXTRAPOLATOR_FAILURE);
    
    functionConfigs.add(new StaticFunctionConfiguration(BlackVolatilitySurfaceSABRInterpolatorFunction.class.getName()));
    functionConfigs.add(new StaticFunctionConfiguration(BlackVolatilitySurfaceMixedLogNormalInterpolatorFunction.class.getName()));
    functionConfigs.add(new StaticFunctionConfiguration(BlackVolatilitySurfaceSplineInterpolatorFunction.Exception.class.getName()));
    functionConfigs.add(new StaticFunctionConfiguration(BlackVolatilitySurfaceSplineInterpolatorFunction.Quiet.class.getName()));
    functionConfigs.add(new ParameterizedFunctionConfiguration(BlackVolatilitySurfaceSABRDefaults.class.getName(), sabrProperties));
    functionConfigs.add(new ParameterizedFunctionConfiguration(BlackVolatilitySurfaceMixedLogNormalDefaults.class.getName(), mixedLogNormalProperties));
    functionConfigs.add(new ParameterizedFunctionConfiguration(BlackVolatilitySurfaceSplineDefaults.class.getName(), splineProperties));
  }
    
  private static void addFXBlackVolatilitySurface(final List<FunctionConfiguration> functionConfigs) {
    functionConfigs.add(new StaticFunctionConfiguration(ForexBlackVolatilitySurfaceFunction.MixedLogNormal.class.getName()));
    functionConfigs.add(new StaticFunctionConfiguration(ForexBlackVolatilitySurfaceFunction.SABR.class.getName()));
    functionConfigs.add(new StaticFunctionConfiguration(ForexBlackVolatilitySurfaceFunction.Spline.class.getName()));
    
    functionConfigs.add(functionConfiguration(FXBlackVolatilitySurfaceDefaults.class,
        "EURUSD", "DiscountingImplied", ForwardCurveValuePropertyNames.PROPERTY_YIELD_CURVE_IMPLIED_METHOD, "DEFAULT"));
  }
  
  private static void addEquityBlackVolatilitySurface(final List<FunctionConfiguration> functionConfigs) {
    functionConfigs.add(new StaticFunctionConfiguration(EquityBlackVolatilitySurfaceFunction.SABR.class.getName()));
    functionConfigs.add(new StaticFunctionConfiguration(EquityBlackVolatilitySurfaceFunction.Spline.class.getName())); 
    
    functionConfigs.add(functionConfiguration(EquityBlackVolatilitySurfaceDefaults.class,
        "DJX Index", "Discounting", ForwardCurveValuePropertyNames.PROPERTY_YIELD_CURVE_IMPLIED_METHOD, "USD", "DefaultTwoCurveUSDConfig", "BBG"));
  }
  
  private static void addLocalVolatilitySurface(final List<FunctionConfiguration> functionConfigs) {
    functionConfigs.add(new StaticFunctionConfiguration(ForexDupireLocalVolatilitySurfaceFunction.MixedLogNormal.class.getName()));
    functionConfigs.add(new StaticFunctionConfiguration(ForexDupireLocalVolatilitySurfaceFunction.SABR.class.getName()));
    functionConfigs.add(new StaticFunctionConfiguration(ForexDupireLocalVolatilitySurfaceFunction.Spline.class.getName()));

    functionConfigs.add(new StaticFunctionConfiguration(EquityDupireLocalVolatilitySurfaceFunction.MixedLogNormal.class.getName()));
    functionConfigs.add(new StaticFunctionConfiguration(EquityDupireLocalVolatilitySurfaceFunction.SABR.class.getName()));
    functionConfigs.add(new StaticFunctionConfiguration(EquityDupireLocalVolatilitySurfaceFunction.Spline.class.getName()));

    functionConfigs.add(functionConfiguration(LocalVolatilitySurfaceDefaults.class, "0.001"));
  }
  
  private static void addEquityVolatilitySurfaceCalculators(final List<FunctionConfiguration> functionConfigs) {
    functionConfigs.add(new StaticFunctionConfiguration(PureBlackVolatilitySurfaceNoDividendCorrectionFunction.Spline.class.getName()));
    functionConfigs.add(new StaticFunctionConfiguration(PureBlackVolatilitySurfaceDividendCorrectionFunction.Spline.class.getName()));
    functionConfigs.add(new StaticFunctionConfiguration(AffineDividendFunction.class.getName()));
//    final List<String> commonBlackSurfaceInterpolatorProperties = Arrays.asList(
//        BlackVolatilitySurfacePropertyNamesAndValues.LOG_TIME,
//        BlackVolatilitySurfacePropertyNamesAndValues.LOG_Y,
//        BlackVolatilitySurfacePropertyNamesAndValues.INTEGRATED_VARIANCE,
//        Interpolator1DFactory.DOUBLE_QUADRATIC,
//        Interpolator1DFactory.LINEAR_EXTRAPOLATOR,
//        Interpolator1DFactory.LINEAR_EXTRAPOLATOR);
//    final List<String> splineProperties = getFXSplineBlackSurfaceProperties(commonBlackSurfaceInterpolatorProperties);
//
//    functionConfigs.add(new ParameterizedFunctionConfiguration(BlackVolatilitySurfaceSplineInterpolatorDefaults.class.getName(), splineProperties));
//
//    final List<String> commonPureSurfaceProperties = new ArrayList<String>(commonBlackSurfaceInterpolatorProperties);
//    commonPureSurfaceProperties.add("Discounting");
//    commonPureSurfaceProperties.add("DefaultTwoCurveUSDConfig");
//    commonPureSurfaceProperties.add("BBG_PRICE");
//    commonPureSurfaceProperties.add("USD");
//    commonPureSurfaceProperties.add(Interpolator1DFactory.DOUBLE_QUADRATIC);
//    commonPureSurfaceProperties.add(Interpolator1DFactory.LINEAR_EXTRAPOLATOR);
//    commonPureSurfaceProperties.add(Interpolator1DFactory.LINEAR_EXTRAPOLATOR);
//    commonPureSurfaceProperties.add(BlackVolatilitySurfacePropertyNamesAndValues.QUIET_SPLINE_EXTRAPOLATOR_FAILURE);
//
//    functionConfigs.add(new ParameterizedFunctionConfiguration(PureVolatilitySurfaceSplineDefaults.class.getName(), commonPureSurfaceProperties));
  }

  private static void addSABRCalculators(final List<FunctionConfiguration> functionConfigs) {
    functionConfigs.add(functionConfiguration(SABRNonLinearLeastSquaresSwaptionCubeFittingFunction.class));
    functionConfigs.add(functionConfiguration(SABRNonLinearLeastSquaresSwaptionCubeFittingDefaults.class, "USD", "BLOOMBERG"));
    functionConfigs.add(functionConfiguration(SABRNonLinearLeastSquaresSwaptionCubeFittingDefaults.class, "EUR", "BLOOMBERG"));
    functionConfigs.add(functionConfiguration(SABRNonLinearLeastSquaresSwaptionCubeFittingDefaults.class, "GBP", "BLOOMBERG"));
    functionConfigs.add(functionConfiguration(SABRNonLinearLeastSquaresSwaptionCubeFittingDefaults.class, "AUD", "BLOOMBERG"));
    functionConfigs.add(functionConfiguration(SABRCMSSpreadNoExtrapolationPVCurveSensitivityFunction.class));
    functionConfigs.add(functionConfiguration(SABRCMSSpreadNoExtrapolationPresentValueFunction.class));
    functionConfigs.add(functionConfiguration(SABRCMSSpreadNoExtrapolationPVSABRSensitivityFunction.Alpha.class));
    functionConfigs.add(functionConfiguration(SABRCMSSpreadNoExtrapolationPVSABRSensitivityFunction.Nu.class));
    functionConfigs.add(functionConfiguration(SABRCMSSpreadNoExtrapolationPVSABRSensitivityFunction.Rho.class));
    functionConfigs.add(functionConfiguration(SABRCMSSpreadNoExtrapolationVegaFunction.class));
    functionConfigs.add(functionConfiguration(SABRCMSSpreadNoExtrapolationYCNSFunction.class));
    functionConfigs.add(functionConfiguration(SABRCMSSpreadRightExtrapolationPVCurveSensitivityFunction.class));
    functionConfigs.add(functionConfiguration(SABRCMSSpreadRightExtrapolationPresentValueFunction.class));
    functionConfigs.add(functionConfiguration(SABRCMSSpreadRightExtrapolationPVSABRSensitivityFunction.Alpha.class));
    functionConfigs.add(functionConfiguration(SABRCMSSpreadRightExtrapolationPVSABRSensitivityFunction.Nu.class));
    functionConfigs.add(functionConfiguration(SABRCMSSpreadRightExtrapolationPVSABRSensitivityFunction.Rho.class));
    functionConfigs.add(functionConfiguration(SABRCMSSpreadRightExtrapolationPVSABRNodeSensitivityFunction.Alpha.class));
    functionConfigs.add(functionConfiguration(SABRCMSSpreadRightExtrapolationPVSABRNodeSensitivityFunction.Nu.class));
    functionConfigs.add(functionConfiguration(SABRCMSSpreadRightExtrapolationPVSABRNodeSensitivityFunction.Rho.class));
    functionConfigs.add(functionConfiguration(SABRCMSSpreadRightExtrapolationVegaFunction.class));
    functionConfigs.add(functionConfiguration(SABRCMSSpreadRightExtrapolationYCNSFunction.class));
    functionConfigs.add(functionConfiguration(SABRNoExtrapolationPVCurveSensitivityFunction.class));
    functionConfigs.add(functionConfiguration(SABRNoExtrapolationPresentValueFunction.class));
    functionConfigs.add(functionConfiguration(SABRNoExtrapolationPVSABRSensitivityFunction.Alpha.class));
    functionConfigs.add(functionConfiguration(SABRNoExtrapolationPVSABRSensitivityFunction.Nu.class));
    functionConfigs.add(functionConfiguration(SABRNoExtrapolationPVSABRSensitivityFunction.Rho.class));
    functionConfigs.add(functionConfiguration(SABRNoExtrapolationVegaFunction.class));
    functionConfigs.add(functionConfiguration(SABRNoExtrapolationYCNSFunction.class));
    functionConfigs.add(functionConfiguration(SABRRightExtrapolationPVCurveSensitivityFunction.class));
    functionConfigs.add(functionConfiguration(SABRRightExtrapolationPresentValueFunction.class));
    functionConfigs.add(functionConfiguration(SABRRightExtrapolationPVSABRSensitivityFunction.Alpha.class));
    functionConfigs.add(functionConfiguration(SABRRightExtrapolationPVSABRSensitivityFunction.Nu.class));
    functionConfigs.add(functionConfiguration(SABRRightExtrapolationPVSABRSensitivityFunction.Rho.class));
    functionConfigs.add(functionConfiguration(SABRRightExtrapolationPVSABRNodeSensitivityFunction.Alpha.class));
    functionConfigs.add(functionConfiguration(SABRRightExtrapolationPVSABRNodeSensitivityFunction.Nu.class));
    functionConfigs.add(functionConfiguration(SABRRightExtrapolationPVSABRNodeSensitivityFunction.Rho.class));
    functionConfigs.add(functionConfiguration(SABRRightExtrapolationVegaFunction.class));
    functionConfigs.add(functionConfiguration(SABRRightExtrapolationYCNSFunction.class));
    functionConfigs.add(functionConfiguration(SABRNoExtrapolationDefaults.class, PriorityClass.BELOW_NORMAL.name(),
        VolatilityDataFittingDefaults.NON_LINEAR_LEAST_SQUARES,
        "USD", "DefaultTwoCurveUSDConfig", "BLOOMBERG",
        "EUR", "DefaultTwoCurveEURConfig", "BLOOMBERG",
        "AUD", "DefaultTwoCurveAUDConfig", "BLOOMBERG",
        "GBP", "DefaultTwoCurveGBPConfig", "BLOOMBERG"));
    functionConfigs.add(functionConfiguration(SABRRightExtrapolationDefaults.class, PriorityClass.BELOW_NORMAL.name(),
        VolatilityDataFittingDefaults.NON_LINEAR_LEAST_SQUARES, "0.07", "10.0",
        "USD", "DefaultTwoCurveUSDConfig", "BLOOMBERG",
        "EUR", "DefaultTwoCurveEURConfig", "BLOOMBERG",
        "AUD", "DefaultTwoCurveAUDConfig", "BLOOMBERG",
        "GBP", "DefaultTwoCurveGBPConfig", "BLOOMBERG"));
    functionConfigs.add(functionConfiguration(SABRNoExtrapolationVegaDefaults.class, PriorityClass.BELOW_NORMAL.name(),
        VolatilityDataFittingDefaults.NON_LINEAR_LEAST_SQUARES, LINEAR, FLAT_EXTRAPOLATOR, FLAT_EXTRAPOLATOR, LINEAR, FLAT_EXTRAPOLATOR, FLAT_EXTRAPOLATOR,
        "USD", "DefaultTwoCurveUSDConfig", "BLOOMBERG",
        "EUR", "DefaultTwoCurveEURConfig", "BLOOMBERG",
        "AUD", "DefaultTwoCurveAUDConfig", "BLOOMBERG",
        "GBP", "DefaultTwoCurveGBPConfig", "BLOOMBERG"));
    functionConfigs
        .add(functionConfiguration(SABRRightExtrapolationVegaDefaults.class, PriorityClass.BELOW_NORMAL.name(), VolatilityDataFittingDefaults.NON_LINEAR_LEAST_SQUARES,
            "0.07", "10.0", LINEAR, FLAT_EXTRAPOLATOR, FLAT_EXTRAPOLATOR, LINEAR, FLAT_EXTRAPOLATOR, FLAT_EXTRAPOLATOR,
            "USD", "DefaultTwoCurveUSDConfig", "BLOOMBERG",
            "EUR", "DefaultTwoCurveEURConfig", "BLOOMBERG",
            "AUD", "DefaultTwoCurveAUDConfig", "BLOOMBERG",
            "GBP", "DefaultTwoCurveGBPConfig", "BLOOMBERG"));
  }

  private static void addBlackCalculators(final List<FunctionConfiguration> functionConfigs) {
    functionConfigs.add(functionConfiguration(InterestRateFutureOptionBlackPresentValueFunction.class));
    functionConfigs.add(functionConfiguration(InterestRateFutureOptionBlackVolatilitySensitivityFunction.class));
    functionConfigs.add(functionConfiguration(InterestRateFutureOptionBlackImpliedVolatilityFunction.class));
    functionConfigs.add(functionConfiguration(InterestRateFutureOptionBlackPV01Function.class));
    functionConfigs.add(functionConfiguration(InterestRateFutureOptionBlackYieldCurveNodeSensitivitiesFunction.class));
    functionConfigs.add(functionConfiguration(InterestRateFutureOptionBlackGammaFunction.class));
    functionConfigs.add(functionConfiguration(InterestRateFutureOptionBlackPriceFunction.class));
    functionConfigs.add(functionConfiguration(InterestRateFutureOptionConstantSpreadThetaFunction.class));
    functionConfigs.add(functionConfiguration(InterestRateFutureOptionBlackDefaults.class, PriorityClass.ABOVE_NORMAL.name(), "USD", "DefaultTwoCurveUSDConfig",
        "DEFAULT"));
    functionConfigs.add(functionConfiguration(MarginPriceFunction.class));
    functionConfigs.add(functionConfiguration(SwaptionBlackPresentValueFunction.class));
    functionConfigs.add(functionConfiguration(SwaptionBlackVolatilitySensitivityFunction.class));
    functionConfigs.add(functionConfiguration(SwaptionBlackPV01Function.class));
    functionConfigs.add(functionConfiguration(SwaptionBlackYieldCurveNodeSensitivitiesFunction.class));
    functionConfigs.add(functionConfiguration(SwaptionBlackImpliedVolatilityFunction.class));
    functionConfigs.add(functionConfiguration(SwaptionConstantSpreadThetaFunction.class));
    functionConfigs.add(functionConfiguration(SwaptionBlackDefaultPropertiesFunction.class, PriorityClass.NORMAL.name(), "EUR", "DefaultTwoCurveEURConfig", "DEFAULT"));
    functionConfigs.add(functionConfiguration(SwaptionBlackThetaDefaults.class, PriorityClass.NORMAL.name(), "1", "EUR", "DefaultTwoCurveEURConfig", "DEFAULT"));
  }

  private static void addFixedIncomeInstrumentCalculators(final List<FunctionConfiguration> functionConfigs) {
    functionConfigs.add(functionConfiguration(InterestRateInstrumentParRateCurveSensitivityFunction.class));
    functionConfigs.add(functionConfiguration(InterestRateInstrumentParRateFunction.class));
    functionConfigs.add(functionConfiguration(InterestRateInstrumentParRateParallelCurveSensitivityFunction.class));
    functionConfigs.add(functionConfiguration(InterestRateInstrumentPresentValueFunction.class));
    functionConfigs.add(functionConfiguration(InterestRateInstrumentPV01Function.class));
    functionConfigs.add(functionConfiguration(InterestRateInstrumentYieldCurveNodeSensitivitiesFunction.class));
    functionConfigs.add(functionConfiguration(InterestRateInstrumentDefaultPropertiesFunction.class, PriorityClass.BELOW_NORMAL.name(), "false",
        "EUR", "DefaultTwoCurveEURConfig",
        "USD", "DefaultTwoCurveUSDConfig",
        "CHF", "DefaultTwoCurveCHFConfig",
        "JPY", "DefaultTwoCurveJPYConfig",
        "GBP", "DefaultTwoCurveGBPConfig"));
  }
  
  private static void addCDSCalculators(final List<FunctionConfiguration> functionConfigs) {
    functionConfigs.add(functionConfiguration(ISDAYieldCurveFunction.class));
    functionConfigs.add(functionConfiguration(ISDAYieldCurveDefaults.class, PriorityClass.NORMAL.name(),
        "USD", "0"));
    functionConfigs.add(functionConfiguration(ISDALegacyCDSHazardCurveFunction.class));
    functionConfigs.add(functionConfiguration(ISDALegacyCDSHazardCurveDefaults.class, PriorityClass.NORMAL.name(), "100", "1e-15", "0.5",
        "USD", "ISDA", "ISDA", "ISDA"));
    functionConfigs.add(functionConfiguration(ISDALegacyVanillaCDSCleanPriceFunction.class));
    functionConfigs.add(functionConfiguration(ISDALegacyVanillaCDSDirtyPriceFunction.class));
    functionConfigs.add(functionConfiguration(ISDALegacyVanillaCDSDefaults.class, PriorityClass.NORMAL.name(), "30"));    
  }

  private static void addPortfolioAnalysisCalculators(final List<FunctionConfiguration> functionConfigs) {
    final String defaultReturnCalculatorName = TimeSeriesReturnCalculatorFactory.SIMPLE_NET_STRICT;
    final String defaultSamplingPeriodName = "P2Y";
    final String defaultScheduleName = ScheduleCalculatorFactory.DAILY;
    final String defaultSamplingFunctionName = TimeSeriesSamplingFunctionFactory.PREVIOUS_AND_FIRST_VALUE_PADDING;
    final String defaultStdDevCalculatorName = StatisticsCalculatorFactory.SAMPLE_STANDARD_DEVIATION;
    final String defaultCovarianceCalculatorName = StatisticsCalculatorFactory.SAMPLE_COVARIANCE;
    final String defaultVarianceCalculatorName = StatisticsCalculatorFactory.SAMPLE_VARIANCE;
    final String defaultExcessReturnCalculatorName = StatisticsCalculatorFactory.MEAN; //TODO static variables?

    functionConfigs.add(functionConfiguration(CAPMBetaDefaultPropertiesPortfolioNodeFunction.class, defaultSamplingPeriodName, defaultScheduleName,
        defaultSamplingFunctionName, defaultReturnCalculatorName, defaultCovarianceCalculatorName, defaultVarianceCalculatorName));
    functionConfigs.add(functionConfiguration(CAPMBetaDefaultPropertiesPositionFunction.class, defaultSamplingPeriodName, defaultScheduleName,
        defaultSamplingFunctionName, defaultReturnCalculatorName, defaultCovarianceCalculatorName, defaultVarianceCalculatorName));
    functionConfigs.add(functionConfiguration(CAPMBetaModelPositionFunction.class, DEFAULT_CONFIG_NAME));
    functionConfigs.add(functionConfiguration(CAPMBetaModelPortfolioNodeFunction.class, DEFAULT_CONFIG_NAME));
    functionConfigs.add(functionConfiguration(CAPMFromRegressionDefaultPropertiesPortfolioNodeFunction.class, defaultSamplingPeriodName, defaultScheduleName,
        defaultSamplingFunctionName, defaultReturnCalculatorName));
    functionConfigs.add(functionConfiguration(CAPMFromRegressionDefaultPropertiesPositionFunction.class, defaultSamplingPeriodName, defaultScheduleName,
        defaultSamplingFunctionName, defaultReturnCalculatorName));
    functionConfigs.add(functionConfiguration(CAPMFromRegressionModelPositionFunction.class, DEFAULT_CONFIG_NAME));
    functionConfigs.add(functionConfiguration(CAPMFromRegressionModelPortfolioNodeFunction.class, DEFAULT_CONFIG_NAME));
    functionConfigs.add(functionConfiguration(SharpeRatioDefaultPropertiesPortfolioNodeFunction.class, defaultSamplingPeriodName, defaultScheduleName,
        defaultSamplingFunctionName, defaultReturnCalculatorName, defaultStdDevCalculatorName, defaultExcessReturnCalculatorName));
    functionConfigs.add(functionConfiguration(SharpeRatioDefaultPropertiesPositionFunction.class, defaultSamplingPeriodName, defaultScheduleName,
        defaultSamplingFunctionName, defaultReturnCalculatorName, defaultStdDevCalculatorName, defaultExcessReturnCalculatorName));
    functionConfigs.add(functionConfiguration(SharpeRatioPositionFunction.class, DEFAULT_CONFIG_NAME));
    functionConfigs.add(functionConfiguration(SharpeRatioPortfolioNodeFunction.class, DEFAULT_CONFIG_NAME));
    functionConfigs.add(functionConfiguration(TreynorRatioDefaultPropertiesPortfolioNodeFunction.class, defaultSamplingPeriodName, defaultScheduleName,
        defaultSamplingFunctionName, defaultReturnCalculatorName, defaultStdDevCalculatorName, defaultExcessReturnCalculatorName, defaultCovarianceCalculatorName,
        defaultVarianceCalculatorName));
    functionConfigs.add(functionConfiguration(TreynorRatioDefaultPropertiesPositionFunction.class, defaultSamplingPeriodName, defaultScheduleName,
        defaultSamplingFunctionName, defaultReturnCalculatorName, defaultStdDevCalculatorName, defaultExcessReturnCalculatorName, defaultCovarianceCalculatorName,
        defaultVarianceCalculatorName));
    functionConfigs.add(functionConfiguration(TreynorRatioPositionFunction.class, DEFAULT_CONFIG_NAME));
    functionConfigs.add(functionConfiguration(TreynorRatioPortfolioNodeFunction.class, DEFAULT_CONFIG_NAME));
    functionConfigs.add(functionConfiguration(JensenAlphaDefaultPropertiesPortfolioNodeFunction.class, defaultSamplingPeriodName, defaultScheduleName,
        defaultSamplingFunctionName, defaultReturnCalculatorName, defaultStdDevCalculatorName, defaultExcessReturnCalculatorName, defaultCovarianceCalculatorName,
        defaultVarianceCalculatorName));
    functionConfigs.add(functionConfiguration(JensenAlphaDefaultPropertiesPositionFunction.class, defaultSamplingPeriodName, defaultScheduleName,
        defaultSamplingFunctionName, defaultReturnCalculatorName, defaultStdDevCalculatorName, defaultExcessReturnCalculatorName, defaultCovarianceCalculatorName,
        defaultVarianceCalculatorName));
    functionConfigs.add(functionConfiguration(JensenAlphaPositionFunction.class, DEFAULT_CONFIG_NAME));
    functionConfigs.add(functionConfiguration(JensenAlphaPortfolioNodeFunction.class, DEFAULT_CONFIG_NAME));
    functionConfigs.add(functionConfiguration(TotalRiskAlphaDefaultPropertiesPortfolioNodeFunction.class, defaultSamplingPeriodName, defaultScheduleName,
        defaultSamplingFunctionName, defaultReturnCalculatorName, defaultStdDevCalculatorName, defaultExcessReturnCalculatorName));
    functionConfigs.add(functionConfiguration(TotalRiskAlphaDefaultPropertiesPositionFunction.class, defaultSamplingPeriodName, defaultScheduleName,
        defaultSamplingFunctionName, defaultReturnCalculatorName, defaultStdDevCalculatorName, defaultExcessReturnCalculatorName));
    functionConfigs.add(functionConfiguration(TotalRiskAlphaPositionFunction.class, DEFAULT_CONFIG_NAME));
    functionConfigs.add(functionConfiguration(TotalRiskAlphaPortfolioNodeFunction.class, DEFAULT_CONFIG_NAME));
  }

  private static void addCashFlowFunctions(final List<FunctionConfiguration> functionConfigs) {
    addSummingFunction(functionConfigs, ValueRequirementNames.FIXED_PAY_CASH_FLOWS);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.FIXED_PAY_CASH_FLOWS);
    addSummingFunction(functionConfigs, ValueRequirementNames.FIXED_RECEIVE_CASH_FLOWS);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.FIXED_RECEIVE_CASH_FLOWS);
    addSummingFunction(functionConfigs, ValueRequirementNames.NETTED_FIXED_CASH_FLOWS);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.NETTED_FIXED_CASH_FLOWS);
    addSummingFunction(functionConfigs, ValueRequirementNames.FLOATING_PAY_CASH_FLOWS);
    addUnitScalingFunction(functionConfigs, ValueRequirementNames.FLOATING_RECEIVE_CASH_FLOWS);
    functionConfigs.add(functionConfiguration(FixedPayCashFlowFunction.class));
    functionConfigs.add(functionConfiguration(FixedReceiveCashFlowFunction.class));
    functionConfigs.add(functionConfiguration(FloatingPayCashFlowFunction.class));
    functionConfigs.add(functionConfiguration(FloatingReceiveCashFlowFunction.class));
    functionConfigs.add(functionConfiguration(NettedFixedCashFlowFunction.class));
  }

  private static void addExternallyProvidedSensitivitiesFunctions(final List<FunctionConfiguration> functionConfigs) {
    final String defaultSamplingPeriodName = "P2Y";
    final String defaultScheduleName = ScheduleCalculatorFactory.DAILY;
    final String defaultSamplingCalculatorName = TimeSeriesSamplingFunctionFactory.PREVIOUS_AND_FIRST_VALUE_PADDING;
    functionConfigs.add(functionConfiguration(ExternallyProvidedSensitivitiesYieldCurveNodeSensitivitiesFunction.class));
    functionConfigs.add(functionConfiguration(ExternallyProvidedSensitivitiesNonYieldCurveFunction.class));

    functionConfigs.add(functionConfiguration(ExternallyProvidedSensitivitiesCreditFactorsFunction.class));
    functionConfigs.add(functionConfiguration(ExternallyProvidedSensitivityPnLFunction.class, DEFAULT_CONFIG_NAME));
    functionConfigs.add(functionConfiguration(ExternallyProvidedSensitivityPnLDefaultPropertiesFunction.class, defaultSamplingPeriodName, defaultScheduleName,
        defaultSamplingCalculatorName));
    functionConfigs.add(functionConfiguration(ExternallyProvidedSecurityMarkFunction.class));
    functionConfigs.add(functionConfiguration(ExternallyProvidedSensitivitiesYieldCurvePV01Function.class));
    functionConfigs.add(functionConfiguration(ExternallyProvidedSensitivitiesYieldCurveCS01Function.class));
    functionConfigs.add(functionConfiguration(ExternallyProvidedSensitivitiesDefaultPropertiesFunction.class, PriorityClass.BELOW_NORMAL.name(),
        "EUR", "DefaultTwoCurveEURConfig",
        "USD", "DefaultTwoCurveUSDConfig",
        "CHF", "DefaultTwoCurveCHFConfig",
        "JPY", "DefaultTwoCurveJPYConfig",
        "GBP", "DefaultTwoCurveGBPConfig"));
  }

  private static void addPassthroughFunctions(final List<FunctionConfiguration> functionConfigs) {
    functionConfigs.add(functionConfiguration(DV01Function.class));
  }

  //-------------------------------------------------------------------------
  public static RepositoryConfigurationSource constructRepositoryConfigurationSource() {
    return new SimpleRepositoryConfigurationSource(constructRepositoryConfiguration());
  }

  @Override
  protected RepositoryConfigurationSource createObject() {
    return constructRepositoryConfigurationSource();
  }

}
