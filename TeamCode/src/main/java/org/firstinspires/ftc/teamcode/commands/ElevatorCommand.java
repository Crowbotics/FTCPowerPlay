package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

import java.util.function.DoubleSupplier;

public class ElevatorCommand extends CommandBase {

    private final ElevatorSubsystem m_elevator;
    private final DoubleSupplier upPower;
    private final DoubleSupplier downPower;

    public ElevatorCommand(ElevatorSubsystem subsystem, DoubleSupplier upPower,DoubleSupplier downPower){
        m_elevator = subsystem;
        this.upPower = upPower;
        this.downPower = downPower;
        addRequirements(subsystem);
    }

    @Override
    public void execute(){
        if(this.upPower.getAsDouble() > 0) {
            this.m_elevator.up(upPower.getAsDouble());
        } else if(this.downPower.getAsDouble() > 0) {
            this.m_elevator.down(downPower.getAsDouble());
        } else {
            this.m_elevator.stop();
        }

    }
}
