package model;

import java.util.Optional;

public interface Action {
	Optional<Direction> getDirection();
	ActionType getType();
}
