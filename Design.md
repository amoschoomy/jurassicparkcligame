#**Design for Assignment 1**

##NOTE PROBABILITES is now changed accordingly to the game. Check the gamefiles.


**Grass**

We have to create a new class called **Grass**. This class will inherit the abstract class Ground. 
By doing this, it will inherit the superclass methods, therefore adhering to the Don't Repeat Yourself principle.

The class will make use of the constructor attribute of **displayChar** in the superclass Ground.

This fits the principle of declaring variables in the tightest possible scope


We will then override the method tick in the superclass Ground to fit in features according to the specifications.

A pseudocode for the method tick in the class Grass

**Hay**

This is for hay class where it comes from harvesting grass. This class will extend PortableItem class
because it is an item that can be picked up or dropped. Its display char will be 'G'.
                                                                                                  
~~~

if (location of grass coordinate x+1 && location of grass coordinate x+2) and Math.random>0.3 and 
if (location.x() + 3 <= location.map().getXRange().max())
    add grass to location grass coordinate x+3
if (location of grass coordinate y+1 && location of grass coordinate y+2) and Math.random>0.3 and
if (location.y()+3 <= location.map().getYRange().max())
    add grass to location grass coordinate y+3
if (location of grass coordinate x-1 && location of grass coordinate x-2) and Math.random>0.3 and
if (location.x() - 3 <= location.map().getXRange().min())
    add grass to location grass coordinate x-3
if (location of grass coordinate y-1 && location of grass coordinate y-2) and Math.random>0.3 and
if (location.y() - 3 <= location.map().getYRange().min())
    add grass to location grass coordinate y-3
                                                    
~~~
**Application**

In the application class, we have to calculate the chances of dirt growing grass. We have set a 40 percent chance temporarily for the grass to grow.
.

Pseudocode

~~~
for (int i = 0; i < map.size(); i++) {
      int x = 0 + (int) (Math.random() * ((map.size() - 1) + 1));
      int y = 0 + (int) (Math.random() * ((map.size() - 1) + 1));
      if (Math.random() > 0.8) gameMap.at(x, y).setGround(new Grass());
    }
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

if tree left or right neighbour == dirt and Math.random >0.2
    add grass to tree left or right neighbour
if tree front or back negihbour == dirt and Math.random>0.2
    add grass to tree front or back neighbour

~~~
Also add the neccesarry methods such as addFruittoTree, removeFruitfromTree which both returns void and adds
or remove Fruit. Add getFruits method which returns an arraylist of fruits in the Tree

**Fruit**

Create simple class named **Fruit** to represent fruit. Inherits PortableItem class.

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

Also add another method called addItemsToVendingMachine to add items sold, takes in a Item object and integer price parameter,
Add those key-value pair to the HashMap.

pseudocode of this method

~~~

iterarte through the hashmap
    append to string key and value
return string
~~~

Add method getItemPrice, which throws an IllegalArgumentException if item is not sold in Vending MAchine.
Add method SellItem which takes in two parameters, a String representation of the itemSold and the owner of that item
and returns an Item to be sold

**Modifications to Player class**
Add Ecopoint type attribute with initial value of 0. Add those necessary setters and getters

**Meal Kit**

We should also create another class for MealKit to represent mealkits sold in vending machine.
It should have a private FoodType(Enum) attribute called **type** that is set in the constuctor.
This attribute will differentiate vegetarian meal kit and meat meal kit.

**Laser Gun**

A LaserGun class should be created that inherits the WeaponItem class. It will use the method of it's superclass

**HarvestAction Class**

We have to add this class to represent harvesting action by player. This class inherits the action class.
This class have a Grass type attribute named **grass** that will be set in the constructor

Override the execute method and inside the method, call harvest method of the Grass class to harvest grass into hay.
Then return a string detailing what is happening.

Ovveride the description method to provide a String description of user action of that turn

**How to Pickup Fruit?**
There is a class in PickupItemAction already, so since Fruit is an Item, we will just use PickUpItemAction class to avoid duplication


**PluckFruitAction Class**
If Player decides to pluck fruit from Tree instead, this class should be used instead. This class will extend Action class.
Override the execute method as follows:


~~~
if Math.random>0.7
    add fruit to inventory
    remove fruit from Tree
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
#  Hungry Dinosaur

> To implement the feature of hungry dianosaur to allow dinosaur to feel hungry without eating food.

**Operations on existing class :** 

* Create 3 new instance variables respectively in Stegosaur and Allosaur class:
		
	* an int variable called **foodLevel** , set its initial value as 50. For every turn, the foodLevel will deduct 1. 

	* an int instance variable called **age** , set its initial value as 0 , it is a value for us to track how many turns/age the dinosaur exists in the game so we can define the lifeStage for the dinosaur.For example, the age of dinosaur egg is 0. For every turn , the age will increase 1.

	* an int instance variable called **starvation** , its initial value is 0. When foodLevel == 0 , starvation will increase 1 every turn, if foodLevel >0 , starvation will set to zero again. This variable is used to track how many turns the dinosaur are in starving state (foodLevel ==0).
	

- Create a function called **Starving()** , it will set dinosaur.hitPoints as 0 to make the dinosaur become unconscious, add 1 to the starvation variable. 

- If the foodLevel ==   0 , it will stop moving.

  		if foodLevel==0:

 			Then dinosaur.Starving()

- After 20 turns , the dinosaur.starvation  == 20 , call out map.removeActor(dinosaur) to remove the dinosaur from the game map to show that the dinosaur is dead.

- Add an exception handling for the foodLevel to set its range between 0 and 100, if lower than 0 or higher 100 , throw an Exception.

		if foodLevel>100 || foodLevel < 0:
			Then throw Exception

- If the foodLevel is lower than 50, the dinosaur will become hungry and it's behaviour instance variable will be set as hungryBehaviour.

		if foodLevel < 50 : 
			behaviour = new HungryBehaviour()

- Create a functions in Stegosaur class to implement its eat feature:
	* eatGrass(Grass grass) : this function is used for stegosaur to eat grass. Add 5 to the dinosaur foodLevel. The grass after eaten will be removed from the map and become a dirt .

	* eatFruit(Item fruit) : this function is used for stegosaur to eat fruit. add 30 to the dinosaur foodLevel per fruit eaten.

- Create a functions in Allosaur class to implement its eat feature:
	* eat(Dinosaur dianosaurMeat) : this function is used for allosaur to eat , check the food.lifeStage to decide what foodLevel the dinosaur will gain. 

			dianosaurMeat.lifeStage == "egg" : foodLevel += 10 
			dianosaurMeat.lifeStage == "babyDinosaur" : foodLevel += 20
			dianosaurMeat.lifeStage == "adult" : foodLevel += 50 

	



**Operation of HungryBehaviour class:**

- Create a class called 'HungryBehaviour' , a class implement the Behaviour interface 

- Create instances variable :
	* HashSet<List<int,int>> foodSourceLocation

	
- Use if else statement to check if the dinosaur an instance of Stegosaur or Allosaur to differentiate what kind of food source they looking for.

- Create a function called **findFoodLocation(dinosaur,List)** , this function will have 2 for loops which takes a parameter of a String List (The string list we construct as the game map in driver class) , this function will save the locations of all the displaySymbols that represent as the food source for the dinosaur into the instance variable foodSourceLocation.

- Create a helper method called **distance()**, a method similar to the FollowBehaviour class distance method , This method will help us to keep tracking the distance between the dinosaur location with its each food source locations in the map.

- Create another function called **findTheClosestFood()** , this method will compare the distance of each food source to the dinosaur, then return the closest food location.

- Next, we overwrite the function **getAction()** , algorithm for this function basically is we get the location of the dinosaur by using the map.getlocationOf(dinosaur) and set the destination as the location of closest food. Then we called out the MoveActorAction method to move the dinosaur 1 step closer to the destination each turn. This way slightly similar to the way we did in the FollowBehaviour getAction method , we keep moving the dinosaur step by step closer to the closest food location until it reached the food locaiton.Then call out the eat function to eat the food. For carnivores like allosaur, it will not reach the location of the food source like stegosaur, it will reach locations adjacent to the food source then attack the food source if it is still alive or eat them if they are dead.


- Create a String return function **displayHungryMessage()** , to display hungry message when dinosaur is hungry, the message will be like : "stegosaur at (19.6) is getting hungry"

		String displayHunryMessage():
			return "Dinosaur at (x,y) is getting hungry"

___


# Breed Behaviour

> To implement the feature of breeding for dianosaur to lay egg and produce new dianosaur.

**Operations on existing class :**

- Create 2 new instance variables in Stegosaur and Allosaur classes : 

 	1. String sex : "M" represent as Male  / “F” represent as Female

	2. String lifeStage : "egg " / “baby” / “adult” (egg stands as an egg of the dinosaur, baby stands as baby dinosaur , adult stands as adult dinosaur)

- Inside the dinosaur class constructor, add an if statement to check if the dinosaur sex is female , it will have a new instance variable: Queue<Dinosaur>**eggNest**,this variable is used to store eggs of dinosaur after they mating.

		if dinosaur.sex == "F" : 
			Queue<Dinosaur> eggNest = new LinkedList<>() 

- Create a boolean return function **readyToBreed()** this function will return check If the foodLevel of the dinosaur is greater than 50 or not , if it does , the function will return true , if not it will return false.

- When the function **readyToBreed()** return **true**, the dinosaur behaviour will be set as '**BreedingBehaviour**'.

		if readyToBreed():
			behaviour = new BreedingBehaviour() 

- Create a new function called **eggHatch()** , this function will change the lifeStage of dinosaur from "egg" to “baby” after 10 Game turns (dinosaur.age == 10), meaning that the egg is hatched and from the GameMap the places adjacent to the female dinosaur who carry the egg will pop out a displayChar represents the baby dinosaur with foodLevel = 10.Then, the dinosaur will be removed from the eggNest ArrayList and become an independent dinosaur object.

- Create a new function called **becomeAdult()**, for baby dinosaur when the game turns reach 30 turns(dinosaur.age ==30), we called out this function to set the lifeStage of the dinosaur become "adult" (dinosaur.setlifeStage("adult")), meaning that the baby dinosaur has grown to become an adult dinosaur.

	
**Operation of the BreedingBehaviour class:**
 - We decided to allow female dinasaur to have BreedingBehaviour , let the female dinosaur to look for male dinosaur for breeding.

 - When female dinasaur behaviour set as BreedingBehaviour , they will started searching if there is an opposite sex but same species dinasaur is located at the locations adjacent to it. Once it found, the female dinosaur can call out mating() method by taking male dinasaur as parameter. 

 - The initial implementation of the looking partner algorithm will be similar to the looking foodSource algorithm mentioned above in the hungry feature design. We look for the same species dinosaur by searching same displayChar from the game Map. Then we store the location of those dinosaur with same species but opposite sex in an instance variable ``HashSet<List<int,int>> PartnerLocation``. Then, we apply the FollowBehaviour method by moving the female dinosaur one step closer to it's partner location.

 - Only when dinosaur.lifeStage == "adult" && dinosaur.foodLevel > 50 && dinosaur.sex == "F":  behaviour can be set as  BreedingBehaviour.

- **mating()** is a function to add a dinosaur with lifeStage as "egg" inside the eggNest instance variable.

		function mating():
			After 10 turns:
				For dinosaur.sex == "F":
					dinosaur.eggNest.add(new Dinosaur("egg")

- Create a String return function **displayBreedingMessage()** , to display hungry message when female dinosaur is in breeding state, the message will be like : "stegosaur at (19.6) is looking partner for breeding"

		String displayBreedingMessage():
			return "Female dinosaur at (x,y) is looking partner for breeding"					

___

# Allosaur

> Another type of dianosaur but unlike stegosaur, it is carnivores.

- A children class of Actor
- Class instance variables: 
	* Behaviour behaviour : represent the behaviour of the allosaur
	* Int age : represent the age or how many game turns the allosaur exists in the game
	* Int foodLevel : represent the energy of the allosaur, eat food to gain foodLevel
	* Int starvation : represent the the time the allosaur inconscious due to hunger
	* String sex : represent the gender of allosaur
	* String lifeStage : represent the life group the dianosaur belongs to
	* Queue<Dianosaur> eggNest : For female allosaur to store eggs inside 

- A Constructor take a String name as parameter , set the name as the allosaur name. 

- displayChar : "a" (subject to change)

- Functions : 
	* getAllowableActions (Same as Stegosaurs)
	* playTurn (Same as Stegosaurs)
	* eat (Mentioned above in the hungry feature implementation)
	* Starving (Mentioned above in the hungry feature implementation)
	* readyToBreed (Mentioned above in the breeding feature implementation)
	* eggHatch (Mentioned above in the breeding feature implementation)
	* becomeAdult (Mentioned above in the breeding feature implementation)


## Note: 

Override the description method to provide a String description of user action of that turn

Probabilty is subject to change

The overall design for this system has managed to reduce dependencies as much as possible, no uses of literal found
it adheres to the fail fast principle whenever possible. Usage of inheritance to avoid duplicate codes is also adhered to DRY principle.
Scope of class attributes are also private to prevent privacy leak. 

All the above designs are following the principle of Reduce Dependencies by reducing the dependencies within classes as much as possible. Moreover, the concept of inheritance have been applied in the design to avoid producing duplicated codes. Besides, concept of Exception handling also being used to apply the principal of fail fast into the design.

**All the designs above are subject to change