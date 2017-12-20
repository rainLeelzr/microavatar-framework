package microavatar.framework.core.support.sequence.impl;

import microavatar.framework.core.support.sequence.Sequence;

import java.util.UUID;

/**
 * @author rain
 */
public class UuidSequence implements Sequence<String> {

    @Override
    public String next() {
        return get();
    }

    public static String get() {
        return UUID.randomUUID().toString();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(UuidSequence.get());
        }
    }
}
