package com.onegolabs.test;

import com.onegolabs.exception.SystemCode;
import com.onegolabs.resources.Messages;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author dmzhg
 */
public class MessagesTest {

    public static final String TEST_WORD_RU = "\u0442\u0435\u0441\u0442";
    public static final String TEST_ERROR_CODE_RU = "\u0442\u0435\u0441\u0442\u043e\u0432\u044b\u0439" + " \u043a\u043e\u0434 \u043e\u0448\u0438\u0431\u043a\u0438";

    @Test
    public void shouldGetMessage() {
        assertEquals(Messages.getString("test"), TEST_WORD_RU);
    }

    @Test
    public void shouldGetExceptionTextByStringKey() {
        assertEquals(Messages.getExceptionText("test"), TEST_WORD_RU);
    }

    @Test
    public void shouldGetExceptionTextByErrorCode() {
        assertEquals(Messages.getExceptionText(SystemCode.TEST), TEST_ERROR_CODE_RU);
    }
}
