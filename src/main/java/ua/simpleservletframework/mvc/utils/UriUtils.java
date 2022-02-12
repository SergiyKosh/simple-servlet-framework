package ua.simpleservletframework.mvc.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class UriUtils {
    public static String formatRequestUri(String cUri, String rmUri) {
        if (cUri.equals("/") && !rmUri.equals("/")) {
            cUri = "";
        } else if (rmUri.equals("/")) {
            rmUri = "";
        }
        return cUri + rmUri;
    }

    public static String[] injectPathVariableIfExists(String rUri, String cUri) {
        String[] pathRUri = rUri.split("/");
        String[] pathCUri = cUri.split("/");

        if (pathCUri.length == pathRUri.length) {
            for (int i = 0; i < pathCUri.length; i++) {
                if (pathCUri[i].startsWith("{") && pathCUri[i].endsWith("}")) {
                    pathCUri[i] = pathRUri[i];
                }
            }
        }

        return pathCUri;
    }

    public static String collectUri(String[] uri) {
        String returnedUri;

        for (int i = 0; i < uri.length; i++) {
            uri[i] = "/" + uri[i];
        }

        return String.join("", uri).substring(1);
    }
}
