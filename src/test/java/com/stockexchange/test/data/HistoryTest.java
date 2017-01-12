package com.stockexchange.test.data;

import com.stockexchange.util.History;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class HistoryTest {
    /**
     * DOCUMENT ME!
     */
    @Test(priority = 1)
    public void historyAdd() {
        History<Integer> history = new History<Integer>(80);

        for (int i = 0; i < 1000; i++) {
            history.add(i);
        }

        int j = 920;

        for (Integer i : history.getAll()) {
            assertTrue(j == i);
            j++;
        }
    }
}
