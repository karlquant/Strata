/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.data.scenario;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.ImmutableValidator;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaBean;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.light.LightMetaBean;

import com.opengamma.strata.collect.ArgChecker;
import com.opengamma.strata.collect.timeseries.LocalDateDoubleTimeSeries;
import com.opengamma.strata.data.MarketData;
import com.opengamma.strata.data.MarketDataId;
import com.opengamma.strata.data.MarketDataName;
import com.opengamma.strata.data.ObservableId;

/**
 * A single scenario view of multi-scenario market data.
 * <p>
 * This wraps an instance of {@link ScenarioMarketData} which contains market data for multiple scenarios.
 * This object returns market data from one of those scenarios. The scenario used as the source of the
 * data is controlled by the {@code scenarioIndex} argument.
 */
@BeanDefinition(style = "light")
final class SingleScenarioMarketData
    implements ImmutableBean, MarketData {

  /**
   * The set of market data for all scenarios.
   */
  @PropertyDefinition(validate = "notNull")
  private final ScenarioMarketData marketData;
  /**
   * The index of the scenario.
   * This index is used to query the multi-scenario market data.
   */
  @PropertyDefinition
  private final int scenarioIndex;

  //-------------------------------------------------------------------------
  /**
   * Obtains an instance from an underlying set of market data and scenario index.
   * <p>
   * This provides a single scenario view of the underlying market data.
   *
   * @param marketData  the market data
   * @param scenarioIndex  the index of the scenario to be viewed
   * @return the market data
   * @throws IllegalArgumentException if the scenario index is invalid
   */
  public static SingleScenarioMarketData of(ScenarioMarketData marketData, int scenarioIndex) {
    return new SingleScenarioMarketData(marketData, scenarioIndex);
  }

  @ImmutableValidator
  private void validate() {
    ArgChecker.inRange(scenarioIndex, 0, marketData.getScenarioCount(), "scenarioIndex");
  }

  //-------------------------------------------------------------------------
  @Override
  public LocalDate getValuationDate() {
    return marketData.getValuationDate().getValue(scenarioIndex);
  }

  @Override
  public boolean containsValue(MarketDataId<?> id) {
    return marketData.containsValue(id);
  }

  @Override
  public <T> T getValue(MarketDataId<T> id) {
    return marketData.getValue(id).getValue(scenarioIndex);
  }

  @Override
  public <T> Optional<T> findValue(MarketDataId<T> id) {
    Optional<MarketDataBox<T>> optionalBox = marketData.findValue(id);
    return optionalBox.map(box -> box.getValue(scenarioIndex));
  }

  @Override
  public Set<MarketDataId<?>> getIds() {
    return marketData.getIds();
  }

  @Override
  public <T> Set<MarketDataId<T>> findIds(MarketDataName<T> name) {
    return marketData.findIds(name);
  }

  @Override
  public Set<ObservableId> getTimeSeriesIds() {
    return marketData.getTimeSeriesIds();
  }

  @Override
  public LocalDateDoubleTimeSeries getTimeSeries(ObservableId id) {
    return marketData.getTimeSeries(id);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code SingleScenarioMarketData}.
   */
  private static MetaBean META_BEAN = LightMetaBean.of(SingleScenarioMarketData.class);

  /**
   * The meta-bean for {@code SingleScenarioMarketData}.
   * @return the meta-bean, not null
   */
  public static MetaBean meta() {
    return META_BEAN;
  }

  static {
    JodaBeanUtils.registerMetaBean(META_BEAN);
  }

  private SingleScenarioMarketData(
      ScenarioMarketData marketData,
      int scenarioIndex) {
    JodaBeanUtils.notNull(marketData, "marketData");
    this.marketData = marketData;
    this.scenarioIndex = scenarioIndex;
    validate();
  }

  @Override
  public MetaBean metaBean() {
    return META_BEAN;
  }

  @Override
  public <R> Property<R> property(String propertyName) {
    return metaBean().<R>metaProperty(propertyName).createProperty(this);
  }

  @Override
  public Set<String> propertyNames() {
    return metaBean().metaPropertyMap().keySet();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the set of market data for all scenarios.
   * @return the value of the property, not null
   */
  public ScenarioMarketData getMarketData() {
    return marketData;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the index of the scenario.
   * This index is used to query the multi-scenario market data.
   * @return the value of the property
   */
  public int getScenarioIndex() {
    return scenarioIndex;
  }

  //-----------------------------------------------------------------------
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      SingleScenarioMarketData other = (SingleScenarioMarketData) obj;
      return JodaBeanUtils.equal(marketData, other.marketData) &&
          (scenarioIndex == other.scenarioIndex);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(marketData);
    hash = hash * 31 + JodaBeanUtils.hashCode(scenarioIndex);
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(96);
    buf.append("SingleScenarioMarketData{");
    buf.append("marketData").append('=').append(marketData).append(',').append(' ');
    buf.append("scenarioIndex").append('=').append(JodaBeanUtils.toString(scenarioIndex));
    buf.append('}');
    return buf.toString();
  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
