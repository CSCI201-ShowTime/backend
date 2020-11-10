package showtime.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;
import showtime.model.Event;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class EventSpecification implements Specification<Event> {

    private final MultiValueMap<String, String> params;

    // TODO: parse array in ctor
    public EventSpecification(MultiValueMap<String, String> params) {
        this.params = params;
    }

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> preds = new ArrayList<>();

        List<Integer> eventid = MVPUtil.parseIntNoexcept( params.get("eventid") );
        if(eventid.size() > 0) {
            preds.add( criteriaBuilder.equal(root.get("eventid"), eventid.get(0)) );
        }

        List<Integer> userid = MVPUtil.parseIntNoexcept( params.get("userid") );
        if(userid.size() > 0) {
            preds.add( criteriaBuilder.equal(root.get("userid"), userid.get(0)) );
        }

        List<Timestamp> start = MVPUtil.parseTimeStampNoexcept( params.get("start") );
        if(start.size() >= 2) {
            preds.add( criteriaBuilder.between(root.get("start"), start.get(0), start.get(1)) );
        }
        else if(start.size() >= 1) {
            preds.add( criteriaBuilder.equal(root.get("start"), start.get(0)) );
        }

        List<Timestamp> end = MVPUtil.parseTimeStampNoexcept( params.get("end") );
        if(end.size() >= 2) {
            preds.add( criteriaBuilder.between(root.get("end"), end.get(0), end.get(1)) );
        }
        else if(end.size() >= 1) {
            preds.add( criteriaBuilder.equal(root.get("end"), end.get(0)) );
        }

        List<String> title = MVPUtil.toStringNoexcept( params.get("title") );
        if(title.size() > 0) {
            preds.add( criteriaBuilder.equal(root.get("title"), title.get(0)) );
        }

        List<String> description = MVPUtil.toStringNoexcept( params.get("description") );
        if(description.size() > 0) {
            preds.add( criteriaBuilder.like(root.get("description"), description.get(0)) );
        }

        List<Integer> visibility = MVPUtil.parseIntNoexcept( params.get("visibility") );
        if(visibility.size() > 0) {
            preds.add( criteriaBuilder.equal(root.get("visibility"), visibility.get(0)) );
        }

        List<Integer> type = MVPUtil.parseIntNoexcept( params.get("type") );
        if(type.size() > 0) {
            preds.add( criteriaBuilder.equal(root.get("type"), type.get(0)) );
        }

        List<String> location = MVPUtil.toStringNoexcept( params.get("location") );
        List<Predicate> locationAnyMatchPreds = new ArrayList<>();
        for(String each : location) {
            locationAnyMatchPreds.add( criteriaBuilder.equal(root.get("location"), each) );
        }
        preds.add( criteriaBuilder.or(locationAnyMatchPreds.toArray(new Predicate[0])) );

        return criteriaBuilder.and(preds.toArray(new Predicate[0]));
    }

}
