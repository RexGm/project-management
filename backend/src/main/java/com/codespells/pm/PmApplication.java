package com.codespells.pm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Yazilim Takim Yonetim ve Katki Analiz Platformu.
 *
 * Modular monolith: tum moduller {@code com.codespells.pm.modules} altinda,
 * ortak altyapi {@code com.codespells.pm.common} altinda toplanir.
 */
@SpringBootApplication
@EnableScheduling
public class PmApplication {

    public static void main(String[] args) {
        SpringApplication.run(PmApplication.class, args);
    }
}
