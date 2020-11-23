## TeamCode Module

Welcome!

This module, TeamCode, is the place where you will write/paste the code for your team's
robot controller App. This module is currently empty (a clean slate) but the
process for adding OpModes is straightforward.


## Creating your own OpModes

0) Understand that a full OpMode is composed of an Auto and a TeleOp

1) Write the Auto and TeleOp you want to use. If they are already writen, skip this step.

2) Make a copy of "ExampleMatchMode" and retitle it something meaningful
    Note: retitling a full OpMode requires changing not only the class name,
    but also the "name" field which controlls how it shows up on the phone,
    and potientially the "group" field there as well

3) Find the "selectAutoAndTeleOp()" method

4) Replace "ExampleAuto" and "ExampleTeleOp" with the Auto and TeleOp you actually want

5) You're Done!


##Creating your own Autos

1) Make a copy of "ExampleAuto" and retitle it something meaningful

2) Change the values of "startX" and "startY" to the coordinates the robot will start at fot this
    auto (units are cm, the back left corner is  (0,0), measure from the center odometry wheel)

3) Delete the body of the "loop()" method and replace it with what you actually want

4) You're Done!


## Creating your own TeleOps

0) Understand that TeleOps are made up of 2 driverPrograms, one for driver1 and one for driver2

1) Make a cody of "Driver1ProgramExample" and "Driver2ProgramExample"

2) Rename the new driverPrograms with the names you want

3) In each of them, delete the body of the "loop()" method and replace it with what you want

4) Copy "ExampleTeleOp" and rename it something meaningful
    Note: renaming the TeleOp also requires changing the constructor method to match

5) in the "init()" method, replace "Driver1ProgramExample" and "Driver2ProgramExample"
    with your new driverPrograms

6) You're Done!
