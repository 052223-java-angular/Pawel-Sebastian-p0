package com.revature.ecommerce_cli;

import java.util.Scanner;
import com.revature.ecommerce_cli.models.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.ecommerce_cli.services.RouterService;

public class App {
  public static void main(String args[]) {
    Scanner scan = new Scanner(System.in);
    final Logger logger = LogManager.getLogger(App.class);
    logger.debug("started main method");
    RouterService router = new RouterService(new Session());
    logger.debug("Navigating to home screen");
    router.navigate("/home", scan);
    scan.close();
    logger.debug("App Closing");
  }
}
