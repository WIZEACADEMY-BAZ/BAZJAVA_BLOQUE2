package com.wizeline.learningspring.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wizeline.learningspring.model.herencia.UserBreaches;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserBreachesServiceImplTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserBreachesServiceImplTest.class);
    @Mock
    UserBreachesService service;
    @Mock
    RestTemplate restTemplate;

    @Test
    void breaches() throws JsonProcessingException {
        LOGGER.info("getUserBreachesTest");
        String json = "{\"Name\":\"000webhost\",\"Title\":\"000webhost\",\"Domain\":\"000webhost.com\",\"BreachDate\":\"2015-03-01\",\"AddedDate\":\"2015-10-26T23:35:45Z\",\"ModifiedDate\":\"2017-12-10T21:44:27Z\",\"PwnCount\":14936670,\"Description\":\"In approximately March 2015, the free web hosting provider <a href=\\\"http://www.troyhunt.com/2015/10/breaches-traders-plain-text-passwords.html\\\" target=\\\"_blank\\\" rel=\\\"noopener\\\">000webhost suffered a major data breach</a> that exposed almost 15 million customer records. The data was sold and traded before 000webhost was alerted in October. The breach included names, email addresses and plain text passwords.\",\"LogoPath\":\"https://haveibeenpwned.com/Content/Images/PwnedLogos/000webhost.png\",\"DataClasses\":[\"Email addresses\",\"IP addresses\",\"Names\",\"Passwords\"],\"IsVerified\":true,\"IsFabricated\":false,\"IsSensitive\":false,\"IsRetired\":false,\"IsSpamList\":false,\"IsMalware\":false}";
        ResponseEntity<String> response = new ResponseEntity<>(json, HttpStatus.OK);
        lenient().when(restTemplate.exchange("https://jsonplaceholder.typicode.com/users", HttpMethod.GET, null, String.class)).thenReturn(response);

        assertAll(
                () -> assertNotNull(service.breaches()),
                () -> assertNotNull(response)
        );
    }
}