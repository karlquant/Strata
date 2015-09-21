/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.finance.rate.future;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.ImmutableValidator;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.strata.basics.currency.Currency;
import com.opengamma.strata.collect.ArgChecker;
import com.opengamma.strata.finance.Product;
import com.opengamma.strata.finance.Security;
import com.opengamma.strata.finance.rate.swap.ExpandedSwapLeg;
import com.opengamma.strata.finance.rate.swap.NotionalExchange;
import com.opengamma.strata.finance.rate.swap.NotionalPaymentPeriod;
import com.opengamma.strata.finance.rate.swap.PaymentEvent;
import com.opengamma.strata.finance.rate.swap.PaymentPeriod;
import com.opengamma.strata.finance.rate.swap.Swap;
import com.opengamma.strata.finance.rate.swap.SwapLeg;
import com.opengamma.strata.finance.rate.swap.SwapLegType;

/**
 * A deliverable swap futures contract.
 * <p>
 * A deliverable swap future is a financial instrument that physically settles an interest rate swap on a future date. 
 * The futures product is margined on a daily basis. 
 * This class represents the structure of a single futures contract.
 */
@BeanDefinition
public final class DeliverableSwapFuture
    implements Product, ImmutableBean, Serializable {

  /**
   * The underlying swap.
   * <p>
   * The delivery date of the future is typically the first accrual date of the underlying swap. 
   * The swap should be a receiver swap of notional 1. 
   */
  @PropertyDefinition(validate = "notNull")
  private final Security<Swap> underlyingSecurity;
  /**
   * The delivery date. 
   * <p>
   * The underlying swap is delivered on this date.
   */
  @PropertyDefinition(validate = "notNull")
  private final LocalDate deliveryDate;
  /**
   * The last date of trading.
   * <p>
   * This date must be before the delivery date of the underlying swap. 
   */
  @PropertyDefinition(validate = "notNull")
  private final LocalDate lastTradeDate;
  /**
   * The notional of the futures. 
   * <p>
   * This is also called face value or contract value.
   */
  @PropertyDefinition(validate = "ArgChecker.notNegative")
  private final double notional;

  @ImmutableValidator
  private void validate() {
    Swap swap = getUnderlyingProduct();
    ArgChecker.inOrderOrEqual(deliveryDate, swap.getStartDate(), "deliveryDate", "startDate");
    ArgChecker.isFalse(swap.isCrossCurrency(), "underlying swap must not be cross currency");
    for (SwapLeg swapLeg : swap.getLegs()) {
      if (swapLeg.getType().equals(SwapLegType.FIXED)) {
        ArgChecker.isTrue(swapLeg.getPayReceive().isReceive(), "underlying must be receiver swap");
      }
      ExpandedSwapLeg expandedSwapLeg = swapLeg.expand();
      for (PaymentEvent event : expandedSwapLeg.getPaymentEvents()) {
        ArgChecker.isTrue(event instanceof NotionalExchange, "PaymentEvent must be NotionalExchange");
        NotionalExchange notioanlEvent = (NotionalExchange) event;
        ArgChecker.isTrue(Math.abs(notioanlEvent.getPaymentAmount().getAmount()) == 1d,
            "notional of underlying swap must be unity");
      }
      for (PaymentPeriod period : expandedSwapLeg.getPaymentPeriods()) {
        ArgChecker.isTrue(period instanceof NotionalPaymentPeriod, "PaymentPeriod must be NotionalPaymentPeriod");
        NotionalPaymentPeriod notioanlPeriod = (NotionalPaymentPeriod) period;
        ArgChecker.isTrue(Math.abs(notioanlPeriod.getNotionalAmount().getAmount()) == 1d,
            "notional of underlying swap must be unity");
      }
    }
    ArgChecker.inOrderOrEqual(lastTradeDate, deliveryDate, "lastTradeDate", "deliveryDate");
  }

  //-------------------------------------------------------------------------
  /**
   * Gets the underlying swap product.
   * 
   * @return the product underlying the option
   */
  public Swap getUnderlyingProduct() {
    return getUnderlyingSecurity().getProduct();
  }

  /**
   * Gets the currency of the underlying swap.
   * <p>
   * The underlying swap must have a single currency.
   * 
   * @return the currency of the swap
   */
  public Currency getCurrency() {
    return getUnderlyingProduct().getReceiveLeg().get().getCurrency();
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code DeliverableSwapFuture}.
   * @return the meta-bean, not null
   */
  public static DeliverableSwapFuture.Meta meta() {
    return DeliverableSwapFuture.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(DeliverableSwapFuture.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static DeliverableSwapFuture.Builder builder() {
    return new DeliverableSwapFuture.Builder();
  }

  private DeliverableSwapFuture(
      Security<Swap> underlyingSecurity,
      LocalDate deliveryDate,
      LocalDate lastTradeDate,
      double notional) {
    JodaBeanUtils.notNull(underlyingSecurity, "underlyingSecurity");
    JodaBeanUtils.notNull(deliveryDate, "deliveryDate");
    JodaBeanUtils.notNull(lastTradeDate, "lastTradeDate");
    ArgChecker.notNegative(notional, "notional");
    this.underlyingSecurity = underlyingSecurity;
    this.deliveryDate = deliveryDate;
    this.lastTradeDate = lastTradeDate;
    this.notional = notional;
    validate();
  }

  @Override
  public DeliverableSwapFuture.Meta metaBean() {
    return DeliverableSwapFuture.Meta.INSTANCE;
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
   * Gets the underlying swap.
   * <p>
   * The delivery date of the future is typically the first accrual date of the underlying swap.
   * The swap should be a receiver swap of notional 1.
   * @return the value of the property, not null
   */
  public Security<Swap> getUnderlyingSecurity() {
    return underlyingSecurity;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the delivery date.
   * <p>
   * The underlying swap is delivered on this date.
   * @return the value of the property, not null
   */
  public LocalDate getDeliveryDate() {
    return deliveryDate;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the last date of trading.
   * <p>
   * This date must be before the delivery date of the underlying swap.
   * @return the value of the property, not null
   */
  public LocalDate getLastTradeDate() {
    return lastTradeDate;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the notional of the futures.
   * <p>
   * This is also called face value or contract value.
   * @return the value of the property
   */
  public double getNotional() {
    return notional;
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
      DeliverableSwapFuture other = (DeliverableSwapFuture) obj;
      return JodaBeanUtils.equal(getUnderlyingSecurity(), other.getUnderlyingSecurity()) &&
          JodaBeanUtils.equal(getDeliveryDate(), other.getDeliveryDate()) &&
          JodaBeanUtils.equal(getLastTradeDate(), other.getLastTradeDate()) &&
          JodaBeanUtils.equal(getNotional(), other.getNotional());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(getUnderlyingSecurity());
    hash = hash * 31 + JodaBeanUtils.hashCode(getDeliveryDate());
    hash = hash * 31 + JodaBeanUtils.hashCode(getLastTradeDate());
    hash = hash * 31 + JodaBeanUtils.hashCode(getNotional());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(160);
    buf.append("DeliverableSwapFuture{");
    buf.append("underlyingSecurity").append('=').append(getUnderlyingSecurity()).append(',').append(' ');
    buf.append("deliveryDate").append('=').append(getDeliveryDate()).append(',').append(' ');
    buf.append("lastTradeDate").append('=').append(getLastTradeDate()).append(',').append(' ');
    buf.append("notional").append('=').append(JodaBeanUtils.toString(getNotional()));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code DeliverableSwapFuture}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code underlyingSecurity} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<Security<Swap>> underlyingSecurity = DirectMetaProperty.ofImmutable(
        this, "underlyingSecurity", DeliverableSwapFuture.class, (Class) Security.class);
    /**
     * The meta-property for the {@code deliveryDate} property.
     */
    private final MetaProperty<LocalDate> deliveryDate = DirectMetaProperty.ofImmutable(
        this, "deliveryDate", DeliverableSwapFuture.class, LocalDate.class);
    /**
     * The meta-property for the {@code lastTradeDate} property.
     */
    private final MetaProperty<LocalDate> lastTradeDate = DirectMetaProperty.ofImmutable(
        this, "lastTradeDate", DeliverableSwapFuture.class, LocalDate.class);
    /**
     * The meta-property for the {@code notional} property.
     */
    private final MetaProperty<Double> notional = DirectMetaProperty.ofImmutable(
        this, "notional", DeliverableSwapFuture.class, Double.TYPE);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "underlyingSecurity",
        "deliveryDate",
        "lastTradeDate",
        "notional");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -729254467:  // underlyingSecurity
          return underlyingSecurity;
        case 681469378:  // deliveryDate
          return deliveryDate;
        case -1041950404:  // lastTradeDate
          return lastTradeDate;
        case 1585636160:  // notional
          return notional;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public DeliverableSwapFuture.Builder builder() {
      return new DeliverableSwapFuture.Builder();
    }

    @Override
    public Class<? extends DeliverableSwapFuture> beanType() {
      return DeliverableSwapFuture.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code underlyingSecurity} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Security<Swap>> underlyingSecurity() {
      return underlyingSecurity;
    }

    /**
     * The meta-property for the {@code deliveryDate} property.
     * @return the meta-property, not null
     */
    public MetaProperty<LocalDate> deliveryDate() {
      return deliveryDate;
    }

    /**
     * The meta-property for the {@code lastTradeDate} property.
     * @return the meta-property, not null
     */
    public MetaProperty<LocalDate> lastTradeDate() {
      return lastTradeDate;
    }

    /**
     * The meta-property for the {@code notional} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Double> notional() {
      return notional;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -729254467:  // underlyingSecurity
          return ((DeliverableSwapFuture) bean).getUnderlyingSecurity();
        case 681469378:  // deliveryDate
          return ((DeliverableSwapFuture) bean).getDeliveryDate();
        case -1041950404:  // lastTradeDate
          return ((DeliverableSwapFuture) bean).getLastTradeDate();
        case 1585636160:  // notional
          return ((DeliverableSwapFuture) bean).getNotional();
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
   * The bean-builder for {@code DeliverableSwapFuture}.
   */
  public static final class Builder extends DirectFieldsBeanBuilder<DeliverableSwapFuture> {

    private Security<Swap> underlyingSecurity;
    private LocalDate deliveryDate;
    private LocalDate lastTradeDate;
    private double notional;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(DeliverableSwapFuture beanToCopy) {
      this.underlyingSecurity = beanToCopy.getUnderlyingSecurity();
      this.deliveryDate = beanToCopy.getDeliveryDate();
      this.lastTradeDate = beanToCopy.getLastTradeDate();
      this.notional = beanToCopy.getNotional();
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case -729254467:  // underlyingSecurity
          return underlyingSecurity;
        case 681469378:  // deliveryDate
          return deliveryDate;
        case -1041950404:  // lastTradeDate
          return lastTradeDate;
        case 1585636160:  // notional
          return notional;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case -729254467:  // underlyingSecurity
          this.underlyingSecurity = (Security<Swap>) newValue;
          break;
        case 681469378:  // deliveryDate
          this.deliveryDate = (LocalDate) newValue;
          break;
        case -1041950404:  // lastTradeDate
          this.lastTradeDate = (LocalDate) newValue;
          break;
        case 1585636160:  // notional
          this.notional = (Double) newValue;
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
    public DeliverableSwapFuture build() {
      return new DeliverableSwapFuture(
          underlyingSecurity,
          deliveryDate,
          lastTradeDate,
          notional);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the underlying swap.
     * <p>
     * The delivery date of the future is typically the first accrual date of the underlying swap.
     * The swap should be a receiver swap of notional 1.
     * @param underlyingSecurity  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder underlyingSecurity(Security<Swap> underlyingSecurity) {
      JodaBeanUtils.notNull(underlyingSecurity, "underlyingSecurity");
      this.underlyingSecurity = underlyingSecurity;
      return this;
    }

    /**
     * Sets the delivery date.
     * <p>
     * The underlying swap is delivered on this date.
     * @param deliveryDate  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder deliveryDate(LocalDate deliveryDate) {
      JodaBeanUtils.notNull(deliveryDate, "deliveryDate");
      this.deliveryDate = deliveryDate;
      return this;
    }

    /**
     * Sets the last date of trading.
     * <p>
     * This date must be before the delivery date of the underlying swap.
     * @param lastTradeDate  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder lastTradeDate(LocalDate lastTradeDate) {
      JodaBeanUtils.notNull(lastTradeDate, "lastTradeDate");
      this.lastTradeDate = lastTradeDate;
      return this;
    }

    /**
     * Sets the notional of the futures.
     * <p>
     * This is also called face value or contract value.
     * @param notional  the new value
     * @return this, for chaining, not null
     */
    public Builder notional(double notional) {
      ArgChecker.notNegative(notional, "notional");
      this.notional = notional;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(160);
      buf.append("DeliverableSwapFuture.Builder{");
      buf.append("underlyingSecurity").append('=').append(JodaBeanUtils.toString(underlyingSecurity)).append(',').append(' ');
      buf.append("deliveryDate").append('=').append(JodaBeanUtils.toString(deliveryDate)).append(',').append(' ');
      buf.append("lastTradeDate").append('=').append(JodaBeanUtils.toString(lastTradeDate)).append(',').append(' ');
      buf.append("notional").append('=').append(JodaBeanUtils.toString(notional));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
