package ua.simpleservletframework.mvc.utils;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

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
        for (int i = 0; i < uri.length; i++) {
            uri[i] = "/" + uri[i];
        }

        return String.join("", uri).substring(1);
    }

    public static String collectUri(AtomicReference<AtomicReferenceArray<String>> uri) {
        for (int i = 0; i < uri.get().length(); i++) {
            uri.get().set(i, "/" + uri.get().get(i));
        }

        return String.join("", uri.get().toString())
                .replaceAll("\\,", "")
                .replaceAll("\\]", "")
                .replaceAll(" ", "")
                .substring(2);
    }
}
