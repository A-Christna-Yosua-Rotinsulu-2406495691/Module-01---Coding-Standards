package id.ac.ui.cs.advprog.eshop.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HomeControllerTest {

    private final HomeController homeController = new HomeController();

    @Test
    void testHomePage() {
        String viewName = homeController.homePage();
        assertEquals("HomePage", viewName);
    }
}