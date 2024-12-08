import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class League {
    private String name;
    private int year;
    int cnt = 1;
    private ArrayList<Club> arrClub = new ArrayList<>();
    private int currWeek;
    private int numWeeks;
    private int matchesPerWeek;

    // Constructor
    public League() {
        name = "";
        year = 0;
        currWeek = 0;
    }

    public League(String name, int year) {
        this.name = name;
        this.year = year;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setClub(Club club) {
        arrClub.add(club);
        cnt++;
    }

    public void setCoach(Coach coach, int ind) {
        arrClub.get(ind).setCoach(coach);
    }

    public void addPlayer(Player player, int clubInd) {
        arrClub.get(clubInd).addPlayers(player);
    }

    public void setCurrWeek(int currWeek) {
        this.currWeek = currWeek;
    }

    public void setNumWeeks(int numWeeks) {
        this.numWeeks = numWeeks;
    }

    public void setMatchesPerWeek(int matchesPerWeek) {
        this.matchesPerWeek = matchesPerWeek;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public Club getClub(int ind) {
        return arrClub.get(ind);
    }

    public Coach getCoach(int ind) {
        return arrClub.get(ind).getCoach();
    }

    public Player getPlayer(int clubInd, int playerInd) {
        return arrClub.get(clubInd).getPlayer(playerInd);
    }

    public ArrayList<Club> getClubArray() {
        return arrClub;
    }

    public int getCurrWeek() {
        return currWeek;
    }

    public int getNumWeeks() {
        return numWeeks;
    }

    public void scheduleMatches() {
        int numClubs = arrClub.size();
        if (numClubs < 2) {
            throw new IllegalArgumentException("Not enough clubs to schedule matches.");
        }

        // Add a bye if the number of clubs is odd
        boolean hasBye = false;
        if (numClubs % 2 != 0) {
            arrClub.add(new Club("Bye", "Bye", 0));
            numClubs++;
            hasBye = true;
        }

        setCurrWeek(1);
        setNumWeeks((numClubs - 1) * 2);
        setMatchesPerWeek(numClubs / 2);

        for (int week = 1; week <= numWeeks; week++) {
            for (int i = 0; i < matchesPerWeek; i++) {
                Club home, away;

                if (i == 0) {
                    home = arrClub.get(0);
                    away = arrClub.get((week - 1 + i) % (numClubs - 1) + 1); // Wrap-around for rotation
                } else {
                    home = arrClub.get((week - 1 + i) % (numClubs - 1) + 1);
                    away = arrClub.get((numClubs - 1 - i + week - 1) % (numClubs - 1) + 1);
                }

                if (week > numWeeks / 2) {
                    Club temp = home;
                    home = away;
                    away = temp;
                }

                if (home.getName().equals("Bye") || away.getName().equals("Bye")) {
                    continue;
                }

                home.setEnemy(week, away, true);
                away.setEnemy(week, home, false);
            }
        }

        if (hasBye) {
            arrClub.removeIf(club -> club.getName().equals("Bye"));
        }
    }

    // Scheduling
    public void scheduling() {
        scheduleMatches();

        System.out.println("--------------------------------------");
        System.out.println("League Scheduling");
        System.out.println("--------------------------------------");

        for (int week = 1; week <= numWeeks; week++) {
            System.out.println("Week " + week);

            HashMap<Club, Boolean> picked = new HashMap<>();

            for (Club club : arrClub) {
                picked.put(club, false);
            }

            for (Club club : arrClub) {
                Club home = null, away = null;
                Boolean isHome = club.getIsHome(week);

                if (isHome == null) {
                    isHome = false;
                }

                if (isHome) {
                    home = club;
                    away = club.getEnemy(week);
                } else {
                    away = club;
                    home = club.getEnemy(week);
                }

                if (home != null && away != null &&
                        picked.containsKey(home) && picked.containsKey(away) &&
                        !picked.get(home) && !picked.get(away)) {

                    picked.put(home, true);
                    picked.put(away, true);

                    System.out.println(home.getName() + " vs " + away.getName());
                }
            }
        }

        System.out.println("--------------------------------------");
    }

    public void simulateOneWeek() {
        for (Club club : arrClub) {
            Club home = null, away = null;
            Boolean isHome = club.getIsHome(currWeek);

            if (isHome == null) {
                isHome = false;
            }

            if (isHome) {
                home = club;
                away = club.getEnemy(currWeek);
            } else {
                away = club;
                home = club.getEnemy(currWeek);
            }

            if (away == null || home == null) {
                continue;
            }

            if (away != null && !home.getVisited(currWeek) && !away.getVisited(currWeek) && home != null) {
                int homeScore = score(home.getRatingPlayer(), away.getRatingPlayer());
                int awayScore = score(away.getRatingPlayer(), home.getRatingPlayer());

                home.setScore(currWeek, homeScore);
                away.setScore(currWeek, awayScore);

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

                home.setVisited(currWeek);
                away.setVisited(currWeek);
            }
        }
        currWeek++;
    }

    public void simulateAllWeeks() {
        for (int week = currWeek; week <= numWeeks; week++) {
            simulateOneWeek();
        }
    }

    // Match Score
    public void matchScore() {
        scheduleMatches();
        simulateAllWeeks();

        System.out.println("--------------------------------------");
        System.out.println("League Match Results");
        System.out.println("--------------------------------------");

        for (int week = 1; week <= numWeeks; week++) {
            System.out.println("Week " + week);

            HashMap<Club, Boolean> picked = new HashMap<>();

            for (Club club : arrClub) {
                picked.put(club, false);
            }

            for (Club club : arrClub) {
                Club home = null, away = null;
                Boolean isHome = club.getIsHome(week);

                if (isHome == null) {
                    isHome = false;
                }

                if (isHome) {
                    home = club;
                    away = club.getEnemy(week);
                } else {
                    away = club;
                    home = club.getEnemy(week);
                }

                if (away == null || home == null) {
                    continue;
                }

                if (!picked.get(home) && !picked.get(away)) {
                    int homeScore = home.getScore(week);
                    int awayScore = away.getScore(week);

                    picked.put(home, true);
                    picked.put(away, true);

                    System.out.println(home.getName() + " " + homeScore + " - " + awayScore + " " + away.getName());
                }
            }
        }

        System.out.println("--------------------------------------");
    }

    public int score(int teamRating, int opponentRating) {
        if (teamRating <= 0 && opponentRating <= 0) {
            return 0;
        }

        Random random = new Random();
        int totalSize = teamRating + opponentRating;

        int[] goalArray = new int[totalSize];

        for (int i = 0; i < teamRating; i++) {
            goalArray[i] = random.nextInt(4) + 2;
        }

        for (int i = teamRating; i < totalSize; i++) {
            goalArray[i] = random.nextInt(3);
        }

        int randomIndex = random.nextInt(totalSize);
        int score = goalArray[randomIndex];

        return score;
    }

    public void sortTable() {
        Collections.sort(arrClub, (club1, club2) -> {
            if (club1.getPoint() != club2.getPoint()) {
                return Integer.compare(club2.getPoint(), club1.getPoint());
            } else if (club1.getGoalAttack() - club1.getGoalDefense() != club2.getGoalAttack()
                    - club2.getGoalDefense()) {
                return Integer.compare(club2.getGoalAttack() - club2.getGoalDefense(),
                        club1.getGoalAttack() - club1.getGoalDefense());
            } else if (club1.getGoalAttack() != club2.getGoalAttack()) {
                return Integer.compare(club2.getGoalAttack(), club1.getGoalAttack());
            }
            return club1.getName().compareTo(club2.getName());
        });
    }

    public void leagueTable() {
        System.out.println("--------------------------------------");
        System.out.println("League Classement");
        System.out.println("--------------------------------------");

        sortTable();

        System.out.printf("Rank\tClub\t\tPlay\tWin\tDraw\tLose\tGoalAttack\tGoalDefense\tPoint\n");
        int rank = 1;
        for (Club club : arrClub) {
            System.out.printf("%d.\t%s\t\t%d\t%d\t%d\t%d\t%d\t\t%d\t\t%d\n", rank++, club.getName(), club.getPlay(),
                    club.getWin(), club.getDraw(),
                    club.getLose(), club.getGoalAttack(), club.getGoalDefense(), club.getPoint());
        }

        System.out.println("--------------------------------------");
    }
}
