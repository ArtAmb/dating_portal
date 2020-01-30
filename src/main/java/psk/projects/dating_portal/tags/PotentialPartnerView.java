package psk.projects.dating_portal.tags;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class PotentialPartnerView {
    long userId;
    Long imageAvatarId;
    String description;
    String login;
    double matchingRate;

    List<PartnerTag> tags;
}
