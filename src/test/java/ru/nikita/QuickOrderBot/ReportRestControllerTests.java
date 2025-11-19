package ru.nikita.QuickOrderBot;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ReportRestControllerTests {

    static String sessionCookie;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;

        Response loginResponse = given()
                .contentType(ContentType.URLENC)
                .formParam("username", "admin@example.com")
                .formParam("password", "admin")
                .when()
                .post("/login")
                .then()
                .statusCode(anyOf(equalTo(HttpStatus.FOUND.value()), equalTo(HttpStatus.OK.value())))
                .extract().response();

        sessionCookie = loginResponse.getCookie("JSESSIONID");
    }

    @Test
    @DisplayName("Успешное создание и генерация отчёта")
    void createAndGenerateReport_shouldReturnOkAndReportId() {
        given()
                .contentType(ContentType.JSON)
                .cookie("JSESSIONID", sessionCookie)
                .when()
                .post("/api/reports")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(notNullValue());
    }

    @Test
    @DisplayName("Запрос несуществующего отчета должен возвращать 404 и текст ошибки")
    void getNotFoundReportContent_shouldReturn404() {
        long notExistingId = 999999;
        given()
                .contentType(ContentType.JSON)
                .cookie("JSESSIONID", sessionCookie)
                .when()
                .get("/api/reports/{id}/content", notExistingId)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body(equalTo("Отчет не найден"));
    }

    @Test
    @DisplayName("Успешно сформированный отчет возвращает html и статус 200")
    void getReadyReportContent_shouldReturnHtml() {
        long reportId = given()
                .contentType(ContentType.JSON)
                .cookie("JSESSIONID", sessionCookie)
                .when().post("/api/reports")
                .then().extract().as(Long.class);

        given()
                .contentType(ContentType.JSON)
                .cookie("JSESSIONID", sessionCookie)
                .when()
                .get("/api/reports/{id}/content", reportId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .header("Content-Type", containsString("text/html"))
                .body(not(isEmptyString()));
    }
}