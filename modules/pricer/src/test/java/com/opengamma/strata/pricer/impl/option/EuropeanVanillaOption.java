/*
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.pricer.impl.option;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;

import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaBean;
import org.joda.beans.TypedMetaBean;
import org.joda.beans.gen.BeanDefinition;
import org.joda.beans.gen.PropertyDefinition;
import org.joda.beans.impl.light.LightMetaBean;

import com.opengamma.strata.collect.ArgChecker;
import com.opengamma.strata.product.common.PutCall;

/**
 * Simple representation of a European-style vanilla option.
 */
@BeanDefinition(style = "light")
public final class EuropeanVanillaOption
    implements ImmutableBean, Serializable {

  /**
   * The strike.
   */
  @PropertyDefinition
  private final double strike;
  /**
   * The time to expiry, year fraction.
   */
  @PropertyDefinition(validate = "ArgChecker.notNegative")
  private final double timeToExpiry;
  /**
   * Whether the option is call or put.
   */
  @PropertyDefinition(validate = "notNull")
  private final PutCall putCall;

  //-------------------------------------------------------------------------
  /**
   * Obtains an instance.
   * 
   * @param strike  the strike
   * @param timeToExpiry  the time to expiry, year fraction
   * @param putCall  whether the option is put or call.
   * @return the option definition
   */
  public static EuropeanVanillaOption of(double strike, double timeToExpiry, PutCall putCall) {
    return new EuropeanVanillaOption(strike, timeToExpiry, putCall);
  }

  //-------------------------------------------------------------------------
  /**
   * Checks if the option is call.
   * 
   * @return true if call, false if put
   */
  public boolean isCall() {
    return putCall == PutCall.CALL;
  }

  //------------------------- AUTOGENERATED START -------------------------
  /**
   * The meta-bean for {@code EuropeanVanillaOption}.
   */
  private static final TypedMetaBean<EuropeanVanillaOption> META_BEAN =
      LightMetaBean.of(EuropeanVanillaOption.class, MethodHandles.lookup());

  /**
   * The meta-bean for {@code EuropeanVanillaOption}.
   * @return the meta-bean, not null
   */
  public static TypedMetaBean<EuropeanVanillaOption> meta() {
    return META_BEAN;
  }

  static {
    MetaBean.register(META_BEAN);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  private EuropeanVanillaOption(
      double strike,
      double timeToExpiry,
      PutCall putCall) {
    ArgChecker.notNegative(timeToExpiry, "timeToExpiry");
    JodaBeanUtils.notNull(putCall, "putCall");
    this.strike = strike;
    this.timeToExpiry = timeToExpiry;
    this.putCall = putCall;
  }

  @Override
  public TypedMetaBean<EuropeanVanillaOption> metaBean() {
    return META_BEAN;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the strike.
   * @return the value of the property
   */
  public double getStrike() {
    return strike;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the time to expiry, year fraction.
   * @return the value of the property
   */
  public double getTimeToExpiry() {
    return timeToExpiry;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets whether the option is call or put.
   * @return the value of the property, not null
   */
  public PutCall getPutCall() {
    return putCall;
  }

  //-----------------------------------------------------------------------
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      EuropeanVanillaOption other = (EuropeanVanillaOption) obj;
      return JodaBeanUtils.equal(strike, other.strike) &&
          JodaBeanUtils.equal(timeToExpiry, other.timeToExpiry) &&
          JodaBeanUtils.equal(putCall, other.putCall);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(strike);
    hash = hash * 31 + JodaBeanUtils.hashCode(timeToExpiry);
    hash = hash * 31 + JodaBeanUtils.hashCode(putCall);
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(128);
    buf.append("EuropeanVanillaOption{");
    buf.append("strike").append('=').append(strike).append(',').append(' ');
    buf.append("timeToExpiry").append('=').append(timeToExpiry).append(',').append(' ');
    buf.append("putCall").append('=').append(JodaBeanUtils.toString(putCall));
    buf.append('}');
    return buf.toString();
  }

  //-------------------------- AUTOGENERATED END --------------------------
}
