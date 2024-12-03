import java.util.ArrayList;
import java.util.Scanner;

public class Soccer {
    private static ArrayList<League> leagues = new ArrayList<>(); // List of leagues

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        init();

        while (true) {
            System.out.println("Menu League");
            System.out.println("1. Create League");
            System.out.println("2. Add Club");
            System.out.println("3. View League");
            System.out.println("4. Scheduling");
            System.out.println("5. Match Score");
            System.out.println("6. League Table");
            System.out.println("7. View All Leagues");
            System.out.println("8. Exit");
            System.out.print("Choose menu league = ");
            int choice = s.nextInt();
            s.nextLine(); 

            switch (choice) {
                case 1:
                    createLeague(s);
                    break;
                case 2:
                    manageClubMenu(s);
                    break;
                case 3:
                    viewLeagueDetails(s);
                    break;
                case 4:
                    scheduleMatches(s);
                    break;
                case 5:
                    generateMatchScores(s);
                    break;
                case 6:
                    viewLeagueTable(s);
                    break;
                case 7:
                    viewAllLeagues();
                    break;
                case 8:
                    System.out.println("Exiting program...");
                    s.close();
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void createLeague(Scanner s) {
        System.out.println("--------------------------------------");
        System.out.println("Create New League");
        System.out.println("--------------------------------------");
        System.out.print("League Name: ");
        String leagueName = s.nextLine();
        System.out.print("Year: ");
        int leagueYear = s.nextInt();
        s.nextLine(); // Clear buffer

        League league = new League(leagueName, leagueYear);
        leagues.add(league);

        System.out.println("League successfully created!");
        System.out.println("--------------------------------------");
    }

    private static void manageClubMenu(Scanner s) {
        System.out.print("Enter league name: ");
        String leagueName = s.nextLine();
        League league = findLeagueByName(leagueName);

        if (league != null) {
            while (true) {
                System.out.println("Menu Club");
                System.out.println("1. Create Club");
                System.out.println("2. Add Coach");
                System.out.println("3. Add Player");
                System.out.println("4. View Club");
                System.out.println("5. Exit");
                System.out.print("Choose menu club = ");
                int choice = s.nextInt();
                s.nextLine();

                switch (choice) {
                    case 1:
                        createClub(s, league);
                        break;
                    case 2:
                        addCoachToClub(s, league);
                        break;
                    case 3:
                        addPlayerToClub(s, league);
                        break;
                    case 4:
                        viewClubDetails(s, league);
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Invalid choice! Please select a valid menu option.");
                }
            }
        } else {
            System.out.println("League not found!");
        }
    }

    private static void createClub(Scanner s, League league) {
        System.out.println("--------------------------------------");
        System.out.println("Create New Club");
        System.out.println("--------------------------------------");
        System.out.print("Club Name: ");
        String clubName = s.nextLine();
        System.out.print("Stadium Name: ");
        String stadiumName = s.nextLine();
        System.out.print("Stadium Capacity: ");
        int stadiumCapacity = s.nextInt();
        s.nextLine(); 

        Club club = new Club(clubName, stadiumName, stadiumCapacity);
        league.setClub(club);

        System.out.println("Club successfully created!");
        System.out.println("--------------------------------------");
    }

    private static void addCoachToClub(Scanner s, League league) {
        System.out.print("Enter club name: ");
        String clubName = s.nextLine();
        Club club = findClubByName(league, clubName);

        if (club != null) {
            System.out.println("--------------------------------------");
            System.out.println("Add New Coach");
            System.out.println("--------------------------------------");
            System.out.print("Coach Name: ");
            String coachName = s.nextLine();
            System.out.print("Age: ");
            int coachAge = s.nextInt();
            s.nextLine();
            System.out.print("Nationality: ");
            String coachNationality = s.nextLine();
            System.out.print("Years of Experience: ");
            int coachExp = s.nextInt();
            s.nextLine();

            Coach coach = new Coach(coachName, coachAge, coachNationality, coachExp);
            club.setCoach(coach);

            System.out.println("Coach successfully added!");
            System.out.println("--------------------------------------");
        } else {
            System.out.println("Club not found!");
        }
    }

    private static void addPlayerToClub(Scanner s, League league) {
        System.out.print("Enter club name: ");
        String clubName = s.nextLine();
        Club club = findClubByName(league, clubName);

        if (club != null) {
            System.out.println("--------------------------------------");
            System.out.println("Add New Player");
            System.out.println("--------------------------------------");
            System.out.print("Player Name: ");
            String playerName = s.nextLine();
            System.out.print("Age: ");
            int playerAge = s.nextInt();
            s.nextLine();
            System.out.print("Nationality: ");
            String playerNationality = s.nextLine();
            System.out.print("Position: ");
            String playerPosition = s.nextLine();
            System.out.print("Shooting: ");
            int shooting = s.nextInt();
            System.out.print("Heading: ");
            int heading = s.nextInt();
            System.out.print("Passing: ");
            int passing = s.nextInt();
            System.out.print("Sliding: ");
            int sliding = s.nextInt();
            System.out.print("Running: ");
            int running = s.nextInt();
            s.nextLine(); 

            Player player = new Player(playerName, playerAge, playerNationality, shooting, sliding, heading, running, passing, playerPosition);
            club.addPlayers(player);

            System.out.println("Player successfully added!");
            System.out.println("--------------------------------------");
        } else {
            System.out.println("Club not found!");
        }
    }

    private static void viewClubDetails(Scanner s, League league) {
        System.out.print("Enter club name: ");
        String clubName = s.nextLine();
        Club club = findClubByName(league, clubName);

        if (club != null) {
            club.viewClub();
        } else {
            System.out.println("Club not found!");
        }
    }

    private static void viewLeagueDetails(Scanner s) {
        System.out.print("Enter league name: ");
        String leagueName = s.nextLine();
        League league = findLeagueByName(leagueName);

        if (league != null) {
            System.out.println("--------------------------------------");
            System.out.println("League Details");
            System.out.println("--------------------------------------");
            System.out.println("Name: " + league.getName());
            System.out.println("Year: " + league.getYear());
            System.out.println("Clubs:");
            league.getClubArray().forEach(club -> System.out.println("- " + club.getName()));
            System.out.println("--------------------------------------");
        } else {
            System.out.println("League not found!");
        }
    }

    private static void scheduleMatches(Scanner s) {
        System.out.print("Enter league name: ");
        String leagueName = s.nextLine();
        League league = findLeagueByName(leagueName);

        if (league != null) {
            league.scheduling();
        } else {
            System.out.println("League not found!");
        }
    }

    private static void generateMatchScores(Scanner s) {
        System.out.print("Enter league name: ");
        String leagueName = s.nextLine();
        League league = findLeagueByName(leagueName);

        if (league != null) {
            league.matchScore();
        } else {
            System.out.println("League not found!");
        }
    }

    private static void viewLeagueTable(Scanner s) {
        System.out.print("Enter league name: ");
        String leagueName = s.nextLine();
        League league = findLeagueByName(leagueName);

        if (league != null) {
            league.leagueTable();
        } else {
            System.out.println("League not found!");
        }
    }

    private static void viewAllLeagues() {
        System.out.println("--------------------------------------");
        System.out.println("Available Leagues:");
        if (leagues.isEmpty()) {
            System.out.println("No leagues created yet.");
        } else {
            leagues.forEach(league -> System.out.println("- " + league.getName() + " (" + league.getYear() + ")"));
        }
        System.out.println("--------------------------------------");
    }

    private static League findLeagueByName(String leagueName) {
        return leagues.stream()
                .filter(league -> league.getName().equalsIgnoreCase(leagueName))
                .findFirst()
                .orElse(null);
    }

    private static Club findClubByName(League league, String clubName) {
        return league.getClubArray().stream()
                .filter(club -> club.getName().equalsIgnoreCase(clubName))
                .findFirst()
                .orElse(null);
    }

    private static void init() {
        // Create Leagues
        League premierLeague = new League("Premier League", 2024);
        League laLiga = new League("La Liga", 2024);
    
        // Add Clubs to Premier League
        Club manchesterUnited = new Club("Manchester United", "Old Trafford", 75000);
        Club arsenal = new Club("Arsenal", "Emirates Stadium", 60000);
        Club chelsea = new Club("Chelsea", "Stamford Bridge", 42000);
        Club liverpool = new Club("Liverpool", "Anfield", 54000);
        Club manchesterCity = new Club("Manchester City", "Etihad Stadium", 55000);
    
        premierLeague.setClub(manchesterUnited);
        premierLeague.setClub(arsenal);
        premierLeague.setClub(chelsea);
        premierLeague.setClub(liverpool);
        premierLeague.setClub(manchesterCity);
    
        // Add Clubs to La Liga
        Club barcelona = new Club("Barcelona", "Camp Nou", 99000);
        Club realMadrid = new Club("Real Madrid", "Santiago Bernabeu", 81000);
        Club atleticoMadrid = new Club("Atletico Madrid", "Metropolitano", 68000);
        Club sevilla = new Club("Sevilla", "Ramon Sanchez Pizjuan", 43000);
        Club valencia = new Club("Valencia", "Mestalla", 49000);
    
        laLiga.setClub(barcelona);
        laLiga.setClub(realMadrid);
        laLiga.setClub(atleticoMadrid);
        laLiga.setClub(sevilla);
        laLiga.setClub(valencia);
    
        // Add Leagues to the system
        leagues.add(premierLeague);
        leagues.add(laLiga);
    
        // Add Coaches
        manchesterUnited.setCoach(new Coach("Erik Ten Hag", 53, "Netherlands", 25));
        arsenal.setCoach(new Coach("Mikel Arteta", 40, "Spain", 10));
        chelsea.setCoach(new Coach("Mauricio Pochettino", 51, "Argentina", 20));
        liverpool.setCoach(new Coach("Jurgen Klopp", 55, "Germany", 25));
        manchesterCity.setCoach(new Coach("Pep Guardiola", 52, "Spain", 15));
    
        barcelona.setCoach(new Coach("Xavi Hernandez", 43, "Spain", 5));
        realMadrid.setCoach(new Coach("Carlo Ancelotti", 64, "Italy", 30));
        atleticoMadrid.setCoach(new Coach("Diego Simeone", 53, "Argentina", 20));
        sevilla.setCoach(new Coach("Jose Luis Mendilibar", 62, "Spain", 20));
        valencia.setCoach(new Coach("Ruben Baraja", 48, "Spain", 8));
    
        // Add Players to Manchester United
        manchesterUnited.addPlayers(new Player("Marcus Rashford", 25, "England", 85, 80, 75, 90, 88, "Forward"));
        manchesterUnited.addPlayers(new Player("Bruno Fernandes", 28, "Portugal", 87, 70, 85, 80, 85, "Midfielder"));
        manchesterUnited.addPlayers(new Player("Casemiro", 31, "Brazil", 80, 78, 85, 88, 83, "Midfielder"));
    
        // Add Players to Barcelona
        barcelona.addPlayers(new Player("Robert Lewandowski", 34, "Poland", 92, 88, 80, 75, 84, "Forward"));
        barcelona.addPlayers(new Player("Pedri", 20, "Spain", 80, 70, 85, 78, 85, "Midfielder"));
        barcelona.addPlayers(new Player("Frenkie de Jong", 25, "Netherlands", 85, 75, 85, 83, 88, "Midfielder"));
    
        // Add Players to Real Madrid
        realMadrid.addPlayers(new Player("Karim Benzema", 35, "France", 90, 85, 80, 70, 88, "Forward"));
        realMadrid.addPlayers(new Player("Luka Modric", 37, "Croatia", 88, 70, 90, 75, 80, "Midfielder"));
        realMadrid.addPlayers(new Player("Vinicius Jr.", 22, "Brazil", 92, 85, 80, 85, 90, "Forward"));
    
        // Add Players to Arsenal
        arsenal.addPlayers(new Player("Bukayo Saka", 21, "England", 85, 75, 80, 82, 88, "Forward"));
        arsenal.addPlayers(new Player("Martin Odegaard", 24, "Norway", 88, 72, 90, 78, 84, "Midfielder"));
        arsenal.addPlayers(new Player("Gabriel Jesus", 25, "Brazil", 87, 80, 75, 85, 83, "Forward"));
    
        // Add Players to Atletico Madrid
        atleticoMadrid.addPlayers(new Player("Antoine Griezmann", 31, "France", 88, 82, 85, 75, 84, "Forward"));
        atleticoMadrid.addPlayers(new Player("Joao Felix", 22, "Portugal", 85, 78, 82, 75, 85, "Forward"));
        atleticoMadrid.addPlayers(new Player("Koke", 30, "Spain", 82, 75, 88, 70, 80, "Midfielder"));
    }
    
}
