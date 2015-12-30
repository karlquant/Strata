/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.calc.config;

import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.google.common.collect.ImmutableMap;
import com.opengamma.strata.basics.CalculationTarget;
import com.opengamma.strata.calc.marketdata.mapping.MarketDataMappings;
import com.opengamma.strata.calc.runner.function.CalculationSingleFunction;

/**
 * Configuration of a task that calculates the value of a single measure for a target.
 * <p>
 * It contains the target, configuration for the function that performs the calculation, and
 * mappings that specify the market data that is used in the calculation.
 */
@BeanDefinition
@SuppressWarnings("rawtypes")
public final class CalculationTaskConfig implements ImmutableBean, Serializable {

  /**
   * The target for which the value will be calculated.
   * This is typically a trade.
   */
  @PropertyDefinition(validate = "notNull")
  private final CalculationTarget target;
  /**
   * The row index of the value in the results grid.
   */
  @PropertyDefinition
  private final int rowIndex;
  /**
   * The column index of the value in the results grid.
   */
  @PropertyDefinition
  private final int columnIndex;
  /**
   * Configuration of the function that will calculate the value.
   */
  @PropertyDefinition(validate = "notNull")
  private final FunctionConfig functionConfig;
  /**
   * The constructor arguments from the pricing rules, used when creating the function instance.
   */
  @PropertyDefinition(validate = "notNull")
  private final ImmutableMap<String, Object> functionArguments;
  /**
   * Mappings that specify the market data that should be used in the calculation.
   */
  @PropertyDefinition(validate = "notNull")
  private final MarketDataMappings marketDataMappings;
  /**
   * The rules for reporting the calculated values.
   */
  @PropertyDefinition(validate = "notNull")
  private final ReportingRules reportingRules;

  //-------------------------------------------------------------------------
  /**
   * Obtains configuration for a task that will calculate a value for a target.
   * <p>
   * This specifies the configuration of a single target, including the rules and cell index.
   *
   * @param target  the target for which the value will be calculated
   * @param rowIndex  the row index of the value in the results grid
   * @param columnIndex  the column index of the value in the results grid
   * @param functionConfig  the configuration of the function that will calculate the value
   * @param functionArguments  the constructor arguments from the pricing rules, used when creating the function instance
   * @param marketDataMappings  the mappings that specify the market data that should be used in the calculation
   * @param reportingRules  the reporting rules to control the output
   * @return the configuration for a task that will calculate the value of a measure for a target
   */
  public static CalculationTaskConfig of(
      CalculationTarget target,
      int rowIndex,
      int columnIndex,
      FunctionConfig functionConfig,
      Map<String, Object> functionArguments,
      MarketDataMappings marketDataMappings,
      ReportingRules reportingRules) {

    return new CalculationTaskConfig(
        target,
        rowIndex,
        columnIndex,
        functionConfig,
        functionArguments,
        marketDataMappings,
        reportingRules);
  }

  //-------------------------------------------------------------------------
  /**
   * Creates the function instance that performs the calculation.
   *
   * @return the function instance that performs the calculation
   */
  @SuppressWarnings("unchecked")
  public CalculationSingleFunction<?, ?> createFunction() {
    return functionConfig.createFunction(functionArguments);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code CalculationTaskConfig}.
   * @return the meta-bean, not null
   */
  public static CalculationTaskConfig.Meta meta() {
    return CalculationTaskConfig.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(CalculationTaskConfig.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static CalculationTaskConfig.Builder builder() {
    return new CalculationTaskConfig.Builder();
  }

  private CalculationTaskConfig(
      CalculationTarget target,
      int rowIndex,
      int columnIndex,
      FunctionConfig functionConfig,
      Map<String, Object> functionArguments,
      MarketDataMappings marketDataMappings,
      ReportingRules reportingRules) {
    JodaBeanUtils.notNull(target, "target");
    JodaBeanUtils.notNull(functionConfig, "functionConfig");
    JodaBeanUtils.notNull(functionArguments, "functionArguments");
    JodaBeanUtils.notNull(marketDataMappings, "marketDataMappings");
    JodaBeanUtils.notNull(reportingRules, "reportingRules");
    this.target = target;
    this.rowIndex = rowIndex;
    this.columnIndex = columnIndex;
    this.functionConfig = functionConfig;
    this.functionArguments = ImmutableMap.copyOf(functionArguments);
    this.marketDataMappings = marketDataMappings;
    this.reportingRules = reportingRules;
  }

  @Override
  public CalculationTaskConfig.Meta metaBean() {
    return CalculationTaskConfig.Meta.INSTANCE;
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
   * Gets the target for which the value will be calculated.
   * This is typically a trade.
   * @return the value of the property, not null
   */
  public CalculationTarget getTarget() {
    return target;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the row index of the value in the results grid.
   * @return the value of the property
   */
  public int getRowIndex() {
    return rowIndex;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the column index of the value in the results grid.
   * @return the value of the property
   */
  public int getColumnIndex() {
    return columnIndex;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets configuration of the function that will calculate the value.
   * @return the value of the property, not null
   */
  public FunctionConfig getFunctionConfig() {
    return functionConfig;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the constructor arguments from the pricing rules, used when creating the function instance.
   * @return the value of the property, not null
   */
  public ImmutableMap<String, Object> getFunctionArguments() {
    return functionArguments;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets mappings that specify the market data that should be used in the calculation.
   * @return the value of the property, not null
   */
  public MarketDataMappings getMarketDataMappings() {
    return marketDataMappings;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the rules for reporting the calculated values.
   * @return the value of the property, not null
   */
  public ReportingRules getReportingRules() {
    return reportingRules;
  }

  //-----------------------------------------------------------------------
  /**
   * Returns a builder that allows this bean to be mutated.
   * @return the mutable builder, not null
   */
  public Builder toBuilder() {
    return new Builder(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      CalculationTaskConfig other = (CalculationTaskConfig) obj;
      return JodaBeanUtils.equal(target, other.target) &&
          (rowIndex == other.rowIndex) &&
          (columnIndex == other.columnIndex) &&
          JodaBeanUtils.equal(functionConfig, other.functionConfig) &&
          JodaBeanUtils.equal(functionArguments, other.functionArguments) &&
          JodaBeanUtils.equal(marketDataMappings, other.marketDataMappings) &&
          JodaBeanUtils.equal(reportingRules, other.reportingRules);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(target);
    hash = hash * 31 + JodaBeanUtils.hashCode(rowIndex);
    hash = hash * 31 + JodaBeanUtils.hashCode(columnIndex);
    hash = hash * 31 + JodaBeanUtils.hashCode(functionConfig);
    hash = hash * 31 + JodaBeanUtils.hashCode(functionArguments);
    hash = hash * 31 + JodaBeanUtils.hashCode(marketDataMappings);
    hash = hash * 31 + JodaBeanUtils.hashCode(reportingRules);
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(256);
    buf.append("CalculationTaskConfig{");
    buf.append("target").append('=').append(target).append(',').append(' ');
    buf.append("rowIndex").append('=').append(rowIndex).append(',').append(' ');
    buf.append("columnIndex").append('=').append(columnIndex).append(',').append(' ');
    buf.append("functionConfig").append('=').append(functionConfig).append(',').append(' ');
    buf.append("functionArguments").append('=').append(functionArguments).append(',').append(' ');
    buf.append("marketDataMappings").append('=').append(marketDataMappings).append(',').append(' ');
    buf.append("reportingRules").append('=').append(JodaBeanUtils.toString(reportingRules));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code CalculationTaskConfig}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code target} property.
     */
    private final MetaProperty<CalculationTarget> target = DirectMetaProperty.ofImmutable(
        this, "target", CalculationTaskConfig.class, CalculationTarget.class);
    /**
     * The meta-property for the {@code rowIndex} property.
     */
    private final MetaProperty<Integer> rowIndex = DirectMetaProperty.ofImmutable(
        this, "rowIndex", CalculationTaskConfig.class, Integer.TYPE);
    /**
     * The meta-property for the {@code columnIndex} property.
     */
    private final MetaProperty<Integer> columnIndex = DirectMetaProperty.ofImmutable(
        this, "columnIndex", CalculationTaskConfig.class, Integer.TYPE);
    /**
     * The meta-property for the {@code functionConfig} property.
     */
    private final MetaProperty<FunctionConfig> functionConfig = DirectMetaProperty.ofImmutable(
        this, "functionConfig", CalculationTaskConfig.class, FunctionConfig.class);
    /**
     * The meta-property for the {@code functionArguments} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<ImmutableMap<String, Object>> functionArguments = DirectMetaProperty.ofImmutable(
        this, "functionArguments", CalculationTaskConfig.class, (Class) ImmutableMap.class);
    /**
     * The meta-property for the {@code marketDataMappings} property.
     */
    private final MetaProperty<MarketDataMappings> marketDataMappings = DirectMetaProperty.ofImmutable(
        this, "marketDataMappings", CalculationTaskConfig.class, MarketDataMappings.class);
    /**
     * The meta-property for the {@code reportingRules} property.
     */
    private final MetaProperty<ReportingRules> reportingRules = DirectMetaProperty.ofImmutable(
        this, "reportingRules", CalculationTaskConfig.class, ReportingRules.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "target",
        "rowIndex",
        "columnIndex",
        "functionConfig",
        "functionArguments",
        "marketDataMappings",
        "reportingRules");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -880905839:  // target
          return target;
        case 23238424:  // rowIndex
          return rowIndex;
        case -855241956:  // columnIndex
          return columnIndex;
        case -1567383238:  // functionConfig
          return functionConfig;
        case -260573090:  // functionArguments
          return functionArguments;
        case -662537845:  // marketDataMappings
          return marketDataMappings;
        case -1647034519:  // reportingRules
          return reportingRules;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public CalculationTaskConfig.Builder builder() {
      return new CalculationTaskConfig.Builder();
    }

    @Override
    public Class<? extends CalculationTaskConfig> beanType() {
      return CalculationTaskConfig.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code target} property.
     * @return the meta-property, not null
     */
    public MetaProperty<CalculationTarget> target() {
      return target;
    }

    /**
     * The meta-property for the {@code rowIndex} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Integer> rowIndex() {
      return rowIndex;
    }

    /**
     * The meta-property for the {@code columnIndex} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Integer> columnIndex() {
      return columnIndex;
    }

    /**
     * The meta-property for the {@code functionConfig} property.
     * @return the meta-property, not null
     */
    public MetaProperty<FunctionConfig> functionConfig() {
      return functionConfig;
    }

    /**
     * The meta-property for the {@code functionArguments} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ImmutableMap<String, Object>> functionArguments() {
      return functionArguments;
    }

    /**
     * The meta-property for the {@code marketDataMappings} property.
     * @return the meta-property, not null
     */
    public MetaProperty<MarketDataMappings> marketDataMappings() {
      return marketDataMappings;
    }

    /**
     * The meta-property for the {@code reportingRules} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ReportingRules> reportingRules() {
      return reportingRules;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -880905839:  // target
          return ((CalculationTaskConfig) bean).getTarget();
        case 23238424:  // rowIndex
          return ((CalculationTaskConfig) bean).getRowIndex();
        case -855241956:  // columnIndex
          return ((CalculationTaskConfig) bean).getColumnIndex();
        case -1567383238:  // functionConfig
          return ((CalculationTaskConfig) bean).getFunctionConfig();
        case -260573090:  // functionArguments
          return ((CalculationTaskConfig) bean).getFunctionArguments();
        case -662537845:  // marketDataMappings
          return ((CalculationTaskConfig) bean).getMarketDataMappings();
        case -1647034519:  // reportingRules
          return ((CalculationTaskConfig) bean).getReportingRules();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      metaProperty(propertyName);
      if (quiet) {
        return;
      }
      throw new UnsupportedOperationException("Property cannot be written: " + propertyName);
    }

  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code CalculationTaskConfig}.
   */
  public static final class Builder extends DirectFieldsBeanBuilder<CalculationTaskConfig> {

    private CalculationTarget target;
    private int rowIndex;
    private int columnIndex;
    private FunctionConfig functionConfig;
    private Map<String, Object> functionArguments = ImmutableMap.of();
    private MarketDataMappings marketDataMappings;
    private ReportingRules reportingRules;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(CalculationTaskConfig beanToCopy) {
      this.target = beanToCopy.getTarget();
      this.rowIndex = beanToCopy.getRowIndex();
      this.columnIndex = beanToCopy.getColumnIndex();
      this.functionConfig = beanToCopy.getFunctionConfig();
      this.functionArguments = beanToCopy.getFunctionArguments();
      this.marketDataMappings = beanToCopy.getMarketDataMappings();
      this.reportingRules = beanToCopy.getReportingRules();
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case -880905839:  // target
          return target;
        case 23238424:  // rowIndex
          return rowIndex;
        case -855241956:  // columnIndex
          return columnIndex;
        case -1567383238:  // functionConfig
          return functionConfig;
        case -260573090:  // functionArguments
          return functionArguments;
        case -662537845:  // marketDataMappings
          return marketDataMappings;
        case -1647034519:  // reportingRules
          return reportingRules;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case -880905839:  // target
          this.target = (CalculationTarget) newValue;
          break;
        case 23238424:  // rowIndex
          this.rowIndex = (Integer) newValue;
          break;
        case -855241956:  // columnIndex
          this.columnIndex = (Integer) newValue;
          break;
        case -1567383238:  // functionConfig
          this.functionConfig = (FunctionConfig) newValue;
          break;
        case -260573090:  // functionArguments
          this.functionArguments = (Map<String, Object>) newValue;
          break;
        case -662537845:  // marketDataMappings
          this.marketDataMappings = (MarketDataMappings) newValue;
          break;
        case -1647034519:  // reportingRules
          this.reportingRules = (ReportingRules) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public Builder set(MetaProperty<?> property, Object value) {
      super.set(property, value);
      return this;
    }

    @Override
    public Builder setString(String propertyName, String value) {
      setString(meta().metaProperty(propertyName), value);
      return this;
    }

    @Override
    public Builder setString(MetaProperty<?> property, String value) {
      super.setString(property, value);
      return this;
    }

    @Override
    public Builder setAll(Map<String, ? extends Object> propertyValueMap) {
      super.setAll(propertyValueMap);
      return this;
    }

    @Override
    public CalculationTaskConfig build() {
      return new CalculationTaskConfig(
          target,
          rowIndex,
          columnIndex,
          functionConfig,
          functionArguments,
          marketDataMappings,
          reportingRules);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the target for which the value will be calculated.
     * This is typically a trade.
     * @param target  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder target(CalculationTarget target) {
      JodaBeanUtils.notNull(target, "target");
      this.target = target;
      return this;
    }

    /**
     * Sets the row index of the value in the results grid.
     * @param rowIndex  the new value
     * @return this, for chaining, not null
     */
    public Builder rowIndex(int rowIndex) {
      this.rowIndex = rowIndex;
      return this;
    }

    /**
     * Sets the column index of the value in the results grid.
     * @param columnIndex  the new value
     * @return this, for chaining, not null
     */
    public Builder columnIndex(int columnIndex) {
      this.columnIndex = columnIndex;
      return this;
    }

    /**
     * Sets configuration of the function that will calculate the value.
     * @param functionConfig  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder functionConfig(FunctionConfig functionConfig) {
      JodaBeanUtils.notNull(functionConfig, "functionConfig");
      this.functionConfig = functionConfig;
      return this;
    }

    /**
     * Sets the constructor arguments from the pricing rules, used when creating the function instance.
     * @param functionArguments  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder functionArguments(Map<String, Object> functionArguments) {
      JodaBeanUtils.notNull(functionArguments, "functionArguments");
      this.functionArguments = functionArguments;
      return this;
    }

    /**
     * Sets mappings that specify the market data that should be used in the calculation.
     * @param marketDataMappings  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder marketDataMappings(MarketDataMappings marketDataMappings) {
      JodaBeanUtils.notNull(marketDataMappings, "marketDataMappings");
      this.marketDataMappings = marketDataMappings;
      return this;
    }

    /**
     * Sets the rules for reporting the calculated values.
     * @param reportingRules  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder reportingRules(ReportingRules reportingRules) {
      JodaBeanUtils.notNull(reportingRules, "reportingRules");
      this.reportingRules = reportingRules;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(256);
      buf.append("CalculationTaskConfig.Builder{");
      buf.append("target").append('=').append(JodaBeanUtils.toString(target)).append(',').append(' ');
      buf.append("rowIndex").append('=').append(JodaBeanUtils.toString(rowIndex)).append(',').append(' ');
      buf.append("columnIndex").append('=').append(JodaBeanUtils.toString(columnIndex)).append(',').append(' ');
      buf.append("functionConfig").append('=').append(JodaBeanUtils.toString(functionConfig)).append(',').append(' ');
      buf.append("functionArguments").append('=').append(JodaBeanUtils.toString(functionArguments)).append(',').append(' ');
      buf.append("marketDataMappings").append('=').append(JodaBeanUtils.toString(marketDataMappings)).append(',').append(' ');
      buf.append("reportingRules").append('=').append(JodaBeanUtils.toString(reportingRules));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
