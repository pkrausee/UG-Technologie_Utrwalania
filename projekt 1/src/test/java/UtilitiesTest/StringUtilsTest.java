package UtilitiesTest;

import org.junit.Test;
import utilities.StringUtils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringUtilsTest {
    @Test
    public void GivenIsNullOrEmpty_WhenGivenNull_ReturnsTrue() {
        assertTrue(StringUtils.isNullOrEmpty(null));
    }

    @Test
    public void GivenIsNullOrEmpty_WhenGivenEmptyString_ReturnsTrue() {
        assertTrue(StringUtils.isNullOrEmpty(""));
    }

    @Test
    public void GivenIsNullOrEmpty_WhenGivenStringWithSpaces_ReturnsTrue() {
        assertTrue(StringUtils.isNullOrEmpty("   "));
    }

    @Test
    public void GivenIsNullOrEmpty_WhenGivenNotEmptyString_ReturnsFalse() {
        assertFalse(StringUtils.isNullOrEmpty("mock"));
    }
}
