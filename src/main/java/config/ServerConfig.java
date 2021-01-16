package config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:config.properties")
public interface ServerConfig  extends Config {
    @Key("url")
    String url();

    String hostname();
    @DefaultValue("42")
    int maxThreads();

}