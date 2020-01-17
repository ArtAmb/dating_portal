package psk.projects.dating_portal.auth;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.HashMap;

@RestController
public class TestController {

    @GetMapping("/test/magic")
    @RolesAllowed(Roles.Consts.ROLE_NORMAL)
    @PreAuthorize("hasRole('" + Roles.Consts.ROLE_NORMAL + "')")
    public HashMap<String, Object> test() {
        HashMap<String, Object> tmp = new HashMap<>();
        tmp.put("result", "EXCELLENT");

        return tmp;
    }

    @GetMapping("/am/i/authorized")
    public Principal amIAuthorized(Principal principal) {
        return principal;

    }

}
