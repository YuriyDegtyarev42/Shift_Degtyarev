package autotests.clients;

import autotests.EndpointConfig;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.actions.ExecuteSQLAction.Builder.sql;
import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;
import static com.consol.citrus.container.FinallySequence.Builder.doFinally;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckControllerHelper extends TestNGCitrusSpringSupport {

    @Autowired
    public HttpClient yellowDuckService;

    @Autowired
    protected SingleConnectionDataSource db;

    @Step("Создание утки")
    public void createDuck(TestCaseRunner runner, Object bodyRequest) {
        runner.$(http().client(yellowDuckService)
                .send()
                .post("/api/duck/create")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(bodyRequest, new ObjectMapper()))
        );
    }

    @Step("Получение свойств утки")
    public void getDuckProperties(TestCaseRunner runner, String id) {
        runner.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/properties")
                .queryParam("id", id)
        );
    }

    @Step("Обновление утки")
    public void updateDuck(TestCaseRunner runner, String color, double height, String id, String material, String sound, WingsState wingsState) {
        runner.$(http().client(yellowDuckService)
                .send()
                .put("/api/duck/update")
                .queryParam("color", color)
                .queryParam("height", String.valueOf(height))
                .queryParam("id", id)
                .queryParam("material", material)
                .queryParam("sound", sound)
                .queryParam("wingsState", wingsState.toString())
        );
    }

    @Step("Удаление утки")
    public void duckDelete(TestCaseRunner runner, String id) {
        runner.$(http().client(yellowDuckService)
                .send()
                .delete("/api/duck/delete")
                .queryParam("id", id)
        );
    }

    @Step("Полет утки")
    public void fly(TestCaseRunner runner, String id) {
        runner.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/fly")
                .queryParam("id", id)
        );
    }

    @Step("Кряканье утки")
    public void quack(TestCaseRunner runner, String id, String repetitionCount, String soundCount) {
        runner.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/quack")
                .queryParam("id", id)
                .queryParam("repetitionCount", repetitionCount)
                .queryParam("soundCount", soundCount)
        );
    }

    @Step("Плаванье утки")
    public void swim(TestCaseRunner runner, String id) {
        runner.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/swim")
                .queryParam("id", id)
        );
    }

    @Step("Обновление в БД")
    public void dbUpdate(TestCaseRunner runner, String sql) {
        runner.$(
                sql(db)
                        .statement(sql)
        );
    }

    @Step("Валидация создания/обновления утки через БД")
    public void dbValidateDuck(TestCaseRunner runner, String id, String color, double height, String material, String sound, WingsState wingsState) {
        runner.$(
                query(db)
                        .statement("select * from duck where id = " + id)
                        .validate("COLOR", color)
                        .validate("HEIGHT", String.valueOf(height))
                        .validate("MATERIAL", material)
                        .validate("SOUND", sound)
                        .validate("WINGS_STATE", wingsState.toString())
        );
    }

    @Step("Выполнение запроса к БД и валидации")
    public void dbSQLQueryValidate(TestCaseRunner runner, String sql, String column, String... values) {
        runner.$(
                query(db)
                        .statement(sql)
                        .validate(column, values)
        );
    }

    @Step("Сохранение id из БД в переменную")
    public void getIDFromDB(TestCaseRunner runner, String sql) {
        runner.$(
                query(db)
                        .statement(sql)
                        .extract("id", "duckId")
        );
    }

    @Step("Очистка БД")
    public void clearDB(TestCaseRunner runner, String duckId) {
        runner.$(
                doFinally()
                        .actions(
                                sql(db)
                                        .statement("delete from duck where id=" + duckId)
                        )
        );
    }

    @Step("Валидация с передачей ответа String'ой")
    public void validateDuckResponseString(TestCaseRunner runner, HttpStatus statusCode, String string) {
        runner.$(http()
                .client(yellowDuckService)
                .receive()
                .response()
                .message()
                .statusCode(statusCode.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(string)
        );
    }

    @Step("Валидация с передачей ответа из папки Resources")
    public void validateDuckResponseResources(TestCaseRunner runner, HttpStatus statusCode, String payload) {
        runner.$(http()
                .client(yellowDuckService)
                .receive()
                .response()
                .message()
                .statusCode(statusCode.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ClassPathResource(payload))
        );
    }

    @Step("Валидация с передачей ответа из папки Payload")
    public void validateDuckResponsePayload(TestCaseRunner runner, HttpStatus statusCode, Object resource) {
        runner.$(http()
                .client(yellowDuckService)
                .receive()
                .response()
                .message()
                .statusCode(statusCode.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(resource, new ObjectMapper()))
        );
    }

}
