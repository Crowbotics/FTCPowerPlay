package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

public class IntakeCommand extends CommandBase {
    public enum Mode {
        IN, OUT;
    }

    private final IntakeSubsystem m_intake;
    private final Mode mode;

    public IntakeCommand(IntakeSubsystem subsystem, Mode mode){
        m_intake = subsystem;
        this.mode = mode;
        addRequirements(subsystem);
    }

    @Override
    public void execute(){
        if(this.mode == Mode.IN) {
            m_intake.in();
        } else if(this.mode == Mode.OUT) {
            m_intake.out();
        }

    }
}
