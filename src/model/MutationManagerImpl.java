package model;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import model.bacteria.Bacteria;
/**
 * 
 * Implementation of MutationManagerImpl.
 *
 */
public class MutationManagerImpl implements MutationManager {
    private Map<Bacteria, Mutation> mutations;

    @Override
    public void bacteriaDead(final Bacteria bacteria) {
        mutations.remove(bacteria);
    }

    @Override
    public void updateMutation(final Collection<Bacteria> bactManager) {
            for (Bacteria b: bactManager) {
                if (!mutations.containsKey(b)) {
                    mutations.put(b, new MutationImpl(b.getGeneticCode().getCode()));
                }
                mutations.get(b).alteratedCode();
            }
     }
    public Map<Bacteria, Mutation> getMutation(){
        return this.mutations;
    }
}
