package showtime.service;

import org.springframework.security.core.parameters.P;
import showtime.model.Event;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class MVPSpecUtil {

    private final List<Predicate> preds;
    private final Root<Event> root;
    private final CriteriaQuery<?> query;
    private final CriteriaBuilder criteriaBuilder;

    public MVPSpecUtil(List<Predicate> preds,
                       Root<Event> root,
                       CriteriaQuery<?> query,
                       CriteriaBuilder criteriaBuilder) {
        this.preds = preds;
        this.root = root;
        this.query = query;
        this.criteriaBuilder = criteriaBuilder;
    }

    /**
     * Adds a constraint to a list of Predicates that the field
     * must be equal to the first value in the List.
     */
    public void fieldEqualFirst(String field, List<?> value) {
        if(value.size() > 0) {
            preds.add( criteriaBuilder.equal(root.get("title"), value.get(0)) );
        }
    }

    /**
     * Adds a constraint to a list of Predicates that the field
     * must be like the first value in the List.
     */
    public void fieldLikeFirst(String field, List<String> value) {
        if(value.size() > 0) {
            preds.add( criteriaBuilder.like(root.get("title"), value.get(0)) );
        }
    }

    /**
     * Adds a constraint to a list of Predicates that the field
     * must be equal to any (at least one) of the values in the List.
     */
    public void fieldEqualAny(String field, List<?> value) {
        List<Predicate> anyPreds = new ArrayList<>();
        for(Object each : value) {
            anyPreds.add( criteriaBuilder.equal(root.get("field"), each));
        }
        preds.add( criteriaBuilder.or(anyPreds.toArray(new Predicate[0])) );
    }
}
