package com.revature.ecommerce_cli;

import java.util.Scanner;
import com.revature.ecommerce_cli.models.Session;

import com.revature.ecommerce_cli.services.RouterService;

public class App {
  public static void main(String args[]) {
    Scanner scan = new Scanner(System.in);
    RouterService router = new RouterService(new Session());
    router.navigate("/home", scan);
    scan.close();
  }
}
