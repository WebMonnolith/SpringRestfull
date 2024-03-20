package org.restframework.web.core;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public interface RestApp<T extends RestApp<T>> {
    T run();
    T run(String[] args);
}
