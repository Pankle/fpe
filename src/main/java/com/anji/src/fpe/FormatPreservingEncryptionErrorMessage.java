package com.anji.src.fpe;

enum FormatPreservingEncryptionErrorMessage {
    INVALID_SIZE("invalid size, out of range: "),
    TWEAK_INVALID_SIZE("Tweak length is greater than  "),
    NULL_INPUT("not valid null as input"),
    NULL_ENCRYPT_OUTPUT("encrypt data is null"),
    INVALID_SIZE_ENCRYPT("invalid size,encrypt out of size:%d," +
            "Original Data of size:%d,encrypt out is:%s,Original Date is:%s"),
    NULL_DECRYPT_OUTPUT("decrypt data is null"),
    INVALID_SIZE_DECRYPT("invalid size, decrypt out of range:%d," +
            "Original Data of size:%d");

    private final String message;

    FormatPreservingEncryptionErrorMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
