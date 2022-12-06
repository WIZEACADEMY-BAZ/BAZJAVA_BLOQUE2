package com.wizeline.learningjavamaven.configuration;

import com.wizeline.learningjavamaven.utils.exceptions.ExcepcionUnica;
import io.jsonwebtoken.*;
import org.apache.catalina.connector.Request;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.Principal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtTokenFilterTest {

  @InjectMocks
  JwtTokenFilter jwtTokenFilter;

  @Mock
  JwtTokenConfig jwtTokenConfig;

  @Test
  void doFilterInternalError() {
    HttpServletRequest request = request(false);
    HttpServletResponse response = response();
    FilterChain filterChain = filter();
    when(jwtTokenConfig.validateAccessToken("token")).thenReturn(false);
    ExcepcionUnica thrown = assertThrows(
        ExcepcionUnica.class,
        () -> jwtTokenFilter.doFilterInternal(request, response, filterChain),
        "Conversion incorrecta"
    );
    assertEquals(404, thrown.getCodigoError());
  }

  @Test
  void doFilterInternalJwtError() throws ServletException, IOException {
    HttpServletRequest request = request(true);
    HttpServletResponse response = response();
    FilterChain filterChain = filter();
    jwtTokenFilter.doFilterInternal(request, response, filterChain);
  }

  JwtParser parser() {
    return new JwtParser() {
      @Override
      public JwtParser requireId(String s) {
        return null;
      }

      @Override
      public JwtParser requireSubject(String s) {
        return null;
      }

      @Override
      public JwtParser requireAudience(String s) {
        return null;
      }

      @Override
      public JwtParser requireIssuer(String s) {
        return null;
      }

      @Override
      public JwtParser requireIssuedAt(Date date) {
        return null;
      }

      @Override
      public JwtParser requireExpiration(Date date) {
        return null;
      }

      @Override
      public JwtParser requireNotBefore(Date date) {
        return null;
      }

      @Override
      public JwtParser require(String s, Object o) {
        return null;
      }

      @Override
      public JwtParser setClock(Clock clock) {
        return null;
      }

      @Override
      public JwtParser setAllowedClockSkewSeconds(long l) {
        return null;
      }

      @Override
      public JwtParser setSigningKey(byte[] bytes) {
        return null;
      }

      @Override
      public JwtParser setSigningKey(String s) {
        return null;
      }

      @Override
      public JwtParser setSigningKey(Key key) {
        return null;
      }

      @Override
      public JwtParser setSigningKeyResolver(SigningKeyResolver signingKeyResolver) {
        return null;
      }

      @Override
      public JwtParser setCompressionCodecResolver(CompressionCodecResolver compressionCodecResolver) {
        return null;
      }

      @Override
      public boolean isSigned(String s) {
        return false;
      }

      @Override
      public Jwt parse(String s) throws ExpiredJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        return null;
      }

      @Override
      public <T> T parse(String s, JwtHandler<T> jwtHandler) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        return null;
      }

      @Override
      public Jwt<Header, String> parsePlaintextJwt(String s) throws UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        return null;
      }

      @Override
      public Jwt<Header, Claims> parseClaimsJwt(String s) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        return null;
      }

      @Override
      public Jws<String> parsePlaintextJws(String s) throws UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        return null;
      }

      @Override
      public Jws<Claims> parseClaimsJws(String s) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        return null;
      }
    };
  }

  HttpServletRequest request(boolean nulo) {
    return new HttpServletRequest() {
      @Override
      public String getAuthType() {
        return null;
      }

      @Override
      public Cookie[] getCookies() {
        return new Cookie[0];
      }

      @Override
      public long getDateHeader(String s) {
        return 0;
      }

      @Override
      public String getHeader(String s) {
        return nulo ? null : "Authorization token";
      }

      @Override
      public Enumeration<String> getHeaders(String s) {
        return null;
      }

      @Override
      public Enumeration<String> getHeaderNames() {
        return null;
      }

      @Override
      public int getIntHeader(String s) {
        return 0;
      }

      @Override
      public String getMethod() {
        return null;
      }

      @Override
      public String getPathInfo() {
        return null;
      }

      @Override
      public String getPathTranslated() {
        return null;
      }

      @Override
      public String getContextPath() {
        return null;
      }

      @Override
      public String getQueryString() {
        return null;
      }

      @Override
      public String getRemoteUser() {
        return null;
      }

      @Override
      public boolean isUserInRole(String s) {
        return false;
      }

      @Override
      public Principal getUserPrincipal() {
        return null;
      }

      @Override
      public String getRequestedSessionId() {
        return null;
      }

      @Override
      public String getRequestURI() {
        return null;
      }

      @Override
      public StringBuffer getRequestURL() {
        return null;
      }

      @Override
      public String getServletPath() {
        return null;
      }

      @Override
      public HttpSession getSession(boolean b) {
        return null;
      }

      @Override
      public HttpSession getSession() {
        return null;
      }

      @Override
      public String changeSessionId() {
        return null;
      }

      @Override
      public boolean isRequestedSessionIdValid() {
        return false;
      }

      @Override
      public boolean isRequestedSessionIdFromCookie() {
        return false;
      }

      @Override
      public boolean isRequestedSessionIdFromURL() {
        return false;
      }

      @Override
      public boolean isRequestedSessionIdFromUrl() {
        return false;
      }

      @Override
      public boolean authenticate(HttpServletResponse httpServletResponse) throws IOException, ServletException {
        return false;
      }

      @Override
      public void login(String s, String s1) throws ServletException {

      }

      @Override
      public void logout() throws ServletException {

      }

      @Override
      public Collection<Part> getParts() throws IOException, ServletException {
        return null;
      }

      @Override
      public Part getPart(String s) throws IOException, ServletException {
        return null;
      }

      @Override
      public <T extends HttpUpgradeHandler> T upgrade(Class<T> aClass) throws IOException, ServletException {
        return null;
      }

      @Override
      public Object getAttribute(String s) {
        return null;
      }

      @Override
      public Enumeration<String> getAttributeNames() {
        return null;
      }

      @Override
      public String getCharacterEncoding() {
        return null;
      }

      @Override
      public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

      }

      @Override
      public int getContentLength() {
        return 0;
      }

      @Override
      public long getContentLengthLong() {
        return 0;
      }

      @Override
      public String getContentType() {
        return null;
      }

      @Override
      public ServletInputStream getInputStream() throws IOException {
        return null;
      }

      @Override
      public String getParameter(String s) {
        return null;
      }

      @Override
      public Enumeration<String> getParameterNames() {
        return null;
      }

      @Override
      public String[] getParameterValues(String s) {
        return new String[0];
      }

      @Override
      public Map<String, String[]> getParameterMap() {
        return null;
      }

      @Override
      public String getProtocol() {
        return null;
      }

      @Override
      public String getScheme() {
        return null;
      }

      @Override
      public String getServerName() {
        return null;
      }

      @Override
      public int getServerPort() {
        return 0;
      }

      @Override
      public BufferedReader getReader() throws IOException {
        return null;
      }

      @Override
      public String getRemoteAddr() {
        return null;
      }

      @Override
      public String getRemoteHost() {
        return null;
      }

      @Override
      public void setAttribute(String s, Object o) {

      }

      @Override
      public void removeAttribute(String s) {

      }

      @Override
      public Locale getLocale() {
        return null;
      }

      @Override
      public Enumeration<Locale> getLocales() {
        return null;
      }

      @Override
      public boolean isSecure() {
        return false;
      }

      @Override
      public RequestDispatcher getRequestDispatcher(String s) {
        return null;
      }

      @Override
      public String getRealPath(String s) {
        return null;
      }

      @Override
      public int getRemotePort() {
        return 0;
      }

      @Override
      public String getLocalName() {
        return null;
      }

      @Override
      public String getLocalAddr() {
        return null;
      }

      @Override
      public int getLocalPort() {
        return 0;
      }

      @Override
      public ServletContext getServletContext() {
        return null;
      }

      @Override
      public AsyncContext startAsync() throws IllegalStateException {
        return null;
      }

      @Override
      public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
        return null;
      }

      @Override
      public boolean isAsyncStarted() {
        return false;
      }

      @Override
      public boolean isAsyncSupported() {
        return false;
      }

      @Override
      public AsyncContext getAsyncContext() {
        return null;
      }

      @Override
      public DispatcherType getDispatcherType() {
        return null;
      }
    };
  }

  HttpServletResponse response() {
    return new HttpServletResponse() {
      @Override
      public void addCookie(Cookie cookie) {

      }

      @Override
      public boolean containsHeader(String s) {
        return false;
      }

      @Override
      public String encodeURL(String s) {
        return null;
      }

      @Override
      public String encodeRedirectURL(String s) {
        return null;
      }

      @Override
      public String encodeUrl(String s) {
        return null;
      }

      @Override
      public String encodeRedirectUrl(String s) {
        return null;
      }

      @Override
      public void sendError(int i, String s) throws IOException {

      }

      @Override
      public void sendError(int i) throws IOException {

      }

      @Override
      public void sendRedirect(String s) throws IOException {

      }

      @Override
      public void setDateHeader(String s, long l) {

      }

      @Override
      public void addDateHeader(String s, long l) {

      }

      @Override
      public void setHeader(String s, String s1) {

      }

      @Override
      public void addHeader(String s, String s1) {

      }

      @Override
      public void setIntHeader(String s, int i) {

      }

      @Override
      public void addIntHeader(String s, int i) {

      }

      @Override
      public void setStatus(int i) {

      }

      @Override
      public void setStatus(int i, String s) {

      }

      @Override
      public int getStatus() {
        return 0;
      }

      @Override
      public String getHeader(String s) {
        return null;
      }

      @Override
      public Collection<String> getHeaders(String s) {
        return null;
      }

      @Override
      public Collection<String> getHeaderNames() {
        return null;
      }

      @Override
      public String getCharacterEncoding() {
        return null;
      }

      @Override
      public String getContentType() {
        return null;
      }

      @Override
      public ServletOutputStream getOutputStream() throws IOException {
        return null;
      }

      @Override
      public PrintWriter getWriter() throws IOException {
        return null;
      }

      @Override
      public void setCharacterEncoding(String s) {

      }

      @Override
      public void setContentLength(int i) {

      }

      @Override
      public void setContentLengthLong(long l) {

      }

      @Override
      public void setContentType(String s) {

      }

      @Override
      public void setBufferSize(int i) {

      }

      @Override
      public int getBufferSize() {
        return 0;
      }

      @Override
      public void flushBuffer() throws IOException {

      }

      @Override
      public void resetBuffer() {

      }

      @Override
      public boolean isCommitted() {
        return false;
      }

      @Override
      public void reset() {

      }

      @Override
      public void setLocale(Locale locale) {

      }

      @Override
      public Locale getLocale() {
        return null;
      }
    };
  }

  FilterChain filter() {
    return new FilterChain() {
      @Override
      public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {

      }
    };
  }
}