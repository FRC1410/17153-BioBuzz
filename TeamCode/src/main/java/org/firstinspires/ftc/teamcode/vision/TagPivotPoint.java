package org.firstinspires.ftc.teamcode.vision;

class TagPivotPoint{
    public static enum hiveType{RED,BLUE};

    private final hiveType type;
    private final boolean tagFlipped;
    private final double x;
    private final double y;
    private final double z;

    public TagPivotPoint(hiveType type, boolean tagFlipped, double x, double y, double z){
        this.tagFlipped = tagFlipped;
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double x(){
        return x;
    }
    public double y(){
        return y;
    }
    public double z(){
        return z;
    }

    public boolean isOnBlueHive(){
        return type==hiveType.BLUE;
    }
    public boolean isOnRedHive(){
        return type==hiveType.RED;
    }

    public boolean flippedTag(){
        return tagFlipped;
    }
}