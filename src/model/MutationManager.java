package model;

import java.util.Set;

import model.bacteria.Bacteria;

public interface MutationManager {
    void bacteriaDead(Bacteria bacteria);
    void updateMutation(final Set<Bacteria> bactManager);
}
