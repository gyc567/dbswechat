package com.dbs.util;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * Created by eric567 [email:gyc567@126.com]
 * on 11/16/2016.
 */
public final class IdentifierGenerator {
    private SecureRandom random = new SecureRandom();

    public static int getNextId() {
        int id = UUID.randomUUID().hashCode();
        return id;
    }
}
