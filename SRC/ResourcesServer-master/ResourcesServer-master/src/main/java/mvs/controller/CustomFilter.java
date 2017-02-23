package mvs.controller;
/**
 * Author: quangminh
 * Date:   2016-18-5
 * Time:   9:51 AM
 */


import mvs.*;
import mvs.Entity.AppConfig;
import mvs.Entity.Response;
import mvs.model.Token;
import mvs.repository.TokenRepository;
import mvs.repository.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CustomFilter implements Filter {
    String url = "http://localhost:8523/oauth20/tokens/";
    @Autowired
    TokenService tokenService;

    String pattern = "^(http|https):\\/\\/([^\\/]*)";
    Pattern r = Pattern.compile(pattern);

    /**
     * Called by the web container to indicate to a filter that it is being
     * placed into service. The servlet container calls the init method exactly
     * once after instantiating the filter. The init method must complete
     * successfully before the filter is asked to do any filtering work.
     * <p/>
     * The web container cannot place the filter into service if the init method
     * either:
     * <ul>
     * <li>Throws a ServletException</li>
     * <li>Does not return within a time period defined by the web
     * container</li>
     * </ul>
     *
     * @param filterConfig The configuration information associated with the
     *                     filter instance being initialised
     * @throws javax.servlet.ServletException if the initialisation fails
     */


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * The <code>doFilter</code> method of the Filter is called by the container
     * each time a request/response pair is passed through the chain due to a
     * client request for a resource at the end of the chain. The FilterChain
     * passed in to this method allows the Filter to pass on the request and
     * response to the next domain in the chain.
     * <p/>
     * A typical implementation of this method would follow the following
     * pattern:- <br>
     * 1. Examine the request<br>
     * 2. Optionally wrap the request object with a custom implementation to
     * filter content or headers for input filtering <br>
     * 3. Optionally wrap the response object with a custom implementation to
     * filter content or headers for output filtering <br>
     * 4. a) <strong>Either</strong> invoke the next domain in the chain using
     * the FilterChain object (<code>chain.doFilter()</code>), <br>
     * 4. b) <strong>or</strong> not pass on the request/response pair to the
     * next domain in the filter chain to block the request processing<br>
     * 5. Directly set headers on the response after invocation of the next
     * domain in the filter chain.
     *
     * @param request  The request to process
     * @param response The response associated with the request
     * @param chain    Provides access to the next filter in the chain for this
     *                 filter to pass the request and response to for further
     *                 processing
     * @throws java.io.IOException            if an I/O error occurs during this filter's
     *                                        processing of the request
     * @throws javax.servlet.ServletException if the processing fails for any other reason
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Tested: don't need this
        //refreshNeo4jSession(request);

        // allow CORS request
        CORSFilter(request, response, chain);
    }

    private void CORSFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest=(HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if(httpServletRequest.getMethod().compareTo("OPTIONS")==0){
            httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
            httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
            httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
            httpServletResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, token, Content-Type, Accept");
            chain.doFilter(request, httpServletResponse);
            return;
        }

        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");




        // check permission here
        AppConfig.getAppConfig();
        Response r= Oauth2Filter.checkToken(tokenService,request);
        if(r.getStatusCode()==200){
            mvs.Entity.Token token=(mvs.Entity.Token)r.getBody();
            String userId=token.getUserId();
            request.setAttribute("true_user_id", userId);
        }
        else{
            ((HttpServletResponse) response).setStatus(r.getStatusCode());
            ((HttpServletResponse) response).sendError(r.getStatusCode(), (String) r.getBody());
            return;
        }
        chain.doFilter(request, httpServletResponse);

    }

    /**
     * Called by the web container to indicate to a filter that it is being
     * taken out of service. This method is only called once all threads within
     * the filter's doFilter method have exited or after a timeout period has
     * passed. After the web container calls this method, it will not call the
     * doFilter method again on this instance of the filter. <br>
     * <br>
     * <p/>
     * This method gives the filter an opportunity to clean up any resources
     * that are being held (for example, memory, file handles, threads) and make
     * sure that any persistent state is synchronized with the filter's current
     * state in memory.
     */
    @Override
    public void destroy() {

    }


}
