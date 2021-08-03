package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.hibernate.validator.cfg.defs.CreditCardNumberDef;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
class CredentialMapperTest {

    @Autowired
    private CredentialMapper credentialMapper;

    @Autowired
    private UserMapper userMapper;

    private static User testUser;

    private Credential testCredential;

    @BeforeAll
    static void beforeAll() {
        testUser = new User(null, "johnnydoe", "someSalt", "password", "John", "Doe");
    }

    @BeforeEach
    void setUp() {
        userMapper.createUser(testUser);
        //retrieve user from database to populate userId
        testUser = userMapper.getUser(testUser.getUsername());

        //create credential and populate database
        testCredential = new Credential(null, "purplepeople.com", "johnnydoe@email.com", "secret-key", "secret-password", testUser.getUserId() );
        credentialMapper.createCredential(testCredential);
    }

    @Test
    void findCredentialById() {
        Credential credentialById = credentialMapper.findCredentialById(1);

        assertThat(credentialById).isNotNull();
        assertThat(credentialById.getUserId()).isEqualTo(testUser.getUserId());
        assertThat(credentialById.getUrl()).isEqualTo(testCredential.getUrl());
        assertThat(credentialById.getPassword()).isEqualTo(testCredential.getPassword());
    }

    @Test
    void findCredentialByUrl() {
        Credential credentialByUrl = credentialMapper.findCredentialByUrl(testCredential.getUrl());

        assertThat(credentialByUrl).isNotNull();
        assertThat(credentialByUrl.getUserId()).isEqualTo(testUser.getUserId());
        assertThat(credentialByUrl.getUsername()).isEqualTo(testCredential.getUsername());
        assertThat(credentialByUrl.getKey()).isEqualTo(testCredential.getKey());
    }

    @Test
    void createCredential() {
        int created = credentialMapper.createCredential(new Credential(null, "carsforfun.com", "johnny", "secret-key", "password", testUser.getUserId()));
        assertThat(created).isGreaterThan(0);
    }

    @Test
    void updateCredential() {
       Credential newCred = new Credential(1, "purplepeople.com", "johnny", "super-secret-key", "password", testUser.getUserId());
        int updated = credentialMapper.updateCredential(newCred);
        assertThat(updated).isGreaterThan(0);

        Credential credentialById = credentialMapper.findCredentialById(1);
        assertThat(credentialById.getUsername()).isEqualTo(newCred.getUsername());
        assertThat(credentialById.getPassword()).isEqualTo(newCred.getPassword());
    }

    @Test
    void deleteCredentialById() {
        int deleted = credentialMapper.deleteCredentialById(1);
        assertThat(deleted).isGreaterThan(0);
    }

    @Test
    void findAllCredentials() {
        List<Credential> allCredentials = credentialMapper.findAllCredentials();
        assertThat(allCredentials).isNotEmpty();

        Credential credential = allCredentials.get(0);
        assertThat(credential).isNotNull();
        assertThat(credential.getUsername()).isEqualTo(testCredential.getUsername());
        assertThat(credential.getKey()).isEqualTo(testCredential.getKey());
        assertThat(credential.getUsername()).isEqualTo(testCredential.getUsername());
    }

    @Test
    void findCredentialByUser() {

        List<Credential> credentialsByUser = credentialMapper.findCredentialByUser(testUser.getUserId());
        assertThat(credentialsByUser).isNotEmpty();

        Credential credential = credentialsByUser.get(0);
        assertThat(credential).isNotNull();
        assertThat(credential.getUsername()).isEqualTo(testCredential.getUsername());
        assertThat(credential.getUserId()).isEqualTo(testCredential.getUserId());
    }
}