package model.geneticcode;

/**
 * Implementation of interface Gene.
 */
public class GeneImpl implements Gene {

    private String code;

    /**
     * 
     * @param code
     *          the code of DNA. the..."name" of genetic code.
     */
    public GeneImpl(final String code) {
        this.code = code;
    }

    @Override
    public void setCode(final String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public int interpret() {
        return (this.getCode().get(0) + this.getCode().get(1));
    }

}
