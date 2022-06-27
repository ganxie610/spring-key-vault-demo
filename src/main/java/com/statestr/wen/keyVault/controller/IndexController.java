package com.statestr.wen.keyVault.controller;

//import com.azure.storage.blob.BlobClient;
//import com.azure.storage.blob.BlobContainerClient;
//import com.azure.storage.blob.BlobServiceClient;
//import com.azure.storage.blob.BlobServiceClientBuilder;
//import com.azure.storage.blob.implementation.ContainersImpl;
//import com.azure.storage.blob.models.BlobItem;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;

import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class IndexController {

    @Resource
    private Environment env;

    @Autowired
    private SecretClient secretClient;

//    @Value("azure-blob://event-sent/user1.txt")
//    private Resource blobFile;


    @GetMapping("/get/{secretName}")
    public String index(@PathVariable("secretName") String secretName){
//        String secret = env.getProperty(secretName);
        KeyVaultSecret secret = secretClient.getSecret(secretName);
        return secret.getValue();
    }

//    @GetMapping("/blob")
//    public String getBlob(){
//        SecretClient keyClient = new SecretClientBuilder()
//                .vaultUrl("https://spring-azure-demo.vault.azure.net/")
//                .credential(new DefaultAzureCredentialBuilder().build())
//                .buildClient();
//        secret1 = keyClient.getSecret("secret1");
////        secret1.getValue();
//        return secret1.getValue();
//    }



//    @GetMapping("/readBlobFile")
//    public String readBlobFile() throws IOException {
//        return StreamUtils.copyToString(
//                this.blobFile.getInputStream(),
//                Charset.defaultCharset());
//    }
//
//    @PostMapping("/writeBlobFile")
//    public String writeBlobFile(@RequestBody String data) throws IOException {
//        try (OutputStream os = ((WritableResource) this.blobFile).getOutputStream()) {
//            os.write(data.getBytes());
//        }
//        return "file was updated";
//    }

}
