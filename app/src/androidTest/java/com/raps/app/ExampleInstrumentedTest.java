package com.raps.app;

import static org.junit.Assert.assertEquals;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Teste instrumentado, executado no dispositivo/emulador.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        var appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.raps.app", appContext.getPackageName());
    }
}
