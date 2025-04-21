# cse237-project25

Team Members:

* [Tegan Thomas](https://github.com/thomas-03) [203-200-9122]
* [Jack Weitzner](https://github.com/JackWeitzner) [301-337-1008]
* [Nina Latimore](https://github.com/nnltmr) [815-909-4472]
* [Peyton Rodriguez](https://github.com/PeytonRod) [224-545-4178]

For each iteration you should answer the following:

What user stories were completed this iteration?
* Users should be able to open a savings, checking, or money market account. https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/54
* Savings and money market accounts earn a specific amount of interest, which is compounded once daily. https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/7
* Users should be able to see the interest rates for savings and money market accounts. https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/58
* Savings and money market accounts have some specified minimum balance. https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/55
* Main menu is subdivided into multiple different menus which are linked to from the main menu. Specifically, we have the login menu, the teller menu, and the user menu. All of these redirect back to the main menu. https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/51
* When a user has no accounts open, they can not make a deposit and in general have limited menu options. https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/26 https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/32
* We added more tests of the main user menu. https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/70
* The bank and menu classes in general are refactored so that the methods and files are smaller. https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/69
* TENTATIVE: A customer should be asked to create security questions when setting up a profile which are then used when the password is reset https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/60
* TENTATIVE: Users can add personal information such as phone number, email address, etc. that is saved and can be added to later. https://github.com/CSE237SP25/project-latimore_thomas_weitzner/issues/50



What user stories do you intend to complete next iteration?
* N/A

  

Is there anything that you implemented but doesn't currently work?
* All code on the development branch works as intended. We have a few pull requests open that might not be merged in by the time of the peer review on some of the issues in "next iteration".
* Some users might need to create a folder `bankapp.bankResources.transactionHistoryStore` for the saving transaction history between sessions to operate properly.

What commands are needed to compile and run your code from the command line (please provide a script that users can run to launch your program)?

Make sure you are in the root of the repo then run:
chmod +x runBearBanks.sh
./runBearBanks.sh


NOTE: In order to initialize the environment and clear previously compiled files you must run ./runBearBanks.sh once and then exit out of the command. During this very first run you may get errors, however after this initialization, future runs of the command should perform properly.

Below are usernames and passwords for testing purposes (or create your own):
`USERNAME`: testedUser2
`USER_PASS`: password2
`TELLER_NAME`:Teller1
`TELLER_PASS`:Password1
