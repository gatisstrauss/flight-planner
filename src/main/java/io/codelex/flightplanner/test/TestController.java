package io.codelex.flightplanner.test;

import io.codelex.flightplanner.test.service.TestService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("testing-api/")
public class TestController {

    private TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @PostMapping("clear")
    public void clearDatabase() {
        testService.clearDatabase();

    }
}
