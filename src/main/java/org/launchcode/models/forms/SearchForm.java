package org.launchcode.models.forms;

import org.launchcode.models.JobFieldType;

/**
 * Created by LaunchCode
 */
public class SearchForm {

    // The search options
    private JobFieldType[] fields = JobFieldType.values();

    // The selected search options
    private JobFieldType searchField = JobFieldType.ALL; //ALL is the default value
    // search.html has Thymeleaf call the setter methods to set the searchField and keyword ...
            // ... based on form inputs/selections.
    // The SearchController adds SearchForm searchForm as a model attribute for the search/results route

    // The search string
    private String keyword;

    public JobFieldType getSearchField() {
        return searchField;
    }

    public void setSearchField(JobFieldType searchField) {
        this.searchField = searchField;
    }

    public JobFieldType[] getFields() {
        return fields;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
