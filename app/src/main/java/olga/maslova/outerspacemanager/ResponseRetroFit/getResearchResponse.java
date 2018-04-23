package olga.maslova.outerspacemanager.ResponseRetroFit;

import java.util.List;

import olga.maslova.outerspacemanager.Research;

public class getResearchResponse {
    private Integer size;
    private List<Research> searches;

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<Research> getSearches() {
        return searches;
    }

    public void setSearches(List<Research> searches) {
        this.searches = searches;
    }
}
