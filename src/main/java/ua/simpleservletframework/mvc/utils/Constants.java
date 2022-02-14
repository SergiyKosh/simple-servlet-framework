package ua.simpleservletframework.mvc.utils;

import java.util.Objects;

public final class Constants {
    private Constants() {
    }

    public static final String IMAGES_LOCATION = Objects.requireNonNull(
            Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("../../resources/images/"),
            "Folder for post images not found"
    ).getPath();
}
