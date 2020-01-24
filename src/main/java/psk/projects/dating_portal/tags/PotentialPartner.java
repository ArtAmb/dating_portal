package psk.projects.dating_portal.tags;

import lombok.Value;

import java.util.List;

@Value
public class PotentialPartner {
    long userId;
    List<UserTag> userTags;
    int matchingRate;

    public PotentialPartner(long userId, List<UserTag> userTags) {
        this.userId = userId;
        this.userTags = userTags;

        this.matchingRate = userTags.size();
    }
}
