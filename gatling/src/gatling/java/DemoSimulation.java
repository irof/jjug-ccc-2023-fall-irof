import io.gatling.javaapi.core.Simulation;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

/**
 * 開始時に流すデモシミュレーション
 *
 * 即レスポンスが返るAPI（10ms以内）なので、「負荷がかかっている」と言えないもの。
 * 実際はとあるボトルネックに当たりはするのだけど、観測は難しいと思います。
 *
 * Injectionの練習はこういうのでやって、期待した通りのリクエスト数のグラフになるか？を見るのがよいです。
 */
public class DemoSimulation extends Simulation {

    {
        setUp(
                scenario("最初のデモ").exec(http("simple").get("/simple"))
                        .injectOpen(
                                // 一気に10人きて
                                atOnceUsers(10),
                                // 5秒に20人まで増えて
                                rampUsers(20).during(5),
                                // 10秒間で10から50まで増えて
                                rampUsersPerSec(10).to(50).during(10),
                                // 10秒間で10から50までランダムに増えて
                                rampUsersPerSec(10).to(50).during(10).randomized(),
                                // 5秒無風で
                                nothingFor(5),
                                // 50人一気にきて
                                atOnceUsers(50),
                                // 5秒無風で
                                nothingFor(5),
                                // 1秒間20人が30秒続く
                                constantUsersPerSec(20).during(30)
                        )
        ).protocols(http.baseUrl("http://localhost:8080"));
    }
}
