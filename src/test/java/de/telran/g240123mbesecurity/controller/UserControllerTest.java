package de.telran.g240123mbesecurity.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @LocalServerPort
    private int port;

    private TestRestTemplate template = new TestRestTemplate();

    private HttpHeaders headers = new HttpHeaders();

    private String localhost = "http://localhost:";

    private String getUser = "/user/name/Vanya";

    private String addUser = "/user/add";

    // 1. Проверка, что возвращает сервер, если мы выполняем действие,
    // которое требует аутентификации, но при этом не передаём логин и пароль.
    // Ожидание - сервер должен вернуть статус "UNAUTHORIZED".

    @Test
    public void getUserByNameWithoutAuthentication() {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        String url = localhost + port + getUser;

        ResponseEntity<String> response = template
                .exchange(url, HttpMethod.GET, entity, String.class);

        HttpStatusCode expected = HttpStatus.UNAUTHORIZED;
        HttpStatusCode actual = response.getStatusCode();

        assertEquals(expected, actual);
        assertNull(response.getBody());
    }

    // 2. Проверим корректную аутентификацию, будем передавать логин и пароль.
    // Ожидание - сервер должен вернуть нам данные из БД.

    @Test
    public void getUserByNameWithAuthentication() {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        String url = localhost + port + getUser;

        ResponseEntity<String> response = template
                .withBasicAuth("Vanya", "qwe")
                .exchange(url, HttpMethod.GET, entity, String.class);

        HttpStatusCode expected = HttpStatus.OK;
        HttpStatusCode actual = response.getStatusCode();

        String expectedBody = "{" +
                "\"id\":1," +
                "\"username\":\"Vanya\"," +
                "\"password\":\"$2a$10$txhLF6D3sTxn6hmkgEXY9uQVnBWSuD9Fn75wCBhPIWWZY/JIVaO5i\"," +
                "\"authorities\":[" +
                "{" +
                "\"id\":1," +
                "\"name\":\"ROLE_ADMIN\"," +
                "\"authority\":\"ROLE_ADMIN\"" +
                "}" +
                "]" +
                "}";

        String actualBody = response.getBody();

        assertEquals(expected, actual);
        assertEquals(expectedBody, actualBody);
    }

    // 3. Проверим, что возвращает сервер, если мы корректно авторизуемся,
    // но не имеем прав на запрашиваемое действие.
    // Ожидание - сервер должен вернуть статус "FORBIDDEN",
    // то есть сервер распознаёт нас как авторизованного пользователя,
    // но не имеющего прав на данное действие.

    @Test
    public void addUserWithoutRequiredRole() {

        String body = "{" +
                "\"username\":\"Vasya\"," +
                "\"password\":\"qwe\"" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        String url = localhost + port + addUser;

        ResponseEntity<String> response = template
                .withBasicAuth("Petya", "qwe")
                .exchange(url, HttpMethod.POST, entity, String.class);

        HttpStatusCode expected = HttpStatus.FORBIDDEN;
        HttpStatusCode actual = response.getStatusCode();

        assertEquals(expected, actual);
    }
}