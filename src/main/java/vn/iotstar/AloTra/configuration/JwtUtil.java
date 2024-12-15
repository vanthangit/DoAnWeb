package vn.iotstar.AloTra.configuration;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    private final JwtDecoder jwtDecoder;

    public JwtUtil(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    public void authenticateAndSetContext(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        List<GrantedAuthority> authorities = jwt.getClaimAsStringList("scope").stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(jwt.getSubject(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
