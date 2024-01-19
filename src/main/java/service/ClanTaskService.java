package service;

import model.ClanTask;

import java.util.Optional;

public interface ClanTaskService {
    Optional<ClanTask> getClanTask(long clanTaskId);
    boolean completeTask(long clanId, long clanTaskId);
}
