package com.mendy;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Mendy
 * @create 2023-08-10 11:05
 */


public class TestcontainersTest extends AbstractTestcontainers {


    @Test
    void canStartPostgresDB() throws InterruptedException {
       assertThat(posrgreSQLContainer.isRunning()).isTrue();
       assertThat(posrgreSQLContainer.isCreated()).isTrue();
//       Thread.sleep(600000);
    }


}
