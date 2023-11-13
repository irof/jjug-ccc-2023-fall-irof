package dev.irof;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class MyController {

    private final MyService service;
    private final MyClient client;

    public MyController(MyService service, MyClient client) {
        this.service = service;
        this.client = client;
    }

    /**
     * 即レスポンスを返すAPI
     *
     * このAPIに負荷をかけてもボトルネックに引っかかってるのを見つけるのは難しいですが、
     * 負荷ツールクライアントやインフラの制限には当てられます。
     */
    @RequestMapping("simple")
    String hello() {
        return "hello";
    }

    /**
     * トランザクションの内側で時間がかかるAPI
     *
     * ようするにコネクションを掴みっぱなしで時間がかかるものです。
     */
    @RequestMapping("transaction")
    void トランザクション() throws Exception {
        service.transactional();
    }

    /**
     * 外部呼び出しするAPI
     *
     * Wiremockにつなげています。
     * /docker/wiremock のスタブ定義をいじることで対向システムの振る舞いを踏まえた負荷テストが行えます。
     */
    @RequestMapping("external")
    void 外部サービス呼び出し() {
        client.call();
    }

    /**
     * 時間のかかるAPI
     *
     * transactionやexternalでも同等のことができますが、より原始的な「単に時間がかかる」ものです。
     * それぞれ同じリクエスト
     */
    @RequestMapping("sleep")
    void 時間のかかる(@RequestParam int ms) throws Exception {
        Thread.sleep(ms);
    }

    @RequestMapping("memory")
    String メモリ使用(@RequestParam int mb) throws Exception {
        var b = new byte[mb * 1024 * 1024];
        Arrays.fill(b, (byte) 97);

        // このsleepの間にメモリこえるリクエストを重ねるとOOME起こせるよ
        Thread.sleep(1000);

        // 「でっかいレスポンス」を返す場合はこれ返す
        // ……んだけど、curlで叩くと邪魔なので空をかえしてます。
        // return new String(b);
        return "";
    }
}
