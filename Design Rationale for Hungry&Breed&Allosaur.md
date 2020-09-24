>>>>> Design for Hungry and Breeding Features
___
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
All the above designs are following the principle of Reduce Dependencies by reducing the dependencies within classes as much as possible. Moreover, the concept of inheritance have been applied in the design to avoid producing duplicated codes. Besides, concept of Exception handling also being used to apply the principal of fail fast into the design.

**All the designs above are subject to change

