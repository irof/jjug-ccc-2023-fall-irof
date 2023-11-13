package item;

import io.gatling.javaapi.core.Simulation;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

/**
 * Open Injectionの練習用
 */
public class SimpleOpenSimulation extends Simulation {

    {
        setUp(
                scenario("Simple-open").exec(http("simple").get("/simple"))
                        .injectOpen(
                                // 1秒あたり10人が訪れるのが10秒続く。合計100リクエストになる。
                                // 上流で流量制限が行われている場合はありえる。
                                // 秒間でリクエストされる数が安定するので、ロングランには向いているが、
                                // アプリケーションの処理時間は考慮しないので1秒以上かかるAPIではどんどん滞留する
                                constantUsersPerSec(10).during(10)

                                //rampUsersPerSec(1).to(500).during(5)
                        )
        ).protocols(http.baseUrl("http://localhost:8080"));
    }
}
