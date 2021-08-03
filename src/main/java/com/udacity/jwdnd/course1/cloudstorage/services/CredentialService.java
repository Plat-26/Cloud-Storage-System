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

    private boolean credentialExits(int credentialId) {
        return credMapper.findCredentialById(credentialId) != null;
    }

    public Credential getCredential(int credentialId) {
        Credential credential = credMapper.findCredentialById(credentialId);

        if(credential != null) {
            String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
            credential.setPassword(decryptedPassword);
            return credential;
        }
        return null;
    }

    public List<Credential> getAllCredentials() {
        return credMapper.findAllCredentials();
    }

    public List<Credential> getCredentialsByUser(int userId) {
        List<Credential> credentials = credMapper.findCredentialByUser(userId);
        if (!credentials.isEmpty()) {
            for(Credential credential : credentials) {
                String decrypted = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
                credential.setDecrypted(decrypted);
            }
        }
        return credentials;
    }

    public boolean addCredential(Credential credential) {
        String encodedKey = encryptionService.generateKey();
        String encryptedPassword = encryptionService.encryptValue(credential.getDecrypted(), encodedKey);
        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);
        int created = credMapper.createCredential(credential);
        return created > 0;
    }

    public boolean updateCredential(Credential credential) {
        Credential optionalCred = credMapper.findCredentialById(credential.getCredentialId());

        if(optionalCred != null) {
            String decryptedPassword = encryptionService.decryptValue(optionalCred.getPassword(), optionalCred.getKey());
            if(!credential.getDecrypted().equals(decryptedPassword)) {
                String newKey = encryptionService.generateKey();
                String newPassword = encryptionService.encryptValue(credential.getDecrypted(), newKey);
                credential.setKey(newKey);
                credential.setPassword(newPassword);
            }

            return credMapper.updateCredential(credential) > 0;
        }
        return false;
    }

    public boolean deleteCredential(int credentialId) {
        if(credentialExits(credentialId)) {
            return credMapper.deleteCredentialById(credentialId) > 0;
        }
        return false; //credential does not exist
    }
}
