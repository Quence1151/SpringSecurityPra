package com.example.springsecuritypra.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import io.jsonwebtoken.*;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;


@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {

	private String secretKey = "c3ByaW5nLWJvb3Qtc2VjdXJpdHktand0LXR1dG9yaWFsLWppd29vbi1zcHJpbmctYm9vdC1zZWN1cml0eS1qd3QtdHV0b3JpYWwK";
	private final UserDetailsService userDetailsService;
	private long tokenValidTime = 1000 * 60 * 30L;

	@PostConstruct
	protected void init() {  // 객체 초기화, secretKey를 Base64로 인코딩
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	// JWT token create
	public String createToken(String userPk, Collection<? extends GrantedAuthority> roles) {

		Claims claims = Jwts.claims().setSubject(userPk); // JWT payload에 저장되는 정보 단위
		claims.put("ROLE_", roles);  //
		Date now = new Date();

		return Jwts.builder()
				.setClaims(claims) // 정보저장
				.setIssuedAt(now)  // 토큰 발행 시간
				.setExpiration(new Date(now.getTime() + tokenValidTime)) // 만료 시간 세팅
				.signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘 + secretKey = signature
				.compact();
	}

	// JWT token에서 인증 정보 조회
	public Authentication getAuthentication(String token) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	// token에서 회원 정보 추출
	public String getUserPk(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}

	// Request의 Header에서 token 값 가져오기    "X-AUTH-TOKEN" : "token value"
	public String resolveToken(HttpServletRequest request) {
		return request.getHeader("X-AUTH-TOKEN");
	}

	// token 유효성 , 만료일자 확인
	public boolean validateToken(String jwtToken) {
		try {
			System.out.println(jwtToken+"here2~");
			Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwtToken);
			return true;
		} catch (io.jsonwebtoken.security.SecurityException| MalformedJwtException e) {
			log.info("잘못된 JWT 서명입니다.");
		} catch (ExpiredJwtException e) {
			log.info("만료된 JWT 토큰입니다.");
		} catch (UnsupportedJwtException e) {
			log.info("지원되지 않는 JWT 토큰입니다.");
		} catch (IllegalArgumentException e) {
			log.info("JWT 토큰이 잘못되었습니다.");
		}
		return false;
	}
}