package br.com.desafio.marvel.threads;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by alexcosta on 29/11/16.
 */
public class SpringSecurityRunnableWrapper implements Runnable {
    private SecurityContext context;
    private Runnable delegate;

    public SpringSecurityRunnableWrapper(SecurityContext context, Runnable delegate) {
        this.context = context;
        this.delegate = delegate;
    }

    public void run() {
        try {
            SecurityContextHolder.setContext(context);
            delegate.run();
        } finally {
            SecurityContextHolder.clearContext();
        }
    }
}