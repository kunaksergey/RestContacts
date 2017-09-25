package ua.shield;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;


/**
 * Created by sa on 18.09.17.
 */
@SpringBootApplication
@EnableCaching
public class Application implements CommandLineRunner {
    private static Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private CacheManager cacheManager;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        log.info("Caching  Configuration");
        log.info("using cache manager: " + cacheManager.getClass().getName());
    }
}
