package org.jikvict.tasks.exposed;

public abstract class PotionDecorator implements Potion {
    private Potion wrappedPotion;

    public PotionDecorator(Potion wrappedPotion) {
        this.wrappedPotion = wrappedPotion;
    }

    @Override
    public String getName() {
        return wrappedPotion.getName();
    };

    @Override
    public String getDescription() {
        return wrappedPotion.getDescription();
    };

    public int getHealingPower() {
        return  wrappedPotion.getHealingPower();
    }

    @Override
    public int getDuration() {
        return wrappedPotion.getDuration();
    }

    @Override
    public void apply(GameCharacter target) throws GameException {
        wrappedPotion.apply(target);
    };
}
