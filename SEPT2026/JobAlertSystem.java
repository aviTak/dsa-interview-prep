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
