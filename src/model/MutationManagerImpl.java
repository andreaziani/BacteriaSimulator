package model;

import java.util.Map;
import java.util.Set;

import model.bacteria.Bacteria;

public class MutationManagerImpl implements MutationManager{
    private Map<Bacteria, Mutation> mutations;

    public void bacteriaDead(Bacteria bacteria) {
        mutations.remove(bacteria);
    }

    public void updateMutation(final Set<Bacteria> bactManager) {
            for (Bacteria b: bactManager) {
                if (!mutations.containsKey(b)) {
                    mutations.put(b, new MutationImpl(b.getGeneticCode().getCode()));
                }
                mutations.get(b).alteratedCode();
            }
     }
}
