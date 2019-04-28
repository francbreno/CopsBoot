package com.breno.copsboot.infrastructure.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component // to autowire later on
@ConfigurationProperties(prefix = "copsboot-security")
public class SecurityConfiguration {

	private String mobileAppClientId;
	private String mobileAppClientSecret;

	public String getMobileAppClientId() {
		return mobileAppClientId;
	}

	public void setMobileAppClientId(String mobileAppClientId) {
		this.mobileAppClientId = mobileAppClientId;
	}

	public String getMobileAppClientSecret() {
		return mobileAppClientSecret;
	}

	public void setMobileAppClientSecret(String mobileAppClientSecret) {
		this.mobileAppClientSecret = mobileAppClientSecret;
	}
}
