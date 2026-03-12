package com.imdb.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.ConfigFactory;

@Sources({"classpath:app.properties", "system:properties", "system:env"})
public interface AppConfig extends Config {

    @Key("base.url")
    @DefaultValue("https://www.imdb.com")
    String baseUrl();

    @Key("browser")
    @DefaultValue("chrome")
    String browser();

    @Key("timeout")
    @DefaultValue("10")
    int timeout();

    @Key("headless")
    @DefaultValue("false")
    boolean headless();

    @Key("browser.size")
    @DefaultValue("1920x1080")
    String browserSize();

    @Key("page.load.timeout")
    @DefaultValue("30")
    int pageLoadTimeout();

    static AppConfig getInstance() {
        return ConfigHolder.INSTANCE;
    }

    class ConfigHolder {
        private static final AppConfig INSTANCE = ConfigFactory.create(AppConfig.class, System.getProperties());
    }
}