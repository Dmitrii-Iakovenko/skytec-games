package service;

import model.Clan;

import java.util.Optional;

public interface ClanService {
    Optional<Clan> getClan(long clanId);
    boolean changeClanGold(long clanId, int reward);
}
