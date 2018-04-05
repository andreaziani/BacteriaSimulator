package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.bacteria.Bacteria;
import model.bacteria.Species;

public class AnalysisImpl implements Analysis {
    
    @Override
    public List<Bacteria> listOfBacteria(final Map<Position, Bacteria> bacteria) {
        final List<Bacteria> list = new ArrayList<>();
        list.addAll(bacteria.values());
        return list;
    }

    @Override
    public Set<Species> speciesOfBacteria(final List<Bacteria> bacteria) {
        Set<Species> species = new HashSet<>(); //TODO va bene?
        for (final Bacteria bt : bacteria) {
            species.add(bt.getSpecies());
        }
        return species;
    }

    @Override
    public Map<Species, List<Bacteria>> dividedBySpecies(final Set<Species> species, final List<Bacteria> bacteria) {
        final Map<Species, List<Bacteria>> map = new HashMap<>();
        for (final Species sp : species) {
            final List<Bacteria> list = new ArrayList<>();
            for (final Bacteria bt : bacteria) {
                if (sp.equals(bt.getSpecies())) {
                    list.add(bt);
                }
            }
            map.put(sp, list);
        }
        return map;
    }

    @Override
    public Map<Species, Integer> numberBySpecies(final Set<Species> species, final List<Bacteria> bacteria) {
        final Map<Species, Integer> map = new HashMap<>();
        for (final Species sp : species) {
            int count = 0;
            for (final Bacteria bt : bacteria) {
                if (sp.equals(bt.getSpecies())) {
                    count++;
                }
            }
            map.put(sp, count);
        }
        return map;
    }

    @Override
    public Set<Species> dead(final Set<Species> species, final List<Bacteria> bacteria) {
        final Map<Species, Integer> map = numberBySpecies(species, bacteria);
        Set<Species> dead = new HashSet<>();
        for (Species sp : species) {
            int a = map.get(sp);
            if (a == 0) {
                dead.add(sp);
            }
        }
        return dead;
    }

    @Override
    public Map<Species, Integer> win(final Set<Species> species, final List<Bacteria> bacteria) {
        final Map<Species, Integer> map = numberBySpecies(species, bacteria);
        final Map<Species, Integer> wins = new HashMap<>();
        
        return null;
    }

    @Override
    public Map<Species, Integer> mutated(final Set<Species> species, final List<Bacteria> bacteria) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<Species, Integer> notMutated(final Set<Species> species, final List<Bacteria> bacteria) {
        // TODO Auto-generated method stub
        return null;
    }

}
