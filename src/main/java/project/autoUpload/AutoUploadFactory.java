package project.autoUpload;

import project.filesWalker.FilesWalkerBig;
import project.filesWalker.FilesWalkerSmall;

import java.util.HashMap;
import java.util.Map;

public class AutoUploadFactory {
    private static final Map<String, AutoUpload> types = new HashMap<>();
    private static final String BIG = "бол";
    private static final String SMALL = "мал";
    private static final String SMALL3 = "мал3";

    public static AutoUpload getAutoUploadInstance(String type) {
        if (types.isEmpty()) {
            types.put(BIG, new AutoUploadTriple(new FilesWalkerBig()));
            types.put(SMALL, new AutoUploadSimple(new FilesWalkerSmall()));
            types.put(SMALL3, new AutoUploadTriple(new FilesWalkerSmall()));
        }

        if (types.containsKey(type.toLowerCase())) {
            return types.get(type);
        }
        throw new RuntimeException("NOT POSSIBLE TO COME HERE");
    }

}