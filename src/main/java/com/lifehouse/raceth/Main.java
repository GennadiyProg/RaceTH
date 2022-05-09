package com.lifehouse.raceth;

import com.lifehouse.raceth.rfid.RFID;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import org.hibernate.engine.spi.ExecuteUpdateResultCheckStyle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main extends Application{
/*
⢀⡴⠑⡄⠀⠀⠀⠀⠀⠀⠀⣀⣀⣤⣤⣤⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠸⡇⠀⠿⡀⠀⠀⠀⣀⡴⢿⣿⣿⣿⣿⣿⣿⣿⣷⣦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠑⢄⣠⠾⠁⣀⣄⡈⠙⣿⣿⣿⣿⣿⣿⣿⣿⣆⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⢀⡀⠁⠀⠀⠈⠙⠛⠂⠈⣿⣿⣿⣿⣿⠿⡿⢿⣆⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⢀⡾⣁⣀⠀⠴⠂⠙⣗⡀⠀⢻⣿⣿⠭⢤⣴⣦⣤⣹⠀⠀⠀⢀⢴⣶⣆
⠀⠀⢀⣾⣿⣿⣿⣷⣮⣽⣾⣿⣥⣴⣿⣿⡿⢂⠔⢚⡿⢿⣿⣦⣴⣾⠁⠸⣼⡿
⠀⢀⡞⠁⠙⠻⠿⠟⠉⠀⠛⢹⣿⣿⣿⣿⣿⣌⢤⣼⣿⣾⣿⡟⠉⠀⠀⠀⠀⠀
⠀⣾⣷⣶⠇⠀⠀⣤⣄⣀⡀⠈⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡇⠀⠀⠀⠀⠀⠀
⠀⠉⠈⠉⠀⠀⢦⡈⢻⣿⣿⣿⣶⣶⣶⣶⣤⣽⡹⣿⣿⣿⣿⡇⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠉⠲⣽⡻⢿⣿⣿⣿⣿⣿⣿⣷⣜⣿⣿⣿⡇⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⣷⣶⣮⣭⣽⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⣀⣀⣈⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠇⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠃⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠹⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠟⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠛⠻⠿⠿⠿⠿⠛⠉
 */

    public static void main(String[] args) {
//        Session session = HibernateUtil.getSession();
//
//
//        // Determine all DAO controllers
//        DAO<Competition> competitionDAO = new CompetitionDAO(session);
//        DAO<Run> runDAO = new RunDAO(session);
//        DAO<Chip> chipDAO = new ChipDAO(session);
//        DAO<Participant> participantDAO = new ParticipantDAO(session);
//        DAO<RelayTeam> relayTeamDAO = new RelayTeamDAO(session);
//        DAO<Distance> distanceDAO = new DistanceDAO(session);
//        DAO<CompetitionGroup> competitionGroupDAO = new GroupDAO(session);
//        DAO<Checkpoint> checkpointDAO = new CheckpointDAO(session);
//        DAO<Sportsman> sportsmanDAO = new SportsmanDAO(session);
//
//        // Creating Competition
//        final Competition competition = new Competition();
//        competition.setNameCompetition("First competition");
//        competitionDAO.create(competition);
//
//        // Creating Group
//        final CompetitionGroup competitionGroup = new CompetitionGroup();
//        competitionGroup.setNameGroup("Дети");
//        competitionGroup.setAge(14);
//        competitionGroup.setGender("Ж");
//        competitionGroupDAO.create(competitionGroup);
//
//        // Creating Distance
//        final Distance distance = new Distance();
//        distance.setLocated("Баринова роща");
//        distance.setLength(1000);
//        distanceDAO.create(distance);
//
//        // Creating Run
//        final Run run = new Run();
//        run.setCompetition(competition);
//        run.setDateRun(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
//        run.setDistance(distance);
//        run.setGroup(competitionGroup);
//        runDAO.create(run);
//
//        // Creating Chip
//        final Chip chip = new Chip();
//        chip.setChipNumber("Donda");
//        chipDAO.create(chip);
//
//        // Creating RelayTeam
//        final RelayTeam relayTeam = new RelayTeam();
//        relayTeam.setNameRelayTeam("ВГДе");
//        relayTeamDAO.create(relayTeam);
//
//        final RelayTeam relayTeam1 = new RelayTeam();
//        relayTeam1.setNameRelayTeam("БББ");
//        relayTeamDAO.create(relayTeam1);
//
//
//        // Creating Participant
//        final Participant participant = new Participant();
//        participant.setRun(run);
//        participant.setChip(chip);
//        participant.setRelayTeam(relayTeam);
//        participant.setRelayStage(1);
//        participant.setTag(237);
//        participantDAO.create(participant);
//
//        // Creating Checkpoint
//        final Checkpoint checkpoint1 = new Checkpoint();
//        checkpoint1.setCrossingTime(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
//        checkpoint1.setParticipant(participant);
//        checkpointDAO.create(checkpoint1);
//
//        final Checkpoint checkpoint2 = new Checkpoint();
//        checkpoint2.setCrossingTime(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
//        checkpoint2.setParticipant(participant);
//        checkpointDAO.create(checkpoint2);
//
//        // Creating sportsman
//        final Sportsman sportsman = new Sportsman();
//        sportsman.setGender(Gender.MALE);
//        sportsman.setBirthdate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
//        sportsman.setName("Mole");
//        sportsman.setLastname("Ass");
//        sportsman.setRegion("Vladimir");
//        sportsmanDAO.create(sportsman);
//
//        final Sportsman sportsman1 = new Sportsman();
//        sportsman1.setGender(Gender.MALE);
//        sportsman1.setBirthdate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
//        sportsman1.setName("Den");
//        sportsman1.setLastname("ewfwfg");
//        sportsman1.setRegion("Kam");
//        sportsmanDAO.create(sportsman1);

        launch(args);
        closeSocket();

//        session.close();
//        HibernateUtil.closeSessionFactory();
    }

    private static void closeSocket() {
        try {
            Process process = Runtime.getRuntime().exec("netstat -ano");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while((line = reader.readLine()) != null) {
                if (line.contains(Integer.toString(RFID.SERVICE_PORT))) {
                    break;
                }
            }
            RFID.threadFlag = false;
            if (line != "" && line != null) {
                String[] array = line.split(" ");
                Runtime.getRuntime().exec("taskkill /PID " + array[array.length-1] + " /F" );
            }
            if (RFID.getTagThread != null) {
                RFID.getTagThread.stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainPage.fxml"));

//        Image imageOk = new Image(getClass().getResourceAsStream("123.jpg"));

        stage.setResizable(false); //Отключение изменения размеров окна
        AnchorPane pain = loader.load();
        stage.setScene(new Scene(pain));
        stage.getIcons().add(new Image("/assets/images/icons/more.png"));
        stage.show();
    }
}

