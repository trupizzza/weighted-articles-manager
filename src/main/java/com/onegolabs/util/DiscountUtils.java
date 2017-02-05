package com.onegolabs.util;

import java.math.BigDecimal;

/**
 * @author dmzhg
 */
public class DiscountUtils {

    public static BigDecimal getDiscountMultiplier(int discScheme, boolean morePrices) {

        if (!morePrices) {
            return DiscountSchemes.NO_DISCOUNT;
        }

        switch (discScheme) {
            case DiscountSchemes.SCHEME_5:
            case DiscountSchemes.SCHEME_EBA:
                return DiscountSchemes.DISCOUNT_5;
            case DiscountSchemes.SCHEME_LOREAL_LUX:
                return DiscountSchemes.DISCOUNT_40;
            case DiscountSchemes.SCHEME_30:
                return DiscountSchemes.DISCOUNT_30;
            case DiscountSchemes.SCHEME_20:
                return DiscountSchemes.DISCOUNT_20;
            case DiscountSchemes.SCHEME_10_1:
            case DiscountSchemes.SCHEME_10_2:
                return DiscountSchemes.DISCOUNT_10;
            case DiscountSchemes.SCHEME_15:
                return DiscountSchemes.DISCOUNT_15;
            case DiscountSchemes.SCHEME_MAX25:
            case DiscountSchemes.SCHEME_SPECIAL:
                return DiscountSchemes.DISCOUNT_25;
            default:
                return DiscountSchemes.NO_DISCOUNT;
        }
    }
}
