package autotests.clients;

import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import java.util.concurrent.atomic.AtomicBoolean;
import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckControllerHelper extends TestNGCitrusSpringSupport {

    @Autowired
    public HttpClient yellowDuckService;

    public void createDuck(TestCaseRunner runner, Object bodyRequest) {
        runner.$(http().client(yellowDuckService)
                .send()
                .post("/api/duck/create")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(bodyRequest, new ObjectMapper()))
        );
    }

    public void createDuckEvenNumber(TestCaseRunner runner, Object bodyRequest) {
        AtomicBoolean evenNumber = new AtomicBoolean(false);
        runner.$(http().client(yellowDuckService)
                .send()
                .post("/api/duck/create")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(bodyRequest, new ObjectMapper()))
        );
        runner.$(http().client(yellowDuckService)
                .receive()
                .response()
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract(fromBody().expression("$.id", "duckId"))
                .validate((message, testContext) -> {
                    try {
                        String id = new ObjectMapper().readTree(message.getPayload().toString()).get("id").toString();
                        evenNumber.set(Integer.parseInt(id) % 2 == 0);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
        );
        if (!evenNumber.get()) {
            createDuckEvenNumber(runner, bodyRequest);
        }
    }

    public void createDuckOddNumber(TestCaseRunner runner, Object body) {
        AtomicBoolean evenNumber = new AtomicBoolean(false);
        runner.$(http().client(yellowDuckService)
                .send()
                .post("/api/duck/create")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(body, new ObjectMapper()))
        );
        runner.$(http().client(yellowDuckService)
                .receive()
                .response()
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract(fromBody().expression("$.id", "duckId"))
                .validate((message, testContext) -> {
                    try {
                        String id = new ObjectMapper().readTree(message.getPayload().toString()).get("id").toString();
                        evenNumber.set(Integer.parseInt(id) % 2 == 0);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
        );
        if (evenNumber.get()) {
            createDuckOddNumber(runner, body);
        }
    }

    public void getID(TestCaseRunner runner) {
        runner.$(http().client(yellowDuckService)
                .receive()
                .response()
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract(fromBody().expression("$.id", "duckId"))
        );
    }

    public void getDuckProperties(TestCaseRunner runner, String id) {
        runner.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/properties")
                .queryParam("id", id)
        );
    }

    public void updateDuck(TestCaseRunner runner, String color, String height, String id, String material, String sound, String wingsState) {
        runner.$(http().client(yellowDuckService)
                .send()
                .put("/api/duck/update")
                .queryParam("color", color)
                .queryParam("height", height)
                .queryParam("id", id)
                .queryParam("material", material)
                .queryParam("sound", sound)
                .queryParam("wingsState", wingsState)
        );
    }

    public void duckDelete(TestCaseRunner runner, String id) {
        runner.$(http().client(yellowDuckService)
                .send()
                .delete("/api/duck/delete")
                .queryParam("id", id)
        );
    }

    public void checkDeleteFromDatabase(TestCaseRunner runner, String id) {
        runner.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/getAllIds")
        );
        runner.$(http().client(yellowDuckService)
                .receive()
                .response()
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .validate(((message, testContext) -> Assert.assertFalse(message.getPayload().toString().contains(id))))
        );
    }

    public void fly(TestCaseRunner runner, String id) {
        runner.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/fly")
                .queryParam("id", id)
        );
    }

    public void quack(TestCaseRunner runner, String id, String repetitionCount, String soundCount) {
        runner.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/quack")
                .queryParam("id", id)
                .queryParam("repetitionCount", repetitionCount)
                .queryParam("soundCount", soundCount)
        );
    }

    public void swim(TestCaseRunner runner, String id) {
        runner.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/swim")
                .queryParam("id", id)
        );
    }

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
