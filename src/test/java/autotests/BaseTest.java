package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.consol.citrus.http.client.HttpClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.ContextConfiguration;

import java.util.Map;

import static com.consol.citrus.actions.ExecuteSQLAction.Builder.sql;
import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;
import static com.consol.citrus.container.FinallySequence.Builder.doFinally;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class BaseTest extends TestNGCitrusSpringSupport {

    @Autowired
    protected SingleConnectionDataSource dataBase;

    protected ObjectMapper objectMapper = new ObjectMapper();

    @Step("Отправка HTTP POST запроса")
    protected void sendPostRequest(TestCaseRunner runner, HttpClient url, String endpoint, Object body) {
        runner.$(http().client(url)
                .send()
                .post(endpoint)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(body, objectMapper))
        );
    }

    @Step("отправляем HTTP GET запрос")
    protected void sendGetRequest(TestCaseRunner runner, HttpClient url, String endpoint, Map<String, String> queryParams) {
        runner.$(http().client(url)
                .send()
                .get(endpoint + getSQLParameters(queryParams))
        );
    }

    @Step("отправляем HTTP PUT запрос")
    protected void sendPutRequest(TestCaseRunner runner, HttpClient url, String endpoint, Map<String, String> queryParams) {
        runner.$(http().client(url)
                .send()
                .put(endpoint + getSQLParameters(queryParams))
        );
    }

    @Step("отправляем HTTP DELETE запрос")
    protected void sendDeleteRequest(TestCaseRunner runner, HttpClient url, String endpoint, String qKey, String qValue) {
        runner.$(http().client(url)
                .send()
                .delete(endpoint)
                .queryParam(qKey, qValue)
        );
    }

    protected static String getSQLParameters(Map<String, String> queryParams) {
        if (queryParams == null || queryParams.isEmpty()) {
            return "";
        }

        StringBuilder queryBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            queryBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        queryBuilder.deleteCharAt(queryBuilder.length() - 1);
        return "?" + queryBuilder;
    }

    @Step("Выполнение SQl запроса")
    protected void executeSql(TestCaseRunner runner, String sql) {
        runner.$(
                sql(dataBase)
                        .statement(sql)
        );
    }

    @Step("Валидация запроса к БД")
    protected void validateSQL(TestCaseRunner runner, String sql, String column, String value) {
        runner.$(
                query(dataBase)
                        .statement(sql)
                        .validate(column, value)
        );
    }

    @Step("запись в переменную из бд")
    protected void getIDFromDBToVariable(TestCaseRunner runner, String sql, String colName, String varName) {
        runner.$(
                query(dataBase)
                        .statement(sql)
                        .extract(colName, varName)
        );
    }

    @Step("Финальный запрос к бд")
    protected void finaleExecuteSqlQuery(TestCaseRunner runner, String sql) {
        runner.$(
                doFinally()
                        .actions(
                                sql(dataBase)
                                        .statement(sql)
                        )
        );
    }

    @Step("Валидация с передачей ответа String'ой")
    public void validateDuckResponseString(TestCaseRunner runner, HttpStatus statusCode, String expectedString, HttpClient url) {
        runner.$(http()
                .client(url)
                .receive()
                .response()
                .message()
                .statusCode(statusCode.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(expectedString)
        );
    }

    @Step("Валидация с передачей ответа из папки Resources")
    public void validateDuckResponseResources(TestCaseRunner runner, HttpStatus statusCode, Object payload, HttpClient url) {
        runner.$(http()
                .client(url)
                .receive()
                .response()
                .message()
                .statusCode(statusCode.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(payload, objectMapper))
        );
    }

    @Step("Валидация с передачей ответа из папки Payload")
    public void validateDuckResponsePayload(TestCaseRunner runner, HttpStatus statusCode, Object resource, HttpClient url) {
        runner.$(http()
                .client(url)
                .receive()
                .response()
                .message()
                .statusCode(statusCode.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(resource, new ObjectMapper()))
        );
    }
}
