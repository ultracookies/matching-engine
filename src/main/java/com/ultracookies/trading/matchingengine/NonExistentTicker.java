package com.ultracookies.trading.matchingengine;

public class NonExistentTicker extends RuntimeException {

    public NonExistentTicker(String msg) {
        super(msg);
    }
}
