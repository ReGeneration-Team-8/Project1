# Regeneration Coding School
## September’s Exercise Specification
###### September 2017
#### Overview
Implementation of a Java application which will cover the functional specs below, regarding
a real life scenario of vehicle’s insurance and ownership information.
#### Goals
The purpose of this exercise is to confirm, justify and consolidate all the learning outcomes
obtained during September Courses
1. Algorithm Design
2. Java Language Fundamentals
3. DB & SQL Usage.
#### Functional Specifications
The application should provide the following functionalities:
##### F1: Vehicle Insurance Status
Given a vehicle’s plates, the application should respond with the vehicle’s insurance status.
* The user should provide the plate as an argument in the command line.
* The plate’s pattern should be provided in “ ABC-1234 ” format. In case different
pattern is provided, the application should respond with an error message.
##### F2: Vehicles’ Insurance that are about to expire
Give a timeframe in days, the application should provide a list of the vehicles of which their
license is going to expire within this timeframe ( what is to expire in the next X days).
* User should provide the number of days
* User should provide the export format (console, or exported file)
##### F3: Sorting of the plates-number
The application, if requested, can provide the plate numbers order in alphanumerical
order. The participants should design an algorithm to sort plates-number in ascending
manner.
TIP: try to implement the sorting solution inside the Java application, and avoid a purely
DB-related solution.
##### F4: Fine calculation per owner
In case that an owner may have one or more uninsured vehicles, the application should be
able to calculate the total fine cost, according to a fine which will be provided as an
argument in the prompt.
Note: A vehicle cannot be physically owned by more than one person.
#### Interface
No Visual interface is required and the application could interact through command line
with the user.
Input & Export in case that file is selected, will be .csv (comma separated value files).
Otherwise, data are going to be fetched from DB.
After completion of the selected task, the application can exit.
#### Technical Requirements
* Java SE 1.8
* Database: MySQL > 5.5 (Already installed in the Vagrant VM)
* The application will feature a command line interface.
#### Milestones
I. M1: Initial investigation on the design of the application, classes to be used for File
processing, requirements elicitations and specifications in any form (e.g. user
stories) - 20/9/2017
II. M2: F1 & F2 (required: file operations)- 25/9/2017
III. M3: Final project delivery and presentations - 30/9/2017
