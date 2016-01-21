/**
 * Copyright (C) 2016 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.function.calculation.index;

import static com.opengamma.strata.calc.runner.function.FunctionUtils.toCurrencyValuesArray;
import static com.opengamma.strata.calc.runner.function.FunctionUtils.toMultiCurrencyValuesArray;
import static com.opengamma.strata.calc.runner.function.FunctionUtils.toScenarioResult;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.opengamma.strata.basics.currency.CurrencyAmount;
import com.opengamma.strata.basics.currency.MultiCurrencyAmount;
import com.opengamma.strata.basics.market.MarketData;
import com.opengamma.strata.calc.marketdata.CalculationMarketData;
import com.opengamma.strata.calc.runner.SingleCalculationMarketData;
import com.opengamma.strata.calc.runner.function.result.CurrencyValuesArray;
import com.opengamma.strata.calc.runner.function.result.MultiCurrencyValuesArray;
import com.opengamma.strata.calc.runner.function.result.ScenarioResult;
import com.opengamma.strata.calc.runner.function.result.ValuesArray;
import com.opengamma.strata.collect.array.DoubleArray;
import com.opengamma.strata.function.marketdata.MarketDataRatesProvider;
import com.opengamma.strata.market.curve.CurveCurrencyParameterSensitivities;
import com.opengamma.strata.market.key.QuoteKey;
import com.opengamma.strata.market.sensitivity.PointSensitivities;
import com.opengamma.strata.pricer.index.DiscountingIborFutureTradePricer;
import com.opengamma.strata.product.index.IborFutureTrade;

/**
 * Multi-scenario measure calculations for Ibor Future trades.
 * <p>
 * Each method corresponds to a measure, typically calculated by one or more calls to the pricer.
 */
final class IborFutureMeasureCalculations {

  /**
   * The pricer to use.
   */
  private static final DiscountingIborFutureTradePricer PRICER = DiscountingIborFutureTradePricer.DEFAULT;

  /**
   * One basis point, expressed as a {@code double}.
   */
  private static final double ONE_BASIS_POINT = 1e-4;

  // restricted constructor
  private IborFutureMeasureCalculations() {
  }

  //-------------------------------------------------------------------------
  // calculates par spread for all scenarios
  static ValuesArray parSpread(
      IborFutureTrade trade,
      CalculationMarketData marketData) {

    DoubleArray array = DoubleArray.of(
        marketData.getScenarioCount(),
        index -> calculateParSpread(trade, singleScenarioMarketData(marketData, index)));
    return ValuesArray.of(array);
  }

  // par spread for one scenario
  private static double calculateParSpread(
      IborFutureTrade trade,
      MarketData marketData) {

    QuoteKey key = QuoteKey.of(trade.getSecurity().getStandardId());
    double price = marketData.getValue(key) / 100;  // convert market quote to value needed
    return PRICER.parSpread(trade, new MarketDataRatesProvider(marketData), price);
  }

  //-------------------------------------------------------------------------
  // calculates present value for all scenarios
  static CurrencyValuesArray presentValue(
      IborFutureTrade trade,
      CalculationMarketData marketData) {

    return marketDataStream(marketData)
        .map(md -> calculatePresentValue(trade, md))
        .collect(toCurrencyValuesArray());
  }

  // present value for one scenario
  private static CurrencyAmount calculatePresentValue(
      IborFutureTrade trade,
      MarketData marketData) {

    QuoteKey key = QuoteKey.of(trade.getSecurity().getStandardId());
    double price = marketData.getValue(key) / 100;  // convert market quote to value needed
    return PRICER.presentValue(trade, new MarketDataRatesProvider(marketData), price);
  }

  //-------------------------------------------------------------------------
  // calculates PV01 for all scenarios
  static MultiCurrencyValuesArray pv01(
      IborFutureTrade trade,
      CalculationMarketData marketData) {

    return marketDataStream(marketData)
        .map(md -> calculatePv01(trade, md))
        .collect(toMultiCurrencyValuesArray());
  }

  // PV01 for one scenario
  private static MultiCurrencyAmount calculatePv01(
      IborFutureTrade trade,
      MarketData marketData) {

    MarketDataRatesProvider provider = new MarketDataRatesProvider(marketData);
    PointSensitivities pointSensitivity = PRICER.presentValueSensitivity(trade, provider);
    return provider.curveParameterSensitivity(pointSensitivity).total().multipliedBy(ONE_BASIS_POINT);
  }

  //-------------------------------------------------------------------------
  // calculates bucketed PV01 for all scenarios
  static ScenarioResult<CurveCurrencyParameterSensitivities> bucketedPv01(
      IborFutureTrade trade,
      CalculationMarketData marketData) {

    return marketDataStream(marketData)
        .map(md -> calculateBucketedPv01(trade, md))
        .collect(toScenarioResult());
  }

  // bucketed PV01 for one scenario
  private static CurveCurrencyParameterSensitivities calculateBucketedPv01(
      IborFutureTrade trade,
      MarketData marketData) {

    MarketDataRatesProvider provider = new MarketDataRatesProvider(marketData);
    PointSensitivities pointSensitivity = PRICER.presentValueSensitivity(trade, provider);
    return provider.curveParameterSensitivity(pointSensitivity).multipliedBy(ONE_BASIS_POINT);
  }

  //-------------------------------------------------------------------------
  // common code, creating a stream of MarketData from CalculationMarketData
  private static Stream<MarketData> marketDataStream(CalculationMarketData marketData) {
    return IntStream.range(0, marketData.getScenarioCount())
        .mapToObj(index -> new SingleCalculationMarketData(marketData, index));
  }

  // creates a SingleCalculationMarketData
  private static MarketData singleScenarioMarketData(CalculationMarketData marketData, int index) {
    return new SingleCalculationMarketData(marketData, index);
  }

}