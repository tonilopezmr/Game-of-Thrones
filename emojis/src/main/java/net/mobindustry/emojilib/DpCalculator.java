package net.mobindustry.emojilib;

public class DpCalculator {

    public final float density;

    public DpCalculator(float density) {
        this.density = density;
    }

    public int dp(float value) {
        return (int) Math.ceil(density * value);
    }
}
