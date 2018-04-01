package controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.Position;
import model.PositionImpl;
import view.model.bacteria.ViewBacteriaImpl;
import view.model.bacteria.ViewSpecies;
import view.model.food.ViewFoodImpl;

public class InitialState {
    private Map<PositionImpl, ViewBacteriaImpl> bacteriaMap;
    private Map<PositionImpl, ViewFoodImpl> foodMap;
    private final Set<ViewFoodImpl> existingFood;
    private final Set<ViewSpecies> species;
    private final double maxX;
    private final double maxY;

    public InitialState(double maxX, double maxY) {
        bacteriaMap = new HashMap<>();
        foodMap = new HashMap<>();
        existingFood = new HashSet<>();
        species = new HashSet<>();
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public void setState(HashMap<PositionImpl, ViewBacteriaImpl> bacteriaMap,
            HashMap<PositionImpl, ViewFoodImpl> foodMap) {
        this.bacteriaMap = bacteriaMap;
        this.foodMap = foodMap;
    }

    public void addFood(ViewFoodImpl food) {
        existingFood.add(food);
    }

    public void addSpecies(ViewSpecies species) {
        this.species.add(species);
    }

    public Map<PositionImpl, ViewBacteriaImpl> getBacteriaMap() {
        return bacteriaMap;
    }

    public Map<PositionImpl, ViewFoodImpl> getFoodMap() {
        return foodMap;
    }

    public Set<ViewFoodImpl> getExistingFood() {
        return existingFood;
    }

    public Set<ViewSpecies> getSpecies() {
        return species;
    }

    public Position getMaxPosition() {
        return new PositionImpl(maxX, maxY);
    }
}
