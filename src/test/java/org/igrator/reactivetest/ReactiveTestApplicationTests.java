package org.igrator.reactivetest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReactiveTestApplicationTests {

    @Autowired
    private TestController controller;

    @Test
    public void contextLoads() {
    }

    @Test
    public void savedDataShouldBeAddedAnId() {
        Text text = new Text("example");

        Mono<Text> result = controller.addText(text);

        result.subscribe((found) -> {
            assertThat(found.getId(), notNullValue());
            assertThat(found.getText(), is(text.getText()));
        });
    }
}
