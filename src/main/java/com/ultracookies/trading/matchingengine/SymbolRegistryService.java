package com.ultracookies.trading.matchingengine;

import org.springframework.stereotype.Service;

@Service
public class SymbolRegistryService {

    public boolean symbolExists(String symbol) {
        return symbol.equals("AAPL");
    }
}
