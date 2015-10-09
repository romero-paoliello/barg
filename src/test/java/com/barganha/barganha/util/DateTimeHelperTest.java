package com.barganha.barganha.util;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

public class DateTimeHelperTest {

    @Test
    public void shouldReturnDateTime() {
        DateTime now = DateTimeHelper.getCurrentDateTime();

        Assert.assertNotNull(now);
    }

    //@Test
    public void shouldReturnDateTimeAsString() {
        String now = DateTimeHelper.getCurrentDateTimeAsString();
        DateTime dateTime = DateTimeHelper.toDateTime(now);

        Assert.assertNotNull(now);
        Assert.assertNotNull(dateTime);
    }
}
