/********************************************************************

 Class:  Person  (extends Animal, implements Prey)
Author:  Greg King
  Date:  December 1, 2004 (last redesigned 12-3-2018)

Models the behavior of people who may or may not be infected with an
infectious disease. 

Date			Modification
03-14-2020		Main coding started, 11:58 pm

*********************************************************************/

import java.awt.*;
import java.util.*;


public class Person extends Animal
{
	private static double recoveryChance = 0.05;
	
	private double distancingFactor;
	private boolean isSick;
	private boolean isRecovered;
	private double infectionChance;
	/**
	*	Constructor creates a Person with Position 0,0.  Animal
	*	has no cage in which to live.
	*/
	public Person()
	{
		super();
		distancingFactor = 0;
		infectionChance = 1;
	}
	
	/**
	*	Constructor creates a Person in a random empty spot in
	*	the given cage.
	*	@param cage  the cage in which Person will be created.
	*/
	public Person(Cage cage)
	{
		super(cage, Color.blue);
		distancingFactor = 1.0;
		isRecovered = false;
		isSick = (Math.random()<=0.01);
		infectionChance = 1;
	}
	
	/**
	*	Constructor creates a Person in a random empty spot in
	*	the given cage.
	*	@param cage  the cage in which Person will be created.
	*	@param factor the chance that this Person moves in any given step
	*/
	public Person(Cage cage, double factor)
	{
		super(cage, Color.blue);
		distancingFactor = factor;
		isRecovered = false;
		isSick = (Math.random()<=0.01);
		infectionChance = 1;
	}
	
	/**
	*	Constructor creates a Person in a random empty spot in
	*	the given cage.
	*	@param cage  the cage in which Person will be created.
	*	@param sick  whether or not the Person is currently infected
	*	@param factor the chance that this Person moves in any given step
	*/
	public Person(Cage cage, boolean sick, double factor)
	{
		super(cage, Color.blue);
		distancingFactor = factor;
		isRecovered = false;
		isSick = sick;
		if(isSick==true)
			myColor = Color.red;
		infectionChance = 1;
	}
	
	/**
	*	Constructor creates a Person in a random empty spot in
	*	the given cage.
	*	@param cage  the cage in which Person will be created.
	*	@param sick  whether or not the Person is currently infected
	*	@param factor the chance that this Person moves in any given step
	*/
	public Person(Cage cage, boolean sick, double factor, double chance)
	{
		super(cage, Color.blue);
		distancingFactor = factor;
		isRecovered = false;
		isSick = sick;
		if(isSick==true)
			myColor = Color.red;
		infectionChance = chance;
	}
	
	/**
	*	Constructor creates a Person in a random empty spot in
	*	the given cage with the specified Color.
	*	@param cage  the cage in which Person will be created.
	*	@param color  the color of the Person
	*/
	public Person(Cage cage, Color color)
	{
		super(cage, color);
		distancingFactor = 1.0;
		isSick = (Math.random()<=0.01);
		isRecovered = false;
	}
	
	/**
	*	Constructor creates a Person in the given Position
	*	in the given Cage
	*	@param cage  the cage in which Person will be created.
	*	@param color  the color of the Person
	*	@param pos	the position of the Person
	*/
	public Person(Cage cage, Position pos)
	{
		super(cage, Color.blue, pos);
		distancingFactor = 1.0;
		isSick = (Math.random()<=0.01);
		isRecovered = false;
	}
	
	/**
	*	Constructor creates a Person in the given Position
	*	the given cage with the specified Color.
	*	@param cage  the cage in which Person will be created.
	*	@param color  the color of the Person
	*	@param pos	the position of the Person
	*/
	public Person(Cage cage, Color color, Position pos)
	{
		super(cage, color, pos);
		distancingFactor = 1.0;
		isSick = (Math.random()<=0.01);
		isRecovered = false;
	}
	
	/**
	*	Constructor creates a Person in the given Position
	*	the given cage with the specified Color.
	*	@param cage  the cage in which Person will be created.
	*	@param pos	the position of the Person
	*	@param factor the chance that this Person moves in any given step.
	*	@param sick  whether or not the person is currently infected
	*/
	public Person(Cage cage, Position pos, double factor, boolean sick)
	{
		super(cage, pos);
		myColor = Color.blue;
		distancingFactor = factor;
		isSick = sick;
		isRecovered = false;
	}
	
	
	/**
	*	Method overwrites the Act method in Animal.  
	*/
	public void act()
	{
		// Person acts as a generic Animal would act, 
		// meaning random movement
		if(generator.nextDouble()<=distancingFactor)
		{
			super.act();
			if(isSick == false && isRecovered == false)
			{
				ArrayList<Position> neighbors = myCage.nonEmptyNeighbors(myPos);
				for(Position temp : neighbors)
				{
					if(myCage.animalAt(temp) instanceof Person)
					{
						Person p = (Person)(myCage.animalAt(temp));
						if(p.sick()==true && isRecovered == false)
						{
							isSick = true;
							myColor = Color.red;
						}
					}
				}
			}
			if(isSick==true)
			{
				if(generator.nextDouble()<=recoveryChance)
				{
					isRecovered = true;
					myColor = Color.magenta;
					isSick = false;
				}
				
			}
		}
	}
	
	/**
	*	Method returns true if obj is a type that can eat 
	*	this Animal, returns false otherwise
	*	@param	obj	object to be evaluated
	*	@return true if obj can eat this Animal, false otherwise
	*/
	public boolean canItEatMe(Animal obj)
	{
		if(obj instanceof Predator)
			return true;
		return false;
	}
	
	public boolean sick()
	{
		return isSick;
	}
			
		
	/**
	*	Method returns the String form of the Animal's
	*	species, in this case "Person"
	*	@return	the String "Person"
	*/
	public String getSpecies()
	{
		return "Person";
	}
	
	
	/**
	 * Returns 0 for uninfected, 1 for infected and 2 for recovered
	 * @return the Person's infection state (0-2)
	 */
	public int getState()
	{
		int state;
		if(isSick == true)
			state = 1;
		else if (isRecovered == true)
			state = 2;
		else
			state = 0;
		return state;
	}
	
	
	/**
	 * Returns the idNumber and state of the Person, as a comma separated String
	 * @return the idNumber and state of the Person
	 */
	public String getData()
	{
		String temp = new String();
		int state;
		if(isSick == true)
			state = 1;
		else if (isRecovered == true)
			state = 2;
		else
			state = 0;
		temp = temp+idNumber+","+state;
		
		return temp;
	}
	
}