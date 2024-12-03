import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

public class League {
    private String name;
    private int year;
    int cnt = 1;
    private ArrayList<Club> arrClub = new ArrayList<>();

    //Constructor
    public League(){
        name = "";
        year = 0;
    }
    public League(String name, int year){
        this.name = name;
        this.year = year;
    }

    //Setters
    public void setName(String name){
        this.name = name;
    }
    public void setYear(int year){
        this.year = year;
    }
    public void setClub(Club club){
        arrClub.add(club);
        cnt++;
    }
    public void setCoach(Coach coach, int ind){
        arrClub.get(ind).setCoach(coach);
    }
    public void addPlayer(Player player, int clubInd){
        arrClub.get(clubInd).addPlayers(player);
    }

    //Getters
    public String getName(){
        return name;
    }
    public int getYear(){
        return year;
    }
    public Club getClub(int ind){
        return arrClub.get(ind);
    }
    public Coach getCoach(int ind){
        return arrClub.get(ind).getCoach();
    }
    public Player getPlayer(int clubInd, int playerInd){
        return arrClub.get(clubInd).getPlayer(playerInd);
    }
    public ArrayList<Club> getClubArray(){
        return arrClub;
    }

    // Scheduling
    public void scheduling() {
        System.out.println("--------------------------------------");
        System.out.println("League Scheduling");
        System.out.println("--------------------------------------");
    
        int numClubs = arrClub.size(); 
        if (numClubs < 2) {
            System.out.println("Not enough clubs to schedule matches.");
            return;
        }
    
        int numWeeks = numClubs - 1; 
        int matchesPerWeek = numClubs / 2;
    
        for (int week = 1; week <= numWeeks; week++) {
            System.out.println("Week " + week);
    
            for (int i = 0; i < matchesPerWeek; i++) {
                Club home = arrClub.get(i);
                Club away = arrClub.get(numClubs - 1 - i);
    
                home.setEnemy(week, away);
                away.setEnemy(week, home);
    
                System.out.println(home.getName() + " vs " + away.getName());
            }
    
            Club last = arrClub.remove(numClubs - 1);
            arrClub.add(1, last);
        }
    
        System.out.println("--------------------------------------");
    }
    

    // Match Score
    public void matchScore() {
        System.out.println("--------------------------------------");
        System.out.println("League Match Results");
        System.out.println("--------------------------------------");
    
        int numClubs = arrClub.size();
        int numWeeks = (numClubs % 2 == 0) ? numClubs - 1 : numClubs;
    
        for (int week = 1; week <= numWeeks; week++) {
            System.out.println("Week " + week);
    
            for (Club home : arrClub) {
                Club away = home.getEnemy(week);
    
                if (away != null && !home.getVisited(week) && !away.getVisited(week)) {
                    int homeScore = score(home.getAvgPlayer(), away.getAvgPlayer());
                    int awayScore = score(away.getAvgPlayer(), home.getAvgPlayer());
    
                    System.out.println(home.getName() + " (" + homeScore + ") vs (" + awayScore + ") " + away.getName());
    
                    home.setGoalAttack(homeScore);
                    home.setGoalDefense(awayScore);
                    away.setGoalAttack(awayScore);
                    away.setGoalDefense(homeScore);
    
                    home.setPlay();
                    away.setPlay();
    
                    if (homeScore > awayScore) {
                        home.setWin();
                        away.setLose();
                        home.setPoint(3);
                    } else if (homeScore < awayScore) {
                        away.setWin();
                        home.setLose();
                        away.setPoint(3);
                    } else { 
                        home.setDraw();
                        away.setDraw();
                        home.setPoint(1);
                        away.setPoint(1);
                    }
    
                    home.setVisited(week);
                    away.setVisited(week);
                }
            }
        }
    
        for (Club club : arrClub) {
            for (int week = 1; week <= numWeeks; week++) {
                club.resetVisited(week);
            }
        }
    
        System.out.println("--------------------------------------");
    }
    


    public int score(int teamAvg, int opponentAvg) {
        Random random = new Random();
    
        int totalSize = teamAvg + opponentAvg;
    
        int[] goalArray = new int[totalSize];
    
        for (int i = 0; i < teamAvg; i++) {
            goalArray[i] = random.nextInt(4) + 2; 
        }
        for (int i = teamAvg; i < totalSize; i++) {
            goalArray[i] = random.nextInt(3); 
        }
    
        int score = goalArray[random.nextInt(totalSize)];
    
        return score;
    }
    

    public void leagueTable() {
        System.out.println("--------------------------------------");
        System.out.println("League Classement");
        System.out.println("--------------------------------------");

        Collections.sort(arrClub, (club1, club2) -> {
            if (club1.getPoint() != club2.getPoint()) {
                return Integer.compare(club2.getPoint(), club1.getPoint());
            }
            return club1.getName().compareTo(club2.getName()); 
        });

        System.out.printf("Rank\tClub\t\tPlay\tWin\tDraw\tLose\tGoalAttack\tGoalDefense\tPoint\n");
        int rank = 1;
        for (Club club : arrClub) {
            System.out.printf("%d.\t%s\t\t%d\t%d\t%d\t%d\t%d\t\t%d\t\t%d\n", rank++, club.getName(), club.getPlay(), club.getWin(), club.getDraw(),
                    club.getLose(), club.getGoalAttack(), club.getGoalDefense(), club.getPoint());
        }

        System.out.println("--------------------------------------");
    }
}
