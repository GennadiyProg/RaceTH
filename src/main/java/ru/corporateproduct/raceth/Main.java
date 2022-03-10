package ru.corporateproduct.raceth;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.IOException;
import javafx.scene.text.Text;
import javafx.scene.Group;
import org.hibernate.Session;
import ru.corporateproduct.raceth.dao.*;
import ru.corporateproduct.raceth.model.*;

import java.util.Calendar;


public class Main extends Application{


    public static void main(String[] args) {
        Session session = HibernateUtil.getSession();

        launch(args);

        // Determine all DAO controllers
        DAO<Competition> competitionDAO = new CompetitionDAO(session);
        DAO<Run> runDAO = new RunDAO(session);
        DAO<Chip> chipDAO = new ChipDAO(session);
        DAO<Participant> participantDAO = new ParticipantDAO(session);
        DAO<RelayTeam> relayTeamDAO = new RelayTeamDAO(session);
        DAO<Distance> distanceDAO = new DistanceDAO(session);
        DAO<CompetitionGroup> competitionGroupDAO = new GroupDAO(session);
        DAO<Checkpoint> checkpointDAO = new CheckpointDAO(session);
        DAO<Sportsman> sportsmanDAO = new SportsmanDAO(session);

        // Creating Competition
        final Competition competition = new Competition();
        competition.setNameCompetition("First competition");
        competitionDAO.create(competition);

        // Creating Group
        final CompetitionGroup competitionGroup = new CompetitionGroup();
        competitionGroup.setNameGroup("Дети");
        competitionGroup.setAge(14);
        competitionGroup.setGender("Ж");
        competitionGroupDAO.create(competitionGroup);

        // Creating Distance
        final Distance distance = new Distance();
        distance.setLocated("Баринова роща");
        distance.setLength(1000);
        distanceDAO.create(distance);

        // Creating Run
        final Run run = new Run();
        run.setCompetition(competition);
        run.setDateRun(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        run.setDistance(distance);
        run.setGroup(competitionGroup);
        runDAO.create(run);

        // Creating Chip
        final Chip chip = new Chip();
        chip.setChipNumber("Donda");
        chipDAO.create(chip);

        // Creating RelayTeam
        final RelayTeam relayTeam = new RelayTeam();
        relayTeam.setNameRelayTeam("ВГДе");
        relayTeamDAO.create(relayTeam);

        final RelayTeam relayTeam1 = new RelayTeam();
        relayTeam1.setNameRelayTeam("БББ");
        relayTeamDAO.create(relayTeam1);


        // Creating Participant
        final Participant participant = new Participant();
        participant.setRun(run);
        participant.setChip(chip);
        participant.setRelayTeam(relayTeam);
        participant.setRelayStage(1);
        participant.setTag(237);
        participantDAO.create(participant);

        // Creating Checkpoint
        final Checkpoint checkpoint1 = new Checkpoint();
        checkpoint1.setCrossingTime(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        checkpoint1.setParticipant(participant);
        checkpointDAO.create(checkpoint1);

        final Checkpoint checkpoint2 = new Checkpoint();
        checkpoint2.setCrossingTime(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        checkpoint2.setParticipant(participant);
        checkpointDAO.create(checkpoint2);

        // Creating sportsman
        final Sportsman sportsman = new Sportsman();
        sportsman.setGender(Gender.MALE);
        sportsman.setBirthdate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        sportsman.setName("Mole");
        sportsman.setLastname("Ass");
        sportsman.setRegion("Vladimir");
        sportsmanDAO.create(sportsman);

        final Sportsman sportsman1 = new Sportsman();
        sportsman1.setGender(Gender.MALE);
        sportsman1.setBirthdate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        sportsman1.setName("Den");
        sportsman1.setLastname("ewfwfg");
        sportsman1.setRegion("Kam");
        sportsmanDAO.create(sportsman1);

        launch(args);

        session.close();
        HibernateUtil.closeSessionFactory();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/StartUp_Page.fxml"));
        AnchorPane pain = loader.load();
        stage.setScene(new Scene(pain));
        stage.show();
    }
}

