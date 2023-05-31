package com.revature.ecommerce_cli;
import com.revature.ecommerce_cli.util.PriceUtil;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PriceUtilTest {

    @Test
    public void toCentsTest() {
        assertEquals(0, PriceUtil.toCents("0000"));
        assertEquals(0, PriceUtil.toCents(".0"));
        assertEquals(100, PriceUtil.toCents("1.0"));
        assertEquals(105, PriceUtil.toCents("1.05"));
        assertEquals(98820, PriceUtil.toCents("988.2"));
        assertEquals(100500, PriceUtil.toCents("1005"));
        assertEquals(100400, PriceUtil.toCents("1004."));
        assertEquals(4, PriceUtil.toCents(".04"));
        assertEquals(0, PriceUtil.toCents("0.00008"));
        assertEquals(14, PriceUtil.toCents(".14"));
        assertEquals(14, PriceUtil.toCents(".1409"));
        assertEquals(10, PriceUtil.toCents(".1009"));
    }
}
