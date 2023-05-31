# eCommerce Command Line Application

## Introduction

This is a Java-based command-line interface (CLI) eCommerce application. The application was built using Java and uses postgresql for the database.

## Running
In order to easily run the program from the source tree, you must have java https://www.oracle.com/java/technologies/downloads/#java17 (preferably java 17) and Apache Maven https://maven.apache.org/download.cgi installed. Run 'mvn compile' and then 'mvn exec:java' from a command line in the root project directory (or use whatever method/extension your IDE/editor offers for maven).

## Navigation
Navigation across pages is achieved via command line input at prompts.

## Home Page
After opening the application, the user has the option to login, register, or exit the app. After registering a user you can log in using your username and password to access the rest of the application.

## Main Page
From the Main page you the user can navigate to the Shopping Cart, Browse Products, Search Products, and view their order history.

## Shopping Cart
The Shopping Cart allows the user to view the items in their cart, with options to remove items and change quantity. If the user enters "0" for quantity item will be removed as well after prompting.

--------------- TODO checkout

## Browse Products
