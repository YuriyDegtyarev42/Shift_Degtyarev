package autotests.duckActionController;

import autotests.duckActionController.helper.duckActionControllerHelper;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class Quack extends TestNGCitrusSpringSupport implements duckActionControllerHelper {

    @Test(description = "Проверка того, что уточка с четным id крякает")
    @CitrusTest
    public void quackEvenNumberDuck(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckEvenNumber(runner, "yellow", 1.1, "wood", "quack", "ACTIVE");
        quack(runner);
        validateQuackResponse(runner, 200, "quack");
    }

    @Test(description = "Проверка того, что уточка с нечетным id крякает")
    @CitrusTest
    public void quackOddNumberDuck(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckOddNumber(runner, "yellow", 1.1, "rubber", "quack", "ACTIVE");
        quack(runner);
        validateQuackResponse(runner, 200, "quack");
    }
    
}
