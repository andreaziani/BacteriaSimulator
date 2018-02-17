package model;

import java.util.Optional;

public interface Perception {
	Optional<Food> getFood();
	double distFromFood(Direction dir);
}
