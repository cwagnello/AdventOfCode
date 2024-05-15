package com.cwagnello;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author cwagnello
 */
public class Instruction {
    private String command;
    private Point p1;
    private Point p2;
    public Instruction(String instruction) {
        Pattern pattern = Pattern.compile("^(\\D+)((\\d+),(\\d+))\\D+((\\d+),(\\d+))$");

        Matcher matcher = pattern.matcher(instruction);
        if (matcher.find()) {
            command = matcher.group(1).trim();
            p1 = new Point(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));
            p2 = new Point(Integer.parseInt(matcher.group(6)), Integer.parseInt(matcher.group(7)));
        }
        else {
            throw new IllegalArgumentException("Instruction not valid: " + instruction);
        }
    }

    @Override
    public String toString() {
        return getCommand() + " from " + getP1() + " to " + getP2();
    }

    /**
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * @param command the command to set
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * @return the p1
     */
    public Point getP1() {
        return p1;
    }

    /**
     * @param p1 the p1 to set
     */
    public void setP1(Point p1) {
        this.p1 = p1;
    }

    /**
     * @return the p2
     */
    public Point getP2() {
        return p2;
    }

    /**
     * @param p2 the p2 to set
     */
    public void setP2(Point p2) {
        this.p2 = p2;
    }
    
}
