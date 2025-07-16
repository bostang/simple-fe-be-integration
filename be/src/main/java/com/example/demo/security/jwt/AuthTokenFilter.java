package com.example.demo.security.jwt;

import com.example.demo.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Component
// Kelas ini digunakan untuk memfilter setiap permintaan HTTP yang masuk
// untuk memeriksa apakah ada token JWT yang valid dalam header permintaan
// Jika token valid, maka pengguna akan diotentikasi dan informasi pengguna akan disimpan dalam konteks keamanan
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    // Metode ini akan dipanggil untuk setiap permintaan HTTP yang masuk
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request); // Mendapatkan token dari header
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) { // Validasi token
                String username = jwtUtils.getUserNameFromJwtToken(jwt); // Mendapatkan username

                UserDetails userDetails = userDetailsService.loadUserByUsername(username); // Memuat UserDetails
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null, // Password null karena sudah terautentikasi via token
                                userDetails.getAuthorities()); // Dapatkan authorities/roles

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication); // Set autentikasi di konteks keamanan
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response); // Lanjutkan filter chain
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7); // Mengambil bagian token setelah "Bearer "
        }

        return null;
    }
}