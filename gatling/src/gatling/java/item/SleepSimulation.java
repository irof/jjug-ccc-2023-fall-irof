package item;

import io.gatling.javaapi.core.Simulation;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

/**
 * sleepするシナリオ
 */
public class SleepSimulation extends Simulation {

    {
        setUp(scenario("スリープ")
                        .exec(http("sleep api").get("/sleep").queryParam("ms", "900"))
                        .injectOpen(
                                constantUsersPerSec(30).during(10)
                        )
//                        .injectClosed(
//                                // 1人10秒
//                                constantConcurrentUsers(1).during(10),
//                                // 10人10秒
//                                constantConcurrentUsers(10).during(10),
//                                // 100人10秒
//                                constantConcurrentUsers(50).during(10)
//                        )
        ).protocols(http.baseUrl("http://localhost:8080"));
    }
}
