package autotests.duckActionController;

import autotests.clients.DuckControllerHelper;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;


public class PropertiesTest extends DuckControllerHelper {

    @Test(description = "Проверка получения свойств уточки (четный id, материал wood)")
    @CitrusTest
    public void getDuckEvenNumberWoodProperties(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckEvenNumber(runner, "yellow", 1.1, "wood", "quack", "ACTIVE");
        getDuckProperties(runner);
        validateDuckResponse(runner, 200, "yellow", 1.1, "wood", "quack", "ACTIVE");
    }

    @Test(description = "Проверка получения свойств уточки (нечетный id, материал rubber)")
    @CitrusTest
    public void getDuckOddNumberRubberProperties(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckOddNumber(runner, "yellow", 1.1, "rubber", "quack", "ACTIVE");
        getDuckProperties(runner);
        validateDuckResponse(runner, 200, "yellow", 1.1, "rubber", "quack", "ACTIVE");
    }

}
