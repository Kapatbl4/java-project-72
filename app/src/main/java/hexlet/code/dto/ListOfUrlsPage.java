package hexlet.code.dto;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
public class ListOfUrlsPage extends BasePage {
    private List<Url> urlsList;

    public static Optional<UrlCheck> getLastUrlCheck(long id) {
        try {
            return UrlCheckRepository.getLast(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
