package app;

import java.util.Random;

public class Asteroid extends Character {
    static Random rnd = new Random();
    private static double size = 20 + rnd.nextInt(10);
    private static double sizeIncrease = 1;
    private double rotationalMovement;

    public Asteroid(int x, int y) {
        super(new PolygonFactory().createPolygon(size), x, y);

        getCharacter().setRotate(rnd.nextInt(360));

        double initialAccelerationTime = (1 + rnd.nextInt(10)) / 60.0;
        accelerate(initialAccelerationTime);

        this.rotationalMovement = (0.5 - rnd.nextDouble()) * 60;
    }

    @Override
    public void move(double deltaTime) {
        super.move(deltaTime);
        if (getCharacter().getTranslateX() < 0) {
            getCharacter().setTranslateX(getCharacter().getTranslateX() + AsteroidsApplication.WIDTH);
        }

        if (getCharacter().getTranslateX() > AsteroidsApplication.WIDTH) {
            getCharacter().setTranslateX(getCharacter().getTranslateX() % AsteroidsApplication.WIDTH);
        }

        if (getCharacter().getTranslateY() < 0) {
            getCharacter().setTranslateY(getCharacter().getTranslateY() + AsteroidsApplication.HEIGHT);
        }

        if (getCharacter().getTranslateY() > AsteroidsApplication.HEIGHT) {
            getCharacter().setTranslateY(getCharacter().getTranslateY() % AsteroidsApplication.HEIGHT);
        }
        getCharacter().setRotate(getCharacter().getRotate() + rotationalMovement * deltaTime);
    }

    public static void setSize(double newSize) {
        sizeIncrease = newSize;
        size *= sizeIncrease;
    }
}