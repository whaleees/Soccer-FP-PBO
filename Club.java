import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Club {
    private String name, stadion;
    private Coach coach;
    private int capacity, play = 0, win = 0, draw = 0, lose = 0, goalattack = 0, goaldefense = 0, point = 0;
    int cnt = 1, Rating = 0, en = 1;

    private ArrayList<Integer> weeklyScore = new ArrayList<>();
    private ArrayList<Boolean> visited = new ArrayList<>();
    private ArrayList<Player> arrayOfPlayers = new ArrayList<>();
    private ArrayList<Club> enemy;
    private ArrayList<Boolean> isHome = new ArrayList<>();
    private Map<Club, Map<Club, Boolean>> head2head = new HashMap<>();

    public void setHead2Head(Club opponent, int goala, int goalb) {
        head2head.putIfAbsent(this, new HashMap<>());
        head2head.putIfAbsent(opponent, new HashMap<>());
        if (goala > goalb) {
            head2head.get(this).put(opponent, true);
            head2head.get(opponent).put(this, false);
        } else if (goalb > goala) {
            head2head.get(opponent).put(this, true);
            head2head.get(this).put(opponent, false);
        } else {
            head2head.get(opponent).put(this, null);
            head2head.get(this).put(opponent, null);
        }
    }

    public boolean getHead2Head(Club opponent) {
        return head2head.getOrDefault(this, new HashMap<>()).get(opponent);
    }

    // Match Score
    public void setVisited(int week) {
        ensureVisitedSize(week); // Ensure the visited list is large enough
        visited.set(week - 1, true); // Convert 1-based index to 0-based
    }

    public boolean getVisited(int week) {
        ensureVisitedSize(week); // Ensure the visited list is large enough
        return visited.get(week - 1); // Convert 1-based index to 0-based
    }

    public void resetVisited(int week) {
        ensureVisitedSize(week); // Ensure the visited list is large enough
        visited.set(week - 1, false); // Convert 1-based index to 0-based
    }

    public void setScore(int week, int score) {
        ensureWeeklyScoreSize(week); // Ensure the weeklyScore list is large enough
        weeklyScore.set(week - 1, score); // Convert 1-based index to 0-based
    }

    public int getScore(int week) {
        ensureWeeklyScoreSize(week); // Ensure the weeklyScore list is large enough
        return weeklyScore.get(week - 1); // Convert 1-based index to 0-based
    }

    // Utility Methods
    private void ensureVisitedSize(int week) {
        while (visited.size() < week) {
            visited.add(false); // Default to false
        }
    }

    private void ensureWeeklyScoreSize(int week) {
        while (weeklyScore.size() < week) {
            weeklyScore.add(0); // Default score is 0
        }
    }

    // Stats
    public void setPlay() {
        play++;
    }

    public void setWin() {
        win++;
    }

    public void setDraw() {
        draw++;
    }

    public void setLose() {
        lose++;
    }

    public void setGoalAttack(int g) {
        goalattack += g;
    }

    public void setGoalDefense(int g) {
        goaldefense += g;
    }

    public void setPoint(int p) {
        point += p;
    }

    public void setEnemy(int week, Club enemyClub, boolean home) {
        while (enemy.size() <= week + 1) {
            enemy.add(null);
        }
        enemy.set(week, enemyClub);

        while (isHome.size() <= week + 1) {
            isHome.add(false);
        }
        isHome.set(week, home);
    }

    public Club getEnemy(int week) {
        if (week < enemy.size()) {
            return enemy.get(week);
        }
        return null;
    }

    public int getPlay() {
        return play;
    }

    public int getWin() {
        return win;
    }

    public int getDraw() {
        return draw;
    }

    public int getLose() {
        return lose;
    }

    public int getGoalAttack() {
        return goalattack;
    }

    public int getGoalDefense() {
        return goaldefense;
    }

    public int getPoint() {
        return point;
    }

    public Boolean getIsHome(int week) {
        if (week < isHome.size()) {
            return isHome.get(week);
        }
        return null;
    }

    public ArrayList<Player> getPlayers() {
        return arrayOfPlayers;
    }

    // Club
    public void setName(String name) {
        this.name = name;
    }

    public void setStadion(String stadion) {
        this.stadion = stadion;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getStadion() {
        return stadion;
    }

    // Coach
    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public Coach getCoach() {
        return coach;
    }

    // Player management
    public void addPlayers(Player player) { // to add player
        arrayOfPlayers.add(player);
        cnt++;
    }

    public void setPlayer(int ind, Player player) { // to add player at speecific ind
        arrayOfPlayers.set(ind, player);
    }

    public Player getPlayer(int ind) {
        return arrayOfPlayers.get(ind);
    }

    public int calculateRatingPlayers() {
        int totalRating = 0;
        int playerCount = 0;

        for (Player player : arrayOfPlayers) {
            if (player != null) {
                totalRating += player.calculateRating();
                playerCount++;
            }
        }

        if (playerCount > 0) {
            return totalRating / playerCount;
        } else {
            return 0;
        }
    }

    public int getRatingPlayer() {
        return calculateRatingPlayers();
    }

    public void displayPlayer(Player player) {
        System.out.println(player.getName() + " - " + player.getPosition() + " - " + player.getAge());
    }

    public Club() {
        name = "";
        stadion = "";
        capacity = 0;
        coach = new Coach();
        coach.setName("");
        enemy = new ArrayList<>();
    }

    public Club(String name, String stadion, int capacity) {
        this.name = name;
        this.stadion = stadion;
        this.capacity = capacity;
        this.enemy = new ArrayList<>();
    }

    public void viewClub() {
        System.out.println("Club Name = " + name);
        System.out.println("Stadion = " + stadion);
        System.out.println("Capacity = " + capacity);

        if (coach == null) {
            System.out.println("Coach = -");
        } else {
            System.out.println("Coach = " + coach.getName() + " - " + coach.getAge() + " - " + coach.getAge());
        }

        if (arrayOfPlayers.isEmpty()) {
            System.out.println("No players in the club.");
        } else {
            System.out.println("Player :");
            for (int i = 0; i < arrayOfPlayers.size(); i++) {
                Player player = arrayOfPlayers.get(i);
                System.out.println(
                        (i + 1) + ". " + player.getName() + " - " + player.getPosition() + " - " + player.getAge());
            }
        }
    }

}
