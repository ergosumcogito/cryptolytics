package com.backendproject.cryptolytics.domain.port.in;

import java.math.BigDecimal;

public interface CryptoQueryPort {
     BigDecimal getIndicatorDataForCrypto(String currency, String indicator);
}
