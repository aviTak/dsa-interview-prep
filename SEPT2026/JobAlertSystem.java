/*

## Smart Alert Rule Engine (Java)

We need an alert mechanism to notify actively looking jobseekers about new job postings that align with their specified criteria. Given the high volume of incoming jobs, a rule-based engine is necessary to efficiently match jobs to jobseekers and trigger the appropriate alerts.

Design an in-memory `JobAlertSystem` that matches `Job` objects against a user's `AlertRule`. The matching logic must support a single rule for each user which can have a composite rule.

The expectation is to **design the core classes and methods** required for this system.

### Example Rules

* `(Title = "Software Engineer")`
* `(Title contains "Senior" OR Title contains "Staff") AND (NOT Company = "Acme Corp")`


*/

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

// Domain model representing incoming job postings
record Job(String id, String title, String company,
           String location, int salary) {}

// Composite Pattern interface for rule evaluation
@FunctionalInterface
interface AlertRule {
    boolean matches(Job job);

    default AlertRule and(AlertRule other) {
        return job -> this.matches(job) && other.matches(job);
    }

    default AlertRule or(AlertRule other) {
        return job -> this.matches(job) || other.matches(job);
    }

    default AlertRule not() {
        return job -> !this.matches(job);
    }
}

// Concrete leaf predicates for field-level matching
class JobRules {
    public static AlertRule titleEquals(String title) {
        return job -> job.title() != null && 
                      job.title().equalsIgnoreCase(title);
    }

    public static AlertRule titleContains(String keyword) {
        return job -> job.title() != null && 
                      job.title().toLowerCase()
                         .contains(keyword.toLowerCase());
    }

    public static AlertRule companyEquals(String company) {
        return job -> job.company() != null && 
                      job.company().equalsIgnoreCase(company);
    }

    public static AlertRule minSalary(int min) {
        return job -> job.salary() >= min;
    }
}

// Core alert engine managing user subscriptions and job matching
public class JobAlertSystem {
    // Thread-safe map of userId -> active AlertRule
    private final Map<String, AlertRule> userRules = 
        new ConcurrentHashMap<>();

    public void registerRule(String userId, AlertRule rule) {
        if (rule == null) {
            userRules.remove(userId);
        } else {
            userRules.put(userId, rule);
        }
    }

    public void removeRule(String userId) {
        userRules.remove(userId);
    }

    // Matches a job against all registered user rules
    public List<String> findMatchingUsers(Job job) {
        List<String> matchedUsers = new ArrayList<>();
        for (var entry : userRules.entrySet()) {
            if (entry.getValue().matches(job)) {
                matchedUsers.add(entry.getKey());
            }
        }
        return matchedUsers;
    }
}
