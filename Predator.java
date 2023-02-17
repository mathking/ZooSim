/****************************************************************************** Interface:  Predator    Author:  Greg King      Date:  December 1, 2004 (last redesign 11-22-2013)Models the behavior of Lions in the simulationDate			Modification12-01-2004	Main coding on this class started11-22-2013	Changed from an abstract class to an interface*******************************************************************************/import java.awt.*;import java.util.*;public interface Predator{	/**	*	Method returns true if obj is a type the animal can eat,	*	returns false otherwise	*	@param	obj	object to be evaluated	*	@return true if obj can be eaten, false otherwise	*/	public boolean isSomethingICanEat(Animal obj);}