public class Player extends Human {
    private int shooting;
    private int dribbling; 
    private int heading;
    private int running; 
    private int passing;
    private String position;
    private int avg;

    public void setShooting(int shooting){
        this.shooting = shooting;
        updateAvg();  
    }

    public void setDribbling(int dribbling){
        this.dribbling = dribbling;
        updateAvg();  
    }

    public void setHeading(int heading){
        this.heading = heading;
        updateAvg();  
    }

    public void setRunning(int running){
        this.running = running;
        updateAvg();  
    }

    public void setPassing(int passing){
        this.passing = passing;
        updateAvg();  
    }

    public void setPosition(String position){
        this.position = position;
    }

    public int getShooting(){
        return shooting;
    }

    public int getDribbling(){
        return dribbling;
    }

    public int getHeading(){
        return heading;
    }

    public int getRunning(){
        return running;
    }

    public int getPassing(){
        return passing;
    }

    public String getPosition(){
        return position;
    }

    public int getAvg(){
        return avg;
    }

    public Player(){
        super();
        shooting = 0;
        dribbling = 0;
        heading = 0;
        running = 0;
        passing = 0;
        position = "";
        avg = 0;
    }

    public Player(String name, int age, String nationality, int shooting, int dribbling, int heading, int running, int passing, String position){
        super(name, age, nationality);
        this.shooting = shooting;
        this.dribbling = dribbling;
        this.heading = heading;
        this.running = running;
        this.passing = passing;
        this.position = position;
        this.avg = (shooting + dribbling + heading + running + passing) / 5;  
    }

    private void updateAvg(){
        this.avg = (shooting + dribbling + heading + running + passing) / 5;
    }
}
