package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ReproducerTest {

    @Test
    public void reproduceBug() throws InterruptedException {
        given().when().get("/test/value").then()
                .statusCode(204);
        synchronized (Engine.lock){
            if(!Engine.done){
                Engine.lock.wait();
            }
        }
        Assertions.assertEquals("value", Engine.before);
        Assertions.assertEquals("value", Engine.after);
    }

}