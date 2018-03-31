package controller;

import java.util.HashMap;
import java.util.HashSet;

import model.PositionImpl;
import view.model.bacteria.ViewBacteriaImpl;
import view.model.bacteria.ViewSpecies;
import view.model.food.ViewFoodImpl;

public class InitialState {
    private HashMap<PositionImpl, ViewBacteriaImpl> bacteriaMap;
    private HashMap<PositionImpl, ViewFoodImpl> foodMap;
    private final HashSet<ViewFoodImpl> existingFood;
    private final HashSet<ViewSpecies> species;

    public InitialState() {
        bacteriaMap = new HashMap<>();
        foodMap = new HashMap<>();
        existingFood = new HashSet<>();
        species = new HashSet<>();
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

    public HashMap<PositionImpl, ViewBacteriaImpl> getBacteriaMap() {
        return bacteriaMap;
    }

    public HashMap<PositionImpl, ViewFoodImpl> getFoodMap() {
        return foodMap;
    }

    public HashSet<ViewFoodImpl> getExistingFood() {
        return existingFood;
    }

    public HashSet<ViewSpecies> getSpecies() {
        return species;
    }
}
