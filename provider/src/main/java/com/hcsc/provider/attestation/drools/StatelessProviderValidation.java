package com.hcsc.provider.attestation.drools;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import com.hcsc.provider.attestation.model.Provider;

import java.util.List;

public class StatelessProviderValidation {

        public static Provider execute(Provider provider) {

           // List<Provider> provider = ApplicationRepository.getProvider();
            KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
            StatelessKieSession kieSession = kieContainer.newStatelessKieSession("StatelessProviderValidation");
            System.out.println("==== DROOLS SESSION START ==== ");
            kieSession.execute(provider);
            System.out.println("==== DROOLS SESSION END ==== ");
            System.out.println("==== PROVIDER AFTER DROOLS SESSION ==== ");

            //provider.forEach(provider1 -> System.out.println(provider1.getProviderId() + " validation " + provider1.getStatus()));
            return provider;

    }

}
