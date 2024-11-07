package com.technosdev.flow.entities;

public class DecryptionInfo {
    public final String clearPayload;
    public final byte[] clearAesKey;

    public DecryptionInfo(String clearPayload, byte[] clearAesKey) {
        this.clearPayload = clearPayload;
        this.clearAesKey = clearAesKey;
    }
}
