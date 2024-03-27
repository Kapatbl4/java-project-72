package hexlet.code.dto;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlsRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public class ListOfUrlsPage extends BasePage {
    private List<Url> urlsList;

    public List<Url> getUrlsList() {
        if (urlsList == null) {
            try {
                setUrlsList();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return urlsList;
    }

    public void setUrlsList() throws SQLException {
        this.urlsList = UrlsRepository.getEntities();
    }


    public Optional<UrlCheck> getLastUrlCheck(long id) {
        try {
            return UrlCheckRepository.getLast(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
