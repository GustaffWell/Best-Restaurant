package com.gustaff_well.best_restaurant.util.exception;

import lombok.NonNull;

public class AppException extends RuntimeException {

    public AppException(@NonNull String message) {
        super(message);
    }
}
