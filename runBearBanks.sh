#!/bin/bash

javac -d bin src/bankapp/*.java
java -cp bin bankapp.Main
