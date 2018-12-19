package source.controller;

import javax.net.ssl.ManagerFactoryParameters;
import javax.swing.*;
import java.util.ArrayList;

public class TutorialManager extends Controller {
    private int index; //for activating current tutorial
    private boolean isTutorialActive; //will be accessed from guiManager
    private ArrayList<JLabel> tutorials = new ArrayList<>();

    public TutorialManager(boolean isTutorialActive){ //yeni playersa true olcak ve oyun ilk defa açılıyosa
        index = 0;
        this.isTutorialActive = isTutorialActive;
    }

    void update(){
        for (int i = 0; i < tutorials.size();i++){
            tutorials.get(i).setVisible(false);
        }
        tutorials.get(index).setVisible(true);
    }

    private void createComponents(){

    }

    private void setBoundsOfComponents(){

    }

    private void setBounds(JLabel label,int x , int y){
        label.setBounds(x,y,label.getPreferredSize().width,label.getPreferredSize().height);
    }
    
    public int getIndex() {
        return index;
    }

    public boolean isTutorialActive() {
        return isTutorialActive;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setTutorialActive(boolean tutorialActive) {
        isTutorialActive = tutorialActive;
    }

}
