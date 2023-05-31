# eCommerce Command Line Application

## Introduction

This is a Java-based command-line interface (CLI) eCommerce application. The application was built using Java and uses postgresql for the database.

## Running
In order to easily run the program from the source tree, you must have java https://www.oracle.com/java/technologies/downloads/#java17 (preferably java 17) and Apache Maven https://maven.apache.org/download.cgi installed. Run 'mvn compile' and then 'mvn exec:java' from a command line in the root project directory (or use whatever method/extension your IDE/editor offers for maven).

## Navigation
Navigation across pages is achieved via command line input at prompts.

Pages:

## Home Page
After opening the application, the user has the option to login, register, or exit the app. After registering a user you can log in using your username and password to access the rest of the application.

## Main Menu
Registration and Logging in brings users to the Main Menu.
From the menu the user can navigate to the Shopping Cart, Browse Products, Search Products, and view their order history.


## Shopping Cart
The Shopping Cart allows the user to view the items in their cart, with options to remove items and change quantity. If the user enters "0" for quantity item will be removed as well after prompting.

If the user selects "checkout" they will be prompted to edit various credit card information, and if valid the items in the cart will be ordered.

## Product Pages
User can be brought to a product's page via many of the application's screens, such as through browsing, searching, or viewing an order.
These pages contain the name, price, category, quantity in stock, and description of the product.
The user may view the reviews for the product, add a review for the product (only 1 per user), or add the product to their cart.

## Browse Products
User will be brought to a list of products in the store, and the user can select a product page to navigate to by inputting a number.

## Search Products
### Search by Product Name:
User can search by substring in product names. for instance, "ham" should return both "ham" and "hamburger". The user is presented with a list of search results similar to the browser.

### Search by Category:
All categories of products are listed. User may search by category name (not substrings). All products in the category are then listed that the user can navigate to their pages.

### Search by Product Price:
User inputs a price range to search for products in. Returns list of products that can be navigated to.

## Order History
The user is presented with a list of their previous orders.
They can input an ID number to go to the page for an order and see the items in that order.
On that page, they can navigate to a product's page by inputting its ID.

