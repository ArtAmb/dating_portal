package psk.projects.dating_portal.tags;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class PartnerTag {
    String name;
    TagPriority priority;
}
