package dwfms.execution.petrinet;

import lombok.Data;

@Data
public class Place {

    int tokens = 0;

    protected Place(int tokens) {
        this.tokens = tokens;
    }

    public boolean hasTokens(int weight) {
        return this.tokens >= weight;
    }

    public void removeTokens(int weight) {
        this.tokens -= weight;
    }

    public void addTokens(int weight) {
        this.tokens += weight;
    }
}
