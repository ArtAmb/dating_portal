package psk.projects.dating_portal.tags;

import lombok.Data;
import lombok.val;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class PotentialPartner {
    long userId;
    List<UserTag> userTags;
    double matchingRate;

    public PotentialPartner(long userId, List<UserTag> userTags) {
        this.userId = userId;
        this.userTags = userTags;

        this.matchingRate = userTags.size();
    }

    /**
     * obliczenie podobienstwa do preferencji
     * @param myPreferencesTag
     */
    public void calculateMatchingRate(List<UserTag> myPreferencesTag) {
        Map<Long, Double> mapByTagId = myPreferencesTag.stream()
                .collect(Collectors.toMap(UserTag::getTagId, u -> TagPriority.getValue(u.getPriority())));

        double tmpRate = 0;
        for (val oneTag : this.userTags) {
            tmpRate += TagPriority.getValue(oneTag.getPriority()) * mapByTagId.getOrDefault(oneTag.getTagId(), 0.0);
        }

        this.matchingRate = tmpRate;
    }

}
