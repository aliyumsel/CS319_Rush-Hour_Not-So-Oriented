package source.controller;

import source.model.*;

//@SuppressWarnings("Duplicates")
public class VehicleController extends Controller {
    public static VehicleController instance;

    private Map map;
    private Vehicle selectedVehicle;
    private Vehicle player;
    private SoundManager soundManager;
    private int numberOfMoves;
    private boolean changed = false;
    Boolean isExitReachable = false;

    private enum CONTROL {
        SLIDE, KEYBOARD, CPU
    }

    private CONTROL currentControl;

    private double[] vehicleOriginPosition;
    private int[] mouseOriginPosition;
    private int[] oldPos;

    VehicleController() {
        instance = this;
        numberOfMoves = 0;
        soundManager = GameEngine.instance.soundManager;
        // currentControl = CONTROL.SLIDE;
        currentControl = CONTROL.KEYBOARD;

        mouseOriginPosition = new int[2];
        vehicleOriginPosition = new double[2];
        oldPos = new int[2];
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
                    int gridPositionX = (int) (selectedVehicle.transform.position.x + 0.5);
                    int gridPositionY = (int) (selectedVehicle.transform.position.y + 0.5);

                    selectedVehicle.moveToPoint(gridPositionX, gridPositionY);
                    selectedVehicle.slideToPoint(gridPositionX, gridPositionY);
                    MapController.instance.updateMap(map.getGameObjects());

                    if (!(oldPos[0] == selectedVehicle.transform.position.gridX && oldPos[1] == selectedVehicle.transform.position.gridY)) {
                        numberOfMoves++;
                    }

                    if (MapController.instance.isPlayerAtExit()) {
                        GameManager.instance.endMap();
                    }
                }

                GameManager.instance.autoSave();

                selectedVehicle = null;
            }

            //Moving while holding the mouse down
            if (selectedVehicle != null) {
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
            if (Input.getMouseButtonPressed(0)) {
                Vehicle temp = MapController.instance.getVehicleBySelectedCell(Input.getMouseMatrixPosition()[0], Input.getMouseMatrixPosition()[1]);

                if (temp != null) {
                    setSelectedVehicle(temp);
                    System.out.println("Selected vehicle: " + selectedVehicle.transform.position.x + ", " + selectedVehicle.transform.position.y);
                }
            }

            if (!isExitReachable) {
                checkExitPath();
            } else {
                CONTROL temp = currentControl;

//                if (selectedVehicle == null) {
//                    currentControl = CONTROL.CPU; //cpu yu silmeyin, slide da da kullanılmak istenirse burası işe yarıyo çok
//                    selectedVehicle = MapController.instance.getPlayerVehicle(); //otomatik gitsin istenirse sadece bu satır kalıcak if ve cpu gidicek
//                }
                System.out.println("inside");
                if (!MapController.instance.isPlayerAtExit() && selectedVehicle == MapController.instance.getPlayerVehicle()) {
                    selectedVehicle.move(0.1);
                } else if (MapController.instance.isPlayerAtExit()) {
                    GameManager.instance.endMap();
                    selectedVehicle = null;
                    currentControl = temp;
                }
            }
        }

        if (selectedVehicle != null) {
            boolean moved = false;
            if (Input.getKeyPressed("w")) {
                moved = tryMove("Upwards");
            } else if (Input.getKeyPressed("a")) {
                moved = tryMove("Left");
            } else if (Input.getKeyPressed("s")) {
                moved = tryMove("Downwards");
            } else if (Input.getKeyPressed("d")) {
                moved = tryMove("Right");
            }

            if (moved) {
                System.out.println("Moved");
                MapController.instance.updateMap(map.getGameObjects());
                changed = true;

                if (MapController.instance.isPlayerAtExit()) {
                    GameManager.instance.endMap();
                    selectedVehicle = null;
                }
            }
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

    private void slideVehicle(String direction) {
//      Timer timer = new Timer();
//      timer.schedule(new TimerTask()
//      {
//         @Override
//         public void run()
//         {
//            System.out.print("=");
//         }
//      }, 0,1/60);
    }

    private void changeGridPosition() {
        int gridPositionX = (int) (selectedVehicle.transform.position.x + 0.5);
        int gridPositionY = (int) (selectedVehicle.transform.position.y + 0.5);

        selectedVehicle.moveToPoint(gridPositionX, gridPositionY);
        //selectedVehicle.slideToPoint(gridPositionX, gridPositionY);
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
        int moveAmount = 0;
        int moveCheck = 0;

        if (vehicleAxis.equals("Horizontal")) {
            if (direction.equals("Left")) {
                moveAmount = -1;
                moveCheck = -1;
            } else if (direction.equals("Right")) {
                moveAmount = 1;
                moveCheck = selectedVehicle.transform.length;
            }
            if (selectedVehicle.transform.position.x + moveCheck < 0 || selectedVehicle.transform.position.x + moveCheck >= map.getMapSize()) {
                return false;
            }

            if (map.getGrid()[(int) selectedVehicle.transform.position.y][(int) selectedVehicle.transform.position.x + moveCheck].equals("Space")) {
                selectedVehicle.move(moveAmount);
                selectedVehicle.isMoving = true;
                return true;
            }
        }
        if (vehicleAxis.equals("Vertical")) {
            if (direction.equals("Upwards")) {
                moveAmount = 1;
                moveCheck = -1;
            } else if (direction.equals("Downwards")) {
                moveAmount = -1;
                moveCheck = selectedVehicle.transform.length;
            }
            if (selectedVehicle.transform.position.y + moveCheck < 0 || selectedVehicle.transform.position.y + moveCheck >= map.getMapSize()) {
                return false;
            }

            if (map.getGrid()[((int) selectedVehicle.transform.position.y) + moveCheck][(int) selectedVehicle.transform.position.x].equals("Space")) {
                selectedVehicle.move(moveAmount);
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
            if (!(map.getGrid()[( player.transform.position.gridY)][ player.transform.position.gridX + i + player.transform.length].equals("Space"))) {
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
}
