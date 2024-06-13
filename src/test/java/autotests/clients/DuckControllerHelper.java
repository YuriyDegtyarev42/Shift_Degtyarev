package autotests.clients;

import autotests.BaseTest;
import autotests.EndpointConfig;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import io.qameta.allure.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;

import java.util.Map;

import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckControllerHelper extends BaseTest {

    @Autowired
    public HttpClient yellowDuckService;

    @Step("Создание утки")
    protected void createDuckByRequest(TestCaseRunner runner, Object duckProperties) {
        sendPostRequest(runner, yellowDuckService, "/api/duck/create", duckProperties);
    }

    @Step("Обновление утки")
    protected void updateDuckByRequest(TestCaseRunner runner, String color, double height, String id, String material, String sound, WingsState wingsState) {
        Map<String, String> queryParams = Map.of(
                "color", color,
                "height", String.valueOf(height),
                "id", id,
                "material", material,
                "sound", sound,
                "wingsState", wingsState.toString()
        );
        sendPutRequest(runner, yellowDuckService, "/api/duck/update", queryParams);
    }

    @Step("Удаление утки")
    protected void deleteDuckByRequest(TestCaseRunner runner) {
        sendDeleteRequest(runner, yellowDuckService, "/api/duck/delete", "id", "${duckId}");
    }

    @Step("Полет утки")
    public void flyDuckByRequest(TestCaseRunner runner, String id) {
        Map<String, String> queryParams = Map.of(
                "id", id
        );
        sendGetRequest(runner, yellowDuckService, "/api/duck/action/fly", queryParams);
    }

    @Step("Плавание утки")
    public void swimDuckByRequest(TestCaseRunner runner, String id) {
        Map<String, String> queryParams = Map.of(
                "id", id
        );
        sendGetRequest(runner, yellowDuckService, "/api/duck/action/swim", queryParams);
    }

    @Step("Получение свойств утки")
    public void duckPropertiesByRequest(TestCaseRunner runner, String id) {
        Map<String, String> queryParams = Map.of(
                "id", id
        );
        sendGetRequest(runner, yellowDuckService, "/api/duck/action/properties", queryParams);
    }

    @Step("Кряканье")
    public void quackDuckByRequest(TestCaseRunner runner, String id, String repetitionCount, String soundCount) {
        Map<String, String> queryParams = Map.of(
                "id", id,
                "repetitionCount", repetitionCount,
                "soundCount", soundCount
        );
        sendGetRequest(runner, yellowDuckService, "/api/duck/action/quack", queryParams);
    }

    @Step("Изменение данных в бд")
    public void databaseUpdate(TestCaseRunner runner, String sqlQuery) {
        executeSql(runner, sqlQuery);
    }

    @Step("валидация данных утки в бд")
    protected void validateDBDuck(TestCaseRunner runner, String id, String color, double height, String material, String sound, WingsState wingsState) {
        runner.$(
                query(dataBase)
                        .statement("select * from duck where id = " + id)
                        .validate("COLOR", color)
                        .validate("HEIGHT", String.valueOf(height))
                        .validate("MATERIAL", material)
                        .validate("SOUND", sound)
                        .validate("WINGS_STATE", wingsState.toString())
        );
    }

    @Step("Сохранение id из БД в переменную")
    public void getIDFromDatabaseToVariable(TestCaseRunner runner, String sql) {
        getIDFromDBToVariable(runner, sql, "id", "duckId");
    }

    @Step("Очистка бд")
    public void clearDB(TestCaseRunner runner) {
        finaleExecuteSqlQuery(runner, "delete from duck where id=${duckId}");
    }

    @Step("Валидация с передачей ответа String'ой")
    public void validateDuckResponseString(TestCaseRunner runner, HttpStatus statusCode, String expectedString) {
        validateDuckResponseString(runner, statusCode, expectedString, yellowDuckService);
    }

    @Step("Валидация с передачей ответа из папки Resources")
    public void validateDuckResponseResources(TestCaseRunner runner, HttpStatus statusCode, String payload) {
        validateDuckResponseResources(runner, statusCode, payload, yellowDuckService);
    }

    @Step("Валидация с передачей ответа из папки Payload")
    public void validateDuckResponsePayload(TestCaseRunner runner, HttpStatus statusCode, Object payload) {
        validateDuckResponsePayload(runner, statusCode, payload, yellowDuckService);
    }

}
