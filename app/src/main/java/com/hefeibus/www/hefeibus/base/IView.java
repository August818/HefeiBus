package com.hefeibus.www.hefeibus.base;

import com.hefeibus.www.hefeibus.sqlite.HistoryDatabase;

/**
 * Base View interface in MVP structure.
 * Always be empty interface.
 */
public interface IView {
    HistoryDatabase getHistoryHandler();
}
