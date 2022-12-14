package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

public class IntakeCommand extends CommandBase {
    public enum Mode {
        IN, OUT, STOP;
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
        if(this.mode.equals(Mode.IN)) {
            m_intake.in();
        } else if(this.mode.equals(Mode.OUT)) {
            m_intake.out();
        } else {
            m_intake.stop();
        }

    }
}
