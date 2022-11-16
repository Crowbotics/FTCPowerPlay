package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;

import java.util.function.DoubleSupplier;

public class ElevatorAutoCommand extends CommandBase {

    private final ElevatorSubsystem m_elevator;
    private final double upPower;
    private final double downPower;
    private final int time;
    private int counter;
    private final Telemetry telemetry;

    public ElevatorAutoCommand(ElevatorSubsystem subsystem, double upPower, double downPower, int distance, Telemetry t){
        m_elevator = subsystem;
        this.upPower = upPower;
        this.downPower = downPower;
        this.time = distance;
        this.counter = 0;
        this.telemetry = t;
        addRequirements(subsystem);
    }

    @Override
    public void execute(){
        if(this.counter >= this.time) {
            this.m_elevator.stop();
            telemetry.addData("Power: ", 0);
        } else if(this.upPower > 0) {
            this.m_elevator.up(upPower);
        telemetry.addData("Power: ", upPower);
        } else if(this.downPower > 0) {
            this.m_elevator.down(downPower);
            telemetry.addData("Power: ", downPower);
        }
        telemetry.addData("Elevator counter", counter);
        telemetry.update();
        this.counter += 1;
    }

    @Override
    public boolean isFinished() {
        return this.counter > this.time;
//        return false;
    }
}
