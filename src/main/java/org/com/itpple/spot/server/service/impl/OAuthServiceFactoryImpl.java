package org.com.itpple.spot.server.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.com.itpple.spot.server.constant.OAuthType;
import org.com.itpple.spot.server.service.OAuthService;
import org.com.itpple.spot.server.service.OAuthServiceFactory;
import org.springframework.stereotype.Service;

@Service
public class OAuthServiceFactoryImpl implements OAuthServiceFactory {

    private final Map<OAuthType, OAuthService> oAuthServiceMap = new HashMap<>();

    public OAuthServiceFactoryImpl(List<OAuthService> oAuthServiceList) {
        for (OAuthService oAuthService : oAuthServiceList) {
            oAuthServiceMap.put(oAuthService.getOAuthType(), oAuthService);
        }
    }

    public OAuthService getOAuthService(OAuthType oAuthType) {
        return oAuthServiceMap.get(oAuthType);
    }
}
