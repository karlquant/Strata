/*
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.market.curve;

import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;

import org.joda.beans.Bean;
import org.joda.beans.BeanBuilder;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaBean;
import org.joda.beans.MetaProperty;
import org.joda.beans.gen.BeanDefinition;
import org.joda.beans.gen.PropertyDefinition;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;
import org.joda.beans.impl.direct.DirectPrivateBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opengamma.strata.basics.ReferenceData;
import com.opengamma.strata.collect.array.DoubleArray;
import com.opengamma.strata.data.scenario.MarketDataBox;
import com.opengamma.strata.data.scenario.ScenarioPerturbation;
import com.opengamma.strata.market.ShiftType;

/**
 * Perturbation which applies a parallel shift to a curve.
 * <p>
 * The shift can be absolute or relative.
 * An absolute shift adds the shift amount to each point on the curve.
 * A relative shift applies a scaling to each point on the curve.
 * <p>
 * For example, a relative shift of 0.1 (10%) multiplies each value on the curve by 1.1, and a shift of -0.2 (-20%)
 * multiplies the value by 0.8. So for relative shifts the shifted value is {@code (value x (1 + shift))}.
 */
@BeanDefinition(builderScope = "private")
public final class CurveParallelShifts
    implements ScenarioPerturbation<Curve>, ImmutableBean, Serializable {

  /** Logger. */
  private static final Logger log = LoggerFactory.getLogger(CurveParallelShifts.class);

  /**
   * The type of shift to apply to the y-values of the curve.
   */
  @PropertyDefinition(validate = "notNull")
  private final ShiftType shiftType;
  /**
   * The amount by which the y-values are shifted.
   */
  @PropertyDefinition(validate = "notNull")
  private final DoubleArray shiftAmounts;

  //-------------------------------------------------------------------------
  /**
   * Creates a shift that adds a fixed amount to the value at every node in the curve.
   *
   * @param shiftAmounts  the amount to add to each node value in the curve
   * @return a shift that adds a fixed amount to the value at every node in the curve
   */
  public static CurveParallelShifts absolute(double... shiftAmounts) {
    return new CurveParallelShifts(ShiftType.ABSOLUTE, DoubleArray.copyOf(shiftAmounts));
  }

  /**
   * Creates a shift that multiplies the values at each curve node by a scaling factor.
   * <p>
   * The shift amount is a decimal percentage. For example, a shift amount of 0.1 is a
   * shift of +10% which multiplies the value by 1.1. A shift amount of -0.2 is a shift
   * of -20% which multiplies the value by 0.8.
   *
   * @param shiftAmounts  the factor to multiply the value at each curve node by
   * @return a shift that multiplies the values at each curve node by a scaling factor
   */
  public static CurveParallelShifts relative(double... shiftAmounts) {
    return new CurveParallelShifts(ShiftType.RELATIVE, DoubleArray.copyOf(shiftAmounts));
  }

  //-------------------------------------------------------------------------
  @Override
  public MarketDataBox<Curve> applyTo(MarketDataBox<Curve> curve, ReferenceData refData) {
    return curve.mapWithIndex(getScenarioCount(), this::applyShift);
  }

  private Curve applyShift(Curve curve, int scenarioIndex) {
    double shiftAmount = shiftAmounts.get(scenarioIndex);
    log.debug("Applying {} parallel shift of {} to curve '{}'", shiftType, shiftAmount, curve.getName());
    return ParallelShiftedCurve.of(curve, shiftType, shiftAmount);
  }

  @Override
  public int getScenarioCount() {
    return shiftAmounts.size();
  }

  //------------------------- AUTOGENERATED START -------------------------
  /**
   * The meta-bean for {@code CurveParallelShifts}.
   * @return the meta-bean, not null
   */
  public static CurveParallelShifts.Meta meta() {
    return CurveParallelShifts.Meta.INSTANCE;
  }

  static {
    MetaBean.register(CurveParallelShifts.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  private CurveParallelShifts(
      ShiftType shiftType,
      DoubleArray shiftAmounts) {
    JodaBeanUtils.notNull(shiftType, "shiftType");
    JodaBeanUtils.notNull(shiftAmounts, "shiftAmounts");
    this.shiftType = shiftType;
    this.shiftAmounts = shiftAmounts;
  }

  @Override
  public CurveParallelShifts.Meta metaBean() {
    return CurveParallelShifts.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the type of shift to apply to the y-values of the curve.
   * @return the value of the property, not null
   */
  public ShiftType getShiftType() {
    return shiftType;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the amount by which the y-values are shifted.
   * @return the value of the property, not null
   */
  public DoubleArray getShiftAmounts() {
    return shiftAmounts;
  }

  //-----------------------------------------------------------------------
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      CurveParallelShifts other = (CurveParallelShifts) obj;
      return JodaBeanUtils.equal(shiftType, other.shiftType) &&
          JodaBeanUtils.equal(shiftAmounts, other.shiftAmounts);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(shiftType);
    hash = hash * 31 + JodaBeanUtils.hashCode(shiftAmounts);
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(96);
    buf.append("CurveParallelShifts{");
    buf.append("shiftType").append('=').append(shiftType).append(',').append(' ');
    buf.append("shiftAmounts").append('=').append(JodaBeanUtils.toString(shiftAmounts));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code CurveParallelShifts}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code shiftType} property.
     */
    private final MetaProperty<ShiftType> shiftType = DirectMetaProperty.ofImmutable(
        this, "shiftType", CurveParallelShifts.class, ShiftType.class);
    /**
     * The meta-property for the {@code shiftAmounts} property.
     */
    private final MetaProperty<DoubleArray> shiftAmounts = DirectMetaProperty.ofImmutable(
        this, "shiftAmounts", CurveParallelShifts.class, DoubleArray.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "shiftType",
        "shiftAmounts");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 893345500:  // shiftType
          return shiftType;
        case 2011836473:  // shiftAmounts
          return shiftAmounts;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends CurveParallelShifts> builder() {
      return new CurveParallelShifts.Builder();
    }

    @Override
    public Class<? extends CurveParallelShifts> beanType() {
      return CurveParallelShifts.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code shiftType} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ShiftType> shiftType() {
      return shiftType;
    }

    /**
     * The meta-property for the {@code shiftAmounts} property.
     * @return the meta-property, not null
     */
    public MetaProperty<DoubleArray> shiftAmounts() {
      return shiftAmounts;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 893345500:  // shiftType
          return ((CurveParallelShifts) bean).getShiftType();
        case 2011836473:  // shiftAmounts
          return ((CurveParallelShifts) bean).getShiftAmounts();
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
   * The bean-builder for {@code CurveParallelShifts}.
   */
  private static final class Builder extends DirectPrivateBeanBuilder<CurveParallelShifts> {

    private ShiftType shiftType;
    private DoubleArray shiftAmounts;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 893345500:  // shiftType
          return shiftType;
        case 2011836473:  // shiftAmounts
          return shiftAmounts;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 893345500:  // shiftType
          this.shiftType = (ShiftType) newValue;
          break;
        case 2011836473:  // shiftAmounts
          this.shiftAmounts = (DoubleArray) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public CurveParallelShifts build() {
      return new CurveParallelShifts(
          shiftType,
          shiftAmounts);
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(96);
      buf.append("CurveParallelShifts.Builder{");
      buf.append("shiftType").append('=').append(JodaBeanUtils.toString(shiftType)).append(',').append(' ');
      buf.append("shiftAmounts").append('=').append(JodaBeanUtils.toString(shiftAmounts));
      buf.append('}');
      return buf.toString();
    }

  }

  //-------------------------- AUTOGENERATED END --------------------------
}
