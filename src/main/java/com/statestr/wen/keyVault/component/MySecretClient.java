package com.statestr.wen.keyVault.component;

import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MySecretClient {

    @Value("${spring.cloud.azure.keyvault.secret.property-sources[0].endpoint}")
    private String keyVaultUrl;
    @Value("${spring.cloud.azure.keyvault.secret.property-sources[0].profile.tenant-id}")
    private String tenantId;
    @Value("${spring.cloud.azure.keyvault.secret.property-sources[0].credential.client-id}")
    private String clientId;
    @Value("${spring.cloud.azure.keyvault.secret.property-sources[0].credential.client-secret}")
    private String clientSecret;

    @Bean
   public SecretClient secretClient(){
       return new SecretClientBuilder()
               .vaultUrl(keyVaultUrl)
               .credential(new ClientSecretCredentialBuilder()
                       .clientSecret(clientSecret)
                       .clientId(clientId)
                       .tenantId(tenantId)
                       .build())
               .buildClient();
   }

}
