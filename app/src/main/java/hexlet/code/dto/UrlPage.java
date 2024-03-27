package hexlet.code.dto;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.SQLException;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UrlPage extends BasePage {
    private Url url;
    private List<UrlCheck> urlCheckList;

    public List<UrlCheck> getUrlCheckList() {
        if (urlCheckList == null) {
            try {
                setUrlCheckList();
            } catch (SQLException e) {
                throw new RuntimeException();
            }
        }
        return urlCheckList;
    }

    public void setUrlCheckList() throws SQLException {
        this.urlCheckList = UrlCheckRepository.getEntitiesByUrlId(url.getId());
    }
}
