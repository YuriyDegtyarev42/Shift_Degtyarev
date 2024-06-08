package autotests.duckController;

import autotests.clients.DuckControllerHelper;
import autotests.payloads.Duck;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;


public class DeleteTest extends DuckControllerHelper {

    @Test(description = "Проверка удаления уточки")
    @CitrusTest
    public void deleteDuck(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck().color("yellow").height(5.6).material("rubber").sound("quack").wingsState(WingsState.ACTIVE);
        createDuck(runner, duck);
        getID(runner);
        duckDelete(runner, "${duckId}");
        validateDuckResponsePayload(runner, 200, "DeleteResponses/DeleteResponse.json");
        checkDeleteFromDatabase(runner,"${duckId}");
    }

}
