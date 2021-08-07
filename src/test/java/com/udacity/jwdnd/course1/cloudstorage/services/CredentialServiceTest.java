package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@MybatisTest
@ExtendWith(MockitoExtension.class)
class CredentialServiceTest {

    @Autowired
    private CredentialMapper credMapper;

    @Autowired
    private UserMapper userMapper;

    @Mock
    private EncryptionService mock_encryptionService;

    @InjectMocks
    CredentialService credentialService;

    private static User testUser;
    private Credential credential;

    @BeforeAll
    static void beforeAll() {
        testUser = new User(null, "johnnydoe", "someSalt", "password", "John", "Doe");
    }

    @BeforeEach
    void setUp() {
        credentialService.setCredMapper(credMapper);
        userMapper.createUser(testUser);
        credential = new Credential(null, "someurl.com", "username", "some-key", "some-password", 1);
        credMapper.createCredential(credential);
    }

    @Test
    void test_updateCredential_reset_fields() {
        //old credential

        //test with new credentials
        Credential updatedCredential = new Credential(1, "a-new-url.com", "new-username", null, "new-password", testUser.getUserId());
        credentialService.updateCredential(updatedCredential);

        String newKey = verify(mock_encryptionService).generateKey();
        verify(mock_encryptionService).encryptValue(updatedCredential.getPassword(), newKey);

        Credential underTest = credMapper.findCredentialById(1);
        assertThat(underTest.getUsername()).isEqualTo(updatedCredential.getUsername());
        assertThat(underTest.getUrl()).isEqualTo(updatedCredential.getUrl());
    }


    @Test
    void test_updateCredential_same_password() {
        //old credential
        when(mock_encryptionService.decryptValue(credential.getPassword(), credential.getKey())).thenReturn(credential.getPassword());

        //test with same credential
        Credential updatedCredential = new Credential(1, "someurl.com", "username", null, "some-password", testUser.getUserId());
        credentialService.updateCredential(updatedCredential);

        String newKey = verify(mock_encryptionService, atMost(0)).generateKey();
        verify(mock_encryptionService, atMost(0)).encryptValue(updatedCredential.getPassword(), newKey);

        Credential underTest = credMapper.findCredentialById(1);
        assertThat(underTest.getUsername()).isEqualTo(credential.getUsername());
        assertThat(underTest.getUrl()).isEqualTo(credential.getUrl());
    }

}