package microavatar.framework.core.pk;

import java.util.UUID;

public class PkGenerator {

    public static String getPk() {
        return UUID.randomUUID().toString();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(PkGenerator.getPk());
        }
    }
}
