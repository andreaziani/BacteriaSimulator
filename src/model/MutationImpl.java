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

    private static final int ZONE_ACTIONS = 1;
    private static final int ZONE_SPEED = 4;
    private static final int ZONE_NUTRIENTS = 7;
    private static final int ZONE_51 = 10;
    private Gene code;
    private int pos;
    private int na;
    private int zone;
    private int naMutate;

    /**
     * Construct a Bacteria's Genetic Code.
     * 
     * @param code
     *            the code of Gene.
     */
    public MutationImpl(final Gene code) {
        this.code = code;
    }

    private int randomPos() {
        int pos;
        Random rndPos = new Random();
        pos = rndPos.nextInt(3);
        return pos;
    }

    private int randomNucleicAcid() {
        int na;
        Random randNa = new Random();
        na = randNa.nextInt(NucleicAcid.values().length);
        return na;
    }

    private int randomZone() {
        int zone;
        Random randomZone = new Random();
        zone = randomZone.nextInt(4);
        return zone;
    }

    @Override
    public void alteratedCode() {
        this.pos = randomPos();
        this.na = randomNucleicAcid();
        this.naMutate = randomNucleicAcid();
        this.zone = randomZone();
        while (this.code.getCode().get(0).equals(NucleicAcid.values()[naMutate])) {
            this.naMutate = randomNucleicAcid();
        }
        this.code.getCode().add(0, NucleicAcid.values()[naMutate]);
        switch (zone) {
            case 1:
                pos += ZONE_ACTIONS;
                this.code.getCode().add(pos, NucleicAcid.values()[na]);
                break;
            case 2:
                pos += ZONE_SPEED;
                this.code.getCode().add(pos, NucleicAcid.values()[na]);
                break;
            case 3:
                pos += ZONE_NUTRIENTS;
                this.code.getCode().add(pos, NucleicAcid.values()[na]);
                break;
            case 4:
                pos += ZONE_51;
                this.code.getCode().add(pos, NucleicAcid.values()[na]);
                break;
            default:
                this.code.getCode();
        }

    }
}
