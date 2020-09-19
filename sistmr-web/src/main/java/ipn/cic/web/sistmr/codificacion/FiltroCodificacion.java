/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.codificacion;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletResponse;

/**
 * Filtro para servlets que codifica la informacion enviada por un formulario utilizando UTF8
 * 
 * @author Iliac Huerta Trujillo <ihuertat@ipn.mx>
 */
@WebFilter(urlPatterns = {"/*"},
           initParams = {
                    @WebInitParam(name = "codificacion", value = "UTF-8")})
public class FiltroCodificacion implements Filter {
    private String codificacion;
    
    public FiltroCodificacion(){
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        codificacion = filterConfig.getInitParameter("codificacion");
        if(codificacion == null){
            codificacion = "UTF-8";
        }
    }

    @Override
    public void doFilter(ServletRequest request, 
                         ServletResponse response, 
                         FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(codificacion);
        ((HttpServletResponse)response).setHeader("X-UA-Compatible", "IE=EmulateIE8");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
    
}
