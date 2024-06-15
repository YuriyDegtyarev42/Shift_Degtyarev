package autotests.duckController;

import autotests.payloads.Duck;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.springframework.http.HttpStatus;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import autotests.clients.DuckControllerHelper;

@Epic("Duck controller")
@Feature("/api/duck/create")
public class CreateTest extends DuckControllerHelper {

    @DataProvider(name = "duckData")
    public Object[][] duckData() {
        return new Object[][]{
                {"orange", 2.86, "wood", "quack", WingsState.FIXED, null},
                {"purple", 3.43, "copper", "good morning", WingsState.ACTIVE, null},
                {"red", 4.53, "rubber", "bonjour", WingsState.ACTIVE, null},
                {"yellow", 6.23, "metal", "ku", WingsState.UNDEFINED, null},
                {"red", 7.12, "silicon", "bark", WingsState.UNDEFINED, null}
        };
    }

    @Test(description = "Проверка создания утки", dataProvider = "duckData")
    @CitrusTest
    @CitrusParameters({"color", "height", "material", "sound", "wingsState", "runner"})
    public void createRubberDuckTest(String color, double height, String material, String sound, WingsState wingsState, @Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck().color(color).height(height).material(material).sound(sound).wingsState(wingsState);

        createDuckByRequest(runner, duck);
        getIDFromDatabaseToVariable(runner, "select * from duck where height = " + height);
        clearDB(runner);

        validateDuckResponsePayload(runner, HttpStatus.OK, duck.id("@isNumber()@"));

        validateDBDuck(runner, "${duckId}", color, height, material, sound, wingsState);
    }

}
