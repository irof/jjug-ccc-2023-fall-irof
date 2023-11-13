package item;

import io.gatling.javaapi.core.Simulation;

import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;

/**
 * 外部サービス呼び出しのシナリオ
 */
public class ExternalSimulation extends Simulation {

    {
        setUp(scenario("外部サービス呼び出し")
                .exec(http("external api").get("/external"))
                .injectOpen(
                        constantUsersPerSec(30).during(10)
                )
        ).protocols(http.baseUrl("http://localhost:8080"));
    }
}
