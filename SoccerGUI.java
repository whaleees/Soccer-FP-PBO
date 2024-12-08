import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

public class SoccerGUI {
  private static ArrayList<League> leagues = new ArrayList<>();
  private static final String FONT_NAME = "Arial";
  private static final Color BLACK = new Color(30, 30, 30);
  private static final Color BACKGROUND_COLOR = new Color(40, 40, 40);

  public SoccerGUI() {
    initializeLeagues();
    JFrame frame = new JFrame("Soccer GM");
    setupHomeFrame(frame);
  }

  private void setupHomeFrame(JFrame frame) {
    frame.getContentPane().removeAll();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1000, 600);
    frame.setLayout(new BorderLayout());
    frame.setLocationRelativeTo(null);

    frame.getContentPane().setBackground(BLACK);

    JPanel headerPanel = new JPanel();
    headerPanel.setLayout(new BorderLayout());
    headerPanel.setBackground(BACKGROUND_COLOR);

    JLabel headerTitleLabel = new JLabel("Soccer GM", SwingConstants.CENTER);
    headerTitleLabel.setFont(new Font(FONT_NAME, Font.BOLD, 28));
    headerTitleLabel.setForeground(Color.WHITE);
    headerTitleLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 10, 0));

    JLabel headerSubLabel = new JLabel("Build. Manage. Dominate.", SwingConstants.CENTER);
    headerSubLabel.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
    headerSubLabel.setForeground(Color.WHITE);
    headerSubLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

    headerPanel.add(headerTitleLabel, BorderLayout.NORTH);
    headerPanel.add(headerSubLabel, BorderLayout.CENTER);
    frame.add(headerPanel, BorderLayout.NORTH);

    JPanel leagueMenuPanel = new JPanel();
    leagueMenuPanel.setLayout(new GridLayout(3, 1, 20, 20));
    leagueMenuPanel.setBackground(BACKGROUND_COLOR);
    leagueMenuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JButton createLeagueButton = createStyledButton("Create League");
    createLeagueButton.setFont(new Font(FONT_NAME, Font.BOLD, 18));
    JButton viewLeagueButton = createStyledButton("View All Leagues");
    viewLeagueButton.setFont(new Font(FONT_NAME, Font.BOLD, 18));
    JButton exitButton = createStyledButton("Exit");

    createLeagueButton.addActionListener(e -> createLeague(frame));
    viewLeagueButton.addActionListener(e -> viewAllLeagues(frame));
    exitButton.addActionListener(e -> System.exit(0));

    leagueMenuPanel.add(createLeagueButton);
    leagueMenuPanel.add(viewLeagueButton);
    leagueMenuPanel.add(exitButton);

    frame.add(leagueMenuPanel, BorderLayout.CENTER);

    frame.revalidate();
    frame.repaint();
    frame.setVisible(true);
  }

  private void createLeague(JFrame frame) {
    frame.getContentPane().removeAll();

    JPanel createLeaguePanel = new JPanel();
    createLeaguePanel.setLayout(new BoxLayout(createLeaguePanel, BoxLayout.Y_AXIS));

    JPanel centerPanel = new JPanel(new GridBagLayout());
    centerPanel.setBackground(BLACK);
    centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(10, 10, 10, 10);

    JLabel createLeagueLabel = new JLabel("Create League", SwingConstants.CENTER);
    createLeagueLabel.setFont(new Font(FONT_NAME, Font.BOLD, 24));
    createLeagueLabel.setForeground(Color.WHITE);
    gbc.gridwidth = 3;
    gbc.gridx = 0;
    gbc.gridy = 0;
    centerPanel.add(createLeagueLabel, gbc);

    JLabel leagueNameLabel = createStyledLabel("League Name");
    JTextField leagueNameField = createStyledTextField();
    JButton randomNameButton = createStyledButton("Random");
    leagueNameField.setPreferredSize(new Dimension(300, randomNameButton.getPreferredSize().height));
    gbc.gridwidth = 1;
    gbc.gridx = 0;
    gbc.gridy = 1;
    centerPanel.add(leagueNameLabel, gbc);
    gbc.gridx = 1;
    centerPanel.add(leagueNameField, gbc);
    gbc.gridx = 2;
    centerPanel.add(randomNameButton, gbc);

    JLabel yearLabel = createStyledLabel("Year");
    JTextField yearField = createStyledTextField();
    JButton randomYearButton = createStyledButton("Random");
    yearField.setPreferredSize(new Dimension(300, randomYearButton.getPreferredSize().height));
    gbc.gridx = 0;
    gbc.gridy = 2;
    centerPanel.add(yearLabel, gbc);
    gbc.gridx = 1;
    centerPanel.add(yearField, gbc);
    gbc.gridx = 2;
    centerPanel.add(randomYearButton, gbc);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(BLACK);
    buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

    JButton createButton = createStyledButton("Create");
    JButton backButton = createStyledButton("Back");
    buttonPanel.add(createButton);
    buttonPanel.add(backButton);
    gbc.gridwidth = 3;
    gbc.gridx = 0;
    gbc.gridy = 3;
    centerPanel.add(buttonPanel, gbc);

    createButton.addActionListener(e -> {
      String name = leagueNameField.getText().trim();
      String yearText = yearField.getText().trim();
      if (name.isEmpty()) {
        showErrorDialog(frame, "Please enter a league name!");
        return;
      }
      if (yearText.isEmpty()) {
        showErrorDialog(frame, "Please enter a year!");
        return;
      }
      try {
        int year = Integer.parseInt(yearText);
        if (year < 1980 || year > 2050) {
          showErrorDialog(frame, "Please enter a valid year between 1980 and 2050!");
          return;
        }
        if (findLeagueByName(name) != null) {
          showErrorDialog(frame, "League already exists!");
          return;
        }
        leagues.add(new League(name, year));
        showInfoDialog(frame, "League created successfully!");
        leagueNameField.setText("");
        yearField.setText("");
      } catch (NumberFormatException ex) {
        showErrorDialog(frame, "Please enter a valid year!");
      }
    });

    backButton.addActionListener(e -> setupHomeFrame(frame));
    randomNameButton.addActionListener(e -> leagueNameField.setText(generateRandomLeagueName()));
    randomYearButton.addActionListener(e -> yearField.setText(String.valueOf(new Random().nextInt(33) + 2017)));

    frame.add(centerPanel, BorderLayout.CENTER);
    frame.revalidate();
    frame.repaint();
  }

  private void viewAllLeagues(JFrame frame) {
    frame.getContentPane().removeAll();

    JPanel leaguePanel = new JPanel();
    leaguePanel.setLayout(new BoxLayout(leaguePanel, BoxLayout.Y_AXIS));
    leaguePanel.setBackground(BACKGROUND_COLOR);
    leaguePanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));

    JLabel titleLabel = new JLabel("All Leagues");
    titleLabel.setFont(new Font(FONT_NAME, Font.BOLD, 28));
    titleLabel.setForeground(Color.WHITE);
    titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
    leaguePanel.add(titleLabel);

    leaguePanel.add(Box.createRigidArea(new Dimension(0, 20)));

    final int PANEL_WIDTH = 550;
    final int PANEL_HEIGHT = 60;

    for (League league : leagues) {
      JPanel leagueItemPanel = new JPanel(new BorderLayout());
      leagueItemPanel.setBackground(BACKGROUND_COLOR);
      leagueItemPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
      leagueItemPanel.setMaximumSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

      JLabel leagueLabel = new JLabel(league.getName() + " (" + league.getYear() + ")");
      leagueLabel.setFont(new Font(FONT_NAME, Font.BOLD, 18));
      leagueLabel.setForeground(Color.WHITE);
      leagueLabel.setPreferredSize(new Dimension(PANEL_WIDTH - 150, PANEL_HEIGHT));

      JButton viewMoreButton = createStyledButton("View More");
      viewMoreButton.setPreferredSize(new Dimension(150, PANEL_HEIGHT)); // Fixed button width
      viewMoreButton.addActionListener(e -> viewLeagueDetails(frame, league));

      leagueItemPanel.add(leagueLabel, BorderLayout.CENTER);
      leagueItemPanel.add(viewMoreButton, BorderLayout.EAST);

      leaguePanel.add(leagueItemPanel);
      leaguePanel.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    JPanel backButtonPanel = new JPanel();
    backButtonPanel.setBackground(BACKGROUND_COLOR);
    JButton backButton = createStyledButton("Back");
    backButton.addActionListener(e -> setupHomeFrame(frame));
    backButtonPanel.add(backButton);
    backButtonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
    leaguePanel.add(backButtonPanel);

    JScrollPane scrollPane = new JScrollPane(leaguePanel);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    frame.add(scrollPane, BorderLayout.CENTER);

    frame.revalidate();
    frame.repaint();
  }

  private void viewLeagueDetails(JFrame frame, League league) {
    frame.getContentPane().removeAll();
    int currWeek = league.getCurrWeek();
    league.sortTable();

    JPanel detailPanel = new JPanel();
    detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
    detailPanel.setBackground(BACKGROUND_COLOR);

    JLabel headerLabel = createStyledLabel(league.getName() + " (" + league.getYear() + ")", 28,
        true, true);
    JPanel leagueTablePanel = setupLeagueTablePanel(frame, league);
    JLabel noClubLabel = createStyledLabel("No clubs in this league", 16, true, false);
    JPanel addClubPanel = setupAddClubPanel(frame, league);
    JPanel simulatePanel = setupSimulationPanel(frame, league, false);
    JPanel simulateSeasonPanel = setupSimulationPanel(frame, league, true);
    JPanel backButtonPanel = setupBackButtonPanel(frame, e -> viewAllLeagues(frame));

    Boolean canStartSeason = false;
    if (league.getClubArray().size() > 1 && league.getClubArray().stream()
        .allMatch(club -> club.getPlayers() != null && club.getPlayers().size() >= 11)) {
      canStartSeason = true;
    }

    JPanel startSeasonPanel = setupStartSeasonPanel(frame, league, canStartSeason);
    JPanel fixturesPanel = setupMatchPanel(league, currWeek, true);
    JButton viewFixturesButton = createStyledButton("View All Fixtures");
    viewFixturesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    viewFixturesButton.addActionListener(e -> viewAllMatches(frame, league, true));
    fixturesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    fixturesPanel.add(viewFixturesButton);

    JPanel resultsPanel = setupMatchPanel(league, currWeek == 0 ? currWeek : currWeek - 1, false);
    JButton viewResultsButton = createStyledButton("View All Results");
    viewResultsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    viewResultsButton.addActionListener(e -> viewAllMatches(frame, league, false));
    resultsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    resultsPanel.add(viewResultsButton);

    // Add all panels to the main detail panel
    detailPanel.add(headerLabel);
    if (league.getClubArray().size() == 0) {
      detailPanel.add(noClubLabel);
    } else {
      detailPanel.add(leagueTablePanel);
    }
    if (currWeek == 0) {
      detailPanel.add(addClubPanel);
    }

    if (currWeek == 0) {
      detailPanel.add(startSeasonPanel);
    }

    if (currWeek < league.getNumWeeks()) {
      detailPanel.add(fixturesPanel);
    }
    if (currWeek > 1) {
      detailPanel.add(resultsPanel);
    }
    if (currWeek < league.getNumWeeks() && currWeek > 0) {
      detailPanel.add(fixturesPanel);
    }

    if (currWeek > 0 && currWeek < league.getNumWeeks()) {
      detailPanel.add(simulatePanel);
      detailPanel.add(simulateSeasonPanel);
    }

    detailPanel.add(backButtonPanel);

    JScrollPane scrollPane = new JScrollPane(detailPanel);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    frame.add(scrollPane);

    frame.revalidate();
    frame.repaint();
  }

  private void viewAllMatches(JFrame frame, League league, boolean isFixtures) {
    frame.getContentPane().removeAll();

    JPanel totalMatchesPanel = new JPanel();
    totalMatchesPanel.setLayout(new BoxLayout(totalMatchesPanel, BoxLayout.Y_AXIS));
    totalMatchesPanel.setBackground(BACKGROUND_COLOR);

    JLabel headerLabel;
    if (isFixtures) {
      headerLabel = createStyledLabel("All Fixtures", 28, true, true);
    } else {
      headerLabel = createStyledLabel("All Results", 28, true, true);
    }
    totalMatchesPanel.add(headerLabel);

    if (isFixtures) {
      for (int week = league.getCurrWeek(); week <= league.getNumWeeks(); week++) {
        JPanel weekPanel = setupMatchPanel(league, week, true);
        totalMatchesPanel.add(weekPanel);
      }
    } else {
      for (int week = 1; week < league.getCurrWeek(); week++) {
        JPanel weekPanel = setupMatchPanel(league, week, false);
        totalMatchesPanel.add(weekPanel);
      }
    }

    JPanel backButtonPanel = setupBackButtonPanel(frame, e -> viewLeagueDetails(frame, league));
    totalMatchesPanel.add(backButtonPanel);

    JScrollPane scrollPane = new JScrollPane(totalMatchesPanel);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    frame.add(scrollPane);

    frame.revalidate();
    frame.repaint();
  }

  private void viewClubDetails(JFrame frame, League league, Club club) {
    frame.getContentPane().removeAll();

    JPanel viewClubPanel = new JPanel();
    viewClubPanel.setLayout(new BoxLayout(viewClubPanel, BoxLayout.Y_AXIS));
    viewClubPanel.setBackground(BACKGROUND_COLOR);

    JLabel headerLabel = createStyledLabel(club.getName(), 28, true, true);

    JLabel clubDetailsLabel = createStyledLabel("Club Details", 20, true, false);
    clubDetailsLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
    JPanel clubDetailsPanel = setupClubDetailsPanel(club);

    JPanel playerList = setupPlayerListPanel(frame, league, club);

    JPanel backButton = setupBackButtonPanel(frame, e -> viewLeagueDetails(frame, league));

    viewClubPanel.add(headerLabel);
    viewClubPanel.add(clubDetailsLabel);
    viewClubPanel.add(clubDetailsPanel);
    viewClubPanel.add(playerList);
    viewClubPanel.add(backButton);

    JScrollPane scrollPane = new JScrollPane(viewClubPanel);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

    frame.add(scrollPane, BorderLayout.CENTER);
    frame.revalidate();
    frame.repaint();
  }

  private void addNewPlayer(JFrame frame, League league, Club club) {
    frame.getContentPane().removeAll();

    JPanel addPlayerPanel = new JPanel();
    addPlayerPanel.setLayout(new BoxLayout(addPlayerPanel, BoxLayout.Y_AXIS));

    JPanel centerPanel = new JPanel(new GridBagLayout());
    centerPanel.setBackground(BLACK);
    centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(10, 10, 10, 10);

    // Player Details Section
    JLabel addPlayerLabel = createStyledLabel("Add a New Player", 24, true, true);
    addPlayerLabel.setHorizontalAlignment(SwingConstants.CENTER);
    gbc.gridwidth = 3;
    gbc.gridx = 0;
    gbc.gridy = 0;
    centerPanel.add(addPlayerLabel, gbc);

    // Name
    JLabel nameLabel = createStyledLabel("Name");
    JTextField nameField = createStyledTextField();
    JButton randomNameButton = createStyledButton("Random");
    nameField.setPreferredSize(new Dimension(300, randomNameButton.getPreferredSize().height));
    gbc.gridwidth = 1;
    gbc.gridx = 0;
    gbc.gridy = 1;
    centerPanel.add(nameLabel, gbc);
    gbc.gridx = 1;
    centerPanel.add(nameField, gbc);
    gbc.gridx = 2;
    centerPanel.add(randomNameButton, gbc);

    // Position
    JLabel posLabel = createStyledLabel("Position");
    String[] positions = { "FW", "MID", "DF", "GK" };
    JComboBox<String> posComboBox = new JComboBox<>(positions);
    gbc.gridx = 0;
    gbc.gridy = 2;
    centerPanel.add(posLabel, gbc);
    gbc.gridx = 1;
    centerPanel.add(posComboBox, gbc);

    // Age
    JLabel ageLabel = createStyledLabel("Age");
    JTextField ageField = createStyledTextField();
    JButton randomAgeButton = createStyledButton("Random");
    ageField.setPreferredSize(new Dimension(300, randomAgeButton.getPreferredSize().height));
    gbc.gridx = 0;
    gbc.gridy = 3;
    centerPanel.add(ageLabel, gbc);
    gbc.gridx = 1;
    centerPanel.add(ageField, gbc);
    gbc.gridx = 2;
    centerPanel.add(randomAgeButton, gbc);

    // Nationality
    JLabel natLabel = createStyledLabel("Nationality");
    JTextField natField = createStyledTextField();
    JButton randomNatButton = createStyledButton("Random");
    natField.setPreferredSize(new Dimension(300, randomNatButton.getPreferredSize().height));
    gbc.gridx = 0;
    gbc.gridy = 4;
    centerPanel.add(natLabel, gbc);
    gbc.gridx = 1;
    centerPanel.add(natField, gbc);
    gbc.gridx = 2;
    centerPanel.add(randomNatButton, gbc);

    // Skills
    String[] skillLabels = { "PAC", "SHO", "PAS", "DRI", "DEF", "PHY" };
    JTextField[] skillFields = new JTextField[skillLabels.length];
    JButton[] randomSkillButtons = new JButton[skillLabels.length];

    for (int i = 0; i < skillLabels.length; i++) {
      JLabel skillLabel = createStyledLabel(skillLabels[i]);
      skillFields[i] = createStyledTextField();
      randomSkillButtons[i] = createStyledButton("Random");
      skillFields[i].setPreferredSize(new Dimension(100, randomSkillButtons[i].getPreferredSize().height));
      gbc.gridx = 0;
      gbc.gridy = 5 + i;
      centerPanel.add(skillLabel, gbc);
      gbc.gridx = 1;
      centerPanel.add(skillFields[i], gbc);
      gbc.gridx = 2;
      centerPanel.add(randomSkillButtons[i], gbc);

      final int index = i;
      randomSkillButtons[i].addActionListener(e -> {
        int skillValue = new Random().nextInt(30) + 65;
        skillFields[index].setText(String.valueOf(skillValue));
      });
    }

    randomNameButton.addActionListener(e -> nameField.setText(generateRandomPersonName()));
    randomAgeButton.addActionListener(e -> ageField.setText(String.valueOf(new Random().nextInt(16) + 18)));
    randomNatButton.addActionListener(e -> natField.setText(generateRandomNationality()));

    // Button Panel
    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(BLACK);
    buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

    JButton createButton = createStyledButton("Add Player");
    JButton backButton = createStyledButton("Back");
    buttonPanel.add(createButton);
    buttonPanel.add(backButton);

    gbc.gridwidth = 3;
    gbc.gridx = 0;
    gbc.gridy = 11;
    centerPanel.add(buttonPanel, gbc);

    createButton.addActionListener(e -> {
      String name = nameField.getText().trim();
      String position = (String) posComboBox.getSelectedItem();
      String ageText = ageField.getText().trim();
      String nationality = natField.getText().trim();

      if (name.isEmpty() || ageText.isEmpty() || nationality.isEmpty()) {
        showErrorDialog(frame, "Please fill in all fields!");
        return;
      }

      try {
        int age = Integer.parseInt(ageText);
        int[] skills = new int[skillLabels.length];
        for (int i = 0; i < skillLabels.length; i++) {
          skills[i] = Integer.parseInt(skillFields[i].getText().trim());
        }

        Player newPlayer = new Player(name, age, nationality, skills[0], skills[1], skills[2], skills[3],
            skills[4], skills[5], position);
        club.addPlayers(newPlayer);

        showInfoDialog(frame, "Player successfully added!");

        // Clear fields
        nameField.setText("");
        ageField.setText("");
        natField.setText("");
        for (JTextField skillField : skillFields) {
          skillField.setText("");
        }
      } catch (NumberFormatException ex) {
        showErrorDialog(frame, "Please enter valid numbers for age and skills!");
      }
    });

    backButton.addActionListener(e -> viewClubDetails(frame, league, club));

    JScrollPane scrollPane = new JScrollPane(centerPanel);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

    frame.add(scrollPane, BorderLayout.CENTER);
    frame.revalidate();
    frame.repaint();
  }

  private void addNewClub(JFrame frame, League league) {
    frame.getContentPane().removeAll();

    JPanel addClubPanel = new JPanel();
    addClubPanel.setLayout(new BoxLayout(addClubPanel, BoxLayout.Y_AXIS));

    JPanel centerPanel = new JPanel(new GridBagLayout());
    centerPanel.setBackground(BLACK);
    centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(10, 10, 10, 10);

    // Club Details Section
    JLabel addClubLabel = createStyledLabel("Add a New Club", 24, true, true);
    addClubLabel.setHorizontalAlignment(SwingConstants.CENTER);
    gbc.gridwidth = 3;
    gbc.gridx = 0;
    gbc.gridy = 0;
    centerPanel.add(addClubLabel, gbc);

    JLabel clubNameLabel = createStyledLabel("Club Name");
    JTextField clubNameField = createStyledTextField();
    JButton randomClubNameButton = createStyledButton("Random");
    clubNameField.setPreferredSize(new Dimension(300, randomClubNameButton.getPreferredSize().height));
    gbc.gridwidth = 1;
    gbc.gridx = 0;
    gbc.gridy = 1;
    centerPanel.add(clubNameLabel, gbc);
    gbc.gridx = 1;
    centerPanel.add(clubNameField, gbc);
    gbc.gridx = 2;
    centerPanel.add(randomClubNameButton, gbc);

    JLabel stadiumLabel = createStyledLabel("Stadium");
    JTextField stadiumField = createStyledTextField();
    JButton randomStadiumButton = createStyledButton("Random");
    stadiumField.setPreferredSize(new Dimension(300, randomStadiumButton.getPreferredSize().height));
    gbc.gridx = 0;
    gbc.gridy = 2;
    centerPanel.add(stadiumLabel, gbc);
    gbc.gridx = 1;
    centerPanel.add(stadiumField, gbc);
    gbc.gridx = 2;
    centerPanel.add(randomStadiumButton, gbc);

    JLabel capacityLabel = createStyledLabel("Stadium Capacity");
    JTextField capacityField = createStyledTextField();
    JButton randomCapacityButton = createStyledButton("Random");
    capacityField.setPreferredSize(new Dimension(300, randomCapacityButton.getPreferredSize().height));
    gbc.gridx = 0;
    gbc.gridy = 3;
    centerPanel.add(capacityLabel, gbc);
    gbc.gridx = 1;
    centerPanel.add(capacityField, gbc);
    gbc.gridx = 2;
    centerPanel.add(randomCapacityButton, gbc);

    // Coach Details Section
    JLabel addCoachLabel = createStyledLabel("Coach Details", 24, true, true);
    addCoachLabel.setHorizontalAlignment(SwingConstants.CENTER);
    gbc.gridwidth = 3;
    gbc.gridx = 0;
    gbc.gridy = 4;
    centerPanel.add(addCoachLabel, gbc);

    JLabel coachNameLabel = createStyledLabel("Name");
    JTextField coachNameField = createStyledTextField();
    coachNameField.setPreferredSize(new Dimension(300, randomClubNameButton.getPreferredSize().height));
    JButton randomCoachNameButton = createStyledButton("Random");
    gbc.gridwidth = 1;
    gbc.gridx = 0;
    gbc.gridy = 5;
    centerPanel.add(coachNameLabel, gbc);
    gbc.gridx = 1;
    centerPanel.add(coachNameField, gbc);
    gbc.gridx = 2;
    centerPanel.add(randomCoachNameButton, gbc);

    JLabel coachAgeLabel = createStyledLabel("Age");
    JTextField coachAgeField = createStyledTextField();
    coachAgeField.setPreferredSize(new Dimension(300, randomClubNameButton.getPreferredSize().height));
    JButton randomCoachAgeButton = createStyledButton("Random");
    gbc.gridx = 0;
    gbc.gridy = 6;
    centerPanel.add(coachAgeLabel, gbc);
    gbc.gridx = 1;
    centerPanel.add(coachAgeField, gbc);
    gbc.gridx = 2;
    centerPanel.add(randomCoachAgeButton, gbc);

    JLabel coachNationalityLabel = createStyledLabel("Nationality");
    JTextField coachNationalityField = createStyledTextField();
    coachNationalityField.setPreferredSize(new Dimension(300, randomClubNameButton.getPreferredSize().height));
    JButton randomCoachNationalityButton = createStyledButton("Random");
    gbc.gridx = 0;
    gbc.gridy = 7;
    centerPanel.add(coachNationalityLabel, gbc);
    gbc.gridx = 1;
    centerPanel.add(coachNationalityField, gbc);
    gbc.gridx = 2;
    centerPanel.add(randomCoachNationalityButton, gbc);

    JLabel coachExpLabel = createStyledLabel("Experience (Years)");
    JTextField coachExpField = createStyledTextField();
    coachExpField.setPreferredSize(new Dimension(300, randomClubNameButton.getPreferredSize().height));
    JButton randomCoachExpButton = createStyledButton("Random");
    gbc.gridx = 0;
    gbc.gridy = 8;
    centerPanel.add(coachExpLabel, gbc);
    gbc.gridx = 1;
    centerPanel.add(coachExpField, gbc);
    gbc.gridx = 2;
    centerPanel.add(randomCoachExpButton, gbc);

    randomClubNameButton.addActionListener(e -> clubNameField.setText(generateRandomClubName()));
    randomStadiumButton.addActionListener(e -> stadiumField.setText(generateRandomStadiumName()));
    randomCapacityButton
        .addActionListener(e -> capacityField.setText(String.valueOf(new Random().nextInt(30000) + 10000)));
    randomCoachNameButton.addActionListener(e -> coachNameField.setText(generateRandomPersonName()));
    randomCoachAgeButton.addActionListener(e -> coachAgeField.setText(String.valueOf(new Random().nextInt(50) + 30)));
    randomCoachNationalityButton.addActionListener(e -> coachNationalityField.setText(generateRandomNationality()));
    randomCoachExpButton.addActionListener(e -> coachExpField.setText(String.valueOf(new Random().nextInt(30) + 1)));

    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(BLACK);
    buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

    JButton createButton = createStyledButton("Create Club");
    JButton backButton = createStyledButton("Back");
    buttonPanel.add(createButton);
    buttonPanel.add(backButton);
    gbc.gridwidth = 3;
    gbc.gridx = 0;
    gbc.gridy = 9;
    centerPanel.add(buttonPanel, gbc);

    createButton.addActionListener(e -> {
      String clubName = clubNameField.getText().trim();
      String stadium = stadiumField.getText().trim();
      String capacityText = capacityField.getText().trim();
      String coachName = coachNameField.getText().trim();
      String coachAgeText = coachAgeField.getText().trim();
      String coachNationality = coachNationalityField.getText().trim();
      String coachExpText = coachExpField.getText().trim();

      if (clubName.isEmpty() || stadium.isEmpty() || capacityText.isEmpty()) {
        showErrorDialog(frame, "Please fill in all fields!");
        return;
      }

      try {
        int capacity = Integer.parseInt(capacityText);
        int coachAge = Integer.parseInt(coachAgeText);
        int coachExp = Integer.parseInt(coachExpText);

        Club newClub = new Club(clubName, stadium, capacity);
        Coach newCoach = new Coach(coachName, coachAge, coachNationality, coachExp);
        newClub.setCoach(newCoach);
        league.setClub(newClub);

        showInfoDialog(frame, "Club successfully created!");

        clubNameField.setText("");
        stadiumField.setText("");
        capacityField.setText("");
        coachNameField.setText("");
        coachAgeField.setText("");
        coachNationalityField.setText("");
        coachExpField.setText("");
      } catch (NumberFormatException ex) {
        showErrorDialog(frame, "Please enter valid numbers for capacity, age, and experience!");
      }
    });

    backButton.addActionListener(e -> viewLeagueDetails(frame, league));

    JScrollPane scrollPane = new JScrollPane(centerPanel);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

    frame.add(scrollPane, BorderLayout.CENTER);
    frame.revalidate();
    frame.repaint();
  }

  // Panels
  private JPanel setupLeagueTablePanel(JFrame frame, League league) {
    JPanel leagueTablePanel = new JPanel();
    leagueTablePanel.setLayout(new GridBagLayout());
    leagueTablePanel.setBackground(BACKGROUND_COLOR);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 12, 10, 12);

    JLabel tableTitleLabel = createStyledLabel("League Table", 20, true, false);
    gbc.gridwidth = 11;
    gbc.gridx = 0;
    gbc.anchor = GridBagConstraints.CENTER;
    leagueTablePanel.add(tableTitleLabel, gbc);

    String[] tableHeaders = { "#", "Club", "P", "W", "D", "L", "F", "A", "GD", "PTS", "" };
    for (int i = 0; i < tableHeaders.length; i++) {
      JLabel tableHeaderLabel = createStyledLabel(tableHeaders[i], 16, true, false);
      tableHeaderLabel.setBorder(null);
      gbc.gridx = i;
      gbc.gridy = 1;
      gbc.gridwidth = 1;
      gbc.anchor = GridBagConstraints.WEST;
      leagueTablePanel.add(tableHeaderLabel, gbc);
    }

    int rank = 1;
    for (Club club : league.getClubArray()) {
      gbc.gridy++;
      JLabel rankLabel = createStyledLabel(String.valueOf(rank++));
      JLabel clubLabel = createStyledLabel(club.getName());
      clubLabel.setAlignmentX(SwingConstants.LEFT);
      JLabel playLabel = createStyledLabel(String.valueOf(club.getPlay()));
      JLabel winLabel = createStyledLabel(String.valueOf(club.getWin()));
      JLabel drawLabel = createStyledLabel(String.valueOf(club.getDraw()));
      JLabel loseLabel = createStyledLabel(String.valueOf(club.getLose()));
      JLabel goalAttackLabel = createStyledLabel(String.valueOf(club.getGoalAttack()));
      JLabel goalDefenseLabel = createStyledLabel(String.valueOf(club.getGoalDefense()));
      JLabel goalDifferenceLabel = createStyledLabel(String.valueOf(club.getGoalAttack() - club.getGoalDefense()));
      JLabel pointLabel = createStyledLabel(String.valueOf(club.getPoint()));

      gbc.gridx = 0;
      leagueTablePanel.add(rankLabel, gbc);
      gbc.gridx = 1;
      gbc.anchor = GridBagConstraints.WEST;
      gbc.fill = GridBagConstraints.NONE;
      leagueTablePanel.add(clubLabel, gbc);
      gbc.gridx = 2;
      leagueTablePanel.add(playLabel, gbc);
      gbc.gridx = 3;
      leagueTablePanel.add(winLabel, gbc);
      gbc.gridx = 4;
      leagueTablePanel.add(drawLabel, gbc);
      gbc.gridx = 5;
      leagueTablePanel.add(loseLabel, gbc);
      gbc.gridx = 6;
      leagueTablePanel.add(goalAttackLabel, gbc);
      gbc.gridx = 7;
      leagueTablePanel.add(goalDefenseLabel, gbc);
      gbc.gridx = 8;
      leagueTablePanel.add(goalDifferenceLabel, gbc);
      gbc.gridx = 9;
      leagueTablePanel.add(pointLabel, gbc);

      JButton viewMoreButton = createStyledButton("View More");
      viewMoreButton.addActionListener(e -> viewClubDetails(frame, league, club));
      gbc.gridx = 10;
      leagueTablePanel.add(viewMoreButton, gbc);
    }
    return leagueTablePanel;
  }

  private JPanel setupAddClubPanel(JFrame frame, League league) {
    JPanel addClubPanel = new JPanel();
    addClubPanel.setLayout(new BoxLayout(addClubPanel, BoxLayout.Y_AXIS));
    addClubPanel.setBackground(BACKGROUND_COLOR);
    addClubPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));

    JLabel addClubLabel = createStyledLabel("Add a New Club", 20, true, false);
    addClubLabel.setBorder(null);
    JLabel addClubSubLabel = createStyledLabel(
        "Add a new club to the league. Clubs can be added only before the season kicks off.", 16, false, false);
    addClubSubLabel.setBorder(null);

    JButton addClubButton = createStyledButton("Add Club");
    addClubButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    addClubButton.addActionListener(e -> addNewClub(frame, league));

    addClubPanel.add(addClubLabel);
    addClubPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    addClubPanel.add(addClubSubLabel);
    addClubPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    addClubPanel.add(addClubButton);
    return addClubPanel;
  }

  private JPanel setupBackButtonPanel(JFrame frame, ActionListener actionListener) {
    JPanel backButtonPanel = new JPanel();
    backButtonPanel.setBackground(BACKGROUND_COLOR);
    JButton backButton = createStyledButton("Back");
    backButton.addActionListener(e -> actionListener.actionPerformed(e));
    backButtonPanel.add(backButton);
    backButtonPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
    return backButtonPanel;
  }

  private JPanel setupMatchPanel(League league, int currWeek, boolean isFixtures) {
    JPanel matchPanel = new JPanel();
    matchPanel.setLayout(new BoxLayout(matchPanel, BoxLayout.Y_AXIS));
    matchPanel.setBackground(BACKGROUND_COLOR);
    matchPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JLabel weekLabel;
    if (isFixtures) {
      weekLabel = createStyledLabel("Week " + currWeek + " Fixtures", 20, true, false);
    } else {
      weekLabel = createStyledLabel("Week " + currWeek + " Results", 20, true, false);
    }
    matchPanel.add(weekLabel);

    HashMap<Club, Boolean> picked = new HashMap<>();
    for (Club club : league.getClubArray()) {
      picked.put(club, false);
    }

    for (Club club : league.getClubArray()) {
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

      if (!picked.get(home) && !picked.get(away)) {
        picked.put(home, true);
        picked.put(away, true);

        String matchDetails;
        if (isFixtures) {
          matchDetails = home.getName() + " vs " + away.getName();
        } else {
          int homeGoals = home.getScore(currWeek);
          int awayGoals = away.getScore(currWeek);
          matchDetails = home.getName() + " " + homeGoals + " - " + awayGoals + " " + away.getName();
        }
        JLabel fixtureLabel = createStyledLabel(matchDetails, 16, false, false);
        fixtureLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        matchPanel.add(fixtureLabel);
      }
    }

    return matchPanel;
  }

  private JPanel setupStartSeasonPanel(JFrame frame, League league, boolean canStartSeason) {
    JPanel startSeasonPanel = new JPanel();
    startSeasonPanel.setLayout(new BoxLayout(startSeasonPanel, BoxLayout.Y_AXIS));
    startSeasonPanel.setBackground(BACKGROUND_COLOR);
    startSeasonPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));

    JLabel seasonLabel = createStyledLabel("Start Season", 20, true, false);
    seasonLabel.setBorder(null);
    String message = canStartSeason ? "Kick off the season and schedule the upcoming matches for all teams."
        : "Cannot start season. Make sure there are at least 2 clubs and 11 players in each club.";
    JLabel seasonSubLabel = createStyledLabel(message, 16, false, false);
    seasonSubLabel.setBorder(null);

    JButton seasonButton = createStyledButton("Start");
    seasonButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    seasonButton.setEnabled(canStartSeason);
    seasonButton.addActionListener(e -> {
      league.scheduleMatches();
      showInfoDialog(frame, "Season started successfully!");
      viewLeagueDetails(frame, league);
    });

    startSeasonPanel.add(seasonLabel);
    startSeasonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    startSeasonPanel.add(seasonSubLabel);
    startSeasonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    startSeasonPanel.add(seasonButton);
    return startSeasonPanel;
  }

  private JPanel setupSimulationPanel(JFrame frame, League league, boolean isAll) {
    JPanel simulatePanel = new JPanel();
    simulatePanel.setLayout(new BoxLayout(simulatePanel, BoxLayout.Y_AXIS));
    simulatePanel.setBackground(BACKGROUND_COLOR);
    simulatePanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));

    String simulateTitle = isAll ? "Simulate Season" : "Simulate Week";
    String simulateSubtitle = isAll ? "Simulate all matches for the current season."
        : "Simulate all matches for the current week.";

    JLabel simulateLabel = createStyledLabel(simulateTitle, 20, true, false);
    simulateLabel.setBorder(null);
    JLabel simulateSubLabel = createStyledLabel(simulateSubtitle, 16, false, false);
    simulateSubLabel.setBorder(null);

    String buttonText = isAll ? "Simulate Season" : "Simulate Week";

    JButton simulateButton = createStyledButton(buttonText);
    simulateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    simulateButton.addActionListener(e -> {
      if (isAll)
        league.simulateAllWeeks();
      else {
        league.simulateOneWeek();
      }

      String message = isAll ? "Season simulated successfully!" : "Week simulated successfully!";

      showInfoDialog(frame, message);
      viewLeagueDetails(frame, league);
    });

    simulatePanel.add(simulateLabel);
    simulatePanel.add(Box.createRigidArea(new Dimension(0, 10)));
    simulatePanel.add(simulateSubLabel);
    simulatePanel.add(Box.createRigidArea(new Dimension(0, 20)));
    simulatePanel.add(simulateButton);
    return simulatePanel;
  }

  private JPanel setupClubDetailsPanel(Club club) {
    JPanel clubDetailsPanel = new JPanel(new GridBagLayout());
    clubDetailsPanel.setBackground(BACKGROUND_COLOR);
    clubDetailsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(10, 20, 10, 20);

    JLabel stadiumLabel = createStyledLabel("Stadium: " + club.getStadion(), 16, true, false);
    stadiumLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
    JLabel capacityLabel = createStyledLabel("Stadium Capacity: " + club.getCapacity(), 16, true, false);
    capacityLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
    JLabel coachNameLabel = createStyledLabel("Coach Name: " + club.getCoach().getName(), 16, true, false);
    coachNameLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
    JLabel coachAgeLabel = createStyledLabel("Coach Age: " + club.getCoach().getAge(), 16, true, false);
    coachAgeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
    JLabel coachNationalityLabel = createStyledLabel("Coach Nationality: " + club.getCoach().getNationality(),
        16, true, false);
    coachNationalityLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
    JLabel coachExpLabel = createStyledLabel("Coach Experience: " + club.getCoach().getExp(), 16, true, false);
    coachExpLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

    gbc.gridwidth = 1;
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.NONE;
    clubDetailsPanel.add(stadiumLabel, gbc);
    gbc.gridx = 1;
    clubDetailsPanel.add(capacityLabel, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    clubDetailsPanel.add(coachNameLabel, gbc);
    gbc.gridx = 1;
    clubDetailsPanel.add(coachAgeLabel, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    clubDetailsPanel.add(coachNationalityLabel, gbc);
    gbc.gridx = 1;
    clubDetailsPanel.add(coachExpLabel, gbc);
    return clubDetailsPanel;
  }

  private JPanel setupPlayerListPanel(JFrame frame, League league, Club club) {
    JPanel playerListPanel = new JPanel();
    playerListPanel.setLayout(new GridBagLayout());
    playerListPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
    playerListPanel.setBackground(BACKGROUND_COLOR);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 8, 5, 8);

    JLabel tableTitleLabel = createStyledLabel("Squad List", 20, true, true);
    gbc.gridwidth = 12;
    gbc.gridx = 0;
    gbc.anchor = GridBagConstraints.CENTER;
    playerListPanel.add(tableTitleLabel, gbc);

    JButton addPlayerButton = createStyledButton("Add Player");
    addPlayerButton.addActionListener(e -> addNewPlayer(frame, league, club));

    if (club.getPlayers() == null || club.getPlayers().size() == 0) {
      JLabel noPlayerLabel = createStyledLabel("No players in this club", 16, true, true);
      noPlayerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
      gbc.gridy = 1;
      gbc.gridx = 0;
      gbc.gridwidth = 12;
      gbc.anchor = GridBagConstraints.CENTER;
      playerListPanel.add(noPlayerLabel, gbc);
      gbc.gridy++;
      playerListPanel.add(addPlayerButton, gbc);
      return playerListPanel;
    }

    String[] tableHeaders = { "#", "Name", "POS", "AGE", "NAT", "PAC", "SHO", "PAS", "DRI", "DEF", "PHY", "OVR" };
    for (int i = 0; i < tableHeaders.length; i++) {
      JLabel tableHeaderLabel = createStyledLabel(tableHeaders[i], 16, true, false);
      tableHeaderLabel.setBorder(null);
      gbc.gridx = i;
      gbc.gridy = 1;
      gbc.gridwidth = 1;
      if (tableHeaders[i] == "Name") {
        gbc.anchor = GridBagConstraints.WEST;
      } else {
        gbc.anchor = GridBagConstraints.CENTER;
      }
      playerListPanel.add(tableHeaderLabel, gbc);
    }

    int cnt = 1;
    for (Player player : club.getPlayers()) {
      gbc.gridy++;

      JLabel rankLabel = createStyledLabel(String.valueOf(cnt++), 15, true, false);
      JLabel nameLabel = createStyledLabel(trimName(player.getName()), 15, true, false);
      nameLabel.setAlignmentX(SwingConstants.LEFT);
      JLabel nationalityLabel = createStyledLabel(trimNationality(player.getNationality()), 15, true, false);
      JLabel positionLabel = createStyledLabel(player.getPosition(), 15, true, false);
      JLabel ageLabel = createStyledLabel(String.valueOf(player.getAge()), 15, true, false);
      JLabel paceLabel = createStyledLabel(String.valueOf(player.getPace()), 15, true, false);
      JLabel shootingLabel = createStyledLabel(String.valueOf(player.getShooting()), 15, true, false);
      JLabel passingLabel = createStyledLabel(String.valueOf(player.getPassing()), 15, true, false);
      JLabel dribblingLabel = createStyledLabel(String.valueOf(player.getDribbling()), 15, true, false);
      JLabel defendingLabel = createStyledLabel(String.valueOf(player.getDefending()), 15, true, false);
      JLabel physicalLabel = createStyledLabel(String.valueOf(player.getPhysical()), 15, true, false);
      JLabel ratingLabel = createStyledLabel(String.valueOf(player.getRating()), 15, true, false);

      gbc.gridx = 0;
      gbc.anchor = GridBagConstraints.CENTER;
      gbc.fill = GridBagConstraints.NONE;
      playerListPanel.add(rankLabel, gbc);
      gbc.gridx = 1;
      gbc.anchor = GridBagConstraints.WEST;
      playerListPanel.add(nameLabel, gbc);
      gbc.gridx = 2;
      gbc.anchor = GridBagConstraints.CENTER;
      playerListPanel.add(positionLabel, gbc);
      gbc.gridx = 3;
      playerListPanel.add(ageLabel, gbc);
      gbc.gridx = 4;
      playerListPanel.add(nationalityLabel, gbc);
      gbc.gridx = 5;
      playerListPanel.add(paceLabel, gbc);
      gbc.gridx = 6;
      playerListPanel.add(shootingLabel, gbc);
      gbc.gridx = 7;
      playerListPanel.add(passingLabel, gbc);
      gbc.gridx = 8;
      playerListPanel.add(dribblingLabel, gbc);
      gbc.gridx = 9;
      playerListPanel.add(defendingLabel, gbc);
      gbc.gridx = 10;
      playerListPanel.add(physicalLabel, gbc);
      gbc.gridx = 11;
      playerListPanel.add(ratingLabel, gbc);
    }

    gbc.gridy++;
    gbc.gridx = 0;
    gbc.gridwidth = 12;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.CENTER;
    playerListPanel.add(addPlayerButton, gbc);

    return playerListPanel;
  }

  // Components
  private JLabel createStyledLabel(String text, int fontSize, boolean isBold, boolean isTitle) {
    JLabel styledLabel = new JLabel(text);
    if (isBold) {
      styledLabel.setFont(new Font(FONT_NAME, Font.BOLD, fontSize));
    } else {
      styledLabel.setFont(new Font(FONT_NAME, Font.PLAIN, fontSize));
    }
    styledLabel.setForeground(Color.WHITE);
    styledLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    if (isTitle) {
      styledLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));
    } else {
      styledLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
    }
    return styledLabel;
  }

  private JTextField createStyledTextField() {
    JTextField textField = new JTextField(25);
    textField.setFont(new Font(FONT_NAME, Font.PLAIN, 14));
    return textField;
  }

  private JLabel createStyledLabel(String text) {
    JLabel label = new JLabel(text, SwingConstants.CENTER);
    label.setFont(new Font(FONT_NAME, Font.BOLD, 16));
    label.setForeground(Color.WHITE);
    return label;
  }

  private JButton createStyledButton(String text) {
    JButton button = new JButton(text);
    button.setFont(new Font("", Font.BOLD, 16));
    button.setForeground(Color.WHITE);
    button.setBackground(new Color(60, 60, 60));
    button.setFocusPainted(false);
    button.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(80, 80, 80), 2),
        BorderFactory.createEmptyBorder(10, 20, 10, 20)));
    button.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(java.awt.event.MouseEvent evt) {
        button.setBackground(new Color(100, 100, 100));
      }

      public void mouseExited(java.awt.event.MouseEvent evt) {
        button.setBackground(new Color(60, 60, 60));
      }
    });
    return button;
  }

  private void showErrorDialog(JFrame frame, String message) {
    JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  private void showInfoDialog(JFrame frame, String message) {
    JOptionPane.showMessageDialog(frame, message, "Info", JOptionPane.INFORMATION_MESSAGE);
  }

  private static League findLeagueByName(String leagueName) {
    return leagues.stream()
        .filter(league -> league.getName().equalsIgnoreCase(leagueName))
        .findFirst()
        .orElse(null);
  }

  private String trimName(String name) {
    String[] parts = name.split(" ");

    if (name.length() > 15 && parts.length > 1) {
      StringBuilder trimmedName = new StringBuilder();
      trimmedName.append(parts[0].charAt(0)).append(". ");
      trimmedName.append(parts[parts.length - 1]);
      return trimmedName.toString();
    }

    return name;
  }

  private String trimNationality(String nationality) {
    return nationality.substring(0, 3).toUpperCase();
  }

  private String generateRandomLeagueName() {
    ArrayList<String> firstLeagueName = new ArrayList<>(
        Arrays.asList(
            "Premiera", "Super", "Elite", "Nacional", "Internacional",
            "Estrella", "Grand", "Primeira", "Top", "Major", "Primera", "Segunda",
            "Liga", "Division", "Oro", "Alpha", "Challenger", "Royal",
            "Continental", "Atletica", "Futbolera", "Sportiva",
            "Regal", "Central", "Eastern", "Western", "Nordic"));

    ArrayList<String> secondLeagueName = new ArrayList<>(
        Arrays.asList(
            "Liga", "League", "Serie", "Division", "Eredivisie", "Bundesliga",
            "Ligue", "Torneo", "LigaPro", "Ekstraklasa", "Allsvenskan",
            "Veikkausliiga", "Eliteserien", "Jupiler", "A-League", "Superliga",
            "Primera", "Segunda", "Eerste", "Futbol", "Premyer",
            "Vysshaya", "Campionato", "ProLeague", "LigaPro", "Meistriliiga",
            "Nacional", "Ascenso", "Championship"));

    Random random = new Random();
    String firstPart = firstLeagueName.get(random.nextInt(firstLeagueName.size()));
    String secondPart = secondLeagueName.get(random.nextInt(secondLeagueName.size()));
    return firstPart + " " + secondPart;
  }

  private String generateRandomClubName() {
    ArrayList<String> clubNames = new ArrayList<>(Arrays.asList(
        "United", "City", "Rovers", "Wanderers", "Athletic", "FC", "Real", "Inter", "Chelsea",
        "Boca", "Juventus", "Barcelona", "Liverpool", "Arsenal", "Paris", "Bayern", "Dynamo",
        "Olympic", "Corinthians", "Milan", "Sevilla", "Flamengo", "River", "Vasco", "Santos",
        "Valencia", "Leeds", "Tottenham", "Roma", "Napoli", "Stars", "Lions", "Eagles", "Warriors",
        "Thunder", "Dragons", "Strikers", "Titans", "Kings", "Academy", "Boys"));

    Random random = new Random();

    boolean isOneWord = random.nextBoolean();

    if (isOneWord) {
      return clubNames.get(random.nextInt(clubNames.size()));
    } else {
      String firstPart = clubNames.get(random.nextInt(clubNames.size()));
      String secondPart = clubNames.get(random.nextInt(clubNames.size()));
      return firstPart + " " + secondPart;
    }
  }

  private String generateRandomStadiumName() {
    ArrayList<String> stadiumAdjectives = new ArrayList<>(Arrays.asList(
        "Grand", "Legendary", "Majestic", "Olympic", "Royal", "Epic", "Modern", "Historic",
        "Vibrant", "Prime", "Glorious", "Ultimate", "Famous", "World", "Premier", "Central",
        "National", "Victory", "Champion", "Elite", "Top", "Dynamic", "Super", "All-Star"));

    ArrayList<String> stadiumTypes = new ArrayList<>(Arrays.asList(
        "Arena", "Stadium", "Park", "Field", "Ground", "Dome", "Complex", "Coliseum", "Court",
        "Center", "Pavilion", "Terrace", "Oval", "Hall", "Ring", "Pitch"));

    ArrayList<String> stadiumLocations = new ArrayList<>(Arrays.asList(
        "City", "Bay", "Mountain", "Park", "Riverside", "Valley", "Coast", "Island", "North",
        "South", "East", "West", "Central", "Harbor", "Forest", "Sunset", "Vista"));

    Random random = new Random();
    boolean isOneWord = random.nextBoolean();

    if (isOneWord) {
      String adjective = stadiumAdjectives.get(random.nextInt(stadiumAdjectives.size()));
      String type = stadiumTypes.get(random.nextInt(stadiumTypes.size()));
      return adjective + " " + type;
    } else {
      String adjective = stadiumAdjectives.get(random.nextInt(stadiumAdjectives.size()));
      String location = stadiumLocations.get(random.nextInt(stadiumLocations.size()));
      String type = stadiumTypes.get(random.nextInt(stadiumTypes.size()));
      return adjective + " " + location + " " + type;
    }
  }

  private String generateRandomPersonName() {
    ArrayList<String> firstNames = new ArrayList<>(Arrays.asList(
        "James", "John", "Robert", "Michael", "David", "Lionel", "Jack", "Harry", "George",
        "William", "Richard", "Joseph", "Charles", "Thomas", "Daniel", "Matthew", "Anthony",
        "Christopher", "Mark", "Paul", "Steven", "Kenneth", "Joshua", "Kevin"));

    ArrayList<String> lastNames = new ArrayList<>(Arrays.asList(
        "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez",
        "Martinez", "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson", "Thomas", "Taylor",
        "Moore", "Jackson", "Martin", "Lee", "Perez", "White", "Harris", "Sanchez"));

    Random random = new Random();

    String firstName = firstNames.get(random.nextInt(firstNames.size()));
    String lastName = lastNames.get(random.nextInt(lastNames.size()));

    return firstName + " " + lastName;
  }

  private String generateRandomNationality() {
    String[] nationalities = { "USA", "Spain", "Italy", "Brazil", "France", "Germany", "Argentina", "England" };
    return nationalities[new Random().nextInt(nationalities.length)];
  }

  private void initializeLeagues() {
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

    laLiga.setClub(barcelona);
    laLiga.setClub(realMadrid);
    laLiga.setClub(atleticoMadrid);
    laLiga.setClub(sevilla);

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
    barcelona.addPlayers(new Player("Robert Lewandowski", 34, "Poland", 82, 92, 75, 80, 85, 85, "FW"));
    barcelona.addPlayers(new Player("Pedri", 20, "Spain", 85, 80, 88, 85, 70, 78, "MID"));
    barcelona.addPlayers(new Player("Frenkie de Jong", 25, "Netherlands", 80, 85, 90, 85, 78, 82, "MID"));
    barcelona.addPlayers(new Player("Gerard Pique", 36, "Spain", 70, 60, 80, 75, 90, 85, "DF"));
    barcelona.addPlayers(new Player("Jules Kounde", 25, "France", 80, 70, 82, 80, 85, 80, "DF"));
    barcelona.addPlayers(new Player("Marc-Andre ter Stegen", 31, "Germany", 50, 88, 80, 75, 87, 90, "GK"));
    barcelona.addPlayers(new Player("Ansu Fati", 21, "Spain", 90, 82, 78, 85, 70, 78, "FW"));
    barcelona.addPlayers(new Player("Sergi Roberto", 32, "Spain", 80, 70, 85, 80, 78, 80, "MID"));
    barcelona.addPlayers(new Player("Raphinha", 27, "Brazil", 89, 83, 85, 80, 76, 82, "FW"));
    barcelona.addPlayers(new Player("Sergio Busquets", 35, "Spain", 60, 60, 90, 70, 85, 80, "MID"));
    barcelona.addPlayers(new Player("Gavi", 19, "Spain", 85, 80, 85, 88, 78, 80, "MID"));

    // Add Players to Real Madrid
    realMadrid.addPlayers(new Player("Karim Benzema", 35, "France", 75, 90, 85, 80, 82, 88, "FW"));
    realMadrid.addPlayers(new Player("Luka Modric", 37, "Croatia", 80, 88, 90, 85, 70, 80, "MID"));
    realMadrid.addPlayers(new Player("Vinicius Jr.", 22, "Brazil", 90, 92, 82, 85, 75, 83, "FW"));
    realMadrid.addPlayers(new Player("Toni Kroos", 34, "Germany", 70, 80, 92, 85, 75, 80, "MID"));
    realMadrid.addPlayers(new Player("David Alaba", 31, "Austria", 75, 70, 85, 80, 88, 84, "DF"));
    realMadrid.addPlayers(new Player("Thibaut Courtois", 31, "Belgium", 50, 88, 80, 75, 87, 90, "GK"));
    realMadrid.addPlayers(new Player("Ferland Mendy", 29, "France", 82, 70, 75, 85, 90, 84, "DF"));
    realMadrid.addPlayers(new Player("Antonio Rudiger", 30, "Germany", 75, 70, 80, 82, 90, 85, "DF"));
    realMadrid.addPlayers(new Player("Eduardo Camavinga", 21, "France", 85, 70, 82, 85, 78, 80, "MID"));
    realMadrid.addPlayers(new Player("Rodrygo", 23, "Brazil", 87, 80, 85, 90, 78, 85, "FW"));
    realMadrid.addPlayers(new Player("Casemiro", 32, "Brazil", 70, 75, 85, 80, 90, 85, "MID"));

    // Add Players to Manchester United
    manchesterUnited.addPlayers(new Player("Marcus Rashford", 25, "England", 85, 80, 75, 90, 88, 85, "FW"));
    manchesterUnited.addPlayers(new Player("Bruno Fernandes", 28, "Portugal", 80, 87, 85, 80, 75, 80, "MID"));
    manchesterUnited.addPlayers(new Player("Casemiro", 31, "Brazil", 78, 80, 85, 83, 88, 84, "MID"));
    manchesterUnited.addPlayers(new Player("Harry Maguire", 31, "England", 70, 65, 80, 75, 90, 85, "DF"));
    manchesterUnited.addPlayers(new Player("Cristiano Ronaldo", 39, "Portugal", 85, 92, 75, 90, 75, 80, "FW"));
    manchesterUnited.addPlayers(new Player("David De Gea", 33, "Spain", 50, 85, 75, 70, 88, 90, "GK"));
    manchesterUnited.addPlayers(new Player("Jadon Sancho", 24, "England", 85, 80, 85, 90, 75, 80, "FW"));
    manchesterUnited.addPlayers(new Player("Raphaël Varane", 30, "France", 70, 65, 85, 80, 90, 85, "DF"));
    manchesterUnited.addPlayers(new Player("Luke Shaw", 29, "England", 75, 70, 82, 75, 88, 85, "DF"));
    manchesterUnited.addPlayers(new Player("Antony", 23, "Brazil", 87, 80, 80, 85, 75, 85, "FW"));
    manchesterUnited
        .addPlayers(new Player("Donny van de Beek", 26, "Netherlands", 75, 70, 85, 82, 80, 80, "MID"));

    // Add Players to Sevilla
    sevilla.addPlayers(new Player("Youssef En-Nesyri", 26, "Morocco", 84, 80, 75, 80, 85, 84, "FW"));
    sevilla.addPlayers(new Player("Ivan Rakitic", 35, "Croatia", 70, 85, 90, 80, 75, 82, "MID"));
    sevilla.addPlayers(new Player("Jesús Navas", 37, "Spain", 85, 80, 78, 80, 80, 82, "DF"));
    sevilla.addPlayers(new Player("Bono", 33, "Morocco", 50, 87, 75, 70, 88, 90, "GK"));
    sevilla.addPlayers(new Player("Jules Kounde", 24, "France", 82, 80, 75, 82, 85, 85, "DF"));
    sevilla.addPlayers(new Player("Lucas Ocampos", 29, "Argentina", 84, 82, 78, 85, 75, 80, "FW"));
    sevilla.addPlayers(new Player("Fernando", 36, "Brazil", 75, 80, 85, 80, 75, 82, "MID"));
    sevilla.addPlayers(new Player("Papu Gomez", 35, "Argentina", 80, 83, 85, 75, 75, 78, "MID"));
    sevilla.addPlayers(new Player("Joan JordAn", 29, "Spain", 78, 80, 82, 75, 85, 78, "MID"));
    sevilla.addPlayers(new Player("Erik Lamela", 31, "Argentina", 85, 78, 75, 82, 80, 85, "FW"));
    sevilla.addPlayers(new Player("Karim Rekik", 28, "Netherlands", 75, 80, 85, 80, 85, 80, "DF"));

    // Add Players to Atletico Madrid
    atleticoMadrid.addPlayers(new Player("Antoine Griezmann", 31, "France", 85, 88, 75, 85, 84, 83, "FW"));
    atleticoMadrid.addPlayers(new Player("Joao Felix", 22, "Portugal", 82, 80, 75, 85, 80, 80, "FW"));
    atleticoMadrid.addPlayers(new Player("Jan Oblak", 30, "Slovenia", 50, 90, 85, 75, 87, 90, "GK"));
    atleticoMadrid.addPlayers(new Player("Jose Gimenez", 28, "Uruguay", 75, 80, 75, 85, 90, 85, "DF"));
    atleticoMadrid.addPlayers(new Player("Marcos Llorente", 28, "Spain", 80, 82, 75, 85, 85, 80, "MID"));
    atleticoMadrid.addPlayers(new Player("Stefan Savic", 32, "Montenegro", 70, 75, 75, 80, 90, 85, "DF"));
    atleticoMadrid.addPlayers(new Player("Koke", 30, "Spain", 70, 80, 75, 88, 82, 84, "MID"));
    atleticoMadrid.addPlayers(new Player("Yannick Carrasco", 29, "Belgium", 85, 85, 75, 85, 75, 80, "FW"));
    atleticoMadrid.addPlayers(new Player("Angel Correa", 28, "Argentina", 80, 85, 78, 75, 80, 78, "FW"));
    atleticoMadrid.addPlayers(new Player("Rodrigo De Paul", 29, "Argentina", 75, 80, 78, 80, 85, 82, "MID"));
    atleticoMadrid.addPlayers(new Player("Reinildo Mandava", 29, "Mozambique", 70, 75, 75, 85, 88, 83, "DF"));

    // Add Players to Chelsea
    chelsea.addPlayers(new Player("N'Golo Kante", 33, "France", 75, 70, 88, 80, 85, 90, "MID"));
    chelsea.addPlayers(new Player("Raheem Sterling", 29, "England", 85, 80, 85, 88, 75, 80, "FW"));
    chelsea.addPlayers(new Player("Mason Mount", 25, "England", 80, 75, 85, 78, 70, 82, "MID"));
    chelsea.addPlayers(new Player("Thiago Silva", 40, "Brazil", 70, 75, 80, 78, 85, 90, "DF"));
    chelsea.addPlayers(new Player("Ben Chilwell", 27, "England", 80, 75, 80, 85, 80, 85, "DF"));
    chelsea.addPlayers(new Player("Kepa Arrizabalaga", 29, "Spain", 50, 85, 75, 70, 88, 90, "GK"));
    chelsea.addPlayers(new Player("Pierre-Emerick Aubameyang", 34, "Gabon", 90, 87, 80, 82, 75, 80, "FW"));
    chelsea.addPlayers(new Player("Christian Pulisic", 25, "USA", 85, 80, 78, 85, 70, 80, "FW"));
    chelsea.addPlayers(new Player("Kai Havertz", 25, "Germany", 80, 85, 85, 80, 75, 80, "FW"));
    chelsea.addPlayers(new Player("Jorginho", 32, "Italy", 70, 75, 85, 80, 78, 80, "MID"));
    chelsea.addPlayers(new Player("Cesar Azpilicueta", 34, "Spain", 75, 70, 80, 78, 85, 80, "DF"));

    // Add Players to Liverpool
    liverpool.addPlayers(new Player("Mohamed Salah", 32, "Egypt", 90, 92, 85, 88, 75, 82, "FW"));
    liverpool.addPlayers(new Player("Virgil van Dijk", 33, "Netherlands", 70, 80, 75, 80, 90, 88, "DF"));
    liverpool.addPlayers(new Player("Alisson Becker", 31, "Brazil", 50, 88, 80, 75, 88, 90, "GK"));
    liverpool.addPlayers(new Player("Sadio Mane", 32, "Senegal", 88, 90, 85, 87, 80, 85, "FW"));
    liverpool.addPlayers(new Player("Fabinho", 30, "Brazil", 75, 75, 80, 80, 85, 85, "MID"));
    liverpool.addPlayers(new Player("Trent Alexander-Arnold", 26, "England", 85, 80, 85, 80, 75, 80, "DF"));
    liverpool.addPlayers(new Player("Jordan Henderson", 34, "England", 75, 75, 80, 82, 85, 80, "MID"));
    liverpool.addPlayers(new Player("Diogo Jota", 27, "Portugal", 85, 80, 78, 85, 75, 80, "FW"));
    liverpool.addPlayers(new Player("Darwin Nunez", 24, "Uruguay", 90, 82, 75, 85, 75, 80, "FW"));
    liverpool.addPlayers(new Player("Luis Diaz", 27, "Colombia", 87, 80, 75, 85, 80, 85, "FW"));
    liverpool.addPlayers(new Player("Ibrahima Konate", 25, "France", 75, 70, 80, 75, 90, 85, "DF"));

    // Add Players to Manchester City
    manchesterCity.addPlayers(new Player("Erling Haaland", 24, "Norway", 85, 92, 80, 75, 85, 90, "FW"));
    manchesterCity.addPlayers(new Player("Kevin De Bruyne", 33, "Belgium", 75, 88, 92, 85, 70, 80, "MID"));
    manchesterCity.addPlayers(new Player("Ruben Dias", 27, "Portugal", 70, 75, 80, 80, 90, 85, "DF"));
    manchesterCity.addPlayers(new Player("Phil Foden", 24, "England", 85, 80, 85, 88, 75, 80, "MID"));
    manchesterCity.addPlayers(new Player("Jack Grealish", 28, "England", 80, 78, 85, 82, 75, 80, "FW"));
    manchesterCity.addPlayers(new Player("Ederson", 30, "Brazil", 50, 85, 75, 70, 88, 90, "GK"));
    manchesterCity.addPlayers(new Player("Bernardo Silva", 29, "Portugal", 80, 85, 90, 85, 70, 82, "MID"));
    manchesterCity.addPlayers(new Player("John Stones", 30, "England", 70, 75, 80, 80, 85, 84, "DF"));
    manchesterCity.addPlayers(new Player("Rodri", 27, "Spain", 70, 75, 85, 80, 85, 85, "MID"));
    manchesterCity.addPlayers(new Player("Kyle Walker", 34, "England", 85, 70, 75, 80, 85, 88, "DF"));
    manchesterCity.addPlayers(new Player("Julian Alvarez", 24, "Argentina", 85, 80, 80, 82, 75, 80, "FW"));

    // Add Players to Arsenal
    arsenal.addPlayers(new Player("Bukayo Saka", 22, "England", 85, 80, 85, 88, 75, 80, "FW"));
    arsenal.addPlayers(new Player("Martin Odegaard", 25, "Norway", 75, 85, 90, 80, 70, 82, "MID"));
    arsenal.addPlayers(new Player("Aaron Ramsdale", 26, "England", 50, 85, 75, 70, 88, 90, "GK"));
    arsenal.addPlayers(new Player("Gabriel Jesus", 27, "Brazil", 85, 82, 75, 85, 75, 80, "FW"));
    arsenal.addPlayers(new Player("William Saliba", 23, "France", 75, 70, 80, 75, 90, 85, "DF"));
    arsenal.addPlayers(new Player("Ben White", 26, "England", 80, 75, 80, 78, 85, 80, "DF"));
    arsenal.addPlayers(new Player("Granit Xhaka", 31, "Switzerland", 70, 75, 85, 80, 80, 85, "MID"));
    arsenal.addPlayers(new Player("Thomas Partey", 30, "Ghana", 75, 80, 78, 80, 88, 85, "MID"));
    arsenal.addPlayers(new Player("Gabriel Martinelli", 23, "Brazil", 87, 82, 75, 85, 75, 80, "FW"));
    arsenal.addPlayers(new Player("Kieran Tierney", 26, "Scotland", 80, 75, 78, 82, 85, 85, "DF"));
    arsenal.addPlayers(new Player("Oleksandr Zinchenko", 27, "Ukraine", 80, 75, 80, 78, 80, 82, "DF"));
  }

  public static void main(String[] args) {
    new SoccerGUI();
  }
}
