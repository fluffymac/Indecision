/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indecision;

import audio.AudioPlayer;
import environment.Environment;
import environment.LocationValidatorIntf;
import grid.Grid;
import images.ResourceTools;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author mayajones
 */
class IndecisionEnvironment extends Environment implements GridDrawData, LocationValidatorIntf {

    private Grid grid;
    private Harry harry;
    private Score score;
    private int level;
    public int SLOW_SPEED = 7;
    public int MEDIUM_SPEED = 5;
    public int HIGH_SPEED = 3;

    private int moveDelayLimit = HIGH_SPEED;
    private int moveDelayCounter = 0;

    private ArrayList<GridObject> gridObjects;

    private Image snitch;
    private Image wand;

    private GameState gameState = GameState.PLAY;

    public IndecisionEnvironment() {
    }

    @Override
    public void initializeEnvironment() {

        score = new Score();
        score.setPosition(new Point(32, 32));

        snitch = ResourceTools.loadImageFromResource("resources/Snitch.png");
        wand = ResourceTools.loadImageFromResource("resources/lightning.png");

        grid = new Grid(30, 21, 25, 25, new Point(50, 50), Color.MAGENTA);
        this.setBackground(ResourceTools.loadImageFromResource("resources/Hogwarts.jpg").getScaledInstance(900, 600, Image.SCALE_SMOOTH));

        harry = new Harry();
        harry.setDirection(Direction.DOWN);
        harry.setDrawData(this);
        harry.setLocationValidator(this);

        ArrayList<Point> body = new ArrayList<>();
        body.add(new Point(3, 1));
//        body.add(new Point(4, 1));
//        body.add(new Point(3, 2));
//        body.add(new Point(2, 2));
//        body.add(new Point(2, 3));
//        body.add(new Point(2, 4));

        harry.setGrowthCounter(3);
        harry.setBody(body);

        gridObjects = new ArrayList<>();
//        gridObjects.add(new GridObject(GridObjectType.SNITCH, new Point(1, 10)));

        
        for (int i = 0; i < 10; i++) {
            gridObjects.add(new GridObject(GridObjectType.SNITCH, getRandomPoint()));            
        }
     
        
        for (int i = 0; i < 5; i++){
        gridObjects.add(new GridObject(GridObjectType.WAND, getRandomPoint()));
        }

        AudioPlayer.play("/resources/ThemeSong.wav");

    }

    @Override
    public void timerTaskHandler() {
        if (harry != null) {
            // if counter >= limit then reset counter and move snake
            // else increment counter
            if (moveDelayCounter >= this.moveDelayLimit) {
                moveDelayCounter = 0;
                harry.move();
            } else {
                moveDelayCounter++;
            }
        }
        
        if (Math.random() < .01) {
//            int posn = (int)(Math.random() * gridObjects.size());
//            System.out.println(gridObjects.size() + " " + posn);
            gridObjects.get((int)(Math.random() * gridObjects.size())).setLocation(getRandomPoint());
        }
    }

    
    @Override
    public void keyPressedHandler(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_C) {
            grid.setShowCellCoordinates(!grid.getShowCellCoordinates());
            System.out.println(grid.getShowCellCoordinates());
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            harry.setDirection(Direction.LEFT);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            harry.setDirection(Direction.RIGHT);
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            harry.setDirection(Direction.UP);

        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            harry.setDirection(Direction.DOWN);
        } else if (e.getKeyCode() == KeyEvent.VK_P) {
            harry.togglePaused();
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            harry.grow(1);
        } else if (e.getKeyCode() == KeyEvent.VK_M) {

        } else if (e.getKeyCode() == KeyEvent.VK_1) {
            setLevel(1);
        }

//        AudioPlayer.play("/resources/CoolSound.wav");
    }

    @Override
    public void keyReleasedHandler(KeyEvent e) {
    }

    @Override
    public void environmentMouseClicked(MouseEvent e) {
    }

    @Override
    public void paintEnvironment(Graphics graphics) {

        switch (gameState) {
            case START:

                break;

            case PLAY:

                if (score != null) {
                    score.draw(graphics);
                }

                if (grid != null) {
//            grid.paintComponent(graphics);
                }
                if (harry != null) {
                    harry.draw(graphics);

                    if (gridObjects != null) {
                        for (GridObject gridObject : gridObjects) {
                            if (gridObject.getType() == GridObjectType.SNITCH) {
                                graphics.drawImage(snitch, grid.getCellSystemCoordinate(gridObject.getLocation()).x,
                                        grid.getCellSystemCoordinate(gridObject.getLocation()).y,
                                        grid.getCellWidth(), grid.getCellHeight(), this);
                            } else if (gridObject.getType() == GridObjectType.WAND) {
                                graphics.drawImage(wand, grid.getCellSystemCoordinate(gridObject.getLocation()).x,
                                        grid.getCellSystemCoordinate(gridObject.getLocation()).y,
                                        grid.getCellWidth(), grid.getCellHeight(), this);
                            }
                        }
                    }
                }
                break;

            case ENDED:

        }

    }

    public Point getRandomPoint() {
        return new Point((int) (grid.getColumns() * Math.random()), (int) (grid.getRows() * Math.random()));
    }

//<editor-fold defaultstate="collapsed" desc="GridDrawData Interface">
    @Override
    public int getCellHeight() {
        return grid.getCellHeight();
    }

    @Override
    public int gerCellWidth() {
        return grid.getCellWidth();
    }

    @Override
    public Point getCellCoordinate(Point cellCoordinate) {
        return grid.getCellSystemCoordinate(cellCoordinate);
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="LocationValidatorIntf">
    @Override
    public Point validateLocation(Point point) {
        //assess and modify the point as required...

        if (harry.selfHit()) {
            System.out.println("Self hit!");
            harry.setPaused(true);
        }

        if (point.x >= this.grid.getColumns()) {
            point.x = 0;
        } else if (point.x < 0) {
            point.x = this.grid.getColumns() - 1;
        }

        if (point.y >= this.grid.getRows()) {
            point.y = 0;
        } else if (point.y < 0) {
            point.y = this.grid.getRows() - 1;
        }

        // check if the snake hit a gridObject, then take apporpriate action
        // - Apple - grow snake by 3
        // - Poison - make sound, kill snake
        //
        // look at all the locations stored in the gridObject Array list
        // for each, compare it to the head location stored int the "point" parameter
        for (GridObject object : gridObjects) {
            if (object.getLocation().equals(point)) {

                if (object.getType() == GridObjectType.SNITCH) {
                    System.out.println("HIT = " + object.getType());
                    harry.grow(2);
                    this.score.addToValue(1);
                    object.setLocation(this.getRandomPoint());
                } else if (object.getType() == GridObjectType.WAND) {
                    System.out.println("HIT = " + object.getType());

                    harry.togglePaused();
                    //decrease score?
                    //change snake colour?
                }
            }
        }

        return point;

//        public int getlevel() {
//            return getLevel();
    }

    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(int level) {
        if (this.level != level) {
            if (level == 1) {
                this.setBackground(ResourceTools.loadImageFromResource("resources/Snape's Lab.jpg").getScaledInstance(900, 600, Image.SCALE_SMOOTH));
            }
        }
        this.level = level;

    }
//</editor-fold>

    /**
     * @return the gameState
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * @param gameState the gameState to set
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
//</editor-fold>

}
