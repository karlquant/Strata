/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.calc.config;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.strata.basics.CalculationTarget;
import com.opengamma.strata.calc.marketdata.mapping.MarketDataMappings;
import com.opengamma.strata.collect.ArgChecker;

/**
 * Market data rules that provide no matches.
 * <p>
 * These rules always returns an empty optional from {@link #mappings}.
 */
@BeanDefinition(builderScope = "private")
final class EmptyMarketDataRules implements MarketDataRules, ImmutableBean {

  /**
   * The single, shared instance of this class.
   */
  static final EmptyMarketDataRules INSTANCE = new EmptyMarketDataRules();

  //-------------------------------------------------------------------------
  @Override
  public Optional<MarketDataMappings> mappings(CalculationTarget target) {
    return Optional.empty();
  }

  @Override
  public MarketDataRules composedWith(MarketDataRules rules) {
    // no point including this rule as it never returns anything
    return ArgChecker.notNull(rules, "rules");
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code EmptyMarketDataRules}.
   * @return the meta-bean, not null
   */
  public static EmptyMarketDataRules.Meta meta() {
    return EmptyMarketDataRules.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(EmptyMarketDataRules.Meta.INSTANCE);
  }

  private EmptyMarketDataRules() {
  }

  @Override
  public EmptyMarketDataRules.Meta metaBean() {
    return EmptyMarketDataRules.Meta.INSTANCE;
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
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(32);
    buf.append("EmptyMarketDataRules{");
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code EmptyMarketDataRules}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null);

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    public BeanBuilder<? extends EmptyMarketDataRules> builder() {
      return new EmptyMarketDataRules.Builder();
    }

    @Override
    public Class<? extends EmptyMarketDataRules> beanType() {
      return EmptyMarketDataRules.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code EmptyMarketDataRules}.
   */
  private static final class Builder extends DirectFieldsBeanBuilder<EmptyMarketDataRules> {

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      throw new NoSuchElementException("Unknown property: " + propertyName);
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      throw new NoSuchElementException("Unknown property: " + propertyName);
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
    public EmptyMarketDataRules build() {
      return new EmptyMarketDataRules();
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      return "EmptyMarketDataRules.Builder{}";
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
