package com.lifehouse.raceth.tmpstorage;

import com.lifehouse.raceth.model.*;
import com.lifehouse.raceth.model.competition.Competition;

import java.util.ArrayList;
import java.util.List;

//Не используются таблицы:
// Chip
public class TmpStorage {
    public static List<Checkpoint> checkpoints = new ArrayList<Checkpoint>();
    public static List<Competition> competitions = new ArrayList<Competition>();
    public static List<CompetitionDay> competitionDays = new ArrayList<>();
    public static List<Group> groups = new ArrayList<Group>();
    public static List<Distance> distances = new ArrayList<Distance>();
    public static List<Participant> participants = new ArrayList<Participant>();
    public static List<RelayTeam> relayTeams = new ArrayList<RelayTeam>();
    public static List<Start> starts = new ArrayList<Start>();
    public static List<Sportsman> sportsmen = new ArrayList<Sportsman>();
}
