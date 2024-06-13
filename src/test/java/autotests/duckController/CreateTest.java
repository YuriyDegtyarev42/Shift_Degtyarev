package autotests.duckController;

import autotests.payloads.Duck;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import autotests.clients.DuckControllerHelper;

@Epic("Duck controller")
@Feature("/api/duck/create")
public class CreateTest extends DuckControllerHelper {

    @Test(description = "Проверка создания уточки с материалом rubber")
    @CitrusTest
    public void createDuckRubber(@Optional @CitrusResource TestCaseRunner runner) {
        String color = "yellow";
        double height = 2.81;
        String material = "rubber";
        String sound = "quack";
        WingsState wingsState = WingsState.ACTIVE;
        Duck duck = new Duck().color(color).height(height).material(material).sound(sound).wingsState(wingsState);
        createDuck(runner, duck);

        getIDFromDB(runner, "select * from duck where height = " + height);
        clearDB(runner, "${duckId}");

        validateDuckResponseResources(runner, HttpStatus.OK, duck.id("@isNumber()@").toString());
        dbValidateDuck(runner, "${duckId}", color, height, material, sound, wingsState);
    }

    @Test(description = "Проверка создания уточки с материалом wood")
    @CitrusTest
    public void createDuckWood(@Optional @CitrusResource TestCaseRunner runner) {
        String color = "yellow";
        double height = 1.21;
        String material = "wood";
        String sound = "quack";
        WingsState wingsState = WingsState.ACTIVE;
        Duck duck = new Duck().color(color).height(height).material(material).sound(sound).wingsState(wingsState);
        createDuck(runner, duck);
        getIDFromDB(runner, "select * from duck where height = " + height);
        clearDB(runner, "${duckId}");

        validateDuckResponseResources(runner, HttpStatus.OK, duck.id("@isNumber()@").toString());
        dbValidateDuck(runner, "${duckId}", color, height, material, sound, wingsState);
    }

}
