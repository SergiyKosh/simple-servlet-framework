package ua.simpleservletframework.mvc.utils;

public class UriUtils {
    public static String formatRequestUri(String cUri, String rmUri) {
        if (cUri.equals("/") && !rmUri.equals("/")) {
            cUri = "";
        } else if (rmUri.equals("/")) {
            rmUri = "";
        }
        return cUri + rmUri;
    }
}
