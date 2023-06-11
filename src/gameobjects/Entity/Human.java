package gameobjects.Entity;

import java.awt.Rectangle;

import effect.Animation;
import effect.Sound;
import gameobjects.Gamemanager.GameWorld;

public abstract class Human extends ParticularObject {
    private boolean isJumping;
    private boolean isDicking;
    private boolean isLanding;
    private long beginTimeMana;
    private int posNow;
    public boolean isCollision = false;
    public boolean isRunning = false;
    public boolean permitToRun = true;
    public long lastRunningTime;
    public long lastPunchTime;
    public boolean isPunch = false;
    public long timeDeath = 0;
    public boolean timeCheck = false;
    public boolean isSuperAttack = false;
    private double walkSpeed = 3;
    private double runSpeed = 10;
    private int distanceRun = 400;

    Animation flyBackAnim, flyForwardAnim;
    Animation idleForwardAnim, idleBackAnim;
    Animation dickForwardAnim, dickBackAnim;
    Animation runForwardAnim, runBackAnim;
    Animation walkForwardAnim, walkBackAnim;
    Animation idlePunchForwardAnim, idlePunchBackAnim, idleSuperAttackForwardAnim, idleSuperAttackBackAnim;
    Animation landingForwardAnim, landingBackAnim;
    Animation deathForwardAnim, deathBackAnim;
    private Sound sound;

    public Human(double posX, double posY, GameWorld gameWorld, double width, double height, double mass, int blood,
            int mana) {
        super(posX, posY, gameWorld, width, height, mass, blood);
        setState(ALIVE);
        setMana(mana);
        beginTimeMana = 0;
        sound = new Sound();
    }

    public Sound getSound() {
        return sound;
    }

    public void setPosNow(int posNow) {
        this.posNow = posNow;
    }

    public int getPosNow() {
        return posNow;
    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        Rectangle rect = getBoundForCollisionWithMap();

        if (getIsDicking()) {
            rect.x = (int) getPosX() - 22;
            rect.y = (int) getPosY() + 20;
            rect.width = 44;
            rect.height = 60;
        } else {
            rect.x = (int) getPosX() - 22;
            rect.y = (int) getPosY() - 40;
            rect.width = 54;
            rect.height = 90;
        }

        return rect;
    }

    @Override
    public void attack() {
        if (!isPunch && !getIsDicking() && !isRunning && getMana() >= 5) {
            setMana(getMana() - 5);
            playSound("punch");
            Bullet bullet = new Fist(getPosX(), getPosY(), this.getGameWorld());
            if (getDirection() == LEFT_DIR) {
                bullet.setPosX(getPosX() - getWidth() + 35);
                bullet.setPosY(getPosY() - 30);
            } else {
                bullet.setPosX(getPosX() + getWidth() - 35);
                bullet.setPosY(getPosY() - 30);
            }
            bullet.setTeamType(getTeamType());
            getGameWorld().bulletManager.addObject(bullet);

            lastPunchTime = System.nanoTime();
            isPunch = true;
        }

    }

    public void run() {
        if (getMana() >= 30) {
            playSound("flash");
            setMana(getMana() - 30);
            lastRunningTime = System.currentTimeMillis();
            setPosNow((int) getPosX());
            isRunning = true;
            if (getDirection() == LEFT_DIR && !getIsDicking() && !getIsJumping() && permitToRun)
                setSpeedX(0 - runSpeed);
            if (getDirection() == RIGHT_DIR && !getIsDicking() && !getIsJumping() && permitToRun)
                setSpeedX(runSpeed);
            permitToRun = false;
        }
    }

    public void dick() {
        if (!getIsJumping())
            setIsDicking(true);
    }

    public void walk() {
        if (getDirection() == LEFT_DIR)
            setSpeedX(0 - walkSpeed);
        else
            setSpeedX(walkSpeed);
    }

    public void jump() {

        if (!getIsJumping()) {
            setIsJumping(true);
            setSpeedY(-7);
            flyBackAnim.reset();
            flyForwardAnim.reset();
        }
        // for clim wall
        else {
            Rectangle rectRightWall = getBoundForCollisionWithMap();
            rectRightWall.x += 1;
            Rectangle rectLeftWall = getBoundForCollisionWithMap();
            rectLeftWall.x -= 1;

            if (getGameWorld().physicalMap.haveCollisionWithRightWall(rectRightWall) != null && getSpeedX() > 0) {
                setSpeedY(-5);
                // setSpeedX(-1);
                flyBackAnim.reset();
                flyForwardAnim.reset();
                // setDirection(LEFT_DIR);
            } else if (getGameWorld().physicalMap.haveCollisionWithLeftWall(rectLeftWall) != null && getSpeedX() < 0) {
                setSpeedY(-5);
                // setSpeedX(1);
                flyBackAnim.reset();
                flyForwardAnim.reset();
                // setDirection(RIGHT_DIR);
            }

        }
    }

    public void standUp() {
        setIsDicking(false);
        idleForwardAnim.reset();
        idleBackAnim.reset();
        dickForwardAnim.reset();
        dickBackAnim.reset();
    }

    public void stopRun() {
        setSpeedX(0);
        runForwardAnim.reset();
        runBackAnim.reset();
        walkForwardAnim.reset();
        walkBackAnim.reset();
        walkBackAnim.unIgnoreFrame(0);
        walkBackAnim.unIgnoreFrame(0);
        runForwardAnim.unIgnoreFrame(0);
        runBackAnim.unIgnoreFrame(0);
    }

    @Override
    public void beHurt(int damageEat) {
        if (!isSuperAttack) {
            setBlood(getBlood() - damageEat);
            state = BEHURT;
            hurtingCallBack();
        }
    }

    public boolean getIsJumping() {
        return isJumping;
    }

    public void setIsJumping(boolean isJumping) {
        this.isJumping = isJumping;
    }

    public boolean getIsDicking() {
        return isDicking;
    }

    public void setIsDicking(boolean isDicking) {
        this.isDicking = isDicking;
    }

    public boolean getIsLanding() {
        return isLanding;
    }

    public void setIsLanding(boolean isLanding) {
        this.isLanding = isLanding;
    }

    @Override

    public void Update() {
        super.Update();
        if (isRunning && (Math.abs(getPosX() - getPosNow()) > distanceRun || isCollision || getState()==ParticularObject.BEHURT)) {
            isRunning = false;
            setSpeedX(0);
            isCollision = false;
        }

        if (System.currentTimeMillis() - lastRunningTime > 5000) {
            permitToRun = true;
        }
        if (System.currentTimeMillis() - startSpeedUp < FastItem.limitSpeedUp) {
            if (getSpeedX() != 0 && !isRunning)
                walkSpeed = 6;
            if (isRunning) {
                runSpeed = 20;
                distanceRun=600;
            }
        } else if(System.currentTimeMillis() - startSpeedUp == FastItem.limitSpeedUp) setSpeedX(0); 
        else if(walkSpeed==6)  {
            walkSpeed = 3;
            runSpeed = 10;
            distanceRun=400;
        }

        if (System.currentTimeMillis() - startSpeedDown < FastItem.limitSpeedUp) {
            if (getSpeedX() != 0 && !isRunning)
                walkSpeed = 1.5;
            if (isRunning) {
                runSpeed = 5;
                distanceRun=300;
            }
        } else if(System.currentTimeMillis() - startSpeedDown == FastItem.limitSpeedUp) setSpeedX(0); 
        else if(walkSpeed==1.5) {
            walkSpeed = 3;
            runSpeed = 10;
            distanceRun=400;
        }

        
        if (getMana() < 100 && System.currentTimeMillis() - beginTimeMana > 2000) {
            setMana(getMana() + 5);
            beginTimeMana = System.currentTimeMillis();
        }
        if (getState() == DEATH) {
            setPosX(getPosX() + getSpeedX());
        }
        if (getState() == ALIVE || getState() == NOTBEHURT) {

            if (!isLanding) {

                setPosX(getPosX() + getSpeedX());

                if (getDirection() == LEFT_DIR &&
                        getGameWorld().physicalMap.haveCollisionWithLeftWall(getBoundForCollisionWithMap()) != null) {
                    if (isRunning)
                        isCollision = true;
                    Rectangle rectLeftWall = getGameWorld().physicalMap
                            .haveCollisionWithLeftWall(getBoundForCollisionWithMap());
                    setPosX(rectLeftWall.x + rectLeftWall.width + getWidth() / 2);

                }
                if (getDirection() == RIGHT_DIR &&
                        getGameWorld().physicalMap.haveCollisionWithRightWall(getBoundForCollisionWithMap()) != null) {
                    if (isRunning)
                        isCollision = true;
                    Rectangle rectRightWall = getGameWorld().physicalMap
                            .haveCollisionWithRightWall(getBoundForCollisionWithMap());
                    setPosX(rectRightWall.x - getWidth() / 2);

                }

                /**
                 * Kiểm tra posY của đối tượng
                 */
                // Cộng 2 vì phải kiểm tra phía dưới của đối tượng khi tốc độ rơi của nó sắp bằng 0

                Rectangle boundForCollisionWithMapFuture = getBoundForCollisionWithMap();
                boundForCollisionWithMapFuture.y += (getSpeedY() != 0 ? getSpeedY() : 2);
                Rectangle rectLand = getGameWorld().physicalMap.haveCollisionWithLand(boundForCollisionWithMapFuture);

                Rectangle rectTop = getGameWorld().physicalMap.haveCollisionWithTop(boundForCollisionWithMapFuture);

                if (rectTop != null) {

                    setSpeedY(0);
                    setPosY(rectTop.y + getGameWorld().physicalMap.getTileSize() + getHeight() / 2);

                } else if (rectLand != null) {
                    setIsJumping(false);
                    if (getSpeedY() > 0)
                        setIsLanding(true);
                    setSpeedY(0);
                    setPosY(rectLand.y - getHeight() / 2 - 1);
                } else {
                    setIsJumping(true);
                    setSpeedY(getSpeedY() + getMass());
                    setPosY(getPosY() + getSpeedY());
                }
            }
        }
    }

    public void playSound(String str) {
        if (GameWorld.isSound == "unmute") {
            sound.setFile(str);
            sound.play();
        }
    }

}