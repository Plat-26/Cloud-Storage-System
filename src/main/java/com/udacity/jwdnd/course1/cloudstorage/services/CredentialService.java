package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credMapper, EncryptionService encryptionService) {
        this.credMapper = credMapper;
        this.encryptionService = encryptionService;
    }

    private boolean credentialExits(String url) {
        return credMapper.findCredentialByUrl(url) != null;
    }

    public Credential getCredential(String url) {
        Credential credential = credMapper.findCredentialByUrl(url);

        if(credential != null) {
            String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
            return new Credential(credential.getCredentialId(), credential.getUrl(), credential.getUsername(), decryptedPassword);
        }
        return null;
    }

    public List<Credential> getAllCredentials() {
        return credMapper.findAllCredentials();
    }

    public boolean addCredential(Credential credential) {
        if(!credentialExits(credential.getUrl())) {
            String encodedKey = encryptionService.generateKey();
            String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
            int num = credMapper.createCredential(new Credential(null, credential.getUrl(), credential.getUsername(), encodedKey, encryptedPassword, credential.getUserId()));
            return num > 0;
        }
        return false;
    }

    public boolean updateCredential(Credential credential) {
        Credential optionalCred = credMapper.findCredentialById(credential.getCredentialId());

        if(optionalCred != null) {
            if(!optionalCred.getUrl().equals(credential.getUrl())) {
                if(credentialExits(credential.getUrl())) {
                    return false; //Credential exists
                }
            }
            String decryptedPassword = encryptionService.decryptValue(optionalCred.getPassword(), optionalCred.getKey());
            if(!credential.getPassword().equals(decryptedPassword)) {
                String newKey = encryptionService.generateKey();
                String newPassword = encryptionService.encryptValue(credential.getPassword(), newKey);
                credential.setKey(newKey);
                credential.setPassword(newPassword);
            }
            credMapper.updateCredential(credential);
            return true;
        }
        return false;
    }

    public boolean deleteCredential(String url) {
        if(credentialExits(url)) {
            credMapper.deleteCredential(url);
            return true;
        }
        return false; //credential does not exist
    }


}
