package dev.sh1on.amlethmp.common.shared.exception;

import java.io.IOException;

public class NetworkRemoteException extends RuntimeException {
    public NetworkRemoteException(String message) {
        super(message);
    }
}
