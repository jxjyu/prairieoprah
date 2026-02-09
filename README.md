# Managing Customers 

## Java Program for Managing Package Forwarding

In the last few years, I have found myself repeatedly helping friends order items from China that are not available here. A lot of Chinese sellers don't ship to Canada, which means that items need to be combined; I have a PO Box in China for this. It also saves a lot of money to combine these items and ship it in one larger parcel. 

However, this has become increasingly complex. Too many items, too many people, and random payments all over the place mean that it has become difficult to track who paid what, if an item was delivered, or how much the shipping should cost.

This program is intended for use long-term after this class is over. There are some missing features still, but they should be added soon.

## INSTRUCTIONS FOR END USER
Upon launching the program, you'll see a variety of options. They are listed below from left to right on the UI:
- You can view existing items, payments, packages, or customers by hitting their respective `View` buttons.
- You can generate new items, payments, packages, or customers by hitting their respective `Add` buttons.
- When generating a new item in `Add Item`, you can select a customer or package to add the item to using the `Select Customer` or `Select Package` button. You can also enter in their name/ID, respectively.
- When generating a new payment in `Add Payment`, you can select the customer the payment is from using the `Select Customer` button. You can also enter in their name directly.
- When generating a new package in `Add Package`, you can select any pre-added item to the package by hitting `Select Items` at the bottom of the `Add Package` screen.
- You can save your Manager at any filepath by hitting the `Save As` button and entering the filepath you wish to save at.
- You can load any existing Manager by hitting the `Load Save` button and entering the filepath your Manager is at. The program also loads any Manager at the default filepath upon launching the program.
- You can locate the visual component of the program, a splash screen that thanks you for your usage, by using the `Exit to Menu` button. 

## USER STORIES
Added 10th October 2025
- I want to be able to add a payment to the manager, with an amount and date stored
- I want to be able to view the items, customers, or packages in the manager
- I want to able to add an item to the manager, with a name, ID, and other details stored
- I want to be able to then assign that item to a customer or package
- I want to be able to find out the contents of a package, and which customer each item belongs to
- I want to be able to search which customer owns a specific item
- I want to be able to add items in bulk, using comma separated lists or similar format

Added 22nd October 2025
- I want to be able to save all the information in the manager, both to a specific path and a default path
- I want to be able to load all the information, both from a specific path and a default path
- I want the manager to autoload all information on startup, if available.

## PROGRAM IDEAS
I want to create a program that would track all of this. The main user would be myself, and it would hold the following information:
- Customer name
- Customer payments history
- Customer purchase history
- Name, weight, price of items
- Which parcel the item was shipped in
- The cost of shipping that parcel, per gram 
- The exchange rate with time stamp, which should be logged

My thoughts at this time are to create the following classes, which should each store the fields in the subindents:
- Customer  
    * String name
    * ArrayList<Item> item
    * ArrayList<Payment> payment
- Item
    * String name
    * double price (in yuan)
    * int weight
- Payment
    * LocalDate date
    * double originalAmount (in CAD)
    * double exchangeRate (CAD per RMB)
- Package
    * int totalWeight (in grams)
    * double cost (in yuan)
    * ArrayList<Item> item
- CustomerManager
    * ArrayList<Customer> customers
    * ArrayList<Package> packages

## Phase 4: Task 3
I want to say, there were some really strange decisions I made when first laying out this project. This probably has something to do with my terrible planning skills, but I'd make a few changes at the very least. Since I'm probably using this in real life, I probably will do so soon.
- Standardise the array numbering from `ItemInterface`, `PaymentInterface` etc, and move it to each class itself (`PaymentInterface` -> `Payment`). There's no need for an extra class, and the fact that some things are listed as "OPT" is because those things weren't initially required. With changes in later versions, for example, an Item ID is now required for `Item` objects, so it should be in order and always required.
- Reusable code in `ui`, such as the Name and Date field with the label "name" next to it, etc, should be moved out of `AddItem`, `AddPackage`, `SelectItems`, etc, and moved into their own classes extending JPanel, which would reduce the duplication. 
There's probably more, but I can't think of others off the top of my head right now.