package ua.simpleservletframework.core.util;

public class Utils {
    public static String requestUri(String cUri, String rmUri) {
        if (cUri.equals("/") && !rmUri.equals("/")) {
            cUri = "";
        } else if (rmUri.equals("/")) {
            rmUri = "";
        }
        return cUri + rmUri;
    }
}
