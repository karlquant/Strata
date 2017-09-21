/*
 * Copyright (C) 2016 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.product.credit.type;

import java.io.Serializable;
import java.time.LocalDate;
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

import com.opengamma.strata.basics.ReferenceData;
import com.opengamma.strata.basics.StandardId;
import com.opengamma.strata.basics.currency.AdjustablePayment;
import com.opengamma.strata.basics.date.Tenor;
import com.opengamma.strata.product.common.BuySell;
import com.opengamma.strata.product.credit.CdsTrade;

/**
 * A template for creating credit default swap trades.
 * <p>
 * This defines almost all the data necessary to create a credit default swap {@link CdsTrade}.
 * The start and end of the trade are defined in terms of {@code AccrualStart} and {@code Tenor}.
 * <p>
 * The legal entity ID, trade date, notional and fixed rate are required 
 * to complete the template and create the trade.
 * As such, it is often possible to get a market quote for a trade based on the template.
 * The start date (if it is not the next day) and end date are computed from trade date 
 * with the standard semi-annual roll convention. 
 * <p>
 * A CDS is quoted in points upfront, par spread, or quoted spread. 
 * For the latter two cases, the market quotes are passed as the fixed rate.
 */
@BeanDefinition(builderScope = "private")
public final class TenorCdsTemplate
    implements CdsTemplate, ImmutableBean, Serializable {

  /**
   * The accrual start.
   * <p>
   * Whether the accrual start is the next day or the previous IMM date.
   */
  @PropertyDefinition(validate = "notNull")
  private final AccrualStart accrualStart;
  /**
  * The tenor of the credit default swap.
  * <p>
  * This is the period to the protection end.
  */
  @PropertyDefinition(validate = "notNull")
  private final Tenor tenor;
  /**
  * The market convention of the credit default swap.
  */
  @PropertyDefinition(validate = "notNull", overrideGet = true)
  private final CdsConvention convention;

  //-------------------------------------------------------------------------
  /**
   * Obtains a template based on the specified tenor and convention.
   * <p>
   * The protection end will be calculated based on standard semi-annual roll convention.
   * 
   * @param accrualStart  the accrual start
   * @param tenor  the tenor of the CDS
   * @param convention  the market convention
   * @return the template
   */
  public static TenorCdsTemplate of(AccrualStart accrualStart, Tenor tenor, CdsConvention convention) {
    return new TenorCdsTemplate(accrualStart, tenor, convention);
  }

  /**
   * Obtains a template based on the specified tenor and convention.
   * <p>
   * The start and end dates will be calculated based on standard semi-annual roll convention.
   * 
   * @param tenor  the tenor of the CDS
   * @param convention  the market convention
   * @return the template
   */
  public static TenorCdsTemplate of(Tenor tenor, CdsConvention convention) {
    return of(AccrualStart.IMM_DATE, tenor, convention);
  }

  //-------------------------------------------------------------------------
  @Override
  public CdsTrade createTrade(
      StandardId legalEntityId,
      LocalDate tradeDate,
      BuySell buySell,
      double notional,
      double fixedRate,
      ReferenceData refData) {

    return accrualStart.equals(AccrualStart.IMM_DATE) ?
        convention.createTrade(legalEntityId, tradeDate, tenor, buySell, notional, fixedRate, refData) :
        convention.createTrade(legalEntityId, tradeDate, tradeDate.plusDays(1), tenor, buySell, notional, fixedRate, refData);
  }

  @Override
  public CdsTrade createTrade(
      StandardId legalEntityId,
      LocalDate tradeDate,
      BuySell buySell,
      double notional,
      double fixedRate,
      AdjustablePayment upFrontFee,
      ReferenceData refData) {

    return accrualStart.equals(AccrualStart.IMM_DATE) ?
        convention.createTrade(legalEntityId, tradeDate, tenor, buySell, notional, fixedRate, upFrontFee, refData) :
        convention.createTrade(
            legalEntityId, tradeDate, tradeDate.plusDays(1), tenor, buySell, notional, fixedRate, upFrontFee, refData);
  }

  //------------------------- AUTOGENERATED START -------------------------
  /**
   * The meta-bean for {@code TenorCdsTemplate}.
   * @return the meta-bean, not null
   */
  public static TenorCdsTemplate.Meta meta() {
    return TenorCdsTemplate.Meta.INSTANCE;
  }

  static {
    MetaBean.register(TenorCdsTemplate.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  private TenorCdsTemplate(
      AccrualStart accrualStart,
      Tenor tenor,
      CdsConvention convention) {
    JodaBeanUtils.notNull(accrualStart, "accrualStart");
    JodaBeanUtils.notNull(tenor, "tenor");
    JodaBeanUtils.notNull(convention, "convention");
    this.accrualStart = accrualStart;
    this.tenor = tenor;
    this.convention = convention;
  }

  @Override
  public TenorCdsTemplate.Meta metaBean() {
    return TenorCdsTemplate.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the accrual start.
   * <p>
   * Whether the accrual start is the next day or the previous IMM date.
   * @return the value of the property, not null
   */
  public AccrualStart getAccrualStart() {
    return accrualStart;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the tenor of the credit default swap.
   * <p>
   * This is the period to the protection end.
   * @return the value of the property, not null
   */
  public Tenor getTenor() {
    return tenor;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the market convention of the credit default swap.
   * @return the value of the property, not null
   */
  @Override
  public CdsConvention getConvention() {
    return convention;
  }

  //-----------------------------------------------------------------------
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      TenorCdsTemplate other = (TenorCdsTemplate) obj;
      return JodaBeanUtils.equal(accrualStart, other.accrualStart) &&
          JodaBeanUtils.equal(tenor, other.tenor) &&
          JodaBeanUtils.equal(convention, other.convention);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(accrualStart);
    hash = hash * 31 + JodaBeanUtils.hashCode(tenor);
    hash = hash * 31 + JodaBeanUtils.hashCode(convention);
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(128);
    buf.append("TenorCdsTemplate{");
    buf.append("accrualStart").append('=').append(accrualStart).append(',').append(' ');
    buf.append("tenor").append('=').append(tenor).append(',').append(' ');
    buf.append("convention").append('=').append(JodaBeanUtils.toString(convention));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code TenorCdsTemplate}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code accrualStart} property.
     */
    private final MetaProperty<AccrualStart> accrualStart = DirectMetaProperty.ofImmutable(
        this, "accrualStart", TenorCdsTemplate.class, AccrualStart.class);
    /**
     * The meta-property for the {@code tenor} property.
     */
    private final MetaProperty<Tenor> tenor = DirectMetaProperty.ofImmutable(
        this, "tenor", TenorCdsTemplate.class, Tenor.class);
    /**
     * The meta-property for the {@code convention} property.
     */
    private final MetaProperty<CdsConvention> convention = DirectMetaProperty.ofImmutable(
        this, "convention", TenorCdsTemplate.class, CdsConvention.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "accrualStart",
        "tenor",
        "convention");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 1071260659:  // accrualStart
          return accrualStart;
        case 110246592:  // tenor
          return tenor;
        case 2039569265:  // convention
          return convention;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends TenorCdsTemplate> builder() {
      return new TenorCdsTemplate.Builder();
    }

    @Override
    public Class<? extends TenorCdsTemplate> beanType() {
      return TenorCdsTemplate.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code accrualStart} property.
     * @return the meta-property, not null
     */
    public MetaProperty<AccrualStart> accrualStart() {
      return accrualStart;
    }

    /**
     * The meta-property for the {@code tenor} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Tenor> tenor() {
      return tenor;
    }

    /**
     * The meta-property for the {@code convention} property.
     * @return the meta-property, not null
     */
    public MetaProperty<CdsConvention> convention() {
      return convention;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 1071260659:  // accrualStart
          return ((TenorCdsTemplate) bean).getAccrualStart();
        case 110246592:  // tenor
          return ((TenorCdsTemplate) bean).getTenor();
        case 2039569265:  // convention
          return ((TenorCdsTemplate) bean).getConvention();
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
   * The bean-builder for {@code TenorCdsTemplate}.
   */
  private static final class Builder extends DirectPrivateBeanBuilder<TenorCdsTemplate> {

    private AccrualStart accrualStart;
    private Tenor tenor;
    private CdsConvention convention;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 1071260659:  // accrualStart
          return accrualStart;
        case 110246592:  // tenor
          return tenor;
        case 2039569265:  // convention
          return convention;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 1071260659:  // accrualStart
          this.accrualStart = (AccrualStart) newValue;
          break;
        case 110246592:  // tenor
          this.tenor = (Tenor) newValue;
          break;
        case 2039569265:  // convention
          this.convention = (CdsConvention) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public TenorCdsTemplate build() {
      return new TenorCdsTemplate(
          accrualStart,
          tenor,
          convention);
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(128);
      buf.append("TenorCdsTemplate.Builder{");
      buf.append("accrualStart").append('=').append(JodaBeanUtils.toString(accrualStart)).append(',').append(' ');
      buf.append("tenor").append('=').append(JodaBeanUtils.toString(tenor)).append(',').append(' ');
      buf.append("convention").append('=').append(JodaBeanUtils.toString(convention));
      buf.append('}');
      return buf.toString();
    }

  }

  //-------------------------- AUTOGENERATED END --------------------------

}
