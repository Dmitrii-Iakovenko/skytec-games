package repository;

import lombok.RequiredArgsConstructor;
import model.Clan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcClanRepository implements EntityRepository<Clan> {
    private static final Logger logger = LoggerFactory.getLogger(JdbcClanRepository.class);
    private final DataSource dataSource;

    @Override
    public Optional<Clan> get(long clanId) {
        String sql = "SELECT id, name, gold FROM clans WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setLong(1, clanId);
            logger.debug("Executing query: {}", sql);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    int gold = resultSet.getInt("gold");
                    Clan clan = new Clan(id, name, gold);
                    logger.info("Clan found: {}", clan);
                    return Optional.of(clan);
                } else {
                    logger.info("Clan with id {} not found", clanId);
                    return Optional.empty();
                }
            } catch (SQLException e) {
                logger.error("Error executing query: {}", sql, e);
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.error("Database connection error", e);
            return Optional.empty();
        }
    }

    @Override
    public boolean save(Clan clan) {
        String sql = "UPDATE clans SET name = ?, gold = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, clan.getName());
            statement.setInt(2, clan.getGold());
            statement.setLong(3, clan.getId());

            logger.debug("Executing query: {}", sql);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Clan with id {} was updated successfully", clan.getId());
                return true;
            } else {
                logger.info("No clan was updated, check if the clan with id {} exists", clan.getId());
                return false;
            }
        } catch (SQLException e) {
            logger.error("Error updating the clan with id {}", clan.getId(), e);
            return false;
        }
    }
}