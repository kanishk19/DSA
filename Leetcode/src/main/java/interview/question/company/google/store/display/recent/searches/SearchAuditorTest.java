package interview.question.company.google.store.display.recent.searches;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchAuditorTest {

    private SearchAuditor searchAuditor;

    @BeforeEach
    public void setup(){
        searchAuditor = new SearchAuditor(3);
    }

    @Test
    public void testHappyCase(){
        searchAuditor.addSearch("abc");
        searchAuditor.addSearch("def");
        searchAuditor.addSearch("xyz");
        searchAuditor.addSearch("abc");

        searchAuditor.addSearch("efg");
        searchAuditor.addSearch("pqr");
        searchAuditor.addSearch("mno");

        List<String> response = searchAuditor.getRecentSearches();
        assertEquals(3, response.size());
    }
}
