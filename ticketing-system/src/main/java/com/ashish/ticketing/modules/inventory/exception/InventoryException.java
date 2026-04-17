package com.ashish.ticketing.modules.inventory.exception;

public class InventoryException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InventoryException(String message) {
        super(message);
    }

    public InventoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
