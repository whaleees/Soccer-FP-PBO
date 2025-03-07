public class Player extends Human {
    private int shooting;
    private int dribbling;
    private int pace;
    private int defending;
    private int passing;
    private int physical;
    private String position;
    private int rating;

    public void setShooting(int shooting) {
        this.shooting = shooting;
        this.rating = calculateRating();
    }

    public void setDribbling(int dribbling) {
        this.dribbling = dribbling;
        this.rating = calculateRating();
    }

    public void setPace(int pace) {
        this.pace = pace;
        this.rating = calculateRating();
    }

    public void setDefending(int defending) {
        this.defending = defending;
        this.rating = calculateRating();
    }

    public void setPassing(int passing) {
        this.passing = passing;
        this.rating = calculateRating();
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setPhysical(int physical) {
        this.physical = physical;
    }

    public int calculateRating() {
        final String pos = position;
        switch (pos) {
            case "FW":
                // Higher weight on shooting, dribbling, pace, and physical
                rating = (int) ((shooting * 0.3) +
                        (dribbling * 0.25) +
                        (pace * 0.2) +
                        (physical * 0.15) +
                        (passing * 0.1));
                break;

            case "MID":
                // Balance between passing, dribbling, defending, and physical
                rating = (int) ((passing * 0.3) +
                        (dribbling * 0.25) +
                        (defending * 0.2) +
                        (physical * 0.15) +
                        (shooting * 0.1));
                break;

            case "DF":
                // Focus on defending, physical, and pace
                rating = (int) ((defending * 0.4) +
                        (physical * 0.3) +
                        (pace * 0.15) +
                        (passing * 0.1) +
                        (dribbling * 0.05));
                break;

            case "GK":
                // Map fields for a goalkeeper and use their weights
                int diving = shooting;
                int reflex = dribbling;
                int handling = defending;
                int speed = pace;
                int kicking = passing;
                int positioning = physical;

                rating = (int) ((diving * 0.3) +
                        (reflex * 0.25) +
                        (handling * 0.2) +
                        (speed * 0.1) +
                        (kicking * 0.1) +
                        (positioning * 0.05));
                break;

            default:
                throw new IllegalArgumentException("Unknown position: " + position);
        }

        return rating;
    }

    public int getShooting() {
        return shooting;
    }

    public int getDribbling() {
        return dribbling;
    }

    public int getPace() {
        return pace;
    }

    public int getDefending() {
        return defending;
    }

    public int getPassing() {
        return passing;
    }

    public int getPhysical() {
        return physical;
    }

    public String getPosition() {
        return position;
    }

    public int getRating() {
        return rating;
    }

    public Player() {
        super();
        shooting = 0;
        dribbling = 0;
        pace = 0;
        defending = 0;
        passing = 0;
        position = "";
        rating = 0;
    }

    public Player(String name, int age, String nationality, int pace, int shooting, int passing, int dribbling,
            int defending, int physical, String position) {
        super(name, age, nationality);
        this.shooting = shooting;
        this.dribbling = dribbling;
        this.pace = pace;
        this.defending = defending;
        this.passing = passing;
        this.position = position;
        this.physical = physical;
        this.rating = calculateRating();
    }
}
