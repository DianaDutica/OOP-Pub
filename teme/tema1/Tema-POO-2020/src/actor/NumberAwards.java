package actor;

import fileio.ActorInputData;

import java.util.List;

/**
 *
 */
public class NumberAwards {
    /**
     *
     * @param actorData
     */
    public static void numberAwards(final List<ActorInputData> actorData) {
        for (ActorInputData actor : actorData) {
            for (ActorsAwards award : actor.getAwards().keySet()) {
                actor.setNrAwards(actor.getNrAwards() + actor.getAwards().get(award));
            }
        }
    }
}
