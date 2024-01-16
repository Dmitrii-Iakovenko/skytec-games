package service;

import model.Clan;

// Так же у нас есть ряд сервисов похожих на эти.
// Их суть в том, что они добавляют(или уменьшают) золото в казне клана
public class UserAddGoldService { // пользователь добавляет золото из собственного кармана

    private final ClanService clans;

    public UserAddGoldService(ClanService clans) {
        this.clans = clans;
    }

    public void addGoldToClan(long userId, long clanId, int gold) {
        Clan clan = clans.getClan(clanId);
        // clan.[gold] += gold;
        // как-то сохранить изменения
    }
}
