package dwfms.execution.petrinet;

public class Arc {

    Place place;
    Transition transition;
    Direction direction;
    int weight = 1;

    enum Direction {

        PLACE_TO_TRANSITON {
            @Override
            public boolean active(Place p, int weight) {
                return p.hasTokens(weight);
            }

            @Override
            public void fire(Place p, int weight) {
                p.removeTokens(weight);
            }
        },
        TRANSITION_TO_PLACE {
            @Override
            public boolean active(Place p, int weight) {
                return p.hasTokens(weight);
            }


            @Override
            public void fire(Place p, int weight) {
                p.addTokens(weight);
            }
        };

        public abstract boolean active(Place p, int weight);
        public abstract void fire (Place p, int weight);
    }

    private Arc(Direction d, Place p, Transition t) {
        super();
        this.direction = d;
        this.place = p;
        this.transition = t;
    }

    Arc(Place p, Transition t) {
        this(Direction.PLACE_TO_TRANSITON, p, t);
        t.addIncoming(this);
    }

    Arc(Transition t, Place p) {
        this(Direction.TRANSITION_TO_PLACE, p, t);
        t.addOutgoing(this);
    }

    public boolean active() {
        return direction.active(place, weight);
    }

    public void fire() {
        this.direction.fire(place, weight);
    }

    public boolean canFire() {
        return direction.active(place, weight);
    }

}
