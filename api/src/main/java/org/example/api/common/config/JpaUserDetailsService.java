package org.example.api.common.config;

import lombok.RequiredArgsConstructor;
import org.example.api.common.config.model.MemberPrincipal;
import org.example.core.domain.member.Member;
import org.example.core.domain.member.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username);
        return new MemberPrincipal(member);
    }
}
