package com.sylvia.createbuttercat.block;

public class SuperButterBlock extends ButterBlock{

    public SuperButterBlock(Properties properties) {
        super(properties);
    }
    @Override
    public int getAmplifier() {return 5;}
    @Override
    public int getDuration() {return 20;}
    @Override
    public boolean canEffectPlayer(){return true;}
}
