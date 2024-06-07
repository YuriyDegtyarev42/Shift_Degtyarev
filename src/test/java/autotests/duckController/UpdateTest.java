package autotests.duckController;

import autotests.clients.DuckControllerHelper;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;


public class UpdateTest extends DuckControllerHelper {

    @Test(description = "Проверка обновления уточки (высота и цвет)")
    @CitrusTest
    public void updateDuckHeightColor(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 1.1, "rubber", "quack", "ACTIVE");
        getID(runner);
        updateDuck(runner, "red", "8.2", "${duckId}", "rubber", "quack");
        validateUpdateResponse(runner, 200, "${duckId}");
    }

    @Test(description = "Проверка обновления уточки (звук и цвет)")
    @CitrusTest
    public void updateDuckSoundColor(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 1.1, "rubber", "quack", "ACTIVE");
        getID(runner);
        updateDuck(runner, "purple", "1.1", "${duckId}", "rubber", "bark");
        validateUpdateResponse(runner, 200, "${duckId}");
    }

}
