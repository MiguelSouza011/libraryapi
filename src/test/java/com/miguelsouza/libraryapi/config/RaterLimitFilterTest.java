package com.miguelsouza.libraryapi.config;

import jakarta.servlet.ServletException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockFilterChain;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class RaterLimitFilterTest {

    private RateLimitFilter rateLimitFilter;

    @BeforeEach
    void setUp() {
        rateLimitFilter = new RateLimitFilter();
    }

    @Test
    void devePermitirAte60Requisicoes() throws ServletException, IOException {

        String ip = "192.168.0.10";

        for (int i = 1; i <= 60; i++) {

            MockHttpServletRequest request =
                    new MockHttpServletRequest("GET", "/livros");

            request.setRemoteAddr(ip);

            MockHttpServletResponse response =
                    new MockHttpServletResponse();

            MockFilterChain filterChain = new MockFilterChain();

            rateLimitFilter.doFilter(request, response, filterChain);

            assertNotEquals(
                    429,
                    response.getStatus(),
                    "A requisição " + i + " não deveria ser bloqueada"
            );
        }
    }

    @Test
    void deveBloquearQuandoUltrapassar60Requisicoes()
            throws ServletException, IOException {

        String ip = "192.168.0.10";

        // Consome as 60 requisições permitidas
        for (int i = 1; i <= 60; i++) {

            MockHttpServletRequest request =
                    new MockHttpServletRequest("GET", "/livros");

            request.setRemoteAddr(ip);

            MockHttpServletResponse response =
                    new MockHttpServletResponse();

            MockFilterChain filterChain = new MockFilterChain();

            rateLimitFilter.doFilter(request, response, filterChain);
        }

        // Tenta realizar a 61ª requisição
        MockHttpServletRequest request =
                new MockHttpServletRequest("GET", "/livros");

        request.setRemoteAddr(ip);

        MockHttpServletResponse response =
                new MockHttpServletResponse();

        MockFilterChain filterChain = new MockFilterChain();

        rateLimitFilter.doFilter(request, response, filterChain);

        assertEquals(429, response.getStatus());
        assertNull(filterChain.getRequest());

        assertTrue(
                response.getContentAsString()
                        .contains("Too Many Requests")
        );
    }

    @Test
    void deveIsolarLimitePorIp() throws ServletException, IOException {
        consumirRequisicoes("192.168.0.10", 60);

        MockHttpServletRequest request =
                new MockHttpServletRequest("GET", "/livros");
        request.setRemoteAddr("192.168.0.20");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        rateLimitFilter.doFilter(request, response, chain);

        assertNotEquals(429, response.getStatus());
        assertSame(request, chain.getRequest());
    }

    private void consumirRequisicoes(String ip, int quantidade)
            throws ServletException, IOException {
        for (int i = 0; i < quantidade; i++) {
            MockHttpServletRequest request =
                    new MockHttpServletRequest("GET", "/livros");
            request.setRemoteAddr(ip);
            rateLimitFilter.doFilter(
                    request, new MockHttpServletResponse(), new MockFilterChain());
        }
    }

    @Test
    void devePermitirRequisicoesDeIpsDiferentes()
            throws ServletException, IOException {

        String ip1 = "192.168.0.10";
        String ip2 = "192.168.0.20";

        for (String ip : new String[]{ip1, ip2}) {

            MockHttpServletRequest request =
                    new MockHttpServletRequest("GET", "/livros");

            request.setRemoteAddr(ip);

            MockHttpServletResponse response =
                    new MockHttpServletResponse();

            MockFilterChain filterChain = new MockFilterChain();

            rateLimitFilter.doFilter(request, response, filterChain);

            assertNotEquals(429, response.getStatus());
        }
    }
}
