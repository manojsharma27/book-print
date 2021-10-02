package com.ms.printing.integration;

import com.ms.printing.bookprint.Application;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(
        classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:applicationIT.properties")
@RunWith(SpringRunner.class)
public abstract class BookPrintITBase {

    @LocalServerPort
    private int port = 0;

    protected TestRestTemplate restTemplate;

    protected void setupBase() {
        restTemplate = template();
    }

    protected TestRestTemplate template() {
        return new TestRestTemplate();
    }

    protected String url(String partialUrl) {
        return "http://localhost:" + this.port + partialUrl;
    }

    protected int getServerPort() {
        return this.port;
    }


}
