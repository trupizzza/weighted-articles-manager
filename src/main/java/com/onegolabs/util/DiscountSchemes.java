package com.onegolabs.util;

import java.math.BigDecimal;

/**
 * @author dmzhg
 */
public class DiscountSchemes {

    // Discount Schemes constants
    public static final int SCHEME_30 = 1;
    public static final int SCHEME_20 = 2;
    public static final int SCHEME_15 = 4;
    public static final int SCHEME_10_1 = 3;
    public static final int SCHEME_10_2 = 9;
    public static final int SCHEME_5 = 5;
    public static final int SCHEME_SPECIAL = 7;
    public static final int SCHEME_LOREAL_LUX = 8;
    public static final int SCHEME_EBA = 11;
    public static final int SCHEME_MAX25 = 12;

    public static final BigDecimal NO_DISCOUNT = new BigDecimal(1);
    public static final BigDecimal DISCOUNT_5 = new BigDecimal(0.950001);
    public static final BigDecimal DISCOUNT_10 = new BigDecimal(0.900001);
    public static final BigDecimal DISCOUNT_15 = new BigDecimal(0.850001);
    public static final BigDecimal DISCOUNT_20 = new BigDecimal(0.800001);
    public static final BigDecimal DISCOUNT_25 = new BigDecimal(0.750001);
    public static final BigDecimal DISCOUNT_30 = new BigDecimal(0.700001);
    public static final BigDecimal DISCOUNT_40 = new BigDecimal(0.600001);
}
