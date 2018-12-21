package source.controller;

import source.model.*;

import java.awt.*;

//@SuppressWarnings("Duplicates")
@SuppressWarnings("Duplicates")
public class VehicleController extends Controller {
    public static VehicleController instance;

    private Map map;
    private Vehicle selectedVehicle;
    //   private Vehicle player;
    private SoundManager soundManager;
    private int numberOfMoves;
    private boolean changed = false;
    private boolean isMoving = false;
    boolean isExitReachable = false;
    boolean isPlayerMoving = false;
    //   private double moveAmount;
//   private int counter = 0;
    private boolean isSelectedVehicleSliding = false;
    private Point destination = new Point();

    private enum CONTROL {
        SLIDE, KEYBOARD, //CPU //CPU kalsın
    }

    private CONTROL currentControl;

    private double[] vehicleOriginPosition;
    private int[] mouseOriginPosition;
    private int[] oldPos;

    VehicleController() {
        instance = this;
        numberOfMoves = 0;
        soundManager = GameEngine.instance.soundManager;
        currentControl = CONTROL.SLIDE;
        //currentControl = CONTROL.KEYBOARD;

        mouseOriginPosition = new int[2];
        vehicleOriginPosition = new double[2];
        oldPos = new int[2];
//      moveAmount = 0;
    }

    public void setMap(Map _map) {
        map = _map;
    }

    //executed every frame write the functionality needed to here
    public void update() {
        if (!GameManager.instance.isGameActive) {
            return;
        }

        if (PowerUpManager.instance.isPowerUpActive()) {
            return;
        }

        if (currentControl == CONTROL.SLIDE) {


            if (Input.getMouseButtonPressed(0)) {
                Vehicle temp = MapController.instance.getVehicleBySelectedCell(Input.getMouseMatrixPosition()[0], Input.getMouseMatrixPosition()[1]);

                if (temp != null) {
                    setSelectedVehicle(temp);
                    vehicleOriginPosition[0] = selectedVehicle.transform.position.x;
                    vehicleOriginPosition[1] = selectedVehicle.transform.position.y;
                    mouseOriginPosition = Input.getMousePosition();

                    oldPos[0] = selectedVehicle.transform.position.gridX;
                    oldPos[1] = selectedVehicle.transform.position.gridY;
                }
            }

            if (Input.getMouseButtonReleased(0)) {

                if (selectedVehicle != null) {
                    selectedVehicle.isSliding = true;

                    int gridPositionX = (int) (selectedVehicle.transform.position.x + 0.5);
                    int gridPositionY = (int) (selectedVehicle.transform.position.y + 0.5);

                    selectedVehicle.moveToPoint(gridPositionX, gridPositionY);
                    MapController.instance.updateMap(map.getGameObjects());
                    destination.x = selectedVehicle.transform.position.gridX;
                    destination.y = selectedVehicle.transform.position.gridY;

                    if (!(oldPos[0] == selectedVehicle.transform.position.gridX && oldPos[1] == selectedVehicle.transform.position.gridY)) {
                        numberOfMoves++;
                    }

                    if (MapController.instance.isPlayerAtExit()) {
                        GameManager.instance.endMap();
                    }
                }
            }

            try {
                if (selectedVehicle != null && selectedVehicle.isSliding) {
                    selectedVehicle.slideToPoint(destination.x, destination.y);
                    isSelectedVehicleSliding = selectedVehicle.isSliding;
                    if (!isSelectedVehicleSliding) {
                        GameManager.instance.autoSave();
                        selectedVehicle = null;
                    }
                }
            } catch (Exception e) {
                //do nothing
            }


            //Moving while holding the mouse down
            if (selectedVehicle != null && !isSelectedVehicleSliding) {
                selectedVehicle.velocity = 0.05;
                if (selectedVehicle.transform.axis.equals("Horizontal")) {
                    int mouseDifference = (Input.getMousePosition()[0] - mouseOriginPosition[0]);

                    int testPositionX = (int) vehicleOriginPosition[0];
                    int testPositionY = (int) vehicleOriginPosition[1];

                    if (mouseDifference > 0) // right
                    {
                        testPositionX += selectedVehicle.transform.length;// (int) ( vehicleOriginPosition[0] + ( ( Input.getMousePosition()[0] - mouseOriginPosition[0] ) / (double) 60 ) );
                    } else if (mouseDifference < 0) // left
                    {
                        testPositionX -= 1;
                    }

                    if (mouseDifference != 0) {
                        double difference = clamp(mouseDifference, -1, 1);
                        if (testPositionX >= 0 && testPositionX < map.getMapSize()) {
                            if (map.getGrid()[testPositionY][testPositionX].equals("Space")) {
                                selectedVehicle.transform.position.x = vehicleOriginPosition[0] + difference;
                            }
                        }

                        if (difference == 1 || difference == -1) {
                            changeGridPosition();
                        }
                    }
                } else // Vertical
                {
                    int mouseDifference = (Input.getMousePosition()[1] - mouseOriginPosition[1]);

                    int testPositionX = (int) vehicleOriginPosition[0];
                    int testPositionY = (int) vehicleOriginPosition[1];

                    if (mouseDifference > 0) // up
                    {
                        testPositionY += selectedVehicle.transform.length;
                    } else if (mouseDifference < 0) // down
                    {
                        testPositionY -= 1;
                    }

                    if (mouseDifference != 0) {
                        double difference = clamp(mouseDifference, -1, 1);

                        if (testPositionY >= 0 && testPositionY < map.getMapSize()) {
                            if (map.getGrid()[testPositionY][testPositionX].equals("Space")) {
                                selectedVehicle.transform.position.y = vehicleOriginPosition[1] + difference;
                            }
                        }

                        if (difference == 1 || difference == -1) {
                            changeGridPosition();
                        }
                    }
                }
            }
        } else if (currentControl == CONTROL.KEYBOARD) {

            if (Input.getMouseButtonPressed(0) && !isMoving) {
                Vehicle temp = MapController.instance.getVehicleBySelectedCell(Input.getMouseMatrixPosition()[0], Input.getMouseMatrixPosition()[1]);

                if (temp != null) {
                    setSelectedVehicle(temp);
                    System.out.println("Selected vehicle: " + selectedVehicle.transform.position.x + ", " + selectedVehicle.transform.position.y);
                }
            }

            if (!isExitReachable) {
                checkExitPath();
            } else {

                if (!MapController.instance.isPlayerAtExit() && selectedVehicle == MapController.instance.getPlayerVehicle()) {
                    isPlayerMoving = true;
                    selectedVehicle.move(0.1);
                } else if (MapController.instance.isPlayerAtExit()) {
                    selectedVehicle = null;
                    isMoving = false;
                    isPlayerMoving = false;
                    GameManager.instance.endMap();

                }
            }
            System.out.println(isMoving + "," + isPlayerMoving);
            if (selectedVehicle != null && !isMoving && !isPlayerMoving) {

                selectedVehicle.velocity = 0.1;
                if (Input.getKeyPressed("w")) {
                    isMoving = tryMove("Upwards");

                    destination.y = selectedVehicle.transform.position.gridY - 1;
                    destination.x = selectedVehicle.transform.position.gridX;
                } else if (Input.getKeyPressed("a")) {
                    isMoving = tryMove("Left");

                    destination.y = selectedVehicle.transform.position.gridY;
                    destination.x = selectedVehicle.transform.position.gridX - 1;
                } else if (Input.getKeyPressed("s")) {
                    isMoving = tryMove("Downwards");

                    destination.y = selectedVehicle.transform.position.gridY + 1;
                    destination.x = selectedVehicle.transform.position.gridX;
                } else if (Input.getKeyPressed("d")) {
                    isMoving = tryMove("Right");

                    destination.y = selectedVehicle.transform.position.gridY;
                    destination.x = selectedVehicle.transform.position.gridX + 1;
                }
            }
            if (selectedVehicle != null && isMoving) {

                selectedVehicle.isSliding = true;
                selectedVehicle.slideToPoint(destination.x, destination.y);
                isSelectedVehicleSliding = selectedVehicle.isSliding;
                if (!isSelectedVehicleSliding) {
                    selectedVehicle.moveToPoint(destination.x, destination.y);
                    MapController.instance.updateMap(map.getGameObjects());
                    changed = true;
                    isMoving = false;
                    isPlayerMoving = false;
                    GameManager.instance.autoSave();

                }
            }

        }
        if (MapController.instance.isPlayerAtExit()) {
            selectedVehicle = null;
            isMoving = false;
            isPlayerMoving = false;
            GameManager.instance.endMap();
        }
    }

    private void setSelectedVehicle(Vehicle _selectedVehicle) {
        if (_selectedVehicle != selectedVehicle) {
            if (changed) {
                numberOfMoves++;
                GameManager.instance.autoSave();
                changed = false;
            }
        }
        selectedVehicle = _selectedVehicle;
        if (soundManager == null) {
            return;
        }
        soundManager.vehicleHorn();
    }

    private void changeGridPosition() {
        int gridPositionX = (int) (selectedVehicle.transform.position.x + 0.5);
        int gridPositionY = (int) (selectedVehicle.transform.position.y + 0.5);

        selectedVehicle.moveToPoint(gridPositionX, gridPositionY);
        MapController.instance.updateMap(map.getGameObjects());

        vehicleOriginPosition[0] = selectedVehicle.transform.position.x;
        vehicleOriginPosition[1] = selectedVehicle.transform.position.y;
        mouseOriginPosition = Input.getMousePosition();

        if (MapController.instance.isPlayerAtExit()) {
            GameManager.instance.endMap();
            selectedVehicle = null;
        }
    }

    private boolean tryMove(String direction) {
        System.out.println("In here??");
        String vehicleAxis = selectedVehicle.transform.axis;

        int moveCheck = 0;

        if (vehicleAxis.equals("Horizontal")) {
            if (direction.equals("Left")) {
                moveCheck = -1;
            } else if (direction.equals("Right")) {
                moveCheck = selectedVehicle.transform.length;
            }
            if (selectedVehicle.transform.position.x + moveCheck < 0 || selectedVehicle.transform.position.x + moveCheck >= map.getMapSize()) {
                return false;
            }

            if (map.getGrid()[selectedVehicle.transform.position.gridY][selectedVehicle.transform.position.gridX + moveCheck].equals("Space")) {
                selectedVehicle.isMoving = true;
                return true;
            }
        }
        if (vehicleAxis.equals("Vertical")) {
            if (direction.equals("Upwards")) {
                moveCheck = -1;
            } else if (direction.equals("Downwards")) {
                moveCheck = selectedVehicle.transform.length;
            }
            if (selectedVehicle.transform.position.y + moveCheck < 0 || selectedVehicle.transform.position.y + moveCheck >= map.getMapSize()) {
                return false;
            }

            if (map.getGrid()[(selectedVehicle.transform.position.gridY) + moveCheck][selectedVehicle.transform.position.gridX].equals("Space")) {
                selectedVehicle.isMoving = true;
                return true;
            }
        }

        return false;
    }

    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    void setNumberOfMoves(int _moves) {
        numberOfMoves = _moves;
    }

    private void checkExitPath() {
        Vehicle player = MapController.instance.getPlayerVehicle();
        boolean temp = true;
        for (int i = 0; i < (map.getMapSize() - player.transform.position.gridX) - player.transform.length; i++) {
            if (!(map.getGrid()[(player.transform.position.gridY)][player.transform.position.gridX + i + player.transform.length].equals("Space"))) {
                temp = false;
            }
        }
        isExitReachable = temp;
        //System.out.println(isExitReachable);
    }

    private double clamp(double value, int min, int max) {
        double difference;
        if (value / (double) 60 > max) {
            difference = max;
        } else if (value / (double) 60 < min) {
            difference = min;
        } else {
            difference = value / (double) 60;
        }
        return difference;
    }

    void toggleCurrentControl() {
        if (currentControl == CONTROL.SLIDE) {
            currentControl = CONTROL.KEYBOARD;
        } else {
            currentControl = CONTROL.SLIDE;
        }
        selectedVehicle = null;
    }

    private void initializeCurrentControl() {
        if (PlayerManager.instance.getCurrentPlayer().getSettings().getControlPreference().equals("Slide")) {
            currentControl = CONTROL.SLIDE;
        } else {
            currentControl = CONTROL.KEYBOARD;
        }
    }

    public void start() {
        initializeCurrentControl();
    }
}
