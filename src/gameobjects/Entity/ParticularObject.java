package gameobjects.Entity;

import effect.Animation;
import gameobjects.Gamemanager.GameWorld;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;

public abstract class ParticularObject extends GameObject {
    public static final int LEAGUE_TEAM = 1;
    public static final int ENEMY_TEAM = 2;
    public static final int BOT_TEAM = 3;
    public static final int ITEM_TEAM = 4;

    public static final int LEFT_DIR = 0;
    public static final int RIGHT_DIR = 1;

    public static final int ALIVE = 0;
    public static final int BEHURT = 1;
    public static final int FEY = 2;
    public static final int DEATH = 3;
    public static final int NOTBEHURT = 4;
    public int state = ALIVE;
    private boolean isTimeLimit;
    private boolean isDistanceLimit;
    private boolean isFrameLimit;
    public long startSpeedUp;
    public long startSpeedDown;
    private double width;
    private double height;
    private double mass;
    private double speedX;
    private double speedY;
    private int blood;
    private int mana;
    private int damage;
    
    private int direction;
    
    protected Animation beHurtForwardAnim, beHurtBackAnim;

    private int teamType;

    private long startTimeNotBeHurt;
    private long timeNotBeHurt;

    public ParticularObject(double posX, double posY, GameWorld gameWorld, double width, double height, double mass,
            int blood) {
        super(posX, posY, gameWorld);
        this.width = width;
        this.height = height;
        this.mass = mass;
        this.blood = blood;
        this.isTimeLimit=false;
        this.isDistanceLimit=false;
        this.isFrameLimit=false;

        direction = RIGHT_DIR;
    }

    


    public boolean getIsFrameLimit() {
        return isFrameLimit;
    }


    public void setFrameLimit(boolean isFrameLimit) {
        this.isFrameLimit = isFrameLimit;
    }


    public void setMana(int mana) {
        this.mana=mana;
    }

    public int getMana() {
        return mana;
    }

    public void setIsDistanceLimit(boolean isDistanceLimit) {
        this.isDistanceLimit=isDistanceLimit;
    }

    public boolean getIsDistanceLimit() {
        return isDistanceLimit;
    }

    public void setIsTimeLimit(boolean isTimeLimit) {
        this.isTimeLimit=isTimeLimit;
    }

    public boolean getIsTimeLimit() {
        return isTimeLimit;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getSpeedX() {
        return speedX;
    }

    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }

    public int getBlood() {
        return blood;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public Animation getBeHurtForwardAnim() {
        return beHurtForwardAnim;
    }

    public void setBeHurtForwardAnim(Animation beHurtForwardAnim) {
        this.beHurtForwardAnim = beHurtForwardAnim;
    }

    public Animation getBeHurtBackAnim() {
        return beHurtBackAnim;
    }

    public void setBeHurtBackAnim(Animation beHurtBackAnim) {
        this.beHurtBackAnim = beHurtBackAnim;
    }

    public int getTeamType() {
        return teamType;
    }

    public void setTeamType(int teamType) {
        this.teamType = teamType;
    }

    public long getStartTimeNotBeHurt() {
        return startTimeNotBeHurt;
    }

    public void setStartTimeNotBeHurt(long startTimeNotBeHurt) {
        this.startTimeNotBeHurt = startTimeNotBeHurt;
    }

    public long getTimeNotBeHurt() {
        return timeNotBeHurt;
    }

    public void setTimeNotBeHurt(long timeNotBeHurt) {
        this.timeNotBeHurt = timeNotBeHurt;
    }

    public Rectangle getBoundForCollisionWithMap() {
        Rectangle bound = new Rectangle();
        bound.x = (int) (getPosX() - (getWidth() / 2));
        bound.y = (int) (getPosY() - getHeight() / 2);
        bound.width = (int) getWidth();
        bound.height = (int) getHeight();
        return bound;
    }

    public void beHurt(int damageEat) {
            setBlood(getBlood() - damageEat);
            state = BEHURT;
            hurtingCallBack();
    }

    @Override
    public void Update() {
        switch (state) {
            case ALIVE:
                ParticularObject object = getGameWorld().particularObjectManager.getCollisionWithEnemyObject(this);
                if(object!=null) {
                    if(object.getDamage()>0) {
                        beHurt(object.getDamage());
                    }
                }
                break;
            case BEHURT:

                if (getBlood() <= 0) {
                    state = FEY;
                } else {
                    if (beHurtBackAnim == null) {
                        state = NOTBEHURT;
                        startTimeNotBeHurt = System.nanoTime();
                    } else {
                        beHurtForwardAnim.Update(System.nanoTime());
                        if (beHurtForwardAnim.isLastFrame()) {
                            beHurtForwardAnim.reset();
                            state = NOTBEHURT;
                            startTimeNotBeHurt = System.nanoTime();
                        }
                    }
                }
                break;
            case FEY:
                state = DEATH;
                break;
            case DEATH:
                break;
            case NOTBEHURT:
                
                if (System.nanoTime() - startTimeNotBeHurt > timeNotBeHurt) {
                    state = ALIVE;
                }
                break;
        }
    }

    public void drawBoundForCollisionWithMap(Graphics2D g2) {
        Rectangle rect = getBoundForCollisionWithMap();
        g2.setColor(Color.BLUE);
        g2.drawRect(rect.x, rect.y, rect.width, rect.height);
    }

    public void drawBoundForCollisionWithEnemy(Graphics2D g2) {
        Rectangle rect = getBoundForCollisionWithEnemy();
        g2.setColor(Color.BLUE);
        g2.drawRect(rect.x, rect.y, rect.width, rect.height);
    }


    public abstract void attack();
    public abstract void superAttack();

    public abstract Rectangle getBoundForCollisionWithEnemy();

    public abstract void draw(Graphics2D g2);

    public void hurtingCallBack() {

    }

}