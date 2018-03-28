package model;

import java.util.Random;

import model.geneticcode.Gene;
import model.geneticcode.NucleicAcid;

/**
 * 
 * Implementation of Mutation.
 *
 */

public class MutationImpl implements Mutation {

    private static final int ZONE_ACTIONS = 0;
    private static final int ZONE_SPEED = 3;
    private static final int ZONE_NUTRIENTS = 6;
    private static final int ZONE_51 = 9;
    private Gene code;

    /**
     * Construct a Bacteria's Genetic Code.
     * 
     * @param code
     *            the code of Gene.
     */
    public MutationImpl(final Gene code) {
        this.code = code;
    }

    @Override
    public void alteratedCode() {
        Random rndPos = new Random();
        int pos = rndPos.nextInt(3);
        Random randNa = new Random();
        int na = randNa.nextInt(NucleicAcid.values().length);
        Random randomZone = new Random();
        int zone = randomZone.nextInt(4);
        if (zone == 0) {
            pos += ZONE_ACTIONS;
            this.code.getCode().add(pos, NucleicAcid.values()[na]);
        } else if (zone == 1) {
            pos += ZONE_SPEED;
            this.code.getCode().add(pos, NucleicAcid.values()[na]);
        } else if (zone == 2) {
            pos += ZONE_NUTRIENTS;
            this.code.getCode().add(pos, NucleicAcid.values()[na]);
        } else if (zone == 3) {
            pos += ZONE_51;
            this.code.getCode().add(pos, NucleicAcid.values()[na]);
        }
    }

}
