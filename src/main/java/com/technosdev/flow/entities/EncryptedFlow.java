package com.technosdev.flow.entities;

public class EncryptedFlow {
    private String encrypted_flow_data;
    private String encrypted_aes_key;
    private String initial_vector;

    public EncryptedFlow() {
    }

    public String getEncrypted_flow_data() {
        return encrypted_flow_data;
    }

    public void setEncrypted_flow_data(String encrypted_flow_data) {
        this.encrypted_flow_data = encrypted_flow_data;
    }

    public String getEncrypted_aes_key() {
        return encrypted_aes_key;
    }

    public void setEncrypted_aes_key(String encrypted_aes_key) {
        this.encrypted_aes_key = encrypted_aes_key;
    }

    public String getInitial_vector() {
        return initial_vector;
    }

    public void setInitial_vector(String initial_vector) {
        this.initial_vector = initial_vector;
    }

    @Override
    public String toString() {
        return "EncryptedFlow{" +
                "encrypted_flow_data='" + encrypted_flow_data + '\'' +
                ", encrypted_aes_key='" + encrypted_aes_key + '\'' +
                ", initial_vector='" + initial_vector + '\'' +
                '}';
    }
}
