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
            System.out.println("Pace");
            int pace = s.nextInt();
            System.out.print("Shooting: ");
            int shooting = s.nextInt();
            System.out.print("Passing: ");
            int passing = s.nextInt();
            System.out.print("Dribbling: ");
            int dribbling = s.nextInt();
            System.out.print("Defending: ");
            int defending = s.nextInt();
            System.out.print("Physical: ");
            int physical = s.nextInt();
            s.nextLine();

            Player player = new Player(playerName, playerAge, playerNationality, pace, shooting, dribbling, passing,
                    defending, physical, playerPosition);
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

        // Add Players to Barcelona
        barcelona.addPlayers(new Player("Robert Lewandowski", 34, "Poland", 82, 92, 75, 80, 85, 85, "Forward"));
        barcelona.addPlayers(new Player("Pedri", 20, "Spain", 85, 80, 88, 85, 70, 78, "Midfielder"));
        barcelona.addPlayers(new Player("Frenkie de Jong", 25, "Netherlands", 80, 85, 90, 85, 78, 82, "Midfielder"));
        barcelona.addPlayers(new Player("Gerard Piqué", 36, "Spain", 70, 60, 80, 75, 90, 85, "Defender"));
        barcelona.addPlayers(new Player("Jules Koundé", 25, "France", 80, 70, 82, 80, 85, 80, "Defender"));
        barcelona.addPlayers(new Player("Marc-André ter Stegen", 31, "Germany", 50, 88, 80, 75, 87, 90, "Goalkeeper"));
        barcelona.addPlayers(new Player("Ansu Fati", 21, "Spain", 90, 82, 78, 85, 70, 78, "Forward"));
        barcelona.addPlayers(new Player("Sergi Roberto", 32, "Spain", 80, 70, 85, 80, 78, 80, "Midfielder"));
        barcelona.addPlayers(new Player("Dani Alves", 40, "Brazil", 85, 70, 82, 80, 88, 83, "Defender"));
        barcelona.addPlayers(new Player("Raphinha", 27, "Brazil", 89, 83, 85, 80, 76, 82, "Forward"));
        barcelona.addPlayers(new Player("Sergio Busquets", 35, "Spain", 60, 60, 90, 70, 85, 80, "Midfielder"));

        // Add Players to Real Madrid
        realMadrid.addPlayers(new Player("Karim Benzema", 35, "France", 75, 90, 85, 80, 82, 88, "Forward"));
        realMadrid.addPlayers(new Player("Luka Modric", 37, "Croatia", 80, 88, 90, 85, 70, 80, "Midfielder"));
        realMadrid.addPlayers(new Player("Vinicius Jr.", 22, "Brazil", 90, 92, 82, 85, 75, 83, "Forward"));
        realMadrid.addPlayers(new Player("Toni Kroos", 34, "Germany", 70, 80, 92, 85, 75, 80, "Midfielder"));
        realMadrid.addPlayers(new Player("David Alaba", 31, "Austria", 75, 70, 85, 80, 88, 84, "Defender"));
        realMadrid.addPlayers(new Player("Thibaut Courtois", 31, "Belgium", 50, 88, 80, 75, 87, 90, "Goalkeeper"));
        realMadrid.addPlayers(new Player("Ferland Mendy", 29, "France", 82, 70, 75, 85, 90, 84, "Defender"));
        realMadrid.addPlayers(new Player("Antonio Rudiger", 30, "Germany", 75, 70, 80, 82, 90, 85, "Defender"));
        realMadrid.addPlayers(new Player("Eduardo Camavinga", 21, "France", 85, 70, 82, 85, 78, 80, "Midfielder"));
        realMadrid.addPlayers(new Player("Rodrygo", 23, "Brazil", 87, 80, 85, 90, 78, 85, "Forward"));
        realMadrid.addPlayers(new Player("Casemiro", 32, "Brazil", 70, 75, 85, 80, 90, 85, "Midfielder"));

        // Add Players to Manchester United
        manchesterUnited.addPlayers(new Player("Marcus Rashford", 25, "England", 85, 80, 75, 90, 88, 85, "Forward"));
        manchesterUnited
                .addPlayers(new Player("Bruno Fernandes", 28, "Portugal", 80, 87, 85, 80, 75, 80, "Midfielder"));
        manchesterUnited.addPlayers(new Player("Casemiro", 31, "Brazil", 78, 80, 85, 83, 88, 84, "Midfielder"));
        manchesterUnited.addPlayers(new Player("Harry Maguire", 31, "England", 70, 65, 80, 75, 90, 85, "Defender"));
        manchesterUnited.addPlayers(new Player("Cristiano Ronaldo", 39, "Portugal", 85, 92, 75, 90, 75, 80, "Forward"));
        manchesterUnited.addPlayers(new Player("David De Gea", 33, "Spain", 50, 85, 75, 70, 88, 90, "Goalkeeper"));
        manchesterUnited.addPlayers(new Player("Jadon Sancho", 24, "England", 85, 80, 85, 90, 75, 80, "Forward"));
        manchesterUnited.addPlayers(new Player("Raphaël Varane", 30, "France", 70, 65, 85, 80, 90, 85, "Defender"));
        manchesterUnited.addPlayers(new Player("Luke Shaw", 29, "England", 75, 70, 82, 75, 88, 85, "Defender"));
        manchesterUnited.addPlayers(new Player("Antony", 23, "Brazil", 87, 80, 80, 85, 75, 85, "Forward"));
        manchesterUnited
                .addPlayers(new Player("Donny van de Beek", 26, "Netherlands", 75, 70, 85, 82, 80, 80, "Midfielder"));

        // Add Players to Sevilla
        sevilla.addPlayers(new Player("Youssef En-Nesyri", 26, "Morocco", 84, 80, 75, 80, 85, 84, "Forward"));
        sevilla.addPlayers(new Player("Ivan Rakitić", 35, "Croatia", 70, 85, 90, 80, 75, 82, "Midfielder"));
        sevilla.addPlayers(new Player("Jesús Navas", 37, "Spain", 85, 80, 78, 80, 80, 82, "Defender"));
        sevilla.addPlayers(new Player("Bono", 33, "Morocco", 50, 87, 75, 70, 88, 90, "Goalkeeper"));
        sevilla.addPlayers(new Player("Jules Koundé", 24, "France", 82, 80, 75, 82, 85, 85, "Defender"));
        sevilla.addPlayers(new Player("Lucas Ocampos", 29, "Argentina", 84, 82, 78, 85, 75, 80, "Forward"));
        sevilla.addPlayers(new Player("Fernando", 36, "Brazil", 75, 80, 85, 80, 75, 82, "Midfielder"));
        sevilla.addPlayers(new Player("Papu Gómez", 35, "Argentina", 80, 83, 85, 75, 75, 78, "Midfielder"));
        sevilla.addPlayers(new Player("Joan Jordán", 29, "Spain", 78, 80, 82, 75, 85, 78, "Midfielder"));
        sevilla.addPlayers(new Player("Erik Lamela", 31, "Argentina", 85, 78, 75, 82, 80, 85, "Forward"));
        sevilla.addPlayers(new Player("Karim Rekik", 28, "Netherlands", 75, 80, 85, 80, 85, 80, "Defender"));

        // Add Players to Atletico Madrid
        atleticoMadrid.addPlayers(new Player("Antoine Griezmann", 31, "France", 85, 88, 75, 85, 84, 83, "Forward"));
        atleticoMadrid.addPlayers(new Player("João Félix", 22, "Portugal", 82, 80, 75, 85, 80, 80, "Forward"));
        atleticoMadrid.addPlayers(new Player("Jan Oblak", 30, "Slovenia", 50, 90, 85, 75, 87, 90, "Goalkeeper"));
        atleticoMadrid.addPlayers(new Player("José Giménez", 28, "Uruguay", 75, 80, 75, 85, 90, 85, "Defender"));
        atleticoMadrid.addPlayers(new Player("Marcos Llorente", 28, "Spain", 80, 82, 75, 85, 85, 80, "Midfielder"));
        atleticoMadrid.addPlayers(new Player("Stefan Savić", 32, "Montenegro", 70, 75, 75, 80, 90, 85, "Defender"));
        atleticoMadrid.addPlayers(new Player("Koke", 30, "Spain", 70, 80, 75, 88, 82, 84, "Midfielder"));
        atleticoMadrid.addPlayers(new Player("Yannick Carrasco", 29, "Belgium", 85, 85, 75, 85, 75, 80, "Forward"));
        atleticoMadrid.addPlayers(new Player("Ángel Correa", 28, "Argentina", 80, 85, 78, 75, 80, 78, "Forward"));
        atleticoMadrid.addPlayers(new Player("Rodrigo De Paul", 29, "Argentina", 75, 80, 78, 80, 85, 82, "Midfielder"));
        atleticoMadrid.addPlayers(new Player("Reinildo Mandava", 29, "Mozambique", 70, 75, 75, 85, 88, 83, "Defender"));

        // Add Players to Chelsea
        chelsea.addPlayers(new Player("N'Golo Kanté", 33, "France", 75, 70, 88, 80, 85, 90, "Midfielder"));
        chelsea.addPlayers(new Player("Raheem Sterling", 29, "England", 85, 80, 85, 88, 75, 80, "Forward"));
        chelsea.addPlayers(new Player("Mason Mount", 25, "England", 80, 75, 85, 78, 70, 82, "Midfielder"));
        chelsea.addPlayers(new Player("Thiago Silva", 40, "Brazil", 70, 75, 80, 78, 85, 90, "Defender"));
        chelsea.addPlayers(new Player("Ben Chilwell", 27, "England", 80, 75, 80, 85, 80, 85, "Defender"));
        chelsea.addPlayers(new Player("Kepa Arrizabalaga", 29, "Spain", 50, 85, 75, 70, 88, 90, "Goalkeeper"));
        chelsea.addPlayers(new Player("Pierre-Emerick Aubameyang", 34, "Gabon", 90, 87, 80, 82, 75, 80, "Forward"));
        chelsea.addPlayers(new Player("Christian Pulisic", 25, "USA", 85, 80, 78, 85, 70, 80, "Forward"));
        chelsea.addPlayers(new Player("Kai Havertz", 25, "Germany", 80, 85, 85, 80, 75, 80, "Forward"));
        chelsea.addPlayers(new Player("Jorginho", 32, "Italy", 70, 75, 85, 80, 78, 80, "Midfielder"));
        chelsea.addPlayers(new Player("César Azpilicueta", 34, "Spain", 75, 70, 80, 78, 85, 80, "Defender"));

        // Add Players to Liverpool
        liverpool.addPlayers(new Player("Mohamed Salah", 32, "Egypt", 90, 92, 85, 88, 75, 82, "Forward"));
        liverpool.addPlayers(new Player("Virgil van Dijk", 33, "Netherlands", 70, 80, 75, 80, 90, 88, "Defender"));
        liverpool.addPlayers(new Player("Alisson Becker", 31, "Brazil", 50, 88, 80, 75, 88, 90, "Goalkeeper"));
        liverpool.addPlayers(new Player("Sadio Mané", 32, "Senegal", 88, 90, 85, 87, 80, 85, "Forward"));
        liverpool.addPlayers(new Player("Fabinho", 30, "Brazil", 75, 75, 80, 80, 85, 85, "Midfielder"));
        liverpool.addPlayers(new Player("Trent Alexander-Arnold", 26, "England", 85, 80, 85, 80, 75, 80, "Defender"));
        liverpool.addPlayers(new Player("Jordan Henderson", 34, "England", 75, 75, 80, 82, 85, 80, "Midfielder"));
        liverpool.addPlayers(new Player("Diogo Jota", 27, "Portugal", 85, 80, 78, 85, 75, 80, "Forward"));
        liverpool.addPlayers(new Player("Darwin Núñez", 24, "Uruguay", 90, 82, 75, 85, 75, 80, "Forward"));
        liverpool.addPlayers(new Player("Luis Díaz", 27, "Colombia", 87, 80, 75, 85, 80, 85, "Forward"));
        liverpool.addPlayers(new Player("Ibrahima Konaté", 25, "France", 75, 70, 80, 75, 90, 85, "Defender"));

        // Add Players to Manchester City
        manchesterCity.addPlayers(new Player("Erling Haaland", 24, "Norway", 85, 92, 80, 75, 85, 90, "Forward"));
        manchesterCity.addPlayers(new Player("Kevin De Bruyne", 33, "Belgium", 75, 88, 92, 85, 70, 80, "Midfielder"));
        manchesterCity.addPlayers(new Player("Ruben Dias", 27, "Portugal", 70, 75, 80, 80, 90, 85, "Defender"));
        manchesterCity.addPlayers(new Player("Phil Foden", 24, "England", 85, 80, 85, 88, 75, 80, "Midfielder"));
        manchesterCity.addPlayers(new Player("Jack Grealish", 28, "England", 80, 78, 85, 82, 75, 80, "Forward"));
        manchesterCity.addPlayers(new Player("Ederson", 30, "Brazil", 50, 85, 75, 70, 88, 90, "Goalkeeper"));
        manchesterCity.addPlayers(new Player("Bernardo Silva", 29, "Portugal", 80, 85, 90, 85, 70, 82, "Midfielder"));
        manchesterCity.addPlayers(new Player("John Stones", 30, "England", 70, 75, 80, 80, 85, 84, "Defender"));
        manchesterCity.addPlayers(new Player("Rodri", 27, "Spain", 70, 75, 85, 80, 85, 85, "Midfielder"));
        manchesterCity.addPlayers(new Player("Kyle Walker", 34, "England", 85, 70, 75, 80, 85, 88, "Defender"));
        manchesterCity.addPlayers(new Player("Julian Álvarez", 24, "Argentina", 85, 80, 80, 82, 75, 80, "Forward"));

        // Add Players to Arsenal
        arsenal.addPlayers(new Player("Bukayo Saka", 22, "England", 85, 80, 85, 88, 75, 80, "Forward"));
        arsenal.addPlayers(new Player("Martin Ødegaard", 25, "Norway", 75, 85, 90, 80, 70, 82, "Midfielder"));
        arsenal.addPlayers(new Player("Aaron Ramsdale", 26, "England", 50, 85, 75, 70, 88, 90, "Goalkeeper"));
        arsenal.addPlayers(new Player("Gabriel Jesus", 27, "Brazil", 85, 82, 75, 85, 75, 80, "Forward"));
        arsenal.addPlayers(new Player("William Saliba", 23, "France", 75, 70, 80, 75, 90, 85, "Defender"));
        arsenal.addPlayers(new Player("Ben White", 26, "England", 80, 75, 80, 78, 85, 80, "Defender"));
        arsenal.addPlayers(new Player("Granit Xhaka", 31, "Switzerland", 70, 75, 85, 80, 80, 85, "Midfielder"));
        arsenal.addPlayers(new Player("Thomas Partey", 30, "Ghana", 75, 80, 78, 80, 88, 85, "Midfielder"));
        arsenal.addPlayers(new Player("Gabriel Martinelli", 23, "Brazil", 87, 82, 75, 85, 75, 80, "Forward"));
        arsenal.addPlayers(new Player("Kieran Tierney", 26, "Scotland", 80, 75, 78, 82, 85, 85, "Defender"));
        arsenal.addPlayers(new Player("Oleksandr Zinchenko", 27, "Ukraine", 80, 75, 80, 78, 80, 82, "Defender"));
    }

}
