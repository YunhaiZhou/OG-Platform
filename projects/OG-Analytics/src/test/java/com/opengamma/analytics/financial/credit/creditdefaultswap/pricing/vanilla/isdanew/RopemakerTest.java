/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.analytics.financial.credit.creditdefaultswap.pricing.vanilla.isdanew;

import static com.opengamma.analytics.financial.credit.creditdefaultswap.pricing.vanilla.isdanew.IMMDateLogic.getIMMDateSet;
import static com.opengamma.analytics.financial.credit.creditdefaultswap.pricing.vanilla.isdanew.IMMDateLogic.getNextIMMDate;
import static com.opengamma.analytics.financial.credit.creditdefaultswap.pricing.vanilla.isdanew.IMMDateLogic.getPrevIMMDate;
import static com.opengamma.analytics.financial.credit.creditdefaultswap.pricing.vanilla.isdanew.IMMDateLogic.isIMMDate;
import static com.opengamma.financial.convention.businessday.BusinessDayDateUtils.addWorkDays;

import java.util.Arrays;

import org.testng.annotations.Test;
import org.threeten.bp.LocalDate;
import org.threeten.bp.Month;
import org.threeten.bp.Period;

import com.opengamma.analytics.financial.model.BumpType;
import com.opengamma.util.ArgumentChecker;

/**
 * 
 */
public class RopemakerTest extends ISDABaseTest {

  private static final PointsUpFrontConverter PUF_CONVERTER = new PointsUpFrontConverter();

  //the buckets
  private static final Period[] TENORS = new Period[] {Period.ofMonths(6), Period.ofYears(1), Period.ofYears(2), Period.ofYears(3), Period.ofYears(4), Period.ofYears(5), Period.ofYears(6),
    Period.ofYears(7), Period.ofYears(8), Period.ofYears(9), Period.ofYears(10) };

  private static final double NOTIONAL = 1e6;
  private static final double COUPON = 0.01;
  private static final double RECOVERY = 0.25;
  private static final Period NON_IMM_TENOR = Period.ofMonths(6);

  @Test(enabled = false)
  public void russiaTest() {
    final String name = "Russia";

    final LocalDate tradeDate = LocalDate.of(2013, Month.JULY, 18);
    final LocalDate firstMaturity = LocalDate.of(2013, Month.JULY, 20);
    final LocalDate lastMaturity = LocalDate.of(2023, Month.SEPTEMBER, 20);
    final LocalDate[] maturities = getAllMaturities(firstMaturity, lastMaturity);

    final double[] spreads = new double[] {0.006316, 0.006345, 0.006374, 0.006613, 0.006754, 0.006846, 0.006919, 0.006991, 0.00706, 0.007292, 0.007483, 0.007652, 0.007897, 0.008114, 0.008307,
      0.008571, 0.008812, 0.009032, 0.009242, 0.009435, 0.009603, 0.009769, 0.009918, 0.010065, 0.010204, 0.010339, 0.010467, 0.010664, 0.010854, 0.011028, 0.011202, 0.011367, 0.011515, 0.011669,
      0.011811, 0.011952, 0.012092, 0.01223, 0.012363, 0.012583, 0.012801, 0.013005, 0.013206, 0.013402, 0.013579, 0.013761, 0.013935, 0.014113, 0.014274, 0.014438, 0.014604, 0.014823, 0.015052,
      0.015284, 0.015486, 0.015697, 0.015905, 0.01609, 0.016284, 0.016492, 0.016649, 0.016817, 0.017014, 0.01716, 0.017327, 0.017517, 0.017645, 0.017802, 0.017972, 0.018089, 0.018236, 0.018419,
      0.018504, 0.018633, 0.018806, 0.018873, 0.018989, 0.019149, 0.019206, 0.019311, 0.019464, 0.019513, 0.019611, 0.019769, 0.019803, 0.019896, 0.02005, 0.020068, 0.020148, 0.020291, 0.020305,
      0.020383, 0.020522, 0.020526, 0.020596, 0.020737, 0.02073, 0.020796, 0.020933, 0.02092, 0.020981, 0.021115, 0.021099, 0.021157, 0.021286, 0.021266, 0.02132, 0.021455, 0.021428, 0.021481,
      0.021615, 0.021581, 0.02163, 0.02176, 0.021724, 0.02177, 0.021897, 0.021858, 0.021902, 0.022033, 0.021981, 0.022025, 0.022122 };

    //yield curve
    final LocalDate spotDate = addWorkDays(tradeDate.minusDays(1), 3, DEFAULT_CALENDAR);
    final String[] yieldCurvePoints = new String[] {"1M", "2M", "3M", "6M", "1Y", "2Y", "3Y", "4Y", "5Y", "6Y", "7Y", "8Y", "9Y", "10Y", "12Y", "15Y", "20Y", "25Y", "30Y" };
    final String[] yieldCurveInstruments = new String[] {"M", "M", "M", "M", "M", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S" };
    final double[] rates = new double[] {0.001915, 0.002313, 0.002662, 0.004, 0.006842, 0.00475, 0.00752, 0.011185, 0.01487, 0.018215, 0.021005, 0.023305, 0.025315, 0.026945, 0.02962, 0.032075,
      0.034025, 0.03496, 0.03552 };
    final ISDACompliantYieldCurve yieldCurve = makeYieldCurve(tradeDate, spotDate, yieldCurvePoints, yieldCurveInstruments, rates, ACT360, D30360, Period.ofMonths(6));

    runGrid(name, tradeDate, maturities, spreads, yieldCurve);
  }

  @Test(enabled = false)
  public void gazpromTest() {
    final String name = "Gazprom";

    final LocalDate tradeDate = LocalDate.of(2013, Month.JULY, 18);
    final LocalDate firstMaturity = LocalDate.of(2013, Month.JULY, 20);
    final LocalDate lastMaturity = LocalDate.of(2023, Month.SEPTEMBER, 20);
    final LocalDate[] maturities = getAllMaturities(firstMaturity, lastMaturity);

    final double[] spreads = new double[] {0.008248, 0.008249, 0.008249, 0.008243, 0.008239, 0.008235, 0.008398, 0.008548, 0.008681, 0.00898, 0.009229, 0.009454, 0.009814, 0.010136, 0.01043,
      0.010901, 0.011348, 0.011739, 0.012099, 0.01244, 0.012743, 0.013042, 0.013325, 0.013613, 0.013844, 0.014074, 0.014307, 0.014627, 0.014943, 0.015254, 0.015522, 0.015797, 0.01607, 0.016302,
      0.016535, 0.016796, 0.016994, 0.017215, 0.017459, 0.017736, 0.018038, 0.01836, 0.018602, 0.018873, 0.019163, 0.019366, 0.019611, 0.019907, 0.020078, 0.020306, 0.020591, 0.020811, 0.021096,
      0.021445, 0.021633, 0.021895, 0.022227, 0.02238, 0.022617, 0.022968, 0.023063, 0.023273, 0.023606, 0.023641, 0.023806, 0.024101, 0.024108, 0.02426, 0.024558, 0.024546, 0.024688, 0.025002,
      0.024957, 0.025085, 0.025391, 0.025318, 0.025426, 0.025718, 0.025634, 0.025738, 0.026032, 0.025936, 0.026033, 0.026337, 0.026221, 0.026312, 0.026614, 0.026482, 0.026564, 0.026863, 0.026723,
      0.026801, 0.0271, 0.02695, 0.027024, 0.027334, 0.027166, 0.027233, 0.027542, 0.027364, 0.027427, 0.027736, 0.027551, 0.027613, 0.027922, 0.02773, 0.027788, 0.028107, 0.027903, 0.027958,
      0.028277, 0.028065, 0.028118, 0.028437, 0.02822, 0.028272, 0.028591, 0.028368, 0.028417, 0.028744, 0.028548, 0.028581, 0.028847 };

    //yield curve
    final LocalDate spotDate = addWorkDays(tradeDate.minusDays(1), 3, DEFAULT_CALENDAR);
    final String[] yieldCurvePoints = new String[] {"1M", "2M", "3M", "6M", "1Y", "2Y", "3Y", "4Y", "5Y", "6Y", "7Y", "8Y", "9Y", "10Y", "12Y", "15Y", "20Y", "25Y", "30Y" };
    final String[] yieldCurveInstruments = new String[] {"M", "M", "M", "M", "M", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S" };
    final double[] rates = new double[] {0.001915, 0.002313, 0.002662, 0.004, 0.006842, 0.00475, 0.00752, 0.011185, 0.01487, 0.018215, 0.021005, 0.023305, 0.025315, 0.026945, 0.02962, 0.032075,
      0.034025, 0.03496, 0.03552 };
    final ISDACompliantYieldCurve yieldCurve = makeYieldCurve(tradeDate, spotDate, yieldCurvePoints, yieldCurveInstruments, rates, ACT360, D30360, Period.ofMonths(6));

    runGrid(name, tradeDate, maturities, spreads, yieldCurve);
  }

  @Test(enabled = false)
  public void brazilTest() {
    final String name = "Brazil";

    final LocalDate tradeDate = LocalDate.of(2013, Month.JULY, 24);
    final LocalDate firstMaturity = LocalDate.of(2013, Month.AUGUST, 20);
    final LocalDate lastMaturity = LocalDate.of(2023, Month.SEPTEMBER, 20);
    final LocalDate[] maturities = getAllMaturities(firstMaturity, lastMaturity);

    final double[] spreads = new double[] {0.004537, 0.004538, 0.004795, 0.004926, 0.004998, 0.005168, 0.005286, 0.005362, 0.00581, 0.006139, 0.006414, 0.006798, 0.007124, 0.007399, 0.007853,
      0.008263, 0.008613, 0.008934, 0.009221, 0.009451, 0.009688, 0.009894, 0.010091, 0.010261, 0.010424, 0.010578, 0.010802, 0.01102, 0.011223, 0.011405, 0.011584, 0.011746, 0.011892, 0.012034,
      0.012183, 0.012297, 0.012422, 0.012551, 0.012746, 0.012951, 0.013157, 0.013321, 0.013497, 0.013673, 0.013807, 0.013957, 0.014132, 0.014236, 0.014371, 0.014535, 0.0147, 0.0149, 0.015133,
      0.015273, 0.015448, 0.01566, 0.015767, 0.015921, 0.016141, 0.016217, 0.016359, 0.016575, 0.016639, 0.016778, 0.016998, 0.017042, 0.017168, 0.017378, 0.017401, 0.017511, 0.017732, 0.017731,
      0.017836, 0.018057, 0.018043, 0.018147, 0.018374, 0.018345, 0.018441, 0.018667, 0.01862, 0.018705, 0.018941, 0.018874, 0.018956, 0.019194, 0.019102, 0.019168, 0.019394, 0.019293, 0.019355,
      0.019579, 0.019468, 0.019523, 0.019755, 0.019633, 0.019686, 0.019919, 0.019789, 0.01984, 0.020074, 0.019937, 0.019985, 0.020219, 0.020073, 0.020116, 0.020357, 0.020202, 0.020245, 0.020487,
      0.020326, 0.020367, 0.020612, 0.020445, 0.020484, 0.020728, 0.020554, 0.02059, 0.020841, 0.020659, 0.020694, 0.020947 };

    //yield curve
    final LocalDate spotDate = addWorkDays(tradeDate.minusDays(1), 3, DEFAULT_CALENDAR);
    final String[] yieldCurvePoints = new String[] {"1M", "2M", "3M", "6M", "1Y", "2Y", "3Y", "4Y", "5Y", "6Y", "7Y", "8Y", "9Y", "10Y", "12Y", "15Y", "20Y", "25Y", "30Y" };
    final String[] yieldCurveInstruments = new String[] {"M", "M", "M", "M", "M", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S" };
    final double[] rates = new double[] {0.0019, 0.002305, 0.002659, 0.003965, 0.006759, 0.004895, 0.007625, 0.011265, 0.014985, 0.018315, 0.02115, 0.02347, 0.025395, 0.02705, 0.029725, 0.03222,
      0.034165, 0.03506, 0.035535 };
    final ISDACompliantYieldCurve yieldCurve = makeYieldCurve(tradeDate, spotDate, yieldCurvePoints, yieldCurveInstruments, rates, ACT360, D30360, Period.ofMonths(6));

    runGrid(name, tradeDate, maturities, spreads, yieldCurve);
  }

  private void runGrid(final String name, final LocalDate tradeDate, final LocalDate[] maturities, final double[] spreads, final ISDACompliantYieldCurve yieldCurve) {
    final double scale = ONE_BP * NOTIONAL;

    final LocalDate stepinDate = tradeDate.plusDays(1); // AKA stepin date
    final LocalDate cashSettleDate = addWorkDays(tradeDate, 3, DEFAULT_CALENDAR); // AKA valuation date
    final LocalDate startDate = getPrevIMMDate(tradeDate);
    final LocalDate nextIMM = getNextIMMDate(tradeDate);
    final LocalDate[] pillarDates = getIMMDateSet(nextIMM, TENORS);

    final int nPillars = pillarDates.length;
    final double[] pillarSpreads = new double[nPillars];
    final CDSQuoteConvention[] pillarQuotes = new CDSQuoteConvention[nPillars];
    for (int i = 0; i < nPillars; i++) {
      final int index = Arrays.binarySearch(maturities, pillarDates[i]);
      if (index < 0) {
        final int insPoint = -(index + 1);
        pillarSpreads[i] = spreads[insPoint - 1];
      } else {
        pillarSpreads[i] = spreads[index];
      }
      pillarQuotes[i] = new QuotedSpread(COUPON, pillarSpreads[i]); //assume the spread at pillars are quoted spreads 
    }

    final CDSAnalytic[] pillarCDSs_IMM = new CDSAnalytic[nPillars];
    final CDSAnalytic[] pillarCDSs_nonIMM = new CDSAnalytic[nPillars];
    for (int i = 0; i < nPillars; i++) {
      pillarCDSs_IMM[i] = new CDSAnalytic(tradeDate, stepinDate, cashSettleDate, startDate, pillarDates[i], PAY_ACC_ON_DEFAULT, TENOR, STUB, PROCTECTION_START, RECOVERY);
      pillarCDSs_nonIMM[i] = new CDSAnalytic(tradeDate, stepinDate, cashSettleDate, startDate, pillarDates[i], PAY_ACC_ON_DEFAULT, NON_IMM_TENOR, STUB, PROCTECTION_START, RECOVERY);
    }

    final int nMat = maturities.length;
    final double[] puf = new double[nMat];
    final double[] upfrontAmount = new double[nMat];
    final double[] parellelCS01 = new double[nMat];
    final double[][] bucketedCS01 = new double[nMat][];

    for (int i = 0; i < nMat; i++) {
      if (isIMMDate(maturities[i])) {
        final CDSAnalytic pricingCDS = new CDSAnalytic(tradeDate, stepinDate, cashSettleDate, startDate, maturities[i], PAY_ACC_ON_DEFAULT, TENOR, STUB, PROCTECTION_START, RECOVERY);
        final QuotedSpread quote = new QuotedSpread(COUPON, spreads[i]);
        puf[i] = PUF_CONVERTER.toPointsUpFront(pricingCDS, quote, yieldCurve).getPointsUpFront();
        upfrontAmount[i] = (puf[i] - pricingCDS.getAccruedPremium(COUPON)) * NOTIONAL;
        parellelCS01[i] = CS01_CAL.parallelCS01(pricingCDS, quote, yieldCurve, ONE_BP);
        bucketedCS01[i] = CS01_CAL.bucketedCS01FromPillarQuotes(pricingCDS, COUPON, yieldCurve, pillarCDSs_IMM, pillarQuotes, ONE_BP);

      } else {
        final CDSAnalytic pricingCDS = new CDSAnalytic(tradeDate, stepinDate, cashSettleDate, startDate, maturities[i], PAY_ACC_ON_DEFAULT, NON_IMM_TENOR, STUB, PROCTECTION_START, RECOVERY);
        puf[i] = 0.0; //these are assumed to be at par 
        upfrontAmount[i] = (puf[i] - pricingCDS.getAccruedPremium(spreads[i])) * NOTIONAL;
        parellelCS01[i] = CS01_CAL.parallelCS01FromParSpreads(pricingCDS, spreads[i], yieldCurve, pillarCDSs_nonIMM, pillarSpreads, ONE_BP, BumpType.ADDITIVE);
        bucketedCS01[i] = CS01_CAL.bucketedCS01FromParSpreads(pricingCDS, spreads[i], yieldCurve, pillarCDSs_nonIMM, pillarSpreads, ONE_BP, BumpType.ADDITIVE);
      }
    }

    output(name, maturities, TENORS, puf, upfrontAmount, bucketedCS01, parellelCS01, scale);
  }

  private static LocalDate[] getAllMaturities(final LocalDate start, final LocalDate end) {
    ArgumentChecker.notNull(start, "start");
    ArgumentChecker.notNull(end, "end");
    ArgumentChecker.isTrue(end.isAfter(start), "need end after start");
    final int day = start.getDayOfMonth();
    ArgumentChecker.isTrue(day == end.getDayOfMonth(), "start and end must be on same day");
    final int n = (end.getYear() - start.getYear()) * 12 + end.getMonthValue() - start.getMonthValue() + 1;
    final LocalDate[] res = new LocalDate[n];
    res[0] = start;
    for (int i = 1; i < n; i++) {
      res[i] = res[i - 1].plusMonths(1);
    }
    ArgumentChecker.isTrue(res[n - 1].isEqual(end), "error");
    return res;
  }

  private void output(final String name, final LocalDate[] maturities, final Period[] pillars, final double[] puf, final double[] upfrontAmount, final double[][] bCS01, final double[] pCS01,
      final double scale) {
    ArgumentChecker.notNull(name, "name");
    ArgumentChecker.noNulls(maturities, "maturities");
    ArgumentChecker.noNulls(pillars, "pillars");
    ArgumentChecker.notEmpty(puf, "puf");
    ArgumentChecker.notEmpty(upfrontAmount, "upfrontAmount");
    ArgumentChecker.noNulls(bCS01, "bCS01");
    ArgumentChecker.notEmpty(pCS01, "pCS01");
    final int rows = maturities.length;
    ArgumentChecker.isTrue(rows == bCS01.length, "bCS01 length wrong");
    ArgumentChecker.isTrue(rows == pCS01.length, "pCS01 length wrong");
    ArgumentChecker.isTrue(rows == puf.length, "puf length wrong");
    ArgumentChecker.isTrue(rows == upfrontAmount.length, "upfrontAmount length wrong");
    final int columns = pillars.length;
    ArgumentChecker.isTrue(columns == bCS01[0].length, "bCS01 width wrong");

    System.out.println(name);
    System.out.print("Maturity");
    for (int j = 0; j < columns; j++) {
      System.out.print("\t" + pillars[j]);
    }
    System.out.print("\t\tSum\tParallel\tPUF\tUpFront Amount\n");

    for (int i = 0; i < rows; i++) {
      System.out.print(maturities[i]);
      double sum = 0.0;
      for (int j = 0; j < columns; j++) {
        final double temp = bCS01[i][j] * scale;
        sum += temp;
        System.out.print("\t" + temp);
      }
      System.out.print("\t\t" + sum + "\t" + scale * pCS01[i] + "\t" + ONE_HUNDRED * puf[i] + "\t" + upfrontAmount[i] + "\n");
    }
    System.out.print("\n");
  }
}
