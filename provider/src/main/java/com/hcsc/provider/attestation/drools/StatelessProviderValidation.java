package com.hcsc.provider.attestation.drools;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;

import com.hcsc.provider.attestation.model.Provider;

public class StatelessProviderValidation {

        public static Provider execute(Provider provider) {

           // List<Provider> provider = ApplicationRepository.getProvider();
            KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
            StatelessKieSession kieSession = kieContainer.newStatelessKieSession("StatelessProviderValidation");
            kieSession.execute(provider);

            //provider.forEach(provider1 -> System.out.println(provider1.getProviderId() + " validation " + provider1.getStatus()));
            return provider;

    }

}
