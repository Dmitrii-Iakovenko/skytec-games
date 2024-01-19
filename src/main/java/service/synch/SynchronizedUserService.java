package service.synch;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.EntityRepository;
import service.ClanService;
import service.UserService;
import util.CacheUtility;

import java.util.Optional;

public class SynchronizedUserService implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(SynchronizedUserService.class);

    private final ClanService clanService;
    private final EntityRepository<User> repository;
    private final CacheUtility<User> cacheUtility;

    public SynchronizedUserService(SynchronizedClanService clanService, EntityRepository<User> repository) {
        this.clanService = clanService;
        this.repository = repository;
        cacheUtility = new CacheUtility<>(repository, User.class);
    }

    @Override
    public Optional<User> getUser(long userId) {
        return cacheUtility.getEntity(userId);
    }

    @Override
    public boolean addGoldToClan(long userId, int contribution) {
        Optional<User> userOptional = getUser(userId);
        if (userOptional.isEmpty()) {
            logger.error("Error adding gold to clan. User not found. User ID: {}, Contribution: {}", userId, contribution);
            return false;
        }

        User user = userOptional.get();
        synchronized (user) {
            if (user.getGold() < contribution) {
                logger.error("Error adding gold to clan. User doesn't have that much money. User ID: {}, Contribution: {}", userId, contribution);
                return false;
            }

            long clanId = user.getClanId();
            // TODO: need transaction
            if (clanService.changeClanGold(clanId, contribution)
                && changeUserGold(userId, contribution)
            ) {
                logger.info("Gold added to Clan ID: {}, UserID: {}, Contribution: {}", clanId, userId, contribution);
                return true;
            } else {
                logger.error("Error adding gold to clan. Clan ID: {}, User ID: {}, Contribution: {}", clanId, userId, contribution);
                return false;
            }
        }
    }

    @Override
    public boolean changeUserGold(long userId, int contribution) {
        // TODO: not implemented
        throw new RuntimeException("not implemented");
    }
}
