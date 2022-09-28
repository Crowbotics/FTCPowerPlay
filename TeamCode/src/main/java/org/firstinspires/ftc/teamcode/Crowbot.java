package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.Robot;

public class Crowbot extends Robot {

    public enum OpModeType{
        TELEOP, AUTO
    }

    public Crowbot(OpModeType type){
        if (type == OpModeType.TELEOP)
            initTele();
        else
            initAuto();

    }

    public void initTele(){
    }

    public void initAuto(){

    }


}
