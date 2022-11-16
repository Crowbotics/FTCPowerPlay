package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

public class IntakeAutoCommand extends CommandBase {
    public enum Mode {
        IN, OUT, STOP;
    }

    private final IntakeSubsystem m_intake;
    private final Mode mode;
    private final int time;
    private int counter;

    public IntakeAutoCommand(IntakeSubsystem subsystem, Mode mode, int time){
        m_intake = subsystem;
        this.mode = mode;
        this.time = time;
        this.counter = 0;
        addRequirements(subsystem);
    }

    @Override
    public void execute(){
        if(this.counter >= this.time) {
            m_intake.stop();
        } else if(this.mode.equals(Mode.IN)) {
            m_intake.in();
        } else if(this.mode.equals(Mode.OUT)) {
            m_intake.out();
        }

        this.counter ++;
    }

    @Override
    public boolean isFinished() {
        return this.counter > this.time ;
    }
}
