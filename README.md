# cse237-project25

Team Members:

* [Tegan Thomas](https://github.com/thomas-03) [203-200-9122]
* [Jack Weitzner](https://github.com/JackWeitzner) [301-337-1008]
* [Nina Latimore](https://github.com/nnltmr) [815-909-4472]
* [Peyton Rodriguez](https://github.com/PeytonRod) [224-545-4178]

For each iteration you should answer the following:

What user stories were completed this iteration?
* Customers should be able to exit (from the login page) which will stop the running of the program https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/45
* refactor bank and menu classes into smaller methods https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/69
* Teller should be able to create an account https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/21
* General debug small issues https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/87
* Teller should be able to log out of profile https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/90
* Transaction history should be saved between sessions https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/74
* All unit tests should pass https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/91
* A customer is not able to log back in immediatley after logging out (Fix this issue) https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/73
* Users should be able to access their previous accounts https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/75
* Bank customers should be able to check account balance https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/3
* Customers can reset their username https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/36
* The date/time of a transaction should appear in the transaction history https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/62
* Menu should display bank name: Bear Banks https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/61
* Menu format should be the same for the login and account menus https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/49
* Users should be able to select which account they want to review the transaction history for https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/67
* customers should be able to select which account should be renamed https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/66
* User should be able to select which account they deposit into https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/52
* user should be able to select which account they are withdrawing from https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/53
* Teller should be able to see all user accounts and amounts in them https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/22
* Customer should be able to log out https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/44
* Customers shouldn't be able to put in or have an overflowing amount of money https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/38






What user stories do you intend to complete next iteration?

* Accounts should earn interest on any deposited funds at a specified rate	https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/7
* Different account types [savings, checking, money markets] should be offered to the customer when opening an account https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/54
* Accounts should earn interest on any deposited funds at a specified rate https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/7
*  There should be a high minimum balance to open a money market account and it should earn more interest https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/55
* Customers should only be able to view a balance of an account when an accounts are open https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/32
* Customers should have to open an account BEFORE making a deposit https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/26
* Main menu should be subdivided into mutliple different menus which are linked to from the main menu. Submenus should be able to navigate back to the main menu. https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/51
* The bank should offer a loan based on the personal information in your profile https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/56
* Customers should be able to open an adjunct or dependent account with its own login information (for a kid, old person, etc) https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/57
* A customer should be asked to create security questions when setting up a profile which are then used when the password is reset https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/60
  



Is there anything that you implemented but doesn't currently work?
* All code on the development branch works as intended. We have a few pull requests open that might not be merged in by the time of the peer review on some of the issues in "next iteration".
* Creating a new teller logs current Teller out, this has been fixed in a pull request that is currently open

What commands are needed to compile and run your code from the command line (please provide a script that users can run to launch your program)?

Make sure you are in the root of the repo then run:
chmod +x runBearBanks.sh
./runBearBanks.sh

Below are usernames and passwords for testing purposes (or create your own):
`USERNAME`: testedUser2
`USER_PASS`: password2
`TELLER_NAME`:Teller1
`TELLER_PASS`:Password1
