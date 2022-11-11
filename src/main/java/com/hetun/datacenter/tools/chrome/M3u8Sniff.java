package com.hetun.datacenter.tools.chrome;

import com.hetun.datacenter.Config;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.remote.http.HttpRequest;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Routable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UncheckedIOException;
import java.util.concurrent.atomic.AtomicReference;

import static org.openqa.selenium.remote.http.Contents.utf8String;


@Component
public class M3u8Sniff {

    ChromeDriver driver;
    AtomicReference<String> m3u8Url = new AtomicReference<>("");
    @Autowired
    public M3u8Sniff(Config config) {

        System.setProperty("webdriver.chrome.driver", config.getChromeDriverPath());
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        driver = new ChromeDriver(chromeOptions);
        NetworkInterceptor networkInterceptor = new NetworkInterceptor(
                driver,
                new Routable() {
                    @Override
                    public boolean matches(HttpRequest req) {
                        String uri = req.getUri();
                        boolean isM3u8 = uri.contains("m3u8");
                        if (isM3u8) {
                            m3u8Url.set(uri);
                        }
                        return isM3u8;
                    }

                    @Override
                    public HttpResponse execute(HttpRequest req) throws UncheckedIOException {
                        System.out.println(req);
                        return new HttpResponse()
                                .setStatus(200)
                                .addHeader("Content-Type", com.google.common.net.MediaType.HTML_UTF_8.toString())
                                .setContent(utf8String("ok"));
                    }
                });

    }
    public String getM3u8Url(String sourceUrl) {
        driver.get(sourceUrl);
        return m3u8Url.get();
    }
}
