#**Design for Assignment 1**

**Grass**

We have to create a new class called **Grass**. This class will inherit the abstract class Ground. 
By doing this, it will inherit the superclass methods, therefore adhering to the Don't Repeat Yourself principle.

The class will make use of the constructor attribute of **displayChar** in the superclass Ground.

The class will have another private boolean attribute named **harvested** and the initial value will be  false. This is to include
hay as part of grass.
This fits the principle of declaring variables in the tightest possible scope

The displayChar of grass will be “g”. For hay, it will be included in the Grass class but with different displayChar which is “G”

We will then override the method tick in the superclass Ground to fit in features according to the specifications.

A pseudocode for the method tick in the class Grass

~~~

if harvested
       displayChar="G"

if (location of grass coordinate x+1 && location of grass coordinate x+2) and Math.random>0.3
    add grass to location grass coordinate x+3
if (location of grass coordinate y+1 && location of grass coordinate y+2) and Math.random>0.3
    add grass to location grass coordinate y+3
if (location of grass coordinate x-1 && location of grass coordinate x-2) and Math.random>0.3
    add grass to location grass coordinate x-3
if (location of grass coordinate y-1 && location of grass coordinate y-2) and Math.random>0.3
    add grass to location grass coordinate y-3
                                                    
~~~
**Application**

In the application class, we have to calculate the chances of dirt growing grass. We have set a 40 percent chance temporarily for the grass to grow.
.

Pseudocode

~~~
for i in  map
    for char in i
        if Math.random>0.4
            char="g"

~~~
        
**Tree**

For the tree class, we have to add fruit to the tree once the tree is mature enough.

To do this we have to add a private attribute called **fruits** which is an ArrayList type of Fruits class.
At every turn, the mature tree may bear a fruit given a chance of 50 percent.

And then we modify the tick method in the tree class according to the specs.
The additions can be explained by

~~~

if Math.random>=0.5
    fruits.add(new Fruit)

if Math.random>=0.3 and fruit is not empty
    fruits.remove(0)
    add Fruit to gameMap location of tree
    change displayChar of tree to "TF"

if tree left or right neighbour == dirt and Math.random >0.2
    add grass to tree left or right neighbour

~~~


**Fruit**

Create simple class named **Fruit** to represent fruit. Inherits Item class.

This class have a private boolean attribute of **riped** with initial value of false.
Create a new int attribute called **age** with initial age of 0.


It also makes use of its superclass constructor for displayChar which will be "F"

Override the tick method to increment the age of the fruit and update the displayChar of fruit
to "x" once it is aged 20

~~~
age++
if age==20
    displayChar="x"
if age>20
    remove Fruit from Location of Fruit at map
~~~





**EcoPoints**

Create an **EcoPoint** class to represent ecopoint currencies

This class will have a private int attribute called **points** with initial value of 0.

This class will have two methods, **gain** and **spend**.

**gain** method will take in an int parameter and increases points according to the value given.
If the value given is <0, throw an Exception. The method will return void.

**spend** method takes in an int parameter and decreases point  according to the value given.
If the value given is <0, throw an Exception. The method will return void.

This fits the principle of Fail Fast where an exception is thrown immediately if the values given
does not fit the use.

Next we will override the toString method defined in Object class to return a String description of how many ecopoints left

The **gain** and **spend** methods will be called appropriately depending on the player actions etc as defined in the specifications

**VendingMachine**

We will also need to create a **VendingMachine** class that inherits Item class. This class 
should have one private HashMap attribute called **itemsSold** that stores all possible items sold in the vending machine and their price.

HashMap is used here because of it's constant time operations and the use of keys-value pair for items and the price

There should be a method called **displayItems** sold and their price that takes in no parameters and return a String.

pseudocode of this method

~~~

iterarte through the hashmap
    append to string key and value
return string
~~~

**MeaL Kit**

We should also create another class for MealKit to represent mealkits sold in vending machine.
It should have a private String attribute called **type** that is set in the constuctor.
This attribute will differentiate vegetarian meal kit and meat meal kit.

**Laser Gun**

A LaserGun class should be created that inherits the WeaponItem class. It will use the method of it's superclass

**Allosaur**

Create another class called Allosaur that will be similar to Stegasaur class except that the feeding behaviour will be different from an Allosaur

**HarvestAction Class**

We have to add this class to represent harvesting action by player. This class inherits the action class.
This class have a Grass type attribute named **grass** that will be set in the constructor

Then override execute method to change the grass boolean attribute to 
harvested==true.

Ovveride the description method to provide a String description of user action of that turn

**FruitAction Class**

The same as above but for fruit actions by Actor.
This class have a Fruit type attribute named **fruit** that will be set in the constructor.

We override the execute method to do:

1) Call PickUpItemAction method of Fruit item if Player decides to pick up fruit from Ground.

2) or this below if Player decides to pluck fruit from Tree instead
~~~
if Math.random>0.7
    add fruit to inventory
    change displayChar of Tree to "T"
    print string successful
else
    print “You search the tree for fruit, but you can’t find any ripe ones.”
~~~

Override the description method to provide a String description of user action of that turn

**VendingMachineAction Class**

Another subclass of Action class.
This class have a VendingMachine type attribute named **vendingMachine** that will be set in the constructor


Overload execute method that also takes in Item object parameter and return void.

It will check for players ecopoint and. It will throw exception if this condition are not met.


```
if player.points<item price
 throw exception
else
    spend ecopoints
    add item to inventory

```


Override the description method to provide a String description of user action of that turn

NOTE: Probabilty is subject to change

The overall design for this system has managed to reduce dependencies as much as possible, no uses of literal found
it adheres to the fail fast principle whenever possible. Usage of inheritance to avoid duplicate codes is also adhered to DRY principle.
Scope of class attributes are also private to prevent privacy leak.