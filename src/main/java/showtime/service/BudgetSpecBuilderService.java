package showtime.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;
import showtime.model.Budget;

import java.util.List;

public class BudgetSpecBuilderService extends EventSpecBuilderService<Budget> {

    Logger logger = LoggerFactory.getLogger(BudgetSpecBuilderService.class);

/*    protected BudgetSpecBuilderService() { }

    public static BudgetSpecBuilderService newBudgetBuilder() {
        return new BudgetSpecBuilderService();
    }*/

    public BudgetSpecBuilderService byAmount(List<Double> amount) {
/*        spec = spec.and((root, query, criteriaBuilder) -> {
            // https://stackoverflow.com/questions/48529445/spring-data-jpa-specification-inheritance
            //Root<Budget> budgetRoot = criteriaBuilder.treat(root, Budget.class);
            return criteriaBuilder.equal(root.get("amount"), 0.0);
        });*/
        spec = spec.and(new SpecEqualOneFrom<>("amount", amount));
        return this;
    }

    public BudgetSpecBuilderService byCategory(List<String> category) {
        spec = spec.and(new SpecEqualOneFrom<>("category", category));
        return this;
    }

    public BudgetSpecBuilderService byTransactionUserid(List<Integer> transxUserid) {
        spec = spec.and(new SpecEqualOneFrom<>("ebudTransactionUserid", transxUserid));
        return this;
    }

    @Override
    public BudgetSpecBuilderService fromMultiValueMap(MultiValueMap<String, String> params) {
        //super.fromMultiValueMap(params);
        byAmount( MVPUtil.parseDoubleNoexcept(params.get("amount")) );
        byCategory( MVPUtil.toStringNoexcept(params.get("category")) );
        byTransactionUserid( MVPUtil.parseIntNoexcept(params.get("transactionuserid")) );
        return this;
    }
}
