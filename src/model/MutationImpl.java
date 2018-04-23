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
    private final Gene code;
    private int pos;
    private int na;
    private int zone;
    private int naMutate;
    private boolean check;
    private int randomMutation;
    private static final int RANGE = 10;

    /**
     * Construct a Bacteria's Genetic Code.
     * 
     * @param code
     *            the code of Gene.
     */
    public MutationImpl(final Gene code) {
        this.code = code;
        this.check = false;
    }

    private int checkMutation() {
        final Random rndMt = new Random();
        return rndMt.nextInt(RANGE);
    }
    private boolean possibilityOfMutation() {
        this.randomMutation = this.randomMutation + checkMutation();
        return this.randomMutation >= 10000;
    }

    private int randomPos() {
        final Random rndPos = new Random();
        this.pos = rndPos.nextInt(3);
        return this.pos;
    }

    private int randomNucleicAcid() {
        final Random randNa = new Random();
        this.na = randNa.nextInt(NucleicAcid.values().length);
        return this.na;
    }

    private int randomZone() {
        final Random randomZone = new Random();
        this.zone = randomZone.nextInt(4);
        return zone;
    }

    @Override
    public boolean isMutated() {
        return this.check;
    }

    @Override
    public void alteratedCode() {
        if (possibilityOfMutation()) {
            this.pos = randomPos();
            this.na = randomNucleicAcid();
            this.naMutate = randomNucleicAcid();
            this.zone = randomZone();
            this.randomMutation = 0;
            this.check = true;
            while (this.code.getCode().get(0).equals(NucleicAcid.values()[this.naMutate])) {
                this.naMutate = randomNucleicAcid();
            }
            this.code.getCode().set(0, NucleicAcid.values()[this.naMutate]);
            switch (this.zone) {
                case 1:
                    this.pos += ZONE_ACTIONS;
                    this.code.getCode().set(this.pos, NucleicAcid.values()[this.na]);
                    break;
                case 2:
                    this.pos += ZONE_SPEED;
                    this.code.getCode().set(this.pos, NucleicAcid.values()[this.na]);
                    break;
                case 3:
                    this.pos += ZONE_NUTRIENTS;
                    this.code.getCode().set(this.pos, NucleicAcid.values()[this.na]);
                    break;
                case 4:
                    this.pos += ZONE_51;
                    this.code.getCode().set(this.pos, NucleicAcid.values()[this.na]);
                    break;
                default:
                    this.code.getCode();
            }
        }
    }
}
