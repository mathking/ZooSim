/********************************************************************

 Class:  Rabbit (extends Animal, implements Prey)
Author:  Greg King
  Date:  October 7, 2019

Models the behavior of Gazelles in the simulation

Date			Modification
12-01-2004	Main coding on this class started
11-22-2013	Redesigned to implement Prey interface
12-03-2018	Redesigned the class for (row, col) rather than (x, y)
10-07-2019  Rewritten for 2019

*********************************************************************/

import java.awt.Color;
import java.util.ArrayList;

public class Rabbit extends Animal 
{
	private static double visualRange = 15.0;
	private static double fertilityRate = 0.10;
	
	private boolean isFemale;
	
	/**
	*	Constructor creates a Rabbit with Position 0,0.  Animal
	*	has no cage in which to live.
	*/
	public Rabbit()
	{
		super();
		isFemale = true;
		if(Math.random()<0.5)
			isFemale = false;
	}
	
	/**
	*	Constructor creates a Rabbit in a random empty spot in
	*	the given cage.
	*	@param cage  the cage in which Rabbit will be created.
	*/
	public Rabbit(Cage cage)
	{
		super(cage, Color.magenta);
		isFemale = true;
		if(Math.random()<0.5)
			isFemale = false;
	}
	
	
	/**
	*	Constructor creates a Rabbit in a random empty spot in
	*	the given cage with the specified Color 
	*	@param cage  the cage in which Rabbit will be created.
	*	@param color  the color of the Rabbit
	*/
	public Rabbit(Cage cage, Color color)
	{
		super(cage, color);
		isFemale = true;
		if(Math.random()<0.5)
			isFemale = false;
	}
	
	
	/**
	*	Constructor creates a Rabbit in the given Position
	*	the given cage with the specified Color.
	*	@param cage  the cage in which Rabbit will be created.
	*	@param pos	the position of the Rabbit
	*/
	public Rabbit(Cage cage, Position pos)
	{
		super(cage, Color.magenta, pos);
		isFemale = true;
		if(Math.random()<0.5)
			isFemale = false;
	}
	
	
	/**
	*	Constructor creates a Rabbit in the given Position
	*	the given cage with the specified Color.
	*	@param cage  the cage in which Rabbit will be created.
	*	@param color  the color of the Rabbit
	*	@param pos	the position of the Rabbit
	*/
	public Rabbit(Cage cage, Color color, Position pos)
	{
		super(cage, color, pos);
		isFemale = true;
		if(Math.random()<0.5)
			isFemale = false;
	}
	
	/**
	*	Method sets the Rabbit's visual range to the given value.
	*	@param range  sets the Rabbit's visual range to 'range'
	*/
	public void setVisualRange(double range)
	{
		visualRange = range;
	}
	
	
	/**
	*	Method overwrites the Act method in Animal.  Rabbit will 
	*	attempt to move away from a Predator if it sees the Predator.
	*/
	public void act()
	{
		Animal closestPredator = findClosestPredator();
		
		// In this case it sees a predator and tries to run away
		if(closestPredator instanceof Predator)
		{
			int predatorRow = closestPredator.getPosition().getRow();
			int predatorCol = closestPredator.getPosition().getCol();
			int myRow = myPos.getRow();
			int myCol = myPos.getCol();
			Position newPos, oldPos = new Position(myRow, myCol);
		
			if(predatorRow > myRow && myRow > 0)
				myRow--;
			else if (predatorRow < myRow && myRow < myCage.numRows()-1)
				myRow++;
			if(predatorCol > myCol && myCol > 0)
				myCol--;
			else if(predatorCol < myCol && myCol < myCage.numCols()-1)
				myCol++;
			newPos = new Position(myRow, myCol);
			
			// Rabbit could not move away, so it moves as a 
			// generic Animal, which means randomly
			if(newPos.equals(oldPos))
				super.act();
			// Rabbit moves to new position which is empty
			else if (myCage.isEmptyAt(newPos))
			{
				myPos = newPos;
				myCage.moveAnimal(oldPos, this);
			}
			// Rabbit could not move to a new location because
			// it was not empty, so it moves as a generic Animal
			else
			{
				super.act();
			}
		}
		else
		{
			super.act();
		}
		
		if(isFemale == true)
		{
			if(Math.random()<fertilityRate)
			{
				reproduce();
			}
		}
	}
	
	/**
	*	Method returns the closest Predator to the Rabbit provided that 
	*	Predator is also within the Rabbit's visual range, if no Predators
	*	are seen the method returns a generic Animal.
	*	@return	closest Predator the Rabbit can see
	*/
	public Animal findClosestPredator()
	{
		Animal closestPredator = new Animal(myCage);
		double distanceToClosest = visualRange+.01;
		// Distance set to just longer than a Rabbit can see
		
		for(int r=0; r<myCage.numRows(); r++)
		{
			for(int c=0; c<myCage.numCols(); c++)
			{
				if(myCage.animalAt(r,c) instanceof Predator)
				{
					if(myPos.distanceTo(new Position(r,c)) < distanceToClosest)
					{
						closestPredator = myCage.animalAt(r,c);
						distanceToClosest = myPos.distanceTo(new Position(r,c));
					}
				}
			}
		}	
		return closestPredator;
	}
	
	
	/**
	 * Allows a female Rabbit to reproduce if there is a compatible male Rabbit nearby.
	 */
	public void reproduce()
	{
		// Check to see if there is a compatible (male) Rabbit nearby
		ArrayList<Position> neighbors = myCage.nonEmptyNeighbors(myPos);
		boolean breed = false;
		for(Position temp : neighbors)
		{
			Animal an = myCage.animalAt(temp.getRow(), temp.getCol());
			if(an instanceof Rabbit)
			{
				Rabbit r = (Rabbit)an;
				if(r.isMale()==true)
				{
					breed = true;
				}
			}
		}
		
		// Add some baby Rabbits if there was a compatible make nearby
		if(breed==true)
		{
			ArrayList<Position> empties = myCage.emptyNeighbors(myPos);
			int maxBabies = (int)(Math.random()*4+1);
			for(int i=0; i<empties.size() && i<maxBabies; i++)
			{
				Rabbit baby = new Rabbit(myCage, empties.get(i));
				myCage.addAnimal(baby);
			}
		}
	}
	
	
	/**
	*	Method returns the String form of the Animal's
	*	species, in this case "Rabbit"
	*	@return	the String "Rabbit"
	*/
	public String getSpecies()
	{
		return "Rabbit";
	}
	
	
	/**
	 * Returns true if Rabbit is a male, false otherwise.
	 * @return true if Rabbit is male, false otherwise.
	 */
	public boolean isMale()
	{
		return !isFemale;
	}
}
