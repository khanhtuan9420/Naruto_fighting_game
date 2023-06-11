package gameobjects.Entity;

import java.applet.AudioClip;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import effect.CacheDataLoader;
import gameobjects.Gamemanager.GameWorld;

public class Naruto extends Human {

    public static final int RUNSPEED = 3;
    
    public boolean startBulletTime=false;
    
    
    public Naruto(float x, float y, GameWorld gameWorld) {
        super(x, y, gameWorld, 70, 110, 0.2f, 100, 100);
        
        
        
        
        setTeamType(LEAGUE_TEAM);

        setTimeNotBeHurt(500*1000000);

        idlePunchForwardAnim = CacheDataLoader.getInstance().getAnimation("Punch");
        idlePunchBackAnim = CacheDataLoader.getInstance().getAnimation("Punch");
        idlePunchBackAnim.flipAllImage();

        idleSuperAttackForwardAnim = CacheDataLoader.getInstance().getAnimation("SuperAttack");
        idleSuperAttackBackAnim = CacheDataLoader.getInstance().getAnimation("SuperAttack");
        
        
        idleSuperAttackBackAnim.flipAllImage();
        
        runForwardAnim = CacheDataLoader.getInstance().getAnimation("Run");
        runBackAnim = CacheDataLoader.getInstance().getAnimation("Run");
        runBackAnim.flipAllImage();

        deathForwardAnim = CacheDataLoader.getInstance().getAnimation("N_death");
        deathBackAnim = CacheDataLoader.getInstance().getAnimation("N_death");
        deathBackAnim.flipAllImage();

        dickForwardAnim = CacheDataLoader.getInstance().getAnimation("Dick");
        dickBackAnim = CacheDataLoader.getInstance().getAnimation("Dick");
        dickBackAnim.flipAllImage();

        walkForwardAnim = CacheDataLoader.getInstance().getAnimation("Walk");
        walkBackAnim = CacheDataLoader.getInstance().getAnimation("Walk");
        walkBackAnim.flipAllImage();  
        
        idleForwardAnim = CacheDataLoader.getInstance().getAnimation("Stance");
        idleBackAnim = CacheDataLoader.getInstance().getAnimation("Stance");
        idleBackAnim.flipAllImage();
        
        
        flyForwardAnim = CacheDataLoader.getInstance().getAnimation("Jump");
        flyForwardAnim.setIsRepeat(false);
        flyBackAnim = CacheDataLoader.getInstance().getAnimation("Jump");
        flyBackAnim.setIsRepeat(false);
        flyBackAnim.flipAllImage();
        
        landingForwardAnim = CacheDataLoader.getInstance().getAnimation("Landing");
        landingBackAnim = CacheDataLoader.getInstance().getAnimation("Landing");
        landingBackAnim.flipAllImage();
        
        
        
        
        
        beHurtForwardAnim = CacheDataLoader.getInstance().getAnimation("Damage");
        beHurtBackAnim = CacheDataLoader.getInstance().getAnimation("Damage");
        beHurtBackAnim.flipAllImage();
        
        
        
        
      
        
        
        
        
        
        
        
        
    }

    @Override
    public void Update() {
        if(getState()==DEATH && (deathBackAnim.getCurrentFrame()>=2 || deathForwardAnim.getCurrentFrame()>=2)) {
            setSpeedX(0);
        }
        if(deathBackAnim.getCurrentFrame()==3 || deathForwardAnim.getCurrentFrame()==3) {
            GameWorld.isGameOver=true;
        } 
        if(getState()==DEATH) {
            if(deathBackAnim.getCurrentFrame()<2 && deathForwardAnim.getCurrentFrame()<2 && getPosX()>getGameWorld().gaara.getPosX() ) setSpeedX(2);
            if(deathBackAnim.getCurrentFrame()<2 && deathForwardAnim.getCurrentFrame()<2 && getPosX()<getGameWorld().gaara.getPosX() ) setSpeedX(-2); 
        }

        if(getBlood()<=0 && !timeCheck) {
            timeDeath=System.currentTimeMillis();
            timeCheck=true;
        }
        if((idleSuperAttackBackAnim.getCurrentFrame()==26 || idleSuperAttackForwardAnim.getCurrentFrame()==26) && startBulletTime) {
            startBulletTime=false;
            BulletAppear();
        }
        super.Update();

        
        
        
        
        

        
        
        

        if(!isSuperAttack) {
            idleSuperAttackBackAnim.reset();
            idleSuperAttackBackAnim.reset();
        }



        if(isPunch){
            if(System.nanoTime() - lastPunchTime > 500*1000000){
                isPunch = false;
                idlePunchBackAnim.reset();
                idlePunchBackAnim.reset();
            }
        }


        
        if(getIsLanding()){
            landingBackAnim.Update(System.nanoTime());
            if(landingBackAnim.isLastFrame()) {
                setIsLanding(false);
                landingBackAnim.reset();
                runForwardAnim.reset();
                runBackAnim.reset();
                walkForwardAnim.reset();
                walkBackAnim.reset();
            }
        }
        
    }

    @Override
    public void draw(Graphics2D g2) {
        
        switch(getState()){
        
            case ALIVE:
            case NOTBEHURT:
                if(getState() == NOTBEHURT && (System.nanoTime()/10000000)%2!=1)
                {
                    
                }else{
                    
                    if(getIsLanding()){

                        if(getDirection() == RIGHT_DIR){
                            landingForwardAnim.setCurrentFrame(landingBackAnim.getCurrentFrame());
                            landingForwardAnim.draw((int) (getPosX()), 
                                    (int) getPosY()+ (getBoundForCollisionWithMap().height/2 - landingForwardAnim.getCurrentImage().getHeight()/2),
                                    g2);
                        }else{
                            landingBackAnim.draw((int) (getPosX()), 
                                    (int) getPosY() + (getBoundForCollisionWithMap().height/2 - landingBackAnim.getCurrentImage().getHeight()/2),
                                    g2);
                        }

                    }else if(getIsJumping()){

                        if(getDirection() == RIGHT_DIR){
                            flyForwardAnim.Update(System.nanoTime());
                            
                            flyForwardAnim.draw((int) (getPosX()), (int) getPosY(), g2);
                        }else{
                            flyBackAnim.Update(System.nanoTime());
                            
                            flyBackAnim.draw((int) (getPosX()), (int) getPosY(), g2);
                        }

                    }else if(getIsDicking()){

                        if(getDirection() == RIGHT_DIR){
                            dickForwardAnim.Update(System.nanoTime());
                            dickForwardAnim.draw((int) (getPosX()), 
                                    (int) getPosY() + (getBoundForCollisionWithMap().height/2 - dickForwardAnim.getCurrentImage().getHeight()/2)+10,
                                    g2);
                        }else{
                            dickBackAnim.Update(System.nanoTime());
                            dickBackAnim.draw((int) (getPosX()), 
                                    (int) getPosY() + (getBoundForCollisionWithMap().height/2 - dickBackAnim.getCurrentImage().getHeight()/2)+10,
                                    g2);
                        }

                    }else{
                        if(getSpeedX() > 0 && !isRunning){
                            walkForwardAnim.Update(System.nanoTime());
                    
                            walkForwardAnim.draw((int) (getPosX()), (int) getPosY(), g2);
                            if(walkForwardAnim.getCurrentFrame() == 1) walkForwardAnim.setIgnoreFrame(0);
                        }else if(getSpeedX() < 0 && !isRunning){
                            walkBackAnim.Update(System.nanoTime());
                            
                            walkBackAnim.draw((int) (getPosX()), (int) getPosY(), g2);
                            if(walkBackAnim.getCurrentFrame() == 1) walkBackAnim.setIgnoreFrame(0);
                        }else if(getSpeedX()==0){
                            if(getDirection() == RIGHT_DIR){
                                if(isPunch){
                                    idlePunchForwardAnim.Update(System.nanoTime());
                                    idlePunchForwardAnim.draw((int) (getPosX()), (int) getPosY(), g2);
                                }else if(isSuperAttack) {
                                    idleSuperAttackForwardAnim.Update(System.nanoTime());
                                    idleSuperAttackForwardAnim.draw((int) (getPosX()), (int) getPosY(), g2);
                                    if( idleSuperAttackForwardAnim.getCurrentFrame()==27) isSuperAttack=false;
                                } else {
                                    idleForwardAnim.Update(System.nanoTime());
                                    idleForwardAnim.draw((int) (getPosX()), (int) getPosY(), g2);
                                }
                
                                
                            }else{
                                
                                if(isPunch){
                                    idlePunchBackAnim.Update(System.nanoTime());
                                    idlePunchBackAnim.draw((int) (getPosX()), (int) getPosY(), g2);
                                }else if(isSuperAttack) {
                                    idleSuperAttackBackAnim.Update(System.nanoTime());
                                    idleSuperAttackBackAnim.draw((int) (getPosX()), (int) getPosY(), g2);
                                    if( idleSuperAttackBackAnim.getCurrentFrame()==27) isSuperAttack=false;
                                } else {
                                    idleBackAnim.Update(System.nanoTime());
                                    idleBackAnim.draw((int) (getPosX()), (int) getPosY(), g2);
                                }
                                
                            }
                        }        
                        if(getSpeedX()>0 && isRunning) {
                            runForwardAnim.Update(System.nanoTime());
                            runForwardAnim.draw((int) (getPosX()), (int) getPosY(), g2);
                            if(runForwardAnim.getCurrentFrame() == 1) runForwardAnim.setIgnoreFrame(0);
                        }
                        if(getSpeedX()<0 && isRunning) {
                            runBackAnim.Update(System.nanoTime());
                            
                            runBackAnim.draw((int) (getPosX()), (int) getPosY(), g2);
                            if(runBackAnim.getCurrentFrame() == 1) runBackAnim.setIgnoreFrame(0);
                        }
                    }
                }
                
                break;
            
            case BEHURT:
                    if(getDirection() == RIGHT_DIR){
                        beHurtForwardAnim.draw((int) (getPosX()), (int) getPosY(), g2);
                    }else{
                        beHurtBackAnim.setCurrentFrame(beHurtForwardAnim.getCurrentFrame());
                        beHurtBackAnim.draw((int) (getPosX()), (int) getPosY(), g2);
                    }
                
                break;
             
            case FEY:
                    break;
            case DEATH:
                if(deathBackAnim.getCurrentFrame()==2 || deathForwardAnim.getCurrentFrame()==2) {
                    deathBackAnim.setIgnoreFrame(0);
                    deathBackAnim.setIgnoreFrame(1);
                    deathForwardAnim.setIgnoreFrame(0);
                    deathForwardAnim.setIgnoreFrame(1);
                }
                if(getPosX()<getGameWorld().gaara.getPosX()) {
                    deathForwardAnim.Update(System.nanoTime());
                    deathForwardAnim.draw((int)getPosX(),(int) getPosY(), g2);
                    
                }
                else {
                    deathBackAnim.Update(System.nanoTime());
                    deathBackAnim.draw((int)getPosX(),(int) getPosY(), g2);
                }
                break;
        }
        
        
        
    }

    
    @Override
    public void superAttack() {
        if(!isSuperAttack  && !getIsDicking() && getMana()>=50) {
            playSound("narutophanthan");
            startBulletTime=true;
            setMana(getMana()-50);
            lastPunchTime = System.nanoTime();
            isSuperAttack = true;
            setPosNow((int)getPosX());
        }
    }

    public void BulletAppear() {
        playSound("rasengan");
        Bullet bullet = new SuperAttack(getPosX(), getPosY(), this.getGameWorld());
        if(getDirection()==LEFT_DIR) {
            bullet.setSpeedX(-7);
            bullet.setPosX(bullet.getPosX() -160);
            if(getSpeedX()!=0 && getSpeedY() ==0) {
                bullet.setPosX(bullet.getPosX()-10);
                bullet.setPosY(bullet.getPosY()-5);
            }
        }
        else {
            bullet.setSpeedX(7);
            bullet.setPosX(bullet.getPosX() +80);
            if(getSpeedX()!=0 && getSpeedY() ==0) {
                bullet.setPosX(bullet.getPosX()+10);
                bullet.setPosY(bullet.getPosY()-5);
            }
        }
        if(getIsJumping()) {
            bullet.setPosY(bullet.getPosY() - 20);
        }
        bullet.setTeamType(getTeamType());
        getGameWorld().bulletManager.addObject(bullet);
    }

    @Override
    public void hurtingCallBack(){
        
        playSound("narutohurt");
    }

}
