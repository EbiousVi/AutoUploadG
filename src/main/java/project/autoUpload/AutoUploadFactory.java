package project.autoUpload;

import project.filesWalker.Directories;
import project.filesWalker.FilesWalkerBig;
import project.filesWalker.FilesWalkerSmall;

import java.util.HashMap;
import java.util.Map;

public class AutoUploadFactory {
    private static final Map<String, AutoUpload> types = new HashMap<>();
    private static final String BIG = "бол";
    private static final String SMALL = "мал";
    private static final String SMALL3 = "мал3";

    public static AutoUpload getAutoUploadInstance(String type, Map<Directories, Boolean> parts) {
        if (type.equals(BIG)) {
            if (types.get(BIG) == null) {
                types.put(BIG, new AutoUploadTriple(new FilesWalkerBig(parts)));
            }
            return types.get(BIG);
        }
        if (type.equals(SMALL)) {
            if (types.get(SMALL) == null) {
                types.put(SMALL, new AutoUploadSimple(new FilesWalkerSmall(parts)));
            }
            return types.get(SMALL);
        }
        if (type.equals(SMALL3)) {
            if (types.get(SMALL3) == null) {
                types.put(SMALL3, new AutoUploadTriple(new FilesWalkerSmall(parts)));
            }
            return types.get(SMALL3);
        }
        throw new RuntimeException("--> Не возможно создать экземпляр Автозагрузки");
    }

}