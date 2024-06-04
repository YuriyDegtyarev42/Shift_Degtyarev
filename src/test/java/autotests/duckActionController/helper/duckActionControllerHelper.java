package autotests.duckActionController.helper;

import com.consol.citrus.TestCaseRunner;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import java.util.concurrent.atomic.AtomicBoolean;

import static com.consol.citrus.dsl.JsonPathSupport.jsonPath;
import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public interface duckActionControllerHelper {

    default void createDuck(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState) {
        runner.$(http().client("http://localhost:2222")
                .send()
                .post("/api/duck/create")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\n" + "  \"color\": \"" + color + "\",\n"
                        + "  \"height\": " + height + ",\n"
                        + "  \"material\": \"" + material + "\",\n"
                        + "  \"sound\": \"" + sound + "\",\n"
                        + "  \"wingsState\": \"" + wingsState
                        + "\"\n" + "}")
        );
    }

    default void getID(TestCaseRunner runner) {
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response()
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract(fromBody().expression("$.id", "duckId"))
        );
    }

    default void fly(TestCaseRunner runner) {
        runner.$(http().client("http://localhost:2222")
                .send()
                .get("/api/duck/action/fly")
                .queryParam("id", "${duckId}")
        );
    }

    default void validateFlyResponse(TestCaseRunner runner, int statusCode, String message) {
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response()
                .message()
                .statusCode(statusCode)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .validate(jsonPath().expression("$.message", message))
        );
    }

    default void validatePropertiesResponse(TestCaseRunner runner, int statusCode, String color, double height, String material, String sound, String wingsState) {
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response()
                .message()
                .statusCode(statusCode)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .validate(jsonPath().expression("$.color", color))
                .validate(jsonPath().expression("$.height", height))
                .validate(jsonPath().expression("$.material", material))
                .validate(jsonPath().expression("$.sound", sound))
                .validate(jsonPath().expression("$.wingsState", wingsState))
        );
    }

    default void getDuckProperties(TestCaseRunner runner) {
        runner.$(http().client("http://localhost:2222")
                .send()
                .get("/api/duck/action/properties")
                .queryParam("id", "${duckId}")
        );
    }


    default void createDuckEvenNumber(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState) {
        AtomicBoolean evenNumber = new AtomicBoolean(false);
        runner.$(http().client("http://localhost:2222")
                .send()
                .post("/api/duck/create")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\n" + "  \"color\": \"" + color + "\",\n"
                        + "  \"height\": " + height + ",\n"
                        + "  \"material\": \"" + material + "\",\n"
                        + "  \"sound\": \"" + sound + "\",\n"
                        + "  \"wingsState\": \"" + wingsState
                        + "\"\n" + "}")
        );
        runner.$(http().client("http://localhost:2222")
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
            createDuckEvenNumber(runner, color, height, material, sound, wingsState);
        }
    }

    default void createDuckOddNumber(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState) {
        AtomicBoolean evenNumber = new AtomicBoolean(false);
        runner.$(http().client("http://localhost:2222")
                .send()
                .post("/api/duck/create")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\n" + "  \"color\": \"" + color + "\",\n"
                        + "  \"height\": " + height + ",\n"
                        + "  \"material\": \"" + material + "\",\n"
                        + "  \"sound\": \"" + sound + "\",\n"
                        + "  \"wingsState\": \"" + wingsState
                        + "\"\n" + "}")
        );
        runner.$(http().client("http://localhost:2222")
                        .receive()
                        .response()
                        .message()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .extract(fromBody().expression("$.id", "duckId"))
                        .validate((message, testContext) -> {
                            try {
                                String id = new ObjectMapper().readTree(message.getPayload().toString()).get("id").toString();
//                        log.info(id);
                                evenNumber.set(Integer.parseInt(id) % 2 == 0);
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        })
        );
        if (evenNumber.get()) {
            createDuckOddNumber(runner, color, height, material, sound, wingsState);
        }
    }

    default void quack(TestCaseRunner runner) {
        runner.$(http().client("http://localhost:2222")
                .send()
                .get("/api/duck/action/quack")
                .queryParam("id", "${duckId}")
                .queryParam("repetitionCount", "1")
                .queryParam("soundCount", "1")
        );
    }

    default void validateQuackResponse(TestCaseRunner runner, int statusCode, String sound) {
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response()
                .message()
                .statusCode(statusCode)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .validate(jsonPath().expression("$.sound", sound))
        );
    }

    default void swim(TestCaseRunner runner, String id) {
        runner.$(http().client("http://localhost:2222")
                .send()
                .get("/api/duck/action/swim")
                .queryParam("id", id)
        );
    }

    default void validateSwimResponse(TestCaseRunner runner, int statusCode, String message) {
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response()
                .message()
                .statusCode(statusCode)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .validate(jsonPath().expression("$.message", message))
        );
    }
}
