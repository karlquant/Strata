/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.calc.runner;

import static com.opengamma.strata.collect.TestHelper.coverImmutableBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertNotNull;

import java.util.List;
import java.util.Set;

import org.testng.annotations.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.opengamma.strata.basics.currency.Currency;
import com.opengamma.strata.basics.market.MarketDataId;
import com.opengamma.strata.basics.market.ObservableId;
import com.opengamma.strata.basics.market.ReferenceData;
import com.opengamma.strata.calc.CalculationRules;
import com.opengamma.strata.calc.Column;
import com.opengamma.strata.calc.Measures;
import com.opengamma.strata.calc.ReportingCurrency;
import com.opengamma.strata.calc.marketdata.MarketDataRequirements;
import com.opengamma.strata.calc.marketdata.TestId;
import com.opengamma.strata.calc.marketdata.TestObservableId;
import com.opengamma.strata.calc.runner.CalculationTaskTest.TestFunction;
import com.opengamma.strata.calc.runner.CalculationTaskTest.TestTarget;

/**
 * Test {@link CalculationTasks}.
 */
@Test
public class CalculationTasksTest {

  private static final ReferenceData REF_DATA = ReferenceData.standard();
  private static final TestTarget TARGET1 = new TestTarget();
  private static final TestTarget TARGET2 = new TestTarget();
  private static final CalculationFunctions CALC_FUNCTIONS = CalculationFunctions.empty();
  private static final ReportingCurrency REPORTING_CURRENCY = ReportingCurrency.of(Currency.USD);

  //-------------------------------------------------------------------------
  public void test_of() {
    CalculationFunctions functions = CalculationFunctions.of(ImmutableMap.of(TestTarget.class, new TestFunction()));
    List<TestTarget> targets = ImmutableList.of(TARGET1, TARGET2);
    List<Column> columns = ImmutableList.of(Column.of(Measures.PRESENT_VALUE), Column.of(Measures.PAR_RATE));
    CalculationRules calculationRules = CalculationRules.of(functions, REPORTING_CURRENCY);

    CalculationTasks test = CalculationTasks.of(calculationRules, targets, columns);
    assertThat(test.getTargets()).hasSize(2);
    assertThat(test.getTargets()).containsExactly(TARGET1, TARGET2);
    assertThat(test.getColumns()).hasSize(2);
    assertThat(test.getColumns()).containsExactly(Column.of(Measures.PRESENT_VALUE), Column.of(Measures.PAR_RATE));
    assertThat(test.getTasks()).hasSize(2);
    assertThat(test.getTasks().get(0).getTarget()).isEqualTo(TARGET1);
    assertThat(test.getTasks().get(0).getCells().size()).isEqualTo(2);
    assertThat(test.getTasks().get(0).getCells().get(0).getRowIndex()).isEqualTo(0);
    assertThat(test.getTasks().get(0).getCells().get(0).getColumnIndex()).isEqualTo(0);
    assertThat(test.getTasks().get(0).getCells().get(0).getMeasure()).isEqualTo(Measures.PRESENT_VALUE);
    assertThat(test.getTasks().get(0).getCells().get(1).getRowIndex()).isEqualTo(0);
    assertThat(test.getTasks().get(0).getCells().get(1).getColumnIndex()).isEqualTo(1);
    assertThat(test.getTasks().get(0).getCells().get(1).getMeasure()).isEqualTo(Measures.PAR_RATE);

    assertThat(test.getTasks().get(1).getTarget()).isEqualTo(TARGET2);
    assertThat(test.getTasks().get(1).getCells().size()).isEqualTo(2);
    assertThat(test.getTasks().get(1).getCells().get(0).getRowIndex()).isEqualTo(1);
    assertThat(test.getTasks().get(1).getCells().get(0).getColumnIndex()).isEqualTo(0);
    assertThat(test.getTasks().get(1).getCells().get(0).getMeasure()).isEqualTo(Measures.PRESENT_VALUE);
    assertThat(test.getTasks().get(1).getCells().get(1).getRowIndex()).isEqualTo(1);
    assertThat(test.getTasks().get(1).getCells().get(1).getColumnIndex()).isEqualTo(1);
    assertThat(test.getTasks().get(1).getCells().get(1).getMeasure()).isEqualTo(Measures.PAR_RATE);

    coverImmutableBean(test);
    assertNotNull(CalculationTasks.meta());
  }

  //-------------------------------------------------------------------------
  public void test_requirements() {
    CalculationFunctions functions = CalculationFunctions.of(ImmutableMap.of(TestTarget.class, new TestFunction()));
    CalculationRules calculationRules = CalculationRules.of(functions, REPORTING_CURRENCY);
    List<TestTarget> targets = ImmutableList.of(TARGET1);
    List<Column> columns = ImmutableList.of(Column.of(Measures.PRESENT_VALUE));

    CalculationTasks test = CalculationTasks.of(calculationRules, targets, columns);

    MarketDataRequirements requirements = test.requirements(REF_DATA);
    Set<? extends MarketDataId<?>> nonObservables = requirements.getNonObservables();
    ImmutableSet<? extends ObservableId> observables = requirements.getObservables();
    ImmutableSet<ObservableId> timeSeries = requirements.getTimeSeries();

    assertThat(nonObservables).hasSize(1);
    assertThat(nonObservables.iterator().next()).isEqualTo(TestId.of("1"));

    MarketDataId<?> observableId = new TestObservableId("2", CalculationTaskTest.FEED);
    assertThat(observables).hasSize(1);
    assertThat(observables.iterator().next()).isEqualTo(observableId);

    MarketDataId<?> timeSeriesId = new TestObservableId("3", CalculationTaskTest.FEED);
    assertThat(timeSeries).hasSize(1);
    assertThat(timeSeries.iterator().next()).isEqualTo(timeSeriesId);
  }

  //-------------------------------------------------------------------------
  public void testToString() {
    List<TestTarget> targets = ImmutableList.of(TARGET1, TARGET1);
    List<Column> columns = ImmutableList.of(
        Column.of(Measures.PRESENT_VALUE),
        Column.of(Measures.PRESENT_VALUE),
        Column.of(Measures.PRESENT_VALUE));
    CalculationRules rules = CalculationRules.of(CALC_FUNCTIONS, REPORTING_CURRENCY);
    CalculationTasks task = CalculationTasks.of(rules, targets, columns);
    assertThat(task.toString()).isEqualTo("CalculationTasks[grid=2x3]");
  }

}
