package com.hcsc.provider.attestation.common;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import org.kie.server.api.marshalling.MarshallingFormat;

public class CommonConstants {

	public static final List<String> PROVIDERS_STATES = Arrays.asList("IL", "MT",
			"NM", "OK", "TX");
	
	public static BigInteger jobExecutionId;
}
